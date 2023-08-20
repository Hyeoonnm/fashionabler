package com.fashionabler.api;

import com.fashionabler.model.Member;
import com.fashionabler.service.MemberService;
import com.fashionabler.util.Encorder;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/memberApi")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("/signup")
    public int signup(@RequestBody Member member) {
        String passwordSecurity = Encorder.hashpw(member.getMemberPasswords(), BCrypt.gensalt());
        member.setMemberPasswords(passwordSecurity);

        List<Member> memberList = memberService.memberList();
        for (Member duplMember :
                memberList) {
            if (member.getMemberId().equals(duplMember.getMemberId())) {
                return 0;
            }
        }
        return memberService.signup(member);
    }
}
