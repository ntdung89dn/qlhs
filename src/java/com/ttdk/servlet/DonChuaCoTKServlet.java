/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.DonChuaCoTK;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ntdung
 */
public class DonChuaCoTKServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * /donchuacotk
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action =request.getParameter("action");
        DonChuaCoTK dcctk = new DonChuaCoTK();
        if(action.equals("loaddoncctk")){
            int page = Integer.parseInt(request.getParameter("page")) ;
            String ngaynhap =request.getParameter("ngaynhap");
//            String dononline = request.getParameter("dononline");
//            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
//            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
//            int loainhan = Integer.parseInt(request.getParameter("loainhan"));
//            String manhan = request.getParameter("manhan");
//            String bnbd = request.getParameter("bnbd");
//            String bbd = request.getParameter("bbd");
            String tbody = dcctk.loadTableDonCCTK(page, ngaynhap, "", 0, 0, 0, "", "", "");
       //     System.out.println(tbody);
            int totalpage = 1;
             if(page ==1){
                totalpage = dcctk.getPageDonCCTK(page, ngaynhap, "", 0, 0, 0, "", "", "");
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
        }else if(action.equals("searchdoncctk")){
             int page = Integer.parseInt(request.getParameter("page")) ;
            String ngaynhap =request.getParameter("ngaynhap");
            String dononline = request.getParameter("maonline");
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            int loainhan = Integer.parseInt(request.getParameter("loainhan"));
            String manhan = request.getParameter("manhan");
            String bnbd = request.getParameter("bnbd");
            String bbd = request.getParameter("bbd");
            System.out.println(loaidon);
            System.out.println(loaidk);System.out.println(loainhan);
            
            String tbody = dcctk.loadTableDonCCTK(page, ngaynhap, dononline, loaidon, loaidk, loainhan, manhan, bnbd, bbd);
            int totalpage = 0;
            if(page ==1){
                totalpage = dcctk.getPageDonCCTK(page,ngaynhap, dononline, loaidon, loaidk, loainhan, manhan, bnbd, bbd);
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
