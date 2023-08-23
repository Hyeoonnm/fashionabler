package com.fashionabler.service.member;

import com.fashionabler.dao.member.MemberDao;
import com.fashionabler.model.member.Member;
import com.fashionabler.util.Encorder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberDao memberDao;

    @Override
    public int signup(@Valid Member member) {
        // 비밀번호 암호화
        // TODO 암호 복호화 작업 해야함
        String passwordSecurity = Encorder.hashpw(member.getMemberPasswords(), BCrypt.gensalt());
        member.setMemberPasswords(passwordSecurity);

        // 중복 회원 검사
        List<Member> memberList = memberList();
        for (Member duplMember : memberList) {
            if (member.getMemberId().equals(duplMember.getMemberId())) {
                return 2;
            }
        }
        return memberDao.signup(member);
    }

    @Override
    public List<Member> memberList() {
        return memberDao.memberList();
    }

    @Override
    public Map<String, String> validateHandling(BindingResult errors) {
        Map<String, String> errorsMap = new HashMap<>();
        for (FieldError error : errors.getFieldErrors()
        ) {
            String validKeyName = String.format("valid_%s", error.getField());
            errorsMap.put(validKeyName, error.getDefaultMessage());
        }
        return errorsMap;
    }
}
