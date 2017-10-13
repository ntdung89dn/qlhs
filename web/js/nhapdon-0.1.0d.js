/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global $pagination, defaultOpts */

var days = 10;
var date = new Date();
 var last = new Date(date.getTime() - (days * 24 * 60 * 60 * 1000));
$( "#s_ngaynhap" ).datepicker({
    dateFormat: 'dd-mm-yy',          
    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
        "Tháng 10", "Tháng 11", "Tháng 12" ],
    dayNamesMin: ["CN","2","3","4","5","6","7"],
    changeMonth: true,
    changeYear: true
}).datepicker("setDate", last);
var searchList = [];
function searchDon(){
       var s_ngaynhap = $("#s_ngaynhap").val().trim();
       var s_dononline = $("#s_dononline").val().trim();
       var s_loaidon = $("#s_loaidon").val().trim();
       var s_hinhnhan = $("#s_hinhnhan").val().trim();
       var s_maloainhan = $("#s_maloainhan").val().trim();  
       var s_nhanbaodam = $("#s_nhanbaodam").val().trim();
       var s_nhanbaodamtt = $("#s_nhanbaodamtt").val().trim();
       var s_benbaodam = $("#s_benbaodam").val().trim();
       searchList.splice(0,searchList.length);
       searchList.push(s_ngaynhap);  searchList.push(s_dononline);  searchList.push(s_loaidon);
         searchList.push(s_hinhnhan);  searchList.push(s_maloainhan);  searchList.push(s_nhanbaodam);
         searchList.push(s_nhanbaodamtt);searchList.push(s_benbaodam);
         $('#div_loading').html("<img src='./images/loading.gif'>");
       $.ajax({
                type: "GET",
                url:"LoadDonAjax",
                dataType : "json",
                data:{"page":1,"action":"search","ngaynhap":s_ngaynhap,"maonline":s_dononline,"loaidon":s_loaidon,
                "loainhan":s_hinhnhan,"manhan":s_maloainhan,"bnbd":s_nhanbaodam,"btp":s_nhanbaodamtt,"bbd":s_benbaodam},
                success: function (data) { 
                    $('#div_loading').empty();
                    console.log(data);
                    var table = data.data;
                    var currentpage = data.curretpage;
                    var totalpage = data.totalpage;
                    $("#nhapdontable tbody").html(table);
                  //  $('#ul_page').hide();
                    $pagination.twbsPagination('destroy');
                  //  loadPageSearch(currentpage);
                    $pagination.twbsPagination($.extend({}, defaultOpts, {
                            startPage: currentpage,
                            totalPages: totalpage,
                            onPageClick: function (event, page) {
                                //console.log("PAGE LOAD "+event);
                                $.ajax({
                                        type: "GET",
                                        url:"LoadDonAjax",
                                        dataType : "json",
                                        data:{"page":page,"action":"search","ngaynhap":searchList[0],"maonline":searchList[1],"loaidon":searchList[2],
                                        "loainhan":searchList[3],"manhan":searchList[4],"bnbd":searchList[5],"btp":searchList[6],"bbd":searchList[7]},
                                        success: function (data) { 
                                            $("#nhapdontable tbody").empty();
                                            $("#nhapdontable tbody").html(data.data); 
                                        }

                               }); 
                            }
                        }));
                }

            }); 
      /// alert("XXXXX");
    };
