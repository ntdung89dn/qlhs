<%-- 
    Document   : chuathuphi
    Created on : Jul 5, 2016, 5:19:43 PM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            table {
                border-width:5px;	
                border-style:none;
            }
            #loadingbnbd ,#loadingbbd{
                height: 500px; 
                overflow-y: scroll; 
              }
              .ui-datepicker select.ui-datepicker-month, .ui-datepicker select.ui-datepicker-year {
                    color: #1c94c4;
                    font-size: 14px;
                    font-weight: bold;
                }
                @media screen and (min-width: 900px) {
                            #myModal .modal-dialog  {width:900px;}
                    }
                    label{
                        color: #000;
                    }
                table#tb_emailbnbd td {
                    word-break: break-all;
                }
            #tbctp_bnbd td,#ctp_bbd td{
                background: #ffffff;
            }
            .ui-state-hover, .ui-autocomplete tr:hover
            {
                color:White;
                background:#1c94c4;
                outline:none;
            }
            input[readonly] {
                background-color: wheat;
            }
            #search_bao_co_modal .modal-dialog  {width:75%;}
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
                <li class="sub-menu"><a class="active"  href="thuphi.jsp?page=chuathuphi">Chưa thu phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=dathuphi">Đã thu phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=tkbl">Thống kê biên lai</a></li>
                <li class="sub-menu" ><a href="thuphi.jsp?page=tkbc">Thống kê báo có</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=thcn">Tổng hợp công nợ</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=xembaophi">Xem thông báo phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=khongcostk">Đơn chưa thu phí không có số tài khoản</a></li>
        </ul> <br>
        <div style="text-align: center"><span style="color: red;font-weight: bold;" id="result_insert"></span></div>
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
                                              <option value="0">Lưu riêng báo có và biên lai</option>
                                              <option value="1">Lấy báo có đã lưu</option>
                                              <option value="2">Lưu báo có cùng với Biên lai</option>
                                              <option value="3">Cập nhật báo có chưa thanh toán</option>
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
                                <input type="checkbox" id="cb_khsave" checked> Lưu khách hàng
                            </div>
                            <div class="col-xs-3">
                                <input class="form-control" type="text" value="" id="ctp_khachhangname" placeholder="Tên khách hàng">
                            </div>
                            <div class="col-xs-3">
                                <input class="form-control" type="text" value="" id="ctp_khachhangdiachi" placeholder="Địa chỉ khách hàng">
                            </div>
                            <div class="col-xs-2">
                                <input class="form-control" type="text" value="" id="ctp_khachhangmaso" placeholder="Mã khách hàng">
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
                          <label for="ctp_tongtienthua" class="col-xs-2 col-form-label">Tổng tiền thừa báo có:</label>
                          <div class="col-xs-3">
                            <input class="form-control" type="text" value="" id="ctp_tongtienthua">
                          </div>
                      </div>
                   <div class="form-group row">
                        <div class="col-xs-3">
                        </div>
                        <div class="col-xs-2">
                            <button type="button" id="btn_saveBC" class="btn btn-primary" style="width: 100%">Lưu báo có</button>
                            <button type="button" id="btn_updateBC" class="btn btn-primary" style="width: 100%">Cập nhật báo có</button>
                        </div>
                       <div class="col-xs-1">
                           <div id="div_returnbank"><input type="checkbox" id="cb_returnBank" > Nộp sai phí. </div>
                        </div>
                        <div class="col-xs-2">
                            <button type="button" id="btn_resetBC" class="btn btn-primary" style="width: 100%">Hủy bỏ</button>
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
        <!-- LỌC DỮ LIỆU -->
        <div>
            <table class="table" >
                <tbody>
                    <tr>
                        <td>Lọc Dữ Liệu</td>
                        <td><input class="form-control" type="text" value="" id="ctp_ngaybatdau" placeholder="Ngày bắt đầu"></td>
                        <td><input class="form-control" type="text" value="" id="ctp_ngaykethuc" placeholder="Ngày kết thúc"></td>
                        <td>
                            <select class="form-control loaidon" id="ctp_loaidon">
                            </select>
                        </td>
                        <td>
                            <select class="form-control" id="ctp_loaihinhnhan">
                                <option value="0">Tất Cả Loại hình nhận</option>
                                <option value="1">CE</option>
                                <option value="2">CF</option>
                                <option value="3">CT</option>
                                <option value="4">CB</option>
                            </select>
                        </td>
                        <td><input class="form-control repalacedot" type="text" value="" id="ctp_maloainhan" placeholder="Mã loại hình nhận"></td>
                        <td>
                            <select class="form-control" id="ctp_sothang">
                                <option value="0">Số tháng</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>
                        </td>
                        <td><input class="form-control repalacedot" type="text" placeholder="Số thông báo phí"value="" id="ctp_sothongbaophi"></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <table style="width: 100%">
            <tbody>
                <tr>
                    <td style="width: 10%">Bên Nhận BĐ</td>
                    <td style="width: 28%"><input class="form-control" type="text" value="" id="searchBnbd"></td>
                    <td style="width: 3%"><img src="./images/edit.png" id="img_CreateBP" data-toggle="modal" data-target="#modal_taottp"></td>
                    <td style="width: 3%"><img src="./images/check-all.png" id="img_checkall"></td>
                    <td style="width: 3%"><img src="./images/check-all-delete.png" id="img_uncheckall"></td>
                    <td style="width: 3%"><img src="./images/table-excel.png" id="img_exportExel" data-toggle="modal" data-target="#excelModal"></td>
                    <td style="width: 10%">Bên BĐ:</td>
                    <td style="width: 25%"><input class="form-control" type="text" value="" id="searchBBD"></td>
                    <td style="width: 3%"><img src="./images/check-all.png" id="img_checkallBBD"></td>
                    <td style="width: 3%"><img src="./images/check-all-delete.png" id="img_uncheckallBBD"></td>
                    <td style="width: 5%"></td>
                </tr>
                <tr>
                    <td colspan="6">
                        <div id="loadingbnbd"><div id="div_loading"></div>
                            <table border="1" id="tbctp_bnbd" style="width: 100%;">
                                <thead style="background-color: #87CEFA">
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên bên BNBĐ</th>
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
                        <div id="tbctp_thongke">
                            Chọn dòng để in thông báo phí: <input type="text" id="1strow" style="width: 30px;">--><input type="text" id="2ndrow" style="width: 30px;">
                            <img src="./images/printer3.png" data-toggle="modal" onclick="InDon()">
                            <img src="./images/email.png" data-toggle="modal" onclick="sendEmail()">
                            <span> Tổng công:</span>

                        </div>
                    </td>
                    <td colspan="5">
                        <div id="loadingbbd">
                            <table border="1" id="ctp_bbd" style="width: 100%;">
                                <thead style="background-color: #87CEFA">
                                    <tr>
                                        <th>STT</th>
                                        <th>Mã Nhận</th>
                                        <th>Ngày nhập</th>
                                        <th>Bên BĐ</th>
                                         <th>Số tiền</th>
                                        <th>Phí</th>
                                        <th>Chọn</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                </tbody>
                            </table>
 
                        </div>
                        
                        <div style="width:90%;height:25px;float:right;text-align:right;margin-right:20px;">
		<span style="float:left;padding:5px;font-weight:bold;padding-left:0px;">Tổng số tiền:</span>
		<div id="tongtien" style="font-weight:bold;margin-top: 5px;"></div>
                        </div>
                    </td>
                    
                </tr>
            </tbody>
        </table>
        
        <div id="dialog" title="Lỗi">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>
                <span id="content_error_indon"></span></p>
          </div>
        <!-- Loading Ajax 
        
        <button type="button" id="checkBtn" class="btn btn-primary" style="width: 100%">Lưu lại</button>-->
        <!-- Modal -->
        <div id="myModal" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Thông báo phí</h4>
              </div>
              <div class="modal-body">
                <div class="panel panel-primary">
                    <div class="panel-heading">Quản Lý Hồ Sơ</div>
                    <div class="panel-body">
                        <div class="container">
                            <form class="form-horizontal">
                                <div class="form-group">
                                  <label class="col-sm-1 control-label">Email</label>
                                  <div class="col-sm-3">
                                      <select id="select_email" class="form-group">

                                      </select>
                                  </div>
                                </div>
                                <div class="form-group">
                                  <label for="e_title" class="col-sm-1 control-label">Tiêu đề</label>
                                  <div class="col-sm-6">
                                    <input class="form-control" id="e_title" type="text" placeholder="Tiêu đề" >
                                  </div>
                                </div>
                                <div class="form-group">
                                  <label for="e_content" class="col-sm-1 control-label">Nội Dung</label>
                                  <div class="col-sm-6">
                                      <textarea name="editor1" id="e_content" rows="10" cols="80" placeholder="Nhập nội dung email">
                                        
                                    </textarea>
                                  </div>
                                </div>
                                <div class="form-group">
                                 <div class="col-sm-3">
                                  </div>
                                  <div class="col-sm-4">
                                      <button type="button" id="btn_Sendmail" class="btn btn-primary">Gửi Email</button>
                                      <button type="button" id="btn_Clear" class="btn btn-primary">Hủy bỏ</button>
                                  </div>
                                </div>
                              </form>
                            <div>
                                <table id="tb_emailbnbd" class="table table-bordered table-striped" style="width: 800px;table-layout: fixed;">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>BNBD</th>
                                            <th>Số Tài Khoản</th>
                                            <th>Email</th>
                                            <th>Chọn</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                                </div>
                          </div>
                    </div>
                  </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
              </div>
            </div>

          </div>
        </div>
        
        <!-- modal cho tạo thông báo phí -->
         <div id="modal_taottp" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                  <h4 class="modal-title">Modal Header</h4>
                </div>
                <div class="modal-body">
                  <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#home">Tạo Báo Phí</a></li>
                    <li><a data-toggle="tab" href="#menu1">Tạo Thông tin báo phí</a></li>
                  </ul>

                  <div class="tab-content">
                    <div id="home" class="tab-pane fade in active">
                        <form class="form-horizontal"><br>
                            <div class="form-group">
                              <label class="col-sm-4">Ngày tạo thông báo</label>
                              <div class="col-sm-6">
                                  <input class="form-control" id="d_thongbao" type="text"  >
                              </div>
                            </div>
                            <div class="form-group">
                              <label for="d_ththanhtoan" class="col-sm-4">Thời hạn thanh toán: </label>
                              <div class="col-sm-6">
                                <input class="form-control" id="d_ththanhtoan" type="text"  >
                              </div>
                            </div>
                            <div class="panel panel-default"><br>
                                    <div class="form-group">
                                        <label for="e_content" class="col-sm-4">
                                            Sử dụng thông tin đã nhập
                                        </label>
                                        <div class="col-sm-6">
                                            <label class="radio-inline">
                                                <input type="radio" name="rd_thongtin" checked="checked" value="1">Thông tin cũ
                                            </label>
                                            <label class="radio-inline">
                                                <input type="radio" name="rd_thongtin" value="2">Không sử dụng
                                            </label><br>
                                            <select id='select_ttnhap' class="form-control">

                                            </select>

                                        </div>
                                    </div>
                                    <div class="form-group">
                                      <label for="d_taikhoan" class="col-sm-4">Tài Khoản</label>
                                      <div class="col-sm-6">
                                          <textarea class="form-control" id="d_taikhoan" type="text"  ></textarea>
                                      </div>
                                    </div>
                                    <div class="form-group">
                                      <label for="d_nguoilienhe" class="col-sm-4">Người liên hệ</label>
                                      <div class="col-sm-6">
                                          <input class="form-control" id="d_nguoilienhe" type="text"  >
                                      </div>
                                    </div>
                                    <div class="form-group">
                                      <label for="d_nguoichiutn" class="col-sm-4 control-label">Người chịu trách nhiệm</label>
                                      <div class="col-sm-6">
                                          <input class="form-control" id="d_nguoichiutn" type="text"  >
                                      </div>
                                    </div>
                                    <div class="form-group">
                                      <label for="select_chucvu" class="col-sm-4 control-label">Chức vụ: </label>
                                      <div class="col-sm-6">
                                          <select id='select_chucvu' class="form-control">
                                          </select>
                                      </div>
                                    </div>
                                    <div class="form-group">
                                     <div class="col-sm-3">
                                      </div>
                                      <div class="col-sm-4">
                                          <button type="button" id="btn_SaveTP" class="btn btn-primary">Lưu lại</button>
                                          <button type="button" id="btn_Clear" class="btn btn-primary">Hủy bỏ</button>
                                      </div>
                                    </div>
                                </div>
                          </form>
                    </div>
                    <div id="menu1" class="tab-pane fade">
                      <form class="form-horizontal">
                            <div class="panel panel-default"><br>
                                    <div class="form-group">
                                        <label for="d_newtt" class="col-sm-4">
                                            Tên rút gọn
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="form-control" id="d_newtt" type="text"  >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                      <label for="dad_tk" class="col-sm-4">Tài Khoản</label>
                                      <div class="col-sm-6">
                                          <textarea class="form-control" id="dad_tk" type="text"  ></textarea>
                                      </div>
                                    </div>
                                    <div class="form-group">
                                      <label for="dad_nguoilh" class="col-sm-4">Người liên hệ</label>
                                      <div class="col-sm-6">
                                          <input class="form-control" id="dad_nguoilh" type="text"  >
                                      </div>
                                    </div>
                                    <div class="form-group">
                                      <label for="dad_nguoictn" class="col-sm-4">Người chịu trách nhiệm</label>
                                      <div class="col-sm-6">
                                          <input class="form-control" id="dad_nguoictn" type="text"  >
                                      </div>
                                    </div>
                                    <div class="form-group">
                                      <label for="sad_chucvu" class="col-sm-4">Chức vụ: </label>
                                      <div class="col-sm-6">
                                          <select id='sad_chucvu' class="form-control">
                                          </select>
                                      </div>
                                    </div>
                                    <div class="form-group">
                                     <div class="col-sm-3">
                                      </div>
                                      <div class="col-sm-4">
                                          <button type="button" id="btn_SaveAddTP" class="btn btn-primary">Lưu lại</button>
                                          <button type="button" id="btn_ClearAdd" class="btn btn-primary">Hủy bỏ</button>
                                      </div>
                                    </div>
                                </div>
                          </form>
                    </div>

                  </div>
                </div>
              <div class="modal-footer">
                  <div id="div_result" style="text-align: left;color: red"></div>
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
              </div>
            </div>

          </div>
        </div>
        
        
        <!--- Tạo excel modal excelModal-->
        <div class="modal fade" id="excelModal" role="dialog">
                <div class="modal-dialog">

                  <!-- Modal content-->
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal">&times;</button>
                      <h4 class="modal-title">Xuất Excel</h4>
                    </div>
                    <div class="modal-body">
                      <p>
                          <label for="sel1">Chọn mục xuất Excel:</label>
                                <select class="form-control" id="select_export_Excel">
                                    <option value="1">Bên nhận bảo đảm</option>
                                    <option value="2">Bên Bảo đảm</option>
                                    <option value="3">Đầy đủ</option>
                                </select>
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
        <div id="dialog-message" title="Lỗi xảy ra">
                <p>
                    <span id="content_baoco_error"></span>
                </p>
        </div>
        
        <!-- modal chọn báo có -->
        <div id="search_bao_co_modal" class="modal fade" data-backdrop="static" data-keyboard="false" role="dialog">
            <div class="modal-dialog">

              <!-- Modal content-->
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                  <h4 class="modal-title">Tìm kiếm báo có</h4>
                </div>
                <div class="modal-body">
                  <div class="form-group row">
                        <div class="col-xs-2">
                            <input class="form-control" type="text" value="" id="searchbc_ngaybc" placeholder="Ngày báo có">
                        </div>
                        <div class="col-xs-2">
                            <input class="form-control" type="text" value="" id="searchbc_sobaoco" placeholder="Số báo có">
                        </div>
                        <div class="col-xs-2">
                            <input class="form-control" type="text" value="" id="searchbc_khname" placeholder="Tên Khách Hàng">
                        </div>
                        <div class="col-xs-2">
                            <input class="form-control" type="text" value="" id="searchbc_account" placeholder="Mã khách hàng">
                        </div>
                        <div class="col-xs-2">
                            <input class="form-control" type="text" value="" id="searchbc_nguoipl" placeholder="Người phát lệnh">
                        </div>
                      <div class="col-xs-2">
                          <button class="btn btn-primary" id="btn_searchbc">Tìm kiếm</button>
                        </div>
                    </div>
                  <table id="tb_searchbc_luu" class="table table-striped table-bordered">
                      <thead>
                          <tr>
                              <th>Ngày báo có</th>
                              <th>Số báo có</th>
                              <th>Người phát lệnh</th>
                              <th>Nội Dung</th>
                              <th>Số tiền thừa</th>
                              <th>Tên Khách Hàng</th>
                              <th>Mã Khách Hàng</th>
                              <th>Chọn</th>
                          </tr>
                      </thead>
                      <tbody></tbody>
                  </table>
                  <div style="text-align: right">
                        <ul class="pagination" id="ul_page" ></ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" id="btn_modal_search_ok" >Xác Nhận</button>
                  <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                </div>
              </div>

            </div>
          </div>
        <!-- modal xóa báo có -->
        <div id="xoa_bao_co_modal" class="modal fade" data-backdrop="static" data-keyboard="false" role="dialog">
            <div class="modal-dialog">

              <!-- Modal content-->
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                  <h4 class="modal-title">Xóa</h4>
                </div>
                <div class="modal-body">
                  <table id="tb_xoabc_luu" class="table table-striped table-bordered">
                      <thead>
                          <tr>
                              <th>Ngày báo có</th>
                              <th>Số báo có</th>
                              <th>Người phát lệnh</th>
                              <th>Số tiền thừa</th>
                              <th>Tên Khách Hàng</th>
                              <th>Mã Khách Hàng</th>
                              <th>Xóa</th>
                          </tr>
                      </thead>
                      <tbody></tbody>
                  </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" id="btn_modalxoa_ok" >Xác Nhận</button>
                  <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                </div>
              </div>

            </div>
          </div>
        
        <script>
            var $pagination = $('#ul_page');
                var searchList = [];
                var defaultOpts = {
                    totalPages: 1,
                    first: 'Trang đầu',
                    prev: 'Trang Trước',
                    next: 'Trang sau',
                    last: 'Trang cuối',
                    loop: false
                };
                $pagination.twbsPagination(defaultOpts);
             var donArr = [];
                  var manhanArr = [];
                  var someDate = new Date();
                  $('#ctp_sobcselect').hide();
                  $('#img_searchbc_add').hide();
                  $('#img_searchbc_sub').hide();
                  $('#ctp_tongtienthua').hide();
                  $('#btn_updateBC').hide();
                  $('label').each(function(){
                     if($(this).prop('for') ==='ctp_tongtienthua') {
                         $(this).hide();
                     }
                  });
                  $( "#dialog-message" ).dialog({
                      autoOpen: false,
                        modal: true,
                        buttons: {
                          Ok: function() {
                            $( this ).dialog( "close" );
                          }
                        }
                      });
                  $(document).on('click','.click_td',function() {
                    resetAll();
                    $('#ctp_ngaynop').datepicker("setDate",someDate);
                    var fromday = $('#ctp_ngaybatdau').val();
                    var today  = $('#ctp_ngaykethuc').val();
                    var donids = $(this).parent().find('input[id^="ctp_donid"]').val();
                    var infor = $(this).parent().find('input[id^="ctp_infor"]').val().split("_");
                    $('#ctp_tendonvi').val(infor[0]);
                    $('#ctp_diachi').val(infor[1]);
                     $('#ctp_lydonop').val('Thanh toán tiền phí đăng ký giao dịch bảo đảm ');
                    $('.click_td').css('color', 'black');
                    $('#panel_bienlai').collapse("show");
                  //  console.log(donids);
                    $(this).css('color', 'red');
                   var tongtien = $(this).parent().find('input:checkbox').val();
               //    tongtien = commaSeparateNumber(tongtien);
                   $('#tongtien').html(Number(tongtien).toLocaleString('vn'));
                    $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        data: {action: "loadbbdctp",fromday: fromday,today: today,donids: donids },
                        success: function (data) {
                            // replace div's content with returned data
                            $('#ctp_bbd tbody').html(data);
                        }
                    });
               });
                $(document).on('change', '.bbdcheckbox', function() {
                  //  console.log("CHECK 1");
                        if(this.checked) {
                          var total = 0;
                          var manhan = $(this).parent().parent().find('td').eq(1).html();
                          var donid = $(this).parent().parent().find('td').eq(3).find('input').val();
                          console.log("DON ID = "+donid);
                          $(this).parent().parent().find('.input_st').val(parseInt($(this).val()));
                          donArr.push(donid);
                          manhanArr.push(manhan);
                         
                          $('.bbdcheckbox').each(function(i,e){
                                if($(this).is(':checked')){
                                    total += parseInt($(this).parent().parent().find('.input_st').val());
                                }
                            });
                          $('#ctp_tongtien').val(total);
                         
                        }else{
                            var donid = $(this).parent().parent().find('td').eq(3).find('input').val();
                            for(var i=0;i< donArr.length;i++){
                                var check = donid.localeCompare(donArr[i]);
                                if(check ==0){
                                    donArr.splice(i,1);
                                    manhanArr.splice(i,1);
                                    $(this).parent().parent().find('.input_st').val('');
                                    var total = 0;
                                    $('.bbdcheckbox').each(function(i,e){
                                        if($(this).is(':checked')){
                                            total += parseInt($(this).parent().parent().find('.input_st').val());
                                        }
                                    });
                                    $('#ctp_tongtien').val(total);
                                }
                            }
                        }
                        $('#ctp_ngaynop').datepicker().datepicker("setDate", new Date());
                        $('#ctp_lydonop').val('Thanh toán tiền lệ phí ĐKGDBĐ cho '+manhanArr);
                        $('#ctp_donids').val(donArr);
                         changeMoney();
                    });
                    $(document).on('change paste keyup', '.input_st', function() {
                        var total = 0;
                        $('.bbdcheckbox').each(function(i,e){
                            if($(this).is(':checked')){
                                total += parseInt($(this).parent().parent().find('.input_st').val());
                            }
                        });
                        $('#ctp_tongtien').val(total);
                    });
                $('#ctp_ngaybaoco').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                 
                $('#ctp_ngaynop').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                   
                }).datepicker("setDate", someDate);
                $('#searchbc_ngaybc').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                   
                }).datepicker("setDate", someDate);
            // add arguments as needed
            var month = someDate.getMonth();
            var year = someDate.getFullYear();
            var thisday = '1-'+month+'-'+year;
                $('#ctp_ngaybatdau').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true,
                    setDay: 'today',
                    onClose: function(){
                        this.focus();
                    }
                }).val(thisday);
                $('#ctp_ngaykethuc').datepicker({
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
                $('#d_thongbao').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                $('#d_ththanhtoan').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                $("#checkBtn").on("click",function(){
                    $('#div_loading').html("<img src='./images/loading.gif'>");
                    //$("#loading").html('<img src="css/demo_wait.gif"> loading...');
                    $.ajax({
                        type: "GET",
                        dataType: "json",
                        url: "",                  
                        success: function (data) {
                            // replace div's content with returned data
                            $('#div_loading').empty();
                        }
                    });
                   
                });
                var changeMoney = function(){
                      var tongbl = $('#ctp_tongtien').val();
                      var tongbc = $('#ctp_sotien').val();
                      var tienthua = tongbl - tongbc;
                      $('#ctp_stconlai').val(tienthua);  
                };
                $(".loaidon").append('<option value="0">Loại đơn</option><option value="1">LĐ</option><option value="2">TĐ</option>\n\
                        <option value="3">Xóa</option><option value="4">VB-XL-TS</option>\n\
                        <option value="5">CC-TT</option>\n\
                        <option value="6">BẢN SAO</option>\n\
                        <option value="7">TBKBTHA</option>\n\
                        <option value="8">MIỄN PHÍ</option>\n\
                        <option value="9">CSGT</option>\n\
                        <option value="10">CSGT-TĐ</option>\n\
                        <option value="11">CSGT-X</option>\n\
                        <option value="12">CSGT-Online</option>\n\
                        <option value="13">CSGT-Online/TĐ</option>'
                );
                
               
                
                // Lưu thu phí
               $('#btn_ctpSave').on('click',function(){
                   var typesaveBC = $('#ctp_type_luubc').val();
                    var ngaynop = $('#ctp_ngaynop').val();
                    var sohieu = $('#ctp_sohieu').val();
                    var tendonvi = $('#ctp_tendonvi').val();
                    var diachitdv = $('#ctp_diachi').val();
                    var lydo = $('#ctp_lydonop').val();
                    var donids = $('#ctp_donids').val();
                    // 1 : trực tiếp , 2 : chuyển khoản
                    var loaitt = $('input[name="loaithanhtoan"]:checked').val();
                    // thông báo phí : true : có, false : ko
                    var tbp = $('#ctpcb_thongbaophi').is(":checked");
                    var tongtien = $('#ctp_tongtien').val();
                    if(ngaynop === ''){
                         $('#content_baoco_error').html('chưa nhập ngày nộp');
                        $( "#dialog-message" ).dialog("open");
                        return false;
                    }else if(sohieu === ''){
                        $('#content_baoco_error').html('chưa nhập số hiệu');
                        $( "#dialog-message" ).dialog("open");
                        return false;
                    }else if(tendonvi === ''){
                        $('#content_baoco_error').html('chưa nhập tên đơn vị');
                        $( "#dialog-message" ).dialog("open");
                        return false;
                    }else if(diachitdv === ''){
                        $('#content_baoco_error').html('chưa nhập địa chỉ đơn vị');
                        $( "#dialog-message" ).dialog("open");
                        return false;
                    }else if(lydo === ''){
                        $('#content_baoco_error').html('chưa nhập lý do nộp');
                        $( "#dialog-message" ).dialog("open");
                        return false; 
                    }else if(donids === ''){
                        $('#content_baoco_error').html('chưa nhập đơn thanh toán');
                        $( "#dialog-message" ).dialog("open");
                        return false;
                    }else if(loaitt === undefined){
                        $('#content_baoco_error').html('chưa chọn loại thanh toán');
                        $( "#dialog-message" ).dialog("open");
                        return false; 
                    }else if(!$.isNumeric(tongtien) ){
                        $('#content_baoco_error').html('Số tiền là kiểu số');
                        $( "#dialog-message" ).dialog("open");
                        return false; 
                    }else if(parseInt(tongtien) < 0 ){
                        $('#content_baoco_error').html('Số tiền phải lớn hơn 0');
                        $( "#dialog-message" ).dialog("open");
                        return false; 
                    }else{
                        var loaithanhtoan = 0;
                        console.log("loaitt = "+loaitt);
                        console.log("TBP = "+tbp);
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

                        var ngaybc = $('#ctp_ngaybaoco').val();
                        var sobc = $('#ctp_sobaoco').val();
                         var sotien = $('#ctp_sotien').val();
                         var ndbaoco = $('#ctp_ndbaoco').val();
                         var khidbc = $('#ctp_baocokhid').val();
                    //     console.log("KH_ID = "+khidbc);
                         var luukhCheck = $('#cb_khsave').prop('checked');
                         var nguoiplbc = $('#ctp_nguoiplbaoco').val();
                         var bcids =[];
                       if(typesaveBC ==='2'){
                           if(khidbc === undefined || khidbc === ''){
                               if(luukhCheck){
                                   $('#content_baoco_error').html('Xin nhập lại khách hàng báo có');
                                    $( "#dialog-message" ).dialog("open");
                                    return false; 
                               }
                              
                           }else if(ngaybc === undefined || ngaybc === ''){
                               $('#content_baoco_error').html('Xin nhập ngày báo có ');
                                $( "#dialog-message" ).dialog("open");
                                return false; 
                           }else if(sobc === undefined || sobc === ''){
                               $('#content_baoco_error').html('Xin nhập số báo có ');
                                $( "#dialog-message" ).dialog("open");
                                return false;
                           }else if(sotien === undefined || sotien === '' || !parseInt(sotien) || parseInt(sotien) <0){
                               $('#content_baoco_error').html('Xin nhập lại số tiền báo có ');
                                $( "#dialog-message" ).dialog("open");
                                return false;
                           }else if(ndbaoco === undefined || ndbaoco === ''){
                               $('#content_baoco_error').html('Xin nhập nội dung báo có ');
                                $( "#dialog-message" ).dialog("open");
                                return false;
                           }else if(nguoiplbc === undefined || nguoiplbc === ''){
                               $('#content_baoco_error').html('Xin nhập người phát lệnh báo có ');
                                $( "#dialog-message" ).dialog("open");
                                return false;
                           }
                        }else if(typesaveBC === '1'){
                            $('#ctp_sobcselect').find('option').each(function(){
                                        bcids.push($(this).val());
                            });
                            if(bcids.length <1){
                                $('#content_baoco_error').html('Chưa chọn báo có đã lưu');
                                $( "#dialog-message" ).dialog("open");
                                return false;
                            }
                        }
                        var total  =0;
                        $('.bbdcheckbox').each(function(i,e){
                                if($(this).is(':checked')){
                                    total += parseInt($(this).parent().parent().find('.input_st').val());
                                }
                            });
                        if(parseInt(tongtien) !== total){
                            $('#content_baoco_error').html('Số tiền nhập vào không đúng ');
                            $( "#dialog-message" ).dialog("open");
                            return false;
                        }
                       $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "luuthuphi",ngaynop: ngaynop,sohieu: sohieu,tendonvi: tendonvi,diachitdv: diachitdv,
                                lydo: lydo, donids: donids,loaithanhtoan: loaithanhtoan,ngaybc: ngaybc,sobc: sobc,
                            sotienbc: sotien,ndbaoco: ndbaoco,tongtien: tongtien,nguoiplbc : nguoiplbc,typesavebc : typesaveBC,luukhCheck: luukhCheck,bcids : bcids.join()},
                            success: function (data) {
                                // replace div's content with returned data
                               // alert(data);
                                window.location = "./print/print_inbienlaimau.jsp";
                                $('#ctp_sobcselect').empty();
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                $('#content_baoco_error').html('Lỗi khi lưu báo phí và biên lai.');
                                $( "#dialog-message" ).dialog("open");
                                return false;
                            }
                        }); 
                       
                    }
               });
               $('#ctp_sotien').on('change paste keyup',function(){
                   if($(this).val() !== ''){
                       var total = $('#ctp_tongtien').val();
                       var tienthua = $(this).val() - total;
                        $('#ctp_tienthua').val(tienthua);
                   }else{
                       $('#ctp_tienthua').val('');
                   }
                   
               });
               function resetAll(){
               //    console.log($('#ctp_type_luubc').val());
                   if($('#ctp_type_luubc').val() !== '1'){
                       $('#ctp_ngaybaoco').val('');
                        $('#ctp_sobaoco').val('');
                        $('#ctp_sotien').val('');
                        $('#ctp_tienthua').val('');
                        $('#ctp_ndbaoco').val('');
                        $('#ctp_khachhangname').val("");
                        $('#ctp_khachhangdiachi').val("");
                        $('#ctp_khachhangmaso').val("");
                        $('#ctp_baocokhid').val("");
                        $('#ctp_nguoiplbaoco').val("");
                   }
                    
                    
                    $('#ctp_ngaynop').val();
                    $('#ctp_sohieu').val('');
                    $('#ctp_tendonvi').val('');
                    $('#ctp_diachi').val('');
                    $('#ctp_lydonop').val('');
                    $('#ctp_donids').val('');
                    // 1 : trực tiếp , 2 : chuyển khoản
                    $('input[name="loaithanhtoan"]').attr('checked', false);
                    // thông báo phí : true : có, false : ko
                    $('#ctpcb_thongbaophi').attr('checked', false);
                    $('#ctp_tongtien').val('');  
                    donArr.splice(0,donArr.length);
                    manhanArr.splice(0,manhanArr.length);
               };
               
               
            $('#img_checkall').on('click',function(){
                $('#tbctp_bnbd').find('input[id^="ctp_cb"]').prop('checked',true);
                totalPrice();
            });
            $('#img_uncheckall').on('click',function(){
                $('#tbctp_bnbd').find('input[id^="ctp_cb"]').prop('checked',false);
                totalPrice();
            });
            $('#img_checkallBBD').on('click',function(){
                $('#ctp_bbd').find('input[id^="ctp_cb"]').prop('checked',true);
                var total = 0;
               var donids = [];
               var manhan = [];
                $('#ctp_bbd').find('input[id^="ctp_cb"]').each(function(i,el){
                        $(this).parent().parent().find('input.input_st').val($(this).val());
                        donids.push($(this).parent().parent().find('input[id^="ctp_donid"]').val());
                        manhan.push($(this).parent().parent().find('td').eq(1).html());
                        total += Number($(this).val());
                });
                var lydo  = $('#ctp_lydonop').val();
                 $('#ctp_lydonop').val(lydo+" "+manhan.toString());
                $('#ctp_donids').val(donids.toString());
                $('#ctp_tongtien').val(total);
            });
            $('#img_uncheckallBBD').on('click',function(){
                $('#ctp_bbd').find('input[id^="ctp_cb"]').prop('checked',false);
                //totalPrice();
                $('#ctp_bbd').find('input[id^="ctp_cb"]').each(function(i,el){
                   $(this) .parent().parent().find('input.input_st').val("");
                   $('#ctp_lydonop').val('Thanh toán tiền phí đăng ký giao dịch bảo đảm ');
                });
                $('#ctp_tongtien').val("");
            });
            $('#wait').hide();
            var loadbnbd_ctp = function(){
                $('#div_loading').html("<img src='./images/loading.gif'>");
                var fromday = $('#ctp_ngaybatdau').val();
                var today  = $('#ctp_ngaykethuc').val();
                var loaidon  = $('#ctp_loaidon').val();
                var loaihinhnhan = $('#ctp_loaihinhnhan').val();
                var sothongbaophi = $('#ctp_sothongbaophi').val();
                var manhan = $('#ctp_maloainhan').val();
                var searchBNBD = $('#searchBnbd').val();
                if($(this).prop('id') ==='searchBnbd'){
                    if(searchBNBD.length > 2){
                       // $('#div_loading').html("<img src='./images/loading.gif'>");
                        $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "loadbnbdctp",fromday: fromday,today: today, loaidon : loaidon,loaihinhnhan: loaihinhnhan
                                ,sothongbaophi: sothongbaophi, manhan : manhan,bnbd: searchBNBD },
                            success: function (data) {
                                // replace div's content with returned data
                                $('#div_loading').empty();
                                $('#tbctp_bnbd tbody').html(data);
                            }
                        });
                    }
                }else{
                    $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "loadbnbdctp",fromday: fromday,today: today, loaidon : loaidon,loaihinhnhan: loaihinhnhan
                                ,sothongbaophi: sothongbaophi, manhan : manhan,bnbd: searchBNBD },
                            success: function (data) {
                                // replace div's content with returned data
                                $('#div_loading').empty();
                                $('#tbctp_bnbd tbody').html(data);
                            }
                        });
                }
                
            };
            $(document).on("change","table#tbctp_bnbd input:checkbox",function() {
                totalPrice();
                
            });
            var totalPrice = function(){
                var totalStr = "Tổng cộng : ";
                var total = 0;
                $('table#tbctp_bnbd').find('input:checkbox:checked').each(function(){
                    total += parseInt($(this).val());
                });
                totalStr += total;
                $('#tbctp_thongke').find('span').last().html(totalStr);
            };
            var InDon = function(){
                var x = $('input[id^="ctp_cb"]:checked').length;
                var ctp_day = $('#ctp_ngaybatdau').val();
                if(x >0){
                   // window.open('./print/print_thongbaophi.jsp?id=');
                   var donids = "";
                   var count = 1;
                   $('table#tbctp_bnbd').find('input[id^="ctp_cb"]:checkbox:checked').each(function(i){
                       
                      // donids = $( this ).parent().parent().find('input[id*="ctp_donid"]').val()+",";
                      if(count === 1){
                           if(count === x){
                                donids = $( this ).parent().parent().find('input[id*="ctp_donid"]').val();
                            }else{
                                donids = $( this ).parent().parent().find('input[id*="ctp_donid"]').val();
                            }
                      }else{
                          if(count === x){
                                donids += "-"+$( this ).parent().parent().find('input[id*="ctp_donid"]').val();
                           }else{
                                donids += "-"+$( this ).parent().parent().find('input[id*="ctp_donid"]').val();
                           }
                      }
                       
                       count ++;
                   });
                   $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "loaddonstbp",id: donids },
                            success: function (data) {
                                // replace div's content with returned data
                                if(data === 'OK'){
                                    $('#tbctp_bnbd tbody').html('');
                                    $('#1strow').val('');$('#2ndrow').val('');
                                    window.location.href = './print/print_thongbaophi.jsp';
                                }else{
                                    $('#content_error_indon').html("Chưa lưu thông tin thu phí");
                                    $( "#dialog" ).dialog( "open" );
                                }
                                
                            }
                        });
             //      window.location.href = './print/print_thongbaophi.jsp?id='+donids;
                }else{
                    $('#content_error_indon').html("Chưa chọn bên bảo đảm");
                    $( "#dialog" ).dialog( "open" );
                }
            };
                $(document).on('change', '#1strow', function() {
                    var fromline = $('#1strow').val();
                    var toline = $('#2ndrow').val();
                    $('input[id^="ctp_cb"]').prop('checked',false);
                    console.log('OK ');
                    if($.isNumeric(fromline)){
                        console.log('OK line 1');
                        if($.isNumeric(toline)){
                            console.log('OK line 2');
                           if(toline < fromline){
                               
                           }else{
                               for(var i=fromline;i <= toline;i++){
                                   var positon = i -1;
                                   $('input#ctp_cb'+positon).prop('checked',true);
                               }
                           }
                        }else if(toline ===''){
                            var positon = fromline -1;
                            $('input#ctp_cb'+positon).prop('checked',true);
                        }
                    }
                    totalPrice();
                  });
                  $(document).on('change', '#2ndrow', function() {
                    var fromline = $('#1strow').val();
                    var toline = $('#2ndrow').val();
                    $('input[id^="ctp_cb"]').prop('checked',false);
                    console.log('OK ');
                    if($.isNumeric(fromline)){
                        console.log('OK line 1');
                        if($.isNumeric(toline)){
                            console.log('OK line 2');
                           if(toline < fromline){
                               
                           }else{
                               for(var i=fromline;i <= toline;i++){
                                   var positon = i -1;
                                   $('input#ctp_cb'+positon).prop('checked',true);
                               }
                           }
                        }else if(toline ===''){
                            var positon = fromline -1;
                            $('input#ctp_cb'+positon).prop('checked',true);
                        }
                    }
                    totalPrice();
                  });
                  
                  function addDonTp(){
                      var manhan = $(this).parent().parent().find('td').eq(1).html();
                      //console.log(manhan);
                  };
             loadbnbd_ctp();
             $( "#dialog" ).dialog({
                     autoOpen: false,
                    resizable: false,
                    height: "auto",
                    width: 400,
                    modal: true,
                    buttons: {
                      "Đóng": function() {
                        $( this ).dialog( "close" );
                      }
                    }
                  });
             var sendEmail = function(){
                 var title = "Thông báo nhắc nợ ";
                 var x = $('input[id^="ctp_cb"]:checked').length;
                 if(x ===0){
                     $('#content_error_indon').html("Xin vui lòng chọn bên chưa thu phí");
                    $( "#dialog" ).dialog( "open" );
                 }else{
                     var khListid = [];
                     var donid = [];
                     $('table#tbctp_bnbd').find('input[id^="ctp_khid"]').each(function(i,el){
                         khListid.push($(this).val());
                     });
                     $('table#tbctp_bnbd').find('input[id^="ctp_donid"]').each(function(i,el){
                         donid.push($(this).val());
                     });
                     $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            dataType: "json",
                            data: {action: "loadkhmail",khlist: khListid,donid: donid },
                            success: function (data) {
                                // replace div's content with returned data
                                $('#select_email').html(data.email);
                                $('#e_title').val(data.title);
                                $('#tb_emailbnbd tbody').html(data.data);
                            }
                        });
                     $('#myModal').modal('show');
                 }
             };
        /**     var createThuPhi = function(){
                $('#modal_taottp').sho
             };
             $('#img_CreateBP').on('click',createThuPhi);**/
             var saveTtBaoPhi = function(){
                 var ngaytao = $('#d_thongbao').val();
                 var ngayttoan = $('#d_ththanhtoan').val();
                 if(ngaytao.trim() === ''){
                     alert("Chưa chọn ngày thông báo phí");
                     return false;
                 }else if(ngayttoan.trim() === ''){
                     alert("Chưa chọn ngày hết hạn thanh toán");
                     return false;
                 }
                 var rdValue =  $("input[name='rd_thongtin']:checked"). val();
                 if(rdValue == 1){
                     var saveInforId = $('#select_ttnhap').val();
                     var taikhoan = $('#d_taikhoan').val();
                    var nguoilienhe = $('#d_nguoilienhe').val();
                    var nguoichiutn = $('#d_nguoichiutn').val();
                    var chucvu = $('#select_chucvu').val();
                     $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        data: {action: "baophiInfor",ngaytao: ngaytao,ngayttoan: ngayttoan,saveInforId: saveInforId,taikhoan: taikhoan,nguoilienhe: nguoilienhe,
                        nguoichiutn: nguoichiutn,chucvu: chucvu },
                        success: function (data) {
                            // replace div's content with returned data
                                $('#modal_taottp').modal('hide');
                                alert("Lưu thông tin báo phí thành công");
                        }
                    });
                 }else if( rdValue == 2){
                     var taikhoan = $('#d_taikhoan').val();
                    var nguoilienhe = $('#d_nguoilienhe').val();
                    var nguoichiutn = $('#d_nguoichiutn').val();
                    var chucvu = $('#select_chucvu').val();
                    $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        data: {action: "baophinoinfor",ngaytao: ngaytao,ngayttoan: ngayttoan,taikhoan: taikhoan,nguoilienhe: nguoilienhe,
                        nguoichiutn: nguoichiutn,chucvu: chucvu },
                        success: function (data) {
                            // replace div's content with returned data
                            //$('#tb_emailbnbd tbody').html(data);
                            if( data === 'OK'){
                                $('#div_result').html('<span>Lưu thành công</span>');
                                $('#d_newtt').val('');
                                $('#dad_tk').val('');
                                $('#dad_nguoilh').val('');
                                $('#dad_nguoictn').val('');
                                $('#sad_chucvu').val(1);
                            }else if(data === 'FAIL'){
                                $('#div_result').html('<span>Lưu chưa thành công</span>');
                            }
                            
                        }
                    });
                 }
                 
                 
