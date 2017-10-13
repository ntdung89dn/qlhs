<%-- 
    Document   : admin
    Created on : May 10, 2017, 8:53:12 AM
    Author     : ntdung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="js/jquery-ui.css">
        <link rel="stylesheet" href="css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="css/simplePagination.css">
        <link href="css/simpleToastMessage.css" rel="stylesheet">
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.twbsPagination.js"></script>
        <style>
            /* navbar */
            .navbar-default {
                background-color:  #ffff80;
                border-color: #E7E7E7;
            }
        </style>
        <%
                if(session.getAttribute("useradmin") == null){
                    response.sendRedirect("login.jsp");
                }
            %>
    </head>
    <body>
        <div class="jumbotron">
            <div class="container text-center">
                <h2 style="color: cornflowerblue">SỔ ĐĂNG KÝ GIAO DỊCH ĐẢM BẢO</h2>
            </div>
        </div>
        <div style="width:1200px; margin:0 auto;">
            <nav class="navbar navbar-default">
            <div class="container-fluid">
              <div class="navbar-header">
                <a class="navbar-brand" href="#">Quản Lý</a>
              </div>
              <!--<ul class="nav navbar-nav">
                <li class="active" id="li_nhanvien"><a href="admin.jsp?page=nhanvien">Quản Lý Nhân Viên</a></li>
                <li id="li_nhom"><a href="admin.jsp?page=quanlynhom">Quản Lý Nhóm</a></li>
                <li id="li_phanquyen"><a href="admin.jsp?page=phanquyen">Phân Quyền</a></li>
                <li id="li_loaidon"><a href="admin.jsp?page=loaidon">Loại đơn</a></li>
              </ul> -->
              <ul class="nav navbar-nav">
                <li class="active" id="li_nhanvien"><a href="#nhanvien">Quản Lý Nhân Viên</a></li>
                <li id="li_nhom"><a href="#nhom">Quản Lý Nhóm</a></li>
                <li id="li_phanquyen"><a href="#phanquyen">Phân Quyền</a></li>
                <li id="li_loaidon"><a href="#loaidon">Loại đơn</a></li>
                <li id="li_logout"><a href="#logout">Thoát</a></li>
              </ul>
            </div>
          </nav>
            <div id="content"><%@include file='admin/nhanvien.jsp'%></div>
            <%-- <%
                String pageContent = request.getParameter("page");
                %> <div id="content"><%
                if("nhanvien".equals(pageContent)){
                    %><%@include file='admin/nhanvien.jsp'%><%
                }else if("quanlynhom".equals(pageContent)){
                    %><%@include file='admin/nhom.jsp'%><%
                }else if("phanquyen".equals(pageContent)){
                    %><%@include file='admin/phanquyen.jsp'%><%
                }else if("loaidon".equals(pageContent)){
                    %><%@include file='admin/loaidon.jsp'%><%
                }
                %></div><%
%> --%>
        </div>
        <script type="text/javascript" src="./js/admin.js"></script>
    </body>
</html>
