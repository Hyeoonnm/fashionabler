package com.fashionabler.api;

import com.fashionabler.model.member.EmailMember;
import com.fashionabler.model.member.Member;
import com.fashionabler.service.member.ConfirmMemberService;
import com.fashionabler.service.member.EmailService;
import com.fashionabler.service.member.MemberService;
import com.fashionabler.util.RedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
    public ResponseEntity<Map<String, String>> mailConfirm(@Valid @RequestBody EmailMember emailMember, BindingResult errors) throws MessagingException, UnsupportedEncodingException, Exception {
        if (errors.hasErrors()) {
            Map<String, String> errorsMap = memberService.validateHandling(errors);
            return ResponseEntity.badRequest().body(errorsMap);
        }

        String emailCode = emailService.sendEmail(emailMember.getMemberEmail());
        emailMember.setAuthCode(emailCode);

        Map<String, String> redisSave = confirmMemberService.save(emailMember);
        if (redisSave.containsKey("RedisKeyAlready")) {
            return ResponseEntity.badRequest().body(redisSave);
        } else return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Map<String, String>> checkEmail(@RequestBody EmailMember emailMember) {

        Map<String, String> map = confirmMemberService.checkAuthCode(emailMember);
        HttpStatus httpStatus;
        if (map.containsKey("emailCheck")) {
            httpStatus = HttpStatus.OK;
        } else httpStatus = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(httpStatus).body(map);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid Member member, BindingResult errors) throws JsonProcessingException {
        if (errors.hasErrors()) {
            Map<String, String> errorsMap = memberService.validateHandling(errors);
            return ResponseEntity.badRequest().body(errorsMap);
        }
        Map<String, String> signup = memberService.signup(member);
        if (signup.containsKey("dupl") || signup.containsKey("confirm") || signup.containsKey("correct")) {
            return ResponseEntity.badRequest().body(signup);
        } else return ResponseEntity.ok().build();
    }
}
