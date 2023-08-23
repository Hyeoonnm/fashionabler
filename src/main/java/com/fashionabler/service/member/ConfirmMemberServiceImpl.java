package com.fashionabler.service.member;

import com.fashionabler.dao.member.ConfirmMemberDao;
import com.fashionabler.model.member.EmailMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmMemberServiceImpl implements ConfirmMemberService{
    private final ConfirmMemberDao confirmMemberDao;

    @Override
    public void save(EmailMember emailMember) {
        int byEmail = confirmMemberDao.findByEmail(emailMember);
        if (byEmail == 0) {
            confirmMemberDao.save(emailMember);
        } else confirmMemberDao.update(emailMember);
    }

    @Override
    public int checkEmail(EmailMember emailMember) {
        return confirmMemberDao.checkEmail(emailMember);
    }

    @Override
    public void deleteEmail(String memberEmail) {
        confirmMemberDao.deleteEmail(memberEmail);
    }

    @Override
    public void confirmEmail(String memberEmail) {
        confirmMemberDao.confirmEmail(memberEmail);
    }

    @Override
    public int checkConfirmMember(String memberEmail) {
        return confirmMemberDao.checkConfirmMember(memberEmail);
    }
}
