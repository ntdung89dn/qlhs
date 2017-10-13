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
public class VanThu {    
    private Connection connect;
    private PreparedStatement pstm;
    private ResultSet rs;
    public VanThu(){
        
    }
    
    // lấy tổng số trang văn thư chua lưu
    public int getPageVT(){
        int page = 0;
        String sql = "select count(*)" +
                            "from tbl_don \n" +
                            "where tbl_don.D_ID not in (select tbl_vtdon.D_ID from tbl_vtdon);";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
             rs = pstm.executeQuery();
             while(rs.next()){
                 page = rs.getInt(1);
             }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if((page%10)==0) page = page /10;
        else page =(page/10)+1;
        return page;
    }
    
    // view văn thư đã lưu theo trang
    public ArrayList<VanThuBean> viewVanThu(int page){
        ArrayList<VanThuBean> vtbArr = new ArrayList<>();
        String sql = " select DISTINCT tbl_don.D_ID as donid,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                        "tbl_don.D_manhan as manhan ,(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                        "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                        "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                        "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon," +
                        "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                        "ifnull(tbl_don.P_Pin,'') as mapin \n" +
                        "from tbl_don \n" +
                        "left join tbl_vtdon on tbl_vtdon.D_ID =  tbl_don.D_ID"+
                        " where tbl_vtdon.D_ID is null and tbl_don.D_isRemove = 0 "+
                        "group by tbl_don.D_ID order by tbl_don.D_ID desc limit "+((page -1)*10)+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("donid");
                String daytime = rs.getString("thoigian");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = "";
                if( !manhan.equals("0")){
                    maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan,daytime);
                }
                
               // String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan,daytime);
                String dononline = rs.getString("dononline");
                String mapin = rs.getString("mapin");
                if(dononline == null) dononline ="";
                if(mapin == null) mapin ="";
                VanThuBean vtb = new VanThuBean();
                vtb.setDonid(id);
                vtb.setDaytime(daytime);
                vtb.setManhan(maloainhan);
                vtb.setDononline(dononline);
                vtb.setMapin(mapin);
                vtbArr.add(vtb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vtbArr;
    }
    
    public String tableVanThu(int page){
        String table = "";
        ArrayList<VanThuBean> vtbArr = viewVanThu(page);
        for(VanThuBean vtb : vtbArr){
            String bnbd = new NhapDon().loadBnbdDon(vtb.getDonid());
            String benbaodam = new NhapDon().loadBBDDon(vtb.getDonid());
            table +="<tr>";
            table += "<td>"+vtb.getDaytime()+"</td>";
            table += "<td>"+vtb.getDononline()+"</td>";
            table += "<td>"+vtb.getMapin()+"</td>";
            table += "<td>"+vtb.getManhan()+"</td>";
            table += "<td>"+bnbd+"</td>";
            table += "<td>"+benbaodam+"</td>";
             table += "<td><input type='checkbox' id='cb_vanthu"+vtb.getDonid()+"' value='"+vtb.getDonid()+"'/></td>";
            table += "</tr>";
        }
        return table;
    }
    
    // lấy tổng số trang search văn thư
    public int getSearchPageVT(int loainhan,String manhan,int loaidon,int loaidkS,String ngaynhapdau,String ngaynhapcuoi,String dononline,String bnbd,String bbd,String barcode){
        int page = 0;
        String whereSql = "";
        String joinSql = "";
        if(!manhan.equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhan)+"%'";
            }
        }
       
        
        if(!ngaynhapdau.equals("")){
             whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaynhapdau+"','%d-%m-%Y')";
        }
        
        if(!ngaynhapcuoi.equals("")){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+ngaynhapcuoi+"','%d-%m-%Y')";;
        }
        if(!dononline.equals("")){
             int lengthSearch = dononline.split(",").length;
            if(lengthSearch >1){
                whereSql += " and tbl_don.D_MDO in ("+dononline.trim()+")";
            }else{
                whereSql += " and tbl_don.D_MDO LIKE '%"+dononline.trim()+"%'";
            }
        }
        
        if(!bnbd.equals("")){
             whereSql += " and  tbl_bnbd.KHD_Name   like '%"+bnbd.trim()+"%'";
        }
        
