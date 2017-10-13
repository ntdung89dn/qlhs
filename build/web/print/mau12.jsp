<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">	
        <link rel="stylesheet" type="text/css" href="../css/report12.css">	
        <meta charset="utf-8">
        <script src="../js/jquery.min.js"></script>
        <script src="../js/jquery-barcode.js"></script>
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
	</style>
        <script>
            $(document).ready(function() {
                var d = new Date();
                var n = d.getDate();
                var m = d.getMonth()+1;
                var y = d.getFullYear();
                function loadDay(){
                  $('#thisday').val(n);
                  $('#thismonth').val(m);
                  $('#thisyear').val(y);
                  
                };
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
                     $("#bc_print").barcode(id, "code11",{barWidth:2, barHeight:30});     
                    console.log(id);
                    $.ajax({
                                url : "../LoadDonAjax",
                                type : "POST",
                                data : {"action":"printdon",'id':id},
                                dataType: 'json',
                                success : function(data) {
                                    $('#gionhan').val(data.gionhan);
                                    $('#phutnhan').val(data.phutnhan);
                                    var ngaynhan = data.ngaynhan.split("-");
                                    $('#ngaynhan').val(ngaynhan[0]);
                                    $('#thangnhan').val(ngaynhan[1]);
                                    $('#namnhan').val(ngaynhan[2]);
                                    $('#ngayhl').val(ngaynhan[0]);
                                    $('#thanghl').val(ngaynhan[1]);
                                    $('#namhl').val(ngaynhan[2]);
                                    $('#dononline').html(data.dononline);
                                    console.log(data.dononline);
                                    $('#manhan').html(data.manhan);
                                    $('#mapin').val(data.mapin);
                                    var bnbd = data.bnbd.split("\n");;
                                    var account = data.account.split("-");
                                    if(bnbd.length === 1){
                                        var bnbd_value = bnbd[0].split("_");
                                        var bnbd_add = ' <div id="bnbd" class="sec_wrapper4" style="margin-top:-0px;">'+
                                                                    '<span class="line1"><strong>2.&nbsp;</strong>Người yêu cầu đăng ký (người có trách nhiệm thông báo việc kê biên):</span>'+
                                                                        '<span class="point1"></span>'+
                                                                        '<span class="point2">'+
                                                                            '<textarea rows="1" id="bennhanbaodam0"  style="height: 43px; font-weight: bold; font-size: 16px; color: rgb(51, 51, 51); position: absolute; right: 0px;">'+bnbd_value[0]+'</textarea>'+
                                                                        '</span>'+
                                                                        '<span class="point14">'+

                                                                       ' </span>'+
                                                                        '<span class="line2">- Mã số KHTX (nếu có):</span>'+
                                                                        '<span class="point3">'+
                                                                            '<input value="'+account[0]+'" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align:left; width: 425px;" type="text">'+
                                                                        '</span>'+
                                                                        '<span class="line3">- Địa chỉ:</span>'+
                                                                        '<span class="point4">'+
                                                                             '<textarea rows="1" id="diachi0" style="height: 43px; font-weight: bold; font-size: 16px; color: rgb(51, 51, 51); position: absolute; right: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+bnbd_value[1]+'</textarea>'+
                                                                       ' </span>'+
                                                                        '<span class="point5">'+
                                                                        '</span>'+
                                                               '</div>';
                                                       $('#formbnbd').append(bnbd_add);
                                    }else{
                                       var bnbd_add = '';
                                        $.each(bnbd,function(index,value){
                                            var bnbd_value = value.split("_");
                                             bnbd_add += ' <div id="bnbd" class="sec_wrapper4" style="margin-top:-'+(index*70)+'px;">'+
                                                                    '<span class="line1"><strong>2.&nbsp;</strong>Người yêu cầu đăng ký (người có trách nhiệm thông báo việc kê biên):</span>'+
                                                                        '<span class="point1"></span>'+
                                                                        '<span class="point2">'+
                                                                            '<textarea rows="1" id="bennhanbaodam0"  style="height: 43px; font-weight: bold; font-size: 16px; color: rgb(51, 51, 51); position: absolute; right: 0px;">'+bnbd_value[0]+'</textarea>'+
                                                                        '</span>'+
                                                                        '<span class="point14">'+

                                                                       ' </span>'+
                                                                        '<span class="line2">- Mã số KHTX (nếu có):</span>'+
                                                                        '<span class="point3">'+
                                                                            '<input value="'+account[0]+'" style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align:left; width: 425px;" type="text">'+
                                                                        '</span>'+
                                                                        '<span class="line3">- Địa chỉ:</span>'+
                                                                        '<span class="point4">'+
                                                                             '<textarea rows="1" id="diachi0" style="height: 43px; font-weight: bold; font-size: 16px; color: rgb(51, 51, 51); position: absolute; right: 0px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+bnbd_value[1]+'</textarea>'+
                                                                       ' </span>'+
                                                                        '<span class="point5">'+
                                                                        '</span>'+
                                                               '</div>';
                                                
                                        });
                                        $('#formbnbd').append(bnbd_add);
                                    }
                                    var bbd = data.benbaodam.split("\\n");
                                    console.log(bbd);
                                    if(bbd.length === 1){
                                        var bbd_value = bbd[0].split("_");
                                        var bbd_name = checkBBDLength(bbd_value[0]);
                                        var bbd_add = '<div id="bbd_div" class="sec_wrapper4" style="margin-top:-220px;"><span class="line4"><strong>3&nbsp;</strong>Bên bảo đảm:</span>'+
                                                                    '<span class="point6">'+
                                                                        '<div style="position:absolute;top:3px;font-size:16px;left:0;width:495px;height:20px;text-align:left;overflow:hidden;">'+
                                                                            '<input name="benbaodam" id="benbaodam0"  value="'+bbd_name+'" style="border: 1px;background:transparent;position:absolute;font-weight: bold;z-index:100;height: 20px;left:5px; width: 495px; text-align:left;" type="text">'+
                                                                       ' </div>'+
                                                                    '</span><span class="line5">;</span>'+
                                                                    '<span class="line6">Giấy tờ Chứng minh tư cách pháp lý số</span>'+
                                                                    '<span class="point7">'+
                                                                       ' <input name="cmnd" id="cmnd0"  value="'+bbd_value[1]+'" style="border: 1px;background:transparent;position: absolute;z-index:100;height: 20px; top: 4px; width: 165px; text-align:center;" type="text">'+
                                                                    '</span>'+
                                                                    '<span class="line7">do</span>'+
                                                                    '<span class="point8">'+
                                                                        '<input name="do_dong1" id="do_dong1_0"  value="  " style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align:left; width: 165px;" type="text">'+
                                                                    '</span>'+
                                                                    '<span class="point9">'+
                                                                        '<input value="" name="do_dong2" id="do_dong2_0"  style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align: left; width: 445px;">'+
                                                                    '</span>'+
                                                                    '<span class="line8">cấp ngày</span>'+
                                                                    '<span class="point10">'+
                                                                        '<input value="  " name="capngay" id="capngay_0"  style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align: center; width: 135px;">'+
                                                                    '</span></div>';
                                                            $('#formbbd').append(bbd_add);
                                    }else{
                                        var bbd_add = '';

                                        $.each(bbd,function(index,value){
                                            bbd_value = value.split(",");
                                            var bbd_name = checkBBDLength(bbd_value[0]);
                                            bbd_add += '<div id="bbd_div'+index+'" class="sec_wrapper4" style="margin-top:-'+(220-(index*75))+'px;"><span class="line4"><strong>3.'+(index+1)+'&nbsp;</strong>Bên bảo đảm:</span>'+
                                                                    '<span class="point6">'+
                                                                        '<div style="position:absolute;top:3px;font-size:16px;left:0;width:495px;height:20px;text-align:left;overflow:hidden;">'+
                                                                            '<input name="benbaodam" id="benbaodam0"  value="'+bbd_name+'" style="border: 1px;background:transparent;position:absolute;font-weight: bold;z-index:100;height: 20px;left:5px; width: 495px; text-align:left;" type="text">'+
                                                                       ' </div>'+
                                                                    '</span><span class="line5">;</span>'+
                                                                    '<span class="line6">Giấy tờ Chứng minh tư cách pháp lý số</span>'+
                                                                    '<span class="point7">'+
                                                                       ' <input name="cmnd" id="cmnd0"  value="'+bbd_value[1]+'" style="border: 1px;background:transparent;position: absolute;z-index:100;height: 20px; top: 4px; width: 165px; text-align:center;" type="text">'+
                                                                    '</span>'+
                                                                    '<span class="line7">do</span>'+
                                                                    '<span class="point8">'+
                                                                        '<input name="do_dong1" id="do_dong1_0"  value="  " style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align:left; width: 165px;" type="text">'+
                                                                    '</span>'+
                                                                    '<span class="point9">'+
                                                                        '<input value="" name="do_dong2" id="do_dong2_0"  style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align: left; width: 445px;">'+
                                                                    '</span>'+
                                                                    '<span class="line8">cấp ngày</span>'+
                                                                    '<span class="point10">'+
                                                                        '<input value="  " name="capngay" id="capngay_0"  style="border: 0px;background:transparent;position: absolute;z-index:100; height: 20px; top: 4px; text-align: center; width: 135px;">'+
                                                                    '</span></div>';
                                                
                                        });
                                        $('#formbbd').append(bbd_add);
                                    }
                                    
                                }
                        });
                };
                var checkBBDLength = function(bbd){
                    console.log("BBD LENGTH = "+bbd.length);
                    var bbdname = "";
                    if(bbd.length > 60){
                        var indexCT = bbd.toLowerCase().indexOf("công ty");
                        bbdname = bbd.substring(0, indexCT)+"CT"+bbd.substring(indexCT +"công ty".length );
                        
                        var indexTNHH = bbdname.toLowerCase().indexOf("trách nhiệm hữu hạn");
                        bbdname = bbdname.substring(0, indexTNHH)+"TNHH"+bbdname.substring(indexTNHH +"trách nhiệm hữu hạn".length );

                        var indexMTV = bbdname.toLowerCase().indexOf("một thành viên");
                        bbdname = bbdname.substring(0, indexMTV)+"MTV"+bbdname.substring(indexMTV +"một thành viên".length );
                    }
                    if(bbdname.length > 60){
                        var indexCT = bbdname.toLowerCase().indexOf("công ty");
                        bbdname = bbd.substring(0, indexCT)+"CT"+bbd.substring(indexCT +"công ty".length );
                        
                        var indexTNHH = bbdname.toLowerCase().indexOf("trách nhiệm hữu hạn");
                        bbdname = bbdname.substring(0, indexTNHH)+"TNHH"+bbdname.substring(indexTNHH +"trách nhiệm hữu hạn".length );

                        var indexMTV = bbdname.toLowerCase().indexOf("một thành viên");
                        bbdname = bbdname.substring(0, indexMTV)+"MTV"+bbdname.substring(indexMTV +"một thành viên".length );
                        
                        var indexXD = bbdname.toLowerCase().indexOf("xây dựng");
                        bbdname = bbdname.substring(0, indexXD)+"XD"+bbdname.substring(indexXD +"xây dựng".length );
                        
                        var indexCP = bbdname.toLowerCase().indexOf("cổ phần");
                        bbdname = bbdname.substring(0, indexCP)+"CP"+bbdname.substring(indexCP +"cổ phần".length );
                        
                        var indexVT = bbdname.toLowerCase().indexOf("vận tải");
                        bbdname = bbdname.substring(0, indexVT)+"VT"+bbdname.substring(indexVT +"vận tải".length );
                        
                        var indexVT = bbdname.toLowerCase().indexOf("thương mại");
                        bbdname = bbdname.substring(0, indexVT)+"TM"+bbdname.substring(indexVT +"thương mại".length );
                        
                        var indexVT = bbdname.toLowerCase().indexOf("dịch vụ");
                        bbdname = bbdname.substring(0, indexVT)+"DV"+bbdname.substring(indexVT +"dịch vụ".length );
                        
                        var indexVT = bbdname.toLowerCase().indexOf(" - ");
                        bbdname = bbdname.substring(0, indexVT)+"-"+bbdname.substring(indexVT +" - ".length );
                    }
                    return bbdname;
                };
                var updateTTDon = function(){
                    var gio = 0;
                    var phut =0;
                    $.ajax({
                            url : "../LoadDonAjax",
                            type : "POST",
                            data : {"action":"hieuluc","gio":gio,"phut":phut},
                            dataType: 'json',
                            success : function(data) {
                                
                                
                            }
                    });
                };
                loadTTDon();
                loadDay();
            });
        </script>

