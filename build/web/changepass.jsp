<%-- 
    Document   : changepass
    Created on : Oct 4, 2017, 10:35:27 AM
    Author     : ntdung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Modal -->
<div id="modal_changepass" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Đổi mật khẩu</h4>
      </div>
      <div class="modal-body">
        <div class="row">
            <div class="col-sm-4">
                <label for="usr">Mật khẩu cũ</label>
            </div>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="cp_passwold">
            </div>
            <div class="col-sm-4">
                  <div id="div_passchange" ></div>
            </div>
          </div>
          <br>
          <div class="row">
            <div class="col-sm-4">
                <label for="usr">Mật khẩu mới</label>
            </div>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="cp_passwnew">
            </div>
          </div>
          <br>
          <div class="row">
            <div class="col-sm-4">
                <label for="usr">Nhập mật khẩu mới</label>
            </div>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="cp_passwnewrepeat">
            </div>
              <div class="col-sm-4">
                  <div id="span_checkpass" ></div>
            </div>
          </div>
      </div>
      <div class="modal-footer">
          <button type="button" class="btn btn-default" id="btn_acceptPass">Đổi mật khẩu</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
      </div>
    </div>

  </div>
</div>
<script>
    var checkpass = false;
    $('#btn_changepass').on('click',function(e){
        e.preventDefault();
        $("#modal_changepass").modal("show");
    });
    $('#btn_acceptPass').on('click',function(){
        var oldpass = $('#cp_passwold').val();
        var newpass = $('#cp_passwnew').val();
        var repeatpass= $('#cp_passwnewrepeat').val();
        if(newpass === repeatpass){
            $('#span_checkpass').html("<span style='color:red;'>Mật khẩu đã trùng</span>");
            $.ajax({
                type: "GET",
                url:"SearchDonServlet",
                data:{"s_page":"checkpass",oldpass : oldpass,newpass: newpass},
                success: function (data) {
                   // $('#dct_duocphan').html(data);
                   $('#div_passchange').html("<span style='color:red;'>"+data+"</span>");
                }
            });
        }else{
            alert("Mật khẩu chưa trùng");
        }
    });
    $('#cp_passwnewrepeat,#cp_passwnew').on('keyup',function(){
        var newpass = $('#cp_passwnew').val();
        var repeatpass= $('#cp_passwnewrepeat').val();
        if(newpass === repeatpass){
            $('#span_checkpass').html("<span style='color:red;'>Mật khẩu đã trùng</span>");
        }else{
            $('#span_checkpass').html("<span style='color:red;'>Mật khẩu chưa trùng</span>");
        }
    });
</script>