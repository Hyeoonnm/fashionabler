$('#check').on('click', function () {
    const data = {
        memberId: $('#memberId').val(),
        memberPasswords: $('#memberPasswords').val(),
        memberEmail: $('#memberEmail').val(),
        memberPhone: $('#memberPhone').val()
    }
    console.log("JSON : " + JSON.stringify(data));
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
            if (data >= 1) {
                alert("회원가입 성공")
                window.location.href = "/"
            }
            if (data < 1) {
                alert("중복된 아이디입니다.")
            }
        },
        error: function (error) {
            alert(error);
        }
    });
});