/**                 $.ajax({
                    type: "POST",
                    url: "thuphiservlet",
                    data: {action: "sessionBaoPhi",ngaytao: ngaytao,ngayttoan: ngayttoan,taikhoan: taikhoan,
                    nguoilienhe: nguoilienhe,nguoichiutn: nguoichiutn,chucvu: chucvu },
                    success: function (data) {
                        // replace div's content with returned data
                        //$('#tb_emailbnbd tbody').html(data);
                    }
                }); **/
             };
             var loadAllTBP = function(){
                 $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        data: {action: "loadallttbp" },
                        success: function (data) {
                            // replace div's content with returned data
                            console.log("data = "+data);
                             $('#select_ttnhap').html(data);
                            var saveInforId = $('#select_ttnhap').val();
                            $.ajax({
                                type: "GET",
                                url: "thuphiservlet",
                                 dataType: "json",
                                data: {action: "loadSaveInfor",saveInforId: saveInforId },
                                success: function (data) {
                                    // replace div's content with returned data
                                    //$('#tb_emailbnbd tbody').html(data);
                                    console.log(data);
                                    $('#d_taikhoan').val(data.taikhoan);
                                    $('#d_nguoilienhe').val(data.nguoilh);
                                    $('#d_nguoichiutn').val(data.nguoictn);
                                    $('#select_chucvu').val(data.cvid);
                                    $('#d_taikhoan').prop("disabled", true);
                                    $('#d_nguoilienhe').prop("disabled", true);
                                    $('#d_nguoichiutn').prop("disabled", true);
                                    $('#select_chucvu').prop("disabled", true);
                                }
                            });
                        }
                    });
             };
             var loadChucvu = function(){
                 $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        data: {action: "loadchucvu" },
                        success: function (data) {
                            // replace div's content with returned data
                            $('#select_chucvu').html(data);
                            $('#sad_chucvu').html(data);
                        }
                    });
             };
            
             $(document).on('change', 'input:radio:checked', function() {
                 var selectValue = $(this).val();
                 if(selectValue == 1){
                     $('#select_ttnhap').show();
                     loadAllTBP();
                     
                 }else{
                     $('#d_taikhoan').prop("disabled", false);
                    $('#d_nguoilienhe').prop("disabled", false);
                    $('#d_nguoichiutn').prop("disabled", false);
                    $('#select_chucvu').prop("disabled", false);
                     $('#select_ttnhap').hide();
                     $('#d_taikhoan').val('');
                    $('#d_nguoilienhe').val('');
                    $('#d_nguoichiutn').val('');
                    $('#select_chucvu').val(1);
                 }
             });
             $(document).on('change', '#select_ttnhap', function() {
                    var saveInforId = $('#select_ttnhap').val();
                    $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                         dataType: "json",
                        data: {action: "loadSaveInfor",saveInforId: saveInforId },
                        success: function (data) {
                            // replace div's content with returned data
                            //$('#tb_emailbnbd tbody').html(data);
                            console.log(data);
                            $('#d_taikhoan').val(data.taikhoan);
                            $('#d_nguoilienhe').val(data.nguoilh);
                            $('#d_nguoichiutn').val(data.nguoictn);
                            $('#select_chucvu').val(data.cvid);
                            $('#d_taikhoan').prop("disabled", true);
                            $('#d_nguoilienhe').prop("disabled", true);
                            $('#d_nguoichiutn').prop("disabled", true);
                            $('#select_chucvu').prop("disabled", true);
                        }
                    });
             });
             var saveAddTP = function(){
                 var shortname = $('#d_newtt').val();
                 var taikhoan = $('#dad_tk').val();
                 var nguoilienhe = $('#dad_nguoilh').val();
                 var nguoichiutn= $('#dad_nguoictn').val();
                 var chucvu = $('#sad_chucvu').val();
                 if(shortname.trim() ===''){
                     alert("Nhập tên rút gọn");
                     return false;
                 }else if(taikhoan.trim() ===''){
                     alert("Nhập tên rút gọn");
                     return false;
                 }else if(nguoilienhe.trim() ===''){
                     alert("Nhập tên rút gọn");
                     return false;
                 }else if(nguoichiutn.trim() ===''){
                     alert("Nhập tên rút gọn");
                     return false;
                 }
                 $.ajax({
                    type: "GET",
                    url: "thuphiservlet",
                    data: {action: "addInforTBP",shortname: shortname,taikhoan: taikhoan,
                    nguoilienhe: nguoilienhe,nguoichiutn: nguoichiutn,chucvu: chucvu },
                    success: function (data) {
                        // replace div's content with returned data
                        //$('#tb_emailbnbd tbody').html(data);
                        if(data === "OK"){
                            $('#div_result').html('<span>Lưu thành công</span>');
                            $('#d_newtt').val('');
                            $('#dad_tk').val('');
                            $('#dad_nguoilh').val('');
                            $('#dad_nguoictn').val('');
                            $('#sad_chucvu').val(1);
                        }else if(data === "FAIL"){
                            console.log("NOT SUCCESS");
                            $('#div_result').html('<span>Không lưu được</span>');
                        }
                    }
                });
             };
             
             var thongbaoNo = function(){
                 var thongtin = [];
                 $('.sendmail').each(function(i,el){
                         thongtin.push($(this).val());
                  });
                  var email = $('#select_email').val();
                  $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        data: {action: "guithongbaono",thongtin: thongtin,email : email },
                        success: function (data) {
                            // replace div's content with returned data
                            //$('#tb_emailbnbd tbody').html(data);
                            console.log(data);
                            if(data ==='OK'){
                                    
                            }else if(data ==="FAIL"){

                            }
                        }
                    });
             };
             
             var exportByBnbd = function(){
                 var fromday = $('#ctp_ngaybatdau').val();
                var today  = $('#ctp_ngaykethuc').val();
                var loaidon  = $('#ctp_loaidon').val();
                var loaihinhnhan = $('#ctp_loaihinhnhan').val();
                var sothongbaophi = $('#ctp_sothongbaophi').val();
                var manhan = $('#ctp_maloainhan').val();
                var searchBNBD = $('#searchBnbd').val();
                 window.location= "thuphiservlet?action=exportCTPBNBD&fromday="+fromday+"&today="+today+"&loaidon="+loaidon
                         +"&loaihinhnhan="+loaihinhnhan+"&sothongbaophi="+sothongbaophi+"&manhan="+manhan+"&searchBNBD="+searchBNBD;
             };
             
             var exportByBBD = function(){
                 var fromday = $('#ctp_ngaybatdau').val();
                var today  = $('#ctp_ngaykethuc').val();
                var loaidon  = $('#ctp_loaidon').val();
                var loaihinhnhan = $('#ctp_loaihinhnhan').val();
                var sothongbaophi = $('#ctp_sothongbaophi').val();
                var manhan = $('#ctp_maloainhan').val();
                var searchBNBD = $('#tbctp_bnbd').find('input[id^="ctp_cb"]:checked').parent().parent().find('input[id^="ctp_khid"]').val();
               //  var donids = $('#tbctp_bnbd').find('input[id^="ctp_cb"]:checked').parent().parent().find('input[id^="ctp_donid"]').val();
                 window.location = "thuphiservlet?action=exportCTPBBD&fromday="+fromday+"&today="+today+"&loaidon="+loaidon
                         +"&loaihinhnhan="+loaihinhnhan+"&sothongbaophi="+sothongbaophi+"&manhan="+manhan+"&searchBNBD="+searchBNBD;
             };
             
             var exportAll = function(){
                 var fromday = $('#ctp_ngaybatdau').val();
                var today  = $('#ctp_ngaykethuc').val();
                var loaidon  = $('#ctp_loaidon').val();
                var loaihinhnhan = $('#ctp_loaihinhnhan').val();
                var sothongbaophi = $('#ctp_sothongbaophi').val();
                var manhan = $('#ctp_maloainhan').val();
                var searchBNBD = $('#searchBnbd').val();
                 window.location= "thuphiservlet?action=exportCTPAll&fromday="+fromday+"&today="+today+"&loaidon="+loaidon
                         +"&loaihinhnhan="+loaihinhnhan+"&sothongbaophi="+sothongbaophi+"&manhan="+manhan+"&searchBNBD="+searchBNBD;
             };
             
             var resetBaoCo = function(){
               //  $('#ctp_sobaoco').val("");$('#ctp_ngaybaoco').val("");
         //      $('#ctp_nguoiplbaoco').val("");$('#ctp_sotien').val("");$('#ctp_ndbaoco').val("");$('#ctp_stconlai').val("");
                    $('#panel_baoco').find('input').each(function(){
                        $(this).val("");
                    });
             };
             
             var clearBaoCo = function(){
                 $('#ctp_khachhangname').val("");$('#ctp_khachhangdiachi').val("");
                 $('#ctp_khachhangmaso').val("");$('#ctp_baocokhid').val("");
                 $('#ctp_sobaoco').val("");$('#ctp_sobaoco').val("");
                 $('#ctp_nguoiplbaoco').val("");$('#ctp_sotien').val("");
                 $('#ctp_stconlai').val("");
             };
             // lưu báo có
             var luuBaoCo = function(){
                 var sobaoco = $('#ctp_sobaoco').val();
                 if(sobaoco.trim() === ''){
                     $('#content_baoco_error').html('chưa nhập số báo có');
                     $( "#dialog-message" ).dialog("open");
                     return false;
                 }
                 var ngaybaoco = $('#ctp_ngaybaoco').val();
                  if(ngaybaoco.trim() === ''){
                     $('#content_baoco_error').html('chưa chọn ngày báo có');
                     $( "#dialog-message" ).dialog("open");
                     return false;
                 }
                 var nguoiplbaoco  = $('#ctp_nguoiplbaoco').val();
                 if(nguoiplbaoco.trim() === ''){
                     $('#content_baoco_error').html('chưa nhập người phát lệnh báo có');
                     $( "#dialog-message" ).dialog("open");
                     return false;
                 }
                 var sotienbc = $('#ctp_sotien').val().replace(/[, .]/g,'');
                 if(sotienbc.trim() === '' || !$.isNumeric(sotienbc)){
                     $('#content_baoco_error').html('chưa nhập số tiền báo có/ số tiền không phải kiểu số');
                     $( "#dialog-message" ).dialog("open");
                     return false;
                 }
                 var ndbaoco = $('#ctp_ndbaoco').val();
                 if(ndbaoco.trim() === ''){
                     $('#content_baoco_error').html('chưa nhập nội dung báo có');
                     $( "#dialog-message" ).dialog("open");
                     return false;
                 }
                 var khid = $('#ctp_baocokhid').val();
                 var khCheck = $('#cb_khsave').prop('checked');
                 if(khCheck){
                     if(khid === undefined || khid === ''){
                        $('#content_baoco_error').html('chưa nhập khách hàng');
                        $( "#dialog-message" ).dialog("open");
                        return false;
                    }
                 }
                 
                 $.ajax({
                        type: "GET",
                        url: "thuphiservlet",
                        data: {action: "luubaoco",sobaoco: sobaoco,ngaybaoco : ngaybaoco, nguoiplbaoco : nguoiplbaoco,
                            sotienbc : sotienbc, ndbaoco: ndbaoco,khid : khid,khcheck :  khCheck},
                        success: function (data) {
                            // replace div's content with returned data
                            //$('#tb_emailbnbd tbody').html(data);
                            console.log(data);
                            if(data ==='OK'){
                                    $('#result_insert').html("LƯU BÁO CÓ SỐ "+sobaoco+" THÀNH CÔNG");
                                    resetBaoCo();
                            }else if(data ==="FAILED"){
                                    $('#result_insert').html("LƯU BÁO CÓ SỐ "+sobaoco+" KHÔNG THÀNH CÔNG");
                            }
                        }
                        ,error : function(){
                            $('#result_insert').html("LƯU BÁO CÓ SỐ "+sobaoco+" KHÔNG THÀNH CÔNG");
                        }
                    }); 
             };
             
             $('#btn_saveBC').on('click',luuBaoCo);
             $('#btn_exportFile').on('click',function(){
                 var typeExport = $('#select_export_Excel').val();
                     if(typeExport ==='1'){
                            exportByBnbd();
                    }else if(typeExport ==='2'){
                        var bnbdSize = $('#tbctp_bnbd').find('input[id^="ctp_cb"]:checked').size();
                        if(bnbdSize ===0){
                            $('#span_error_Export').html("Chưa chọn Bên nhận bảo đảm");
                        }else if(bnbdSize > 1){
                            $('#span_error_Export').html("Chỉ chọn 1 bên nhận bảo đảm khi xuất file");
                        }else{
                            exportByBBD();
                        }
                    }else if(typeExport ==='3'){
                        exportAll();
                    }
             });
             $('#btn_Sendmail').on('click',thongbaoNo);
             $('#btn_SaveTP').on('click',saveTtBaoPhi);
             $('#btn_SaveAddTP').on('click',saveAddTP);
           // $('#searchBnbd').on('keydown paste',loadbnbd_ctp);
            $('#ctp_ngaybatdau,#ctp_ngaykethuc,#ctp_maloainhan,#ctp_sothongbaophi,#searchBnbd').on('keydown',function(e){
                    if(e.keyCode ===13){
                        loadbnbd_ctp();
                    }
            });
            $('#btn_resetBC').on('click',resetBaoCo);
            
            $("#ctp_type_luubc").on('change',function(){
                
                    if($(this).val() === '1'){
                        $('#btn_saveBC').hide();
                        $('#btn_resetBC').hide();
                        $('#btn_updateBC').hide();
                        $('#ctp_stconlai').show();
                        $('#div_returnbank').hide();
                        $('label').each(function(){
                            if($(this).prop('for') ==='ctp_stconlai'){
                                $(this).show();
                            }
                        });
                        // hiện modal tìm kiếm báo có
                        $('#search_bao_co_modal').modal("show");
                        $('#ctp_sobcselect').show();
                        $('#img_searchbc_add').show();
                        $('#img_searchbc_sub').show();
                        $('#cb_khsave').hide();
                        $('#ctp_tongtienthua').show();
                        $('label').each(function(){
                           if($(this).prop('for') ==='ctp_tongtienthua') {
                               $(this).show();
                           }
                        });
                        $('#panel_bienlai').show();
                    }else if($(this).val() === '2'){
                        $('#btn_saveBC').hide();
                        $('#btn_resetBC').hide();
                        $('#ctp_stconlai').show();
                        $('#div_returnbank').hide();
                        $('label').each(function(){
                            if($(this).prop('for') ==='ctp_stconlai'){
                                $(this).show();
                            }
                        });
                        $('#cb_khsave').show();
                        $('#ctp_sobcselect').hide();
                        $('#ctp_sobcselect').empty();
                        $('#img_searchbc_add').hide();
                        $('#img_searchbc_sub').hide();
                        $('#ctp_tongtienthua').hide();
                        $('#btn_updateBC').hide();
                        $('label').each(function(){
                           if($(this).prop('for') ==='ctp_tongtienthua') {
                               $(this).hide();
                           }
                        });
                        $('#panel_bienlai').show();
                    }else if($(this).val() === '0'){
                        $('#btn_saveBC').show();
                        $('#btn_resetBC').show();
                        $('#ctp_stconlai').hide();
                        $('#div_returnbank').hide();
                        $('label').each(function(){
                            if($(this).prop('for') ==='ctp_stconlai'){
                                $(this).hide();
                            }
                        });
                        $('#btn_updateBC').hide();
                        $('#cb_khsave').show();
                        $('#ctp_sobcselect').hide();
                        $('#ctp_sobcselect').empty();
                        $('#img_searchbc_add').hide();
                        $('#img_searchbc_sub').hide();
                        $('#ctp_tongtienthua').hide();
                        $('label').each(function(){
                           if($(this).prop('for') ==='ctp_tongtienthua') {
                               $(this).hide();
                           }
                        });
                        $('#panel_bienlai').show();
                    }else if($(this).val() ==='3'){
                        $('#ctp_tongtienthua').hide();
                        $('label').each(function(){
                           if($(this).prop('for') ==='ctp_tongtienthua') {
                               $(this).hide();
                           }
                        });
                        $('#ctp_sobcselect').show();
                        $('#ctp_sobcselect').empty();
                        $('#img_searchbc_add').show();
                        $('#img_searchbc_sub').show();
                        $('#ctp_stconlai').hide();
                        $('#panel_bienlai').hide();
                        $('#btn_updateBC').show();
                        $('#btn_saveBC').hide();
                        $('#cb_khsave').show();
                        $('#div_returnbank').show();
                    }
                });
                /** $('#ddt_thoigiannhap').on('keydown',function(e){
                    if(e.keyCode ===13){
                        $('#ddt_search').click();
                    }
            });
      $('#ctp_ngaybatdau').on('change',function(){
                    loadbnbd_ctp();
                });
                $('#ctp_ngaykethuc').on('change',function(){
                    loadbnbd_ctp();
                });**/
            loadAllTBP();
            loadChucvu();
            $('#ctp_stconlai').hide();
                   $('label').each(function(){
                       if($(this).prop('for') ==='ctp_stconlai'){
                           $(this).hide();
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
                  var loadBCCB = function(){
                      var bcid = $('#ctp_sobcselect').val();
                      $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            dataType: 'json',
                            data: {action: "loadbaococtp",bcid: bcid},
                            success: function (data) {
                               $('#ctp_khachhangname').val(data.khname);$('#ctp_khachhangdiachi').val(data.address);
                               $('#ctp_khachhangmaso').val(data.account);$('#ctp_ngaybaoco').val(data.ngaybc);
                               if(data.khid === ''){
                                   $('#cb_khsave').prop('checked',false);
                               }else{
                                   $('#cb_khsave').prop('checked',true);
                               }
                               $('#ctp_sobaoco').val(data.bcnumber);$('#ctp_nguoiplbaoco').val(data.nguoipl);
                               $('#ctp_ndbaoco').val(data.ndbc);$('#ctp_sotien').val(data.total);
                               $('#ctp_stconlai').val(data.bcthua);$('#ctp_baocokhid').val(data.khid);
                            }
                        });
                  };
                  $('#cb_khsave').on('change',function(){
                     if(!$(this).is(":checked")){
                         $('#ctp_khachhangname').hide();
                         $('#ctp_khachhangdiachi').hide();
                         $('#ctp_khachhangmaso').hide();
                     }else{
                         $('#ctp_khachhangname').show();
                         $('#ctp_khachhangdiachi').show();
                         $('#ctp_khachhangmaso').show();
                     }
                  });
                  var _baocoSearch = [];
                  var searchBaoCo = function(){
                      var ngaythang = $('#searchbc_ngaybc').val();
                      var sobc = $('#searchbc_sobaoco').val();
                      var khname = $('#searchbc_khname').val();
                      var account = $('#searchbc_account').val();
                      var nguoipl = $('#searchbc_nguoipl').val();
                      _baocoSearch.splice(0,_baocoSearch.length);
                      _baocoSearch.push(ngaythang);_baocoSearch.push(sobc);_baocoSearch.push(khname);
                      _baocoSearch.push(account);_baocoSearch.push(nguoipl);
                      $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            dataType: 'json',
                            data: {action: "searchbaocoluu",page: 1,ngaythang: ngaythang,sobc : sobc, khname : khname, account : account, nguoipl : nguoipl},
                            success: function (data) {
                               $('#tb_searchbc_luu tbody').html(data.data);
                               var totalPages = data.totalpage;
                                var currentPage = data.currentpage;
                                $pagination.twbsPagination('destroy');
                                $pagination.twbsPagination($.extend({}, defaultOpts, {
                                    startPage: currentPage,
                                    totalPages: totalPages,
                                    onPageClick: function (event, page) {
                                        console.log('PAGE = '+page);
                                        $.ajax({
                                            type: "GET",
                                            url: "thuphiservlet",
                                            dataType: 'json',
                                            data: {action: "searchbaocoluu",page: page,ngaythang: _baocoSearch[0],sobc : _baocoSearch[1], khname : _baocoSearch[2], account : _baocoSearch[3], nguoipl : _baocoSearch[4]},
                                            success: function (data) {
                                               $('#tb_searchbc_luu tbody').html(data.data);
                                            }
                                        });
                                    }
                                }));
                            }
                        });
                  };
                  var addOption = [];
                  var tongtien = [];
                  $(document).on('click','.cb_searchbc',function(){
                      if($(this).is(':checked')){
                          var sobc = $(this).parent().parent().find('td').eq(1).html();
                          tongtien.push($(this).parent().parent().find('td').eq(4).html().replace(/\./g,''));
                          addOption.push($(this).val()+"_"+sobc);
                         // $('#ctp_sobcselect').append("<option value='"+$(this).val()+"'>"+sobc+"</option>");
                      }else{
                          var id = $(this).val();
                          $.each(addOption,function(i,el){
                              var optionID = el.split("_")[0];
                              if(id === optionID){
                                  addOption.splice(i,1);
                                  tongtien.splice(i,1);
                              }
                          });
                      }
                  });
                  $('#search_bao_co_modal').on('hidden.bs.modal', function () {
                        addOption = [];
                        tongtien = [];
                        $('#tb_searchbc_luu tbody').empty();
                    });
                  $('#btn_modal_search_ok').on('click',function(){
                     // console.log(addOption);
                    var option = "";
                      for(var i=0; i < addOption.length;i++){
                          var total = 0;
                          if($('#ctp_sobcselect').find('option').length >0){
                              var check = false;
                              $('#ctp_sobcselect').find('option').each(function(){
                                    if($(this).val() === addOption[i].split("_")[0]){
                                         check = true;
                                    }
                                });
                                if(!check){
                                    option = '<option value="'+addOption[i].split("_")[0]+'">'+addOption[i].split("_")[1]+'</option>';
                                    total += Number(tongtien[i]);
                                    console.log(total);
                                }
                          }else{
                                option += '<option value="'+addOption[i].split("_")[0]+'">'+addOption[i].split("_")[1]+'</option>';
                               // $('#ctp_sobcselect').append(option);
                               total += Number(tongtien[i]);
                          }
                          $('#ctp_sobcselect').append(option);
                          if($('#ctp_tongtienthua').val() === ''){
                              $('#ctp_tongtienthua').val(total);
                          }else{
                             var oldtotal =  $('#ctp_tongtienthua').val();
                             total += Number(oldtotal);
                             $('#ctp_tongtienthua').val(total);
                          }
                          
                      }
                      loadBCCB();
                       $('#search_bao_co_modal').modal('toggle');
                  });
                  
                  $('#ctp_sobcselect').on('change',loadBCCB);
                  $('#img_searchbc_add').on('click',function(){
                      $('#search_bao_co_modal').modal('toggle');
                  });
                  $('#img_searchbc_sub').on('click',function(){
                      var bcids = [];
                      if($('#ctp_sobcselect').find('option').length >0){
                          $('#ctp_sobcselect').find('option').each(function(){
                                bcids.push($(this).val());
                            });
                            var bcluu = bcids.join();
                            $.ajax({
                                  type: "GET",
                                  url: "thuphiservlet",
                                  data: {action: "loadxoabcluu",bcluu: bcluu},
                                  success: function (data) {
                                      console.log(data);
                                     $('#tb_xoabc_luu tbody').html(data);
                                  }
                              });
                            $('#xoa_bao_co_modal').modal('toggle');
                      }else{
                          $('#content_baoco_error').html('Không có báo có đã lưu để xóa');
                          $( "#dialog-message" ).dialog("open");
                          return false;
                          
                      }
                      
                  });
                  var bcremove = [];
                  function xoaviewBCLuu() {
                      var id = $(this).prop('id');
                      var value = id+"_"+$(this).parent().parent().find('td').eq(3).html();
                      bcremove.push(value);
                      $(this).parent().parent().remove();
                  };
                  $(document).on('click','.xoa_bc_img',xoaviewBCLuu);
                  $('#xoa_bao_co_modal').on('hidden.bs.modal', function () {
                        bcremove = [];
                        $('#tb_xoabc_luu tbody').empty();
                    });
                  $('#btn_modalxoa_ok').on('click',function(){
                      $('#ctp_sobcselect').find('option').each(function(){
                         for(var i=0; i < bcremove.length;i++) {
                             if($(this).val()===bcremove[i].split("_")[0]){
                                 $(this).remove();
                                 var total = parseInt($('#ctp_tongtienthua').val()) - parseInt(bcremove[i].split("_")[1]);
                                 $('#ctp_tongtienthua').val(total);
                             }
                         }
                      });
                      if($('#ctp_sobcselect').find('option').length > 0){
                          loadBCCB();
                      }else{
                          resetBaoCo();
                      }
                      
                      $('#xoa_bao_co_modal').modal('toggle');
                  });
                  var updateBaoCo = function(){
                      var ngaybc = $('#ctp_ngaybaoco').val();
                      var isreturnBank = $('#cb_returnBank').prop("checked");
                      console.log(isreturnBank);
                      if(ngaybc.trim() === ''){
                          $('#content_baoco_error').html('Chưa chọn ngày báo có');
                          $( "#dialog-message" ).dialog("open");
                          return false;
                      }
                      var  sobc = $('#ctp_sobaoco').val();
                      if(sobc.trim() === ''){
                          $('#content_baoco_error').html('Chưa nhập số báo có');
                          $( "#dialog-message" ).dialog("open");
                          return false;
                      }
                      var nguoipl = $('#ctp_nguoiplbaoco').val();
                      if(nguoipl.trim() === ''){
                          $('#content_baoco_error').html('Chưa nhập người phát lệnh báo có');
                          $( "#dialog-message" ).dialog("open");
                          return false;
                      }
                      var ndbc = $('#ctp_ndbaoco').val();
                      if(ndbc.trim() === ''){
                          $('#content_baoco_error').html('Chưa nhập nội dung báo có');
                          $( "#dialog-message" ).dialog("open");
                          return false;
                      }
                      var khid = $('#ctp_baocokhid').val();
                      var khsave = $('#cb_khsave').is(":checked");
                      if(khsave){
                          if(khid.trim() === ''){
                                $('#content_baoco_error').html('Chưa nhập khách hàng báo có');
                                $( "#dialog-message" ).dialog("open");
                                return false;
                            }
                      }
                      var sotien = $('#ctp_sotien').val();
                      if(sotien.trim() === ''){
                          $('#content_baoco_error').html('Chưa nhập số tiền báo có');
                          $( "#dialog-message" ).dialog("open");
                          return false;
                      }
                      var bcid = $('#ctp_sobcselect').val();
                     // alert($('#ctp_sobcselect').length);
                      if($('#ctp_sobcselect').val() === null){
                          $('#content_baoco_error').html('Chưa lấy báo có đã lưu');
                          $( "#dialog-message" ).dialog("open");
                          return false;
                      }
                      
                      $('#result_insert').html("ĐANG CẬP NHẬT");
                      $.ajax({
                          type: "GET",
                          url: "thuphiservlet",
                          data: {action: "updatebaoco",ngaybc: ngaybc,sobc: sobc,nguoipl : nguoipl, ndbc : ndbc, khid: khid, khsave : khsave,sotien: sotien,bcid : bcid,isreturnBank : isreturnBank },
                          success: function (data) {
                              console.log(data);
                              if(data === 'OK'){
                                  $('#result_insert').html("CẬP NHẬT BÁO CÓ THÀNH CÔNG");
                              }else{
                                  $('#result_insert').html("CẬP NHẬT BÁO CÓ KHÔNG THÀNH CÔNG");
                              }
                          }
                      }); 
                  };
                  $('#cb_returnBank').on('change',function(){
                      if($(this).is(':checked')){
                          var oldcontent = $('#ctp_ndbaoco').val();
                          $('#ctp_ndbaoco').val(oldcontent+" -- "+"Hoàn trả phí nộp sai");
                      }else{
                          var oldcontent = $('#ctp_ndbaoco').val().substring(0,$('#ctp_ndbaoco').val().indexOf("--"));
                          $('#ctp_ndbaoco').val(oldcontent);
                      }
                  });
                  $('#div_returnbank').hide();
                  $('#btn_updateBC').on('click',updateBaoCo);
                  $('#ctp_tongtien').on('keyup',changeMoney);
                  $('#ctp_sotien').on('keyup',changeMoney);
                  $('#btn_searchbc').on('click',searchBaoCo);
         </script>
    </body>
</html>
