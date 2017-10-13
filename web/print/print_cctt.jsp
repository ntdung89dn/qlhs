<%-- 
    Document   : print_cctt
    Created on : Mar 27, 2017, 11:29:14 PM
    Author     : ntdung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<title>
		Quan Ly Ho So  	</title>	
	<link rel="stylesheet" type="text/css" href="../css/report13.css">
	<script src="../js/jquery.min.js"></script>

    <style type="text/css">
            textarea {
                    background: none repeat scroll 0 0 transparent;
                    border: 0 none white;
                    line-height: 23px;
                    outline: medium none;
                    overflow: hidden;
                    padding: 0;
                    width: 610px;
                    z-index: 100;
                    font-family: "Times New Roman",Times,serif;
            }
    </style>
        <script>
            $(document).ready(function() {
                
                function loadDay(){
                    var d = new Date();
                    var n = d.getDate();
                    var m = d.getMonth()+1;
                    var y = d.getFullYear();
                  $('div.date').find('.point2').find('input').val(n);
                  $('div.date').find('.point3').find('input').val(m);
                  $('div.date').find('.point4').find('input').val(y);
                };
                loadDay();
                $.urlParam = function(name){
                    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
                    if (results===null){
                       return null;
                    }
                    else{
                       return results[1] || 0;
                    }
                };
                function loadTTDon(){
                    var id= $.urlParam('id');
                    console.log(id);
                    $.ajax({
                                url : "../LoadDonAjax",
                                type : "POST",
                                data : {"action":"printdon",'id':id},
                                dataType: 'json',
                                success : function(data) {
                                    console.log(data);
                                    $('#bnbd_infor').find('.point2').find('input').val(data.gionhan);
                                    $('#bnbd_infor').find('.point3').find('input').val(data.phutnhan);
                                    var ngaynhan = data.ngaynhan.split("-");
                                    $('#bnbd_infor').find('.point4').find('input').val(ngaynhan[0]);
                                    $('#bnbd_infor').find('.point5').find('input').val(ngaynhan[1]);
                                    $('#bnbd_infor').find('.point6').find('input').val(ngaynhan[2]);
                                    
                                    $('.sec_wrapper4').find('.point1').find('input').val(data.gionhan);
                                    $('.sec_wrapper4').find('.point2').find('input').val(data.phutnhan);
                                    $('.sec_wrapper4').find('.point3').find('input').val(ngaynhan[0]);
                                    $('.sec_wrapper4').find('.point4').find('input').val(ngaynhan[1]);
                                    $('.sec_wrapper4').find('.point5').find('input').val(ngaynhan[2]);
                                    
                                    $('#ngayhl').val(ngaynhan[0]);
                                    $('#thanghl').val(ngaynhan[1]);
                                    $('#namhl').val(ngaynhan[2]);
                                    $('#dononline').html(data.dononline);
                                    console.log(data.dononline);
                                    $('#cctt_manhan').val(data.manhan);
                                    $('#mapin').val(data.mapin);
                                    var bnbd = data.bnbd.split("\n");
                                    $.each(bnbd,function(i,el){
                                        var bnbd_value = el.split("_");
                                        var bnbd = '<span class="line9" style="margin-top:0px;">Người yêu cầu cung cấp thông tin là:</span>'+
                             '<span class="point7" id="bnbd_name" style="margin-top:'+i*75+'px;">'+
                                '<textarea rows="1" id="bennhanbaodam"'+i+' style="height: 43px; font-weight: bold; font-size: 16px; color: rgb(51, 51, 51); position: absolute; right: 0px;" onkeyup="CapNhatDon(&#39;bennhanbaodam&#39;)" onclick="setCaretPosition(document.getElementById(&#39;bennhanbaodam&#39;+0),69)" onkeypress="setCaretPosition(document.getElementById(&#39;bennhanbaodam&#39;+0),69)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
                               bnbd_value[0] +'</textarea>'+
                             '</span>  <span class="point10" style="margin-top:0px;"></span>'+
                             '<span class="line10" style="margin-top:0px;">Đỉa chỉ liên hệ:</span>'+
                             '<span class="point8" style="margin-top:0px;">'+
                                    '<input id="diachi0" value=" '+bnbd_value[1]+'" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 520px;" onkeyup="CapNhatDon(&#39;bennhanbaodam&#39;)">'+
                               '</span>';
                                        $('#bnbd_infor').append(bnbd);
                                    });
                                    var bbd = data.benbaodam.split("\\n");
                                    console.log(bbd);
                                    var bbdAdd = '';
                                    $.each(bbd,function(i,el){
                                        var margin = 50*i;
                                        var bbd_value = el.split("_");
                                        bbdAdd += '<span class="line2">Giấy tờ xác định tư cách pháp lý của bên bảo đảm</span>'+
                                                        '<span class="line3" style="margin-top:'+margin+'px;">- Tên bên bảo đảm:</span>'+
                                                       '<span class="point2" style="margin-top:'+margin+'px;">'+
                                                              '<input value="'+bbd_value[0]+' " id="ten_bbd'+i+'" onkeyup="CapNhatDon(&#39;benbaodam&#39;)" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px;font-weight: bold; text-align: left; width: 485px;z-index:100">'+
                                                       '</span>'+
                                                       '<span class="line4" style="margin-top:'+margin+'px;">- Số:</span>'+
                                                       '<span class="point3" style="margin-top:'+margin+'px;">'+
                                                              '<input value="'+bbd_value[1]+'" id="so_bbd'+i+'" onkeyup="CapNhatDon(&#39;benbaodam&#39;)" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 120px;z-index:100">'+
                                                      '</span>'+
                                                       '<span class="line5" style="margin-top:'+margin+'px;">do</span>'+
                                                       '<span class="point4" style="margin-top:'+margin+'px;">'+
                                                      '<input value="  " id="do_bbd'+i+'" onkeyup="CapNhatDon(&#39;benbaodam&#39;)" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 230px;z-index:100">'
                                                          ' </span>'+
                                                           '<span class="line6" style="margin-top:'+margin+'px;">cấp ngày</span>'+
                                                           '<span class="point5" style="margin-top:'+margin+'px;">'+
                                                      '<input value="  " id="ngaycap_bbd0" onkeyup="CapNhatDon(&#39;benbaodam&#39;)" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 130px;z-index:100">'+
                                                        '</span>';
                                    
                                    });
                                    if((bbd.length%2) !== 0){
                                        var margin = 75+ 60*(bbd.length-1);
                                       // $('#bbd_infor').css('height:'+margin+'px !important');
                                        $('#bbd_infor').attr('style','height:'+margin+'px !important;');
                                        console.log(margin);
                                    }else{
                                        var margin = 75+ 50*(bbd.length-1);
                                    //    $('#bbd_infor').css('height',margin+'px !important');
                                        $('#bbd_infor').attr('style','height:'+margin+'px !important;');
                                        console.log(margin);
                                    }
                                    $('#bbd_infor').append(bbdAdd);
                                   // $('.sec_wrapper3')
                                   if(( bbd.length %2) !== 0){
                                       var margin = 113+50* (bbd.length-1);
                                       var skInfor = '<img src="image/uncheck.png" class="point6" name-value="0" id="chonSK" style="top:'+margin+'px;border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; width:20px;">'+
				 '<span class="line7" style="top:'+margin+'px;">Phương tiện giao thông cơ giới có số khung:</span>'+
				 '<span class="point7" style="top:'+margin+'px;">'+
                                                            '<input value="" id="sokhung" readonly="true" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 275px;z-index:100">'+
				' </span>';
                                        var kbInfor = '<img src="image/uncheck.png" class="point8" name-value="0" id="chonKB" style="top:'+(margin+30)+'px;border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; width:20px;">'+
				 '<span class="line8" style="top:'+(margin+30)+'px;">Số đăng ký giao dịch bảo đảm, hợp đồng, thông báo việc kê biên thi hành án:</span>'+
				 '<span class="point10" style="top:'+(margin+50)+'px;">'+
                     '<input value="" id="kebien" readonly="true" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 638px;z-index:100">'+
				 '</span>';
                                       $('body #bbd_infor').append(skInfor);
                                       $('body #bbd_infor').append(kbInfor);
                                   }else{
                                       var margin = 113+60* (bbd.length-1);
                                       var skInfor = '<img src="image/uncheck.png" class="point6" name-value="0" id="chonSK" style="top:'+margin+'px;border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; width:20px;">'+
				 '<span class="line7" style="top:283px;">Phương tiện giao thông cơ giới có số khung:</span>'+
				 '<span class="point7" style="top:279px;">'+
                                                        '<input value="" id="sokhung" readonly="true" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 275px;z-index:100">'+
				' </span>';
                                        var kbInfor = '<img src="image/uncheck.png" class="point8" name-value="0" id="chonKB" style="top:'+(margin+30)+'px;border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; width:20px;">'+
				 '<span class="line8" style="top:'+(margin+30)+'px;">Số đăng ký giao dịch bảo đảm, hợp đồng, thông báo việc kê biên thi hành án:</span>'+
				 '<span class="point10" style="top:'+(margin+50)+'px;">'+
                     '<input value="" id="kebien" readonly="true" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 638px;z-index:100">'+
				 '</span>';
                                         $('body #bbd_infor').append(skInfor);
                                         $('body #bbd_infor').append(kbInfor);
                                   }
                                  
                                }
                        });
                };
                loadTTDon();
            });
        </script>
