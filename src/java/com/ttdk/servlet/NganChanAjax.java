/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.NganChanDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Thorfinn
 */
public class NganChanAjax extends HttpServlet {

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
        NganChanDB ncdb = new NganChanDB();
        String action = request.getParameter("action");
        if(action.equals("load")){
            String term = request.getParameter("term");
            
            String autoNC = ncdb.loadNCAjax(term);
            response.getWriter().write(autoNC);
        }else if(action.equals("checknc")){
            String name = request.getParameter("name");
            String maso = request.getParameter("cmnd");
            ArrayList<String> ncArr = ncdb.checkNC(name, maso);
            if(ncArr.size()>0){
                //String text = "<b>Bên bảo đảm đã bị ngăn chặn, vui lòng coi thông tin <a href='file/"+ncArr.get(0)+"'>Tại đây</a></b>";
                String text = "<b>Bên bảo đảm đã bị ngăn chặn, vui lòng coi thông tin <a onclick='window.open(\"file/"+ncArr.get(0)+"\")'>Tại đây</a></b>";
                response.getWriter().write(text);
            }else{
                 response.getWriter().write("ok");
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
