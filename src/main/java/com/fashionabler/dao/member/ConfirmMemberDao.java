package com.fashionabler.dao.member;

import com.fashionabler.model.member.EmailMember;

public interface ConfirmMemberDao {
    void save(EmailMember emailMember);

    int findByEmail(EmailMember emailMember);

    void update(EmailMember emailMember);

    int checkEmail(EmailMember emailMember);

    void deleteEmail(String memberEmail);
}
