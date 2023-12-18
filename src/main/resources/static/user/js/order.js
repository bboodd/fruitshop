$(function () {

    $(document).on('click', '#add_delivery_btn', () => {
        $('.delivery__add').show();
    })

    $('.delivery__add__cancel , .delivery__add__confirm').on('click', () => {

        $('.delivery__add').hide();
    })

    $(document).on('change', '#where', (e) => {
        console.log('where = ' + $(e.target).val());
        const deliveryId = parseInt($(e.target).val());

        axios({
            method: 'post',
            url: '/selectDelivery',
            data: {
                deliveryId : deliveryId
            },
            dataType: 'JSON',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => {
            console.log(res.data);
            $('#delivery_name').val(res.data.delivery_name);
            $('#delivery_user_name').val(res.data.delivery_user_name);
            $('#phone').val(res.data.phone_number);
            $('#address_zipcode').val(res.data.zipcode);
            $('#address_detail').val(res.data.address + " " + res.data.address_detail);
            $('#individual_address').val(res.data.address);
            $('#individual_address_detail').val(res.data.address_detail);

        }).catch(error => {
            console.error(error);
        });
    })

    $(document).on('click', '.delivery__add__confirm', (e) => {
        const userId = parseInt($('#userId').val());
        const deliveryNameInput = $('#delivery_name_input').val();
        const deliveryUserNameInput = $('#delivery_user_name_input').val();
        const phoneInput = $('#phone_input').val();
        const addressInput = $('#address_input').val();
        const addressInput2 = $('#address_input_2').val();
        const addressInput3 = $('#address_input_3').val();

        if(deliveryNameInput === '' || deliveryUserNameInput === '' || phoneInput === '' || addressInput === '' || addressInput2 === '' || addressInput3 === '') {
            return false;
        }

        axios({
            method: 'post',
            url: '/user/addDelivery',
            data: {
                userId : userId,
                deliveryName : deliveryNameInput,
                deliveryUserName : deliveryUserNameInput,
                phone : phoneInput,
                zipcode : addressInput,
                address : addressInput2,
                addressDetail : addressInput3
            },
            dataType: 'JSON',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => {
            $('#where').append("<option value=" +res.data+ ">" + deliveryNameInput + "</option>");

        }).catch(error => {
            console.error(error);
        });
    })

    $(document).on('click', '.order_btn', () => {

        $("input[name='deliveryUserName']").val($('#delivery_user_name').val());
        $("input[name='zipcode']").val($('#address_zipcode').val());
        $("input[name='phoneNumber']").val($('#phone').val());
        $("input[name='address']").val($('#individual_address').val());
        $("input[name='addressDetail']").val($('#individual_address_detail').val());
        $("input[name='request']").val($('#ask').val());
        $("input[name='payment']").val('미결제');

        //상품정보
        let form_contents = '';
        $('.price').each(function (index, element) {
            let productId = $(element).find('.individual_productId_input').val();
            let productAmount = $(element).find('.individual_productAmount_input').val();
            let productId_input = "<input name='orders[" + index + "].productId' type='hidden' value='" + productId + "'>";
            form_contents += productId_input;
            let productAmount_input = "<input name='orders[" + index + "].amount' type='hidden' value='" + productAmount + "'>";
            form_contents += productAmount_input;
        });
        $('.order_form').append(form_contents);

        $('.order_form').submit();
    })

})

/* 다음 주소 연동 */
function execution_daum_address(){
    console.log("동작");
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 추가해야할 코드
                // 주소변수 문자열과 참고항목 문자열 합치기
                addr += extraAddr;

            } else {
                addr += ' ';
            }

            // 제거해야할 코드
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            $("#address_input").val(data.zonecode);
            $("#address_input_2").val(addr);
            // 커서를 상세주소 필드로 이동한다.
            $("#address_input_3").attr("readonly", false);
            $("#address_input_3").focus();

        }
    }).open();



}