package com.fashionabler.dao;

import com.fashionabler.mapper.MemberMapper;
import com.fashionabler.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao {
    private final MemberMapper memberMapper;

    @Override
    public int signup(Member member) {
        return memberMapper.signup(member);
    }

    @Override
    public List<Member> memberList() {
        return memberMapper.memberList();
    }
}
