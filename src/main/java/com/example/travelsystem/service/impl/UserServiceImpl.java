package com.example.travelsystem.service.impl;

import com.example.travelsystem.mapper.UserMapper;
import com.example.travelsystem.model.User;
import com.example.travelsystem.service.TourLineService;
import com.example.travelsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TourLineService tourLineService;

    @Override
    public void register(User user) {
        // 注册时可以对 createdAt 设置一个当前时间
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        user.setCreatedAt(now);

        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 可以给一个默认角色
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("ROLE_USER");
        }
        userMapper.insertUser(user);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void updateUserInfo(User user) {
        User existingUser = userMapper.findById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        // 这里如果想在更新时同步更新时间，也可以加上
        // String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // user.setCreatedAt(now);

        userMapper.updateUser(user);
    }

    @Override
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        userMapper.updatePassword(userId, passwordEncoder.encode(newPassword));
    }

    @Override
    public List<User> getAllUsers(int page, int size, String sortField, String sortDirection) {
        int offset = (page - 1) * size;
        return userMapper.findAll(offset, size, sortField, sortDirection);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        userMapper.deleteUser(userId);
    }

    @Override
    public int countUsers() {
        return userMapper.countUsers();
    }

    @Override
    public User findById(Integer userId) {
        return userMapper.findById(userId);
    }
    @Override
    public void publishTourLine(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid TourLine ID");
        }
        tourLineService.publishTourLine(id);
    }

    @Override
    public void unpublishTourLine(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid TourLine ID");
        }
        tourLineService.unpublishTourLine(id);
    }

}
