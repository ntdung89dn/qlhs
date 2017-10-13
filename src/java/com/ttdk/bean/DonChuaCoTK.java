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
public class DonChuaCoTK {
    
    private Connection connect ;
    PreparedStatement pstm = null;
    private ResultSet rs =  null;
    
    // Load đơn chưa có tài khoản
    public ArrayList<ArrayList> loadDonChuaCoTK(int page,String ngaynhap,String maonline,int loaidon,int loaidk,int loaihinhnhan,
            String manhan,ArrayList<Integer> donids){
        String whereSql = "";
        if(!ngaynhap.equals("")){
            whereSql += " and tbl_don.D_Date >= str_to_date('"+ngaynhap+"','%d-%m-%Y') ";
        }
        if(!maonline.equals("")){
            String[] maoS = maonline.split(",");
            if(maoS.length == 1){
                whereSql += " and tbl_don.D_MDO  like '%"+maonline+"%'";
            }else{
                whereSql += " and tbl_don.D_MDO in ("+maonline+") ";
            }
            
        }
        if(!manhan.equals("")){
            String[] mnS = manhan.split(",");
            if(mnS.length == 1){
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhan)+"%' ";
            }else{
                whereSql += " and tbl_don.D_manhan in ("+manhan+") ";
            }
        }
        if(loaidon != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidon;
        }
        if(loaidk != 0){
            whereSql += " and tbl_don.DK_ID = "+loaidk;
        }
        if(loaihinhnhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhan;
        }
        
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        ArrayList<Integer> khids = getKhangChuaCoTK();
        String khid = khids.toString().replaceAll("\\[", "").replaceAll("\\]", "");
        ArrayList<ArrayList> data =new ArrayList<>();
        String sql = "select tbl_don.D_ID as donid ,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                            "tbl_don.D_SLTS  as slts,tbl_don.D_manhan as manhan ,(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                            "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon," +
                            "ifnull( tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull( tbl_don.P_Pin,'') as mapin \n" +
                            "from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                         //   "where tbl_khachhang.KH_Account  = '' and tbl_khachhang.KH_Status is null "+whereSql+
                            "where tbl_don.KH_ID in ("+khid+") and tbl_don.D_isRemove = 0 "+whereSql+
                            "group by tbl_don.D_ID order by tbl_don.D_ID desc limit "+(page-1)*10+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList dt = new ArrayList();
                int donid = rs.getInt("donid");
                String ngaynhaprs = rs.getString("thoigian");
                int slts = rs.getInt("slts");
                String cctt = rs.getString("cctt");
                String loaidkrs = rs.getString("loaidk");
                String manhanrs = rs.getString("manhan");
                String loainhanrs = rs.getString("loainhan");
                String loaidonrs = rs.getString("loaidon");
                String maloainhan = new NhapDon().returnManhan(loaidkrs, manhanrs, loainhanrs, ngaynhaprs);
                String dononline = rs.getString("dononline");
                String mapin = rs.getString("mapin");
                dt.add(donid);dt.add(ngaynhaprs);dt.add(dononline);dt.add(mapin);
                dt.add(maloainhan);dt.add(loaidonrs);dt.add(slts);dt.add(cctt);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonChuaCoTK.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        return data;
    }
    
    // Lấy số trang đơn chưa có số tài khoản
    public int getPageDonCCTK(int page,String ngaynhap,String maonline,int loaidon,int loaidk,int loaihinhnhan,
            String manhan,String bnbd,String bbd){
        int total = 0;
        String whereSql = "";
        if(!ngaynhap.equals("")){
            whereSql += " and tbl_don.D_Date >= str_to_date('"+ngaynhap+"','%d-%m-%Y') ";
        }
        if(!maonline.equals("")){
            String[] maoS = maonline.split(",");
            if(maoS.length == 1){
                whereSql += " and tbl_don.D_MDO like '%"+maonline+"%'";
            }else{
                whereSql += " and tbl_don.D_MDO in ("+maonline+") ";
            }
            
        }
        if(!manhan.equals("")){
            String[] mnS = manhan.split(",");
            if(mnS.length == 1){
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhan)+"%' ";
            }else{
                whereSql += " and tbl_don.D_manhan in ("+manhan+") ";
            }
        }
        if(loaidk != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidk;
        }
        if(loaidon != 0){
            whereSql += " and tbl_loaidk.DK_ID = "+loaidon;
        }
        if(loaihinhnhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhan;
        }
        
