package com.fashionabler.service;

import com.fashionabler.dao.MemberDao;
import com.fashionabler.model.Member;
import com.fashionabler.util.Encorder;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
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
