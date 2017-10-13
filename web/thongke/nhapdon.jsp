<%-- 
    Document   : nhapdon
    Created on : Jul 5, 2016, 5:17:56 PM
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
              #tb_tknhapdon  td{
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
                <li class="sub-menu"><a   class="active"  href="thongke.jsp?page=nhapdon">Nhập đơn</a></li>
                <%
                    }
                if(session.getAttribute("19").equals("1")){
                %>
                <li class="sub-menu"><a href="thongke.jsp?page=vanthu">Văn thư</a></li>
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
        <table class="table" id="tb_tknhapdon">
            <thead style="background-color: #87CEFA">
                <tr>
                        <th rowspan="2" align="center" class="column3">Thời điểm nhập</th>
                        <th colspan="2" align="center" class="column2">Số Đơn Do Online Cấp</th>
                        <th rowspan="2" align="center" class="column3">Loại Hình Nhận </th>
                        <th rowspan="2" align="center" class="column1">Loại Đơn</th>
                        <th rowspan="2" align="center" class="column2">Lệ Phí</th>
                        <th rowspan="2" align="center" class="column1">Cung Cấp Thông Tin</th>
                        <th rowspan="2" align="center" class="column4">Bên Nhận Bảo Đảm</th>
                        <th rowspan="2" align="center" class="column4">Bên Nhận Bảo Đảm(TT Phí)</th>
                        <th rowspan="2" align="center" class="column4">Bên Bảo Đảm</th>
                </tr>
                <tr style="width: 100%;">
                        <th class="column2" align="center">Số Đơn Online</th>
                        <th class="column2" align="center">Số Pin</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
        <table id="tb_tkdulieu">
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
        var days = 3;
        var date = new Date();
        var firstdate = new Date(date.getFullYear(), date.getMonth(), 1);
         var last = new Date(date.getTime() - (days * 24 * 60 * 60 * 1000));
        $( "#tk_ngaybatdau" ).datepicker({
                dateFormat: 'dd-mm-yy',          
                monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                    "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                    "Tháng 10", "Tháng 11", "Tháng 12" ],
                dayNamesMin: ["CN","2","3","4","5","6","7"],
                changeMonth: true,
                changeYear: true
            }).datepicker("setDate", firstdate);
            $( "#tk_ngaykethuc" ).datepicker({
                dateFormat: 'dd-mm-yy',          
                monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                    "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                    "Tháng 10", "Tháng 11", "Tháng 12" ],
                dayNamesMin: ["CN","2","3","4","5","6","7"],
                changeMonth: true,
                changeYear: true
            }).datepicker("setDate", date);
            // phân trang  --------------------------
                var $pagination = $('#ul_page');
                var searchList = [];
                var defaultOpts = {
                    totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'Trang cuối',
                    next: 'Tiếp',
                    last: 'Sau',
                    loop: false,
                    initiateStartPageClick: false
                };
                 $pagination.twbsPagination(defaultOpts);
             /**    $.ajax({
                       url : "thongke",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "nhapdon",page: 1,ngaybatdau: $('#tk_ngaybatdau').val()
                                },
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $.ajax({
                                        type: "GET",
                                        url:"thongke",
                                        dataType : "json",
                                        data:{"action":"nhapdon","page":page,ngaybatdau: $('#tk_ngaybatdau').val()},
                                        success: function (data) {
                                            console.log(data);
                                            $('#tb_tknhapdon tbody').html(data.data);
                                            loadDuLieuTK(data);
                                        }
                                    });
                                }
                            }));
                        }
                  });**/
                var searchTable = function(){
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
                    searchList.splice(0,searchList.length);
                    searchList.push(ngaybatdau);searchList.push(ngayketthuc);searchList.push(maonline);searchList.push(loaidon);
                    searchList.push(loaihinhnhan);searchList.push(manhan);searchList.push(loaidk);searchList.push(bnbd);
                     searchList.push(btp);searchList.push(bbd);
                    $.ajax({
                        type: "GET",
                        url:"thongke",
                        dataType : "json",
                        data:{"action":"nhapdon","page":1,ngaybatdau: ngaybatdau,ngayketthuc: ngayketthuc,maonline: maonline,
                        loaidon: loaidon,loaihinhnhan: loaihinhnhan,manhan: manhan,loaidk: loaidk,bnbd: bnbd,btp: btp,bbd: bbd,type: "search"},
                        success: function (data) {
                            console.log(data);
                          var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $('#tb_tknhapdon tbody').html(data.data);
                            loadDuLieuTK(data);
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $.ajax({
                                            type: "GET",
                                            url:"thongke",
                                            dataType : "json",
                                            data:{"action":"nhapdon","page":page,ngaybatdau: searchList[0],ngayketthuc: searchList[1],maonline: searchList[2],
                loaidon: searchList[3],loaihinhnhan: searchList[4],manhan: searchList[5],loaidk: searchList[6],bnbd: searchList[7],
                btp: searchList[8],bbd: searchList[9]},
                                            success: function (data) { 
                                                $('#tb_tknhapdon tbody').html(data.data);
                                               // loadDuLieuTK(data);
                                            }
                                    }); 
                                }
                            }));
                        }
                    });
                };
                function loadDuLieuTK(data){
                    var tbody = "<tr><td colspan='8'>Tổng số hồ sơ: <span style='color:red'>"+data.tongdon+"</span></td><tr>";
                        tbody += "<tr><td colspan='2'>CE: <span style='color:red'>"+data.ce+"</span></td>";
                        tbody += "<td colspan='2'>CF: <span style='color:red'>"+data.cf+"</span></td>";
                        tbody += "<td colspan='2'>CT: <span style='color:red'>"+data.ct+"</span></td>";
                        tbody += "<td colspan='2'>CB: <span style='color:red'>"+data.cb+"</span></td></tr>";
                        tbody += "<tr> <td colspan='2'>LĐ: <span style='color:red'>"+data.ld+"</span></td>";
                        tbody += "<td colspan='2'>TĐ: <span style='color:red'>"+data.td+"</span></td>";
                        tbody += "<td colspan='2'>Xóa: <span style='color:red'>"+data.xoa+"</span></td>";
                        tbody += "<td colspan='2'>VB-XL-TS: <span style='color:red'>"+data.vbxl+"</span></td></tr></tr>";
                        tbody += "<td colspan='2'>CC-TT: <span style='color:red'>"+data.cctt+"</span></td>";
                        tbody += "<td colspan='2'>BẢN SAO: <span style='color:red'>"+data.bs+"</span></td>";
                        tbody += "<td colspan='2'>TBKBTHA: <span style='color:red'>"+data.tbkb+"</span></td>";
                        tbody += "<td colspan='2'>Miễn phí: <span style='color:red'>"+data.mp+"</span></td></tr><tr>";
                        tbody += "<td colspan='2'>CSGT: <span style='color:red'>"+data.csgt+"</span></td>";
                        tbody += "<td colspan='2'>CSGT-TĐ: <span style='color:red'>"+data.csgttd+"</span></td>";
                        tbody += "<td colspan='2'>CSGT-X: <span style='color:red'>"+data.csgtx+"</span></td>";
                        tbody += "<td colspan='2'>CSGT-Online: <span style='color:red'>"+data.csgto+"</span></td></tr><tr>";
                        tbody += "<td colspan='2'>CSGT-Online/TĐ: <span style='color:red'>"+data.csgtotd+"</span></td></tr>";
                        $('#tb_tkdulieu tbody').html(tbody);
                }
               // ajaxLoadTable;
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
                    window.location="thongke?action=tkexcel&ngaybatdau="+ngaybatdau+"&ngayketthuc="+ngayketthuc
                            +"&maonline="+maonline+"&loaidon="+loaidon+"&loaihinhnhan="+loaihinhnhan+"&manhan="+manhan
                            +"&loaidk="+loaidk+"&bnbd="+bnbd+"&btp="+btp+"&bbd="+bbd;
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
