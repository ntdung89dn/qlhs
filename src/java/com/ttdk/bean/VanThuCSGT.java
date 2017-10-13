/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import com.ttdk.connect.DBConnect;
import com.ttdk.servlet.NhapdonServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ntdung
 */
public class VanThuCSGT {
    private Connection connect;
    private PreparedStatement pstm;
    private ResultSet rs;
    public VanThuCSGT(){}
    
    
    // Lấy tổng số trang 
    public int pageLoadCSgtcg(int page,String ngaybatdau,String ngayketthuc,String maonline,String manhan,String bnbd,String bbd,String barcode,int loainhan){
        int total = 0;
        String whereSql = "";
        if(!manhan.equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhan+"%'";
            }
        }
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        if(!ngayketthuc.trim().equals("")){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y')";
        }
        
        if(!maonline.equals("")){
            String[] ons = manhan.split(",");
            if(ons.length >1){
                whereSql += " and tbl_don.D_MDO in("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+maonline+"%'";
            }
        }
        if(loainhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loainhan+" ";
        }
//        if(!bnbd.equals("")){
//             whereSql += " and (select group_concat(`KHD_Name` separator ' & ') \n" +
//                                    " as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_don.D_ID order by tbl_bnbd.KHD_ID) like '%"+bnbd.trim()+"%'";
//        }
//        
//        if(!bbd.equals("")){
//             whereSql += " and (select group_concat(`BDB_Name` separator ' & ') \n" +
//                " as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) like '%"+bbd.trim()+"%'";
//        }
        ArrayList<Integer> donids =  new ArrayList<>();
        if(!bnbd.trim().equals("")){
            donids.addAll(new NhapDon().searchDonIDByBNBD(bnbd,page)) ;
        }
        if(!bbd.trim().equals("")){
            donids.addAll(new NhapDon().searchDonIDByBBD(bbd,page));
        }
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        if(!barcode.trim().equals("")){
            whereSql += " and tbl_don.D_ID in ("+barcode+")";
        }
        String sql = "select count(distinct tbl_don.D_ID )" +
                            "from tbl_don\n" +
                            "where tbl_don.D_ID not in(SELECT tbl_doncsgt.D_ID FROM tbl_doncsgt )  \n" +
                            "and tbl_don.DK_ID = 3 "+whereSql+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (total/10)+1;
    }
    
    // Lấy dữ liệu đơn phụ lục chưa gửi csgt
    public ArrayList<ArrayList<String>> loadCsgtChuaGui(int page,String ngaybatdau,String ngayketthuc,String maonline,
            String manhan,String bnbd,String bbd,String barcode,int loainhan){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String whereSql = "";
        if(!manhan.equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhan+"%'";
            }
        }
        
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        if(!ngayketthuc.trim().equals("")){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y')";
        }
        
        if(!maonline.equals("")){
            String[] ons = maonline.split(",");
            if(ons.length >1){
                whereSql += " and tbl_don.D_MDO in("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+maonline+"%'";
            }
        }
        if(loainhan !=0){
            whereSql += " and tbl_don.LN_ID = "+loainhan+ " ";
        }
