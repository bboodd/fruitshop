$(function () {

    const userId = parseInt($('#userId').val());


    $(document).on('click', '#plus', (e) => {
        const amount = $(e.target).closest('span').prev();
        let productId = parseInt(e.target.dataset.value);
        let amountVal = parseInt(amount.val());
        let max = parseInt($(e.target).closest('span').next().val());
        let price = parseInt($(e.target).closest('div').next().find('input').val());
        let discount = parseInt($(e.target).closest('div').next().find('input').next().val());
        const productTotal = $(e.target).closest('td').next();

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
                productTotal.html(total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

                $('#totalSumPrice').val(parseInt($('#totalSumPrice').val()) + price);
                $('#totalPrice').html($('#totalSumPrice').val().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

                $('#totalSumDiscount').val(parseInt($('#totalSumDiscount').val()) + (price * (discount/100)));
                $('#totalDiscount').html("-" + $('#totalSumDiscount').val().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

                $('#totalSumTotal').val(parseInt($('#totalSumTotal').val()) + (price * (1-(discount/100))));

                if(parseInt($('#totalSumTotal').val()) >= 50000){
                    $('.delivery').html("무료");
                    $('#total').html($('#totalSumTotal').val().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
                }
                else{
                    $('.delivery').html("3000원");
                    let temp = parseInt($('#totalSumTotal').val()) + 3000
                    $('#total').html(temp.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
                };

            }).catch(error => {
                console.error(error);
            });
        }
    })

    $(document).on('click', '#minus', (e) => {
        const amount = $(e.target).closest('span').prev();
        let productId = parseInt(e.target.dataset.value);
        let amountVal = parseInt(amount.val());
        let max = parseInt($(e.target).closest('span').next().val());
        let price = parseInt($(e.target).closest('div').next().find('input').val());
        let discount = parseInt($(e.target).closest('div').next().find('input').next().val());
        const productTotal = $(e.target).closest('td').next();

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
                productTotal.html(total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

                $('#totalSumPrice').val(parseInt($('#totalSumPrice').val()) - price);
                $('#totalPrice').html($('#totalSumPrice').val().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

                $('#totalSumDiscount').val(parseInt($('#totalSumDiscount').val()) - (price * (discount/100)));
                $('#totalDiscount').html("-" + $('#totalSumDiscount').val().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

                $('#totalSumTotal').val(parseInt($('#totalSumTotal').val()) - (price * (1-(discount/100))));

                if(parseInt($('#totalSumTotal').val()) >= 50000){
                    $('.delivery').html("무료");
                    $('#total').html($('#totalSumTotal').val().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
                }
                else{
                    $('.delivery').html("3000원");
                    let temp = parseInt($('#totalSumTotal').val()) + 3000;
                    $('#total').html(temp.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
                };

            }).catch(error => {
                console.error(error);
            });
        }
    })

    $(document).on('click', '#delete', (e) => {
        const productId = parseInt(e.target.dataset.value);

        let amount = parseInt($(e.target).closest('div').prev().find('input').val());
        const price = parseInt($(e.target).prev().prev().val());
        const discount = parseInt($(e.target).prev().val());

        console.log(amount + " " + price + " " + discount);

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


            $('#totalSumPrice').val(parseInt($('#totalSumPrice').val()) - (price * amount));
            $('#totalPrice').html($('#totalSumPrice').val().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

            $('#totalSumDiscount').val(parseInt($('#totalSumDiscount').val()) - ((price * (discount/100)) * amount));
            $('#totalDiscount').html("-" + $('#totalSumDiscount').val().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");

            $('#totalSumTotal').val(parseInt($('#totalSumTotal').val()) - ((price * (1-(discount/100))) * amount));

            if(parseInt($('#totalSumTotal').val()) >= 50000){

                $('.delivery').html("무료");
                $('#total').html($('#totalSumTotal').val().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
            }
            else if(parseInt($('#totalSumTotal').val()) == 0){
                $('.delivery').html("0원");
                $('#total').html("0원");
            }
            else{
                $('.delivery').html("3000원");
                let temp = parseInt($('#totalSumTotal').val()) + 3000;
                $('#total').html(temp.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원");
            };
        }).catch(error => {
            console.error(error);
        });
    })

//     체크박스 구현

    $(document).on('click', '#checkAll', () => {
        if($("#checkAll").is(":checked")) $("input[name=check]").prop("checked", true);
        else {
            $("input[name=check]").prop("checked", false);

        }
    })

    $(document).on('click', 'input[name=check]', () => {
        let total = $("input[name=check]").length;
        let checked = $("input[name=check]:checked").length;

        if(total != checked) $("#checkAll").prop("checked", false);
        else $("#checkAll").prop("checked", true);
    })
})