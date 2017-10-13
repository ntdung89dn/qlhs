<%-- 
    Document   : dathuphi
    Created on : Jul 5, 2016, 5:19:49 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            .ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year {
                    color: #000000;
                    font-size: 14px;
                    font-weight: bold;
                }
                #loadingbnbd ,#loadingbbd{
                height: 500px; 
                overflow-y: scroll; 
              }
             #tbctp_bnbd td,#ctp_bbd td{
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
                 %>
            <li class="active"><a href="thuphi.jsp?page=chuathuphi">Thu Phí Lệ Phí</a></li>
            
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
                <li class="sub-menu"><a href="thuphi.jsp?page=chuathuphi">Chưa thu phí</a></li>
                <li class="sub-menu"><a class="active" href="thuphi.jsp?page=dathuphi">Đã thu phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=tkbl">Thống kê biên lai</a></li>
                <li class="sub-menu" ><a href="thuphi.jsp?page=tkbc">Thống kê báo có</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=thcn">Tổng hợp công nợ</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=xembaophi">Xem thông báo phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=khongcostk">Đơn chưa thu phí không có số tài khoản</a></li>
        </ul> <br>
        <div style="text-align: center"><span style="color: red;font-weight: bold;" id="result_dathuphi"></span></div>
        <div class="panel panel-default">
            <!-- Default panel contents -->
            <div class="panel-heading">Thông tin thu phí</div>
            <form>
            <div class="panel-body">
                <!-- nhập báo có -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="form-group row" style="margin-bottom: 0px;">
                          <div class="col-xs-1">
                                <h4 class="panel-title pull-left">
                                    <a data-toggle="collapse" href="#panel_baoco">Báo có</a>
                                </h4>
                          </div>
                            <div class="col-xs-3">
                                    <select  id="ctp_type_luubc" class="form-control pull-left" style="float: right;">
                                              <option value="0">Xem báo có</option>
                                              <option value="1">Cập nhật báo có</option>
                                      </select>
                            </div>
                            <div class="col-xs-3">
                                    <select  id="ctp_sobcselect" class="form-control pull-left" style="float: right;">
                                      </select>
                            </div>
                            <div class="col-xs-1">
                                <img src="./images/fileadd.png" title="Thêm báo có" id="img_searchbc_add">
                                <img src="./images/document_delete.png" title="Xóa báo có" id="img_searchbc_sub">
                            </div>
                            
                        </div>
                        
                    </div>
                    <div id="panel_baoco" class="panel-collapse collapse">
                      <div class="panel-body">
                          <div class="form-group row">
                              <div class="col-xs-3">
                                  <input type="checkbox" id="cb_khsave"> Lưu khách hàng
                              </div>
                            <div class="col-xs-3">
                                <input class="form-control" type="text" value="" id="ctp_khachhangname" placeholder="Tên khách hàng" readonly>
                            </div>
                            <div class="col-xs-3">
                                <input class="form-control" type="text" value="" id="ctp_khachhangdiachi" placeholder="Địa chỉ khách hàng" readonly>
                            </div>
                            <div class="col-xs-2">
                                <input class="form-control" type="text" value="" id="ctp_khachhangmaso" placeholder="Mã khách hàng" readonly>
                                <input  type="text" value="" id="ctp_baocokhid" hidden>
                            </div>
                           </div>
                        <div class="form-group row">
                          <div class="col-xs-1">
                          </div>
                          <label for="ctp_ngaybaoco" class="col-xs-2 col-form-label">Ngày BC:</label>
                          <div class="col-xs-3">
                            <input class="form-control" type="text" value="" id="ctp_ngaybaoco">
                          </div>
                          <label for="ctp_sobaoco" class="col-xs-1 col-form-label">Số BC:</label>
                          <div class="col-xs-3">
                            <input class="form-control" type="text" value="" id="ctp_sobaoco">
                          </div>
                      </div>
                      <div class="form-group row">
                          <div class="col-xs-1">
                          </div>
                          <label for="ctp_nguoiplbaoco" class="col-xs-2 col-form-label">Người phát lệnh:</label>
                          <div class="col-xs-7">
                            <input class="form-control" type="text" value="" id="ctp_nguoiplbaoco">
                          </div>
                      </div>
                      <div class="form-group row">
                          <div class="col-xs-1">
                          </div>
                          <label for="ctp_ndbaoco" class="col-xs-2 col-form-label">ND BD:</label>
                          <div class="col-xs-7">
                            <input class="form-control" type="text" value="" id="ctp_ndbaoco">
                          </div>
                      </div>
                      <div class="form-group row">
                          <div class="col-xs-1">
                          </div>
                          <label for="ctp_sotien" class="col-xs-2 col-form-label">Số tiền:</label>
                          <div class="col-xs-3">
                            <input class="form-control" type="text" value="" id="ctp_sotien">
                          </div>
                          <label for="ctp_stconlai" class="col-xs-1 col-form-label" >Còn lại :</label>
                          <div class="col-xs-3">
                              <input class="form-control" type="text" value="" id="ctp_stconlai" readonly>
                          </div>
                      </div>
                          <div class="form-group row">
                          <div class="col-xs-1">
                          </div>
                      </div>
                   <div class="form-group row">
                        <div class="col-xs-3">
                        </div>
                        <div class="col-xs-2">
                            <!--<button type="button" id="btn_saveBC" class="btn btn-primary" style="width: 100%">Lưu báo có</button> -->
                        </div>
                       <div class="col-xs-1">
                        </div>
                        <div class="col-xs-2">
                            <!--<button type="button" id="btn_resetBC" class="btn btn-primary" style="width: 100%">Hủy bỏ</button> -->
                        </div>
                </div>
               </div>
                    </div>
                  </div>
                <!-- Nhập biên lai -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                      <h4 class="panel-title">
                        <a data-toggle="collapse" href="#panel_bienlai">Biên lai</a>
                      </h4>
                    </div>
                    <div id="panel_bienlai" class="panel-collapse collapse">
                      <div class="panel-body">
                          <div class="form-group row">
                                <div class="col-xs-1">
                                    <input type="text" id="dtp_bienlaiid" hidden>
                                </div>
                                <label for="ctp_ngaynop" class="col-xs-2 col-form-label">Ngày nộp:</label>
                                <div class="col-xs-3">
                                  <input class="form-control" type="text" value="" id="ctp_ngaynop">
                                </div>
                                <label for="ctp_sohieu" class="col-xs-1 col-form-label">Số hiệu:</label>
                                <div class="col-xs-3">
                                  <input class="form-control" type="text" value="" id="ctp_sohieu">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-xs-1">
                                </div>
                                <label for="ctp_tendonvi" class="col-xs-2 col-form-label">Tên đơn vị:</label>
                                <div class="col-xs-7">
                                  <input class="form-control" type="text" value="" id="ctp_tendonvi">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-xs-1">
                                </div>
                                <label for="ctp_diachi" class="col-xs-2 col-form-label">Địa chỉ:</label>
                                <div class="col-xs-7">
                                  <input class="form-control" type="text" value="" id="ctp_diachi">
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-xs-1">
                                </div>
                                <label for="ctp_lydonop" class="col-xs-2 col-form-label">Lý do nộp:</label>
                                <div class="col-xs-7">
                                  <input class="form-control" type="text" value="" id="ctp_lydonop">
                                  <input type="text" value="" id="ctp_donids" hidden>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-xs-1">
                                </div>

                                <div class="col-xs-7">
                                    <label class="custom-control custom-radio">
                                        <input id="ctprd_tructiep" name="loaithanhtoan" type="radio" class="custom-control-input" value="1">
                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">Trực tiêp</span>
                                    </label>
                                    <label class="custom-control custom-radio">
                                        <input id="ctprd_chuyenkhoan" name="loaithanhtoan" type="radio" class="custom-control-input" value="2">
                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">Chuyển khoản</span>
                                    </label>
                                    <label class="custom-control custom-checkbox">
                                        <input type="checkbox" id="ctpcb_thongbaophi" class="custom-control-input">
                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">Thông báo phí</span>
                                    </label>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-xs-1">
                                </div>
                                <label for="ctp_tongtien" class="col-xs-2 col-form-label">Tổng tiền:</label>
                                <div class="col-xs-3">
                                    <input class="form-control" type="text" value="" id="ctp_tongtien">
                                </div>
                            </div>
                          <div class="form-group row">
                                <div class="col-xs-3">
                                </div>
                                <div class="col-xs-3">
                                    <button type="button" id="btn_ctpSave" class="btn btn-primary" style="width: 100%">Lưu lại</button>
                                </div>

                                <div class="col-xs-3">
                                    <button type="button" id="btn_ctpReset" class="btn btn-primary" style="width: 100%">Hủy bỏ</button>
                                </div>
                            </div>
                      </div>
                    </div>
                  </div>
                
                
                
            </div>
        </form>
        </div>
        <div>
            <table class="table" >
                <tbody>
                    <tr>
                        <td>Lọc Dữ Liệu</td>
                        <td><input class="form-control" type="text" value="" id="dtp_ngaybatdau" placeholder="Ngày bắt đầu"></td>
                        <td><input class="form-control" type="text" value="" id="dtp_ngaykethuc" placeholder="Ngày kết thúc"></td>
                        <td>
                            <select class="form-control" id="dtp_loaihinhnhan">
                                <option value="0">Loại hình nhận</option>
                                <option value="1">CE</option>
                                <option value="2">CF</option>
                                <option value="3">CT</option>
                                <option value="4">CB</option>
                            </select>
                        </td>
                        <td><input class="form-control repalacedot" type="text" value="" id="dtp_maloainhan" placeholder="Mã loại hình nhận"></td>
                        <td>
                            <select class="form-control" id="dtp_loaidk">
                                <option value="0">Loại đăng ký</option>
                                <option value="1">BD</option>
                                <option value="2">CSGT</option>
                                <option value="3">TT</option>
                            </select>
                        </td>
                        <td><input class="form-control repalacedot" type="text" placeholder="Số hiệu"value="" id="dtp_sohieu"></td>
                    </tr>
                </tbody>
            </table>
            <table style="width: 100%">
            <tbody>
                <tr>
                    <td style="width: 10%">Bên Nhận BĐ</td>
                    <td style="width: 20%"><input class="form-control" type="text" value="" id="searchBNBD"></td>
                    <td style="width: 5%"></td>
                    <td style="width: 3%"><img src="./images/check-all.png" id="img_checkall"></td>
                    <td style="width: 5%"><img src="./images/check-all-delete.png" id="img_uncheckall"></td>
                    <td style="width: 5%"><img src="./images/table-excel.png" id="img_exportExel" data-toggle="modal" data-target="#excelModal"></td>
                    <td style="width: 10%">Bên BĐ:</td>
                    <td style="width: 25%"><input class="form-control" type="text" value="" id="searchBBD"></td>
                    <td style="width: 3%"><img src="./images/check-all.png" id="img_checkallBBD"></td>
                    <td style="width: 3%"><img src="./images/check-all-delete.png" id="img_uncheckallBBD"></td>
                    <td style="width: 5%"></td>
                </tr>
                <tr>
                    <td colspan="6" valign="top">
                        <div id="loadingbnbd"><div id="btp_loading"></div>
                            <table border="1" id="tbctp_bnbd" style="width: 100%;">
                                <thead style="background-color: #87CEFA">
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên đơn vị thu phí</th>
                                        <th>Chọn</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
                            
                            
                        </div>
                        
                    </td>
                    <td colspan="5" valign="top">
                        <div id="loadingbbd">
                            <div id="bbdtp_loading"></div>
                            <table border="1" id="ctp_bbd" style="width: 100%;">
                                <thead style="background-color: #87CEFA">
                                    <tr>
                                        <th>STT</th>
                                        <th>Loại hình nhận</th>
                                        <th>Ngày</th>
                                        <th>Bên bảo đảm</th>
                                        <th>ST</th>
                                        <th>TT</th>
                                        <th>Xóa</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            
                        </div>
                    </td>
                    
                </tr>
                <tr><td colspan="5" style="text-align: right">Tổng cộng: <span id="tongtien_span"></span></td><td></td>
                    <td  colspan="4"><div class="form-group">
                                                <label for="sort" class="col-sm-4 control-label"> Tổng số hóa đơn </label>
                                                <div class="col-sm-4">
                                                    <select class="form-control" name="sort" id="tshoadon">
                                                    </select>
                                                 </div>
                                            </div></tr>
            </tbody>
        </table>
            
            <!--- Tạo excel modal excelModal-->
        <div class="modal fade" id="excelModal" role="dialog">
                <div class="modal-dialog">

                  <!-- Modal content-->
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal">&times;</button>
                      <h4 class="modal-title">Xuất file EXCEL</h4>
                    </div>
                    <div class="modal-body">
                      <p>
                          <label for="sel1">Chọn mục xuất Excel:</label>
                                <select class="form-control" id="select_export_Excel">
                                    <option value="1">Bên nhận bảo đảm</option>
                                    <option value="2">Bên Bảo đảm</option>
                                    <option value="3">Đầy đủ</option>
                                    <option value="4">Xuất file thu phí theo cục(Mã khách hàng + mã đơn online)</option>
                                </select>
                      <div id="search_don"></div>
                      <div><span id="span_error_Export" style="color: red;font-weight: bold;"></span></div>
                      </p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="btn_exportFile">Xuất file Excel</button>
                      <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                    </div>
                  </div>

                </div>
              </div>
            
            
            
            <div id="dialog-confirm" title="Thay đổi thông tin thu phí">
                <input type="text" id="dontpid_xoa" value="" hidden>
                <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Bạn đang thực hiện Xóa đơn đã thu phí. <br>
                    Bạn có muốn <span style="color: red;"><strong>xóa</strong></span> đơn đã thu phí không ?</p>
              </div>
            <div id="deletedon_popup" title="Xóa đơn">
                <p><span id="delete_don_content" style="color: red;font-weight: bold;"></span></p>
             </div>
            <script>
                
                var days = 60;
                var date = new Date();
                 var last = new Date(date.getTime() - (days * 24 * 60 * 60 * 1000));
                 $('#ctp_ngaybaoco').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true,
                    onClose: function(){
                        this.focus();
                    }
                });
                $('#ctp_ngaynop').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true,
                    onClose: function(){
                        this.focus();
                    }
                }).datepicker("setDate", last);
               $('#dtp_ngaybatdau').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true,
                    onClose: function(){
                        this.focus();
                    }
                }).datepicker("setDate", last);
                $('#dtp_ngaykethuc').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true,
                    onClose: function(){
                        this.focus();
                    }
                });
                // lấy toàn bộ báo có number
                function loadAllBaoCo(){
                    $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        data: {action: "loadallbaoco",bienlaiid : $('#tshoadon').val()},
                        success: function (data) {
                            $('#ctp_sobcselect').val(data);
                        },error: function (xhr, ajaxOptions, thrownError) {
                              console.log("STATUS = "+xhr.status);
                              console.log(thrownError);
                            }
                    });
                }
                // td click
                $(document).on('click','.click_td',function() {
                   // var donids = $(this).parent().parent().find('input').val();
                    var bienlais= $(this).parent().find('.bien_lais').val();
                      $('.click_td').css('color', 'black');
                        $(this).css('color', 'red');
                      //  $('#ctp_bbd tbody').empty();
                      $('#ctp_bbd tbody').empty();
                      $('#bbdtp_loading').html("<img src='./images/loading.gif'>");
                      $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            dataType: "json",
                            data: {action: "loadInforbdtp",bienlais : bienlais},
                            success: function (data) {
                                console.log(data);
                                $('#bbdtp_loading').empty();
                               $('#tshoadon').html(data.sohieuop);
                              // $('#ctp_bbd tbody').empty();
                               $('#ctp_bbd tbody').html(data.donnopphi);
                               $('#ctp_ngaybaoco').val(data.ngaybc);$('#ctp_sobaoco').val(data.bcnumber);$('#ctp_sotien').val(data.bctotal);
                               $('#ctp_stconlai').val(data.bcsodu);$('#ctp_diachi').val(data.diachi);$('#ctp_lydonop').val(data.lydo);
                               $('#ctp_tongtien').val(data.bienlaitotal);$('#ctp_tendonvi').val(data.tendvi);$('#ctp_ngaynop').val(data.ngaybienlai);
                               $('#ctp_sohieu').val(data.sohieu);$('#ctp_ndbaoco').val(data.noidungbc);
                               $('#ctp_khachhangname').val(data.khname);$('#ctp_khachhangdiachi').val(data.khaddress);$('#ctp_khachhangmaso').val(data.khaccount);
                               $('#ctp_baocokhid').val(data.khid);
                               $('#dtp_bienlaiid').val(data.dtpid);
                               $('#ctp_sobcselect').html("<option value='"+data.bcid+"'>"+data.bcnumber+"</option>");
                               var loaithanhtoan = data.loaithanhtoan;
                               console.log(loaithanhtoan);
                               $('input:radio[name=radio]').prop('checked',false);
                               switch (loaithanhtoan) {
                                    case '1' :$('#ctprd_tructiep').prop('checked',true);
                                                    $('#ctpcb_thongbaophi').prop('checked',false);
                                        break;
                                    case '2' :$('#ctprd_tructiep').prop('checked',true);
                                                    $('#ctpcb_thongbaophi').prop('checked',true);
                                        break;
                                   case '3' :$('#ctprd_chuyenkhoan').prop('checked',true);
                                                    $('#ctpcb_thongbaophi').prop('checked',false);
                                        break;
                                   case '4' :$('#ctpcb_thongbaophi').prop('checked',true);
                                                $('#ctprd_chuyenkhoan').prop('checked',true);
                                        break;
                                    default: 
                                        break;
                                }
                            },error: function (xhr, ajaxOptions, thrownError) {
                                  console.log("STATUS = "+xhr.status);
                                  console.log(thrownError);
                             }
                        });
                        // Load toàn bộ báo có
                        loadAllBaoCo();
               });
               $('#tshoadon').on('change',function(){
                   var sohieu = $(this).val();
                   $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        dataType: "json",
                        data: {action: "loadthpsohieu",sohieu: sohieu },
                        success: function (data) {
                            // replace div's content with returned data
                            console.log(data);
                            $('#ctp_ngaynop').val(data.ngaytp);
                            $('#ctp_sohieu').val(data.sohieu);
                            $('#ctp_tendonvi').val(data.tdv);
                            $('#ctp_diachi').val(data.diachi);
                             $('#ctp_lydonop').val(data.lydo);
                             if(data.loaitt ==1){
                                 $('#ctprd_tructiep').attr("checked",true);
                                 $('#ctpcb_thongbaophi').attr("checked",false);
                             }else if(data.loaitt ==2){
                                 $('#ctprd_tructiep').attr("checked",true);
                                 $('#ctpcb_thongbaophi').attr("checked",true);
                             }else if(data.loaitt ==3){
                                 $('#ctprd_chuyenkhoan').attr("checked",true)
                                  $('#ctpcb_thongbaophi').attr("checked",false);
                             }else if(data.loaitt ==4){
                                 $('#ctprd_chuyenkhoan').attr("checked",true);
                                 $('#ctpcb_thongbaophi').attr("checked",true);
                             }
                             $('#ctp_tongtien').val(data.tongtien);
                             $('#ctp_donids').val(data.donis); 
                            $('#ctp_bbd tbody').html(data.data);
                            
                        }
                    });
               });
                var loadBenTp = function(){
                  var ngaybatdau = $('#dtp_ngaybatdau').val();
                  var ngayketthuc = $('#dtp_ngaykethuc').val();
                  var loaihinhnhan = $('#dtp_loaihinhnhan').val();
                  var manhan = $('#dtp_maloainhan').val();
                  var loaidk = $('#dtp_loaidk').val();
                  var sohieu = $('#dtp_sohieu').val();
                  var bennopphi = $('#searchBNBD').val();
                  $('#btp_loading').html("<img src='./images/loading.gif'>");
                  $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        data: {action: "loadnopphi",ngaybatdau: ngaybatdau,ngayketthuc: ngayketthuc,loaihinhnhan: loaihinhnhan,manhan: manhan,
                            loaidk: loaidk, sohieu: sohieu,bennopphi:bennopphi},
                        success: function (data) {
                            $('#btp_loading').empty();
                           // console.log("BEN THU PHI " +data);
                            $('#tbctp_bnbd tbody').html(data);
                        },error: function (xhr, ajaxOptions, thrownError) {
                              console.log("STATUS = "+xhr.status);
                              console.log(thrownError);
                            }
                    });
                };
                var getTongTien = function(){
                    
                    var infor = $(this).val();
                    
                };
                $(document).on('change', '.btpcheckbox', function() {
                    if(this.checked) {
                      // checkbox is checked
                      var infor = $(this).parent().parent().find('.bien_lais').val();
                      $('#btp_loading').html("<img src='./images/loading.gif'>");
                      $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "laytongtien",bienlais : infor},
                            success: function (data) {
                                $('#btp_loading').empty();
                               // console.log("BEN THU PHI " +data);
                               var tongtien = $('#tongtien_span').html();
                               tongtien = Number(tongtien) + Number(data);
                                $('#tongtien_span').html(tongtien);
                            },error: function (xhr, ajaxOptions, thrownError) {
                                  console.log("STATUS = "+xhr.status);
                                  console.log(thrownError);
                                }
                        });
                    }else{
                        var infor = $(this).parent().parent().find('.bien_lais').val();
                      $('#btp_loading').html("<img src='./images/loading.gif'>");
                      $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "laytongtien",bienlais : infor},
                            success: function (data) {
                                $('#btp_loading').empty();
                               // console.log("BEN THU PHI " +data);
                               var tongtien = $('#tongtien_span').html();
                               tongtien = Number(tongtien) - Number(data);
                                $('#tongtien_span').html(tongtien);
                                $('#tongtien_span').html(tongtien);
                            },error: function (xhr, ajaxOptions, thrownError) {
                                  console.log("STATUS = "+xhr.status);
                                  console.log(thrownError);
                                }
                        });
                    }
                });
                $('#img_checkall').on('click',function(){
                    $('#tbctp_bnbd').find('input.btpcheckbox').prop('checked',true);
                    totalPrice();
                });
                $('#img_uncheckall').on('click',function(){
                    $('#tbctp_bnbd').find('input.btpcheckbox').prop('checked',false);
                    $('#tongtien_span').val("");
                });
                var totalPrice = function(){
                    $('#btp_loading').html("<img src='./images/loading.gif'>");
                    var tongtien = $('#tongtien_span').html();
                    if(tongtien ==='' || tongtien === undefined){
                            tongtien = "0";
                        }
                    $('#tbctp_bnbd').find('input.btpcheckbox').each(function(){
                        var infor = $(this).val();
                        var name = infor.split("_")[0];
                        var diachi = infor.split("_")[1];
                        $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "laytongtien",name: name,diachi: diachi,type: total},
                            success: function (data) {
                                $('#btp_loading').empty();
                               // console.log("BEN THU PHI " +data);
                               tongtien = Number(tongtien) + Number(data);
                                
                            },error: function (xhr, ajaxOptions, thrownError) {
                                  console.log("STATUS = "+xhr.status);
                                  console.log(thrownError);
                                }
                        });
                    });
                    $('#tongtien_span').html(tongtien);
                    $('#btp_loading').empty();
                };
                
                function updateBaoCo(){
                    var isSaveKH = $('#cb_khsave').prop('checked');
                    var khid = $('#ctp_baocokhid').val();
                    var ngaybc = $('#ctp_ngaybaoco').val();
                    var sobc = $('#ctp_sobaoco').val();
                    var nguoipl = $('#ctp_nguoiplbaoco').val();
                    var ndbc = $('#ctp_ndbaoco').val();
                    var sotien = $('#ctp_sotien').val();
                    var tienthua = $('#ctp_stconlai').val();
                    var bcid = $('#ctp_sobcselect').val();
                    $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "updatebaocodtp",khid:  khid,ngaybc: ngaybc,sobc: sobc,nguoipl: nguoipl,ndbc: ndbc,sotien: sotien,tienthua: tienthua,savekh : isSaveKH,bcid : bcid},
                            success: function (data) {
                                $('#result_dathuphi').html(data);
                            },error: function (xhr, ajaxOptions, thrownError) {
                                  console.log("STATUS = "+xhr.status);
                                  console.log(thrownError);
                                }
                        });
                }
                
                function updateBienLai(){
                    var tendonvi = $('#ctp_tendonvi').val();
                    var diachi = $('#ctp_diachi').val();
                    var lydo = $('#ctp_lydonop').val();
                    var sohieu = $('#ctp_sohieu').val();
                    var loaithanhtoan = 0;
                    var loaitt = $('input[name="loaithanhtoan"]:checked').val();
                    var tbp = $('#ctpcb_thongbaophi').is(":checked");
                    if(parseInt(loaitt) === 1){
                        if(tbp){
                            loaithanhtoan =2;
                        }else{
                            loaithanhtoan =1;
                        }

                    }else  if(parseInt(loaitt) === 2){
                        if(tbp){
                            loaithanhtoan =4;
                        }else{
                            loaithanhtoan =3;
                        }
                    }
                    console.log($('#ctp_tongtien').val());
                    var tongtien = $('#ctp_tongtien').val().replace(/\./g,'');
                    console.log(tongtien);
                    var ngaynop = $('#ctp_ngaynop').val();
                    var bienlaiid = $('#dtp_bienlaiid').val();
                    $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "updatebienlaidtp",tendonvi: tendonvi,diachi: diachi,lydo: lydo,
                                thanhtoan: loaithanhtoan,tongtien: tongtien,ngaynop: ngaynop,blid:bienlaiid,sohieu: sohieu },
                            success: function (data) {
                                if(data === 'OK'){
                                    window.location = "./print/print_inbienlaimau.jsp";
                                    $('#result_dathuphi').append(data);
                                }
                                
                            },error: function (xhr, ajaxOptions, thrownError) {
                                  console.log("STATUS = "+xhr.status);
                                  console.log(thrownError);
                                }
                        });
                }
                var printBienLai = function(){
                    var typeSaveBc = $('#ctp_type_luubc').val();
                    if(typeSaveBc === '1'){
                        updateBaoCo();
                    }
                    updateBienLai();
                    var tendonvi = $('#ctp_tendonvi').val();
                    var diachi = $('#ctp_diachi').val();
                    var lydo = $('#ctp_lydonop').val();
                    var thanhtoan = $("input[name='loaithanhtoan']:checked").val();
                    console.log("LOAITHANH TOAN  = "+thanhtoan);
                    var tongtien = $('#ctp_tongtien').val();
                    
                    $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "printbienlai",tendonvi: tendonvi,diachi: diachi,lydo: lydo,thanhtoan: thanhtoan,tongtien: tongtien},
                            success: function (data) {
                                if(data === 'OK'){
                                    window.location = "./print/print_inbienlaimau.jsp";
                                }
                                
                            },error: function (xhr, ajaxOptions, thrownError) {
                                  console.log("STATUS = "+xhr.status);
                                  console.log(thrownError);
                                }
                        }); 
                    //window.location = "./print/print_inbienlaimau.jsp";
                };
                
                loadBenTp();
                $('#img_uncheckallBBD').on('click',function(){
                    $('#ctp_bbd').find('input.ctp_cb').prop('checked',false);
                    //totalPrice();
                    $('#ctp_bbd').find('input.ctp_cb').each(function(i,el){
                       $(this) .parent().parent().find('input.input_st').val("");
                    });
                    $('#ctp_tongtien').val("");
                });
                $('#img_checkallBBD').on('click',function(){
                    $('#ctp_bbd').find('input.ctp_cb').prop('checked',true);
                    var total = 0;
                    $('#ctp_bbd').find('input.ctp_cb').each(function(i,el){
                       $(this) .parent().parent().find('input.input_st').val($(this).val());
                       total += Number($(this).val());
                    });
                    $('#ctp_tongtien').val(total);
                });
                $(document).on('click','.ctp_cb',function(){
                   if($(this).is(':checked')) {
                       var total = $('#ctp_tongtien').val();
                        var sub = $(this).val();
                        var result = Number(total) +Number(sub);
                        $('#ctp_tongtien').val(result);
                   }else{
                       var total = $('#ctp_tongtien').val();
                        var sub = $(this).val();
                        var result = Number(total) -Number(sub);
                        $('#ctp_tongtien').val(result);
                   }
                    
                });
                var exportByBnbd = function(){
                 var fromday = $('#dtp_ngaybatdau').val();
                var today  = $('#dtp_ngaykethuc').val();
                var loaidk  = $('#dtp_loaidk').val();
                var loaihinhnhan = $('#dtp_loaihinhnhan').val();
                var manhan = $('#dtp_maloainhan').val();
                var searchBNBD = $('#searchBNBD').val();
                var sohieu = $('#dtp_sohieu').val();
                 window.location= "thuphiservlet?action=exportDTPBNBD&fromday="+fromday+"&today="+today+"&loaidk="+loaidk
                         +"&loaihinhnhan="+loaihinhnhan+"&manhan="+manhan+"&searchBNBD="+searchBNBD+"&sohieu="+sohieu;
             };
             
             var exportByBBD = function(){
                 var fromday = $('#dtp_ngaybatdau').val();
                var today  = $('#dtp_ngaykethuc').val();
                var loaidon  = $('#ctp_loaidon').val();
                var loaihinhnhan = $('#dtp_loaihinhnhan').val();
                var sothongbaophi = $('#ctp_sothongbaophi').val();
                var manhan = $('#dtp_maloainhan').val();
                var searchBNBD = $('#tbctp_bnbd').find('input:checkbox:checked').val();
                searchBNBD = searchBNBD.replace("&","%26");
              //  a//lert(searchBNBD);
               //  var donids = $('#tbctp_bnbd').find('input[id^="ctp_cb"]:checked').parent().parent().find('input[id^="ctp_donid"]').val();
                 window.location = "thuphiservlet?action=exportDTPBBD&fromday="+fromday+"&today="+today+"&loaidon="+loaidon
                         +"&loaihinhnhan="+loaihinhnhan+"&sothongbaophi="+sothongbaophi+"&manhan="+manhan+"&searchBNBD="+searchBNBD;
             };
             
             var exportAll = function(){
                 var fromday = $('#dtp_ngaybatdau').val();
                var today  = $('#dtp_ngaykethuc').val();
                var loaidk  = $('#dtp_loaidk').val();
                var loaihinhnhan = $('#dtp_loaihinhnhan').val();
                var manhan = $('#dtp_maloainhan').val();
                var searchBNBD = $('#searchBNBD').val();
                var sohieu = $('#dtp_sohieu').val();
                 window.location= "thuphiservlet?action=exportDTPAll&fromday="+fromday+"&today="+today+"&loaidk="+loaidk
                         +"&loaihinhnhan="+loaihinhnhan+"&manhan="+manhan+"&searchBNBD="+searchBNBD+"&sohieu="+sohieu;
             };
             
             
             // Xuất file thu phí theo cục
             var exportDonThuPhi = function(){
                 var fromday = $('#kts_ngaybatdau').val();
                 var today = $('#kts_ngaykethuc').val();
                 var loaihinhnhan = $('#kts_loaihinhnhan').val();
                 var loaithanhtoan = $('#kts_loaithanhtoan').val();
                 window.location = "thuphiservlet?action=exportThuphiCuc&fromday="+fromday+"&today="+today
                         +"&loaihinhnhan="+loaihinhnhan+"&loaithanhtoan="+loaithanhtoan;
             };
                // chonj vaf export file excel
                $('#btn_exportFile').on('click',function(){
                    var typeExport = $('#select_export_Excel').val();
                        if(typeExport ==='1'){
                               exportByBnbd();
                       }else if(typeExport ==='2'){
                           var bnbdSize = $('#tbctp_bnbd').find('input:checkbox:checked').size();
                           if(bnbdSize ===0){
                               $('#span_error_Export').html("Chưa chọn Bên nhận bảo đảm");
                           }else if(bnbdSize > 1){
                               $('#span_error_Export').html("Chỉ chọn 1 bên nhận bảo đảm khi xuất file");
                           }else{
                               exportByBBD();
                           }
                       }else if(typeExport ==='3'){
                           exportAll();
                       // alert("Chức năng chưa được cập nhật");
                       }else if(typeExport === '4'){
                           exportDonThuPhi();
                       }
                });
                $('#btn_ctpSave').on('click',printBienLai);
        /**        $('#dtp_ngaybatdau').on('change paste',loadBenTp);
                $('#dtp_ngaykethuc').on('change paste',loadBenTp);
                $('#dtp_maloainhan').on('change paste',loadBenTp);
                $('#dtp_sohieu').on('change paste',loadBenTp);
                $('#searchBNBD').on('change paste',loadBenTp);**/
                $('#dtp_ngaybatdau,#dtp_ngaykethuc,#dtp_maloainhan,#dtp_sohieu,#searchBNBD').on('keydown',function(e){
                    if(e.keyCode ===13){
                        loadBenTp();
                    }
                });
                $('#select_export_Excel').on('change',function(){
                   var selectVal = $(this).val();
                   if(selectVal === '4'){
                       var daySearch = date.getDate();
                       if(daySearch < 10){
                           daySearch = "0"+daySearch;
                       }
                       var monthSearch = date.getMonth();
                       if(monthSearch < 10){
                           monthSearch = "0"+monthSearch;
                       }
                       var yearSearch  = date.getFullYear();
                       var searchTime = daySearch+"-"+monthSearch+"-"+yearSearch;
                       var searchHtml = '<table class="table " style="width: 550px;">'+
                '<tbody>'+
                        '<td><input class="form-control" type="text" value="" id="kts_ngaybatdau" placeholder="Ngày bắt đầu" ></td>'+
                        '<td><input class="form-control" type="text" value="" id="kts_ngaykethuc" placeholder="Ngày kết thúc"></td>'+
                        '<td><select class="form-control" id="kts_loaihinhnhan">'+
                                '<option value="0">Loại hình nhận</option>'+
                                '<option value="CE">CE</option>'+
                                '<option value="CF">CF</option>'+
                                '<option value="CT">CT</option>'+
                                '<option value="CB">CB</option>'+
                            '</select></td>'+
                        '<td><select class="form-control" id="kts_loaithanhtoan">'+
                        '<option value="0">Thanh Toán</option><option value="1,2">Tiền mặt</option>'+
                '<option value="3,4">Chuyển khoản</option></select></td>'+
                    '</tr></tbody></table>';
                       $('#search_don').html(searchHtml);
                       $('#kts_ngaybatdau').val(searchTime);
                   }else{
                       $('#search_don').empty();
                   }
                });
                $('#kts_ngaybatdau').on('click',function(){
                    $(this).datepicker({
                            dateFormat: 'dd-mm-yy',          
                            monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                                "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                                "Tháng 10", "Tháng 11", "Tháng 12" ],
                            dayNamesMin: ["CN","2","3","4","5","6","7"],
                            changeMonth: true,
                            changeYear: true
                        });
                });
                $(document).on('focus','#kts_ngaybatdau,#kts_ngaykethuc',function(){
                    $(this).datepicker({
                            dateFormat: 'dd-mm-yy',          
                            monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                                "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                                "Tháng 10", "Tháng 11", "Tháng 12" ],
                            dayNamesMin: ["CN","2","3","4","5","6","7"],
                            changeMonth: true,
                            changeYear: true
                        });
                });
                $('#deletedon_popup').dialog({
                    autoOpen: false
                });
                $( "#dialog-confirm" ).dialog({
                        autoOpen: false,
                        resizable: false,
                        height: "auto",
                        width: 400,
                        modal: true,
                        buttons: {
                          "Xóa": function() {
                              var donid = $('#dontpid_xoa').val();
                              $.ajax({
                                    type: "GET",
                                    url: "thuphiservlet",
                                    data: {action: "deletedontp",donid: donid},
                                    success: function (data) {
                                        if(data === 'OK'){
                                            $('#ctp_bbd').find(".remove_dontp").each(function(){
                                                if($('#dontpid_xoa').val() === $(this).prop("id")){
                                                      $(this).parent().parent().remove();
                                                  }
                                            });
                                             var total = 0;
                                            $('#ctp_bbd').find('input:checkbox:checked').each(function(i,el){
                                                    $(this) .parent().parent().find('input.input_st').val($(this).val());
                                                    total += Number($(this).val());
                                            });
                                            $('#ctp_tongtien').val(total);
                                            $('#dontpid_xoa').val("");
                                            $('#delete_don_content').html("Xóa không thành công");
                                            $('#deletedon_popup').dialog('open');
                                        }else if(data === 'FAILED'){
                                            $('#delete_don_content').html("Đã xóa thành công");
                                            $('#deletedon_popup').dialog('open');
                                        }

                                    },error: function (xhr, ajaxOptions, thrownError) {
                                          console.log("STATUS = "+xhr.status);
                                          console.log(thrownError);
                                        }
                                });
                                $( this ).dialog( "close" );
                          },
                          "Không": function() {
                            $( this ).dialog( "close" );
                          }
                        }
                      });
                $(document).on('click','.remove_dontp',function(){
                    var donid = $(this).prop("id");
                    $('#dontpid_xoa').val(donid);
                    $( "#dialog-confirm" ).dialog("open");
                });
                
                function baocoLoad(){
                    var typebc = $('#ctp_type_luubc').val();
                    if(typebc === '0'){
                        $('#btn_saveBC').hide();
                        $('#btn_resetBC').hide();
                    }else if(typebc === '1'){
                        $('#btn_saveBC').show();
                        $('#btn_resetBC').show();
                    }
                }
               
                baocoLoad();
                $('#cb_khsave').on('change',function(){
                     if(!$(this).is(":checked")){
                         $('#ctp_khachhangname').prop('readonly','readonly');
                         $('#ctp_khachhangdiachi').prop('readonly','readonly');
                         $('#ctp_khachhangmaso').prop('readonly','readonly');
                     }else{
                         $('#ctp_khachhangname').removeAttr('readonly');
                         $('#ctp_khachhangdiachi').removeAttr('readonly');
                         $('#ctp_khachhangmaso').removeAttr('readonly');
                     }
                  });
                  var bddbtpauto ={     
                    source : function(request, response) {
                        
                        $.ajax({
                                url : "khachhang",
                                type : "GET",
                                data : {
                                        term : request.term
                                },
                                dataType : "json",
                                success : function(data) {
                                        response(data);
                                }
                        });
                    },
                    minLength: 4,
                    select: function (event, ui) {
                        $("#ctp_khachhangname").val( ui.item.name );
                        $('#ctp_khachhangdiachi').val( ui.item.address );
                        //$(this).autocomplete('close');
                        $("#ctp_khachhangmaso").val(ui.item.account);
                        $("#ctp_baocokhid").val(ui.item.id);
                       // $(".ui-menu-item").hide();
                        return false;
                    }
                };
                var bdtp_name = $("#ctp_khachhangname").autocomplete(bddbtpauto).data('ui-autocomplete');
                bdtp_name._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bdtp_name._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bdtp_name._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.address +"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                  
                  function changeValueMoney(){
                      var tongtienbl = $('#ctp_tongtien').val();
                      var tongtienbc = $('#ctp_sotien').val();
                      var tienthua = tongtienbc - tongtienbl;
                      $('#ctp_stconlai').val(tienthua);
                  }
                  $('#ctp_tongtien,#tongtienbc').on('keyup',changeValueMoney);
                $('#ctp_type_luubc').on('change',baocoLoad);
            </script>
        </div>
    </body>
</html>
