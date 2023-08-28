package com.fashionabler.service.member;

import com.fashionabler.dao.member.ConfirmMemberDao;
import com.fashionabler.model.member.EmailMember;
import com.fashionabler.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConfirmMemberServiceImpl implements ConfirmMemberService {
    private final RedisUtil redisUtil;

    @Override
    public Map<String, String> save(EmailMember emailMember) {
        if (redisUtil.getData(emailMember.getAuthCode()) == null) {
            redisUtil.setData(emailMember.getAuthCode(), emailMember.getMemberEmail(), 180000);
            Map<String, String> map = new HashMap<>();
            map.put("RedisKeySave", "인증번호를 발송하였습니다.");
            return map;
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("RedisKeyAlready", "이미 인증번호를 발송하였습니다");
            return map;
        }
    }

    @Override
    public Map<String, String> checkAuthCode(EmailMember emailMember) {
        if (redisUtil.getData(emailMember.getAuthCode()) == null || !redisUtil.getData(emailMember.getAuthCode()).equals(emailMember.getMemberEmail())) {
            Map<String, String> map = new HashMap<>();
            map.put("emailFail", "인증번호를 다시 입력해주세요.");

            return map;
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("emailCheck", "이메일 인증 완료");
            return map;
        }
    }

}
