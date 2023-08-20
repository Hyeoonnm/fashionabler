$('#sendEmail').on('click', function () {
    const data = {
        memberEmail: $('#memberEmail').val(),
    }

    $.ajax({
        url: "../memberApi/sendEmail",
        type: "post",
        contentType: "application/json",
        dataType: 'JSON',
        data: JSON.stringify(data),
        success: function (data) {
            console.log(data);
            // // 인증번호 확인 이벤트 리스너를 등록
            // $('#confirmEmail').off('click').on('click', function () {
            //     if ($('#memberEmailConfirm').val() === data.memberEmail) {
            //         alert("이메일 인증 성공")
            //     } else {
            //         alert("인증번호가 다릅니다.")
            //     }
            // });
        },
        error: function (error) {
            console.log("sendEmail Api Error");
            console.log(error);
        }
    });
});

$('#signupBtn').on('click', function () {
    const data = {
        memberId: $('#memberId').val(),
        memberPasswords: $('#memberPasswords').val(),
        memberEmail: $('#memberEmail').val(),
        memberPhone: $('#memberPhone').val()
    }

    $.ajax({
        url: "../memberApi/signup",
        type: "post",
        contentType: "application/json",
        dataType: 'JSON',
        data: JSON.stringify(data),
        success: function (data) {
            if (data === 1) {
                alert("회원가입 성공")
                window.location.href = "/"
            } else if (data === 0) {
                alert("중복된 아이디입니다.");
            } else if (data === 2) {
                alert("비밀번호 8자리 이상 입력해주세요.");
            } else if (data === 3) {
                alert("이메일을 형식에 맞게 작성하세요");
            }
        },
        error: function (error) {
            alert(error);
        }
    });
});