// $(function(){

    //아이디 중복 체크

    $(document).on('blur', '#email', () => {

        let email = document.getElementById("email").value;

        axios({
            method: "post",
            url: "/user/emailCheck",
            data: {
                "email" : email
            },
            dataType: "JSON", // 응답 데이터 타입
            headers: {'Content-Type': 'application/json'} // 요청 헤더에 JSON 형식으로 데이터 전송
        }).then(res => {
            // 서버로부터의 응답 데이터는 res 변수에 저장
            $("#emailCheck").text(res.data);
        });
    })



    // 회원 가입 시 사용자 입력값들 검증

    // 정규표현식 활용
    //각 입력값들의 유효성 검증을 위한 정규표현식을 변수로 선언.
    const getPwCheck = RegExp(/^[a-zA-Z0-9]{8,20}$/);
    // [허용문자], {최소문자길이, 최대 문자길이}

    // 입력값 중 하나라도 만족하지 못한다면 회원 가입 처리를 막기 위한 논리형 변수 선언
    let chk1 = false, chk2 = false, chk3 = false, chk4 = false;

    // 2. PW 입력값 검증
    $(document).on('keyup', '#pw', ()=> {

        // 비밀번호 공백 확인
        if ($('#pw').val() === ''){
            $('#pw').css('background', 'pink', 'border', '1px', 'solid', 'red');
            $('#pw').html('<b style="font: normal 11px/1 Nanum Gothic; color:red; margin-top: 7px;">비밀번호를 입력해주세요</b>');
            chk2 = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        }
        // // 비밀번호 유효성 검사
        // else if (!getPwCheck.test($(event.target).val()) || $(event.target).val().length < 8){
        //     $(event.target).css('background', 'pink');
        //     $('#pwChk').html('<b style="font-size: 14px; color:blue">[비밀번호는 특수문자 포함 8자 이상입니다.]</b>');
        //     chk2 = false;
        //     // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        // }
        // // 통과
        // else{
        //     $(event.target).css('background', 'aqua');
        //     $('#pwChk').html('<b style="font-size: 14px; color:skyblue">[비밀번호 입력 확인 완료.]</b>');
        //     chk2 = true;
        //     // 입력값 검증 성공 표시
        // }
    });
    // PW 검증 끝
// });