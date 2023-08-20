package com.fashionabler.service;

import com.fashionabler.model.Member;

import java.util.List;

public interface MemberService {
    int signup(Member member);
    List<Member> memberList();
}
