/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.HieuSuat;
import com.ttdk.bean.NhapDon;
import com.ttdk.bean.ThongKe;
import com.ttdk.createFile.ExportExcelFile;
import com.ttdk.createFile.ExportExcelPhanDon;
import com.ttdk.createFile.ExportExcelVanThu;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ntdung
 */
public class ThongKeServlet extends HttpServlet {

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
        ThongKe tk = new ThongKe();
       if(action.equals("nhapdon")){
           int page = Integer.parseInt(request.getParameter("page"));
           String ngaybatdau = request.getParameter("ngaybatdau");
           String type = request.getParameter("type");
           if(ngaybatdau == null){
//               if(type == null || !type.equals("search")){
//                   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy ");
//                    Date date = new Date();
//                    long nowDay =  date.getTime();
//                    long before3day = nowDay - (3 * 24 * 60 * 60 * 1000);
//
//                    date.setTime(before3day);
//                    ngaybatdau = dateFormat.format(date);
//               }else{
//                   ngaybatdau = "";
//               }
                ngaybatdau = "";
           }
           String ngayketthuc= request.getParameter("ngayketthuc");
           if(ngayketthuc == null){
               ngayketthuc = "";
           }
           String maonlineS= request.getParameter("maonline");
           if(maonlineS == null){
               maonlineS = "";
           }
           int loaidonS = 0;
           if(request.getParameter("loaidon") != null){
               loaidonS = Integer.parseInt(request.getParameter("loaidon"));
           }
          // int loaidonS = Integer.parseInt(request.getParameter("loaidon"));
          int loaihinhnhanS = 0;
           if(request.getParameter("loaihinhnhan") != null){
               loaihinhnhanS = Integer.parseInt(request.getParameter("loaihinhnhan"));
           }
//           int loaihinhnhanS = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String manhanS = request.getParameter("manhan");
            if(manhanS == null){
               manhanS = "";
           }
         //   int loaidkS = Integer.parseInt(request.getParameter("loaidk"));
            int loaidkS = 0;
           if(request.getParameter("loaidk") != null){
               loaidkS = Integer.parseInt(request.getParameter("loaidk"));
           }
            String bnbdS = request.getParameter("bnbd");
            if(bnbdS == null){
               bnbdS = "";
           }
            String btpS = request.getParameter("btp");
            if(btpS == null){
               btpS = "";
           }
            String bbdS = request.getParameter("bbd");
             if(bbdS == null){
                bbdS = "";
              }
             ArrayList<Integer> donids =  new ArrayList<>();
            if(!bnbdS.trim().equals("")){
                donids.addAll(new NhapDon().searchDonIDByBNBD(bnbdS,page)) ;
            }
            if(!bbdS.trim().equals("")){
                donids.addAll(new NhapDon().searchDonIDByBBD(bbdS,page));
            }
            String tbody = tk.viewThongKeNhapDon(page, ngaybatdau, ngayketthuc, maonlineS, loaidonS,
                    loaihinhnhanS, manhanS, loaidkS, btpS, donids);
          //  System.out.println(tbody);
          JSONObject obj = new JSONObject();
          try {
            int totalpage = 0;
             if(page ==1){
                totalpage = tk.getPageTKND(page,ngaybatdau, ngayketthuc, maonlineS, loaidonS, 
                    loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                //thống kê dữ liệu đơn
                int totaldon = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, loaidonS, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                // thống kê dữ liệu theo loại hình nhận
                int totalce = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, loaidonS, 1, manhanS, loaidkS, btpS, donids);
                int totalcf = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, loaidonS, 2, manhanS, loaidkS, btpS, donids);
                int totalct = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, loaidonS, 3, manhanS, loaidkS, btpS, donids);
                int totalcb= tk.countDon(ngaybatdau, ngayketthuc, maonlineS, loaidonS, 4, manhanS, loaidkS, btpS, donids);
                // thống kê theo loại đơn
                int totalld = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 1, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totaltd = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 2, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalxoa = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 3, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalvbxl = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 4, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalcctt = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 5, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalbs = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 6, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalTBKBTHA = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 7, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalmp = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 8, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalcsgt = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 9, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalcsgttd = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 10, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalcsgtx = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 11, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalcsgto = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 12, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                int totalcsgtotd = tk.countDon(ngaybatdau, ngayketthuc, maonlineS, 13, loaihinhnhanS, manhanS, loaidkS, btpS, donids);
                obj.put("tongdon", totaldon);
                obj.put("ce", totalce);
                obj.put("cf", totalcf);
                obj.put("ct", totalct);
                obj.put("cb", totalcb);
                obj.put("ld", totalld);
                obj.put("td", totaltd);
                obj.put("xoa", totalxoa);
                obj.put("vbxl", totalvbxl);
                obj.put("cctt", totalcctt);
                obj.put("bs", totalbs);
                obj.put("tbkb", totalTBKBTHA);
                obj.put("mp", totalmp);
                obj.put("csgt", totalcsgt);
                obj.put("csgttd", totalcsgttd);
                obj.put("csgtx", totalcsgtx);
                obj.put("csgto", totalcsgto);
                obj.put("csgtotd", totalcsgtotd);
             }
                obj.put("totalpage",totalpage);
                obj.put("currentpage", page);
                
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
           response.getWriter().write(pagelist);
       }else if(action.equals("tkexcel")){
           String ngaybatdau = request.getParameter("ngaybatdau");
           if(ngaybatdau == null){
               ngaybatdau = "";
           }
           String ngayketthuc= request.getParameter("ngayketthuc");
           if(ngayketthuc == null){
               ngayketthuc = "";
           }
           String maonlineS= request.getParameter("maonline");
           if(maonlineS == null){
               maonlineS = "";
           }
           int loaidonS = 0;
           if(request.getParameter("loaidon") != null){
               loaidonS = Integer.parseInt(request.getParameter("loaidon"));
           }
          // int loaidonS = Integer.parseInt(request.getParameter("loaidon"));
          int loaihinhnhanS = 0;
           if(request.getParameter("loaihinhnhan") != null){
               loaihinhnhanS = Integer.parseInt(request.getParameter("loaihinhnhan"));
           }
//           int loaihinhnhanS = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String manhanS = request.getParameter("manhan");
            if(manhanS == null){
               manhanS = "";
           }
         //   int loaidkS = Integer.parseInt(request.getParameter("loaidk"));
            int loaidkS = 0;
           if(request.getParameter("loaidk") != null){
               loaidkS = Integer.parseInt(request.getParameter("loaidk"));
           }
            String bnbdS = request.getParameter("bnbd");
            if(bnbdS == null){
               bnbdS = "";
           }
            String btpS = request.getParameter("btp");
            if(btpS == null){
               btpS = "";
           }
            String bbdS = request.getParameter("bbd");
             if(bbdS == null){
                bbdS = "";
              }
           response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Thongkebienlai.xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ExportExcelFile().exportTkNhapdon(ngaybatdau, ngayketthuc,
                    maonlineS, loaidonS, loaihinhnhanS, manhanS, loaidkS, bnbdS, btpS, bbdS);
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
       }else if(action.equals("vanthu")){
           int page = Integer.parseInt(request.getParameter("page"));
           String ngaybatdau = request.getParameter("ngaybatdau");
           if(ngaybatdau == null){
               ngaybatdau = "";
           }
           String ngayketthuc= request.getParameter("ngayketthuc");
           if(ngayketthuc == null){
               ngayketthuc = "";
           }
           String maonlineS= request.getParameter("maonline");
           if(maonlineS == null){
               maonlineS = "";
           }
           int loaidonS = 0;
           if(request.getParameter("loaidon") != null){
               loaidonS = Integer.parseInt(request.getParameter("loaidon"));
           }
          // int loaidonS = Integer.parseInt(request.getParameter("loaidon"));
          int loaihinhnhanS = 0;
           if(request.getParameter("loaihinhnhan") != null){
               loaihinhnhanS = Integer.parseInt(request.getParameter("loaihinhnhan"));
           }
//           int loaihinhnhanS = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String manhanS = request.getParameter("manhan");
            if(manhanS == null){
               manhanS = "";
           }
         //   int loaidkS = Integer.parseInt(request.getParameter("loaidk"));
            int loaidkS = 0;
           if(request.getParameter("loaidk") != null){
               loaidkS = Integer.parseInt(request.getParameter("loaidk"));
           }
            String bnbdS = request.getParameter("bnbd");
            if(bnbdS == null){
               bnbdS = "";
           }
            String btpS = request.getParameter("btp");
            if(btpS == null){
               btpS = "";
           }
            String bbdS = request.getParameter("bbd");
             if(bbdS == null){
                bbdS = "";
              }
             String mabuudien = request.getParameter("mabuudien");
             if(mabuudien == null){
                mabuudien = "";
              }
             ArrayList<Integer> donids =  new ArrayList<>();
            if(!bnbdS.trim().equals("")){
                donids.addAll(new NhapDon().searchDonIDByBNBD(bnbdS,page)) ;
            }
            if(!bbdS.trim().equals("")){
                donids.addAll(new NhapDon().searchDonIDByBBD(bbdS,page));
            }
             String tbody = tk.returnTableTkVanthu(page, ngaybatdau, ngayketthuc, maonlineS, loaidonS, loaihinhnhanS,
                     manhanS, loaidkS,  btpS,  mabuudien,donids);
             JSONObject obj = new JSONObject();
             int totalpage = 0;
             if(page ==1){
                totalpage = tk.getTotalPagetkvt(page, ngaybatdau, ngayketthuc, maonlineS, loaidonS,
                     loaihinhnhanS, manhanS, loaidkS,  btpS,  mabuudien,donids);
             }
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage", page);
                obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
           response.getWriter().write(pagelist);
       }else if(action.equals("tkexcelvanthu")){
           String ngaybatdau = request.getParameter("ngaybatdau");
           if(ngaybatdau == null){
               ngaybatdau = "";
           }
           String ngayketthuc= request.getParameter("ngayketthuc");
           if(ngayketthuc == null){
               ngayketthuc = "";
           }
           String maonlineS= request.getParameter("maonline");
           if(maonlineS == null){
               maonlineS = "";
           }
           int loaidonS = 0;
           if(request.getParameter("loaidon") != null){
               loaidonS = Integer.parseInt(request.getParameter("loaidon"));
           }
          // int loaidonS = Integer.parseInt(request.getParameter("loaidon"));
          int loaihinhnhanS = 0;
           if(request.getParameter("loaihinhnhan") != null){
               loaihinhnhanS = Integer.parseInt(request.getParameter("loaihinhnhan"));
           }
//           int loaihinhnhanS = Integer.parseInt(request.getParameter("loaihinhnhan"));
            String manhanS = request.getParameter("manhan");
            if(manhanS == null){
               manhanS = "";
           }
         //   int loaidkS = Integer.parseInt(request.getParameter("loaidk"));
            int loaidkS = 0;
           if(request.getParameter("loaidk") != null){
               loaidkS = Integer.parseInt(request.getParameter("loaidk"));
           }
            String bnbdS = request.getParameter("bnbd");
            if(bnbdS == null){
               bnbdS = "";
           }
            String btpS = request.getParameter("btp");
            if(btpS == null){
               btpS = "";
           }
            String bbdS = request.getParameter("bbd");
             if(bbdS == null){
                bbdS = "";
              }
             String mabuudien = request.getParameter("mabuudien");
             if(mabuudien == null){
                mabuudien = "";
              }
             response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Thongkevanthu-"+ngaybatdau+"-"+ngayketthuc+".xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ExportExcelVanThu().exportVanThuExcel( ngaybatdau, ngayketthuc, 
                    maonlineS, loaidonS, loaihinhnhanS, manhanS, loaidkS, bnbdS, btpS, bbdS, mabuudien);
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
       }else if(action.equals("phandon")){
           String ngaybatdau = request.getParameter("ngaybatdau");
           String ngayketthuc = request.getParameter("ngayketthuc");
            HieuSuat hs = new HieuSuat();
           // String table = hs.tableHS(fromday, today);
           String tbody = hs.viewTableHieuSuat(ngaybatdau, ngayketthuc);
           response.getWriter().write(tbody);
       }else if(action.equals("tkexcelphandon")){
           String ngaybatdau = request.getParameter("ngaybatdau");
           if(ngaybatdau == null){
               ngaybatdau = "";
           }
           String ngayketthuc= request.getParameter("ngayketthuc");
           if(ngayketthuc == null){
               ngayketthuc = "";
           }
             response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Thongkephandon-"+ngaybatdau+"-"+ngayketthuc+".xls");
            OutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = new ExportExcelPhanDon().exportExcelPD(ngaybatdau, ngayketthuc);
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
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
