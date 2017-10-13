<%-- 
    Document   : csgt
    Created on : Jul 5, 2016, 5:15:43 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            tbody{
                background: #ffffff;
            }
        </style>
    </head>
    <body>        
        <ul id="1stmenu" class="nav nav-pills">
           <%
                if(session.getAttribute("1").equals("1") || session.getAttribute("2").equals("1") || session.getAttribute("3").equals("1") || session.getAttribute("4").equals("1")
                        || session.getAttribute("5").equals("1") || session.getAttribute("6").equals("1") || session.getAttribute("7").equals("1") || session.getAttribute("8").equals("1")
                        || session.getAttribute("9").equals("1")){
                %>
            <li><a href="xulydon.jsp?page=nhapdon">Xử Lý Đơn</a></li>
             <%
                 }
                if(session.getAttribute("10").equals("1") || session.getAttribute("11").equals("1") || session.getAttribute("12").equals("1") || session.getAttribute("13").equals("1")){
                %>
            <li><a href="vanthu.jsp?page=themvanthu">Văn Thư</a></li>
             <%
                 }
            %>
            <li  class="active"><a href="khachhang.jsp?page=khachhang">Khách Hàng</a></li>
            <li ><a href="nganchan.jsp?page=nganchan">Ngăn Chặn</a></li>
            <%
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
        <ul id="sub-menu-3">
            <li class="sub-menu"><a  href="khachhang.jsp?page=khachhang">Khách hàng</a></li>
            <li class="sub-menu"><a class="active" href="khachhang.jsp?page=csgt">Công an</a></li>
        </ul> 
        <div class="panel panel-default">
            <div class="panel-heading">Địa chỉ CSGT/Sở/Chi cục gửi phụ lục 05</div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-xs-2">
                        
                    </div>
                    <div class="col-xs-2">
                        Tên CSGT/Sở/Chi Cục : 
                    </div>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="csgt_name" value="" style="width: 100%">
                        <input type="text"  id="csgt_nameid" value="" hidden>
                    </div>
                    <div class="col-xs-2">
                            <div class="form-group">
                                <select class="form-control" id="select_city">
                                </select>
                            </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-2">
                        
                    </div>
                    <div class="col-xs-2">
                        Địa Chỉ : 
                    </div>
                    <div class="col-xs-6">
                        <table id="tb_diachi" style="width: 100%">
                            <tbody>
                            <tr><td style="width: 80%"><input type="text" class="form-control" id="csgt_diachi" value="" style="width: 100%"></td>
                                <td><input type="text"  id="csgt_dcid" value="" style="width: 100%" hidden=""></td></tr>
                            <tr><td><button type="button" class="btn btn-default" id="btn_addcsgt"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button></td><td></td></tr>
                            </tbody>
                        </table>
                        
                        
                    </div>
                    <div class="col-xs-2">

                    </div>
                </div><br>
                 <div class="row">
                    <div class="col-xs-2">
                        
                    </div>
                    <div class="col-xs-2">
                        Biển số : 
                    </div>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="csgt_bienso" value="" style="width: 100%"/>
                        <input type="text" id="csgt_biensoid" value="" hidden>
                    </div>
                    <div class="col-xs-2">

                    </div>
                </div><br>
                <div class="row">
                    <div class="col-xs-5">
                        
                    </div>
                    <div class="col-xs-1">
                        <button type="button" class="btn btn-default" id="btn_AddCSGT" style="width: 100%">Lưu Lại</button>
                    </div>
                    <div class="col-xs-1">
                        <button type="reset"  class="btn btn-default" style="width: 100%" >Hủy Bỏ</button>
                    </div>
                    <div class="col-xs-5"> 

                    </div>
                </div>
            </div>
         </div>
        <div>
            <div id="kh_table"> Lọc dữ liệu</div>
        <div class="row">
            <div class="col-xs-3">
                <select class="form-control" id="searchs_city">
                 </select>
            </div>
            <div class="col-xs-3">
                <input type="text" id="s_csgtname" class="form-control" value="" placeholder="Tên CSGT/Sở/Chi cục" style="width: 100%"/>
            </div>
            <div class="col-xs-3">
                <input type="text" id="s_csgtdiachi" class="form-control" value="" placeholder="Địa chỉ" style="width: 100%"/>
            </div>

        </div>
            <br>
            <table class="table table-bordered" id="tb_csgt">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th style="width: 5%;">STT</th>
                    <th  style="width: 35%;">Tên CSGT/Sở/Chi Cục</th>
                    <th style="width: 40%;">Địa chỉ</th>
                    <th style="width: 9%;">Biển số</th>
                    <th style="width: 3%;">Sửa</th>
                    <th style="width: 3%;">Xóa</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
        </div>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        <script>
            var $pagination = $('#ul_page');
                var searchList = [];
                var defaultOpts = {
                    totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'Trang cuối',
                    next: 'TIếp',
                    last: 'Sau',
                    loop: false
                };
                 $pagination.twbsPagination(defaultOpts);
                 $.ajax({
                       url : "CSGTServlet",
                       type : "GET",
                       dataType : "json",
                       data : {
                                        "action" : "loadcsgt",page: 1
                                },
                        
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                  //  console.log('PAGE = '+page);
                                    loadCSGT(page);
                                   
                                }
                            }));
                        }
                  });
                  var loadCSGT = function (page){
                       if(page === undefined){
                            page = 1;
                        }
                    $.ajax({
                            url : "CSGTServlet",
                            type : "GET",
                            dataType : "json",
                            data:{action: "loadcsgt",page: page},
                            success: function (data) {
                            //    $('#tb_khloc tbody').empty();
                                $('#tb_csgt tbody').html(data.data);
                                 $('#select_city').html(data.city);
                                 $('#searchs_city').html(data.city);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        }); 
                };
                
                var addInputDC = function(){
                    var rowCount = $('input[id^="csgt_diachi"]').length;
                     
                     var divBtn = '<tr ><td style="width: 80%" ><input type="text" class="form-control csgt_diachi" id="csgt_diachi'+rowCount+'" value="" ></td>'+
                                            '<td><button type="button" class="btn btn-default subRow"><span class="glyphicon glyphicon-minus" aria-hidden="true"></td></tr>';
                    $(this).parent().parent().before(divBtn);
                };
                $("#tb_diachi").on('click', '.subRow', function () {
                    $(this).closest('tr').remove();
                });
                
                var addCSGT = function(){
                    var cityid = $('#select_city').val();
                    var name = $('#csgt_name').val();
                    var csgtdc = [];
                    $('input[id^="csgt_diachi"]').each(function(){
                       csgtdc.push($(this).val()) ;
                    });
                    var bienso = $('#csgt_bienso').val();
                    $.ajax({
                            url : "CSGTServlet",
                            type : "GET",
                            dataType : "json",
                            data:{action: "addcsgt",cityid: cityid,name: name,diachi: csgtdc,bienso: bienso},
                            success: function (data) {
                               // $('#tb_csgt tbody').append(data.data);
                               loadCSGT(1);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        });
                };
                $('#btn_AddCSGT').on('click',addCSGT);
                $('#btn_addcsgt').on('click',addInputDC);
                $('#csgt_bienso').on('change paste', function(){
                   $(this).val($(this).val().replace(/[- . ;]/g,","));
                });
                $('#csgt_name').on('change paste', function(){
                        var csgtname = $(this).val();
                        $('#select_city option').each(function(){
                            var opVal = $(this).text();
                            if( opVal === "TP Hồ Chí Minh"){
                                opVal = "Hồ Chí Minh";
                            }
                           if(csgtname.toLowerCase().indexOf(opVal.toLowerCase()) >= 0){
                               $('#select_city').val($(this).val());
                           }
                        });
                    
                });
                
                function editCSGT(id){
                    $('.csgt_diachi').parent().parent().remove();
                    $.ajax({
                            url : "CSGTServlet",
                            type : "GET",
                            dataType : "json",
                            data:{action: "loadcsgtname",nameid: id},
                            success: function (data) {
                               // console.log(data);
                                $('#select_city').val(data.cityid);
                                $('#csgt_name').val(data.name);
                                $('#csgt_bienso').val(data.bienso);
                                $('#csgt_biensoid').val(data.bsid);
                                $('#csgt_dcid').val(data.diachiid);
                                $('#csgt_nameid').val(data.nameid);
                                var diachi = data.diachi;
                                for(var i=0; i< diachi.length;i++){
                                    if(i===0){
                                        $('#csgt_diachi').val(diachi[i]);
                                    }else{
                                        var divBtn = '<tr ><td style="width: 80%" ><input type="text" class="form-control csgt_diachi" id="csgt_diachi'+i+'" value="" ></td>'+
                                                               '<td><button type="button" class="btn btn-default subRow"><span class="glyphicon glyphicon-minus" aria-hidden="true"></td></tr>';
                                       $('#tb_diachi tr:last-child').before(divBtn);
                                       
                                        $('#csgt_diachi'+i).val(diachi[i]);
                                        $('#btn_AddCSGT').unbind('click');
                                        $('#btn_AddCSGT').on('click',saveEdit);
                                    }
                                  
                                }
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        });
                };
                
                var saveEdit = function(){
                    var cityid = $('#select_city').val();
                    var name = $('#csgt_name').val();
                    var nameid = $('#csgt_nameid').val();
                    var csgtdc = [];
                    $('input[id^="csgt_diachi"]').each(function(){
                       csgtdc.push($(this).val()) ;
                    });
                    var csgtdcid = $('#csgt_dcid').val();
                    var bienso = $('#csgt_bienso').val();
                    var bsid =  $('#csgt_biensoid').val();
                    $.ajax({
                            url : "CSGTServlet",
                            type : "GET",
                            data:{action: "updatecsgt",cityid: cityid,name: name,diachi: csgtdc,bienso: bienso,
                                dcid: csgtdcid,nameid: nameid,bsid: bsid},
                            success: function (data) {
                               // $('#tb_csgt tbody').append(data.data);
                               $('input').val('');
                               loadCSGT(1);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        });
                     $('#btn_AddCSGT').unbind('click');
                     $('#btn_AddCSGT').on('click',addCSGT);
                };
                
                var delCSGT = function(id){
                     $.ajax({
                            url : "CSGTServlet",
                            type : "GET",
                            data:{action: "delnamecsgt",nameid: id},
                            success: function (data) {
                               // $('#tb_csgt tbody').append(data.data);
                               $('input').val('');
                               loadCSGT(1);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        });
                };
                
                var searchCSGT = function(){
                    
                };
        </script>
    </body>
</html>
