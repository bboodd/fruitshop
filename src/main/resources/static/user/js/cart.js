$(function () {

    const userId = parseInt($('#userId').val());


    $(document).on('click', '#plus', (e) => {
        let amount = $(e.target).closest('td').prev().find('.individual_productCartAmount_input');
        let amountVal = parseInt(amount.val());
        let price = parseInt($(e.target).closest('td').prev().find('.individual_productPrice_input').val());
        let discount = parseInt($(e.target).closest('td').prev().find('.individual_productDiscountRate_input').val());
        let productId = parseInt($(e.target).closest('td').prev().find('.individual_productId_input').val());
        let max = parseInt($(e.target).closest('td').prev().find('.individual_productQuantity_input').val());

        if(amountVal<max){

            axios({
                method: 'post',
                url: '/updateCart',
                data: {
                    userId: userId,
                    productId: productId,
                    amount: amountVal + 1
                },
                dataType: 'JSON',
                headers: { 'Content-Type': 'application/json' }
            }).then(res => {

                $(e.target).next().html(amountVal+1);
                amount.val(amountVal + 1);
                let total = price * (amountVal+1);
                $('#productTotal').html(total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

                setTotalInfo($('.cart_info_td'));
            }).catch(error => {
                console.error(error);
            });
        }
    })

    $(document).on('click', '#minus', (e) => {
        let amount = $(e.target).closest('td').prev().find('.individual_productCartAmount_input');
        let amountVal = parseInt(amount.val());
        let price = parseInt($(e.target).closest('td').prev().find('.individual_productPrice_input').val());
        let discount = parseInt($(e.target).closest('td').prev().find('.individual_productDiscountRate_input').val());
        let productId = parseInt($(e.target).closest('td').prev().find('.individual_productId_input').val());

        if(amountVal>1){
            axios({
                method: 'post',
                url: '/updateCart',
                data: {
                    userId: userId,
                    productId: productId,
                    amount: amountVal - 1
                },
                dataType: 'JSON',
                headers: { 'Content-Type': 'application/json' }
            }).then(res => {

                $(e.target).prev().html(amountVal-1);
                amount.val(amountVal - 1);
                let total = price * (amountVal-1);
                $('#productTotal').html(total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

                setTotalInfo($('.cart_info_td'));
            }).catch(error => {
                console.error(error);
            });
        }
    })

    $(document).on('click', '#delete', (e) => {
        const productId = parseInt(e.target.dataset.value);

        axios({
            method: 'post',
            url: '/deleteCart',
            data: {
                userId: userId,
                productId: productId
            },
            dataType: 'JSON',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => {
            $(e.target).closest('tr').html("");
            $('#count').text(parseInt($('#count').text()) - 1);

            setTotalInfo($('.cart_info_td'));
        }).catch(error => {
            console.error(error);
        });
    })

//     체크박스 구현

    $(document).on('click', '#checkAll', () => {
        if($("#checkAll").is(":checked")) {
            $("input[name=check]").prop("checked", true);
        }
        else {
            $("input[name=check]").prop("checked", false);
        }

        setTotalInfo($('.cart_info_td'));
    })

    $(document).on('change', '.cart_checkbox', (e) => {
        let total = $("input[name=check]").length;
        let checked = $("input[name=check]:checked").length;

        if(total != checked) {
            $("#checkAll").prop("checked", false);
        }
        else {
            $("#checkAll").prop("checked", true);
        }

        setTotalInfo($('.cart_info_td'));
    })

    $('#buy').on('click', () => {
        let form_contents = '';
        let orderNumber = 0;

        $('.cart_info_td').each((index, element) => {

            if($(element).find(".cart_checkbox").is(":checked") === true) {

                let productId = $(element).find(".individual_productId_input").val();
                let amount = $(element).find(".individual_productCartAmount_input").val();

                let productId_input = "<input name='orders[" + orderNumber + "].productId' type='hidden' value='" + productId + "'>";
                form_contents += productId_input;

                let amount_input = "<input name='orders[" + orderNumber + "].amount' type='hidden' value='" + amount + "'>";
                form_contents += amount_input;

                orderNumber++;
            }
        });

        $(".order_form").html(form_contents);
        $(".order_form").submit();
    })
})

function setTotalInfo(){
    let totalPrice = 0;
    let totalDiscount = 0;
    let deliveryPrice = 0;
    let finalTotalPrice = 0;

    $('.cart_info_td').each(function (index, element){
        if($(element).find('.cart_checkbox').is(":checked") === true){
            let amount = parseInt($(element).find('.individual_productCartAmount_input').val());
            let price = parseInt($(element).find('.individual_productPrice_input').val());
            let discountRate = parseInt($(element).find('.individual_productDiscountRate_input').val());

            totalPrice += price * (1 - (discountRate / 100)) * amount;
            totalDiscount += price * (discountRate / 100) * amount;
        }
    });

    if(totalPrice - totalDiscount >= 50000){
        deliveryPrice = 0;
    } else if(totalPrice - totalDiscount == 0){
        deliveryPrice = 1;
    } else{
        deliveryPrice = 3000;
    }

    if(deliveryPrice === 3000){
        finalTotalPrice = totalPrice - totalDiscount + deliveryPrice;
    } else{
        finalTotalPrice = totalPrice - totalDiscount;
    }

    $('#totalPrice').text(totalPrice.toLocaleString() + "원");

    $('#totalDiscount').text("-" + totalDiscount.toLocaleString() + "원");

    if(deliveryPrice === 0 ){
        $('.delivery').text("무료");
    }else if(deliveryPrice === 1){
        $('.delivery').text("0원");
    } else{
        $('.delivery').text(deliveryPrice.toLocaleString() + "원");
    }

    $('#total').text(finalTotalPrice.toLocaleString() + "원");

}