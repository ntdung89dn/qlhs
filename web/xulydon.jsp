<%-- 
    Document   : home
    Created on : Jul 1, 2016, 10:51:05 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Quản Lý Hồ Sơ</title>
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
        <script src="js/jquery-ui.js"></script>
        <script src="js/jquery.dataTables.js"></script>
        <script src="js/simpleToastMessage.js"></script>
        <script src="js/jquery.twbsPagination.js"></script>
        
        <style>
            #ui-datepicker-div .ui-datepicker-month {
                color: #000
            }
            #ui-datepicker-div .ui-datepicker-year{
                color: #000
            }
            body {
                background-color: #E6E6FA
            }
        </style>
    </head>
    <body>
       
         <div style="width:80%; margin:0 auto;">
             
            <div id="header"><%@include file='header.jsp'%></div>
            <%
                String pageContent = request.getParameter("page");
                %> <div id="content"><%
                if("nhapdon".equals(pageContent)){ 
                    %><%@include file='xulydon/nhapdon.jsp'%><%
                }else if("donchuataikhoan".equals(pageContent)){
                    %><%@include file='xulydon/donchuacotaikhoan.jsp'%><%
                }else if("phandon".equals(pageContent)){
                    %><%@include file='xulydon/phandon.jsp'%><%
                }else if("donchotra".equals(pageContent)){
                    %><%@include file='xulydon/donchotra.jsp'%><%
                }else if("dondatra".equals(pageContent)){
                    %><%@include file='xulydon/dondatra.jsp'%> <%
                }else if("thongke".equals(pageContent)){
                    %><%@include file='xulydon/thongke.jsp'%><%
                }else if("danhsachno".equals(pageContent)){
                    %><%@include file='xulydon/danhsachno.jsp'%> <%
                }else if("loadonline".equals(pageContent)){
                    %><%@include file='xulydon/loaddononline.jsp'%> <%
                }
                %></div><%
            %>
            <div id="footer"><%@include file='changepass.jsp'%></div>
            <div id="footer"><%@include file='footer.jsp'%></div>
            
         </div>
        
    </body>
</html>
