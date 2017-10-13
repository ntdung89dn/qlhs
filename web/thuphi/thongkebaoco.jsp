<%-- 
    Document   : thongkebaoco
    Created on : Jul 5, 2016, 5:20:10 PM
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
            #tb_thongkebaoco   td{
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
                <li class="sub-menu"><a class="active" href="thuphi.jsp?page=tkbc">Thống kê báo có</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=thcn">Tổng hợp công nợ</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=xembaophi">Xem thông báo phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=khongcostk">Đơn chưa thu phí không có số tài khoản</a></li>
        </ul> <br>
       <!-- <div id="div_loading"></div> -->
       <div class="modal"></div>
        <table class="table table-hover" >
                <tbody>
                    <tr>
                        <td>Lọc Dữ Liệu</td>
                        <td><input class="form-control" type="text" value="" id="tkbc_ngaybatdau" placeholder="Ngày bắt đầu"></td>
                        <td><input class="form-control" type="text" value="" id="tkbc_ngaykethuc" placeholder="Ngày kết thúc"></td>
                        <td>
                            <input class="form-control" type="text" value="" id="tkbc_sobaoco" placeholder="Số báo có">
                        </td>
                        <td>
                            <input class="form-control" type="text" value="" id="tkbc_sotaikhoan" placeholder="Số tài khoản">
                        </td>
                        <td>
                            <input class="form-control" type="text" value="" id="tkbc_bnbd" placeholder="Bên nhận bảo đảm">
                        </td>
                        <td>
                            <select class="form-control" id="tkbc_tienthua">
                                <option value="0">Tất cả</option>
                                <option value="1">Tiền thừa dương</option>
                                <option value="2">Tiền thừa âm</option>
                            </select>
                        </td>
                        <td><input class="btn btn-default" type="button" value="Tìm kiếm" id="btn_Search"></td>
                          <td><input class="btn btn-default" type="button" value="Xuất Excel" id="btn_Excel"></td>
                    </tr>
                </tbody>
            </table>
        
        <table id="tb_thongkebaoco" style="width: 100%" class="table-bordered table-hover">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th rowspan="2" >STT</th>
                    <th rowspan="2" >Ngày báo có</th>
                    <th rowspan="2" >Số báo có</th>
                    <th rowspan="2" >Mã Khách hàng</th>
                    <th rowspan="2" >Tên BNBĐ</th>
                    <th rowspan="2" >Tổng tiền báo có</th>
                     <th rowspan="2" >Tổng tiền thanh toán</th>
                     <th rowspan="2" >Tổng tiền thừa</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <script>
            var searchList = [];
            var days = 30;
            var date = new Date();
             var last = new Date(date.getTime() - (days * 24 * 60 * 60 * 1000));
            $('#tkbc_ngaybatdau').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                }).datepicker("setDate", last);
                $('#tkbc_ngaykethuc').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                
                function loadSearchTkBaoco(){
                    var ngaybatdau = $('#tkbc_ngaybatdau').datepicker().val();
                    var ngayketthuc = $('#tkbc_ngaykethuc').datepicker().val();
                    var sobaoco = $('#tkbc_sobaoco').val();
                    var loaitienthua = $('#tkbc_tienthua').val();
                    var taikhoan = $('#tkbc_sotaikhoan').val();
                    var bnbd = $('#tkbc_bnbd').val();
                    searchList.splice(0,searchList.length);
                    searchList.push(ngaybatdau);searchList.push(ngayketthuc);searchList.push(sobaoco);searchList.push(loaitienthua);
                    searchList.push(bnbd);searchList.push(taikhoan);
                    $.ajax({
                        type: "GET",
                        url:"thuphiservlet",
                        data:{"action":"searchbaoco","page": 1,ngaybatdau: ngaybatdau, ngayketthuc: ngayketthuc,
                            sobaoco: sobaoco,loaitienthua: loaitienthua,bnbd: bnbd, taikhoan: taikhoan},
                        success: function (data) {
                          //  $('#tb_thongkebaoco tbody').empty();
                            $('#tb_thongkebaoco tbody').html(data);
                        }
                    });
                };
                $body = $("body");
               $(document).on({
                    ajaxStart: function() { $body.addClass("loading");    },
                     ajaxStop: function() { $body.removeClass("loading"); }    
                });
                $('#btn_Search').on('click',loadSearchTkBaoco);
                $('#btn_Excel').on('click',function(){
                    var ngaybatdau = $('#tkbc_ngaybatdau').datepicker().val();
                    var ngayketthuc = $('#tkbc_ngaykethuc').datepicker().val();
                    var sobaoco = $('#tkbc_sobaoco').val();
                    var loaitienthua = $('#tkbc_tienthua').val();
                    var bnbd = $('#tkbc_sotaikhoan').val();
                    var taikhoan = $('#tkbc_bnbd').val();
                    window.location="thuphiservlet?action=thongkebcexcel&ngaybatdau="+ngaybatdau+"&ngayketthuc="+ngayketthuc
                            +"&sobaoco="+sobaoco+"&loaitienthua="+loaitienthua+"&bnbd="+bnbd+"&taikhoan="+taikhoan;
                });
        </script>
    </body>
</html>
