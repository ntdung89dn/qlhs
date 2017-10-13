<%-- 
    Document   : thuphi
    Created on : Jul 3, 2016, 10:55:49 PM
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
        <link rel="stylesheet" href="css/jquery.dataTables.min.css">
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/jquery.dataTables.js"></script>
        <script src="js/jquery.twbsPagination.js"></script>
        <script src="js/ckeditor.js"></script>
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
            if("chuathuphi".equals(pageContent)){
                %> <div id="content"><%@include file='thuphi/chuathuphi.jsp'%></div><%
            }else if("dathuphi".equals(pageContent)){
                %> <div id="content"><%@include file='thuphi/dathuphi.jsp'%></div><%
            }else if("tkbl".equals(pageContent)){
                %> <div id="content"><%@include file='thuphi/thongkebienlai.jsp'%></div><%
            }else if("tkbc".equals(pageContent)){
                %> <div id="content"><%@include file='thuphi/thongkebaoco.jsp'%></div><%
            }else if("thcn".equals(pageContent)){
                %> <div id="content"><%@include file='thuphi/tonghopcongno.jsp'%></div><%
            }else if("xembaophi".equals(pageContent)){
                %> <div id="content"><%@include file='thuphi/xembaophi.jsp'%></div><%
            }else if("khongcostk".equals(pageContent)){
                %> <div id="content"><%@include file='thuphi/khongcosotk.jsp'%></div><%
            }
            
         %>
            <div id="footer"><%@include file='changepass.jsp'%></div>
            <div id="footer"><%@include file='footer.jsp'%></div>
         </div>
    </body>
</html>
