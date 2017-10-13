<%-- 
    Document   : xemvanthu
    Created on : Jul 5, 2016, 5:15:26 PM
    Author     : Thorfinn
--%>

<%@page import="com.ttdk.bean.VanThu"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            .ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year {
                color: #1c94c4;
                font-size: 14px;
                font-weight: bold;
            }
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
            }
            th, td {
                padding: 5px;
                text-align: center;
            }
            table {
                width: 100%;
            }
            tr div {
                width: 100%;
            }
           #xvt_table td{
                background: #ffffff;
            }
            .modal {
                    display:    none;
                    position:   fixed;
                    z-index:    1000;
                    top:        0;
                    left:       0;
                    height:     100%;
                    width:      100%;
                    background: rgba( 255, 255, 255, .8 ) 
                                url(./images/loading.gif) 
                                50% 50% 
                                no-repeat;
                }/**
                 When the body has the loading class, we turn
               the scrollbar off with overflow:hidden */
            body.loading {
                overflow: hidden;   
            }

            /* Anytime the body has the loading class, our
               modal element will be visible */
            body.loading .modal {
                display: block;
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
                %>
            <li class="active"><a href="vanthu.jsp?page=themvanthu">Văn Thư</a></li>
            <%
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
        <ul id="sub-menu-2">
            <%
                if(session.getAttribute("10").equals("1")){
                %>
                <li class="sub-menu"><a  href="vanthu.jsp?page=themvanthu">Thêm Văn Thư</a></li>
                 <%
                     }
                if(session.getAttribute("11").equals("1")){
                %>
                <li class="sub-menu"><a class="active" href="vanthu.jsp?page=xemvanthu">Xem Văn Thư</a></li>
                 <%
                     }
                if(session.getAttribute("10").equals("1")){
                %>
                <li class="sub-menu"><a href="vanthu.jsp?page=themcsgt">Thêm CSGT</a></li>
                <%
                     }
                if(session.getAttribute("11").equals("1")){
                %>
                <li class="sub-menu"><a  href="vanthu.jsp?page=xemcsgt">Xem Văn Thư gửi CSGT</a></li>
                <%
                     }
                %>
        </ul><br>
        <div class="modal"></div>
        <fieldset>
            <legend class="the-legend">Lọc Dữ Liệu</legend>
                
                <div>
                    <input type="text" name="xvt_ngaynhap" id="xvt_ngaynhap" placeholder="Ngày Gởi" style="width: 90px !important; height: 30px"/>
                    <input type="text" name="xvt_mabuudien" class="repalacedot" id="xvt_mabuudien" placeholder="Mã Bưu điện" style="width: 90px !important; height: 30px"/>
                    <input type="text" name="xvt_noinhan" id="xvt_noinhan" placeholder="Nơi nhận" style="width: 90px !important; height: 30px"/>
                    <input type="text" name="xvt_sobienlai"  id="xvt_sobienlai" placeholder="Số biên lai" style="width: 90px !important; height: 30px"/>
                     <button type="button" id="btn_search" class="btn btn-default">Tìm Kiếm</button>
                     <button type="button" id="btn_searchMore" class="btn btn-default">Tìm Kiếm Nâng Cao</button>
                     <button type="button" id="btn_more"  data-toggle='collapse' data-target='#search_more' class="btn btn-default accordion-toggle" >Nâng cao</button>
                </div><br>
                <div class='accordian-body collapse' id='search_more'>
                    <select id="xvt_loaidon"  style="width: 150px !important;height: 30px">
                        <option value="0">Tất cả loại đăng ký</option>
                        <option value="1">BD</option>
                        <option value="2">TT</option>
                        <option value="3">CST</option>
                    </select>
                    <input type="text" class="repalacedot" name="xvt_dononline" id="xvt_dononline" placeholder="Số đơn online" style="width: 90px !important; height: 30px" />
                    <select name="xvt_hinhnhan" id="xvt_hinhnhan" style="width: 105px !important; height: 30px">
                            <option value="0">Tất cả loại hình nhận</option>
                            <option value="1">CE</option>
                            <option value="2">CF</option>
                            <option value="3">CT</option>
                            <option value="4">CB</option>
                    </select>
                    <input type="text" class="repalacedot" name="xvt_maloainhan" id="xvt_maloainhan" placeholder="Mã loại hình nhận"/>
                    <select id="xvt_loaidk">
                        <option value="0">Loại đăng ký</option>
                        <option value="1">LĐ</option><option value="2">TĐ</option>
                        <option value="3">Xóa</option><option value="4">VB-XL-TS</option>
                        <option value="5">CC-TT</option>
                        <option value="6">BẢN SAO</option>
                        <option value="7">TBKBTHA</option>
                        <option value="8">MIỄN PHÍ</option>
                        <option value="9">CSGT</option>
                        <option value="10">CSGT-TĐ</option>
                        <option value="11">CSGT-X</option>
                        <option value="12">CSGT-Online</option>
                        <option value="13">CSGT-Online/TĐ</option>
                    </select>
                    <input type="text" name="xvt_nhanbaodam" id="xvt_nhanbaodam" placeholder="Bên nhận bảo đảm"/>
                    <input type="text" name="xvt_benbaodam" id="xvt_benbaodam" placeholder="Bên bảo dảm"/>
                </div>
                
                
               
        </fieldset><br>
        <div id='div_loading'></div>
        <div class='data-show'></div>
        <div style="text-align: right"><button type="button" onclick="xoaVanthu();">Xóa</button><button type="button" onclick="suaVanthu();">Sửa</button></div>
        <table id="xvt_table">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th rowspan="2">Ngày Gởi</th>
                    <th rowspan="2">Mã Số Bưu Điện</th>
                    <th rowspan="2">Nơi Nhận</th>
                    <th rowspan="2">Số Biên Lai</th>
                    <th rowspan="2">Bên Nhận Bảo đảm</th>
                    <th rowspan="2">Ghi Chú</th>
                    <th rowspan="2">Chọn</th>
                </tr>
            </thead>
            <tbody>
                </tbody>
        </table>
         <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
    </body>
    <script>
            $body = $("body");
               $(document).on({
                    ajaxStart: function() { $body.addClass("loading");    },
                     ajaxStop: function() { $body.removeClass("loading"); }    
                });
            $('#btn_searchMore').hide();
                $( "#xvt_ngaynhap" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
          /**      $('div[id^="vt_tths"]').on('shown.bs.collapse', function () {
                     
                     
                });
                $('div[id^="vt_tths"]').on('hidden.bs.collapse', function () {
                      var id = $(this).prop('id').substring(7);
                      $('span#'+id).removeClass("glyphicon glyphicon-minus").addClass("glyphicon glyphicon-plus");
                     
                }); **/
                $(document).on('shown.bs.collapse','div[id^="vt_tths"]',function(){
                     var id = $(this).prop('id').substring(7);
                      $('span#'+id).removeClass("glyphicon glyphicon-plus").addClass("glyphicon glyphicon-minus");
                });
                $(document).on('hidden.bs.collapse','div[id^="vt_tths"]',function(){
                      var id = $(this).prop('id').substring(7);
                      $('span#'+id).removeClass("glyphicon glyphicon-minus").addClass("glyphicon glyphicon-plus");
                });
                $(document).on('click','.sobienlai',function(e){
                   console.log($(this).attr('value')) ;
                   var sbl = $(this).attr('value');
                   var position = $(this).position();
                   $('.sobienlai').show();
                   $(this).hide();
                   
                   $('div.data-show').show();
                   $('div.data-show').html(sbl).css({"position": "absolute",'top':position.top,'left':position.left,'background':'#ffffff'});
                   
                });
                var tr_Click = function(){
                    var id = $(this).find('span').prop('id');
                    $('div.data-show').hide();
                    console.log(id);
                    $.ajax({
                        type: "GET",
                        url:"vanthu",
                        data:{"action":"xemvanthu","vtid":id},
                        success: function (data) {
                            $('#xvt_table').find('div#vt_tths'+id).html(data);
                        }
                    });
                };
                
                var normal_Search = function(){
                    $('div.data-show').hide();
                   var ngaynhap = $('#xvt_ngaynhap').val();
                    var mabuudien = $('#xvt_mabuudien').val();
                    var noinhan = $('#xvt_noinhan').val();
                    var sobienlai = $('#xvt_sobienlai').val();
                    searchList.splice(0,searchList.length);
                    searchList.push(ngaynhap);searchList.push(mabuudien);searchList.push(noinhan);searchList.push(sobienlai);
                    $.ajax({
                        type: "GET",
                        url:"vanthu",
                        dataType : "json",
                        data:{"action":"svtluu_normal","page": 1,"ngaynhap":ngaynhap,"mabuudien":mabuudien,"noinhan":noinhan,"sobienlai":sobienlai},
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                          //  $('#xvt_table tbody').empty();
                            $('#xvt_table tbody').html(data.data);
                            $('#xvt_table tbody tr a span').on("click",tr_Click);
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage:  currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $('div.data-show').hide();
                                    $.ajax({
                                            type: "GET",
                                            url:"vanthu",
                                            dataType : "json",
                                            data:{"action":"svtluu_normal","page":page,"ngaynhap":searchList[0],"mabuudien":searchList[1],
                                                "noinhan":searchList[2],"sobienlai":searchList[3]},
                                            success: function (data) { 
                                               // console.log('data 1= '+data);
                                                $('#xvt_table tbody').html(data.data);
                                                $('#xvt_table tbody tr a span').on("click",tr_Click);
                                            }
                                    }); 
                                }
                            }));
                            
                        }
                    });
                };
                var more_Search = function(){
                    $('div.data-show').hide();
                     var ngaynhap = $('#xvt_ngaynhap').val();
                    var mabuudien = $('#xvt_mabuudien').val();
                    var noinhan = $('#xvt_noinhan').val();
                    var sobienlai = $('#xvt_sobienlai').val();
                    var loaidon = $('#xvt_loaidon').val();
                    var dononline = $('#xvt_dononline').val();
                    var loaihinhnhan = $('#xvt_hinhnhan').val();
                    var maloainhan = $('#xvt_maloainhan').val();
                    var bnbd = $('#xvt_nhanbaodam').val();
                    var bbd = $('#xvt_benbaodam').val();
                    var loaidk = $('#xvt_loaidk').val();
                    searchList.splice(0,searchList.length);
                    searchList.push(ngaynhap); searchList.push(mabuudien);searchList.push(noinhan);
                    searchList.push(sobienlai); searchList.push(loaidon);searchList.push(dononline);
                    searchList.push(loaihinhnhan); searchList.push(maloainhan);searchList.push(bnbd);
                    searchList.push(bbd); searchList.push(loaidk);
                    $.ajax({
                        type: "GET",
                        url:"vanthu",
                        dataType : "json",
                        data:{"action":"svtluu_more","page": 1,"ngaynhap":ngaynhap,"mabuudien":mabuudien,"noinhan":noinhan,"sobienlai":sobienlai,
                        "loaidon":loaidon,"dononline":dononline,"loaihinhnhan":loaihinhnhan,"maloainhan":maloainhan,"bnbd":bnbd,"bbd":bbd,"loaidk":loaidk},
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $('#xvt_table tbody').empty();
                            $('#xvt_table tbody').append(data.data);
                            $('#xvt_table tbody tr a span').on("click",tr_Click);
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $('div.data-show').hide();
                                    $.ajax({
                                            type: "GET",
                                            url:"vanthu",
                                            dataType : "json",
                                            data:{"action":"svtluu_more","page":page,"ngaynhap":searchList[0],"mabuudien":searchList[1],"noinhan":searchList[2],
                                                "sobienlai":searchList[3],"loaidon":searchList[4],"dononline":searchList[5],"loaihinhnhan":searchList[6],"maloainhan":searchList[7],
                                                "bnbd":searchList[8],"bbd":searchList[9],"loaidk":searchList[10]},
                                            success: function (data) { 
                                                console.log('data = '+data);
                                                //$('#xvt_table tbody').empty();
                                                $('#xvt_table tbody').html(data.data);
                                            //    $('#xvt_table tbody tr a span').on("click",tr_Click);
                                            }
                                    }); 
                                }
                            }));
                            
                        }
                    });
                };
                var more_Click =  function(){
                    $('#btn_search').toggle();
                    $('#btn_searchMore').toggle();
                };
                // phân trang  --------------------------
                var $pagination = $('#ul_page');
                var searchList = [];
                var defaultOpts = {
                    totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'Trang cuối',
                    next: 'TIếp',
                    last: 'Sau',
                    loop: false,
                    initiateStartPageClick: false
                };
                 $pagination.twbsPagination(defaultOpts);
                 $.ajax({
                       url : "vanthu",
                       type : "GET",
                       data : {
                                        "action" : "loadvtluu",page: 1
                                },
                        dataType : "json",
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $('#xvt_table tbody').html(data.data);
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $('div.data-show').hide();
                                    $('#div_loading').html("<img src='images/loading.gif'>");
                                    console.log(page);
                                   $.ajax({
                                        type: "GET",
                                        url:"vanthu",
                                        data:{"action":"loadvtluu","page":page},
                                        dataType : "json",
                                        success: function (data) {
                                            console.log('data = '+data.data);
                                           $('#xvt_table tbody').empty();
                                          $('#div_loading').empty();
                                            $('#xvt_table tbody').html(data.data);
                                         //   $('#xvt_table tbody tr a').on("click",tr_Click);
                                        }
                                    });
                                }
                            }));
                        }
                  });
                //--------------------------------------------
                $('#xvt_ngaynhap').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_searchMore').click();
                    }
                });
                $('#xvt_mabuudien').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_searchMore').click();
                    }
                });
                $('#xvt_noinhan').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_searchMore').click();
                    }
                });
                $('#xvt_sobienlai').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_searchMore').click();
                    }
                });
                $('#xvt_dononline').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_searchMore').click();
                    }
                });
                $('#xvt_maloainhan').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_searchMore').click();
                    }
                });
                $('#xvt_nhanbaodam').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_searchMore').click();
                    }
                });
                $('#xvt_benbaodam').on('keydown',function(e){
                    if(e.keyCode ==13){
                         $('#btn_searchMore').click();
                    }
                });
          //      $('#xvt_table tbody tr').on("click",tr_Click);
                var xoaDon = function(id){
                  $.ajax({
                        type: "GET",
                        url:"vanthu",
                        data:{"action":"xoadonvtluu",id: id},
                        dataType : "json",
                        success: function (data) {
                            console.log('data = '+data.data);
                           $('#xvt_table tbody').empty();
                          $('#div_loading').empty();
                            $('#xvt_table tbody').html(data.data);
                         //   $('#xvt_table tbody tr a').on("click",tr_Click);
                        }
                    });
                };
                var xoaVanthu = function(){
                    var vanthuids = [];
                    $('#xvt_table').find('input:checkbox:checked').each(function(){
                        vanthuids.push($(this).val());
                    });
                    $('#div_loading').html("<img src='images/loading.gif'>");
                    $.ajax({
                        type: "GET",
                        url:"vanthu",
                        data:{"action":"xoavanthu",vanthuids: vanthuids},
                        dataType : "json",
                        success: function (data) {
                        //    console.log('data = '+data.data);
                          $('#div_loading').empty();
                            $('#xvt_table tbody').html(data.data);
                         //   $('#xvt_table tbody tr a').on("click",tr_Click);
                        }
                    });
                };
                var suaVanthu  = function(){
                   // var check = $('input:checkbox:checked');
                   var number =  $('#xvt_table').find('input:checkbox:checked').size();
                   if(number > 1){
                       alert("Chỉ chọn 1 văn thư để sửa");
                   }else{
                       var vtid = $('#xvt_table').find('input:checkbox:checked').val();
                       $.ajax({
                            type: "GET",
                            url:"vanthu",
                            data:{"action":"suavanthu",vtid: vtid},
                            success: function (data) {
                                window.location = "./vanthu.jsp?page=themvanthu";
                            }
                        });
                   }
                };
                $(document).on('click','#xvt_table tbody tr a',tr_Click);
                $('#btn_search').on('click',normal_Search);
                $('#btn_more').on('click',more_Click);
                $('#btn_searchMore').on('click',more_Search);
                $('div.data-show').on('mouseup',function(){
                    $('.sobienlai').show();
                   $(this).hide();
                   
                });
        </script>
</html>
