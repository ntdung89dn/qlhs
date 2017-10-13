/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ttdk.bean.InforPrint;
import com.ttdk.bean.Khachhang;
import com.ttdk.bean.LePhiBean;
import com.ttdk.bean.NhapDon;
import com.ttdk.connect.DBConnect;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Thorfinn
 */
public class LoadDonAjax extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        NhapDon nd = new NhapDon();
        HttpSession session=request.getSession();
        if(action.equals("loaddon")){
            String page = request.getParameter("page");
            int pageNo = Integer.parseInt(page);
            String day = request.getParameter("ngaynhap");
         //   System.out.println(pageNo);
            String role3 = session.getAttribute("3").toString();
            String role4 = session.getAttribute("4").toString();
           //String table = nd.viewNhapDon(pageNo,role3,role4,day);
            String table = nd.viewDonNhapTable(pageNo, role3, role4, day);
            response.getWriter().write(table);
        }else if(action.equals("getrow")){
            String ngaynhap = request.getParameter("ngaynhap");
            int nopage = nd.countRow(ngaynhap);
       //     System.out.println("nopage "+nopage);
              JSONArray arr = new JSONArray();
              JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",nopage);
                obj.put("currentpage",1);
                arr.put(obj);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
             response.getWriter().write(pagelist);
        }else if(action.equals("printdon")){
            String donidStr = request.getParameter("id");
            int donid = Integer.parseInt(donidStr);
            ArrayList<Object> loadDon = nd.loadPrintDon(donid);
            JSONArray arr = new JSONArray();
            JSONObject obj = new JSONObject();
            try {
                obj.put("gionhan", loadDon.get(0));
                obj.put("phutnhan", loadDon.get(1));
                obj.put("ngaynhan", loadDon.get(2));
                obj.put("manhan", loadDon.get(3));
                obj.put("mapin", loadDon.get(4));
                obj.put("dononline", loadDon.get(5));
                obj.put("bnbd", loadDon.get(6));
                obj.put("account", loadDon.get(7));
                obj.put("benbaodam", loadDon.get(8));
             //   System.out.println("NAME = "+loadDon.get(8));
                arr.put(obj);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String searchList = obj.toString();
            response.getWriter().write(searchList);
        }else if(action.equals("edit")){
            int donid = Integer.parseInt(request.getParameter("donid"));
        //    System.out.println(donid);
            ArrayList<Object> loadDon = nd.loadEditDon(donid);
            JSONArray arr = new JSONArray();
            JSONObject obj = new JSONObject();
            try {
                obj.put("donid", loadDon.get(0));
                obj.put("gionhan", loadDon.get(1));
                obj.put("phutnhan", loadDon.get(2));
                obj.put("ngaynhan", loadDon.get(3));
                obj.put("dononline", loadDon.get(4));
                obj.put("mapin", loadDon.get(5));
                obj.put("slts", loadDon.get(6));
                obj.put("cctt", loadDon.get(7));
                obj.put("loaidon", loadDon.get(8));
                obj.put("bnbd", loadDon.get(9));
                obj.put("bndbtp", loadDon.get(10));
                obj.put("benbaodam", loadDon.get(11));
                obj.put("manhan", loadDon.get(12));
                arr.put(obj);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
             String searchList = obj.toString();
            response.getWriter().write(searchList);
        }else if(action.equals("delete")){
            int donid = Integer.parseInt(request.getParameter("donid"));
        }else if(action.equals("update")){
            Connection connect =  new DBConnect().dbConnect();
            int donid =  Integer.parseInt(request.getParameter("donid"));
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            String maloainhan = request.getParameter("maloainhan");
            int gionhan = Integer.parseInt(request.getParameter("gionhan"));
            int phutnhan = Integer.parseInt(request.getParameter("phutnhan"));

            
            String ngaynhap= request.getParameter("ngaynhap");
            String[] ngaynhapArr = ngaynhap.split("-");
            ngaynhap = ngaynhapArr[2]+"-"+ngaynhapArr[1]+"-"+ngaynhapArr[0];
            int sotaisan = 0;
            if(!maloainhan.trim().equals("") || maloainhan.trim().length() >0){
                 sotaisan = Integer.parseInt(request.getParameter("slts"));
            }
            int cctt = Integer.parseInt(request.getParameter("cctt"));
            String khidStr = request.getParameter("btp");
            int khid = 0;
            if(khidStr.trim().equals("")){
                String btpname = request.getParameter("btp_name");
                String bdtp_dc = request.getParameter("btp_dc");
                String bdtp_tk = request.getParameter("bdtp_tk");
               khid = new Khachhang().addKhachHang(btpname, bdtp_dc, "", bdtp_tk);
            }else{
                khid = Integer.parseInt(request.getParameter("bdtp_id"));
            }
       //     System.out.println("KH ID = "+khid);
            nd.updateBTP(donid, khid);
            ArrayList<LePhiBean> lpArr = nd.getLePhi();
            int lpid = 0;
            switch(loaidon){
                case 1: lpid = lpArr.get(0).getLpid();
                    break;
                case 2: lpid = lpArr.get(1).getLpid();
                    break;
                case 3: lpid = lpArr.get(2).getLpid();
                    break;
                case 4: lpid = lpArr.get(3).getLpid();
                    break;
                case 5: lpid = lpArr.get(4).getLpid();
                    break;
                case 6: lpid = lpArr.get(5).getLpid();
                    break;
                case 7: lpid = lpArr.get(6).getLpid();
                    break;
                case 8: lpid = lpArr.get(7).getLpid();
                    break;
                case 9: lpid = lpArr.get(8).getLpid();
                    break;
                case 10: lpid = lpArr.get(9).getLpid();
                    break;
                case 11: lpid = lpArr.get(10).getLpid();
                    break;
                case 12: lpid = lpArr.get(11).getLpid();
                    break;
                case 13: lpid = lpArr.get(12).getLpid();
                    break;
                default:
                    break;
            }
                String sodononline = request.getParameter("dononline");
                
                String mapinStr = request.getParameter("mapin");
                mapinStr = mapinStr.trim();
                
                String bddb = request.getParameter("bnbd");
                Object obj=JSONValue.parse(bddb);  
                 org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
                org.json.simple.JSONArray content =  (org.json.simple.JSONArray) jsonObject.get("content");
                ArrayList<Integer> listBNBD = nd.checkBNBD(donid);
                if(listBNBD.size() > content.size()){
                    int range = listBNBD.size() - content.size();
                    for(int i=0; i< range;i++){
                         nd.deleteBNBD(listBNBD.get(i));
                         listBNBD.remove(i);
                    }
                    for(int i=0;i < listBNBD.size();i++){
                        Object object = content.get(i);
                        org.json.simple.JSONObject jObj= (org.json.simple.JSONObject) object;  
                        int bnbd_id = listBNBD.get(i);
                        String name = (String) jObj.get("name");  
                        String diachi = (String) jObj.get("diachi");  
                        nd.updateBNBD(bnbd_id, name, diachi);
                    }
                }else if(listBNBD.size() == content.size()){
                    for(int i=0;i < listBNBD.size();i++){
                        Object object = content.get(i);
                        org.json.simple.JSONObject jObj= (org.json.simple.JSONObject) object;  
                        int bnbd_id = listBNBD.get(i);
                        String name = (String) jObj.get("name");  
                        String diachi = (String) jObj.get("diachi");  
                        nd.updateBNBD(bnbd_id, name, diachi);
                    }
                }else if(listBNBD.size() < content.size()){
                    for(int i=0;i < content.size();i++){
                         Object object = content.get(i);
                          org.json.simple.JSONObject jObj= (org.json.simple.JSONObject) object;  
                          String name = (String) jObj.get("name");  
                           String diachi = (String) jObj.get("diachi");
                        if(i < listBNBD.size() ){
                            int bnbd_id = listBNBD.get(i);
                            nd.updateBNBD(bnbd_id, name, diachi);
                        }else{
                            nd.insertBNBD(connect,donid, name, diachi);
                        }
                        
                    }
                    
                }
                String bbd = request.getParameter("bbd");
                Object objBBD =JSONValue.parse(bbd);  
                 org.json.simple.JSONObject jsonObjectBBD = (org.json.simple.JSONObject) objBBD;
                org.json.simple.JSONArray contentBBD =  (org.json.simple.JSONArray) jsonObjectBBD.get("content");
                ArrayList<Integer> listBBD = nd.checkBBD(donid);
                if(listBBD.size() > contentBBD.size()){
                    int range = listBBD.size() - contentBBD.size();
                    for(int i=0; i< range;i++){
                         nd.deleteBBD(listBBD.get(i));
                         listBBD.remove(i);
                    }
                    for(int i=0;i < listBBD.size();i++){
                        Object object = contentBBD.get(i);
                        org.json.simple.JSONObject jObj= (org.json.simple.JSONObject) object;  
                        int bbd_id = listBBD.get(i);
                        String name = (String) jObj.get("name");  
                        String diachi = (String) jObj.get("cmnd");  
                        nd.updateBBD(bbd_id, name, diachi);
                    }
                }else if(listBBD.size() == contentBBD.size()){
                    for(int i=0;i < listBBD.size();i++){
                        Object object = contentBBD.get(i);
                        org.json.simple.JSONObject jObj= (org.json.simple.JSONObject) object;  
                        int bbd_id = listBBD.get(i);
                        String name = (String) jObj.get("name");  
                        String diachi = (String) jObj.get("cmnd");  
                        nd.updateBBD(bbd_id, name, diachi);
                    }
                }else if(listBBD.size() < contentBBD.size()){
                    for(int i=0;i < contentBBD.size();i++){
                         Object object = contentBBD.get(i);
                          org.json.simple.JSONObject jObj= (org.json.simple.JSONObject) object;  
                          String name = (String) jObj.get("name");  
                           String diachi = (String) jObj.get("cmnd");
                        if(i < listBBD.size() ){
                            int bbd_id = listBBD.get(i);
                            nd.updateBBD(bbd_id, name, diachi);
                        }else{
                            nd.insertBBD(connect,donid, name, diachi);
                        }
                        
                    }
                    
                }
                String manhan = "";
                if(!maloainhan.trim().equals("")){
                    manhan = maloainhan.substring(maloainhan.indexOf("C")+5);
//                    System.out.println("manhan Sub= "+manhan);
                    int loaidk = Integer.parseInt(request.getParameter("loaidk"));
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
//            System.out.println("manhan Str = "+manhan);
            int manhanInt = Integer.parseInt(manhan);
                nd.updateDon(donid, ngaynhap, manhan, loaidon, cctt, lpid, sotaisan,sodononline,mapinStr,gionhan,phutnhan);
            try {
                new DBConnect().closeAll(connect, null, null);
                //response.sendRedirect("xulydon.jsp?page=nhapdon");
            } catch (SQLException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(action.equals("search")){
            int page = Integer.parseInt(request.getParameter("page"));
         // int page  =1;
            String time = request.getParameter("ngaynhap");
            String maonline = request.getParameter("maonline");       
            String loaidonStr = request.getParameter("loaidon");
            if(loaidonStr == null || loaidonStr.trim().equals("")){
                loaidonStr = "0";
            }
            int loaidon = Integer.parseInt(loaidonStr) ;
            String loainhanStr = request.getParameter("loainhan");
             if(loainhanStr == null || loainhanStr.trim().equals("")){
                loainhanStr = "0";
            }
            int loainhan = Integer.parseInt(loainhanStr) ;
            String manhan = request.getParameter("manhan");
            String bnbd = request.getParameter("bnbd");
            String btp = request.getParameter("btp");
            String bbd = request.getParameter("bbd");
            if(time == null){
                time = "";
            }
            if(maonline == null){
                maonline ="";
            }
            if(manhan == null){
                manhan ="";
            }
             if(bnbd == null){
                bnbd ="";
            }
            if(btp == null){
                btp ="";
            }
            if(bbd == null){
                bbd ="";
            }
            //System.out.println(page+","+time+","+maonline+","+loaidon+","+loainhan+","+manhan+","+bbd+","+bnbd+","+btp);
         //   System.out.println("LD = "+loaidon+", LN ="+loainhan);
            String role3 = session.getAttribute("3").toString();
            String role4 = session.getAttribute("4").toString();
        //     String data = nd.searchNhapDon(page,time, maonline, loaidon, loainhan, manhan, bbd, bnbd, btp,role3,role4);
             String data = nd.viewSearchDonNhap(page, time, maonline, loaidon, loainhan, manhan, bbd, bnbd, btp, role3, role4);
             int totalpage = 0;
             if(page ==1){
                totalpage = nd.getPagesearchDon(page,time, maonline, loaidon, loainhan, manhan, bbd, bnbd, btp);
             }
              JSONObject obj = new JSONObject();
            try {
                obj.put("totalpage",totalpage);
                 obj.put("currentpage", page);
                 obj.put("data", data);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
//            System.out.println("data =================================== \n"+pagelist);
//             System.out.println("=================================== \n");
             response.getWriter().write(pagelist);
        }else if(action.equals("checkdon")){
            int loaidon = Integer.parseInt(request.getParameter("loaidon"));
            String[] bnbd = request.getParameterValues("bnbd[]");
            String[] bbd = request.getParameterValues("bbd[]");
            String ngaynhap = request.getParameter("ngaynhap");
            String maonline = request.getParameter("maonline");
//            System.out.println(bnbd[0]);
//            System.out.println(bbd[0]);
            try{
            ArrayList<String> data = nd.checkDonTrung(bnbd, bbd, loaidon, ngaynhap,maonline);
            String result = "";
            if(data == null){
                response.getWriter().write("");
            }else{
               if(data.get(0).trim().equals("")){
                   result = "Ngày Nhập "+data.get(1)+" Số đơn : "+data.get(2);
               }else{
                   result = "Ngày Nhập "+data.get(1)+" Số đơn : "+data.get(0);
               }
                 response.getWriter().write(result);
            }
            }catch(UnsupportedEncodingException ex){
                ex.printStackTrace();
                response.getWriter().write("");
            }
        }else if(action.equals("updategiohl")){
            int giohl = Integer.parseInt(request.getParameter("giohl"));
            int donid = Integer.parseInt(request.getParameter("donid"));
            System.out.println(giohl);System.out.println(donid);
            nd.updateGioHL(giohl, donid);
        }else if(action.equals("updatephuthl")){
            int phuthl = Integer.parseInt(request.getParameter("phuthl"));
            int donid = Integer.parseInt(request.getParameter("donid"));
            System.out.println(phuthl);System.out.println(donid);
            nd.updatePhutHL(phuthl, donid);
        }else if(action.equals("deletedon")){
            int donid = Integer.parseInt(request.getParameter("donid"));
            int result = nd.deleteDonCheck(donid);
            String resultStr = "";
            if(result != 0){
                resultStr = "OK";
            }else{
                resultStr = "FAILED";
            }
            response.getWriter().write(resultStr);
        }else if(action.equals("loadmaphuluc")){
            String loaiphuluc = request.getParameter("loaiphuluc");
            String ngaynhap = request.getParameter("ngaynhap");
            String maso = "";
            if(loaiphuluc.equals("online")){
                maso = nd.getMaPhuLuc(0, ngaynhap);
            }else if(loaiphuluc.equals("thuong")){
                maso = nd.getMaPhuLuc(1, ngaynhap);
            }
            response.getWriter().write(maso);
        }else if(action.equals("updateedit")){
            String id = request.getParameter("typeid");
            String oldvalue = request.getParameter("oldvalue");
            String updateValue  = request.getParameter("updatevalue");
            int donid = Integer.parseInt(request.getParameter("donid"));
            String typeid = "";
            if(id.contains("bnbd")){
                if(id.contains("bnbddc")){
                    typeid = "bnbddc";
                }else{
                    typeid = "bnbd";
                }
            }else if(id.contains("bbd")){
                typeid = "bbd";
            }else if(id.contains("cmnd")){
                typeid = "cmnd";
            }else if(id.contains("mapin")){
                typeid = "mapin";
            }
            //System.out.println(typeid);
            int result =  new InforPrint().updateDonPrint(oldvalue, updateValue, donid, typeid);
            if(result !=0){
                response.getWriter().write("OK");
            }else{
                response.getWriter().write("FAILED");
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
