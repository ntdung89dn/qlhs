<%-- 
    Document   : khongcosotk
    Created on : Jul 5, 2016, 5:20:50 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            @media screen and (min-width: 768px) {
                    .modal-dialog {
                      width: 1000px; /* New width for default modal */
                    }
                    .modal-sm {
                      width: 350px; /* New width for small modal */
                    }
                }
                @media screen and (min-width: 992px) {
                    .modal-lg {
                      width: 950px; /* New width for large modal */
                    }
                }
               #tb_cctk td{
                background: #ffffff;
            }
            .modal_loading {
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
            body.loading .modal_loading {
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
                <li class="sub-menu" ><a href="thuphi.jsp?page=tkbc">Thống kê báo có</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=thcn">Tổng hợp công nợ</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=xembaophi">Xem thông báo phí</a></li>
                <li class="sub-menu"><a class="active" href="thuphi.jsp?page=khongcostk">Đơn chưa thu phí không có số tài khoản</a></li>
        </ul> <br>
        <div class="modal_loading"></div>
        <table class="table" >
                <tbody>
                    <tr>
                        <td><input class="form-control" type="text" value="" id="ktc_ngaybatdau" placeholder="Ngày bắt đầu" style="width: 100px !important"></td>
                        <td><input class="form-control" type="text" value="" id="ktc_ngaykethuc" placeholder="Ngày kết thúc" style="width: 100px !important"></td>
                        <td>
                            <input class="form-control" type="text" value="" id="ktc_dononline" placeholder="Số đơn online">
                        </td>
                        <td>
                            <select id="ktc_loaidon" class="form-control">
                                <option value="0">Loại đăng ký</option>
                                <option value="1">LĐ</option>
                                <option value="2">TĐ</option>
                                <option value="3">Xóa</option>
                                <option value="4">VB-XL-TS</option>
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
                            <select name="xvt_hinhnhan" id="ktc_loainhan"  class="form-control">
                                <option value="0">Tất cả loại hình nhận</option>
                                <option value="1">CE</option>
                                <option value="2">CF</option>
                                <option value="3">CT</option>
                                <option value="4">CB</option>
                            </select>
                        </td>
                        <td><input class="form-control" type="text" value="" id="ktc_manhan" placeholder="Mã nhận" ></td>
                        <td>
                            
                            <select id="ktc_loaidk" class="form-control">
                                <option value="0">Tất cả loại đăng ký</option>
                                <option value="1">BD</option>
                                <option value="2">TT</option>
                                <option value="3">CST</option>
                            </select>
                        </td>
                        <td>
                            <input class="form-control" type="text" value="" id="ktc_bnbd" placeholder="Bên nhận bảo đảm">
                        </td>
                        <td>
                            <input class="form-control" type="text" value="" id="ktc_bbd" placeholder="Bên bảo đảm">
                        </td>
                        <td><input class="btn btn-default" type="button" value="Tìm kiếm" id="btn_kcstkSearch"></td>
                    </tr>
                </tbody>
            </table>
        <table id="tb_cctk" style="width: 100%" class="table-bordered">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th rowspan="2" >Thời điểm nhập</th>
                    <th colspan="2" >Số đơn do Online cấp</th>
                    <th rowspan="2" >Loại hình nhận</th>
                    <th rowspan="2" >Loại đơn</th>
                    <th rowspan="2" >Số lượng tài sản</th>
                    <th rowspan="2" >Cung cấp thông tin</th>
                     <th rowspan="2" >Bên nhận bảo đảm</th>
                     <th rowspan="2" >Bên bảo đảm</th>
                </tr>
                <tr>
                    <th>Số đơn Online</th>
                    <th>Số pin</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                </tr>
            </tbody>
        </table>
        <div><br>
            <div style="text-align: left">
                <button type="button" class="btn btn-link" data-toggle="modal" data-target="#tkhs_modal">Thống kê hồ sơ</button>
            </div>
            <div style="text-align: right">
                <ul class="pagination" id="ul_page" ></ul>
            </div>
            
        </div>
        <!-- Modal -->
            <div id="tkhs_modal" class="modal fade" role="dialog">
              <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Thống kê hồ sơ</h4>
                  </div>
                  <div class="modal-body">
                     <div class="row">
                            <div class="col-sm-2" ><span>Tổng số hồ sơ đến ngày</span></div>
                            <div class="col-sm-3" ><input class="form-control" type="text" value="" id="ktc_ngaythongkebatdau" placeholder="Ngày bắt đầu thống kê" style="width: 100px !important"></div>
                            <div class="col-sm-3" ><input class="form-control" type="text" value="" id="ktc_ngaythongkeketthuc" placeholder="Ngày kết thúc thống kê" style="width: 100px !important"></div>
                            <div class="col-sm-2" ><button type="button" id="btn_thongkehs" class="btn btn-default" >Xem</button></div>
                      </div>
                      <div id="ktc_thongke_loading"></div>
                      <div id="thongtin_thongke" style="float:left; border: solid 0px; width:97%; margin-top:10px;">
                      </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                  </div>
                </div>

              </div>
            </div>
        <script>
            $body = $("body");
            var $pagination = $('#ul_page');
                var searchList = [];
                var defaultOpts = {
                    totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'trước',
                    next: 'Tiếp',
                    last: 'Trang cuối',
                    loop: false
                };
                 $pagination.twbsPagination(defaultOpts);
        /**         $.ajax({
                       url : "thuphiservlet",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "loaddonchuacotk",page: 1
                                },
                        
                        success: function (data) {
                            $('#tb_cctk tbody').html(data.data);
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $.ajax({
                                            url : "thuphiservlet",
                                            type : "GET",
                                            dataType : "json",
                                            data:{action: "loaddonchuacotk",page: page},
                                            success: function (data) {
                                               // $('#nganchantb tbody').empty();
                                                console.log(data);
                                                $('#tb_cctk tbody').html(data.data);
                                            },
                                            error: function (data) {
                                                console.log('Error:', data);
                                            }
                                        }); 
                                }
                            }));
                        }
                  }); **/
                  $( "#ktc_ngaybatdau" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                $( "#ktc_ngaykethuc" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                $( "#ktc_ngaythongkebatdau" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                }).datepicker("setDate", new Date());
                $( "#ktc_ngaythongkeketthuc" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                }).datepicker("setDate", new Date());
                var searchList = [];
                var searchDonCCTK = function(){
                    var ngaynhap = $('#ktc_ngaybatdau').val();
                    var ngayketthuc = $('#ktc_ngaykethuc').val();
                    var maonline = $('#ktc_dononline').val();
                    var loaidon = $('#ktc_loaidon').val();
                    var loainhan = $('#ktc_loainhan').val();
                    var manhan = $('#ktc_manhan').val();
                    var loaidk  = $('#ktc_loaidk').val();
                    var bnbd = $('#ktc_bnbd').val();
                    var bbd = $('#ktc_bbd').val();
                    searchList.splice(0,searchList.length);
                    searchList.push(ngaynhap);searchList.push(ngayketthuc);searchList.push(maonline);searchList.push(loaidon);
                    searchList.push(loainhan);searchList.push(manhan);searchList.push(loaidk);
                    searchList.push(bnbd);searchList.push(bbd);
                    $body.addClass("loading");
                    $.ajax({
                       url : "thuphiservlet",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "searchdonchuacotk",page: 1,ngaybatdau: ngaynhap,ngayketthuc: ngayketthuc,maonline: maonline,loaidon: loaidon,
                                        loainhan: loainhan,manhan: manhan,loaidk: loaidk,bnbd: bnbd,bbd: bbd
                                },
                        
                        success: function (data) {
                            $body.removeClass("loading");
                            $('#tb_cctk tbody').html(data.data);
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
                                            data:{action: "searchdonchuacotk",page: page,ngaybatdau: searchList[0],ngayketthuc: searchList[1],maonline: searchList[2],loaidon: searchList[3],
                                        loainhan: searchList[4],manhan: searchList[5],loaidk: searchList[6],bnbd: searchList[7],bbd: searchList[8]},
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
                var thongkeHS = function(){
                    var ngaybatdau  = $('#ktc_ngaythongkebatdau').val();
                    var ngayketthuc = $('#ktc_ngaythongkeketthuc').val();
                    $('#ktc_thongke_loading').html("<img src='./images/loading.gif'>");
                    $.ajax({
                        url : "thuphiservlet",
                        type : "GET",
                        data:{action: "loadthongtinhoso",ngaybatdau: ngaybatdau,ngayketthuc:  ngayketthuc},
                        success: function (data) {
                            $('#ktc_thongke_loading').empty();
                            $('#thongtin_thongke').html(data);
                        },
                        error: function (data) {
                            console.log('Error:', data);
                        }
                    }); 
                };
                $('#btn_kcstkSearch').on('click',searchDonCCTK);
                $('#btn_thongkehs').on('click',thongkeHS);
        </script>
    </body>
</html>
