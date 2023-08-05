$(function(){

    let chk1 = false, chk2 = false, chk3 = false, chk4 = false, chk5 = false;


    $("#productPicture").on("change", (e) => {

        let file = e.target.files[0];

        if(!valideImageType(file)) {
            console.warn("invalide image file type");
            return;
        }

        $("#thumbnail").attr("src", window.URL.createObjectURL(file)).width(108).height(108);
        chk5 = true;

        // let reader = new FileReader();
        // reader.onload = (e) => {
        //     $("#thumbnail").attr("src", e.target.result);
        // }
        //
        // reader.readAsDataURL(file);
    });

    $(document).on('click', '#submit', ()=> {
        //입력값 유효성 검사 - 이름, 카테고리, 가격, 할인, 수량

    });
});

function valideImageType(image) {
    const result = ([ 'image/jpeg',
        'image/png',
        'image/jpg' ].indexOf(image.type) > -1);
    return result;
}