<%-- 
    Document   : donchotra
    Created on : Jul 4, 2016, 8:09:06 AM
    Author     : Thorfinn
--%>

<%@page import="com.ttdk.bean.SearchDon"%>
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
            table {
                width: 100%;
            }
            
            td{
                background: #ffffff;
            }
        </style>
        <script>
            $(document).ready(function(){
             
            });
        </script>
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
                if(session.getAttribute("1").equals("1") || session.getAttribute("2").equals("1")){
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
                <li class="sub-menu"><a class="active" href="xulydon.jsp?page=donchotra">Xem đơn chờ trả</a></li>
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
                <li class="sub-menu"><a href="xulydon.jsp?page=loadonline">Đơn online</a></li>
                <%
                    }
                    %>
            </ul> 
        <br>
        <form>
            <fieldset>
                Lọc Dữ Liệu: 
                <input type="text" id="dct_online" name="dct_online" class="repalacedot" placeholder="Số đơn online" />
                <select name="dctcb_manhan" id="dctcb_manhan">
                    <option value="0">Loại đơn</option>
                    <option value="1">CE</option>
                    <option value="2">CF</option>
                    <option value="3">CT</option>
                    <option value="4">CB</option>
                </select>
                <select name="dctcb_loaidon" id="dctcb_loaidon">
                    <option value="0">Loại </option>
                    <option value="1">BD</option>
                    <option value="2">TT</option>
                    <option value="3">CSGT</option>
                </select>
                <select name="dctcb_loaidk" id="dctcb_loaidk">
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
                <input type="text" id="dct_manhan" name="dct_manhan" class="repalacedot" placeholder="Mã loại hình nhận" />
                <input type="text" id="dct_ngayduocphan" name="dct_ngayduocphan" id="dct_datepicker" placeholder="Ngày Phân Đơn"/>
               <!-- <input type="text" id="dct_duocphan" name="dct_duocphan" placeholder="Người được phân"/>  Tất cả: &nbsp;<input type="checkbox" id="cb_all" name="cb_all"/> -->
                <select id="dct_duocphan">
                    
                </select>
                
                <button id="dct_search" type="button" class="btn btn-default">Tìm Kiếm</button>
               
            </fieldset>
        </form>
        <br>
        <fieldset style="text-align: right;">
            <button type="button" class="btn btn-default" id="dct_trahetdon">Trả đơn</button>
             <button type="button" class="btn btn-default" id="dct_tickall">Chọn hết</button>
             <button type="button" class="btn btn-default" id="dct_clearall">Bỏ chọn</button>
             <!--<button type="button" class="btn btn-default" id="dct_dondadien1">Lưu đơn</button> -->
        </fieldset>
        <br>
        <div id="div_loading"></div><div id="div_result"></div>
        <table id="tb_donchotra" class="table table-striped table-bordered">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th rowspan="2">Thời Điểm Nhập</th>
                    <th colspan="2">Số Đơn Do Online Cấp</th>
                    <th rowspan="2">Loại Hình Nhận</th>
                    <th rowspan="2">Loại Đơn</th>
                    <th rowspan="2">Số Lượng Tài Sản</th>
                    <th colspan="4">Thông tin phân đơn</th>
                    <th rowspan="2">Thông tin phân đơn</th>
                     <th rowspan="2">Trả đơn</th>
                </tr>
                <tr>
                    <th>Số Đơn Online</th>
                    <th >Số Pin</th>
                    <th>Nhân Viên</th>
                    <th>Thời Điểm</th>
                    <th>SL Tài Sản</th>
                    <th>Người Phân</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
        </table><br>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="notiModal" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Hồi đơn</h4>
              </div>
              <div class="modal-body">
                <p>Bạn có muốn hồi đơn?</p>
              </div>
              <div class="modal-footer">
                  <button type="button" id="btn_tradonmodal" class="btn btn-default" data-dismiss="modal">Trả đơn</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
              </div>
            </div>

          </div>
        </div>
        <script>
            var searchList = [];
            // function here
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
         /**        $('#div_loading').html("<img src='./images/loading.gif'>");
                 $.ajax({
                       url : "TraDonServlet",
                       type : "GET",
                       data : {
                                        "action" : "loaddonchotra",page: 1
                                },
                        dataType : "json",
                        success: function (data) {
                            $('#div_loading').empty();
                            console.log(data);
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $('#tb_donchotra tbody').empty();
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    console.log('PAGE = '+page);
                                    $('#div_loading').empty();
                                    $('#tb_donchotra tbody').html(data.data);
                                    $.ajax({
                                                    type: "GET",
                                                    url:"TraDonServlet",
                                                    dataType : "json",
                                                    data:{"action":"loaddonchotra",page: page},
                                                    success: function (data) { 
                                                        $('#div_loading').empty();
                                                         $('#tb_donchotra tbody').html(data.data);
                                                    }

                                                }); 
                                    
                                }
                            }));
                        }
                  });
                  var loadTable = function(){
                      $.ajax({
                        url : "TraDonServlet",
                        type : "GET",
                        dataType : "json",
                        data : {
                                         "action" : "loaddonchotra","page":1
                                 },
                         success: function (data) {
                             $('#tb_donchotra tbody').html(data.data);
                                var currentpage = data.currentpage;
                                var totalpage = data.totalpage;
                              //  $('#ul_page').hide();
                                $pagination.twbsPagination('destroy');
                              //  loadPageSearch(currentpage);
                                $pagination.twbsPagination($.extend({}, defaultOpts, {
                                        startPage: currentpage,
                                        totalPages: totalpage,
                                        onPageClick: function (event, page) {
                                            $.ajax({
                                                    type: "GET",
                                                    url:"TraDonServlet",
                                                    dataType : "json",
                                                    data:{"action":"loaddonchotra",page: page},
                                                    success: function (data) { 
                                                        $('#div_loading').empty();
                                                         $('#tb_donchotra tbody').html(data.data);
                                                    }

                                                }); 
                                        }
                                    }));
                         }
                        });
                  }; **/
                  var inputSearch = function(){
                    var dct_ngayduocphan = $("#dct_ngayduocphan").datepicker().val();
                    var dct_online =  $("#dct_online").val();
                    var dctcb_manhan = $("#dctcb_manhan").val();
                    var dct_manhan = $("#dct_manhan").val();
                    var dct_duocphan = $("#dct_duocphan").val();
                    var dct_loaidon = $('#dctcb_loaidon').val();
                    var dct_loaidk = $('#dctcb_loaidk').val();
                    searchList.splice(0,searchList.length);
                    searchList.push(dct_ngayduocphan);searchList.push(dct_online);searchList.push(dctcb_manhan);searchList.push(dct_manhan);
                    searchList.push(dct_duocphan);searchList.push(dct_loaidon);searchList.push(dct_loaidk);
                    $('#div_loading').html("<img src='./images/loading.gif'>");
                    $.ajax({
                            type: "GET",
                            url:"SearchDonServlet",
                            dataType : "json",
                            data:{"s_page":"donchotra","daytime":dct_ngayduocphan,"dononline":dct_online,"cb_loainhan":dctcb_manhan,
                            "manhan":dct_manhan,"nvnhan":dct_duocphan,'loaidon':dct_loaidon,"loaidk":dct_loaidk,page:1},
                            success: function (data) {
                               // $('#tb_donchotra tbody').empty();
                               $('#div_loading').empty();
                                $('#tb_donchotra tbody').html(data.data);
                                var currentpage = data.currentpage;
                                var totalpage = data.totalpage;
                              //  $('#ul_page').hide();
                                $pagination.twbsPagination('destroy');
                              //  loadPageSearch(currentpage);
                                $pagination.twbsPagination($.extend({}, defaultOpts, {
                                        startPage: currentpage,
                                        totalPages: totalpage,
                                        onPageClick: function (event, page) {
                                        //    $('#div_loading').html("<img src='./images/loading.gif'>");
                                            $.ajax({
                                                    type: "GET",
                                                    url:"SearchDonServlet",
                                                    dataType : "json",
                                                    data:{"s_page":"donchotra","daytime": searchList[0],"dononline": searchList[1],"cb_loainhan": searchList[2],
                                                                "manhan": searchList[3],"nvnhan": searchList[4],'loaidon': searchList[5],"loaidk": searchList[6],page:page},
                                                    success: function (data) { 
                                                  //      $('#div_loading').empty();
                                                         $('#tb_donchotra tbody').html(data.data);
                                                    }

                                                }); 
                                        }
                                    }));
                            }
                    });
                };
               
                function loadNhanvienNhan(){
                     $.ajax({
                            type: "GET",
                            url:"TraDonServlet",
                            data:{"action":"loadnvnhan"},
                            success: function (data) {
                                $('#dct_duocphan').html(data);
                                inputSearch();
                            }
                        });
                };
                loadNhanvienNhan();
                 $( "#dct_ngayduocphan" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
              $('#dct_dondadien').on("click",function(){
                  var inputOID = [];
                  var data = "";
                  var pdidArr = new Array();
                  $('#tb_donchotra tbody').find('input[id^="online"]').each(function(){
                      if($(this).val().trim().length >0){
                          inputOID.push($(this).prop('id'));
                      }
                  });
                 $('#tb_donchotra input:checkbox:checked').each(function(index){
                     pdidArr.push($(this).val());
                     console.log("DON ID = "+$(this).val());
                 });
                  $.each(pdidArr,function(i,el){
                     var maonline = $('#cb'+el).parent().parent().find('input[id^="online"]').val();
                     var mapin = $('#cb'+el).parent().parent().find('input[id^="mapin"]').val();
                     var donid = $('#cb'+el).parent() .parent().find('input[id^="donid"]').val();
                     if(maonline == undefined || maonline ==""){
                         maonline = "_";
                     }
                     if(mapin == undefined || mapin ==""){
                         mapin = "_";
                     }
                     if(data === ""){
                         data = donid+"-"+maonline+"-"+mapin;
                     }else{
                         data += ","+donid+"-"+maonline+"-"+mapin;
                     }
                  });
                  console.log(data);
           /**       $.ajax({
                            type: "POST",
                            url:"TraDonServlet",
                            data:{"action":"luunhieudon","don":data},
                            success: function (data) {
                                $('#tb_donchotra tbody').empty();
                                $('#tb_donchotra tbody').append(data);

                            }
                    }); **/
              });
              
                
                var tickAll = function(){
                    $('#tb_donchotra').find('input:checkbox').each(function(){
                            $('#tb_donchotra input:checkbox').prop('checked',true);
                    });
                   
                };
                
                var traDon = function(){
                    var data = "";
                   
                    $('#tb_donchotra tbody').find('input:checkbox:checked').each(function(){
                         var tgTra = new Date();
                        var $tr = $(this).parent().parent();
                        var pdid = $(this).prop('value');
                        var hh = tgTra.getHours();
                        var min = tgTra.getMinutes();
                        var dd = tgTra.getDate();
                        var mm = tgTra.getMonth()+1; //January is 0!
                        var yyyy = tgTra.getFullYear();

                        if(dd<10) {
                            dd='0'+dd;
                        } 

                        if(mm<10) {
                            mm='0'+mm;
                        } 

                    tgTra = yyyy+'-'+mm+'-'+dd+" "+hh+":"+min;
                         var dononline = $tr.find('input[id^="online"]').val();
                          var mapin = $tr.find('input[id^="mapin"]').val();
                         
                          if(dononline.trim() === ""  && mapin.trim() ===""){
                                    console.log(pdid);
                                    return false;
                            }else{
                              $.ajax({
                                type: "GET",
                                url:"TraDonServlet",
                                data:{"action":"tradon","pdid":pdid,"tgtra":tgTra},
                                success: function (data) {
                                    $('#tb_donchotra tbody').empty();
                                   inputSearch();

                                }
                            });
                            }
                    });
                    
                };
                 var ldCLick = function(){
                    var $tr = $(this).parent().parent();
                    
                    var donid = $tr.find('input[id^="donid"]').val();
                    var dononline = $tr.find('input[id^="online"]').val();
                    var mapin = $tr.find('input[id^="mapin"]').val();
                    if(dononline.trim() === "" ){
                        alert("Đơn online không được để trống");
                    }else{
                        var data =  donid+"-"+dononline+"-"+mapin;
                        $.ajax({
                            type: "GET",
                            url:"TraDonServlet",
                            data:{"action":"luudon","don":data},
                            success: function (data) {
                                $('#div_result').html("<span>"+data+"</span>");
                            }
                        });
                    }
                };
                var hoiDon = function(){
                    var pdid = $(this).parent().find('input[id^="donid"]').val();
                    var aValue = $(this).parent().parent().find('a').html();
                    var slts = $(this).parent().parent().find('td').eq(8).html();
                    var nvNhan = $(this).parent().parent().find('td').eq(6).find('input').val();
                    var ngaynhap = $(this).parent().parent().find('td').eq(0).html();
                    ngaynhap = ngaynhap.substring(5);
                    console.log(ngaynhap);
                    console.log(nvNhan);
                    $('#notiModal .modal-title').html("Hồi đơn");
                    $('#notiModal .modal-body').html("Bạn có muốn hồi đơn "+aValue+" không ?<input type='text' id='pdid_modal' value='"+pdid+"' hidden/>"+
             "<input type='text' id='ts_modal' value='"+slts+"' hidden/><input type='text' id='nvnhan_modal' value='"+nvNhan+"' hidden/>"+
             "<input type='text' id='ngaynhap_modal' value='"+ngaynhap+"' hidden/>");
              //      $('#notiModal').toggle();
                    $('#notiModal').modal('show');
                    /** $.ajax({
                            type: "POST",
                            url:"TraDonServlet",
                            data:{"action":"hoidon","pdid":pdid},
                            success: function (data) {
                                $('#tb_donchotra tbody').empty();
                                $('#tb_donchotra tbody').append(data);

                            }
                        });**/
                };
                var tradonModal = function(){
                    var pdid = $('#notiModal').find('#pdid_modal').val();
                    var slts = $('#notiModal').find('#ts_modal').val();
                    var nvnhan = $('#notiModal').find('#nvnhan_modal').val();
                    var ngaynhap = $('#notiModal').find('#ngaynhap_modal').val();
                  //  var nvNhan $('#')
                      $.ajax({
                            type: "GET",
                            url:"TraDonServlet",
                            data:{"action":"hoidon","pdid":pdid,slts: slts,nvnhan: nvnhan, ngaynhap: ngaynhap},
                            success: function (data) {
                                $('#tb_donchotra tbody').empty();
                                $('#tb_donchotra tbody').html(data);

                            }
                        });
                };
                var clearAll = function(){
                    $('#tb_donchotra').find('input:checkbox').each(function(){
                            $('#tb_donchotra input:checkbox').prop('checked',false);
                    });
                };
                $('#btn_tradonmodal').on('click',tradonModal);
                $('#dct_trahetdon').on('click',traDon);
              //  $('button[id^="dct_hdon"]').on("click",hoiDon);
                $(document).on('click','.dct_hdon',hoiDon);
                $('#dct_tickall').on('click',tickAll);
                $('#dct_clearall').on('click',clearAll);
              //  $('button[id^="dct_save"]').on("click",ldCLick);
              //  $("#dct_ngayduocphan").on("change",inputSearch);
              //  $('#tb_donchotra').on("click",".dct_save",ldCLick);
              $(document).on('click','.dct_save',ldCLick);
                $("#dct_online").on("keydown",function(e){
                    if(e.keyCode ==13){
                        $('#dct_search').click();
                    }
                });
                $("#dctcb_manhan").on("keydown",function(e){
                    if(e.keyCode ==13){
                        $('#dct_search').click();
                    }
                });
                 $("#dct_manhan").on("keydown",function(e){
                    if(e.keyCode ==13){
                        $('#dct_search').click();
                    }
                });
                
                  $('#dct_search').on('click',inputSearch);
                  
        </script>
    </body>
</html>
