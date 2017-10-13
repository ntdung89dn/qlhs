/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.connect.DBConnect;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * @author Thorfinn
 */
public class KhachhangAuto extends HttpServlet {

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
        String term = request.getParameter("term");
        ArrayList<String> al=new ArrayList<>();
        PreparedStatement ps = null;
        JSONArray arr = new JSONArray();
        try {
            DBConnect dbcon = new DBConnect();
            
            Connection conn = dbcon.dbConnect();
            String sql = "select tbl_khachhang.KH_ID, KH_Account as Account,KH_Name as KH_Name ,"
                    + "KH_Address as Address ,KH_Email as Email ,tbl_khachhang.KH_Status as status "
                    + "from tbl_khachhang \n" 
                    + "where ( KH_Account LIKE ? or KH_Name LIKE ? or KH_Address LIKE ? ) and tbl_khachhang.KH_Status is null LIMIT 15 ;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%"+term + "%");
            ps.setString(2, "%"+term + "%");
            ps.setString(3, "%"+term + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    int id = rs.getInt("KH_ID");
                    String account = rs.getString("Account");
                    String name = rs.getString("KH_Name");
                    String address = rs.getString("Address");
                    String email = rs.getString("Email");
                    JSONObject obj = new JSONObject();
                    obj.put("id", id);
                    obj.put("account", account);
                    obj.put("name", name);
                    obj.put("address", address);
                    obj.put("email", email);
                   // System.out.println(data);
                   // String jsonTxt = "{name:}";
                    arr.put(obj);
                    //al.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachhangAuto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(KhachhangAuto.class.getName()).log(Level.SEVERE, null, ex);
        }
        String searchList = arr.toString();
        response.getWriter().write(searchList);
        System.out.println(searchList);
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
