<%-- 
    Document   : loaddononline
    Created on : May 9, 2017, 9:53:06 AM
    Author     : ntdung
--%>

<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.Socket"%>
<%@page import="com.ttdk.bean.LoadDonMongoDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
            }
            th, td {
                padding: 5px;
                text-align: center;
                
            }
            th{
                height: 10px;
                padding: 0;
                background-color: #87CEFA;
            }
            td{
                background: #ffffff;
            }
        </style>
    </head>
    <body>
        <ul id="1stmenu" class="nav nav-pills">
            <li class="active"><a href="xulydon.jsp?page=nhapdon">Xử Lý Đơn</a></li>
            <%
                if(session.getAttribute("10").equals("1") || session.getAttribute("11").equals("1") || session.getAttribute("12").equals("1") || session.getAttribute("13").equals("1")){
                %>
            <li><a href="vanthu.jsp?page=themvanthu">Văn Thư</a></li>
             <%
                 }
                if(session.getAttribute("14").equals("1") || session.getAttribute("15").equals("1") || session.getAttribute("16").equals("1") || session.getAttribute("17").equals("1")){
                %>
            <li><a href="khachhang.jsp?page=khachhang">Khách Hàng</a></li>
            <li><a href="nganchan.jsp?page=nganchan">Ngăn Chặn</a></li>
            <%
                 }
                if(session.getAttribute("18").equals("1") || session.getAttribute("19").equals("1") || session.getAttribute("20").equals("1")){
                %>
            <li><a href="thongke.jsp?page=nhapdon">Thống kê</a></li>
             <%
                 }
                if(session.getAttribute("21").equals("1")){
                %>
            <li><a href="thuphi.jsp?page=chuathuphi">Thu Phí Lệ Phí</a></li>
             <%
                 }
             //   if(session.getAttribute("21").equals("1")){
                %>
            
            <!--    <li class="dropdown pull-right"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Cài Đặt<span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#">Đổi Mật Khẩu</a></li>
                    <li><a href="#">Thông tin cá nhân</a></li>
                </ul>
            </li> -->
            <li  class="pull-right"><a id="btn_changepass" >Đổi mật khẩu</a></li>
            <li  class="pull-right"><a id="logout" href="logout"><span class="glyphicon glyphicon-log-in"></span> Đăng Xuất</a></li>
            <li class="pull-right"><a href="#"><span class="glyphicon glyphicon-user"></span><%= session.getAttribute("fullname").toString() %><input type="hidden" id="username" name="username" value="<%=session.getAttribute("username")%>" /></a></li>
            

        </ul>
        <ul id="sub-menu-1">
                <%
                if(session.getAttribute("1").equals("1") && session.getAttribute("2").equals("1")){
                %>
                <li class="sub-menu"><a href="xulydon.jsp?page=nhapdon">Nhập đơn</a></li>
                <%
                    }

                    if(session.getAttribute("5").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=phandon">Phân đơn</a></li>
                <%
                    }
                    if(session.getAttribute("6").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=donchotra">Xem đơn chờ trả</a></li>
                 <%
                    }
                     if(session.getAttribute("8").equals("1") ){
                    %>
                <li class="sub-menu"><a  href="xulydon.jsp?page=dondatra">Xem đơn đã trả</a></li>
                <%
                    }
                     if(session.getAttribute("9").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=thongke">Thống kê hiệu suất</a></li>
                <%
                    }
                     if(session.getAttribute("22").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=danhsachno">Danh sách nợ</a></li>
                <%
                    }
                     if(session.getAttribute("1").equals("1") || session.getAttribute("2").equals("1")){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=donchuataikhoan">Đơn chưa có số tài khoản</a></li>
                <%
                    }if(session.getAttribute("1").equals("1") && session.getAttribute("2").equals("1")){
                    %>
                <li class="sub-menu"><a class="active" href="xulydon.jsp?page=loadonline">Đơn online</a></li>
                <%
                    }
                String username = (String)request.getAttribute("username");
                String password = (String)request.getAttribute("passwd");
                    %>
        </ul> <br>
        Lấy dữ liệu từ cục
        <form method="POST" action="sendSocket">
        <div class="row">
            <div class="col-sm-2"><input type="text" class="form-control" id="on_username" name="username" <% if(username != null){ %> value="<%=username%>" <% }else{ %> value="" <% } %>  placeholder="Tên đăng nhập vào cục" autocomplete="off" required=""></div>
            <div class="col-sm-2"><input type="password" class="form-control" id="on_password" name="password"<%  if(password != null){ %> value="<%=password%>" <% }else{ %> value="" <% } %> placeholder="Mật khẩu" autocomplete="off" required=""></div>
            <div class="col-sm-2"><button type="submit" class="btn btn-default">Lấy dữ liệu từ cục</button></div>
            <div class="col-sm-2"><button type="button" class="btn btn-default" id="btn_reloadMG">Lấy dữ liệu có sẵn</button> </div>
        </div><br>
        </form>
         <div class="row">
            <div class="col-sm-1">Lọc dữ liệu</div>
            <div class="col-sm-2"><input type="text" class="form-control" id="on_maonline"   placeholder="Mã đơn online" ></div>
            <div class="col-sm-2">
                <select class="form-control" id="select_locdon">
                    <option value="0">Đơn chưa nhập</option>
                    <option value="1">Đơn đã nhập</option>
                </select>
            </div>
            <div class="col-sm-2">
                <select class="form-control" id="select_locphuluc">
                    <option value="0">Đơn chưa nhập Phụ Lục</option>
                    <option value="1">Đơn đã nhập Phụ Lục</option>
                </select>
            </div>
        </div>
            <br>
        <% 
             
            //  out.println(username);
             String table = "";
             String resultSend = "0";
             if(username != null && password != null ){
                 try{
                        int character;
                        Socket socket = new Socket("127.0.0.1", 8765);
                      //  BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        InputStream inSocket = socket.getInputStream();
                        OutputStream outSocket = socket.getOutputStream();

                        String str = username+"&"+password+"\n";
                        System.out.println("VALUE = "+str);
                        byte buffer[] = str.getBytes();
                        outSocket.write(buffer);
                         
                        while ((character = inSocket.read()) != -1) {
                            resultSend = String.valueOf((char) character);
                            if(resultSend.equals("1")) break;
                            
                        //    out.print((char) character);
                        }

                        socket.close();
                        System.out.println("LAST RESULT = "+resultSend);
                        if(resultSend.equals("1")){
                                LoadDonMongoDB ldmg =  new LoadDonMongoDB();
                                table = ldmg.loadCollectionMG();
                              //  out.print(table);
                                request.removeAttribute("username");
                                request.removeAttribute("password");
                            }
                    }
                    catch(java.net.ConnectException e){
                    %>
                     Bạn Cần phải chạy file Server trước
                    <%
                    }
            }
                    %>
                    <div id="div_loading"></div>
        <table class="table table-hover table-striped" id="tb_loadonline">
            <thead>
                <tr>
                        <th rowspan="2" align="center" >STT</th>
                        <th rowspan="2" align="center" >Thời điểm nhập</th>
                        <th colspan="2" align="center" >Số Đơn Do Online Cấp</th>
                        <th rowspan="2" align="center" >Loại Đơn</th>
                        <th rowspan="2" align="center" >Bên Nhận Bảo Đảm</th>
                        <th rowspan="2" align="center" >Bên Nhận Bảo Đảm(TT Phí)</th>
                        <th rowspan="2" align="center" >Bên Bảo Đảm</th>
                        <th rowspan="2" align="center">Phụ Lục</th>
                        <th rowspan="2" align="center" >Nhập</th>
                        
                </tr>
                <tr style="width: 100%;">
                        <th class="column2" align="center">Số Đơn Online</th>
                        <th class="column2" align="center">Số Pin</th>
                </tr>
            </thead>
            <tbody>
                <%= table %>
            </tbody>
        </table>
        
        <script >
            /* 
                    * To change this license header, choose License Headers in Project Properties.
                    * To change this template file, choose Tools | Templates
                    * and open the template in the editor.
                    */
           /**        $( "#on_ngaybatdau" ).datepicker({
                       dateFormat: 'dd/mm/yy',          
                       monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                           "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                           "Tháng 10", "Tháng 11", "Tháng 12" ],
                       dayNamesMin: ["CN","2","3","4","5","6","7"],
                       changeMonth: true,
                       changeYear: true
                   });
                   $( "#on_ngayketthuc" ).datepicker({
                       dateFormat: 'dd/mm/yy',          
                       monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                           "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                           "Tháng 10", "Tháng 11", "Tháng 12" ],
                       dayNamesMin: ["CN","2","3","4","5","6","7"],
                       changeMonth: true,
                       changeYear: true
                   }); **/
                    var loadData = function(){
                        var username = $('#on_username').val();
                        var passwd = $('#on_password').val();
                        $('#tb_loadonline tbody').empty();
                       $.ajax({
                                type: "GET",
                                url:"sendSocket",
                                data:{username: username,password: passwd},
                                success: function (data) {
                                    console.log(data);
                                }
                        });
                   };

                   var checkData = function(){
                       console.log("ĐANG KIỂM TRA");
                       $('#div_loading').html("<img src='./images/loading.gif'>");
              /**         $('#tb_loadonline').find(' > tbody > tr ').each(function(){
                           
                       }); **/
                            var td = $(this).parent().parent().find('td');
                           var ngaynhap = td.eq(1).html();
                           var online = td.eq(2).html();
                               $.ajax({
                                   type: "GET",
                                   url:"LoadOnlineServlet",
                                   data:{action: "checkonline",ngaynhap: ngaynhap,online: online},
                                   success: function (data) {
                                       console.log(data);
                                       $('#div_loading').html("");
                                       if(data ==='y'){
                                           td.eq(8).html("Đơn đã nhập <br><button type='button' class='btn btn-default xoadon' >Xóa</button>");
                                       }else{
                                       //    $(this).hide();
                                            td.eq(8).html("Chưa Nhập");
                                           
                                       }
                                   },
                                   error: function (data) {
                                       console.log(data);
                                   }
                               });

                   };

                   $('#btn_loaddata').on('click',loadData);
                   $(document).ready(function() {
                       $(document).on('click', '.xoa_online',function(event){
                           $(this).parent().parent().remove();
                       });
                       $(document).on('click', '.giaychungnhan',function(event){
                           var td =  $(this).parent().parent().find('td');
                           var ngaynhap = td.eq(2).html();
                           console.log(ngaynhap) ;
                       });
                   });
                   $(document).on('change paste keyup','.input_slphuluc',function(){
                       var parent = $(this).parent();
                       var sophuluc = $(this).val();
                       console.log(sophuluc);
                       if(sophuluc > 0){
                           
                           
                           $.ajax({
                                type: "GET",
                                url:"LoadOnlineServlet",
                                data:{action: "getnumberpl"},
                                success: function (data) {
                                    parent.find('.input_socvphuluc').val(data);
                                    //parent.find("a").prop("href",linkPL);
                                },
                                error: function (data) {
                                    console.log(data);
                                }
                            });
                            $(this).parent().find('div').show();
                       }else{
                           $(this).parent().find('div').hide();
                       }
                   });
                   var reloadData= function(){
                       $('#tb_loadonline tbody').empty();
                       $('#div_loading').html("<img src='./images/loading.gif'>");
                       $.ajax({
                               type: "GET",
                               url:"LoadOnlineServlet",
                               data:{action: "reload"},
                               success: function (data) {
                                   $('#div_loading').html("");
                                   $('#tb_loadonline tbody').html(data);
                               },
                               error: function (data) {
                                   console.log(data);
                               }
                           });
                   };
                    var nhapDon = function(){
                        $('#div_loading').html("<img src='./images/loading.gif'>");
                        var maonline = $(this).parent().parent().find('td').eq(2).html();
                        var maloaidon = $(this).parent().parent().find('select.loaidon').val();
                        var sopl = $(this).parent().parent().find('input.input_slphuluc').val();
                        var socv = $(this).parent().parent().find('input.input_socvphuluc').val();
                        var soplonline = $(this).parent().parent().find('input.input_soonlinepl').val();
                        var khid = $(this).parent().parent().find('input[type=radio]:checked').val();
                        $.ajax({
                               type: "GET",
                               url:"LoadOnlineServlet",
                               data:{action: "insert",maonline : maonline,maloaidon: maloaidon,soluongpl: sopl,socvdau: socv,soplonline: soplonline,khid: khid},
                               success: function (data) {
                            //       console.log(data);
                                 //  $('#tb_loadonline tbody').html(data);
                                 var linkPrint = "./print/print_mau2017.jsp?id="+data+"&type=on";
                                 var linkPhuluc = "./print/phuluc05.jsp?id="+data+"&page=";
                             //    $(this).append("<a target='_' href='"+linkPrint+"'></a>");
                             //"./print/print_mau2017.jsp?id="+donKey+"&type=on";
                                 $('#tb_loadonline').find("tr").each(function(){
                                     var value = $(this).find("td").eq(2).html();
                                     
                                     if(value === maonline){
                                         console.log(value);
                                         $(this).find("td").eq(9).html("<a target='_blank' href='"+linkPrint+"'>In Đơn</a>");
                                         $(this).find("td").eq(8).find('div').append("<input type='text' value='"+data+"' class='barcode' hidden>");
                                     }
                                 });
                                 $('#div_loading').html("");
                               },
                               error: function (data) {
                                   console.log(data);
                               }
                           });
                    };
                    var xoaDon = function(){
                        $('#div_loading').html("<img src='./images/loading.gif'>");
                        $.ajax({
                               type: "GET",
                               url:"LoadOnlineServlet",
                               data:{action: "xoadon"},
                               success: function (data) {
                                   $('#div_loading').html("Đã xóa đơn "+data);
                                   $.ajax({
                                        type: "GET",
                                        url:"LoadOnlineServlet",
                                        data:{action: "reload"},
                                        success: function (data) {
                                            $('#div_loading').html("");
                                            $('#tb_loadonline tbody').html(data);

                                        },
                                        error: function (data) {
                                            console.log(data);
                                        }
                                    });
                               },
                               error: function (data) {
                                   console.log(data);
                               }
                           });
                    };
                    
                    var searchDon = function(){
                        var maonline = $('#on_maonline').val();
                        var loaidon = $('#select_locdon').val();
                        var phuluc = $('#select_locphuluc').val();
                        $('#div_loading').html("<img src='./images/loading.gif'>");
                        $.ajax({
                               type: "GET",
                               url:"LoadOnlineServlet",
                               data:{action: "searchdon",maonline:maonline,loaidon: loaidon,phuluc: phuluc},
                               success: function (data) {
                                   $('#div_loading').empty();
                                   console.log(data);
                                   $('#tb_loadonline tbody').html(data);
                               },
                               error: function (data) {
                                   console.log(data);
                               }
                           });
                    };
                    $(document).on('click','.link_phuluc',function(){
                        var numberPage = $(this).parent().parent().find('.input_slphuluc').val();
                        var socv = $(this).parent().find('.input_socvphuluc').val();
                        var maonline = $(this).parent().find('.input_soonlinepl').val();
                        var barcode  = $(this).parent().find('.barcode').val();
                        var loaipt  = $(this).parent().parent().find('#loaiphuongtien').val();
                        window.open( './print/phuluc05.jsp?id='+barcode+'&page='+numberPage+'&sopl='+socv+"&maonline="+maonline+"&type="+loaipt,'_blank');
                        
                    });
                    $('#select_locdon,#select_locphuluc').on("change",searchDon);
                    $('#on_maonline').on('keyup',searchDon);
                    $(document).on('click','.kiemtra',checkData);
                    $(document).on('click','.xoadon',xoaDon);
                    $(document).on('click','.nhapdon',nhapDon);
                   //$('.kiemtra').on('click',checkData);
                   $('#btn_reloadMG').on('click',reloadData);
                   //$('.giaychungnhan').on('click',loadDonPrint);
        </script>
    </body>
</html>
