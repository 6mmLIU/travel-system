// src/main/java/com/example/travelsystem/mapper/UserMapper.java
package com.example.travelsystem.mapper;

import com.example.travelsystem.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 与 UserMapper.xml 一一对应的接口
 */
@Mapper          // 让 Spring Boot + MyBatis 扫描到
public interface UserMapper {

    /* 1. 注册：插入新用户（XML 中 <insert id="insert">） */
    void insert(User user);

    /* 2. 根据用户名查询（<select id="findByUsername">） */
    User findByUsername(@Param("username") String username);

    /* 3. 根据 ID 查询（<select id="findById">） */
    User findById(@Param("id") Integer id);

    /* 4. 更新可变字段：昵称 / 邮箱 / 角色（<update id="updateProfile">） */
    int updateProfile(User user);

    /* 5. 修改密码（<update id="updatePassword">，用 map 也行，用两个参数也行） */
    int updatePassword(@Param("id") Integer id,
                       @Param("password") String encodedPassword);

    /* 6. 删除用户（<delete id="deleteById">） */
    void deleteById(@Param("id") Integer id);

    /* 7. 统计总数（<select id="count">） */
    int count();

    /* 8. 分页 + 排序查询（<select id="findAllUsers">）
       offset = (page-1)*size，size = pageSize
       orderBy = "username ASC" or "created_at DESC" */
    List<User> findAllUsers(@Param("offset") int offset,
                            @Param("size")   int size,
                            @Param("orderBy") String orderBy);
}
