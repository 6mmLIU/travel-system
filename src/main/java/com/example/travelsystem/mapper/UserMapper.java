package com.example.travelsystem.mapper;

import com.example.travelsystem.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    User findByUsername(String username);

    User findById(Integer id);

    void updateUser(User user);

    void updatePassword(@Param("id") Integer id, @Param("password") String password);

    void deleteUser(Integer id);

    int countUsers();

    List<User> findAll(@Param("offset") int offset,
                       @Param("limit") int limit,
                       @Param("sortField") String sortField,
                       @Param("sortDirection") String sortDirection);
}
