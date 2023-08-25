$(function() {

    let selectedCategory = '';
    let searchKeyword = '';
    let pageNum = 1;
    let pageSize = 9;

    $(document).on('click', '.category-btn', (e) => {
        $(e.target).css({
            backgroundColor : "#333",
            color: "#fff"
        });
        $('.category-btn').not($(e.target)).css({
            backgroundColor : 'buttonface',
            color: "#000"
        });

        if(selectedCategory === e.target.value){
            $(e.target).css({
                backgroundColor : 'buttonface',
                color: "#000"
            });
            selectedCategory = '';
            fetchData();
        } else{
            selectedCategory = e.target.value;
            fetchData();
        }
    });

    $(document).on('click', '#searchBtn', () => {
        searchKeyword = $('#searchKeyword').val();
        console.log(searchKeyword);
        fetchData();
    });

    $(document).on('click', '#prev', (e) => {
        e.preventDefault();

        if(pageNum > 1){
            pageNum-=1;
            fetchData();
        }
    });

    $(document).on('click', '#next', (e) => {
        e.preventDefault();

        if(pageNum < $("#next").data('value')){
            pageNum+=1;
            fetchData();
        }
    });

    $(document).on('click', '#pageNum', (e) => {
        pageNum = parseInt(e.target.dataset.value);
        console.log(pageNum);
        fetchData();
    });

    const userId = parseInt($("#userId").val());

    $(document).on('click', '#like_x', (e) => {
        e.preventDefault();
        e.stopPropagation();
        console.log(e.target.dataset.value);

       if($("#userId").val()) {

           let productId = parseInt(e.target.dataset.value);

           axios({
               method: 'post',
               url: '/addLike',
               data: {
                   userId: userId,
                   productId: productId,
               },
               dataType: 'JSON',
               headers: { 'Content-Type': 'application/json' }
           }).then(res => {
               $(e.target).attr('class','material-icons red__heart')
               $(e.target).attr('id','like_o')
           }).catch(error => {
               console.error(error);
           });
       }
    });

    $(document).on('click', '#like_o', (e) => {
        e.preventDefault();
        e.stopPropagation();
        console.log(e.target.dataset.value);

        if($("#userId").val()) {

            let productId = parseInt(e.target.dataset.value);

            axios({
                method: 'post',
                url: '/deleteLike',
                data: {
                    userId: userId,
                    productId: productId,
                },
                dataType: 'JSON',
                headers: { 'Content-Type': 'application/json' }
            }).then(res => {
                $(e.target).attr('class','material-symbols-outlined')
                $(e.target).attr('id','like_x')
            }).catch(error => {
                console.error(error);
            });
        }
    });

    function fetchData() {
        axios({
            method: 'post',
            url: '/product',
            data: {
                selectedCategory: selectedCategory,
                searchKeyword: searchKeyword,
                pageSize: pageSize,
                pageNum: pageNum,
                userId: userId
            },
            dataType: 'JSON',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => {
            // 응답 데이터를 처리하는 부분입니다
            console.log(res.data);

            $("#product_list").empty(); // 기존 데이터를 모두 제거

            for (let i = 0; i < res.data.data.list.length; i++) {
                let html = `
                <li>
                    <a href="/user/product/${res.data.data.list[i].id}/detail">
                        <img src="${res.data.data.list[i].url}">
                        
                        <div class="icons">
                            <span class="${res.data.data.list[i].like_id != 0 ? 'material-icons red__heart' : 'material-symbols-outlined'}"
                                  data-value="${res.data.data.list[i].id}" id="${res.data.data.list[i].like_id != 0 ? "like_o" : "like_x"}"
                                  onclick="location.href='javascript:void(0)'">favorite</span>
                            <span class="material-symbols-outlined" data-value="${res.data.data.list[i].id}" id="cart"
                                  onclick="location.href='javascript:void(0)'">shopping_cart</span>
                        </div>
                        
                        <div class="txt">
                            <div class="title">
                                <span>${res.data.data.list[i].name}</span>
                            </div>
                            
                            <div class="price">
                                    ${res.data.data.list[i].price}원
                            </div>
                        </div>
                    </a>
                </li>
                `;
                $("#product_list").append(html);
            }
            $("#numbers").empty();

            let html2=``;

            for(let i=0;i<res.data.data.navigatepageNums.length;i++){
                let html2 =`
                
            <a id="pageNum" data-value="${res.data.data.navigatepageNums[i]}"
               class="${res.data.data.pageNum == res.data.data.navigatepageNums[i]? 'active' : 'NoClass'}"
               style="${res.data.data.pageNum == res.data.data.navigatepageNums[i]? 'font-weight:bold;' : 'font-weight:normal;'}" 
               href='javascript:void(0)'
               >${res.data.data.navigatepageNums[i]}</a>
            `;
                $("#numbers").append(html2);
            }


            $("#next").data('value',res.data.data.pages);
        }).catch(error => {
            console.error(error);
        });
    }
})


new Swiper('.promotion .swiper', {
    slidesPerView: 1, // 한 번에 보여 줄 슬라이드 개수
    spaceBetween: 10, // 슬라이드 사이 여백 (10px)
    centeredSlides: true, // 1번 슬라이드가 가운데 보이기
    loop: true,
    autoplay: {
        delay: 5000 // 5초
    },
    pagination: {
        el: '.promotion .swiper-pagination', // 페이지 번호 요소 선택자
        clickable: true // 사용자의 페이지 번호 요소 제어 기능 여부
    },
    navigation: {
        prevEl: '.promotion .swiper-button-prev',
        nextEl: '.promotion .swiper-button-next'
    }
});




new Swiper("#popular .mySwiper", {
    slidesPerView: 3,
    spaceBetween: 0,
    loop: true,
    navigation: {
        prevEl: '#popular .swiper-button-prev',
        nextEl: '#popular .swiper-button-next'
    },
});

new Swiper("#new .mySwiper", {
    slidesPerView: 3,
    spaceBetween: 0,
    loop: true,
    navigation: {
        prevEl: '#new .swiper-button-prev',
        nextEl: '#new .swiper-button-next'
    },
});

new Swiper("#recomm .mySwiper", {
    slidesPerView: 3,
    spaceBetween: 0,
    loop: true,
    navigation: {
        prevEl: '#recomm .swiper-button-prev',
        nextEl: '#recomm .swiper-button-next'
    },
});