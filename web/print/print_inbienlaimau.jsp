<%-- 
    Document   : print_inbienlaimau
    Created on : Jul 31, 2017, 1:33:19 PM
    Author     : ntdung
--%>

<%@page import="com.ttdk.createFile.ChangeNumberToText"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>In Biên Lai</title>
        <link rel="stylesheet" type="text/css" href="../css/bienlai.css">
        <style type="text/css">
                textarea {
                        background: none repeat scroll 0 0 transparent;
                        border: 0 none white;
                        line-height: 16px;
                        outline: medium none;
                        overflow: hidden;
                        padding: 0;
                        width: 600px;
                        z-index: 100;
                        color: #333333;
                        font-family: "Times New Roman",Times,serif;
                        font-size: 12px!important;
                        font-weight: normal!important;
                }
        </style>
    </head>
    <body>
        <%
            String tendonvi = "",diachi = "",lydo = "",loaithanhtoan="",tongtien="",tienchu="",nvien="",newtongtien  = "",monthStr = "",dayStr="";
                if(session.getAttribute("tendonvi") != null){
                    tendonvi = session.getAttribute("tendonvi").toString();
                    diachi = session.getAttribute("diachi").toString();
                    lydo = session.getAttribute("lydo").toString();
                    loaithanhtoan = session.getAttribute("loaithanhtoan").toString();
                    tongtien = session.getAttribute("tongtien").toString();
                    System.out.println(tongtien);
                    tongtien = new ChangeNumberToText().priceWithDecimal(Double.valueOf(tongtien));
                    System.out.println(tongtien);
                    tienchu = session.getAttribute("tienchu").toString();
                    nvien = session.getAttribute("nvien").toString();
                }
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                int day = calendar.get(Calendar.DATE);
                if(day <10){
                    dayStr = "0"+day;
                }else{
                    dayStr = ""+day;
                }
                //Note: +1 the month for current month
                int month = calendar.get(Calendar.MONTH) + 1;
                if(month <3){
                    monthStr = "0"+month;
                }else{
                    monthStr = ""+month;
                }
                int year = calendar.get(Calendar.YEAR);
                String yearSub = String.valueOf(year).substring(String.valueOf(year).length()-2);
            %>
        <div class="wrapper">
                <div style="height:115px;width:650px;"></div>
                <div class="content"> 
                    <div class="sec_wrapper1">
                            <span class="line1"></span>
                            <span class="point1">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;								
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=tendonvi %></span>
                            <span class="line2"></span>
                            <span class="point2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=diachi %></span>
                            <span class="line3"></span>
                            <span class="point3">
                                     <textarea rows="1" id="lydo" style="height: 64px; font-weight: bold; font-size: 16px; color: rgb(51, 51, 51); position: absolute; right: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= lydo %></textarea>
                            </span>
                            <span class="point4"></span>
                            <span class="point5"></span>
                            <span class="line5"></span>
                            <span class="point6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=tongtien %></span>
                            <span class="line6"></span>
                            <span class="point7" id="tongtien">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%=tienchu %> đồng chẵn</span>
                            <span class="point8"></span>
                            <span class="line7"></span>
                            <span class="point9">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= loaithanhtoan %>						</span>
                            <span class="line8"></span>
                            <span class="point10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=dayStr %></span>
                            <span class="line9"></span>
                            <span class="point11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=monthStr %></span>
                            <span class="line10"></span>
                            <span class="point12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=yearSub%></span>
                            <span class="point13" style="height: 15px;left: 480px;position: absolute;top: 288px;width: 200px;">&nbsp;&nbsp;&nbsp;<%=nvien %></span>
                    </div>
            </div>
   </div>
    </body>
</html>
