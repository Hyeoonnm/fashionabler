package com.fashionabler.api;

import com.fashionabler.model.EmailMember;
import com.fashionabler.model.Member;
import com.fashionabler.service.EmailService;
import com.fashionabler.service.MemberService;
import com.fashionabler.util.Encorder;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/memberApi")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    private final EmailService emailService;

    @PostMapping(value = "/sendEmail", produces = MediaType.TEXT_PLAIN_VALUE)
    public String mailConfirm(@RequestBody EmailMember emailMember) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(emailMember.getMemberEmail());
        return authCode;
    }

    /**
     * @param member
     * @return 0 회원가입 실패 / 1 회원가입 성공 / 2 비밀번호 8자리 이상 / 3 이메일 형식
     */
    @PostMapping("/signup")
    public int signup(@RequestBody Member member) {

        if (member.getMemberPasswords().length() < 8) {
            return 2;
        }
        if (!member.getMemberEmail().contains("@")) {
            return 3;
        }

        // 비밀번호 암호화
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
