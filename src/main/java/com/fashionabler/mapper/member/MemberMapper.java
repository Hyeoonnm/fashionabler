package com.fashionabler.mapper.member;

import com.fashionabler.model.member.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    int signup(Member member);

    List<Member> memberList();
}