        if(!bbd.equals("")){
             whereSql += " and   tbl_benbaodam.BDB_Name  like '%"+bbd.trim()+"%'";
        } 
        if(loainhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loainhan; 
        }
        if(loaidon != 0){
           // System.out.println("loaidonS : "+loaidon);
            whereSql += " and tbl_don.LD_ID = "+loaidon;
        }
        if(loaidkS != 0){
            whereSql += " and  tbl_don.DK_ID = "+loaidkS;
        }
        if(!barcode.equals("")){
            int lengthSearch = barcode.split(",").length;
            if(lengthSearch >1){
                whereSql += " and tbl_don.D_ID in ("+barcode.trim()+") ";
            }else{
                whereSql += " and tbl_don.D_ID = "+barcode.trim()+" ";
            }
        }
       // System.out.println(whereSql);
        String sql = "select count(DISTINCT tbl_don.D_ID)"+
                            "from tbl_don \n" +
                            "left join tbl_vtdon on tbl_vtdon.D_ID = tbl_don.D_ID\n "+
                            " where tbl_vtdon.D_ID is null and  tbl_don.D_isRemove = 0 \n" +whereSql+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
             rs = pstm.executeQuery();
             while(rs.next()){
                 page = rs.getInt(1);
             }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         page =(page/10)+1;
        return page;
    }
    
    // Search văn thư
    public ArrayList<VanThuBean> searchDonVT(int page,int loainhan,String manhan,int loaidon,int loaidkS,String ngaynhapdau,String ngaynhapcuoi,String dononline,String bnbd,String bbd,String barcode){
        ArrayList<VanThuBean> vtbArr = new ArrayList<>();
        String whereSql = "";
        if(!manhan.equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhan)+"%'";
            }
        }
       
        
        if(!ngaynhapdau.equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaynhapdau+"','%d-%m-%Y')";
        }
        
        if(!ngaynhapcuoi.equals("")){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+ngaynhapcuoi+"','%d-%m-%Y')";;
        }
        if(!dononline.equals("")){
             int lengthSearch = dononline.split(",").length;
            if(lengthSearch >1){
                whereSql += " and tbl_don.D_MDO in ("+dononline.trim()+")";
            }else{
                whereSql += " and tbl_don.D_MDO LIKE '%"+dononline.trim()+"%'";
            }
        }
        
        if(!bnbd.equals("")){
             whereSql += " and  tbl_bnbd.KHD_Name   like '%"+bnbd.trim()+"%'";
        }
        
        if(!bbd.equals("")){
             whereSql += " and  tbl_benbaodam.BDB_Name   like '%"+bbd.trim()+"%'";
        }
        if(loainhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loainhan; 
        }
        if(loaidon != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidon;
        }
        if(loaidkS != 0){
            whereSql += " and  tbl_don.DK_ID = "+loaidkS;
        }
        if(!barcode.equals("")){
            int lengthSearch = barcode.split(",").length;
            if(lengthSearch >1){
                whereSql += " and tbl_don.D_ID in ("+barcode.trim()+") ";
            }else{
                whereSql += " and tbl_don.D_ID = "+barcode.trim()+" ";
            }
        }
        //System.out.println(whereSql);
        String sql = "select DISTINCT tbl_don.D_ID as donid,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                            "tbl_don.D_manhan as manhan , \n" +
                            "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin, \n" +
                            "group_concat(DISTINCT tbl_bnbd.KHD_Name separator ' & ') as bnbd, \n" +
                            " group_concat(DISTINCT tbl_benbaodam.BDB_Name separator ' & ')  as benbaodam \n" +
                            "from tbl_don \n" +
                            "left join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID "+
                            "left join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                            "left join tbl_vtdon on tbl_vtdon.D_ID = tbl_don.D_ID\n "+
                            " where tbl_vtdon.D_ID is null \n" +whereSql+
                            " group by tbl_don.D_ID order by tbl_don.D_ID desc limit "+((page -1)*10)+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("donid");
                String daytime = rs.getString("thoigian");
                String loaidk = rs.getString("loaidk");
                String manhanrs = rs.getString("manhan");
                String loainhanrs = rs.getString("loainhan");
                String manhanRS = new NhapDon().returnManhan(loaidk, manhanrs, loainhanrs,daytime);
                String dononlineRS = rs.getString("dononline");
                String mapinRS = rs.getString("mapin");
                String bnbdRS = rs.getString("bnbd");
                String bbdRS = rs.getString("benbaodam");
                if(dononlineRS == null) dononlineRS ="";
                if(mapinRS == null) mapinRS ="";
                VanThuBean vtb = new VanThuBean();
                vtb.setDonid(id);
                vtb.setDaytime(daytime);
                vtb.setManhan(manhanRS);
                vtb.setDononline(dononlineRS);
                vtb.setMapin(mapinRS);
                vtb.setBnbd(bnbdRS);
                vtb.setBbd(bbdRS);
                vtbArr.add(vtb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vtbArr;
    }
    
    public String tableSVanThu(int page,int loainhan,String manhan,int loaidon,int loaidk,String ngaynhapdau,String ngaynhapcuoi,String dononline,String bnbd,String bbd,String barcode){
        String table = "";
        ArrayList<VanThuBean> vtbArr = searchDonVT(page,loainhan,manhan,loaidon, loaidk,ngaynhapdau,ngaynhapcuoi, dononline, bnbd, bbd,barcode);
        for(VanThuBean vtb : vtbArr){
            table +="<tr>";
            table += "<td>"+vtb.getDaytime()+"</td>";
            table += "<td>"+vtb.getDononline()+"</td>";
            table += "<td>"+vtb.getMapin()+"</td>";
            table += "<td>"+vtb.getManhan()+"</td>";
            table += "<td>"+vtb.getBnbd()+"</td>";
            table += "<td>"+vtb.getBbd()+"</td>";
             table += "<td><input type='checkbox' id='cb_vanthu"+vtb.getDonid()+"' value='"+vtb.getDonid()+"'/></td>";
            table += "</tr>";
        }
        return table;
    }
    // Kiem tra va insert ngay goi
