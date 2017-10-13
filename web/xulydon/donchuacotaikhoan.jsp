<%-- 
    Document   : donchuacotaikhoan
    Created on : Jul 4, 2016, 8:10:28 AM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đơn chưa có tài khoản</title>
        <style>
           #tb_loaddcctk td{
                background: #ffffff;
            }
        </style>
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
                <li class="sub-menu"><a  href="xulydon.jsp?page=dondatra">Xem đơn đã trả</a></li>
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
                <li class="sub-menu"><a class="active" href="xulydon.jsp?page=donchuataikhoan">Đơn chưa có số tài khoản</a></li>
                <%
                    }if(session.getAttribute("1").equals("1") && session.getAttribute("2").equals("1")){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=loadonline">Đơn online</a></li>
                <%
                    }
                    %>
        </ul> <br>
        <div id="kh_table"></div> Lọc dữ liệu
        <div class="row">
            <table class="table">
                <tbody>
                  <tr>
                      <td><input type="text" id="dcctk_ngaynhap" class="form-control" placeholder="Ngày nhập" style="width: 100px;! important"></td>
                    <td><input type="text" id="dcctk_maonline" class="form-control repalacedot" placeholder="Mã online" style="width: 120px;! important"></td>
                    <td>
                        <select id="dcctk_loaidon" class="form-control">
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
                    </td>
                    <td>
                        <select id="ddtcb_loainhan" class="form-control">
                            <option value="0">Tất Cả</option>
                            <option value="1">CE</option>
                            <option value="2">CF</option>
                            <option value="3">CT</option>
                             <option value="4">CB</option>
                        </select>
                    </td>
                    <td><input type="text" id="dcctk_manhan" class="form-control repalacedot" placeholder="Mã nhận" style="width: 120px;! important"></td>
                    <td>
                            <select id="ddtcb_loaidangky" class="form-control">
                                <option value="0">Tất Cả</option>
                                <option value="1">BD</option>
                                <option value="2">CSGT</option>
                                <option value="3">TT</option>
                            </select>
                    </td>
                    <td><input type="text" class="form-control" placeholder="Bên Nhận BD" id="dcctk_bnbd"></td>
                    <td><input type="text" class="form-control" placeholder="Bên Bảo Đảm" id="dcctk_bbd"></td>
                    <td><button type="button" class="btn btn-primary" id="dcctk_search">Tìm kiếm</button></td>
                  </tr>
                </tbody>
              </table>
        </div>
        <br>
        <div id="div_loading"></div>
        <table id="tb_loaddcctk" class="table table-bordered table-striped">
             <thead style="background-color: #87CEFA">
                 <tr>
                     <th rowspan="2">Ngày nhập</th>
                     <th colspan="2">Số đơn do online cấp</th>
                     <th rowspan="2">Loại hình nhận</th>
                     <th rowspan="2">Loại đơn</th>
                     <th rowspan="2">Số lượng tài sản</th>
                     <th rowspan="2">Cung cấp thông tin</th>
                     <th rowspan="2">Bên nhận bảo đảm</th>
                     <th rowspan="2">Bên bảo đảm</th>
                     <th rowspan="2">Sửa</th>
                     <th rowspan="2">Xóa</th>
                 </tr>
                 <tr>
                     <th >Mã Online</th>
                     <th>Mã Pin</th>
                 </tr>
             </thead>
             <tbody>
                 
             </tbody>
         </table>
        <div style="text-align: right">
                <ul class="pagination" id="ul_page" ></ul>
            </div>
        <script>
            var days = 60;
            var date = new Date();
             var last = new Date(date.getTime() - (days * 24 * 60 * 60 * 1000));
             $( "#dcctk_ngaynhap" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                }).datepicker("setDate", last);
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
                       url : "donchuacotk",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "loaddoncctk",page: 1,ngaynhap: $('#dcctk_ngaynhap').val()
                                },
                        
                        success: function (data) {
                            $('#div_loading').empty();
                            $('#tb_loaddcctk tbody').html(data.data);
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $.ajax({
                                            url : "donchuacotk",
                                            type : "GET",
                                            dataType : "json",
                                            data:{action: "loaddoncctk",page: page,ngaynhap: $('#dcctk_ngaynhap').val()},
                                            success: function (data) {
                                               // $('#nganchantb tbody').empty();
                                                $('#tb_loaddcctk tbody').html(data.data);
                                            },
                                            error: function (data) {
                                                console.log('Error:', data);
                                            }
                                        }); 
                                }
                            }));
                        }
                  });
                  
                var searchList = [];
                var searchDonCCTK = function(){
                    var ngaynhap = $('#dcctk_ngaynhap').val();
                    var maonline = $('#dcctk_maonline').val();
                    var loaidon = $('#dcctk_loaidon').val();
                    var loainhan = $('#ddtcb_loainhan').val();
                    var manhan = $('#dcctk_manhan').val();
                    var loaidk  = $('#ddtcb_loaidangky').val();
                    var bnbd = $('#dcctk_bnbd').val();
                    var bbd = $('#dcctk_bbd').val();
                    searchList.splice(0,searchList.length);
                    searchList.push(ngaynhap);searchList.push(maonline);searchList.push(loaidon);
                    searchList.push(loainhan);searchList.push(manhan);searchList.push(loaidk);
                    searchList.push(bnbd);searchList.push(bbd);
                    $('#btp_loading').html("<img src='./images/loading.gif'>");
                    $.ajax({
                       url : "donchuacotk",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "searchdoncctk",page: 1,ngaynhap: ngaynhap,maonline: maonline,loaidon: loaidon,
                                        loainhan: loainhan,manhan: manhan,loaidk: loaidk,bnbd: bnbd,bbd: bbd
                                },
                        
                        success: function (data) {
                            $('#btp_loading').empty();
                            $('#tb_loaddcctk tbody').html(data.data);
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $.ajax({
                                            url : "donchuacotk",
                                            type : "GET",
                                            dataType : "json",
                                            data:{action: "searchdoncctk",page: page,ngaynhap: searchList[0],maonline: searchList[1],loaidon: searchList[2],
                                        loainhan: searchList[3],manhan: searchList[4],loaidk: searchList[5],bnbd: searchList[6],bbd: searchList[7]},
                                            success: function (data) {
                                               // $('#nganchantb tbody').empty();
                                                $('#tb_loaddcctk tbody').html(data.data);
                                            },
                                            error: function (data) {
                                                console.log('Error:', data);
                                            }
                                        }); 
                                }
                            }));
                        }
                  });
                };
                $('#dcctk_search').on('click',searchDonCCTK);
        </script>
    </body>
</html>
