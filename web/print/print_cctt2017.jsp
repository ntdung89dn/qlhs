<%-- 
    Document   : print_cctt2017
    Created on : Jun 20, 2017, 2:03:41 PM
    Author     : ntdung
--%>

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
        <style type="text/css">      
            html, body {       
                margin: 0;      
                padding: 0;         
                font-family: "Times New Roman";      
                font-size: 14pt;       
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
            span.checkbox {         
                border: 1px #000 solid;       
                  height: 0.6cm;         
                  width: 0.65cm;    
                  font-weight: bold;       
                  line-height: calc(0.6cm - 2px);     
                  margin-right: 3pt;       
                  display: inline-block;      
            }       
            span.checkbox:after {         
                content: " ";         
                width: 100%;        
                text-align: center;    
                margin: 1px;         
                height: 100%;      
                vertical-align: middle;      
                margin-left: calc(-1cm + 3px);   
            }       
            span.checkbox.active:after {           
                content: "✕";            width: 100%;   
                text-align: center;            margin: 1px;       
                height: 100%;            vertical-align: middle;        
                margin-left: calc(-1cm + 3px);        }        
            @page {            orphans: 4;            widows: 2;        }        
            @media print {            
                html, body {                background-color: #fff;            }           
                .page {                width: initial !important;       
                                     min-height: initial !important;         
                                     margin: 0 !important;            
                                     padding: 0 !important;        
                                     border: initial !important;            
                                     border-radius: initial !important;          
                                     background: initial !important;          
                                     box-shadow: initial !important;            
                                  //   page-break-after: always;       
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
            table.table-border {            border-collapse: collapse;        }     
            table.table-border td, table th {            border: 1pt solid windowtext;        }      
            table.table-border td {            padding: 0.1cm;        }     
            table.table-border td p {            text-align: left;        }       
            ul.branch-info {            list-style: none;            margin-top: 0;        }        
            ul.branch-info li:before{            content: "- ";        }    
        /**    #bc_print {
                -webkit-transform: rotate(270deg);
                -moz-transform: rotate(270deg);
                -o-transform: rotate(270deg);
                -ms-transform: rotate(270deg);
                transform: rotate(270deg);
            }**/
            select#print_cctt_cbbbd{
                border:0px;
                outline:0px;
                 border:0px;
                outline:0px;
                -webkit-appearance: none;
                 -moz-appearance: none;
                 text-indent: 1px;
                 text-overflow: '';
            }
            textarea {
                border: none;
                background-color: transparent;
                resize: none;
                outline: none;
                font-family: Times New Roman;
            }
            input{
                font-family: Times New Roman;
            }
        </style> 
        <title>Cung cấp thông tin có cấp giấy chứng nhận (GCN)</title>
    </head>
    <body class="document"><div class="page">
            <%
                int id = Integer.parseInt(request.getParameter("id"));
                InforPrint ip = new InforPrint();
                ArrayList<String> data = ip.loadPrintInfor(id);
                String  giohl = "00";
                if(data.get(0) != null){
                    giohl = data.get(0);
                }
               
                String phuthl = "00";
                if(data.get(1) != null){
                    phuthl = data.get(1);
                }
                String thoigianhl = data.get(2);
                String[] thoigian = thoigianhl.split("-");
                String namhl = thoigian[2];
                String ngayhl = thoigian[0];
                String thanghl = thoigian[1];
                String dononline  = data.get(3);
                String mapin = data.get(4);
                String[] bnbdList = data.get(5).split("&");
                String[] benbaodamList = data.get(6).split("&");
                String maloainhan = data.get(7);
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                //getTime() returns the current date in default time zone
                Date date = calendar.getTime();
                int day = calendar.get(Calendar.DATE);
                //Note: +1 the month for current month
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
            %>
            <table width="100%" style="margin-top: 0px;margin-left: 0px;">
                    <tbody>
                        <tr>
                            <td><div id="bc_print" style="float: left; font-size: 0px; background-color: #FFFFFF;"></div></td>
                            <td align="right"><span style="font-weight: bold;font-size: 13px;"><%= maloainhan %></span></td>
                        </tr>
                    </tbody>
                </table><br>
    <table width="100%">
        <tbody><tr>
            <td style="width: 25%; text-align: center"><img src="./image/logo.PNG" alt="logo" style="width:115px; height: 115px; "></td>
            <td style="text-align: center; width: 70%">
                <p style="text-transform: uppercase; text-align: center; font-weight: bold;">Cộng hòa xã hội chủ nghĩa Việt Nam</p>
                <p style="text-align: center; font-weight: bold;">Độc lập - Tự do - Hạnh phúc</p>
                <hr style="width: 50%; margin: 0 auto; color: black; display: block; margin-top: 3pt;" size="1">
                <p style="margin-top: 0.5cm"></p>
                <p style="font-size: 12pt; font-style: italic; text-align: center">
                                                                                        Đà Nẵng, ngày <%= day %> tháng <%= month %> năm <%= year %>                                                        </p>
            </td>
        </tr>
    </tbody></table>
    <p style="text-align: center; font-weight: bold; font-size: 14pt; text-transform: uppercase;">
        VĂN BẢN CUNG CẤP THÔNG TIN VỀ BIỆN PHÁP BẢO ĐẢM,        <br>
        HỢP ĐỒNG, THÔNG BÁO VIỆC KÊ BIÊN TÀI SẢN THI HÀNH ÁN    </p>
    <p style="margin-top: 1cm"></p>
    <p style="text-indent: 1.5cm;">Người yêu cầu cung cấp thông tin: Trung tâm Đăng ký giao dịch tài sản tại Đà Nẵng</p>
    <p style="text-indent: 1.5cm; margin-top: 6pt;">Địa chỉ liên hệ: 109 Hoàng Sĩ Khải, Phường An Hải Bắc, Quận Sơn Trà, Đà Nẵng, Sơn Trà, Đà Nẵng, Việt Nam</p>
    <p style="margin-top: 0.5cm"></p>
            <p style="text-align: center; font-weight: bold; font-size: 14pt; text-transform: uppercase;">
            trung tâm đăng ký giao dịch, tài sản tại thành phố Đà nẵng            Chứng nhận        </p>
        <p style="margin-top: 1cm"></p>
    <p style="text-indent: 1cm; margin-bottom: 3pt;">
        <span style="font-weight: bold;">1. </span>
        Việc tra cứu thông tin được thực hiện theo tiêu chí sau đây:    </p>
    <p style="text-indent: 1cm; margin-top: 3pt; margin-bottom: 3pt;">
        <span class="checkbox "></span><span>Số giấy tờ xác định tư cách pháp lý của bên bảo đảm
            
        </span>
        <span style="display: inline;" class="span-hidden-object">
            <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 
            <select id="print_cctt_cbbbd" style="font-size: 17px;">
                <option value="1">Công dân Việt Nam</option>
                <option value="2">Tổ chức có đăng ký kinh doanh trong nước</option>
                <option value="3">Người nước ngoài</option>
                <option value="4">Tổ chức nước ngoài</option>
                <option value="5">Người không quốc tịch cư trú tại Việt Nam</option>
            </select><br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="hovaten"></span><br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="masocanhan"></span>
        </span>
        
    </p>
    <p style="text-indent: 1cm; margin-top: 3pt; margin-bottom: 3pt;">
        <span class="checkbox "></span><span>Số khung của phương tiện giao thông cơ giới<span class="span-hidden-object">: <input type="text" class="" style="font-weight: bold;border: 0;font-size: 17px;" value="nhập số khung"></span></span>
    </p>
    <p style="text-indent: 1cm; margin-top: 3pt; margin-bottom: 3pt;">
        <span class="checkbox active"></span><span>Số đơn đăng ký: <span class="span-hidden-object">: <input type="text" class="" style="font-weight: bold;border: 0;font-size: 17px;" value="nhập số đơn đăng ký"></span></span>
    </p>
    <p style="text-indent: 1cm; margin-top: 3pt; margin-bottom: 3pt;">
        <span class="checkbox "></span><span>Tên tổ chức (tổ chức khác)</span><br>
        <span class="span-hidden-object">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea style="font-weight: bold;border: 0;font-size: 17px;width: 550px;">Nhập tên tổ chức khác</textarea></span>
        
    </p>
    <p style="margin-top: 0.3cm"></p>
        <p style="text-indent: 1cm; margin-top: 3pt; margin-bottom: 3pt;">
        <span style="font-weight: bold;">2. </span>
        Thông tin về biện pháp bảo đảm, hợp đồng, thông báo việc kê biên tài sản thi hành án tra cứu lúc <input value="<%= giohl %>" style="border: 0px; max-width: 21px;" maxlength="2" id="print_giohl"> giờ <input value="<%=phuthl%>" style="border: 0px; max-width: 21px;" maxlength="2" id="print_phuthl"> phút,
        ngày <input value="<%=ngayhl%>" style="border: 0px; max-width: 21px;font-size: 16px;height: 30px;" maxlength="2" id="print_ngayhl"> tháng <input value="<%= thanghl %>" style="border: 0px; max-width: 21px;" maxlength="2" id="print_thanghl"> năm <input value="<%= namhl %>" style="border: 0px; max-width: 35px;" maxlength="4" id="print_namhl"> được Trung tâm Đăng ký giao dịch, tài sản cung cấp kèm theo Giấy chứng nhận.    </p>
    <p style="margin-top: 0.5cm"></p>
    <p style="margin-top: 0.5cm"></p>
    <table style="border: none;" width="100%">
        <tbody>
        <tr style="border: none;">
            <td width="50%" style="border: none;">
              <!--  <div id="bc_print" style="font-size: 20px;font-weight: bold;bottom: 50px;position: absolute;left: 270px;"></div>    
                <div id="bc_print" style="font-size: 20px;font-weight: bold;bottom: -50px;position: relative;vertical-align: top;left: -130px;"></div>   -->
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
<!--<div class="page">
    <p style="text-transform: uppercase; font-weight: bold;">KẾT QUẢ CUNG CẤP THÔNG TIN CÓ XÁC NHẬN CỦA CƠ QUAN ĐĂNG KÝ</p>
    <p style="margin-top: 1cm"></p>
    <p>Thông tin đã được tìm thấy trong cơ sở dữ liệu của Cục đăng ký quốc gia giao dịch bảo đảm thỏa mãn các tiêu chí tra cứu thông tin như sau:</p>
    <p style="margin-top: 0.3cm"></p>
    <p>- Số đơn cung cấp thông tin: 7000201004</p>
    <p>- Số đơn đăng ký: 1000123062</p>
    <p>- Ngày/giờ tra cứu: 10-06-2017 11:44</p>
            <hr style="margin-top: 0.5cm; border: 1px #000 solid;">
        <p style="font-weight: bold; font-size: 18pt">
          Đăng ký giao dịch bảo đảm / Hợp đồng - 1000123062        </p>
        <p style="margin-top: 0.3cm; font-weight: bold;">
            Loại hình giao dịch: Giao dịch bảo đảm         </p>
                                <p style="text-transform: uppercase; font-size: 14pt; font-weight: bold; margin-top: 0.2cm">
        Đăng ký lần đầu    </p>
    <p>
      Trạng thái:  
      Đã duyệt    </p>
        <p style="margin:5px 0; padding:0; ">
      Số hợp đồng : 
      01/2017/9754544/HĐTC    </p>
            <p> 
      Ngày ký hợp đồng: 
      12/05/2017 
    </p>
        <br>
    <table cellspacing="0" cellpadding="5" width="100%" class="data-table table-border">
      <tbody><tr>
                    <th style="text-align: left; width: 50%;">
              Số đơn đăng ký            </th>
                <th style="text-align: left; width: 50%;">
          Thời điểm đăng ký         </th>
      </tr>
      <tr>
        <td style="text-align: left;">
          1000123062        </td>
        <td style="text-align: left; ">
          10-06-2017 10:43        </td>
      </tr>
    </tbody></table>
    <br>
            <p style="font-weight: bold;">Bên bảo đảm </p>
        <table cellspacing="0" cellpadding="5" width="100%" class="data-table table-border">
            <tbody><tr>
                <th style="width: 25%; text-align: center; vertical-align: middle; ">
                    <span>Loại chủ thể </span>
                </th>
                <th style="width: 25%; text-align: center; vertical-align: middle; ">
                    <span>Số giấy tờ <br> chứng minh tư <br>cách pháp lý</span>
                </th>
                <th style="width: 20%; text-align: center; vertical-align: middle; ">
                    <span>Tên</span>
                </th>
                <th style="width: 35%; text-align: center; vertical-align: middle; ">
                    <span>Địa chỉ</span>
                </th>
            </tr>
        
                    <tr>
                <td>
                    Công dân Việt Nam                </td>
                <td>
                Số CMND/Căn cước công dân: 273067087                                </td>
                                    <td>Phạm Thị Dung</td>
                                <td>
                Tổ 1, Khu Phố Hải Dinh, phường Kim Dinh, Bà Rịa, Bà Rịa Vũng Tàu, Việt Nam                </td>
            </tr>
                </tbody></table>
        <br>
        <p style="font-weight: bold;">Bên nhận bảo đảm </p>
        <table cellpadding="5" cellspacing="0" width="100%" class="data-table table-border">
                    <tbody><tr>
            <th style="text-align: center; width: 40%; ">
                Tên            </th>
            <th style="text-align: center; width: 60%; ">
                Địa chỉ            </th>
            </tr>
                                            <tr>
                                    <td style="text-align: left;">
                        ádsa                    </td>
                                <td style="text-align: left;">
                1111, Ngọc Hiền, Cà Mau, Việt Nam                </td>
            </tr>
                </tbody></table>
        <br>
        <p style="font-weight: bold;">Loại tài sản bảo đảm</p>
        <p>
            &nbsp;Phương tiện giao thông cơ giới có số khung (ô tô, mô tô, xe gắn máy...)<br>        </p>
        <br>
        <p style="font-weight: bold;">Mô tả tài sản bảo đảm </p>
        <p>
            Xe ô tô con nhãn hiệu TOYOTA        </p>
                    <br>
            <p style="font-weight: bold;">Số khung</p>
            <table cellpadding="5" cellspacing="0" width="100%" class="data-table table-border">
                <thead>
                    <tr>
                        <th style="text-align: center;">
                            <span class="nobr">
                            Số khung                            </span>
                        </th>
                        <th style="text-align: center;">
                            <span class="nobr">
                            Số máy                            </span>
                        </th>
                        <th style="text-align: center;">
                            <span class="nobr">
                            Biển số                            </span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                            <tr class="vin_number_csv">
                    <td>
                        43G079220078                    </td>
                    <td>
                                            </td>
                    <td>
                                            </td>
                </tr>
                        </tbody></table>
                                <hr style="margin-top: 0.5cm; border: 1px #000 solid;">
                <p style="text-transform: uppercase; font-size: 14pt; font-weight: bold; margin-top: 0.2cm">
        Đăng ký thay đổi    </p>
    <p>
      Trạng thái:  
      Đã duyệt    </p>
        <p style="margin:5px 0; padding:0; ">
      Số hợp đồng : 
      01/2017/9754544/HĐTC    </p>
            <p> 
      Ngày ký hợp đồng: 
      12/05/2017 
    </p>
        <br>
    <table cellspacing="0" cellpadding="5" width="100%" class="data-table table-border">
      <tbody><tr>
                    <th style="text-align: left; width: 50%;">
              Đăng ký thay đổi Số đơn            </th>
                <th style="text-align: left; width: 50%;">
          Thời điểm đăng ký         </th>
      </tr>
      <tr>
        <td style="text-align: left;">
          2000054966        </td>
        <td style="text-align: left; ">
          10-06-2017 11:09        </td>
      </tr>
    </tbody></table>
    <br>
            <p style="font-weight: bold;">Bên bảo đảm </p>
        <table cellspacing="0" cellpadding="5" width="100%" class="data-table table-border">
            <tbody><tr>
                <th style="width: 25%; text-align: center; vertical-align: middle; ">
                    <span>Loại chủ thể </span>
                </th>
                <th style="width: 25%; text-align: center; vertical-align: middle; ">
                    <span>Số giấy tờ <br> chứng minh tư <br>cách pháp lý</span>
                </th>
                <th style="width: 20%; text-align: center; vertical-align: middle; ">
                    <span>Tên</span>
                </th>
                <th style="width: 35%; text-align: center; vertical-align: middle; ">
                    <span>Địa chỉ</span>
                </th>
            </tr>
        
                    <tr>
                <td>
                    Công dân Việt Nam                </td>
                <td>
                Số CMND/Căn cước công dân: 273067087                                </td>
                                    <td>Phạm Thị Dung</td>
                                <td>
                Tổ 1, Khu Phố Hải Dinh, phường Kim Dinh, Bà Rịa, Bà Rịa Vũng Tàu, Việt Nam                </td>
            </tr>
                    <tr>
                <td>
                    Công dân Việt Nam                </td>
                <td>
                Số CMND/Căn cước công dân: 123456987                                </td>
                                    <td>Nguyễn Hữu Thành</td>
                                <td>
                43-Phan Đăng Lưu, Phục Hoà, Cao Bằng, Việt Nam                </td>
            </tr>
                </tbody></table>
        <br>
        <p style="font-weight: bold;">Bên nhận bảo đảm </p>
        <table cellpadding="5" cellspacing="0" width="100%" class="data-table table-border">
                    <tbody><tr>
            <th style="text-align: center; width: 40%; ">
                Tên            </th>
            <th style="text-align: center; width: 60%; ">
                Địa chỉ            </th>
            </tr>
                                                            <tr>
                                    <td style="text-align: left;">
                        ádsa                    </td>
                                <td style="text-align: left;">
                1111, Ngọc Hiền, Cà Mau, Việt Nam                </td>
            </tr>
                    <tr>
                                    <td style="text-align: left;">
                        Nguyễn Hữu Thành                    </td>
                                <td style="text-align: left;">
                43- Phan Đăng Lưu, Phong Điền, Cần Thơ, Việt Nam                </td>
            </tr>
                </tbody></table>
        <br>
        <p style="font-weight: bold;">Loại tài sản bảo đảm</p>
        <p>
            &nbsp;Phương tiện giao thông cơ giới có số khung (ô tô, mô tô, xe gắn máy...)<br>Máy móc, thiết bị, xe máy chuyên dùng (xe lu, xe cẩu...)<br>        </p>
        <br>
        <p style="font-weight: bold;">Mô tả tài sản bảo đảm </p>
        <p>
            SƠMI RƠMOÓC        </p>
                    <br>
            <p style="font-weight: bold;">Số khung</p>
            <table cellpadding="5" cellspacing="0" width="100%" class="data-table table-border">
                <thead>
                    <tr>
                        <th style="text-align: center;">
                            <span class="nobr">
                            Số khung                            </span>
                        </th>
                        <th style="text-align: center;">
                            <span class="nobr">
                            Số máy                            </span>
                        </th>
                        <th style="text-align: center;">
                            <span class="nobr">
                            Biển số                            </span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                            <tr class="vin_number_csv">
                    <td>
                        2131SDFS                    </td>
                    <td>
                        ...                    </td>
                    <td>
                        43A-215.26                    </td>
                </tr>
                        </tbody></table>
                                <hr style="margin-top: 0.5cm; border: 1px #000 solid;">
                <p style="text-transform: uppercase; font-size: 14pt; font-weight: bold; margin-top: 0.2cm">
        Xóa đơn đăng ký    </p>
    <p>
      Trạng thái:  
      Đã duyệt    </p>
        <p style="margin:5px 0; padding:0; ">
      Số hợp đồng : 
      01/2017/9754544/HĐTC    </p>
            <p> 
      Ngày ký hợp đồng: 
      12/05/2017 
    </p>
        <br>
    <table cellspacing="0" cellpadding="5" width="100%" class="data-table table-border">
      <tbody><tr>
                    <th style="text-align: left; width: 50%;">
              Xóa đơn đăng ký Số đơn            </th>
                <th style="text-align: left; width: 50%;">
          Thời điểm đăng ký         </th>
      </tr>
      <tr>
        <td style="text-align: left;">
          4000018807        </td>
        <td style="text-align: left; ">
          10-06-2017 11:13        </td>
      </tr>
    </tbody></table>
    <br>
            <p style="font-weight: bold;">Bên bảo đảm </p>
        <table cellspacing="0" cellpadding="5" width="100%" class="data-table table-border">
            <tbody><tr>
                <th style="width: 25%; text-align: center; vertical-align: middle; ">
                    <span>Loại chủ thể </span>
                </th>
                <th style="width: 25%; text-align: center; vertical-align: middle; ">
                    <span>Số giấy tờ <br> chứng minh tư <br>cách pháp lý</span>
                </th>
                <th style="width: 20%; text-align: center; vertical-align: middle; ">
                    <span>Tên</span>
                </th>
                <th style="width: 35%; text-align: center; vertical-align: middle; ">
                    <span>Địa chỉ</span>
                </th>
            </tr>
        
                    <tr>
                <td>
                    Công dân Việt Nam                </td>
                <td>
                Số CMND/Căn cước công dân: 273067087                                </td>
                                    <td>Phạm Thị Dung</td>
                                <td>
                Tổ 1, Khu Phố Hải Dinh, phường Kim Dinh, Bà Rịa, Bà Rịa Vũng Tàu, Việt Nam                </td>
            </tr>
                    <tr>
                <td>
                    Công dân Việt Nam                </td>
                <td>
                Số CMND/Căn cước công dân: 123456987                                </td>
                                    <td>Nguyễn Hữu Thành</td>
                                <td>
                43-Phan Đăng Lưu, Phục Hoà, Cao Bằng, Việt Nam                </td>
            </tr>
                </tbody></table>
        <br>
        <p style="font-weight: bold;">Bên nhận bảo đảm </p>
        <table cellpadding="5" cellspacing="0" width="100%" class="data-table table-border">
                    <tbody><tr>
            <th style="text-align: center; width: 40%; ">
                Tên            </th>
            <th style="text-align: center; width: 60%; ">
                Địa chỉ            </th>
            </tr>
                                                            <tr>
                                    <td style="text-align: left;">
                        ádsa                    </td>
                                <td style="text-align: left;">
                1111, Ngọc Hiền, Cà Mau, Việt Nam                </td>
            </tr>
                    <tr>
                                    <td style="text-align: left;">
                        Nguyễn Hữu Thành                    </td>
                                <td style="text-align: left;">
                43- Phan Đăng Lưu, Phong Điền, Cần Thơ, Việt Nam                </td>
            </tr>
                </tbody></table>
        <br>
        <p style="font-weight: bold;">Loại tài sản bảo đảm</p>
        <p>
            &nbsp;Phương tiện giao thông cơ giới có số khung (ô tô, mô tô, xe gắn máy...)<br>Máy móc, thiết bị, xe máy chuyên dùng (xe lu, xe cẩu...)<br>        </p>
        <br>
        <p style="font-weight: bold;">Mô tả tài sản bảo đảm </p>
        <p>
            SƠMI RƠMOÓC        </p>
                    <br>
            <p style="font-weight: bold;">Số khung</p>
            <table cellpadding="5" cellspacing="0" width="100%" class="data-table table-border">
                <thead>
                    <tr>
                        <th style="text-align: center;">
                            <span class="nobr">
                            Số khung                            </span>
                        </th>
                        <th style="text-align: center;">
                            <span class="nobr">
                            Số máy                            </span>
                        </th>
                        <th style="text-align: center;">
                            <span class="nobr">
                            Biển số                            </span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                            <tr class="vin_number_csv">
                    <td>
                        2131SDFS                    </td>
                    <td>
                        ...                    </td>
                    <td>
                        43A-215.26                    </td>
                </tr>
                        </tbody></table>
                                        
            </div> -->
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
                loadBarcode();
              //  $('.span-hidden-object').hide();
                $(document).on('click','span.checkbox',function(){
                    $('span.checkbox').removeClass("active");
                    $(this).addClass("active");
                    showSpanHidden();
                });
                function showSpanHidden(){
                    $('.span-hidden-object').hide();
                    $('span.checkbox').each(function(i,el){
                         if($(this).hasClass("active")){
                             $(this).parent().find('.span-hidden-object').show();
                           //  console.log("ABCXUZ");
                           if($('#print_cctt_cbbbd').length){
                               selectCbb();
                           }
                           
                         }
                    });
                };
                var selectCbb = function(){
                    var selectValue = $('#print_cctt_cbbbd').val();
                    console.log(selectValue);
                    if(selectValue ==="1"){
                        $('#print_cctt_cbbbd').parent().find("#hovaten").html("-Họ và Tên: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập họ và tên' >");
                        $('#print_cctt_cbbbd').parent().find("#masocanhan").html("-Số CMND /Số CM sỹ quan /Số CM quân đội: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập cmnd' >");
                    }else if(selectValue ==="2"){
                        $('#print_cctt_cbbbd').parent().find("#hovaten").html("-Tên công ty: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập tên tổ chức' >");
                        $('#print_cctt_cbbbd').parent().find("#masocanhan").html("-Mã Số Thuế: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập mã số thuế'>");
                    }else if(selectValue ==="3"){
                        $('#print_cctt_cbbbd').parent().find("#hovaten").html("-Họ và Tên: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập tên' >");
                        $('#print_cctt_cbbbd').parent().find("#masocanhan").html("-Số hộ chiếu: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập số hộ chiếu' >");
                    }else if(selectValue ==="4"){
                        $('#print_cctt_cbbbd').parent().find("#hovaten").html("-Tên tổ chức: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập tên tổ chức' >");
                        $('#print_cctt_cbbbd').parent().find("#masocanhan").html("-Quyết định thành lập: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập quyết định thành lập' >");
                    }else if(selectValue ==="5"){
                        $('#print_cctt_cbbbd').parent().find("#hovaten").html("-Họ và Tên: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập tên ' >");
                        $('#print_cctt_cbbbd').parent().find("#masocanhan").html("-Thông tin nhận biết: <input type='text' style='font-weight: bold;border: 0;font-size: 17px;' value='nhập thông tin' >");
                    }
                   
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
                $('#print_cctt_cbbbd').on('change',selectCbb);
                showSpanHidden();
            </script>
    </body>
</html>
