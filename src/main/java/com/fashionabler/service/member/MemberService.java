package com.fashionabler.service.member;

import com.fashionabler.model.member.Member;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Map<String, String> signup(Member member);
    List<Member> memberList();

    Map<String, String> validateHandling(BindingResult errors);
}
