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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ntdung
 */
public class TrungTamInfor {
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    
    public ArrayList<String> getInfor(){
        ArrayList<String> ttiArr = new ArrayList<>();
        String sql = "select tbl_ttinfor.TTI_Name as name ,tbl_ttinfor.TTI_Address as address \n" +
                            "from tbl_ttinfor \n" +
                            "where tbl_ttinfor.TTI_Status = '1';";
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                String name = rs.getString("name");
                String address = rs.getString("address");
                ttiArr.add(name);
                ttiArr.add(address);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrungTamInfor.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ttiArr;
    }


}
