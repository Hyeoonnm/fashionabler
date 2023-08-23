package com.fashionabler.api;

import com.fashionabler.model.member.EmailMember;
import com.fashionabler.model.member.Member;
import com.fashionabler.service.member.ConfirmMemberService;
import com.fashionabler.service.member.EmailService;
import com.fashionabler.service.member.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/memberApi")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;
    private final EmailService emailService;
    private final ConfirmMemberService confirmMemberService;

    @PostMapping(value = "/sendEmail")
    public ResponseEntity<Map<String, String>> mailConfirm(@Valid @RequestBody EmailMember emailMember, BindingResult errors) throws MessagingException, UnsupportedEncodingException {
        if (errors.hasErrors()) {
            Map<String, String> errorsMap = memberService.validateHandling(errors);
            return ResponseEntity.badRequest().body(errorsMap);
        }

        String emailCode = emailService.sendEmail(emailMember.getMemberEmail());
        emailMember.setAuthCode(emailCode);
        // TODO return save에 따른 값으로 변경
        // 사용자 eamil 정보 , 인증번호 정보 db에 저장
        confirmMemberService.save(emailMember);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Integer> checkEmail(@RequestBody EmailMember emailMember) {
        int confirmInt = confirmMemberService.checkEmail(emailMember);
        if (confirmInt == 1) {
            confirmMemberService.confirmEmail(emailMember.getMemberEmail());
            // 성공
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        // 실패
        else return ResponseEntity.badRequest().body(0);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid Member member, BindingResult errors) throws JsonProcessingException {
        if (errors.hasErrors()) {
            Map<String, String> errorsMap = memberService.validateHandling(errors);
            return ResponseEntity.badRequest().body(errorsMap);
        }

        int signup = memberService.signup(member);
        if (signup == 2) {
            Map<String, String> duplMap = new HashMap<>();
            duplMap.put("dupl", "중복된 아이디입니다.");
            return ResponseEntity.badRequest().body(duplMap);
        }

        if (confirmMemberService.checkConfirmMember(member.getMemberEmail()) == 1) {
            confirmMemberService.deleteEmail(member.getMemberEmail());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            Map<String, String> nonConfirm = new HashMap<>();
            nonConfirm.put("confirm", "인증번호를 입력하셔야 합니다.");
            return ResponseEntity.badRequest().body(nonConfirm);
        }

    }
}
