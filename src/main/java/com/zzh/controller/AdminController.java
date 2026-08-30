package com.zzh.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController                                              // ④ 标记这是接口类
@RequestMapping("/api/admin")                                // ⑤ 路由前缀
public class AdminController {

    @GetMapping("/info")                                     // ⑥ GET 请求，路径 /api/admin/info
    @PreAuthorize("hasRole('ADMIN')")                        // ⑦ ⭐ 核心：只有 ADMIN 角色能进
    public Map<String, String> info() {
        // ⑨ 从 SecurityContext 拿当前登录用户名
        String currentUser = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return Map.of("msg", "只有 ADMIN 能看到: " + currentUser);
    }
}