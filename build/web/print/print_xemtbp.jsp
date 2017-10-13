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
        <title>Thông báo phí</title>
    </head>
    <body >
        <%
            try{
                
            
            Thuphi tp = new Thuphi();
            String tbp_ids = (String)request.getSession().getAttribute("id");
            System.out.println(tbp_ids);
            String[] tbpList = tbp_ids.split("_");
            
            ArrayList<String> ttinfor = new TrungTamInfor().getInfor();
            for(int i =0; i < tbpList.length;i++){
                ArrayList<String> tbpInfor = tp.loadTBPLuu(Integer.parseInt(tbpList[i]));
             //   data.add(sotbp);data.add(ngaytao);data.add(ngayhh);
           //     data.add(nguoictn);data.add(lienhe);data.add(cvid);
                String sobp = tbpInfor.get(0);
                String ngaytao = tbpInfor.get(1);
                String thangbp = ngaytao.split("-")[1];
                String ngayhh = tbpInfor.get(2);
                String nguoictn = tbpInfor.get(3);
                String lienhe = tbpInfor.get(4);
                String chucvu = tbpInfor.get(5);
                String taikhoan = tbpInfor.get(6);
                ArrayList<String> donTbp = tp.loadDonTBPLuu(Integer.parseInt(tbpList[i]));
                ArrayList<String> khTP = new Thuphi().printGetBNBD(tbpList[i]);
                String tbody_thongbaophi = tp.loadInforDonTBP(donTbp.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            %>

         <div class="wrapper">
         <div class="header">
         	<div class="top_header">
                                    <div class="top_header_left">
                                        <div>CỤC ĐĂNG KÝ QUỐC GIA</div>
                                            <div>GIAO DỊCH BẢO ĐẢM</div>
                                            <div><strong>TRUNG TÂM ĐĂNG KÝ</strong></div>
                                            <div><strong>GIAO DỊCH TÀI SẢN TẠI ĐÀ NẴNG</strong></div>
                                            <div><strong>Số:<span id="sothongbao0"><%=(thangbp +sobp)%></span>/TT3</strong></div>
                                    </div>
                                    <div class="top_header_right">
                                            <div style="word-spacing: 1px;"><strong>CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</strong></div>
                                            <div><strong> Độc lập - Tự do - Hạnh phúc </strong></div>
                                            <div style="margin-top:5px;"><strong><i>Đà Nẵng, Ngày <%=ngaytao.split("-")[0] %> tháng <%=ngaytao.split("-")[1] %> năm <%=ngaytao.split("-")[2]%></i></strong></div>
                                    </div>
                                    <div class="content_header">
                                            <div style="font-size:16px;word-spacing: 1px;margin-left:15px;"><strong>THÔNG BÁO THANH TOÁN LỆ PHÍ ĐĂNG KÝ,PHÍ CCTT VỀ GIAO DỊCH BẢO ĐẢM</strong></div>
                                    </div>
                                    <div class="bottom_header">
                                        <div><strong>Kính gửi : <%=khTP.get(0) %></strong></div>
                                            <div style="margin-top:2px;"><strong>Địa chỉ : <span id="dc0"><%=khTP.get(1) %></span></strong></div>
                                            <div style="margin-top:2px;height:20;">
                                                    <div style="width:300;float:left;height:auto;">
                                                            <strong>Mã khách hàng: </strong>
                                                            <span id="tk0"><%=khTP.get(2) %></span>
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
                                                <strong><i>Diễn giải chi tiết đơn ngân hàng chưa thanh toán đến thời điểm cuối ngày <%=ngaytao.split("-")[0] %>-<%=ngaytao.split("-")[1] %>-<%=ngaytao.split("-")[2]%></i></strong>
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
                                                                   <strong>*Đề nghị Quý ngân hàng thanh toán trước ngày : <%=ngayhh.split("-")[0] %>/<%=ngayhh.split("-")[1] %>/<%=ngayhh.split("-")[2]%></strong>
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
                                                        <strong><%=nguoictn %></strong>
                                                </div>

                     </div>
           <div style="page-break-before: always;"></div>                 
          <%
                   }
              }catch(Exception ex){
              ex.printStackTrace();
              response.sendRedirect("../404.jsp");
            }
            %>
        
</body>
</html>
