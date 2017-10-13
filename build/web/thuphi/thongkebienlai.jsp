<%-- 
    Document   : thongkebienlai
    Created on : Jul 5, 2016, 5:20:01 PM
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
                .vertical-alignment-helper {
                    display:table;
                    height: 100%;
                    width: 100%;
                }
                .vertical-align-center {
                    /* To center vertically */
                    display: table-cell;
                    vertical-align: middle;
                }
                .modal-content {
                    /* Bootstrap sets the size of the modal in the modal-dialog class, we need to inherit it */
                    width:inherit;
                    height:inherit;
                    /* To center horizontally */
                    margin: 0 auto;
                }
            #tb_viewchitiet {
                border-collapse: collapse;
                width: 100%;
            }

            .viewchitiet {
                text-align: left;
                padding: 8px;
            }

            tr:nth-child(even){background-color: #f2f2f2}

            #tb_viewchitiet th {
                background-color: #4CAF50;
                color: white;
            }
           #tb_thongkebienlai td{
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
                <li class="sub-menu"><a class="active" href="thuphi.jsp?page=tkbl">Thống kê biên lai</a></li>
                <li class="sub-menu" ><a href="thuphi.jsp?page=tkbc">Thống kê báo có</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=thcn">Tổng hợp công nợ</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=xembaophi">Xem thông báo phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=khongcostk">Đơn chưa thu phí không có số tài khoản</a></li>
        </ul> <br>
        <table class="table" >
                <tbody>
                    <tr>
                        <td>Lọc Dữ Liệu</td>
                        <td><input class="form-control" type="text" value="" id="tkbl_ngaybatdau" placeholder="Ngày bắt đầu"></td>
                        <td><input class="form-control" type="text" value="" id="tkbl_ngaykethuc" placeholder="Ngày kết thúc"></td>
                        <td>
                            <input class="form-control" type="text" value="" id="tkbl_sobienlai" placeholder="Số biên lai">
                        </td>
                        <td>
                            <select class="form-control" id="tkbl_loaithanhtoan">
                                <option value="0">Loại Thanh Toán</option>
                                <option value="1">Trực tiếp</option>
                                <option value="2">Chuyển khoản</option>
                                <option value="3">Trực tiếp thông báo phi</option>
                                <option value="4">Chuyển khoản thông báo phí</option>
                            </select>
                        </td>
                        <td><input class="btn btn-default" type="button" value="Tìm kiếm" id="btn_Search"></td>
                          <td><input class="btn btn-default" type="button" value="Xuất Excel" id="btn_Excel"></td>
                    </tr>
                </tbody>
            </table>
        <div class="modal"></div>
        <table id="tb_thongkebienlai" style="width: 100%" class="table-bordered">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th rowspan="2" >STT</th>
                    <th rowspan="2" >Ngày nộp</th>
                    <th rowspan="2" >Số hiệu</th>
                    <th rowspan="2" >Ngày báo có</th>
                    <th rowspan="2" >Số báo có</th>
                    <th rowspan="2" >Số tiền báo có</th>
                    <th rowspan="2" >N.Dung báo có</th>
                    <th rowspan="2" >Số TK</th>
                    <th rowspan="2" >BNBĐ</th>
                    <th rowspan="2" >Diễn giải</th>
                    <th rowspan="2" >Tổng tiền</th>
                     <th rowspan="2" >GDBĐ</th>
                     <th rowspan="2" >CCTT</th>
                      <th rowspan="2">Bản sao</th>
                      <th rowspan="2">Chi tiết</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        <!-- Modal -->
            <div class="modal fade" id="myModal" role="dialog">
                <div class="vertical-alignment-helper">
                    <div class="modal-dialog vertical-align-center">
                        <div class="modal-content ">
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Chi tiết</h4>
                          </div>
                          <div class="modal-body">
                              <table id="tb_viewchitiet" >
                                  <thead style="background-color: #87CEFA">
                                      <tr class="viewchitiet">
                                          <th class="viewchitiet">STT</th>
                                          <th>Mã nhận</th>
                                          <th>Ngày nhập</th>
                                          <th>Bên Bảo đảm</th>
                                          <th>Lệ phí</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                      
                                  </tbody>
                              </table>
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                          </div>
                        </div>
                      </div>
                </div>            
            </div>
        <script>
            $('#tkbl_ngaybatdau').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                $('#tkbl_ngaykethuc').datepicker({
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
                    startPage: 1,
                    first: 'Trang đầu',
                    prev: 'Trang cuối',
                    next: 'TIếp',
                    last: 'Sau',
                    loop: false
                };
                 $pagination.twbsPagination(defaultOpts);
                /** $.ajax({
                       url : "thuphiservlet",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "thongkebienlai",page: 1
                                },
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    console.log('PAGE = '+page);
                                    loadTkBienlai(page);
                                }
                            }));
                        }
                  });
                  function loadTkBienlai(page){
                      
                    if(page === undefined){
                        page = '1';
                    }
                    $.ajax({
                        type: "GET",
                        url:"thuphiservlet",
                        dataType : "json",
                        data:{"action":"thongkebienlai","page": page},
                        success: function (data) {
                            $('#tb_thongkebienlai tbody').empty();
                            $('#tb_thongkebienlai tbody').append(data.data);
                        }
                    });
                }; **/
                  function loadSearchTkBienlai(){
                    var ngaybatdau = $('#tkbl_ngaybatdau').datepicker().val();
                    var ngayketthuc = $('#tkbl_ngaykethuc').datepicker().val();
                    var sobienlai = $('#tkbl_sobienlai').val();
                    var loaitt = $('#tkbl_loaithanhtoan').val();
                    searchList.splice(0,searchList.length);
                    searchList.push(ngaybatdau);searchList.push(ngayketthuc);searchList.push(sobienlai);searchList.push(loaitt);
                    $.ajax({
                        type: "GET",
                        url:"thuphiservlet",
                        dataType : "json",
                        data:{"action":"searchthongkebienlai","page": 1,ngaybatdau: ngaybatdau, ngayketthuc: ngayketthuc,
                            sobienlai: sobienlai,loaitt: loaitt},
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $('#tb_thongkebienlai tbody').empty();
                            $('#tb_thongkebienlai tbody').append(data.data);
                           // console.log(searchList);
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage:currentPage ,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $.ajax({
                                            type: "GET",
                                            url:"thuphiservlet",
                                            dataType : "json",
                                            data:{"action":"searchthongkebienlai","page":page,ngaybatdau: searchList[0], ngayketthuc: searchList[1],
                    sobienlai: searchList[2],loaitt: searchList[3]},
                                            success: function (data) { 
                                                $('#tb_thongkebienlai tbody').empty();
                                                $('#tb_thongkebienlai tbody').html(data.data);
                                            }
                                    }); 
                                }
                            }));
                        }
                    });
                };
                $('#btn_Search').on('click',loadSearchTkBienlai);
                $('#tkbl_ngaybatdau').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_Search').click();
                    }
                });
                $('#tkbl_ngaykethuc').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_Search').click();
                    }
                });
                $('#tkbl_sobienlai').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_Search').click();
                    }
                });
                $('#btn_Excel').on('click',function(){
                    var ngaybatdau = $('#tkbl_ngaybatdau').datepicker().val();
                    var ngayketthuc = $('#tkbl_ngaykethuc').datepicker().val();
                    var sobienlai = $('#tkbl_sobienlai').val();
                    var loaitt = $('#tkbl_loaithanhtoan').val();
                    window.location="thuphiservlet?action=tkblexcel&ngaybatdau="+ngaybatdau+"&ngayketthuc="+ngayketthuc+"&sobienlai="+sobienlai+"&loaitt="+loaitt;
                });
            var viewChiTiet =function (sohieu){
             //   var donids = $(this).parent().find('input').val();
                $('#myModal').modal('show');
                $.ajax({
                            type: "GET",
                            url:"thuphiservlet",
                            data:{"action":"viewtkbldon",sohieu: sohieu},
                            success: function (data) { 
                               console.log(data);
                               $('#tb_viewchitiet tbody').html(data);
                            }
                    }); 
            };
            $body = $("body");
               $(document).on({
                    ajaxStart: function() { $body.addClass("loading");    },
                     ajaxStop: function() { $body.removeClass("loading"); }    
                });
        </script>
    </body>
</html>
