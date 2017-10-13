<%-- 
    Document   : phandon
    Created on : Jul 5, 2016, 5:18:09 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            table {
                border-width:5px;	
                border-style:none;
            }
            #loadingbnbd ,#loadingbbd{
                height: 500px; 
                overflow-y: scroll; 
              }
              .ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year {
                    color: #1c94c4;
                    font-size: 14px;
                    font-weight: bold;
                }
                table#tb_tkphandon, #tb_tkphandon th, #tb_tkphandon td {
                    border: 1px solid black;
                    text-align: center;
                }
            .ScrollStyle
            {
                max-height: 1200px;
                overflow-x: scroll;
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
                }

            /* When the body has the loading class, we turn
               the scrollbar off with overflow:hidden */
            body.loading {
                overflow: hidden;   
            }

            /* Anytime the body has the loading class, our
               modal element will be visible */
            body.loading .modal {
                display: block;
            }
            thead {
                background-color: #87CEFA;
            }
           #tb_tkphandon td{
                background: #ffffff;
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
            %>
            <li class="active"><a href="thongke.jsp?page=nhapdon">Thống kê</a></li>
            <%
                 if(session.getAttribute("21").equals("1")){
                %>
            <li><a href="thuphi.jsp?page=chuathuphi">Thu Phí Lệ Phí</a></li>
             <%
                 }
                %>
            
            <!--    <li class="dropdown pull-right"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Cài Đặt<span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#">Đổi Mật Khẩu</a></li>
                    <li><a href="#">Thông tin cá nhân</a></li>
                </ul>
            </li> -->
            <li  class="pull-right"><a id="btn_changepass" >Đổi mật khẩu</a></li>
            <li  class="pull-right"><a id="logout" href="logout"><span class="glyphicon glyphicon-log-in"></span> Đăng Xuất</a></li>
            <li class="pull-right"><a href="#"><span class="glyphicon glyphicon-user"></span> Xin Chào....<input type="hidden" id="username" name="username" value="<%=session.getAttribute("username")%>" /></a></li>
            
        </ul>
        <ul id="sub-menu-1">
                <%
                if(session.getAttribute("18").equals("1") ){
                %>
                <li class="sub-menu"><a   href="thongke.jsp?page=nhapdon">Nhập đơn</a></li>
                <%
                    }
                if(session.getAttribute("19").equals("1")){
                %>
                <li class="sub-menu"><a href="thongke.jsp?page=vanthu">Văn thư</a></li>
                <%
                    }
                if(session.getAttribute("20").equals("1")){
                %>
                <li class="sub-menu"><a  class="active"  href="thongke.jsp?page=phandon">Phân đơn</a></li>
                <%
                    }
                %>
        </ul> <br>
        <!-- LỌC DỮ LIỆU -->
        <div>
            <table class="table" >
                <tbody>
                    <tr>
                        <td>Lọc Dữ Liệu</td>
                        <td><input class="form-control" type="text" value="" id="tk_ngaybatdau" placeholder="Ngày bắt đầu"></td>
                        <td><input class="form-control" type="text" value="" id="tk_ngaykethuc" placeholder="Ngày kết thúc"></td>
                        <td>
                            <select class="form-control" id="tk_loaihinhnhan">
                                <option value="0">Chi tiết</option>
                                <option value="1">Không chi tiết</option>
                            </select>
                        </td>
                        <td><input class="btn btn-default" type="button" value="Tìm kiếm" id="btn_Search">
                                <input class="btn btn-default" type="button" value="Xuất Excel" id="btn_Excel">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="modal"></div>
        <div  style="overflow: auto" class="container">
            <table class="table" id="tb_tkphandon">
            </table>
        </div>
        
        
        <script>
        $( "#tk_ngaybatdau" ).datepicker({
                dateFormat: 'dd-mm-yy',          
                monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                    "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                    "Tháng 10", "Tháng 11", "Tháng 12" ],
                dayNamesMin: ["CN","2","3","4","5","6","7"],
                changeMonth: true,
                changeYear: true
            });
            $( "#tk_ngaykethuc" ).datepicker({
                dateFormat: 'dd-mm-yy',          
                monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                    "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                    "Tháng 10", "Tháng 11", "Tháng 12" ],
                dayNamesMin: ["CN","2","3","4","5","6","7"],
                changeMonth: true,
                changeYear: true
            });
            function searchPhandon(){
                var ngaybatdau = $('#tk_ngaybatdau').val();
                var ngayketthuc = $('#tk_ngaykethuc').val();
                $.ajax({
                        type: "GET",
                        url:"./thongke",
                        data:{"action":"phandon",ngaybatdau: ngaybatdau, ngayketthuc: ngayketthuc},
                        success: function (data) {
                            console.log(data);
                            $('#tb_tkphandon').empty();
                            $('#tb_tkphandon').html(data);
                        }
                    });
            };
                //--------------------------------------------
                var exportExcel = function(){
                    var ngaybatdau = $('#tk_ngaybatdau').val();
                    var ngayketthuc = $('#tk_ngaykethuc').val();
                    var maonline = $('#tk_maonline').val();
                    window.location="thongke?action=tkexcelphandon&ngaybatdau="+ngaybatdau+"&ngayketthuc="+ngayketthuc
                            +"&maonline="+maonline;
                };
                $('#btn_Search').on('click',searchPhandon);
                $('#btn_Excel').on('click',exportExcel);
                $body = $("body");
                $(document).on({
                    ajaxStart: function() { $body.addClass("loading");    },
                     ajaxStop: function() { $body.removeClass("loading"); }    
                });
        </script>
    </body>
</html>

