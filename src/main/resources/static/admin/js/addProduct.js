$(function(){

    let nameCheck = false, imageCheck = false, priceCheck = false, stockCheck = false, contentCheck = false;

    $(document).on('blur', '#name', () => {

        let name = document.getElementById("name").value;

        if(name === '') {
            //값이 비어있으면 발생
            nameCheck = false;
            // 입력했다가 다시 잘못입력할 수 있으므로 모든 조건식에 넣어야함
        }
        else {

            // name 중복 확인(비동기 처리)

            axios({
                method: "post",
                url: "/admin/productNameCheck",
                data: {
                    "name": name
                },
                dataType: "JSON", // 응답 데이터 타입
                headers: {'Content-Type': 'application/json'} // 요청 헤더에 JSON 형식으로 데이터 전송
            }).then(res => {
                // 서버로부터의 응답 데이터는 res 변수에 저장
                if (!res.data) {
                    $("#productNameCheck").text("");
                    $("#name").attr('class', 'NoClass');
                    nameCheck = true;
                } else {
                    $("#productNameCheck").text("이미 있는 상품 이름입니다.");
                    $("#name").attr('class', 'wrong__pw__input');
                    nameCheck = false;
                }
            });
        }
    });

    $(document).on('blur', '#price', (e) => {
        $('#totalPrice').val(e.target.value);
    })

    $(document).on('blur', '#discountRate', (e) => {
        if(e.target.value > 100){
            $('#discountRate').val(100);
        }
        if(e.target.value < 0){
            $('#discountRate').val(0);
        }
        price = $('#price').val();
        discount = e.target.value;
        $('#totalPrice').val(price*(1-(discount/100)));
    });


    $("#productPicture").on("change", (e) => {

        let file = e.target.files[0];

        if(!valideImageType(file)) {
            console.warn("invalide image file type");
            return;
        }

        $("#thumbnail").attr("src", window.URL.createObjectURL(file)).width(108).height(108);
        imageCheck = true;

        // let reader = new FileReader();
        // reader.onload = (e) => {
        //     $("#thumbnail").attr("src", e.target.result);
        // }
        //
        // reader.readAsDataURL(file);
    });

    $(document).on('click', '#submit', ()=> {

        //입력값 유효성 검사 - 이름, 할인, 이미지

        if(!$('#discountRate').val()){
            $('#discountRate').focus();
            return false;
        }
        if(!nameCheck){
            $('#name').focus();
            return false;
        }
        if(!imageCheck){
            $('#productPicture').focus();
            return false;
        }
        //가격 수량 내용
        if(!$('#price').val()){
            $('#price').focus();
            return false;
        }
        if(!$('#stockQuantity').val()){
            $('#stockQuantity').focus();
            return false;
        }
        if(!$('#content').val()){
            $('#content').focus();
            return false;
        }
    });
});

function valideImageType(image) {
    const result = ([ 'image/jpeg',
        'image/png',
        'image/jpg' ].indexOf(image.type) > -1);
    return result;
}