package com.fashionabler.service.member;

import com.fashionabler.dao.member.ConfirmMemberDao;
import com.fashionabler.dao.member.MemberDao;
import com.fashionabler.model.member.Member;
import com.fashionabler.util.Encorder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;
    private final ConfirmMemberDao confirmMemberDao;

    @Override
    @Transactional
    public Map<String, String> signup(@Valid Member member) {
        // 비밀번호 암호화
        // TODO 암호 복호화 작업 해야함
        String passwordSecurity = Encorder.hashpw(member.getMemberPasswords(), BCrypt.gensalt());
        member.setMemberPasswords(passwordSecurity);

        // 중복 회원 검사
        List<Member> memberList = memberList();
        for (Member duplMember : memberList) {
            if (member.getMemberId().equals(duplMember.getMemberId())) {
                Map<String, String> duplMap = new HashMap<>();
                duplMap.put("dupl", "중복된 아이디입니다.");
                return duplMap;
            }
        }

        // 이메일 인증번호 유효성 검사
        if (confirmMemberDao.checkConfirmMember(member.getMemberEmail()) == null) {
            Map<String, String> nonConfirm = new HashMap<>();
            nonConfirm.put("confirm", "인증번호를 입력하셔야 합니다.");
            return nonConfirm;
        } else if (confirmMemberDao.checkConfirmMember(member.getMemberEmail()) == 0) {
            Map<String, String> confirmBtn = new HashMap<>();
            confirmBtn.put("correct", "인증완료 버튼을 눌러주세요.");
            return confirmBtn;
        } else {
            memberDao.signup(member);
            Map<String, String> signupMap = new HashMap<>();
            confirmMemberDao.deleteEmail(member.getMemberEmail());
            signupMap.put("signup", "회원 가입 완료");
            return signupMap;
        }
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
