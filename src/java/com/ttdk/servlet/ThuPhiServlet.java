/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.BaoCoBean;
import com.ttdk.bean.Khachhang;
import com.ttdk.bean.NhanVien;
import com.ttdk.bean.ThongBaoPhiBean;
import com.ttdk.bean.ThongKe;
import com.ttdk.bean.ThuPhiBean;
import com.ttdk.bean.Thuphi;
import com.ttdk.connect.DBConnect;
import com.ttdk.createFile.ChangeNumberToText;
import com.ttdk.createFile.ExportExcelFile;
import com.ttdk.createFile.SendEmail;
import com.ttdk.createFile.THCNExcel;
import com.ttdk.createFile.ThongBaoPhiPDF;
import com.ttdk.createFile.ThuPhiExcel;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author ntdung
 */
public class ThuPhiServlet extends HttpServlet {

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
        Thuphi tp = new Thuphi();
        HttpSession session = request.getSession();
        if(action.equals("loadbnbdctp")){
            String fromday = request.getParameter("fromday");
            String today  = request.getParameter("today");
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int loaihinhnhan = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String manhan = request.getParameter("manhan");
            String sothongbaophi = request.getParameter("sothongbaophi");
            String bnbd = request.getParameter("bnbd");
            String table = tp.returnTableBNBD_chuaTP(fromday, today, manhan, sothongbaophi, loaidon,bnbd);
            //System.out.println("TABLE = "+table);
            response.getWriter().write(table);
        }else if(action.equals("loadbbdctp")){
            String fromday = request.getParameter("fromday");
            String today  = request.getParameter("today");
           // int khdid = Integer.parseInt(request.getParameter("khdid"));
           String donids = request.getParameter("donids");
            System.out.println(donids);
            if(fromday.equals("")){
                if(today.equals("")){
                    Calendar now = Calendar.getInstance();   // Gets the current date and time
                    int year = now.get(Calendar.YEAR);      // The current year as an int
                    int month = now.get(Calendar.MONTH) +1;
                    if(month ==1){
                        month =12;
                        year = year - 1;
                    }
                    fromday = year+"-"+month+"-"+1;
                }
            }
            String table = tp.returnBBD_CTP(fromday, today, donids);
            response.getWriter().write(table);
        }else if(action.equals("luuthuphi")){
            
            // đơn thu phí
            String ngaynop = request.getParameter("ngaynop");
            String sohieu = request.getParameter("sohieu");
            String tendonvi = request.getParameter("tendonvi");
            String diachi = request.getParameter("diachitdv");
            String lydo = request.getParameter("lydo");
            String donids = request.getParameter("donids");
            int tongtien = Integer.parseInt(request.getParameter("tongtien"));
            String nguoiplbc = request.getParameter("nguoiplbc");
            String typesavebc = request.getParameter("typesavebc");
           // int total = Integer.parseInt(request.getParameter("tongtien"));
                int loaithanhtoan = Integer.parseInt(request.getParameter("loaithanhtoan"));
         //       System.out.println("ASdasdasdsad = "+loaithanhtoan);
                Connection conn = new DBConnect().dbConnect();
                
                String result = "";
                try {
                    conn.setAutoCommit(false);
                    int key = tp.insertThuPhi(ngaynop, sohieu, tendonvi, diachi, lydo, loaithanhtoan,tongtien,conn);
                            String[] don = donids.split(",");
                            for(String donid : don){
                                int idInt = Integer.parseInt(donid);
                                tp.insertDonTP(key, idInt,conn);
                                    result = "ok";
                            }
                        if(Integer.parseInt(typesavebc) ==2){
                                // lưu báo có
                            String ngaybaoco =request.getParameter("ngaybc");
                            String sobc = request.getParameter("sobc");
                            int sotien = Integer.parseInt(request.getParameter("sotienbc"));
                            int tienthua = tongtien - sotien;
                            String noidung = request.getParameter("ndbaoco");
                            String bcids = request.getParameter("bcids");
                            String[] bcidList = bcids.split(",");
                            for(String bcid : bcidList){
                                int id = Integer.parseInt(bcid);
                                tp.insertBCnBL(id, key, conn);
                            }
                        }
                        
                    long total = Long.parseLong(String.valueOf(tongtien));
                    String thanhtoan = "";
                    switch(loaithanhtoan){
                        case 1 : thanhtoan = "Trực tiếp";
                            break;
                        case 2: thanhtoan = "Chuyển khoản";
                            break;
                        default:
                            break;
                    }
                    String tienchu = new ChangeNumberToText().convert(total);
                    session.setAttribute("tendonvi", tendonvi);session.setAttribute("diachi", diachi);
                    session.setAttribute("loaithanhtoan", thanhtoan);session.setAttribute("tongtien", tongtien);
                    session.setAttribute("tienchu", tienchu);session.setAttribute("lydo", lydo);
                    String username = session.getAttribute("username").toString();
                    //System.out.println("USERNAME = "+username);
                    String fullname = new NhanVien().getFullName(username);
                    session.setAttribute("nvien", fullname);
                    conn.commit();
                    result = "OK";
                } catch (SQLException ex) {
                    Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex);
                    result = "FAILED";
                    try {
                        conn.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }finally{
                    try {
                        conn.setAutoCommit(true);
                        new DBConnect().closeAll(conn, null);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                response.getWriter().write(result);
        }else if(action.equals("loadnopphi")){
            String ngaybatdau = request.getParameter("ngaybatdau");
            String ngayketthuc = request.getParameter("ngayketthuc");
            int loaihinhnhan = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String manhan = request.getParameter("manhan");
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            String sohieu = request.getParameter("sohieu");
            String bennopphi = request.getParameter("bennopphi");
//            System.out.println(ngaybatdau);System.out.println(ngayketthuc);System.out.println(loaihinhnhan);
//            System.out.println(manhan);System.out.println(loaidk);
            String tbody = tp.returnBenDTP(ngaybatdau, ngayketthuc, loaihinhnhan, manhan, loaidk, sohieu,bennopphi);
       //     System.out.println("TBODY = "+tbody);
            response.getWriter().write(tbody);
        }else if(action.equals("loadbtpdon")){
            String sohieu = request.getParameter("sohieu");
            int dontpid = Integer.parseInt(request.getParameter("dtpid"));
            BaoCoBean bcb = tp.viewBaoCo(dontpid);
            String ngaybc = bcb.getNgaybc();
            String bcnumber = bcb.getBcnumber();
            int sotien = bcb.getSotien();
            int tienthua = bcb.getTienthua();
            String noidung = bcb.getNoidung();
            
          //  System.out.println(sohieu);
            String tbody = tp.getDonNopPhi(dontpid);
            JSONObject obj = new JSONObject();
            try {
                obj.put("ngaybc",ngaybc);
                obj.put("bcnumber", bcnumber);
                obj.put("sotien", sotien);
                obj.put("tienthua", tienthua);
                obj.put("noidung", noidung);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
        }else if(action.equals("loadthpsohieu")){
            String dtpid = request.getParameter("sohieu");
            ThuPhiBean tpb = tp.getTPTheoSohieu(Integer.parseInt(dtpid));
            String tbody = tp.getDonNopPhi(Integer.parseInt(dtpid));
            JSONObject obj = new JSONObject();
//            System.out.println(tpb.getNgaytp());System.out.println(tpb.getSohieu());
//            System.out.println(tpb.getTendonvi());System.out.println(tpb.getTdvdiachi());
//            System.out.println(tpb.getLydo());System.out.println(tpb.getLoaitt());System.out.println(tpb.getTongtien());
            try {
                obj.put("ngaytp",tpb.getNgaytp());
                obj.put("sohieu", tpb.getSohieu());
                obj.put("tdv", tpb.getTendonvi());
                obj.put("diachi", tpb.getTdvdiachi());
                obj.put("lydo", tpb.getLydo());
                obj.put("loaitt", tpb.getLoaitt());
                obj.put("donis", tpb.getDonids());
                obj.put("tongtien", tpb.getTongtien());
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            System.out.println(pagelist);
            response.getWriter().write(pagelist);
        }else if(action.equals("searchthongkebienlai")){
            int page = Integer.parseInt(request.getParameter("page"));
            String ngaybatdau =request.getParameter("ngaybatdau");
            String ngayketthuc = request.getParameter("ngayketthuc");
            String sobienlai = request.getParameter("sobienlai");
            int loaitt = Integer.parseInt(request.getParameter("loaitt"));
            String tbody = tp.viewSearchThongkeBienlai(page, ngaybatdau, ngayketthuc, sobienlai, loaitt);
             JSONObject obj = new JSONObject();
             int totalpage = 0;
             if(page ==1){
               totalpage = tp.getPageSearchTKBL(page, ngaybatdau, ngayketthuc, sobienlai, loaitt);
            }
            Double tongcong = tp.getTongTien(ngaybatdau, ngayketthuc, sobienlai, loaitt);
             Double tongBD = tp.getTongBD(ngaybatdau, ngayketthuc, sobienlai, loaitt);
             Double tongTT = tp.getTongTT(ngaybatdau, ngayketthuc, sobienlai, loaitt);
             Double tongBS = tp.getTongBS(ngaybatdau, ngayketthuc, sobienlai, loaitt);
             ChangeNumberToText changeNumber = new ChangeNumberToText();
             tbody += "<tr><td colspan='10'>Tổng cộng</td><td>"+changeNumber.priceWithDecimal(tongcong)+"</td>"
                     + "<td>"+changeNumber.priceWithDecimal(tongBD)+"</td>"
                     + "<td>"+changeNumber.priceWithDecimal(tongTT)+"</td>"
                     + "<td>"+changeNumber.priceWithDecimal(tongBS)+"</td><td></td></tr>";
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("thongkebienlai")){
            int page = Integer.parseInt(request.getParameter("page"));
            String tbody = tp.viewThongkeBienlai(page);
             JSONObject obj = new JSONObject();
             int totalpage = 0;
             if(page ==1){
                totalpage = tp.getPageTKBL();
             }
             Double tongcong = tp.getTongTien("","","",0);
             Double tongBD = tp.getTongBD("","","",0);
             Double tongTT = tp.getTongTT("","","",0);
             Double tongBS = tp.getTongBS("","","",0);
             tbody += "<tr><td colspan='10'>Tổng cộng</td><td>"+tongcong+"</td><td>"+tongBD+"</td><td>"+tongTT+"</td><td>"+tongBS+"</td><td></td></tr>";
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("tkblexcel")){
            String ngaybatdau =request.getParameter("ngaybatdau");
            String ngayketthuc = request.getParameter("ngayketthuc");
            String sobienlai = request.getParameter("sobienlai");
            int loaitt = Integer.parseInt(request.getParameter("loaitt"));
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Thongkebienlai.xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ExportExcelFile().exportExcelTKBL(ngaybatdau, ngayketthuc, sobienlai, loaitt);
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
        }else if(action.equals("thongkebaoco")){
            int page = 1;
            if(request.getParameter("page") != null){
                page = Integer.parseInt(request.getParameter("page"));
            }
            String ngaybaoco = request.getParameter("ngaybaoco");
            String tbody = tp.viewTKBC(page,ngaybaoco);
             JSONObject obj = new JSONObject();
             int totalpage = 0;
             if(page ==1){
                totalpage = tp.getPageTKBC(ngaybaoco);
             }
             String tongcong = tp.getTongTienBC(ngaybaoco, "", "", "", "", 0);
             tbody += tongcong;
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("searchbaoco")){
            String ngaybatdau =request.getParameter("ngaybatdau");
            String ngayketthuc = request.getParameter("ngayketthuc");
            String sobaoco =request.getParameter("sobaoco");
            String bnbd = request.getParameter("bnbd");
            String taikhoan = request.getParameter("taikhoan");
            String khids = "";
            if(!bnbd.trim().equals("") || !taikhoan.trim().equals("")){
                khids = new Khachhang().getKHID(bnbd, taikhoan).toString().replaceAll("\\]", "").replaceAll("\\[", "");
            }
            int loaitienthua = Integer.parseInt(request.getParameter("loaitienthua"));
            String tbody = tp.viewSearchTKBC(ngaybatdau, ngayketthuc, sobaoco, khids, loaitienthua);
            System.out.println("TBODY = "+tbody);
             response.getWriter().write(tbody);
        }else if(action.equals("thongkebcexcel")){
            String ngaybatdau =request.getParameter("ngaybatdau");
            String ngayketthuc = request.getParameter("ngayketthuc");
            String sobaoco =request.getParameter("sobaoco");
            String bnbd = request.getParameter("bnbd");
            String taikhoan = request.getParameter("taikhoan");
            int loaitienthua = Integer.parseInt(request.getParameter("loaitienthua"));
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Thongkebaoco_"+ngaybatdau+"_den_"+ngayketthuc+".xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ExportExcelFile().exportExcelTKBC(ngaybatdau, ngayketthuc, sobaoco, taikhoan, bnbd, loaitienthua);
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
        }else if(action.equals("viewtkbldon")){
            String sohieu = request.getParameter("sohieu");
         //   System.out.println(donids);
            String tbody = tp.viewChitetBL(sohieu);
            response.getWriter().write(tbody);
        }else if(action.equals("tonghopcongno")){
            String ngayketthuc = request.getParameter("ngayketthuc");
            int current_page = Integer.parseInt(request.getParameter("page"));
            String ngaybatdau = "";
         //   System.out.println("ngayketthuc = "+ngayketthuc);
            if(ngayketthuc.equals("")){
                Date today = new Date(); 
                Calendar cal = Calendar.getInstance();
                cal.setTime(today); 
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH)+1;
                int year = cal.get(Calendar.YEAR);
                ngaybatdau = "01-"+month+"-"+year;
                ngayketthuc = dayOfMonth +"-"+month+"-"+year;
            }else{
                String[] ngaySearch = ngayketthuc.split("-");
                ngaybatdau = "01-"+ngaySearch[1]+"-"+ngaySearch[2];
            }
            String tbody = tp.getTonghopCongno(ngaybatdau, ngayketthuc,current_page);
            //String tbody = tp.viewTHCN(ngaybatdau, ngayketthuc);
            JSONObject obj = new JSONObject();
            int totalpage = 1;
            if(current_page == 1){
                totalpage = tp.getPageTKBC(ngaybatdau);
            }
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",current_page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("thcnexcel")){
            String ngayketthuc = request.getParameter("ngayketthuc");
            String ngaybatdau = "";
            
            if(ngayketthuc.equals("")){
                Date today = new Date(); 
                Calendar cal = Calendar.getInstance();
                cal.setTime(today); 
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH)+1;
                int year = cal.get(Calendar.YEAR);
                ngaybatdau = "01-"+month+"-"+year;
                ngayketthuc = dayOfMonth +"-"+month+"-"+year;
            }else{
                String[] ngaySearch = ngayketthuc.split("-");
                ngaybatdau = "01-"+ngaySearch[1]+"-"+ngaySearch[2];
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Tonghopcongno"+ngayketthuc+".xls");
            OutputStream out = response.getOutputStream();
            System.out.println("ngayketthuc = "+ngayketthuc);
            HSSFWorkbook workbook = new THCNExcel().exportVanThuExcel(ngaybatdau, ngayketthuc);
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
        }else if(action.equals("loadkhmail")){
            String[] khListid = request.getParameterValues("khlist[]");
            String[] donid = request.getParameterValues("donid[]");
//            for(String kh :  khListid){
//                System.out.println(kh);
//            }
            Date today = new Date(); 
            Calendar cal = Calendar.getInstance();
            cal.setTime(today); 
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH)+1;
            int year = cal.get(Calendar.YEAR);
            String  title = "Thông báo nợ "+ dayOfMonth +"-"+month+"-"+year;
            String emailBody = tp.optionEmail();
            String tbody = tp.tbodyKh(khListid,donid);
            JSONObject obj = new JSONObject();
            try {
                obj.put("email",emailBody);
                obj.put("title",title);
                obj.put("data",tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
            
        }else if(action.equals("baophinoinfor")){
            String ngaybp = request.getParameter("ngaytao");
            String ngayttoan = request.getParameter("ngayttoan");
            String taikhoan = request.getParameter("taikhoan");
            String nguoilienhe = request.getParameter("nguoilienhe");
            String nguoichiutn = request.getParameter("nguoichiutn");
            int chucvu = Integer.parseInt(request.getParameter("chucvu"));
            System.out.println(ngaybp);System.out.println(ngayttoan);System.out.println(taikhoan);
            System.out.println(nguoilienhe);System.out.println(nguoichiutn);System.out.println(chucvu);
           
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
            tp.inserBPInfor(taikhoan, nguoilienhe, nguoichiutn, chucvu, taikhoan);
      //      HttpSession session = request.getSession();
            session.setAttribute("ngaybp", ngaybp);
            session.setAttribute("ngayttoan", ngayttoan);
            session.setAttribute("taikhoan", taikhoan);
            session.setAttribute("nguoilienhe", nguoilienhe);
            session.setAttribute("nguoichiutn", nguoichiutn);
            switch(chucvu){
                case 1: session.setAttribute("chucvu", "Giám đốc");
                case 2: session.setAttribute("chucvu", "Phó giám đốc");
            }
            response.getWriter().write("OK");
        }else if(action.equals("loadallttbp")){
            String option = tp.loadAllTTBP();
            response.getWriter().write(option);
        }else if(action.equals("loadchucvu")){
            String option = tp.loadChucVu();
            response.getWriter().write(option);
        }else if(action.equals("addInforTBP")){
            String shortname = request.getParameter("shortname");
            String taikhoan = request.getParameter("taikhoan");
            String nguoilh = request.getParameter("nguoilienhe");
            String nguoictn = request.getParameter("nguoichiutn");
            int cvid = Integer.parseInt(request.getParameter("chucvu")) ;
            System.out.println(shortname);System.out.println(taikhoan);System.out.println(nguoilh);
            System.out.println(nguoictn);System.out.println(cvid);
            int keySn = tp.checkShortName(shortname);
            if(keySn ==0){
                int keytk = tp.checkTKNH(taikhoan);
                if(keytk ==0){
                    keytk = tp.saveTKNH(taikhoan);
                }
                int keyNLH = tp.checkNguoiLH(nguoilh);
                if(keyNLH ==0){
                    keyNLH = tp.saveNguoiLienHe(nguoilh);
                }
                int keyntcn =  tp.checkNguoiCTN(nguoictn, cvid);
                if(keyntcn == 0){
                    keyntcn = tp.saveNguoichiuTN(nguoictn, cvid);
                }
                tp.saveThongtinUse(shortname, keytk, keyNLH, keyntcn);
                response.getWriter().write("OK");
            }else{
                response.getWriter().write("FAIL");
            }
            
        }else if(action.equals("loadSaveInfor")){
            int saveInforId = Integer.parseInt(request.getParameter("saveInforId"));
            ArrayList infor = tp.loadTTBPInfor(saveInforId);
           JSONObject obj = new JSONObject();
            String taikhoan = infor.get(0).toString();
            String nguoilh = infor.get(1).toString();
            String nguoictn = infor.get(2).toString();
            int cvid = Integer.parseInt(infor.get(3).toString());
            try {
                obj.put("taikhoan",taikhoan);
                obj.put("nguoilh",nguoilh);
                obj.put("nguoictn", nguoictn);
                obj.put("cvid", cvid);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
            
        }else if(action.equals("baophiInfor")){
            String ngaybp = request.getParameter("ngaytao");
            String ngayttoan = request.getParameter("ngayttoan");
            int inforId = Integer.parseInt(request.getParameter("saveInforId"));
            String taikhoan = request.getParameter("taikhoan");
            String nguoilienhe = request.getParameter("nguoilienhe");
            String nguoichiutn = request.getParameter("nguoichiutn");
            int chucvu = Integer.parseInt(request.getParameter("chucvu"));
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
            
            session.setAttribute("inforid", inforId);
            session.setAttribute("ngaybp", ngaybp);
            session.setAttribute("ngayttoan", ngayttoan);
            session.setAttribute("taikhoan", taikhoan);
            session.setAttribute("nguoilienhe", nguoilienhe);
            session.setAttribute("nguoichiutn", nguoichiutn);
            switch(chucvu){
                case 1: session.setAttribute("chucvu", "GIÁM ĐỐC");
                case 2: session.setAttribute("chucvu", "PHÓ GIÁM ĐỐC");
            }
        }else if(action.equals("guithongbaono")){
            Date today = new Date(); 
            Calendar cal = Calendar.getInstance();
            cal.setTime(today); 
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH)+1;
            int year = cal.get(Calendar.YEAR);
            String ngaybaono = dayOfMonth +"-"+month+"-"+year;
            String ngayhethan = "";
            String[] thongtin = request.getParameterValues("thongtin[]");
            String email = request.getParameter("email");
            
            for(String ttin :  thongtin){
                String[] tt = ttin.split("-");
                String sender = new Khachhang().getEmailKH(Integer.parseInt(tt[0]));
                boolean check = new SendEmail().email(Integer.parseInt(tt[0]), tt[1], ngaybaono,ngayhethan,email,sender);
                if(check){
                    response.getWriter().write("OK");
                }else{
                    response.getWriter().write("FAIL");
                }
            }
            
        }else if(action.equals("loaddonchuacotk")){
            int page = Integer.parseInt(request.getParameter("page"));
            String tbody = tp.tableDCCSTK(page, "", "", "", 0, 0, 0, "", "", "");
            int totalpage = 0;
             if(page ==1){
                totalpage = tp.getPageDonCCSTK("", "", "", 0, 0, 0, "", "", "");
             }
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("searchdonchuacotk")){
            int page = Integer.parseInt(request.getParameter("page"));
            String ngaynhap  = request.getParameter("ngaybatdau");
            String ngayketthuc = request.getParameter("ngayketthuc");
            String maonline = request.getParameter("maonline");
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int loainhan = Integer.parseInt(request.getParameter("loainhan"));
            String manhan = request.getParameter("manhan");
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            String bnbd = request.getParameter("bnbd");
            String bbd = request.getParameter("bbd");
            String tbody = tp.tableDCCSTK(page, ngaynhap, ngayketthuc, maonline, loaidon, loaidk, loainhan, manhan, bnbd, bbd);
            int totalpage = 0;
             if(page ==1){
                totalpage = tp.getPageDonCCSTK(ngaynhap, ngayketthuc, maonline, loaidon, loaidk, loainhan, manhan, bnbd, bbd);
             }
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("loadthongtinhoso")){
            String ngaybatdau = request.getParameter("ngaybatdau");
            String ngayketthuc = request.getParameter("ngayketthuc");
            String tbody = tp.viewTSHoCKTK(ngaybatdau, ngayketthuc);
            response.getWriter().write(tbody);
        }else if(action.equals("laytongtien")){
            String bienlais = request.getParameter("bienlais");
            //String donids = tp.getDonidsTP(bienlais);
            String tongtien = tp.getTongTien(bienlais);
            response.getWriter().write(String.valueOf(tongtien));
        }else if(action.equals("loadInforbdtp")){
            String bienlais = request.getParameter("bienlais");
//            System.out.println(name);
//            System.out.println(diachi);
            String donNp = "";
            String sohieuOption = "";
            ArrayList<ArrayList<String>> sohieu = tp.getSoHieu(bienlais);
            ArrayList<String> data =  null;
            for(int i=0; i < sohieu.size();i++){
                sohieuOption += "<option value='"+sohieu.get(i).get(0)+"'>"+sohieu.get(i).get(1)+"</option>";
                if(i ==0){
                    donNp = tp.getBBDNopPhi(sohieu.get(i).get(0));
                    data = tp.loadBaoCoVaBienLai(sohieu.get(i).get(0));
                }
            }
            System.out.println(sohieuOption);
          //  System.out.println(data.toString());
            JSONObject obj = new JSONObject();
            try {
                obj.put("sohieuop",sohieuOption);
                obj.put("donnopphi",donNp);
//                data.add(dtpid);data.add(tendvi);data.add(diachi);data.add(sohieu);data.add(lydo);
//                data.add(ngaybienlai);data.add(bienlaitotal);data.add(loaithanhtoan);data.add(bcid);
//                data.add(ngaybc);data.add(bcnumber);data.add(bctotal);data.add(bcsodu);data.add(noidungbc);
              //  System.out.println("Số hiệu : "+data.get(3));
                obj.put("dtpid",data.get(0));obj.put("tendvi",data.get(1));obj.put("diachi",data.get(2));obj.put("sohieu",data.get(3));obj.put("lydo",data.get(4));
                obj.put("ngaybienlai",data.get(5));obj.put("bienlaitotal",data.get(6));obj.put("loaithanhtoan",data.get(7));obj.put("bcid",data.get(8));
                obj.put("ngaybc",data.get(9));obj.put("bcnumber",data.get(10));obj.put("bctotal",data.get(11));obj.put("bcsodu",data.get(12));
                obj.put("noidungbc",data.get(13));obj.put("khname",data.get(14));obj.put("khaddress",data.get(15));obj.put("khaccount",data.get(16));
                obj.put("khid",data.get(17));
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
         //   System.out.println(pagelist);
             response.getWriter().write(pagelist);
            
        }else if(action.equals("loadtbp")){
            String month = request.getParameter("month");
            String year = request.getParameter("year");
            String sohieu = request.getParameter("sohieu");
            if(month == null){
                month = "0";
            }
            if(year == null){
                year = "0";
            }
            String bnbd = request.getParameter("sohieu");
            String tbody = "";
            tbody = tp.returnTableTBP(Integer.parseInt(month), Integer.parseInt(year), sohieu,bnbd);
            response.getWriter().write(tbody);
        }else if(action.equals("luuthongbaophi")){
            String[] inforbps = request.getParameterValues("inforlist[]");
            ArrayList<Integer> tbpidList = new ArrayList<>();
            for(String infors : inforbps){
                System.out.println(infors);
                String[] infor = infors.split("_");
                int sotbp = Integer.parseInt(infor[0]);
                String ngaygui = infor[1]; String ngayhh = infor[2];
                int intbpid = Integer.parseInt(infor[3]);
                String[] donids = infor[4].split(",");
                int tbpid = tp.saveTBp(sotbp, ngaygui, ngayhh, intbpid);
                tbpidList.add(tbpid);
                if(tbpid !=0){
                    tp.saveDonTBP(donids, tbpid);
                }
            }
           session.setAttribute("tbpids", tbpidList);
            response.getWriter().write("OK");
        }else if(action.equals("loaddonstbp")){
            String donids = request.getParameter("id");
            String result = "FAIL";
            if(session.getAttribute("ngayttoan") != null){
                session.setAttribute("id", donids);
                result = "OK";
            }
            response.getWriter().write(result);
            
          //  response.sendRedirect("./print/print_thongbaophi.jsp");
        }else if(action.equals("loadsohieu")){
            String sohieu = request.getParameter("sohieu");
            int month = Integer.parseInt(request.getParameter("thang"));
            int year = Integer.parseInt(request.getParameter("nam"));
            String bnbd = request.getParameter("bnbd");
            String tbody = tp.returnTableTBP(month, year, sohieu, bnbd);
            response.getWriter().write(tbody);
        }else if(action.equals("loaddontbp")){
            String donids = request.getParameter("donids");
            String tbody = tp.returnTableTBPDon(donids);
            response.getWriter().write(tbody);
        }else if(action.equals("loadtbpdaluu")){
            String[] tbpid = request.getParameterValues("tbpid[]");
            String tbp_id = "";
            for(int i=0;i < tbpid.length;i++){
                if(i==0){
                    tbp_id += tbpid[i];
                }else{
                    tbp_id += "_"+tbpid[i];
                }
            }
            session.setAttribute("id", tbp_id);
        }else if(action.equals("loadbienlai")){
            String sohieu = request.getParameter("sohieu");
            
        }else if(action.equals("loademailtbp")){
            String[] khids = request.getParameterValues("khid[]");
            String[] tbpids = request.getParameterValues("tbpid[]");
            String tbody = tp.tbodyKh(khids,tbpids);
            String  title = "Trung tâm gửi thông báo phí ";
            String emailBody = tp.optionEmail();
            JSONObject obj = new JSONObject();
            try {
                obj.put("email",emailBody);
                obj.put("title",title);
                obj.put("data",tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.contains("guithongbaophi")){
            String thongtin = request.getParameter("thongtin");
            int khid = Integer.parseInt(thongtin.split("-")[0]);
            int tbpid = Integer.parseInt(thongtin.split("-")[1]);
            String email = request.getParameter("email");
            String sender = new Khachhang().getEmailKH(khid);
            System.out.println(khid);System.out.println(tbpid);System.out.println(email);
            boolean check = new SendEmail().emailTBP(khid, tbpid, email, sender);
           //boolean check = false;
                if(check){
                    response.getWriter().write("OK");
                }else{
                    response.getWriter().write("FAIL");
                }
        }else if(action.equals("printbienlai")){
            String tendonvi = request.getParameter("tendonvi");
            String diachi = request.getParameter("diachi");
            String lydo = request.getParameter("lydo");
            int thanhtoan = Integer.parseInt(request.getParameter("thanhtoan"));
            String loaithanhtoan = "";
            switch(thanhtoan){
                case 1 : loaithanhtoan = "Trực tiếp";
                    break;
                case 2: loaithanhtoan = "Chuyển khoản";
                    break;
                default:
                    break;
            }
            String tongtien = request.getParameter("tongtien");
            long total = Long.parseLong(tongtien);
            String tienchu = new ChangeNumberToText().convert(total);
            session.setAttribute("tendonvi", tendonvi);session.setAttribute("diachi", diachi);
            session.setAttribute("loaithanhtoan", loaithanhtoan);session.setAttribute("tongtien", tongtien);
            session.setAttribute("tienchu", tienchu);session.setAttribute("lydo", lydo);
            String username = session.getAttribute("username").toString();
            //System.out.println("USERNAME = "+username);
            String fullname = new NhanVien().getFullName(username);
            session.setAttribute("nvien", fullname);
            response.getWriter().write("OK");
        }else if(action.equals("exportCTPBNBD")){
            String fromday = request.getParameter("fromday");
            if(fromday == null){
                fromday = "";
            }
            String today  = request.getParameter("today");
            if(today == null){
                today = "";
            }
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            String manhan = request.getParameter("manhan");
            if(manhan == null){
                manhan = "";
            }
            String sothongbaophi = request.getParameter("sothongbaophi");
            if(sothongbaophi == null){
                sothongbaophi = "";
            }
            String bnbd = request.getParameter("bnbd");
            if(bnbd == null){
                bnbd = "";
            }
            ArrayList<ThuPhiBean> listTpb = tp.loadBTP_chuaTP(fromday, today, manhan, sothongbaophi,loaidon, bnbd);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=bnbd_chuathuphi.xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ThuPhiExcel().exportChuaThuPhiExcelByBnbd(listTpb,"TỔNG HỢP CHƯA THU PHÍ");
            workbook.write(out);
            out.close();
        }else if(action.equals("exportCTPBBD")){
            String fromday = request.getParameter("fromday");
            if(fromday == null){
                fromday = "";
            }
            String today  = request.getParameter("today");
            if(today == null){
                today = "";
            }
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            String manhan = request.getParameter("manhan");
            if(manhan == null){
                manhan = "";
            }
            String sothongbaophi = request.getParameter("sothongbaophi");
            if(sothongbaophi == null){
                sothongbaophi = "";
            }
            int bnbd = Integer.parseInt(request.getParameter("searchBNBD")) ;
            ArrayList kh = new Khachhang().searchKh(bnbd);
            String khang = kh.get(1)+" ("+kh.get(4)+")";
            String donids = tp.loadDonidsCTP(fromday, today, manhan, sothongbaophi, loaidon, bnbd);
            ArrayList<ThuPhiBean> tbp = tp.loadBBD_chuaTP(donids);
            
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=bbd_chuathuphi.xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ThuPhiExcel().exportChuaThuPhiExcelByBbd(tbp,khang,"TỔNG HỢP CHƯA THU PHÍ");
            workbook.write(out);
            out.close();
        }else if(action.equals("exportCTPAll")){
            String fromday = request.getParameter("fromday");
            if(fromday == null){
                fromday = "";
            }
            String today  = request.getParameter("today");
            if(today == null){
                today = "";
            }
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            String manhan = request.getParameter("manhan");
            if(manhan == null){
                manhan = "";
            }
            String sothongbaophi = request.getParameter("sothongbaophi");
            if(sothongbaophi == null){
                sothongbaophi = "";
            }
           // int bnbd = Integer.parseInt(request.getParameter("searchBNBD")) ;
           String bnbd = request.getParameter("searchBNBD");
           if(bnbd == null){
                bnbd = "";
            }
            ArrayList<ThuPhiBean> tbp = tp.loadBTP_chuaTP(fromday, today, manhan, sothongbaophi, loaidon,bnbd);
            ArrayList<String> searchData = new ArrayList<>();
            searchData.add(fromday);searchData.add(today);searchData.add(manhan);
            searchData.add(sothongbaophi);searchData.add(String.valueOf(loaidon));
            HSSFWorkbook workbook = new ThuPhiExcel().exportChuaThuPhiExcelAll(tbp,searchData,"TỔNG HỢP CHƯA THU PHÍ");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=bbd_chuathuphi.xls");
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        }else if(action.equals("exportDTPBNBD")){
            String fromday = request.getParameter("fromday");
            if(fromday == null){
                fromday = "";
            }
            String today  = request.getParameter("today");
            if(today == null){
                today = "";
            }
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            int loaihinhnhan = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String manhan = request.getParameter("manhan");
            if(manhan == null){
                manhan = "";
            }
            String sohieu = request.getParameter("sohieu");
            if(sohieu == null){
                sohieu = "";
            }
            String bnbd = request.getParameter("searchBNBD");
            if(bnbd == null){
                bnbd = "";
            }
         //   ArrayList<ThuPhiBean> listTpb = tp.loadBTP_chuaTP(fromday, today, manhan, sothongbaophi,loaidon, bnbd);
            ArrayList<ArrayList<String>> bnbdList = tp.loadBTP(fromday, today, loaihinhnhan, manhan, loaidk, sohieu,bnbd); //11-22% disk
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=bnbd_thuphi.xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ThuPhiExcel().exportThuPhiExcelByBnbd(bnbdList, "TỔNG HỢP THU PHÍ", fromday, today,sohieu);
            workbook.write(out);
            out.close();
        }else if(action.equals("exportDTPBBD")){
            String fromday = request.getParameter("fromday");
            if(fromday == null){
                fromday = "";
            }
            String today  = request.getParameter("today");
            if(today == null){
                today = "";
            }
            String sohieu = request.getParameter("sohieu");
            if(sohieu == null){
                sohieu = "";
            }
            String bnbd = request.getParameter("searchBNBD") ;
           // System.out.println("BNBD = "+bnbd);
            String name = bnbd.split("_")[0];
            String diachi = bnbd.split("_")[1];
            
            ArrayList<ThuPhiBean> tbp = tp.loadDTPInfor(name, diachi, fromday, today, sohieu);
            
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=bbd_thuphi.xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ThuPhiExcel().exportThuPhiExcelByBbd(tbp,name+" _ "+diachi,"TỔNG HỢP THU PHÍ",fromday,today);
            workbook.write(out);
            out.close();
        }else if(action.equals("exportDTPAll")){
            String fromday = request.getParameter("fromday");
            if(fromday == null){
                fromday = "";
            }
            String today  = request.getParameter("today");
            if(today == null){
                today = "";
            }
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            int loaihinhnhan = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String manhan = request.getParameter("manhan");
            if(manhan == null){
                manhan = "";
            }
            String sohieu = request.getParameter("sohieu");
            if(sohieu == null){
                sohieu = "";
            }
            String bnbd = request.getParameter("searchBNBD");
            if(bnbd == null){
                bnbd = "";
            }
            String bbd = "";
            String donids  = tp.getInforDTPByDon(bbd, manhan, loaihinhnhan);
            HSSFWorkbook workbook = new ThuPhiExcel().exportThuPhiExcelAll(donids, "TỔNG HỢP THU PHÍ", fromday, today, sohieu,bnbd);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=bbd_thuphi.xls");
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        }else if(action.equals("exportThuphiCuc")){
            String fromday = request.getParameter("fromday");
            String today = request.getParameter("today");
            int loaihinhnhan = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String loaithanhtoan = request.getParameter("loaithanhtoan");
            String donids = tp.getDonidsDTP(fromday, today, loaithanhtoan);
            ArrayList<ArrayList<String>> donList = tp.loadDuLieuchoCuc(loaihinhnhan, donids);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=bbd_thuphi.xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ThuPhiExcel().exportDTPCuc(donList);
            workbook.write(out);
            out.close();
        }else if(action.equals("deletedontp")){
            String donid = request.getParameter("donid");
            int result = tp.DeleteDonTP(Integer.parseInt(donid));
            String resultStr = "";
            if(result !=0){
                resultStr = "OK";
            }else{
                resultStr = "FAILED";
            }
            response.getWriter().write(resultStr);
        }else if(action.equals("luubaoco")){
            String sobaoco = request.getParameter("sobaoco");
            String ngaybaoco = request.getParameter("ngaybaoco");
            String nguoiplbaoco = request.getParameter("nguoiplbaoco");
            String sotienbc = request.getParameter("sotienbc");
            String ndbaoco = request.getParameter("ndbaoco");
            boolean khCheck  =  Boolean.getBoolean(request.getParameter("khcheck"));
            int khid = 0;
            if(khCheck){
                khid = Integer.parseInt(request.getParameter("khid"));
            }
            Connection conn = new DBConnect().dbConnect();
            int result = 0;
            try {
                conn.setAutoCommit(false);
                result = tp.insertBaoCo(ngaybaoco, sobaoco, Integer.parseInt(sotienbc), Integer.parseInt(sotienbc), ndbaoco, nguoiplbaoco,conn);
                if(khCheck){
                    if(result >0){
                        tp.insertKHIDBC(khid, result, conn);
                    }else{
                        conn.rollback();
                       // conn.commit();
                    }
                }
                conn.commit();
                
            } catch (SQLException ex) {
                Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }finally{
                    try {
                        new DBConnect().closeAll(conn, null);
                    } catch (SQLException ex) {
                        Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
            System.out.println(result);
            String resultStr = "";
            if(result !=0){
                resultStr = "OK";
            }else{
                resultStr = "FAILED";
            }
            response.getWriter().write(resultStr);
        }else if(action.equals("searchbaocoluu")){
            String ngaythang = request.getParameter("ngaythang");
            String sobc = request.getParameter("sobc");
            String khname = request.getParameter("khname");
            String account = request.getParameter("account");
            String khids = "";
            if(!khname.trim().equals("") || !account.trim().equals("")){
                ArrayList<String> data = new Khachhang().getKHID(khname, account);
                khids = data.toString().replaceAll("\\[", "").replaceAll("\\]", "");
            }
            String nguoipl = request.getParameter("nguoipl");
            int page = Integer.parseInt(request.getParameter("page"));
            int totalpage = 1;
            if(page ==1){
                totalpage = tp.getPageSearchBCLuu(ngaythang, sobc, khids, nguoipl);
            }
            String tbody = tp.viewSearchBC(ngaythang, sobc,khids, nguoipl,page);
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("data",tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
         //   System.out.println(pagelist);
             response.getWriter().write(pagelist);
        }else if(action.equals("loadxoabcluu")){
            String bcluuid = request.getParameter("bcluu");
            System.out.println(bcluuid);
            String tbody = tp.viewXoaBC(bcluuid);
            response.getWriter().write(tbody);
        }else if(action.equals("loadbaococtp")){
            String bcid= request.getParameter("bcid");
            ArrayList<String> bcInfor = tp.getBCInforByID(bcid).get(0);
            ArrayList<String> khInfor = tp.getKhID(Integer.parseInt(bcid));
            String ngaybc = bcInfor.get(1);
            String bcnumber = bcInfor.get(2);
            String nguoipl = bcInfor.get(3);
            String bcthua =  bcInfor.get(4);
            String total = bcInfor.get(5);
            String ndbc = bcInfor.get(6);
            String khname = "", account = "",address = "",khid="";
            if(!khInfor.isEmpty()){
                khname = khInfor.get(0);account = khInfor.get(1);address = khInfor.get(2);
                khid = khInfor.get(3);
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("ngaybc",ngaybc);obj.put("bcnumber",bcnumber);
                obj.put("nguoipl",nguoipl);obj.put("bcthua",bcthua);
                obj.put("total",total);obj.put("khname",khname);
                obj.put("account",account);obj.put("address",address);obj.put("ndbc",ndbc);
                obj.put("khid",khid);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
         //   System.out.println(pagelist);
             response.getWriter().write(pagelist);
        }else if(action.equals("updatebaoco")){
            String ngaybc = request.getParameter("ngaybc");
            int bcid = Integer.parseInt(request.getParameter("bcid"));
            String sobc = request.getParameter("sobc");
            String nguoipl = request.getParameter("nguoipl");
            String ndbc = request.getParameter("ndbc");
            int khid = Integer.parseInt(request.getParameter("khid"));
            int sotien = Integer.parseInt(request.getParameter("sotien"));
            boolean check  =  Boolean.valueOf(request.getParameter("khsave"));
            boolean isreturnBank  = Boolean.valueOf(request.getParameter("isreturnBank"));
            int returnBank = 0;
            int bctienthua = sotien ;
            if(isreturnBank){
                bctienthua = 0;
                returnBank = 1;
            }
            System.out.println("CHECK = "+check);
            String result = "";
            Connection conn = new DBConnect().dbConnect();
            try {
                conn.setAutoCommit(false);
                tp.updateBaoCo(ngaybc, sobc, sotien, bctienthua, ndbc, bcid,nguoipl,returnBank,conn);
                boolean checkKH = tp.selectBCKH(bcid, khid);
                System.out.println("CHECK = "+checkKH);
                if(check){
                    if(!checkKH){
                        tp.insertKHIDBC(khid, bcid, conn);
                    }else{
                        tp.updateBCKH(bcid, khid, conn);
                    }
                    
                }else{
                    if(checkKH){
                        tp.deleteBCKH(bcid, khid, conn);
                    }
                }
                result = "OK";
            } catch (SQLException ex) {
                Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex);
                result = "FAILED";
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }finally{
                try {
                    conn.commit();
                    new DBConnect().closeAll(conn, null);
                } catch (SQLException ex) {
                    Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            response.getWriter().write(result);
        }else if(action.equals("updatebaocodtp")){
            int khid = Integer.parseInt(request.getParameter("khid"));
            String ngaybc = request.getParameter("ngaybc");
            String sobc  = request.getParameter("sobc");
            String nguoipl = request.getParameter("nguoipl");
            String ndbc = request.getParameter("ctp_ndbaoco");
            int sotien = Integer.parseInt(request.getParameter("sotien"));
            int tienthua = Integer.parseInt(request.getParameter("tienthua"));
            boolean saveKh = Boolean.parseBoolean(request.getParameter("savekh"));
            int bcid =  Integer.parseInt(request.getParameter("bcid"));
            Connection conn = new DBConnect().dbConnect();
            int result  =  tp.updateBaoCo(ngaybc, sobc, sotien, tienthua, ndbc, bcid, nguoipl, 0, conn);
            String resultStr = "";
            if(saveKh){
               tp.updateBCKH(bcid, khid, conn);
            }
            try {
                new DBConnect().closeAll(conn, null);
            } catch (SQLException ex) {
                Logger.getLogger(ThuPhiServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(result ==0){
                resultStr = "Cập nhật báo có thành công";
            }else{
                resultStr = "Cập nhật báo có không thành công";
            }
            response.getWriter().write(resultStr);
        }else if(action.equals("updatebienlaidtp")){
            String ngaynop = request.getParameter("ngaynop");
            String sohieu = request.getParameter("sohieu");
            String tendonvi = request.getParameter("tendonvi");
            String diachi = request.getParameter("diachi");
            String lydo = request.getParameter("lydo");
            int tongtien = Integer.parseInt(request.getParameter("tongtien"));
            int thanhtoan = Integer.parseInt(request.getParameter("thanhtoan"));
            int bienlaiid = Integer.parseInt(request.getParameter("blid"));
            int result = tp.updateThuPhi(tendonvi, diachi, lydo, thanhtoan, tongtien, sohieu, ngaynop, bienlaiid);
            String resultStr  = "";
            if(result ==0){
                resultStr = "<br> Cập nhật biên lai thành công";
            }else{
                resultStr = "<br> Cập nhật biên lai không thành công";
            }
            response.getWriter().write(resultStr);
        }else if(action.contains("downloadtbp")){
            //String[] inforbps = request.getParameterValues("inforlist[]");
            ArrayList<Integer> tbplist = (ArrayList<Integer>) session.getAttribute("tbpids");
          //  System.out.println("TBP ID = "+tbplist.toString().replace("[", "").replace("]", ""));
            ArrayList<ThongBaoPhiBean> data = tp.getDataTbp(tbplist.toString().replace("[", "").replace("]", ""));
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=thongbaophi.pdf");
            OutputStream outputStream = response.getOutputStream();
            new ThongBaoPhiPDF().createPDFTBP(outputStream, data);
            outputStream.close();
        }else if(action.contains("loadallbaoco")){
            String bienlai = request.getParameter("bienlaiid");
            String option = tp.viewBaoCoByBLID(Integer.parseInt(bienlai));
            response.getWriter().write(option);
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
