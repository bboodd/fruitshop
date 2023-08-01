$(document).on('click', '#findPw', () => {

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
        if(!res.data){
            $('.txt05').show();
            $("#findEmailFail").on('click', () => {$('.txt05').hide();})
        } else{
            $("#findEmailOk").attr('href', '/user/changePw?email=' + email);
            $('.txt04').show();
        }
    });

});
