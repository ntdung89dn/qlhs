/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('ul.navbar-nav li a').click(function(e) {
    $('.navbar li.active').removeClass('active');
    var $this = $(this).parent();
    if (!$this.hasClass('active')) {
        $this.addClass('active');
        var id = $(this).parent().prop('id');
        if(id === 'li_nhanvien'){
            $('#content').load('admin/nhanvien.jsp').fadeIn("slow");
            $.getScript( "./js/nhanvien.js");
        }else if(id==='li_nhom'){
            $('#content').load('admin/nhom.jsp').fadeIn("slow");
             $.getScript( "./js/nhom.js");
        }else if(id==='li_phanquyen'){
            $('#content').load('admin/phanquyen.jsp').fadeIn("slow");
            $.getScript( "./js/phanquyen.js");
        }else if(id==='li_loaidon'){
            $('#content').load('admin/loaidon.jsp').fadeIn("slow");
            $.getScript( "./js/loaidon.js");
        }else if(id==='li_logout'){
            window.location.replace("login.jsp");
        }
    }
  });
function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.href.substring(loc.href.indexOf('#')+1);
    
    if(pathName ==='nhanvien'){
        $('#content').load('admin/nhanvien.jsp').fadeIn("slow");
        $.getScript( "./js/nhanvien.js");
    }else if(pathName ==='nhom'){
        $('#content').load('admin/nhom.jsp').fadeIn("slow");
             $.getScript( "./js/nhom.js");
    }else if(pathName ==='phanquyen'){
        $('#content').load('admin/phanquyen.jsp').fadeIn("slow");
        $.getScript( "./js/phanquyen.js");
    }else if(pathName ==='loaidon'){
        $('#content').load('admin/loaidon.jsp').fadeIn("slow");
        $.getScript( "./js/loaidon.js");
    }
 //   return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
getAbsolutePath();