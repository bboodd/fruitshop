$(function() {

    let selectedStatus = '';
    let selectedCategory = '';
    let searchKeyword = '';
    let selectedTab = 10;

    $(document).on('change', '#howmany', () => {
        selectedTab = parseInt($('#howmany option:selected').val());
        console.log(selectedTab);
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
        console.log(searchKeyword);
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
                selectedTab: selectedTab
            },
            dataType: 'JSON',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => {
            // 응답 데이터를 처리하는 부분입니다
            console.log(res.data);


            $("#search_result").empty();
            $("#search_result").append(res.data.count);
            $("#product_list").empty(); // 기존 데이터를 모두 제거

            for (let i = 0; i < res.data.data.length; i++) {
                const html = `
        <tr>
            <td>
                <input type="checkbox" id="product_status" value="${res.data.data[i].id}">
            </td>
            <td>${res.data.data[i].id}</td>
            <td>${res.data.data[i].status}</td>
            <td>${res.data.data[i].category_name}</td>
            <td>${res.data.data[i].name}</td>
            <td>${res.data.data[i].price}</td>
            <td>${res.data.data[i].discount_rate}</td>
            <td>269</td>
            <td>135</td>
            <td>23</td>
            <td>${res.data.data[i].created_at}</td>
            <td>
                <button>수정</button>
            </td>
            <td>
                ${res.data.data[i].status == '판매중지' ? res.data.data[i].updated_at : `<button id="stop" value="${res.data.data[i].id}">중지</button>`}
            </td>
        </tr>    
    `;
                $("#product_list").append(html);
            }
        }).catch(error => {
            console.error(error);
        });
    }
});