package com.example.travelsystem.service.impl;

import com.example.travelsystem.mapper.TourLineMapper;
import com.example.travelsystem.mapper.UserMapper;
import com.example.travelsystem.model.User;
import com.example.travelsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户业务实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TourLineMapper tourLineMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* ===================== 注册 / 登录相关 ===================== */

    @Override
    @Transactional
    public void register(User user) {
        // 1. 用户名唯一性校验
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        // 2. 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 3. 插入数据库
        userMapper.insert(user);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User findById(Integer userId) {
        return userMapper.findById(userId);
    }

    /* ===================== 个人信息 ===================== */

    @Override
    @Transactional
    public void updateUserInfo(User user) {
        userMapper.updateProfile(user);   // 只改昵称 / 邮箱 / 头像等
    }

    @Override
    @Transactional
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User u = userMapper.findById(userId);
        if (u == null || !passwordEncoder.matches(oldPassword, u.getPassword())) {
            throw new IllegalArgumentException("旧密码错误");
        }
        userMapper.updatePassword(userId, passwordEncoder.encode(newPassword));
    }

    /* ===================== 后台管理：分页 / 统计 / 删除 ===================== */

    @Override
    public List<User> getAllUsers(int page, int size, String sortField, String sortDirection) {
        int offset  = (page - 1) * size;
        String orderBy = sortField + " " + sortDirection;   // 例：username ASC
        return userMapper.findAllUsers(offset, size, orderBy);
    }

    @Override
    public int countUsers() {
        return userMapper.count();
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        userMapper.deleteById(userId);
    }

    /* ===================== 旅游线路上下架（管理员功能） ===================== */

    @Override
    @Transactional
    public void publishTourLine(Integer id) {
        tourLineMapper.publishById(id);
    }

    @Override
    @Transactional
    public void unpublishTourLine(Integer id) {
        tourLineMapper.unpublishById(id);
    }
}
