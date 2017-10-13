/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.DonInfor;
import com.ttdk.bean.LePhiBean;
import com.ttdk.bean.LoadDonMongoDB;
import com.ttdk.bean.NhapDon;
import com.ttdk.connect.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ntdung
 */
public class LoadOnlineServlet extends HttpServlet {

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

        String action  = request.getParameter("action");
        LoadDonMongoDB ldmg =  new LoadDonMongoDB();
        if(action.equals("reload")){
            String table = "";
            
            try {
                table = ldmg.loadCollectionMG();
            } catch (JSONException ex) {
                Logger.getLogger(LoadOnlineServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.getWriter().write(table);
        }else if(action.equals("insert")){
            int donKey = 0;
            NhapDon nd = new NhapDon();
            Connection connect =  new DBConnect().dbConnect();
            try {
                String maonline = request.getParameter("maonline");
                int maloaidon = Integer.parseInt(request.getParameter("maloaidon"));
                int soluongpl = Integer.parseInt(request.getParameter("soluongpl"));
                int socvdau = Integer.parseInt(request.getParameter("socvdau"));
                String masoplonline = request.getParameter("soplonline");
                int khid = Integer.parseInt(request.getParameter("khid"));
                ArrayList<LePhiBean> lpArr = nd.getLePhi();
                int ldID = 0;
                int loaidonpl = 0;
                switch(maloaidon){
                    case 1: ldID = lpArr.get(0).getLpid(); loaidonpl= 12;
                        break;
                    case 2: ldID = lpArr.get(1).getLpid();loaidonpl= 13;
                        break;
                    case 3: ldID = lpArr.get(2).getLpid();loaidonpl= 11;
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
                
                //   String text  =  ldmg.searchDonByOn(maonline);
                DonInfor di = ldmg.updateDonByMaOn(maonline, maloaidon);
               // int khid = di.
               String mapin = di.getMapin();
                String tgiandk = di.getThoigiandk();
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date date = sdf1.parse(tgiandk.split(" ")[0]);
                java.sql.Date ngaynhap = new Date(date.getTime());
                int gio = Integer.parseInt(tgiandk.split(" ")[1].replaceAll(":", "").substring(0, 2));
                int phut = Integer.parseInt(tgiandk.split(" ")[1].replaceAll(":", "").substring(2, 4));
                //      String ngaynhap = tgiandk.split(" ")[0];
                // ngaynhap-slts-BD-manhan-CE-LĐ-khid-cctt-lephi-maonline-mapin-gionhap-phutnhap
                
                connect.setAutoCommit(false);
                donKey = nd.insertNhapDon(connect,ngaynhap, 0, 1, 0, 1, maloaidon, khid, 1, ldID, maonline, mapin, gio, phut);
                nd.updateGioHL(gio, phut, donKey, connect);
                if(donKey >0){
                    ArrayList<ArrayList<String>> bnbdList = di.getBennhanbaodam();
                    for(ArrayList<String> bnbd : bnbdList){
                        nd.insertBNBD(connect,donKey, bnbd.get(0), bnbd.get(1));
                    }
                    ArrayList<ArrayList<String>> bbdList = di.getBenbaodam();
                    for(ArrayList<String> bbd : bbdList){
                        String maso = "";
                        if(bbd.get(1) !=null){
                            maso = bbd.get(1).substring(bbd.get(1).indexOf(":")+1);
                        }
                        nd.insertBBD(connect,donKey, bbd.get(0), maso);
                    }
                    connect.commit();
                    new DBConnect().closeAll(connect, null, null);
                    
                }
                
                ldmg.updateBarcodeByMaOn(maonline, String.valueOf(donKey));
                if(soluongpl >0){
                    for(int i=0; i < soluongpl;i++){
                        connect =  new DBConnect().dbConnect();
                        connect.setAutoCommit(false);
                        int lppl = 0;
                        switch(loaidonpl){
                            case 1: lppl = lpArr.get(0).getLpid();
                                break;
                            case 2: lppl = lpArr.get(1).getLpid();
                                break;
                            case 3: lppl = lpArr.get(2).getLpid();
                                break;
                            case 4: lppl = lpArr.get(3).getLpid();
                                break;
                            case 5: lppl = lpArr.get(4).getLpid();
                                break;
                            case 6: lppl = lpArr.get(5).getLpid();
                                break;
                            case 7: lppl = lpArr.get(6).getLpid();
                                break;
                            case 8: lppl = lpArr.get(7).getLpid();
                                break;
                            case 9: lppl = lpArr.get(8).getLpid();
                                break;
                            case 10: lppl = lpArr.get(9).getLpid();
                                break;
                            case 11: lppl = lpArr.get(10).getLpid();
                                break;
                            case 12: lppl = lpArr.get(11).getLpid();
                                break;
                            case 13: lppl = lpArr.get(12).getLpid();
                                break;
                            default:
                                break;
                        }
                        int donPL = nd.insertNhapDon(connect,ngaynhap, 0, 3, socvdau, 2, loaidonpl, khid, 1, lppl, masoplonline, mapin, gio, phut);
                        nd.updateGioHL(gio, phut, donPL, connect);
                        if(donPL >0){
                            ArrayList<ArrayList<String>> bnbdList = di.getBennhanbaodam();
                            for(ArrayList<String> bnbd : bnbdList){
                                nd.insertBNBD(connect,donPL, bnbd.get(0), bnbd.get(1));
                            }
                            ArrayList<ArrayList<String>> bbdList = di.getBenbaodam();
                           // System.out.println("BBD SIZE = "+bbdList.size());
                            for(ArrayList<String> bbd : bbdList){
                                String maso = "";
                                if(bbd.get(1) !=null){
                                    maso = bbd.get(1).substring(bbd.get(1).indexOf(":")+1);
                                }
                                nd.insertBBD(connect,donPL, bbd.get(0),maso);
                            }

                        }
                        new DBConnect().closeAll(connect, null, null);
                        connect.commit();
                        socvdau++;
                    }
                    
                }
                
            } catch (ParseException | SQLException ex) {
                try {
                    Logger.getLogger(LoadOnlineServlet.class.getName()).log(Level.SEVERE, null, ex);
                    connect.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(LoadOnlineServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }finally{
                try {
                    new DBConnect().closeAll(connect, null);
                } catch (SQLException ex) {
                    Logger.getLogger(LoadOnlineServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String url = "./print/print_mau2017.jsp?id="+donKey+"&type=on";
            response.getWriter().write(String.valueOf(donKey));
        }else if(action.equals("checkdon")){
            String maonline  = request.getParameter("maonline");
            String ngaynhap = request.getParameter("ngaynhap");
            boolean check = ldmg.checkOnlineNotExist(ngaynhap, maonline);
            String result = "n";
            if(check){
                result = "y";
            }
            response.getWriter().write(result);
        }else if(action.equals("searchdon")){
            String maonline = request.getParameter("maonline");
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            int phuluc = Integer.parseInt(request.getParameter("phuluc"));
            System.out.println(maonline);System.out.println(loaidon);System.out.println(phuluc);
            String tbody = ldmg.viewSearchDonByOn(maonline, loaidon, phuluc);
            System.out.println(tbody);
            response.getWriter().write(tbody);
        }else if(action.equals("getnumberpl")){
            int maphuluc = ldmg.getNumberPLO();
            System.out.println(maphuluc); 
            response.getWriter().write(String.valueOf(maphuluc));
        }else if(action.equals("savephuluc")){
            String result = "";
            try {
                int donid = Integer.parseInt(request.getParameter("donid"));
                String maonline = request.getParameter("maonline");
                int page = Integer.parseInt(request.getParameter("page"));
                String loaidon = request.getParameter("loaidon");
                int mapl = Integer.parseInt(request.getParameter("mapl"));
                NhapDon nd = new NhapDon();
                ArrayList<Object> donObj = nd.loadPrintDon(donid);
                String madonOnline = donObj.get(5).toString();
                ArrayList<LePhiBean> lpArr = nd.getLePhi();
                int loaidonpl = 0;
                int lppl = 0;
                if(loaidon.toLowerCase().contains("lần đầu")){
                    loaidonpl =12; 
                }else if(loaidon.toLowerCase().contains("thay đổi")){
                    loaidonpl = 13;
                }else if(loaidon.toLowerCase().contains("xóa")){
                    loaidonpl = 11;
                }
                switch(loaidonpl){
                    case 1: lppl = lpArr.get(0).getLpid();
                    break;
                    case 2: lppl = lpArr.get(1).getLpid();
                    break;
                    case 3: lppl = lpArr.get(2).getLpid();
                    break;
                    case 4: lppl = lpArr.get(3).getLpid();
                    break;
                    case 5: lppl = lpArr.get(4).getLpid();
                    break;
                    case 6: lppl = lpArr.get(5).getLpid();
                    break;
                    case 7: lppl = lpArr.get(6).getLpid();
                    break;
                    case 8: lppl = lpArr.get(7).getLpid();
                    break;
                    case 9: lppl = lpArr.get(8).getLpid();
                    break;
                    case 10: lppl = lpArr.get(9).getLpid();
                    break;
                    case 11: lppl = lpArr.get(10).getLpid();
                    break;
                    case 12: lppl = lpArr.get(11).getLpid();
                    break;
                    case 13: lppl = lpArr.get(12).getLpid();
                    break;
                    default:
                        break;
                }
                Connection connect =  new DBConnect().dbConnect();
                String ngaynhapStr = donObj.get(2).toString();
                int khid= Integer.parseInt(donObj.get(9).toString());
                String mapin = donObj.get(10).toString();
                int gio = Integer.parseInt(donObj.get(0).toString());
                int phut = Integer.parseInt(donObj.get(1).toString());
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date date = sdf1.parse(ngaynhapStr);
                java.sql.Date ngaynhap = new Date(date.getTime());
                ArrayList<ArrayList<String>> bnbdList = nd.getBNBDByDonID(donid);
                ArrayList<ArrayList<String>> bbdList = nd.getBBDByDonID(donid);
                String barcodePL = "";
                for(int i =0; i< page;i++){
                    int donPL = nd.insertNhapDon(connect,ngaynhap, 0, 3, (mapl+i), 2, loaidonpl, khid, 1, lppl, maonline, mapin, gio, phut);
                    nd.updateGioHL(gio, phut, donPL, connect);
                    if(donPL >0){
                        bnbdList.forEach((bnbd) -> {
                            nd.insertBNBD(connect,donPL, bnbd.get(0), bnbd.get(1));
                        });
                        bbdList.forEach((bbd) -> {
                            String maso = "";
                            if(bbd.get(1) !=null){
                                maso = bbd.get(1).substring(bbd.get(1).indexOf(":")+1);
                            }
                            nd.insertBBD(connect,donPL, bbd.get(0),maso);
                        });
                    }
                   if(barcodePL.equals("")){
                       barcodePL = ""+donPL;
                   }else{
                       barcodePL += "-"+donPL;
                   }
                }
                new DBConnect().closeAll(connect, null, null);
                ldmg.updateSavePL(String.valueOf(donid),barcodePL,maonline);
                result = barcodePL;
            } catch (ParseException | SQLException ex) {
                Logger.getLogger(LoadOnlineServlet.class.getName()).log(Level.SEVERE, null, ex);
                result = "FAILED";
            }
            response.getWriter().write(result);
        }else if(action.equals("addcsgt")){
            String name = request.getParameter("name");
            String diachi = request.getParameter("diachi");
            String barcode = request.getParameter("donid");
            ldmg.updateCSGT(name, diachi, barcode);
            response.getWriter().write("OK");
        }else if(action.equals("getbarcode")){
            String barcode= request.getParameter("barcode");
            ArrayList<String> data = ldmg.loadBarcodes(barcode);
            JSONObject obj = new JSONObject();
            try {
                obj.put("barcode",data.get(0));
                obj.put("maonlinepl",data.get(1));
            } catch (JSONException ex) {
                Logger.getLogger(LoadOnlineServlet.class.getName()).log(Level.SEVERE, null, ex);
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
