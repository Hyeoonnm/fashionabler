package com.fashionabler.service.member;

import com.fashionabler.dao.member.MemberDao;
import com.fashionabler.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberDao memberDao;

    @Override
    public int signup(Member member) {

        return memberDao.signup(member);
    }

    @Override
    public List<Member> memberList() {
        return memberDao.memberList();
    }
}
