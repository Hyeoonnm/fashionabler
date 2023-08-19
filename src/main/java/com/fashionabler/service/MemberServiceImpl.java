package com.fashionabler.service;

import com.fashionabler.mapper.MemberMapper;
import com.fashionabler.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberMapper memberMapper;

    @Override
    public List<Member> memberList() {
        return memberMapper.memberList();
    }
}
