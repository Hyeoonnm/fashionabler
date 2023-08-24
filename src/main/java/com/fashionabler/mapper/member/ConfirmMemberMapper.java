package com.fashionabler.mapper.member;

import com.fashionabler.model.member.EmailMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfirmMemberMapper {
    void save(EmailMember emailMember);

    int findByEmail(String memberEmail);

    void update(EmailMember emailMember);

    int checkEmail(EmailMember emailMember);

    void deleteEmail(String memberEmail);

    void confirmEmail(String memberEmail);

    Integer checkConfirmMember(String memberEmail);
}
