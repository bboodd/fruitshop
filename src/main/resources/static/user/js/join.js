$(function(){

    // 회원 가입 시 사용자 입력값들 검증


    //각 입력값들의 유효성 검증을 위한 정규표현식을 변수로 선언.
    //영문, 숫자, 특수문자 (8자-20자 이내)로 입력하세요.
    const getPwCheck = RegExp(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/);

    //글자수만 2글자 이상
    const getNicknameCheck = RegExp(/^[\w\Wㄱ-ㅎㅏ-ㅣ가-힣]{2,}$/);


    // 입력값 중 하나라도 만족하지 못한다면 회원 가입 처리를 막기 위한 논리형 변수 선언
    let chk1 = false, chk2 = false, chk3 = false, chk4 = false, chk5 = false;

    //1.아이디 중복 체크

    $(document).on('blur', '#email', () => {

        let email = document.getElementById("email").value;

        if(email === '') {
            //값이 비어있으면 발생

            $("#emailCheck").text("이메일은 필수 정보 입니다.");
            $("#email").attr('class', 'wrong__pw__input');

            chk1 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        }
        else {

            // ID 중복 확인(비동기 처리)

            axios({
                method: "post",
                url: "/user/emailCheck",
                data: {
                    "email": email
                },
                dataType: "JSON", // 응답 데이터 타입
                headers: {'Content-Type': 'application/json'} // 요청 헤더에 JSON 형식으로 데이터 전송
            }).then(res => {
                // 서버로부터의 응답 데이터는 res 변수에 저장
                if (!res.data) {
                    $("#emailCheck").text("");
                    $("#email").attr('class', 'NoClass');
                    chk1 = true;
                } else {
                    $("#emailCheck").text("이미 가입된 계정입니다.");
                    $("#email").attr('class', 'wrong__pw__input');
                    chk1 = false;
                }
            });
        }
    });

    //2.닉네임 중복 체크

    $(document).on('blur', '#nickname', () => {

        let nickname = document.getElementById("nickname").value;

        if(nickname === '') {
            //값이 비어있으면 발생

            $("#nicknameCheck").text("닉네임은 필수 정보 입니다.");
            $("#nickname").attr('class', 'wrong__pw__input');

            chk2 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        } // 닉네임 유효성 검사
        else if(!getNicknameCheck.test(nickname)){

            $("#nicknameCheck").text("닉네임은 두글자 이상입니다.");
            $("#nickname").attr('class', 'wrong__pw__input');

            chk2 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        }
        else {
            axios({
                method: "post",
                url: "/user/nicknameCheck",
                data: {
                    "nickname": nickname
                },
                dataType: "JSON", // 응답 데이터 타입
                headers: {'Content-Type': 'application/json'} // 요청 헤더에 JSON 형식으로 데이터 전송
            }).then(res => {
                // 서버로부터의 응답 데이터는 res 변수에 저장
                if (!res.data) {
                    $("#nicknameCheck").text("");
                    $("#nickname").attr('class', 'NoClass');
                    chk2 = true;
                } else {
                    $("#nicknameCheck").text("해당 닉네임은 이미 사용 중입니다.");
                    $("#nickname").attr('class', 'wrong__pw__input');
                    chk2 = false;
                }
            });
        }
    });

    // 3. PW 입력값 검증
    $(document).on('blur', '#password', ()=> {

        let password = document.getElementById("password").value;

        if(password === '') {
            //값이 비어있으면 발생

            $("#pwdCheck").text("비밀번호를 입력해 주세요.");
            $("#password").attr('class', 'wrong__pw__input');

            chk3 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        } // 비밀번호 유효성 검사
        else if (!getPwCheck.test(password)){

            $("#pwdCheck").text("비밀번호는 특수문자 포함 8자 이상입니다.");
            $("#password").attr('class', 'wrong__pw__input');
            chk3 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        }// 통과
        else{
            $("#pwdCheck").text("");
            $("#password").attr('class', 'NoClass');
            chk3 = true;
        }
    });
    // 4. 비밀번호 확인 검증
    $(document).on('blur', '#password2', ()=> {

        let password2 = document.getElementById("password2").value;

        // 비밀번호 확인 공백 검증
        if(password2 === '') {
            //값이 비어있으면 발생

            $("#pwdCheck2").text("비밀번호 확인은 필수 입니다.");
            $("#password2").attr('class', 'wrong__pw__input');

            chk4 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        } // 비밀번호 확인 유효성 검사
        else if (password2 !== $('#password').val()){

            $("#pwdCheck2").text("입력한 비밀번호가 일치하지 않습니다");
            $("#password2").attr('class', 'wrong__pw__input');
            chk4 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        }// 통과
        else{
            $("#pwdCheck2").text("");
            $("#password2").attr('class', 'NoClass');
            chk4 = true;
        }
    });

    //전체 약관 동의
    $(document).on('click', '#allChk', () => {
        $("input[name=termStatus]").prop("checked", $('#allChk').prop("checked"));
    });

    $(document).on('click', 'input[name=termStatus]', () => {
        $("#allChk").prop("checked", $("input[name=termStatus]").not("#allChk").length === $("input[name=termStatus]:checked").not("#allChk").length);
    });


    //회원가입 버튼 눌렀을 때 확인
    $(document).on('click', '#submit', ()=> {

        //필수 약관 체크
        let requiredCheckboxes = $('.required_term');
        let uncheckedRequiredTerms = requiredCheckboxes.filter((index, element) => {
            return !$(element).prop('checked');
        });

        if (uncheckedRequiredTerms.length > 0) {
            alert('필수 약관에 동의해주세요.');
            chk5=false;
            return false;
        } else{
            chk5=true;
        }

        if(!chk1){
            $('#email').focus();
            return false;
        }
        if(!chk2){
            $('#nickname').focus();
            return false;
        }
        if(!chk3){
            $('#password').focus();
            return false;
        }
        if(!chk4){
            $('#password2').focus();
            return false;
        }
    });

});