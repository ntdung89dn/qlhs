<%-- 
    Document   : nganchan
    Created on : Jul 4, 2016, 2:08:42 PM
    Author     : Thorfinn
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.ttdk.bean.NganChanDB"%>
<%@page import="com.ttdk.connect.DBConnect"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
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
            <li><a href="khachhang.jsp?page=khachhang">Khách Hàng</a></li>
            <li   class="active"><a href="nganchan.jsp?page=nganchan">Ngăn Chặn</a></li>
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
        <ul id="sub-menu-4">
           <li class="sub-menu"><a class="active" href="#home">Ngăn chặn</a></li>
        </ul> 
        <div class="panel panel-default">
            <div class="panel-heading">Ngăn chặn</div>
            <div class="panel-body">
                <form action="FileUploadServlet?action=addKH" method="post" enctype="multipart/form-data" acceptcharset="UTF-8" class="form-signin" >
                    <div class="form-group row">
                        <label for="nc_tenkh" class="col-sm-2 form-control-label">Tên Khách Hàng:</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="nc_tenkh" name="nc_tenkh">
                            <input type="text"  id="nc_id" name="nc_id" hidden>
                        </div>
                        <div class="col-sm-2">
                            <p class="help-block bg-warning" id="kh_err">* Nhập tên Khách Hàng</p>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="nc_soid" class="col-sm-2 form-control-label">Số ID:</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="nc_soid" name="nc_soid">
                        </div>
                        <div class="col-sm-2">
                            <p class="help-block bg-warning" id="id_err">* Nhập Số ID</p>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="nc_diachi" class="col-sm-2 form-control-label">Địa Chỉ:</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="nc_diachi" name="nc_diachi">
                        </div>
                        <div class="col-sm-2">
                            <p class="help-block bg-warning" id="dc_err">* Nhập địa chỉ</p>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="nc_lydo" class="col-sm-2 form-control-label">Lý Do Chặn:</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="nc_lydo" name="nc_lydo">
                        </div>
                        <div class="col-sm-2">
                            <p class="help-block bg-warning" id="ld_err">* Nhập lý do</p>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="nc_filenc" class="col-sm-2 form-control-label">File:</label>
                        <div class="col-sm-7">
                            <input type="file" class="form-control" id="nc_filenc" name="nc_filenc">
                        </div>
                        <div class="col-sm-2">
                            <p class="help-block bg-warning" id="f_err">* Chọn file</p>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-offset-3 col-sm-10">
                            <div class="col-sm-2">
                                <button type="submit" id="btn_saveNC" class="btn btn-secondary" style="width: 100%">Lưu Lại</button>
                            </div>
                            <div class="col-sm-2">
                                <button type="reset" class="btn btn-secondary" style="width: 100%">Hủy Bỏ</button>
                            </div>
                             <div class="col-sm-2">
                                 <h3>${requestScope["message"]}</h3>
                            </div>
                        </div>
                    </div>
                  </form>
            </div>
        </div>
        <div>        
            <br/>
            <table class="table table-bordered" id="nganchantb">
                <thead style="background-color: #87CEFA">
                    <tr>
                        <th>STT</th>
                        <th>Tên</th>
                        <th>Mã số</th>
                        <th>Địa chỉ</th>
                        <th>Lý do</th>
                        <th>File</th>
                        <th>Sửa</th>
                        <th>Xóa</th>
                    </tr>
                </thead>
                <tbody>
                    
                </tbody>
            </table>
            <div style="text-align: right">
                <ul class="pagination" id="ul_page" ></ul>
            </div>
        </div>
        <script>
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
                 $pagination.twbsPagination(defaultOpts);
                 $.ajax({
                       url : "FileUploadServlet",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "loadNC",page: 1
                                },
                        
                        success: function (data) {
                            $('#nganchantb tbody').html(data.data);
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $.ajax({
                                            url : "FileUploadServlet",
                                            type : "GET",
                                            dataType : "json",
                                            data:{action: "loadNC",page: page},
                                            success: function (data) {
                                               // $('#nganchantb tbody').empty();
                                                $('#nganchantb tbody').html(data.data);
                                            },
                                            error: function (data) {
                                                console.log('Error:', data);
                                            }
                                        }); 
                                }
                            }));
                        }
                  });
                  var updateNC = function(){
                      var name = $('#nc_tenkh').val();
                      var soid = $('#nc_soid').val();
                      var diachi = $('#nc_diachi').val();
                      var lydo = $('#nc_lydo').val();
                      
                      $.ajax({
                            url : "FileUploadServlet",
                            type : "GET",
                            dataType : "json",
                            data:{action: "updateNC",name: name,sodi: soid, diachi: diachi,lydo: lydo},
                            success: function (data) {
                              $('#nc_tenkh').val('');
                              $('#nc_soid').val('');
                              $('#nc_diachi').val('');
                              $('#nc_lydo').val('');
                              $('form').attr('action', 'FileUploadServlet?action=addKH');
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        }); 
                  };
                  var editNC = function(ncid){
                      $.ajax({
                            url : "FileUploadServlet",
                            type : "GET",
                            dataType : "json",
                            data:{action: "editNC",ncid: ncid},
                            success: function (data) {
                              $('#nc_tenkh').val(data.name);
                              $('#nc_soid').val(data.soid);
                              $('#nc_diachi').val(data.diachi);
                              $('#nc_lydo').val(data.lydo);
                              $('#nc_id').val(data.nc_id);
                              $('form').attr('action', 'FileUploadServlet?action=updateNC');
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        }); 
                  };
                  var deleteNC = function(ncid){
                      $.ajax({
                            url : "FileUploadServlet",
                            type : "GET",
                            dataType : "json",
                            data:{action: "deleteNC",ncid: ncid},
                            success: function (data) {
                              $('#nc_tenkh').val(data.name);
                              $('#nc_soid').val(data.soid);
                              $('#nc_diachi').val(data.diachi);
                              $('#nc_lydo').val(data.lydo);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        }); 
                  };
        </script>
    </body>
</html>
