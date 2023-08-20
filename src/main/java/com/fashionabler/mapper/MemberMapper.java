package com.fashionabler.mapper;

import com.fashionabler.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    int signup(Member member);

    List<Member> memberList();
}