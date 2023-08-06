$(function(){

    $(document).on('click', '#stop_one',(e) => {

        const selectedStopId = e.target.value;

        axios({
            method: "post",
            url: "/admin/productStopAndDelete",
            data: {
                "selectedStopId": selectedStopId
            },
            dataType: "JSON", // 응답 데이터 타입
            headers: {'Content-Type': 'application/json'} // 요청 헤더에 JSON 형식으로 데이터 전송
        }).then(res => {
            // 서버로부터의 응답 데이터는 res 변수에 저장
            if (res.data) {
                location.reload();
            }
        }).catch(err => {
            console.log("에러 : ", err);
        });
    });

    $("#stop_sale").on('click', () => {

        const selectedIds = [];

        $("input[type='checkbox']:checked").each(function() {
                selectedIds.push($(this).val());
        });

        axios({
            method: 'post',
            url: '/admin/productStopAndDelete',
            data: {
                selectedStopIds: selectedIds
            },
            dataType: 'JSON', // 응답 데이터 타입
            headers: { 'Content-Type': 'application/json' } // 요청 헤더에 JSON 형식으로 데이터 전송
        }).then(res => {
            console.log(res.data);
            if (res.data) {
                location.reload();
            }
        }).catch(err => {
            console.log("에러 : ", err);
        });
    });

    $("#product_delete").on('click', () => {

        const selectedIds = [];

        $("input[type='checkbox']:checked").each(function() {
                selectedIds.push($(this).val());
        });

        axios({
            method: 'post',
            url: '/admin/productStopAndDelete',
            data: {
                selectedDeleteIds: selectedIds
            },
            dataType: 'JSON', // 응답 데이터 타입
            headers: { 'Content-Type': 'application/json' } // 요청 헤더에 JSON 형식으로 데이터 전송
        }).then(res => {
            if(res.data) {
                location.reload();
            }
        }).catch(err => {
            console.log("에러 : ", err);
        });
    });
});