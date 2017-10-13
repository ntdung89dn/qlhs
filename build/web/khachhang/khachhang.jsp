<%-- 
    Document   : khachhang
    Created on : Jul 3, 2016, 10:55:23 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            table{
                margin: 0 auto;
                width: 100%;
                clear: both;
                border-collapse: collapse;
                table-layout: fixed;
                word-wrap:break-word;
              }
              td{
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
            %>
            <li  class="active"><a href="khachhang.jsp?page=khachhang">Khách Hàng</a></li>
            <li ><a href="nganchan.jsp?page=nganchan">Ngăn Chặn</a></li>
            <%
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
        <ul id="sub-menu-3">
            <li class="sub-menu"><a class="active" href="khachhang.jsp?page=khachhang">Khách hàng</a></li>
            <li class="sub-menu"><a href="khachhang.jsp?page=csgt">Công an</a></li>
        </ul><br>
        <%
            if(session.getAttribute("16").equals("1")){
            %>
        <div class="form-group">
            <fieldset>
                <legend class="the-legend">Thông tin khách hàng</legend>
                <div class="row">
                    <div class="col-xs-2">
                        
                    </div>
                    <div class="col-xs-2">
                        Tên Khách hàng : 
                    </div>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="kh_name" value="" style="width: 100%"/>
                    </div>
                    <div class="col-xs-2">

                    </div>
                </div><br>
                <div class="row">
                    <div class="col-xs-2">
                        
                    </div>
                    <div class="col-xs-2">
                        Địa Chỉ : 
                    </div>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="kh_diachi" value="" style="width: 100%"/>
                    </div>
                    <div class="col-xs-2">

                    </div>
                </div><br>
                <div class="row">
                    <div class="col-xs-2">
                        
                    </div>
                    <div class="col-xs-2">
                        Email : 
                    </div>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="kh_email" value="" style="width: 100%"/>
                    </div>
                    <div class="col-xs-2">

                    </div>
                </div><br>
                
                <div class="row">
                    <div class="col-xs-2">
                        
                    </div>
                    <div class="col-xs-2">
                        Tài Khoản : 
                    </div>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="kh_taikhoan" value="" style="width: 100%"/>
                    </div>
                    <div class="col-xs-2">

                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-xs-5">
                        
                    </div>
                    <div class="col-xs-1">
                        <button type="button" class="btn btn-default" id="btn_AddKh" style="width: 100%">Lưu Lại</button>
                    </div>
                    <div class="col-xs-1">
                        <button type="reset"  class="btn btn-default" style="width: 100%" >Hủy Bỏ</button>
                    </div>
                    <div class="col-xs-5"> 

                    </div>
                </div>
            </fieldset>
        </div>
        <%
                }
            %>
        <br>
        <div id="kh_table"></div> Lọc dữ liệu
        <div class="row">
            <div class="col-xs-3">
                <input type="text" id="s_tenkhachhang"  value="" placeholder="Tên khách hàng" style="width: 100%"/>
            </div>
            <div class="col-xs-3">
                <input type="text" id="s_khdiachi"  value="" placeholder="Địa chỉ" style="width: 100%"/>
            </div>
            <div class="col-xs-3">
                <input type="text" id="s_khemail"  value="" placeholder="Email" style="width: 100%"/>
            </div>
            <div class="col-xs-3"> 
                <input type="text" id="s_khtaikhoan"  value="" placeholder="Tài khoản" style="width: 100%"/>
            </div>
        </div>
        <br>
        <table class="table table-bordered" id="tb_khloc">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th style="width: 5%;">STT</th>
                    <th  style="width: 25%;">Tên khách hàng</th>
                    <th style="width: 25%;">Địa chỉ</th>
                    <th style="width: 25%;">Email</th>
                    <th style="width: 15%;">Tài khoản</th>
                    <% if(session.getAttribute("16").equals("1")){
                        %>
                    <th style="width: 5%;">Edit</th>
                    <%                         
                    } 
                    if(session.getAttribute("17").equals("1")){
                    %>
                    <th style="width: 5%;">Xóa</th>
                    <%                         
                    }  %>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
        <!-- MODELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL --->
        <!-- Modal -->
        <div id="updateModal" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Cập nhật thông tin khách hàng</h4>
              </div>
              <div class="modal-body" id="modal-body">
                <div class="row">
                    <div class="col-xs-1">
                        
                    </div>
                    <div class="col-xs-3">
                        Tên Khách Hàng:
                    </div>
                    <div class="col-xs-7">
                        <input class="form-control" type="text" id="update_khname" value="" style="width: 100%"/>
                    </div>
                    <div class="col-xs-1"> 

                    </div>
                </div><br>
                <div class="row">
                    <div class="col-xs-1">
                        
                    </div>
                    <div class="col-xs-3">
                        Địa chỉ:
                    </div>
                    <div class="col-xs-7">
                        <input class="form-control" type="text" id="update_khdiachi" value="" style="width: 100%"/>
                    </div>
                    <div class="col-xs-1"> 

                    </div>
                </div><br>
                <div class="row">
                    <div class="col-xs-1">
                        
                    </div>
                    <div class="col-xs-3">
                        Email:
                    </div>
                    <div class="col-xs-7">
                        <input class="form-control" type="text" id="update_khemail" value="" style="width: 100%"/>
                    </div>
                    <div class="col-xs-1"> 

                    </div>
                </div><br>
                <div class="row">
                    <div class="col-xs-1">
                        
                    </div>
                    <div class="col-xs-3">
                        Tài Khoản:
                    </div>
                    <div class="col-xs-7">
                        <input class="form-control" type="text" id="update_khtk" value="" style="width: 100%"/>
                    </div>
                    <div class="col-xs-1"> 

                    </div>
                </div>
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal">Cập nhật</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
              </div>
            </div>

          </div>
        </div>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        <script>
              //  loadKH();
                
                var $pagination = $('#ul_page');
                var searchList = [];
                var defaultOpts = {
                    totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'Trang cuối',
                    next: 'TIếp',
                    last: 'Sau',
                    loop: false
                };
                 $pagination.twbsPagination(defaultOpts);
                 $.ajax({
                       url : "searchkh",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "loadkh",page: 1
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
                                    loadKH(page);
                                }
                            }));
                        }
                  });
                  var loadKH = function (page){
                       if(page === undefined){
                            page = 1;
                        }
                    $.ajax({
                            url : "searchkh",
                            type : "GET",
                            dataType : "json",
                            data:{action: "loadkh",page: page},
                            success: function (data) {
                                $('#tb_khloc tbody').empty();
                                $('#tb_khloc tbody').append(data.data);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        }); 
                };
                
                function searchKH(){
                    delay(function(){
                        var khname = $("#s_tenkhachhang").val();
                        var khaddress = $("#s_khdiachi").val();
                        var khemail = $("#s_khemail").val();
                        var khaccount = $("#s_khtaikhoan").val();
                        $.ajax({
                                url : "searchkh",
                                type : "GET",
                                data:{action: "searchkh",name: khname, address: khaddress, email: khemail, account: khaccount},
                                success: function (data) {
                                    $('#tb_khloc tbody').empty();
                                    $('#tb_khloc tbody').append(data);
                                },
                                error: function (data) {
                                    console.log('Error:', data);
                                }
                            });
                      }, 500 );
                    
                };
                var delay = (function(){
                    var timer = 0;
                    return function(callback, ms){
                      clearTimeout (timer);
                      timer = setTimeout(callback, ms);
                    };
                  })();
                  
                  function addKhachHang(){
                      var name = $('#kh_name').val();
                      var address = $('#kh_diachi').val();
                      var email = $('#kh_email').val();
                      var account = $('#kh_taikhoan').val();
                      $.ajax({
                            url : "searchkh",
                            type : "GET",
                            data:{action: "addkh",name: name, address: address, email: email, account: account},
                            success: function (data) {
                                loadKH(1);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        });
                  };
                  
                  function lockKH(khid,isLock){
                      console.log(isLock);
                      $.ajax({
                            url : "searchkh",
                            type : "GET",
                            data:{action: "lockkh",khid: khid,typelock: isLock},
                            success: function (data) {
                                loadKH(1);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        });
                  };
                  function addKhachHang(){
                      var name = $('#kh_name').val();
                      var address = $('#kh_diachi').val();
                      var email = $('#kh_email').val();
                      var account = $('#kh_taikhoan').val();
                      $.ajax({
                            url : "searchkh",
                            type : "GET",
                            data:{action: "updatekh",name: name, address: address, email: email, account: account},
                            success: function (data) {
                                loadKH(1);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        });
                  };
                  var updateKH = function(khid){
                      var name = $('#kh_name').val();
                      var diachi  =  $('#kh_diachi').val();
                      var email =  $('#kh_email').val();
                      var taikhoan =  $('#kh_taikhoan').val();
                      $.ajax({
                            url : "searchkh",
                            type : "GET",
                            dataType: 'json',
                            data:{action: "updatekh",name: name,diachi: diachi, email: email, taikhoan: taikhoan,khid: khid},
                            success: function (data) {
                                $('#kh_name').val('');
                                $('#kh_diachi').val('');
                                $('#kh_email').val('');
                                $('#kh_taikhoan').val('');
                                $('#btn_AddKh').unbind('click');
                                $('#btn_AddKh').on('click',addKhachHang);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        });
                  };
                  var editKH = function(khid){
                      $.ajax({
                            url : "searchkh",
                            type : "GET",
                            dataType: 'json',
                            data:{action: "editkh",khid: khid},
                            success: function (data) {
                                $('#kh_name').val(data.name);
                                $('#kh_diachi').val(data.diachi);
                                $('#kh_email').val(data.email);
                                $('#kh_taikhoan').val(data.taikhoan);
                                $('#btn_AddKh').unbind('click');
                                $('#btn_AddKh').on('click',updateKH(khid));
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        });
                  };
                  
                $("#s_tenkhachhang").on("keyup paste",searchKH);
                $("#s_khdiachi").on("keyup",searchKH);
                $("#s_khemail").on("keyup",searchKH);
                $("#s_khtaikhoan").on("keyup",searchKH);
                $('#btn_AddKh').on('click',addKhachHang);
                $('#btn_Lock').on('click',lockKH);

        </script>
    </body>
</html>
