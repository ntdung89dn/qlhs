/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.NhanVien;
import com.ttdk.bean.NhanVienBean;
import com.ttdk.bean.NhapDon;
import com.ttdk.bean.SearchDon;
import com.ttdk.bean.TraDon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author NTD
 */
public class TraDonServlet extends HttpServlet {

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
        HttpSession session=request.getSession();  
        String username = session.getAttribute("username").toString();
        TraDon td = new TraDon();
        NhapDon nd = new NhapDon();
        String tbody = "";
        SearchDon sd = new SearchDon();
        if(action.equals("luudon")){
            String don = request.getParameter("don");
            String[] dontt = don.split("-");
            int donid = Integer.parseInt(dontt[0]);
            String online = dontt[1];
             String mapin = "";
            if(dontt.length >2){
                mapin = dontt[2];
            }
            td.updateTradon(donid, online, mapin);
            System.out.println(donid+"-"+online+"-"+mapin);
     //          tbody = sd.donChoTra(1,username);
               response.getWriter().write("ĐÃ LƯU");
        }else if(action.equals("luunhieudon")){
            String donS = request.getParameter("don");
           // System.out.println(donS);
            String[] dontt = donS.split(",");
            for(String donttStr : dontt){
                String[] value = donttStr.split("-");
                int donid = Integer.parseInt(value[0]);
                String online = value[1];
                String mapin = value[2];
                if(online.equals("_")){
                    online = "";
                }
                if(mapin.equals("_")){
                    mapin = "";
                }
                td.updateTradon(donid, online, mapin);
            }
              tbody = sd.donChoTra(1,username);
              response.getWriter().write(tbody);
        }else if(action.equals("hoidon")){
            String pdidStr = request.getParameter("pdid");
            //pdidStr = pdidStr.substring("dct_hdon".length());
            int pdid = Integer.parseInt(pdidStr);
            ArrayList<ArrayList>  userList = td.checkDonNhieuTs(pdid);
            int slts = Integer.parseInt(request.getParameter("slts"));
            String nvNhan = request.getParameter("nvnhan");
            String ngaynhap = request.getParameter("ngaynhap");
            if(userList.size() == 1){
                new NhanVien().updateDinhMuc(slts, nvNhan, ngaynhap);
            }else{
                for(ArrayList user : userList){
                    int sltsNv = Integer.parseInt(user.get(1).toString());
                    String userid = user.get(0).toString();
                    new NhanVien().updateDinhMuc(sltsNv, userid, ngaynhap);
                }
            }
            td.hoiDon(pdid);
             tbody = sd.donChoTra(1,username);
             response.getWriter().write(tbody);
        }else if(action.equals("tradon")){
            String pdidStr = request.getParameter("pdid");
            int pdid = Integer.parseInt(pdidStr);
            String tgtra = request.getParameter("tgtra");
            System.out.println(pdid +"-"+tgtra);
            td.traDon(tgtra, pdid);
            //  tbody = sd.donChoTra(1,username);
              response.getWriter().write("OK");
        }else if(action.equals("loaddonchotra")){
            int page = Integer.parseInt(request.getParameter("page"));
            JSONObject obj = new JSONObject();
            try {
            tbody = sd.donChoTra(page,username);
            int totalpage = 0;
            if(page ==1){
                totalpage = sd.pageDChoTra(username);
            }
                obj.put("totalpage",totalpage);
                 obj.put("currentpage", page);
                 obj.put("data", tbody);
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
        }else if(action.equals("loaddanhsachno")){
            String btp = request.getParameter("btp");
            int st = Integer.parseInt(request.getParameter("st")) ;
            int total = Integer.parseInt(request.getParameter("total"));
            tbody = nd.loadTableBTP_CTP(btp, st, total);
            response.getWriter().write(tbody);
        }else if(action.equals("loadbbddns")){
            String donids = request.getParameter("donids");
            tbody = nd.loadTableBBD_CTP(donids);
            response.getWriter().write(tbody);
        }else if(action.equals("loadnvnhan")){
            ArrayList<NhanVienBean> nvLits = new NhanVien().getAllnvName();
            String option = "<option value='all' >Tất cả</option>";
            String user = session.getAttribute("username").toString();
            for(NhanVienBean nhanvien : nvLits){
                if(user.equals(nhanvien.getUsername())){
                    option += "<option value='"+nhanvien.getUsername()+"' selected>"+nhanvien.getFullname()+"</option>";
                }else{
                    option += "<option value='"+nhanvien.getUsername()+"'>"+nhanvien.getFullname()+"</option>";
                }
                
            }
            response.getWriter().write(option);
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
