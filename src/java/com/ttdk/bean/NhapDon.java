/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import com.ttdk.connect.DBConnect;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thorfinn
 */
public class NhapDon {
    private Connection connect;
    private PreparedStatement pstm;
    private ResultSet rs;
    public NhapDon(){}
    
    public String getMaso(int loaidk,int loainhan){
        String maso = "";
        String last = "";
        String head = "";
        switch (loaidk) {
            case 1: last = "BD";
                break;
            case 2: last = "TT";
                break;
            case 3: last = "CSGT";
                break;
            default:
                break;
        }

        
        String table = "";
        switch (loainhan) {
            case 1: head = "CE";
                break;
            case 2: head = "CF";
                break;
            case 3: head = "CT";
                break;
            case 4: head = "CB";
                break;
            default:
                break;
        }
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String headYStr = df.format(Calendar.getInstance().getTime());
        int headY = Integer.parseInt(headYStr+"0");
        DateFormat dfYear = new SimpleDateFormat("yyyy");
        String sqlYearStr = dfYear.format(Calendar.getInstance().getTime());
       // System.out.println("YYYY"+sqlYearStr);
        int body = 0;
        String sql =  "Select coalesce(MAX(D_manhan)+1, 1) from tbl_don "
                + "where tbl_don.DK_ID = ? and tbl_don.LN_ID = ? and YEAR(tbl_don.D_Date)= "+sqlYearStr+" order by tbl_don.D_ID desc limit 1;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, loaidk);
            pstm.setInt(2, loainhan);
            rs = pstm.executeQuery();
            while(rs.next()){
                body = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if(body < 99){
            maso = head + headYStr + "0000"+String.valueOf(body)+last;
        }else if(body < 999){
            maso = head + headYStr + "000"+String.valueOf(body)+last;
        }else if(body < 999){
            maso = head + headYStr + "00"+String.valueOf(body)+last;
        }else{
            maso = head + headYStr + "0"+String.valueOf(body)+last;
        }
//        else{
//            maso = head + headYStr +String.valueOf(body)+last;
//        }
        //maso = head + headYStr + String.valueOf(body)+last;
        return maso;
    }
     
    public int insertNhapDon(Connection conn,Date ngaynhap,int sotaisan,int dkid,int manhan,int lnid,int loaidon,int khtp,int cctt,int lpID,String maonline,String mapin,int gio_nhap,int phutnhap){
        // loai hop dong = loaidang ky --> qlhs
        int key =-1;
        //connect = new DBConnect().dbConnect();
        String sql = "insert into tbl_don(D_Date,D_SLTS,DK_ID,D_manhan,LN_ID,LD_ID,CCTT_ID,KH_ID,LP_ID,D_GioNhan,D_PhutNhan,D_MDO,P_Pin) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
        
        if(conn != null){
            try {
                pstm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                pstm.setDate(1, ngaynhap);
                pstm.setInt(2, sotaisan);
                pstm.setInt(3, dkid);
                pstm.setInt(4, manhan);
                pstm.setInt(5, lnid);
                pstm.setInt(6, loaidon);
                pstm.setInt(7, cctt);
                pstm.setInt(8, khtp);                
                pstm.setInt(9, lpID);  
                 pstm.setInt(10, gio_nhap); pstm.setInt(11, phutnhap); pstm.setString(12, maonline); pstm.setString(13, mapin);
                pstm.executeUpdate();
                rs = pstm.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    key = rs.getInt(1);
                }
            } catch (SQLException ex) {
               // Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
               ex.printStackTrace();
            }finally{
            try {
                new DBConnect().closePSRS( pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
        return key;
    }
  
    
    public void insertBNBD(Connection conn,int donID,String KHName,String KHDC){
        String sql = "insert into tbl_bnbd(KHD_ID,KHD_Name,KHD_Address) values(?,?,?);";
      //  connect = new DBConnect().dbConnect();
        if(conn != null){
            try {
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, donID);
                pstm.setString(2, KHName);
                pstm.setString(3, KHDC);
                pstm.executeUpdate();
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closePSRS( pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void insertBBD(Connection conn,int donID,String BBDName,String BBDCMND){
        String sql = "insert into tbl_benbaodam(D_ID,BDB_Name,BDB_CMND) values(?,?,?);";
        //connect = new DBConnect().dbConnect();
        if(conn != null){
            try {
                pstm = conn.prepareStatement(sql);
                pstm.setInt(1, donID);
                pstm.setNString(2, BBDName);
                pstm.setNString(3, BBDCMND);
                pstm.executeUpdate();
                pstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closePSRS( pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    public String returnManhan(String loaidk,String manhan,String loainhan,String thoigian){
        String[] thoigianArr = thoigian.split("-");
        String yyyy = thoigianArr[2];
       // yyyy = yyyy.substring(2);
        String manloaihan = "";
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        //String headYStr = df.format(Calendar.getInstance().getTime());
        String headYStr = yyyy.substring(2);
        int headY = Integer.parseInt(headYStr+"0");
        int body = Integer.parseInt(manhan);
     //   System.out.println("BODY = "+body);
        if(body < 99){
            manloaihan = loainhan + headYStr + "0000"+String.valueOf(body)+loaidk;
        }else if(body < 999){
            manloaihan = loainhan + headYStr + "000"+String.valueOf(body)+loaidk;
        }else if(body < 9999){
            manloaihan = loainhan + headYStr + "00"+String.valueOf(body)+loaidk;
        }else{
            manloaihan = loainhan + headYStr + "0"+String.valueOf(body)+loaidk;
        }
        
//        }else{
//            manloaihan = loainhan + headY +String.valueOf(body)+loaidk;
//        }
        return manloaihan;
    }
    // View đơn đã nhập
    public String viewNhapDon(int page,String role3,String role4,String day){
        String sql = "select tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                                "tbl_don.D_SLTS as slts,tbl_cctt.CCTT_Des as cctt,\n" +
                                "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan \n" +
                                ",tbl_loaidon.LD_Des as loaidon,\n" +
                                "ifnull(tbl_don.D_MDO,'') as dononline,\n" +
                                "ifnull(tbl_don.P_Pin,'') as mapin,\n" +
                                "group_concat(DISTINCT tbl_bnbd.KHD_Name separator ' & ') as bnbd,\n" +
                                "tbl_khachhang.KH_Name  as bndbtp,\n" +
                                "group_concat( tbl_benbaodam.BDB_Name,'\\n',tbl_benbaodam.BDB_CMND separator '\\n--------------\\n') as benbaodam \n" +
                                "from tbl_don\n" +
                                "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                                "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                                "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                                "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                                "inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID \n" +
                                "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                                "inner join tbl_khachhang  on tbl_khachhang.KH_ID = tbl_don.KH_ID\n" +
                                    " where tbl_don.D_Date > STR_TO_DATE(?,'%d-%m-%Y') "+
                                " group by tbl_don.D_ID order by tbl_don.D_ID DESC limit "+(page-1)*10+",10;";
        String table = "";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, "'"+day+"'");
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                table +="<tr>";
                String thoigian = rs.getString("thoigian");
                table +="<td>"+thoigian+"</td>";
                String maOnline = rs.getString("dononline");
                if(maOnline == null){
                    maOnline = "";
                }
                
                String loaidk = rs.getString("loaidk");
                if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maOnline+"</a></td>";
                }else{
                    table +="<td ><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maOnline+"</a></td>";
                }
                String maPin = rs.getString("mapin");
                 if(maPin == null){
                    maPin = "";
                }
                table +="<td>"+maPin+"</td>";
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = returnManhan(loaidk, manhan, loainhan,thoigian);
              //  System.out.println(loaidk);
                if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }else{
                    table +="<td><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }
                String loaidon = rs.getString("loaidon"); 
                table +="<td>"+loaidon+"</td>";
                int slts = rs.getInt("slts");
                table +="<td>"+slts+"</td>";
                String cctt = rs.getString("cctt");
                table +="<td>"+cctt+"</td>";
                String bnbd = rs.getString("bnbd");
                table +="<td>"+bnbd+"</td>";
                String benTP = rs.getString("bndbtp");
                table +="<td>"+benTP+"</td>";
                String benbd = rs.getString("benbaodam");
                table +="<td>"+benbd+"</td>";
                if(role3.equals("1")){
                    table +="<td><img src=\"./images/document_edit.png\" data-toggle=\"modal\" onclick='editDon('"+id+"')'></td>\n" ;
                }
                if(role4.equals("1")){
                    table += "<td><img src=\"./images/document_delete.png\" onclick='deleteDon('"+id+"')'></td></tr>";
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    public ArrayList<ArrayList<String>> loadDonTable(int page,String day){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                            "tbl_don.D_SLTS as slts,(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                            "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,\n" +
                            "tbl_don.D_manhan as manhan,\n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin, \n" +
                            "tbl_khachhang.KH_Name  as bndbtp\n" +
                            "from tbl_don\n" +
                            "inner join tbl_khachhang  on tbl_khachhang.KH_ID = tbl_don.KH_ID\n" +
                                    " where tbl_don.D_Date > STR_TO_DATE(?,'%d-%m-%Y') "+
                                " group by tbl_don.D_ID order by tbl_don.D_ID DESC limit "+(page-1)*10+",10;";
        String table = "";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, "'"+day+"'");
            rs = pstm.executeQuery();
            while(rs.next()){
                 ArrayList<String> dt = new ArrayList<>();
                String id = rs.getString("id");
                dt.add(id);
                String thoigian = rs.getString("thoigian");
                dt.add(thoigian);
                String maOnline = rs.getString("dononline");
                if(maOnline == null){
                    maOnline = "";
                }
                dt.add(maOnline);
                String loaidk = rs.getString("loaidk");
                dt.add(loaidk);
                String maPin = rs.getString("mapin");
                 if(maPin == null){
                    maPin = "";
                }
                 dt.add(maPin);
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = returnManhan(loaidk, manhan, loainhan,thoigian);
              //  System.out.println(loaidk);
                dt.add(maloainhan);
                String loaidon = rs.getString("loaidon"); 
                dt.add(loaidon);
                String slts = rs.getString("slts");
                dt.add(slts);
                String cctt = rs.getString("cctt");
                dt.add(cctt);
                String benTP = rs.getString("bndbtp");
                dt.add(benTP);
                data.add(dt);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // load bnbd
    public String loadBnbdDon(int donid){
        String bnbd = "";
        String sql = "select ifnull(group_concat(DISTINCT tbl_bnbd.KHD_Name separator ' & '),'') as bnbd\n" +
                            "from tbl_bnbd where tbl_bnbd.KHD_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                bnbd = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bnbd;
    }
    // load ben bao dam
    public String loadBBDDon(int donid){
        String bbd = "";
        String sql = "select ifnull(group_concat(DISTINCT tbl_benbaodam.BDB_Name separator ' & '),'') as bbd\n" +
                            "from tbl_benbaodam where tbl_benbaodam.D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                bbd = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bbd;
    }
    // view đơn nhập table
    public String viewDonNhapTable(int page,String role3,String role4,String day){
        String table = "";
        ArrayList<ArrayList<String>> donList = loadDonTable(page, day);
        int i=0;
        for(ArrayList<String> don : donList){
            String id = don.get(0);
            table +="<tr>";
            table +="<td>"+i+"</td>";
                String thoigian = don.get(1);
                table +="<td>"+thoigian+"</td>";
                String maOnline = don.get(2);
                if(maOnline == null){
                    maOnline = "";
                }
                String loaidk = don.get(3);
                if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maOnline+"</a></td>";
                }else{
                    table +="<td ><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maOnline+"</a></td>";
                }
                String maPin = don.get(4);
                 if(maPin == null){
                    maPin = "";
                }
                table +="<td>"+maPin+"</td>";
                String maloainhan = don.get(5);
              //  System.out.println(loaidk);
                if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }else{
                    table +="<td><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }
                String loaidon = don.get(6);
                table +="<td>"+loaidon+"</td>";
                String slts = don.get(7);
                table +="<td>"+slts+"</td>";
                String cctt = don.get(8);
                table +="<td>"+cctt+"</td>";
                String bnbd = loadBnbdDon(Integer.parseInt(id));
                table +="<td>"+bnbd+"</td>";
                String benTP = don.get(9);
                table +="<td>"+benTP+"</td>";
                String benbd = loadBBDDon(Integer.parseInt(id));
                table +="<td>"+benbd+"</td>";
                if(role3.equals("1")){
                    table +="<td><img src=\"./images/document_edit.png\" data-toggle=\"modal\" onclick='editDon('"+id+"')'></td>\n" ;
                }
                if(role4.equals("1")){
                    table += "<td><img src=\"./images/document_delete.png\" onclick='deleteDon('"+id+"')'></td></tr>";
                }
            i++;
        }
        return table;
    }
    //count row
    public int countRow(String ngaynhap){
        int page = 0;
        String sql = "select count(*) from tbl_don\n" +
                                "where tbl_don.D_ID in (select tbl_bnbd.KHD_ID from tbl_bnbd)\n" +
                                " and tbl_don.D_ID in (select tbl_benbaodam.D_ID from tbl_benbaodam) " +
                                    " and tbl_don.D_Date > STR_TO_DATE(?,'%d-%m-%Y') "+
                                " group by tbl_don.D_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, "'"+ngaynhap+"'");
            rs = pstm.executeQuery();
            if(rs.next()){
                page = rs.getInt(1);
                System.out.println(page);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return page/10 +1;
    }
    
    //  Lay so trang don chua phan
    public int getPageDCP(){
        int page = 0;
        String sql = "select  count(DISTINCT tbl_don.D_ID) as donid \n" +
                            "from tbl_don\n" +
                            "where D_isRemove =0 and  tbl_don.D_SLTS > 0 and (tbl_don.D_manhan <>'' or tbl_don.D_manhan > 0 ) "
                + " and tbl_don.D_manhan <> '' and tbl_don.D_ID not in  (SELECT tbl_phandon.D_ID FROM   tbl_phandon);";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
             rs = pstm.executeQuery();
             while(rs.next()){
                 page = rs.getInt(1);
             }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           return (page/10)+1;
    }
    // Search chua phan don
    public String searchChuaPhanDon(int page){
        String sql = "select tbl_don.D_ID as donid,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                            "tbl_don.D_SLTS as slts,\n" +
                            "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,\n" +
                            "tbl_don.D_manhan as manhan ,"+
                            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin " +
                            "from tbl_don\n" +
                            "where tbl_don.D_SLTS > 0 and (tbl_don.D_manhan <>'' or tbl_don.D_manhan > 0 ) "
                        + "and tbl_don.D_ID not in  (SELECT tbl_phandon.D_ID FROM   tbl_phandon) "+
                            "group by tbl_don.D_ID order by tbl_don.D_ID desc limit "+(page-1)*10+",10;";
        System.out.println(sql);
        String table = "";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                 int donid = rs.getInt("donid");
                 int slts = rs.getInt("slts");
                 if(slts ==0){
                     continue;
                 }
                 String thoigian = rs.getString("thoigian");
                 if(thoigian == null){
                     continue;
                 }
                table +="<tr>";
                
                String loaidk = rs.getString("loaidk");
                table +="<td>"+thoigian+"</td>";
                String maOnlineRS = rs.getString("dononline");
              //  table +="<td class='dononline'><a href='./print/mau12.jsp?id="+donid+"'>"+maOnlineRS+"</a></td>";
                if(loaidk.equals("TT")){
                    table +="<td><a target=\"_blank\" href=\"./print/print_cctt2017.jsp?id="+donid+"\">"+maOnlineRS+"</a></td>";
                }else{
                    table +="<td><a target=\"_blank\" href=\"./print/print_mau2017.jsp?id="+donid+"\">"+maOnlineRS+"</a></td>";
                }
                String maPin = rs.getString("mapin");
                table +="<td>"+maPin+"</td>";
                
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = "";
                maloainhan = returnManhan(loaidk, manhan, loainhan,thoigian);
                if(loaidk.equals("TT")){
                    table +="<td><a target=\"_blank\" href=\"./print/print_cctt2017.jsp?id="+donid+"\">"+maloainhan+"</a></td>";
                }else{
                    table +="<td><a target=\"_blank\" href=\"./print/print_mau2017.jsp?id="+donid+"\">"+maloainhan+"</a></td>";
                }
                String loaidonRS = rs.getString("loaidon"); 
                table +="<td>"+loaidonRS+"</td>";
                
                table +="<td class=\"slts\" >"+slts+"</td>";
               
                table +="<td><input id='donid"+donid+"' class=\"selectDon\" type=\"checkbox\" value=\""+donid+"\"></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    // Search chua phan don
    public String searchChuaPhanDon(int page,String ngaynhapS,String maonlineS,int loainhanS, int loaidonS, int loaidkS,String manhanS){
        String whereSql = "";
        if(!ngaynhapS.equals("")){
            whereSql += " and tbl_don.D_Date >= str_to_date('"+ngaynhapS+"','%d-%m-%Y') ";
        }
        if(!maonlineS.equals("")){
            String[] maoS = maonlineS.split(",");
            if(maoS.length == 1){
                whereSql += " and tbl_don.D_MDO like '%"+maonlineS+"%'";
            }else{
                whereSql += " and tbl_don.D_MDO in ("+maonlineS+") ";
            }
            
        }
        if(!manhanS.equals("")){
            String[] mnS = manhanS.split(",");
            if(mnS.length == 1){
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhanS)+"%' ";
            }else{
                whereSql += " and tbl_don.D_manhan in ("+manhanS+") ";
            }
        }
        if(loaidkS != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidkS;
        }
        if(loaidonS != 0){
            whereSql += " and tbl_loaidk.DK_ID = "+loaidonS;
        }
        if(loainhanS != 0){
            whereSql += " and tbl_don.LN_ID = "+loainhanS;
        }
        String sql = "select tbl_don.D_ID as donid,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                            "tbl_don.D_SLTS as slts,tbl_cctt.CCTT_Des as cctt,\n" +
                            "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan \n" +
                            ",tbl_loaidon.LD_Des as loaidon,\n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin " +
                            "from tbl_don\n" +
                            "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                            "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                            "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                            "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n"+
                        "inner join tbl_khachhang  on tbl_khachhang.KH_ID = tbl_don.KH_ID\n" +
                        "where tbl_don.D_SLTS > 0 and (tbl_don.D_manhan <>'' or tbl_don.D_manhan > 0 )  and tbl_don.D_ID not in (SELECT tbl_phandon.D_ID FROM   tbl_phandon) "+whereSql
                + " limit "+(page-1)*10+",10;";
        System.out.println(sql);
        String table = "";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                 int donid = rs.getInt("donid");
                 int slts = rs.getInt("slts");
                 if(slts ==0){
                     continue;
                 }
                table +="<tr>";
                String thoigian = rs.getString("thoigian");
                table +="<td>"+thoigian+"</td>";
                String loaidk = rs.getString("loaidk");
                String maOnlineRS = rs.getString("dononline");
          //      table +="<td class=\"dononline\"><a href=\"./print/mau12.jsp?id="+donid+"\">"+maOnlineRS+"</a></td>";
                if(loaidk.equals("TT")){
                    table +="<td><a target=\"_blank\" href=\"./print/print_cctt2017.jsp?id="+donid+"\">"+maOnlineRS+"</a></td>";
                }else{
                    table +="<td><a target=\"_blank\" href=\"./print/print_mau2017.jsp?id="+donid+"\">"+maOnlineRS+"</a></td>";
                }
                String maPin = rs.getString("mapin");
                table +="<td>"+maPin+"</td>";
                
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = returnManhan(loaidk, manhan, loainhan,thoigian);
                if(loaidk.equals("TT")){
                    table +="<td><a target=\"_blank\" href=\"./print/print_cctt2017.jsp?id="+donid+"\">"+maloainhan+"</a></td>";
                }else{
                    table +="<td><a target=\"_blank\" href=\"./print/print_mau2017.jsp?id="+donid+"\">"+maloainhan+"</a></td>";
                }
                String loaidonRS = rs.getString("loaidon"); 
                table +="<td>"+loaidonRS+"</td>";
                
                table +="<td class=\"slts\" >"+slts+"</td>";
               
                table +="<td></td><td></td><td></td><td></td><td><input id='donid"+donid+"' class=\"selectDon\" type=\"checkbox\" value=\""+donid+"\"></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    // get total page for search don
    public int pageSCPD(String ngaynhapS,String maonlineS,int loainhanS, int loaidonS, int loaidkS,String manhanS){
        int total = 0;
        String whereSql = "";
        if(!ngaynhapS.equals("")){
            whereSql += " and tbl_don.D_Date >= str_to_date('"+ngaynhapS+"','%d-%m-%Y') ";
        }
        if(!maonlineS.equals("")){
            String[] maoS = maonlineS.split(",");
            if(maoS.length == 1){
                whereSql += " and tbl_don.D_MDO like '%"+maonlineS+"%'";
            }else{
                whereSql += " and tbl_don.D_MDO in ("+maonlineS+") ";
            }
            
        }
        if(!manhanS.equals("")){
            String[] mnS = manhanS.split(",");
            if(mnS.length == 1){
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhanS)+"%' ";
            }else{
                whereSql += " and tbl_don.D_manhan in ("+manhanS+") ";
            }
        }
        if(loaidkS != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidkS;
        }
        if(loaidonS != 0){
            whereSql += " and tbl_loaidk.DK_ID = "+loaidonS;
        }
        if(loainhanS != 0){
            whereSql += " and tbl_don.LN_ID = "+loainhanS;
        }
        String sql = "select count(DISTINCT tbl_don.D_ID)"+
                    "from tbl_don\n" +
                    "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                    "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                    "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                 "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                "where tbl_don.D_SLTS > 0 and (tbl_don.D_manhan <>'' or tbl_don.D_manhan > 0 ) and tbl_don.D_ID not in (SELECT tbl_phandon.D_ID FROM   tbl_phandon) "+whereSql+ " ;";
      //  System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
          //      System.out.println(total);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (total/10)+1;
    }
    // kiểm tra đơn phân
    public int checkDonPhan(int donid){
        int slts = 0;
        String sql = "select D_ID,PD_TS from tbl_phandon where D_ID = ?";
         connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                slts = rs.getInt("PD_TS");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                pstm.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        return slts;
    }

    public String donDaPhan(){

        String sql = "select tbl_don.D_ID as donid, concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
            "tbl_don.D_SLTS ,tbl_cctt.CCTT_Des, tbl_don.D_loainhan, tbl_loaidon.LD_Des,\n" +
            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
            "ifnull(tbl_don.P_Pin,'') as mapin,\n" +
            "group_concat(tbl_bnbd.KHD_Name separator ' \n ') as bnbd,\n" +
            "tbl_khachhang.KH_Name  as bndbtp,\n" +
            "(select group_concat(`BDB_Name` separator ' \n ')\n" +
            "as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) as benbaodam\n" +
            " from tbl_don\n" +
            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID\n" +
            "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID\n" +
            "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID\n" +
            "inner join tbl_bnbd on tbl_don.D_ID =  tbl_bnbd.KHD_ID\n" +
            " inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID "+
            "left join tbl_phandon on tbl_don.D_ID = tbl_phandon.D_ID \n" +
            "limit 1,10;";
        String table = "";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                table +="<tr>";
                String donid=  rs.getString("donid");
                String thoigian = rs.getString("thoigian");
                table +="<td>"+thoigian+"</td>";
                int maOnlineRS = rs.getInt("dononline");
                String loaidk = rs.getString("loaidk");
             //   table +="<td ><a href=\"./print/mau12.jsp\""+maOnlineRS+"</td>";
                if(loaidk.equals("TT")){
                    table +="<td><a target=\"_blank\" href=\"./print/print_cctt2017.jsp?id="+donid+"\">"+maOnlineRS+"</a></td>";
                }else{
                    table +="<td><a target=\"_blank\" href=\"./print/print_mau2017.jsp?id="+donid+"\">"+maOnlineRS+"</a></td>";
                }
                int maPin = rs.getInt("mapin");
                table +="<td>"+maPin+"</td>";
                
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String manhanRS = rs.getString("manhan");
                String maloainhan = returnManhan(loaidk, manhanRS, loainhan,thoigian);
                if(loaidk.equals("TT")){
                    table +="<td><a target=\"_blank\" href=\"./print/print_cctt2017.jsp?id="+donid+"\">"+maloainhan+"</a></td>";
                }else{
                    table +="<td><a target=\"_blank\" href=\"./print/print_mau2017.jsp?id="+donid+"\">"+maloainhan+"</a></td>";
                }
                String loaidonRS = rs.getString("loaidon"); 
                table +="<td>"+loaidonRS+"</td>";
                int slts = rs.getInt("slts");
                table +="<td>"+slts+"</td>";
                String cctt = rs.getString("cctt");
                table +="<td>"+cctt+"</td>";
                String bnbdRS = rs.getString("bnbd");
                table +="<td>"+bnbdRS+"</td>";
                String benTP = rs.getString("bndbtp");
                table +="<td>"+benTP+"</td>";
                String benbd = rs.getString("benbaodam");
                table +="<td>"+benbd+"</td>";
                table +="<td><img src=\"./images/document_edit.png\"  onclick=\"editDon()\"></td>" +
                "<td><img src=\"./images/document_delete.png\" onclick=\"deleteDon()\"></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    public void traDon(int donID,int nvID){
        String sql = "delete from tbl_phandon where donID = ? and PD_NVnhan = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donID);
            pstm.setInt(2, nvID);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    //phan don
    public void phanDon(int donID,String nvPhan,String nvNhan,int sotaisan,String tgPhan) throws SQLException{
        String sql = "Insert into tbl_phandon(D_ID,PD_NVphan,PD_NVnhan,PD_TS,PD_Time) values(?,?,?,?,?);";
        connect = new DBConnect().dbConnect();
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donID);
            pstm.setString(2, nvPhan);
            pstm.setString(3, nvNhan);
            pstm.setInt(4, sotaisan);
            pstm.setString(5, tgPhan);
            pstm.executeUpdate();
        
    }
    public int kiemtraTaiSan(int donid){
        int ts = -1;
        String sql = "select D_SLTS from tbl_don where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                ts = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ts;
    }
    public void traDon(Date tgTra){
        String sql = "Update tbl_phandon SET PD_tra = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setDate(1, tgTra);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
  
    
    // search đơn id theo bnbd 25-7-2017
    public ArrayList<Integer> searchDonIDByBNBD(String bnbd,int page){
        ArrayList<Integer> donid = new ArrayList<>();
        String sql = "  select tbl_bnbd.KHD_ID \n" +
                                "from tbl_bnbd where tbl_bnbd.KHD_Name like ? \n" +
                                "group by tbl_bnbd.KHD_ID order by tbl_bnbd.KHD_ID desc limit "+(page-1)*10+",10;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, "%"+bnbd+"%");
            rs = pstm.executeQuery();
            while(rs.next()){
                donid.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return donid;
    }
    
    // search ddown id theo bnbd
    public ArrayList<Integer> searchDonIDByBBD(String bbd,int page){
        ArrayList<Integer> donid = new ArrayList<>();
        String sql = "  select tbl_benbaodam.D_ID \n" +
                                "from tbl_benbaodam where tbl_benbaodam.BDB_Name like ? \n" +
                                "group by tbl_benbaodam.D_ID order by tbl_benbaodam.D_ID desc limit "+(page-1)*10+",10;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, "%"+bbd+"%");
            rs = pstm.executeQuery();
            while(rs.next()){
                donid.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return donid;
    }
    
    // search đơn mới
    public ArrayList<ArrayList<String>> searchDonNhap(int page,String time,String maonline,int loaidon,int loaihinhnhan,String manhan,String btp,ArrayList<Integer> donids,String role3,String role4){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String whereSql = "";
        if(!time.trim().equals("")){
            whereSql +=" and tbl_don.D_Date >= STR_TO_DATE('"+time+"', '%d-%m-%Y') ";
        }
        if(!maonline.trim().equals("")){
            String[] searchOn = maonline.split(",");
            if(searchOn.length >1){
                whereSql += " and tbl_don.D_MDO in("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+maonline+"%'";
            }
            
        }
        if(loaidon != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidon;
        }
        if(loaihinhnhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhan ;
        }
        if(!manhan.trim().equals("")){
            String[] searchND = manhan.split(",");
            if(searchND.length >1){
                whereSql += " and tbl_don.D_manhan in ("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhan)+"%'";
            }
            
        }
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", "")+")";
        }
        if(!btp.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name like '%"+btp.trim()+"%'";
        }
        String sql ="select tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                        "tbl_don.D_SLTS as slts,(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                        "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                        "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                        "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,\n" +
                        "tbl_don.D_manhan as manhan,\n" +
                        "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                        "ifnull(tbl_don.P_Pin,'') as mapin, \n" +
                        "tbl_khachhang.KH_Name  as bndbtp\n" +
                        "from tbl_don\n" +
                        "inner join tbl_khachhang  on tbl_khachhang.KH_ID = tbl_don.KH_ID\n" +
                        "where  tbl_don.D_isRemove = 0 "+whereSql+"  order by TIMESTAMP(tbl_don.D_Date) DESC,tbl_don.D_manhan DESC limit "+(page-1)*10+",10;";
        //System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String id = rs.getString("id");
                dt.add(id);
                String thoigian = rs.getString("thoigian");
                dt.add(thoigian);
                String maOnline = rs.getString("dononline");
                if(maOnline.equals("0")){
                    maOnline = "";
                }
                dt.add(maOnline);
                String loaidk = rs.getString("loaidk");
                dt.add(loaidk);
                String maPin = rs.getString("mapin");
                 if(maPin == null){
                    maPin = "";
                }
                 dt.add(maPin);
                String manhanrs = rs.getString("manhan");
                System.out.println("MA NHAN = "+manhanrs);
                String loainhan = rs.getString("loainhan");
                String maloainhan = "";
                if(!manhanrs.equals("") && !manhanrs.equals("0")){
                     maloainhan = returnManhan(loaidk, manhanrs, loainhan,thoigian);
                }
              //  String maloainhan = returnManhan(loaidk, manhanrs, loainhan,thoigian);
              //  System.out.println(loaidk);
                dt.add(maloainhan);
                String loaidonrs = rs.getString("loaidon"); 
                dt.add(loaidonrs);
                String slts = rs.getString("slts");
                dt.add(slts);
                String cctt = rs.getString("cctt");
                dt.add(cctt);
                String benTP = rs.getString("bndbtp");
                dt.add(benTP);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // view table search đơn nhập
    public String viewSearchDonNhap(int page,String time,String maonline,int loaidon,int loaihinhnhan,String manhan,
            String bbd,String bnbd,String btp,String role3,String role4){
        ArrayList<Integer> donids =  new ArrayList<>();
        if(!bnbd.trim().equals("")){
            donids.addAll(searchDonIDByBNBD(bnbd,page)) ;
        }
        if(!bbd.trim().equals("")){
            donids.addAll(searchDonIDByBBD(bbd,page));
        }
        String table = "";
        ArrayList<ArrayList<String>> donList = searchDonNhap(page, time, maonline, loaidon, loaihinhnhan, manhan, btp, donids, role3, role4);
        for(ArrayList<String> don : donList){
            String id = don.get(0);
            table +="<tr>";
                String thoigian = don.get(1);
                table +="<td>"+thoigian+"</td>";
                String maOnline = don.get(2);
                if(maOnline.equals("0")){
                    maOnline = "";
                }
                String loaidk = don.get(3);
                if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maOnline+"</a></td>";
                }else{
                    table +="<td ><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maOnline+"</a></td>";
                }
                String maPin = don.get(4);
                 if(maPin == null){
                    maPin = "";
                }
                table +="<td>"+maPin+"</td>";
                String maloainhan = don.get(5);
              //  System.out.println(maloainhan);
                if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }else{
                    table +="<td><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }
                String loaidonS = don.get(6);
                table +="<td>"+loaidonS+"</td>";
                String slts = don.get(7);
                table +="<td>"+slts+"</td>";
                String cctt = don.get(8);
                table +="<td>"+cctt+"</td>";
                String bnbdS = loadBnbdDon(Integer.parseInt(id));
                table +="<td>"+bnbdS+"</td>";
                String benTP = don.get(9);
                table +="<td>"+benTP+"</td>";
                String benbd = loadBBDDon(Integer.parseInt(id));
                table +="<td>"+benbd+"</td>";
                if(role3.equals("1")){
                    table +="<td><img src=\"./images/document_edit.png\" data-toggle=\"modal\" onclick='editDon(\""+id+"\")'></td>\n" ;
                }
                if(role4.equals("1")){
                    table += "<td><img src=\"./images/document_delete.png\" id='"+id+"'' class='img_delete_don'></td></tr>";
                }
        }
        return table;
    }
    
    
    
    
    
    // Select lệ phí
    
    public ArrayList<LePhiBean> getLePhi(){
        ArrayList<LePhiBean> lpArr = new ArrayList<>();
        String sql = "select * from tbl_lephi where LP_End is null;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                LePhiBean lpb = new LePhiBean();
                lpb.setLpid(rs.getInt(1));
                lpb.setLdID(rs.getInt(2));
                lpb.setLpPrice(rs.getInt(3));
                lpb.setBeginDate(rs.getDate(4));
                lpb.setEndDate(rs.getDate(5));
                lpArr.add(lpb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lpArr;
    }
    
    public ArrayList<Object> loadPrintDon(int donid){
        ArrayList<Object> donArr = new ArrayList<>();
        String sql = "select DISTINCT  tbl_don.D_GioNhan as gionhan,tbl_don.D_PhutNhan as phutnhan,DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhan,\n" +
                           "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan,"+
                            "tbl_loaidon.LD_Des as loaidon,\n" +
                            "tbl_don.D_MDO as dononline,\n" +
                            "tbl_don.P_Pin as mapin,\n" +
                            "group_concat(DISTINCT  tbl_bnbd.KHD_Name separator ' & ') as bnbd,\n" +
                            "tbl_khachhang.KH_Name  as bndbtp,tbl_don.KH_ID as khid,tbl_khachhang.KH_Account as account,\n" +
                            "group_concat(DISTINCT  tbl_benbaodam.BDB_Name,'\\n',tbl_benbaodam.BDB_CMND separator '\\n--------------\\n') as benbaodam \n" +
                            " from tbl_don\n" +
                            "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                            "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                            "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                            "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                            "inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID \n" +
                            "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                            "inner join tbl_khachhang  on tbl_khachhang.KH_ID = tbl_don.KH_ID\n" +
                            "where tbl_don.D_ID = ?; ";
        System.out.println(sql); 
         connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
             pstm.setInt(1, donid);
             rs = pstm.executeQuery();
             while(rs.next()){
                 donArr.add(rs.getInt("gionhan"));
                 donArr.add(rs.getInt("phutnhan"));
                 donArr.add(rs.getString("ngaynhan"));
                 String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, rs.getString("ngaynhan"));
                 donArr.add(maloainhan);
                 donArr.add(rs.getString("mapin"));
                 donArr.add(rs.getString("dononline"));
                 donArr.add(rs.getString("bnbd"));
                 donArr.add(rs.getString("account"));
                 donArr.add(rs.getString("benbaodam"));
                 donArr.add(rs.getString("khid"));
                 donArr.add(rs.getString("mapin"));
             }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return donArr;
    }
    
    public  ArrayList<Object> loadEditDon(int donid){
        ArrayList<Object> don = new ArrayList<>();
        String sql ="select DISTINCT  tbl_don.D_ID as donid, tbl_don.D_GioNhan as gionhan,tbl_don.D_PhutNhan as phutnhan,\n" +
                        "DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhan,tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan,\n" +
                        "tbl_don.D_SLTS as slts,tbl_cctt.CCTT_Des as cctt,tbl_loaidon.LD_Des as loaidon, \n" +
                        "tbl_don.D_MDO as dononline, \n" +
                        "tbl_don.P_Pin as mapin, \n" +
                        "group_concat( DISTINCT tbl_bnbd.KHD_Name,'_',tbl_bnbd.KHD_Address separator '\\n') as bnbd, \n" +
                        "group_concat(tbl_khachhang.KH_Name,'_',tbl_khachhang.KH_Address,'_',tbl_khachhang.KH_Account,\"_\",tbl_khachhang.KH_ID)as bndbtp,\n" +
                        "group_concat( DISTINCT tbl_benbaodam.BDB_Name,'_',tbl_benbaodam.BDB_CMND separator '\\n') as benbaodam \n" +
                        "from tbl_don \n" +
                        "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID\n" +
                        "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID\n" +
                        " inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                        "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                        "inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID\n" +
                        "inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID\n" +
                        " inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                            "where tbl_don.D_ID =?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                don.add(rs.getInt("donid"));
                don.add(rs.getInt("gionhan"));
                don.add(rs.getInt("phutnhan"));
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                 String maloainhan = returnManhan(loaidk, manhan, loainhan,rs.getString("ngaynhan"));
                don.add(rs.getString("ngaynhan"));
                don.add(rs.getString("dononline"));
                don.add(rs.getString("mapin"));
                 don.add(rs.getInt("slts"));
                 don.add(rs.getString("cctt"));
                 don.add(rs.getString("loaidon"));
                don.add(rs.getString("bnbd"));
                don.add(rs.getString("bndbtp"));
                don.add(rs.getString("benbaodam"));
                don.add(maloainhan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return don;
    }
    
    
    public ArrayList<Integer> checkBNBD(int id){
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "select BNBD_ID from tbl_bnbd where KHD_ID = ? ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("BNBD_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    public void updateBNBD(int id,String name,String address){
        String sql = "update tbl_bnbd set KHD_Name = ?,  KHD_Address = ? where BNBD_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setInt(3, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void updateBTP(int id,int btpid){
        String sql = "update tbl_don set KH_ID = ? where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, btpid);
            pstm.setInt(2, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public ArrayList<Integer> checkBBD(int id){
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "select BBD_ID from tbl_benbaodam where D_ID = ? ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("BBD_ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    public void updateBBD(int id,String name,String cmnd){
        String sql = "update tbl_benbaodam set BDB_Name = ?,  BDB_CMND = ? where BBD_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, cmnd);
            pstm.setInt(3, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void updateDon(int donid,String daytime,String manhan,int loaidon,int cctt,int lpid,int slts,String maonline,String mapin,int gio_nhap,int phutnhap){
        String sql = "update tbl_don set D_manhan = ?, D_Date = ?, D_SLTS = ?, LD_ID = ?, CCTT_ID = ?,"
                + " LP_ID = ? ,D_GioNhan =?,D_PhutNhan = ?,D_GioNhan = ?,D_PhutNhan = ?,D_MDO = ?,P_Pin = ?where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, manhan);
            pstm.setString(2, daytime);
            pstm.setInt(3, slts);
            pstm.setInt(4, loaidon);
            pstm.setInt(5, cctt);
            pstm.setInt(6, lpid);
            pstm.setInt(7, gio_nhap);pstm.setInt(8, phutnhap);
            pstm.setString(9, maonline);pstm.setString(10, mapin);
            pstm.setInt(11, donid);
            
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    // delete don
    public void deleteBNBD(int bnbdid){
        String sql = "delete from tbl_bnbd where BNBD_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bnbdid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void deleteBBD(int bbdid){
        String sql = "delete from tbl_benbaodam where BBD_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bbdid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    // search don page
    public int getPagesearchDon(int page,String time,String maonline,int loaidon,int loaihinhnhan,String manhan,String bbd,String bnbd,String btp){
        int returnPage = 0;
        String whereSql = "";
        if(!time.trim().equals("")){
            whereSql +=" and tbl_don.D_Date >= STR_TO_DATE('"+time+"', '%d-%m-%Y') ";
        }
        if(!maonline.trim().equals("")){
            String[] searchOn = maonline.split(",");
            if(searchOn.length >1){
                whereSql += " and tbl_don.D_MDO in("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+maonline+"%'";
            }
            
        }
        if(loaidon != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidon;
        }
        if(loaihinhnhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhan ;
        }
        if(!manhan.trim().equals("")){
            String[] searchND = manhan.split(",");
            if(searchND.length >1){
                whereSql += " and tbl_don.D_manhan in ("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhan)+"%'";
            }
            
        }
        ArrayList<Integer> donids =  new ArrayList<>();
        if(!bnbd.trim().equals("")){
            donids.addAll(searchDonIDByBNBD(bnbd,page)) ;
        }
        if(!bbd.trim().equals("")){
            donids.addAll(searchDonIDByBBD(bbd,page));
        }
        if(!donids.isEmpty()){
            whereSql += " and tbl_don.D_ID in ("+donids.toString().replaceAll("\\[", "").replaceAll("\\]", "")+") ";
        }
        if(!btp.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name like '%"+btp+"%'";
        }
        String sql = "select count(distinct tbl_don.D_ID)"+
                    " from tbl_don\n " +
                    "where  D_isRemove = 0 "+whereSql+" limit 1 ;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                returnPage = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return returnPage/10 +1;
    }
    
    // Load bên thu phí chưa thanh toán
    
    public ArrayList<ArrayList<String>> loadBTP_CTP(String btp,int st,int total){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String whereSql = "";
        String havingSql = "";
        if(!btp.equals("")){
            whereSql += " and tbl_khachhang.KH_Name  like '%"+btp+"%'";
        }
        if(st >= 0){
            havingSql += " and count(distinct date_format(tbl_don.D_Date,'%m-%Y')) >= "+st+" ";
        }
        if(total >= 0){
            havingSql += " and SUM((select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID)) >= "+total+" ";
        }
        String sql = "select tbl_don.KH_ID as khID,count(distinct date_format(tbl_don.D_Date,'%m-%Y')) as sothang,\n" +
                            " tbl_khachhang.KH_Name  as bnbd,\n" +
                            "SUM((select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID))  as tongtien ,\n" +
                            "GROUP_CONCAT(tbl_don.D_ID SEPARATOR ',') as donids\n" +
                            "from tbl_don \n" +
                        " inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID "+
                            "where tbl_don.D_ID not in (SELECT tbl_dondatp.D_ID FROM tbl_dondatp) \n" +
                            whereSql+" group by tbl_don.KH_ID"
                            + " having 1=1"+havingSql+" ;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String khID = rs.getString("khID");
                String sothang = rs.getString("sothang");
                String bnbd = rs.getString("bnbd");
                String tongtien = rs.getString("tongtien");
                String donids = rs.getString("donids");
                dt.add(khID);dt.add(bnbd);dt.add(sothang);
                dt.add(tongtien);dt.add(donids);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
   
    // load view talble btp chua tp
    public String loadTableBTP_CTP(String btp,int st,int total){
        String table = "";
        ArrayList<ArrayList<String>> data = loadBTP_CTP(btp, st, total);
        int i =1;
        for(ArrayList<String> dt : data){
            table += "<tr>";
            table += "<td>"+i+"</td>";
            table += "<td>"+dt.get(1)+"<input  type='text' value='"+dt.get(0)+"' class='khid' hidden></td>";
            table += "<td>"+dt.get(2)+"</td>";
            table += "<td>"+dt.get(3)+"<input  type='text' value='"+dt.get(4)+"' class='donids' hidden></td>";
            table += "</tr>";
            i++;
        }
        return table;
    }
    
    // load ben bao dam chua tp
    public  ArrayList<ThuPhiBean> loadBBD_CTP(String donids){
        String sql =  "select tbl_don.D_ID as donid,DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap,tbl_don.D_MDO as maonline ,\n" +
                            "group_concat(tbl_benbaodam.BDB_Name  SEPARATOR ',') as benbaodam,\n" +
                            "(select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID) as tongtien\n" +
                            "from tbl_don \n" +
                            "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID \n" +
                            "where  tbl_don.D_ID in( "+donids+") group by tbl_don.D_ID ;";
        ArrayList<ThuPhiBean> tpbArr = new ArrayList<>();
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                String ngaynhap = rs.getString("ngaynhap");
                String manhan = rs.getString("maonline");
                ThuPhiBean tpb = new ThuPhiBean();
                tpb.setBenbaodam(rs.getString("benbaodam"));
                tpb.setTongtien(rs.getDouble("tongtien"));
                tpb.setDonids(rs.getString("donid"));
                tpb.setManhan(manhan);             
                tpb.setNgaynhap(ngaynhap);
                tpbArr.add(tpb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tpbArr;
    }
    // load table ben bao dam chua tp
    public String loadTableBBD_CTP(String donids){
        String table = "";
        ArrayList<ThuPhiBean> listTpb = loadBBD_CTP(donids);
        for(int i = 0;i < listTpb.size();i++){
            ThuPhiBean tpb = listTpb.get(i);
            table += "<tr>";
            table += "<td>"+(i+1)+"</td>";
            table += "<td>"+tpb.getManhan()+"</td>";
            table += "<td>"+tpb.getNgaynhap()+"</td>";
            table += "<td>"+tpb.getBenbaodam()+"<input type='text' id='ctp_donid"+i+"' value='"+tpb.getDonids()+"' hidden></td>";
             Double total = tpb.getTongtien();
             Integer totalInt = total.intValue();
             table += "<td>"+totalInt+"</td>";
            table += "</tr>";
        }
        return table;
    }
    
    // Load Nhân viên 
    public ArrayList<ArrayList<String>> loadNhanVien(){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select ifnull(tbl_nhanviennhapdon.U_ID,0),tbl_username.U_ID from tbl_nhanviennhapdon\n" +
                            "inner join tbl_username on tbl_username.U_ID = tbl_nhanviennhapdon.U_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs= pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString(1));dt.add(rs.getString(2));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    public ArrayList<String> checkDonTrung(String[] bnbd,String[] bbd,int ldid, String ngaynhap,String maonline) throws UnsupportedEncodingException{
        ArrayList<String> data = null;

        String bnbdCheck = "";
        for(int i=0; i < bnbd.length;i++){
            if(i ==0){
                bnbdCheck += "('"+bnbd[i]+"'";
            }else{
                bnbdCheck += ",'"+bnbd[i]+"'";
            }
            if(i == bnbd.length-1){
                bnbdCheck += ")";
            }
        }
        String bbdCheck = "";
        for(int i=0; i < bbd.length;i++){
            if(i ==0){
                bbdCheck += "('"+bbd[i]+"'";
            }else{
                bbdCheck += ",'"+bbd[i]+"'";
            }
            if(i == bbd.length-1){
                bbdCheck += ")";
            }
        }
                String sql = "select tbl_don.D_MDO as dononline,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
            "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan\n" +
            "from tbl_don \n" +
            "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID\n" +
            "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID\n" +
            " inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
            "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
            "inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID\n" +
            "inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID\n" +
            " inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
            " where tbl_bnbd.KHD_Name in "+bnbdCheck+" \n" +
            " and tbl_benbaodam.BDB_Name in "+bbdCheck+" and tbl_don.LD_ID = ? \n" +
            " and tbl_don.D_Date >= str_to_date(?,'%d-%m-%Y') and tbl_don.D_MDO = ?;";
                
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, ldid);
            pstm.setString(2, ngaynhap);
            pstm.setString(3, maonline);
            rs= pstm.executeQuery();
            while(rs.next()){
                System.out.println(sql);
                data = new ArrayList<>();
                String online = rs.getString("dononline");
                String thoigian = rs.getString("thoigian");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan =rs.getString("loainhan");
                String maloainhan = returnManhan(loaidk, manhan, loainhan, thoigian);
                data.add(online);
                data.add(thoigian);
                data.add(maloainhan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Cập nhật giờ hiệu lực
    public void updateGioHL(int giohl,int donid){
        String sql = "update tbl_don set D_GioHL = ? where tbl_don.D_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, giohl);
            pstm.setInt(2, donid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // cập nhật giờ,phút  hiệu lực với đơn online
    public void updateGioHL(int giohl,int phuthl,int donid,Connection conn){
        String sql = "update tbl_don set D_GioHL = ?, D_PhutHL = ? where tbl_don.D_ID = ? ;";
    //    connect = new DBConnect().dbConnect();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, giohl);
            pstm.setInt(2, phuthl);
            pstm.setInt(3, donid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // update phut hieuluc
    public void updatePhutHL(int phuthl,int donid){
        String sql = "update tbl_don set D_PhutHL = ? where tbl_don.D_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, phuthl);
            pstm.setInt(2, donid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    // lấy đơn id có trong bnbd và bbd
    public ArrayList<String> getDonIDinBNBDvaBBD(){
        ArrayList<String> data =  new ArrayList<>();
        String sql = "select tbl_bnbd.KHD_ID as donid from tbl_bnbd\n" +
                    "union\n" +
                    "select tbl_benbaodam.D_ID from tbl_benbaodam";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // load bnbd by donid
    public ArrayList<ArrayList<String>> getBNBDByDonID(int donid){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select KHD_Name as bnbdname,KHD_Address as diachi from tbl_bnbd where KHD_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString("bnbdname"));dt.add(rs.getString("diachi"));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // load bbd by donid
    public ArrayList<ArrayList<String>> getBBDByDonID(int donid){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select BDB_Name as bbdname,BDB_CMND as maso from tbl_benbaodam where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString("bbdname"));dt.add(rs.getString("maso"));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // check đơn đã lưu phân đơn
    public boolean checkDonLuuVT(int donid){
        boolean check = false;
        String sql = "select 1 from tbl_vtDon where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    // check đơn lưu thongbaophi
    public boolean checkDonLuuTBP(int donid){
        boolean check = false;
        String sql = "select 1 from tbl_guithuphi where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    // check đơn lưu VT CSGT
    public boolean checkDonLuuTPCSGT(int donid){
        boolean check = false;
        String sql = "select 1 from tbl_doncsgt where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    // check đơn lưu thu phí
    public boolean checkDonLuuTP(int donid){
        boolean check = false;
        String sql = "select 1 from tbl_dondatp where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    // kiểm tra xóa đơn
    public int deleteDonCheck(int donid){
        //Kiểm tra đơn đã được lưu văn thư hay tbp hay tp hay chưa
        int result = 0;
        boolean checkDonLuu = checkDonLuuTP(donid);
        if(checkDonLuu){
            result = updateDeleteDon(donid);
        }else{
            checkDonLuu = checkDonLuuTPCSGT(donid);
            if(!checkDonLuu){
                checkDonLuu = checkDonLuuVT(donid);
                if(!checkDonLuu){
                    checkDonLuu = checkDonLuuTBP(donid);
                    if(!checkDonLuu){
                        deleteDonBBD(donid);deleteDonBNBD(donid);
                        result = deleteDon(donid);
                    }else{
                        result = updateDeleteDon(donid);
                    }
                }else{
                    result = updateDeleteDon(donid);
                }
            }else{
                result = updateDeleteDon(donid);
            }
        }
        return result;
    }
    
    // update đơn đã xóa
    public int updateDeleteDon(int donid){
        int resultUpdate = 0;
        String sql = "update tbl_don set D_isRemove = 1 where D_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            resultUpdate = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultUpdate;
    }
    // Xóa đơn lưu
    public int deleteDon(int donid){
        int result = 0;
        String sql = "delete from tbl_don where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            result = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    // Xóa bên bảo đảm
    public int deleteDonBBD(int donid){
        int result = 0;
        String sql = "delete from tbl_benbaodam where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            result = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    // Xóa bên nhận bảo đảm
    public int deleteDonBNBD(int donid){
        int result = 0;
        String sql = "delete from tbl_bnbd where KHD_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            result = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    // lấy mã đơn online
    public String getMaPhuLuc(int loaimapl,String ngaynhap){
        String maso = "";
        String head = "";
        String whereSql = "";
        switch(loaimapl){
            case 1 : whereSql += " and tbl_don.DK_ID = 3 and tbl_don.LN_ID = 1;";
                          head = "CE";
                break;
            case 0: whereSql += " and tbl_don.DK_ID = 3 and tbl_don.LN_ID = 2;";
                        head = "CF";
                break;
        }
        String sql = "select ifnull(MAX(D_manhan)+1,1) as donpl, date_format(tbl_don.D_Date,'%y') as headY \n" +
                            "from tbl_don\n" +
                            "where YEAR(tbl_don.D_Date) = YEAR(str_to_date(?,'%d-%m-%Y'))"+whereSql;
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, ngaynhap);
            rs = pstm.executeQuery();
            while(rs.next()){
                int body = rs.getInt(1);
                String headYStr = rs.getString("headY");
                if(body < 99){
                    maso = head + headYStr + "0000"+String.valueOf(body)+"CSGT";
                }else if(body < 999){
                    maso = head + headYStr + "000"+String.valueOf(body)+"CSGT";
                }else if(body < 999){
                    maso = head + headYStr + "00"+String.valueOf(body)+"CSGT";
                }else{
                    maso = head + headYStr + "0"+String.valueOf(body)+"CSGT";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return maso;
    }
}