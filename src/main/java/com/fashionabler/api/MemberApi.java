package com.fashionabler.api;

import com.fashionabler.model.member.EmailMember;
import com.fashionabler.model.member.Member;
import com.fashionabler.service.member.ConfirmMemberService;
import com.fashionabler.service.member.EmailService;
import com.fashionabler.service.member.MemberService;
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
    private final ConfirmMemberService confirmMemberService;

    @PostMapping(value = "/sendEmail")
    public int mailConfirm(@RequestBody EmailMember emailMember) throws MessagingException, UnsupportedEncodingException {
        String emailCode = emailService.sendEmail(emailMember.getMemberEmail());
        emailMember.setAuthCode(emailCode);
        // TODO return save에 따른 값으로 변경
        // 사용자 eamil 정보 , 인증번호 정보 db에 저장
        confirmMemberService.save(emailMember);
        return 1;
    }

    @PostMapping("/checkEmail")
    public int checkEmail(@RequestBody EmailMember emailMember) {
        int confirmInt = confirmMemberService.checkEmail(emailMember);
        if (confirmInt == 1) {
            // 인증번호 인증 완료시 해당 db정보 삭제
            confirmMemberService.deleteEmail(emailMember.getMemberEmail());
            // 성공
            return 1;
        }
        // 실패
        else return 0;
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
        // TODO 암호 복호화 작업 해야함
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
