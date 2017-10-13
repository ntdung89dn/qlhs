<%-- 
    Document   : footer
    Created on : Jun 28, 2016, 3:00:35 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
   
        <footer class="container-fluid text-center">
            <p>Trung tâm đăng ký 3</p>
        </footer>
        <script src="js/replacedot.js">
        <script>
            $('#logout').on('click',function(){
                $.ajax({
                    type: "POST",
                    url:"LoginAction",
                    data:{action: "logout"},
                    success: function (data) {
                    }
                  });
            });
        </script>
    </body>
</html>
