/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import com.ttdk.connect.DBConnect;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Thorfinn
 */
public class NganChanDB implements Serializable{
   // private ArrayList<ArrayList<Object>> nganchan;
    private Connection connect ;
    PreparedStatement pstm = null;
    private ResultSet rs =  null;
    
    public NganChanDB(){
        
    }
    
    // get ArrayList của ngăn chặn
    public ArrayList getData(int page){
        ArrayList<ArrayList<Object>> nganchan = new ArrayList<ArrayList<Object>>();
        
        String sql = "select DISTINCT NC_ID as ID,NC_Name as name,NC_maso as maso,NC_Address as diachi, NC_Lydo as lydo, NC_file,NC_Created,NC_modified "
                + "from tbl_nganchans limit "+(page-1)*10+",10;";
        connect = new DBConnect().dbConnect();
        if(connect != null){
            try {
                pstm = connect.prepareStatement(sql);
                rs = pstm.executeQuery();
                while(rs.next()){
                    ArrayList<Object> data = new ArrayList<Object>();
                    data.add(rs.getInt(1));
                    data.add(rs.getString(2));
                    data.add(rs.getString(3));
                    data.add(rs.getString(4));
                    data.add(rs.getString(5));
                    data.add(rs.getString(6));
                    data.add(rs.getString(7));
                    data.add(rs.getDate(8));
                    nganchan.add(data);
                }
                //out.print("RS = "+ rs.getString(0));
            } catch (SQLException ex) {
                Logger.getLogger(NganChanDB.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
            return nganchan;
    }
    
    // Load tbody của ngăn chặn
    public String loadTableNc(int page){
        String tbody = "";
        ArrayList<ArrayList<Object>> ncList = getData(page);
        System.out.println(ncList.size());
        for(int i=0; i < ncList.size();i++){
            tbody+= "<tr>";
            tbody += "<td>"+i+"</td>";
            tbody += "<td>"+ncList.get(i).get(1)+"</td>";
            tbody += "<td>"+ncList.get(i).get(2)+"</td>";
            tbody += "<td>"+ncList.get(i).get(3)+"</td>";
            tbody += "<td>"+ncList.get(i).get(4)+"</td>";
            tbody += "<td><a target='_blank' href='file/"+ncList.get(i).get(5)+"'>"+ncList.get(i).get(5)+"</a></td>";
            tbody +="<td><img src='./images/document_edit.png' data-toggle='modal' onclick='editNC("+ncList.get(i).get(0)+")'></td>\n" +
            "<td ><img src='./images/document_delete.png' onclick='deleteNC("+ncList.get(i).get(0)+")'></td></tr>";
            tbody+= "</tr>";
        }
        return tbody;
    }
    // Lấy tổng số trang ngăn chặn
    public int getPageNC(){
        int total = 0;
        String sql_nganchan = "select count(NC_ID) "
                + "from tbl_nganchans ;";
        connect = new DBConnect().dbConnect();
        if(connect != null){
            try {
                pstm = connect.prepareStatement(sql_nganchan);
                rs=  pstm.executeQuery();
                while(rs.next()){
                    total = rs.getInt(1);
                }
                //out.print("RS = "+ rs.getString(0));
            } catch (SQLException ex) {
                Logger.getLogger(NganChanDB.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return (total/10)+1;
    }
    
    // insert ngăn chặn
    public void insertNC(String name, String maso, String address,String file, String empCreate,String content) throws SQLException{
        int ldID = -1;
        String insert_sql = "Insert into tbl_nganchans(NC_Name,NC_maso,NC_Address,NC_Lydo,NC_file,NC_Created,NC_Edited,NC_Status) values(?,?,?,?,?,?,?,?,?)";
        connect = new DBConnect().dbConnect();
        if(connect != null){
            try {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy"); 
                Date date =  new Date();
                String dateStr =  dateFormat.format(date);
                java.sql.Date daySQL = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                pstm = connect.prepareStatement(insert_sql);
                pstm.setString(1, name);
                pstm.setString(2, maso);
                pstm.setString(3, address);
                pstm.setString(4, content);
                pstm.setString(5, file);
                pstm.setDate(6, daySQL);
                pstm.setString(7, empCreate);
                pstm.setBoolean(8, true);
                pstm.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(NganChanDB.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void deleteNC(int ncid,String url) throws SQLException{
        String selectLDID = "select LD_IDNC,NC_file from tbl_nganchans where NC_ID = ?";
        String delNCStr = "DELETE from tbl_nganchans where NC_ID = ?";
        connect = new DBConnect().dbConnect();
        int ldid = -1;
        String file = "";
        if(connect != null){
            pstm = connect.prepareStatement(selectLDID);
            pstm.setInt(1, ncid);
            rs = pstm.executeQuery();
            if(rs.next()){
                ldid = rs.getInt(1);
                file = rs.getString(2);
            }
            pstm.close();
            File f = new File(url+file);
            f.delete();
            pstm = connect.prepareStatement(delNCStr);
            pstm.setInt(1, ncid);
            pstm.executeUpdate();
            pstm.close();
            
        }
    }
    
    public String loadNCAjax(String term){
        String ncKH = "";
        JSONArray arr = new JSONArray();    
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement("SELECT NC_Name,NC_maso FROM tbl_nganchans  WHERE NC_Name  LIKE ? LIMIT 15 ;");
            pstm.setString(1, "%"+term + "%");
                ResultSet rs = pstm.executeQuery();
                while(rs.next()){
                    String name = rs.getString("NC_Name");
                    String id = rs.getString("NC_maso"); 
                    System.out.println(name);
                    System.out.println(id);
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("name", name);
                        obj.put("account", id);
                        arr.put(obj);
                    } catch (JSONException ex) {
                        Logger.getLogger(NganChanDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
        } catch (SQLException ex) {
            Logger.getLogger(NganChanDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        ncKH = arr.toString();
        return ncKH;
    }
    
    public ArrayList<String> checkNC(String name,String maso){
        ArrayList<String> ncArr = new ArrayList<>();
        String sql = "select  NC_file as fileluu from tbl_nganchans where NC_Name = ? and NC_maso = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, maso);
            rs = pstm.executeQuery();
            while(rs.next()){
                ncArr.add(rs.getString("fileluu"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NganChanDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        return ncArr;
    }
    
    // lấy thông tin ngăn chặn bằng nc id
    public ArrayList<String> loadEditNC(int ncid){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select DISTINCT NC_ID as ID,NC_Name as name,NC_maso as maso,NC_Address as diachi,NC_Lydo as lydo "
                + "from tbl_nganchans   where nc.NC_ID = ?; ";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, ncid);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString("name"));
                data.add(rs.getString("maso"));
                data.add(rs.getString("diachi"));
                data.add(rs.getString("lydo"));
                data.add(rs.getString("ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NganChanDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // cập nhật ngăn chặn có file
    public boolean updateNC(String namenc,String soid,String diachi,String filename,String editer,int ncid,String lydo){
        boolean check = false;
        String sql = "update tbl_nganchans set NC_Name = ?, NC_maso = ?, NC_Address = ?, NC_Edited = ?,NC_file = ? ,NC_Lydo where NC_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, namenc);
            pstm.setString(2, soid);
            pstm.setString(3, diachi);
            pstm.setString(4, editer);
            pstm.setString(5, filename);
            pstm.setString(6, lydo);
            pstm.setInt(7, ncid);
            pstm.executeUpdate();
            check = true;
        } catch (SQLException ex) {
            Logger.getLogger(NganChanDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    // cập nhật ngăn chặn không file
    public boolean updateNC(String namenc,String soid,String diachi,String editer,int ncid,String lydo){
        boolean check = false;
        String sql = "update tbl_nganchans set NC_Name = ?, NC_maso = ?, NC_Address = ? , NC_Lydo = ?where NC_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, namenc);
            pstm.setString(2, soid);
            pstm.setString(3, diachi);
            pstm.setString(4, lydo);
            pstm.setInt(5, ncid);
            pstm.executeUpdate();
            check = true;
        } catch (SQLException ex) {
            Logger.getLogger(NganChanDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
  
}
