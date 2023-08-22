package com.fashionabler.api;

import com.fashionabler.model.member.EmailMember;
import com.fashionabler.model.member.Member;
import com.fashionabler.service.member.ConfirmMemberService;
import com.fashionabler.service.member.EmailService;
import com.fashionabler.service.member.MemberService;
import com.fashionabler.util.Encorder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid Member member, BindingResult errors) throws JsonProcessingException {
        if (errors.hasErrors()) {
            Map<String, String> errorsMap = memberService.validateHandling(errors);
            return ResponseEntity.badRequest().body(errorsMap);
        }

        // 비밀번호 암호화
        // TODO 암호 복호화 작업 해야함
        String passwordSecurity = Encorder.hashpw(member.getMemberPasswords(), BCrypt.gensalt());
        member.setMemberPasswords(passwordSecurity);

        List<Member> memberList = memberService.memberList();
        for (Member duplMember : memberList) {
            if (member.getMemberId().equals(duplMember.getMemberId())) {
                Map<String, String> duplMap = new HashMap<>();
                duplMap.put("dupl", "중복된 아이디입니다.");
                return ResponseEntity.badRequest().body(duplMap);
            }
        }

        memberService.signup(member);
        Map<String, String> singupMap = new HashMap<>();
        singupMap.put("signup", "회원가입 성공");
        return ResponseEntity.ok(singupMap);
    }
}
