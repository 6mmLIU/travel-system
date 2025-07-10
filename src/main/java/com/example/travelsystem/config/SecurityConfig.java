package com.example.travelsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                // 开发时关闭 CSRF，生产环境请根据需要开启
                .csrf(csrf -> csrf.disable())

                // 授权规则
                .authorizeHttpRequests(auth -> auth

                        // 1. 登录/注册、静态资源、错误页 对所有人开放
                        .requestMatchers(
                                "/users/login",
                                "/users/register",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/auth/error"      // 403 专用错误页
                        ).permitAll()

                        // 2. 列表页 /tour-lines/all 仅 ADMIN 可访问
                        .requestMatchers(HttpMethod.GET, "/tour-lines/all")
                        .hasRole("ADMIN")

                        // 3. 上下架接口 仅 ADMIN
                        .requestMatchers(HttpMethod.POST,
                                "/tour-lines/publish/**",
                                "/tour-lines/unpublish/**"
                        ).hasRole("ADMIN")

                        // 4. 其余所有 /tour-lines/** 登录用户可访问（详情、搜索、筛选、新增页面/提交等）
                        .requestMatchers("/tour-lines/**")
                        .authenticated()

                        // 会员管理全部留给 ADMIN
                        .requestMatchers("/members/**")
                        .hasRole("ADMIN")

                        // 其他所有请求只要登录即可
                        .anyRequest().authenticated()
                )

                // 表单登录配置
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl("/users/index", true)
                        .permitAll()
                )

                // 注销配置
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/users/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                // 无权限（403）时跳转到自定义错误页
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/auth/error")
                )
        ;

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
