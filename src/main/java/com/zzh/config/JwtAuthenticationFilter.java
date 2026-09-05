package com.zzh.config;

import com.zzh.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.slf4j.Logger;


@Component   // ⭐ 让 Spring 把它注册成 Bean
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserDetailsService userDetailsService;   // ⭐ 字段，不是方法体

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        System.out.println("=== JwtFilter START, token=" + (request.getHeader("Authorization") != null ?
                request.getHeader("Authorization").substring(0, 30) + "..." : "null"));

        // ① 剥前缀 → 取 token
        String header = request.getHeader("Authorization");
        String token = null;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        // ② 验签 + 取 username（一次 parseToken，不再调两次）
        if (token != null) {
            try {
                Claims claims = jwtUtil.parseToken(token);
                // parseToken 内已含验证签名+验过期
                String username = claims.getSubject();         //subject 就是 username

                // ③ 判 SecurityContext 为空（防重复）
                if (username != null
                        && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // ④ 重新查库 → 拿最新 authorities(权限)
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken auth =   // ⭐ 创建
                            // Authentication 对象(一张"已登录工牌"，上面写"你是谁 + 你有什么角色")
                        new UsernamePasswordAuthenticationToken(
                            userDetails,             // principal
                            null,                    // credentials 永存空
                            userDetails.getAuthorities()   // ⭐ 拿 ROLE_USER/ROLE_ADMIN
                        );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    // 把工牌装进口袋 ← 这才是"装"的落点
                }
            } catch (ExpiredJwtException e) {
                log.warn("❌ JWT 已过期: {}", e.getMessage());
            } catch (Exception e) {
                log.error("❌ JWT 认证失败", e);
            }
        }

        // ⑤ 放行
        filterChain.doFilter(request, response);
    }
}
