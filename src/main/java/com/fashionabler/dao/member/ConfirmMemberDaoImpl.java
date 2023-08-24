package com.fashionabler.dao.member;

import com.fashionabler.mapper.member.ConfirmMemberMapper;
import com.fashionabler.model.member.EmailMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConfirmMemberDaoImpl implements ConfirmMemberDao{
    private final ConfirmMemberMapper confirmMemberMapper;

    @Override
    public void save(EmailMember emailMember) {
        confirmMemberMapper.save(emailMember);
    }

    @Override
    public int findByEmail(EmailMember emailMember) {
        return confirmMemberMapper.findByEmail(emailMember.getMemberEmail());
    }

    @Override
    public void update(EmailMember emailMember) {
        confirmMemberMapper.update(emailMember);
    }

    @Override
    public int checkEmail(EmailMember emailMember) {
        return confirmMemberMapper.checkEmail(emailMember);
    }

    @Override
    public void deleteEmail(String memberEmail) {
        confirmMemberMapper.deleteEmail(memberEmail);
    }

    @Override
    public void confirmEmail(String memberEmail) {
        confirmMemberMapper.confirmEmail(memberEmail);
    }

    @Override
    public Integer checkConfirmMember(String memberEmail) {
        return confirmMemberMapper.checkConfirmMember(memberEmail);
    }
}
