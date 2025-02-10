package com.example.travelsystem.model;

import lombok.Data;

@Data
public class Member {
    private Integer id;         // 会员 ID
    private String name;        // 姓名
    private String email;       // 邮箱
    private String phone;       // 电话
    private String status;      // 状态 (ACTIVE / INACTIVE)
    private String createdAt;   // 创建时间
}
