/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.servlet;

import com.ttdk.bean.LydoDB;
import com.ttdk.bean.NganChanDB;
import com.ttdk.connect.DBConnect;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Thorfinn
 */
public class FileUploadServlet extends HttpServlet {

    //private final String UPLOAD_DIRECTORY = getServletContext().getRealPath("/WEB-INF/");
    //URL resource = getServletContext().getResource("/");
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        String action = request.getParameter("action");
      //  System.out.println(action);
        NganChanDB nganchan = new NganChanDB();
        String UPLOAD_DIRECTORY = getServletContext().getRealPath("/file/");
        if(action.equals("addKH")){
            String nameKh = "";
            String maso = "";
            String address = "";
            String nc_lydo = "";
            String file_name = "";
            String editer = session.getAttribute("username").toString();
            
           // ServletContext context = request.getSession().getServletContext();
            
            
            if(ServletFileUpload.isMultipartContent(request)){
                
                try {
                    List<FileItem> multiparts = new ServletFileUpload(
                            new DiskFileItemFactory()).parseRequest(request);

                    for(FileItem item : multiparts){
                        if(!item.isFormField()){
                            File f= new File(item.getName());
                            if(!f.exists()){
                                file_name = new File(item.getName()).getName();
                                item.write( new File(UPLOAD_DIRECTORY + File.separator + file_name));
                            }else{
                                file_name = new File(item.getName()).getName()+"-001";
                                item.write( new File(UPLOAD_DIRECTORY + File.separator + file_name));
                            }

                        }else{
                            String fieldname = item.getFieldName();
                            String fieldvalue = item.getString();
                            switch (fieldname) {
                                case "nc_tenkh": nameKh = new String (fieldvalue.getBytes ("iso-8859-1"), "UTF-8");
                                    break;
                                case "nc_soid": maso = new String (fieldvalue.getBytes ("iso-8859-1"), "UTF-8");
                                    break;
                                case "nc_diachi": address = new String (fieldvalue.getBytes ("iso-8859-1"), "UTF-8");
                                    break;
                                case "nc_lydo": nc_lydo = new String (fieldvalue.getBytes ("iso-8859-1"), "UTF-8");
                                    break;
                                default:
                                    break;
                            }

                        }

                    }
                    nganchan.insertNC(nameKh, maso, address, file_name, editer, nc_lydo);
                    //File uploaded successfully
                    request.setAttribute("message", "File Uploaded Successfully");
                } catch (Exception ex) {
                    request.setAttribute("message", "File Upload Failed due to " + ex);
                }

            }else{
                
                request.setAttribute("message",
                        "Sorry this Servlet only handles file upload request");
            }
          //  request.getRequestDispatcher("nganchan.jsp?page=nganchan").forward(request, response);
            response.sendRedirect("nganchan.jsp?page=nganchan");
        }else if(action.equals("delete")){
            int NC_ID = Integer.parseInt(request.getParameter("ncid"));
            try {
                nganchan.deleteNC(NC_ID,UPLOAD_DIRECTORY);
                response.sendRedirect("nganchan.jsp?page=nganchan");
            } catch (SQLException ex) {
                Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message",
                        "ERROR");
            }
        }else if(action.equals("loadNC")){
            int page = Integer.parseInt(request.getParameter("page"));
            String tbody = nganchan.loadTableNc(page);
            int totalpage = nganchan.getPageNC();
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
        }else if(action.equals("searchNC")){
            
        }else if(action.equals("deleteNC")){
            
        }else if(action.equals("editNC")){
            int ncid = Integer.parseInt(request.getParameter("ncid"));
            ArrayList<String > ncDetail = nganchan.loadEditNC(ncid);
            JSONObject obj = new JSONObject();
            try {
                obj.put("name",ncDetail.get(0));
                obj.put("soid", ncDetail.get(1));
                obj.put("diachi", ncDetail.get(2));
                obj.put("lydo", ncDetail.get(3));
                obj.put("nc_id", ncDetail.get(4));
            } catch (JSONException ex) {
                Logger.getLogger(LoadDonAjax.class.getName()).log(Level.SEVERE, null, ex);
            }
            String pagelist = obj.toString();
            response.getWriter().write(pagelist);
        }else if(action.equals("updateNC")){
            String nameKh = "";
            String maso = "";
            String address = "";
            String nc_lydo = "";
            String file_name = "";
            int nc_id = 0;
            String editer = session.getAttribute("username").toString();
           // ServletContext context = request.getSession().getServletContext();
            if(ServletFileUpload.isMultipartContent(request)){
                System.out.println("EDITING");
                try {
                    List<FileItem> multiparts = new ServletFileUpload(
                            new DiskFileItemFactory()).parseRequest(request);

                    for(FileItem item : multiparts){
                        if(!item.isFormField()){
                            System.out.println("EDITING x");
                            File f= new File(item.getName());
                            if(!f.exists()){
                                file_name = new File(item.getName()).getName();
                                item.write( new File(UPLOAD_DIRECTORY + File.separator + file_name));
                            }else{
                                file_name = new File(item.getName()).getName()+"-001";
                                item.write( new File(UPLOAD_DIRECTORY + File.separator + file_name));
                            }

                        }else{
                            System.out.println("EDITING xxx");
                            String fieldname = item.getFieldName();
                            String fieldvalue = item.getString();
                            switch (fieldname) {
                                case "nc_tenkh": nameKh = new String (fieldvalue.getBytes ("iso-8859-1"), "UTF-8");
                                    break;
                                case "nc_soid": maso = new String (fieldvalue.getBytes ("iso-8859-1"), "UTF-8");
                                    break;
                                case "nc_diachi": address = new String (fieldvalue.getBytes ("iso-8859-1"), "UTF-8");
                                    break;
                                case "nc_lydo": nc_lydo = new String (fieldvalue.getBytes ("iso-8859-1"), "UTF-8");
                                    break;
                                case "nc_id": nc_id = Integer.parseInt(fieldvalue);
                                    break;
                                default:
                                    break;
                            }
                            if(file_name.isEmpty()){
                                System.out.println("EDITING emty");
                                boolean checkNC = nganchan.updateNC(nameKh, maso, address, editer, nc_id,nc_lydo);
                            }else{
                                System.out.println("EDITING non emty");
                                boolean checkNC = nganchan.updateNC(nameKh, maso, editer, file_name, editer, nc_id,nc_lydo);
                            }
                        }

                    }
                    
                    
                    //File uploaded successfully
                    request.setAttribute("message", "File Uploaded Successfully");
                } catch (Exception ex) {
                    request.setAttribute("message", "File Upload Failed due to " + ex);
                }

            }else{
                System.out.println("EDITING 2");
                request.setAttribute("message",
                        "Sorry this Servlet only handles file upload request");
            }
          //  request.getRequestDispatcher("nganchan.jsp?page=nganchan").forward(request, response);
            response.sendRedirect("nganchan.jsp?page=nganchan");

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
