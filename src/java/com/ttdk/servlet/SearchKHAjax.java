/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.google.gson.JsonArray;
import com.ttdk.bean.Khachhang;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Thorfinn
 */
public class SearchKHAjax extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
        Khachhang kh = new Khachhang();
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        if(action.equals("loadkh")){
            int page = Integer.parseInt(request.getParameter("page"));
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String account = request.getParameter("account");
            if(name == null){
                name = "";
            }
            if(address == null){
                address = "";
            }
            if(email == null){
                email = "";
            }
            if( account == null){
                account = "";
            }
            int totalpage = kh.khCount();
            int roleEdit = Integer.parseInt(session.getAttribute("16").toString());
            int roleDel = Integer.parseInt(session.getAttribute("17").toString());
            String tbody = kh.loadKh(page,roleEdit,roleDel);
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
        }else if(action.equals("searchkh")){
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            String account = request.getParameter("account");
            if(name == null){
                name = "";
            }
            if(address == null){
                address = "";
            }
            if(email == null){
                email = "";
            }
            if( account == null){
                account = "";
            }
             int roleEdit = Integer.parseInt(session.getAttribute("16").toString());
            int roleDel = Integer.parseInt(session.getAttribute("17").toString());
            String tbody = kh.searchKh(name, address, email, account,roleEdit,roleDel);
            response.getWriter().write(tbody);
        }else if(action.equals("addkh")){
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            if(email == null){
                email = " ";
            }
            String account = request.getParameter("account");
            if(account == null){
                account = " ";
            }
            kh.addKhachHang(name, address, email, account); 
            response.getWriter().write("ADD");
        }else if(action.equals("deletekh")){
            int khid = Integer.parseInt(request.getParameter("khid"));
            int checkUpdate = kh.deleteKH(khid);
            if(checkUpdate ==0){
                
            }else{
                
            }
        }else if(action.equals("lockkh")){
            int khid = Integer.parseInt(request.getParameter("khid"));
            String isLock = request.getParameter("typelock");
            System.out.println("IS LOCK = "+isLock);
            int checkLock = kh.lockKH(khid,isLock);
            if(checkLock ==0){
                
            }else{
                
            }
        }else if(action.equals("updatekh")){
            String name = request.getParameter("name");
            String address = request.getParameter("diachi");
            String email = request.getParameter("email");
            if(email == null){
                email = " ";
            }
            String account = request.getParameter("taikhoan");
            if(account == null){
                account = " ";
            }
            int khid = Integer.parseInt(request.getParameter("khid"));
            //System.out.println(name);System.out.println(address);System.out.println(email);System.out.println(account);
          //  kh.updateKH(name, address, email, account);
          kh.updateKHList(name, address, account, email, khid);
        }else if(action.equals("editkh")){
            int khid = Integer.parseInt(request.getParameter("khid"));
            ArrayList detail = kh.searchKh(khid);
            JSONObject obj = new JSONObject();
            try {
                obj.put("id",detail.get(0));
                obj.put("name", detail.get(1));
                obj.put("diachi", detail.get(2));
                obj.put("email", detail.get(3));
                obj.put("taikhoan", detail.get(4));
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
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