</head>
<body>
       	<div class="wrapper">
         <div class="header">
             <div class="temp"> Mẫu số 13 </div>
             <div class="logo"> 
                 <img src="./image/logo.PNG" alt="Logo">
             </div>
             <div class="chxh">
                 CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM
             </div>
             <div class="dltd">
                 <u> Độc lập - Tự do - Hạnh phúc </u>
             </div>
             <div class="date">
                 <span class="point1">
                     <input value="Đà Nẵng" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width:80px;" maxlength="2">
                 </span>
                 <span class="line1" style="font-size: 14px;">, ngày</span>
                 <span class="point2">
                      <input value="27" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
                 </span>
                 <span class="line2">tháng</span>
                <span class="point3">
                     <input value="03" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
                 </span>
                 <span class="line3">năm</span>
                 <span class="point4">
                      <input value="2017" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="4">
                 </span>
             </div>
             <div class="title">
                 <span style="margin-left:40px;">VĂN BẢN CUNG CẤP THÔNG TIN VỀ GIAO DỊCH BẢO ĐẢM,</span><br>
                 <span style="margin-left:20px;">HỢP ĐỒNG, THÔNG BÁO VIỆC KÊ BIÊN TÀI SẢN THI HÀNH ÁN</span>
             </div>
             <span class="ghichu">(Ban hành kèm theo Thông tư số 05/2011/TT-BTP ngày 16/02/2011 của bộ Tư pháp)</span>
         </div>
      <div class="content">
	                       <div class="sec_wrapper1" id="bnbd_infor" style="height:113px !important;">
                 <span class="line1">Theo Đơn(văn bản)yêu cầu cung cấp thông tin có số thứ tự tiếp nhận:</span>
                 <span class="point1">
                        <input type="text" value="" id="cctt_manhan" style=" width:70px; border:0; margin-top: 4px; padding:0px; " >
				 						
				  
				 </span>
             	 <span class="point9">
				 					 						
				 </span>
                 <span class="line3">và thời điểm tiếp nhận là</span>
                 <span class="point2">
				     <input value="13" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 30px;" maxlength="2">
				 </span>
                 <span class="line4">giờ</span>
                 <span class="point3">
                    <input value="50" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 30px;" maxlength="2">
				 </span>
                 <span class="line5">phút,</span>
                 <span class="line6">ngày</span>
                 <span class="point4">
                    <input value="23" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 65px;" maxlength="2">
				 </span>
                 <span class="line7">tháng</span>
                 <span class="point5">
                    <input value="06" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 65px;" maxlength="2">
				 </span>
                 <span class="line8">năm</span>
                 <span class="point6">
                            <input value="2016" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 105px;" maxlength="4">
                 </span>
                             <!--<span class="line9" style="margin-top:0px;">Người yêu cầu cung cấp thông tin là:</span>
                             <span class="point7" id="bnbd_name" style="margin-top:0px;">
                                <textarea rows="1" id="bennhanbaodam0" style="height: 43px; font-weight: bold; font-size: 16px; color: rgb(51, 51, 51); position: absolute; right: 0px;" onkeyup="CapNhatDon(&#39;bennhanbaodam&#39;)" onclick="setCaretPosition(document.getElementById(&#39;bennhanbaodam&#39;+0),69)" onkeypress="setCaretPosition(document.getElementById(&#39;bennhanbaodam&#39;+0),69)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ngân Hàng TMCP Sài Gòn Thương Tín Chi Nhánh Bình Phước Phòng Giao Dịch Chơn Thành </textarea>
                             </span>
                             <span class="point10" style="margin-top:0px;">

                             </span>
                             <span class="line10" style="margin-top:0px;">Đỉa chỉ liên hệ:</span>
                             <span class="point8" style="margin-top:0px;">
                                    <input id="diachi0" value=" Tổ 02, KP 04, TT Chơn Thành, Huyện Chơn Thành, Tỉnh Bình Phước" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 520px;" onkeyup="CapNhatDon(&#39;bennhanbaodam&#39;)">
                               </span> -->
				 	
                  </div>
             <div class="sec_wrapper2">
                 <span class="line1" style="text-align:center">TRUNG TÂM ĐĂNG KÝ GIAO DỊCH, TÀI SẢN TẠI ĐÀ NẴNG</span>
                 <span class="line2">CHỨNG NHẬN</span>
             </div>
                          <div class="sec_wrapper3"  id='bbd_infor'>
                 <span class="line1">1.Việc tra cứu thông tin được thực hiện theo tiêu chí sau đây:</span>
                    <!-- <span class="point1">style="height:245px !important;"
                         <input type="input"  value="" onclick="KiemTraBBD()" id="chon_bbd" maxlength="1" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; text-align:center; width:20px;font-weight:bold;text-transform:uppercase;">

                     </span> -->
                    <img src="image/uncheck.png" id="checkBBD" class="point1" name-value="0" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; width:20px;">
                    <!-- <span class="line2">Giấy tờ xác định tư cách pháp lý của bên bảo đảm</span>
                      <span class="line3" style="margin-top:0px;">- Tên bên bảo đảm:</span>
                     <span class="point2" style="margin-top:0px;">
                            <input value="1185948280 " id="ten_bbd0" onkeyup="CapNhatDon(&#39;benbaodam&#39;)" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px;font-weight: bold; text-align: left; width: 485px;z-index:100">
                     </span>
                     <span class="line4" style="margin-top:0px;">- Số:</span>
                     <span class="point3" style="margin-top:0px;">
                            <input value="  " id="so_bbd0" onkeyup="CapNhatDon(&#39;benbaodam&#39;)" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 120px;z-index:100">
                    </span>
                     <span class="line5" style="margin-top:0px;">do</span>
                     <span class="point4" style="margin-top:0px;">
                    <input value="  " id="do_bbd0" onkeyup="CapNhatDon(&#39;benbaodam&#39;)" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 230px;z-index:100">
				 </span>
				 <span class="line6" style="margin-top:0px;">cấp ngày</span>
				 <span class="point5" style="margin-top:0px;">
                    <input value="  " id="ngaycap_bbd0" onkeyup="CapNhatDon(&#39;benbaodam&#39;)" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 130px;z-index:100">
				 </span>



                                                                     <!--<span class="point6" style="top:283px;">
				     <input type="input" id="chonSK" value="" onclick="KiemTraSK()" maxlength="1" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; text-align:center; width:20px;font-weight:bold;text-transform:uppercase;">
				 </span> 
                                                                        <img src="image/uncheck.png" class="point6" name-value="0" id="chonSK" style="top:283px;border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; width:20px;">
				 <span class="line7" style="top:283px;">Phương tiện giao thông cơ giới có số khung:</span>
				 <span class="point7" style="top:279px;">
                 	 <input value="" id="sokhung" readonly="true" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 275px;z-index:100">
				 </span>-->
				<!-- <span class="point8" style="top:313px;">
				     <input type="input" id="chonKB" value="" onclick="KiemTraKB()" maxlength="1" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; text-align:center; width:20px;font-weight:bold;text-transform:uppercase;">
				 </span>
                                <img src="image/uncheck.png" class="point8" name-value="0" id="chonKB" style="top:313px;border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; width:20px;">
				 <span class="line8" style="top:313px;">Số đăng ký giao dịch bảo đảm, hợp đồng, thông báo việc kê biên thi hành án:</span>
				 <span class="point10" style="top:333px;">
                     <input value="" id="kebien" readonly="true" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: left; width: 638px;z-index:100">
				 </span> -->
                     </div>
                     <div class="sec_wrapper4">
                             <span class="line1"><strong>2.</strong>Thông tin về giao dịch bảo đảm, hợp đồng, thông báo việc kê biên tài sản thi </span>
                             <span class="line2">hành án tra cứu lúc</span>
                             <span class="point1">
                                <input value="13" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 30px;z-index:100">
                             </span>
                             <span class="line3">giờ</span>
                             <span class="point2">
                                    <input value="50" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 30px;z-index:100">
                             </span>
                             <span class="line4">phút, ngày</span>
                             <span class="point3">
                                    <input value="23" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 65px;z-index:100">
                             </span>
                             <span class="line5">tháng</span>
                             <span class="point4">
                                <input value="06" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 65px;z-index:100">
                             </span>
                             <span class="line6">năm</span>
                             <span class="point5">
                                <input value="2016" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 65px;z-index:100">
                             </span>
                             <span class="line7">(gồm</span>
                             <span class="point6">
                                <input value="" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 30px;z-index:100">
                             </span>
                             <span class="line8">trang).</span>
                     </div>
                     <div class="sec_wrapper5">
                             <span class="line3">GIÁM ĐỐC</span>
                             <span class="line4">(Ký tên, đóng dấu)</span>
                     </div>
      </div>
         <div class="footer"></div>
    </div>
    <script>
        var KiemTraBBD = function(){
            var check = $(this).attr("name-value");
            console.log(check);
            if(check ==0){
                 $(this).attr("src","image/check.png");
                 $(this).attr("name-value",1);
                 $('input[id^="ten_bbd"]').prop('readonly', false);
                 $('input[id^="so_bbd"]').prop('readonly', false);
                 $('input[id^="do_bbd"]').prop('readonly', false);
            }else{
                $(this).attr("src","image/uncheck.png");
                $(this).attr("name-value",0);
                $('input[id^="ten_bbd"]').prop('readonly', true);
                 $('input[id^="so_bbd"]').prop('readonly', true);
                 $('input[id^="do_bbd"]').prop('readonly', true);
            }
           
        };
        
        var KiemTraSK = function(){
            var check = $(this).attr("name-value");
            console.log(check);
            if(check ==0){
                 $(this).attr("src","image/check.png");
                 $(this).attr("name-value",1);
                 $('#sokhung').prop('readonly', false);
            }else{
                $(this).attr("src","image/uncheck.png");
                $(this).attr("name-value",0);
                $('#sokhung').prop('readonly', true);
            }
           
        };
        
        var KiemTraKB = function(){
            var check = $(this).attr("name-value");
            console.log(check);
            if(check ==0){
                 $(this).attr("src","image/check.png");
                 $(this).attr("name-value",1);
                 $('#kebien').prop('readonly', false);
            }else{
                $(this).attr("src","image/uncheck.png");
                $(this).attr("name-value",0);
                $('#kebien').prop('readonly', true);
            }
           
        };
        $('#checkBBD').on('click',KiemTraBBD);
      //  $('#chonSK').on('click',KiemTraSK);
      //  $('#chonKB').on('click',KiemTraKB);
      $(document).on('click','#chonSK',KiemTraSK);
      $(document).on('click','#chonKB',KiemTraKB);
    </script>
</body></html>
