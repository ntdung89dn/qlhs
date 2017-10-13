<%-- 
    Document   : themvanthu
    Created on : Jul 5, 2016, 5:15:09 PM
    Author     : Thorfinn
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.ttdk.bean.VanThu"%>
<%@page import="com.ttdk.bean.SearchDon"%>
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
            td{
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
                <li class="sub-menu"><a class="active"  href="vanthu.jsp?page=themvanthu">Thêm Văn Thư</a></li>
                 <%
                     }
                if(session.getAttribute("11").equals("1")){
                %>
                <li class="sub-menu"><a href="vanthu.jsp?page=xemvanthu">Xem Văn Thư</a></li>
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
                     String ngaygoi = "",noinhan="",sobuudien="",sobienlai="",ghichu="",sohoso = "",donids="";
                     boolean isEdit = false;
                     if(session.getAttribute("vanthuid") != null){
                            isEdit = true;
                            int vtid = Integer.parseInt(session.getAttribute("vanthuid").toString());
                            ArrayList<String> data = new VanThu().getInforVanThuById(vtid);
                            ngaygoi = data.get(0);noinhan = data.get(1);sobuudien = data.get(2);
                            sobienlai = data.get(3);ghichu = data.get(4);
                            ArrayList<ArrayList<String>> donList = new VanThu().getDonLuuVTByVTid(vtid);
                            for(ArrayList<String> don : donList){
                                if(sohoso.equals("")){
                                    sohoso += don.get(0);
                                    donids += don.get(1);
                                }else{
                                    sohoso += ","+don.get(0);
                                    donids += ","+don.get(1);
                                }
                            }
                         session.removeAttribute("vanthuid");
                    }
                %>
        </ul><br>
        <div style="text-align: center;"><span id="result_add" style="font-weight: bold;color: red;"></span></div>
        <div class="panel panel-default">
            <div class="panel-heading">Thông tin văn thư</div>
            <div class="panel-body" id="panel_body">
                <!--<form action="" method="post" class="form-signin"> -->
                    <div class="form-group row">
                        <div class="col-sm-2">
                            
                        </div> 
                        <label for="ngaygoi" class="col-sm-1 form-control-label">Ngày Gởi:</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="ngaygoi" name="ngaygoi" value="<%=ngaygoi %>">
                        </div>
                        <div class="col-sm-1">
                            <p class="help-block bg-warning" id="ng_err">***</p>
                        </div> 
                        <label for="noinhan" class="col-sm-1 form-control-label">Nơi Nhận:</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="noinhan" name="noinhan" value="<%=noinhan %>">
                        </div>
                        <div class="col-sm-1">
                            <p class="help-block bg-warning" id="nn_err">***</p>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-2">
                            
                        </div> 
                        <label for="sdb" class="col-sm-1 form-control-label">Số Bưu Điện:</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="sdb" name="sdb" value="<%=sobuudien %>">
                        </div>
                        <div class="col-sm-1">
                            <p class="help-block bg-warning" id="bd_err">***</p>
                        </div> 
                        <label for="sbl" class="col-sm-1 form-control-label">Số biên lai:</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="sbl" name="sbl" value="<%=sobienlai %>">
                        </div>
                        <div class="col-sm-1">
                            <p class="help-block bg-warning" id="nn_err">***</p>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-2">
                            
                        </div> 
                        <label for="ghichu" class="col-sm-1 form-control-label">Ghi Chú:</label>
                        <div class="col-sm-8">
                            <textarea class="form-control" id="ghichu" name="ghichu" value="<%=ghichu %>"></textarea>
                        </div>
                        <div class="col-sm-1">
                            <p class="help-block bg-warning" id="dc_err">*****</p>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-2">
                            
                        </div> 
                        <label for="sohoso" class="col-sm-1 form-control-label">Số Hồ Sơ:</label>
                        <div class="col-sm-7">
                            <input type="hidden" class="form-control" id="idhs" name="idhs" value="<%=donids %>">
                            <input type="text" class="form-control" id="sohoso" name="sohoso" value="<%=sohoso %>" readonly>
                        </div>
                        <div class="col-sm-1">
                            <p class="help-block bg-warning" id="ld_err">***</p>
                        </div>
                    </div> 
                    <div class="form-group row">
                        
                        <div class="col-sm-4">
                            
                        </div>
                        <div class="col-sm-2">
                            <%
                                if(isEdit){
                                    %>
                            <button type="button" id="btn_Edit" class="btn btn-secondary" style="width: 100%">Cập nhật</button>
                            <%
                                }else{
                                %>
                            <button type="button" id="btn_submit" class="btn btn-secondary" style="width: 100%">Lưu Lại</button>
                            <%
                                }
                                %>
                        </div>
                         <div class="col-sm-2">
                             <button type="button" id="btn_reset" class="btn btn-secondary" style="width: 100%">Hủy Bỏ</button>
                        </div>
                        <div class="col-sm-4">
                            
                        </div>
                    </div> 
                  <!--</form> -->
            </div>
        </div>
        <div id="search-form">
            <fieldset>
            <legend class="the-legend">Lọc Dữ Liệu</legend>
                <select id="tvt_loainhan"  style="width: 150px !important;height: 30px">
                    <option value="0">Loại nhận</option>
                    <option value="1">CE</option>
                    <option value="2">CF</option>
                    <option value="3">CT</option>
                    <option value="4">CB</option>
                </select>
            
                <select id="tvt_loaidk">
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
                <input type="text" name="s_ngaynhapdau" id="s_ngaynhapdau" placeholder="Ngày Nhập Đầu" style="width: 90px !important; height: 30px"/>
                <input type="text" name="s_ngaynhapcuoi" id="s_ngaynhapcuoi" placeholder="Ngày Nhập Cuối" style="width: 90px !important; height: 30px"/>
                <input type="text" class="repalacedot" name="s_dononline" id="s_dononline" placeholder="Số đơn online" style="width: 90px !important; height: 30px" />
                <select id="tvt_loaidon">
                    <option value="0">Loại </option>
                    <option value="1">BD</option>
                    <option value="2">TT</option>
                    <option value="3">CSGT</option>
                </select>
                <input type="text" class="repalacedot" name="s_maloainhan" id="s_maloainhan" placeholder="Mã loại hình nhận"/>
                <input type="text" name="s_nhanbaodam" id="s_nhanbaodam" placeholder="Bên nhận bảo đảm"/>
                <input type="text" name="s_benbaodam" id="s_benbaodam" placeholder="Bên bảo dảm"/>
                <input type="text" class="repalacedot" name="s_mabarcode" id="s_mabarcode" placeholder="mã barcode"/>
                <button type="button" id="btn_search" class="btn btn-default">Tìm Kiếm</button>
        </fieldset><br> 
        <div id="div_loading"></div>
        <table id="vanthu" class="table-striped" >
            <thead style="background-color: #87CEFA">
                <tr>
                    <th rowspan="2">Thời Điểm Nhập</th>
                    <th colspan="2">Số Đơn Do Online Cấp</th>              
                    <th rowspan="2">Loại Hình Nhận</th>
                    <th rowspan="2">Bên Nhận Bảo Đảm</th>
                    <th rowspan="2">Bên Bảo Đảm</th>
                    <th rowspan="2">Chọn</th>
            </tr>
            <tr>
                <th>Số Đơn Online</th>
                <th>Mã Pin</th>
            </tr>
            </thead>
            <tbody>
            <%
                    VanThu vt = new VanThu();
                 String   tbody = vt.tableVanThu(1);
                    
                %>
                <%=tbody %>
                </tbody>
        </table><br>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        </div>
                <!-- Modal -->
            <div id="myModal" class="modal fade" role="dialog">
              <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Thông báo</h4>
                  </div>
                  <div class="modal-body">
                    <p>Không cùng Bên Nhận Bảo Đảm , Bạn có muốn thêm vào hay không ?</p>
                  </div>
                  <div class="modal-footer">
                      <button type="button" id="btn_Add" class="btn btn-default" data-dismiss="modal">Thêm vào</button>
                    <button type="button" id="btn_Close" class="btn btn-default" data-dismiss="modal">Đóng</button>
                  </div>
                </div>
              </div>
                <script>
                    var donArr = [];
                var idArr = [];
                var _nhArr = [];
                $( "#ngaygoi" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                $( "#s_ngaynhapdau" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                $( "#s_ngaynhapcuoi" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                var $pagination = $('#ul_page');
                var searchList = [];
                var defaultOpts = {
                    totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'Trang trước',
                    next: 'Trang sau',
                    last: 'Trang cuối',
                    loop: false,
                    initiateStartPageClick: false
                };
                 $pagination.twbsPagination(defaultOpts);
                 $.ajax({
                       url : "vanthu",
                       type : "GET",
                       data : {
                                        "action" : "getrow_vt"
                                },
                        dataType : "json",
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    console.log('event = '+event.name);
                                    $.ajax({
                                        type: "GET",
                                        url:"vanthu",
                                        data:{"action":"reload","page":page},
                                        success: function (data) {
                                            $('#vanthu tbody').html(data);
                                             $('input[id^="cb_vanthu"]').on("click",cb_Click);
                                        }
                                    });
                                }
                            }));
                        }
                  });

                var cb_Click = function(){
                    var id = $(this).val();
                    var check = $(this).prop('checked');
                    if(check){
                        idArr[idArr.length] = id;
                        var don = $(this).parent().parent().find('td').eq(1).html();
                        var _nhName = $(this).parent().parent().find('td').eq(4).html();
                        if(_nhArr.length ==0){
                            _nhArr.push(_nhName);
                        }else{
                          $.each(_nhArr,function(i,e){
                            var compare = e.toLowerCase().localeCompare(_nhName.toLowerCase());
                            if( compare == 0) {
                                _nhArr.push(_nhName);
                            }else{
                                $('#myModal').modal('show');
                            }
                           });
                        }
                        
                        if(don ===''){
                            don = $(this).parent().parent().find('td').eq(1).html();
                        }
                          console.log(don);
                         donArr[donArr.length] = don;
                        $('#idhs').val(idArr);
                        $('#sohoso').val(donArr);
                        console.log(donArr);
                    }else{
                        for(var i=0;i<idArr.length;i++){
                            if(id === idArr[i]){
                                idArr.splice(i,1);
                                donArr.splice(i,1);
                                _nhArr.splice(i,1);
                            }
                             $('#idhs').val(idArr);
                            $('#sohoso').val(donArr);
                            console.log(donArr);
                        }
                    }
                    
                };
                var reset_Click = function(){
                    $('#panel_body').find('input').val("");
                    $('#panel_body').find('textarea').val("");
                    $('#vanthu').find('input:checkbox').prop('checked',false);
                    donArr = [];
                     idArr = [];
                     _nhArr = [];
                     $.ajax({
                        type: "GET",
                        url:"vanthu",
                        data:{"action":"reload","page":1},
                        success: function (data) {
                            $('#vanthu tbody').html(data);
                             $('input[id^="cb_vanthu"]').on("click",cb_Click);
                        }
                    });
                };
                var submit_Click = function(){
                    $('#result_add').html('');
                    var daytime = $( "#ngaygoi" ).datepicker().val();
                    console.log(daytime);
                    var noinhan = $('#noinhan').val();
                    var sobuudien = $('#sdb').val();
                    console.log(sobuudien);
                    var sobl = $('#sbl').val();
                    var ghichu = $('#ghichu').val();
                    console.log(ghichu);
                    var idhs = $('#idhs').val();
                    console.log(idhs);
                  if(sobuudien === undefined || !sobuudien || sobuudien.trim().length ===0){
                        alert("Cần nhập số bưu điện");
                    }else{
                        if(idhs === undefined || !idhs || idhs.trim().length ===0){
                                alert("Chưa chọn đơn");
                            }else{
                                $('#div_loading').html("<img src='./images/loading.gif'>");
                                $.ajax({
                                    type: "GET",
                                    url:"vanthu",
                                    data:{"action":"insert","ngaygoi":daytime,"noinhan":noinhan,"sobuudien":sobuudien,
                                        "sobienlai":sobl,"ghichu":ghichu,"donid":idhs},
                                    success: function (data) {
                                        $('#result_add').html(data);
                                        $('#div_loading').empty();
                                        vt_Search();
                                        reset_Click();
                                    }
                                });
                            }
                    }
                };
                
                var vt_Search = function(){
                    var ngaynhandau = $("#s_ngaynhapdau").val();
                     var ngaynhancuoi = $("#s_ngaynhapcuoi").val();
                    var loainhan = $("#tvt_loainhan").val();
                    var mabarcode = $('#s_mabarcode').val();
                    var dononline = $("#s_dononline").val();
                    var maloainhan = $("#s_maloainhan").val();
                    var loaidon = $("#tvt_loaidon").val();
                    var loaidk = $("#tvt_loaidk").val();
                    var bnbd = $("#s_nhanbaodam").val();
                    var bbd = $("#s_benbaodam").val();
                    searchList.splice(0,searchList.length);
                    searchList.push(ngaynhandau);searchList.push(ngaynhancuoi);searchList.push(loainhan);
                    searchList.push(maloainhan);searchList.push(dononline);searchList.push(bnbd);
                    searchList.push(bbd);searchList.push(loaidon);searchList.push(loaidk);searchList.push(mabarcode);
                    $('#div_loading').html("<img src='./images/loading.gif'>");
                    $.ajax({
                        url:"vanthu",
                        method: "GET",
                        dataType : "json",
                        data:{action: "search_donvt","page": 1,"ngaynhandau": ngaynhandau,"ngaynhancuoi": ngaynhancuoi,"loainhan": loainhan,
                            "maloainhan": maloainhan,"dononline": dononline,"bnbd": bnbd,"bbd": bbd,'loaidon': loaidon,"loaidk": loaidk,barcode : mabarcode}, 
                        success: function (data) {
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $('#div_loading').empty();
                            $('#vanthu tbody').html(data.data);
                            $('input[id^="cb_vanthu"]').on("click",cb_Click);
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages:  totalPages,
                                onPageClick: function (event, page) {
                                    searchVTPage(searchList,page);
                                }
                            }));
                             
                             
                        }
                    });
                };
                var searchVTPage= function(slist,page){
                    $('#div_loading').html("<img src='./images/loading.gif'>");
                    $.ajax({
                            url:"vanthu",
                            method: "GET",
                            dataType : "json",
                            data:{"action":"search_donvt","page": page,"ngaynhandau": slist[0],"ngaynhancuoi": slist[1],"loainhan": slist[2],
                                "maloainhan": slist[3],"dononline": slist[4],"bnbd": slist[5],"bbd": slist[6],'loaidon': slist[7],
                                "loaidk": slist[8],barcode : slist[9]},
                            success: function (data) { 
                                $('#div_loading').empty();
                                $('#vanthu tbody').html(data.data);
                                $('input[id^="cb_vanthu"]').on("click",cb_Click);
                            }
                    }); 
                };
                var addModal = function(){
                     $('#myModal').modal('hide');
                     // donArr[donArr.length] = don;
                };
                
                var editVanthu = function(){
                    $('#div_loading').html("<img src='./images/loading.gif'>");
                    var daytime = $( "#ngaygoi" ).datepicker().val();
                    console.log(daytime);
                    var noinhan = $('#noinhan').val();
                    var sobuudien = $('#sdb').val();
                    console.log(sobuudien);
                    var sobl = $('#sbl').val();
                    var ghichu = $('#ghichu').val();
                    console.log(ghichu);
                    var idhs = $('#idhs').val();
                    console.log(idhs);
                  if(sobuudien === undefined || !sobuudien || sobuudien.trim().length ===0){
                        alert("Cần nhập số bưu điện");
                    }else{
                        if(idhs === undefined || !idhs || idhs.trim().length ===0){
                                alert("Chưa chọn đơn");
                            }else{
                                $('#div_loading').html("<img src='./images/loading.gif'>");
                                $.ajax({
                                    type: "GET",
                                    url:"vanthu",
                                    data:{"action":"updatevanthu","ngaygoi":daytime,"noinhan":noinhan,"sobuudien":sobuudien,
                                        "sobienlai":sobl,"ghichu":ghichu,"donid":idhs},
                                    success: function (data) {
                                        $('#div_loading').empty();
                                     //   reset_Click();
                                     window.location = "./vanthu.jsp?page=xemvanthu";
                                    }
                                });
                            }
                    }
                };
                var closeModal = function(){
                     $('#myModal').modal('hide');
                     var closeCB = idArr[donArr.length-1];
                      $('#cb_vanthu'+closeCB).attr('checked',false);
                      idArr.splice(donArr.length-1,1);
                      donArr.splice(donArr.length-1,1);
                      $('#sohoso').val(donArr);
                      $('#idhs').val(idArr);
                };
                $('#s_dononline').on('keydown',function(e){
                   if(e.keyCode ==13) {
                       $('#btn_search').click();
                   }
                });
                $('#s_nhanbaodam').on('keydown',function(e){
                    console.log("sadas dasdsad"+$(this).val());
                   if(e.keyCode ==13) {
                       $('#btn_search').click();
                   }
                });
                $('#s_benbaodam').on('keydown',function(e){
                   if(e.keyCode ==13) {
                       $('#btn_search').click();
                   }
                });
                $('#s_maloainhan').on('keydown',function(e){
                   if(e.keyCode ==13) {
                       $('#btn_search').click();
                   }
                });
                $('#btn_search').on("click",vt_Search);
                 $('#btn_submit').on('click',submit_Click);
                 $('#btn_Edit').on('click',editVanthu);
                $('#btn_reset').on('click',reset_Click);
                $('input[id^="cb_vanthu"]').on("click",cb_Click);
                $('#btn_Add').on("click",addModal);
                $('#btn_Close').on('click',closeModal);
                </script>
    </body>
</html>
