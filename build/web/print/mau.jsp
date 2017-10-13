<%-- 
    Document   : mau
    Created on : Jul 28, 2016, 8:25:45 AM
    Author     : Thorfinn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	<title>
		Quan Ly Ho So  	</title>	
	<link href="/favicon.ico" type="image/x-icon" rel="icon"><link href="/favicon.ico" type="image/x-icon" rel="shortcut icon">
	<link rel="stylesheet" type="text/css" href="/css/css/report12.css">
	<link rel="stylesheet" type="text/css" href="/css/datepicker/jquery_datepicker.css">

	<script type="text/javascript" src="/js/datepicker/jquery.min.js"></script>
	<script type="text/javascript" src="/js/datepicker/jquery-ui.min.js"></script>

	
	<style type="text/css">
		textarea {
			background: none repeat scroll 0 0 transparent;
			border: 0 none white;
			line-height: 23px;
			outline: medium none;
			overflow: hidden;
			padding: 0;
			width: 655px;
			z-index: 100;
			font-family: "Times New Roman",Times,serif;
		}
                html, body, ul, li {
    list-style: none outside none;
    margin: 0;
    padding: 0;
}
body {
    color: #333333;
    font-family: "Times New Roman",Times,serif;
    font-size: 12px;
    font-weight: normal;
    height: 100%;
    text-align: center;
    width: 100%;
}
.wrapper {
    margin: 0 auto;
    min-height: 650px;
    text-align: left;
    width: 650px;
}
.header {
    height: 210px;
    position: relative;
    width: 650px;
    z-index: 2;
}
.header .temp {
    font-size: 14px;
    font-weight: bold;
    position: absolute;
    right: 1px;
}
.header .logo {
    position: absolute;
    top: 10px;
}
.header .chxh {
    font-size: 19px;
    font-weight: bold;
    left: 170px;
    position: absolute;
    top: 40px;
    word-spacing: 1px;
}
.header .dltd {
    font-size: 18px;
    font-weight: bold;
    left: 260px;
    position: absolute;
    top: 65px;
    word-spacing: 1px;
}
.header .date {
    font-size: 14px;
    left: 200px;
    position: absolute;
    top: 100px;
    width: 600px;
    word-spacing: 1px;
}
.header .date .point1 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 15px;
    position: absolute;
    top: -4px;
    width: 80px;
    word-spacing: 2px;
}
.header .date .line1 {
    font-size: 16px;
    left: 95px;
    position: absolute;
    top: 0;
    width: 40px;
}
.header .date .line2 {
    font-size: 16px;
    left: 180px;
    position: absolute;
    top: 0;
    width: 40px;
}
.header .date .line3 {
    font-size: 16px;
    left: 260px;
    position: absolute;
    top: 0;
    width: 40px;
}
.header .date .point2 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 135px;
    position: absolute;
    top: -4px;
    width: 40px;
}
.header .date .point3 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 215px;
    position: absolute;
    top: -4px;
    width: 40px;
}
.header .date .point4 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 290px;
    position: absolute;
    top: -4px;
    width: 50px;
}
.header .title {
    font-size: 19px;
    font-weight: bold;
    left: 30px;
    position: absolute;
    top: 135px;
    width: 650px;
    word-spacing: 1px;
}
.header .ghichu {
    font-size: 14px;
    font-style: italic;
    left: 30px;
    margin-left: 40px;
    position: absolute;
    top: 190px;
    width: 650px;
    word-spacing: 2px;
}
.content {
    clear: both;
    width: 650px;
}
.content .sec_wrapper1 {
    clear: both;
    height: 65px;
    margin-top: 35px;
    position: relative;
    width: 650px;
}
.content .sec_wrapper1 .line1 {
    font-size: 18px;
    margin-left: 40px;
    position: absolute;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .line2 {
    font-size: 18px;
    position: absolute;
    right: -24px;
    width: 60px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .line3 {
    font-size: 18px;
    position: absolute;
    top: 22px;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .line4 {
    font-size: 18px;
    position: absolute;
    right: 170px;
    top: 22px;
    width: 60px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .line5 {
    font-size: 18px;
    position: absolute;
    right: 117px;
    top: 22px;
    width: 60px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .line6 {
    font-size: 18px;
    position: absolute;
    right: 75px;
    top: 22px;
    width: 60px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .line7 {
    font-size: 18px;
    position: absolute;
    top: 42px;
    width: 60px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .line8 {
    font-size: 18px;
    left: 145px;
    position: absolute;
    top: 42px;
    width: 60px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .point1 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: 40px;
    top: -4px;
    width: 183px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .point2 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: 235px;
    top: 18px;
    width: 27px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .point3 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: 181px;
    top: 18px;
    width: 25px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .point4 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: -1px;
    top: 18px;
    width: 95px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .point5 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 45px;
    position: absolute;
    top: 38px;
    width: 95px;
    word-spacing: 2px;
}
.content .sec_wrapper1 .point6 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 180px;
    position: absolute;
    top: 38px;
    width: 95px;
    word-spacing: 2px;
}
.content .sec_wrapper2 {
    clear: both;
    height: 45px;
    margin-top: 23px;
    position: relative;
    width: 650px;
}
.content .sec_wrapper2 .line1 {
    font-size: 20px;
    font-weight: bold;
    position: absolute;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper2 .point1 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: -1px;
    top: -2px;
    width: 177px;
    word-spacing: 2px;
}
.content .sec_wrapper2 .line2 {
    font-size: 20px;
    font-weight: bold;
    margin-left: 5px;
    position: absolute;
    text-align: center;
    top: 20px;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper3 {
    clear: both;
    height: 85px;
    margin-top: 23px;
    position: relative;
    width: 650px;
}
.content .sec_wrapper3 .line1 {
    font-size: 18px;
    margin-left: 35px;
    position: absolute;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line2 {
    font-size: 18px;
    position: absolute;
    top: 22px;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line3 {
    font-size: 18px;
    position: absolute;
    right: -14px;
    top: 22px;
    width: 150px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line4 {
    font-size: 18px;
    position: absolute;
    top: 42px;
    width: 50px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line5 {
    font-size: 18px;
    left: 61px;
    position: absolute;
    top: 42px;
    width: 50px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line6 {
    font-size: 18px;
    left: 133px;
    position: absolute;
    top: 42px;
    width: 50px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line7 {
    font-size: 18px;
    left: 175px;
    position: absolute;
    top: 42px;
    width: 50px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line8 {
    font-size: 18px;
    left: 260px;
    position: absolute;
    top: 42px;
    width: 50px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line9 {
    font-size: 18px;
    position: absolute;
    right: 250px;
    top: 42px;
    width: 50px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line10 {
    font-size: 18px;
    position: absolute;
    right: -39px;
    top: 42px;
    width: 250px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line11 {
    font-size: 18px;
    position: absolute;
    top: 62px;
    width: 400px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .line12 {
    font-size: 18px;
    position: absolute;
    right: 140px;
    top: 62px;
    width: 100px;
    word-spacing: 2px;
}
.content .sec_wrapper3 .point1 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: 132px;
    top: 18px;
    width: 215px;
}
.content .sec_wrapper3 .point2 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 15px;
    position: absolute;
    top: 38px;
    width: 40px;
}
.content .sec_wrapper3 .point3 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 86px;
    position: absolute;
    top: 38px;
    width: 40px;
}
.content .sec_wrapper3 .point4 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 210px;
    position: absolute;
    top: 38px;
    width: 45px;
}
.content .sec_wrapper3 .point5 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 305px;
    position: absolute;
    top: 38px;
    width: 40px;
}
.content .sec_wrapper3 .point6 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: 215px;
    top: 38px;
    width: 45px;
}
.content .sec_wrapper3 .point7 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: 240px;
    top: 58px;
    width: 30px;
}
.content .sec_wrapper4 {
    clear: both;
    height: 220px;
    margin-top: 10px;
    position: relative;
    width: 650px;
}
.content .sec_wrapper4 .line1 {
    font-size: 18px;
    margin-left: 35px;
    position: absolute;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line2 {
    font-size: 18px;
    margin-left: 35px;
    position: absolute;
    top: 80px;
    width: 200px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line3 {
    font-size: 18px;
    margin-left: 35px;
    position: absolute;
    top: 106px;
    width: 75px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line4 {
    font-size: 18px;
    margin-left: 35px;
    position: absolute;
    top: 157px;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line5 {
    font-size: 18px;
    margin-left: 35px;
    position: absolute;
    right: -21px;
    top: 157px;
    width: 20px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line6 {
    font-size: 18px;
    position: absolute;
    top: 183px;
    width: 310px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line7 {
    font-size: 18px;
    position: absolute;
    right: 155px;
    top: 184px;
    width: 30px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line8 {
    font-size: 18px;
    position: absolute;
    right: 130px;
    top: 207px;
    width: 70px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line9 {
    font-size: 18px;
    margin-left: 35px;
    position: absolute;
    top: 222px;
    width: 130px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line10 {
    font-size: 18px;
    position: absolute;
    right: 57px;
    top: 185px;
    width: 30px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .line11 {
    font-size: 18px;
    position: absolute;
    right: 10px;
    top: 185px;
    width: 30px;
    word-spacing: 2px;
}
.content .sec_wrapper4 .point1 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: -2px;
    top: -4px;
    width: 60px;
}
.content .sec_wrapper4 .point2 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    top: 22px;
    width: 652px;
}
.content .sec_wrapper4 .point3 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: -2px;
    top: 76px;
    width: 423px;
}
.content .sec_wrapper4 .point4 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: -2px;
    top: 102px;
    width: 547px;
}
.content .sec_wrapper4 .point5 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    top: 127px;
    width: 652px;
}
.content .sec_wrapper4 .point6 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 160px;
    position: absolute;
    top: 153px;
    width: 492px;
}
.content .sec_wrapper4 .point7 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 300px;
    position: absolute;
    top: 180px;
    width: 165px;
}
.content .sec_wrapper4 .point8 {
     border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: -2px;
    top: 179px;
    width: 168px;
}
.content .sec_wrapper4 .point9 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    left: 0;
    position: absolute;
    top: 203px;
    width: 445px;
}
.content .sec_wrapper4 .point10 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: -1px;
    top: 203px;
    width: 135px;
}
.content .sec_wrapper4 .point11 {
     border-bottom: 1px dotted #000000;
    height: 20px;
    left: 148px;
    position: absolute;
    top: 218px;
    width: 504px;
}
.content .sec_wrapper4 .point12 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: 40px;
    top: 180px;
    width: 45px;
}
.content .sec_wrapper4 .point13 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    right: -2px;
    top: 180px;
    width: 40px;
}
.content .sec_wrapper4 .point14 {
    border-bottom: 1px dotted #000000;
    height: 20px;
    position: absolute;
    top: 45px;
    width: 652px;
}
.content .sec_wrapper5 {
    clear: both;
    height: 85px;
    margin-top: 35px;
    position: relative;
    width: 650px;
}
.content .sec_wrapper5 .line1 {
    font-size: 16px;
    font-style: italic;
    font-weight: bold;
    margin-left: 35px;
    position: absolute;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper5 .line2 {
    font-size: 16px;
    font-style: italic;
    font-weight: bold;
    position: absolute;
    top: 20px;
    width: 650px;
    word-spacing: 2px;
}
.content .sec_wrapper5 .line3 {
    font-size: 20px;
    font-weight: bold;
    left: 430px;
    position: absolute;
    top: 70px;
    width: 630px;
    word-spacing: 2px;
}
.content .sec_wrapper5 .line4 {
    font-size: 15px;
    font-style: italic;
    left: 420px;
    position: absolute;
    top: 90px;
    width: 630px;
    word-spacing: 2px;
}
.footer {
    width: 650px;
}

	</style>

</head>
<body >
       	<div class="wrapper">
         <div class="header">
             <div class="temp"> M?u s? 12 </div>
             <div class="logo"> 
                 <img src="/css/images/logo.PNG" alt="Logo">
             </div>
             <div class="chxh">
                 C?NG HÒA XÃ H?I CH? NGHIA VI?T NAM
             </div>
             <div class="dltd">
                 <u> Ð?c l?p - T? do - H?nh phúc </u>
             </div>
             <div class="date">
                 <span class="point1">
				     <input value="Ðà N?ng" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width:80px;" maxlength="2">
				 </span>
				 <span class="line1">, ngày</span>
				 <span class="point2">
				      <input value="28" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
				 </span>
				 <span class="line2">tháng</span>
                 <span class="point3">
				     <input value="07" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
				 </span>
				 <span class="line3">nam</span>
				 <span class="point4">
				      <input value="2016" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="4">
				 </span>
             </div>
             <div class="title">
                 <span style="margin-left:40px;">GI?Y CH?NG NH?N ÐANG KÝ GIAO D?CH B?O Ð?M,</span><br>
                 <span>H?P Ð?NG, THÔNG BÁO VI?C KÊ BIÊN TÀI S?N THI HÀNH ÁN</span>
             </div>
             <span class="ghichu">(Ban hành kèm theo Thông tu s? 05/2011/TT-BTP ngày 16/02/2011 c?a b? Tu pháp)</span>
         </div>
      <div class="content">
  		

             <div class="sec_wrapper1">
                 <span class="line1">Theo Ðon yêu c?u dang ký, van b?n thông báo s?:</span>
                 <span class="point1">
				    <div style="position:absolute;top:3px;font-size:16px;left:0;width:185px;height:20px;text-align:center;overflow:hidden;">
					CE16033549BD
                                    </div>
				 </span>
                 <span class="line2">du?c</span>
                 <span class="line3">Trung tâm Ðang ký giao d?ch, tài s?n ti?p nh?n vào</span>
                 <span class="point2">
					<input value="9" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:30px;height:20px;text-align:center;overflow:hidden;" maxlength="2">
				 </span>
                 <span class="line4">gi?</span>
                 <span class="point3">
				 	<input value="54" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:25px;height:20px;text-align:center;overflow:hidden;" maxlength="2">
				 </span>
                 <span class="line5">phút,</span>
                 <span class="line6">ngày</span>
                 <span class="point4">
				 	<input value="28" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:95px;height:20px;text-align:center;overflow:hidden;" maxlength="2">
				 </span>
                 <span class="line7">tháng</span>
                 <span class="point5">
				 	<input value="05" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:95px;height:20px;text-align:center;overflow:hidden;" maxlength="2">
				 </span>
                 <span class="line8">nam</span>
                 <span class="point6">
				 	<input value="2016" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:95px;height:20px;text-align:center;overflow:hidden;" maxlength="4">
				 </span>
			</div>
	

             <div class="sec_wrapper2">
                 <span class="line1" style="text-align:center">TRUNG TÂM ÐANG KÝ GIAO D?CH, TÀI S?N T?I ÐÀ N?NG</span>
                 <span class="line2">CH?NG NH?N</span>
             </div>
             <div class="sec_wrapper3">
                 <span class="line1"><strong>1.&nbsp;</strong>N?i dung dang ký, thông báo vi?c kê biên dã du?c c?p nh?t vào Co s? d? li?u</span>
                 <span class="line2">V? giao d?ch b?o d?m, có s? dang ký là</span>
                 <span class="point1">
				    <div style="position:absolute;top:3px;font-size:16px;left:0;width:205px;height:20px;text-align:center;overflow:hidden;">CE33549</div>  
				 </span>
                 <span class="line3">, hi?u l?c dang ký</span>
				 <span class="line4">t?</span>
				 <span class="point2">
									    <input value="" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2" onkeyup="ajax_capnhat('gio_hieuluc',this.value,post_id)">
				 </span>
				 <span class="line5">gi?</span>
				 <span class="point3">
				    <input value="" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2" onkeyup="ajax_capnhat('phut_hieuluc',this.value,post_id)">
				 </span>
				 <span class="line6">phút, </span>
				 <span class="line7">ngày</span>
				 <span class="point4">
				    <input value="28" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
				 </span>
				 <span class="line8">tháng</span>
				 <span class="point5">
				    <input value="05" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 40px;" maxlength="2">
				 </span>
				 <span class="line9">nam</span>
				 <span class="point6">
				    <input value="2016" style="border: 0px;background:transparent;position: absolute; text-align:left; height: 20px; top: 4px; width: 60px;" maxlength="4">
					&nbsp;					
				 </span>
				 <span class="line10">và du?c Trung tâm Ðang ký</span>
				 <span class="line11">giao d?ch, tài s?n g?i kèm theo Gi?y ch?ng nh?n (</span>
				 <span class="point7">
				    <input style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 30px;" onkeyup="ajax_capnhat('sotrang_kemtheo',this.value,post_id)" value="0" maxlength="4">
				 </span>
				 <span class="line12">trang).</span>
             </div>
			 
			 
			 
			 <div class="sec_wrapper4" style="margin-top:-0px;">
			     <span class="line1"><strong>2.&nbsp;</strong>Ngu?i yêu c?u dang ký (ngu?i có trách nhi?m thông báo vi?c kê biên):</span>
				 <span class="point1"></span>
				 <span class="point2">
				     <textarea rows="1" id="bennhanbaodam0" onkeyup="CapNhatDon('bennhanbaodam')" style="height: 43px; font-weight: bold; font-size: 16px; color: rgb(51, 51, 51); position: absolute; right: 0px;">NGÂN HÀNG TMCP K? THUONG VI?T NAM TRUNG TÂM GIAO D?CH H?I S? </textarea>
				 </span>
				 <span class="point14">
				        
				 </span>
				 <span class="line2">- Mã s? KHTX (n?u có):</span>
				 <span class="point3">
				     <input value="" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align:left; width: 425px;" type="text">
				 </span>
				 <span class="line3">- Ð?a ch?:</span>
				 <span class="point4">
				      <textarea rows="1" id="diachi0" onkeyup="CapNhatDon('bennhanbaodam')" onclick="setCaretPosition(document.getElementById('diachi'+0),27)" onkeypress="setCaretPosition(document.getElementById('diachi'+0),27)" style="height: 43px; font-weight: bold; font-size: 16px; color: rgb(51, 51, 51); position: absolute; right: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 191 bà tri?u, Hai Bà Trung, Hà N?i, Vi?t Nam</textarea>
				 </span>
				 <span class="point5">
				 </span>
			</div>
			
						<div class="sec_wrapper4" style="margin-top:-220px;">	 
				 <span class="line4"><strong>3.1&nbsp;</strong>Bên b?o d?m:</span>
				 <span class="point6">
				     <div style="position:absolute;top:3px;font-size:16px;left:0;width:495px;height:20px;text-align:left;overflow:hidden;">
					    <input name="benbaodam" id="benbaodam0" onkeyup="CapNhatDon('benbaodam')" value="Vuong Tu?n Anh " style="border: 1px;background:transparent;position:absolute;font-weight: bold;z-index:100;height: 20px;left:5px; width: 495px; text-align:left;" type="text">
					 </div>
				 </span>
				 <span class="line5">;</span>
				 <span class="line6">Gi?y t? Ch?ng minh tu cách pháp lý s?</span>
				 <span class="point7">
				     <input name="cmnd" id="cmnd0" onkeyup="CapNhatDon('benbaodam')" value="  " style="border: 1px;background:transparent;position: absolute;z-index:100;height: 20px; top: 4px; width: 165px; text-align:center;" type="text">
				 </span>
				 <span class="line7">do</span>
				 <span class="point8">
				     <input name="do_dong1" id="do_dong1_0" onkeyup="CapNhatDon('benbaodam')" value="  " style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align:left; width: 165px;" type="text">
				 </span>
				 <span class="point9">
				     <input value="" name="do_dong2" id="do_dong2_0" onkeyup="CapNhatDon('benbaodam')" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align: left; width: 445px;">
				 </span>
				 <span class="line8">c?p ngày</span>
				 <span class="point10">
				     <input value="  " name="capngay" id="capngay_0" onkeyup="CapNhatDon('benbaodam')" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align: center; width: 135px;">
				 </span>	 
			</div>	 	
						<div class="sec_wrapper4" style="margin-top:-145px;">	 
				 <span class="line4"><strong>3.2&nbsp;</strong>Bên b?o d?m:</span>
				 <span class="point6">
				     <div style="position:absolute;top:3px;font-size:16px;left:0;width:495px;height:20px;text-align:left;overflow:hidden;">
					    <input name="benbaodam" id="benbaodam1" onkeyup="CapNhatDon('benbaodam')" value=" Duong Th? Bích H?nh " style="border: 1px;background:transparent;position:absolute;font-weight: bold;z-index:100;height: 20px;left:5px; width: 495px; text-align:left;" type="text">
					 </div>
				 </span>
				 <span class="line5">;</span>
				 <span class="line6">Gi?y t? Ch?ng minh tu cách pháp lý s?</span>
				 <span class="point7">
				     <input name="cmnd" id="cmnd1" onkeyup="CapNhatDon('benbaodam')" value="  " style="border: 1px;background:transparent;position: absolute;z-index:100;height: 20px; top: 4px; width: 165px; text-align:center;" type="text">
				 </span>
				 <span class="line7">do</span>
				 <span class="point8">
				     <input name="do_dong1" id="do_dong1_1" onkeyup="CapNhatDon('benbaodam')" value="  " style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align:left; width: 165px;" type="text">
				 </span>
				 <span class="point9">
				     <input value="" name="do_dong2" id="do_dong2_1" onkeyup="CapNhatDon('benbaodam')" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align: left; width: 445px;">
				 </span>
				 <span class="line8">c?p ngày</span>
				 <span class="point10">
				     <input value="" name="capngay" id="capngay_1" onkeyup="CapNhatDon('benbaodam')" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align: center; width: 135px;">
				 </span>	 
			</div>	 	
				 
			<div class="sec_wrapper4" style="margin-top:-210px;">		 
				 <span class="line9"><strong>4.&nbsp;</strong>Mã cá nhân:</span>
				 <span class="point11">
				     <input style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 505px;" value="">
				 </span>
			 </div>

			 
			 <div class="sec_wrapper5">
			     <span class="line1">(Ngu?i yêu c?u dang ký hoàn toàn ch?u trách nhi?m v? vi?c b?o m?t thông tin liên quan</span>
				 <span class="line2">d?n Mã cá nhân do co quan dang ký c?p).</span>
				 <span class="line3">GIÁM Ð?C</span>
				 <span class="line4">(Ký tên, dóng d?u)</span>
			 </div>
      </div>
         <div class="footer"></div>
    </div>
	

</body></html>
