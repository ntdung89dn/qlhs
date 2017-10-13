<%-- 
    Document   : csgt
    Created on : Mar 10, 2017, 1:46:40 PM
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
                <li class="sub-menu"><a  href="vanthu.jsp?page=xemvanthu">Xem Văn Thư</a></li>
                 <%
                     }
                if(session.getAttribute("10").equals("1")){
                %>
                <li class="sub-menu"><a class="active" href="vanthu.jsp?page=themcsgt">Thêm CSGT</a></li>
                <%
                     }
                if(session.getAttribute("11").equals("1")){
                %>
                <li class="sub-menu"><a  href="vanthu.jsp?page=xemcsgt">Xem Văn Thư gửi CSGT</a></li>
                <%
                     }
                %>
        </ul><br>
        <div class="modal_loading"></div>
        <div style="text-align: center;"><span id="result_add" style="font-weight: bold;color: red;"></span></div>
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
                            <button type="button" id="btn_submit" class="btn btn-secondary" style="width: 100%">Lưu Lại</button>
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
        <div>
        <div id="kh_table"> Lọc dữ liệu</div>
        <div class="row">
            <div class="col-xs-2">
                <input type="text" id="s_ngaybatdau" class="form-control" value="" placeholder="Ngày bắt đầu" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" id="s_ngayketthuc" class="form-control" value="" placeholder="Ngày kết thúc" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" id="s_maonline" class="form-control repalacedot" value="" placeholder="Mã Online" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <select id="s_loainhan" class="form-control">
                    <option value="0">Loại nhận</option>
                    <option value="1">CE</option>
                    <option value="2">CF</option>
                </select>
            </div>
            <div class="col-xs-2">
                <input type="text" id="s_manhan" class="form-control repalacedot" value="" placeholder="Mã nhận" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" id="s_bnbd" class="form-control" value="" placeholder="Bên nhận bảo đảm" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" id="s_bbd" class="form-control" value="" placeholder="Bên Bảo Đảm" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <input type="text" id="s_barcode" class="form-control repalacedot" value="" placeholder="Mã Barcode" style="width: 100%"/>
            </div>
            <div class="col-xs-2">
                <button type="button" id="btn_searchCSGT" class="btn btn-primary">Tìm kiếm</button>
            </div>
        </div>
            <br>
            <table class="table table-bordered" id="tb_vtcsgt" >
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
                
            </tbody>
        </table>
        </div>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        
        <script>
            // parameters
            var _donSelect = [];
            var _madonSelect = [];
            var _searchList = [];
            var _city = '';
            $body = $("body");
            //function ...........
            $( "#ngaygoi" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
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
                // load số trang
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
                                        "action" : "csgtcg",page: 1,loainhan: 0
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
                                    console.log('PAGE = '+page);
                                    console.log(event.type);
                                    $.ajax({
                                            url : "vanthu",
                                            type : "GET",
                                            data : {
                                                             "action" : "csgtcg",page: page,loainhan:0
                                                     },
                                             dataType : "json",
                                             success: function (data) {
                                                 console.log(data);
                                                 $('#tb_vtcsgt tbody').html(data.data);
                                             }
                                       });
                                }
                            }));
                        }
                  });
                  //****************
                  var loadCsgtCg = function(){
                      var ngaybatdau = $('#s_ngaybatdau').val();
                      var ngayketthuc = $('#s_ngayketthuc').val();
                      var maonline = $('#s_maonline').val();
                      var manhan = $("#s_manhan").val();
                      var bnbd = $('#s_bnbd').val();
                      var bbd = $('#s_bbd').val();
                      var barcode = $('#s_barcode').val();
                      var loainhan = $('#s_loainhan').val();
                      $body.addClass("loading");
                      _searchList.splice(0,_searchList.length);
                      _searchList.push(ngaybatdau);_searchList.push(ngayketthuc);_searchList.push(maonline);
                      _searchList.push(manhan);_searchList.push(bnbd);_searchList.push(bbd);_searchList.push(barcode);_searchList.push(loainhan);
                      $.ajax({
                        url : "vanthu",
                        type : "GET",
                        data : {
                                         "action" : "csgtcg",page: 1,ngaybatdau: ngaybatdau,ngayketthuc: ngayketthuc,
                                         maonline: maonline,manhan: manhan, bnbd: bnbd,bbd: bbd,barcode: barcode,loainhan : loainhan
                                 },
                         dataType : "json",
                         success: function (data) {
                             $body.removeClass("loading");
                          //   console.log(data);
                             $('#tb_vtcsgt tbody').html(data.data);
                             var totalPages = data.totalpage;
                            var currentPage = data.currentpage;
                            $pagination.twbsPagination('destroy');
                            $pagination.twbsPagination($.extend({}, defaultOpts, {
                                startPage: currentPage,
                                totalPages: totalPages,
                                onPageClick: function (event, page) {
                                  //  console.log('PAGE = '+page);
                                 //   console.log(event.type);
                                  $body.addClass("loading");
                                    $.ajax({
                                            url : "vanthu",
                                            type : "GET",
                                            data : {
                                                             "action" : "csgtcg",page: page,ngaybatdau: _searchList[0],ngayketthuc: _searchList[1],
                                         maonline: _searchList[2],manhan: _searchList[3], bnbd: _searchList[4],bbd: _searchList[5],barcode:_searchList[6],loainhan : _searchList[7]
                                                     },
                                             dataType : "json",
                                             success: function (data) {
                                                 $body.removeClass("loading");
                                           //      console.log(data);
                                                 $('#tb_vtcsgt tbody').html(data.data);
                                             }
                                       });
                                }
                            }));
                         }
                   });
                };
                loadCsgtCg();
                var selectCBDon = function(){
                    if ($(this).is(':checked')) {
                        _donSelect.push($(this).val());
                        _madonSelect.push($(this).parent().parent().find('td').eq(3).html());
                        $('#sohoso').val(_madonSelect);
                        $('#idhs').val(_donSelect);
                    }else{
                        for(var i=0;i < _donSelect.length;i++){
                            if($(this).val() == _donSelect[i]){
                                _donSelect.splice(i,1);
                                _madonSelect.splice(i,1);
                                $('#sohoso').val(_madonSelect);
                                $('#idhs').val(_donSelect);
                            }
                        }
                    }
                    console.log(_donSelect);
                };
                var getDiachi = function(){
                    var diachi = $('#vtgt_noinhan').val();
                    $.ajax({
                          url : "vanthu",
                          type : "GET",
                          data : {
                                           "action" : "getdiachicsgt",diachi: diachi
                                   },
                           dataType : "json",
                           success: function (data) {
                               console.log(data);
                               $('#tb_vtcsgt tbody').html(data.data);
                           }
                     });
                };
                var submitCSGT = function(){
                    $('#result_add').html('');
                        if($('#sdb').val().trim() == ''){
                            alert('Nhập số bưu điện');
                        }else{
                            if($('sohoso').val() == '' ){
                                alert('Nhập số hồ sơ');
                            }else{
                                if($("#vtgt_noinhan").val() ==''){
                                    alert('Nhập nơi nhận của csgt/sở/chi cục');
                                }else{
                                    if($("#csgt_diachi").val() ==''){
                                        alert('Nhập địa chỉ csgt/sở/chi cục');
                                    }else{
                                        var ngaygoi = $('#ngaygoi').val();
                                        var sobuudien = $('#sdb').val();
                                        var dcid = $("#csgt_dcid").val();
                                        var donid = $('#idhs').val();
                                        var ghichu = $('#ghichu').val();
                                        $('#ghichu').val("");
                                        $.ajax({
                                            url : "vanthu",
                                            type : "GET",
                                            data : {
                                                             "action" : "addvtcsgt",ngaygoi: ngaygoi, sobuudien: sobuudien,dcid : dcid,
                                                             donid : donid,ghichu: ghichu
                                                     },
                                             success: function (data) {
                                                 console.log(data);
                                                 $('#result_add').html(data);
                                                 $('#sdb').val("");$('sohoso').val("");$("#vtgt_noinhan").val("");
                                                 $("#csgt_diachi").val("");$('#ngaygoi').val("");$('#sdb').val("");
                                                 $("#csgt_dcid").val("");$('#idhs').val("");$('#ghichu').val("");
                                                 $('#sohoso').val("");$('#idhs').val("");
                                                 loadCsgtCg();
                                             }
                                       });
                                                
                                    }
                                }
                            }
                        }
                };
            
                
                
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
                  
                  $('#s_ngaybatdau,#s_ngayketthuc,#s_maonline,#s_manhan,#s_bnbd,#s_bbd,#s_barcode').on('keydown',function(e){
                     if(e.keyCode === 13) {
                         $('#btn_searchCSGT').click();
                     }
                  });
                  // add event to button
                  $('table#tb_vtcsgt').on('change',':checkbox',selectCBDon);
                $('#btn_submit').on('click',submitCSGT);
                $('#btn_searchCSGT').on('click',loadCsgtCg);
        </script>
    </body>
</html>
