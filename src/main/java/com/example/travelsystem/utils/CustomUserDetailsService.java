package com.example.travelsystem.utils;

import com.example.travelsystem.model.User;
import com.example.travelsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义 UserDetailsService 用于从数据库/Service 中加载用户信息
 * 并构造出 Spring Security 所需要的 UserDetails 对象。
 */
@Service
@Primary // 如果有多个 UserDetailsService 实例，使用此注解优先使用这个
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    // 假设你有一个 UserService，能通过用户名查到 User 对象

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库/Service 查找
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // 假设 user.getRole() 返回 "ADMIN" 或 "USER" 等角色标识
        String role = user.getRole();
        if (role == null || role.trim().isEmpty()) {
            // 若没设置角色，可默认给个普通用户角色
            role = "USER";
        }

        // 使用 Spring Security 提供的便捷构造器
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())  // 数据库里的已加密密码
                .authorities(role)
                .build();
    }
}
