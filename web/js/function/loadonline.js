/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$( "#on_ngaybatdau" ).datepicker({
    dateFormat: 'dd/mm/yy',          
    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
        "Tháng 10", "Tháng 11", "Tháng 12" ],
    dayNamesMin: ["CN","2","3","4","5","6","7"],
    changeMonth: true,
    changeYear: true
});
$( "#on_ngayketthuc" ).datepicker({
    dateFormat: 'dd/mm/yy',          
    monthNamesShort: [ "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9",
        "Tháng 10", "Tháng 11", "Tháng 12" ],
    dayNamesMin: ["CN","2","3","4","5","6","7"],
    changeMonth: true,
    changeYear: true
});

 var loadData = function(){
     var username = $('#on_username').val();
     var passwd = $('#on_password').val();
     $('#tb_loadonline tbody').empty();
    $.ajax({
            type: "GET",
            url:"PhanDonServlet",
            data:{action: "loadonline",username: username,password: passwd},
            success: function (data) {
               // $('#dns_locdl tbody').html(data);
             //  console.log(data);
               $('#tb_loadonline tbody').html(data);
            },
            error: function (data) {
                console.log(data);
            }
        });
};

var checkData = function(){

    $('#tb_loadonline').find(' > tbody > tr ').each(function(){
        var td = $(this).find('td');
        var ngaynhap = td.eq(2).html();
        var online = td.eq(3).html();
            $.ajax({
                type: "POST",
                url:"PhanDonServlet",
                data:{action: "checkonline",ngaynhap: ngaynhap,online: online},
                success: function (data) {
                    if(data ==='y'){
                        $(this).parent().css("background-color","red");
                    }else{
                        $(this).parent().css("background-color","green");
                    }
                },
                error: function (data) {
                    console.log(data);
                }
            });
    });

};

$('#btn_loaddata').on('click',loadData);
$(document).ready(function() {
    $(document).on('click', '.xoa_online',function(event){
        $(this).parent().parent().remove();
    });
    $(document).on('click', '.giaychungnhan',function(event){
        var td =  $(this).parent().parent().find('td');
        var ngaynhap = td.eq(2).html();
        console.log(ngaynhap) ;
    });
});

$('.kiemtra').on('click',checkData);
//$('.giaychungnhan').on('click',loadDonPrint);