$( "#dialog-confirm" ).dialog({
        autoOpen: false,
        resizable: false,
        height: "auto",
        width: 400,
        modal: true,
        buttons: {
          "Xóa đơn": function() {
              $.ajax({
                    type: "GET",
                    url:"LoadDonAjax",
                    data:{"action":"deletedon","donid":$('#del_donid').val()},
                    success: function (data) {
                       if(data ==='OK'){
                           $('#nhapdontable').find('.img_delete_don').each(function(){
                               console.log("ID = "+$(this).prop("id"));
                               console.log("del_donid = "+$('#del_donid').val());
                               console.log($(this).parent().parent());
                               if($(this).prop("id") === $('#del_donid').val()){
                                   $(this).parent().parent().remove();
                               }
                           });
                           $('#del_donid').val("");
                            $('#result_nhapdon').val("Đơn đã được xóa thành công!");
                       }else{
                           $('#result_nhapdon').val("Xảy ra lỗi khi xóa đơn. Đơn chưa được xóa!");
                       }
                    }
                });
            $( this ).dialog( "close" );
          },
          "Đóng": function() {
            $( this ).dialog( "close" );
          }
        }
      });
      
      // load dialog confirm phulucj
    $( "#dialog-phuluc" ).dialog({
        autoOpen: false,
        resizable: false,
        height: "auto",
        width: 400,
        modal: true,
        buttons: {
          "Online": function() {
              $.ajax({
                    type: "GET",
                    url:"LoadDonAjax",
                    data:{"action":"loadmaphuluc","loaiphuluc": "online",ngaynhap : $('#ngaynhap').val()},
                    success: function (data) {
                       $('#nd_maphuluc').val(data);
                    }
                });
            $( this ).dialog( "close" );
          },
          "Thường": function() {
              $.ajax({
                    type: "GET",
                    url:"LoadDonAjax",
                    data:{"action":"loadmaphuluc","loaiphuluc": "thuong",ngaynhap : $('#ngaynhap').val()},
                    success: function (data) {
                       $('#nd_maphuluc').val(data);
                    }
                });
            $( this ).dialog( "close" );
          }
        }
      });
      // open dialog phu lục
    $('#cb_nhapphuluc').on('change',function(){
       var maloainhan = $('#maloainhan').val();
       console.log(maloainhan);
       if($(this).prop('checked')){
           if(maloainhan === ''){
               $('#dialog-phuluc').dialog("open");
           }else{
               $.ajax({
                    type: "GET",
                    url:"LoadDonAjax",
                    data:{"action":"loadmaphuluc","loaiphuluc": "thuong",ngaynhap : $('#ngaynhap').val()},
                    success: function (data) {
                       $('#nd_maphuluc').val(data);
                    }
                });
           }
       }
   });
   // check đơn đã lưu
    var deleteDon = function(){
        var donid = $(this).prop("id");
        $('#del_donid').val(donid);
        $( "#dialog-confirm" ).dialog("open");
       };
    $(document).on('click','.img_delete_don',deleteDon);
   $("#btn_search").on("click",searchDon);
   
   $("#s_maloainhan").on("keydown",function(e){
       if (e.keyCode === 13) {
            $("#btn_search").click();
        }
   });
   $("#s_dononline").on("keydown",function(e){
       if (e.keyCode ===13) {
            $("#btn_search").click();
        }
   });
   $("#s_nhanbaodam").on("keydown",function(e){
       if (e.keyCode === 13) {
            $("#btn_search").click();
        }
   });
   $("#s_nhanbaodamtt").on("keydown",function(e){
       if (e.keyCode === 13) {
            $("#btn_search").click();
        }
   });
   $("#s_benbaodam").on("keydown",function(e){
       if (e.keyCode === 13) {
            $("#btn_search").click();
        }
   });

    $(".loaidon").append('<option value="0">Tất cả</option><option value="1">LĐ</option><option value="2">TĐ</option>\n\
            <option value="3">Xóa</option><option value="4">VB-XL-TS</option>\n\
            <option value="5">CC-TT</option>\n\
            <option value="6">BẢN SAO</option>\n\
            <option value="7">TBKBTHA</option>\n\
            <option value="8">MIỄN PHÍ</option>\n\
            <option value="9">CSGT</option>\n\
            <option value="10">CSGT-TĐ</option>\n\
            <option value="11">CSGT-X</option>\n\
            <option value="12">CSGT-Online</option>\n\
            <option value="13">CSGT-Online/TĐ</option>'
    );