//        if(!bnbd.equals("")){
//             whereSql += " and (select group_concat(`KHD_Name` separator ' & ') \n" +
//                                    " as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_don.D_ID order by tbl_bnbd.KHD_ID) like '%"+bnbd.trim()+"%'";
//        }
//        
//        if(!bbd.equals("")){
//             whereSql += " and (select group_concat(`BDB_Name` separator ' & ') \n" +
//                " as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) like '%"+bbd.trim()+"%'";
//        }
        ArrayList<Integer> donids =  new ArrayList<>();
        if(!bnbd.trim().equals("")){
            donids.addAll(new NhapDon().searchDonIDByBNBD(bnbd,page)) ;
        }
        if(!bbd.trim().equals("")){
            donids.addAll(new NhapDon().searchDonIDByBBD(bbd,page));
        }
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        if(!barcode.trim().equals("")){
            whereSql += " and tbl_don.D_ID in ("+barcode+")";
        }
        String sql = "select tbl_don.D_ID as donid,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as ngaynhap,\n" +
                            "tbl_don.D_SLTS as slts,(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                            "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,\n" +
                            "tbl_don.D_manhan as manhan,\n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin "+
                            "from tbl_don\n" +
                            "where tbl_don.D_ID not in(SELECT tbl_doncsgt.D_ID FROM tbl_doncsgt) \n" +
                            "and tbl_don.DK_ID = 3  and tbl_don.D_isRemove = 0 "+whereSql+" order by tbl_don.D_ID desc limit "+((page-1)*10)+",10;"; //
        //
        // 
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String donid = rs.getString("donid");
                String ngaynhap = rs.getString("ngaynhap");
                String loaidk = rs.getString("loaidk");
                String manhanrs = rs.getString("manhan");
                String loainhanrs = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhanrs, loainhanrs, ngaynhap);
                String dononline = rs.getString("dononline");
                String mapin = rs.getString("mapin");
                dt.add(donid);dt.add(ngaynhap);
                dt.add(maloainhan);dt.add(dononline);dt.add(mapin);
                data.add(dt);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // View đơn csgt
    public String viewTableCsgtChuaLuuVT(int page,String ngaybatdau,String ngayketthuc,String maonline,
            String manhan,String bnbd,String bbd,String barcode,int loainhan){
        String table = "";
//        dt.add(donid);dt.add(ngaynhap);
//                dt.add(maloainhan);dt.add(dononline);dt.add(mapin);
        ArrayList<ArrayList<String>> data = loadCsgtChuaGui(page, ngaybatdau, ngayketthuc, maonline, manhan, bnbd, bbd,barcode,loainhan);
        for(ArrayList<String> dt : data){
            String donid = dt.get(0);
            String ngaynhap = dt.get(1);
            String maloainhan = dt.get(2);
            String dononline = dt.get(3);
            String mapin = dt.get(4);
            String bnbdrs = new NhapDon().loadBnbdDon(Integer.parseInt(donid));
            String bbdrs = new NhapDon().loadBBDDon(Integer.parseInt(donid));
            table += "<tr>";
            table += "<td>"+ngaynhap+"</td>";
            table += "<td>"+dononline+"</td>";
            table += "<td>"+mapin+"</td>";
            table += "<td>"+maloainhan+"</td>";
            table += "<td>"+bnbdrs+"</td>";
            table += "<td>"+bbdrs+"</td>";
            table += "<td><input type='checkbox' value='"+donid+"'></td>";
            table += "</tr>";
        }
        
        return table;
    }
    
    // Lấy dữ liệu csgt
    public ArrayList<ArrayList> getCsgtDiachi(String diachi){
        ArrayList<ArrayList> data = new ArrayList<>();
        String sql = "select DISTINCT tbl_csgtname.CSN_Name as csname, tbl_csgtdiachi.CSDC_Diachi as diachi,"
                + "tbl_csgtdiachi.CSDC_ID as dcid "
                + " from tbl_csgtname \n" +
                            "inner join tbl_csgtdiachi on tbl_csgtdiachi.CSN_ID = tbl_csgtname.CSN_ID\n" +
                            "where tbl_csgtname.CSN_Name like ? limit 0,3;";
      //  System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, "%"+diachi+"%");
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList list = new ArrayList();
                String name = rs.getString("csname");
                String diachirs = rs.getString("diachi");
                String dcid = rs.getString("dcid");
                list.add(name);
                list.add(diachirs);
                list.add(dcid);
                data.add(list);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Lưu đơn gửi csgt
    public void addDonCSGT(int donid,int vtgtid){
        String sql = "insert into tbl_doncsgt(D_ID,VTGT_ID) values(?,?)";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            pstm.setInt(2, vtgtid);
            int update = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // Lưu văn thư gửi csgt
    public int addVtCsgt(String ngaygoi,int dcid,String ghichu,String mabuudien){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            java.sql.Date ngaynhanSql = null;
            try {
                Date parsed = format.parse(ngaygoi);
                ngaynhanSql = new java.sql.Date(parsed.getTime());
               // System.out.println("Ngay SQL = "+ngaynhanSql);
            } catch (ParseException ex) {
               // Logger.getLogger(NhapdonServlet.class.getName()).log(Level.SEVERE, null, ex);
               ngaynhanSql  = null;
            }
        String sql = "insert into tbl_vtcsgt(VT_ngaygoi,CSDC_ID,VTGT_GhiChu,VTGT_MBD) values(?,?,?,?)";
        connect = new DBConnect().dbConnect();
        int key  =0;
        try {
            pstm = connect.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setDate(1, ngaynhanSql);
            pstm.setInt(2, dcid);
            pstm.setString(3, ghichu);
            pstm.setString(4, mabuudien);
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
                while(rs.next()){
                    key = rs.getInt(1);
                }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // Load đơn đã gửi csgt
    public ArrayList<ArrayList> loadDonGuiCSGT(int page,String ngaybatdau,String ngayketthuc,String maonline,String manhan,
            String bnbd,String bbd,String mabuudiens,String noinhan,int cityid){
        ArrayList<ArrayList> data = new ArrayList<>();
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_vtcsgt.VT_ngaygoi >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and tbl_vtcsgt.VT_ngaygoi <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        
//        if(!bnbd.equals("")){
//             whereSql += " and (select group_concat(`KHD_Name` separator ' & ') \n" +
//                                    " as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_doncsgt.D_ID order by tbl_bnbd.KHD_ID) like '%"+bnbd.trim()+"%'";
//        }
//        
//        if(!bbd.equals("")){
//             whereSql += " and (select group_concat(`BDB_Name` separator ' & ') \n" +
//                " as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_doncsgt.D_ID group by D_ID) like '%"+bbd.trim()+"%'";
//        }
        ArrayList<Integer> donidList =  new ArrayList<>();
        if(!bnbd.trim().equals("")){
            donidList.addAll(new NhapDon().searchDonIDByBNBD(bnbd,page)) ;
        }
        if(!bbd.trim().equals("")){
            donidList.addAll(new NhapDon().searchDonIDByBBD(bbd,page));
        }
        if(!donidList.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donidList.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        if(cityid != 0){
            whereSql += " and tbl_csgtname.C_ID = "+cityid; 
        }
        if(!manhan.equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhan+"%'";
            }
        }
        if(!mabuudiens.equals("")){
            String[] mabd = manhan.split(",");
            if(mabd.length >1){
                whereSql += " and tbl_vtcsgt.VTGT_MBD in("+mabuudiens+")";
            }else{
                whereSql += " and tbl_vtcsgt.VTGT_MBD like '%"+mabuudiens+"%'";
            }
        }
        
        if(!maonline.equals("")){
            String[] mon = manhan.split(",");
            if(mon.length >1){
                whereSql += " and tbl_don.D_MDO in("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+maonline+"%'";
            }
        }
        
        String sql= "select distinct tbl_vtcsgt.VTGT_ID as vtgtid, tbl_vtcsgt.VTGT_MBD as mabuudien,\n" +
                            "ifnull(DATE_FORMAT(tbl_vtcsgt.VT_ngaygoi,'%d-%m-%Y' ),'') as ngaygoi,\n" +
                            "tbl_vtcsgt.VTGT_GhiChu as ghichu,tbl_csgtname.CSN_Name as csgtname,\n" +
                            "group_concat(tbl_doncsgt.D_ID separator ',') as donids,ifnull(VTGT_NguoiNhan,'') as nguoinhan \n" +
                            "from tbl_vtcsgt\n" +
                            "inner join (tbl_doncsgt inner join tbl_don on tbl_don.D_ID = tbl_doncsgt.D_ID)"
                        + "on tbl_doncsgt.VTGT_ID = tbl_vtcsgt.VTGT_ID\n" +
                            "inner join (tbl_csgtdiachi inner join tbl_csgtname on tbl_csgtname.CSN_ID = tbl_csgtdiachi.CSN_ID )\n" +
                            "on tbl_csgtdiachi.CSDC_ID = tbl_vtcsgt.CSDC_ID \n" +
                            " where 1=1 "+whereSql+
                            "group by tbl_vtcsgt.VTGT_MBD limit "+((page-1)*10)+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs= pstm.executeQuery();
            while(rs.next()){
                ArrayList dt = new ArrayList<>();
                int vtgtid = rs.getInt("vtgtid");
                String donids = rs.getString("donids");
                String mabuudien = rs.getString("mabuudien");
                String ngaygoi = rs.getString("ngaygoi");
                String ghichu = rs.getString("ghichu");
                String csgtname = rs.getString("csgtname");
                String bennhan = rs.getString("nguoinhan");
                dt.add(donids); dt.add(mabuudien);dt.add(ngaygoi);
                dt.add(ghichu); dt.add(csgtname);dt.add(bennhan);dt.add(vtgtid);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Lấy đơn gửi csgt
    public ArrayList<ArrayList> getMaDonCSGT(String donids){
        ArrayList<ArrayList> data = new ArrayList<>();
        String sql = "select distinct tbl_don.D_ID as donid,ifnull(DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y' ),'') as ngaynhap,\n" +
                            "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan,\n" +
                            "tbl_don.D_MDO as dononline\n" +
                            "from tbl_don\n" +
                            "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID\n" +
                            "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID where tbl_don.D_ID in ("+donids+");";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList dt = new ArrayList();
                int donid = rs.getInt("donid");
                String thoigian = rs.getString("ngaynhap");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, thoigian);
                String dononline = rs.getString("dononline");
                dt.add(donid);
                dt.add(maloainhan);
                dt.add(dononline);
                data.add(dt);
                System.out.println(data.size());
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return data;
    }
    
    // Lấy đơn gửi csgt
    public ArrayList<ArrayList> getMaDonCSGTByVTID(String vtid){
        ArrayList<ArrayList> data = new ArrayList<>();
        String sql = "select distinct tbl_don.D_ID as donid,ifnull(DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y' ),'') as ngaynhap,\n" +
                            "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan,\n" +
                            "tbl_don.D_MDO as dononline\n" +
                            "from tbl_don\n" +
                            "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID\n" +
                            " inner join tbl_doncsgt on tbl_doncsgt.D_ID =  tbl_don.D_ID "+
                            "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID where tbl_doncsgt.VTGT_ID in ("+vtid+");";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList dt = new ArrayList();
                int donid = rs.getInt("donid");
                String thoigian = rs.getString("ngaynhap");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, thoigian);
                String dononline = rs.getString("dononline");
                dt.add(donid);
                dt.add(maloainhan);
                dt.add(dononline);
                data.add(dt);
                System.out.println(data.size());
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return data;
    }
    // return table văn thư csgt
    public String  returnVTCSGT(int page,String ngaybatdau,String ngayketthuc,String maonline,String manhan,
            String bnbd,String bbd,String mabuudiens,String noinhan,int cityid){
        String table = "";
        ArrayList<ArrayList> dVtList = loadDonGuiCSGT(page,ngaybatdau, ngayketthuc, maonline, manhan, bnbd, bbd, mabuudiens, noinhan, cityid);
        for(ArrayList dvt : dVtList){
            table += "<tr>";
            String mabuudien = dvt.get(1).toString();
            String ngaygoi = dvt.get(2).toString();
            String ghichu = dvt.get(3).toString();
            String csgtname = dvt.get(4).toString();
            String nguoinhan = dvt.get(5).toString();
            
           // ArrayList<ArrayList> madonList = getMaDonCSGT(dvt.get(0).toString());
            ArrayList<ArrayList> madonList = getMaDonCSGTByVTID(dvt.get(6).toString());
            String dt  = dvt.get(6).toString();
            int rowspan = madonList.size();
            table +="<td class='dt"+dt+"' rowspan='"+rowspan+"'>"+ngaygoi+"</td>";
            table +="<td class='dt"+dt+"' rowspan='"+rowspan+"'>"+mabuudien+"</td>";
            table +="<td class='dt"+dt+"' rowspan='"+rowspan+"'>"+nguoinhan+"</td>";
            table +="<td class='dt"+dt+"' rowspan='"+rowspan+"'>"+csgtname+"</td>";
            for(int i =0;i <madonList.size();i++){
                if(i==0){
                    String donid = madonList.get(i).get(0).toString();
                    String maloainhan = madonList.get(i).get(1).toString();
                    String dononline = madonList.get(i).get(2).toString();
                    table += "<td class='dt"+dt+" row-sub' rowspan='1'>"+maloainhan+"</td>";
                    table += "<td class='dt"+dt+" row-sub' rowspan='1'>"+dononline+"</td>";
                    table +="<td class='dt"+dt+"' rowspan='"+rowspan+"'>"+ghichu+"</td>";
                    table += "<td class='dt"+dt+"' rowspan='1'><img src=\"./images/document_delete.png\" title='Xóa đơn văn thư' data-toggle=\"modal\" onclick=\"delDon('"+donid+"','"+madonList.size()+"','"+dt+"')\">"
                            + "<input type='text' id='"+donid+"' class ='donid_Class' value='"+donid+"' hidden><input type='checkbox' value='"+dt+"'></td>";
                    table += "<td class='dt"+dt+"' rowspan='"+rowspan+"'>"
                            + "<a href='javascript:void(0)'><img src=\"./images/document_edit.png\"  onclick=\"addMore("+dt+")\" title='Sửa Văn thư'></a>"
                            + "<p><img src=\"./images/document_delete.png\" data-toggle=\"modal\" onclick=\"delVTCSGT('"+dt+"')\"></p>"
                            + "<p hidden><img src=\"./images/delete_multi.png\" data-toggle=\"modal\" onclick=\"delMulti('"+dt+"')\" ></p></td>";
                    table += "</tr>";
                }else{
                    String donid = madonList.get(i).get(0).toString();
                    String maloainhan = madonList.get(i).get(1).toString();
                    String dononline = madonList.get(i).get(2).toString();
                    table += "<tr>";
                    table += "<td class='dt"+dt+" row-sub' rowspan='1'>"+maloainhan+"</td>";
                    table += "<td class='dt"+dt+" row-sub' rowspan='1'>"+dononline+"</td>";
                    table += "<td class='dt"+dt+"' rowspan='1'><img src=\"./images/document_delete.png\" data-toggle=\"modal\" onclick=\"delDon('"+donid+"','"+madonList.size()+"')\">"
                            + "<input type='text' id='"+donid+"'class ='donid_Class'  value='"+donid+"' hidden><input type='checkbox' value='"+dt+"'></td>";
                    table += "</tr>";
                }
            }
        }
        return table;
    }
    
    // Lấy tổng số trang search
    public int totalPageCSGTLuu(int page,String ngaybatdau,String ngayketthuc,String maonline,String manhan,
            String bnbd,String bbd,String mabuudiens,String noinhan,int cityid){
        int total = 0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_vtcsgt.VT_ngaygoi >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and tbl_vtcsgt.VT_ngaygoi <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        
//        if(!bnbd.equals("")){
//             whereSql += " and (select group_concat(`KHD_Name` separator ' & ') \n" +
//                                    " as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_doncsgt.D_ID order by tbl_bnbd.KHD_ID) like '%"+bnbd.trim()+"%'";
//        }
//        
//        if(!bbd.equals("")){
//             whereSql += " and (select group_concat(`BDB_Name` separator ' & ') \n" +
//                " as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_doncsgt.D_ID group by D_ID) like '%"+bbd.trim()+"%'";
//        }
        ArrayList<Integer> donidList =  new ArrayList<>();
        if(!bnbd.trim().equals("")){
            donidList.addAll(new NhapDon().searchDonIDByBNBD(bnbd,page)) ;
        }
        if(!bbd.trim().equals("")){
            donidList.addAll(new NhapDon().searchDonIDByBBD(bbd,page));
        }
        if(!donidList.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donidList.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        if(cityid != 0){
            whereSql += " and tbl_csgtname.C_ID = "+cityid; 
        }
        if(!manhan.equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhan)+"%'";
            }
        }
        if(!mabuudiens.equals("")){
            String[] mabd = manhan.split(",");
            if(mabd.length >1){
                whereSql += " and tbl_don.D_manhan in("+mabuudiens+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+mabuudiens+"%'";
            }
        }
        
        if(!maonline.equals("")){
            String[] mon = maonline.split(",");
            if(mon.length >1){
                whereSql += " and tbl_don.D_MDO in("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+Integer.parseInt(maonline)+"%'";
            }
        }
        
        String sql= "select count( distinct tbl_vtcsgt.VTGT_ID) "+
                            "from tbl_vtcsgt\n" +
                            "inner join (tbl_doncsgt inner join tbl_don on tbl_don.D_ID = tbl_doncsgt.D_ID)"
                        + "on tbl_doncsgt.VTGT_ID = tbl_vtcsgt.VTGT_ID\n" +
                            "inner join (tbl_csgtdiachi inner join tbl_csgtname on tbl_csgtname.CSN_ID = tbl_csgtdiachi.CSN_ID )\n" +
                            "on tbl_csgtdiachi.CSDC_ID = tbl_vtcsgt.CSDC_ID \n" +
                            " where tbl_don.DK_ID = 3 "+whereSql+
                            "group by tbl_vtcsgt.VTGT_MBD ;";
        
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (total/10)+1;
    }
    
    
    // Update văn thư csgt
    public int updateVTCsgt(int vtcsgtid, String ngaygui,int csgtdcid,String mabuudien,String ghichu){
        int result = 0;
        String sql = "update tbl_vtcsgt set VT_ngaygoi = str_to_date(?,'%d-%m-%Y'),CSDC_ID = ?,VTGT_GhiChu = ?,VTGT_MBD = ? where VTGT_ID = ? ";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, ngaygui);
            pstm.setInt(2, csgtdcid);
            pstm.setString(3, ghichu);
            pstm.setString(4, mabuudien);
            pstm.setInt(5, vtcsgtid);
            result = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    // Xóa đơn lưu csgt by donid
    public int delDonLuuCSgt(int donid){
        int key = 0;
        String sql ="delete from tbl_doncsgt where D_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            key = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
        
    }
     // Xóa đơn lưu csgt by donid
    public int delDonLuuCSgtByVTId(int vtid){
        int key = 0;
        String sql ="delete from tbl_doncsgt where VTGT_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, vtid);
            key = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
        
    }
    // Xóa văn thư và đơn csgt
    public int delVTCsgt(int vtcsgtid){
        int key = 0;
        String sql ="delete from tbl_vtcsgt where VTGT_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, vtcsgtid);
            key = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    
    // lấy đơn văn thư csgt by id
    public ArrayList<String> getVTCSGTByID(int vtid){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select DATE_FORMAT(VT_ngaygoi,'%d-%m-%Y') as ngaygoi,\n" +
                        "(select tbl_csgtname.CSN_Name from tbl_csgtname where tbl_csgtname.CSN_ID = tbl_csgtdiachi.CSN_ID) as csgtname,\n" +
                        "tbl_csgtdiachi.CSDC_Diachi as csgtdc,tbl_vtcsgt.CSDC_ID as csgtdcid \n" +
                        ",VTGT_NguoiNhan as nguoinhan,VTGT_GhiChu as ghichu,VTGT_MBD as mabuudien from tbl_vtcsgt\n" +
                        "left join tbl_csgtdiachi on tbl_csgtdiachi.CSDC_ID = tbl_vtcsgt.CSDC_ID where tbl_vtcsgt.VTGT_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, vtid);
            rs = pstm.executeQuery();
            while(rs.next()){
                String ngaygoi = rs.getString("ngaygoi");
                String csgtname = rs.getString("csgtname");
                String csgtdc = rs.getString("csgtdc");
                String nguoinhan = rs.getString("nguoinhan");
                String ghichu = rs.getString("ghichu");
                String mabuudien = rs.getString("mabuudien");
                String csgtdcid = rs.getString("csgtdcid");
                data.add(ngaygoi);data.add(csgtname);data.add(csgtdc);data.add(nguoinhan);
                data.add(ghichu); data.add(mabuudien);data.add(csgtdcid);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
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
