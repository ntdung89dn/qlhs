/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.CSGT;
import com.ttdk.bean.CSGTBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
 * @author ntdung
 */
public class CSGTServlet extends HttpServlet {

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
        CSGT csgt = new CSGT();
        if(action.equals("loadcsgt")){
            int page = Integer.parseInt(request.getParameter("page"));
          //  System.out.println("PAGE = "+page);
            int totalpage = 0;
             if(page ==1){
                totalpage = csgt.getPageCSGT();
             }
            String cityList =  csgt.getCityData();
            String tbody = csgt.getCsgtInfor();
       //     System.out.println(tbody);
            JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                obj.put("currentpage",page);
                obj.put("city",cityList);
                obj.put("data",tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("addcsgt")){
            String name = request.getParameter("name");
            String[] diachi = request.getParameterValues("diachi[]");
            String bienso = request.getParameter("bienso").trim();
            int cityid = Integer.parseInt(request.getParameter("cityid"));
            csgt.addCSGT(cityid, name, diachi, bienso);
            response.getWriter().write("OK");
        }else if(action.equals("loadcsgtname")){
            int id = Integer.parseInt(request.getParameter("nameid"));
            CSGTBean csgtInfor = csgt.loadEditCSGT(id);
            JSONObject obj = new JSONObject();
            JSONArray dcJson = new JSONArray();
            JSONArray dcidJson = new JSONArray();
            try {
                for( ArrayList dc : csgtInfor.getCsgtdc()){
                    dcJson.put(dc.get(1));
                    dcidJson.put(dc.get(0));
                }
                obj.put("name",csgtInfor.getName());
                obj.put("nameid",csgtInfor.getNameid());
                obj.put("diachi",dcJson);
                obj.put("diachiid",dcidJson);
                obj.put("cityid",csgtInfor.getCityid());
                obj.put("bienso",csgtInfor.getBienso());
                obj.put("bsid",csgtInfor.getBsid());
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("updatecsgt")){
            String name = request.getParameter("name");
            String[] diachi = request.getParameterValues("diachi[]");
            int cityid = Integer.parseInt(request.getParameter("cityid"));
            String csgtdcid = request.getParameter("dcid");
            String[] csdcid = csgtdcid.split(",");
            int[] dcid = new int[csdcid.length];
            for(int i=0; i< csdcid.length;i++){
                dcid[i] = Integer.parseInt(csdcid[i]);
            }
            int nameid = Integer.parseInt(request.getParameter("nameid"));
            String bienso = request.getParameter("bienso").trim();
            String[] biensoList = bienso.split(",");
            int[] bs = new int[biensoList.length];
            for(int i=0; i< biensoList.length;i++){
                bs[i] = Integer.parseInt(biensoList[i]);
            }
            String bsid = request.getParameter("bsid");
            String[] bsoid = bienso.split(",");
            int[] bsList = new int[bsoid.length];
            for(int i=0; i< bsoid.length;i++){
                bsList[i] = Integer.parseInt(bsoid[i]);
            }
            csgt.updateCSGT(cityid, name, diachi, bs, nameid, dcid,bsList);
            //csgt.addCSGT(cityid, name, diachi, bienso);
            
            response.getWriter().write("OK");
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
