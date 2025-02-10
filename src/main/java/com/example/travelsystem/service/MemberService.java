package com.example.travelsystem.service;

import com.example.travelsystem.mapper.MemberMapper;
import com.example.travelsystem.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberService {
    List<Member> getAllMembers(int page, int size);

    int getTotalCount();

    void addMember(Member member);

    Member getMemberById(Integer id);

    void updateMember(Member member);

    void deleteMember(Integer id);

    List<Member> searchMembers(String keyword); // 新增方法
}
