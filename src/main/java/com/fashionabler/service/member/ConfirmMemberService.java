package com.fashionabler.service.member;

import com.fashionabler.model.member.EmailMember;

public interface ConfirmMemberService {
    void save(EmailMember emailMember);

    int checkEmail(EmailMember emailMember);

    void deleteEmail(String memberEmail);

    void confirmEmail(String memberEmail);

    int checkConfirmMember(String memberEmail);
}
