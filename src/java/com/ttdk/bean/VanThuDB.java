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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thorfinn
 */
public class VanThuDB {
    private Connection connect;
    private PreparedStatement pstm;
    private ResultSet rs;
    
    public void addVanThu(Date ngaygoi, String noinhan,String sobuudien,
            String sobienlai,String ghichu,String sohoso){
        String sqlVT = "Insert into tbl_vanthu(VT_maBD,VT_ngaygoi,VT_noinhan,VT_Ghichu) values(?,?,?,?)";
        String sqlBL = "insert into tbl_bienlai(BL_ID,BL_maso) values(?,?)";
        String sqlVTdon = "insert into tbl_vtdon(VTD_ID,VTD_maso) values(?,?)";
        connect = new DBConnect().dbConnect();
        int key = -1;
        try {
            pstm = connect.prepareStatement(sqlVT,Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, sobuudien);
            pstm.setDate(2, ngaygoi);
            pstm.setString(3, noinhan); 
            pstm.setString(3, ghichu);             
            int t = pstm.executeUpdate();          
            rs = pstm.getGeneratedKeys();
            if(rs.next()){
                key = rs.getInt(1);
            }
            rs.close();
            pstm.close();
            String[] soBL = sobienlai.split(",");
            String[] donID = sohoso.split(".");
            if(key != -1){
                pstm = connect.prepareStatement(sqlBL);
                for (String soBL1 : soBL) { 
                    pstm.setInt(1, key);
                    int soblInt = Integer.parseInt(soBL1);
                    pstm.setInt(2, soblInt);
                    pstm.addBatch();
                }
                pstm.executeUpdate();
                pstm = connect.prepareStatement(sqlVTdon);
                for (String donID1 : donID) { 
                    pstm.setInt(1, key);
                    int donIDInt = Integer.parseInt(donID1);
                    pstm.setInt(2, donIDInt);
                    pstm.addBatch();
                }
                pstm.executeUpdate();
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(VanThuDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
