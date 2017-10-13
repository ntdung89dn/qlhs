/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var loadPhanQuyen = function(){
    $('.navbar li.active').removeClass('active');
    $('#li_phanquyen').addClass('active');
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        dataType : "json",
        data:{"action":"loadphanquyen"},
        success: function (data) {
            $('#qlpq_nhom').html(data.nhom);
            $('#tbqlpq_phanquyen tbody').html(data.quyen);
        }
    });
};

var luuPhanQuyen = function(){
    var nhomid = $('#qlpq_nhom').val();
    if(nhomid ==0){
        alert("Xin vui lòng chọn nhóm ");
        return false;
    }
    var quyenid = [];
    $('input:checkbox:checked').each(function(i,el){
       quyenid.push($(this).val());
    });
    var noquyenid = [];
    $('input:checkbox:not(:checked)').each(function(i,el){
       noquyenid.push($(this).val());
    });
    console.log(noquyenid);
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        dataType : "json",
        data:{"action":"savephanquyen",nhomid: nhomid,phanquyenids : quyenid,noquyenid: noquyenid},
        success: function (data) {
            
        }
    });
};

var loadPhanQuyenNhom = function(){
    var nhomid = $('#qlpq_nhom').val();
    console.log(nhomid);
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        data:{"action":"loadrolenhom",nhomid: nhomid},
        success: function (data) {
            if(data !== ''){
                var roleid = data.split(',');
                $('input:checkbox').each(function(i,el){
                    for(var i=0;i < roleid.length;i++){
                        if(roleid[i] == $(this).val()){
                            $(this).prop("checked",true);
                            break;
                        }else{
                            $(this).prop("checked",false);
                        }
                    }
                 });
            }else{
                $('input:checkbox').each(function(i,el){
                    $(this).prop("checked",false);
                 });
            }
            
        }
    });
};
$('#qlpq_nhom').on('change',loadPhanQuyenNhom);
loadPhanQuyen();