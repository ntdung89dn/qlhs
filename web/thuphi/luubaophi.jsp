<%-- 
    Document   : luubaophi
    Created on : Jan 5, 2017, 9:13:01 PM
    Author     : ntdung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="panel panel-default">
            <!-- Default panel contents -->
            <div class="panel-heading">Thông tin thu phí</div>
            <form>
            <div class="panel-body">
                <div class="form-group row">
                    <div class="col-xs-1">
                    </div>
                    <label for="ctp_ngaybaoco" class="col-xs-2 col-form-label">Ngày BC:</label>
                    <div class="col-xs-3">
                      <input class="form-control" type="text" value="" id="ctp_ngaybaoco">
                    </div>
                    <label for="ctp_sobaoco" class="col-xs-1 col-form-label">Số BC:</label>
                    <div class="col-xs-3">
                      <input class="form-control" type="text" value="" id="ctp_sobaoco">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-xs-1">
                    </div>
                    <label for="ctp_sotien" class="col-xs-2 col-form-label">Số tiền:</label>
                    <div class="col-xs-3">
                      <input class="form-control" type="text" value="" id="ctp_sotien">
                    </div>
                    <label for="ctp_tienthua" class="col-xs-1 col-form-label">Tiền thừa:</label>
                    <div class="col-xs-3">
                      <input class="form-control" type="text" value="" id="ctp_tienthua">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-xs-1">
                    </div>
                    <label for="ctp_ndbaoco" class="col-xs-2 col-form-label">ND BD:</label>
                    <div class="col-xs-7">
                      <input class="form-control" type="text" value="" id="ctp_ndbaoco">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-xs-1">
                    </div>
                    <label for="ctp_ngaynop" class="col-xs-2 col-form-label">Ngày nộp:</label>
                    <div class="col-xs-3">
                      <input class="form-control" type="text" value="" id="ctp_ngaynop">
                    </div>
                    <label for="ctp_sohieu" class="col-xs-1 col-form-label">Số hiệu:</label>
                    <div class="col-xs-3">
                      <input class="form-control" type="text" value="" id="ctp_sohieu">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-xs-1">
                    </div>
                    <label for="ctp_tendonvi" class="col-xs-2 col-form-label">Tên đơn vị:</label>
                    <div class="col-xs-7">
                      <input class="form-control" type="text" value="" id="ctp_tendonvi">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-xs-1">
                    </div>
                    <label for="ctp_diachi" class="col-xs-2 col-form-label">Địa chỉ:</label>
                    <div class="col-xs-7">
                      <input class="form-control" type="text" value="" id="ctp_diachi">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-xs-1">
                    </div>
                    <label for="ctp_lydonop" class="col-xs-2 col-form-label">Lý do nộp:</label>
                    <div class="col-xs-7">
                      <input class="form-control" type="text" value="" id="ctp_lydonop">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-xs-1">
                    </div>
                    
                    <div class="col-xs-7">
                        <label class="custom-control custom-radio">
                            <input id="ctprd_tructiep" name="radio" type="radio" class="custom-control-input">
                            <span class="custom-control-indicator"></span>
                            <span class="custom-control-description">Trực tiêp</span>
                        </label>
                        <label class="custom-control custom-radio">
                            <input id="ctprd_chuyenkhoan" name="radio" type="radio" class="custom-control-input">
                            <span class="custom-control-indicator"></span>
                            <span class="custom-control-description">Chuyển khoản</span>
                        </label>
                        <label class="custom-control custom-checkbox">
                            <input type="checkbox" id="ctpcb_thongbaophi" class="custom-control-input">
                            <span class="custom-control-indicator"></span>
                            <span class="custom-control-description">Thông báo phí</span>
                        </label>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-xs-1">
                    </div>
                    <label for="ctp_tongtien" class="col-xs-2 col-form-label">Tổng tiền:</label>
                    <div class="col-xs-3">
                        <input class="form-control" type="text" value="" id="ctp_tongtien">
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-xs-3">
                    </div>
                    <div class="col-xs-3">
                        <button type="button" id="btn_ctpSave" class="btn btn-primary" style="width: 100%">Lưu lại</button>
                    </div>
                    
                    <div class="col-xs-3">
                        <button type="button" id="btn_ctpReset" class="btn btn-primary" style="width: 100%">Hủy bỏ</button>
                    </div>
                </div>
            </div>
        </form>
        </div>
    </body>
</html>
