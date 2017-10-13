<%-- 
    Document   : loginadmin
    Created on : May 10, 2017, 8:43:40 AM
    Author     : ntdung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="wrapper">
	<div class="container">
		<h1>Trung tâm đăng ký</h1>
		
		<form class="form">
			<input type="text" id="username" placeholder="Tên đăng nhập">
			<input type="password" id="passwd" placeholder="Mật khẩu">
			<button type="submit" id="login-button">Đăng nhập</button>
		</form>
	</div>
	
	<ul class="bg-bubbles">
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
	</ul>
</div>
        <script >
              $("#login-button").click(function(event){
                    event.preventDefault();

                  //  $('form').fadeOut(500);
                    var user=$('#username').val();
                   var pwd=$('#passwd').val();
                   if(user === '' || pwd ===''){
                       alert("LOGIN FAILED");
                       return false;
                   }else{
                       $.ajax({
                           type: "POST",
                           url:"AdminServlet?action=login&username="+user+"&passwd="+pwd,
                         //  data:{action: "login","username":user,"passwd":pwd},
                           success: function (data) {
                           //    console.log(data);
                              if(data==='FAILED'){
                                   $('#error').text("1232423");
                              }else if(data==='ERROR'){
                                  $('#error').text("asdadadasd");
                              }else if(data==='login'){
                                 window.location.replace("admin.jsp");
                              }
                           }
                       });
                   }

               });
         </script>
    </body>
</html>
