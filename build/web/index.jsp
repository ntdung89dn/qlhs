 <%-- 
    Document   : login
    Created on : Jul 1, 2016, 10:13:46 PM
    Author     : Thorfinn
--%>

<%@page import="com.ttdk.bean.ErrorBean"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.ttdk.connect.DBConnect"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
       <style>
          /* Remove the jumbotron's default bottom margin */
           .jumbotron {
            margin-bottom: 0;
            background-color: transparent !important 
          }

          /* Add a gray background color and some padding to the footer */
          footer {
            background-color: #f2f2f2;
            padding: 25px;
          }
          #body{
            background-color: #31b0d5
         }
            legend {
                margin-left: 1em;
                color: #000000;
                font-weight: bold;
            }
  
        </style>
      </head>
      <body>
          <%
            if(session.getAttribute("username")!=null & session.getAttribute("role")!=null){
                response.sendRedirect("xulydon.jsp?page=nhapdon");
            }  
          %>
        <div style="width:1200px; margin:0 auto;">
            <div class="jumbotron">
                <div class="container text-center">
                    <h2 style="color: cornflowerblue">SỔ ĐĂNG KÝ GIAO DỊCH ĐẢM BẢO</h2>
                </div>
            </div>
            <div id="content"><%@include file='indexContent.jsp'%></div>
          <div id="footer"><%@include file='footer.jsp'%></div>
        </div>
    </body>
</html>
