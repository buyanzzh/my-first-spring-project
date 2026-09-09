package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.entity.*;
import com.zzh.mapper.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // 所有依赖都是 final：一旦注入就不能再改
    private final UserMapper userMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final UserRoleMapper userRoleMapper;
    private final PermissionMapper permissionMapper;
    private final RoleMapper roleMapper;

    // 关键：只有「一个」构造器，所有依赖一次性列在参数里。
    // 不是你自己 new，是 Spring 造这个 Bean 时，按参数类型从容器里找到对应的 Bean 塞进来。
    public UserDetailsServiceImpl(UserMapper userMapper,
                                  RolePermissionMapper rolePermissionMapper,
                                  UserRoleMapper userRoleMapper,
                                  PermissionMapper permissionMapper,
                                  RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.userRoleMapper = userRoleMapper;
        this.permissionMapper = permissionMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 五步单表查询（不是 JOIN 联查！面试别说错）：
        // user → user_role → role → role_permission → permission

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