//    public int checkNgayGoi(String ngaygoi){
//        int key = 0;
//        String sql = "select VTNG_ID from tbl_vtngaygoi where VT_ngaygoi = ?";
//        connect = new DBConnect().dbConnect();
//        try {
//            pstm = connect.prepareStatement(sql);
//            pstm.setString(1, ngaygoi);
//            rs = pstm.executeQuery();
//            while(rs.next()){
//                key = rs.getInt(1);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
//        }finally{
//            try {
//                new DBConnect().closeAll(connect, pstm, rs);
//            } catch (SQLException ex) {
//                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return key;
//    }
//    public int insertNgayGoi(String ngaygoi){
//       // int key =checkNgayGoi(ngaygoi);
//            String sql = "insert into tbl_vtngaygoi(VT_ngaygoi) values(?)";
//            connect = new DBConnect().dbConnect();
//            try {
//                pstm = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
//                pstm.setString(1, ngaygoi);
//                pstm.executeUpdate();
//                rs = pstm.getGeneratedKeys();
//                while(rs.next()){
//                    key = rs.getInt(1);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
//            }finally{
//            try {
//                new DBConnect().closeAll(connect, pstm, rs);
//            } catch (SQLException ex) {
//                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        }
//        
//        
//        return key;
//    }
    
   //insert van thu
    public int insertVanThu(String ngaygoi,String noinhan,String mabuudien,String sobienlai,String ghichu) throws SQLException{
        int key = 0;
        String sql = "insert into tbl_vanthu(VT_ngaygoi,VT_noinhan,VT_MBD,VT_SBL,VT_GC) values (?,?,?,?,?)";
        connect = new DBConnect().dbConnect();
        pstm = connect.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        if(ngaygoi.equals("")){
            pstm.setNull(1, java.sql.Types.DATE);
        }else{
            pstm.setString(1, ngaygoi);
        }
        pstm.setString(2, noinhan);
        pstm.setString(3, mabuudien);
        pstm.setString(4, sobienlai);
        pstm.setString(5, ghichu);
        pstm.executeUpdate();
        rs = pstm.getGeneratedKeys();
        while(rs.next()){
            key = rs.getInt(1);
        }
        new DBConnect().closeAll(connect, pstm, rs);
        return key;
    }
    public void insertVTDon(int vtid,int donid) throws SQLException{
        String sql = "insert into tbl_vtDon(VT_ID,D_ID) values(?,?); ";
        connect = new DBConnect().dbConnect();
        pstm = connect.prepareStatement(sql);
        pstm.setInt(1, vtid);
        pstm.setInt(2, donid);
        pstm.executeUpdate();
        new DBConnect().closeAll(connect, pstm, rs);
    }
    
    
    // update van thu
    
    public void updateVanThu(String vtngoi,String noinhan,String mabuudien,String sobienlai,String ghichu,int vtid) throws SQLException{
        String sql = "update tbl_vanthu set VT_ngaygoi = str_to_date(?,'%d-%m-%Y'),VT_noinhan = ?,VT_MBD = ?,VT_SBL = ?,VT_GC = ? where VT_ID = ?";
        connect = new DBConnect().dbConnect();
        pstm = connect.prepareStatement(sql);
        pstm.setString(1, vtngoi);
        pstm.setString(2, noinhan);
        pstm.setString(3, mabuudien);
        pstm.setString(4, sobienlai);
        pstm.setString(5, ghichu);
        pstm.setInt(6, vtid);
        pstm.executeUpdate();
        new DBConnect().closeAll(connect, pstm, rs);
    }
    
    public int countDonLuuVT(int vtid){
        int count=0;
        String sql = "select count(*) from tbl_vtdon where VT_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, vtid);
            rs = pstm.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }
    // lấy số trang văn thư đã lưu
    public int getPageVTLuu(){
        int page = 0;
        String sql = "select count( DISTINCT tbl_vanthu.VT_ID) as vtid \n" +
                        "from tbl_vanthu \n" +
                        "inner join tbl_vtdon on tbl_vanthu.VT_ID = tbl_vtdon.VT_ID;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
             rs = pstm.executeQuery();
             while(rs.next()){
                 page = rs.getInt(1);
             }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         page =(page/10)+1;
        return page;
    }
    
    // view văn thư đã lưu
    public String viewVanThuLuu(int page){
        String sql = "select DISTINCT tbl_vanthu.VT_ID as vtid,\n" +
                            "ifnull(tbl_vanthu.VT_ngaygoi ,'') as ngaygoi,\n" +
                            "tbl_vanthu.VT_MBD as mabuudien, \n" +
                            "tbl_vanthu.VT_noinhan as noinhan,tbl_vanthu.VT_SBL as sobienlai, tbl_vanthu.VT_GC as ghichu,\n" +
                            "(select group_concat(DISTINCT tbl_bnbd.KHD_Name separator ' & ') as bnbd from tbl_don inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID \n" +
                            "where tbl_don.D_ID in ((group_concat(tbl_vtdon.D_ID SEPARATOR ',')))\n" +
                            "group by tbl_don.KH_ID) as bnbd, \n" +
                            "(group_concat(tbl_vtdon.D_ID SEPARATOR ',')) as donid \n" +
                            "from tbl_vanthu \n" +
                            "inner join tbl_vtdon on tbl_vanthu.VT_ID = tbl_vtdon.VT_ID \n" +
                            "group by tbl_vanthu.VT_ID order by tbl_vanthu.VT_ID desc limit "+(page-1)*10+",10;";
        System.out.println(sql);
         String tbody = "";
         connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                String id =  rs.getString("vtid");
                String ngaygoi = rs.getString("ngaygoi");
                String mabuudien = rs.getString("mabuudien");
                String noinhan = rs.getString("noinhan");
                String sobienlai = rs.getString("sobienlai");
                String ghichu = rs.getString("ghichu");
                String bnbd = rs.getString("bnbd");
                tbody += "<tr >";
                tbody += "<td> <a href=\"#\" data-toggle='collapse' data-target='#vt_tths"+id+"' class='accordion-toggle' >\n" +
                        "<span class=\"glyphicon glyphicon-plus\"  id='"+id+"'></span>" +
                        "</a> "+ngaygoi+"</td>";
                tbody += "<td>"+mabuudien+"</td>";
                tbody += "<td>"+noinhan+"</td>";
                String sobl = "";
                if(sobienlai.length() >17){
                    sobl = sobienlai.substring(0, 17)+"...";
                }else{
                    sobl = sobienlai;
                }
                tbody += "<td width='150px'><div><span class='sobienlai' value='"+sobienlai+"'>"+sobl+"</span></div></td>";
                 tbody += "<td>"+bnbd+"</td>";
                tbody += "<td>"+ghichu+"</td>";
                tbody += "<td><input type='checkbox' value='"+id+"'></td>";
                tbody += "</tr><tr ><td  colspan='6' style='padding: 0 !important;'><div id='vt_tths"+id+"' class=\"accordian-body collapse\"></div></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tbody;
    }
    
    public String selectDon(String vtid){
        String tbody = "<table ><thead style='background-color: #D6DBDF;'><tr><th>Ngày Nhận</th>"
                + "<th>Đơn Online</th><th>Loại hình nhận</th>"
                + "<th>Bên bảo đảm</th><th>Xóa</th></tr></thead>";

        String sql = "select DISTINCT tbl_don.D_ID as donid ,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian, \n" +
                            " tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan, \n" +
                            "tbl_don.D_MDO  as dononline,  \n" +
                         //   "group_concat(DISTINCT tbl_bnbd.KHD_Name separator ' & ')   as bnbd,  \n" +
                            "group_concat(tbl_benbaodam.BDB_Name separator ' & ')   as benbaodam  \n" +
                            "from tbl_don \n" +
                            "left join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                            "left join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                            "left join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                            "left join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                            "left join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID \n" +
                            "left join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                            "inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID " +
                            "left join tbl_vtdon on tbl_don.D_ID = tbl_vtdon.D_ID\n" +
                            "where tbl_vtdon.VT_ID in ("+vtid+") "+
                            "group by tbl_don.D_ID order by tbl_don.D_ID desc;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
             
            while(rs.next()){
                int id =  rs.getInt("donid");
                String ngaygio = rs.getString("thoigian");
                String loaidk = rs.getString("loaidk");
                String manhanrs = rs.getString("manhan");
                String loainhanrs = rs.getString("loainhan");
                String maloainhanrs = new NhapDon().returnManhan(loaidk, manhanrs, loainhanrs,ngaygio);
                String dononline = rs.getString("dononline");
                if(dononline == null){
                    dononline = "";
                }
                //String bnbd = rs.getString("bnbd");
                String benbaodam = rs.getString("benbaodam");
                tbody +="<tr>";
                tbody += "<td>"+ngaygio+"</td>";
                tbody += "<td>"+dononline+"</td>";
                tbody += "<td>"+maloainhanrs+"</td>";
                tbody += "<td>"+benbaodam+"</td>";
                tbody += "<td><img src='./images/clear_btn.png' class='img_remove' onclick='xoaDon("+id+"-"+vtid+")'></td></tr>";
            }
            tbody +="</table>";
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tbody;
    }
    
    // search van thu luu
    public ArrayList<ArrayList<String>> searchVTLuu(int page,String ngaygoi,String mabuudien,String noinhan, String sobienlai){
        ArrayList<ArrayList<String>> vtLuuArr = new ArrayList<>();
        String whereSQL = "";
        
        if(!ngaygoi.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_ngaygoi>= str_to_date('"+ngaygoi+"','%d-%m-%Y') ";
        }
        
        if(!mabuudien.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_MBD like '%"+mabuudien+"%'";
        }
        
        if(!noinhan.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_noinhan like '%"+noinhan+"%'";
        }
        
        if(!sobienlai.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_SBL like '%"+sobienlai+"%'";
        }
        String sql = "select DISTINCT tbl_vanthu.VT_ID as vtid,\n" +
                            "ifnull(date_format(tbl_vanthu.VT_ngaygoi,'%d-%m-%Y'),'') as ngaygoi,\n" +
                            " tbl_vanthu.VT_MBD as mabuudien, \n" +
                            "tbl_vanthu.VT_noinhan as noinhan,tbl_vanthu.VT_SBL as sobienlai, tbl_vanthu.VT_GC as ghichu,"
                            + "(select group_concat(DISTINCT tbl_bnbd.KHD_Name separator ' & ') as bnbd from tbl_don inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID \n" +
                            "where tbl_don.D_ID in ((group_concat(tbl_vtdon.D_ID SEPARATOR ',')))\n" +
                            "group by tbl_don.KH_ID) as bnbd, (group_concat(tbl_vtdon.D_ID SEPARATOR ',')) as donid \n" +
                            "from tbl_vanthu \n" +
                            "left join tbl_vtdon on tbl_vanthu.VT_ID = tbl_vtdon.VT_ID \n" +
                            "where 1=1 "+whereSQL+
                            " group by tbl_vanthu.VT_ID order by tbl_vanthu.VT_ID desc limit "+(page-1)*10+","+((page-1)*10+10)+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> content = new ArrayList<>();
                String id =  rs.getString("vtid");
                String ngaygoiRS = rs.getString("ngaygoi");
                String mabuudienRS = rs.getString("mabuudien");
                String noinhanRS = rs.getString("noinhan");
                String sobienlaiRS = rs.getString("sobienlai");
                String ghichu = rs.getString("ghichu");
                String donid = rs.getString("donid");
                String bnbd = rs.getString("bnbd");
                content.add(id);
                content.add(ngaygoiRS);
                content.add(mabuudienRS);
                content.add(noinhanRS);
                content.add(sobienlaiRS);
                content.add(bnbd);
                content.add(ghichu);
                content.add(donid);
                vtLuuArr.add(content);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vtLuuArr;
    }
    
    // Lấy page search bình thường
    public int getPageNormalSearch(String ngaygoi,String mabuudien,String noinhan, String sobienlai){
        int page = 0;
        String whereSQL = "";
        
        if(!ngaygoi.trim().equals("")){
            whereSQL += " and DATE_FORMAT(tbl_vtngaygoi.VT_ngaygoi, '%d-%m-%Y') like '%"+ngaygoi+"%'";
        }
        
        if(!mabuudien.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_MBD like '%"+mabuudien+"%'";
        }
        
        if(!noinhan.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_noinhan like '%"+noinhan+"%'";
        }
        
        if(!sobienlai.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_SBL like '%"+sobienlai+"%'";
        }
        String sql = "select count( DISTINCT tbl_vanthu.VT_ID) "+
                            "from tbl_vanthu\n" +
                            "left join tbl_vtdon on tbl_vanthu.VT_ID = tbl_vtdon.VT_ID\n" +
                            "where 1=1 "+whereSQL+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareCall(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                page = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
//        if((page%10)==0) page = page /10;
//        else page =(page/10)+1;
        return (page/10)+1;
    }
    
    // Search đơn bình thường
    public String normalSearch(int page,String ngaygoi,String mabuudien,String noinhan, String sobienlai){
        String tbody = "";
        ArrayList<ArrayList<String>> vtLuuArr = searchVTLuu(page,ngaygoi, mabuudien, noinhan, sobienlai);
        for(ArrayList<String> content : vtLuuArr){
            String id = content.get(0);
            String ngaygoiRS = content.get(1);
            String mabuudienRS = content.get(2);
            String noinhanRS = content.get(3);
            String sobienlaiRS = content.get(4);
            String bnbd = content.get(5);
            String ghichu = content.get(6);
            tbody += "<tr >";
            tbody += "<td> <a href=\"#\" data-toggle='collapse' data-target='#vt_tths"+id+"' class='accordion-toggle' >\n" +
                    "<span class=\"glyphicon glyphicon-plus\"  id='"+id+"'></span>\n" +
                    "</a> "+ngaygoiRS+"</td>";
            tbody += "<td>"+mabuudienRS+"</td>";
            tbody += "<td>"+noinhanRS+"</td>";
            String sobl = "";
            if(sobienlaiRS.length() >17){
                sobl = sobienlaiRS.substring(0, 17)+"...";
            }else{
                sobl = sobienlaiRS;
            }
            tbody += "<td width='150px'><div><span class='sobienlai' value='"+sobienlaiRS+"'>"+sobl+"</td>";
            tbody += "<td>"+bnbd+"</td>";
            tbody += "<td>"+ghichu+"</td>";
            tbody += "<td><input type='checkbox' value='"+id+"'></td>";
            tbody += "</tr><tr ><td  colspan='6' style='padding: 0 !important;'><div id='vt_tths"+id+"' class=\"accordian-body collapse\"></div></td></tr>";
        }
        return tbody;
    }
    
    // lấy đơn vt đã lưu
    public ArrayList<ArrayList<Object>> selectDonVT(int donid){
        ArrayList<ArrayList<Object>> donArr = new ArrayList<>();
        String whereSql = "";
        if(donid == -1){
            
        }
        String sql = "select DISTINCT tbl_don.D_ID as donid ,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian, \n" +
                            " tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan, \n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline,  \n" +
                            "group_concat(DISTINCT tbl_bnbd.KHD_Name separator ' & ')  as bnbd,  \n" +
                            "group_concat(DISTINCT tbl_benbaodam.BDB_Name separator ' & ')   as benbaodam  \n" +
                            "from tbl_don \n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID  \n" +
                            "left join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID  \n" +
                            "left join tbl_bnbd on tbl_don.D_ID =  tbl_bnbd.KHD_ID \n" +
                            "left join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                            "left join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                            "left join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                            "left join tbl_vtdon on tbl_don.D_ID = tbl_vtdon.D_ID\n" +
                            "where tbl_vtdon.VT_ID = ? "+whereSql+
                            " order by tbl_don.D_ID desc;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<Object> content = new ArrayList<>();
                int id =  rs.getInt("donid");
                String ngaygio = rs.getString("thoigian");
                String manhan = rs.getString("manhan");
                String dononline = rs.getString("dononline");
                if(dononline == null){
                    dononline = "";
                }
                String bnbd = rs.getString("bnbd");
                String bbd = rs.getString("benbaodam");
                content.add(id);
                content.add(ngaygio);
                content.add(manhan);
                content.add(dononline);
                content.add(bnbd);
                content.add(bbd);
                donArr.add(content);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return donArr;
    }
    
    // lấy số đơn id
    public ArrayList<Integer> searchDonID(String manhan, String dononline,String bnbd,String bbd){
        ArrayList<Integer> idArr =  new ArrayList<>();
        String whereSelect = "";
        if(!manhan.trim().equals("")){
            System.out.println(manhan);
             whereSelect += " and tbl_don.D_loainhan LIKE '"+manhan.trim()+"'";
        }
        if(!dononline.trim().equals("")){
            int lengthSearch = dononline.split(",").length;
            if(lengthSearch >1){
                whereSelect += " and tbl_don.D_MDO in ("+dononline.trim()+")";
            }else{
                whereSelect += " and tbl_don.D_MDO LIKE '%"+dononline.trim()+"%'";
            }
            
        }
        if(!bnbd.trim().equals("")){
            whereSelect += " and tbl_bnbd.KHD_Name LIKE '%"+bnbd.trim()+"%'";
        }

        if(!bbd.trim().equals("")){
            whereSelect += " and tbl_benbaodam.BDB_Name LIKE '%"+bbd.trim()+"%'";
        }
        String sql = "select DISTINCT tbl_don.D_ID as donid"+
                    " from tbl_don\n " +
                    "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID\n" +
                    "left join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID\n" +
                    "left join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID\n" +
                    "left join tbl_bnbd on tbl_don.D_ID =  tbl_bnbd.KHD_ID "+
                    "left join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" 
                    + "where 1=1 "+whereSelect+" limit 0,10;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                idArr.add(rs.getInt("donid"));
            }
            rs.close();
            pstm.close();
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return idArr;
    }
    
    public String searchVTMore(int vtid,String ngaynhap,String mabuudien,String noinhan,String sobienlai){
        String whereSQL = "";
        
        if(!ngaynhap.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_ngaygoi >= str_to_date('"+ngaynhap+"','%d-%m-%Y') ";
        }
        
        if(!mabuudien.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_MBD like '%"+mabuudien+"%'";
        }
        
        if(!noinhan.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_noinhan like '%"+noinhan+"%'";
        }
        
        if(!sobienlai.trim().equals("")){
            whereSQL += " and tbl_vanthu.VT_SBL like '%"+sobienlai+"%'";
        }
        String sql = "select DISTINCT tbl_vanthu.VT_ID as vtid,\n" +
                "ifnull(DATE_FORMAT(tbl_vanthu.VT_ngaygoi,'%d-%m-%Y'),'') as ngaygoi,\n" +
                " tbl_vanthu.VT_MBD as mabuudien, \n" +
                "tbl_vanthu.VT_noinhan as noinhan,tbl_vanthu.VT_SBL as sobienlai, tbl_vanthu.VT_GC as ghichu,\n" +
                "(select distinct tbl_khachhang.KH_Name from tbl_don\n" +
                " inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID \n" +
                "where tbl_don.D_ID in (select tbl_vtdon.D_ID from tbl_vtdon where tbl_vtdon.VT_ID = ? )) as bnbd,\n" +
                "  (group_concat(tbl_vtdon.D_ID SEPARATOR ',')) as donid \n" +
                "from tbl_vanthu\n" +
                "left join tbl_vtdon on tbl_vanthu.VT_ID = tbl_vtdon.VT_ID\n" +
                "where tbl_vanthu.VT_ID = ?\n" +whereSQL+
                " group by tbl_vanthu.VT_ID order by tbl_vanthu.VT_ID desc ;";
        //System.out.println(vtid);
        connect = new DBConnect().dbConnect();
        String tbody  = "";
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, vtid);
            pstm.setInt(2, vtid);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id =  rs.getInt("vtid");
                String ngaygoi = rs.getString("ngaygoi");
                String mabuudienRS = rs.getString("mabuudien");
                String noinhanRS = rs.getString("noinhan");
                String sobienlaiRS = rs.getString("sobienlai");
                String ghichu = rs.getString("ghichu");
                String bnbd = rs.getString("bnbd");
                tbody += "<tr >";
                tbody += "<td> <a href=\"#\" data-toggle='collapse' data-target='#vt_tths"+id+"' class='accordion-toggle' >\n" +
                        "<span class=\"glyphicon glyphicon-plus\"  id='"+id+"'></span>\n" +
                        "</a> "+ngaygoi+"</td>";
                tbody += "<td>"+mabuudienRS+"</td>";
                tbody += "<td>"+noinhanRS+"</td>";
                String sobl;
                if(sobienlaiRS.length() >17){
                    sobl = sobienlaiRS.substring(0, 17)+"...";
                }else{
                    sobl = sobienlaiRS;
                }
                tbody += "<td width='150px'><div><span class='sobienlai' value='"+sobienlaiRS+"'>"+sobl+"</td>";
                tbody += "<td>"+bnbd+"</td>";
                tbody += "<td>"+ghichu+"</td>";
                tbody += "<td><input type='checkbox' value='"+id+"'></td>";
                tbody += "</tr><tr ><td  colspan='6' style='padding: 0 !important;'><div id='vt_tths"+id+"' class=\"accordian-body collapse\"></div></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tbody;
    }
    
    // Lấy page search nâng cao
    public int getPageSearchMore(String manhan,String dononline,String bnbd,String bbd){
        int page = 0;
        String whereSelect = "";
        String innerSQl = "";
        if(!manhan.trim().equals("")){
             whereSelect += " and tbl_don.D_manhan LIKE '%"+manhan.trim()+"%'";
        }
        if(!dononline.trim().equals("")){
            if(dononline.trim().split(",").length >1){
                whereSelect += " and tbl_don.D_MDO in ( "+dononline.trim()+")";
            }else{
                whereSelect += " and tbl_don.D_MDO LIKE '%"+dononline.trim()+"%'";
            }
            
        }
        if(!bnbd.trim().equals("")){
            whereSelect += " and tbl_bnbd.KHD_Name LIKE '%"+bnbd.trim()+"%'";
        }

        if(!bbd.trim().equals("")){
            whereSelect += " and tbl_benbaodam.BDB_Name LIKE '%"+bbd.trim()+"%'";
        }
        String sql = "select count( DISTINCT tbl_vtdon.VT_ID) from tbl_don\n" +
                            "left join tbl_vtdon on tbl_vtdon.D_ID = tbl_don.D_ID\n" +
                            "left join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID\n" +
                            "left join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +innerSQl+
                            "where 1=1 "+whereSelect+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareCall(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                page = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if((page%10)==0) page = page /10;
        else page =(page/10)+1;
        return page;
    }
    // Search nang cao
    public ArrayList<Integer> searchVTNangCao(int page,String manhan,String dononline,String bnbd,String bbd){
        ArrayList<Integer> idArr =  new ArrayList<>();
        String whereSelect = "";
        if(!manhan.trim().equals("")){
             whereSelect += " and tbl_don.D_manhan LIKE '%"+manhan.trim()+"%'";
        }
        if(!dononline.trim().equals("")){
            int lengthSearch = dononline.split(",").length;
            if(lengthSearch >1){
                whereSelect += " and tbl_don.D_MDO in ("+dononline.trim()+")";
            }else{
                whereSelect += " and tbl_don.D_MDO LIKE '%"+dononline.trim()+"%'";
            }
        }
        if(!bnbd.trim().equals("")){
            whereSelect += " and tbl_bnbd.KHD_Name LIKE '%"+bnbd.trim()+"%'";
        }

        if(!bbd.trim().equals("")){
            whereSelect += " and tbl_benbaodam.BDB_Name LIKE '%"+bbd.trim()+"%'";
        }
        String sql = "select tbl_vtdon.VT_ID as vtid from tbl_don\n" +
                            "left join tbl_vtdon on tbl_vtdon.D_ID = tbl_don.D_ID\n" +
                            "left join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID\n" +
                            "left join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                            "where 1=1 "+whereSelect+
                            " group by tbl_vtdon.VT_ID order by tbl_vtdon.VT_ID desc limit "+(page-1)*10+",10;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                idArr.add(rs.getInt("vtid"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return idArr;
    }
    
    // view search more
    public String searchMore(int page, String ngaynhap,String mabuudien,String noinhan,String sobienlai,
                                String manhan,String dononline,String bnbd,String bbd){
        String tbody  ="";
        ArrayList<Integer> idArr = searchVTNangCao(page,manhan, dononline, bnbd, bbd);
        for(int id : idArr){
            System.out.println(id);
            tbody += searchVTMore(id, ngaynhap, mabuudien, noinhan, sobienlai);
        }
        return tbody;
    }
    
    // xóa đơn văn thư lưu
    public int xoaDonVTLuu(int donid,int vt_id){
        int sucess = 0;
        String sql = "delete from tbl_vtdon where tbl_vtdon.D_ID = ? and tbl_vtdon.VT_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            pstm.setInt(2, vt_id);
            sucess = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sucess;
    }
    
    // Lấy thông tin văn thư by id
    public ArrayList<String> getInforVanThuById(int vtid){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select date_format(VT_ngaygoi,'%d-%m-%Y') as ngaygoi,VT_noinhan as noinhan"
                + ",VT_MBD as mabuudien,VT_SBL as sobienlai,VT_GC as ghichu from tbl_vanthu where VT_ID = ? ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, vtid);
            rs= pstm.executeQuery();
            while(rs.next()){
                String ngaygoi = rs.getString("ngaygoi");
                String noinhan = rs.getString("noinhan");
                String mabuudien = rs.getString("mabuudien");
                String sobienlai = rs.getString("sobienlai");
                String ghichu = rs.getString("ghichu");
                data.add(ngaygoi);data.add(noinhan);data.add(mabuudien);
                data.add(sobienlai);data.add(ghichu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Lấy đơn id và số văn thư
    public ArrayList<ArrayList<String>> getDonLuuVTByVTid(int vtid){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                        "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                        "tbl_don.D_manhan as manhan,\n" +
                        "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                        "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon\n" +
                        "from tbl_don\n" +
                        "left join tbl_vtdon on tbl_vtdon.D_ID = tbl_don.D_ID\n" +
                        "where tbl_vtdon.VT_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, vtid);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String thoigian = rs.getString("thoigian");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String loaidk = rs.getString("loaidk");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan,thoigian);
                String donid = rs.getString("id");
                dt.add(maloainhan);dt.add(donid);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    // Load đơn văn thư lưu edit
    
    public ArrayList<VanThuBean> loadTableDonVTLuuById(String donids,int page){
        ArrayList<VanThuBean> vtbArr = new ArrayList<>();
        String sql = " select DISTINCT tbl_don.D_ID as donid,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                        "tbl_don.D_manhan as manhan ,(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                        "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                        "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                        "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon," +
                        "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                        "ifnull(tbl_don.P_Pin,'') as mapin \n" +
                        "from tbl_don \n" +
                        " where tbl_don.D_ID in ("+donids+") "+
                        "group by tbl_don.D_ID order by tbl_don.D_ID desc limit "+((page -1)*10)+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("donid");
                String daytime = rs.getString("thoigian");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan,daytime);
                String dononline = rs.getString("dononline");
                String mapin = rs.getString("mapin");
                if(dononline == null) dononline ="";
                if(mapin == null) mapin ="";
                VanThuBean vtb = new VanThuBean();
                vtb.setDonid(id);
                vtb.setDaytime(daytime);
                vtb.setManhan(maloainhan);
                vtb.setDononline(dononline);
                vtb.setMapin(mapin);
                vtbArr.add(vtb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vtbArr;
    }
}
