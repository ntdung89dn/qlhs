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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ntdung
 */
public class ThongKe {
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    
    public ThongKe(){}
    
    // thống kê nhập đơn
    public int getPageTKND(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String btpS,ArrayList<Integer> donids){
        int total = 0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        if(!maonlineS.trim().equals("")){
            String[] searchOn = maonlineS.split(",");
            if(searchOn.length >1){
                whereSql += " and  tbl_don.D_MDO in("+maonlineS+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+maonlineS+"%'";
            }
        }
        if(loaidonS != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidonS;
        }
        if(loaihinhnhanS != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhanS ;
        }
        if(!manhanS.trim().equals("")){
            String[] searchND = manhanS.split(",");
            if(searchND.length >1){
                whereSql += " and tbl_don.D_manhan in ("+manhanS+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhanS+"%'";
            }
        }
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        if(!btpS.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name like '%"+btpS+"%'";
        }
        String sql = "select count( tbl_don.D_ID) "+
                            "from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                             " where tbl_don.D_isRemove = 0 " +whereSql
                            + " order by tbl_don.D_ID DESC ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println((total/5)+1);
        return (total/10)+1;
    }
    // thống kê nhập đơn
    public ArrayList<ArrayList<String>> tkNhapdon(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String btpS,ArrayList<Integer> donids){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String table = "";
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        if(!maonlineS.trim().equals("")){
            String[] searchOn = maonlineS.split(",");
            if(searchOn.length >1){
                whereSql += " and tbl_don.D_MDO  in("+maonlineS+")";
            }else{
                whereSql += " and  tbl_don.D_MDO like '%"+maonlineS+"%'";
            }
        }
        if(loaidonS != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidonS;
        }
        if(loaihinhnhanS != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhanS ;
        }
        if(!manhanS.trim().equals("")){
            String[] searchND = manhanS.split(",");
            if(searchND.length >1){
                whereSql += " and tbl_don.D_manhan in ("+manhanS+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhanS+"%'";
            }
        }
//        if(!bbdS.trim().equals("")){
//            whereSql += " and group_concat(tbl_benbaodam.BDB_Name` separator ' \\n ') like '%"+bbdS+"%'";
//        }
//        if(!bnbdS.trim().equals("")){
//            whereSql += " and group_concat(tbl_bnbd.KHD_Name separator ' \\n ') like '%"+bnbdS+"%'";
//        }
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        if(!btpS.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name  like '%"+btpS+"%'";
        }
        String sql = "select tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                            "tbl_don.D_SLTS as slts,(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                            "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,\n" +
                            "tbl_don.D_manhan as manhan,\n" +
                            "(select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID =tbl_don.LP_ID) as lephi,\n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin, \n" +
                            "tbl_khachhang.KH_Name  as bndbtp\n" +
                            "from tbl_don\n" +
                            "inner join tbl_khachhang  on tbl_khachhang.KH_ID = tbl_don.KH_ID "
                            + " where tbl_don.D_isRemove = 0 "+whereSql
                            + " group by tbl_don.D_ID order by tbl_don.D_ID DESC limit "+(page-1)*10+",10;";
    //    System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String donid = rs.getString("id");
                String ngaynhap = rs.getString("thoigian");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, ngaynhap);
                String loaidon = rs.getString("loaidon");
                String bndbtp = rs.getString("bndbtp");
                String maonline = rs.getString("dononline");
                String mapin = rs.getString("mapin");
                String cctt = rs.getString("cctt");
                String lephi = rs.getString("lephi");
                dt.add(ngaynhap);dt.add(maloainhan);dt.add(loaidon);dt.add(bndbtp);
                dt.add(maonline);dt.add(mapin);dt.add(cctt);dt.add(lephi);dt.add(donid);
                data.add(dt);
//                table += "<tr>";
//                table += "<td>"+ngaynhap+"</td>";
//                table += "<td>"+maonline+"</td>";
//                table += "<td>"+mapin+"</td>";
//                table += "<td>"+maloainhan+"</td>";
//                table += "<td>"+loaidon+"</td>";
//                table += "<td>"+lephi.intValue()+"</td>";
//                table += "<td>"+cctt+"</td>";
////                table += "<td>"+bnbd+"</td>";
//                table += "<td>"+bndbtp+"</td>";
////                table += "<td>"+bbd+"</td>";
//                table += "</tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // view table thống kê nhập đơn
    public String viewThongKeNhapDon(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String btpS,ArrayList<Integer> donids){
        String table = "";
//        dt.add(ngaynhap);dt.add(maloainhan);dt.add(loaidon);dt.add(bndbtp);
//                dt.add(maonline);dt.add(mapin);dt.add(cctt);dt.add(lephi);
            ArrayList<ArrayList<String>> data = tkNhapdon(page, ngaybatdau, ngayketthuc, maonlineS, loaidonS, loaihinhnhanS,
                    manhanS, loaidkS, btpS,donids);
            for(ArrayList<String> dt : data){
                String ngaynhap = dt.get(0);
                String maloainhan = dt.get(1);
                String loaidon = dt.get(2);
                String btp = dt.get(3);
                String maonline = dt.get(4);
                String mapin = dt.get(5);
                String cctt = dt.get(6);
                String lephi = dt.get(7);
                String id = dt.get(8);
                String bnbd = new NhapDon().loadBnbdDon(Integer.parseInt(id));
                String bbd = new NhapDon().loadBBDDon(Integer.parseInt(id));
                table += "<tr>";
                table += "<td>"+ngaynhap+"</td>";
                table += "<td>"+maonline+"</td>";
                table += "<td>"+mapin+"</td>";
                table += "<td>"+maloainhan+"</td>";
                table += "<td>"+loaidon+"</td>";
                table += "<td>"+lephi+"</td>";
                table += "<td>"+cctt+"</td>";
                table += "<td>"+bnbd+"</td>";
                table += "<td>"+btp+"</td>";
                table += "<td>"+bbd+"</td>";
                table += "</tr>";
            }
            return table;
    }
    // tính tổng đơn
    public int countDon(String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String btpS,ArrayList<Integer> donids){
        int total =0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        if(!maonlineS.trim().equals("")){
            String[] searchOn = maonlineS.split(",");
            if(searchOn.length >1){
                whereSql += " and tbl_don.D_MDO  in("+maonlineS+")";
            }else{
                whereSql += " and tbl_don.D_MDO  like '%"+maonlineS+"%'";
            }
        }
        if(loaidonS != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidonS;
        }
        if(loaihinhnhanS != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhanS ;
        }
        if(!manhanS.trim().equals("")){
            String[] searchND = manhanS.split(",");
            String dons = "";
            if(searchND.length >1){
                whereSql += " and tbl_don.D_manhan in ("+dons+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhanS+"%'";
            }
        }
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        if(!btpS.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name  like '%"+btpS+"%'";
        }
        String sql = "select count(distinct tbl_don.D_ID)\n" +
                            "from tbl_don\n" 
                + " where tbl_don.D_isRemove = 0 \n"  +whereSql+ " limit 1;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }
    
    
    // view thống kê văn thư
    public ArrayList<ArrayList> tkVanthu(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String btpS,String mabuudien,ArrayList<Integer> donidList){
        ArrayList<ArrayList> vtList = new ArrayList();
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_vanthu.VT_ngaygoi >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and tbl_vanthu.VT_ngaygoi <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        if(!maonlineS.trim().equals("")){
            String[] searchOn = maonlineS.split(",");
            if(searchOn.length >1){
                whereSql += " and tbl_don.D_MDO  in("+maonlineS+")";
            }else{
                whereSql += " and tbl_don.D_MDO  like '%"+maonlineS+"%'";
            }
        }
        if(loaidonS != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidonS;
        }
        if(loaihinhnhanS != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhanS ;
        }
        if(!manhanS.trim().equals("")){
            String[] searchND = manhanS.split(",");
            String dons = "";
            if(searchND.length >1){
                whereSql += " and tbl_don.D_manhan in ("+dons+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhanS+"%'";
            }
        }
        if(!btpS.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name like '%"+btpS+"%'";
        }
         if(!donidList.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donidList.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        if(!mabuudien.trim().equals("")){
            String[] mabuudienArr = mabuudien.split(",");
            if(mabuudienArr.length >1){
                whereSql += " and tbl_don.D_manhan in ("+mabuudien+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+mabuudien+"%'";
            }
        }
        String sql = "select tbl_vanthu.VT_ID as vtid,\n" +
                                "ifnull(DATE_FORMAT(VT_ngaygoi ,'%d-%m-%Y'),'') as ngaygoi,\n" +
                                " tbl_vanthu.VT_MBD as mabuudien, \n" +
                                "tbl_vanthu.VT_noinhan as noinhan,tbl_vanthu.VT_SBL as sobienlai,\n" +
                                "group_concat(tbl_vtdon.D_ID) as donids\n" +
                                "from tbl_don \n" +
                                "inner join (tbl_vtdon inner join tbl_vanthu on tbl_vanthu.VT_ID = tbl_vtdon.VT_ID)\n" +
                                "on tbl_don.D_ID = tbl_vtdon.D_ID "+
                                "where 1=1 \n" +whereSql
                                + " group by tbl_vanthu.VT_ID order by tbl_vanthu.VT_ngaygoi desc limit "+((page-1)*10)+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList data = new ArrayList();
                int vtid = rs.getInt("vtid");
                String ngaygoi = rs.getString("ngaygoi");
                String mabuudienrs = rs.getString("mabuudien");
                String donids = rs.getString("donids");
                String noinhan = rs.getString("noinhan");
                String sobienlai = rs.getString("sobienlai");
                data.add(vtid);data.add(ngaygoi);data.add(mabuudienrs);
                data.add(noinhan);data.add(sobienlai);data.add(donids);
                vtList.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return vtList;
    }
    
    public ArrayList<VanThuBean> getDonVT(String donids){
        ArrayList<VanThuBean> vtList = new ArrayList<>();
        String sql = "select DISTINCT tbl_don.D_ID as donid ,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian, \n" +
                            " tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan, \n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin,\n" +
                            "group_concat(tbl_bnbd.KHD_Name separator ' & ')   as bnbd,  "
                        + " tbl_khachhang.KH_Name  as bndbtp,\n" +
                            "group_concat(tbl_benbaodam.BDB_Name separator ' & ')  as benbaodam  \n" +
                            "from tbl_don \n" +
                            "inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID\n"+
                            " inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID "+
                            " inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID"+
                            " inner join tbl_loaidk on tbl_loaidk.DK_ID = tbl_don.DK_ID"+
                            " inner join tbl_loainhan on tbl_loainhan.LN_ID = tbl_don.LN_ID "+
                             " where tbl_don.D_ID in("+donids+") group by tbl_don.D_ID order by tbl_don.D_ID desc ;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
       //     pstm.setString(1, donids);
            rs = pstm.executeQuery();
            while(rs.next()){
                VanThuBean vtb = new VanThuBean();
                int donid = rs.getInt("donid");
                String ngaynhap = rs.getString("thoigian");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, ngaynhap);
                if(maloainhan.equals("CE1700000BD")){
                    maloainhan = "";
                }
                String maonline = rs.getString("dononline");
                String mapin = rs.getString("mapin"); 
                String btp = rs.getString("bndbtp");
                String bnbd = rs.getString("bnbd");
                String bbd = rs.getString("benbaodam");
                vtb.setDonid(donid);vtb.setDaytime(ngaynhap);vtb.setManhan(maloainhan);
                vtb.setDononline(maonline);vtb.setMapin(mapin);vtb.setBtp(btp);vtb.setBbd(bbd);vtb.setBnbd(bnbd);
                vtList.add(vtb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        return vtList;
    }
    
    // lấy đơn lưu văn thư nếu group > 1023
    public String getDonLuuVTByVTID(int vtid){
        String donids = "";
        String sql = "SELECT D_ID FROM qlhsdb.tbl_vtdon where VT_ID = ? ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, vtid);
            rs = pstm.executeQuery();
            while(rs.next()){
                if(donids.equals("")){
                    donids = rs.getString(1);
                }else{
                    donids += ","+rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return donids;
    }
    // return table thống kê văn thư
    public String returnTableTkVanthu(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String btpS,String mabuudien,ArrayList<Integer> donids){
        String table ="";
        ArrayList<ArrayList> vtList = tkVanthu(page, ngaybatdau, ngayketthuc, maonlineS, loaidonS,
                loaihinhnhanS, manhanS, loaidkS,  btpS,  mabuudien,donids);
        for(ArrayList data : vtList){
            String donid = data.get(5).toString();
            if(donid.length() >1023){
                donid = getDonLuuVTByVTID(Integer.parseInt(data.get(0).toString()));
            }
            ArrayList<VanThuBean> vtb = getDonVT(donid);
            
            int row = vtb.size();
            table += "<tr>";
            table += "<th rowspan='"+row+"' class='body' align='center' valign='middle'>"+data.get(1)+"</th>";
            table += "<th rowspan='"+row+"' class='body' align='center' valign='middle'>"+data.get(2)+"</th>";
            table += "<th rowspan='"+row+"' class='body' align='center' valign='middle'>"+data.get(3)+"</th>";
            table += "<th rowspan='"+row+"' class='body' align='center' valign='middle'>"+data.get(4)+"</th>";
            int i=1;
            for(VanThuBean vt : vtb){
                String bnbd = new NhapDon().loadBnbdDon(vt.getDonid());
                String bbd  = new NhapDon().loadBBDDon(vt.getDonid());
                if(i==1){
                    table += "<td >"+vt.getDaytime()+"</td>";
                    table += "<td >"+vt.getDononline()+"</td>";
                    table += "<td>"+vt.getMapin()+"</td>";
                    table += "<td>"+vt.getManhan()+"</td>";
                    table += "<td>"+bnbd+"</td>";
                    table += "<td>"+vt.getBtp()+"</td>";
                    table += "<td>"+bbd+"</td>";
                    table+= "</tr>";
                }else{
                     table+= "<tr>";
                    table += "<td>"+vt.getDaytime()+"</td>";
                    table += "<td>"+vt.getDononline()+"</td>";
                    table += "<td>"+vt.getMapin()+"</td>";
                    table += "<td>"+vt.getManhan()+"</td>";
                    table += "<td>"+bnbd+"</td>";
                    table += "<td>"+vt.getBtp()+"</td>";
                    table += "<td>"+bbd+"</td>";
                    table+= "</tr>";
                }
                i++;
            }
        }
        return table;
    }
    
    // lấy tổng số trang thống kê văn thư
    public int getTotalPagetkvt(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String btpS,String mabuudien,ArrayList<Integer> donids){
        int total = 0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        if(!maonlineS.trim().equals("")){
            String[] searchOn = maonlineS.split(",");
            if(searchOn.length >1){
                whereSql += " and tbl_don.D_MDO  in("+maonlineS+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+maonlineS+"%'";
            }
        }
        if(loaidonS != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidonS;
        }
        if(loaihinhnhanS != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhanS ;
        }
        if(!manhanS.trim().equals("")){
            String[] searchND = manhanS.split(",");
            String dons = "";
            if(searchND.length >1){
                whereSql += " and tbl_don.D_manhan in ("+dons+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhanS+"%'";
            }
        }
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", mabuudien)+")";
        }
        if(!btpS.trim().equals("")){
            whereSql += " and  tbl_khachhang.KH_Name  like '%"+btpS+"%'";
        }
        if(!mabuudien.trim().equals("")){
            String[] mabuudienArr = mabuudien.split(",");
            if(mabuudienArr.length >1){
                whereSql += " and tbl_don.D_manhan in ("+mabuudien+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+mabuudien+"%'";
            }
        }
        String sql = "select count( DISTINCT tbl_vanthu.VT_ID) as vtid \n" +
                                "from tbl_don \n" +
                                "inner join (tbl_vtdon inner join tbl_vanthu on tbl_vanthu.VT_ID = tbl_vtdon.VT_ID)\n" +
                                "on tbl_don.D_ID = tbl_vtdon.D_ID "+
                                " where tbl_don.D_isRemove = 0 "+whereSql+ " limit 1 ;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (total/10)+1;
    }
    
    
    /// Thống kê phân đơn
    // Lấy tổng số tài sản theo ngày
    public ArrayList<PhanDonBean> getSLTSDays(String ngaybatdau,String ngayketthuc){
        ArrayList<PhanDonBean> pdbList = new ArrayList<>();
        String whereSql = "";
         if(!ngaybatdau.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        String sql = "select distinct DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngayphan, SUM(tbl_don.D_SLTS) as total \n" +
                        "from tbl_don \n" +
                        "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                        "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                        "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                        "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                        "inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID \n" +
                        "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                        "inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID\n" +
                        "where tbl_don.D_isRemove = 0 "+whereSql+
                        "group by tbl_don.D_Date;";
        connect = new DBConnect().dbConnect();
        System.out.println(sql);
        try {
            pstm = connect.prepareStatement(sql);
            rs= pstm.executeQuery();
            while(rs.next()){
                String ngayphan = rs.getString("ngayphan");
                int slts = rs.getInt("total");
                PhanDonBean pdb = new PhanDonBean();
                pdb.setNgayphan(ngayphan);
                pdb.setSlts(slts);
                pdbList.add(pdb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pdbList;
    }
    
    // Lấy số tài sản của nhân viên
    public int getSLTSNhanvien(String username,String ngaylay){
        int total = 0;
        String sql = "select  SUM(tbl_phandon.PD_TS) as total \n" +
                            "from tbl_don \n" +
                            "inner join tbl_phandon on tbl_phandon.D_ID = tbl_don.D_ID\n" +
                            "where DATE(tbl_don.D_Date) = STR_TO_DATE(?, '%d-%m-%Y') \n" +
                            "and tbl_phandon.PD_NVnhan = ? \n" +
                            "group by tbl_phandon.PD_NVnhan;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, ngaylay);
            pstm.setString(2, username);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }
    
    // Lấy id nhân viên được phân theo quãng thời gian
    public ArrayList<String> getNVienDuocPhan(String ngaybatdau,String ngayketthuc){
        ArrayList<String> nvList = new ArrayList<>();
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and DATE(tbl_phandon.PD_Time) >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and DATE(tbl_phandon.PD_Time) <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        String sql = "select distinct tbl_phandon.PD_NVnhan \n" +
                "from tbl_phandon\n" +
                "inner join tbl_don on tbl_phandon.D_ID = tbl_don.D_ID\n" +
                "where 1 =1 "+whereSql+" ;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                nvList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nvList;
    }
    
    // Lấy view dữ liệu thông tin phân đơn
    public String viewTablePhanDon(String ngaybatdau,String ngayketthuc){
        ArrayList<PhanDonBean> pdbList = getSLTSDays(ngaybatdau, ngayketthuc);
        ArrayList<String> nvList = getNVienDuocPhan(ngaybatdau, ngayketthuc);
        String table = "<table>";
        String th = "<thead><tr><th>Nhân viên</th>";
        String tbody = "<tbody><tr>";
         int i=0;
         System.out.println("nv "+nvList.size());
          DecimalFormat df2 = new DecimalFormat(".##");
        for(String nv : nvList){
            tbody += "<tr>";
            tbody += "<td>"+nv+"</td>";
            double tongdinhmuc = 0;
            double tongthucte= 0;
            double tongts = 0;
            for(PhanDonBean pdb : pdbList){
                double dinhmuc = getDinhmuc(pdb.getSlts(), nvList.size());
                double sodu = getSoDuTS(pdb.getSlts(), nvList.size());
                ArrayList<String> nvTop = countDonNVNhan(pdb.getNgayphan(), (int)sodu);
                if(i< sodu && dinhmuc != 1){
                    for(String nvtop : nvTop){
                        if(nvtop.equals(nv)){
                            dinhmuc += 1;
                          i++;
                        }
                    }
                }
               
                double sotsnv = getSLTSNhanvien(nv, pdb.getNgayphan());
                tbody += "<td><span style='font-weight:bold'>"+(int)dinhmuc+"</span><span style='color:red;font-weight:bold'>("+df2.format((dinhmuc/pdb.getSlts())*100)+"%)</span></td>";
                tbody += "<td><span style='font-weight:bold'>"+getSLTSNhanvien(nv, pdb.getNgayphan())+"</span><span style='color:red;font-weight:bold'>("+df2.format((sotsnv/pdb.getSlts())*100)+"%)</span></td>";
                tongdinhmuc += dinhmuc;
                tongthucte += getSLTSNhanvien(nv, pdb.getNgayphan());
                tongts += pdb.getSlts();
            }
            
            tbody += "<td><span style='font-weight:bold'>"+tongdinhmuc+"</span>"
                    + "<span style='color:red;font-weight:bold'>("+df2.format((tongdinhmuc/tongts)*100)+"%)</span></td>"
                    + "<td><span style='font-weight:bold'>"+tongthucte+"</span>"
                    + "<span style='color:red;font-weight:bold'>("+df2.format((tongthucte/tongts)*100)+"%)</span></td></tr>";
        }
        for(PhanDonBean pdb : pdbList){
                 th += "<th colspan='2'>"+pdb.getNgayphan()+"</th>";
         }
        th += "<td>Tổng định mức</td><td>Tổng thực tế</td></tr></thead>";
        table += th+tbody+"</table>";
        return table;
    }
    
    public int getDinhmuc(int slts,int slnv){
        int dinhmuc = 0;
        if(slts < slnv){
            dinhmuc = 1;
        }else{
            dinhmuc = slts/slnv;
        }
        return dinhmuc;
    }
    public int getSoDuTS(int slts,int slnv){
        int sodu = slts%slnv;
        return sodu;
    }
    
    public ArrayList<String> countDonNVNhan(String ngayphan,int limit){
        ArrayList<String> nvLIst = new ArrayList<>();
        String whereSql = "";
        
        if(!ngayphan.trim().equals("") ){
            whereSql += " and DATE(PD_Time) = STR_TO_DATE('"+ngayphan+"','%d-%m-%Y') ";
        }
        String sql = "select PD_NVnhan \n" +
                        "from tbl_phandon where 1=1 \n" +whereSql+
                        " group by PD_NVnhan \n" +
                        "  LIMIT "+limit+";";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                nvLIst.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nvLIst;
    }
    
}
