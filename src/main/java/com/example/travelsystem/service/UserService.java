package com.example.travelsystem.service;

import com.example.travelsystem.model.User;

import java.util.List;

public interface UserService {
    // 用户注册
    void register(User user);

    // 根据用户名查找用户
    User findByUsername(String username);

    // 更新用户信息
    void updateUserInfo(User user);

    // 修改密码
    void changePassword(Integer userId, String oldPassword, String newPassword);

    // 分页和排序查询用户
    List<User> getAllUsers(int page, int size, String sortField, String sortDirection);

    // 删除用户
    void deleteUser(Integer userId);

    // 统计用户总数
    int countUsers();

    // 根据ID查找用户
    User findById(Integer userId);

    void publishTourLine(Integer id);

    void unpublishTourLine(Integer id);
}
