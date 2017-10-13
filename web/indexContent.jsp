<%-- 
    Document   : indexContent
    Created on : Jul 5, 2016, 10:43:16 PM
    Author     : Thorfinn
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
                        <div>***** Mật khẩu demo : Abc@123<span id="error" style="font-weight: bold;color: red;font-size: 15px"></span></div>
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
         <!--<fieldset>
                    <legend>Trung tâm đăng ký</legend>
                   <div class="form-group row">
                        <div class="col-xs-4">
                        </div>
                        <label for="ctp_sotien" class="col-xs-2 col-form-label">Tên đăng nhập:</label>
                        <div class="col-xs-3">
                          <input class="form-control" type="text" value="" id="username">
                        </div>
                        <div class="col-xs-4">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-4">
                        </div>
                        <label for="ctp_tienthua" class="col-xs-2 col-form-label">Mật Khẩu:</label>
                        <div class="col-xs-3">
                            <input class="form-control" type="password" value="" id="passwd">
                        </div>
                        <div class="col-xs-4">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-xs-5">
                        </div>
                        <div class="col-xs-1">
                        </div>
                        <input type="button" id="login" class="btn btn-default col-xs-1" value="Đăng nhập"/>
                        <div class="col-xs-1" id="error">
                        </div>
                        <div class="col-xs-4">
                        </div>
                    </div>
                    
                    
                </fieldset> -->
         <script >
              $("#login-button").click(function(event){
                    event.preventDefault();

                  //  $('form').fadeOut(500);
                    var user=$('#username').val().toLowerCase();
                   var pwd=$('#passwd').val();
                   if(user === '' || pwd ===''){
                       alert("Kiểm tra lại tên đăng nhập và mật khẩu");
                       return false;
                   }else{
                       $.ajax({
                           type: "POST",
                           url:"LoginAction?action=login&username="+user+"&passwd="+pwd,
                           cache: false,
                       //    data:{action: "login","username":user,"passwd":pwd},
                           success: function (data) {
                           //    console.log(data);
                              if(data==='FAILED'){
                                   $('#error').html("Kiểm tra lại tên đăng nhập và mật khẩu");
                              }else if(data==='ERROR'){
                                  $('#error').html("Kiểm tra lại tên đăng nhập và mật khẩu");
                              }else{
                                 window.location.replace(data);
                                // window.location.replace("xulydon/nhapdon");
                              }
                           }
                       });
                   }

               });
         </script>
    </body>
</html>
