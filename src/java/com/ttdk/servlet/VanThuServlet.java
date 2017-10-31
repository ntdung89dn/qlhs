/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.CSGT;
import com.ttdk.bean.VanThu;
import com.ttdk.bean.VanThuCSGT;
import com.ttdk.createFile.ExportExcelCSGT;
import com.ttdk.createFile.ExportExcelVanThu;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Thorfinn
 */
public class VanThuServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        VanThu vt = new VanThu();
        HttpSession session = request.getSession();
//        System.out.println(action);
        if(action.contains("reload")){
             int page = Integer.parseInt(request.getParameter("page"));
             String tbody = vt.tableVanThu(page);
             response.getWriter().write(tbody);
        }else if(action.equals("insert")){
            String ngaygoi = request.getParameter("ngaygoi");
            String noinhan = request.getParameter("noinhan");
            String sobuudien = request.getParameter("sobuudien");
            String sobienlai = request.getParameter("sobienlai");
            String ghichu = request.getParameter("ghichu");
            String donids = request.getParameter("donid");
            if(!ngaygoi.trim().equals("")){
                 String[] daytime = ngaygoi.split("-");
                ngaygoi = daytime[2]+"-"+daytime[1]+"-"+daytime[0];
            }
           int vtid  = 0;
            try {
                     vtid = vt.insertVanThu(ngaygoi, noinhan, sobuudien, sobienlai, ghichu);
                
                String[] donidArr= donids.split(",");
                for(String donidStr : donidArr){
                    int donid = Integer.parseInt(donidStr);
                    vt.insertVTDon(vtid, donid);
                }
            } catch (SQLException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String resultStr = "";
            if(vtid != 0){
            //    response.getWriter().write();
                resultStr = "Nhập văn thư có mã bưu điện "+sobuudien+"  thành công";
            }else{
          //      response.getWriter().write("Nhập văn thư có mã bưu điện "+sobuudien+" không thành công");
                resultStr = "Nhập văn thư có mã bưu điện "+sobuudien+" không thành công";
            }
           JSONObject obj = new JSONObject();
            try {
                obj.put("result",resultStr);
                obj.put("vtid",vtid);
            } catch (JSONException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
          //  System.out.println(tbody);
             response.getWriter().write(pagelist);
        }else if(action.equals("updatevanthu")){
            String ngaygoi = request.getParameter("ngaygoi");
            String noinhan = request.getParameter("noinhan");
            String sobuudien = request.getParameter("sobuudien");
            String sobienlai = request.getParameter("sobienlai");
            String ghichu = request.getParameter("ghichu");
            String donids = request.getParameter("donid");
           System.out.println(ngaygoi);System.out.println(noinhan);System.out.println(sobuudien);
           System.out.println(sobienlai);System.out.println(ghichu);System.out.println(donids);
           int vtid =Integer.parseInt(session.getAttribute("vanthuid").toString()) ;
            try {
                vt.updateVanThu(ngaygoi, noinhan, sobuudien, sobienlai, ghichu,vtid);
                
                String[] donidArr= donids.split(",");
                int count = vt.countDonLuuVT(vtid);
                if( count < donidArr.length){
                    System.out.println("--------------------INSERT -------");
                    for(int i= donidArr.length; i > count;i--){
                        int donid = Integer.parseInt(donidArr[i]);
                        vt.insertVTDon(vtid, donid);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.removeAttribute("vanthuid");
        }else if(action.equals("xemvanthu")){
            String id = request.getParameter("vtid");
            System.out.println(id);
            String tbody = vt.selectDon(id);
        //    System.out.println(tbody);
            response.getWriter().write(tbody);
        }else if(action.equals("search_donvt")){
            int page = Integer.parseInt(request.getParameter("page"));
            String ngaynhandau = request.getParameter("ngaynhandau").replaceAll("/", "-");
            if(ngaynhandau == null){
                ngaynhandau ="";
            }else{
                ngaynhandau = ngaynhandau.replaceAll("/", "-");
            }
            String ngaynhancuoi = request.getParameter("ngaynhancuoi");
            if(ngaynhancuoi == null){
                ngaynhancuoi ="";
            }else{
                ngaynhancuoi = ngaynhancuoi.replaceAll("/", "-");
            }
            int loainhan = Integer.parseInt(request.getParameter("loainhan"));
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            String maloainhan = request.getParameter("maloainhan");
            if(maloainhan == null){
                maloainhan = "";
            }
            String dononline = request.getParameter("dononline");
            if(dononline == null){
                dononline = "";
            }
            String bnbd = request.getParameter("bnbd");
            if(bnbd == null){
                bnbd = "";
            }
            String bbd = request.getParameter("bbd");
            if(bbd == null){
                bbd = "";
            }
            String barcode = request.getParameter("barcode");
            int totalpage = 0;
            if(page == 1){
                totalpage = vt.getSearchPageVT(loainhan, maloainhan, loaidon, loaidk, ngaynhandau, ngaynhancuoi, dononline, bnbd, bbd,barcode);
            }
            
            String tbody = vt.tableSVanThu(page,loainhan,maloainhan,loaidon,loaidk, ngaynhandau,ngaynhancuoi, dononline, bnbd, bbd,barcode);
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data",tbody);
            } catch (JSONException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
          //  System.out.println(tbody);
             response.getWriter().write(pagelist);
        }else if(action.equals("svtluu_normal")){
            int page = Integer.parseInt(request.getParameter("page"));
            String ngaynhap = request.getParameter("ngaynhap");
            String mabuudien = request.getParameter("mabuudien");
            String noinhan = request.getParameter("noinhan");
            String sobienlai = request.getParameter("sobienlai");
            int totalpage = vt.getPageNormalSearch(ngaynhap, mabuudien, noinhan, sobienlai);
            String tbody = vt.normalSearch(page,ngaynhap, mabuudien, noinhan, sobienlai);
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data",tbody);
            } catch (JSONException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
        }else if(action.equals("svtluu_more")){
            int page = Integer.parseInt(request.getParameter("page"));
            String ngaynhap = request.getParameter("ngaynhap");
            String mabuudien = request.getParameter("mabuudien");
            String noinhan = request.getParameter("noinhan");
            String sobienlai = request.getParameter("sobienlai");
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            String dononline = request.getParameter("dononline");
            int loaihinhnhan = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String maloainhan = request.getParameter("maloainhan");
            String bnbd = request.getParameter("bnbd");
            String bbd  = request.getParameter("bbd");
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int loainhan = Integer.parseInt(request.getParameter("loaihinhnhan"));
            if(maloainhan.trim().equals("") && bnbd.trim().equals("") && bbd.trim().equals("") && dononline.trim().equals("") ){
                int totalpage = vt.getPageNormalSearch(ngaynhap, mabuudien, noinhan, sobienlai);
                String tbody = vt.normalSearch(page,ngaynhap, mabuudien, noinhan, sobienlai);
                JSONObject obj = new JSONObject();
                try {
                    obj.put("totalpage",totalpage);
                    obj.put("currentpage",page);
                    obj.put("data",tbody);
                } catch (JSONException ex) {
                    Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                String pagelist = obj.toString();
                response.getWriter().write(pagelist);
            }else{
                
                int totalpage = vt.getPageSearchMore(maloainhan, dononline, bnbd, bbd);
                String tbody = vt.searchMore(page,ngaynhap, mabuudien, noinhan, sobienlai, maloainhan, dononline, bnbd, bbd);
          //      System.out.println("more : "+tbody);
               JSONObject obj = new JSONObject();
                try {
                    obj.put("totalpage",totalpage);
                    obj.put("currentpage",page);
                    obj.put("data",tbody);
                } catch (JSONException ex) {
                    Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
                }
                String pagelist = obj.toString();
                response.getWriter().write(pagelist);
            }
            //System.out.println(ngaynhap);System.out.println(mabuudien);System.out.println(noinhan);System.out.println(sobienlai);
        }else if(action.equals("getrow_vt")){
            int totalpage = vt.getPageVT();
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",1);
            } catch (JSONException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
          //  System.out.println(tbody);
             response.getWriter().write(pagelist);
        }else if(action.equals("loadvtluu")){
            int page  = Integer.parseInt(request.getParameter("page"));
            int totalpage = 0;
            if(page == 1){
                totalpage = vt.getPageVTLuu();
            }
            String tbody = vt.viewVanThuLuu(page);
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
         //   System.out.println(pagelist);
             response.getWriter().write(pagelist);
        }else if(action.equals("csgtcg")){
            int page = Integer.parseInt(request.getParameter("page"));
            String ngaybatdau = request.getParameter("ngaybatdau");
            if(ngaybatdau == null){
                ngaybatdau = "";
            }
            String ngayketthuc = request.getParameter("ngayketthuc");
            if(ngayketthuc == null){
                ngayketthuc = "";
            }
            String manhan = request.getParameter("manhan");
            if(manhan == null){
                manhan = "";
            }
            String bnbd = request.getParameter("bnbd");
            if(bnbd == null){
                bnbd = "";
            }
            String bbd = request.getParameter("bbd");
             if(bbd == null){
                bbd = "";
            }
             String maonline = request.getParameter("maonline");
             if(maonline == null){
                maonline = "";
            }
             String barcode = request.getParameter("barcode");
             if(barcode == null){
                 barcode= "";
             }
             int loainhan = Integer.parseInt(request.getParameter("loainhan"));
            VanThuCSGT vtgt = new VanThuCSGT();
            int totalpage = 0;
            if(page ==1){
                totalpage =  vtgt.pageLoadCSgtcg(page,ngaybatdau, ngayketthuc, maonline, manhan, bnbd, bbd,barcode,loainhan);
            }
              
            String tbody = vtgt.viewTableCsgtChuaLuuVT(page,ngaybatdau, ngayketthuc, maonline, manhan, bnbd, bbd,barcode,loainhan);
          //  System.out.println(tbody);
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
         //   System.out.println(pagelist);
             response.getWriter().write(pagelist);
        }else if(action.equals("diachi")){
            VanThuCSGT vtgt = new VanThuCSGT();
            String diachi = request.getParameter("diachi");
            ArrayList<ArrayList> data = vtgt.getCsgtDiachi(diachi);
            System.out.println("DATA SIZE = "+data.size());
            
            JSONArray arr = new JSONArray();
            try {
                for(ArrayList dt : data){
                    JSONObject obj = new JSONObject();
                    obj.put("name",dt.get(0));
                    obj.put("diachi",dt.get(1));
                    obj.put("dcid",dt.get(2));
                    arr.put(obj);
                }
                
            } catch (JSONException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = arr.toString();
           System.out.println(pagelist);
             response.getWriter().write(pagelist);
        }if(action.equals("addvtcsgt")){
            VanThuCSGT vtgt = new VanThuCSGT();
            String ngaygoi = request.getParameter("ngaygoi");
            String sobuudien = request.getParameter("sobuudien");
            int dcid = Integer.parseInt(request.getParameter("dcid"));
            String don = request.getParameter("donid");
            String bennhan = request.getParameter("bennhan");
            if(bennhan == null){
                bennhan = "";
            }
            String[] donids = don.split(",");
            int[] donid  = new int[donids.length];
            for(int i=0; i< donids.length;i++){
                donid[i] = Integer.parseInt(donids[i]);
            }
            String ghichu = request.getParameter("ghichu");
            if( ghichu == null){
                ghichu = "";
            }
            
                int vtid  = vtgt.addVtCsgt(ngaygoi, dcid, ghichu, sobuudien);
                     if(vtid !=0){
                         for(int did : donid){
                             vtgt.addDonCSGT(did, vtid);;
                         }
                         response.getWriter().write("Nhập văn thư csgt mã bưu điện "+sobuudien+" thành công");
                     }else{
                         response.getWriter().write("Nhập văn thư csgt mã bưu điện "+sobuudien+" không thành công");
                     }
            
        }else if(action.equals("loadcity")){
            String getCity = new CSGT().getCityData();
            response.getWriter().write(getCity);
        }else if(action.equals("loadvtcsgtluu")){
            VanThuCSGT vtgt = new VanThuCSGT();
            int page = Integer.parseInt(request.getParameter("page"));
            String ngaybatdau = request.getParameter("ngaybatdau");
            if(ngaybatdau == null){
                ngaybatdau = "";
            }
            String ngayketthuc = request.getParameter("ngayketthuc");
            if(ngayketthuc == null){
                ngayketthuc = "";
            }
            String manhan = request.getParameter("manhan");
            if(manhan == null){
                manhan = "";
            }
            String maonline = request.getParameter("maonline");
            if(maonline == null){
                maonline = "";
            }
            String bnbd = request.getParameter("bnbd");
            if(bnbd == null){
                bnbd = "";
            }
            String bbd = request.getParameter("bbd");
             if(bbd == null){
                bbd = "";
            }
            String mabuudien = request.getParameter("mabuudien");
            if(mabuudien == null){
                mabuudien = "";
            } 
            String noinhan = request.getParameter("noinhan");
            if(noinhan == null){
                noinhan = "";
            }
            int cityid = 0;
            try{
                 cityid = Integer.parseInt(request.getParameter("cityid"));
            }catch(Exception ex){
                
            }
            String tbody = vtgt.returnVTCSGT(page, ngaybatdau, ngayketthuc, maonline, manhan, bnbd, bbd, mabuudien, noinhan, cityid);
            int totalpage = 0;
            if(page ==1){
                totalpage = vtgt.totalPageCSGTLuu(page,ngaybatdau, ngayketthuc, maonline, manhan, bnbd, bbd, mabuudien, noinhan, cityid);
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("addmorecsgt")){
            int vtcsgtid = Integer.parseInt(request.getParameter("vtcsgtid"));
            String[] donids = request.getParameterValues("donid[]");

            VanThuCSGT vtgt = new VanThuCSGT();
            for(String donid : donids){
                int id = Integer.parseInt(donid);
                vtgt.addDonCSGT(id, vtcsgtid);
            }
            response.getWriter().write("ADD");
        }else if(action.equals("deldoncsgt")){
            int donid = Integer.parseInt(request.getParameter("donid")) ;
            VanThuCSGT vtgt = new VanThuCSGT();
            vtgt.delDonLuuCSgt(donid);
        }else if(action.equals("delmultidon")){
            String[] donids =  request.getParameterValues("donid[]");
            VanThuCSGT vtgt = new VanThuCSGT();
            for(String donid : donids){
                int id = Integer.parseInt(donid);
                vtgt.delDonLuuCSgt(id);
            }
        }else if(action.equals("csgtblexcel")){
            String ngaybatdau = request.getParameter("ngaybatdau");
            if(ngaybatdau == null){
                ngaybatdau = "";
            }
            String ngayketthuc = request.getParameter("ngayketthuc");
            if(ngayketthuc == null){
                ngayketthuc = "";
            }
            String manhan = request.getParameter("manhan");
            if(manhan == null){
                manhan = "";
            }
            String maonline = request.getParameter("maonline");
            if(maonline == null){
                maonline = "";
            }
            String bnbd = request.getParameter("bnbd");
            if(bnbd == null){
                bnbd = "";
            }
            String bbd = request.getParameter("bbd");
             if(bbd == null){
                bbd = "";
            }
            String mabuudien = request.getParameter("mabuudien");
            if(mabuudien == null){
                mabuudien = "";
            } 
            String noinhan = request.getParameter("noinhan");
            if(noinhan == null){
                noinhan = "";
            }
            int cityid = Integer.parseInt(request.getParameter("cityid"));
            HSSFWorkbook workbook = new ExportExcelCSGT().exportVanThuExcel(ngaybatdau, ngayketthuc, maonline, manhan, bnbd, bbd, mabuudien, noinhan, cityid);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Thongkecsgt"+ngaybatdau+"-"+ngayketthuc+".xls");
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
        }else if(action.equals("updatevtcsgt")){
            String ngaygui = request.getParameter("ngaygui");
            String mabuudien = request.getParameter("mabuudien");
            int csgtdcid = Integer.parseInt(request.getParameter("csgtdcid")) ;
            int vtid = Integer.parseInt(request.getParameter("vtid"));
        //    String bennhan = request.getParameter("bennhan");
      //      String noinhan = request.getParameter("noinhan");
            String ghichu = request.getParameter("ghichu");
            int result = new VanThuCSGT().updateVTCsgt(vtid, ngaygui, csgtdcid, mabuudien, ghichu);
//            System.out.println("VALUE =");
//            System.out.println(ngaygui);System.out.println(mabuudien);System.out.println(bennhan);
//            System.out.println(noinhan);System.out.println(ghichu);
            if(result == 0){
                response.getWriter().write("Có lỗi xảy ra khi cập nhật văn thư csgt");
            }else{
                response.getWriter().write("Cập nhật văn thư csgt thành công");
            }
        }else if(action.equals("xoadonvtluu")){
            String id = request.getParameter("id");
            int donid = Integer.parseInt(id.split("-")[0]);
            int vtdon = Integer.parseInt(id.split("-")[1]);
            int check = vt.xoaDonVTLuu(donid, vtdon);
            response.getWriter().write(check);
        }else if(action.equals("suavanthu")){
            String vtid = request.getParameter("vtid");
         //   System.out.println("Van thu id = "+vtid);
            session.setAttribute("vanthuid", vtid);
           // response.sendRedirect("./vanthu.jsp?page=themvanthu");
        }else if(action.equals("xoavtcsgt")){
            String vtcsgtid = request.getParameter("vtcsgtid");
            new VanThuCSGT().delVTCsgt(Integer.parseInt(vtcsgtid));
        }else if(action.equals("xoadonvavt")){
            String vtcsgtid = request.getParameter("vtcsgtid");
            new VanThuCSGT().delDonLuuCSgtByVTId(Integer.parseInt(vtcsgtid));
            new VanThuCSGT().delVTCsgt(Integer.parseInt(vtcsgtid));
        }else if(action.equals("loadvtsgtedit")){
            int vtcsgtid = Integer.parseInt(request.getParameter("vtid"));
            ArrayList<String> vtinfor = new VanThuCSGT().getVTCSGTByID(vtcsgtid);
            JSONObject obj = new JSONObject();
            try {
                obj.put("ngaygoi",vtinfor.get(0));obj.put("csgtname",vtinfor.get(1));obj.put("csgtdc", vtinfor.get(2));
                obj.put("nguoinhan",vtinfor.get(3));obj.put("ghichu",vtinfor.get(4));obj.put("mabuudien", vtinfor.get(5));
                obj.put("csgtdcid", vtinfor.get(6));
            } catch (JSONException ex) {
                Logger.getLogger(VanThuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("savevtexcel")){
            int vtid  = Integer.parseInt(request.getParameter("vtid"));
            String noinhan = request.getParameter("noinhan");
            HSSFWorkbook workbook =  new ExportExcelVanThu().createExcelVTSave(vtid,noinhan);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=donvanthuluu-"+noinhan+".xls");
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
