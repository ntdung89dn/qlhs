/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var $pagination = $('#ul_page');
var searchList = [];
var defaultOpts = {
    totalPages: 20
};
$pagination.twbsPagination(defaultOpts);
$.ajax({
       url : "AdminServlet",
       type : "GET",
       data : {
                        "action" : "loadallnhanvien",page: 1
                },
        dataType : "json",
        success: function (data) {
            var totalPages = data.totalpage;
            var currentPage = data.currentpage;
            $('#qlnv_nhanvien tbody').html(data.data);
            $pagination.twbsPagination('destroy');
            $pagination.twbsPagination($.extend({}, defaultOpts, {
                startPage: currentPage,
                totalPages: totalPages,
                onPageClick: function (event, page) {
                   $.ajax({
                        type: "GET",
                        url:"AdminServlet",
                        dataType : "json",
                        data:{"action":"loadallnhanvien","page":page},
                        success: function (data) {
                            $('#qlnv_nhanvien tbody').html(data.data);
                        }
                    });
                }
            }));
        }
  });

// load don edit
var editDon = function(username){
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        dataType : "json",
        data:{"action":"loadnvedit","username":username},
        success: function (data) {
            clearDon();
            $('#qlnv_fullname').val(data.fullname);
            $('#qlnv_username').val(data.username);
            //$('#qlnv_username').prop('readonly', true);
            $('#qlnv_mota').val(data.infor);
            $('#qlnv_password').val(data.passwd);
            $('#btn_luunv').unbind('click');
            $('#btn_luunv').bind( "click",{id: data.username,iid: data.iid}, saveEdit,false );
        }
    });
};

// luu don
var saveDon = function(){
    var fullname = $('#qlnv_fullname').val();
    var username = $('#qlnv_username').val();
    var mota = $('#qlnv_mota').val();
    var password = $('#qlnv_password').val();
    var nhom = $('#qlnv_nhom').val();
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        data:{"action":"luunhanvien","username":username,fullname: fullname,infor: mota,password: password,nhom: nhom},
        success: function (data) {
            console.log(data);
            clearDon();
            reloadTableNV();
        },error: function(data){
            console.log(data);
        }
    });
};

// clear all input
var clearDon = function(){
    $('#qlnv_fullname').val('');
    $('#qlnv_username').val('');
    $('#qlnv_mota').val('');
    $('#qlnv_nhom').val('');
    $('#qlnv_password').val('');
    $('#qlnv_username').prop('readonly', false);
    $('#btn_luunv').unbind('click');
    $('#btn_luunv').bind( "click",saveDon );
};

// save Edit
var saveEdit = function(event){
    var fullname = $('#qlnv_fullname').val();
    var username = $('#qlnv_username').val();
    var mota = $('#qlnv_mota').val();
    var password = $('#qlnv_password').val();
    var nhom = $('#qlnv_nhom').val();
    $('#btn_luunv').unbind('click');
    $('#btn_luunv').bind( "click",saveDon );
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        data:{"action":"saveeditnv","username":username,fullname: fullname,infor: mota,password: password, iid: event.data.iid,nhom: nhom},
        success: function (data) {
            clearDon();
            $.ajax({
                url : "AdminServlet",
                type : "GET",
                data : {
                                 "action" : "loadallnhanvien",page: 1
                         },
                 dataType : "json",
                 success: function (data) {
                     var totalPages = data.totalpage;
                     var currentPage = data.currentpage;
                     $('#qlnv_nhanvien tbody').html(data.data);
                     $pagination.twbsPagination('destroy');
                     $pagination.twbsPagination($.extend({}, defaultOpts, {
                         startPage: currentPage,
                         totalPages: totalPages,
                         onPageClick: function (event, page) {
                            $.ajax({
                                 type: "GET",
                                 url:"AdminServlet",
                                 dataType : "json",
                                 data:{"action":"loadallnhanvien","page":page},
                                 success: function (data) {
                                     $('#qlnv_nhanvien tbody').html(data.data);
                                 }
                             });
                         }
                     }));
                 }
           });
        }
    });
};
var loadNhom = function(){
    $('.navbar li.active').removeClass('active');
    $('#li_nhanvien').addClass('active');
    $.ajax({
        type: "GET",
        url:"AdminServlet",
        data:{"action":"loadnhomnv"},
        success: function (data) {
            $('#qlnv_nhom').html(data);
        }
    });
};

var reloadTableNV = function(){
    $.ajax({
       url : "AdminServlet",
       type : "GET",
       data : {
                        "action" : "loadallnhanvien",page: 1
                },
        dataType : "json",
        success: function (data) {
            var totalPages = data.totalpage;
            var currentPage = data.currentpage;
            $('#qlnv_nhanvien tbody').html(data.data);
            $pagination.twbsPagination('destroy');
            $pagination.twbsPagination($.extend({}, defaultOpts, {
                startPage: currentPage,
                totalPages: totalPages,
                onPageClick: function (event, page) {
                   $.ajax({
                        type: "GET",
                        url:"AdminServlet",
                        dataType : "json",
                        data:{"action":"loadallnhanvien","page":page},
                        success: function (data) {
                            $('#qlnv_nhanvien tbody').html(data.data);
                        }
                    });
                }
            }));
        }
  });
};
loadNhom();
$('#btn_luunv').on('click',saveDon);