<%-- 
    Document   : phandon
    Created on : Jul 4, 2016, 8:08:52 AM
    Author     : Thorfinn
--%>

<%@page import="com.ttdk.bean.NhapDon"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.ttdk.bean.KhachhangBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.ttdk.bean.Khachhang"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <style>
            .the-legend {
              font-size: 1.2em !important;
                font-weight: bold !important;
                text-align: left !important;
                width:auto;
                padding:0 10px;
                border-bottom:none;
            }
            .the-fieldset {
                border: 1px groove #ddd !important;
                padding: 0 1.4em 1.4em 1.4em !important;
                margin: 0 0 1.5em 0 !important;
                -webkit-box-shadow:  0px 0px 0px 0px #000;
                 box-shadow:  0px 0px 0px 0px #000;
            }
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
                
            }
            th, td {
                padding: 5px;
                text-align: center;
            }
            #nhapdontable td{
                background: #ffffff;
            }
            table {
                width: 100%;
            }
            @media screen and (min-width: 768px) {
	#editModal .modal-dialog  {width:1200px;}
                }
            .ui-state-hover, .ui-autocomplete tr:hover
            {
                color:White;
                background:#1c94c4;
                outline:none;
            }
            .margin-bottom {
                margin-bottom:0px;
            }
        </style>
        <%
             if(session.getAttribute("1").equals("1")){
            %>
        <script>
            var khidSave;
            var _isTrung = false;
            var _isAccept = false;
            $(document).ready(function() {
                $( "#datepicker" ).datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                
                $('#ngaynhapEdit').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
                $('#ngaynhap').datepicker({
                    dateFormat: 'dd-mm-yy',          
                    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
                        "Tháng 10", "Tháng 11", "Tháng 12" ],
                    dayNamesMin: ["CN","2","3","4","5","6","7"],
                    changeMonth: true,
                    changeYear: true
                });
               // $("#maloainhan").
                var bddbauto ={
                    source : function(request, response) {
                        $.ajax({
                                url : "khachhang",
                                type : "GET",
                                data : {
                                        term : request.term
                                },
                                dataType : "json",
                                success : function(data) {
                                        
                                        response(data);
                                }
                        });
                    },
                    minLength: 4,
                    select: function (event, ui) {
                        $(this).closest("tr").find("input:text[id^='bddb_name']").val( ui.item.name );
                        //$(this).autocomplete('close');
                        $(this).closest("tr").find("input:text[id^='bddb_dc']").val(ui.item.address);
                       // $(".ui-menu-item").hide();
                        return false;
                    }
                };
                var bddbtpauto ={     
                    source : function(request, response) {
                        
                        $.ajax({
                                url : "khachhang",
                                type : "GET",
                                data : {
                                        term : request.term
                                },
                                dataType : "json",
                                success : function(data) {
                                        response(data);
                                }
                        });
                    },
                    minLength: 4,
                    select: function (event, ui) {
                        $("#bdtp_name").val( ui.item.name );
                        $('#bdtp_nameCheck').val( ui.item.name );
                        //$(this).autocomplete('close');
                        $("#bdtp_dc").val(ui.item.address);
                        $("#bdtp_dcCheck").val(ui.item.address);
                        $("#bdtp_tk").val(ui.item.account);
                         $("#bdtp_tkCheck").val(ui.item.account);
                        $("#bdtp_id").val(ui.item.id);
                        khidSave = ui.item.id;
                        if(!$("#btn_submit").is(":hidden")){
                            $('#bddb_name').val( ui.item.name);
                            $('#bddb_dc').val(ui.item.address);
                        }
                       // $(".ui-menu-item").hide();
                        return false;
                    }
                };
                var ncauto ={     
                    source : function(request, response) {
                        $.ajax({
                                url : "nganchanAjax",
                                type : "GET",
                                data : {action : "load",
                                        term : request.term
                                },
                                dataType : "json",
                                success : function(data) {
                                        response(data);
                                }
                        });
                    },
                    select: function (event, ui) {
                        $(this).closest("tr").find("input:text[id^='bdb_name']").val( ui.item.name );
                        $(this).closest("tr").find("input:text[id^='bdb_cmnd']").val(ui.item.account);
                        $.ajax({
                                url : "nganchanAjax",
                                type : "GET",
                                data : {"action":"checknc","name":ui.item.name,"cmnd":ui.item.account },
                                success : function(data) {
                                    console.log(data);
                                        if(data !=="ok" ){
                                             $('#nganchandiv').html(data);
                                             $('#nganchandiv').append("<button type='button' id='btn_accept' class='btn btn-primary btn-md'>Chấp nhận</button>");
                                             $('#btn_accept').on('click',showSubmit);
                                             $('button#btn_submit').hide();
                                        }
                                       
                                }
                        });
                        return false;
                    }
                };
                var showSubmit = function(){
                    $('#nganchandiv').html("");
                    $('button#btn_submit').show();
                };
                var bddb_name = $("#bddb_name").autocomplete(bddbauto).data('ui-autocomplete');
                bddb_name._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bddb_name._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bddb_name._renderItem = function(table, item) {
                      var name = String(item.name).replace(new RegExp(this.term, "gi"),"<span class='ui-state-highlight'>$&</span>");
                      var address = String(item.address).replace(new RegExp(this.term, "gi"),"<span class='ui-state-highlight'>$&</span>");
                      var account = String(item.account).replace(new RegExp(this.term, "gi"),"<span class='ui-state-highlight'>$&</span>");
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+name+"</td>"+"<td>"+address +"</td>"+"<td>"+account +"</td>" )
                      .appendTo( table );
                  };
                var bddb_dc = $("#bddb_dc").autocomplete(bddbauto).data('ui-autocomplete');
                bddb_dc._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bddb_dc._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bddb_dc._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.address +"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                var bdtp_name = $("#bdtp_name").autocomplete(bddbtpauto).data('ui-autocomplete');
                bdtp_name._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bdtp_name._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bdtp_name._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.address +"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                var bdtp_dc  = $("#bdtp_dc").autocomplete(bddbtpauto).data('ui-autocomplete');
                bdtp_dc._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bdtp_dc._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bdtp_dc._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.address +"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                var bdtp_tk =  $("#bdtp_tk").autocomplete(bddbtpauto).data('ui-autocomplete');
                bdtp_tk._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bdtp_tk._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bdtp_tk._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.address +"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                 var bbdname = $("#bdb_name").autocomplete(ncauto).data('ui-autocomplete');
                  bbdname._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Bên bảo đảm</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bbdname._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bbdname._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                var bdb_cmnd =  $("#bdb_cmnd").autocomplete(ncauto);
                bdb_cmnd._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Bên bảo đảm</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bdb_cmnd._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bdb_cmnd._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                window.count = 0;
                $('#addRow').on( 'click', function () {
                    var rowCount = $('input[id^="bddb_name"]').length;
                    console.log("rowCount "+rowCount);
                    window.count++;
                    var newbddbname = '<tr><td><input type="text" id="bddb_name'+rowCount+'" class="bddb_name" name="bddb_name" style="width: 100%" ></td>';
                    $('#tb_bennhandambao > tbody:last-child').append(newbddbname+'<td><input type="text" id="bddb_dc'+rowCount+'" class="bddb_dc" name="bddb_dc" style="width: 100%"></td>'+
                        '<td><button type="button" class="btn btn-primary btn-block subRow">Xóa</button></td></tr>'); 
                    var bddb_name = $("#bddb_name"+rowCount).autocomplete(bddbauto).data('ui-autocomplete');
                bddb_name._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bddb_name._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bddb_name._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.address +"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                var bddb_dc = $("#bddb_dc"+rowCount).autocomplete(bddbauto).data('ui-autocomplete');
                bddb_dc._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bddb_dc._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bddb_dc._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.address +"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                });
                $("#tb_bennhandambao").on('click', '.subRow', function () {
                    $(this).closest('tr').remove();
                });
                window.countDB = 0;
                $('#addRowBD').on( 'click', function () {
                    var rowCount = $('input[id^="bdb_name"]').length;                  
                    $('#tb_bendambao > tbody:last-child').append('<tr><td><input type="text" id="bdb_name'+rowCount+'" class="bdb_name" name="bdb_name" style="width: 100%"></td>'+
                        '<td><input type="text" id="bdb_cmnd'+rowCount+'" name="bdb_cmnd" style="width: 100%"></td>'+
                        '<td><button type="button" class="btn btn-primary btn-block subRow">Xóa</button></td></tr>');
                        var bbdname = $("#bdb_name"+rowCount).autocomplete(ncauto).data('ui-autocomplete');
                          bbdname._renderMenu = function(ul, items) {
                            var self = this;
                            //table definitions
                            ul.append("<table><thead><tr><th>Bên bảo đảm</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                            $.each( items, function( index, item ) {
                              self._renderItemData(ul, ul.find("table tbody"), item );
                            });
                          };
                         bbdname._renderItemData = function(ul,table, item) {
                            return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                          };      
                          bbdname._renderItem = function(table, item) {
                            return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                              //.data( "item.autocomplete", item )
                              .append( "<td>"+item.name+"</td>"+"<td>"+item.account +"</td>" )
                              .appendTo( table );
                          };
                        var bdb_cmnd =  $("#bdb_cmnd"+rowCount).autocomplete(ncauto);
                        bdb_cmnd._renderMenu = function(ul, items) {
                            var self = this;
                            //table definitions
                            ul.append("<table><thead><tr><th>Bên bảo đảm</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                            $.each( items, function( index, item ) {
                              self._renderItemData(ul, ul.find("table tbody"), item );
                            });
                          };
                         bdb_cmnd._renderItemData = function(ul,table, item) {
                            return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                          };      
                          bdb_cmnd._renderItem = function(table, item) {
                            return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                              //.data( "item.autocomplete", item )
                              .append( "<td>"+item.name+"</td>"+"<td>"+item.account +"</td>" )
                              .appendTo( table );
                          };
                });
                $("#tb_bendambao").on('click', '.subRow', function () {
                    $(this).closest('tr').remove();
                });
                $("#btn_clear").on("click",function(){
                    //clear bảng thông tin
                    $('#cb_nhapphuluc').prop('checked',false);
                    $("#loaidk").val("1");$("#loainhan").val("1");$("#gionhan").val("");$("#gionhan").val("");
                    $("#sodononline").val("");$("#mapin").val("");$("#cctt").val("0");$("#loaidon").val("1");
                    $("#sotaisan").val("");
                    $("#tb_bennhandambao :button.subRow").closest('tr').remove();
                    $("#tb_bendambao :button.subRow").closest('tr').remove();
                    $('#tthoso').find('input').val('');
                    $('#nganchandiv').html("");
                    getDay();
                    loadMaso();
                    $('button:submit').show();
                });
                function loadMaso(){
                    var loaidk = $("#loaidk").val();
                    var loainhan = $("#loainhan").val();
                        $.ajax({
                            type: "GET",
                            url:"NhapdonAjax",
                            data:{"loaidk":loaidk,"manhan":loainhan},
                            success: function (data) {
                                $("#maloainhan").val(data);
                            }
                        }); 
                }
                $("#loainhan").on("change",loadMaso);
                $("#loaidk").on("change",loadMaso);
                loadMaso();
                
                function getDay(){
                    var d = new Date();

                    var month = d.getMonth()+1;
                    var day = d.getDate();

                    var output = ((''+day).length<2 ? '0' : '') + day + '-' +
                    ((''+month).length<2 ? '0' : '') + month +'-'+ d.getFullYear();
                    $("#ngaynhap").val(output);
                    
                }   
                getDay();
                
  
               var bbd_keyUp = function(){
                   var bbd_name = $('#bdb_name').val();
                    var bdb_cmnd = $('#bdb_cmnd').val();
                    $.ajax({
                                url : "nganchanAjax",
                                type : "GET",
                                data : {"action":"checknc","name":bbd_name,"cmnd":bdb_cmnd },
                                success : function(data) {
                                        if(data !=="ok" ){
                                             $('#nganchandiv').html(data);
                                             $('#nganchandiv').append(" <button type='button' id='btn_accept' class='btn btn-link' >Chấp nhận</button>");
                                             $('#btn_accept').on('click',showSubmit);
                                             $('button:submit').hide();
                                        }
                                       
                                }
                        });
               };
               var updateClick = function(){
                   $('button:submit').show();
                   $(this).hide();
                   var donid = $(this).val();
                   var gionhan = $('#gionhan').val();
                   var phutnhan = $('#phutnhan').val();
                   var maloainhan = $('#maloainhan').val();
                   if(maloainhan === null || maloainhan === undefined){
                       maloainhan = "";
                   }
                   var dononline = $('#sodononline').val();
                   var mapin = $('#mapin').val();
                   var ngaynhap = $('#ngaynhap').val();
                   var cctt = $('#cctt').val();
                   var loaidon = $('#loaidon').val();
                   var slts = $('#sotaisan').val();
                   var btp = $('#bdtp_id').val();
                   var btp_name = $('#bdtp_name').val();
                   var btp_dc = $('#bdtp_dc').val();
                   var bdtp_tk = $('#bdtp_tk').val();
                   var loaidk = $('select#loaidk').val();
                   var bnbd = {};
                   var bnbd_content = [];
                   bnbd.content = bnbd_content;
                   $('input[id^="bddb_name"]').each(function(){
                       var content = {
                           "name": $(this).val(),
                           "diachi": $(this).parent().parent().find('input[id^="bddb_dc"]').val()
                       };
                       bnbd.content.push(content);
                       // bnbd.name = $(this).val();
                      //  bnbd.dc = $(this).parent().parent().find('input[id^="bddb_dc"]').val();
                   });
                   var bnbdJson = JSON.stringify(bnbd);
                   var bbd = {};
                    var bbd_content = [];
                    bbd.content = bbd_content;
                   $('input[id^="bdb_name"]').each(function(){
                       console.log($(this).val());
                       var content = {
                           "name": $(this).val(),
                           "cmnd": $(this).parent().parent().find('input[id^="bdb_cmnd"]').val()
                       };
                       bbd.content.push(content);
                      // bbd.name.pus = $(this).val();
                       //bbd.cmnd = $(this).parent().parent().find('input[id^="bdb_cmnd"]').val();
                   });
                   var bbdJson = JSON.stringify(bbd);
                   $.ajax({
                        type: "GET",
                        url:"LoadDonAjax",
                        data:{action:"update","donid":donid,"gionhan":gionhan,"loaidk":loaidk,"phutnhan":phutnhan,"maloainhan":maloainhan,"dononline":dononline,
                        "mapin":mapin,"ngaynhap":ngaynhap,"cctt":cctt,"loaidon":loaidon,"slts":slts,"btp":btp,"bnbd":bnbdJson,"bbd":bbdJson,
                    btp_name: btp_name,btp_dc : btp_dc,bdtp_tk: bdtp_tk},
                        success: function (data) {
                            location.reload();
                        }
                    });
               };
                var addDon = function(){
                    var gionhan = $.trim($("#gionhan").val());
                   var phutnhan = $.trim($("#phutnhan").val());
                   var ngaynhan = $.trim($("#ngaynhap").val());
                   var sotaisan = $.trim($("#sotaisan").val());
                   //console.log($('#cb_nhapphuluc').prop("checked"));
                   //alert($(".bddb_name").length);
                    $("#modal-error-body").empty();
                    
                   if(gionhan === ''){
                       $("#modal-error-body").append("<p>Chưa nhập giờ nhận</p>");
                       $('#myModal').modal('show'); 
                       return false;
                   }else if(Number(gionhan) > 59 || Number(gionhan) <0){
                       $("#modal-error-body").append("<p>Số phút phải bé hơn 59 và lớn hơn 0</p>");
                       $('#myModal').modal('show'); 
                       return false;
                   }else if(phutnhan === ''){
                       $("#modal-error-body").append("<p>Chưa nhập phút nhận</p>");
                       $('#myModal').modal('show'); 
                       return false;
                    }else if(Number(phutnhan) > 59 || Number(phutnhan) <0){
                       $("#modal-error-body").append("<p>Nhập sai số phút</p>");
                        $('#myModal').modal('show'); 
                        return false;
                           
                    }else if(ngaynhan === ''){
                       $("#modal-error-body").append("<p>Chưa nhập phút nhận</p>");
                       $('#myModal').modal('show'); 
                       return false;
                   }else if(ngaynhan.length >0){
                       var check = ngaynhan.split("-");
                       if(Number(check[1]) >12 || Number(check[1])<1){
                           $("#modal-error-body").append("<p>Sai tháng. Số tháng phải từ 1 đến 12</p>");
                            $('#myModal').modal('show'); 
                            return false;
                       }
                       if(Number(check[0]) >31 || Number(check[0])<1){
                            $("#modal-error-body").append("<p>Sai Ngay. Số ngày phải từ 1 đến 31</p>");
                            $('#myModal').modal('show'); 
                            return false;
                       }
                       if(Number(check[2]) === 2){
                           if(Number(check[1]) >29){
                                $("#modal-error-body").append("<p>Tháng 2 chỉ có 28 hoặc 29 ngày</p>");
                                $('#myModal').modal('show'); 
                                return false;
                           }
                       }
                   }else if(sotaisan === ''){
                       $("#modal-error-body").append("<p>Chưa nhập số tài sản</p>");
                       $('#myModal').modal('show'); 
                       return false;
                   }else if(Number(sotaisan) < 0){
                       $("#modal-error-body").append("<p>Số tài sản phải lớn hoặc bằng 0</p>");
                       $('#myModal').modal('show'); 
                       return false;
                   }else if($(".bddb_name").length === 1){
                        
                        var check = $.trim($(".bddb_name").val());
                        if(check === ''){
                            $("#modal-error-body").append("<p>Chưa nhập tên bên đảm bảo</p>");
                            $('#myModal').modal('show'); 
                            return false;
                        }
                    }else if($(".bddb_dc").length === 1){
                        var check = $.trim($(".bddb_dc").val());
                        if(check === ''){
                            $("#modal-error-body").append("<p>Chưa nhập địa chỉ bên đảm bảo</p>");
                            $('#myModal').modal('show'); 
                            return false;
                        }
                    }
                   var loaidon = $('#loaidon').val();
                   var loaidk = $('#loaidk').val();
                   var maloainhan = $('#maloainhan').val();
                   if(maloainhan === null || maloainhan === undefined){
                       maloainhan = "";
                   }
                   var gionhan = $('#gionhan').val();
                   var phutnhan = $('#phutnhan').val();
                   var dononline = $('#sodononline').val();
                   var mapin = $('#mapin').val();
                   var maloainhan = $('#maloainhan').val();
                   var ngaynhap = $('#ngaynhap').val();
                   var cctt = $('#cctt').val();
                   var slts = $('#sotaisan').val();
                   var bnbd = [];
                   var bnbbddc = [];
                   var bbd = [];
                   var bbdmaso = [];
                   $('input[id^="bddb_name"]').each(function(){
                        bnbd.push($(this).val());
                    });
                    $('input[id^="bddb_dc"]').each(function(){
                        bnbbddc.push($(this).val());
                    });
                    $('input[id^="bdb_name"]').each(function(){
                        bbd.push($(this).val());
                    });
                    $('input[id^="bdb_cmnd"]').each(function(){
                        bbdmaso.push($(this).val());
                    });
                    
                    var maonline = $('#sodononline').val();
                    var days = 1;
                    var date = new Date();
                     var last = new Date(date.getTime() - (days * 24 * 60 * 60 * 1000));
                     var converDay = last.getDate()+"-"+(last.getMonth() +1)+"-"+last.getFullYear();
                     $('#check_don_loading').html("<img src='./images/loading.gif'> <span style='color:blue'>Đang kiểm tra đơn...</span>");
                     var btpid = $('#bdtp_id').val();
                     var btpname = $('#bdtp_name').val();
                     var btpdc = $('#bdtp_dc').val();
                     var btptk = $('#bdtp_tk').val();
                     var loainhan = $('#loainhan').val();
                    $.ajax({
                        type: "GET",
                        url:"LoadDonAjax",
                        data:{action:"checkdon",loaidon : loaidon,maloainhan :maloainhan,bnbd: bnbd,"bbd": bbd,ngaynhap : converDay,maonline : maonline},
                        success: function (data) {
                            if(data === '' || _isAccept){
                               $('#check_don_loading').html("<img src='./images/loading.gif'><span style='color:blue'>Đang nhập đơn</span>");
                              /**  $.post('NhapdonServlet?action=add"', function(data) {
                                    $('#check_don_loading').html("<span style='color:blue'>Đã lưu đơn</span>");
                                }); **/
                               var savephuluc = $('#cb_nhapphuluc').prop('checked');
                               var maphuluc = $('#nd_maphuluc').val();
                               var sophuluc  = $('#nd_soluongpl').val();
                               $.ajax({
                                    type: "GET",
                                    url:"NhapdonServlet",
                                    data:{action:"add",loaidon : loaidon,maloainhan :maloainhan,ngaynhap : ngaynhap,loaidk:loaidk,
                                    sodononline: dononline,mapin: mapin,gionhan :gionhan,phutnhan : phutnhan,cctt: 1,slts : slts,bnbd: bnbd,bnbbddc: bnbbddc,
                                bbd: bbd,bbdmaso: bbdmaso,bdtp_id : btpid,bdtp_name: btpname,bdtp_dc: btpdc,bdtp_tk: btptk,loainhan: loainhan,
                            savephuluc: savephuluc, maphuluc : maphuluc, sophuluc : sophuluc},
                                    success: function (data) {
                                        $('#check_don_loading').empty();
                                        if(data === 'OK'){
                                            $('#result_nhapdon').html("Lưu đơn "+maloainhan.toUpperCase()+" thành công");
                                        }else if(data === 'FAILED'){
                                            $('#result_nhapdon').html("Lưu đơn "+maloainhan.toUpperCase()+" không thành công thành công");
                                        }
                                        
                                      //  $('#check_don_loading').html("<span style='color:blue'>Đã lưu đơn</span>");
                                        $("#btn_clear").click();
                                    },error: function (jqXHR, textStatus, errorThrown) {
                                        $('#check_don_loading').html("<span style='color:blue'>Lỗi nhập đơn</span>");
                                }
                                });
                            }else{
                                $('#check_don_loading').html("<span style='color:blue'>Đơn có thể bị trùng với : "+data+"</span>\n\
                    <button type='button' id='btn_accept' class='btn btn-primary btn-md'>Chấp nhận</button>");
                               // $('#nganchandiv').append("<button type='button' id='btn_accept' class='btn btn-primary btn-md'>Chấp nhận</button>");
                                 $('#btn_accept').on('click',acceptDon);
                                 $('#btn_submit').hide();
                            }
                        }
                    });
               };
               $('button#btn_submit').on('click',addDon);
               $('button#btn_update').hide();
               $('#bdb_name').on('keyup',bbd_keyUp);
               $('#bdb_cmnd').on('keyup',bbd_keyUp);
               $('button#btn_update').on('click',updateClick);
            } );
            
            var acceptDon = function(){
                $('#nganchandiv').html("");
                $('button:submit').show();
                _isAccept = true;
            };
            
            
            function addBNBDEdit(row,name,address){
                var bddbauto ={     
                    source : function(request, response) {
                        $.ajax({
                                url : "khachhang",
                                type : "GET",
                                data : {
                                        term : request.term
                                },
                                dataType : "json",
                                success : function(data) {
                                        response(data);
                                }
                        });
                    },
                    select: function (event, ui) {
                        $(this).closest("tr").find("input:text[id^='bddb_name']").val( ui.item.name );
                        //$(this).autocomplete('close');
                        $(this).closest("tr").find("input:text[id^='bddb_dc']").val(ui.item.address);
                       // $(".ui-menu-item").hide();
                        return false;
                    }
                };
                var newbddbname = '<tr><td><input type="text" id="bddb_name'+row+'" class="bddb_name" name="bddb_name" style="width: 100%" value="'+name+'"></td>';
                    $('#tb_bennhandambao > tbody:last-child').append(newbddbname+'<td><input type="text" id="bddb_dc'+row+'" class="bddb_dc" name="bddb_dc" style="width: 100%" value="'+address+'"></td>'+
                        '<td><button type="button" class="btn btn-primary btn-block subRow">Xóa</button></td></tr>'); 
                    var bddb_name = $("#bddb_name"+row).autocomplete(bddbauto).data('ui-autocomplete');
                bddb_name._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bddb_name._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bddb_name._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.address +"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
                var bddb_dc = $("#bddb_dc"+row).autocomplete(bddbauto).data('ui-autocomplete');
                bddb_dc._renderMenu = function(ul, items) {
                    var self = this;
                    //table definitions
                    ul.append("<table><thead><tr><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                    $.each( items, function( index, item ) {
                      self._renderItemData(ul, ul.find("table tbody"), item );
                    });
                  };
                 bddb_dc._renderItemData = function(ul,table, item) {
                    return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                  };      
                  bddb_dc._renderItem = function(table, item) {
                    return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                      //.data( "item.autocomplete", item )
                      .append( "<td>"+item.name+"</td>"+"<td>"+item.address +"</td>"+"<td>"+item.account +"</td>" )
                      .appendTo( table );
                  };
            }
            var showSubmit = function(){
                    $('#nganchandiv').html("");
                    $('button:submit').show();
                };
            function addBBDEdit(row,name,address){
                var ncauto ={     
                    source : function(request, response) {
                        $.ajax({
                                url : "nganchanAjax",
                                type : "GET",
                                data : {action : "load",
                                        term : request.term
                                },
                                dataType : "json",
                                success : function(data) {
                                        response(data);
                                }
                        });
                    },
                    select: function (event, ui) {
                        $(this).closest("tr").find("input:text[id^='bdb_name']").val( ui.item.name );
                        $(this).closest("tr").find("input:text[id^='bdb_cmnd']").val(ui.item.account);
                        $.ajax({
                                url : "nganchanAjax",
                                type : "GET",
                                data : {"action":"checknc","name":ui.item.name,"cmnd":ui.item.account },
                                success : function(data) {
                                    console.log(data);
                                        if(data !=="ok" ){
                                             $('#nganchandiv').html(data);
                                             $('#nganchandiv').append("<button type='button' id='btn_accept' class='btn btn-primary btn-md'>Chấp nhận</button>");
                                             $('#btn_accept').on('click',showSubmit);
                                             $('#btn_submit').hide();
                                        }
                                       
                                }
                        });
                        return false;
                    }
                };
                $('#tb_bendambao > tbody:last-child').append('<tr><td><input type="text" id="bdb_name'+row+'" class="bdb_name" name="bdb_name" style="width: 100%" value="'+name+'"></td>'+
                        '<td><input type="text" id="bdb_cmnd'+row+'" name="bdb_cmnd" style="width: 100%" value="'+address+'"></td>'+
                        '<td><button type="button" class="btn btn-primary btn-block subRow">Xóa</button></td></tr>');
                        var bbdname = $("#bdb_name"+row).autocomplete(ncauto).data('ui-autocomplete');
                          bbdname._renderMenu = function(ul, items) {
                            var self = this;
                            //table definitions
                            ul.append("<table><thead><tr><th>Bên bảo đảm</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                            $.each( items, function( index, item ) {
                              self._renderItemData(ul, ul.find("table tbody"), item );
                            });
                          };
                         bbdname._renderItemData = function(ul,table, item) {
                            return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                          };      
                          bbdname._renderItem = function(table, item) {
                            return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                              //.data( "item.autocomplete", item )
                              .append( "<td>"+item.name+"</td>"+"<td>"+item.account +"</td>" )
                              .appendTo( table );
                          };
                          
                        var bdb_cmnd =  $("#bdb_cmnd"+row).autocomplete(ncauto);
                        bdb_cmnd._renderMenu = function(ul, items) {
                            var self = this;
                            //table definitions
                            ul.append("<table><thead><tr><th>Bên bảo đảm</th><th>Số Tài Khoản</th></tr></thead><tbody></tbody></table>");
                            $.each( items, function( index, item ) {
                              self._renderItemData(ul, ul.find("table tbody"), item );
                            });
                          };
                         bdb_cmnd._renderItemData = function(ul,table, item) {
                            return this._renderItem( table, item ).data( "ui-autocomplete-item", item );
                          };      
                          bdb_cmnd._renderItem = function(table, item) {
                            return $( "<tr class='ui-menu-item' role='presentation'></tr>" )
                              //.data( "item.autocomplete", item )
                              .append( "<td>"+item.name+"</td>"+"<td>"+item.account +"</td>" )
                              .appendTo( table );
                          };
            }
            var editDon = function(donid){
                var listrowBBD = [];
                var listrowBNBD = [];
                $('input[id^="bddb_name"]').each(function(){
                    listrowBNBD.push($(this).prop('id'));
                });
                for(var i=1;i<listrowBNBD .length;i++){
                    $('#bddb_name'+i).parent().parent().remove();
                }
                $('input[id^="bdb_name"]').each(function(){
                    listrowBBD.push($(this).prop('id'));
                });
                for(var i=1;i<listrowBBD.length;i++){
                    $('#bdb_name'+i).parent().parent().remove();
                }
                   $.ajax({
                            type: "GET",
                            url:"LoadDonAjax",
                            data:{"action":"edit","donid":donid},
                            dataType: 'json',
                            success: function (data) {
                               $('#maloainhan').val(data.manhan);
                               $('#gionhan').val(data.gionhan);
                               $('#phutnhan').val(data.phutnhan);
                               $('#ngaynhap').val(data.ngaynhan);
                               $('#sodononline').val(data.dononline);
                               $('#mapin').val(data.mapin);
                               $('#sotaisan').val(data.slts);
                               if(data.manhan.includes("CE")){
                                   $('#loainhan').val(1);
                               }else if(data.manhan.includes("CF")){
                                   $('#loainhan').val(2);
                               }else if(data.manhan.includes("CB")){
                                   $('#loainhan').val(4);
                               }else if(data.manhan.includes("CT")){
                                   $('#loainhan').val(3);
                               }
                                if(data.manhan.includes("BD")){
                                   $('#loaidk').val(1);
                               }else if(data.manhan.includes("TT")){
                                   $('#loaidk').val(2);
                               }else if(data.manhan.includes("CSGT")){
                                   $('#loaidk').val(3);
                               }
                               
                               if( data.cctt.toLowerCase().includes("không có")){
                                   $('#cctt').val(1);
                               }else if(data.cctt.toLowerCase().includes("bên bảo đảm")){
                                   $('#cctt').val(2);
                               }else if(data.cctt.toLowerCase().includes("vbth")){
                                   $('#cctt').val(3);
                               }else if(data.cctt.toLowerCase().includes("sk")){
                                   $('#cctt').val(4);
                               }
                             //  console.log(data.loaidon);
                               switch(data.loaidon){
                                   case "LĐ": $('#loaidon').val(1);
                                            break;
                                   case "TĐ": $('#loaidon').val(2);
                                    break;
                                   case "Xóa": $('#loaidon').val(3);
                                    break;
                                   case "VB-XL-TS": $('#loaidon').val(4);
                                    break;
                                   case "CC-TT": $('#loaidon').val(5);
                                    break;
                                   case "BẢN SAO": $('#loaidon').val(6);
                                    break;
                                   case "TBKBTHA": $('#loaidon').val(7);
                                    break;
                                   case "MIỄN PHÍ": $('#loaidon').val(8);
                                    break;
                                   case "CSGT": $('#loaidon').val(9);
                                    break;
                                   case "CSGT-TĐ": $('#loaidon').val(10);
                                    break;
                                   case "CSGT-X": $('#loaidon').val(11);
                                    break;
                                   case "CSGT-Online": $('#loaidon').val(12);
                                    break;
                                   case "CSGT-Online/TĐ": $('#loaidon').val(13);
                                        break;
                               }
                               var listbnbd = data.bnbd.split("\n");
                               for(var i=0; i < listbnbd.length;i++){
                                   var bnbdValues = listbnbd[i].split("_");
                                   if(i ===0){
                                       $('#bddb_name').val(bnbdValues[0]);
                                       $('#bddb_dc').val(bnbdValues[1]);
                                   }else{
                                       addBNBDEdit(i,bnbdValues[0],bnbdValues[1]);
                                   }
                               }
                               var bnbdtpValues = data.bndbtp.split("_");
                               $('#bdtp_name').val(bnbdtpValues[0]);
                               $('#bdtp_dc').val(bnbdtpValues[1]);
                               $('#bdtp_tk').val(bnbdtpValues[2]);
                               $('#bdtp_id').val(bnbdtpValues[3]);
                               
                               var listbbd = data.benbaodam.split('\n');
                               for(var i=0; i < listbbd.length;i++){
                                   var bbdValues = listbbd[i].split("_");
                                   if(i ===0){
                                       $('#bdb_name').val(bbdValues[0]);
                                       $('#bdb_cmnd').val(bbdValues[1]);
                                   }else{
                                       addBBDEdit(i,bbdValues[0],bbdValues[1]);
                                   }
                               }
                               $('#btn_submit').hide();
                               $('button#btn_update').show();
                               $('button#btn_update').prop('value',data.donid);
                               
                            }
                    });
               };

               
              
        </script>
        <%
            }
