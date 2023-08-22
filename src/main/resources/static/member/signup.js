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
            console.log(data);
            if (data.hasOwnProperty('valid_memberId')) {
                $('#valid_memberId').text(data.valid_memberId);
                $('#valid_memberId').css('color', 'red');
            } else $('#valid_memberId').text('');

            if (data.hasOwnProperty('valid_memberPasswords')) {
                $('#valid_memberPasswords').text(data.valid_memberPasswords);
                $('#valid_memberPasswords').css('color', 'red');
            } else $('#valid_memberPasswords').text('');

            if (data.hasOwnProperty('valid_memberPhone')) {
                $('#valid_memberPhone').text(data.valid_memberPhone);
                $('#valid_memberPhone').css('color', 'red');
            } else $('#valid_memberPhone').text('');

            if (data.hasOwnProperty('valid_memberEmail')) {
                $('#valid_memberEmail').text(data.valid_memberEmail);
                $('#valid_memberEmail').css('color', 'red');
            } else $('#valid_memberEmail').text('');

            if (data.hasOwnProperty('dupl')) {
                alert(data.dupl);
            }
            if (data.hasOwnProperty('signup')) {
                alert(data.signup);
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
const autoHyphen = (target) => {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}