package com.fashionabler.dao.member;

import com.fashionabler.model.member.Member;

import java.util.List;

public interface MemberDao {
    int signup(Member member);

    List<Member> memberList();
}
