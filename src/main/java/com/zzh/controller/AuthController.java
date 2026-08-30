package com.zzh.controller;

import com.zzh.utils.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager am, JwtUtil jwtUtil) {
        this.authenticationManager = am; this.jwtUtil = jwtUtil;
    }

    // 登录：认证成功 → 发 token
    @PostMapping("/api/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        String token = jwtUtil.generateToken(req.username());
        return Map.of("token", token);
    }

    // 验证：带 token 访问 → 返回当前用户名（证明过滤器生效）
    @GetMapping("/api/me")
    public String me() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public record LoginRequest(String username, String password) {}
}
