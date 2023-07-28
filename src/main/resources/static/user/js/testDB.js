$(document).on('click', '#DBbtn', () => {
    // Axios 사용해 Post 요청
    axios({
        method: "post",
        url: "/dbAlert", // "/alert" URL 로 요청
        data: {
            "result": "title 테스트", // 전송할 데이터의 제목 필드
            "msg": "msg 테스트" // 전송할 데이터의 메시지 필드
        },
        dataType: "JSON", // 응답 데이터 타입
        headers: {'Content-Type': 'application/json'} // 요청 헤더에 JSON 형식으로 데이터 전송
    }).then(res => {
        // 서버로부터의 응답 데이터는 res 변수에 저장

        // 응답 데이터(res.data)에 포함된 "alert.html"을 페이지에 추가
        $("body").append(res.data);
    });
});