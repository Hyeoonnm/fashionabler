package com.fashionabler.controller;

import com.fashionabler.model.Member;
import com.fashionabler.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @RequestMapping("/memberList")
    public List<Member> memberList() {
        List<Member> list = memberService.memberList();
        return list;
    }
}
