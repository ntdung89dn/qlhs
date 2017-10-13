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
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thorfinn
 */
public class SearchDon {
    private Connection connect;
    private PreparedStatement pstm;
    private ResultSet rs;
    
    public SearchDon(){}
    
    // Search don chua phan
    public String searchChuaPhan(int page,String day,String maOnline,int loainhan,
        String maloainhan,int loaidonS, int loaidkS){
        String whereSelect = "";
       day =  day.replaceAll("/", "-");
           
        if(!maOnline.trim().equals("") ){
            int lengthSearch = maOnline.split(",").length;
            if(lengthSearch >1){
                whereSelect += " and tbl_don.D_MDO in ("+maOnline.trim()+")";
            }else{
                whereSelect += " and tbl_don.D_MDO LIKE '%"+maOnline.trim()+"%'";
            }
        }
        
        if(loainhan != 0){
            whereSelect += " and tbl_don.LN_ID = "+loainhan; 
        }
        if(loaidonS != 0){
        //    System.out.println("loaidonS : "+loaidonS);
            whereSelect += " and tbl_don.LD_ID = "+loaidonS;
        }
        if(loaidkS != 0){
      //      System.out.println("LOAIDK : "+loaidkS);
            whereSelect += " and  tbl_don.DK_ID = "+loaidkS;
        }
        if(!maloainhan.trim().equals("")){
            String[] manhans = maloainhan.split(",");
            if(manhans.length >1){
                whereSelect += " and tbl_don.D_manhan in("+maloainhan+")";
            }else{
                whereSelect += " and tbl_don.D_manhan like '%"+maloainhan+"%'";
            }
        }
        //String selectMaNhan = "";
       // System.out.println("day = "+day);
        String sql = "select tbl_don.D_ID as donid,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                        "tbl_don.D_SLTS as slts,tbl_cctt.CCTT_Des as cctt,\n" +
                        "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan \n" +
                        ",tbl_loaidon.LD_Des as loaidon,\n" +
                        "tbl_don.D_MDO as dononline,\n" +
                        "tbl_don.P_Pin as mapin,\n" +
                        "group_concat(tbl_bnbd.KHD_Name separator ' & ') as bnbd,\n" +
                        "tbl_khname.KHN_Name  as bndbtp,\n" +
                        "group_concat(tbl_benbaodam.BDB_Name,'\\n',tbl_benbaodam.BDB_CMND separator '\\n--------------\\n') as benbaodam \n" +
                        "from tbl_don  \n " +
                    "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                    "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                    "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                    "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                    "inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID \n" +
                    "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                    "inner join tbl_khachhang  on tbl_khachhang.KH_ID = tbl_don.KH_ID  \n" +
                        "where tbl_don.D_Date = str_to_date('"+day+"','%d-%m-%Y') "+whereSelect+" "
                + "limit "+(page-1)*10+",10;";
        //System.out.println(sql);
        String table = "";
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
             rs = pstm.executeQuery();
            while(rs.next()){
                table +="<tr>";
                int donid = rs.getInt("donid");
                String thoigian = rs.getString("thoigian");
                table +="<td>"+thoigian+"</td>";
                String loaidk = rs.getString("loaidk");
                int maOnlineRS = rs.getInt("dononline");
            //    table +="<td ><a href=\"./print/mau12.jsp\""+maOnlineRS+"</td>";
                if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+donid+"\">"+maOnlineRS+"</a></td>";
                }else{
                    table +="<td ><a href=\"./print/print_mau2017.jsp?id="+donid+"\">"+maOnlineRS+"</a></td>";
                }
                int maPin = rs.getInt("mapin");
                table +="<td>"+maPin+"</td>";
               
                String manhanrs = rs.getString("manhan");
                String loainhanrs = rs.getString("loainhan");
                String maloainhanrs = new NhapDon().returnManhan(loaidk, manhanrs, loainhanrs,thoigian);
                if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+donid+"\">"+maloainhanrs+"</a></td>";
                }else{
                    table +="<td ><a href=\"./print/print_mau2017.jsp?id="+donid+"\">"+maloainhanrs+"</a></td>";
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
                table +="<td><input id=\"donid"+donid+"\" value=\""+donid+"\" type=\"checkbox\"></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
            table= "";
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    // lấy số trang đơn phân
    public int getPageSearchCP(String day,String maOnline,int loainhan,
        String maloainhan,int loaidonS, int loaidkS){
        int page = 0;
        String whereSelect = "";
       day =  day.replaceAll("/", "-");
           
        if(!maOnline.trim().equals("") ){
             int lengthSearch = maOnline.split(",").length;
            if(lengthSearch >1){
                whereSelect += " and tbl_don.D_MDO in ("+maOnline.trim()+")";
            }else{
                whereSelect += " and tbl_don.D_MDO LIKE '%"+maOnline.trim()+"%'";
            }
        }
        
        if(loainhan != 0){
            whereSelect += " and tbl_don.LN_ID = "+loainhan; 
        }
        if(loaidonS != 0){
         //   System.out.println("loaidonS : "+loaidonS);
            whereSelect += " and tbl_don.LD_ID = "+loaidonS;
        }
        if(loaidkS != 0){
        //    System.out.println("LOAIDK : "+loaidkS);
            whereSelect += " and  tbl_don.DK_ID = "+loaidkS;
        }
        if(!maloainhan.trim().equals("")){
            String[] manhans = maloainhan.split(",");
            if(manhans.length >1){
                whereSelect += " and tbl_don.D_manhan in("+maloainhan+")";
            }else{
                whereSelect += " and tbl_don.D_manhan like '%"+maloainhan+"%'";
            }
        }

        String sql = "select count( DISTINCT  tbl_don.D_ID)" +
                    " from tbl_don \n " +
                    "inner join tbl_khachhang  on tbl_khachhang.KH_ID = tbl_don.KH_ID  \n" +
                        "where DATE(tbl_don.D_Date) = str_to_date('"+day+"','%d-%m-%Y') "+whereSelect+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
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
    

    
    public int getDonID(String loaihingnhan){
        int t = -1;
        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
      //  System.out.println("YEAR = "+year);
     //   System.out.println("month = "+month);
        try {
            
            String sql = "select D_ID from tbl_don where D_manhan = ? and D_DATE like '%"+year+"-"+month+"%' order by D_ID desc limit 1; ";
            connect = new DBConnect().dbConnect();
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, loaihingnhan);
            rs = pstm.executeQuery();
            while(rs.next()){
                t = rs.getInt("D_ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return t;
    }
    
    // VIEW don cho tra
    
    public String donChoTra(int page,String username){
        String sql = "select DISTINCT tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigiannhap,\n" +
                            "ifnull(tbl_don.D_MDO,'') as dononline,\n" +
                            "  ifnull(tbl_don.P_Pin,'') as mapin,\n" +
                            "tbl_don.D_manhan as manhan , tbl_don.D_SLTS as slts,\n" +
                                "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                                "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                                "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,"+
                            "  (select tbl_username.U_FullName from tbl_username where tbl_username.U_ID =  tbl_phandon.PD_NVnhan) as nvnhan, tbl_phandon.PD_NVnhan as userid,\n" +
                            "  tbl_phandon.PD_Time as tgphan,tbl_phandon.PD_TS as sltsphan,\n" +
                            "    (select tbl_username.U_FullName from tbl_username where tbl_username.U_ID =  tbl_phandon.PD_NVphan) as nvphan\n" +
                            "  ,tbl_phandon.PD_ID as phandonid \n" +
                            "from tbl_don\n" +
                            "inner join tbl_phandon on tbl_phandon.D_ID = tbl_don.D_ID\n" +
                            "where tbl_phandon.PD_tra is null and tbl_phandon.PD_NVnhan = ? limit "+(page-1)*10+",10;";
        System.out.println(sql);
        String table = "";
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            pstm.setString(1, username);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int pdid = rs.getInt("phandonid");
                table +="<tr>";
                String thoigian = rs.getString("thoigiannhap");
                table +="<td>"+thoigian+"</td>";
                String maOnlineRS = rs.getString("dononline");
                if(maOnlineRS == null ){
                    maOnlineRS = "";
                }
           //     System.out.println(maOnlineRS);
                table +="<td ><input type='text' id='online"+id+"' value='"+maOnlineRS+"' /></td>";
               String loaidk = rs.getString("loaidk");
//               if(loaidk.equals("TT")){
//                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maOnlineRS+"</a></td>";
//                }else{
//                    table +="<td><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maOnlineRS+"</a></td>";
//                }
                String maPin = rs.getString("mapin");
                if(maPin == null){
                    maPin = "";
                }
                table +="<td><input type='text' id='mapin"+id+"' value='"+maPin+"' /></td>";
                
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan,thoigian);
                
               // table +="<td><a href=\"./print/mau12.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                if(loaidk.equals("TT")){
                    table +="<td><a target='_blank' href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }else{
                    table +="<td><a target='_blank' href=\"./print/print_mau2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }
                String loaidonRS = rs.getString("loaidon"); 
                table +="<td>"+loaidonRS+"</td>";
                int slts = rs.getInt("slts");
                table +="<td>"+slts+"</td>";
                String nvnhan = rs.getString("nvnhan");
                String userid = rs.getString("userid");
                table +="<td>"+nvnhan+"<input type='text' value='"+userid+"' hidden></td>";
                String tgphanD =rs.getString("tgphan");
                tgphanD = tgphanD.substring(0, tgphanD.indexOf("."));
                String[] daytime = tgphanD.split(" ");
                daytime[1] = daytime[1].substring(0, daytime[1].lastIndexOf(":"));
                tgphanD = daytime[1] +" "+daytime[0];
                table +="<td>"+tgphanD+"</td>";
                int sltsphan = rs.getInt("sltsphan");
                table +="<td>"+sltsphan+"</td>";
                String nvphan = rs.getString("nvphan");
                table +="<td>"+nvphan+"</td>";
                table +="<td><button type=\"button\" id='dct_save"+id+"' class=\"dct_save btn btn-link\" >Lưu</button>-<button type=\"button\" id='dtchdon"+id+"' class=\"dct_hdon btn btn-link\" data-toggle=\"modal\" >Hồi đơn</button>"
                        + "<input type='text' id='donid"+id+"' value='"+id+"' hidden/></td><td><input type='checkbox' id='cb"+pdid+"' value='"+pdid+"'></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    
    //Lấy số trang đơn chờ trả
    public int pageDChoTra(String username){
        int total = 0;
        String sql = "select count(DISTINCT tbl_don.D_ID)  from tbl_don\n" +
                    "inner join (tbl_phandon pd1 inner join tbl_username user1 on user1.U_ID = pd1.PD_NVnhan)\n" +
                    "on tbl_don.D_ID = pd1.D_ID \n" +
                    "inner join (tbl_phandon pd2 inner join tbl_username user2 on user2.U_ID = pd2.PD_NVphan)\n" +
                    "on tbl_don.D_ID = pd2.D_ID \n" +
                    "where pd1.PD_tra is null and pd1.PD_NVphan = ? "+
                                "order by tbl_don.D_ID desc;";
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            pstm.setString(1, username);
            rs = pstm.executeQuery();
            while(rs.next()){
                total =  rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (total/10)+1;
    }
    // search don cho tra
    
    public String searchDCT(int page,String day,String maOnline,int loainhan,
        String maloainhan,String nguoinhan,int loaidonS,int loaidkS){
        String whereSelect = "";
        if(!day.trim().equals("")){
            whereSelect += " and tbl_phandon.PD_Time like '%"+day+"%'";
        }
        if(!maOnline.trim().equals("")){
            String[] onlineS = maOnline.split(",");
            if(onlineS.length >1){
                whereSelect += " and tbl_don.D_MDO in ("+maOnline+")";
            }else{
                whereSelect += " and tbl_don.D_MDO LIKE '%"+maOnline+"%'";
            }
            
        }
        if(!nguoinhan.trim().equals("all")){
            whereSelect +=" and tbl_phandon.PD_NVnhan = '"+nguoinhan+"'";
        }
        
        if(loainhan != 0){
            whereSelect += " and tbl_don.LN_ID = "+loainhan; 
        }
        if(loaidonS != 0){
          //  System.out.println("loaidonS : "+loaidonS);
            whereSelect += " and tbl_don.LD_ID = "+loaidonS;
        }
        if(loaidkS != 0){
          //  System.out.println("LOAIDK : "+loaidkS);
            whereSelect += " and  tbl_don.DK_ID = "+loaidkS;
        }
        if(!maloainhan.trim().equals("")){
            String[] manhans = maloainhan.split(",");
            if(manhans.length >1){
                whereSelect += " and tbl_don.D_manhan in("+maloainhan+")";
            }else{
                whereSelect += " and tbl_don.D_manhan like '%"+maloainhan+"%'";
            }
        }
        
        String sql ="select DISTINCT tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigiannhap,\n" +
                    "ifnull(tbl_don.D_MDO,'') as dononline,\n" +
                    "  ifnull(tbl_don.P_Pin,'') as mapin,\n" +
                    " tbl_don.D_manhan as manhan , tbl_don.D_SLTS as slts,\n" +
                    "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                    "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                    "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,"+
                    "  (select tbl_username.U_FullName from tbl_username where tbl_username.U_ID =  tbl_phandon.PD_NVnhan) as nvnhan, tbl_phandon.PD_NVnhan as userid,\n" +
                    "  tbl_phandon.PD_Time as tgphan,tbl_phandon.PD_TS as sltsphan,\n" +
                    "    (select tbl_username.U_FullName from tbl_username where tbl_username.U_ID =  tbl_phandon.PD_NVphan) as nvphan\n" +
                    "  ,tbl_phandon.PD_ID as phandonid \n" +
                    "from tbl_don\n" +
                    "inner join tbl_phandon on tbl_phandon.D_ID = tbl_don.D_ID\n" +
                    "where tbl_phandon.PD_tra is null and tbl_don.D_isRemove = 0 "+whereSelect+" order by tbl_don.D_ID desc limit "+(page-1)*10+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        String table = "";
        try {
            pstm =  connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int pdid = rs.getInt("phandonid");
                table +="<tr>";
                String thoigian = rs.getString("thoigiannhap");
                table +="<td>"+thoigian+"</td>";
                String maOnlineRS = rs.getString("dononline");
                String loaidk = rs.getString("loaidk");
                if(maOnlineRS == null){
                    maOnlineRS = "";
                }
                table +="<td><input type='text'  id='online"+id+"' value='"+maOnlineRS+"' style='max-width:150px;' ></td>";
//                if(loaidk.equals("TT")){
//                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maOnlineRS+"</a></td>";
//                }else{
//                    table +="<td><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maOnlineRS+"</a></td>";
//                }
                String maPin = rs.getString("mapin");
         //       System.out.println("MA PINNNNNNNNN "+maPin);
                 if(maPin == null){
                    maPin = "";
                }
                table +="<td ><input type='text' id='mapin"+id+"' value='"+maPin+"' style='max-width:50px;' ></td>";
                 
                String manhanrs = rs.getString("manhan");
                String loainhanrs = rs.getString("loainhan");
                String maloainhanrs = new NhapDon().returnManhan(loaidk, manhanrs, loainhanrs,thoigian);
              //  table +="<td><a href=\"./print/mau12.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                  if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                }else{
                    table +="<td><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                }
                String loaidonRS = rs.getString("loaidon"); 
                table +="<td>"+loaidonRS+"</td>";
                int slts = rs.getInt("slts");
                table +="<td>"+slts+"</td>";
                String nvnhan = rs.getString("nvnhan");
                String userid = rs.getString("userid");
                table +="<td>"+nvnhan+"<input type='text' value='"+userid+"' hidden></td>";
                String tgphanD =rs.getString("tgphan");
                tgphanD = tgphanD.substring(0, tgphanD.indexOf("."));
                String[] daytime = tgphanD.split(" ");
                daytime[1] = daytime[1].substring(0, daytime[1].lastIndexOf(":"));
                tgphanD = daytime[1] +" "+daytime[0];
                table +="<td>"+tgphanD+"</td>";
                int sltsphan = rs.getInt("sltsphan");
                table +="<td>"+sltsphan+"</td>";
                String nvphan = rs.getString("nvphan");
                table +="<td>"+nvphan+"</td>";
                table +="<td><button type=\"button\" id='dct_save"+id+"' class=\"dct_save btn btn-link\" >Lưu</button>-<button type=\"button\" id='dct_hdon"+id+"' class=\"btn btn-link dct_hdon\" data-toggle=\"modal\" >Hồi đơn</button>"
                        + "<input type='text' id='donid"+id+"' value='"+id+"' hidden/></td><td><input type='checkbox' value='"+pdid+"'></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return table;
    }
    // get page search don cho tra
    public int getpageSDChoTra(String day,String maOnline,int loainhan,
        String maloainhan,String nguoinhan,int loaidonS,int loaidkS){
        int total = 0;
        String whereSelect = "";
        if(!day.trim().equals("")){
            whereSelect += " and tbl_phandon.PD_Time like '%"+day+"%' ";
        }
        if(!maOnline.trim().equals("")){
            String[] onlineS = maOnline.split(",");
            if(onlineS.length >1){
                whereSelect += " and  tbl_don.D_MDO in ("+maOnline+")";
            }else{
                whereSelect += " and tbl_don.D_MDO  LIKE '%"+maOnline+"%'";
            }
            
        }
        if(!nguoinhan.equals("all")){
            whereSelect +=" and tbl_phandon.PD_NVnhan = '"+nguoinhan+"'";
        }
        
        if(loainhan != 0){
            whereSelect += " and tbl_don.LN_ID = "+loainhan; 
        }
        if(loaidonS != 0){
          //  System.out.println("loaidonS : "+loaidonS);
            whereSelect += " and tbl_don.LD_ID = "+loaidonS;
        }
        if(loaidkS != 0){
          //  System.out.println("LOAIDK : "+loaidkS);
            whereSelect += " and  tbl_don.DK_ID = "+loaidkS;
        }
        if(!maloainhan.trim().equals("")){
            String[] manhans = maloainhan.split(",");
            if(manhans.length >1){
                whereSelect += " and tbl_don.D_manhan in("+maloainhan+")";
            }else{
                whereSelect += " and tbl_don.D_manhan like '%"+maloainhan+"%'";
            }
        }
        
        String sql ="select count(DISTINCT tbl_don.D_ID)  from tbl_don\n" +
                                "inner join tbl_phandon on tbl_don.D_ID = tbl_phandon.D_ID \n" +
                                " where tbl_phandon.PD_tra is null and tbl_don.D_isRemove = 0 "+whereSelect+" order by tbl_don.D_ID desc;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return (total/10)+1;
    }
    //  Load don da tra
    
    public String loadDonDaTra(int page,String username,String ngayddt){
        String table = "";
        String sql = "select DISTINCT tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigiannhap,\n" +
                            "tbl_don.D_MDO as dononline,\n" +
                            "tbl_don.P_Pin as mapin,\n" +
                            "tbl_don.D_manhan as manhan , tbl_don.D_SLTS as slts,\n" +
                            "(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                            "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,"+
                            "tbl_username.U_FullName as nvnhan,\n" +
                            "tbl_phandon.PD_Time as tgphan,tbl_phandon.PD_tra as tgtra, tbl_phandon.PD_TS as sltsphan\n" +
                            "from tbl_don\n" +
                            "inner join (tbl_phandon inner join tbl_username on tbl_username.U_ID = tbl_phandon.PD_NVnhan)on tbl_don.D_ID = tbl_phandon.D_ID \n" +
                            "where tbl_phandon.PD_tra is not null and tbl_don.D_isRemove = 0 and tbl_phandon.PD_NVnhan = ? and tbl_don.D_Date = str_to_date(?,'%d-%m-%Y') \n" +
                            " order by tbl_don.D_ID desc limit "+((page-1)*10)+",10;";
        //System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, ngayddt);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                table +="<tr>";
                String thoigian = rs.getString("thoigiannhap");
                table +="<td>"+thoigian+"</td>";
                String maOnlineRS = rs.getString("dononline");
                if(maOnlineRS == null){
                    maOnlineRS = "";
                }
                table +="<td >"+maOnlineRS+"</td>";
                String maPin = rs.getString("mapin");
                 if(maPin == null){
                    maPin = "";
                }
                table +="<td>"+maPin+"</td>";
                String loaidk = rs.getString("loaidk");
                String manhanrs = rs.getString("manhan");
                String loainhanrs = rs.getString("loainhan");
                String maloainhanrs = new NhapDon().returnManhan(loaidk, manhanrs, loainhanrs,thoigian);
            //    table +="<td><a href=\"./print/mau12.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                if(loaidk.equals("TT")){
                    table +="<td><a href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                }else{
                    table +="<td><a href=\"./print/print_mau2017.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                }
                String loaidonRS = rs.getString("loaidon"); 
                table +="<td>"+loaidonRS+"</td>";
                int slts = rs.getInt("slts");
                table +="<td>"+slts+"</td>";
                String tgphanD =rs.getString("tgphan");
                tgphanD = tgphanD.substring(0, tgphanD.indexOf("."));
                String[] daytime = tgphanD.split(" ");
                daytime[1] = daytime[1].substring(0, daytime[1].lastIndexOf(":"));
                tgphanD = daytime[1] +" "+daytime[0];
                table +="<td>"+tgphanD+"</td>";
                String nvnhan = rs.getString("nvnhan");
                table +="<td>"+nvnhan+"</td>";
                String tgTra = "";
                if(rs.getString("tgtra") != null){
                    tgTra=rs.getString("tgtra");
                    tgTra = tgTra.substring(0, tgTra.indexOf("."));
                    String[] daytimeT = tgTra.split(" ");
                    daytimeT[1] = daytimeT[1].substring(0, daytimeT[1].lastIndexOf(":"));
                    tgTra = daytimeT[1] +" "+daytimeT[0];
                }
                
                 table +="<td>"+tgTra+"</td>";
                int sltsphan = rs.getInt("sltsphan");
                table +="<td>"+sltsphan+"</td>";
                
                table +="</tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return table;
    }
    // SEARCH LOAD DON DA TRA
    public String loadSearchDonDaTra(int page,String ngaygio,String maonline, int loainhan,String manhan
            ,int loaidon,int  loaidkInt,String nguoinhan){
        String whereSql = "";
        if(!ngaygio.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaygio+"','%d-%m-%Y') ";
        }
        if(!maonline.trim().equals("")){
            String[] onlineS = maonline.split(",");
            if(onlineS.length >1){
                whereSql += " and tbl_don.D_MDO in ("+maonline+")";
            }else{
                whereSql += " and  tbl_don.D_MDO  LIKE '%"+maonline+"%'";
            }
        }
        if(!nguoinhan.trim().equals("all")){
            whereSql +=" and tbl_phandon.PD_NVnhan = '"+nguoinhan+"'";
        }
        
        if(loainhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loainhan; 
        }
        if(loaidkInt != 0){
      //      System.out.println("loaidonS : "+loaidon);
            whereSql += " and tbl_don.LD_ID = "+loaidkInt;
        }
        if(loaidon != 0){
      //      System.out.println("LOAIDK : "+loaidkInt);
            whereSql += " and  tbl_don.DK_ID = "+loaidon;
        }
        if(!manhan.trim().equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhan+"%'";
            }
        }
        
        String table = "";
        String sql = "select DISTINCT tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigiannhap,\n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin,\n" +
                            "(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                            "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,\n" +
                            "tbl_don.D_manhan as manhan , tbl_don.D_SLTS as slts,\n" +
                            "(select tbl_username.U_FullName from tbl_username where tbl_username.U_ID = tbl_phandon.PD_NVnhan) as nvnhan,\n" +
                            "date_format(tbl_phandon.PD_Time,'%H:%i %d-%m-%Y') as tgphan,\n" +
                            "ifnull(date_format(tbl_phandon.PD_tra,'%H:%i %d-%m-%Y'),'') as tgtra, tbl_phandon.PD_TS as sltsphan\n" +
                            "from tbl_don\n" +
                            "inner join tbl_phandon on tbl_phandon.D_ID = tbl_don.D_ID\n" +
                            "where tbl_phandon.PD_tra is not null and tbl_don.D_isRemove = 0" +whereSql+
                            " order by tbl_don.D_ID desc limit "+((page-1)*10)+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                table +="<tr>";
                String thoigian = rs.getString("thoigiannhap");
                table +="<td>"+thoigian+"</td>";
                String maOnlineRS = rs.getString("dononline");
               // table +="<td >"+maOnlineRS+"</td>";
                String loaidk = rs.getString("loaidk");
                if(loaidk.equals("TT")){
                    table +="<td><a target='_blank' href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maOnlineRS+"</a></td>";
                }else{
                    table +="<td><a target='_blank' href=\"./print/print_mau2017.jsp?id="+id+"\">"+maOnlineRS+"</a></td>";
                }
                String maPin = rs.getString("mapin");
                table +="<td>"+maPin+"</td>";
                
                String manhanrs = rs.getString("manhan");
                String loainhanrs = rs.getString("loainhan");
                String maloainhanrs = new NhapDon().returnManhan(loaidk, manhanrs, loainhanrs,thoigian);
                //table +="<td><a href=\"./print/mau12.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                if(loaidk.equals("TT")){
                    table +="<td><a target='_blank' href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                }else{
                    table +="<td><a target='_blank' href=\"./print/print_mau2017.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                }
                String loaidonRS = rs.getString("loaidon"); 
                table +="<td>"+loaidonRS+"</td>";
                int slts = rs.getInt("slts");
                table +="<td>"+slts+"</td>";
                String tgphanD =rs.getString("tgphan");
                table +="<td>"+tgphanD+"</td>";
                String nvnhan = rs.getString("nvnhan");
                table +="<td>"+nvnhan+"</td>";
                String tgTra="";
                if(rs.getString("tgtra") != null){
                    tgTra=rs.getString("tgtra");
                }
                 table +="<td>"+tgTra+"</td>";
                int sltsphan = rs.getInt("sltsphan");
                table +="<td>"+sltsphan+"</td>";
                
                table +="</tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return table;
    }
    // search don da tra
    public String searchDDTra(String maonline,int loainhan,String manhan,String ngayphan,String nvNhan){
        String table = "";
        String sql = "select tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigiannhap,\n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline, \n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin,\n" +
                            "tbl_don.D_manhan as manhan , tbl_don.D_SLTS as slts,\n" +
                            "(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                            "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,"+
                            "tbl_username.U_FullName as nvnhan,\n" +
                            "tbl_phandon.PD_Time as tgphan,tbl_phandon.PD_tra as tgtra, tbl_phandon.PD_TS as sltsphan\n" +
                            "from tbl_don\n" +
                            "inner join (tbl_phandon inner join tbl_username on tbl_username.U_ID = tbl_phandon.PD_NVnhan) on tbl_don.D_ID = tbl_phandon.D_ID \n" +
                            "where tbl_phandon.PD_tra is not null and tbl_don.D_isRemove = 0 \n" +
                            "order by tbl_don.D_ID desc limit 0,15;";
        
        return table;
    }
     // lấy số trang đơn đã trả
    public int getPageDDT(String username,String ngayddt){
        int page = 0;
        String sql = "select count(DISTINCT tbl_don.D_ID) "+
                            "from tbl_don\n" +
                            "inner join (tbl_phandon inner join tbl_username on tbl_username.U_ID = tbl_phandon.PD_NVnhan) on tbl_don.D_ID = tbl_phandon.D_ID \n" +
                            "where tbl_phandon.PD_tra is not null and tbl_don.D_isRemove = 0 and tbl_phandon.PD_NVnhan = ? and tbl_don.D_Date = str_to_date(?,'%d-%m-%Y');";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, ngayddt);
            rs = pstm.executeQuery();
            while(rs.next()){
                page = rs.getInt(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (page/10)+1;
    }   
    
    // Lấy đơn id từ người nhập liệu
    
    public ArrayList<Integer> getDoniDPD(String username){
        ArrayList<Integer> data = new ArrayList<>();
        String sql = "";
        return data;
    }
    // Lấy page search đơn
    public int getSearchPageDDT(int page,String ngaygio,String maonline, int loainhan,String manhan
            ,int loaidon,int  loaidkInt,String nguoinhan){
        String whereSql = "";
        String subQuery = "";
        if(!ngaygio.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaygio+"','%d-%m-%Y')";
        }
        if(!maonline.trim().equals("")){
            String[] onlineS = maonline.split(",");
            if(onlineS.length >1){
                whereSql += " and tbl_don.D_MDO in ("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO LIKE '%"+maonline+"%'";
            }
        }
        if(!nguoinhan.trim().equals("all")){
            
            subQuery +=" and tbl_phandon.PD_NVnhan = '"+nguoinhan+"'";
        }
        
        if(loainhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loainhan; 
        }
        if(loaidon != 0){
     //       System.out.println("loaidonS : "+loaidon);
            whereSql += " and tbl_don.LD_ID = "+loaidon;
        }
        if(loaidkInt != 0){
     //       System.out.println("LOAIDK : "+loaidkInt);
            whereSql += " and  tbl_don.DK_ID = "+loaidkInt;
        }
        if(!manhan.trim().equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhan+"%'";
            }
        }
        String sql = "select count(*)\n" +
                            "from tbl_don\n" +
                            "where tbl_don.D_ID in(select tbl_phandon.D_ID from tbl_phandon where tbl_phandon.PD_tra is not null "+subQuery+" )  "+whereSql+";";
        //System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                page = rs.getInt(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (page/10)+1;
    }
    
    // lấy đơn id đơn đã trả
    public ArrayList<ArrayList<String>> getDonIDDaTra(String username,int id){
        ArrayList<ArrayList<String>> donList = new ArrayList<>();
        String whereSql = "";
        if(!username.equals("all")){
            whereSql +=" and tbl_phandon.PD_NVnhan = '"+username+"'";
        }
        String sql = "select tbl_phandon.PD_ID as pdid ,tbl_phandon.D_ID as donid\n" +
                            " ,tbl_phandon.PD_NVnhan as nvnhan, \n" +
                            " (select tbl_username.U_FullName from tbl_username where tbl_username.U_ID = tbl_phandon.PD_NVnhan) as fullname,\n" +
                            " date_format(tbl_phandon.PD_Time,'%H:%i %d-%m-%Y') as thoidiemphan,\n" +
                            "ifnull(date_format(tbl_phandon.PD_tra,'%H:%i %d-%m-%Y'),'') as thoidiemtra,tbl_phandon.PD_TS as tsphan\n" +
                            " from tbl_phandon " +
                    " where tbl_phandon.PD_tra is not null  and tbl_phandon.D_ID = ? "+whereSql+" order by tbl_phandon.PD_ID desc;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String donid = rs.getString("donid");
                String nvnhan = rs.getString("nvnhan");
                String fullname = rs.getString("fullname");
                String tgnhan = rs.getString("thoidiemphan");
                String tgtra = rs.getString("thoidiemtra");
                String slts  = rs.getString("tsphan");
                dt.add(donid);dt.add(fullname);dt.add(tgnhan);dt.add(tgtra);dt.add(slts);
                donList.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return donList;
    }
    
    // lấy dữ liệu đơn đã trả
    public ArrayList<ArrayList<String>> searchDonDT(int page,String ngaygio,String maonline, int loainhan,String manhan
            ,int loaidon,int  loaidkInt,String username){
        ArrayList<ArrayList<String>> data= new ArrayList<>();
        String whereSql = "";
        String subSql = "";
        if(!ngaygio.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+ngaygio+"','%d-%m-%Y')";
        }
        if(!maonline.trim().equals("")){
            String[] onlineS = maonline.split(",");
            if(onlineS.length >1){
                whereSql += " and tbl_don.D_MDO in ("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO LIKE '%"+maonline+"%'";
            }
        }
        
        if(loainhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loainhan; 
        }
        if(loaidon != 0){
     //       System.out.println("loaidonS : "+loaidon);
            whereSql += " and tbl_don.LD_ID = "+loaidon;
        }
        if(loaidkInt != 0){
     //       System.out.println("LOAIDK : "+loaidkInt);
            whereSql += " and  tbl_don.DK_ID = "+loaidkInt;
        }
        if(!manhan.trim().equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhan+"%'";
            }
        }
        if(!username.equals("all")){
            subSql += " and tbl_phandon.PD_NVnhan = '"+username+"'";
        }
        String sql ="select tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigiannhap,\n" +
                    "tbl_don.D_MDO as dononline,\n" +
                    "tbl_don.P_Pin as mapin,tbl_don.D_manhan as manhan ,\n" +
                    "(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,\n" +
                    "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                    "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                    "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon," +
                    " tbl_don.D_SLTS as slts\n" +
                    "from tbl_don\n" +
                "where tbl_don.D_ID in(select tbl_phandon.D_ID from tbl_phandon where tbl_phandon.PD_tra is not null "+subSql+" ) and tbl_don.D_isRemove = 0 "
                +whereSql+ "  order by tbl_don.D_ID desc limit "+(page-1)*10+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String donid = rs.getString("id");
                String ngaynhap = rs.getString("thoigiannhap");
                String maonliners = rs.getString("dononline");
                String mapin = rs.getString("mapin");
                String loaidk  = rs.getString("loaidk");
                String manhanrs = rs.getString("manhan");
                String loainhanrs = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhanrs, loainhanrs,ngaynhap);
                String slts = rs.getString("slts");
                String loaidonrs = rs.getString("loaidon");
                dt.add(ngaynhap);dt.add(maonliners);dt.add(mapin);dt.add(maloainhan);dt.add(slts);dt.add(loaidk);dt.add(loaidonrs);dt.add(donid);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // view table đơn đã trả
    public String viewTableDonDaTra(int page,String ngaygio,String maonline, int loainhan,String manhan
            ,int loaidon,int  loaidkInt,String nguoinhan){
        String table = "";
     //   ArrayList<Integer> donIDSearch = searchDonIDDT(page, ngaygio, maonline, loainhan, manhan, loaidon, loaidkInt);
        ArrayList<ArrayList<String>> donInfor = searchDonDT(page, ngaygio, maonline, loainhan, manhan, loaidon, loaidkInt,nguoinhan);
        for(ArrayList<String> don : donInfor){
                String ngaynhap = don.get(0);
                String dononline = don.get(1);
                String mapin = don.get(2);
                String maloainhan = don.get(3);
                String slts = don.get(4);
                String loaidk = don.get(5);
                String loaidonrs = don.get(6);
                String id = don.get(7);
                ArrayList<ArrayList<String>> donDTList = getDonIDDaTra(nguoinhan, Integer.parseInt(id));
                
                table +="<tr rowspan='"+donDTList.size()+"'>";
                table +="<td rowspan='"+donDTList.size()+"'>"+ngaynhap+"</td>";
                if(loaidk.equals("TT")){
                    table +="<td rowspan='"+donDTList.size()+"'><a target='_blank' href=\"./print/print_cctt2017.jsp?id="+id+"\">"+dononline+"</a></td>";
                }else{
                    table +="<td rowspan='"+donDTList.size()+"'><a target='_blank' href=\"./print/print_mau2017.jsp?id="+id+"\">"+dononline+"</a></td>";
                }
                table +="<td rowspan='"+donDTList.size()+"'>"+mapin+"</td>";

                //table +="<td><a href=\"./print/mau12.jsp?id="+id+"\">"+maloainhanrs+"</a></td>";
                if(loaidk.equals("TT")){
                    table +="<td rowspan='"+donDTList.size()+"'><a target='_blank' href=\"./print/print_cctt2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }else{
                    table +="<td rowspan='"+donDTList.size()+"'><a target='_blank' href=\"./print/print_mau2017.jsp?id="+id+"\">"+maloainhan+"</a></td>";
                }
                table +="<td rowspan='"+donDTList.size()+"'>"+loaidonrs+"</td>";
                table +="<td rowspan='"+donDTList.size()+"'>"+slts+"</td>";
                int stt = 1;
                for(ArrayList<String> donphan : donDTList){
                  //  String donid = dondt.get(0);
                    String fullname = donphan.get(1);
                    String tgnhan = donphan.get(2);
                    String tgtra = donphan.get(3);
                    String tsphan = donphan.get(4);
                    if(stt ==1){
                        table +="<td rowspan='1' >"+tgnhan+"</td>";
                        table +="<td rowspan='1' >"+fullname+"</td>";
                         table +="<td rowspan='1' >"+tgtra+"</td>";
                        table +="<td rowspan='1' >"+tsphan+"</td>";
                        table += "</tr>";
                    }else{
                        table += "<tr>";
                        table +="<td rowspan='1' >"+tgnhan+"</td>";
                        table +="<td rowspan='1' >"+fullname+"</td>";
                         table +="<td rowspan='1' >"+tgtra+"</td>";
                        table +="<td rowspan='1' >"+tsphan+"</td>";
                        table += "</tr>";
                    }
                    
                    
                    stt++;
                }
                

                table +="</tr>";
        }
       // ArrayList<ArrayList<String>> donDTList = getDonIDDaTra(nguoinhan, page);
        
        return table;
    }
}
