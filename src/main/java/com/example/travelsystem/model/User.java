package com.example.travelsystem.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;

    // 如果你的数据库里是 datetime 或 timestamp，可以用 LocalDateTime 或其他类型
    private String createdAt;
    private String nickname;
    private String email;
}
