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
public class InforPrint {
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    
    public InforPrint(){
        
    }
    
    public ArrayList<String> loadPrintInfor(int donid){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select ifnull(tbl_don.D_GioHL,0) as giohl, ifnull(tbl_don.D_PhutHL,0) as phuthl,DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngayhl,\n" +
                        "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan,"+
                        "ifnull(tbl_don.D_MDO,'') as dononline,\n" +
                        "ifnull(tbl_don.P_Pin,'') as mapin,\n" +
                        "ifnull(group_concat(DISTINCT tbl_bnbd.KHD_Name,'_',tbl_bnbd.KHD_Address separator ' & '),'') as bnbd,\n" +
                        "ifnull(group_concat(DISTINCT tbl_benbaodam.BDB_Name,'_',tbl_benbaodam.BDB_CMND separator '&'),'') as benbaodam \n" +
                        "from tbl_don\n" +
                        "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                        "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                        "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                        "left join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID \n" +
                        "left join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                        "where tbl_don.D_ID = ?\n" +
                        "group by tbl_don.D_ID order by tbl_don.D_ID DESC;";
//        System.out.println(donid);
//        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                String giohl = rs.getString("giohl");
                String phuthl = rs.getString("phuthl");
                String ngayhl = rs.getString("ngayhl");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = "";
                if( !manhan.equals("0")){
                    maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan,ngayhl);
                }
               //  String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan,ngayhl);
                String maonline = rs.getString("dononline");
                String mapin = rs.getString("mapin");
                String bnbd = rs.getString("bnbd");
                String benbaodam = rs.getString("benbaodam");
                data.add(giohl);data.add(phuthl);data.add(ngayhl);
                data.add(maonline);data.add(mapin);
                data.add(bnbd);data.add(benbaodam);data.add(maloainhan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InforPrint.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // update dữ liệu
    public int updateDonPrint(String oldvalue,String updatevalue,int donid,String typeid){
        String sql = "";
        System.out.println(typeid);
        switch(typeid){
            case "bnbd" : sql ="update tbl_bnbd set KHD_Name = '"+updatevalue.trim()+"' where KHD_ID = "+donid+" and KHD_Name like '%"+oldvalue.trim()+"%';";
                break;
            case "bnbddc" : sql = "update tbl_bnbd set KHD_Address = '"+updatevalue.trim()+"' where KHD_ID = "+donid+" and KHD_Address like '%"+oldvalue.trim()+"%';";
                break;
            case "bbd" : sql = "update tbl_benbaodam set BDB_Name = '"+updatevalue.trim()+"' where D_ID = "+donid+" and BDB_Name like '%"+oldvalue.trim()+"%' ;";
                break;
            case "cmnd" : sql = "update tbl_benbaodam set BDB_CMND = '"+updatevalue.trim()+"' where D_ID = "+donid+" and BDB_CMND like '%"+oldvalue.trim()+"%' ;";
                break;
            case "mapin" : sql = "update tbl_don set P_Pin = '"+updatevalue.trim()+"' where D_ID = "+donid+";";
                break;
            default:
                break;
        }
        System.out.println(sql);
        int result = 0;
        if(!sql.equals("")){
            connect = new DBConnect().dbConnect();
            try {
                pstm = connect.prepareStatement(sql);
               result =  pstm.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(InforPrint.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        return result;
    }
}
