
const imageFiles = [];
const formData = new FormData();
let mainImage = new Blob();
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
                } else if(res.data === 1){
                    if(!$("#id").val()){
                        $("#productNameCheck").text("이미 있는 상품 이름입니다.");
                        $("#name").attr('class', 'wrong__pw__input');
                        nameCheck = false;
                    } else{
                        $("#productNameCheck").text("");
                        $("#name").attr('class', 'NoClass');
                        nameCheck = true;
                    }
                } else {
                    $("#productNameCheck").text("이미 있는 상품 이름입니다.");
                    $("#name").attr('class', 'wrong__pw__input');
                    nameCheck = false;
                }
            });
        }
    });

    $(document).on('blur', '#price', (e) => {
        if(!$("#price").val()){
            $("#priceCheck").text("상품 가격을 입력해주세요.");
            $("#price").attr('class', 'wrong__input');
            priceCheck = false;
        } else {
            $("#priceCheck").text("");
            $("#price").attr('class', 'NoClass');
            priceCheck = true;
            $('#totalPrice').val(e.target.value);
        }
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

    $(document).on('blur', '#stockQuantity', (e) => {
        if(!e.target.value){
            $("#stockQuantityCheck").text("상품 수량을 입력해주세요.");
            $("#stockQuantity").attr('class', 'wrong__input');
            stockCheck = false;
        } else {
            $("#stockQuantityCheck").text("");
            $("#stockQuantity").attr('class', 'NoClass');
            stockCheck = true;
        }
    });


    $("#productPicture").on("change", (e) => {

        let file = e.target.files[0];
        mainImage = e.target.files[0];

        console.log(file);
        console.log(mainImage);

        if(!valideImageType(mainImage)) {
            console.warn("invalide image file type");
            return;
        }

        // imageFiles.push(file);

        $("#thumbnail").attr("src", window.URL.createObjectURL(file)).width(108).height(108);
        imageCheck = true;

        // let reader = new FileReader();
        // reader.onload = (e) => {
        //     $("#thumbnail").attr("src", e.target.result);
        // }
        //
        // reader.readAsDataURL(file);
    });

    $(document).on('blur', '#content', (e) => {
        if(!e.target.value){
            $("#contentCheck").text("상품 상세내용을 입력해주세요.");
            $("#content").attr('class', 'wrong__input');
            contentCheck = false;
        } else {
            $("#contentCheck").text("");
            $("#content").attr('class', 'NoClass');
            contentCheck = true;
        }
    });

    $(document).on('click', '#modify', (e)=> {

        e.preventDefault();

        //입력값 유효성 검사 - 이름, 할인, 이미지

        if($('#discountRate').val() == ''){
            $('#discountRate').val(0);
        }
        if(!nameCheck){
            $('#name').focus();
            return false;
        }
        if(!imageCheck){
            $('#productPicture').focus();
            return false;
        }
        if(!priceCheck){
            $('#price').focus();
            return false;
        }
        if(!stockCheck){
            $('#stockQuantity').focus();
            return false;
        }
        if(!contentCheck){
            $('#content').focus();
            return false;
        }



        for (const file of imageFiles){
            formData.append("file", file);
        }
        formData.append("mainImage", mainImage);
        formData.append("id", $("#id").val());
        formData.append("name", $("#name").val());
        formData.append("categoryId", $("#categoryId").val());
        formData.append("price", $("#price").val());
        formData.append("discountRate", $("#discountRate").val());
        formData.append("stockQuantity", $("#stockQuantity").val());
        formData.append("content", tinymce.get("content").getContent());


        axios({
            method: 'post',
            url: '/admin/editProduct',
            data: formData,
            headers: {'Content-Type': 'multipart/form-data'}
        }).then(res => {
            if(!res.data){
                $(".txt04").show();
            } else{
                alert("상품 수정 실패");
            }
        }).catch(err => {
            console.log("업로드 에러 : ", err);
        });

    });


});



tinymce.init({
    selector : '#content',
    plugins : 'image',
    // menubar : false,
    width : 1000,
    height : 500,
    toolbar : "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | image",
    image_title : true,
    file_picker_types: 'image',
    // images_reuse_filename: true,
    // automatic_uploads: true,
    images_upload_handler: function (blobInfo, success){

        const file = new File([blobInfo.blob()], blobInfo.filename());
        const fileName = blobInfo.filename();
        if(!fileName.includes("blobid")){
            imageFiles.push(file);
        }

        success(URL.createObjectURL(file));
        console.log(file);
    }
});

function valideImageType(image) {
    const result = ([ 'image/jpeg',
        'image/png',
        'image/jpg' ].indexOf(image.type) > -1);
    return result;
}