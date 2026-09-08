package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.entity.*;
import com.zzh.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 一 . 查 Database
        //五表联查

        //1 . user
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().
                                eq(User::getUsername, username));

        //返回值 容器（用于封装查询到的角色和权限）
        List<String> roleKeys = new ArrayList<>();
        List<String> permKeys = new ArrayList<>();

        if(user != null){
            //2 . userRole
            List<UserRole> userRoles =
                    userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().
                    eq(UserRole::getUserId, user.getId()));

            //3. 如果是无角色用户：直接返回空 authorities
            List<Integer> roleIds = List.of();
            if(!userRoles.isEmpty()) {
                roleIds = userRoles.stream().map(UserRole::getRoleId).toList();
                List<Role> roles = roleMapper.selectBatchIds(roleIds);
                roles.forEach(r -> roleKeys.add(r.getRoleKey()));
            }else{
                return new LoginUser(user, List.of(), List.of());
            }

            //4 . rolePermission
            List<RolePermission> rps  = rolePermissionMapper
                    .selectList(new LambdaQueryWrapper<RolePermission>()
                            .in(RolePermission::getRoleId, roleIds));
            List<Integer> permIds = rps.stream().
                   map(RolePermission::getPermissionId).distinct().toList();

            //5 . permission
            if (!permIds.isEmpty()) {
                List<Permission> perms = permissionMapper.selectBatchIds(permIds);
                perms.forEach(p -> permKeys.add(p.getPermKey()));
            }
        }else{
            throw new UsernameNotFoundException("user not found");
        }

        return new LoginUser(user, roleKeys, permKeys);
    }
}
