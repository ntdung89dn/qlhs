/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.DangNhap;
import com.ttdk.bean.ErrorBean;
import com.ttdk.connect.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thorfinn
 */
public class LoginAction extends HttpServlet {

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
                HttpSession session=request.getSession();  
                String action = request.getParameter("action");
                DangNhap dn = new DangNhap();
                if(action.equals("login")){
                    if(request.getParameter("username") != null && request.getParameter("passwd") != null){
                        String userName = request.getParameter("username");
                            String password = request.getParameter("passwd");
                        //System.out.append(userName);System.out.append(password);
//                            DBConnect dbConn = new DBConnect();
//                            Connection con = dbConn.dbConnect();
//                            if(con != null){
//                            try {
//                                Statement st = con.createStatement();
//                                ResultSet rs= rs = st.executeQuery("select * from tbl_username where U_ID ='"+ userName + "' and U_Pass='" + password + "'");
//
//                                //out.print("RS = "+ rs.getString(0));
//                                if (rs.next()) {
//                                    session.setAttribute("username", userName);
//                                    session.setAttribute("role", String.valueOf(rs.getInt(3)));
//                                    response.setContentType("text/html;charset=UTF-8");
//                                    response.getWriter().write("login");
//                                }
//                                else
//                                {
//                                    response.setContentType("text/html;charset=UTF-8");
//                                    response.getWriter().write("FAILED");
//                                }
//                            } catch (SQLException ex) {
//                                Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                                ArrayList data =    dn.checkLogin(userName.toLowerCase(), password);
                                if(data != null){
                                    ArrayList<ArrayList<String>> roleList = dn.getPhanQuyen(userName);
                                    session.setAttribute("username", userName);
                                    session.setAttribute("fullname", data.get(1));
                                    session.setAttribute("roledes", data.get(2));
                                    session.setAttribute("role", data.get(3));
                                    //session.setAttribute("role", String.valueOf(rs.getInt(3)));
                                    String returnPage = "";
                                    boolean checkPage = false;
                                    for(ArrayList<String> role : roleList){
                                      //  System.out.println("ROLE name ="+role.get(0));
                                        session.setAttribute(role.get(0), role.get(1));
                                        if(!checkPage){
                                            if(role.get(1).equals("1")){
                                                if(role.get(0).equals("1") || role.get(0).equals("2") || role.get(0).equals("3") || role.get(0).equals("4") ){
                                                    returnPage = "xulydon.jsp?page=nhapdon";
                                                    session.setAttribute("defaultpage", returnPage);
                                                    checkPage =  true;
                                                }else if( role.get(0).equals("5")){
                                                    returnPage = "xulydon.jsp?page=phandon";
                                                    session.setAttribute("defaultpage", returnPage);
                                                    checkPage =  true;
                                                }else if( role.get(0).equals("6")){
                                                    returnPage = "xulydon.jsp?page=donchotra";
                                                    session.setAttribute("defaultpage", returnPage);
                                                    checkPage =  true;
                                                }else if( role.get(0).equals("7")){
                                                    returnPage = "xulydon.jsp?page=dondatra";
                                                    session.setAttribute("defaultpage", returnPage);
                                                    checkPage =  true;
                                                }else if( role.get(0).equals("8")){
                                                    returnPage = "xulydon.jsp?page=dondatra";
                                                    session.setAttribute("defaultpage", returnPage);
                                                    checkPage =  true;
                                                }else if( role.get(0).equals("9")){
                                                    returnPage = "xulydon.jsp?page=thongke";
                                                    session.setAttribute("defaultpage", returnPage);
                                                    checkPage =  true;
                                                }else if( role.get(0).equals("10") ||  role.get(0).equals("11") ||  role.get(0).equals("12") ||  role.get(0).equals("13")){
                                                    returnPage = "vanthu.jsp?page=xemvanthu";
                                                    session.setAttribute("defaultpage", returnPage);
                                                    checkPage =  true;
                                                }else if( role.get(0).equals("14") || role.get(0).equals("15") || role.get(0).equals("16") || role.get(0).equals("17")){
                                                    returnPage = "khachhang.jsp?page=khachhang";
                                                    session.setAttribute("defaultpage", returnPage);
                                                    checkPage =  true;
                                                }else if( role.get(0).equals("18") || role.get(0).equals("19") || role.get(0).equals("20")){
                                                    returnPage = "khachhang.jsp?page=khachhang";
                                                    session.setAttribute("defaultpage", returnPage);
                                                    checkPage =  true;
                                                }
                                            }
                                        }
                                        
                                    }
                                   // String returnPage = "";
                                    int roleid = Integer.parseInt(data.get(3).toString());
                                    response.setContentType("text/html;charset=UTF-8");
                                    response.getWriter().write(returnPage);
                                }else{
                                    response.setContentType("text/html;charset=UTF-8");
                                    response.getWriter().write("FAILED");
                                }
                            }else{
                                response.setContentType("text/html;charset=UTF-8");
                                response.getWriter().write("ERROR");
                                System.out.println("ERROR");
                            }
                }else if(action.equals("logout")){
                    session.removeAttribute("username");
                    session.removeAttribute("role");
                    response.sendRedirect("http://localhost:8084/QLHoSo/");
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
