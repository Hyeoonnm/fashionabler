package com.fashionabler.dao;

import com.fashionabler.model.Member;

import java.util.List;

public interface MemberDao {
    int signup(Member member);

    List<Member> memberList();
}
