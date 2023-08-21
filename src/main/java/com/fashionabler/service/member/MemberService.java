package com.fashionabler.service.member;

import com.fashionabler.model.member.Member;

import java.util.List;

public interface MemberService {
    int signup(Member member);
    List<Member> memberList();
}
