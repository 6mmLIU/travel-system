package com.example.travelsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 关闭 CSRF（示例用，实际可酌情开启）
                .csrf(csrf -> csrf.disable())

                // 1) 配置权限
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/login",
                                "/users/register",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/error"
                        ).permitAll()
                        // 如果你要限制线路/会员管理为管理员
                        .requestMatchers("/tour-lines/**", "/members/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // 2) 表单登录
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl("/users/index", true)
                        .permitAll()
                )

                // 3) 注销
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                )

                // 4) 处理 403 权限不足：跳转到 /error
                .exceptionHandling(e -> e.accessDeniedPage("/auth/error"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
