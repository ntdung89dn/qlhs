<%-- 
    Document   : khachang
    Created on : Jul 15, 2016, 9:07:53 AM
    Author     : Thorfinn
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
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/jquery.twbsPagination.js"></script>
        <style>
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
                if("khachhang".equals(pageContent)){
                    %> <div id="content"><%@include file="khachhang/khachhang.jsp"%></div><%
                }else if("csgt".equals(pageContent)){
                    %> <div id="content"><%@include file='khachhang/csgt.jsp'%></div><%
                }
            %>
            <div id="footer"><%@include file='changepass.jsp'%></div>
            <div id="footer"><%@include file='footer.jsp'%></div>
         </div>
    </body>
</html>
