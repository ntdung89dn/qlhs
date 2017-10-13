/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.DonInfor;
import com.ttdk.bean.NhanVien;
import com.ttdk.bean.NhanVienBean;
import com.ttdk.bean.NhapDon;
import com.ttdk.bean.SearchDon;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Thorfinn
 */
public class PhanDonServlet extends HttpServlet {

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
        String table = "";
        String action = request.getParameter("action");
        NhapDon nd = new NhapDon();
        SearchDon sd = new SearchDon();
        if(action.equals("normal")){
            String pass = "";
            String failed = "";
             String[] ttdon = request.getParameterValues("donids[]");
             String nvPhan = request.getParameter("nvPhan");
             String nvNhan = request.getParameter("nvNhan");
             String[] tsdon = request.getParameterValues("taisan[]");
             String ngaynhap = request.getParameter("ngaynhap");
             Calendar now = Calendar.getInstance();
             int yyyy = now.get(Calendar.YEAR);
             int mm = now.get(Calendar.MONTH)+1;
             int dd = now.get(Calendar.DAY_OF_MONTH);
             int hh = now.get(Calendar.HOUR_OF_DAY);
            int min = now.get(Calendar.MINUTE);
              String tgPhan = yyyy+"-"+mm+"-"+dd+" "+hh+":"+min;
              for(int i=0; i < ttdon.length;i++){
                  int id = Integer.parseInt(ttdon[i]);
                  int ts = Integer.parseInt(tsdon[i]);
                try {
                    nd.phanDon(id, nvPhan, nvNhan,ts, tgPhan);
                    int dmid = new NhanVien().checkDinhMuc(nvNhan, ngaynhap);
                    if(dmid == -1){
                        new NhanVien().insertDinhMuc(ts, nvNhan, ngaynhap);
                    }else{
                        new NhanVien().updateDinhMuc(ts, dmid);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PhanDonServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
              }
             response.getWriter().write("OK");
            
        }else if(action.equals("fast")){
            String result = " Đã lưu: ";
            String failed = "Lỗi: ";
            String donIDs = request.getParameter("donid");
            String nvPhan = request.getParameter("nvPhan");
            String nvNhan = request.getParameter("nvNhan");
            String tgPhan = request.getParameter("tgPhan");
            String[] donID = donIDs.split(",");
            String ngaynhap = request.getParameter("ngaynhap");
            for(String donid : donID){
                int id = Integer.parseInt(donid);
                int slts = nd.kiemtraTaiSan(id);
                try {
                    nd.phanDon(id, nvPhan, nvNhan, slts, tgPhan);
                    int dmid = new NhanVien().checkDinhMuc(nvNhan, ngaynhap);
                    if(dmid == -1){
                        new NhanVien().insertDinhMuc(slts, nvNhan, ngaynhap);
                    }else{
                        new NhanVien().updateDinhMuc(slts, dmid);
                    }
                    result += donid;
                } catch (SQLException ex) {
                  //  Logger.getLogger(PhanDonServlet.class.getName()).log(Level.SEVERE, null, ex);
                        failed += donid;
                }
            }
//            for(int i=0;i<donID.length;i++){
//                if(!donID[i].trim().equals("") || donID[i] != null){
//                   // nd.phanDon(i, nvPhan, nvNhan, i, tgPhan);
//                   int id = sd.getDonID(donID[i]);
//                   int slts = nd.kiemtraTaiSan(id);
//                    try {
//                        nd.phanDon(id, nvPhan, nvNhan, slts, tgPhan);
//                        result +=donID[i];
//                    } catch (SQLException ex) {
//                       failed += donID[i];
//                    }
//                }
//            }
            response.getWriter().write(result +"\n"+failed);
        }else if(action.equals("search")){
            int page = Integer.parseInt(request.getParameter("page"));
            String daytime = request.getParameter("daytime");
            String donOnline = request.getParameter("dononline");
            int loainhan = Integer.parseInt(request.getParameter("loainhan"));
            String manhan  = request.getParameter("manhan");
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            table = sd.searchChuaPhan(page,daytime, donOnline, loainhan, manhan,loaidon,loaidk);
            int totalpage = 0;
             if(page ==1){
                totalpage = sd.getPageSearchCP(daytime, donOnline, loainhan, manhan, loaidon, loaidk);
             }
            JSONArray arr = new JSONArray();
              JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                 obj.put("currentpage", page);
                 obj.put("data", table);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
        }else if(action.equals("reload")){
            int currentpage = Integer.parseInt(request.getParameter("page"));
            table = nd.searchChuaPhanDon(currentpage);
        //    System.out.println("asdadasd \n ===============================================================\n"+ table);
       //     System.out.println(" \n ===============================================================\n");
//            int totalpage = nd.getPageDCP();
//            JSONArray arr = new JSONArray();
//              JSONObject obj = new JSONObject();
//            try {
//                obj.put("totalpage",totalpage);
//                 obj.put("currentpage", currentpage);
//                 obj.put("data", table);
//            } catch (JSONException ex) {
//                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            String pagelist = obj.toString();
//            response.getWriter().write(pagelist);
            response.getWriter().write(table);
        }else if(action.equals("checkts")){
            
        }else if(action.equals("get_row")){
            int page = nd.getPageDCP();
            int currentpage = 1;
            JSONArray arr = new JSONArray();
              JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",page);
                 obj.put("currentpage", currentpage);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
        }else if(action.equals("loadtablepd")){
            String date = request.getParameter("date");
            System.out.println("DATE = "+date);
            String tbody = new NhanVien().loadTablenvPhan(date);
            response.getWriter().write(tbody);
        }else if(action.equals("normalnts")){
            String[] ttdon = request.getParameterValues("donids[]");
             String nvPhan = request.getParameter("nvPhan");
             String[] nvNhan = request.getParameterValues("nvNhan[]");
             String[] tsdon = request.getParameterValues("taisan[]");
             String ngaynhap = request.getParameter("ngaynhap");
             int donid = Integer.parseInt(ttdon[0]);
             Calendar now = Calendar.getInstance();
             int yyyy = now.get(Calendar.YEAR);
             int mm = now.get(Calendar.MONTH)+1;
             int dd = now.get(Calendar.DAY_OF_MONTH);
             int hh = now.get(Calendar.HOUR_OF_DAY);
            int min = now.get(Calendar.MINUTE);
              String tgPhan = yyyy+"-"+mm+"-"+dd+" "+hh+":"+min;
             for(int i=0; i < nvNhan.length;i++){
                 int slts = Integer.parseInt(tsdon[i]);
                try {
                    nd.phanDon(donid, nvPhan, nvNhan[i], slts, tgPhan);
                    int dmid = new NhanVien().checkDinhMuc(nvNhan[i], ngaynhap);
                    if(dmid == -1){
                        new NhanVien().insertDinhMuc(slts, nvNhan[i], ngaynhap);
                    }else{
                        new NhanVien().updateDinhMuc(slts, dmid);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PhanDonServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
             response.getWriter().write("OK");
        }else if(action.equals("searchchuaphandon")){
            String ngaynhan = request.getParameter("ngaynhap");
            String maonline = request.getParameter("maonline");
            int loainhan = Integer.parseInt(request.getParameter("loainhan"));
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            String manhan = request.getParameter("manhan");
            int page = Integer.parseInt(request.getParameter("page"));
//            System.out.println(ngaynhan);System.out.println(maonline);System.out.println(loainhan);
//            System.out.println(loaidon);System.out.println(loaidk);System.out.println(manhan);
            String tbody = nd.searchChuaPhanDon(page, ngaynhan, maonline, loainhan, loaidon, loaidk, manhan);
            int totalpage = 0;
             if(page ==1){
                totalpage = nd.pageSCPD(ngaynhan, maonline, loainhan, loaidon, loaidk, manhan);
             }
           JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                 obj.put("currentpage", page);
                 obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
        }else if(action.equals("loadnhanvien")){
            
            ArrayList<NhanVienBean> nhanvien = new NhanVien().getAllnvName();
            int i =1;
            table = "<table><thead><tr><th>STT</th><th>Tên Nhân Viên</th><th> Chọn</th></tr>"
                    + "</thead><tbody>";
            for(NhanVienBean nvb : nhanvien){
                if(nvb.getUsername().equals("admin")) continue;
                else if(nvb.getUsername().equals("minhtuan")) continue;
                table += "<tr>";
                table += "<td>"+i+"</td>";
                table += "<td>"+nvb.getFullname()+"</td>";
                if(nvb.isNhapLieu()){
                    table += "<td><input type='checkbox'  value='"+nvb.getUsername()+"' checked></td>";
                }else{
                     table += "<td><input type='checkbox'  value='"+nvb.getUsername()+"'></td>";
                }
                
                table +="</tr>";
            }
            table += "</tbody></table>";
            response.getWriter().write(table);
        }else if(action.equals("updatenhaplieu")){
            String[] nhanviens = request.getParameterValues("nhanvien[]");
            for(String nhanvien : nhanviens){
                String[] nvs = nhanvien.split("-");
                new NhanVien().updateNVNhapLieu(nvs[0], Integer.parseInt(nvs[1]));
            }
            response.getWriter().write("Lưu thành công");
        }else if(action.equals("loadnhaplieu")){
            NhanVien nv = new NhanVien();
            ArrayList<NhanVienBean> nvPDNs = nv.getNvNhapLieu();
            String option = "";
            for(NhanVienBean nvPDN : nvPDNs){
                option += " <option value='"+nvPDN.getUsername()+"'>"+nvPDN.getFullname() +"</option>";
            }
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
