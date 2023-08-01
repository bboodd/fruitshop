$(function() {

    //영문, 숫자, 특수문자 (8자-20자 이내)로 입력하세요.
    const getPwCheck = RegExp(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/);

    let chk1 = false, chk2 = false;

    // PW 입력값 검증
    $(document).on('blur', '#newPw', ()=> {

        let newPw = document.getElementById("newPw").value;


        if(newPw === '') {
            //값이 비어있으면 발생

            $("#pwdCheck").text("비밀번호를 입력해 주세요.");
            $("#newPw").attr('class', 'wrong__pw__input');

            chk1 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        } // 비밀번호 유효성 검사
        else if (!getPwCheck.test(newPw)){

            $("#pwdCheck").text("비밀번호는 특수문자 포함 8자 이상입니다.");
            $("#newPw").attr('class', 'wrong__pw__input');
            chk1 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        }// 통과
        else{
            $("#pwdCheck").text("");
            $("#newPw").attr('class', 'NoClass');
            chk1 = true;
        }
    });
    // 4. 비밀번호 확인 검증
    $(document).on('blur', '#newPw2', ()=> {

        let newPw2 = document.getElementById("newPw2").value;

        // 비밀번호 확인 공백 검증
        if(newPw2 === '') {
            //값이 비어있으면 발생

            $("#pwdCheck2").text("비밀번호 확인은 필수 입니다.");
            $("#newPw2").attr('class', 'wrong__pw__input');

            chk2 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        } // 비밀번호 확인 유효성 검사
        else if (newPw2 !== $('#newPw').val()){

            $("#pwdCheck2").text("입력한 비밀번호가 일치하지 않습니다");
            $("#newPw2").attr('class', 'wrong__pw__input');
            chk2 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        }// 통과
        else{
            $("#pwdCheck2").text("");
            $("#newPw2").attr('class', 'NoClass');
            chk2 = true;
        }
    });

    $(document).on('click', '#changePw', () => {

        if (chk1 !== chk2) {
            $('#wrong__pw').text('비밀번호가 일치하지 않습니다.');
            $('#passwordConfirm').focus();
            return false;
        }

        const newPassword = $('#newPw').val();
        const email = new URLSearchParams(location.search).get('email');


        axios({
            method: 'post',
            url: '/user/changePassword',
            data: {
                "email" : email,
                "newPassword" : newPassword
            },
            dataType: "JSON", // 응답 데이터 타입
            headers: {'Content-Type': 'application/json'} // 요청 헤더에 JSON 형식으로 데이터 전송
        }).then(res => {
            if (!res.data) {
                $('.txt05').show();
            } else {
                $('.txt04').show();
            }
        })
    });
});