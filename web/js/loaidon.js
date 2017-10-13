/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var loadLoaiDon = function(){
    $('.navbar li.active').removeClass('active');
    $('#li_loaidon').addClass('active');
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        data:{"action":"loadloaidon"},
        success: function (data) {
            console.log(data);
            $('#tb_loaidon').html(data);
        }
    });
};
loadLoaiDon();

