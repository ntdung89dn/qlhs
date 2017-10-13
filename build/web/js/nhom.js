/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// load don edit
var loadNhom = function(){
    $('.navbar li.active').removeClass('active');
    $('#li_nhom').addClass('active');
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        dataType : "json",
        data:{"action":"loadnhom"},
        success: function (data) {
            $('#qln_nhom tbody').html(data.nhom);
            $('div#div_nvlist').html(data.nhanvien);
        }
    });
};
var clearDon = function(){
    $('input').prop('checked',false);
    $('#qln_name').val('');
    $('#btn_luunhom').unbind('click');
    $('#btn_luunhom').bind('click',luuNhom);
};

var luuNhom = function(event){
    var name = $('#qln_name').val();
    var listnv = [];
    $('input:checkbox:checked').each(function(i,el){
        listnv.push($(this).prop('id'));
    });
    var role = $('#qln_nameid').val();
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        data:{"action":"luunhom",name: name,username: listnv,role: role},
        success: function (data) {
            reloadNhom();
        },error: function(data){
            console.log(data);
        }
    });
};

var loadEditNhom = function(roleid){
    $('#div_nvlist input:checkbox:checked').each(function(i,el){
        $(this).prop('checked',false);
     });
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        dataType : "json",
        data:{"action":"loadeditnhom",roleid: roleid},
        success: function (data) {
            $('#qln_name').val(data.name);
            $('#qln_nameid').val(data.roleid);
            if(data.roleid !== ''){
                console.log("NVIDS = "+data.nvids);
                if(data.nvids !== '' && data.nvids !== undefined){
                    var userids = data.nvids.split(',');
                    $('#div_nvlist input:checkbox').each(function(i,el){
                        var checkid =  $(this).prop('id');
                        for(var j =0; j < userids.length;j++){
                            if(checkid === userids[j]){
                                $(this).prop('checked',true);
                            }
                        }
                     });
                }else{
                    $('#div_nvlist input:checkbox').each(function(i,el){
                        $(this).prop('checked',false);
                     });
                }
                
            }else{
                $('#div_nvlist input:checkbox').each(function(i,el){
                    $(this).prop('checked',true);
                });
            }
            
            $('#btn_luunhom').unbind('click');
            $('#btn_luunhom').bind('click',editNhom);
        },error: function(data){
            console.log(data);
        }
    });
    
};

var editNhom = function(){
    var name = $('#qln_name').val();
    var roleid = $('#qln_nameid').val();
    var listnv = [];
    $('input:checkbox:checked').each(function(i,el){
        listnv.push($(this).prop('id'));
    });
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        data:{"action":"saveeditnhom",name: name,roleid: roleid,nhanvien: listnv},
        success: function (data) {
            clearDon();
            reloadNhom();
        },error: function(data){
            console.log(data);
        }
    });
};
var reloadNhom = function(){
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        data:{"action":"reloadnhom"},
        success: function (data) {
            $('#qln_nhom tbody').html(data);
        }
    });
};
var deleteNhom = function(){
    
};

loadNhom();

$('#btn_luunhom').on('click',luuNhom);