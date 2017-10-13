<%-- 
    Document   : tonghopcongno
    Created on : Jul 5, 2016, 5:20:20 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            .ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year {
                    color: #000000;
                    font-size: 14px;
                    font-weight: bold;
                }
               #thcn_table td{
                background: #ffffff;
            }
            .modal {
                    display:    none;
                    position:   fixed;
                    z-index:    1000;
                    top:        0;
                    left:       0;
                    height:     100%;
                    width:      100%;
                    background: rgba( 255, 255, 255, .8 ) 
                                url(./images/loading.gif) 
                                50% 50% 
                                no-repeat;
                }/**
                 When the body has the loading class, we turn
               the scrollbar off with overflow:hidden */
            body.loading {
                overflow: hidden;   
            }

            /* Anytime the body has the loading class, our
               modal element will be visible */
            body.loading .modal {
                display: block;
            }
            #loadingbnbd ,#loadingbbd{
                height: 500px; 
                overflow-y: scroll; 
              }
        </style>
    </head>
    <body>
        <ul id="1stmenu" class="nav nav-pills">
            <%
                if(session.getAttribute("1").equals("1") || session.getAttribute("2").equals("1") || session.getAttribute("3").equals("1") || session.getAttribute("4").equals("1")
                        || session.getAttribute("5").equals("1") || session.getAttribute("6").equals("1") || session.getAttribute("7").equals("1") || session.getAttribute("8").equals("1")
                        || session.getAttribute("9").equals("1")){
                %>
            <li><a href="xulydon.jsp?page=nhapdon">Xử Lý Đơn</a></li>
             <%
                 }
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
                 %>
            <li class="active"><a href="thuphi.jsp?page=chuathuphi">Thu Phí Lệ Phí</a></li>
            
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
                <li class="sub-menu"><a href="thuphi.jsp?page=chuathuphi">Chưa thu phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=dathuphi">Đã thu phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=tkbl">Thống kê biên lai</a></li>
                <li class="sub-menu" ><a href="thuphi.jsp?page=tkbc">Thống kê báo có</a></li>
                <li class="sub-menu"><a class="active"  href="thuphi.jsp?page=thcn">Tổng hợp công nợ</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=xembaophi">Xem thông báo phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=khongcostk">Đơn chưa thu phí không có số tài khoản</a></li>
        </ul> <br>
        <table class="table" >
                <tbody>
                    <tr>
                        <td>Lọc Dữ Liệu</td>
                        <td><input class="form-control" type="text" value="" id="thcn_congno" placeholder="Ngày tháng"></td>
                        <td><input class="form-control" type="text" value="" id="thcn_khachhang" placeholder="Khách hàng"></td>
                                <td>
                                    <select class="form-control" id="thcn_selectloaisearch">
                                        <option value="0">Tổng tiền</option>
                                        <option value="1">Đầu kỳ</option>
                                        <option value="2">Phải thu</option>
                                        <option value="3">Đã thu</option>
                                        <option value="4">Cuối kỳ</option>
                                        <option value="5">Báo có</option>
                                    </select>
                                </td>
                        <td><input class="form-control" type="text" value="" id="thcn_tongtien" placeholder="Tổng tiền"></td>
                        <td><input class="btn btn-default" type="button" value="Tìm kiếm" id="btn_Search">
                                <input class="btn btn-default" type="button" value="Xuất Excel" id="btn_Excel">
                        </td>
                    </tr>
                </tbody>
            </table>
        <table id="thcn_table" style="width: 100%" class="table-bordered ">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th rowspan="2" >STT</th>
                    <th rowspan="2" >Tên Khách Hàng</th>
                    <th rowspan="2" >Mã Khách Hàng</th>
                    <th colspan="2" >Đầu kỳ</th>
                    <th colspan="2" >Phải thu</th>
                    <th colspan="2" >Đã thu</th>
                    <th colspan="2" >Cuối kỳ</th>
                    <th colspan="2" >Báo có</th>
                    <th rowspan="2" >Chi tiết</th>
                </tr>
                <tr>
                    <th>Tổng tiền</th>
                    <th>Chi tiết</th>
                    <th>Tổng tiền</th>
                    <th>Chi tiết</th>
                    <th>Tổng tiền</th>
                    <th>Chi tiết</th>
                    <th>Tổng tiền</th>
                    <th>Chi tiết</th>
                    <th>Còn dư</th>
                    <th>Chi tiết</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <div class="modal"></div>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        <script>
            var $pagination = $('#ul_page');
                var searchList = [];
                var defaultOpts = {
                    totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'Trang Trước',
                    next: 'Trang sau',
                    last: 'Trang cuối',
                    loop: false
                };
                $pagination.twbsPagination(defaultOpts);
            $('#thcn_congno').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                var searchFunction = function(){
                    var ngayketthuc = $('#thcn_congno').val();
                    var khachhang = $('#thcn_khachhang').val();
                    var loaisearchc= $('#thcn_selectloaisearch').val();
                    var sotien = $('#thcn_tongtien').val();
                    $.ajax({
                        url : "thuphiservlet",
                        type : "GET",
                        data : {
                                         "action" : "tonghopcongno",page: 1,ngayketthuc: ngayketthuc,khname: khachhang,loaisearchc : loaisearchc, sotien: sotien
                                 },
                         dataType : "json",
                         success: function (data) {
                             console.log(data);
                             $('#thcn_table tbody').html(data.data);
                             var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $body.addClass("loading");
                                    $.ajax({
                                            url : "thuphiservlet",
                                            type : "GET",
                                            dataType : "json",
                                            data:{action: "tonghopcongno",page: page,ngayketthuc: ngayketthuc,khname: khachhang,loaisearchc : loaisearchc, sotien: sotien},
                                            success: function (data) {
                                               // $('#nganchantb tbody').empty();
                                               $body.removeClass("loading");
                                                $('#tb_cctk tbody').html(data.data);
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
                var exportEcel = function(){
                    var ngayketthuc = $('#thcn_congno').val();
                    window.location="thuphiservlet?action=thcnexcel&ngayketthuc="+ngayketthuc;
                };
                
                $body = $("body");
               $(document).on({
                    ajaxStart: function() { $body.addClass("loading");    },
                     ajaxStop: function() { $body.removeClass("loading"); }    
                });
                $('#btn_Search').on('click',searchFunction);
                $('#btn_Excel').on('click',exportEcel);
        </script>
    </body>
</html>
