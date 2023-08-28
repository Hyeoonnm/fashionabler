package com.fashionabler.service.member;

import com.fashionabler.model.member.EmailMember;

import java.util.Map;

public interface ConfirmMemberService {
    Map<String, String> save(EmailMember emailMember);

    Map<String, String> checkAuthCode(EmailMember emailMember);
}
