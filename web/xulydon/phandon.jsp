<%-- 
    Document   : nhapdon
    Created on : Jul 4, 2016, 8:08:38 AM
    Author     : Thorfinn
--%>

<%@page import="com.ttdk.bean.NhanVienBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ttdk.bean.NhanVien"%>
<%@page import="com.ttdk.bean.NhapDon"%>
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
        <% NhanVien nv = new NhanVien();
            %>
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
                <li class="sub-menu"><a class="active"  href="xulydon.jsp?page=phandon">Phân đơn</a></li>
                <%
                    }
                    if(session.getAttribute("6").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=donchotra">Xem đơn chờ trả</a></li>
                 <%
                    }
                     if(session.getAttribute("8").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=dondatra">Xem đơn đã trả</a></li>
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
                    String result_nhapdon = "";
                    if(session.getAttribute("insert") != null){
                        result_nhapdon = session.getAttribute("insert").toString();
                    }
                    %>
            </ul> 
        <br>
        <form>
            <div style="text-align: center;width: 100%;"><span style="color: red;font-weight: bold;"><%=result_nhapdon%></span></div>
            <div style="text-align: center;width: 100%;"><span style="color: red;font-weight: bold;" id="result_phandon"></span></div>
            <fieldset>
                Lọc Dữ Liệu: <input type="text" name="ldl_date" id="loc_datepicker" placeholder="Ngày Nhập" style="width: 100px;"/>
                <input type="text" class='repalacedot' id="ldl_online" name="ldl_online" placeholder="Số đơn online" />
                <select name="cb_loainhan" id="cb_loainhan">
                    <option value="0">Loại đơn</option>
                    <option value="1">CE</option>
                    <option value="2">CF</option>
                    <option value="3">CT</option>
                    <option value="4">CB</option>
                </select>
                <select name="cb_loaidon" id="cb_loaidon">
                    <option value="0">Loại </option>
                    <option value="1">BD</option>
                    <option value="2">TT</option>
                    <option value="3">CSGT</option>
                </select>
                <select name="cb_loaidk" id="cb_loaidk">
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
                <input type="text" class='repalacedot' id="ldl_manhan" name="ldl_manhan" placeholder="Mã loại hình nhận" />
                <button type="button" id="pdSearch_btn">Tìm Kiếm</button>
                <div class="pull-right">
                    <button type="button" id="btn_phandon">Phân đơn</button>
                    <button type="button" id="btn_all">Chọn hết</button>
                    <button type="button" id="btn_clear">Bỏ hết</button>
                </div>
            </fieldset>
        </form><br>
        <fieldset>
         <!--   Phân đơn nhanh: 
            <textarea type="text" id="ip_pdNhanh" name="ip_pdNhanh" placeholder="Mã đơn" style="height: 35px;" readonly></textarea>
            <input type="text" id="ip_pdnid" name="ip_pdnid" hidden>
            <select name="cb_pdnhanh" id="cb_pdnhanh" >
                
            </select>
            <button type="button" id="btn_phandonnhanh">Phân đơn nhanh</button> -->
            <button type="button" id="btn_nvnhapdon" style="margin-left: 10px;">Chọn Nhân viên nhập đơn</button>
        </fieldset><br>
        <div id="Error_log"></div>
        <div id="div_loading"></div>
        <table id="tb_phandon" class="table table-striped table-bordered" style="background-color: lemonchiffon;">
            <thead style="background-color: #87CEFA" >
                <tr>
                    <th rowspan="2">Thời Điểm Nhập</th>
                    <th colspan="2">Số Đơn Do Online Cấp</th>              
                    <th rowspan="2">Loại Hình Nhận</th>
                    <th rowspan="2">Loại Đơn</th>
                    <th rowspan="2">Số Lượng Tài Sản</th>
                    <th rowspan="2">Chọn</th>
                </tr>
                <tr>
                    <th>Số Đơn Online</th>
                    <th>Mã Pin</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table><br>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        <div id="phandonModal" class="modal fade" role="dialog">
          <div class="modal-dialog modal-lg">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Phân đơn</h4>
                <input type="text" value="<%=session.getAttribute("username") %>" disabled/>
              </div>
              
              <div class="modal-body" id="modal-body">
                  <div class="container" style="width: 100%;color: #000000">
                    <ul id="phandon_tab" class="nav nav-tabs" style="color: #000000">
                        <li id="li1ts" class="active"><a data-toggle="tab" href="#don1ts" >Đơn 1 tài sản</a></li>

                    </ul>

                      <div id="tab_contentPD" class="tab-content" style="color: #000000">
                        <div id="don1ts" class="tab-pane fade in active" style="color: #000000">
                            <table border="1" id="tb_modalpd" >
                                <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên nhân viên</th>
                                        <th>Phân đơn</th>
                                        <th>Định mức</th>
                                        <th>Thực tế</th>
                                        <th>Chọn</th>         
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                </tbody>
                            </table>
                        </div>
                        <!--<div id="don1ts" class="tab-pane fade">
                          <h3>Menu 1</h3>
                          <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                        </div>
                        <div id="menu2" class="tab-pane fade">
                          <h3>Menu 2</h3>
                          <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
                        </div>
                        <div id="menu3" class="tab-pane fade">
                          <h3>Menu 3</h3>
                          <p>Eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
                        </div> -->
                    </div>
                    </div>

                </div>
                <div class="modal-footer">
                  <button type="button" id="btn_save" class="btn btn-default">Lưu</button>
                <button type="button" id="btn_huy" class="btn btn-default" data-dismiss="modal">Hủy</button>
              </div>
            </div>

          </div>
        </div>
        
        <div id="modal_error" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Lỗi</h4>
              </div>
              <div class="modal-body" id="modal-error-body">
                  
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              </div>
            </div>

          </div>
        </div>
              
              <!-- Modal load nhân viên để phân--->
              <div id="modal_Loadnhanvien" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Chọn Nhân Viên Nhập Liệu</h4>
              </div>
              <div class="modal-body" id="modal-nhanvien-body">
                  
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal" id="btn_luunvnhaplieu">Lưu</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
              </div>
            </div>

          </div>
        </div>
     <script>
         // parameters
         var _donidSelectList = [];
         var _madonSelectList = [];
         var _tsSelectList = [];
         var _ttPhandon = [];
         var _donnts = [];
         var _cbSelect = [];
         var _phandon1ts = false;
         var _donPhan = [];
         
         $( "#loc_datepicker" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
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
                 var loadPage = {
                     
                 };
                 $.ajax({
                       url : "PhanDonServlet",
                       type : "GET",
                       data : {
                                        "action" : "get_row"
                                },
                        dataType : "json",
                        success: function (data) {
                            console.log(data);
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $('#div_loading').html("<img src='./images/loading.gif'>");
                                    $('#tb_phandon tbody').empty();
                                    console.log('PAGE = '+page);
                                    $.ajax({
                                        url : "PhanDonServlet",
                                        type : "GET",
                                        data : {
                                                         "action" : "reload","page":page
                                                 },
                                         success: function (data) {
                                             $('#div_loading').empty();
                                             $('#tb_phandon tbody').html(data);
                                         }
                                        });
                                    
                                }
                            }));
                        }
                  });
                  var _ngaynhapSelect = '';
                 var selectDon = function(){
                    if ($(this).is(':checked')) {
                            if(_ngaynhapSelect === '' || _ngaynhapSelect === undefined){
                                var ngaynhap = $(this).parent().parent().find('td').eq(0).html();
                                var index = ngaynhap.indexOf(':');
                                 _ngaynhapSelect = ngaynhap.substring(index+3).trim();
                                 console.log("DATE = "+_ngaynhapSelect);
                                  _donidSelectList.push($(this).val());
                                var madon = $(this).parent().parent().find('td').eq(3).find('a').html();
                                var taisan = $(this).parent().parent().find('td').eq(5).html();
                                _madonSelectList.push(madon);
                                _tsSelectList.push(taisan);
                                $('#ip_pdNhanh').val(_madonSelectList);
                                 $('#ip_pdnid').val(_donidSelectList);
                            }else{
                                var ngaynhap = $(this).parent().parent().find('td').eq(0).html();
                                var index = ngaynhap.indexOf(':');
                                var daycheck = ngaynhap.substring(index+3).trim();
                                console.log(daycheck);
                                if(daycheck != _ngaynhapSelect){
                                    alert('Phân đơn khác ngày ');
                                    $(this).prop('checked',false);
                                    return false;
                                }else{
                                    _donidSelectList.push($(this).val());
                                    var madon = $(this).parent().parent().find('td').eq(3).find('a').html();
                                    var taisan = $(this).parent().parent().find('td').eq(5).html();
                                    
                                    _madonSelectList.push(madon);
                                    _tsSelectList.push(taisan);
                                    $('#ip_pdNhanh').val(_madonSelectList);
                                    $('#ip_pdnid').val(_donidSelectList);
                                }
                            }
                        } else {
                            for (var i = 0; i < _donidSelectList.length; i++) {
                                if(_donidSelectList[i] == $(this).val()){
                                    _donidSelectList.splice(i,1);
                                    _madonSelectList.splice(i,1);
                                    _tsSelectList.splice(i,1);
                                    $('#ip_pdNhanh').val(_madonSelectList);
                                    $('#ip_pdnid').val(_donidSelectList);
                                }
                            }
                            if(_donidSelectList.length ==0){
                                _ngaynhapSelect='';
                            }
                        }
                  };
                  
                  // phân đơn nhanh
                  var phandonNhanh = function(){
                    if($('#ip_pdNhanh').val() ==''){
                        alert('Chưa chọn đơn để phân');
                    }else{
                        var donID = $("#ip_pdnid").val();
                        var nvPhan = $("#username").val();
                        var nvNhan = $("#cb_pdnhanh").val();
                        var tgPhan = new Date();
                        var hh = tgPhan.getHours();
                        var min = tgPhan.getMinutes();
                        var dd = tgPhan.getDate();
                        var mm = tgPhan.getMonth()+1; //January is 0!
                        var yyyy = tgPhan.getFullYear();

                        if(dd<10) {
                            dd='0'+dd;
                        } 

                        if(mm<10) {
                            mm='0'+mm;
                        } 

                        tgPhan = yyyy+'-'+mm+'-'+dd+" "+hh+":"+min;
                        $.ajax({
                            type: "GET",
                            url:"PhanDonServlet",
                            data:{"action":"fast","donid":donID,"nvPhan":nvPhan,"nvNhan":nvNhan,"tgPhan":tgPhan,ngaynhap: _ngaynhapSelect},
                            success: function (data) {
                                $('#ip_pdNhanh').val("");
                                $("#ip_pdnid").val('');
                                $('#cb_pdnhanh').first();
                                 successToast(data);
                                 $.ajax({
                                        type: "GET",
                                        url:"PhanDonServlet",
                                        data:{"action":"reload",page: 1},
                                        success: function (data) {
                                            $('#tb_phandon tbody').empty();
                                            $('#tb_phandon tbody').append(data);
                                            _ngaynhapSelect = '';
                                             _donidSelectList.splice(0,_donidSelectList.length);
                                            _madonSelectList.splice(0,_madonSelectList.length);
                                            _tsSelectList.splice(0,_tsSelectList.length);
                                        },
                                        error: function (jqXHR, textStatus, errorThrown) {
                                            console.log(errorThrown);
                                            _ngaynhapSelect = '';
                                            _donidSelectList.splice(0,_donidSelectList.length);
                                            _madonSelectList.splice(0,_madonSelectList.length);
                                            _tsSelectList.splice(0,_tsSelectList.length);
                                        }
                                });
                            }
                        });     
                    }
                                
                };
                $('#phandonModal').on('hidden.bs.modal', function (e) {
                    $('.cb_phandon').prop('checked', false);
                    _donidSelectList = [];
                    _madonSelectList = [];
                    _tsSelectList = [];
                    _ttPhandon = [];
                    _donnts = [];
                    _cbSelect = [];
                    _phandon1ts = false;
                    _donPhan = [];
                    _donidSelectList = [];
                    _madonSelectList= [];
                    _tsSelectList= [];
                    _donPhan = [];
                    _ngaynhapSelect='';
                    $('#tb_phandon').find('input:checkbox').prop('checked',false);
                    $('#ip_pdNhanh').val('');
                    $('#ip_pdnid').val('');
              });
              
              // Phân đơn bình thường
              var phanDon = function(){
        /**          $('table#tb_phandon .selectDon[type=checkbox]:checked').each(function(){
                        _donidSelectList.push($(this).val());
                        var madon = $(this).parent().parent().find('td').eq(3).html();
                        var taisan = $(this).parent().parent().find('td').eq(5).html();
                        _madonSelectList.push(madon);
                        _tsSelectList.push(taisan);
                  }); **/
                  var _tagname = [];
                    if(_donidSelectList.length==0){
                        alert("Chưa chọn đơn để phân");
                    }else{
                       // var ngaynhap = $('#tb_phandon').find()
                        //var date = "";
                       // console.log(_ngaynhapSelect);
                       $('#tb_modalpd tbody').html("<span>Đang lấy định mức</span><img src='./images/loading.gif'>");
                      $.ajax({
                                type: "GET",
                                url:"PhanDonServlet",
                                data:{"action":"loadtablepd",date: _ngaynhapSelect},
                                success: function (data) {
                             //       $('#tb_phandon tbody').empty();
                                //    $('#tb_phandon tbody').append(data);
                                    $('#tb_modalpd tbody').html(data);
                                }
                        });                        
                        $('#phandonModal').modal({
                                backdrop: 'static',
                                keyboard: true, 
                                show: true
                        });
                        // đóng else
                    }
              };
              /****
              *   _donidSelectList.splice(i,1);
                    _madonSelectList.splice(i,1);
                    _tsSelectList.splice(i,1);
               */
              var nvNhan = [];
              var tsphan = [];
              var addPhandon = function(){
                    if ($(this).is(':checked')) {
                        var lengthCheck = $('#tb_modalpd').find(".cb_phandon[type='checkbox']:checked").length;
                        if(lengthCheck  ==1){
                            nvNhan.push($(this).val());
                            $(this).parent().parent().css('background-color','#FF7F50');
                        }else{
                            if(_donidSelectList.length > 1){
                                alert("ChỈ phân được cho 1 nhân viên");
                                $(this).prop('checked',false);
                                return false;
                            }else{
                                console.log("Tài sản length = "+_tsSelectList.length);
                                if(_tsSelectList[0] < lengthCheck){
                                    alert("Hết tài sản để phân");
                                    $(this).prop('checked',false);
                                    return false;
                                }else{
                                    nvNhan.push($(this).val());
                                    var tsdu = _tsSelectList[0]%nvNhan.length;
                                    if(tsphan.length > 0){
                                        tsphan.splice(0,tsphan.length);
                                    }
                                    for(var i=0; i< nvNhan.length;i++){
                                        tsphan.push(Math.floor(_tsSelectList[0]/nvNhan.length));
                                    }
                                    for(var i=0; i < tsdu;i++){
                                        tsphan[i] = tsphan[i] + 1;
                                    }
                                   $(this).parent().parent().css('background-color','#FF7F50');
                                }
                            }
                            
                        }

                    } else {
                        $(this).parent().parent().css('background-color','');
                        for(var i=0; i < nvNhan.length;i++){
                            if($(this).val() == nvNhan[i]){
                                nvNhan.splice(i,1);
                                var tsdu = _tsSelectList[0]%nvNhan.length;
                                    if(tsphan.length > 0){
                                        tsphan.splice(0,tsphan.length);
                                    }
                                    if(nvNhan.length >0 ){
                                        for(var i=0; i< nvNhan.length;i++){
                                            tsphan.push(Math.floor(_tsSelectList[0]/nvNhan.length));
                                        }
                                        for(var i=0; i < tsdu;i++){
                                            tsphan[i] = tsphan[i] + 1;
                                        }
                                    }
                                    
                            }
                        }
                    }
              };

                  var saveDon = function(){
                    $('table[id^="tb_modalpd"]').each(function(){
                    //    console.log($(this).prop('id'));
                        var lengthCheck = $(this).find(".cb_phandon[type='checkbox']:checked").length;
                     //   console.log(lengthCheck);
                         if(lengthCheck ==0){
                            alert("Cần chọn nhân viên trước khi phân");
                            return false;
                        }else if(lengthCheck == 1){
                            //alert($(this).closest('div').prop('id'));
                                var nvnhan = $(this).find(".cb_phandon[type='checkbox']:checked").val();
                                var nvPhan = $("#username").val();
                            $.ajax({
                               url : "PhanDonServlet",
                               type : "GET",
                               data : {
                                                "action" : "normal",donids: _donidSelectList,nvPhan: nvPhan,nvNhan : nvnhan,taisan: _tsSelectList, ngaynhap: _ngaynhapSelect
                                        },
                                success: function (data) {
                                    $.ajax({
                                            type: "GET",
                                            url:"PhanDonServlet",
                                            data:{"action":"reload",page: 1},
                                            success: function (data) {
                                                $('#tb_phandon tbody').empty();
                                                $('#tb_phandon tbody').append(data);

                                            }
                                    });
                             },error: function(data){
                                 console.log(data);
                             }
                          });
                          $('#phandonModal').modal('hide');
                        }else{
                            var nvPhan = $("#username").val();
                            $.ajax({
                               url : "PhanDonServlet",
                               type : "GET",
                               data : {
                                                "action" : "normalnts",donids: _donidSelectList,nvPhan: nvPhan,nvNhan : nvNhan,taisan: tsphan,ngaynhap: _ngaynhapSelect
                                        },
                                success: function (data) {
                                    $.ajax({
                                            type: "GET",
                                            url:"PhanDonServlet",
                                            data:{"action":"reload",page: 1},
                                            success: function (data) {
                                                $('#tb_phandon tbody').empty();
                                                $('#tb_phandon tbody').append(data);

                                            }
                                    });
                             },error: function(data){
                                 console.log(data);
                             }
                          });
                          $('#phandonModal').modal('hide');
                        }
                    });
                  };
                  var tickAll = function(){
                      var ngay_nhap_dau = '';
                      $('#tb_phandon').find('input:checkbox').each(function(i,el){
                          if(i==0){
                              ngay_nhap_dau = $(this).parent().parent().find('td').eq(0).html();
                          }
                          var ngaynhap = $(this).parent().parent().find('td').eq(0).html();
                          if(ngay_nhap_dau == ngaynhap && ngay_nhap_dau != ''){
                              $(this).prop('checked',true);
                              _donidSelectList.push($(this).val()) ;
                              var madon = $(this).parent().parent().find('td').eq(3).html();
                              var taisan = $(this).parent().parent().find('td').eq(5).html();
                              _madonSelectList.push(madon);
                              _tsSelectList.push(taisan);
                              var index = ngay_nhap_dau.indexOf(':');
                              _ngaynhapSelect = ngay_nhap_dau.substring(index+3).trim();
                              $('#ip_pdNhanh').val(_madonSelectList);
                              $('#ip_pdnid').val(_donidSelectList);
                          }
                      });
                    //  console.log(_donidSelectList);
                  };
                  var clearTickAll = function(){
                      $('#tb_phandon').find('input:checkbox').prop('checked',false);
                      $('#tb_phandon').find('input:checkbox').each(function(i,el){
                         //_donidSelectList.push($(this).val()) ;
                         for(var i=0; i < _donidSelectList.length;i++){
                             if($(this).val() == _donidSelectList[i]){
                                 _donidSelectList.splice(i,1);
                                 _madonSelectList.splice(i,1);
                                _tsSelectList.splice(i,1);
                                $('#ip_pdNhanh').val(_madonSelectList);
                                $('#ip_pdnid').val(_donidSelectList);
                             }
                         }
                      });
                      console.log(_donidSelectList);
                  };  
                  
                  var searchDon = function(){
                      $('#div_loading').html("<img src='./images/loading.gif'>");
                      var ngaynhap = $('#loc_datepicker').val();
                      var maonline = $('#ldl_online').val();
                      var loainhan = $('#cb_loainhan').val();
                      var loaidon = $('#cb_loaidon').val();
                      var cb_loaidk = $('#cb_loaidk').val();
                      var manhan = $('#ldl_manhan').val();
                      searchList.splice(0,searchList.length);
                      searchList.push(ngaynhap);searchList.push(maonline);searchList.push(loainhan);
                      searchList.push(loaidon);searchList.push(cb_loaidk);searchList.push(manhan);
                      $.ajax({
                                type: "GET",
                                url:"PhanDonServlet",
                                dataType : "json",
                                data:{"action":"searchchuaphandon",ngaynhap: ngaynhap,maonline: maonline, loainhan: loainhan,
                                loaidon: loaidon, loaidk: cb_loaidk, manhan: manhan,page: 1},
                                success: function (data) {
                                    $('#div_loading').empty();
                                   $('#tb_phandon tbody').html(data.data);
                                   var currentpage = data.currentpage;
                                var totalpage = data.totalpage;
                              //  $('#ul_page').hide();
                                $pagination.twbsPagination('destroy');
                              //  loadPageSearch(currentpage);
                                $pagination.twbsPagination($.extend({}, defaultOpts, {
                                        startPage: currentpage,
                                        totalPages: totalpage,
                                        onPageClick: function (event, page) {
                                            $('#div_loading').html("<img src='./images/loading.gif'>");
                                            $.ajax({
                                                    type: "GET",
                                                    url:"PhanDonServlet",
                                                    dataType : "json",
                                                    data:{"action":"searchchuaphandon",ngaynhap: searchList[0],maonline:  searchList[1], loainhan:  searchList[2],
                                loaidon:  searchList[3], loaidk:  searchList[4], manhan:  searchList[5],page: page},
                                                    success: function (data) { 
                                                        $('#div_loading').empty();
                                                         $('#tb_phandon tbody').html(data.data);
                                                    }

                                                }); 
                                        }
                                    }));
                                }
                        });   
                  };
                  var nvNhandon = function(){
                      //modal-nhanvien-body
                      $.ajax({
                            type: "GET",
                            url:"PhanDonServlet",
                            data:{"action":"loadnhanvien"},
                            success: function (data) { 
                                 $('#modal-nhanvien-body').html(data);
                                 $('#modal_Loadnhanvien').modal({
                                        backdrop: 'static',
                                        keyboard: true, 
                                        show: true
                                });
                            }

                        }); 
                  };
                  function luuNhapLieu(){
                      var nvNL = [];
                      $('#modal-nhanvien-body').find('input:checkbox').each(function(){
                          //console.log($(this).val());
                          var result = $(this).val();
                          if($(this).is(':checked')){
                              result += "-1";
                          }else{
                              result += "-0";
                          }
                          nvNL.push(result);
                      });
                      console.log(nvNL);
                      $.ajax({
                            type: "GET",
                            url:"PhanDonServlet",
                            data:{"action":"updatenhaplieu",nhanvien : nvNL},
                            success: function (data) { 
                               // loadNhapLieu();
                            }

                        }); 
                      $('#modal_Loadnhanvien').hide();
                  };
             /**     var loadNhapLieu = function(){
                      $.ajax({
                            type: "GET",
                            url:"PhanDonServlet",
                            data:{"action":"loadnhaplieu"},
                            success: function (data) { 
                                $('#cb_pdnhanh').html(data);
                            }

                        }); 
                  };
                  loadNhapLieu(); **/
                  // add function to event
                  $('#btn_all').on('click',tickAll);
                  $('#btn_clear').on('click',clearTickAll);
                  $('#tb_phandon').on('change', ':checkbox', selectDon);
                  $("#btn_phandonnhanh").on("click",phandonNhanh);
                  $('#btn_phandon').on('click',phanDon);
                  $('#btn_save').on('click',saveDon);
                  $('#modal-body').on('change','.cb_phandon',addPhandon);
                  $('#pdSearch_btn').on('click',searchDon);
                  $("#loc_datepicker").on("keydown",function(e){
                        if(e.keyCode ==13){
                            $('#pdSearch_btn').click();
                        }
                   });
                   $("#ldl_online").on("keydown",function(e){
                        if(e.keyCode ==13){
                            $('#pdSearch_btn').click();
                        }
                   });
                   $("#ldl_manhan").on("keydown",function(e){
                        if(e.keyCode ==13){
                            $('#pdSearch_btn').click();
                        }
                   });
                   $('#btn_nvnhapdon').on('click',nvNhandon);
                   $('#btn_luunvnhaplieu').on('click',luuNhapLieu);
     </script>
    </body>
    
</html>
