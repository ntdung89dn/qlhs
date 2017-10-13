/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.Khachhang;
import com.ttdk.bean.LePhiBean;
import com.ttdk.bean.NhapDon;
import com.ttdk.connect.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thorfinn
 */
public class NhapdonServlet extends HttpServlet {

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
            request.setCharacterEncoding("UTF-8");
            NhapDon nd = new NhapDon();
            
            int loaidk = Integer.parseInt(request.getParameter("loaidk"));
            int loainhan = Integer.parseInt(request.getParameter("loainhan"));
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            String maloainhan = request.getParameter("maloainhan");
            String manhan = "";
            if(!maloainhan.trim().equals("")){
                manhan = maloainhan.substring(maloainhan.indexOf("C")+5);
                switch (loaidk) {
                    case 1: manhan = manhan.replaceAll("BD", "");
                        break;
                    case 2: manhan = manhan.replaceAll("TT", "");
                        break;
                    case 3: manhan = manhan.replaceAll("CSGT", "");
                        break;
                    default:
                        break;
                }
            }else{
                manhan = "0";
            }
            int manhanInt = Integer.parseInt(manhan);
            int gionhan = Integer.parseInt(request.getParameter("gionhan"));
            int phutnhan = Integer.parseInt(request.getParameter("phutnhan"));
            
            
            String ngaynhapStr = request.getParameter("ngaynhap");
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            java.sql.Date ngaynhanSql = null;
            try {
                Date parsed = format.parse(ngaynhapStr);
                ngaynhanSql = new java.sql.Date(parsed.getTime());
               // System.out.println("Ngay SQL = "+ngaynhanSql);
            } catch (ParseException ex) {
                Logger.getLogger(NhapdonServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            int sotaisan = 0;
            if(!maloainhan.trim().equals("") || maloainhan.trim().length() >0){
                 sotaisan = Integer.parseInt(request.getParameter("slts"));
            }
            int cctt = Integer.parseInt(request.getParameter("cctt"));
            String khidStr = request.getParameter("bdtp_id");
            int khid = 0;
            if(khidStr.trim().equals("")){
                String btpname = request.getParameter("bdtp_name");
                String bdtp_dc = request.getParameter("bdtp_dc");
                String bdtp_tk = request.getParameter("bdtp_tk");
                if(bdtp_tk == null){
                    bdtp_tk = "";
                }
               khid = new Khachhang().addKhachHang(btpname, bdtp_dc, "", bdtp_tk);
            }else{
                khid = Integer.parseInt(request.getParameter("bdtp_id"));
            }
            //String ma_loainhan = request.getParameter("maloainhan");
           // int key = -1;
            ArrayList<LePhiBean> lpArr = nd.getLePhi();
            int ldID = 0;
            switch(loaidon){
                case 1: ldID = lpArr.get(0).getLpid();
                    break;
                case 2: ldID = lpArr.get(1).getLpid();
                    break;
                case 3: ldID = lpArr.get(2).getLpid();
                    break;
                case 4: ldID = lpArr.get(3).getLpid();
                    break;
                case 5: ldID = lpArr.get(4).getLpid();
                    break;
                case 6: ldID = lpArr.get(5).getLpid();
                    break;
                case 7: ldID = lpArr.get(6).getLpid();
                    break;
                case 8: ldID = lpArr.get(7).getLpid();
                    break;
                case 9: ldID = lpArr.get(8).getLpid();
                    break;
                case 10: ldID = lpArr.get(9).getLpid();
                    break;
                case 11: ldID = lpArr.get(10).getLpid();
                    break;
                case 12: ldID = lpArr.get(11).getLpid();
                    break;
                case 13: ldID = lpArr.get(12).getLpid();
                    break;
                default:
                    break;
            }
            System.out.println("ldID = "+ldID);
            String mapinStr = "";
            if(request.getParameter("mapin") != null){
                mapinStr = request.getParameter("mapin").trim();
            }
            String sodononline = "";
            if( request.getParameter("sodononline") != null){
                sodononline = request.getParameter("sodononline").trim();
            }
            String[] bddb_name = request.getParameterValues("bnbd[]");
            String[] bddb_address = request.getParameterValues("bnbbddc[]");
            String[] bdb_name = request.getParameterValues("bbd[]");
            String[] bdb_dc = request.getParameterValues("bbdmaso[]");
            String result = "";
           Connection connect = new DBConnect().dbConnect();
            try {
                connect.setAutoCommit(false);
                int key = nd.insertNhapDon(connect,ngaynhanSql, sotaisan,loaidk,manhanInt,loainhan, loaidon,khid, cctt,ldID,sodononline,mapinStr,gionhan,phutnhan);
                if(key != -1){
                    for(int i=0;i<bddb_name.length;i++){
                        nd.insertBNBD(connect,key, bddb_name[i], bddb_address[i]);
                    }
                    for(int i=0;i<bdb_name.length;i++){
                        nd.insertBBD(connect,key, bdb_name[i], bdb_dc[i]);
                    }
                }
                boolean savephuluc = Boolean.valueOf(request.getParameter("savephuluc"));
                if(savephuluc){
                    String maphuluc  = request.getParameter("maphuluc");
                    int manhanpl = 1,loaidonpl = 9,tsplon = 1;
                    if(maphuluc.toUpperCase().contains("CF")){
                        manhanpl  =2;
                        tsplon = 0;
                        switch (loaidon) {
                            case 1:
                                loaidonpl = 12;
                                break;
                            case 2:
                                loaidonpl = 13;
                                break;
                            case 3:
                                loaidonpl = 11;
                                break;
                            default:
                                break;
                        }
                    }else if(maphuluc.toUpperCase().contains("CE")){
                        if(loaidon == 2){
                            loaidonpl = 10;
                        }else  if(loaidon == 3){
                            loaidonpl = 11;
                        }
                    }

                     maphuluc = maphuluc.substring(maloainhan.indexOf("C")+5).replaceAll("CSGT", "");
                    int sophuluc = Integer.parseInt(request.getParameter("sophuluc")) ;
                    int maphulucInt = Integer.parseInt(maphuluc);
                    for(int i=0; i < sophuluc;i++){
                        int keypl = nd.insertNhapDon(connect,ngaynhanSql, tsplon,3,maphulucInt,manhanpl, loaidonpl,khid, cctt,ldID,sodononline,mapinStr,gionhan,phutnhan);
                        for(int j=0;j<bddb_name.length;j++){
                            nd.insertBNBD(connect,keypl, bddb_name[j], bddb_address[j]);
                        }
                        for(int j=0;j<bdb_name.length;j++){
                            nd.insertBBD(connect,keypl, bdb_name[j], bdb_dc[j]);
                        }
                        maphulucInt++;
                    }
                }
                
                
                connect.commit();
                result = "OK";
            } catch (SQLException ex) {
                Logger.getLogger(NhapdonServlet.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    connect.rollback();
                    result =  "FAILED";
                } catch (SQLException ex1) {
                    Logger.getLogger(NhapdonServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }finally{
                try { 
                    connect.setAutoCommit(true);
                    new DBConnect().closeAll(connect, null);
                } catch (SQLException ex) {
                    Logger.getLogger(NhapdonServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            HttpSession session = request.getSession();
            if(result.equals("OK")){
                session.setAttribute("insert", "Lưu đơn "+maloainhan+" thành công");
            }else if(result.equals("FAILED")){
                session.setAttribute("insert", "Lưu đơn "+maloainhan+" không thành công");
            }
            
            response.getWriter().write(result);
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
