<%-- 
    Document   : phanquyen
    Created on : May 10, 2017, 10:04:07 AM
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
            <div class="panel-heading">Trang Quản Lý Phân Quyền</div>
            <div class="panel-body">
                  <div class="row">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2">Chọn nhóm: </div>
                    <div class="col-sm-4"><select class="form-control" id="qlpq_nhom"></select></div>
                    <div class="col-sm-2"></div>
                    <div class="col-sm-2"><a href="javascript:void(0)" style="font-weight: bold;font-size: 15px;" onclick="luuPhanQuyen();"><img src="./images/disk_blue.png">Lưu lại</a></div>
                  </div><br>
                <br>
                <table id="tbqlpq_phanquyen" class="table table-bordered table-striped">
                    <thead>
                        <th>STT</th>
                        <th>Quyền</th>
                        <th>Mô tả</th>
                        <th>Chọn</th>
                    </thead>
                    <tbody>
                        
                    </tbody>
                </table>
            </div>
         </div>
        <script type="text/javascript" src="./js/phanquyen.js"></script>
    </body>
</html>
