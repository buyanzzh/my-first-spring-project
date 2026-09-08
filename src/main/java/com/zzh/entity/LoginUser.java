package com.zzh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
    private User user;
    private List<String> roleKeys;      // [ADMIN]——裸的，不带前缀
    private List<String> permKeys;      // [tree:query, ...]——不带前缀

    @Override
    // 返回权限信息  authorities(权限)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 关键：Spring Security 约定角色前要加 "ROLE_" 前缀（@PreAuthorize("hasRole('USER')") 才能匹配）
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (String roleKey : roleKeys) {
            list.add(new SimpleGrantedAuthority("ROLE_" + roleKey));
        }
        for(String permKey : permKeys){
            list.add(new SimpleGrantedAuthority(permKey));
        }
        return list;
    }

    @Override public String getPassword() { return user.getPassword(); } // BCrypt 哈希字符串
    @Override public String getUsername() { return user.getUsername(); }

    // 这 4 个都返回 true（账号/凭据都正常；以后做封号/过期再细化）
    @Override public boolean isAccountNonExpired()    { return true; }
    @Override public boolean isAccountNonLocked()     { return true; }
    @Override public boolean isCredentialsNonExpired(){ return true; }
    @Override public boolean isEnabled()              { return true; }
}
