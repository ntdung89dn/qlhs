/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ttdk.bean.Khachhang;
import com.ttdk.bean.KhachhangBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ntdung
 */
@WebServlet(name = "LoadKHServlet", urlPatterns = {"/loadkh"})
public class LoadKHServlet extends HttpServlet {

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
        String type = request.getParameter("action");
        Khachhang khdb = new Khachhang();
        HashMap<String, Object> JSONROOT = new HashMap<String, Object>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(request.getParameter("action"));
        if(type.equals("load")){
            try{
                Khachhang kh = new Khachhang();
                int startPageIndex = Integer.parseInt(request.getParameter("jtStartIndex"));
                List<KhachhangBean> listKHB = null;// kh.getKhachHangList(startPageIndex,9);
                int userCount =0; //kh.khCount();
                System.out.println(userCount);
                JSONROOT.put("Result", "OK");
                JSONROOT.put("Records", listKHB);
                JSONROOT.put("TotalRecordCount", userCount);
                // Convert Java Object to Json
                
                String jsonArray =  gson.toJson(JSONROOT);

                response.getWriter().print(jsonArray);
            } catch (Exception ex) {
                JSONROOT.put("Result", "ERROR");
                JSONROOT.put("Message", ex.getMessage());
                String error = gson.toJson(JSONROOT);
                response.getWriter().print(error);
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
