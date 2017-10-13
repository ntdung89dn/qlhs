<%-- 
    Document   : nhanvien
    Created on : May 10, 2017, 8:52:36 AM
    Author     : ntdung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="panel panel-default">
            <div class="panel-heading">Trang Quản Lý Nhân Viên</div>
            <div class="panel-body">
                <div></div>
                 <div class="row">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">Họ và tên</div>
                    <div class="col-sm-4"><input type="text" class="form-control" id="qlnv_fullname" ></div>
                  </div><br>
                <div class="row">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">Tên đăng nhập</div>
                    <div class="col-sm-4"><input type="text" class="form-control" id="qlnv_username"></div>
                  </div><br>
                <div class="row">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">Mật khẩu: </div>
                    <div class="col-sm-4"><input type="password" class="form-control" id="qlnv_password"></div>
                  </div><br>
                <div class="row">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">Mô tả: </div>
                    <div class="col-sm-4"><input type="text" class="form-control" id="qlnv_mota"></div>
                  </div><br>
                  <div class="row">
                    <div class="col-sm-4"></div>
                    <div class="col-sm-2"><button type="button" class="btn btn-info" id="btn_luunv">Lưu Nhân Viên</button></div>
                    <div class="col-sm-2"><button type="button" onclick="clearDon();return false;" class="btn btn-info" id="btn_clear">Hủy bỏ</button></div>
                  </div><br>
                <br>
                <table id="qlnv_nhanvien" class="table table-bordered">
                    <thead>
                        <th>STT</th>
                        <th>Họ và Tên</th>
                        <th>Tên đăng nhập</th>
                        <th>Mô tả</th>
                        <th>Sửa</th>
                        <th>Xóa</th>
                    </thead>
                    <tbody>
                        
                    </tbody>
                </table>
                <div style="text-align: right">
                    <ul class="pagination" id="ul_page" ></ul>
                </div>
            </div>
         </div>
        
        <script type="text/javascript" src="./js/nhanvien.js"></script>
    </body>
</html>