</head>
<body>
       	<div class="wrapper">
         <div class="header">
             <div class="temp">Mẫu số 12</div>
             <div class="logo"> 
                 <img src="./image/logo.PNG" alt="Logo">
             </div>
             <div class="chxh">
                CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM
             </div>
             <div class="dltd">
                 <u> Độc lập - Tự do - Hạnh phúc</u>
             </div>
             <div class="date">
                 <span class="point1">
                     <input value="Đà Nẵng" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width:80px;" maxlength="2">
                     </span>
                     <span class="line1">, ngày</span>
                     <span class="point2">
                          <input id="thisday" value="" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
                     </span>
                     <span class="line2">tháng</span>
                 <span class="point3">
                         <input value="" id="thismonth" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
                     </span>
                     <span class="line3">năm</span>
                     <span class="point4">
                          <input value=""id="thisyear"  style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="4">
                     </span>
             </div>
             <div class="title">
                 <span style="margin-left:40px;">GIẤY CHỨNG NHẬN ĐĂNG KÝ GIAO DỊCH BẢO ĐẢM,</span><br>
                 <span>HỢP ĐỒNG, THÔNG BÁO VIỆC KÊ BIÊN TÀI SẢN THI HÀNH ÁN</span>
             </div>
             <span class="ghichu">(Ban hành kèm theo Thông tư số 05/2011/TT-BTP ngày 16/02/2011 của bộ Tư pháp)</span>
         </div>
      <div class="content">
  		

             <div class="sec_wrapper1">
                 <span class="line1">Theo Đơn yêu cầu đăng ký, văn bản thông báo số:</span>
                 <span class="point1">
                     <div id="manhan"style="position:absolute;top:3px;font-size:16px;left:0;width:185px;height:20px;text-align:center;overflow:hidden;">
                                    </div>
				 </span>
                 <span class="line2">được</span>
                 <span class="line3">Trung tâm Đăng ký giao dịch, tài sản tiếp nhận vào</span>
                 <span class="point2">
                                    <input value="" id="gionhan" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:30px;height:20px;text-align:center;overflow:hidden;" maxlength="2">
                             </span>
                 <span class="line4">giờ</span>
                 <span class="point3">
                     <input value="" id="phutnhan" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:25px;height:20px;text-align:center;overflow:hidden;" maxlength="2">
				 </span>
                 <span class="line5">phút, </span>
                 <span class="line6">ngày</span>
                 <span class="point4">
                     <input value="" id="ngaynhan" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:95px;height:20px;text-align:center;overflow:hidden;" maxlength="2">
                             </span>
                 <span class="line7">tháng</span>
                 <span class="point5">
                     <input value="05" id="thangnhan" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:95px;height:20px;text-align:center;overflow:hidden;" maxlength="2">
                             </span>
                 <span class="line8">năm</span>
                 <span class="point6">
                     <input value="" id="namnhan" style="border: 0px;background:transparent;position:absolute;top:3px;left:0;width:95px;height:20px;text-align:center;overflow:hidden;" maxlength="4">
                             </span>
                    </div>
	

             <div class="sec_wrapper2">
                 <span class="line1" style="text-align:center">TRUNG TÂM ĐĂNG KÝ GIAO DỊCH, TÀI SẢN TẠI ĐÀ NẴNG</span>
                 <span class="line2">CHỨNG NHẬN</span>
             </div>
             <div class="sec_wrapper3">
                 <span class="line1"><strong>1.&nbsp;</strong>Nội dung đăng ký, thông báo việc kê biên đã được cập nhật vào Cơ sở dữ liệu</span>
                 <span class="line2">Về giao dịch bảo đảm, có số đăng ký là</span>
                 <span class="point1">
                     <div style="position:absolute;top:3px;font-size:16px;left:0;width:205px;height:20px;text-align:center;overflow:hidden;"><span id="dononline"></span></div>  
				 </span>
                 <span class="line3">, hiệu lực đăng ký</span>
                             <span class="line4">từ</span>
                             <span class="point2">
                                     <input value="" id="phuthl" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
                             </span>
                             <span class="line5">giờ</span>
                             <span class="point3">
                                <input value="" id="phuthl" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
                             </span>
                             <span class="line6">phút, </span>
                             <span class="line7">ngày</span>
                             <span class="point4">
                                <input value="" id="ngayhl" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 45px;" maxlength="2">
                             </span>
                             <span class="line8">tháng</span>
                             <span class="point5">
                                <input value="" id="thanghl" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 40px;" maxlength="2">
                             </span>
                             <span class="line9">năm</span>
                             <span class="point6">
                                <input value="" id="namhl" style="border: 0px;background:transparent;position: absolute; text-align:left; height: 20px; top: 4px; width: 60px;" maxlength="4">
                                    &nbsp;					
                             </span>
                             <span class="line10">và được Trung tâm Đăng ký</span>
                             <span class="line11">giao dịch, tài sản gửi kèm theo Giấy chứng nhận (</span>
                             <span class="point7">
                                <input style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 30px;" onkeyup="ajax_capnhat('sotrang_kemtheo',this.value,post_id)" value="0" maxlength="4">
                             </span>
                             <span class="line12">trang).</span>
             </div>
			 
			 
			 
                    
          <div id="formbnbd" ></div>
          <div id="formbbd" ></div>



                    <div class="sec_wrapper4" style="margin-top:-210px;">		 
                             <span class="line9"><strong>4.&nbsp;</strong>Mã cá nhân:</span>
                             <span class="point11">
                                 <input id="mapin" style="border: 0px;background:transparent;position: absolute; height: 20px; top: 4px; text-align: center; width: 505px;" value="">
                             </span>
                     </div>


                     <div class="sec_wrapper5">
                         
                         <span class="line1">(Người yêu cầu đăng ký hoàn toàn chịu trách nhiệm về việc bảo mật thông tin liên quan</span>
                             <span class="line2">đến Mã cá nhân do cơ quan đăng ký cấp).</span>
                             <span class="line3">GIÁM ĐỐC</span>
                             <span class="line4">(Ký tên, đóng dấu)</span>
                             <div id="bc_print" style="font-size: 20px;font-weight: bold;left: 10px;position: absolute;top: 70px;width: 630px;word-spacing: 2px;"></div>   
                             
                     </div>
      </div>
         <div class="footer"></div>
    </div>
</body></html>