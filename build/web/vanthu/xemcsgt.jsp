<%-- 
    Document   : xemcsgt
    Created on : Mar 20, 2017, 11:06:45 AM
    Author     : ntdung
--%>

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
            td{
                background: #ffffff;
            }
            #updateVTCSgt {
                min-width: 800px !important;
            }
            .ui-autocomplete {
                position: absolute;
                top: 0;
                left: 0;
                z-index: 1510 !important;
              }
              .modal_loading {
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
            body.loading .modal_loading {
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
            
            <div class="modal_loading"></div>
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
                <li class="sub-menu"><a class="active" href="vanthu.jsp?page=xemcsgt">Xem Văn Thư gửi CSGT</a></li>
                <%
                     }
                %>
        </ul><br>
        <div style="text-align: center;"><span id="result_add" style="font-weight: bold;color: red;"></span></div>
        <div id="kh_table"> Lọc dữ liệu</div>
        <div class="row">
            <div class="col-xs-2">
                <input type="text" id="s_ngaybatdau" class="form-control" value="" placeholder="Ngày bắt đầu" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" id="s_ngayketthuc" class="form-control" value="" placeholder="Ngày kết thúc" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" class="repalacedot" id="s_maonline" class="form-control" value="" placeholder="Mã Online" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" class="repalacedot" id="s_manhan" class="form-control" value="" placeholder="Mã nhận" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" id="s_bnbd" class="form-control" value="" placeholder="Bên nhận bảo đảm" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" id="s_bbd" class="form-control" value="" placeholder="Bên Bảo Đảm" style="width: 100%"/>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-xs-2">
                <input type="text" class="repalacedot" id="s_mabuudien" class="form-control" value="" placeholder="Mã bưu điện" style="width: 100%" autocomplete="off"/>
            </div>
            <div class="col-xs-3">
                <input type="text" id="s_noinhan" class="form-control" value="" placeholder="Nơi nhận" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <div class="form-group">
                        <select class="form-control" id="select_city">
                        </select>
                    </div>
            </div>
            <div class="col-sm-5" style="text-align: right;">
                <button type="button" id="btn_Search" class="btn btn-default" >Tìm kiếm</button>
                <button type="button" id="btn_Excel" class="btn btn-default" >Xuất Excel</button>
            </div>
        </div>
        <br>
            <table class="table table-bordered" id="tb_vtcsgt">
            <thead style="background-color: #87CEFA">
                <tr>
                    <th rowspan="2">Ngày gởi</th>
                    <th rowspan="2">Mã bưu điện</th>
                    <th rowspan="2">Người nhận</th>      
                    <th rowspan="2">Nơi Nhận</th>
                    <th rowspan="2">Mã Nhận</th>
                    <th rowspan="2">Số đơn online</th>
                    <th rowspan="2">Ghi chú</th>
                    <th rowspan="2">Xóa</th>
                    <th rowspan="2">Chọn</th>
            </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
        </div>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        
        <!-- Modal Thêm đơn -->
            <div id="myModal" class="modal fade" role="dialog">
              <div class="modal-dialog modal-lg">

                <!-- Modal content-->
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title"></h4>
                  </div>
                  <div class="modal-body">
                      <div class="panel panel-default">
                        <div class="panel-heading">Văn thư gửi phụ lục 05</div>
                        <div class="panel-body" id="panel_body">
                            <!--<form action="" method="post" class="form-signin"> -->
                                <div class="form-group row">
                                    <div class="col-sm-2">

                                    </div> 
                                    <label for="ngaygoi" class="col-sm-1 form-control-label">Ngày Gởi:</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" id="ngaygoi" name="ngaygoi">
                                    </div>
                                    <div class="col-sm-1">
                                        <p class="help-block bg-warning" id="ng_err">***</p>
                                    </div> 
                                    <label for="noinhan" class="col-sm-1 form-control-label">Nơi Nhận:</label>
                                    <div class="col-sm-3 ui-widget">
                                        <input type="text" class="form-control" id="vtgt_noinhan" placeholder="Nơi nhận">
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
                                        <input type="text" class="form-control" id="sdb" name="sdb">
                                    </div>
                                    <div class="col-sm-1">
                                        <p class="help-block bg-warning" id="bd_err">***</p>
                                    </div> 
                                    <label for="sbl" class="col-sm-1 form-control-label">Địa chỉ:</label>
                                    <div class="col-sm-3">
                                      <input type="text" class="form-control" id="csgt_diachi" name="csgt_diachi">
                                      <input type="text"  id="csgt_dcid" name="csgt_dcid" hidden>
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
                                        <textarea class="form-control" id="ghichu" name="ghichu"></textarea>
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
                                        <input type="hidden" class="form-control" id="idhs" name="idhs" value="">
                                        <input type="text" class="form-control" id="sohoso" name="sohoso" value="" readonly>
                                    </div>
                                    <div class="col-sm-1">
                                        <p class="help-block bg-warning" id="ld_err">***</p>
                                    </div>
                                </div> 
                                <div class="form-group row">

                                    <div class="col-sm-4">

                                    </div>
                                    <div class="col-sm-2">
                                        <button type="button" id="btn_updatevtcsgt" class="btn btn-secondary" style="width: 100%">Lưu Lại</button>
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
                      <br>
                      <div class="row">
                          <div class="col-xs-4">
                                <input type="text" id="s_addmanhan" class="form-control" value="" placeholder="Mã nhận" style="width: 100%"/>
                            </div>
                            <div class="col-xs-4">
                                  <input type="text" id="s_addmaonline" class="form-control" value="" placeholder="Mã Online" style="width: 100%"/>
                            </div>
                          <div class="col-xs-4">
                                  <input type="text" id="s_addbnbd" class="form-control" value="" placeholder="Bên nhân bảo đảm" style="width: 100%"/>
                            </div>

                      </div><br>
                      <div class="row">
                          <div class="col-xs-4">
                            </div>
                            <div class="col-xs-4">
                            </div>
                          <div class="col-xs-4">
                            </div>
                          <div class="col-xs-2">
                                  <button type="button" id="btn_SCsgt" class="btn btn-default" >Tìm kiếm</button>
                            </div>
                      </div><br>
                      <table class="table table-bordered" id="tb_vtaddcsgt">
                            <thead>
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

                            </tbody>
                        </table>
                      <div style="text-align: right">
                        <ul class="pagination" id="ul_addpage" ></ul>
                    </div>
                  </div>
                  <div class="modal-footer">
                      <button type="button" id="btn_AddCsgt" class="btn btn-default" data-dismiss="modal">Thêm vào</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                  </div>
                </div>

              </div>
            </div>
        <!-- xóa đơn văn thư và văn thư -->
        <div id="deldialog" title="Xóa">
            <p>Bạn có muốn xóa các đơn đã chọn ?</p>
         </div>
        <div id="delVTdialog" title="Xóa Văn thư">
            <p>Bạn có muốn xóa Văn thư đã lưu ?</p>
         </div>
        
        <!-- modal update văn thư csgt -->
        <div id="updateVTCSgt" class="modal fade" role="dialog">
              <div class="modal-dialog modal-lg">

                <!-- Modal content-->
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title"></h4>
                  </div>
                  <div class="modal-body">
                      
                  </div>
                  <div class="modal-footer">
                      <button type="button" id="btn_AddCsgt" class="btn btn-default" data-dismiss="modal">Thêm vào</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                  </div>
                </div>

              </div>
            </div>
        <script>
            // parameters
            var _donSelect = [];
            var _donDelSelect = [];
            var xoaVt = "n";
            var vtcsgtid = "";
            $body = $("body");
            //function ...........
            
                $( "#s_ngaybatdau" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                
                $( "#s_ngayketthuc" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                $( "#ngaygoi" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                var loadCity = function (){
                    $body.addClass("loading");
                    $.ajax({
                            url : "vanthu",
                            type : "GET",
                            data:{action: "loadcity"},
                            success: function (data) {
                                $body.removeClass("loading");
                                var citydt = '<option value="0" >Tất cả</option>'+data;
                                 $('#select_city').html(citydt);
                            },
                            error: function (data) {
                                console.log('Error:', data);
                            }
                        }); 
                };
                 loadCity();
                // load số trang
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
                                        "action" : "loadvtcsgtluu",page: 1
                                },
                        dataType : "json",
                        success: function (data) {
                            console.log(data);
                            $('#tb_vtcsgt tbody').html(data.data);
                            var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                    $body.addClass("loading");
                                    //loadCsgtCg(page);
                                    $.ajax({
                                        url : "vanthu",
                                        type : "GET",
                                        data : {
                                                         "action" : "loadvtcsgtluu",page: page
                                                 },
                                         dataType : "json",
                                         success: function (data) {
                                             //console.log(data);
                                             $body.removeClass("loading");
                                             $('#tb_vtcsgt tbody').html(data.data);
                                         }
                                   });
                                }
                            }));
                        }
                  });
                  //****************
                  var loadCsgtCg = function(){
                      $('#div_loading').html("<img src='./images/loading.gif'>");
                      var ngaybatdau = $('#s_ngaybatdau').val();
                      var ngayketthuc = $('#s_ngayketthuc').val();
                      var maonline = $('#s_maonline').val();
                      var manhan = $("#s_manhan").val();
                      var bnbd = $('#s_bnbd').val();
                      var bbd = $('#s_bbd').val();
                      var mabuudien = $('#s_mabuudien').val();
                      var noinhan = $('#s_noinhan').val();
                      var cityid = $('#select_city').val();
                      searchList.splice(0,searchList.length);
                      searchList.push(ngaybatdau);searchList.push(ngayketthuc);searchList.push(maonline);
                      searchList.push(manhan);searchList.push(bnbd);searchList.push(bbd);
                       searchList.push(mabuudien);searchList.push(noinhan);searchList.push(cityid);
                       $body.addClass("loading");
                      $.ajax({
                        url : "vanthu",
                        type : "GET",
                        data : {
                                         "action" : "loadvtcsgtluu",page: 1,ngaybatdau: ngaybatdau,ngayketthuc: ngayketthuc,
                                         maonline: maonline,manhan: manhan, bnbd: bnbd,bbd: bbd,mabuudien: mabuudien,
                                         noinhan: noinhan,cityid: cityid
                                 },
                         dataType : "json",
                         success: function (data) {
                             $body.removeClass("loading");
                             $('#div_loading').empty();
                             //console.log(data);
                             $('#tb_vtcsgt tbody').html(data.data);
                             var totalPages = data.totalpage;
                                var currentPage = data.currentpage;
                                $pagination.twbsPagination('destroy');
                                $pagination.twbsPagination($.extend({}, defaultOpts, {
                                    startPage: currentPage,
                                    totalPages: totalPages,
                                    onPageClick: function (event, page) {
                                     //   loadCsgtCg(page);
                                     $body.addClass("loading");
                                            $.ajax({
                                                   type: "GET",
                                                   url:"vanthu",
                                                   dataType : "json",
                                                   data:{"action":"loadvtcsgtluu","page":page,ngaybatdau: searchList[0],ngayketthuc: searchList[1],
                                                   maonline: searchList[2],manhan: searchList[3], bnbd: searchList[4],bbd: searchList[5], mabuudien: searchList[6],
                                                    noinhan: searchList[7],cityid: searchList[8] },
                                                   success: function (data) { 
                                                       $body.removeClass("loading");
                                                       $('#tb_vtcsgt tbody').html(data.data);
                                                   }
                                           }); 
                                    }
                                }));
                         }
                   });
                };
                
                var inputEnter =  function(e){
                  if(e.keyCode ==13)  {
                      $('#btn_Search').click();
                  }
                };
                var saveDon =  function(dt){
                    var data = $('#tb_vtcsgt').find('.dt'+dt);
                      var ngaygui = data.eq(0).find('input').val();
                      var mabuudien = data.eq(1).find('input').val();
                      var bennhan = data.eq(2).find('input').val();
                      var noinhan = data.eq(3).find('input').val();
                      var ghichu = data.eq(6).find('input').val();
                      console.log(ngaygui);console.log(mabuudien);console.log(bennhan);
                      console.log(noinhan);console.log(ghichu);
                      $.ajax({
                            url : "vanthu",
                            type : "GET",
                            data : {
                                             "action" : "updatevtcsgt",ngaygui: ngaygui,mabuudien:  mabuudien,bennhan: bennhan,
                                             noinhan: noinhan,ghichu: ghichu
                                     },
                             success: function (data) {
                                 loadCsgtCg();
                             },error: function(data){
                                  console.log("ERROR = "+data);
                             }
                       });
                };
                var clearDon = function(dt){
                    var data = $('#tb_vtcsgt').find('.dt'+dt);
                    var ngaygui = data.eq(0).find('input').val();
                    data.eq(0).html(ngaygui);
                    var mabuudien = data.eq(1).find('input').val();
                    data.eq(1).html(mabuudien);
                    var bennhan = data.eq(2).find('input').val();
                    data.eq(2).html(bennhan);
                    var noinhan = data.eq(3).find('input').val();
                    data.eq(3).html(noinhan);
                    var ghichu = data.eq(6).find('input').val();
                    data.eq(6).html(ghichu);
                     data.eq(8).find('p:first').html('<img src=\"./images/document_edit.png\"  onclick=\"editDon('+dt+')\">');
                };
                var addMore = function(dt){
                    $body.addClass("loading");
                    $.ajax({
                            url : "vanthu",
                            type : "GET",
                            dataType: 'json',
                            data : {
                                             "action" : "loadvtsgtedit",vtid : dt
                                     },
                             success: function (data) {
                                    $body.removeClass("loading");
                                    $('#ngaygoi').val(data.ngaygoi);$('#vtgt_noinhan').val(data.csgtname);$('#csgt_diachi').val(data.csgtdc);
                                    $('#sdb').val(data.mabuudien);$('#ghichu').val(data.ghichu);$('#csgt_dcid').val(data.csgtdcid);
                             },error: function(data){
                                  console.log("ERROR = "+data);
                             }
                       });
                    $('#idvtcsgt').val(dt);
                    var data = $('#tb_vtcsgt').find('.dt'+dt);
                    var noinhan = data.eq(2).text();
                    $('#myModal h4').html(noinhan+'<input type="text" id="idvtcsgt" value="'+dt+'" hidden>');    
                    $('#tb_vtaddcsgt tbody').empty();
                    $('#myModal').modal('show');
                };
                
                $('#btn_Excel').on('click',function(){
                    var ngaybatdau = $('#s_ngaybatdau').val();
                    var ngayketthuc = $('#s_ngayketthuc').val();
                    var maonline = $('#s_maonline').val();
                    var manhan = $("#s_manhan").val();
                    var bnbd = $('#s_bnbd').val();
                    var bbd = $('#s_bbd').val();
                    var mabuudien = $('#s_mabuudien').val();
                    var noinhan = $('#s_noinhan').val();
                    var cityid = $('#select_city').val();
                    window.location="vanthu?action=csgtblexcel&ngaybatdau="+ngaybatdau+"&ngayketthuc="+ngayketthuc
                            +"&maonline="+maonline+"&manhan="+manhan+"&bnbd="+bnbd+"&bbd="+bbd+"&mabuudien="+mabuudien
                            +"&noinhan="+noinhan+"&cityid="+cityid;
                });
                
                var searchAddMore = function(){
                    $body.addClass("loading");
                    var $paginationAdd = $('#ul_addpage');
                    var defaultAddOpts = {
                        totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'Trang cuối',
                    next: 'TIếp',
                    last: 'Sau',
                    loop: false,
                    initiateStartPageClick: false
                    };
                 $paginationAdd.twbsPagination(defaultAddOpts);
                      var maonline = $('#s_addmaonline').val();
                      var manhan = $("#s_addmanhan").val();
                      var bnbd = $('#s_addbnbd').val();
                      $.ajax({
                        url : "vanthu",
                        type : "GET",
                        data : {
                                         "action" : "csgtcg",page: 1,maonline: maonline,manhan: manhan,bnbd: bnbd
                                 },
                         dataType : "json",
                         success: function (data) {
                             //console.log(data);
                             $body.removeClass("loading");
                             $('#tb_vtaddcsgt tbody').html(data.data);
                             var totalPages = data.totalpage;
                                var currentPage = data.currentpage;
                                $paginationAdd.twbsPagination('destroy');
                                $paginationAdd.twbsPagination($.extend({}, defaultAddOpts, {
                                    startPage: currentPage,
                                    totalPages: totalPages,
                                    onPageClick: function (event, page) {
                                       // loadCsgtCg(page);
                                       $body.addClass("loading");
                                       var maonline = $('#s_addmaonline').val();
                                        var manhan = $("#s_addmanhan").val();
                                        $.ajax({
                                          url : "vanthu",
                                          type : "GET",
                                          data : {
                                                           "action" : "csgtcg",page: page,maonline: maonline,manhan: manhan,bnbd: bnbd
                                                   },
                                           dataType : "json",
                                           success: function (data) {
                                               //console.log(data);
                                               $body.removeClass("loading");
                                               $('#tb_vtaddcsgt tbody').html(data.data);
                                               
                                           }
                                     });
                                    }
                                }));
                         }
                   });
                };
                var  addDonCSGT = function(){
                   // console.log(donid);
                    
                    if(_donSelect.length ==0){
                        alert("Chưa chọn đơn thêm vào");
                        return false;
                    }else{
                        console.log("RELOAD");
                        var vtcsgtid = $('#idvtcsgt').val();
                        $.ajax({
                            url : "vanthu",
                            type : "GET",
                            data : {
                                             "action" : "addmorecsgt",vtcsgtid: vtcsgtid,donid:  _donSelect
                                     },
                             success: function (data) {
                                 loadCsgtCg();
                             },error: function(data){
                                  console.log("ERROR = "+data);
                             }
                       });
                    }
                };
                
                $('#tb_vtaddcsgt').on('click',":checkbox",function(){
                    if($(this).is(':checked')){
                        _donSelect.push($(this).val());
                    }else{
                        for(var i=0; i< _donSelect.length;i++){
                            if(_donSelect[i] == $(this).val()){
                                _donSelect.splice(i,1);
                            }
                        }
                    }
                });
                var delDon = function(id,row,csid){
                    _donDelSelect.push(id);
                    if(row ==='1'){
                        xoaVt = "y";
                        vtcsgtid = csid;
                    }
                     $( "#deldialog" ).dialog( "open" );
                    
                };
                
                var checkMulti = function(){
                    if($(this).is(':checked')){
                        var id = $(this).parent().find('input.donid_Class').val();
                        _donDelSelect.push(id);
                         var dt = $(this).val();
                        var data = $('#tb_vtcsgt').find('.dt'+dt);
                        data.eq(7).find('p:last').show();
                    }else{
                        var id = $(this).parent().find('input.donid_Class').val();
                        for(var i=0; i< _donDelSelect.length;i++){
                            if(_donDelSelect[i] == id){
                                _donDelSelect.splice(i,1);
                            }
                            if(_donDelSelect.length ==0){
                                console.log(_donDelSelect);
                                var data = $('#tb_vtcsgt').find('.dt'+$(this).val());
                                data.eq(7).find('p:last').hide();
                            }
                        }
                    }
                   
                };
                
                var delMulti = function(dt){
                    console.log(_donDelSelect)
                    if(_donDelSelect.length ==0){
                        alert("Chưa chọn đơn xóa");
                    }else{
                        $( "#deldialog" ).dialog( "open" );
                    }
                };
                $( "#deldialog" ).dialog({
                    modal: true,
                    autoOpen: false,
                    show: {
                      effect: "blind",
                      duration: 1000
                    },
                    hide: {
                      effect: "explode",
                      duration: 1000
                    },
                    buttons: {
                        Đóng: function() {
                          $( this ).dialog( "close" );
                        },
                        Xóa: function() {
                          $.ajax({
                                url : "vanthu",
                                type : "GET",
                                data : {
                                                 "action" : "delmultidon",donid:  _donDelSelect
                                         },
                                 success: function (data) {
                                    console.log(data);
                                     $( "#deldialog" ).dialog( "close" );
                                      _donDelSelect.splice(0,_donDelSelect.length);
                                      if(xoaVt ==='y'){
                                          $.ajax({
                                                url : "vanthu",
                                                type : "GET",
                                                data : {
                                                                 "action" : "xoavtcsgt",vtcsgtid:  vtcsgtid
                                                         },
                                                 success: function (data) {
                                                     $('#result_add').html("Xóa đơn thành công");
                                                 },error: function(data){
                                                      //console.log("ERROR = "+data);
                                                      $('#result_add').html("Xóa đơn không thành công");
                                                 }
                                           });
                                      }
                                      xoaVt = "";
                                      vtcsgtid = "";
                                     loadCsgtCg();
                                 },error: function(data){
                                      console.log("ERROR = "+data);
                                 }
                           });
                        }
                    }
                  });
                  $( "#delVTdialog" ).dialog({
                    modal: true,
                    autoOpen: false,
                    show: {
                      effect: "blind",
                      duration: 1000
                    },
                    hide: {
                      effect: "explode",
                      duration: 1000
                    },
                    buttons: {
                        Đóng: function() {
                          $( this ).dialog( "close" );
                        },
                        Xóa: function() {
                          $.ajax({
                                url : "vanthu",
                                type : "GET",
                                data : {
                                                 "action" : "xoadonvavt",vtcsgtid:  vtcsgtid
                                         },
                                 success: function (data) {
                                     $( "#delVTdialog" ).dialog( "close" );
                                     loadCsgtCg();
                                 },error: function(data){
                                      console.log("ERROR = "+data);
                                 }
                           });
                        }
                    }
                  });
                 var delVTCSGT = function(id){
                     vtcsgtid = id;
                      $( "#delVTdialog" ).dialog( "open" );
                 };
                 $('#btn_updatevtcsgt').on('click',function(){
                     var ngaygoi = $('#ngaygoi').val();
                     var csgdcid = $('#csgt_dcid').val();
                     var mabuudien = $('#sdb').val();
                     var ghichu = $('#ghichu').val();
                     var vtid = $('#idvtcsgt').val();
                     $.ajax({
                            url : "vanthu",
                            type : "GET",
                            data : {
                                             "action" : "updatevtcsgt",ngaygui:  ngaygoi,csgtdcid : csgdcid, mabuudien: mabuudien,ghichu :ghichu,vtid: vtid
                                     },
                             success: function (data) {
                                 $( "#delVTdialog" ).dialog( "close" );
                                 loadCsgtCg();
                             },error: function(data){
                                  console.log("ERROR = "+data);
                             }
                       });
                 });
                $('#tb_vtcsgt').on('click',':checkbox',checkMulti);
                $('#btn_AddCsgt').on('click',addDonCSGT);
                $('#btn_SCsgt').on('click',searchAddMore);
                $("#s_ngaybatdau").on("keydown",inputEnter);
                $("#s_ngayketthuc").on("keydown",inputEnter);
                $("#s_maonline").on("keydown",inputEnter);
                $("#s_manhan").on("keydown",inputEnter);
                $("#s_bnbd").on("keydown",inputEnter);
                $("#s_bbd").on("keydown",inputEnter);
                $("#s_noinhan").on("keydown",inputEnter);

                $('#btn_Search').on('click',loadCsgtCg);
                
               /*********
                 * autocomplete
                 **/
                var csgtauto ={     
                    source : function(request, response) {
                        $.ajax({
                                url : "vanthu",
                                type : "GET",
                                data : {
                                        "action": "diachi",diachi: request.term
                                },
                                dataType : "json",
                                success : function(data) {
                                        response(data);
                                }
                        });
                    },
                    select: function (event, ui) {
                        $("#vtgt_noinhan").val( ui.item.name );
                        //$(this).autocomplete('close');
                        $("#csgt_diachi").val(ui.item.diachi);
                        $("#csgt_dcid").val(ui.item.dcid);
                       // $(".ui-menu-item").hide();
                        return false;
                    },minLength: 2
                };
                var csgt = $("#vtgt_noinhan").autocomplete(csgtauto).data('ui-autocomplete');
                csgt._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 csgt._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  csgt._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.diachi +"</td>" )
                      .appendTo( table );
                  };
        </script>
    </body>
</html>