        ArrayList<Integer> donids =  new ArrayList<>();
        if(!bnbd.trim().equals("")){
            donids.addAll(new NhapDon().searchDonIDByBNBD(bnbd,page)) ;
        }
        if(!bbd.trim().equals("")){
            donids.addAll(new NhapDon().searchDonIDByBBD(bbd,page));
        }
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", "")+") ";
        }
        ArrayList<Integer> khids = getKhangChuaCoTK();
        String khid = khids.toString().replaceAll("\\[", "").replaceAll("\\]", "");
        String sql = "select count(tbl_don.D_ID) as donid "+
                        "from tbl_don\n" +
                        "where  tbl_don.KH_ID in ("+khid+")  and tbl_don.D_isRemove = 0 "+whereSql+";";
//        String sql = "select count(tbl_don.D_ID) as donid "+
//                            "from tbl_don\n" +
//                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
//                            "where  tbl_khachhang.KH_Account  = ''  and tbl_khachhang.KH_Status is null  "+whereSql+";";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonChuaCoTK.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        total = (total/10)+1;
        return total;
    }
    // Load table đơn chưa có số tk
    public String loadTableDonCCTK(int page,String ngaynhap,String maonline,int loaidon,int loaidk,int loaihinhnhan,String manhan,String bnbd,String bbd){
        String table = "";
        ArrayList<Integer> donIds = new ArrayList<>();
        if(!bnbd.trim().equals("")){
            donIds.addAll(new NhapDon().searchDonIDByBNBD(bnbd,page)) ;
        }
        if(!bbd.trim().equals("")){
            donIds.addAll(new NhapDon().searchDonIDByBBD(bbd, page));
        }
        ArrayList<ArrayList> donList = loadDonChuaCoTK(page, ngaynhap, maonline, loaidon, loaidk, loaihinhnhan, manhan,donIds);
        for(int i=0;i<donList.size();i++){
            table += "<tr>";
            int id = Integer.parseInt(donList.get(i).get(0).toString());
            String bnbaodam = new NhapDon().loadBnbdDon(id);
            String benbaodam = new NhapDon().loadBBDDon(id);
            table += "<td>"+donList.get(i).get(1)+"</td>";
            table += "<td>"+donList.get(i).get(2)+"</td>";
            table += "<td>"+donList.get(i).get(3)+"</td>";
            table += "<td>"+donList.get(i).get(4)+"</td>";
            table += "<td>"+donList.get(i).get(5)+"</td>";
            table += "<td>"+donList.get(i).get(6)+"</td>";
            table += "<td>"+donList.get(i).get(7)+"</td>";
            table += "<td>"+bnbaodam+"</td>";
            table += "<td>"+benbaodam+"</td>";
            table +="<td><img src=\"./images/document_edit.png\" data-toggle=\"modal\" onclick=\"editDon('"+id+"')\"></td>\n" +
                "<td><img src=\"./images/document_delete.png\" onclick=\"deleteDon('"+id+"')\"></td></tr>";
            table += "</tr>";
        }
        return table;
    }
    
    // get khach hang chua co stk
    public ArrayList<Integer> getKhangChuaCoTK(){
        ArrayList<Integer> data = new ArrayList<>();
        String sql = " select tbl_khachhang.KH_ID from tbl_khachhang where tbl_khachhang.KH_Account  = ''\n" +
                        " and tbl_khachhang.KH_Status is null;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonChuaCoTK.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        return data;
    }
}
