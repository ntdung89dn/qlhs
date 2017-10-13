/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import com.ttdk.connect.DBConnect;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thorfinn
 */
public class NhanVien {
    private Connection connect;
    private PreparedStatement pstm;
    private ResultSet rs;
    
    public String tenNhanVien(){
        String name = "";
        
        return name;
    }
    
    public ArrayList<NhanVienBean> getAllnvName(){
        ArrayList<NhanVienBean> nvArr = new ArrayList<>();
        
        String sql = "select ifnull(tbl_username.U_IsNL,0) as isNL,tbl_username.U_ID as uid ,U_FullName as fullname from tbl_username\n" +
                        "where tbl_username.U_Status = 1 ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                if(rs.getString("uid").equals("admin")){
                    continue;
                }
                NhanVienBean nvBean = new NhanVienBean();
                nvBean.setUsername(rs.getString("uid"));
                nvBean.setFullname(rs.getString("fullname"));
                boolean isNL = true;
                if(rs.getInt("isNL")== 0 ){
                    isNL = false;
                }
                nvBean.setIsNhapLieu(isNL);
                nvArr.add(nvBean);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return nvArr;
    }
    
    public ArrayList<NhanVienBean> getNvNhapLieu(){
        ArrayList<NhanVienBean> nvArr = new ArrayList<>();
        
        String sql = "select tbl_username.U_ID as uid ,U_FullName as fullname from tbl_username\n" +
                    "where tbl_username.U_Status = 1 and tbl_username.U_IsNL =1;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                NhanVienBean nvBean = new NhanVienBean();
                nvBean.setUsername(rs.getString("uid"));
                nvBean.setFullname(rs.getString("fullname"));
                nvArr.add(nvBean);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return nvArr;
    }
    // lấy định mức trong ngày
    public ArrayList<NhanVienBean> dinhMuc(){
        String sql = "select tbl_username.U_ID as uid ,U_FullName as fullname,\n" +
            "(select sum(tbl_phandon.PD_TS) from tbl_phandon where tbl_phandon.PD_NVnhan = tbl_username.U_ID and tbl_phandon.PD_Time = ? ) as dinhmuc,\n" +
            "(select sum(tbl_phandon.PD_TS) from tbl_phandon where tbl_phandon.PD_NVnhan = tbl_username.U_ID and tbl_phandon.PD_Time = ? ) as tongdinhmuc from tbl_username \n" +
            "where tbl_username.U_Status = 1;";
        ArrayList<NhanVienBean> nvArr = new ArrayList<>();
        connect = new DBConnect().dbConnect();
        java.util.Date now = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(now.getTime());
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setDate(1, sqlDate);
            pstm.setDate(2, sqlDate);
            rs = pstm.executeQuery();
            while(rs.next()){
                NhanVienBean nvBean = new NhanVienBean();
                nvBean.setUsername(rs.getString("uid"));
                nvBean.setFullname(rs.getString("fullname"));
                nvBean.setDinhmuc(rs.getInt("dinhmuc"));
                nvBean.setDinhmucreal(rs.getInt("tongdinhmuc"));
                nvArr.add(nvBean);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nvArr;
    }
    // lay dinh muc theo khoang thoi gian
    public ArrayList<NhanVienBean> dinhMuc(Date fromday,Date today){
        String sql = "SELECT * FROM qlhsdb.tbl_dinhmuc " +
            "where  DM_Time = ? ;";
        ArrayList<NhanVienBean> nvArr = new ArrayList<>();
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setDate(1, fromday);
            pstm.setDate(2, today);
            pstm.setDate(3, fromday);
            pstm.setDate(4, today);
            rs = pstm.executeQuery();
            while(rs.next()){
                NhanVienBean nvBean = new NhanVienBean();
                nvBean.setUsername(rs.getString("uid"));
                nvBean.setFullname(rs.getString("fullname"));
                nvBean.setDinhmuc(rs.getInt("dinhmuc"));
                nvBean.setDinhmucreal(rs.getInt("tongdinhmuc"));
                nvArr.add(nvBean);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nvArr;
    }
    
    // Lưu đinh mức
    
    public void insertDinhMuc(int dinhmuc,String userid,String ngayphan){
        String sql = "insert into tbl_dinhmuc(DM_DM,DM_NVnhan,DM_Time) values(?,?,STR_TO_DATE(?, '%d-%m-%Y'));";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, dinhmuc);
            pstm.setString(2, userid);
            pstm.setString(3, ngayphan);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // kiểm tra định mức
    public int checkDinhMuc(String nvNhan,String ngaynhap){
        int dmid = -1;
        String sql = "select DM_ID from  tbl_dinhmuc where DM_NVnhan = ? and DM_Time = STR_TO_DATE(?,'%d-%m-%Y'); ";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, nvNhan);
            pstm.setString(2, ngaynhap);
            rs = pstm.executeQuery();
            while(rs.next()){
                dmid = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dmid;
    }
    // Cập nhật định mức theo id
    public void updateDinhMuc(int slts,int dmid){
        String sql = "update tbl_dinhmuc set DM_DM = DM_DM + ? where DM_ID =  ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, slts);
            pstm.setInt(2, dmid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // Cập nhật định mức theo user id va ngay
    public void updateDinhMuc(int slts,String nvNhan,String ngaynhap){
        String sql = "update tbl_dinhmuc set DM_DM = DM_DM - ? where DM_NVnhan =  ? and str_to_date(?,'%d-%m-%Y');";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, slts);
            pstm.setString(2, nvNhan);
            pstm.setString(3, ngaynhap);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // Lưu nhân viên nhập liệu
    
    public void insertnvNL(String userid){
        String sql = "insert into tbl_nvnhaplieu(NL_NV) values(?);";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, userid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Load định mức theo ngày
    public  ArrayList loadDMDay(String date,String username){
        ArrayList dt = new ArrayList();
        String sql = "select DM_ID as id,DM_DM as dinhmuc,tbl_dinhmuc.DM_NVnhan as username,\n" +
                            "tbl_username.U_FullName as fullname\n" +
                            " from tbl_dinhmuc\n" +
                            " inner join tbl_username on tbl_username.U_ID = tbl_dinhmuc.DM_NVnhan\n" +
                            " where tbl_dinhmuc.DM_Time = str_to_date(?,'%d-%m-%Y') and DM_NVnhan = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, date);
            pstm.setString(2, username);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String fullname = rs.getString("fullname");
                String userid = rs.getString("username");
                int dinhmuc = rs.getInt("dinhmuc");
                dt.add(id);dt.add(fullname);dt.add(userid);
                dt.add(dinhmuc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dt;
    }
    
    //Lấy tất cả username
    public ArrayList<ArrayList<String>> getUsername(){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select U_ID as username,U_FullName as fullname  from tbl_username  where U_IsNL = 1 order by tbl_username.U_ID asc;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt =new ArrayList<>();
                String userid = rs.getString("username");
                String fullname = rs.getString("fullname");
                dt.add(userid);dt.add(fullname);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Lấy tổng số tài sản trong ngày
    public int totalTSPhan(String date){
        int slts = 0;
        String sql = "select sum(tbl_don.D_SLTS) from tbl_don where tbl_don.D_Date = str_to_date(?,'%d-%m-%Y');";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, date);
            rs = pstm.executeQuery();
            while(rs.next()){
                slts = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return slts;
    }
    // Lấy số người được phân đơn
    public ArrayList<String> loadNvPhandon(){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select * from tbl_nvnhaplieu;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // Lấy số người được phân đơn theo ngày
    public ArrayList<String> loadNVPhantheongay(String date){
        ArrayList<String> data = new ArrayList<>();
         String sql = " select distinct PD_NVnhan from tbl_phandon "
                + "where DATE(PD_Time) = str_to_date(?,'%d-%m-%Y');";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1,date);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // lấy định mức theo ngày
    public ArrayList<ArrayList> getDinhMuc(String date){
        ArrayList<ArrayList> data = new ArrayList<>();
        String sql = "select DISTINCT  DM_NVNhan as username, ifnull(DM_DM,0) as dinhmuc ,date_format(DM_TIME,'%d-%m-%Y') as dmday,\n" +
            "tbl_username.U_Fullname as fullname\n" +
            " from tbl_dinhmuc \n" +
            " inner join tbl_username on tbl_username.U_ID = tbl_dinhmuc.DM_NVNhan\n" +
            " where tbl_dinhmuc.DM_Time = str_to_date(?,'%d-%m-%Y');";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1,date);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList dt = new ArrayList();
                dt.add(rs.getString("username"));
                dt.add(rs.getString("fullname"));
                dt.add(rs.getInt("dinhmuc"));
                dt.add(rs.getString("dmday"));
                
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // return table phandon
    public String loadTablenvPhan(String date){
        String table = "";
        ArrayList<ArrayList<String>> userList = getUsername();
        int slts = totalTSPhan(date);
       // ArrayList<String> nvP =  null;
       int listSize = userList.size();
       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); //For declaring values in new date objects. use same date format when creating dates
       int checkDate = 0;
        try {
            java.util.Date date1 = sdf.parse(date);
            java.util.Date today = new  java.util.Date();
            System.out.println("--------------------------------------------------------");
            System.out.println("DAY ="+date);
            System.out.println("TODAY ="+sdf.format(today));
            System.out.println("COMPARE ="+sdf.format(date1).compareTo(sdf.format(today)));
            checkDate = sdf.format(date1).compareTo(sdf.format(today));
            System.out.println("--------------------------------------------------------");
        } catch (ParseException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<ArrayList> dinhmucList =  null;
        dinhmucList = getDinhMuc(date);
       if(checkDate !=0){
           if( !dinhmucList.isEmpty() ){
                listSize = dinhmucList.size();
            }
       }
        
        int[] totalDM = new int[listSize];
        int stsChung = slts/listSize;
        for(int i=0;i<listSize;i++){
            totalDM[i] = stsChung;
        }
        int tsdu = slts%listSize;
        for(int i=0; i< tsdu;i++){
              totalDM[i] += 1;
        }

    //    int stsChung = slts/nvP.size();
  //      System.out.println(stsChung);
  String username = "";
            String fullname = "";
        for(int i=0; i< listSize;i++){
            
            if( checkDate !=0 ){
                username = dinhmucList.get(i).get(0).toString();
                fullname = dinhmucList.get(i).get(1).toString();
            }else{
                username = userList.get(i).get(0);
                fullname = userList.get(i).get(1);
            }
            ArrayList data = loadDMDay(date, username);
             table +="<tr>";
             table += "<td>"+(i+1)+"</td>";
             table += "<td>"+fullname+"</td>";
             table += "<td><input type='checkbox' value='"+username+"'></td>";
             table += "<td><strong>"+totalDM[i]+"<span>("+(totalDM[i]/slts)*100+"%)</span></strong></td>";
            if(!data.isEmpty()){
                table += "<td>"+data.get(3)+"<span style='color:red'>("+(Double.parseDouble(data.get(3).toString())/totalDM[i])*100+"%)</span></strong></td>";
                table += "<td><input type='checkbox' value='"+data.get(2)+"' class='cb_phandon'><input type='checkbox' value='"+data.get(1)+"' hidden></td>";
            }else{
                table += "<td><strong>0<span style='color:red'>(0%)</span></strong></td>";
                table += "<td><input type='checkbox' value='"+username+"' class='cb_phandon'><input type='checkbox' value='"+i+"' hidden></td>";
            }
           table += "</tr>";
        }
        return table;
    }
    
    // hồi đơn định mức
    
    // lưu nhân viên nhập liệu
    public void updateNVNhapLieu(String username,int nl){
        String sql = "update tbl_username set U_IsNL = ? where U_ID = ? ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, nl);
            pstm.setString(2,username);
            pstm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
// Lấy fullname nhân viên
    public String getFullName(String username){
        String fullname = "";
        String sql = "select tbl_username.U_FullName from tbl_username where tbl_username.U_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1,username);
            rs =  pstm.executeQuery();
            while(rs.next()){
                fullname = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fullname;
    }
    
}
