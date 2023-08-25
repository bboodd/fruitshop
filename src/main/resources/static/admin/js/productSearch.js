$(function() {

    let selectedStatus = '';
    let selectedCategory = '';
    let searchKeyword = '';
    let pageSize = 10;
    let pageNum = 1;

    $(document).on('change', '#pageSize', () => {
        pageSize = parseInt($('#pageSize option:selected').val());
        fetchData();
    });

    $(document).on('click', '.status-btn', (e) => {
        $(e.target).css({
            backgroundColor : "#333",
            color: "#fff"
        });
        $('.status-btn').not($(e.target)).css({
            backgroundColor : 'buttonface',
            color: "#000"
        });
        if(selectedStatus === e.target.value){
            $(e.target).css({
                backgroundColor : 'buttonface',
                color: "#000"
            });
            selectedStatus = '';
            fetchData();
        } else{
            selectedStatus = e.target.value;
            fetchData();
        }
    });

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

        if(pageNum < e.target.value){
            pageNum+=1;
            fetchData();
        }
    });

    $(document).on('click', '#pageNum', (e) => {
        pageNum = parseInt(e.target.value);
        fetchData();
    });

    function fetchData() {
        axios({
            method: 'post',
            url: '/admin/product',
            data: {
                selectedStatus: selectedStatus,
                selectedCategory: selectedCategory,
                searchKeyword: searchKeyword,
                pageSize: pageSize,
                pageNum: pageNum
            },
            dataType: 'JSON',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => {
            // 응답 데이터를 처리하는 부분입니다
            console.log(res.data);


            $("#search_result").empty();
            $("#search_result").append(res.data.count);
            $("#product_list").empty(); // 기존 데이터를 모두 제거

            for (let i = 0; i < res.data.data.list.length; i++) {
                const html = `
        <tr>
            <td>
                <input type="checkbox" id="product_status" value="${res.data.data.list[i].id}">
            </td>
            <td>${res.data.data.list[i].id}</td>
            <td>${res.data.data.list[i].status}</td>
            <td>${res.data.data.list[i].category_name}</td>
            <td>${res.data.data.list[i].name}</td>
            <td>${res.data.data.list[i].price}</td>
            <td>${res.data.data.list[i].discount_rate}</td>
            <td>null</td>
            <td>null</td>
            <td>null</td>
            <td>${res.data.data.list[i].created_at}</td>
            <td>
                <button onclick="location.href='/admin/product/${res.data.data.list[i].id}/edit'" type="button">수정
                </button>
            </td>
            <td>
                ${res.data.data.list[i].status == '판매중지' ? res.data.data.list[i].updated_at : `<button id="stop_one" value="${res.data.data.list[i].id}">중지</button>`}
            </td>
        </tr>    
    `;
                $("#product_list").append(html);
            }

            $("#numbers").empty();

            for(let i=0;i<res.data.data.navigatepageNums.length;i++){
                const html2 =`


                <button id="pageNum" value="${res.data.data.navigatepageNums[i]}" 
                class="${res.data.data.pageNum == res.data.data.navigatepageNums[i]? 'active' : 'NoClass'}"
                style="${res.data.data.pageNum == res.data.data.navigatepageNums[i]? 'background-color:#333; color: #fff' : 'background-color:none; color: none'}">
                ${res.data.data.navigatepageNums[i]}</button>
            `;
                $("#numbers").append(html2);
            }

            $("#next").val(res.data.data.pages);
        }).catch(error => {
            console.error(error);
        });
    }
});