%>
    </head>
    <body> 
        <ul id="1stmenu" class="nav nav-pills">
            <li class="active"><a href="xulydon.jsp?page=nhapdon" >Xử Lý Đơn</a></li>
            <%
                if(session.getAttribute("10").equals("1") || session.getAttribute("11").equals("1") || session.getAttribute("12").equals("1") || session.getAttribute("13").equals("1")){
                %>
            <li><a href="vanthu.jsp?page=themvanthu" >Văn Thư</a></li>
             <%
                 }
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
             //   if(session.getAttribute("21").equals("1")){
                %>
            
           <!-- <li class="dropdown pull-right"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Cài Đặt<span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#">Đổi Mật Khẩu</a></li>
                    <li><a href="#">Thông tin cá nhân</a></li>
                </ul>
            </li>
           <li  class="pull-right"><button type="button" class="btn btn-link" id="btn_changepass"><a >Đổi mật khẩu</a></button></li> -->
           <li  class="pull-right"><a id="btn_changepass" >Đổi mật khẩu</a></li>
            <li  class="pull-right"><a id="logout" href="logout"><span class="glyphicon glyphicon-log-in"></span> Đăng Xuất</a></li>
            <li class="pull-right"><a href="#"><span class="glyphicon glyphicon-user"></span><%= session.getAttribute("fullname").toString() %><input type="hidden" id="username" name="username" value="<%=session.getAttribute("username")%>" /></a></li>
            

        </ul>
        <ul id="sub-menu-1">
             <%
                if(session.getAttribute("1").equals("1") || session.getAttribute("2").equals("1")){
                %>
                <li class="sub-menu"><a class="active" href="xulydon.jsp?page=nhapdon" >Nhập đơn</a></li>
                <%
                    }

                    if(session.getAttribute("5").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=phandon">Phân đơn</a></li>
                <%
                    }
                    if(session.getAttribute("6").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=donchotra">Xem đơn chờ trả</a></li>
                 <%
                    }
                     if(session.getAttribute("8").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=dondatra">Xem đơn đã trả</a></li>
                <%
                    }
                     if(session.getAttribute("9").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=thongke">Thống kê hiệu suất</a></li>
                <%
                    }
                     if(session.getAttribute("22").equals("1") ){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=danhsachno">Danh sách nợ</a></li>
                <%
                    }
                     if(session.getAttribute("1").equals("1") || session.getAttribute("2").equals("1")){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=donchuataikhoan">Đơn chưa có số tài khoản</a></li>
                <%
                    }if(session.getAttribute("1").equals("1") && session.getAttribute("2").equals("1")){
                    %>
                <li class="sub-menu"><a href="xulydon.jsp?page=loadonline">Đơn online</a></li>
                <%
                    }
                    %>
            </ul>
            <% if(session.getAttribute("1").equals("1")){
                %>
        <form action="NhapdonServlet?action=add" method="POST">
            <div style="text-align: center; width: 100%;" ><span style="color: red;font-weight: bold;" id="result_nhapdon"></span></div>
        <fieldset class="well the-fieldset" id="tthoso">
            <legend class="the-legend"><h4>Thông tin hồ sơ</h4></legend>
            <div class="row form-group margin-bottom">
                <div class="col-xs-1">

                </div>
                <div class="col-xs-2">
                    <h5>Loại Đăng Ký:</h5>
                </div>  
                <div class="col-xs-2">
                    <select id="loaidk" name="loaidk" style="width: 180px !important;height: 30px" class="form-control">
                        <option value="1">Giao dịch đảm bảo</option>
                        <option value="2">Cung Cấp thông tin</option>
                        <option value="3">Cảnh sát giao thông</option>
                    </select>
                </div>
                <div class="col-xs-1">

                </div>
                <div class="col-xs-2">
                    <h5>Loại Hình Nhận: </h5>
                </div>
                <div class="col-xs-2">
                    <select id="loainhan" name="loainhan" class="form-control">
                        <option value="1">Email</option>
                        <option value="2">Fax</option>
                        <option value="3">Trực tiếp</option>
                        <option value="4">Bưu điện</option>
                    </select>
                      
                </div>
                <div class="col-xs-2">
                   <input type="text" id="maloainhan" name="maloainhan" value="" class="form-control" />
                </div>    
            </div>
            <div class="row form-group margin-bottom">
                <div class="col-xs-1">

                </div>
                <div class="col-xs-2">
                    <h5>Thời Điểm Nhận:</h5>
                </div>    
                <div class="col-xs-1">
                    <input type="text" id="gionhan" name="gionhan" placeholder="Giờ Nhận" value=""class="form-control"/>
                </div>
                <div class="col-xs-1">
                    <input type="text" id="phutnhan" name="phutnhan" placeholder="Phút Nhận" value="" class="form-control"/>
                </div>
                <div class="col-xs-1">
                </div>
                <div class="col-xs-2">
                    <h5>Ngày Nhập:</h5>
                </div>
                <div class="col-xs-2">
                    <input type="text" id="ngaynhap" name="ngaynhap" id="datepicker" class="form-control">
                </div>
            </div>
            <div class="row form-group margin-bottom">
                <div class="col-xs-1">

                </div>
                <div class="col-xs-2">
                    <h5>Số Đơn Online Cấp:</h5>
                </div>    
                <div class="col-xs-2">
                    <input type="text" id="sodononline" name="sodononline" placeholder="Số đơn online" value="" class="form-control"/>
                </div>
               <div class="col-xs-1">
                    <input type="text" id="mapin" name="mapin" placeholder="Mã Pin" value=""  class="form-control"/>
                </div>
                <div class="col-xs-2">
                   <!-- <h4>Cung Cấp Thông tin:</h4> -->
                   <h5>Số lượng tài sản: </h5>
                </div>
                <div class="col-xs-4">
                    <input type="text" id="sotaisan" name="sotaisan" value="1" style="width: 85px !important;height: 30px" class="form-control"/>
                    
                </div>
            </div>
            <div class="row form-group margin-bottom">
                <div class="col-xs-1">
<select id="cctt" name="cctt" style="width: 150px !important; height: 30px;visibility:hidden;" class="form-control">
                        <option value="1">Không có</option>
                        <option value="2">Bên Bảo Đảm</option>
                        <option value="3">VBTH</option>
                        <option value="4">SK</option>
                    </select>
                </div>
                <div class="col-xs-2">
                    <h5>Loại Đơn:</h5>
                </div>    
                <div class="col-xs-2">
                    <select id="loaidon" class="form-control" name="loaidon" style="width: 150px !important; height: 30px" >
                        <option value="1">LĐ</option>
                        <option value="2">TĐ</option>
                        <option value="3">Xóa</option>
                        <option value="4">VB-XL-TS</option>
                        <option value="5">CC-TT</option>
                        <option value="6">BẢN SAO</option>
                        <option value="7">TBKBTHA</option>
                        <option value="8">MIỄN PHÍ</option>
                        <option value="9">CSGT</option>
                        <option value="10">CSGT-TĐ</option>
                        <option value="11">CSGT-X</option>
                        <option value="12">CSGT-Online</option>
                        <option value="13">CSGT-Online/TĐ</option>
                    </select>
                </div>
                <div class="col-xs-1">
                </div>
                <div class="col-xs-2">
                    <!-- <h4>Số lượng tài sản: </h4>  -->
                    
                    <input type="checkbox" id="cb_nhapphuluc" name="nhapphuluc"  /> Nhập kèm phụ lục 
                </div>
                <div class="col-xs-3">
                    <input type="text" id="nd_maphuluc" name="nd_maphuluc" value="1" style="width: 150px !important;height: 30px" class="form-control"/> 
                </div>
                <div class="col-xs-3">
                    
                </div>
                <div class="col-xs-2">
                    Số lượng phụ lục :
                </div>
                <div class="col-xs-2">
                    <input type="text" id="nd_soluongpl" name="nd_soluongpl" value="1" style="width: 150px !important;height: 30px" class="form-control"/> 
                </div>
            </div>
            <!-- BUTTON HERE---------------------------------->
            <div class="row form-group margin-bottom">
                <div class="col-xs-1">
                </div>
                <div class="col-xs-10">
                    <table id="tb_bennhandambaotp" class="table table-bordered" style="width: 100%" border="0">
                        <thead>
                            <tr>
                                <th>Bên Nhận Bảo Đảm (Thu Phí)</th>
                                <th>Địa chỉ(Thu Phí)</th>
                                <th>Số tài khoản</th>
                            </tr>
                        </thead>
                        <tbody id="fbody">
                            <tr>
                                <td style="width: 40%">
                                    <input type="text" id="bdtp_name" name="bdtp_name" style="width: 100%" class="form-control">
                                    <input type="text" id="bdtp_nameCheck"  hidden>
                                </td>
                                <td style="width: 40%" >
                                    <input type="text" id="bdtp_dc" name="bdtp_dc" style="width: 100%" class="form-control">
                                    <input type="text" id="bdtp_dcCheck"  hidden>
                                </td>
                                <td style="width: 20%">
                                    <input type="text" id="bdtp_tk" name="bdtp_tk" style="width: 100%" class="form-control">
                                    <input type="text" id="bdtp_tkCheck"  hidden>
                                    <input type="text" id="bdtp_id" name="bdtp_id" value="" hidden >
                                </td>
                                
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-xs-1">
                    
                </div>    
            </div>
            <!-- bên nhận bảo đảm chưa thu phí -->
            <div class="row form-group margin-bottom">
                <div class="col-xs-1">
                </div>
                <div class="col-xs-10">
                    <table id="tb_bennhandambao" class="table table-bordered" style="width: 100%" border="0">
                        <thead>
                            <tr>
                                <th>Bên Nhận Bảo Đảm</th>
                                <th>Địa chỉ</th>
                                <th>Xóa</th>
                            </tr>
                        </thead>
                        <tbody id="fbody">
                            <tr>
                                <td><input type="text" id="bddb_name" class="bddb_name form-control" name="bddb_name" style="width: 100%" /></td>
                                <td><input type="text" id="bddb_dc" name="bddb_dc" style="width: 100%" class="form-control"/></td>
                                <td>
                                        <span id="addRow" class="glyphicon glyphicon-plus"></span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-xs-1">
                    
                </div>    
            </div>
            
            
            <div class="row margin-bottom">
                <div class="col-xs-1">
                </div>
                <div class="col-xs-10">
                    <table id="tb_bendambao" class="table table-bordered" style="width: 100%" border="0">
                        <thead>
                            <tr>
                                <th>Bên Bảo Đảm</th>
                                <th>CMND</th>
                                <th>Xóa</th>
                            </tr>
                        </thead>
                        <tbody id="fbody">
                            <tr>
                                <td><input type="text" id="bdb_name" name="bdb_name" style="width: 100%" class="form-control"></td>
                                <td><input type="text" id="bdb_cmnd" name="bdb_cmnd" style="width: 100%" class="form-control"></td>
                                <td>
                                        <span id="addRowBD" class="glyphicon glyphicon-plus"></span>
                                </td>
                            </tr>
                            <tr><td colspan="3" ><div id="nganchandiv"></div></td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-xs-1">
                    
                </div>    
            </div>
            <div class="row margin-bottom">
                <div class="col-xs-4">
                </div>
                <div class="col-xs-2">
                    <button type="button" id="btn_submit"class="btn btn-primary btn-md" style="width: 100%" >Lưu Lại</button>
                    <button type="button" id='btn_update' class="btn btn-primary btn-md" style="width: 100%" hidden="">Cập Nhật</button>
                </div>
                <div class="col-xs-2">
                    <button type="button" id="btn_clear" class="btn btn-primary btn-md" style="width: 100%" >Hủy bỏ</button>
                </div>
                <div class="col-xs-4">
                    <div id="check_don_loading"></div>
                </div>    
            </div>
        </fieldset>
       </form>
                <%
                    
                    }%>
        <fieldset>
            <legend class="the-legend">Lọc Dữ Liệu</legend>
                <input type="text" name="s_ngaynhap" id="s_ngaynhap" placeholder="Ngày Nhập" style="width: 90px !important; height: 30px"/>
                <input type="text" class='repalacedot' name="s_dononline" id="s_dononline" placeholder="Số đơn online" style="width: 90px !important; height: 30px" />
                <select name="s_loaidon" id="s_loaidon" class="loaidon" style="width: 70px !important; height: 30px">
                        
                </select>
                <select name="s_hinhnhan" id="s_hinhnhan" style="width: 105px !important; height: 30px">
                    <option value="0">Loại Nhận</option>
                        <option value="1">Email</option>
                        <option value="2">Fax</option>
                        <option value="3">Bưu điện</option>
                        <option value="4">Trực tiếp</option>
                        
                </select>
                <input type="text" name="s_maloainhan" class='repalacedot' id="s_maloainhan" placeholder="Mã loại hình nhận"/>
                <input type="text" name="s_nhanbaodam" id="s_nhanbaodam" placeholder="Bên nhận bảo đảm"/>
                <input type="text" name="s_nhanbaodamtt" id="s_nhanbaodamtt" placeholder="Bên nhận bảo đảm (TT)"/>
                <input type="text" name="s_benbaodam" id="s_benbaodam" placeholder="Bên bảo dảm"/>
                <button type="button" id="btn_search" class="btn btn-default">Tìm Kiếm</button>
        </fieldset><br>
        <div id="div_loading"></div>
        <table id="nhapdontable" class="table table-striped table-bordered">
            <thead style="background-color: #87CEFA" >
                <tr>
                    <th rowspan="2">Thời Điểm Nhập</th>
                    <th colspan="2">Số Đơn Do Online Cấp</th>              
                    <th rowspan="2">Loại Hình Nhận</th>
                    <th rowspan="2">Loại Đơn</th>
                    <th rowspan="2">Số Lượng Tài Sản</th>
                    <th rowspan="2">Cung Cấp thông tin</th>
                    <th rowspan="2">Bên Nhận Bảo Đảm</th>
                    <th rowspan="2">Bên Nhận Đảm Bảo Thông Báo Phí</th>
                    <th rowspan="2">Bên Bảo Đảm</th>
                    <% if(session.getAttribute("3").equals("1")){
                        %>
                    <th rowspan="2">Sửa chữa</th>
                    <%                         
                    } 
                    if(session.getAttribute("4").equals("1")){
                    %>
                    <th rowspan="2">Xóa</th>
                    <%                         
                    }  %>
            </tr>
            <tr>
                <th>Số Đơn Online</th>
                <th>Mã Pin</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table><br>
        <div style="text-align: right">
            <ul class="pagination" id="ul_page" ></ul>
        </div>
        <!-- MODELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL --->
        <!-- Modal -->
        <div id="myModal" class="modal fade" role="dialog">
          <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Lỗi</h4>
              </div>
              <div class="modal-body" id="modal-error-body">
                  
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
              </div>
            </div>

          </div>
        </div>
        <!-- modal/dialog khi xóa đơn-->
        <div id="dialog-confirm" title="Xóa Đơn">
            <input type="text" id="del_donid" value="" hidden>
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;">
                    
                </span>Thông tin về đơn, phân đơn sẽ bị xóa khi bạn XÓA. Bạn có muốn xóa đơn không ?</p>
          </div>
        
        <!-- modal/dialog khi xác nhận phụ lục-->
        <div id="dialog-phuluc" title="Loại phụ lục">
            <input type="text" id="del_donid" value="" hidden>
            <p>Phụ lục bạn nhập là phụ lục <span style="color: red;"><strong>ONLINE</strong></span> ?</p>
          </div>
        <script>
            var $pagination = $('#ul_page');
            var daybegin = $('#s_ngaynhap').val();
            var defaultOpts = {
                totalPages: 1,
                first: 'Trang đầu',
                prev: 'Trang cuối',
                next: 'TIếp',
                last: 'Sau',
                loop: false
            };
               
             $pagination.twbsPagination(defaultOpts);
            /** $.ajax({
                   url : "LoadDonAjax",
                   type : "GET",
                   data : {
                                    "action" : "getrow",ngaynhap : daybegin
                            },
                    dataType : "json",
                    success: function (data) {
                        var totalPages = data.totalpage;
                        var currentPage = data.currentpage;
                        $pagination.twbsPagination('destroy');
                        $pagination.twbsPagination($.extend({}, defaultOpts, {
                            startPage: currentPage,
                            totalPages: totalPages,
                            onPageClick: function (event, page) {
                                console.log('PAGE = '+page);
                                $("#nhapdontable tbody").empty();
                                $('#div_loading').html("<img src='./images/loading.gif'>");
                                $.ajax({
                                    type: "GET",
                                    url:"LoadDonAjax",
                                    data:{"action":"loaddon","page":page,ngaynhap : daybegin},
                                    success: function (data) {
                                        $('#div_loading').empty();
                                       $("#nhapdontable tbody").append(data);
                                    }
                            }); 
                            }
                        }));
                    }
              });**/
            function loadTable(page){
               $("#nhapdontable tbody").empty();
                $.ajax({
                        type: "GET",
                        url:"LoadDonAjax",
                        data:{"action":"loaddon","page":page},
                        success: function (data) {
                            if(data !==""){
                                $("#nhapdontable tbody").append(data);
                            }else{
                                alert('Hết đơn');
                                return false;
                            }

                        }
                }); 
            };
            var checkChangeBTP = function(){
             //      console.log("CHECK");
                   var btpname = $('#bdtp_name').val().toLowerCase();
                   var btp_dc = $('#bdtp_dc').val().toLowerCase();
                   
                   if(btpname !== '' && btp_dc !== '' ){
                       var btp_nameCheck = $('#bdtp_nameCheck').val().toLowerCase();
                        if(btp_nameCheck != btpname){
                            $('#bdtp_id').val('');
                        }else{
                            var btp_dcCheck = $('#bdtp_dcCheck').val().toLowerCase();
                             if(btp_dc != btp_dcCheck){
                                 $('#bdtp_id').val('');
                             }else{
                                 var tk = $('#bdtp_tk').val().toLowerCase();
                                 var tkCheck = $('#bdtp_tkCheck').val().toLowerCase();
                                 if(tk != tkCheck){
                                     $('#bdtp_id').val('');
                                 }else{
                                     $('#bdtp_id').val(khidSave);
                                 }
                             }
                        }
                   }
                   
                   
               };
                $('#nd_soluongpl').on('keyup',function(e){
                    if(e.keyCode !== 8 || e.keyCode !== 16 || e.keyCode !== 20 || e.keyCode !== 9 ||e.keyCode !== 13 )
                    if(isNaN($(this).val())){
                        alert("Số lượng phụ lục phải nhập kiểu số");
                        return false;
                    }
                });
               $('#bdtp_name').on('keyup paste',checkChangeBTP);
               $('#bdtp_dc').on('keyup paste',checkChangeBTP);
                $('#bdtp_tk').on('keyup paste',checkChangeBTP);
        </script>
        <script src="js/nhapdon-0.1.0d.js"></script>
    </body>
</html>
