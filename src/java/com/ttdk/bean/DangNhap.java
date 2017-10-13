/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import com.ttdk.connect.DBConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ntdung
 */
public class DangNhap {
        private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    
    // lấy phân quyền
    public ArrayList<ArrayList<String>> getPhanQuyen(String username){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select tbl_phanquyen.Q_ID as qid,tbl_phanquyen.PQ_Status as PQstatus\n" +
                    "from tbl_username \n" +
                    "inner join (tbl_phannhom inner join tbl_phanquyen on tbl_phanquyen.R_ID = tbl_phannhom.R_ID)\n" +
                    "on tbl_phannhom.U_ID = tbl_username.U_ID where tbl_username.U_ID = ?;";
        connect = new DBConnect().dbConnect();
            try {
                pstm = connect.prepareStatement(sql);
                pstm.setString(1, username);
                rs= pstm.executeQuery();
                while(rs.next()){
                    ArrayList<String> dt = new ArrayList<>();
                    dt.add(rs.getString("qid"));
                    dt.add(rs.getString("PQstatus"));
                    data.add(dt);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return data;
    }
    // check login
    public ArrayList checkLogin(String username,String password){
        //boolean check = false;
        ArrayList data = null;
        String sql = "select tbl_username.U_ID as username, tbl_username.U_FullName as fullname,\n" +
                            "tbl_role.R_Des as roledes,tbl_phannhom.R_ID as roleid \n" +
                            "from tbl_phannhom\n" +
                            "inner join tbl_username on tbl_username.U_ID = tbl_phannhom.U_ID\n" +
                            "inner join tbl_role on tbl_role.R_ID = tbl_phannhom.R_ID"
                + " where LOWER(tbl_username.U_ID) = ? and tbl_username.U_Pass= ?;";
        connect = new DBConnect().dbConnect();
            try {
                pstm = connect.prepareStatement(sql);
                pstm.setString(1, username);
                pstm.setString(2, password);
                rs = pstm.executeQuery();
                while(rs.next()){
                    data = new ArrayList();
                    data.add(rs.getString("username"));
                    data.add(rs.getString("fullname"));
                    data.add(rs.getString("roledes"));
                    data.add(rs.getString("roleid"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        return data;
    }
    
    public boolean isNvNhapdon(String username){
        boolean check = false;
        String sql = "select ifnull(U_IsNL,0) from tbl_username \n" 
                + "where U_ID = ? ; ";
        connect = new DBConnect().dbConnect();
            try {
                pstm = connect.prepareStatement(sql);
                pstm.setString(1, username);
                rs =pstm.executeQuery();
                while(rs.next()){
                    if(rs.getInt(1) ==1){
                        check = true;
                    }
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        return check;
    }
    
    // cập nhật mật khẩu
    public int updatePassword(String username, String password){
        int key  = 0;
        String sql = "update tbl_username set tbl_username.U_Pass = ? where tbl_username.U_ID = ?;";
        connect = new DBConnect().dbConnect();
            try {
                pstm = connect.prepareStatement(sql);
                pstm.setString(1, password);
                pstm.setString(2, username);
                key = pstm.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        return key;
    }
}
