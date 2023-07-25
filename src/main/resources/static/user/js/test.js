// #userIcon 요소를 클릭 했을 때의 이벤트 핸들러 설정
$(document).on('click', '#buttonTest', () => {

    // Axios를 사용하여 서버로 POST 요청을 보냄
    axios({
        method: 'post',
        url: "/alert",
        data: {
            "title": "title 테스트",
            "msg": "msg 테스트"
        },
        dataType: "JSON",
        header: {'Content-Type': 'application/json'}
    }).then(res => {
        $('body').append(res.data);
    });


    $(document).on('click', '#confirmBtn', () => {
        $('.txt04').remove();
    });
})