<%-- 
    Document   : header
    Created on : Jun 28, 2016, 3:00:26 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
      /* Remove the navbar's default rounded borders and increase the bottom margin */

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
      .nav {
          color: #bce8f1;
          background-color: #31b0d5;
      }
      ul#main-menu {
                list-style-type: none;
                margin: 0;
                padding: 0;
                overflow: hidden;
                background-color: #31b0d5;
            }
            ul#sub-menu-1,ul#sub-menu-2,ul#sub-menu-3,ul#sub-menu-4,ul#sub-menu-5,ul#sub-menu-6 {
                list-style-type: none;
                margin: 0;
                padding: 0;
                overflow: hidden;
                background-color: #5bc0de;
            }
            li {
                float: left;
            }

            li a, .dropbtn {
                display: inline-block;
                color: white;
                text-align: center;
                padding: 14px 16px;
                text-decoration: none;
            }

            li a:hover, .dropdown:hover .dropbtn {
                background-color: #46b8da;
            }
            
            li.sub-menu-1 a:hover,li.sub-menu-2 a:hover,li.sub-menu-3 a:hover,li.sub-menu-4 a:hover,li.sub-menu-5 a:hover,li.sub-menu-6 a:hover {
                background-color: #bce8f1;
            }
            li.dropdown {
                display: inline-block;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #f9f9f9;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            }

            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
                text-align: left;
            }

            .dropdown-content a:hover {background-color: #f1f1f1}

            .dropdown:hover .dropdown-content {
                display: block;
            }
            .active {
                color: tomato;
            }
    </style>
    
    </head>
    <body>
         <%!
             public String returnPageSession(HttpSession checkSession,String page){
                    String returnPage = "";
                    if(checkSession.getAttribute("1").equals("1") || checkSession.getAttribute("2").equals("1")){

                    }   
                    return returnPage;
                }
            %>
            <%
            if(session == null || session.getAttribute("username")==null || session.getAttribute("role")==null){
                response.sendRedirect("index.jsp");
                return;
            }else{
                String pageContent = request.getParameter("page");
                String uri = request.getRequestURI();
                System.out.println("URI = "+uri);
                 if( uri.contains("xulydon.jsp") &&("nhapdon".equals(pageContent) || "donchuataikhoan".equals(pageContent) || "loadonline".equals(pageContent) || "danhsachno".equals(pageContent)) 
                         && (session.getAttribute("1").equals("0") && session.getAttribute("2").equals("0"))){ 
                     response.sendRedirect(session.getAttribute("defaultpage").toString());
                }else if( uri.contains("xulydon.jsp") && "phandon".equals(pageContent) && session.getAttribute("5").equals("0")){
                        response.sendRedirect(session.getAttribute("defaultpage").toString());
                }else if( uri.contains("xulydon.jsp") && "donchotra".equals(pageContent) && session.getAttribute("6").equals("0")){
                        response.sendRedirect(session.getAttribute("defaultpage").toString());
                }else if( uri.contains("xulydon.jsp") && "dondatra".equals(pageContent) && session.getAttribute("8").equals("0")){
                         response.sendRedirect(session.getAttribute("defaultpage").toString());
                }else if( uri.contains("xulydon.jsp") && "thongke".equals(pageContent) && session.getAttribute("9").equals("0")){
                         response.sendRedirect(session.getAttribute("defaultpage").toString());
                }else if( uri.contains("vanthu.jsp") && ("xemvanthu".equals(pageContent) ||"themvanthu".equals(pageContent) || "themcsgt".equals(pageContent) || "xemcsgt".equals(pageContent)) &&
                       (session.getAttribute("10").equals("0") && session.getAttribute("11").equals("0") && session.getAttribute("12").equals("0") && session.getAttribute("13").equals("0")) ){
                    response.sendRedirect(session.getAttribute("defaultpage").toString());
                }else if(uri.contains("khachhang.jsp") && ("khachhang".equals(pageContent) ||"csgt".equals(pageContent) || "nganchan".equals(pageContent)) &&
                       (session.getAttribute("14").equals("0") && session.getAttribute("15").equals("0") && session.getAttribute("16").equals("0") && session.getAttribute("17").equals("0")) ){
                    response.sendRedirect(session.getAttribute("defaultpage").toString());
                }else if(uri.contains("thongke.jsp") && ("nhapdon".equals(pageContent) ||"vanthu".equals(pageContent) || "phandon".equals(pageContent)) &&
                       (session.getAttribute("18").equals("0") && session.getAttribute("19").equals("0") && session.getAttribute("20").equals("0")) ){
                    response.sendRedirect(session.getAttribute("defaultpage").toString());
                }else if(uri.contains("thuphi.jsp") && session.getAttribute("21").equals("0") ){
                    response.sendRedirect(session.getAttribute("defaultpage").toString());
                }
            }
         %>
        <div class="jumbotron">
            <div class="container text-center">
                <h2 style="color: cornflowerblue">SỔ ĐĂNG KÝ GIAO DỊCH ĐẢM BẢO</h2>
            </div>
        </div>
        
        
                         <script>
            $("ul#1stmenu a").click(function(){
                // If this isn't already active
                
                if (!$(this).parent().hasClass("active")) {
                  // Remove the class from anything that is active
                  $("li .active").removeClass("active");
                //  console.log("CLICK");
                  // And make this active
                  $(this).parent().addClass("active");
                }
              });
    </script>
    </body>
</html>
