<%-- 
    Document   : vanthu
    Created on : Jul 5, 2016, 5:18:03 PM
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
                table#tb_tkvanthu, #tb_tkvanthu th, #tb_tkvanthu td {
                    border: 1px solid black;
                    text-align: center;
                }
            #tb_tkvanthu td{
                background: #ffffff;
            }
            #tb_tkvanthu th.body{
                background: #aab7d1;
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
            <li class="pull-right"><a href="#"><span class="glyphicon glyphicon-user"></span><%= session.getAttribute("fullname").toString() %><input type="hidden" id="username" name="username" value="<%=session.getAttribute("username")%>" /></a></li>
            

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
                <li class="sub-menu"><a class="active" href="thongke.jsp?page=vanthu">Văn thư</a></li>
                <%
                    }
                if(session.getAttribute("20").equals("1")){
                %>
                <li class="sub-menu"><a href="thongke.jsp?page=phandon">Phân đơn</a></li>
                <%
                    }
                %>
        </ul> <br>
        <div class="modal"></div>
        <!-- LỌC DỮ LIỆU -->
        <div>
            <table class="table" >
                <tbody>
                    <tr>
                        <td>Lọc Dữ Liệu</td>
                        <td><input class="form-control" type="text" value="" id="tk_ngaybatdau" placeholder="Ngày bắt đầu"></td>
                        <td><input class="form-control" type="text" value="" id="tk_ngaykethuc" placeholder="Ngày kết thúc"></td>
                        <td><input class="form-control" type="text" value="" id="tk_mabuudien" placeholder="Mã Bưu Điện"></td>
                        <td><input class="form-control" type="text" value="" id="tk_maonline" placeholder="Số online"></td>
                        <td>
                            <select class="form-control loaidon" id="tk_loaidon">
                            </select>
                        </td>
                        <td>
                            <select class="form-control" id="tk_loaihinhnhan">
                                <option value="0">Loại hình nhận</option>
                                <option value="1">CE</option>
                                <option value="2">CF</option>
                                <option value="3">CT</option>
                                <option value="4">CB</option>
                            </select>
                        </td>
                        <td><input class="form-control" type="text" value="" id="tk_maloainhan" placeholder="Mã loại hình nhận"></td>
                        <td>
                            <select class="form-control" id="tk_loaidk">
                                <option value="0">Loại đăng ký</option>
                                <option value="1">BD</option>
                                <option value="2">TT</option>
                                <option value="3">CSGT</option>
                            </select>
                        </td>
                        <td>
                            <input class="form-control" type="text" value="" id="tk_bnbd" placeholder="Bên nhận bảo đảm">
                        </td>
                        <td><input class="form-control" type="text" value="" id="tk_bentp" placeholder="Bên nhận bảo đảm( Thu phí)"></td>
                        <td><input class="form-control" type="text" value="" id="tk_bbd" placeholder="Bên bảo đảm"></td>
                        <td><input class="btn btn-default" type="button" value="Tìm kiếm" id="btn_Search">
                                <input class="btn btn-default" type="button" value="Xuất Excel" id="btn_Excel">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <table class="table" id="tb_tkvanthu">
            <thead style="background-color: #87CEFA">
                <tr>
                        <th rowspan="2" align="center" >Ngày gửi</th>
                        <th rowspan="2" align="center" >Mã số bưu điện</th>
                        <th rowspan="2" align="center" >Nơi nhận</th>
                        <th rowspan="2" align="center" >Sổ biên lai</th>
                        <th colspan="7" align="center">Thông tin hồ sơ</th>
                </tr>
                <tr>
                    <th>Ngày nhận</th>
                    <th>Số đơn online</th>
                    <th>Số pin</th>
                    <th>Loại hình nhận</th>
                    <th>Bên Nhận Bảo Đảm</th>
                    <th>Bên Nhận Bảo Đảm(TT phí)</th>
                    <th>Bên Bảo Đảm</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        
        <script>
            $("#tk_loaidon").append('<option value="0">Loại đơn</option><option value="1">LĐ</option><option value="2">TĐ</option>\n\
                        <option value="3">Xóa</option><option value="4">VB-XL-TS</option>\n\
                        <option value="5">CC-TT</option>\n\
                        <option value="6">BẢN SAO</option>\n\
                        <option value="7">TBKBTHA</option>\n\
                        <option value="8">MIỄN PHÍ</option>\n\
                        <option value="9">CSGT</option>\n\
                        <option value="10">CSGT-TĐ</option>\n\
                        <option value="11">CSGT-X</option>\n\
                        <option value="12">CSGT-Online</option>\n\
                        <option value="13">CSGT-Online/TĐ</option>'
                );
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
            // phân trang  --------------------------
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
                /**
                 $.ajax({
                       url : "./thongke",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "vanthu",page: 1
                                },
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages/**,
                                onPageClick: function (event, page) {
                                    console.log('event = '+event.toString());
                                    ajaxLoadTable(page);
                            }));
                        }
                  }); **/
              /**    var ajaxLoadTable = function(page){
                    if(page === undefined){
                        page = '1';
                    }
                    $.ajax({
                        type: "POST",
                        url:"./thongke",
                        dataType : "json",
                        data:{"action":"vanthu","page":page},
                        success: function (data) {
                            console.log(data);
                            $('#tb_tkvanthu tbody').empty();
                            $('#tb_tkvanthu tbody').append(data.data);
                        }
                    });
                }; **/
                function searchTable(){
                    var ngaybatdau = $('#tk_ngaybatdau').val();
                    var ngayketthuc = $('#tk_ngaykethuc').val();
                    var maonline = $('#tk_maonline').val();
                    var loaidon = $('#tk_loaidon').val();
                    var loaihinhnhan = $('#tk_loaihinhnhan').val();
                    var manhan = $('#tk_maloainhan').val();
                    var loaidk = $('#tk_loaidk').val();
                    var bnbd = $('#tk_bnbd').val();
                    var btp = $('#tk_bentp').val();
                    var bbd = $('#tk_bbd').val();
                    var mabuudien = $('#tk_mabuudien').val();
                    searchList.splice(0,searchList.length);
                    searchList.push(ngaybatdau);searchList.push(ngayketthuc);searchList.push(maonline);searchList.push(loaidon);
                    searchList.push(loaihinhnhan);searchList.push(manhan);searchList.push(loaidk);searchList.push(bnbd);
                     searchList.push(btp);searchList.push(bbd);searchList.push(mabuudien);
                    $.ajax({
                        type: "GET",
                        url:"./thongke",
                        dataType : "json",
                        data:{"action":"vanthu","page":1,ngaybatdau: ngaybatdau,ngayketthuc: ngayketthuc,maonline: maonline,
                        loaidon: loaidon,loaihinhnhan: loaihinhnhan,manhan: manhan,loaidk: loaidk,bnbd: bnbd,btp: btp,bbd: bbd,mabuudien: mabuudien},
                        success: function (data) {
                            console.log(data);
                          var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $('#tb_tkvanthu tbody').empty();
                            $('#tb_tkvanthu tbody').append(data.data);
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $.ajax({
                                            type: "GET",
                                            url:"./thongke",
                                            dataType : "json",
                                            data:{"action":"vanthu","page":page,ngaybatdau: searchList[0],ngayketthuc: searchList[1],maonline: searchList[2],
                loaidon: searchList[3],loaihinhnhan: searchList[4],manhan: searchList[5],loaidk: searchList[6],bnbd: searchList[7],
                btp: searchList[8],bbd: searchList[9],mabuudien: searchList[10]},
                                            success: function (data) { 
                                                $('#tb_tkvanthu tbody').empty();
                                                $('#tb_tkvanthu tbody').html(data.data);
                                            }
                                    }); 
                                }
                            }));
                        }
                    });
                };
                //--------------------------------------------
                var exportExcel = function(){
                    var ngaybatdau = $('#tk_ngaybatdau').val();
                    var ngayketthuc = $('#tk_ngaykethuc').val();
                    var maonline = $('#tk_maonline').val();
                    var loaidon = $('#tk_loaidon').val();
                    var loaihinhnhan = $('#tk_loaihinhnhan').val();
                    var manhan = $('#tk_maloainhan').val();
                    var loaidk = $('#tk_loaidk').val();
                    var bnbd = $('#tk_bnbd').val();
                    var btp = $('#tk_bentp').val();
                    var bbd = $('#tk_bbd').val();
                    var mabuudien = $('#tk_mabuudien').val();
                    window.location="thongke?action=tkexcelvanthu&ngaybatdau="+ngaybatdau+"&ngayketthuc="+ngayketthuc
                            +"&maonline="+maonline+"&loaidon="+loaidon+"&loaihinhnhan="+loaihinhnhan+"&manhan="+manhan
                            +"&loaidk="+loaidk+"&bnbd="+bnbd+"&btp="+btp+"&bbd="+bbd+"&mabuudien="+mabuudien;
                };
                $body = $("body");
                $(document).on({
                    ajaxStart: function() { $body.addClass("loading");    },
                     ajaxStop: function() { $body.removeClass("loading"); }    
                });
                $('#btn_Search').on('click',searchTable);
                $('#btn_Excel').on('click',exportExcel);
        </script>
    </body>
</html>
