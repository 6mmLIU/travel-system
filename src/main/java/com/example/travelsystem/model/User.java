package com.example.travelsystem.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;                // 用户角色：USER / ADMIN / MERCHANT 等
    private String nickname;            // 昵称
    private String email;               // 邮箱
    private String avatar;              // 头像 URL
    private LocalDateTime registerTime; // 注册时间（对应 register_time 列）
}
