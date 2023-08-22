package com.fashionabler.service.member;

import com.fashionabler.dao.member.MemberDao;
import com.fashionabler.model.member.Member;
import lombok.RequiredArgsConstructor;
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
    public int signup(Member member) {

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
