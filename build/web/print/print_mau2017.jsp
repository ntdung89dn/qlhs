<%-- 
    Document   : printonline
    Created on : Jun 6, 2017, 7:58:48 AM
    Author     : ntdung
--%>

<%@page import="com.ttdk.bean.ChiTietDon"%>
<%@page import="com.ttdk.bean.LoadDonMongoDB"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ttdk.bean.InforPrint"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <meta http-equiv="X-UA-Compatible" content="IE=edge"> 
        <script src="../js/jquery.min.js"></script>
        <script src="../js/jquery-barcode.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="../css/bootstrap.min.css">
        <style type="text/css">        
            html, body {            
                margin: 0;          
                padding: 0;      
                font-family: "Times New Roman";        
                font-size: 13pt;        
                background-color: #eee;        
            }
            * {          
                box-sizing: border-box;       
                -moz-box-sizing: border-box;     
            } 
            .page {          
                margin: 1cm auto;          
                background: #fff;         
                box-shadow: 0 4px 5px rgba(75, 75, 75, 0.2);         
                outline: 0;       
            } 
            div.page-break {            page-break-after: always;        }        
            h1 {            page-break-before: always;        }       
            h1, h2, h3, h4, h5, h6 {            page-break-after: avoid;        }       
            p {            margin: 0;            text-align: justify;        }        
            a {            text-decoration: none;            color: black;        }       
            table {            page-break-inside: avoid;        }       
            @page {      
                orphans: 4;        
                widows: 2;        }        
            @media print { 
                html, body {  
                    background-color: #fff;           
                }          
              .page {                
                  width: initial !important;               
                  min-height: initial !important;              
                  margin: 0 !important;                
                  padding: 0 !important;               
                  border: initial !important;                
                  border-radius: initial !important;                
                  background: initial !important;                
                  box-shadow: initial !important;                
              //    page-break-after: always;            
              }        
            }        
            /* CSS for A4 page*/        
            .page {            
                width: 21cm;            
                min-height: 29.7cm;            
                padding-left: 2cm;           
                padding-top: 1cm;            
                padding-right: 2cm;            
                padding-bottom: 2cm;        
            }        
            @page {            
                size: A4 portrait;           
                margin-left: 2cm;            
                margin-top: 1cm;            
                margin-right: 2cm;            
                margin-bottom: 2cm;        
            }        
            table.table-border {            
                border-collapse: collapse;       
            }       
            table.table-border td, table th { 
                border: 1pt solid windowtext;       
            }        
            table.table-border td {            
                padding: 0.1cm;        
            table.table-border td, table th { 
            }        
            table.table-border td p 
            {            
                text-align: left;        
            }        
            ul.branch-info { 
                list-style: none;            
                margin-top: 0;        
            }        
            ul.branch-info li:before{
                content: "- ";        
            }    
         /**   #bc_print {
                -webkit-transform: rotate(270deg);
                -moz-transform: rotate(270deg);
                -o-transform: rotate(270deg);
                -ms-transform: rotate(270deg);
                transform: rotate(270deg);
            }**/
           #print_bbd_maso{
                border:none;
                outline: none;
                -webkit-appearance: none;
                 -moz-appearance: none;
                 text-indent: 1px;
                 text-overflow: '';
            }
            #maloainhan_top,#bc_print{
                display: inline;
            }
        </style>
        <title>Giấy Chứng Nhận</title>
    </head>
    <body class="document">﻿<div class="page"> 
        <%
                int id = Integer.parseInt(request.getParameter("id"));
                InforPrint ip = new InforPrint();
                ArrayList<String> data = ip.loadPrintInfor(id);
                String giohl = "0";
                String phuthl = "0";
                if(!data.isEmpty()){
                     giohl = data.get(0);
                     phuthl = data.get(1);
                }
                if(giohl.length() <2){
                    giohl = "0"+giohl;
                }
                if(phuthl.length() <2){
                    phuthl = "0"+phuthl;
                }
                String thoigianhl = data.get(2);
                String[] thoigian = thoigianhl.split("-");
                String namhl = thoigian[2];
                String ngayhl = thoigian[0];
                String thanghl = thoigian[1];
                String dononline  = data.get(3);
                String mapin = data.get(4);
                if(mapin.equals("")){
                    mapin = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                }
                String[] bnbdList = null;
                if(!data.get(5).equals("")){
                   bnbdList = data.get(5).split("&");
                }else{
                    bnbdList = new String[]{""};
                }
                String[] benbaodamList =null;
                if(!data.get(6).equals("")){
                   benbaodamList = data.get(6).split("&");
                }else{
                    benbaodamList = new String[]{""};
                }
                    //    = data.get(6).split("&");
                String maloainhan = data.get(7);
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                //getTime() returns the current date in default time zone
                Date date = calendar.getTime();
                String day = String.valueOf(calendar.get(Calendar.DATE));
                if(day.length() <2){
                    day = "0"+day;
                }
                //Note: +1 the month for current month
                String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
                if(month.length() <2){
                    month = "0"+month;
                }
                int year = calendar.get(Calendar.YEAR);
            %>
            
            <!-- style="margin-top: -70px;margin-left: -60px;" -->
                <table width="100%" style="margin-top:0px;margin-left: 0px;">
                    <tbody>
                        <tr>
                            <td><div id="bc_print" style="float: left; font-size: 0px; background-color: #FFFFFF;"></div></td>
                            <td align="right"><span style="font-weight: bold;font-size: 13px;"><%= maloainhan %></span></td>
                        </tr>
                    </tbody>
                </table>
    <table width="100%">
        <tbody>
            <tr>
                <td style="width: 25%; text-align: center"><img src="./image/logo.PNG" alt="logo" style="width:115px; height: 115px; "></td>
                <td style="text-align: center; width: 70%">
                    <p style="text-transform: uppercase; text-align: center; font-weight: bold;">Cộng hòa xã hội chủ nghĩa Việt Nam</p>
                    <p style="text-align: center; font-weight: bold;">Độc lập - Tự do - Hạnh phúc</p>
                    <hr style="width: 35%; margin: 0 auto; color: black; display: block;">
                    <p style="margin-top: 0.5cm"></p>
                    <p style="font-size: 12pt; font-style: italic; text-align: center"> Đà Nẵng, ngày <%= day %> tháng <%= month %> năm <%= year %></p>
                </td>
            </tr>
    </tbody>
    </table>
    <p style="text-align: center; font-weight: bold; font-size: 14pt; text-transform: uppercase;">
        Giấy chứng nhận biện pháp giao dịch bảo đảm,        <br>
        hợp đồng, thông báo việc kê biên tài sản thi hành án   
    </p>
    <div style="margin: 0; padding: 0; line-height: 16pt;">
                <p style="margin-top: 0.5cm"></p>
        <p style="margin-top: 0.5cm"></p>
                    <p style="text-align: center; font-weight: bold; font-size: 14pt; text-transform: uppercase;">
                        trung tâm đăng ký giao dịch, tài sản tại thành phố Đà nẵng Chứng nhận</p>
                <br>
        <p style="text-indent: 1cm;">
            <span style="font-weight: bold;">1. </span>
            Nội dung đăng ký, thông báo việc kê biên của đơn yêu cầu đăng ký số <%= dononline %> đã được cập nhật vào Cơ sở dữ liệu về biện pháp bảo đảm;             có hiệu lực đăng ký từ thời điểm &nbsp;	&nbsp;
            <input value="<%= giohl %>" style="border: 0px; max-width: 20px;" maxlength="2" id="print_giohl"> giờ <input value="<%= phuthl %>" style="border: 0px; max-width: 20px;" maxlength="2" id="print_phuthl"> phút, ngày <input value="<%= ngayhl %>" style="border: 0px; max-width: 20px;" maxlength="2" id="print_ngayhl">
            tháng <input value="<%= thanghl %>" style="border: 0px; max-width: 20px;" maxlength="2" id="print_thanghl"> năm <input value="<%= namhl %>" style="border: 0px; max-width: 40px;" maxlength="4" id="print_namhl">            và được Trung tâm Đăng ký giao dịch, tài sản gửi kèm theo Giấy chứng nhận.        </p>
        <p style="text-indent: 1cm;">
            <span style="font-weight: bold;">2. </span>
            Bên nhận bảo đảm (người có trách nhiệm thông báo việc kê biên):                    </p>
        <%
            int stt = 1;
            for(String bnbd : bnbdList){
                String[] bnbdInfor = null;
                if(!bnbd.equals("")){
                    bnbdInfor = bnbd.split("_");
                }else{
                    bnbdInfor = new String[]{"",""};
                }
                
            %>
        <p style="text-indent: 1cm; font-weight: bold;" class="click_edit" id="bnbd<%=stt%>">- <%= bnbdInfor[0] %></p>
        <p style="text-indent: 1cm;" class="click_edit" id="bnbddc<%=stt%>">-Địa chỉ:  <%= bnbdInfor[1] %></p>
        <% stt++; } %>
        <p>
            
        </p>
        <p style="text-indent: 1cm;">
            <span style="font-weight: bold;">3. </span>
            Bên bảo đảm:                     </p><!-- chus ys ma so doanh nghiep-->
        <%
            stt = 1;
            for(String bbd : benbaodamList){
                String[] bbdInfor = bbd.split("_");
                String cmnd = "";
                if(bbdInfor.length >1){
                    cmnd = bbdInfor[1];
                }
                boolean isCTY = false;
                if(bbdInfor[0].toLowerCase().contains("cty") || bbdInfor[0].toLowerCase().contains("công ty") || bbdInfor[0].toLowerCase().contains("tnhh")
                        || bbdInfor[0].toLowerCase().contains("mtv")){
                    isCTY = true;
                }
            %>
        <p style="text-indent: 1cm; font-weight: bold;" class="click_edit" id="bbd<%=stt%>"><%= bbdInfor[0] %></p>
        <p style="text-indent: 1cm;">  - <select id="print_bbd_maso" style="border: none;outline: none; -webkit-appearance: none; -moz-appearance: none;text-indent: 1px;text-overflow: '';">
                <option <% if(!isCTY){ %> selected<%   } %>> Số CMND/Căn cước công dân: </option>
                <option <% if(isCTY){ %> selected<%   } %>>Mã Số Thuế: </option>
              </select> <strong class="click_edit" id="cmnd<%=stt%>"><%= cmnd %></strong>
            <% stt++; } %>
        </p>
            <p></p>
        <p style="text-indent: 1cm;">
            <span style="font-weight: bold;">4. </span>
            Mã cá nhân: <span class="click_edit" id="mapin"><%= mapin %> </span>       </p><p style="text-indent: 1cm; font-weight: bold; font-style: italic;">(Người yêu cầu đăng ký hoàn toàn chịu trách nhiệm về việc bảo mật thông tin liên quan đến Mã cá nhân do cơ quan đăng ký cấp)</p>
        <p></p>
        <p style="margin-top: 0.5cm"></p>
        <p style="margin-top: 0.5cm"></p>
        <table style="border: none;" width="100%">
            <tbody>
            <tr style="border: none;">
                <td width="50%" style="border: none;">
                       
                </td>
                <td width="50%" style="border: none; vertical-align: top;">
                    <p style="text-align: center; vertical-align: top; font-size: 14pt">
                        <strong style="text-transform: uppercase;">Giám đốc</strong>
                    </p>
                    <p style="font-style: italic; text-align: center; margin-top: 0">(Ký tên, đóng dấu)</p>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
        </div>﻿
        <%
            if(request.getParameter("type") !=null){
                ChiTietDon _chiTietDon = new LoadDonMongoDB().getChiTietDon(String.valueOf(id));
            %>
</div>﻿<div class="page">
        <p style="text-transform: uppercase; font-size: 14pt; font-weight: bold;">Chi tiết đơn đăng ký</p>

        
                                                Số đơn đăng ký lần đầu: <%=_chiTietDon.getMalandau() %>                    <br>
                    Thời điểm đăng ký: <%=_chiTietDon.getThoigianlandau()%>               <br>
                                    Loại hình giao dịch:                    <%=_chiTietDon.getLoaigiaodich() %>                 <br>
                                                                        <br>
                            <p style="text-transform: uppercase; font-size: 14pt; font-weight: bold;"><%=_chiTietDon.getLoaidon() %></p>
                                        Số hợp đồng: <%=_chiTietDon.getSohopdong()%>                        <br>
                                        <%
                                            if(_chiTietDon.getNgayhopdong() != null && !_chiTietDon.getNgayhopdong().contains("")){
                                                
                                            %>
                            Ngày ký hợp đồng : <%=_chiTietDon.getNgayhopdong()%> 
                            <% 
                                            }
                                %><br>
            <table cellpadding="3" cellspacing="0" width="100%" class="data-table table-border">
                <tbody><tr>
                                            <th><span style="text-align: center; vertical-align: middle;">Số đơn đăng ký </span></th>
                                        <th><span style="text-align: center; vertical-align: middle;">Thời điểm đăng ký</span></th>
                </tr>
                <tr>
                    <td><%=dononline %></td>
                    <td><%=_chiTietDon.getThoigiandk() %></td>
                </tr>
            </tbody></table>
                                                                <br>
                    <p style="font-weight: bold;">Bên bảo đảm </p>
                    <table class="data-table table-border" cellpadding="3" cellspacing="0" width="100%">
                        <tbody><tr>
                            <th style="width: 25%; text-align: center; vertical-align: middle; "><span>Loại chủ thể</span></th>
                            <th style="width: 20%; text-align: center; vertical-align: middle; "><span>Số giấy tờ chứng minh tư cách pháp lý</span></th>
                            <th style="width: 20%; text-align: center; vertical-align: middle; "><span>Tên</span></th>
                            <th style="width: 35%; text-align: center; vertical-align: middle; "><span>Địa chỉ</span></th>
                        </tr>
                        <%
                            ArrayList<ArrayList<String>> bbdList = _chiTietDon.getBenbaodam();
                            for(ArrayList<String> bbd : bbdList){
                            %>
                                <tr>
                                    <td><%=bbd.get(0) %></td>
                                    <td><%=bbd.get(1) %></td>
                                    <td><%=bbd.get(2) %></td>
                                    <td><%=bbd.get(3) %></td>
                                </tr>
                        <%
                            }
                            %>
                                                    </tbody></table>
                    <br>
                                                                    <p style="font-weight: bold;">Bên nhận bảo đảm </p>
                    <table class="data-table table-border" cellpadding="3" cellspacing="0" width="100%">
                                                            <tbody><tr>
                                        <th style="width: 30%; text-align: center; vertical-align: middle; "><span>Tên</span></th>
                                        <th style="width: 70%; text-align: center; vertical-align: middle; "><span>Địa chỉ</span></th>
                                    </tr>
                                    <%
                                        for(String bnbd : bnbdList){
                                            String[] bnbdInfor = bnbd.split("_");
                                    %>
                                     <tr>
                                        <td><%= bnbdInfor[0] %></td>
                                        <td><%= bnbdInfor[1] %>
                                        </td>
                                    </tr>
                                <%
                                    }
                                    %>
                                                    </tbody></table>
                    <br>
                    <% 
                        if(!_chiTietDon.getMotats().equals("") && _chiTietDon.getMotats() != null){
                        %>
                                                    <p style="font-weight: bold;">Mô tả tài sản</p>
                    <%=_chiTietDon.getMotats()%>
                    <% } %>
                    <br><br>
                    <% if(!_chiTietDon.getSokhungList().isEmpty()){
                        
                        %>
                    <p style="font-weight: bold;">Số khung</p>
                    <table class="data-table table-border" cellpadding="3" cellspacing="0" width="100%">
                        <tbody>
                        <tr class="first last">
                            <th style="text-align: center  !important;">
                                <span style="text-align: center  !important;">Số khung</span>
                            </th>
                            <th style="text-align: center  !important;">
                                <span style="text-align: center  !important;">Số máy</span>
                            </th>
                            <th style="text-align: center  !important;">
                                <span style="text-align: center  !important; ">Biển số</span>
                            </th>
                        </tr>
                        </tbody>
                        <tbody>
                            <%
                                    ArrayList<ArrayList<String>> _inforpt = _chiTietDon.getSokhungList();
                                    for(ArrayList<String> phuongtien : _inforpt){
                                        String sokhung = phuongtien.get(0);
                                        String somay = "";
                                        if(!phuongtien.get(1).equals("-")){
                                            somay  = phuongtien.get(1);
                                        }
                                        String bienso = "";
                                        if(!phuongtien.get(2).equals("-")){
                                            bienso = phuongtien.get(2);
                                        }
                                %>
                            <tr class="vin_number_csv">
                                <td><%=sokhung %></td>
                                <td><%=somay %></td>
                                <td><%=bienso %></td>
                            </tr>
                        </tbody>
                    </table>
                    <%
                                    }
                                }
                        %>
                            </div>
            <%
                }
                %>
                <!-- Modal -->
                <div id="editModal" class="modal fade" role="dialog">
                  <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h2>Cập nhật dữ liệu</h2>
                      </div>
                      <div class="modal-body">
                          <input type="text" class="form-control" id="input_value" disabled> <input id="input_id" type="text" hidden><br>
                          <input type="text" class="form-control" id="input_update" placeholder="Giá trị cập nhật">
                      </div>
                      <div class="modal-footer">
                          <button type="button" class="btn btn-default"  id="btn_editPrint">Lưu Lại</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                      </div>
                    </div>

                  </div>
                </div>
 <script>
                $.urlParam = function(name){
                    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
                    if (results===null){
                       return null;
                    }
                    else{
                       return results[1] || 0;
                    }
                };
                var loadBarcode = function(){
                    var id = $.urlParam('id'); 
                    $("#bc_print").barcode(id, "code128",{barWidth:2, barHeight:30});
                };
                var upateGioHL = function(){
                    var giohl = $('#print_giohl').val();
                    var id = $.urlParam('id'); 
                    $.ajax({
                        type: "GET",
                        url:"../LoadDonAjax",
                        data:{"action":"updategiohl",giohl: giohl,donid: id},
                        success: function (data) {
                        }
                    }); 
                };
                
                var updatePhutHL = function(){
                    var phuthl = $('#print_phuthl').val();
                    var id = $.urlParam('id'); 
                    $.ajax({
                        type: "GET",
                        url:"../LoadDonAjax",
                        data:{"action":"updatephuthl",phuthl: phuthl,donid: id},
                        success: function (data) {
                        }
                    }); 
                };
                
                $('.click_edit').on('dblclick',function(){
                  // alert("X") ;
                  var id = $(this).prop('id');
                  var typeid = "";
                  var value = "";
                  if(id.substring(0,4) === 'bnbd'){
                      if(id.substring(0,6) === 'bnbddc'){
                            value = $(this).html().substr(11);
                        }else{
                            value = $(this).html().substr(2);
                        }
                  }else if(id.substring(0,3) === 'bbd'){
                       value = $(this).html();
                  }else if(id.substring(0,4) === 'cmnd'){
                       value = $(this).html();
                  }else if(id.substring(0,5) === 'mapin'){
                       value = $(this).html();
                  }
                  $('#input_id').val(id);
                  $('#input_value').val(value);
                  $('#editModal').modal('show');
                });
                $('#btn_editPrint').on('click',function(){
                    var oldvalue = $('#input_value').val();
                    var id = $('#input_id').val();
                    var donid = $.urlParam('id'); 
                    var updatevalue = $('#input_update').val();
                    $.ajax({
                        type: "GET",
                        url:"../LoadDonAjax",
                        data:{"action":"updateedit",oldvalue: oldvalue,typeid: id,donid : donid,updatevalue : updatevalue},
                        success: function (data) {
                            if(data === 'OK'){
                                alert("CẬP NHẬT THÀNH CÔNG");
                                location.reload();
                            }else{
                                alert("CẬP NHẬT KHÔNG THÀNH CÔNG");
                            }
                            
                        }
                    }); 
                });
                $('#print_giohl').on('keyup',upateGioHL);
                $('#print_phuthl').on('keyup',updatePhutHL);
                loadBarcode();
            </script>
        </body>           
</html>
