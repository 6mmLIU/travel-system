// src/main/java/com/example/travelsystem/service/UserService.java
package com.example.travelsystem.service;

import com.example.travelsystem.model.User;

import java.util.List;

public interface UserService {
    /**
     * 用户注册：会对密码进行加密后保存
     */
    void register(User user);

    /**
     * 根据用户名查找用户（登录时使用）
     */
    User findByUsername(String username);

    /**
     * 根据 ID 查找用户（个人中心、后台管理使用）
     */
    User findById(Integer userId);

    /**
     * 更新用户基本信息（昵称、头像、邮箱等）
     */
    void updateUserInfo(User user);

    /**
     * 修改密码：先校验 oldPassword，再加密 newPassword 并更新
     */
    void changePassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 分页 + 排序查询所有用户
     * @param page          当前页，从 1 开始
     * @param size          每页记录数
     * @param sortField     排序字段，如 "username", "register_time"
     * @param sortDirection 排序方向："ASC" 或 "DESC"
     */
    List<User> getAllUsers(int page, int size, String sortField, String sortDirection);

    /**
     * 删除指定 ID 的用户
     */
    void deleteUser(Integer userId);

    /**
     * 统计用户总数（后台分页必备）
     */
    int countUsers();

    /**
     * 发布线路（后台管理员操作）
     */
    void publishTourLine(Integer id);

    /**
     * 下架线路（后台管理员操作）
     */
    void unpublishTourLine(Integer id);
}
