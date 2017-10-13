<%-- 
    Document   : thongke
    Created on : Jul 4, 2016, 8:09:33 AM
    Author     : Thorfinn
--%>

<%@page import="com.ttdk.bean.HieuSuat"%>
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
                background-color: #87CEFA;
            }

        </style>
        <script>
            $(document).ready(function(){
                $( "#tk_fromday" ).datepicker({
                    dateFormat: 'dd-mm-yy'
                });
                $( "#tk_today" ).datepicker({
                      dateFormat: 'dd-mm-yy'
                });
                
                function searchHieuSuat(fromday,today){
                    $.ajax({
                        type: "GET",
                        url:"HieuSuatServlet",
                        data:{"action":"tk_don","fromday":fromday,"today":today},
                        success: function (data) {
                            $('#tk_hieusuat').empty();
                            $('#tk_hieusuat').append(data);

                        }
                    });
                }
                var tk_Search = function(){
                    var fromday = $('#tk_fromday').datepicker().val();
                    var today  =$('#tk_today').datepicker().val();
                    if(fromday !== "" && today !== ""){
                        var fromdayArr = fromday.split("-");
                        var todayArr = today.split("-");
                        var fromYear = fromdayArr[2];
                        var fromMonth = fromdayArr[1];
                        var fromDay = fromdayArr[0];
                        var toYear = todayArr[2];
                        var toMonth = todayArr[1];
                        var toDay = todayArr[0];
                        if(parseInt(fromYear) > parseInt(toYear)){
                            alert('Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc');
                        }else{
                            if(parseInt(fromYear) === parseInt(toYear)){
                               if(parseInt(fromMonth) > parseInt(toMonth)){
                                   alert('Tháng bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc');
                               }else{
                                   if(parseInt(fromMonth) === parseInt(toMonth)){
                                       if(parseInt(fromDay) > parseInt(toDay)){
                                           alert('Ngày bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc');
                                       }else{
                                          searchHieuSuat(fromday,today);
                                       }
                                   }else{
                                       searchHieuSuat(fromday,today);
                                   }
                               }
                            }else{
                               searchHieuSuat(fromday,today);
                            }
                        }
                    }
                    
                };
                
                $('#tk_search').on('click',tk_Search);
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
                if(session.getAttribute("1").equals("1") && session.getAttribute("2").equals("1")){
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
                <li class="sub-menu"><a class="active"  href="xulydon.jsp?page=thongke">Thống kê hiệu suất</a></li>
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
        <br>
        <fieldset>
            Lọc Dữ Liệu: <input type="text" name="tk_fromday" id="tk_fromday" placeholder="Ngày bắt đầu"/>
            
            <input type="text" name="tk_today" id="tk_today" placeholder="Ngày kết thúc" />
            <button type="button" id="tk_search" class="btn btn-default">Tìm Kiếm</button>
        </fieldset><br>
     
            <div style="overflow: auto" class="container">
                <table id="tk_hieusuat" >
                </table>
            </div>
        <script>
            $( "#tk_fromday" ).datepicker({
                dateFormat: 'dd-mm-yy',          
                monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                    "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                    "Tháng 10", "Tháng 11", "Tháng 12" ],
                dayNamesMin: ["CN","2","3","4","5","6","7"],
                changeMonth: true,
                changeYear: true
            });
            $( "#tk_today" ).datepicker({
                dateFormat: 'dd-mm-yy',          
                monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                    "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                    "Tháng 10", "Tháng 11", "Tháng 12" ],
                dayNamesMin: ["CN","2","3","4","5","6","7"],
                changeMonth: true,
                changeYear: true
            });
        </script>
    </body>
</html>
