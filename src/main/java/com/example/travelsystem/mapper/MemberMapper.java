package com.example.travelsystem.mapper;

import com.example.travelsystem.model.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 使用注解实现分页查询
    @Select("SELECT * FROM members ORDER BY id ASC LIMIT #{offset}, #{size}")
    List<Member> getAllMembers(@Param("offset") int offset, @Param("size") int size);

    // 使用注解实现获取总记录数
    @Select("SELECT COUNT(*) FROM members")
    int getTotalCount();

    // 使用注解实现添加会员
    @Insert("INSERT INTO members (name, email, phone, status) VALUES (#{name}, #{email}, #{phone}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addMember(Member member);

    // 使用 XML 配置实现根据 ID 查询会员
    Member getMemberById(@Param("id") Integer id);

    // 使用 XML 配置实现更新会员信息
    void updateMember(Member member);

    // 使用 XML 配置实现删除会员
    void deleteMember(@Param("id") Integer id);

    // 使用 XML 配置实现搜索会员
    List<Member> searchMembers(@Param("keyword") String keyword);
}
