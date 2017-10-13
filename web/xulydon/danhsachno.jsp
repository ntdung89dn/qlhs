<%-- 
    Document   : danhsachno
    Created on : Jul 4, 2016, 8:10:06 AM
    Author     : Thorfinn
--%>

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
            th{
                height: 10px;
                padding: 0;
            }
            td{
                background: #ffffff;
            }
            #loadingbnbd ,#loadingbbd{
                height: 500px; 
                overflow-y: scroll; 
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
                <li class="sub-menu"><a href="xulydon.jsp?page=dondatra">Xem đơn đã trả</a></li>
                <%
                    }
                     if(session.getAttribute("9").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=thongke">Thống kê hiệu suất</a></li>
                <%
                    }
                     if(session.getAttribute("22").equals("1") ){
                    %>
                <li class="sub-menu"><a class="active" href="xulydon.jsp?page=danhsachno">Danh sách nợ</a></li>
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
        </ul> <br>
        <div class="row">
            <div>
                <div class="col-xs-6">Lọc : <input type="text" id="dns_bnbd" style="width: 400px"placeholder="Bên Nhận Bảo Đảm" >&nbsp;&nbsp;	&nbsp;<img src="./images/excel.png"/></div>
                <div class="col-xs-6"><input type="text" id="dns_tienmin" style="width: 100px" placeholder="Số tiền thấp nhất" value="1">
                    <input type="text" id="dns_tienmax" style="width: 100px" placeholder="Số tiền cao nhất" value="1000"></div><br>
                     <br>
                <div class="col-xs-6">
                    <div id="loadingbnbd">
                        <table id="dns_locdl" style="width: 100%;" style=""class="table table-bordered">
                        <thead style="background-color: #87CEFA">
                            <tr>
                                <th>STT</th>
                                <th>Danh Sách Bên Nhận Đảm Bảo</th>
                                <th>ST</th>
                                <th>TT</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                        </tbody>
                    </table>
                    </div>
                    
                </div>
                <div class="col-xs-6">
                    <div id="loadingbbd">
                        <table id="dns_chitiet" style="width: 100%;" class="table table-bordered">
                        <thead style="background-color: #87CEFA">
                            <tr>
                                <th>STT</th>
                                <th>Loại Hình Nhận</th>
                                <th>Ngày</th>
                                <th>Bên Bảo Đảm</th>
                                <th>ST</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                        </tbody>
                    </table>
                  </div>
                    
                </div>
            </div>
        </div>
        <br>
        <script>
            var loadBTP = function(){
                var  btp = $('#dns_bnbd').val();
                var st = $('#dns_tienmin').val();
                var total = $('#dns_tienmax').val();
                $.ajax({
                        type: "GET",
                        url:"TraDonServlet",
                        data:{"action":"loaddanhsachno",btp: btp, st: st, total: total},
                        success: function (data) {
                            $('#dns_locdl tbody').html(data);
                        }
                    });
            };
            loadBTP();
            $(document).on('click','tr',function(){
                var donids  = $(this).find('.donids').val();
                console.log(donids);
                $.ajax({
                        type: "GET",
                        url:"TraDonServlet",
                        data:{"action":"loadbbddns",donids: donids},
                        success: function (data) {
                            $('#dns_chitiet tbody').html(data);
                        }
                    });
            });
            
            var searchDNS = function(){
                var id = $(this).prop('id');
                
                var minLength =0;
                switch(id){
                    case "dns_bnbd" : minLength = 4;
                        break;
                    case "dns_tienmin": minLength = 1;
                        break;
                    case "dns_tienmax": minLength = 3;
                }
                var lengthText = $(this) .val().length;
               if(lengthText > minLength || lengthText ===0){
                   
                   loadBTP();
               }
            };
            $('#dns_bnbd').on('keyup paste',searchDNS);
            $('#dns_tienmin').on('keyup paste',searchDNS);
            $('#dns_tienmax').on('keyup paste',searchDNS);
         </script>
    </body>
</html>
