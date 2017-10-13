<%-- 
    Document   : nhom
    Created on : May 10, 2017, 10:03:55 AM
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
            <div class="panel-heading">Trang Quản Lý Nhóm</div>
            <div class="panel-body">
                  <div class="row">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">Tên nhóm: </div>
                    <div class="col-sm-4"><input type="text" class="form-control" id="qln_name" ><input type="text" id="qln_nameid" value="" hidden></div>
                  </div><br>
                  <div class="row">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">Nhân viên: </div>
                    <div class="col-sm-6" id="div_nvlist"></div>
                  </div><br>
                 <!-- <div class="row">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">Mô tả: </div>
                    <div class="col-sm-4"><input type="text" class="form-control" id="qln_mota" ></div>
                  </div><br> -->
                 <div class="row">
                    <div class="col-sm-4"></div>
                    <div class="col-sm-2"><button type="button" class="btn btn-info" id="btn_luunhom">Lưu Nhóm</button></div>
                    <div class="col-sm-2"><button type="button" onclick="clearDon();return false;" class="btn btn-info" id="btn_clear">Hủy bỏ</button></div>
                  </div><br>
                <br>
                <table id="qln_nhom" class="table table-bordered">
                    <thead>
                        <th>STT</th>
                        <th>Tên nhóm</th>
                        <th>Danh sách nhân viên</th>
                        <th>Sửa</th>
                        <th>Xóa</th>
                    </thead>
                    <tbody>
                        
                    </tbody>
                </table>
            </div>
         </div>
        <script type="text/javascript" src="./js/nhom.js"></script>
    </body>
</html>
