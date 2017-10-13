/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.AdminFunction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ntdung
 */
public class AdminServlet extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        AdminFunction af = new AdminFunction();
        HttpSession session=request.getSession();  
        
        if(action.equals("login")){
            String useradmin = request.getParameter("username");
            String password = request.getParameter("passwd");
            if(!useradmin.equals("admin")){
                response.getWriter().write("FAILED");
            }else{
                boolean check = af.loginAdmin(useradmin, password);
                if(check){
                    session.setAttribute("useradmin", useradmin);
                    response.getWriter().write("login");
                }else{
                    response.getWriter().write("FAILED");
                }
            }
        }else if(action.equals("loadallnhanvien")){
            int page = Integer.parseInt(request.getParameter("page")) ;
            int totalpage = 0;
             if(page ==1){
                totalpage = af.getPageNhanVien();
             }
            String tbody = af.loadTableAllNhanvien(page);
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
        }else if(action.equals("loadnvedit")){
            String username = request.getParameter("username");
            ArrayList<String> nvien = af.loadNVEdit(username);
            JSONObject obj = new JSONObject();
            try {
                obj.put("fullname",nvien.get(1));
                obj.put("username",nvien.get(0));
                obj.put("infor", nvien.get(2));
                obj.put("passwd", nvien.get(3));
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("luunhanvien")){
            // Lưu nhân viên ở đây
            System.out.println("LOADING");
            String username = request.getParameter("username");
            String fullname = request.getParameter("fullname");
            String infor = request.getParameter("infor");
            String password = request.getParameter("password");
            af.saveNewNhanVien(username, password,  fullname,  infor);
        }else if(action.equals("saveeditnv")){
            String username = request.getParameter("username");
          //  int iid = Integer.parseInt(request.getParameter("iid")) ;
            String password = request.getParameter("password");
            String infor = request.getParameter("infor");
            String fullname = request.getParameter("fullname");
            af.saveNVEdit(password, fullname,  infor, username);
        }else if(action.equals("loadnhom")){
            String tbody = af.loadNhom();
            String tbodynhanvien = af.loadNhanVienNhom();
            JSONObject obj = new JSONObject();
            try {
                obj.put("nhom",tbody);
                obj.put("nhanvien",tbodynhanvien);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("loadnhomnv")){
            String tbody = af.optionNhom();
            response.getWriter().write(tbody);
        }else if(action.equals("luunhom")){
            String name = request.getParameter("name");
            int role = 0;
            if(request.getParameter("role") != null && !request.getParameter("role").trim().equals("")){
                role = Integer.parseInt(request.getParameter("role"));
            }
            if(role ==0){
                int keyNhom = af.saveNhom(name);
                if(request.getParameterValues("username[]") != null){
                    String[] users = request.getParameterValues("username[]");
                }
            }else{
                af.updateTenNhom(name, role);
                if(request.getParameterValues("username[]") != null){
                    af.deleteNVNhom(role);
                    String[] users = request.getParameterValues("username[]");
                    af.updateRoleNV(users, role);
                }
            }
        }else if(action.equals("loadeditnhom")){
            int roleid = Integer.parseInt(request.getParameter("roleid"));
            ArrayList<String> inforNhom = af.loadEditNhom(roleid);
            JSONObject obj = new JSONObject();
            try {
                obj.put("name",inforNhom.get(1));
                obj.put("roleid",inforNhom.get(0));
                obj.put("nvids", inforNhom.get(2));
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("saveeditnhom")){
            String name = request.getParameter("name");
            int roleid = Integer.parseInt(request.getParameter("roleid"));
            String[] nvlist = request.getParameterValues("nhanvien[]");
            System.out.println(nvlist);
            ArrayList inforNhom = af.loadEditNhom(roleid);
            String name_nhom = inforNhom.get(1).toString();
          //  String[] nvidListCu = inforNhom.get(2).toString().split(",");
            if(!name_nhom.toLowerCase().trim().equals(name.toLowerCase().trim())){
                System.out.println("Fuck u");
            }else{
                  af.deleteNVNhom(roleid);
                  if(nvlist != null ){
                      af.updateRoleNV(nvlist, roleid);
                  }
                 
                
            }
            
        }else if(action.equals("loadphanquyen")){
            String optionNhom = "<option value='0' >Chọn nhóm để phân quyền</option>"+af.optionNhom();
            String tbody = af.loadTableQuyen();
            JSONObject obj = new JSONObject();
            try {
                obj.put("nhom", optionNhom);
                obj.put("quyen", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("savephanquyen")){
            int nhomid = Integer.parseInt(request.getParameter("nhomid"));
            String[] pqids = request.getParameterValues("phanquyenids[]");
            String[] npqids = request.getParameterValues("noquyenid[]");
            if(pqids != null){
                af.savePhanQuyenList(nhomid, pqids, 1);
            }
            if(npqids != null){
                af.savePhanQuyenList(nhomid, npqids, 0);
            }
            
        }else if(action.equals("loadloaidon")){
            String loaidon = af.getTableLoaiDon();
             response.getWriter().write(loaidon);
        }else if(action.equals("loadrolenhom")){
            int nhomid = Integer.parseInt(request.getParameter("nhomid"));
            String roleid = af.getRoleidNhom(nhomid);
            if(roleid == null){
                roleid = "";
            }
            response.getWriter().write(roleid);
        }else if(action.equals("reloadnhom")){
            String tbody = af.loadNhom();
             response.getWriter().write(tbody);
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
