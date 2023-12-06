$(function() {

    const quantity = $('#quantity').val();
    const userId = parseInt($("#userId").val());
    const productId = parseInt($("#productId").val());

    $(document).on('click', '#plus', () => {
        let amount = parseInt($('#amount').val());

        if(amount < quantity){
            $('#amount').val(amount+1);
        } else{
            return 0;
        }

        $('#total').empty();
        let total = parseInt($('#afterDiscount').val()) * (amount+1);

        $('#deliveryFee').empty();
        if(total >= 50000){
            $('#deliveryFee').append("무료");
        } else{
            $('#deliveryFee').append("3000원");
        }

        total = total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        $('#total').append(total + "원");
    });

    $(document).on('click', '#minus', () => {
        let amount = parseInt($('#amount').val());

        if(amount > 1){
            $('#amount').val(amount-1);
        } else{
            return 0;
        }

        $('#total').empty();
        let total = parseInt($('#afterDiscount').val()) * (amount-1);

        $('#deliveryFee').empty();
        if(total >= 50000){
            $('#deliveryFee').append("무료");
        } else{
            $('#deliveryFee').append("3000원");
        }

        total = total.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        $('#total').append(total + "원");
    });

    $(document).on('click', '.detailed__info', () => {
        $('.detailed__info').css({
            backgroundColor : "#252525",
            color: "#fff"
        });
        $('#sec1').show();
        $('.review').css({
            backgroundColor : "#f0f0f0",
            color: "#6c6c6c"
        });
        $('#sec2').hide();
    });

    $(document).on('click', '.review', () => {
        $('.review').css({
            backgroundColor : "#252525",
            color: "#fff"
        });
        $('#sec2').show();
        $('.detailed__info').css({
            backgroundColor : "#f0f0f0",
            color: "#6c6c6c"
        });
        $('#sec1').hide();
    });

    $(document).on('click', '#detailCart', () => {
        if($("#userId").val()){
            const amount = parseInt($('#amount').val());
            const cartAmount = parseInt($('#cartAmount').val());
            const quantity = parseInt($('#quantity').val());

            if(amount+cartAmount <= quantity){
                axios({
                    method: 'post',
                    url: '/addCart',
                    data: {
                        userId: userId,
                        productId: productId,
                        amount:  amount
                    },
                    dataType: 'JSON',
                    headers: { 'Content-Type': 'application/json' }
                }).then(res => {
                    $('#cartModal').show();

                    $("#countCart").empty();
                    $("#countCart").append(res.data);

                    $("#cartAmount").val(amount+cartAmount);
                }).catch(error => {
                    console.error(error);
                });
            } else{
                $('#maxModal').show();
            }

        } else{
            $(location).attr("href", "/user/login");
        }
    });

    $('#stay').on('click', () => {
        $('#cartModal').hide();
        $("#amount").val(1);
    });
    $('#stay2').on('click', () => {
        $('#maxModal').hide();
        $("#amount").val(1);
    });

    $('#buy').on('click', () => {
        if($("#userId").val()){

            let amount = $('#amount').val();
            $(".order_form").find("input[name='orders[0].amount']").val(amount);
            $(".order_form").submit();
        } else{
            $(location).attr("href", "/user/login");
        }
    })
})