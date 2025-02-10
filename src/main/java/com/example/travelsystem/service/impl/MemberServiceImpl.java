package com.example.travelsystem.service.impl;

import com.example.travelsystem.mapper.MemberMapper;
import com.example.travelsystem.model.Member;
import com.example.travelsystem.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Member> getAllMembers(int page, int size) {
        int offset = (page - 1) * size;
        return memberMapper.getAllMembers(offset, size);
    }

    @Override
    public int getTotalCount() {
        return memberMapper.getTotalCount();
    }

    @Override
    public void addMember(Member member) {
        memberMapper.addMember(member);
    }

    @Override
    public Member getMemberById(Integer id) {
        return memberMapper.getMemberById(id);
    }

    @Override
    public void updateMember(Member member) {
        memberMapper.updateMember(member);
    }

    @Override
    public void deleteMember(Integer id) {
        memberMapper.deleteMember(id);
    }

    @Override
    public List<Member> searchMembers(String keyword) {
        return memberMapper.searchMembers(keyword); // 新增搜索功能调用
    }
}
