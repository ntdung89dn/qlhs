<%-- 
    Document   : dondatra
    Created on : Jul 4, 2016, 8:09:20 AM
    Author     : Thorfinn
--%>

<%@page import="com.ttdk.bean.SearchDon"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
            }
            th, td {
                padding: 5px;
                text-align: center;
            }
            table {
                width: 100%;
            }
            td{
                background: #ffffff;
            }
        </style>
        <script>
            $(document).ready(function(){
                $( "#ddt_thoigiannhap" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                }).datepicker( "setDate", new Date());
                
                var $pagination = $('#ul_page');
                var searchList = [];
                var defaultOpts = {
                    totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'Trang cuối',
                    next: 'TIếp',
                    last: 'Sau',
                    loop: false,
                    initiateStartPageClick: false
                };
                
                 $pagination.twbsPagination(defaultOpts);
                 $('#div_loading').html("<img src='./images/loading.gif'>");
                 $.ajax({
                       url : "SearchDonServlet",
                       type : "GET",
                       data : {
                                        "s_page" : "load_pageddt",page : 1,ngayddt : $('#ddt_thoigiannhap').val()
                                },
                        dataType : "json",
                        success: function (data) {
                            $('#tb_dondatra tbody').html(data.data);
                            $('#div_loading').empty();
                            console.log(data);
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $('#div_loading').empty();
                                  //  console.log('PAGE = '+page);
                                    $.ajax({
                                        url : "SearchDonServlet",
                                        type : "GET",
                                        data : {
                                                         "s_page" : "load_pageddt","page":page
                                                 },
                                         dataType : "json",
                                         success: function (data) {
                                             
                                             $('#tb_dondatra tbody').html(data.data);
                                         }
                                   });
                                }
                            }));
                        }
                  });
                  
                
                var inputSearch = function(){
                    searchList.splice(0,searchList.length);
                    var thoigiannhap = $('#ddt_thoigiannhap').val();
                    var ddt_online =  $("#ddt_online").val();
                    var ddtcb_manhan = $("#ddtcb_manhan").val();
                    var ddt_manhan = $("#ddt_manhan").val();
                    var ddt_duocphan = $("#ddt_duocphan").val();
                    var ddt_loaidk = $('#ddtcb_loaidk').val();
                    var ddt_loaidon = $('#ddtcb_loaidon').val();
                    searchList.push(thoigiannhap);searchList.push(ddt_online);
                    searchList.push(ddtcb_manhan);searchList.push(ddt_manhan);searchList.push(ddt_duocphan);
                    searchList.push(ddt_loaidon);searchList.push(ddt_loaidk);
                   // console.log(dct_loaidk);
                   $('#div_loading').html("<img src='./images/loading.gif'>");
                    $.ajax({
                            type: "GET",
                            url:"SearchDonServlet",
                            dataType : "json",
                            data:{"page":1,"s_page":"dondatra","daytime":thoigiannhap,"dononline":ddt_online,"cb_loainhan":ddtcb_manhan,
                            "manhan":ddt_manhan,"nvnhan":ddt_duocphan,'loaidon':ddt_loaidon,
                            "loaidk":ddt_loaidk},
                            success: function (data) {
                                $('#div_loading').empty();
                                var table = data.data;
                                var currentpage = data.currentpage;
                                var totalpage = data.totalpage;
                                $('#tb_dondatra tbody').html(table);
                                $pagination.twbsPagination('destroy');
                              //  loadPageSearch(currentpage);
                                $pagination.twbsPagination($.extend({}, defaultOpts, {
                                        startPage: currentpage,
                                        totalPages: totalpage,
                                        onPageClick: function (event, page) {
                                            console.log(event);
                                            $('#div_loading').html("<img src='./images/loading.gif'>");
                                            $.ajax({
                                                    type: "GET",
                                                    url:"SearchDonServlet",
                                                    dataType : "json",
                                                    data:{"page":page,"s_page":"dondatra","daytime":searchList[0],"dononline":searchList[1],"cb_loainhan":searchList[2],
                            "manhan":searchList[3],"nvnhan":searchList[4],'loaidon':searchList[5],"loaidk":searchList[6]},
                                                    success: function (data) { 
                                                        $('#div_loading').empty();
                                                        $("#tb_dondatra tbody").html(data.data); 
                                                    }

                                                }); 
                                        }
                                    }));
                            }
                    });
                };
                $('#ddt_thoigiannhap').on('keydown',function(e){
                    if(e.keyCode ===13){
                        $('#ddt_search').click();
                    }
                });
                $('#ddt_online').on('keydown',function(e){
                    if(e.keyCode ==13){
                        $('#ddt_search').click();
                    }
                });
                $('#ddt_manhan').on('keydown',function(e){
                    if(e.keyCode ==13){
                        $('#ddt_search').click();
                    }
                });
                $('#ddt_ngayduocphan').on('keydown',function(e){
                    if(e.keyCode ==13){
                        $('#ddt_search').click();
                    }
                });
                $('#ddt_search').on('click',inputSearch);
            });
        </script>
    </head>
    <body>
        <ul id="1stmenu" class="nav nav-pills">
            <li class="active"><a href="xulydon.jsp?page=nhapdon">Xử Lý Đơn</a></li>
             <%
                if(session.getAttribute("10").equals("1") || session.getAttribute("11").equals("1") || session.getAttribute("12").equals("1") || session.getAttribute("13").equals("1")){
                %>
            <li><a href="vanthu.jsp?page=themvanthu">Văn Thư</a></li>
             <%
                 }
                if(session.getAttribute("14").equals("1") || session.getAttribute("15").equals("1") || session.getAttribute("16").equals("1") || session.getAttribute("17").equals("1")){
                %>
            <li><a href="khachhang.jsp?page=khachhang">Khách Hàng</a></li>
            <li><a href="nganchan.jsp?page=nganchan">Ngăn Chặn</a></li>
            <%
                 }
                if(session.getAttribute("18").equals("1") || session.getAttribute("19").equals("1") || session.getAttribute("20").equals("1")){
                %>
            <li><a href="thongke.jsp?page=nhapdon">Thống kê</a></li>
             <%
                 }
                if(session.getAttribute("21").equals("1")){
                %>
            <li><a href="thuphi.jsp?page=chuathuphi">Thu Phí Lệ Phí</a></li>
             <%
                 }
             //   if(session.getAttribute("21").equals("1")){
                %>
            <!--    <li class="dropdown pull-right"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Cài Đặt<span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#">Đổi Mật Khẩu</a></li>
                    <li><a href="#">Thông tin cá nhân</a></li>
                </ul>
            </li> -->
            <li  class="pull-right"><a id="btn_changepass" >Đổi mật khẩu</a></li>
            <li  class="pull-right"><a id="logout" href="logout"><span class="glyphicon glyphicon-log-in"></span> Đăng Xuất</a></li>
            <li class="pull-right"><a href="#"><span class="glyphicon glyphicon-user"></span><%= session.getAttribute("fullname").toString() %><input type="hidden" id="username" name="username" value="<%=session.getAttribute("username")%>" /></a></li>
            

        </ul>
        <ul id="sub-menu-1">
                <%
                if(session.getAttribute("1").equals("1") || session.getAttribute("2").equals("1")){
                %>
                <li class="sub-menu"><a href="xulydon.jsp?page=nhapdon">Nhập đơn</a></li>
                <%
                    }

                    if(session.getAttribute("5").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=phandon">Phân đơn</a></li>
                <%
                    }
                    if(session.getAttribute("6").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=donchotra">Xem đơn chờ trả</a></li>
                 <%
                    }
                     if(session.getAttribute("8").equals("1") ){
                    %>
                <li class="sub-menu"><a class="active" href="xulydon.jsp?page=dondatra">Xem đơn đã trả</a></li>
                <%
                    }
                     if(session.getAttribute("9").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=thongke">Thống kê hiệu suất</a></li>
                <%
                    }
                     if(session.getAttribute("22").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=danhsachno">Danh sách nợ</a></li>
                <%
                    }
                     if(session.getAttribute("1").equals("1") || session.getAttribute("2").equals("1")){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=donchuataikhoan">Đơn chưa có số tài khoản</a></li>
                <%
                    }if(session.getAttribute("1").equals("1") && session.getAttribute("2").equals("1")){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=loadonline">Đơn online</a></li>
                <%
                    }
                    %>
            </ul> 
            <fieldset>
                Lọc Dữ Liệu: 
                <input type="text" id="ddt_thoigiannhap"  placeholder="Ngày Nhập Đơn" />
                <input type="text" id="ddt_online" class='repalacedot' name="ddt_online" placeholder="Số đơn online" />
                <select id="ddtcb_manhan" >
                    <option value="0">Tất Cả</option>
                    <option value="1">CE</option>
                    <option value="2">CF</option>
                    <option value="4">CT</option>
                     <option value="3">CB</option>
                </select>
                <select name="ddtcb_loaidon" id="ddtcb_loaidk" >
                    <option value="0">Loại đăng ký</option>
                    <option value="1">BD</option>
                    <option value="2">TT</option>
                    <option value="3">CSGT</option>
                </select>
                <select name="ddtcb_loaidk" id="ddtcb_loaidon"  >
                    <option value="0">Loại Đơn</option>
                    <option value="1">LĐ</option><option value="2">TĐ</option>
                        <option value="3">Xóa</option><option value="4">VB-XL-TS</option>
                        <option value="5">CC-TT</option>
                        <option value="6">BẢN SAO</option>
                        <option value="7">TBKBTHA</option>
                        <option value="8">MIỄN PHÍ</option>
                        <option value="9">CSGT</option>
                        <option value="10">CSGT-TĐ</option>
                        <option value="11">CSGT-X</option>
                        <option value="12">CSGT-Online</option>
                        <option value="13">CSGT-Online/TĐ</option>
                </select>
                <input type="text" id="ddt_manhan" class='repalacedot'  name="ddt_manhan" placeholder="Mã loại hình nhận" />
               <!-- <input type="text" id="ddt_duocphan" name="ddt_duocphan" placeholder="Người được phân"/>
                Tất cả: &nbsp;<input type="checkbox" id="cb_all" name="cb_all"/>  -->
                <select id="ddt_duocphan">
                    
                </select>
                <button id="ddt_search" type="button" class="btn btn-default">Tìm Kiếm</button>
               
            </fieldset>
            <div id="div_loading"></div>
        <table id="tb_dondatra">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th rowspan="2">Thời Điểm Nhập</th>
                    <th colspan="2">Số Đơn Do Online Cấp</th>
                    <th rowspan="2">Loại Hình Nhận</th>
                    <th rowspan="2">Loại Đơn</th>
                    <th rowspan="2">Số Lượng Tài Sản</th>
                    <th colspan="4">Thông tin đơn được phân</th>
                </tr>
                <tr>
                    <th>Số Đơn Online</th>
                    <th>Số Pin</th>
                    <th>Thời điểm phân</th>
                    <th>Cán bộ xử lý</th>
                    <th>Thời điểm trả đơn</th>
                     <th>SLTS</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
        </table><br>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        <script>
            function loadNhanvienNhan(){
                     $.ajax({
                            type: "GET",
                            url:"TraDonServlet",
                            data:{"action":"loadnvnhan"},
                            success: function (data) {
                                console.log(data);
                                $('#ddt_duocphan').html(data);

                            }
                        });
                };
                loadNhanvienNhan();
        </script>
    </body>
</html>
