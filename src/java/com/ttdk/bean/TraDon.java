/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import com.ttdk.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NTD
 */
public class TraDon {
    private Connection connect;
    private PreparedStatement pstm;
    private ResultSet rs;
    public TraDon(){
     
    }
    
    
    // Hồi đơn đơn
    public void hoiDon(int pdid){
        String sql = "delete from tbl_phandon where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, pdid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TraDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Trả đơn
    
    public void traDon(String daytime,int pdid){
        
        String sql = "Update tbl_phandon SET PD_tra = ? where PD_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, daytime);
             pstm.setInt(2, pdid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TraDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Kiểm tra đơn phân nhiều 
    public ArrayList<ArrayList> checkDonNhieuTs(int donid){
        ArrayList<ArrayList> userList = new ArrayList<>();
        String sql = "select PD_NVnhan,PD_TS from tbl_phandon where tbl_phandon.D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs= pstm.executeQuery();
            while(rs.next()){
                ArrayList dt = new ArrayList();
                dt.add(rs.getString(1));
                dt.add(rs.getString(2));
                userList.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TraDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return userList;
    }
    
    // 
    public void updateTradon(int donid,String maonline,String mapin){
        String sql = "update tbl_don set D_MDO = ? ,P_Pin = ? where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, maonline);
            pstm.setString(2, mapin);
            pstm.setInt(3, donid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TraDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
