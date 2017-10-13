<%-- 
    Document   : print_thongbaophi
    Created on : Dec 23, 2016, 9:48:21 PM
    Author     : ntdung
--%>

<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ttdk.bean.TrungTamInfor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ttdk.bean.Thuphi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="../js/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/thongbao.css">
        <link rel="stylesheet" type="text/css" href="../js/jquery-ui.css">
        <style>
            .qpSend a {
                background: url(../images/btmSend.jpg) no-repeat;
                width: 71px;
                height: 30px;
                display: block;
                line-height: 30px;
                text-decoration: none;
                text-align: center;
                color: #fff;
                display: block;
                float: left;
                margin-left: 10px;
            }
        </style>
        <script src="../js/jquery-ui.js"></script>
        <title>Thông báo phí</title>
    </head>
    <body >
        <%
            try{
                
            
            Thuphi tp = new Thuphi();
            String donids = (String)request.getSession().getAttribute("id");
            String[] donID = donids.split("-");
            int pagePrint = donID.length;
            String tbody_thongbaophi = "";
            ArrayList<String> bnbdArr = null;
            ArrayList<String> ttinfor =  null;
             Date date = new Date(); // your date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            String monthStr = ""+month;
            if(month <10){
                monthStr = "0"+month;
            }
             int day = cal.get(Calendar.DAY_OF_MONTH);
            String dayStr = ""+day;
              if(day <10){
                dayStr = "0"+day;
            }
             cal.add(Calendar.DATE, 20);
              int nyear = cal.get(Calendar.YEAR);
            int nmonth = cal.get(Calendar.MONTH);
            String nextMonth = ""+nmonth;
            if(nmonth <10){
                nextMonth = "0"+nmonth;
            }
             int nday = cal.get(Calendar.DAY_OF_MONTH)+1;
             String nextDay = ""+nday;
              if(nday <10){
                    nextDay = "0"+nday;
                }
                String ngaybp = (String)request.getSession().getAttribute("ngaybp");
                
                String ngayhh = "",taikhoan="",nguoilienhe="",nguoichiutn="",chucvu="",inforid="";
                if(ngaybp == null){
                    ArrayList<String> tttp = tp.getTTTP();
                    taikhoan = tttp.get(0);
                    nguoilienhe = tttp.get(1);
                    nguoichiutn = tttp.get(2);
                    chucvu = tttp.get(3);
                }else{
                //    String[] timebp = ngaybp.split("-");
                    inforid = request.getSession().getAttribute("inforid").toString();
                     ngayhh = (String)request.getSession().getAttribute("ngayttoan");
                    taikhoan = session.getAttribute("taikhoan").toString();
                    nguoilienhe = session.getAttribute("nguoilienhe").toString();
                    nguoichiutn  = session.getAttribute("nguoichiutn").toString();
                    chucvu = session.getAttribute("chucvu").toString();
                }
             String thangbp = ngaybp.split("-")[1];
                if(thangbp.length()>1){
                    thangbp = thangbp.substring(1);
                }
                String timebp = ngaybp.split("-")[2]+"-"+ngaybp.split("-")[1]+"-";
                int sobp = (tp.getTBPNumber(timebp)+1);
          //      String  sothongbaophi = thangbp+(tp.getTBPNumber(ngaybp)+1);
          
            if(pagePrint == 1){
                tbody_thongbaophi = tp.printThongbaoPhi(donids);
                bnbdArr  = new Thuphi().printGetBNBD(donids);
                ttinfor = new TrungTamInfor().getInfor();
                String infor_thongbaophi = sobp+"_"+ngaybp+"_"+ngayhh+"_"+inforid+"_"+donids;
            %>
            <input type="text" class="tbp_values" value="<%=infor_thongbaophi%>" hidden>
            <form name="thongbaophi" method="post" action="" target="iframe">
        <div class="qpSend" id="inthongbao">
            <a class="qpSend" style="margin-left:10px;" href="javascript:void(0)" onclick="saveThuPhi()">Lưu Lại</a>
            <a class="qpSend" style="margin-left:10px;" href="javascript:void(0)" onclick="troLai()">Trở Lại</a>
        </div>
         <div class="wrapper">
         <div class="header">
         	<div class="top_header">
                                    <div class="top_header_left">
                                        <div>CỤC ĐĂNG KÝ QUỐC GIA</div>
                                            <div>GIAO DỊCH BẢO ĐẢM</div>
                                            <div><strong>TRUNG TÂM ĐĂNG KÝ</strong></div>
                                            <div><strong>GIAO DỊCH TÀI SẢN TẠI ĐÀ NẴNG</strong></div>
                                            <div><strong>Số:<span id="sothongbao0"><%=(thangbp +sobp)%></span>
                                                    /TT3</strong></div>
                                    </div><br>
                                    <div class="top_header_right">
                                            <div style="word-spacing: 1px;"><strong>CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</strong></div>
                                            <div><strong> Độc lập - Tự do - Hạnh phúc </strong></div>
                                            <div style="margin-top:5px;"><strong><i>Đà Nẵng, Ngày <%=ngaybp.split("-")[0] %> tháng <%=ngaybp.split("-")[1] %> năm <%=ngaybp.split("-")[2]%></i></strong></div>
                                    </div>
                                    <div class="content_header">
                                            <div style="font-size:16px;word-spacing: 1px;margin-left:15px;"><strong>THÔNG BÁO THANH TOÁN LỆ PHÍ ĐĂNG KÝ,PHÍ CCTT VỀ GIAO DỊCH BẢO ĐẢM</strong></div>
                                    </div>
                                    <div class="bottom_header">
                                        <div><strong>Kính gửi : <%=bnbdArr.get(0) %></strong></div>
                                            <div style="margin-top:2px;"><strong>Địa chỉ : <span id="dc0"><%=bnbdArr.get(1) %></span></strong></div>
                                            <div style="margin-top:2px;height:20;">
                                                    <div style="width:300;float:left;height:auto;">
                                                            <strong>Mã khách hàng: </strong>
                                                            <span id="tk0"><%=bnbdArr.get(2) %></span>
                                                         <!--   <script language="javascript">GetMaKhachHang("Ngân Hàng Tmcp Công Thương Việt Nam Chi Nhánh Quảng Trị",0)</script> -->
                                                    </div>
                                                    <!--<div style="width:300;float:left;height:auto;">
                                                            <strong>Điện thoại :</strong>
                                                    </div>
                                                <div style="width:300;float:right;height:auto;">
                                                            <strong>Fax :</strong>
                                                    </div>-->
                                            </div><br>
                                            <div style="margin-top:2px;height:auto;word-spacing: 1px;font-size:12px;text-align:center;">
                                                <strong><i>Diễn giải chi tiết đơn ngân hàng chưa thanh toán đến thời điểm cuối ngày <%=ngaybp.split("-")[0] %>-<%=ngaybp.split("-")[1] %>-<%=ngaybp.split("-")[2] %></i></strong>
                                            </div>
                                    </div>
                            </div>
         </div>
         <div style="display:table; border: solid 0px #000000">
                                <table style="width:100%;border-color:#000000;border-collapse:collapse;border-style:solid;margin-top:40px;" border="1" cellpadding="0" cellspacing="0">
                                    <thead>
                                        <tr style="height:30px;">
                                                <th style="width:25px;">Stt</th>
                                            <th style="width:85px;">Ngày tháng</th>
                                            <th style="width:85px;">Số đơn</th>
                                            <th style="">Bên bảo đảm</th>
                                            <th style="width:70px;">Số tiền</th>
                                        </tr>
                                    </thead>
                                        <tbody>
                                            <%= tbody_thongbaophi %>
                                        </tbody>
                                </table>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                                                   <!-- <input type="hidden" name="data[Content][ngayketthuc_0]" id="ngayketthuc_0" value="2017-01-12"> -->
                                       <strong>*Đề nghị Quý ngân hàng thanh toán trước ngày : <%=ngayhh.split("-")[0] %>/<%=ngayhh.split("-")[1] %>/<%=ngayhh.split("-")[2] %></strong>
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    Quá thời hạn trên, Trung Tâm sẽ từ chối đơn yêu cầu đăng ký GDBD của Quý ngân hàng theo quy định.
                             </div>

                             <div style="margin-top:2px;text-align:left;font-size:13px;font-weight:bold;">
                                    Thanh toán bằng chuyển khoản qua kho bạc: <br>
                                    &nbsp;&nbsp;&nbsp; - Tên đơn vị nhận tiền; <%=ttinfor.get(0) %>.<br>

                                    &nbsp;&nbsp;&nbsp; - Tài khoản: <%=taikhoan %> tại kho bạc nhà nước Đà Nẵng  
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                 hoặc nộp tiền mặt tại trung tâm: <%=ttinfor.get(1) %>.
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    - Sau khi nhận được tiền phí, lệ phí, Trung tâm sẽ gửi Biên lai thu phí, lệ phí tới Quý ngân hàng.
                             </div>
                                                     <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    <strong>- Khi chuyển khoản, đề nghị Quý Ngân Hàng</strong>
                                    THANH TOÁN 01 LẦN TOÀN BỘ SỐ TIỀN PHÍ THEO THÔNG BÁO
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    <strong>Và ghi tại phần Nội dung:Thanh toán theo THÔNG BÁO SỐ.....</strong>
                                    (nằm ở góc trái )<strong> HOẶC SỐ ĐƠN</strong> phải nộp phí
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    Và ở dòng <strong>NGƯỜI TRẢ TIỀN:</strong>
                                    Ghi rõ tên Ngân hàng xác định đúng của Ngân hàng hay phòng giao dịch nào.
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    *Nếu có vướng mắc, xin liên hệ 

                                                                     - ĐT:(0236)3933111 - bấm 104 hoặc 0236.3503880 
                             </div>
                     </div>
             <div class="footer">

                                                    <div style="margin-top:10px; float:right; margin-right:16px;text-align:right;font-size:16px; width:100%">
                                <%
                                        if(chucvu.equals("GIÁM ĐỐC")){
                                            
                                            %>
                                            <strong>GIÁM ĐỐC</strong>
                                            </div>
                                            <%
                                        }else{
                                    %>
                                    <strong>KT GIÁM ĐỐC</strong>
                                    </div>
                                    <div style="margin-top:0px; float:right; margin-right:10px;text-align:right;font-size:16px;  width:100%">
                                            <strong><%= chucvu%></strong>
                                    </div>
                            
                                    <%
                                        }
                                        %>
                            

                                                    <div style="margin-top:50px; float:right; margin-right:0px;text-align:right;font-size:16px;margin-bottom:5px;width:100%;">
                                                        <strong><%=nguoichiutn %></strong>
                                                </div>

                     </div>
          </div>
          <%
                   }else{
                            for(int i=0;i<pagePrint;i++){
                                tbody_thongbaophi = new Thuphi().printThongbaoPhi(donID[i]);
                                bnbdArr  = new Thuphi().printGetBNBD(donID[i]);
                                ttinfor = new TrungTamInfor().getInfor();
                                String infor_thongbaophi = sobp+"_"+ngaybp+"_"+ngayhh+"_"+inforid+"_"+donID[i];
          %>
                                <input type="text" class="tbp_values" value="<%=infor_thongbaophi%>" hidden>
          <%
                                if(i !=0){
                                    
                                    
                                    %>
                                         <div style="page-break-before: always;"></div>                                       
                                     <%
                                }

                             if(i==0){
                        %>
                                <div class="qpSend" id="inthongbao">
                                    <a class="qpSend" style="margin-left:10px;" href="javascript:void(0)" onclick="saveThuPhi()">Lưu Lại</a>
                                    <a class="qpSend" style="margin-left:10px;" href="javascript:void(0)" onclick="troLai()">Trở Lại</a>
                                </div>       
                        <%
                             }
                        %>
                        <div class="wrapper">
                                 <div class="header">
                                        <div class="top_header">
                                    <div class="top_header_left">
                                        <div>CỤC ĐĂNG KÝ QUỐC GIA</div>
                                            <div>GIAO DỊCH BẢO ĐẢM</div>
                                            <div><strong>TRUNG TÂM ĐĂNG KÝ</strong></div>
                                            <div><strong>GIAO DỊCH TÀI SẢN TẠI ĐÀ NẴNG</strong></div>
                                            <div><strong>Số:<span id="sothongbao0"><%=(thangbp +sobp) %></span>/TT3</strong></div>
                                    </div>
                                    <div class="top_header_right">
                                            <div style="word-spacing: 1px;"><strong>CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</strong></div>
                                            <div><strong> Độc lập - Tự do - Hạnh phúc </strong></div>
                                            <div style="margin-top:5px;"><strong><i>Đà Nẵng, Ngày <%=dayStr %> tháng <%=monthStr %> năm <%=year%></i></strong></div>
                                    </div>
                                    <div class="content_header">
                                            <div style="font-size:16px;word-spacing: 1px;margin-left:15px;"><strong>THÔNG BÁO THANH TOÁN LỆ PHÍ ĐĂNG KÝ,PHÍ CCTT VỀ GIAO DỊCH BẢO ĐẢM</strong></div>
                                    </div>
                                    <div class="bottom_header">
                                        <div><strong>Kính gửi : <%=bnbdArr.get(0) %></strong></div>
                                            <div style="margin-top:2px;"><strong>Địa chỉ : <span id="dc0"><%=bnbdArr.get(1) %></span></strong></div>
                                            <div style="margin-top:2px;height:20;">
                                                    <div style="width:300;float:left;height:auto;">
                                                            <strong>Mã khách hàng: </strong>
                                                            <span id="tk0"><%=bnbdArr.get(2) %></span>
                                                         <!--   <script language="javascript">GetMaKhachHang("Ngân Hàng Tmcp Công Thương Việt Nam Chi Nhánh Quảng Trị",0)</script> -->
                                                    </div>
                                                    <!--<div style="width:300;float:left;height:auto;">
                                                            <strong>Điện thoại :</strong>
                                                    </div>
                                                <div style="width:300;float:right;height:auto;">
                                                            <strong>Fax :</strong>
                                                    </div>-->
                                            </div><br>
                                            <div style="margin-top:2px;height:auto;word-spacing: 1px;font-size:12px;text-align:center;">
                                                     <strong><i>Diễn giải chi tiết đơn ngân hàng chưa thanh toán đến thời điểm cuối ngày <%=ngaybp.split("-")[0] %>-<%=ngaybp.split("-")[1] %>-<%=ngaybp.split("-")[2] %></i></strong>
                                            </div>
                                    </div>
                            </div>
         </div>
         <div style="display:table; border: solid 0px #000000">
                                <table style="width:100%;border-color:#000000;border-collapse:collapse;border-style:solid;margin-top:40px;" border="1" cellpadding="0" cellspacing="0">
                                    <thead>
                                        <tr style="height:30px;">
                                                <th style="width:25px;">Stt</th>
                                            <th style="width:85px;">Ngày tháng</th>
                                            <th style="width:85px;">Số đơn</th>
                                            <th style="">Bên bảo đảm</th>
                                            <th style="width:70px;">Số tiền</th>
                                        </tr>
                                    </thead>
                                        <tbody>
                                            <%= tbody_thongbaophi %>
                                        </tbody>
                                </table>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    <strong>*Đề nghị Quý ngân hàng thanh toán trước ngày : <%=ngayhh.split("-")[0] %>/<%=ngayhh.split("-")[1] %>/<%=ngayhh.split("-")[2] %></strong>
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    Quá thời hạn trên, Trung Tâm sẽ từ chối đơn yêu cầu đăng ký GDBD của Quý ngân hàng theo quy định.
                             </div>

                             <div style="margin-top:2px;text-align:left;font-size:13px;font-weight:bold;">
                                    Thanh toán bằng chuyển khoản qua kho bạc: <br>
                                    &nbsp;&nbsp;&nbsp; - Tên đơn vị nhận tiền; <%=ttinfor.get(0) %>.<br>

                                    &nbsp;&nbsp;&nbsp; - Tài khoản: <%=taikhoan %> tại kho bạc nhà nước Đà Nẵng  
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                 hoặc nộp tiền mặt tại trung tâm: <%=ttinfor.get(1) %>.
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    - Sau khi nhận được tiền phí, lệ phí, Trung tâm sẽ gửi Biên lai thu phí, lệ phí tới Quý ngân hàng.
                             </div>
                                                     <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    <strong>- Khi chuyển khoản, đề nghị Quý Ngân Hàng</strong>
                                    THANH TOÁN 01 LẦN TOÀN BỘ SỐ TIỀN PHÍ THEO THÔNG BÁO
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    <strong>Và ghi tại phần Nội dung:Thanh toán theo THÔNG BÁO SỐ.....</strong>
                                    (nằm ở góc trái )<strong> HOẶC SỐ ĐƠN</strong> phải nộp phí
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    Và ở dòng <strong>NGƯỜI TRẢ TIỀN:</strong>
                                    Ghi rõ tên Ngân hàng xác định đúng của Ngân hàng hay phòng giao dịch nào.
                             </div>
                             <div style="margin-top:2px;text-align:left;font-size:13px;">
                                    *Nếu có vướng mắc, xin liên hệ 

                                                                     - ĐT:(0236)3933111 - bấm 104 hoặc 0236.3503880 
                             </div>
                     </div>
             <div class="footer">

                                                    <div style="margin-top:10px; float:right; margin-right:16px;text-align:right;font-size:16px; width:100%">
                                    <strong>KT GIÁM ĐỐC</strong>
                            </div>
                            <div style="margin-top:0px; float:right; margin-right:10px;text-align:right;font-size:16px;  width:100%">
                                <strong><%= chucvu%></strong>
                            </div>

                                                    <div style="margin-top:50px; float:right; margin-right:0px;text-align:right;font-size:16px;margin-bottom:5px;width:100%;">
                                                        <strong><%=nguoichiutn %></strong>
                            </div>

                     </div>
          </div>
                                         <%
    
    
                                             sobp = sobp +1;
                            }
                         }
              }catch(Exception ex){
              ex.printStackTrace();
              response.sendRedirect("../404.jsp");
            }
            %>
        
     <!--       <input type="hidden" name="data[Content][ds_post_0]" id="ds_post_0" value="450160 | 453985 | 464049 | 464450 | 464488 | 469591 | 476767 | 476768 | 485215 | 485341 | 492096 | 493071 | 495299 | 495393 | 505863 | 513147 | 517268 | 517269 | 520344 | 520513 | 524882 | 534328 | 535324 | 535695 | 536381 | 536880 | 537022 | 537208">
            <input type="hidden" name="data[Content][tenbenbaodam_0]" id="tenbenbaodam_0" value="Ngân Hàng Tmcp Công Thương Việt Nam Chi Nhánh Quảng Trị">
            <input type="hidden" name="data[Content][dcbenbaodam_0]" id="dcbenbaodam_0" value="Số 236 Hùng Vương, TP Đông Hà tỉnh Quảng Trị">
            <input type="hidden" name="data[Content][sothongbao_0]" id="sothongbao_0" value="1">
            <input type="hidden" name="data[Content][bennhanbaodam_id_0]" id="bennhanbaodam_id_0" value="59">	

            <input type="hidden" name="data[Content][ngaythongbao_0]" id="ngaythongbao_0" value="2016-12-23">
            <input type="hidden" name="data[Content][thoihan_tb_0]" id="thoihan_tb_0" value="1970-01-01">	
            <input type="hidden" name="data[Content][lienhe_tb_0]" id="lienhe_tb_0" value="">	
            <input type="hidden" name="data[Content][trachnhiem_tb_0]" id="trachnhiem_tb_0" value="">	
            <input type="hidden" name="data[Content][taikhoan_0]" id="taikhoan_0" value="3511.1043491">	
            <input type="hidden" name="data[Content][chucvu_0]" id="chucvu_0" value="">		 -->	

                            </form>
          <!--  <iframe name="iframe" id="iframe" style="width:0px;height:0px;border:0px;" src="./Quản Lý Hồ Sơ_files/saved_resource.html"></iframe> -->
          <div id="dialog-confirm" title="Lưu thông báo phí">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Bạn có muốn lưu PDF sau khi lưu thông báo phí ?</p>
          </div>
          <script>
              $( "#dialog-confirm" ).dialog({
                    resizable: false,
                    height: "auto",
                    width: 400,
                    modal: true,
                    autoOpen: false,
                    buttons: {
                      "Lưu PDF": function() {
                          var inforList = [];
                        $('.tbp_values').each(function(i,el){
                             inforList.push($(this).val());
                             console.log($(this).val());
                        });
                        $.ajax({
                                type: "GET",
                                url: "../thuphiservlet",
                                data: {action: "luuthongbaophi",inforlist :  inforList },
                                success: function (data) {
                                    if(data === 'OK'){

                                        window.location= "../thuphiservlet?action=downloadtbp";
                                        $('#inthongbao').hide();
                                    }else if(data === 'FAIL'){

                                    }
                                }
                            });
                        $( this ).dialog( "close" );
                      },
                      "Không Lưu": function() {
                        $( this ).dialog( "close" );
                      }
                    }
                  });
              var saveThuPhi = function(){
                  
                  $( "#dialog-confirm" ).dialog( "open" );
                  
                    
              };
              
              var troLai = function(){
                  window.location.href = '../thuphi.jsp?page=chuathuphi';
              };
          </script>
</body>
</html>
