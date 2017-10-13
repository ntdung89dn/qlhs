<%-- 
    Document   : xembaophi
    Created on : Jul 5, 2016, 5:20:29 PM
    Author     : Thorfinn
--%>

<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
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
            #loadingbnbd ,#loadingbbd{
                height: 500px; 
                overflow-y: scroll; 
              }
          @media screen and (min-width: 900px) {
                    #myModal .modal-dialog  {width:900px;}
            }
            table#tb_emailbnbd td {
                    word-break: break-all;
            }
            #xbp_bnbd td,#xbp_bbd td{
            background: #ffffff;
        }
        .row_focus{
            color: red;
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
                <li class="sub-menu"><a href="thuphi.jsp?page=dathuphi">Đã thu phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=tkbl">Thống kê biên lai</a></li>
                <li class="sub-menu" ><a href="thuphi.jsp?page=tkbc">Thống kê báo có</a></li>
                <li class="sub-menu"><a  href="thuphi.jsp?page=thcn">Tổng hợp công nợ</a></li>
                <li class="sub-menu"><a class="active" href="thuphi.jsp?page=xembaophi">Xem thông báo phí</a></li>
                <li class="sub-menu"><a href="thuphi.jsp?page=khongcostk">Đơn chưa thu phí không có số tài khoản</a></li>
        </ul> <br>
        <div class="modal_loading"></div>
        <table class="table" >
                <tbody>
                    <tr>
                        <td>Lọc Dữ Liệu</td>
                        <td><input class="form-control" type="text" value="" id="xbp_sohieu" placeholder="Số hiệu"></td>
                        <%
                            Calendar cal = Calendar.getInstance();
                             int month = cal.get(Calendar.MONTH)+1;
                             int year = cal.get(Calendar.YEAR);
                            %>
                        <td><input class="form-control" type="text" value="<%=month%>" id="xbp_thang" placeholder="Tháng"></td>
                        <td>
                            <input class="form-control" type="text" value="<%=year%>" id="xbp_nam" placeholder="Năm">
                        </td>
                    </tr>
                </tbody>
            </table>
                        
                        
         <div id="div_loading"></div>
             
        <table style="width: 100%;">
            <tbody>
                <tr style="width: 50%;">
                    <td style="width: 10%">Bên Nhận BĐ</td>
                    <td style="width: 20%"><input class="form-control" type="text" value="" id="dns_bnbd"></td>
                    <td style="width: 3%"><img src="./images/check-all.png" onclick="checkAll()"></td>
                    <td style="width: 3%"><img src="./images/check-all-delete.png" onclick="unCheckAll()"></td>
                    <td style="width: 5%"><label><input type="radio" name="optradio">Option</label></td>
                    <td style="width: 5%"><label><input type="radio" name="optradio">Option</label></td>
                </tr>
                <tr style="width: 50%;">
                    <td colspan="6" style="vertical-align:top;">
                        <div id="loadingbnbd">
                            <table border="1" id="xbp_bnbd" style="width: 100%;">
                                <thead style="background-color: #87CEFA">
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên bên BNBĐ</th>
                                        <th>Chọn</th>
                                        <th>SL</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>

                        </div>
                    </td>
                    <td colspan="5" style="vertical-align:top;">
                        <div id="loadingbbd" >
                            <table border="1" id="xbp_bbd" style="width: 100%;">
                                <thead style="background-color: #87CEFA">
                                    <tr>
                                        <th>STT</th>
                                        <th>Loại hình nhận</th>
                                        <th>Ngày</th>
                                        <th>Bên Bảo Đảm</th>
                                         <th>ST</th>
                                         <th>TT</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                </tbody>
                            </table>
 
                        </div>
                    </td>
                    
                </tr>
            </tbody>
        </table>
        Chọn dòng để in thông báo phí: <input type="text" id="1strow" style="width: 30px;">--><input type="text" id="2ndrow" style="width: 30px;">
        <img src="./images/printer3.png" data-toggle="modal" onclick="InDon()"> <a><img src="./images/email.png" onclick="sendEmail()"></a>
        <!-- Modal  send email-->
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
                                    <thead style="background-color: #87CEFA">
                                        <tr>
                                            <th>STT</th>
                                            <th>BNBD</th>
                                            <th>Số Tài Khoản</th>
                                            <th>Email</th>
                                            <th>Chọn</th>
                                            <th>Gửi</th>
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
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              </div>
            </div>

          </div>
        </div>
        <div id="dialog" title="Lỗi">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>
            Chưa chọn thông báo phí</p>
          </div>
          <script>
              $body = $("body");
              var loadThongbaophi = function(){
                  $body.addClass("loading");
                  var sohieu = $('#xbp_sohieu').val();
                  var thang = $('#xbp_thang').val();
                  var nam = $('#xbp_nam').val();
                  var bnbd = $('#dns_bnbd').val();
                  $.ajax({
                        type: "GET",
                        url:"thuphiservlet",
                        data:{"action":"loadsohieu",sohieu: sohieu,thang: thang, nam: nam,bnbd: bnbd},
                        success: function (data) {
                            $body.removeClass("loading");
                            $('#xbp_bnbd tbody').html(data);
                        }
                    });
              };
            //  loadThongbaophi();
              $(document).on('click','.td_click',function(){
                  $(this).parent().parent().find("tr").removeClass("row_focus");
                  $(this).parent().addClass("row_focus");
                 var donids = $(this).parent().find('input.input_donids').val();
                  $.ajax({
                        type: "GET",
                        url:"thuphiservlet",
                        data:{"action":"loaddontbp",donids: donids},
                        success: function (data) {
                            $('#xbp_bbd tbody').html(data);
                        }
                    });
              });
              
              var timeout;
              var searchBNBD = function(){
                  var search = $(this).val();
                  if(search.length >5 || search ===''){
                            if(timeout){ clearTimeout(timeout);}
                                    //start new time, to perform ajax stuff in 500ms
                             timeout = setTimeout(function() {
                                     var sohieu = $('#xbp_sohieu').val();
                                    var thang = $('#xbp_thang').val();
                                    var nam = $('#xbp_nam').val();
                                    $.ajax({
                                          type: "GET",
                                          url:"thuphiservlet",
                                          data:{"action":"loadsohieu",sohieu: sohieu,thang: thang, nam: nam,bnbd: search},
                                          success: function (data) {
                                              $('#xbp_bnbd tbody').html(data);
                                          }
                                      });
                              },300);
                  }
              };
              
              var checkAll = function(){
                  $('#xbp_bnbd').find('input:checkbox').prop('checked', true);
              };
              
              var unCheckAll  = function(){
                  $('#xbp_bnbd').find('input:checkbox').prop('checked', false);
              };
             // $(document).on('click','input:checkbox',checkAll);
              var InDon = function(){
                var x = $('input:checkbox:checked').length;
                if(x >0){
                    var tbpid = [];
                    $('input:checkbox:checked').each(function(i,el){
                   //    console.log($(this).val());
                       tbpid.push($(this).val());
                    });
                    $.ajax({
                          type: "GET",
                          url:"thuphiservlet",
                          data:{"action":"loadtbpdaluu",tbpid: tbpid},
                          success: function (data) {
                              window.location.href = './print/print_xemtbp.jsp';
                          }
                      });
                }else{
                    alert("Chưa chọn thông báo phí");
                }
               
               };
            /**   $body = $("body");
               $(document).on({
                    ajaxStart: function() { $body.addClass("loading");    },
                     ajaxStop: function() { $body.removeClass("loading"); }    
                });**/
                
                var sendEmail = function(){
          //       var title = "Thông báo nhắc nợ ";
                 var x = $('input:checkbox:checked').length;
                 if(x ===0){
                    $( "#dialog" ).dialog( "open" );
                 }else{
                    var tbpid = [];
                    var khid = [];
                    $('input:checkbox:checked').each(function(i,el){
                   //    console.log($(this).val());
                       tbpid.push($(this).val());
                       khid.push($(this).parent().find('input.input_khid').val());
                    //   console.log($(this).parent().find('input.input_khid').val());
                    });
                    
                    $('input.input_khid').each(function(i,el){
                   //    console.log($(this).val());
                       
                    });
                     $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            dataType: "json",
                            data: {action: "loademailtbp",khid: khid,tbpid: tbpid },
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
               var sendThongBaoPhi = function(){
                   //var thongtin = [];
                   var email = $('#select_email').val();
                 $('.sendmail').each(function(i,el){
                     var div_result = $(this).parent().parent().find(".send_result");
                     div_result.html("<img src='./images/loading.gif'>");
                     if($(this).is(":checked")){
                         $.ajax({
                            type: "GET",
                            url: "thuphiservlet",
                            data: {action: "guithongbaophi",thongtin: $(this).val(),email : email },
                            success: function (data) {
                                // replace div's content with returned data
                                //$('#tb_emailbnbd tbody').html(data);
                                console.log(data);
                                if(data ==='OK'){
                                    div_result.html("<img src='./images/success.png'>");
                                }else if(data ==="FAIL"){
                                    div_result.html("<img src='./images/clear_btn.png'>");
                                }
                            }
                        });
                     }
                        
                  });
               };
               $('#btn_Sendmail').on('click',sendThongBaoPhi);
              $('#dns_bnbd').on('change paste',loadThongbaophi);
              $('#xbp_sohieu').on('keyup paste',loadThongbaophi);
              $('#xbp_thang').on('keyup paste',loadThongbaophi);
              $('#xbp_nam').on('keyup paste',loadThongbaophi);
         </script>      
    </body>
</html>
