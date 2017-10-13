/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.DangNhap;
import com.ttdk.bean.SearchDon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Thorfinn
 */
public class SearchDonServlet extends HttpServlet {

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
        SearchDon sd = new SearchDon();
        String action = request.getParameter("s_page");
        HttpSession session=request.getSession();  
    //    String username = session.getAttribute("username").toString();
        
        //System.out.println(searchPage);
        if(action.equals("nhapdon")){
            String day = request.getParameter("daysearch");
            String maOnline = request.getParameter("maonline");
            int manhan = Integer.parseInt(request.getParameter("cb_loaidon"));
            String manhanStr = request.getParameter("ldl_manhan");
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int loaidk = Integer.parseInt(request.getParameter("loaidk")); 
            sd.searchChuaPhan(1,day, maOnline, manhan,manhanStr,loaidon,loaidk);
            
            
        }else if(action.equals("donchotra")){
            String day = request.getParameter("daytime");
            if(!day.trim().equals("")){
                String[] daytime = day.split("/");
                day = daytime[2]+"-"+daytime[1]+"-"+daytime[0];
            }
            String maOnline = request.getParameter("dononline");
            int manhan = Integer.parseInt(request.getParameter("cb_loainhan"));
            String manhanStr = request.getParameter("manhan");
            String nguoinhan  = request.getParameter("nvnhan");
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            int page = Integer.parseInt(request.getParameter("page"));
            String tbody = sd.searchDCT(page,day, maOnline, manhan, manhanStr, nguoinhan,loaidk,loaidon);
            int totalpage = 0;
             if(page ==1){
                totalpage = sd.getpageSDChoTra(day, maOnline, manhan, manhanStr, nguoinhan, loaidk, loaidon);
             }
            System.out.println("Page  = "+totalpage);
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
        }else if(action.equals("dondatra")){
            int page = Integer.parseInt(request.getParameter("page"));
            String day = request.getParameter("daytime").replaceAll("/", "-");
            String maOnline = request.getParameter("dononline");
            int manhan = Integer.parseInt(request.getParameter("cb_loainhan"));
            String manhanStr = request.getParameter("manhan");
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            String nhanviennhan = request.getParameter("nvnhan");
         //   String ngaytradon = request.getParameter("ngaytradon");
    //        String ngayphandon = request.getParameter("ngayphandon");
       //     String tbody = sd.loadSearchDonDaTra(page,day, maOnline, manhan, manhanStr, loaidon, loaidk, nhanviennhan);
            String tbody = sd.viewTableDonDaTra(page, day, maOnline, manhan, manhanStr, loaidon, loaidk, nhanviennhan);
            int totalpage = 0;
             if(page ==1){
                totalpage = sd.getSearchPageDDT(page,day, maOnline, manhan, manhanStr, loaidon, loaidk, nhanviennhan);
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
        }else if(action.equals("load_pageddt")){
            String username = session.getAttribute("username").toString();
            int currentpage = Integer.parseInt(request.getParameter("page"));
            String daytime = request.getParameter("ngayddt");
            String tbody = sd.loadDonDaTra(currentpage,username,daytime);
            int totalpage = 0;
             if(currentpage ==1){
                totalpage = sd.getPageDDT(username,daytime);
             }
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                 obj.put("currentpage", currentpage);
                 obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
        }else if(action.equals("getrow_ddt")){
            String username = session.getAttribute("username").toString();
            String daytime = request.getParameter("ngayddt");
            int totalpage = sd.getPageDDT(username,daytime);
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                 obj.put("currentpage", 1);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
        }else if(action.equals("checkpass")){
            String oldpass = request.getParameter("oldpass");
            String passnew = request.getParameter("newpass");
            String user  =  session.getAttribute("username").toString();
            if(session.getAttribute("username") == null){
                response.sendRedirect("./index.jsp");
            }else{
              ArrayList data =  new DangNhap().checkLogin(user, oldpass);
              if(data !=null){
                  int key =  new DangNhap().updatePassword(user, passnew);
                  if(key ==0){
                      response.getWriter().write("Có lỗi xảy ra, chưa cập nhật được mật khẩu");
                  }else{
                      response.getWriter().write("Cập nhật mật khẩu thành công");
                  }
                  
              }else{
                  response.getWriter().write("Mật khẩu cũ không đúng. Xin vui lòng nhập lại mật khẩu");
              }
            }
            
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
