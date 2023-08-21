
// 이메일 전송 fetch
document.getElementById("sendEmail").onclick = function () {
    const inputVal = document.querySelector("input[name='memberEmail']").value;

    if (!CheckEmail(inputVal)) {
        return alert("정확한 이메일 형식을 작성해주세요.")
    }

    const data = {
        memberEmail: inputVal,
    }

    fetch("../memberApi/sendEmail", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data === 1) {
                alert("인증번호를 전송하였습니다.");
            }
        })
        .catch((error) => console.log(error));
};

// 회원가입 fetch
document.getElementById("signupBtn").onclick = function () {
    const memberId = document.querySelector("input[name='memberId']").value;
    const memberPasswords = document.querySelector("input[name='memberPasswords']").value;
    const memberEmail = document.querySelector("input[name='memberEmail']").value;
    const memberPhone = document.querySelector("input[name='memberPhone']").value;

    const data = {
        memberId: memberId,
        memberPasswords: memberPasswords,
        memberEmail: memberEmail,
        memberPhone: memberPhone
    }

    fetch("../memberApi/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => response.json())
        .then((data) => {
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
        })
        .catch((error) => console.log(error));
};

document.getElementById('confirmEmail').onclick = function () {
    const memberEmail = document.querySelector("input[name='memberEmail']").value;
    const authCode = document.querySelector("input[name='authCode']").value;

    const data = {
        memberEmail: memberEmail,
        authCode: authCode
    }

    fetch("../memberApi/checkEmail", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data === 1) {
                alert("인증이 완료되었습니다.");
            } else alert("다시 입력해주십시오");
        })
        .catch((error) => console.log(error));
};

function CheckEmail(str) {
    var reg_email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
    if (!reg_email.test(str)) {
        return false;
    } else {
        return true;
    }
}