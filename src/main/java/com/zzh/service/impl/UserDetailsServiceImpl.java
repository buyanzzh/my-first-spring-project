package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.entity.LoginUser;
import com.zzh.entity.User;
import com.zzh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查 DB
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        User user = userMapper.selectOne(queryWrapper.eq(User::getUsername, username));

        // 2. 查不到 → throw new UsernameNotFoundException(...)
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        // 3. 查到 → 封装成 org.springframework.security.core.userdetails.User
        //         (参数：username + hashedPassword + authorities(角色转 SimpleGrantedAuthority))

        return new LoginUser(user);
    }
}
