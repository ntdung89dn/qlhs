/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import com.ttdk.connect.DBConnect;
import com.ttdk.createFile.ChangeNumberToText;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thorfinn
 */
public class Thuphi {
    private ArrayList<String> list;
    private KhachhangBean khbean = new KhachhangBean();
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    private ArrayList<ArrayList<String>> data;
    ChangeNumberToText changeNumber ;
    
    //load khách hàng id
    public ArrayList<Integer> loadKHID(String name){
        ArrayList<Integer> data = new ArrayList<>();
        String sql = "select tbl_khachhang.KH_ID from tbl_khachhang where tbl_khachhang.KH_Name like ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, "%"+name+"%");
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    // select Bên thu phí chưa thu phí 
    public ArrayList<ThuPhiBean> loadBTP_chuaTP(String fromday,String endday,String manhan,String sothongbaophi,int loaidon,String bnbd){
        String whereSql = "";
        if(!fromday.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+fromday+"','%d-%m-%Y') ";
        }
        
        if(!endday.trim().equals("") ){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+endday+"','%d-%m-%Y') ";
        }
        
        if(!manhan.trim().equals("") ){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhan+"%'";
            }
        }
        
        if(!sothongbaophi.trim().equals("") ){
            whereSql += " and tbl_don.D_manhan like '%"+sothongbaophi+"%' ";
        }
        if(!bnbd.trim().equals("") ){
            whereSql += " and tbl_khachhang.KH_Name  like '%"+bnbd+"%' ";
        }
        if(loaidon !=0){
            whereSql += " and tbl_don.LD_ID = "+loaidon+" ";
        }
        ArrayList<ThuPhiBean> tpbArr = new ArrayList<>();
        String sql = " select tbl_don.KH_ID as khID,tbl_khachhang.KH_Name as bnbd,tbl_khachhang.KH_Address  as KHAddress,\n" +
                            " tbl_khachhang.KH_Account as KHAccount,SUM((select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID))  as tongtien,"
                            + "GROUP_CONCAT(tbl_don.D_ID SEPARATOR ',') as donids\n" +
                            " from tbl_don\n" +
                            " inner join tbl_khachhang  on tbl_khachhang.KH_ID = tbl_don.KH_ID \n" +
                            "  left join tbl_dondatp on tbl_dondatp.D_ID = tbl_don.D_ID "+
                            " where tbl_dondatp.D_ID is null and tbl_don.D_isRemove = 0 \n" +whereSql+
                            " group by tbl_don.KH_ID order by tbl_khachhang.KH_ID asc;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ThuPhiBean tpb = new ThuPhiBean();
                tpb.setKhID(rs.getInt("khID"));
                tpb.setBnbd(rs.getString("bnbd"));
                tpb.setTongtien(rs.getDouble("tongtien"));
                tpb.setAccount(rs.getString("KHAccount")); 
                tpb.setDonids(rs.getString("donids"));
                tpb.setAddress(rs.getString("KHAddress")); 
                tpbArr.add(tpb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tpbArr;
    }
    
    // select view table BNBD chưa thu phí
    public String returnTableBNBD_chuaTP(String fromday,String endday,String manhan,String sothongbaophi,int loaidon,String bnbd){
        String table = "";
        ArrayList<ThuPhiBean> listTpb = loadBTP_chuaTP(fromday, endday, manhan,sothongbaophi,loaidon, bnbd);
        NumberFormat nf = new DecimalFormat("#");
        for(int i = 0;i < listTpb.size();i++){
            ThuPhiBean tpb = listTpb.get(i);
            table += "<tr>";
            table += "<td>"+(i+1)+"</td>";
            table += "<td class='click_td'>"+tpb.getBnbd()+"("+tpb.getAccount()+")<input type='text' id='ctp_donid"+i+"' value='"+tpb.getDonids()+"' hidden>"
                    + "<input type='text' id='ctp_khid"+i+"' value='"+tpb.getKhID()+"' hidden><input type='text' id='ctp_infor"+i+"' value='"+tpb.getBnbd()+"_"+tpb.getAddress()+"' hidden></td>";
             table += "<td><input type='checkbox' id='ctp_cb"+i+"' value='"+nf.format(tpb.getTongtien())+"' ></td>";
            table += "</tr>";
        }
        return table;
    }
    public ArrayList<ThuPhiBean> loadBBD_chuaTP(String donids){
        ArrayList<ThuPhiBean> tpbArr = new ArrayList<>();

        String sql = "select tbl_don.D_ID as donid,DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap, ifnull(tbl_don.D_MDO,'') as dononline,"
                + "ifnull(group_concat(tbl_benbaodam.BDB_Name  SEPARATOR ','),'') as benbaodam,tbl_don.DK_ID as loaidk,\n" +
        "(select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID)  as tongtien \n" +
        "from tbl_don \n"
                    + "left join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID \n" +
            "where  tbl_don.D_ID in( "+donids+") group by tbl_don.D_ID ;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                String ngaynhap = rs.getString("ngaynhap");
                String manhan = rs.getString("dononline");
                if(manhan.equals("0")){
                    manhan = "";
                }
                ThuPhiBean tpb = new ThuPhiBean();
                tpb.setBenbaodam(rs.getString("benbaodam"));
                tpb.setTongtien(rs.getDouble("tongtien"));
                tpb.setDonids(rs.getString("donid"));
                int loaidk = rs.getInt("loaidk");
                if(loaidk ==3){
                    tpb.setIsPhuluc(true);
                }else{
                    tpb.setIsPhuluc(false);
                }
                tpb.setManhan(manhan);             
                tpb.setNgaynhap(ngaynhap);
                tpbArr.add(tpb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tpbArr;
    }
    
    public String returnBBD_CTP(String fromday,String endday,String donids){
        String table = "";
        ArrayList<ThuPhiBean> listTpb = loadBBD_chuaTP(donids);
        changeNumber = new ChangeNumberToText();
        for(int i = 0;i < listTpb.size();i++){
            ThuPhiBean tpb = listTpb.get(i);
            table += "<tr>";
            table += "<td>"+(i+1)+"</td>";
            table += "<td>"+tpb.getManhan()+"</td>";
            table += "<td>"+tpb.getNgaynhap()+"</td>";
            String phulucStr = "";
            if(tpb.isIsPhuluc()){
                phulucStr += "<span style='color:red'>(PL 04)</span>";
            }
            table += "<td>"+tpb.getBenbaodam()+" "+phulucStr+"<input type='text' id='ctp_donid"+i+"' value='"+tpb.getDonids()+"' hidden></td>";
             table += "<td><input class='input_st' type='text' value=''></td>"; 
             Double total = tpb.getTongtien();
             int totalInt = total.intValue();
             String totalStr = changeNumber.priceWithDecimal(total);
             table += "<td>"+totalStr+"</td>";
             table += "<td><input type='checkbox' id='ctp_cb"+i+"' class='bbdcheckbox' value='"+totalInt+"' ></td>";
            table += "</tr>";
        }
        return table;
    }
    
    
    public int insertThuPhi(String ngaynop,String sohieu,String tendonvi,String diachi,String lydo, int loaithanhtoan,int total, Connection conn){
        int key = -1;
        String sql = "insert into tbl_donTP(DTP_TDV,DTP_TDVDC,DTP_Sohieu,DTP_Lydo,DTP_Date,LTT_ID,DTP_Total) \n" +
                            " values(?,?,?,?,str_to_date(?,'%d-%m-%Y'),?,?);";
     //   connect = new DBConnect().dbConnect();
        try {
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, tendonvi);
            pstm.setString(2, diachi);
            pstm.setString(3, sohieu);
            pstm.setString(4, lydo);
            pstm.setString(5, ngaynop);
            pstm.setInt(6, loaithanhtoan);
            pstm.setInt(7, total);
            int affect = pstm.executeUpdate();
            if(affect !=0){
                rs = pstm.getGeneratedKeys();
                if(rs.next()){
                    key = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }

    public int insertDonTP(int dtpid, int donid,Connection conn){
        int key = 0;
        String sql = "insert into tbl_dondatp(D_ID,DTP_ID) \n" +
                            " values(?,?);";
      //  connect = new DBConnect().dbConnect();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, donid);
            pstm.setInt(2, dtpid);
            key = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, null);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
   // Chức năng bên thu phí 
    public ArrayList<ArrayList<String>> loadBTP(String ngaybatdau,String ngayketthuc,int loaihinhnhan,String manhan,int loaidk,String sohieu,String bennopphi){
        ArrayList<ArrayList<String>>  bdtpList= new ArrayList<>();
         String whereSql = "";
         boolean isJoin = false;
         String joinSql = "";
         if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         
         if(loaihinhnhan != 0){
             whereSql += " and  tbl_don.LN_ID = "+loaihinhnhan;
             isJoin = true;
         }
         if(loaihinhnhan != 0){
             whereSql += " and tbl_don.DK_ID = "+loaidk;
             isJoin = true;
         }
         if(!manhan.trim().equals("") ){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhan+"%'";
            }
            isJoin = true;
        }
         if(!sohieu.trim().equals("")){
             String[] sohieus = sohieu.split(",");
            if(sohieus.length >1){
                whereSql += " and tbl_dontp.DTP_Sohieu in("+sohieu+")";
            }else{
                whereSql += " and tbl_dontp.DTP_Sohieu like '%"+sohieu+"%'";
            }
         }
         if(isJoin){
             joinSql += " inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID ";
         }
         if(!bennopphi.equals("")){
             whereSql += " and tbl_dontp.DTP_TDV like '%"+bennopphi+"%'";
         }
        String sql = "select tbl_dontp.DTP_TDV as bnp ,tbl_dontp.DTP_TDVDC,tbl_dontp.DTP_Total as total,"
                + "group_concat(distinct tbl_dontp.DTP_ID) as dtpids \n" +
                        "from tbl_dondatp \n" +
                        "inner join tbl_dontp on tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID "+joinSql
                    + " where 1=1" + whereSql
                            + " group by tbl_dontp.DTP_TDV,tbl_dontp.DTP_TDVDC order by tbl_dontp.DTP_TDV desc;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        //String table = "";
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString(1));
                dt.add(rs.getString(2));
                dt.add(rs.getString(4));
                dt.add(rs.getString(3));
                bdtpList.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bdtpList;
    }
    
    // lấy tổng tiền theo đơn idsloadDTPIDS
    public String getTongTien(String bienlais){
        String tongtien = "";
        String sql = "select sum(tbl_dontp.DTP_Total) as total\n" +
                                "from tbl_dontp \n" +
                                " where tbl_dontp.DTP_ID in(?);";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, bienlais);
            rs= pstm.executeQuery();
            while(rs.next()){
                tongtien = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tongtien;
    }
    
    // Lấy đơn id theo ten va dia chi nguoi nop phi
    public String getDonidsTP(String bienlais){
        String donids = "";
        String sql = "select tbl_dondatp.D_ID as bnp\n" +
                            "from tbl_dondatp \n" +
                            "inner join tbl_dontp on tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID  \n" +
                            "where tbl_dontp.DTP_ID in(?) \n" +
                            "order by tbl_dondatp.D_ID desc;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, bienlais);
            rs= pstm.executeQuery();
            while(rs.next()){
                if(donids.length() ==0){
                    donids += rs.getString(1);
                }else{
                    donids += ","+rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return donids;
    }
    
    // tra ve ben da thu phi
    public String returnBenDTP(String ngaybatdau,String ngayketthuc,int loaihinhnhan,String manhan,int loaidk,String sohieu,String bennopphi){
        String table = "";
        ArrayList<ArrayList<String>> bnbdList = loadBTP(ngaybatdau, ngayketthuc, loaihinhnhan, manhan, loaidk, sohieu,bennopphi);
        //ArrayList<ArrayList<String>> donList = loadBTP(ngaybatdau, ngayketthuc, loaihinhnhan, manhan, loaidk, sohieu);
        //String insidevalue = ngaynhap+"_"+sohieurs+"_"+bnp+"_"+diachi+"_"+lydo+"_"+loaithanhtoan+"_"+total+"_"+donids+"_"+dtpid;
        //dt.add(ngaynhap);dt.add(sohieurs);dt.add(bnp);dt.add(diachi);dt.add(lydo);dt.add(loaithanhtoan);dt.add(donids);dt.add(dtpid);
        int stt = 1;
        for(ArrayList<String> bnbd : bnbdList){
         //   String insidevalue = dt.get(0)+"_"+dt.get(1)+"_"+dt.get(2)+"_"+dt.get(3)+"_"+dt.get(4)+"_"+dt.get(5)+"_"+getTongTien(dt.get(6))+"_"+dt.get(6)+"_"+dt.get(7);
          //  String donids = getDonidsTP(bnbd.get(0), bnbd.get(1));
          //  String tongtien = getTongTien(donids);
          String blids = "";
          if(bnbd.get(3).length() >1023){
              blids = getDonidsTP(bnbd.get(3));
          }else{
              blids = bnbd.get(2);
          }
         // String value = bnbd.get(0) +"_"+bnbd.get(1);
            table += "<tr>";
            table += "<td>"+stt+"</td>";
            table += "<td class='click_td'>"+bnbd.get(0)+"<input type='text' class='bien_lais' value='"+blids+"' hidden></td>";
            table += "<td><input type='checkbox'  class='btpcheckbox' value='"+bnbd.get(3)+"'></td>";
            stt++;
            table += "</tr>";
            System.out.println(stt);
        }
        return table;
    }
    
    public ArrayList<ArrayList<String>> getSoHieu(String bienlais){
        ArrayList<ArrayList<String>> data1 = new ArrayList<>();
        String sql = "select tbl_dontp.DTP_ID, tbl_dontp.DTP_Sohieu \n" +
                            "from tbl_dontp \n" +
                            "where tbl_dontp.DTP_ID in("+bienlais+") \n" +
                            "order by tbl_dontp.DTP_ID desc;";
        System.out.println(sql);
        System.out.println(bienlais);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs= pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt =  new ArrayList<>();
                dt.add(rs.getString(1));dt.add(rs.getString(2));
                data1.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data1;
    }
    
    // load ben bao dam theo so hieu
    public String getBBDNopPhi(String dontpid){
        String table = "";
        String sql = "select distinct tbl_don.D_ID as donid,DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap,\n" +
                            "(select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID)  as tongtien,tbl_don.D_MDO as dononline,\n" +
                            "(select group_concat( tbl_benbaodam.BDB_Name separator ' & ') from tbl_benbaodam where tbl_benbaodam.D_ID= tbl_dondatp.D_ID) as benbaodam,\n" +
                            "(select tbl_dontp.DTP_Sohieu from tbl_dontp where tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID ) as sohieu\n" +
                            ",tbl_don.DK_ID as loaidk \n" +
                            "from tbl_dondatp\n" +
                            "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                            "where tbl_dondatp.DTP_ID in(?)\n" +
                            " group by tbl_dondatp.D_ID order by tbl_dondatp.D_ID desc;";
        System.out.println(sql);
       // System.out.println(sohieu);
        connect = new DBConnect().dbConnect();
        changeNumber = new ChangeNumberToText();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, dontpid);
            rs = pstm.executeQuery();
            int i=1;
             while(rs.next()){
                 int donid = rs.getInt("donid");
                 table += "<tr>";
                 String ngaynhap = rs.getString("ngaynhap");
                 String PL = "";
                 int loaidk = rs.getInt("loaidk");
                 if(loaidk == 3){
                     PL = "<span style='color:red;'>(PL04)</span>";
                 }
                 String manhan = rs.getString("dononline");
                 Double tongtien = rs.getDouble("tongtien");
                 int total = tongtien.intValue();
                 String totalStr = changeNumber.priceWithDecimal(tongtien);
                 table += "<td>"+i+"</td>";
                 table += "<td>"+manhan+"</td>";
                  table += "<td>"+ngaynhap+"</td>";
                 table += "<td>"+rs.getString("benbaodam")+" "+PL+"</td>";
                 table += "<td>"+totalStr+"</td>";
                 table += "<td><input type='checkbox' class='ctp_cb' value='"+total+"' checked></td>";
                 table += "<td><img src='./images/clear_btn.png' class='remove_dontp' id='"+donid+"'></td>";
                
                 table += "</tr>";
                 i++;
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    // lấy đơn đã nộp phí
    public String getDonNopPhi(int  dtpid){
        String table = "";
        String sql = "select distinct tbl_don.D_ID as donid,DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap,"
                + "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                            "tbl_don.D_manhan as manhan,\n" +
                            " (select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                            "(select group_concat(`BDB_Name` separator ' & ')\n" +
                            "as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) as benbaodam,\n" +
                            "tbl_dontp.DTP_Sohieu,(select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID) as tongtien\n" +
                            "from tbl_dondatp\n" +
                            "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                            "inner join tbl_dontp on tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID\n" +
                            " where tbl_dontp.DTP_ID = ?;";
       // System.out.println(sql);
       // System.out.println(sohieu);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, dtpid);
            rs = pstm.executeQuery();
            int i=1;
             while(rs.next()){
                 int donid = rs.getInt("donid");
                 table += "<tr>";
                 String ngaynhap = rs.getString("ngaynhap");
                 String loainhanrs = rs.getString("loainhan");
                 String manhanrs = rs.getString("manhan");
                 String loaidk = rs.getString("loaidk");
                 String manhan = new NhapDon().returnManhan(loaidk, manhanrs, loainhanrs, ngaynhap);
                 Double tongtien = rs.getDouble("tongtien");
                 int total = tongtien.intValue();
                 table += "<td>"+i+"</td>";
                 table += "<td>"+manhan+"</td>";
                  table += "<td>"+ngaynhap+"</td>";
                 table += "<td>"+rs.getString("benbaodam")+"</td>";
                 table += "<td>"+total+"</td>";
                 table += "<td><input type='checkbox' checked></td>";
                 table += "<td><img src='./images/clear_btn.png' class='remove_dontp' id='"+donid+"'></td>";
                
                 table += "</tr>";
                 i++;
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    public ThuPhiBean getTPTheoSohieu(int dtpid){
        ThuPhiBean tpb = new ThuPhiBean();
        String sql = "select DATE_FORMAT(tbl_dontp.DTP_Date,'%d-%m-%Y') as ngaytp,tbl_dontp.DTP_Sohieu as sohieu,\n" +
                            " tbl_dontp.DTP_TDV as tdv,  tbl_dontp.DTP_TDVDC as diachi, tbl_dontp.DTP_Lydo as lydo,\n" +
                            " tbl_dontp.LTT_ID as loaitt,\n" +
                            " group_concat( tbl_dondatp.D_ID SEPARATOR ',')  as donids, tbl_dontp.DTP_Total as tongtien\n" +
                            "from tbl_dondatp\n" +
                            "inner join tbl_dontp on tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID\n" +
                            " where tbl_dontp.DTP_ID = ?;";
       // System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, dtpid);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                System.out.println(" ngaytp"+rs.getString("ngaytp"));
                String ngaytp = rs.getString("ngaytp");
                String sohieurs = rs.getString("sohieu");
                String tdv = rs.getString("tdv");
                String diachi = rs.getString("diachi");
                String lydo = rs.getString("lydo");
                int loaitt = rs.getInt("loaitt");
                String donids = rs.getString("donids");
                Double tongtien = rs.getDouble("tongtien");
                tpb.setNgaytp(ngaytp);
                tpb.setSohieu(sohieurs);
                tpb.setTendonvi(tdv);
                tpb.setTdvdiachi(diachi);
                tpb.setLydo(lydo);
                tpb.setLoaitt(loaitt);
                tpb.setTongtien(tongtien);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tpb;
    }
    
    // SQl  báo có
    
    // select báo có
    public BaoCoBean viewBaoCo(int dontpid){
        BaoCoBean bcb = new BaoCoBean();
        String sql="select distinct BC_ID as bcid,DATE_FORMAT(BC_Date,'%d-%m-%Y') as ngaybc,BC_Number as bcnumber,BC_Total as sotien,"
                + "BC_Thua as tienthua,BC_ND as noidung \n" +
                        "from tbl_baoco"
                +" inner join (tbl_thanhtoanbienlai inner join tbl_donTP on tbl_thanhtoanbienlai.DTP_ID =  tbl_donTP.DTP_ID )"
                + " on tbl_thanhtoanbienlai.BC_ID =  tbl_baoco.BC_ID "
                + " where DTP_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, dontpid);
            rs = pstm.executeQuery();
            while(rs.next()){
                int bcid = rs.getInt("bcid");
                String ngaybc = rs.getString("ngaybc");
                String bcnumber = rs.getString("bcnumber");
                int sotien = rs.getInt("sotien");
                int tienthua = rs.getInt("tienthua");
                String noidung = rs.getString("noidung");
                bcb.setBcid(bcid);
                bcb.setNgaybc(ngaybc);
                bcb.setBcnumber(bcnumber);
                bcb.setSotien(sotien);
                bcb.setTienthua(tienthua);
                bcb.setNoidung(noidung);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bcb;
    }
    // insert báo có
    public int insertBaoCo(String ngaybaoco,String sobaoco,int sotien,int tienthua,String noidungbc,String nguoiplbaoco,Connection conn){
        int returnRow = 0;
        String sql = "insert into tbl_baoco(BC_Date,BC_Number,BC_Total,BC_Thua,BC_ND,BC_Nguoiphatlenh)"
                + " values(str_to_date(?,'%d-%m-%Y'),?,?,?,?,?); ";
       // connect = new DBConnect().dbConnect();
        try {
            pstm = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, ngaybaoco);
            pstm.setString(2, sobaoco);
            pstm.setInt(3, sotien);
            pstm.setInt(4, tienthua);
            pstm.setString(5, noidungbc);
            pstm.setString(6, nguoiplbaoco);
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            while(rs.next()){
                returnRow = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return returnRow;
    }
    // lưu báo có với khách hàng id
    public int insertKHIDBC(int khid,int bcid,Connection conn){
        int result = 0;
        String sql = "insert into tbl_baocokh(BC_ID,KH_ID) values(?,?)";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, bcid);
            pstm.setInt(2, khid);
            result = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    // check báo có
    public boolean checkBaoCo(int dontpid){
        boolean check = false;
        String sql = "select 1 from tbl_baoco where DTP_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, dontpid);
            rs = pstm.executeQuery();
            if(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    
    // Thống kê biên lai
    
    // Lấy tổng số trang thống kê biên lai tìm kiếm
    public int getPageSearchTKBL(int page,String ngaybatdau,String ngayketthuc,String sobienlai,int loaitt){
        int currentpage = 0 ;
        String whereSql ="";
        if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         if(!sobienlai.trim().equals("")){
             String[] sohieus = sobienlai.split(",");
            if(sohieus.length >1){
                whereSql += " and tbl_dontp.DTP_Sohieu in("+sobienlai+")";
            }else{
                whereSql += " and tbl_dontp.DTP_Sohieu like '%"+sobienlai+"%'";
            }
         }
         if(loaitt != 0){
              whereSql += " and tbl_dontp.DTP_Sohieu like '%"+loaitt+"%'";
         }
        String sql = "select count(distinct tbl_dondatp.D_ID)\n" +
                    "from tbl_dondatp\n" +
                    "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                    "inner join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                    "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                    " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                    "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID) on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID "
                + whereSql+" limit "+(page-1)*10+","+((page-1)*10+10)+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {                
                currentpage = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if((currentpage%10)==0) currentpage = currentpage /10;
        else currentpage =(currentpage/10)+1;
        return currentpage;
    }
    // Lấy tổng số trang thống kê biên lai tìm kiếm
    public int getPageTKBL(){
        int currentpage = 0 ;
        
        String sql = "select count(distinct tbl_dondatp.D_ID)\n" +
                        "from tbl_dondatp\n" +
                        "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                        "inner join ( tbl_dontp inner join tbl_baoco on tbl_baoco.DTP_ID = tbl_baoco.DTP_ID) on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {                
                currentpage = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if((currentpage%10)==0) currentpage = currentpage /10;
        else currentpage =(currentpage/10)+1;
        return currentpage;
    }
     // view thống kê biên lai tìm kiếm
    public String viewThongkeBienlai(int page){
        String table = "";
        
        String sql = "select  DATE_FORMAT(tbl_dontp.DTP_Date,'%d-%m-%Y') as ngaynop,tbl_dontp.DTP_Sohieu as sohieu,\n" +
                            "DATE_FORMAT(tbl_baoco.BC_Date,'%d-%m-%Y') as ngaybaoco, tbl_baoco.BC_Number as sobaoco,\n" +
               "(select tbl_khachhang.KH_Account from tbl_khachhang where"
                + " tbl_khachhang.KH_ID = tbl_don.KH_ID and tbl_khachhang.KH_Name like tbl_dontp.DTP_TDV ) as taikhoan,"+
                            "tbl_baoco.BC_Total as sotienbc,tbl_baoco.BC_ND as noidung, tbl_dontp.DTP_TDV as bennp,\n" +
                            "tbl_dontp.DTP_Lydo as lydo, ifnull(tbl_dontp.DTP_Total,0) as tongtien,\n" +
                            "ifnull((select SUM( tbl_lephi.LP_Price) from tbl_don \n" +
                            "inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                            "inner join tbl_dondatp on tbl_dondatp.D_ID = tbl_don.D_ID\n" +
                            "where tbl_don.LD_ID in (1,2,3,4,7,8,9,10,11,12,13) and tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID group by tbl_dondatp.DTP_ID),0) as phiBD,\n" +
                            "ifnull((select SUM( tbl_lephi.LP_Price) from tbl_don \n" +
                            "inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                            "inner join tbl_dondatp on tbl_dondatp.D_ID = tbl_don.D_ID\n" +
                            "where tbl_don.LD_ID = 5 and tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID group by tbl_dondatp.DTP_ID),0) as phiBS,\n" +
                            "ifnull((select SUM( tbl_lephi.LP_Price) from tbl_don \n" +
                            "inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                            "inner join tbl_dondatp on tbl_dondatp.D_ID = tbl_don.D_ID\n" +
                            "where tbl_don.LD_ID = 6 and tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID group by tbl_dondatp.DTP_ID),0) as phiTT,\n" +
                            "group_concat(DISTINCT tbl_dontp.DTP_Sohieu SEPARATOR  ',') as sohieu,\n" +
                            " group_concat(DISTINCT tbl_don.D_ID SEPARATOR  ',') as donids\n" +
                            "from tbl_dondatp\n" +
                            "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                            "inner join ( tbl_dontp inner join tbl_baoco on tbl_baoco.DTP_ID = tbl_baoco.DTP_ID) \n" +
                            "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID group by tbl_dondatp.DTP_ID order by tbl_dondatp.DTP_ID desc"
                + " limit "+(page-1)*10+","+((page-1)*10+10)+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i=1;
            while(rs.next()){
                String ngaynop = rs.getString("ngaynop");
                String sohieu = rs.getString("sohieu");
                String ngaybc = rs.getString("ngaybaoco");
                String sobc = rs.getString("sobaoco");
                int sotienbc = rs.getInt("sotienbc");
                String noidung = rs.getString("noidung");
                String tendv = rs.getString("bennp");
                String lydo = rs.getString("lydo");
                int tongtien = rs.getInt("tongtien");
                int phiBD = rs.getInt("phiBD");
                int phiBS = rs.getInt("phiBS");
                int phiTT = rs.getInt("phiTT");
                String taikhoan = rs.getString("taikhoan");
             //   String donids = rs.getString("donids");
            //    System.out.println(donids);
                table += "<tr>";
                table += "<td>"+i+"</td>";
                 table += "<td>"+ngaynop+"</td>";
                 table += "<td>"+sohieu+"</td>";
                 table += "<td>"+ngaybc+"</td>";
                 table += "<td>"+sobc+"</td>";
                 table += "<td>"+sotienbc+"</td>";
                 table += "<td>"+noidung+"</td>";
                 table += "<td>"+taikhoan+"</td>";
                 table += "<td>"+tendv+"</td>";
                 table += "<td>"+lydo+"</td>";
                 table += "<td>"+tongtien+"</td>";
                 table += "<td>"+phiBD+"</td>";
                 table += "<td>"+phiTT+"</td>";
                 table += "<td>"+phiBS+"</td>";
                 table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+sohieu+")'></td>";
                table += "</tr>";
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    // view thống kê biên lai tìm kiếm
    public String viewSearchThongkeBienlai(int page,String ngaybatdau,String ngayketthuc,String sobienlai,int loaitt){
        String table = "";
        String whereSql ="";
        if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         if(!sobienlai.trim().equals("")){
             String[] sohieus = sobienlai.split(",");
            if(sohieus.length >1){
                whereSql += " and tbl_dontp.DTP_Sohieu in("+sobienlai+")";
            }else{
                whereSql += " and tbl_dontp.DTP_Sohieu like '%"+sobienlai+"%'";
            }
         }
         if(loaitt != 0){
              whereSql += " and tbl_dontp.DTP_Sohieu like '%"+loaitt+"%'";
         }
        String sql = "select  DATE_FORMAT(tbl_dontp.DTP_Date,'%d-%m-%Y') as ngaynop,ifnull(tbl_dontp.DTP_Sohieu,'') as sohieu,\n" +
                        "ifnull(DATE_FORMAT(tbl_baoco.BC_Date,'%d-%m-%Y'),'') as ngaybaoco, ifnull(tbl_baoco.BC_Number,'') as sobaoco,\n" +
                        "ifnull((select tbl_khachhang.KH_Account from tbl_khachhang where"
                + " tbl_khachhang.KH_ID = tbl_don.KH_ID and tbl_khachhang.KH_Name like tbl_dontp.DTP_TDV ),'') as taikhoan,"+
                        "ifnull(tbl_baoco.BC_Total,0) as sotienbc,ifnull(tbl_baoco.BC_ND,'') as noidung, tbl_dontp.DTP_TDV as bennp,\n" +
                        "tbl_dontp.DTP_Lydo as lydo, ifnull(tbl_dontp.DTP_Total,0) as tongtien,\n" +
                        "ifnull((select SUM( tbl_lephi.LP_Price) from tbl_don \n" +
                        "inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                        "inner join tbl_dondatp on tbl_dondatp.D_ID = tbl_don.D_ID\n" +
                        "where tbl_don.LD_ID in (1,2,3,4,7,8,9,10,11,12,13) and tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID group by tbl_dondatp.DTP_ID),0) as phiBD,\n" +
                        "ifnull((select SUM( tbl_lephi.LP_Price) from tbl_don \n" +
                        "inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                        "inner join tbl_dondatp on tbl_dondatp.D_ID = tbl_don.D_ID\n" +
                        "where tbl_don.LD_ID = 6 and tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID group by tbl_dondatp.DTP_ID),0) as phiBS,\n" +
                        "ifnull((select SUM( tbl_lephi.LP_Price) from tbl_don \n" +
                        "inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                        "inner join tbl_dondatp on tbl_dondatp.D_ID = tbl_don.D_ID\n" +
                        "where tbl_don.LD_ID = 5 and tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID group by tbl_dondatp.DTP_ID),0) as phiTT,\n" +
                        "group_concat(DISTINCT tbl_dontp.DTP_Sohieu SEPARATOR  ',') as sohieu,\n" +
                        " group_concat(DISTINCT tbl_don.D_ID SEPARATOR  ',') as donids\n" +
                        "from tbl_dondatp\n" +
                        "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                        "inner join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                        "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                        " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                        "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID) \n" +
                        "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID  where 1=1 "
                + whereSql+"group by tbl_dondatp.DTP_ID limit "+(page-1)*10+",10;";
        System.out.println(sql);
        ChangeNumberToText changeNumber = new ChangeNumberToText();
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i = 1;
            while(rs.next()){
                
                String ngaynop = rs.getString("ngaynop");
                if(ngaynop != null){
                    String sohieu = rs.getString("sohieu");
                    String ngaybc = rs.getString("ngaybaoco");
                    String sobc = rs.getString("sobaoco");
                    Double sotienbc = rs.getDouble("sotienbc");
                    String noidung = rs.getString("noidung");
                    String tendv = rs.getString("bennp");
                    String lydo = rs.getString("lydo");
                    Double tongtien = rs.getDouble("tongtien");
                    Double phiBD = rs.getDouble("phiBD");
                    Double phiBS = rs.getDouble("phiBS");
                    Double phiTT = rs.getDouble("phiTT");
                    String taikhoan = rs.getString("taikhoan");
                    //String donids = rs.getString("donids");
                    table += "<tr>";
                    table += "<td>"+i+"</td>";
                     table += "<td>"+ngaynop+"</td>";
                     table += "<td>"+sohieu+"</td>";
                     table += "<td>"+ngaybc+"</td>";
                     table += "<td>"+sobc+"</td>";
                     table += "<td>"+ changeNumber.priceWithDecimal(sotienbc)+"</td>";
                     table += "<td>"+noidung+"</td>";
                     table += "<td>"+taikhoan+"</td>";
                     table += "<td>"+tendv+"</td>";
                     table += "<td>"+lydo+"</td>";
                     table += "<td>"+changeNumber.priceWithDecimal(tongtien)+"</td>";
                     table += "<td>"+changeNumber.priceWithDecimal(phiBD)+"</td>";
                     table += "<td>"+changeNumber.priceWithDecimal(phiTT)+"</td>";
                     table += "<td>"+changeNumber.priceWithDecimal(phiBS)+"</td>";
                     table += "<td><img src='./images/document_view.png' onclick='return viewChiTiet("+sohieu+");'></td>";
                    table += "</tr>";
                    i++;
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    // view chi tiết đơn
    public String viewChitetBL(String sohieu){
        String table = "";
        String sql = "select (select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                    "tbl_don.D_manhan as manhan,\n" +
                    "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                    "DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap,\n" +
                    "(select group_concat(`BDB_Name` separator ' &')\n" +
                    "as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) as benbaodam,\n" +
                    "(select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID) as lephi\n" +
                    "from tbl_dondatp  \n" +
                    "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                    "inner join tbl_dontp on tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID\n" +
                    "where tbl_dontp.DTP_Sohieu = ?;";
         connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, sohieu);
            rs = pstm.executeQuery();
            int i=1;
            while(rs.next()){
                String loainhan = rs.getString("loainhan");
                String manhan = rs.getString("manhan");
                String loaidk = rs.getString("loaidk");
                String ngaynhap = rs.getString("ngaynhap");
                String benbaodam = rs.getString("benbaodam");
                int lephi = rs.getInt("lephi");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, ngaynhap);
                table += "<tr>";
                table += "<td>"+i+"</td>";
                table += "<td>"+maloainhan+"</td>";
                table += "<td>"+ngaynhap+"</td>";
                table += "<td>"+benbaodam+"</td>";
                table += "<td>"+lephi+"</td>";
                table += "</tr>";
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
         
        return table;
    }
    // Lấy tổng tiền biên lai
    public Double getTongTien(String ngaybatdau,String ngayketthuc,String sobienlai,int loaitt){
        Double total = 0.0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         if(!sobienlai.trim().equals("")){
             String[] sohieus = sobienlai.split(",");
            if(sohieus.length >1){
                String shs = "";
                 for(int i=0;i< sohieus.length;i++){
                    if(i < sohieus.length -1){
                        shs += "'"+sohieus[i] +"',";
                    }else{
                        shs +="'"+ sohieus[i] +"',";
                    }
                }
                whereSql += " and tbl_dontp.DTP_Sohieu in("+shs+")";
            }else{
                whereSql += " and tbl_dontp.DTP_Sohieu like '%"+sobienlai+"%'";
            }
         }
         if(loaitt != 0){
              whereSql += " and tbl_dontp.DTP_Sohieu like '%"+loaitt+"%'";
         }
        String sql = "select SUM((select tbl_lephi.LP_Price \n" +
                        "from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID )) as lephi \n" +
                        "from tbl_dondatp\n" +
                            "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                            "inner join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                            "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                            " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                            "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID)\n" +
                            "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID where 1=1 "+whereSql+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }
    // Lấy tổng tiên đơn BD
    public Double getTongBD(String ngaybatdau,String ngayketthuc,String sobienlai,int loaitt){
        Double total = 0.0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         if(!sobienlai.trim().equals("")){
             String[] sohieus = sobienlai.split(",");
            if(sohieus.length >1){
                whereSql += " and tbl_dontp.DTP_Sohieu in("+sobienlai+")";
            }else{
                whereSql += " and tbl_dontp.DTP_Sohieu like '%"+sobienlai+"%'";
            }
         }
         if(loaitt != 0){
              whereSql += " and tbl_dontp.DTP_Sohieu like '%"+loaitt+"%'";
         }
        String sql = "select SUM((select tbl_lephi.LP_Price \n" +
                        "from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID and tbl_don.LD_ID in (1,2,3,4,7,8,9,10,11,12,13))) as lephi \n" +
                        "from tbl_dondatp\n" +
                        "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                        "inner join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                        "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                        " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                        "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID)\n" +
                        "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID where 1=1 "+whereSql+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }
    // Lấy tổng tiền đơn TT
    public Double getTongTT(String ngaybatdau,String ngayketthuc,String sobienlai,int loaitt){
        Double total = 0.0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         if(!sobienlai.trim().equals("")){
             String[] sohieus = sobienlai.split(",");
            if(sohieus.length >1){
                whereSql += " and tbl_dontp.DTP_Sohieu in("+sobienlai+")";
            }else{
                whereSql += " and tbl_dontp.DTP_Sohieu like '%"+sobienlai+"%'";
            }
         }
         if(loaitt != 0){
              whereSql += " and tbl_dontp.DTP_Sohieu like '%"+loaitt+"%'";
         }
        String sql = "select SUM((select tbl_lephi.LP_Price \n" +
                    "from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID and tbl_don.LD_ID =5)) as lephi \n" +
                    "from tbl_dondatp\n" +
                    "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                    "inner join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                    "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                    " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                    "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID)\n" +
                    "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID where 1=1 "+whereSql+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }
    // Lấy tổng tiền bản sao
    public Double getTongBS(String ngaybatdau,String ngayketthuc,String sobienlai,int loaitt){
        Double total = 0.0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         if(!sobienlai.trim().equals("")){
             String[] sohieus = sobienlai.split(",");
            if(sohieus.length >1){
                whereSql += " and tbl_dontp.DTP_Sohieu in("+sobienlai+")";
            }else{
                whereSql += " and tbl_dontp.DTP_Sohieu like '%"+sobienlai+"%'";
            }
         }
         if(loaitt != 0){
              whereSql += " and tbl_dontp.DTP_Sohieu like '%"+loaitt+"%'";
         }
        String sql = "select SUM((select tbl_lephi.LP_Price \n" +
                "from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID and tbl_don.LD_ID =6)) as lephi \n" +
                "from tbl_dondatp\n" +
                "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                "inner join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID)\n" +
                "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID where 1=1 "+whereSql+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }
    // Thống kê báo có
    // get Total page thống kê báo có
    public int getPageTKBC(String ngaybaoco){
        int page = 0;
        String sql = "select count(distinct tbl_dondatp.DTP_ID)\n" +
                        "from tbl_dondatp\n" +
                        "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                        "inner join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                        "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                        " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                        "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID)\n" +
                        "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID "
                + "where tbl_baoco.BC_Date >= str_to_date(?,'%d-%m-%Y') ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, ngaybaoco);
            rs = pstm.executeQuery();
            while(rs.next()){
                page = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if((page%10)==0 && page > 0) page = page /10;
        else page =(page/10)+1;
        return page;
    }
    // get Total page search  thống kê báo có
    public int getPageSearchTKBC(String ngaybatdau,String ngayketthuc,String sobaoco,String sotaikhoan,String bnbd,int checktien){
        int page = 0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_baoco.BC_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_baoco.BC_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         if(!sobaoco.trim().equals("")){
             String[] sobc = sobaoco.split(",");
             if(sobc.length >1){
                 whereSql += " and tbl_baoco.BC_Number in ("+sobaoco+") ";
             }else{
                 whereSql += " and tbl_baoco.BC_Number like '%"+sobaoco+"%' ";
             }
         }
         if(!sotaikhoan.trim().equals("")){
             String[] sotk = sotaikhoan.split(",");
             if(sotk.length >1){
                 whereSql += " and tbl_baoco.BC_Number in ("+sotaikhoan+") ";
             }else{
                 whereSql += " and tbl_baoco.BC_Number like '%"+sotaikhoan+"%' ";
             }
         }
         if(checktien !=0){
             if(checktien ==1){
                 whereSql += " and tbl_baoco.BC_Thua >=0";
             }else if(checktien ==2){
                 whereSql += " and tbl_baoco.BC_Thua < 0 ";
             }
         }
         if(!bnbd.trim().equals("")){
             whereSql += " and tbl_khachhang.KH_Name  like '%"+bnbd+"%'";
         }
         
         if(!sotaikhoan.trim().equals("")){
             String[] stks = sotaikhoan.split(",");
             if(stks.length >1){
                 whereSql += " and tbl_khachhang.KH_Account in ("+sotaikhoan+")";
             }else{
                 whereSql += " and tbl_khachhang.KH_Account like '%"+sotaikhoan+"%' ";
             }
         }
        String sql = "select count(distinct tbl_dondatp.DTP_ID)\n" +
"from tbl_dondatp\n" +
"inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
"inner join tbl_baoco on tbl_baoco.DTP_ID = tbl_dondatp.DTP_ID where 1=1 "+whereSql+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                page = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
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
    
    // get view thống kê báo có
    public String viewTKBC(int page,String ngaynhap){
        String table = "";
        String sql = "select distinct tbl_dondatp.DTP_ID,DATE_FORMAT(tbl_baoco.BC_Date,'%d-%m-%Y') as ngaybc, tbl_baoco.BC_Number as sobc,\n" +
                        "tbl_khachhang.KH_Account as taikhoan,\n" +
                        "tbl_khachhang.KH_Name as bnbd,\n" +
                        "tbl_baoco.BC_Total as tongtienbc,tbl_dontp.DTP_Total as tongtientt,tbl_baoco.BC_Thua as tienthua\n" +
                        "from tbl_dondatp\n" +
                        "inner join (tbl_don inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID ) on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                        "inner join tbl_baoco on tbl_baoco.DTP_ID = tbl_dondatp.DTP_ID "
                    + "inner join tbl_dontp on tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID "
                    +"where tbl_baoco.BC_Date >= str_to_date(?,'%d-%m-%Y') "
                + "order by tbl_baoco.BC_Date desc limit "+(page-1)*10+",10 ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, ngaynhap);
            rs = pstm.executeQuery();
            int i=1;
            while(rs.next()){
                String ngaybc = rs.getString("ngaybc");
                String sobc = rs.getString("sobc");
                String taikhoan = rs.getString("taikhoan");
                String bnbd = rs.getString("bnbd");
                int sotienbc = rs.getInt("tongtienbc");
                int tienthua = rs.getInt("tienthua");
                int tongtientt = rs.getInt("tongtientt");
                table += "<tr>";
                table += "<td style='height:50px'>"+i+"</td>";
                table += "<td>"+ngaybc+"</td>";
                table += "<td>"+sobc+"</td>";
                table += "<td>"+taikhoan+"</td>";
                table += "<td>"+bnbd+"</td>";
                table += "<td>"+sotienbc+"</td>";
                table += "<td>"+tongtientt+"</td>";
                table += "<td>"+tienthua+"</td>";
                
                table += "</tr>";
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    // load bên báo có
    public void loadInforBaoCo(){
        String sql = "select DATE_FORMAT(tbl_baoco.BC_Date,'%d-%m-%Y') as ngaybc, tbl_baoco.BC_Number as sobc,\n" +
                            "ifnull(sum(tbl_baoco.BC_Total),0.0) as tongtienbc,\n" +
                            "ifnull(sum(tbl_baoco.BC_Thua),0.0) as tongthua,\n" +
                            "group_concat(tbl_baoco.DTP_ID)\n" +
                            "from tbl_baoco \n" +
                            "where tbl_baoco.BC_Date >= STR_TO_DATE(?,'%d-%m-%Y') and tbl_baoco.BC_Number='00000206'\n" +
                            "group by tbl_baoco.BC_Date,tbl_baoco.BC_Number;";
        
    }
    // get view Search thống kê báo có
    public String viewSearchTKBC(String ngaybatdau,String ngayketthuc,String sobaoco,String khids,int checktien){
        String table = "";
        String whereSql = "";
        
         if(!sobaoco.trim().equals("")){
             String[] sobc = sobaoco.split(",");
             if(sobc.length >1){
                 whereSql += " and tbl_baoco.BC_Number in ("+sobaoco.trim()+") ";
             }else{
                 whereSql += " and tbl_baoco.BC_Number like '%"+sobaoco.trim()+"%' ";
             }
         }
         if(checktien !=0){
             if(checktien ==1){
                 whereSql += " and tbl_baoco.BC_Thua >=0";
             }else if(checktien ==2){
                 whereSql += " and tbl_baoco.BC_Thua < 0 ";
             }
         }
         if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_baoco.BC_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_baoco.BC_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
        String sql = "select distinct tbl_baoco.BC_ID,DATE_FORMAT(tbl_baoco.BC_Date,'%d-%m-%Y') as ngaybc, tbl_baoco.BC_Number as sobc,\n" +
                        "ifnull((select tbl_khachhang.KH_Account from tbl_khachhang where tbl_khachhang.KH_ID = tbl_baocokh.KH_ID),'') as taikhoan,\n" +
                        "ifnull((select tbl_khachhang.KH_Name from tbl_khachhang where tbl_khachhang.KH_ID = tbl_baocokh.KH_ID),'')  as bnbd,\n" +
                        " concat(tbl_baoco.BC_Nguoiphatlenh , '<br>',tbl_baoco.BC_ND) as ttbc,"+
                        "ifnull(tbl_baoco.BC_Total,0.0) as tongtienbc,ifnull(tbl_baoco.BC_Thua,0.0) as tienthua\n" +
                        "from tbl_baoco \n" +
                        "LEFT JOIN tbl_baocokh ON tbl_baocokh.BC_ID = tbl_baoco.BC_ID "
                + "where 1=1 "
                +whereSql+" ;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i=1;
            changeNumber = new ChangeNumberToText();
            while(rs.next()){
                String ngaybc = rs.getString("ngaybc");
                String sobc = rs.getString("sobc");
                String taikhoan = rs.getString("taikhoan");
                String bnbdrs = rs.getString("bnbd");
                String ttbc  = rs.getString("ttbc");
                Double sotienbcD = rs.getDouble("tongtienbc");
               // System.out.println(sotienbcD);
                String sotienbc = changeNumber.priceWithDecimal(sotienbcD);
               
              //  Double tongtienttD = rs.getDouble("tienthua");
                Double tienthuaD = rs.getDouble("tienthua");
                String tongtientt = changeNumber.priceWithDecimal((sotienbcD-tienthuaD));
                 //Double tienthuaD = rs.getDouble("tienthua");
               //  Double tienthuaD = sotienbcD - tongtienttD;
                String tienthua = changeNumber.priceWithDecimal(tienthuaD);
                table += "<tr >";
                table += "<td style='height:50px' align='center'>"+i+"</td>";
                table += "<td align='center' >"+ngaybc+"</td>";
                table += "<td align='center' >"+sobc+"</td>";
                table += "<td align='center' >"+taikhoan+"</td>";
                if(!bnbdrs.equals("")){
                    table += "<td >"+bnbdrs+"</td>";
                }else{
                    table += "<td >"+ttbc+"</td>";
                }
                
                table += "<td align='center' >"+sotienbc+"</td>";
                 table += "<td align='center'>"+tongtientt+"</td>";
                table += "<td align='center'>"+tienthua+"</td>";
               
                table += "</tr>";
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    // Lấy tổng tiền các loại
    public String getTongTienBC(String ngaybatdau,String ngayketthuc,String sobaoco,String sotaikhoan,String bnbd,int checktien){
        String table  = "";
        String whereSql = "";
        
         if(!sobaoco.trim().equals("")){
             String[] sobc = sobaoco.split(",");
             if(sobc.length >1){
                 whereSql += " and tbl_baoco.BC_Number in ("+sobaoco.trim()+") ";
             }else{
                 whereSql += " and tbl_baoco.BC_Number like '%"+sobaoco.trim()+"%' ";
             }
         }
         if(checktien !=0){
             if(checktien ==1){
                 whereSql += " and tbl_baoco.BC_Thua >=0";
             }else if(checktien ==2){
                 whereSql += " and tbl_baoco.BC_Thua < 0 ";
             }
         }
         if(!bnbd.trim().equals("")){
             whereSql += " and tbl_baocokh.KH_ID in(select tbl_khachhang.KH_ID from tbl_khachhang where tbl_khachhang.KH_Name  like '%"+bnbd.trim()+"%') ";
         }
         
         if(!sotaikhoan.trim().equals("")){
             String[] stks = sotaikhoan.split(",");
             if(stks.length >1){
                 whereSql += " and tbl_baocokh.KH_ID in(select tbl_khachhang.KH_ID from tbl_khachhang where tbl_khachhang.KH_Account from in ("+sotaikhoan.trim()+"))";
             }else{
                 whereSql += " and tbl_baocokh.KH_ID in(select tbl_khachhang.KH_ID from tbl_khachhang where tbl_khachhang.KH_Account like '%"+sotaikhoan.trim()+"%') ";
             }
         }
         if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_baoco.BC_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_baoco.BC_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
        String sql = "select ifnull(SUM(tbl_baoco.BC_Total),0) as tongtienbc,ifnull(SUM(tbl_baoco.BC_Thua),0) as tienthua\n" +
                        "from tbl_baoco\n" +
                        " left join tbl_baocokh ON tbl_baocokh.BC_ID = tbl_baoco.BC_ID "
                + "where 1=1  "+whereSql+";";
        System.out.println("----------------------------------------");
        System.out.println(sql);
        System.out.println("----------------------------------------");
        connect = new DBConnect().dbConnect();
        try {
          //  DecimalFormat df = new DecimalFormat("#");
        //    df.setMaximumFractionDigits(0);
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){

                double tongtienbc = rs.getDouble("tongtienbc");
                
              //  double tongtienthua = rs.getDouble("tienthua");
              double tongtienthua = rs.getDouble("tienthua");
              double tongtientt = tongtienbc - tongtienthua;
                table += "<tr><td colspan='5' align='right'>Tổng cộng : &nbsp;&nbsp;&nbsp;</td><td align='center'>"+changeNumber.priceWithDecimal(tongtienbc)
                        +"</td><td align='center' >"+changeNumber.priceWithDecimal(tongtientt)+"</td><td align='center'>"+changeNumber.priceWithDecimal(tongtienthua)+"</td><td></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }

    
    // Xem thông báo phí
    public String viewThongBaoPhi(String sohieu,String thang,String nam){
        String table = "";
        String sql = "";
        return table;
    }
    
    // Lấy thông tin cho việc In thông báo phí
    // View In thÔng báo phí
    public String printThongbaoPhi(String donids){
        String table = "";
        String sql = "select tbl_don.D_ID as donid,DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap,"
                + " tbl_don.DK_ID as dkid,tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,\n" +
                            "tbl_loainhan.LN_Short as loainhan,"
                + "tbl_don.D_MDO as maonline,"
                + "group_concat(tbl_benbaodam.BDB_Name  SEPARATOR ',') as benbaodam,\n" +
        "tbl_lephi.LP_Price  as tongtien\n" +
        "from tbl_don \n"
                    + "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID \n" +
                    "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID\n" +
                    "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID\n" +
                    "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                    " inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID "+
            "where tbl_don.D_ID not in(SELECT tbl_dondatp.D_ID FROM tbl_dondatp) "+
            " and tbl_don.D_ID in( "+donids+") group by tbl_don.D_ID ;";
//        System.out.println(sql); 
        connect = new DBConnect().dbConnect();
        long tongtien = 0;
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i=1;
            while(rs.next()){
                String ngaynhap = rs.getString("ngaynhap");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, ngaynhap); 
                maloainhan = maloainhan.replaceAll("CSGT", "");
                String bbd = rs.getString("benbaodam");
                String maonline = rs.getString("maonline");
                if(maonline == null){
                    maonline = "0";
                }
                long sotien = rs.getLong("tongtien");
                tongtien += sotien;
                table += "<tr style=\"height:25px;\">";
                table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+i+"</td>";
                table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+ngaynhap+"</td>";
                System.out.println("LOAIDK = "+loaidk);
                if(loaidk.equals("CSGT")){
                     table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+maloainhan+"</td>";
                     table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+bbd+"<span style=\"color:red\">(PL04)</span></td>";
                }else{
                     table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+maonline+"</td>";
                     if(!loaidk.equals("TT")){
                          table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+bbd+"</td>";
                      }else{
                         int noCCtt = maonline.split(",").length;
                          table += "<td style=\"font-size:12px;border:1 #000000 solid;\"><span style=\"color:red\">"+noCCtt+"CC-TT</span></td>";
                     }
                    
                }
                 table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+sotien+"</td>";
                table += "</tr>";
                i++;
            }
            table += "<tr><td colspan='4'><span style='font-weight: bold;font-size:12px;border:1 #000000 solid;'>Tổng số tiền thanh toán</span></td><td><span style='font-weight: bold;'>"+tongtien+"</span></td></tr>";
            changeNumber = new ChangeNumberToText();
            String sotienStr = new ChangeNumberToText().convert(tongtien);
            table += "<tr><td colspan='5'><span style='font-weight: bold;font-size:12px;border:1 #000000 solid;'>Số tiền bằng chữ ( "+sotienStr+" đồng chẵn)</span></td></tr>";
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    // lấy bên nhận bảo đảm
    public ArrayList<String> printGetBNBD(String donids){
        ArrayList<String> bnbdArr = new  ArrayList<>();
        String sql = "select  tbl_khachhang.KH_Name  as bnbd,\n" +
                        "tbl_khachhang.KH_Address  as diachi,\n" +
                        "ifnull(tbl_khachhang.KH_Account ,'') as makhachhang\n" +
                        " from tbl_don"
                + " inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID \n" +
                        "where tbl_don.D_ID in (?) group by tbl_don.KH_ID ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, donids);
            rs = pstm.executeQuery();
            while(rs.next()){
                String bnbd = rs.getString("bnbd");
                String diachi = rs.getString("diachi");
                String makh = rs.getString("makhachhang");
                bnbdArr.add(bnbd);
                bnbdArr.add(diachi);
                bnbdArr.add(makh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bnbdArr;
    }
     // Lấy danh sách khách hàng của tổng công nợ
    public ArrayList<ArrayList<String>> getKHang(String ngaydauky,int page){
        String limit  = "";
        if( page != -1){
            limit = "limit "+(page-1)*10+",10";
        }
        ArrayList<ArrayList<String>> khList = new ArrayList<>();
        String sql = "select tbl_khachhang.KH_ID as khID, tbl_khachhang.KH_Name  as khname,\n" +
                        "tbl_khachhang.KH_Account as khaccount \n" +
                        "from tbl_don \n" +
                        "inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID \n" +
                        " where  tbl_don.D_Date <= str_to_date(?,'%d-%m-%Y') \n" +
                        "and tbl_don.D_ID not in (select tbl_dondatp.D_ID from tbl_dondatp ) and tbl_don.D_isRemove = 0 \n" +
                        " group by tbl_don.KH_ID order by tbl_don.D_ID "+limit;
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, ngaydauky);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String khid = rs.getString("khID");
                String khname = rs.getString("khname");
                String account = rs.getString("khaccount");
                dt.add(khid);dt.add(khname);dt.add(account);
                khList.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return khList;
    }
    // Lấy số đầu kì
    public ArrayList<String> getSodauKy(int khid,String ngaydauky){
        ArrayList<String> dt = new ArrayList<>();
        String sql = "select sum( tbl_lephi.LP_Price ) as sodauky,\n" +
                        "group_concat(distinct tbl_don.D_ID separator ',') as donids\n" +
                        "from tbl_don \n" +
                        " left join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID "+
                        "where tbl_don.D_ID not in (select tbl_dondatp.D_ID from tbl_dondatp ) and tbl_don.D_isRemove = 0 \n" +
                        " and tbl_don.D_Date < str_to_date(?,'%d-%m-%Y') "+
                        " and tbl_don.KH_ID = ?;";
        System.out.println(khid);
        System.out.println(ngaydauky);
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, ngaydauky);
            pstm.setInt(2, khid);
            rs = pstm.executeQuery();
            while(rs.next()){
                changeNumber = new ChangeNumberToText();
                Double sodaukyD = rs.getDouble("sodauky");
                String sodauky = changeNumber.priceWithDecimal(sodaukyD);
                
                String donids = rs.getString("donids");
                dt.add(sodauky);dt.add(donids);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dt;
    }
    //Lấy số phải thu
    public ArrayList<String> getSoPhaiThu(int khid,String ngaybatdau,String ngayketthuc){
        ArrayList<String> dt = new ArrayList<>();
        String sql = "select sum(tbl_lephi.LP_Price  ) as sophaithu,\n" +
                        "group_concat(tbl_don.D_ID separator ',') as donids \n" +
                        "from tbl_don \n" +
                        " left join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID "+
                        "where tbl_don.D_ID not in (select tbl_dondatp.D_ID from tbl_dondatp )\n" +
                        "and tbl_don.D_Date between str_to_date('"+ngaybatdau+"','%d-%m-%Y') and str_to_date('"+ngayketthuc+"','%d-%m-%Y')\n" +
                        " and tbl_don.KH_ID = "+khid+"\n" +
                        "group by tbl_don.KH_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                Double sophaithuD = rs.getDouble("sophaithu");
                String sophaithu =  changeNumber.priceWithDecimal(sophaithuD);
                String donids = rs.getString("donids");
                dt.add(sophaithu);dt.add(donids);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dt;
    }
    // Lấy tổng số phải thu
    public ArrayList<String> getDaThu(int khid,String ngaybatdau,String ngayketthuc){
        ArrayList<String> dt = new ArrayList<>();
        String sql =  "select sum( tbl_lephi.LP_Price ) as sodathu,\n" +
                        "group_concat(tbl_don.D_ID separator ',') as donids \n" +
                        "from tbl_don \n" +
                        " left join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID "+
                        "where tbl_don.D_ID in (select tbl_dondatp.D_ID from tbl_dondatp )\n" +
                        "and tbl_don.D_Date between str_to_date('"+ngaybatdau+"','%d-%m-%Y') and str_to_date('"+ngayketthuc+"','%d-%m-%Y')\n" +
                        " and tbl_don.KH_ID = "+khid+"\n" +
                        "group by tbl_don.KH_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                Double sodathuD = rs.getDouble("sodathu");
                String sodathu = changeNumber.priceWithDecimal(sodathuD);
                String donids = rs.getString("donids");
                dt.add(sodathu);dt.add(donids);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dt;
    } 
    // Lấy số cuối kì
    public ArrayList<String> getCuoiKy(int khid,String ngayketthuc){
        ArrayList<String> dt = new ArrayList<>();
        String sql = "select sum(tbl_lephi.LP_Price  ) as socuoiky,\n" +
                    "group_concat(tbl_don.D_ID separator ',') as donids \n" +
                    "from tbl_don \n" +
                    " left join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID "+
                    "where tbl_don.D_ID not in (select tbl_dondatp.D_ID from tbl_dondatp ) and tbl_don.D_isRemove = 0 \n" +
                    "and tbl_don.D_Date <= str_to_date('"+ngayketthuc+"','%d-%m-%Y')\n" +
                    " and tbl_don.KH_ID = "+khid+"\n" +
                    "group by tbl_don.KH_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                Double socuoikyD = rs.getDouble("socuoiky");
                String socuoiky = changeNumber.priceWithDecimal(socuoikyD);
                String donids = rs.getString("donids");
                dt.add(socuoiky);dt.add(donids);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dt;
    }
    // Lấy thông tin báo có
    public ArrayList<String> getSotienBaoCo(int khid,String ngaybatdau,String ngaykethuc){
        ArrayList<String> dt = new ArrayList<>();
        String sql = "select sum(tbl_baoco.BC_Thua) as tongdu,group_concat( distinct tbl_baoco.BC_ID) as bcids \n" +
        "from tbl_dondatp\n" +
        "left join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
        "left join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
        "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
        " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
        "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID)\n" +
        "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID \n" +
         "where tbl_don.D_Date <= str_to_date('"+ngaykethuc+"','%d-%m-%Y') \n" +
          " and tbl_don.KH_ID = "+khid+" and tbl_don.D_isRemove = 0 \n" +
        "group by tbl_don.KH_ID having sum(tbl_baoco.BC_Thua) > 0;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
             rs = pstm.executeQuery();
             while(rs.next()){
                Double tongduD = rs.getDouble("tongdu");
                String tongdu = changeNumber.priceWithDecimal(tongduD);
                String bcids = rs.getString("bcids");
                dt.add(tongdu);dt.add(bcids);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dt;
    }
    // Tổng hợp công nợ
    public String getTonghopCongno(String ngaydauky,String ngaykethuc,int page){
        String table = "";
        ArrayList<ArrayList<String>> khList = getKHang(ngaykethuc,page);
        for(int i=0;i< khList.size();i++){
//            System.out.print("SL K hang"+khList.size());
//            System.out.println("  Name = "+khList.get(i).get(1));
            int khid = Integer.parseInt(khList.get(i).get(0)) ;
            ArrayList<String> sdkList = getSodauKy(khid,ngaydauky);
            ArrayList<String> sptList = getSoPhaiThu(khid, ngaydauky, ngaykethuc);
            ArrayList<String> dtList = getDaThu(khid, ngaydauky, ngaykethuc);
            ArrayList<String> ckList = getCuoiKy(khid, ngaykethuc);
            ArrayList<String> bcList = getSotienBaoCo(khid, ngaydauky, ngaykethuc);
            if(sptList.isEmpty()){
                if(sptList.isEmpty()){
                    if(dtList.isEmpty()){
                        if(ckList.isEmpty()){
                            if(bcList.isEmpty()){
                                continue;
                            }
                        }
                    }
                }
            }
            table +="<tr>";
            table += "<td>"+(i+1)+"</td>";
            table += "<td>"+khList.get(i).get(1)+"</td>";
            table += "<td>"+khList.get(i).get(2)+"</td>";
            if(sdkList.isEmpty()){
                table += "<td>0</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet(0)'></td>";
            }else{
                table += "<td>"+sdkList.get(0)+"</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+sdkList.get(1)+")'></td>";
            }
            
            if(sptList.isEmpty()){
                table += "<td>0</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet(0)'></td>";
            }else{
                table += "<td>"+sptList.get(0)+"</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+sptList.get(1)+")'></td>";
            }
            
            if(dtList.isEmpty()){
                table += "<td>0</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet(0)'></td>";
            }else{
                table += "<td>"+dtList.get(0)+"</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+dtList.get(1)+")'></td>";
            }
            if(ckList.isEmpty()){
                Double totalCK = 0d;
                if(!sdkList.isEmpty()){
                    totalCK += Double.parseDouble(sdkList.get(0)) ;
                }
                if(!sptList.isEmpty()){
                    totalCK += Double.parseDouble(sptList.get(0)) ;
                }
                if(!dtList.isEmpty()){
                    totalCK -= Double.parseDouble(dtList.get(0)) ;
                }
                table += "<td>"+totalCK.intValue()+"</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet(0)'></td>";
            }else{
                 table += "<td>"+ckList.get(0)+"</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+ckList.get(1)+")'></td>";
            }
            if(bcList.isEmpty()){
                table += "<td>0</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet(0)'></td>";
            }else{
                table += "<td>"+bcList.get(0)+"</td>";
                table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+bcList.get(1)+")'></td>";
            }
            
            table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+khid+")'></td>";
            table += "</tr>";
        }
        return table;
    }
    /*******
     * 
     * Chức năng về thông tin thu phí
    ******/

    // Lưu Ngày thông báo phí
//    public int saveNgayTBP(java.sql.Date ngaybp){
//        int key =0;
//        String sql = "insert into tbl_ngaytbp(NBP_Gui) values (?)";
//        connect = new DBConnect().dbConnect();
//        try {
//            pstm = connect.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//            pstm.setDate(1, ngaybp);
//            int affect = pstm.executeUpdate();
//            if(affect !=0){
//                rs = pstm.getGeneratedKeys();
//                if(rs.next()){
//                    key = rs.getInt(1);
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
//        }finally{
//            try {
//                new DBConnect().closeAll(connect, pstm, rs);
//            } catch (SQLException ex) {
//                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return key;
//    }
    
    // Lưu người liên hệ
    public int saveNguoiLienHe(String nguoilienhe){
        int key =0;
        String sql = "insert into tbl_NguoiLienhe(NLH_Lienhe) values (?)";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, nguoilienhe);
            int affect = pstm.executeUpdate();
            if(affect !=0){
                rs = pstm.getGeneratedKeys();
                if(rs.next()){
                    key = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // Lưu người chịu trách nhiệm
    public int saveNguoichiuTN(String nguoictn,int cvid){
        int key =0;
        String sql = "insert into tbl_NguoiCTN(NBP_Trachnhiem,CV_ID) values (?,?)";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, nguoictn);
             pstm.setInt(2, cvid);
            int affect = pstm.executeUpdate();
            if(affect !=0){
                rs = pstm.getGeneratedKeys();
                if(rs.next()){
                    key = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // Lưu ngày hết hạn
    public int saveNgayHetHan(java.sql.Date ngaytt){
        int key =0;
        String sql = "insert into tbl_ngayhethan(NHH_Date,NHH_Status) values (?,'1')";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setDate(1, ngaytt);
            int affect = pstm.executeUpdate();
            if(affect !=0){
                rs = pstm.getGeneratedKeys();
                if(rs.next()){
                    key = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // Lưu tài khoản ngân hàng
    public int saveTKNH(String taikhoan){
        int key =0;
        String sql = "insert into tbl_taikhoanNH(TK_Des) values (?)";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, taikhoan);
            int affect = pstm.executeUpdate();
            if(affect !=0){
                rs = pstm.getGeneratedKeys();
                if(rs.next()){
                    key = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // save thông tin báo phí
    public void inserBPInfor(String shortname,String nguoilh,String nguoictn,int cv,String taikhoan){
        String sql ="insert into tbl_saveInforTBP(ITBP_Short,NLH_Lienhe,NBP_Trachnhiem,TK_Des,CV_ID) values (?,?,?,?,?)";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, shortname);
            pstm.setString(2, nguoilh);
            pstm.setString(3, nguoictn);
            pstm.setString(4, taikhoan);
            pstm.setInt(5, cv);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Lưu thông tin thu phí
    public int saveThongtinThuPhi(int keynbp,int keynhh,int itbpid){
        int returnKey = 0;
        String sql ="insert into tbl_thongbaophi(NTBP_ID,NHH_ID,ITBP_ID) values (?,?,?)";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, keynbp);
            pstm.setInt(2, keynhh);
            pstm.setInt(3, itbpid);
            returnKey = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnKey;
    }
    
    // Lưu thông tin lưu phí sử dụng lại
    public int saveThongtinUse(String shorname,int tknh,int keynlh,int keyngctn){
        int key = 0;
        String sql = "insert into tbl_saveInforTBP(TK_ID,NLH_ID,CTN_ID,ITBP_Short) values (?,?,?,?)";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, tknh);
            pstm.setInt(2, keynlh);
            pstm.setInt(3, keyngctn);
            pstm.setString(4, shorname);
            int affect = pstm.executeUpdate();
            if(affect !=0){
                rs = pstm.getGeneratedKeys();
                if(rs.next()){
                    key = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // Load tất cả dữ liệu chọn
    public String loadAllTTBP(){
        String option = "";
        String sql = "select tbl_saveInforTBP.ITBP_ID as id,\n" +
                            "tbl_saveInforTBP.ITBP_Short as inforname \n" +
                            "from tbl_saveInforTBP\n" +
                         " where tbl_saveInforTBP.ITBP_Short <> '';";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String inforname = rs.getString("inforname");
                option += "<option value='"+id+"'>"+inforname+"</option>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return option;
    }
    
    // Load thông tin thu phí đã lưu
    public ArrayList loadTTBPInfor(int id){
        ArrayList data = new ArrayList();
        String sql = "select tbl_saveInforTBP.ITBP_ID as id,\n" +
                            "tbl_saveInforTBP.ITBP_Short as inforname,\n" +
                            "ifnull(tbl_saveInforTBP.TK_Des,'') as taikhoan,\n" +
                            "tbl_saveInforTBP.NLH_Lienhe as nguoilienhe, tbl_saveInforTBP.NBP_Trachnhiem as ngchiutn,\n" +
                            "tbl_saveInforTBP.CV_ID as chucvu\n" +
                            "from tbl_saveInforTBP\n" +
                                " where tbl_saveInforTBP.ITBP_ID = ?;";
      //  System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while(rs.next()){
                String taikhoan = rs.getString("taikhoan");
                String nguoilienhe = rs.getString("nguoilienhe");
                String nguoictn = rs.getString("ngchiutn");
                int cvid = rs.getInt("chucvu");
                data.add(taikhoan);data.add(nguoilienhe);data.add(nguoictn);data.add(cvid);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Load chức vụ
    public String loadChucVu(){
        String option = "";
        String sql = "select CV_ID as id,\n" +
                            "CV_Des as chucvu \n" +
                            "from tbl_chucvu;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String chucvu = rs.getString("chucvu");
                option += "<option value='"+id+"'>"+chucvu+"</option>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return option;
    }
    
    // Kiểm tra lưu infor thu phí
    
    // Kiểm tra tên rút gọn
    public int checkShortName(String shortname){
        int key = 0;
        String sql = "select ITBP_ID as id from tbl_saveInforTBP where tbl_saveInforTBP.ITBP_Short = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, shortname);
            rs = pstm.executeQuery();
            while(rs.next()){
                key  = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // Kiểm tra người liên hệ
    public int checkNguoiLH(String nguoilh){
        int key = 0;
        String sql = "select NLH_ID as id from tbl_NguoiLienhe where NLH_Lienhe = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, nguoilh);
            rs = pstm.executeQuery();
            while(rs.next()){
                key  = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // KIểm tra người chịu trách nhiệm
    public int checkNguoiCTN(String nguoictn,int cvid){
        int key = 0;
        String sql = "select CTN_ID  as id from tbl_NguoiLienhe where NBP_Trachnhiem = ? and CV_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, nguoictn);
            pstm.setInt(2, cvid);
            rs = pstm.executeQuery();
            while(rs.next()){
                key  = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // check số tài khoản
    public int checkTKNH(String taikhoan){
        int key = 0;
        String sql = "select TK_ID  as id from tbl_taikhoanNH where TK_Des = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, taikhoan);
            rs = pstm.executeQuery();
            while(rs.next()){
                key  = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // Lấy email của kế toán
    public ArrayList<String> loadAllEmail(){
        ArrayList<String> emailList = new ArrayList<>();
        String sql = "select TTE_ID as email from tbl_ttemail where TTE_Status = '1';";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                emailList.add(rs.getString("email"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return emailList;
    }
    // Trả về String email
    public String optionEmail(){
        String tbody = "";
        ArrayList<String>  emailList = loadAllEmail();
        for(String email : emailList){
            tbody += "<option value="+email+">"+email+"</option>";
        }
        return tbody;
    };
    // Lấy khách hàng chọn
    public ArrayList<String> loadSelectInfor(int khid){
        ArrayList<String>  data = new ArrayList<>();
        String sql = "select tbl_khachhang.KH_Name as bnbd,\n" +
                            " tbl_khachhang.KH_Account as KHAccount,\n" +
                            "tbl_khachhang.KH_Email as KHEMail\n" +
                            "from tbl_khachhang "+
                            "where tbl_khachhang.KH_ID = ? \n" +
                            "group by tbl_khachhang.KH_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, khid);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString("bnbd"));
                data.add(rs.getString("KHAccount"));
                data.add(rs.getString("KHEMail"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // Tạo bảng cho bnbd
    public String tbodyKh(String[] khid,String[] donids){
        String tbody = "";
        int i=1;
        for(String id : khid){
            ArrayList<String>  khDt = loadSelectInfor(Integer.parseInt(id));
            tbody += "<tr>";
            tbody += "<td>"+i+"</td>";
            tbody += "<td>"+khDt.get(0)+"</td>";
            tbody += "<td>"+khDt.get(1)+"</td>";
            tbody += "<td>"+khDt.get(2)+"</td>";
            tbody += "<td><input type='checkbox' class='sendmail' value='"+id+"-"+donids[i-1]+"'></td>";
             tbody += "<td><div class='send_result'></div></td>";
            tbody += "</tr>";
        }
        
        return tbody;
    }
    
    // Lấy thông tin thu phí
    public ArrayList<String> getTTTP(){
        ArrayList<String> data = new ArrayList();
        String sql = "select TK_Des as taikhoan, \n" +
                            "NLH_Lienhe as nguoilienhe, NBP_Trachnhiem as nguoictn,\n" +
                            "(select tbl_chucvu.CV_Des from tbl_chucvu where tbl_chucvu.CV_ID = tbl_saveInforTBP.CV_ID) as chucvu \n" +
                            "from tbl_saveInforTBP;" ;
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                String taikhoan = rs.getString("taikhoan");
                String nguoilh =rs.getString("nguoilienhe");
                String nguoictn = rs.getString("nguoictn");
                String chucvu = rs.getString("chucvu");
                data.add(taikhoan);data.add(nguoilh);
                data.add(nguoictn);data.add(chucvu);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
     // Lấy số công văn
    public int getSoCV(int khid){
        int cvNo = 0;
        String sql = "select count(*) \n" +
                    "from tbl_thongbaophi\n" +
                    "inner join (tbl_guithuphi inner join tbl_don on tbl_guithuphi.D_ID = tbl_don.D_ID) \n" +
                    "on tbl_guithuphi.TBP_ID = tbl_thongbaophi.TBP_ID\n" +
                    "where tbl_don.KH_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, khid);
            rs = pstm.executeQuery();
            while(rs.next()){
                cvNo = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cvNo+1;
    }
    
    // Thống kê đơn chưa có số tài khoản
    
    // Lấy tổng số trang thống kê đơn chưa có stk
    public int getPageDonCCSTK(String ngaybatdau,String ngayketthuc,String maonline,int loaidon,int loaidk,int loaihinhnhan,
            String manhan,String bnbd,String bbd){
        int totalpage = 0;
        String whereSql = "";
         if(!ngaybatdau.equals("")){
            whereSql += " and DATE(tbl_don.D_Date) >= str_to_date('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        if(!ngayketthuc.equals("")){
            whereSql += " and DATE(tbl_don.D_Date) <= str_to_date('"+ngayketthuc+"','%d-%m-%Y') ";
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
        if(loaidon != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidon;
        }
        if(loaidk != 0){
            whereSql += " and tbl_don.DK_ID = "+loaidk;
        }
        if(loaihinhnhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhan;
        }
        
        if(!bbd.trim().equals("")){
            whereSql += " and (select group_concat(`BDB_Name` separator ' \\n ')\n" +
                            "as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) like '%"+bbd+"%'";
        }
        if(!bnbd.trim().equals("")){
            whereSql += " and (select group_concat(`KHD_Name` separator ' \\n ') \n" +
                            "as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_don.D_ID order by KHD_ID) like '%"+bnbd+"%'";
        }
        String sql = "select count(*) "+
                            " from tbl_don\n" +
                            " inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID "+
                            "where (tbl_khachhang.KH_Status is null \n" +
                            "and tbl_khachhang.KH_Account = '') and tbl_don.D_isRemove = 0 "+whereSql+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                totalpage = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if((totalpage%10)==0){
            if(totalpage !=0) totalpage = totalpage/10;
            else totalpage = 1;
        }else totalpage =  (totalpage/10)+1;
        return totalpage;
    }
    // Load đơn thu phí chưa có số tk
    public  ArrayList<ArrayList> thuphiDonCCSKTK(int page,String ngaybatdau,String ngayketthuc,String maonline,int loaidon,int loaidk,int loaihinhnhan,
            String manhan,String bnbd,String bbd){
        ArrayList<ArrayList> data =new ArrayList<>();
        String whereSql = "";
        if(!ngaybatdau.equals("")){
            whereSql += " and DATE(tbl_don.D_Date) >= str_to_date('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        if(!ngayketthuc.equals("")){
            whereSql += " and DATE(tbl_don.D_Date) <= str_to_date('"+ngayketthuc+"','%d-%m-%Y') ";
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
        if(loaidon != 0){
            whereSql += " and tbl_don.LD_ID = "+loaidon;
        }
        if(loaidk != 0){
            whereSql += " and tbl_don.DK_ID = "+loaidk;
        }
        if(loaihinhnhan != 0){
            whereSql += " and tbl_don.LN_ID = "+loaihinhnhan;
        }
        
        if(!bbd.trim().equals("")){
            whereSql += " and (select group_concat(`BDB_Name` separator ' \n ')\n" +
                            "as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) like '%"+bbd+"%'";
        }
        if(!bnbd.trim().equals("")){
            whereSql += " and (select group_concat(`KHD_Name` separator ' \n ') \n" +
                            "as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_don.D_ID order by KHD_ID) like '%"+bnbd+"%'";
        }
        String sql = "select DISTINCT tbl_don.D_ID as donid ,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                            "tbl_don.D_SLTS  as slts,(select tbl_cctt.CCTT_Des from tbl_cctt where tbl_cctt.CCTT_ID = tbl_don.CCTT_ID) as cctt,"
                + "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,"
                + "tbl_don.D_manhan as manhan ,"
                + "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,"
                + "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,  \n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline,\n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin \n" +
                            "from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where (tbl_khachhang.KH_Account = ''\n" +
                            "or tbl_khachhang.KH_Account is null ) and tbl_don.D_isRemove = 0 "+whereSql
                            +" order by TIMESTAMP(tbl_don.D_Date) DESC,tbl_don.D_manhan DESC limit "+(page-1)*10+",10;";
//        System.out.println(sql);
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
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // load table view get don ccstk
    public String tableDCCSTK(int page,String ngaybatdau,String ngayketthuc,String maonline,int loaidon,int loaidk,int loaihinhnhan,
            String manhan,String bnbd,String bbd){
        ArrayList<ArrayList> data = thuphiDonCCSKTK(page, ngaybatdau, ngayketthuc, maonline, loaidon, loaidk, loaihinhnhan, manhan, bnbd, bbd);
        String table = "";
        for(int i=0; i < data.size();i++){
            String id = String.valueOf(data.get(i).get(0));
            table += "<tr>";
            table += "<td>"+data.get(i).get(1)+"</td>";
            table += "<td>"+data.get(i).get(2)+"</td>";
            table += "<td>"+data.get(i).get(3)+"</td>";
            table += "<td>"+data.get(i).get(4)+"</td>";
            table += "<td>"+data.get(i).get(5)+"</td>";
            table += "<td>"+data.get(i).get(6)+"</td>";
            table += "<td>"+data.get(i).get(7)+"</td>";
            String bnbdS = new NhapDon().loadBnbdDon(Integer.parseInt( id));
            table += "<td>"+bnbdS+"</td>";
            String benbd = new NhapDon().loadBBDDon(Integer.parseInt( id));
            table += "<td>"+benbd+"</td>";
//            table +="<td><img src=\"./images/document_edit.png\" data-toggle=\"modal\" onclick=\"editDon('"+id+"')\"></td>\n" +
//                "<td><img src=\"./images/document_delete.png\" onclick=\"deleteDon('"+id+"')\"></td></tr>";
            table += "</tr>";
        }
        return table;
    }
    
    // Thống kê hồ sơ
    public ArrayList<Integer> loadThongKeHS(String ngaybatdau,String ngayketthuc, boolean online){
        String whereSql = "";
         if(online){
            whereSql += " and tbl_don.D_manhan = 0 ";
        }else{
             whereSql += " and tbl_don.D_manhan <> 0 ";
         }
        if(!ngaybatdau.equals("")){
            whereSql += " and tbl_don.D_Date >= str_to_date('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        if(!ngaybatdau.equals("")){
            whereSql += "  and tbl_don.D_Date <= str_to_date('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        
       
        String sql = "select (select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 "+whereSql+") as tsdon,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and  tbl_don.LN_ID =1  "+whereSql+" ) as tsce,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and  tbl_don.LN_ID =2 "+whereSql+"  ) as tscf,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and  tbl_don.LN_ID =3 "+whereSql+" ) as tscb,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and  tbl_don.LN_ID =4 "+whereSql+"  ) as tsct,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and  tbl_don.LD_ID =1 "+whereSql+" ) as tsld,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and  tbl_don.LD_ID =2  "+whereSql+") as tstd,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and  tbl_don.LD_ID =3 "+whereSql+"  ) as tsxoa,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and tbl_don.LD_ID =4  "+whereSql+" ) as tsvbxlts,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and  tbl_don.LD_ID =5 "+whereSql+" ) as tscctt,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and tbl_don.LD_ID =6  "+whereSql+") as tsbs,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where tbl_don.D_isRemove = 0 and  tbl_don.LD_ID =7 "+whereSql+"  ) as tstbkbtha,\n" +
                            "(select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on  tbl_khachhang.KH_ID =tbl_don.KH_ID \n" +
                            "where  tbl_don.LD_ID =8 "+whereSql+" )  as tsmp,\n" +
                            " (select count(distinct tbl_don.D_ID) from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "where  tbl_don.D_isRemove = 0 and tbl_don.LD_ID > 8  "+whereSql+") as tscsgt;";
        ArrayList<Integer> sldon = new ArrayList<>();
        //System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try{
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int tsdon = 0, tsce=0,tscf =0,tscb = 0,tsct = 0,tsld = 0,tstd = 0,tsxoa = 0,tsvbxsts = 0,
                    tscctt = 0,tsbs = 0,tstbkbtha = 0,tsmp = 0,tscsgt = 0;
            while(rs.next()){
                tsdon = rs.getInt("tsdon");tsce = rs.getInt("tsce");
                tscf = rs.getInt("tscf");tscb = rs.getInt("tscb");
                tsct = rs.getInt("tsct");tsld = rs.getInt("tsld");
                tstd = rs.getInt("tstd");tsxoa = rs.getInt("tsxoa");
                tsvbxsts = rs.getInt("tsvbxlts");tscctt = rs.getInt("tscctt");
                tsbs = rs.getInt("tsbs");tstbkbtha = rs.getInt("tstbkbtha");
                tsmp = rs.getInt("tsmp");tscsgt = rs.getInt("tscsgt");
                
                
            }
            sldon.add(tsdon);sldon.add(tsce);sldon.add(tscf);
            sldon.add(tscb);sldon.add(tsct);sldon.add(tsld);
            sldon.add(tstd);sldon.add(tsxoa);sldon.add(tsvbxsts);
            sldon.add(tscctt);sldon.add(tsbs);sldon.add(tstbkbtha);
            sldon.add(tsmp);sldon.add(tscsgt);
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sldon;
    }
    
    //
   public ArrayList<Integer> checkListDon(ArrayList<Integer> listdon){
       if(listdon.isEmpty()){
            for(int i=0;i<15;i++){
                listdon.add(0);
            }
        }
       return listdon;
   }
   
   // Lấy tổng số đơn 
   public int getTotalHs(String ngaybatdau,String ngayketthuc){
       int total = 0;
       String whereSql = "";
       if(!ngaybatdau.equals("")){
           whereSql += " and tbl_don.D_Date >= str_to_date('"+ngaybatdau+"','%d-%m-%Y') ";
       }
       
       if(!ngayketthuc.equals("")){
           whereSql += " and tbl_don.D_Date <= str_to_date('"+ngayketthuc+"','%d-%m-%Y') ";
       }
       String sql = " select count(*) from tbl_don where 1=1 "+whereSql;
       connect = new DBConnect().dbConnect();
        try{
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       return total;
   }
    // Trả về thống kê hồ sơ chưa có tài khoản view
    public String viewTSHoCKTK(String ngaybatdau,String ngayketthuc){
        String table_view = "";
        ArrayList<Integer> tsDonNow = loadThongKeHS(ngayketthuc, ngayketthuc,false);
        ArrayList<Integer> tsDonDay = loadThongKeHS(ngaybatdau, ngayketthuc,false);
        
        ArrayList<Integer> tsOnNow = loadThongKeHS(ngayketthuc, ngayketthuc,true);
        ArrayList<Integer> tsOnDay = loadThongKeHS(ngaybatdau, ngayketthuc,true);
//        for(int i=0; i< tsDonDay.size();i++){
//            System.out.println(tsDonDay.get(i));
//        }
//        tsDonNow = checkListDon(tsDonNow);
//        tsDonDay = checkListDon(tsDonDay);
//        tsOnNow = checkListDon(tsOnNow);
//        tsOnDay = checkListDon(tsOnDay);
//        System.out.println(tsDonNow.size());System.out.println(tsDonDay.size());
//        System.out.println(tsOnNow.size());System.out.println(tsOnDay.size());
//        sldon.add(tsdon);sldon.add(tsce);sldon.add(tscf);
//                sldon.add(tscb);sldon.add(tsct);sldon.add(tsld);
//                sldon.add(tstd);sldon.add(tsxoa);sldon.add(tsvbxsts);
//                sldon.add(tscctt);sldon.add(tsbs);sldon.add(tstbkbtha);
//                sldon.add(tsmp);sldon.add(tscsgt);
        table_view += "<br>" +
                                "<table><tbody><tr><td>"+
                               "<div style=\"float:left;font-weight:bolder;width:380px;\">"+
                                "Tổng Số Hồ Sơ Ngày(GDĐB+CCTT) "+ngayketthuc+":<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(0)+" </strong>\n" +
                                "</div>\n" +
                                "<div style=\"font-weight:bolder;width:380px;\">\n" +
                                "CE:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(1)+";</strong>\n" +
                                "CF:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(2)+";</strong>\n" +
                                "CT:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(4)+";</strong>\n" +
                                "CB:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(3)+";</strong>\n" +
                                "</div>\n" +
                                "<div style=\"font-weight:bolder;width:380px;\">\n" +
                                "LĐ:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(5)+";</strong>\n" +
                                "TĐ:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(6)+";</strong>\n" +
                                "Xóa:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(7)+";</strong>\n" +
                                "VBXLTS:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(8)+";</strong>\n" +
                                "CCTT:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(9)+";</strong>\n" +
                                "BS:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(10)+";</strong>\n" +
                                "KB:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(11)+";</strong>\n" +
                                "Miễn Phí:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(12)+";</strong>						\n" +
                                "CSGT:<strong style=\"color:#FF0000\">&nbsp;"+tsDonNow.get(13)+";</strong>									\n" +
                                "</div>" +
                                "<div style=\"float:left;font-weight:bolder;width:380px; margin-top: 10px;\">\n" +
                                "Tổng Số Hồ Sơ Từ Ngày "+ngaybatdau+"Đến Ngày "+ngayketthuc+" :<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(0)+" </strong>\n" +
                                "</div>" +
                                "<div style=\"font-weight:bolder;width:380px;\">\n" +
                                "CE:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(1)+";</strong>\n" +
                                "CF:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(2)+";</strong>\n" +
                                "CT:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(4)+";</strong>\n" +
                                "CB:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(3)+";</strong>\n" +
                                "</div>\n" +
                                "<div style=\"font-weight:bolder;width:380px;\">\n" +
                                "LĐ:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(5)+";</strong>\n" +
                                "TĐ:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(6)+";</strong>\n" +
                                "Xóa:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(7)+";</strong>\n" +
                                "VBXLTS:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(8)+";</strong>\n" +
                                "CCTT:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(9)+";</strong>\n" +
                                "BS:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(10)+";</strong>\n" +
                                "KB:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(11)+";</strong>\n" +
                                "Miễn Phí:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(12)+";</strong>\n" +
                                "CSGT:<strong style=\"color:#FF0000\">&nbsp;"+tsDonDay.get(13)+";</strong>\n" +
                                "</div>\n" +
                                "</div></td>\n" +
                                "<td><div style=\"float:left;font-weight:bolder;width:380px;\">\n" +
                                "<div style=\"float:left;font-weight:bolder;width:380px;color:#00883C;\">\n" +
                                "Tổng Số Hồ Sơ Ngày(Online) "+ngayketthuc+":<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(0)+" </strong>\n" +
                                "</div>\n" +
                                "<div style=\"font-weight:bolder;width:380px;color:#00883C;\">\n" +
                                "LĐ:<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(5)+";</strong>\n" +
                                "TĐ:<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(6)+";</strong>\n" +
                                "Xóa:<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(7)+";</strong>\n" +
                                "VBXLTS:<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(8)+";</strong>\n" +
                                "CCTT:<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(9)+";</strong>\n" +
                                "BS:<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(10)+";</strong>\n" +
                                "KB:<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(11)+";</strong>\n" +
                                "Miễn Phí:<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(12)+";</strong>						\n" +
                                "CSGT:<strong style=\"color:#FF0000\">&nbsp;"+tsOnNow.get(13)+";</strong>												\n" +
                                "</div>\n" +
                                "<div style=\"float:left;font-weight:bolder;width:380px; margin-top: 25px;color:#00883C;\">\n" +
                                "Tổng Số Hồ Sơ Từ Ngày "+ngaybatdau+"Đến Ngày "+ngayketthuc+" :<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(0)+" </strong>\n" +
                                "</div>\n" +
                                "<div style=\"font-weight:bolder;width:380px;color:#00883C;\">\n" +
                                "LĐ:<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(5)+";</strong>\n" +
                                "TĐ:<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(6)+";</strong>\n" +
                                "Xóa:<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(7)+";</strong>\n" +
                                "VBXLTS:<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(8)+";</strong>\n" +
                                "CCTT:<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(9)+";</strong>\n" +
                                "BS:<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(10)+";</strong>\n" +
                                "KB:<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(11)+";</strong>\n" +
                                "Miễn Phí:<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(12)+";</strong>						\n" +
                                "CSGT:<strong style=\"color:#FF0000\">&nbsp;"+tsOnDay.get(13)+";</strong>										\n" +
                                "</div>\n" +
                                "</div></td><td>Tổng số đơn:<strong style=\"color:#FF0000\">&nbsp;"+getTotalHs(ngaybatdau, ngayketthuc)+" </strong>" +
                                "<div style=\"padding-left:20px;float:left;font-weight:bolder;width:380px;clear:both;\">" +
                                 "</td></tr></tbody></table>\n" ;
        return table_view;
    }
    
    //get page search bao co
    public int getPageSearchBaoco(String ngaybatdau,String ngayketthuc,String sobaoco,String sotaikhoan,String bnbd,int checktien){
        int page = 0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_baoco.BC_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_baoco.BC_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         if(!sobaoco.trim().equals("")){
             String[] sobc = sobaoco.split(",");
             if(sobc.length >1){
                 whereSql += " and tbl_baoco.BC_Number in ("+sobaoco+") ";
             }else{
                 whereSql += " and tbl_baoco.BC_Number like '%"+sobaoco+"%' ";
             }
         }
         if(!sotaikhoan.trim().equals("")){
             String[] sotk = sotaikhoan.split(",");
             if(sotk.length >1){
                 whereSql += " and tbl_baoco.BC_Number in ("+sotaikhoan+") ";
             }else{
                 whereSql += " and tbl_baoco.BC_Number like '%"+sotaikhoan+"%' ";
             }
         }
         if(checktien !=0){
             if(checktien ==1){
                 whereSql += " and tbl_baoco.BC_Thua >=0";
             }else if(checktien ==2){
                 whereSql += " and tbl_baoco.BC_Thua < 0 ";
             }
         }
         if(!bnbd.trim().equals("")){
             whereSql += " and tbl_baocokh.KH_ID in(select tbl_khachhang.KH_ID from tbl_khachhang where tbl_khachhang.KH_Name  like '%"+bnbd+"%')";
         }
         
         if(!sotaikhoan.trim().equals("")){
             String[] stks = sotaikhoan.split(",");
             if(stks.length >1){
                 whereSql += " and tbl_baocokh.KH_ID in(select tbl_khachhang.KH_ID from tbl_khachhang where  tbl_khachhang.KH_Account from in ("+sotaikhoan+"))";
             }else{
                 whereSql += " and tbl_baocokh.KH_ID in(select tbl_khachhang.KH_ID from tbl_khachhang where  tbl_khachhang.KH_Account from like '%"+sotaikhoan+"%' ) ";
             }
         }
        String sql = "select count(*) " +
                        "from tbl_baoco "
                + "LEFT JOIN tbl_baocokh ON tbl_baocokh.BC_ID = tbl_baoco.BC_ID\n" 
                + "where 1=1 "
                +whereSql+" ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                page = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if((page%10)==0 && page >0) page = page /10;
        else page =(page/10)+1;
        return page;
    }
    
    /// load thông báo phí
    public ArrayList<ArrayList<String>> loadThongBaoPhi(int month,int  year,String sohieu,String bnbd){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String whereSql = "";
        if(month >0 && month < 13){
            whereSql += " and MONTH(TBP_ngaygui) = "+month;
        }
        if(year >0){
            whereSql += " and YEAR(TBP_ngaygui) = "+year;
        }
        if(!sohieu.equals("")){
            whereSql += " and TBP_Number like '"+(sohieu)+"%'";
        }
        if(!bnbd.equals("")){
            whereSql += " and tbl_khachhang.KH_Name like '%"+bnbd+"%'";
        }
        String sql ="select distinct tbl_thongbaophi.TBP_ID as id,tbl_khachhang.KH_Name,tbl_thongbaophi.TBP_Number ,"
                + "count(distinct date_format(tbl_don.D_Date,'%m-%Y')) as sthang,\n" +
                            " group_concat(distinct tbl_guithuphi.D_ID ) as donid,tbl_don.KH_ID as khid "+
                            "from tbl_thongbaophi\n" +
                            "inner join (tbl_guithuphi inner join \n" +
                            "(tbl_don inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID)\n" +
                            "on tbl_don.D_ID = tbl_guithuphi.D_ID)\n" +
                            "on tbl_guithuphi.TBP_ID = tbl_thongbaophi.TBP_ID\n" +
                            "where 1=1 "+whereSql+" group by tbl_thongbaophi.TBP_ID order by tbl_thongbaophi.TBP_ID asc;";
//       System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
//            pstm.setInt(1, month);
//            pstm.setInt(2, year);
//            pstm.setString(3, sohieu);
             rs = pstm.executeQuery();
             while(rs.next()){
                 ArrayList<String> dt = new ArrayList<>();
                 String btp = rs.getString(2);
                 String tbpnumber = rs.getString(3);
                 String st = rs.getString(4);
                 String donids = rs.getString(5);
                 String tbpid = rs.getString("id");
                 String khid = rs.getString("khid");
                 dt.add(btp);dt.add(tbpnumber);dt.add(st);dt.add(donids);dt.add(tbpid);dt.add(khid);
                 data.add(dt);
             }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      return data;
    }
    
    // lấy thông báo phí
    public String returnTableTBP(int month,int year,String sohieu,String bnbd){
        //String monthSearch = year+"-"+month+"-";
        ArrayList<ArrayList<String>> data = loadThongBaoPhi(month, year, sohieu,bnbd);
        String tbody = "";
        int stt = 1;
        for(ArrayList<String> dt : data){
            tbody += "<tr title='Số Thông báo phí: "+(dt.get(1))+"'>";
             tbody += "<td>"+stt+"</td>";
            tbody += "<td class='td_click'>"+dt.get(0)+"<input type='text' class='input_donids' value='"+dt.get(3)+"' hidden></td>";
            tbody += "<td><input type='checkbox' value='"+dt.get(4)+"' class='tbp_checkbox'><input type='text' class='input_khid' value='"+dt.get(5)+"' hidden></td>";
        //    tbody += "<td>"+dt.get(1)+"</td>";
            tbody += "<td>"+dt.get(2)+"</td>";
            
            tbody += "</tr>";
            stt++;
        }
        return tbody;
    }
    
    // Load đơn bên thông báo phí
    public ArrayList<ArrayList<String>> loadDonTBP(String donids){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select distinct tbl_don.D_ID as donid,tbl_don.D_MDO as maonline,\n" +
                        "DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap,group_concat( distinct tbl_benbaodam.BDB_Name separator '&')  as benbaodam,\n" +
                        " tbl_don.DK_ID as dkid,tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,\n" +
                        "tbl_loainhan.LN_Short as loainhan,(select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID) as lephi \n" +
                        "from tbl_don\n" +
                        "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID \n" +
                        "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID\n" +
                        "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID\n" +
                        "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID\n" +
                        "inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID\n" +
                        "inner join tbl_guithuphi on tbl_guithuphi.D_ID = tbl_don.D_ID " +
                        " where tbl_guithuphi.D_ID in ("+donids+") group by tbl_don.D_ID\n" +
                            " order by tbl_guithuphi.GTP_ID desc;";
//        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
             rs = pstm.executeQuery();
             while(rs.next()){
                 ArrayList<String> dt = new ArrayList<>();
                 String donid =rs.getString("donid");
                 String maonline  = rs.getString("maonline");
                 String ngaynhap = rs.getString("ngaynhap");
                 String benbaodam = rs.getString("benbaodam");
                 String lephi = rs.getString("lephi");
                 String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, ngaynhap); 
                maloainhan = maloainhan.replaceAll("CSGT", "");
                 dt.add(donid);dt.add(maonline);dt.add(ngaynhap);dt.add(benbaodam);dt.add(lephi);dt.add(maloainhan);dt.add(loaidk);
                 data.add(dt);
             }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    } 
    
    // return table view tpb ddown
    public String returnTableTBPDon(String donids){
        String tbody  = "";
        ArrayList<ArrayList<String>> data =  loadDonTBP(donids);
        int stt = 1;
        
        for(ArrayList<String> dt : data){
//            dt.add(donid);dt.add(maonline);dt.add(ngaynhap);dt.add(benbaodam);dt.add(lephi);dt.add(maloainhan);
            String donid = dt.get(0);
            String maonline = dt.get(1);
            String ngaynhap = dt.get(2); String bbd = dt.get(3);String lephi = dt.get(4);
            String maloainhan = dt.get(5);
            tbody += "<tr style=\"height:25px;\">";
            tbody += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+stt+"</td>";
            tbody += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+dt.get(1)+"</td>";
       //     System.out.println("LOAIDK = "+loaidk);
            String loaidk = dt.get(6);
            if(loaidk.equals("CSGT")){
                 tbody += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+ngaynhap+"</td>";
                 tbody += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+bbd+"<span style=\"color:red\">(PL04)</span></td>";
            }else{
                 tbody += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+ngaynhap+"</td>";
                 if(!loaidk.equals("TT")){
                      tbody += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+bbd+"</td>";
                  }else{
                     int noCCtt = maonline.split(",").length;
                      tbody += "<td style=\"font-size:12px;border:1 #000000 solid;\"><span style=\"color:red\">"+noCCtt+"CC-TT</span></td>";
                 }

            }
             tbody += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+lephi+"</td>";
            tbody += "</tr>";
            stt++;
        }
        return tbody;
    }
    
    // Lấy thông báo phí number
    public int getTBPNumber(String ngaygui){
        int number = 0;
        String sql = "SELECT ifnull(MAX(tbl_thongbaophi.TBP_Number),0) "
                + "FROM qlhsdb.tbl_thongbaophi where tbl_thongbaophi.TBP_ngaygui like ?;";
        //System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, ngaygui+"%");
            rs = pstm.executeQuery();
            while(rs.next()){
                number = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return number;
    }
    
    // lưu thông báo phí
    public int saveTBp(int sotbp,String ngaygui,String ngayhh,int intbpid){
        int key = 0;
        String sql = "Insert into tbl_thongbaophi(TBP_Number,TBP_ngaygui,TBP_Ngayhethan,ITBP_ID) values(?,str_to_date(?,'%d-%m-%Y'),str_to_date(?,'%d-%m-%Y'),?)";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, sotbp);
            pstm.setString(2, ngaygui);
            pstm.setString(3, ngayhh);
            pstm.setInt(4, intbpid);
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if(rs.next()){
                 key = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return key;
    }
    
    // Lưu đơn thông báo phí
    public void saveDonTBP(String[] donids,int tbpid){
        String sql = "insert into tbl_guithuphi(D_ID,TBP_ID) values";
        int i=1;
        for(String donid : donids){
            if(i==1){
                sql += "("+donid+","+tbpid+")";
            }else{
                sql += ",("+donid+","+tbpid+")";
            }
            i++;
        }
        sql += ";";
        //System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    // Load thông báo phí đã lưu
    public ArrayList<ArrayList<String>> loadThongBaoPhi(int tbp_id){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, tbp_id);
            rs = pstm.executeQuery();
            while(rs.next()){
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // load bao co và biên lai
    public ArrayList<String> loadBaoCoVaBienLai(String dontpid){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select tbl_dontp.DTP_ID as dtpid,tbl_dontp.DTP_TDV as tendonvi,DTP_TDVDC as diachi, tbl_dontp.DTP_Sohieu as sohieu,\n" +
                        "tbl_dontp.DTP_Lydo as lydo, date_format(tbl_dontp.DTP_Date,'%d-%m-%Y') as ngaybienlai,\n" +
                        "tbl_dontp.DTP_Total as total,tbl_dontp.LTT_ID as loaithanhtoan,\n" +
                        "ifnull(tbl_baoco.BC_ID,'') as bcid,ifnull(date_format(tbl_baoco.BC_Date,'%d-%m-%Y'),'') as ngaybc,\n" +
                        "ifnull(tbl_baoco.BC_Number,'') as bcnumber,ifnull(tbl_baoco.BC_Total,'') as bctotal,ifnull(tbl_baoco.BC_Thua,'') as bcsodu,\n" +
                        "ifnull(tbl_baoco.BC_ND,'') as noidungbc ,\n" +
                        "(select tbl_khachhang.KH_Name from tbl_khachhang where tbl_khachhang.KH_ID= tbl_baocokh.KH_ID) as khname,\n" +
                        "(select tbl_khachhang.KH_Address from tbl_khachhang where tbl_khachhang.KH_ID= tbl_baocokh.KH_ID) as khadrress,\n" +
                        "(select tbl_khachhang.KH_Account from tbl_khachhang where tbl_khachhang.KH_ID= tbl_baocokh.KH_ID) as khaccount,tbl_baocokh.KH_ID as khid "+
                        "from tbl_dontp\n" +
                        "left join (tbl_thanhtoanbienlai left join \n" +
                        "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                        " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                        "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID "+
                        "where tbl_thanhtoanbienlai.DTP_ID in( ? );";
        System.out.println("---------------------------------------------------------------");
        System.out.println(dontpid);
        System.out.println(sql);
        System.out.println("---------------------------------------------------------------");
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, dontpid);
            rs = pstm.executeQuery();
            while(rs.next()){
                String dtpid = rs.getString("dtpid");
                String tendvi = rs.getString("tendonvi");
                String diachi = rs.getString("diachi");
                String sohieu = rs.getString("sohieu");
                String lydo = rs.getString("lydo");
                String ngaybienlai = rs.getString("ngaybienlai");
                String bienlaitotal = rs.getString("total");
                String loaithanhtoan = rs.getString("loaithanhtoan");
                String bcid = rs.getString("bcid");
                String ngaybc = rs.getString("ngaybc");
                String bcnumber  = rs.getString("bcnumber");
                String bctotal = rs.getString("bctotal");
                String bcsodu = rs.getString("bcsodu");
                String noidungbc = rs.getString("noidungbc");
                String khname  =rs.getString("khname");
                String khaddress = rs.getString("khadrress");
                String khaccount = rs.getString("khaccount");
                String khid = rs.getString("khid");
                data.add(dtpid);data.add(tendvi);data.add(diachi);data.add(sohieu);data.add(lydo);
                data.add(ngaybienlai);data.add(bienlaitotal);data.add(loaithanhtoan);data.add(bcid);
                data.add(ngaybc);data.add(bcnumber);data.add(bctotal);data.add(bcsodu);data.add(noidungbc);
                data.add(khname);data.add(khaddress);data.add(khaccount);data.add(khid);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Load thÔng báo phí đã lưu
    public ArrayList<String> loadTBPLuu(int id){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select  tbl_thongbaophi.TBP_Number as sotbp,date_format(tbl_thongbaophi.TBP_ngaygui,'%d-%m-%Y') as ngaytao,\n" +
                            "date_format(tbl_thongbaophi.TBP_Ngayhethan,'%d-%m-%Y') as ngayhh ,tbl_saveinfortbp.NBP_Trachnhiem as nguoictn,\n" +
                            "tbl_saveinfortbp.NLH_Lienhe as lienhe, tbl_chucvu.CV_Des as cvid,tbl_saveinfortbp.TK_Des as taikhoan \n" +
                                "from tbl_thongbaophi\n" +
                                "inner join tbl_saveinfortbp on tbl_saveinfortbp.ITBP_ID = tbl_thongbaophi.ITBP_ID\n" +
                                "inner join tbl_chucvu on tbl_chucvu.CV_ID = tbl_saveinfortbp.CV_ID "
                            + " where tbl_thongbaophi.TBP_ID = ? ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while(rs.next()){
                String sotbp = rs.getString("sotbp");
                String ngaytao = rs.getString("ngaytao");
                String ngayhh = rs.getString("ngayhh");
                String nguoictn = rs.getString("nguoictn");
                String lienhe = rs.getString("lienhe");
                String cvid = rs.getString("cvid");
                String taikhoan = rs.getString("taikhoan");
                data.add(sotbp);data.add(ngaytao);data.add(ngayhh);
                data.add(nguoictn);data.add(lienhe);data.add(cvid);data.add(taikhoan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    // Load đơn thông báo phí đã lưu
    public ArrayList<String> loadDonTBPLuu(int tblid){
        ArrayList<String> dt = new ArrayList<>();
        String sql = "select * from tbl_guithuphi where tbl_guithuphi.TBP_ID = ?;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, tblid);
             rs = pstm.executeQuery();
             while(rs.next()){
                dt.add(rs.getString(1));

             }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dt;
    }
    
    // load thông tin đơn đã lưu thông báo phí
    public String loadInforDonTBP(String donids){
        String table = "";
        String sql = "select DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap,"
                + " tbl_don.DK_ID as dkid,tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,\n" +
                            "tbl_loainhan.LN_Short as loainhan,"
                + "tbl_don.D_MDO as maonline,"
                + "group_concat(tbl_benbaodam.BDB_Name  SEPARATOR ',') as benbaodam,\n" +
        "tbl_lephi.LP_Price  as tongtien\n" +
        "from tbl_don \n"
                    + "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID \n" +
                    "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID\n" +
                    "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID\n" +
                    "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                    " inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID "+
            "where  tbl_don.D_ID in( "+donids+") group by tbl_don.D_ID ;";
        System.out.println(sql); 
        connect = new DBConnect().dbConnect();
        long tongtien = 0;
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i=1;
            while(rs.next()){
                String ngaynhap = rs.getString("ngaynhap");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, ngaynhap); 
                maloainhan = maloainhan.replaceAll("CSGT", "");
                String bbd = rs.getString("benbaodam");
                String maonline = rs.getString("maonline");
                if(maonline == null){
                    maonline = "0";
                }
                long sotien = rs.getLong("tongtien");
                tongtien += sotien;
                table += "<tr style=\"height:25px;\">";
                table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+i+"</td>";
                table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+ngaynhap+"</td>";
                System.out.println("LOAIDK = "+loaidk);
                if(loaidk.equals("CSGT")){
                     table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+maloainhan+"</td>";
                     table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+bbd+"<span style=\"color:red\">(PL04)</span></td>";
                }else{
                     table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+maonline+"</td>";
                     if(!loaidk.equals("TT")){
                          table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+bbd+"</td>";
                      }else{
                         int noCCtt = maonline.split(",").length;
                          table += "<td style=\"font-size:12px;border:1 #000000 solid;\"><span style=\"color:red\">"+noCCtt+"CC-TT</span></td>";
                     }
                    
                }
                 table += "<td style=\"font-size:12px;border:1 #000000 solid;\">"+sotien+"</td>";
                table += "</tr>";
                i++;
            }
            table += "<tr><td colspan='4'><span style='font-weight: bold;font-size:12px;border:1 #000000 solid;'>Tổng số tiền thanh toán</span></td><td><span style='font-weight: bold;'>"+tongtien+"</span></td></tr>";
            changeNumber = new ChangeNumberToText();
            String sotienStr = new ChangeNumberToText().convert(tongtien);
            table += "<tr><td colspan='5'><span style='font-weight: bold;font-size:12px;border:1 #000000 solid;'>Số tiền bằng chữ ( "+sotienStr+" đồng chẵn)</span></td></tr>";
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return table;
    }
    
    // lấy số thông báo phí
    public ArrayList<String> geTBP(int tbpid){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select TBP_Number,MONTH(tbl_thongbaophi.TBP_ngaygui),\n" +
                "date_format(tbl_thongbaophi.TBP_ngaygui,'%d-%m-%Y'),\n" +
                "date_format(tbl_thongbaophi.TBP_Ngayhethan,'%d-%m-%Y')"
                + " from tbl_thongbaophi where TBP_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, tbpid);
            rs = pstm.executeQuery();
            while(rs.next()){
              String  tbp_number = rs.getString(2)+rs.getString(1);
                String ngaygui = rs.getString(3);
                String ngayhh= rs.getString(4);
                data.add(tbp_number);data.add(ngaygui);data.add(ngayhh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    public ArrayList<Integer> getDonTBPByID(int tbpid){
        ArrayList<Integer> data = new ArrayList<>();
        String sql = "select D_ID from tbl_guithuphi where TBP_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, tbpid);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    
    
    // select Đơn ID thu phí chưa thu phí 
    public String loadDonidsCTP(String fromday,String endday,String manhan,String sothongbaophi,int loaidon,int bnbdid){
        String whereSql = "";
        if(!fromday.trim().equals("")){
            whereSql += " and tbl_don.D_Date >= STR_TO_DATE('"+fromday+"','%d-%m-%Y') ";
        }
        
        if(!endday.trim().equals("") ){
            whereSql += " and tbl_don.D_Date <= STR_TO_DATE('"+endday+"','%d-%m-%Y') ";
        }
        
        if(!manhan.trim().equals("") ){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+manhan.trim()+"%'";
            }
        }
        
        if(!sothongbaophi.trim().equals("") ){
            whereSql += " and tbl_don.D_manhan like '%"+sothongbaophi+"%' ";
        }
        if(loaidon !=0){
            whereSql += " and tbl_don.LD_ID = "+loaidon+" ";
        }
       String donids = "";
        String sql = " select tbl_don.D_ID  as donids\n" +
                            " from tbl_khachhang\n" +
                            " inner join tbl_don  on tbl_don.KH_ID = tbl_khachhang.KH_ID\n" +
                            " where tbl_don.D_isRemove = 0 and tbl_don.D_ID not in(SELECT tbl_dondatp.D_ID FROM tbl_dondatp ) and tbl_don.KH_ID = ? \n" +whereSql+
                            " group by tbl_don.D_ID order by tbl_don.D_ID asc;";
      //  System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bnbdid);
            rs = pstm.executeQuery();
            int i=0;
            while(rs.next()){
                if(i==0){
                    donids += rs.getString(1);
                }else{
                    donids += ","+rs.getString(1);
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return donids;
    }
    
    
    // load đơn theo đơn id
    public ArrayList<String> loadDonByID(int donid,Connection conn){
        ArrayList<String> dt = new ArrayList<>();
        String sql = "select ifnull(tbl_don.D_MDO,'') as madon,tbl_lephi.LP_Price as tongtien,tbl_don.DK_ID as loaidk, "
                + "DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')  as ngaynhap "
                + " from tbl_don  "
                + " inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID  "
                + "where tbl_don.D_ID = ? order by tbl_don.D_ID desc;";
        System.out.println(sql);
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                String mdo = rs.getString("madon");
                String tongtien = rs.getString("tongtien");
                int loaidk = rs.getInt("loaidk");
                String PL = "";
                if(loaidk ==3){
                    PL = " (PL 04)";
                }
                String ngaynhap = rs.getString("ngaynhap");
                dt.add(mdo);dt.add(tongtien);dt.add(PL);dt.add(ngaynhap);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dt;
    }
    
    // load ben bao dam
    public String loadBBDDon(int donid,Connection conn){
        String bbd = "";
        String sql = "select ifnull(group_concat(DISTINCT tbl_benbaodam.BDB_Name separator ' & '),'') as bbd\n" +
                            "from tbl_benbaodam where tbl_benbaodam.D_ID = ?;";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                bbd = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapDon.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bbd;
    }
    
    // load so hieu ben thu phi cho excel
    public ArrayList<ArrayList<String>> getSoHieuExcel(String dtpids,Connection conn){
        ArrayList<ArrayList<String>> data1 = new ArrayList<>();
        String sql = "select distinct tbl_dontp.DTP_ID, DTP_Sohieu,DTP_Total,DATE_FORMAT(tbl_dontp.DTP_Date,'%d-%m-%Y')  \n" +
                            "from tbl_dondatp \n" +
                            "inner join tbl_dontp on tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID  \n" +
                            "where tbl_dontp.DTP_ID in ("+dtpids+") \n"+ //+whereSql+
                            "order by tbl_dondatp.D_ID desc;";
        try {
            pstm = conn.prepareStatement(sql);
            rs= pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt =  new ArrayList<>();
                dt.add(rs.getString(1));dt.add(rs.getString(2));dt.add(rs.getString(3));dt.add(rs.getString(4));
                data1.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(null, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data1;
    }
    
    // Load tong tien so hieu excel
    public String getTongtienSoHieu(int dtpid,Connection conn){
        String total = "";
        String sql = "  select sum((select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID)) as tongtien\n" +
                            "  from tbl_don \n" +
                            "  inner join tbl_dondatp on tbl_dondatp.D_ID = tbl_don.D_ID\n" +
                            "  where tbl_dondatp.DTP_ID = ? and tbl_don.D_isRemove = 0 ;";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, dtpid);
            rs= pstm.executeQuery();
            while(rs.next()){
                total = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(null, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }
    // Lấy đơn thu phí id 
    public String loadDTPIDS(String ngaybatdau,String ngayketthuc,String name,String diachi,String sohieu){
        String dtpids = "";
         String whereSql = "";
         if(!ngaybatdau.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
         }
         if(!ngayketthuc.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
         }
         if(!sohieu.trim().equals("")){
             if(sohieu.split(",").length >1){
                 whereSql += " and tbl_dontp.DTP_Sohieu in("+sohieu+") ";
             }else{
                 whereSql += " and tbl_dontp.DTP_Sohieu  like '%"+sohieu+"%' ";
             }
             
         }
        String sql = "select distinct tbl_dontp.DTP_ID \n" +
                            "from tbl_dontp\n" +
                            "inner join tbl_dondatp on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID  \n" +
                            "where DTP_TDV = ? and DTP_TDVDC = ? " + whereSql+ " ;";
       // System.out.println(sql);
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, diachi);
            rs = pstm.executeQuery();
            int i=1;
            while(rs.next()){
                if(i==1){
                    dtpids = rs.getString(1);
                }else{
                    dtpids = ","+rs.getString(1);
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dtpids;
    }
    
    // Lấy thông tin thu phí theo tên, dia chỉ, ngay thang, số hiệu
    public ArrayList<ThuPhiBean> loadDTPInfor(String name,String diachi,String fromday,String today,String sohieu){
        ArrayList<ThuPhiBean> tbpList = new ArrayList<>();
        String whereSql  = "";
        if(!fromday.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date >= STR_TO_DATE('"+fromday+"','%d-%m-%Y') ";
         }
         if(!today.trim().equals("")){
             whereSql += " and tbl_dontp.DTP_Date <= STR_TO_DATE('"+today+"','%d-%m-%Y') ";
         }
         if(!sohieu.trim().equals("")){
             if(sohieu.split(",").length >1){
                 whereSql += " and tbl_dontp.DTP_Sohieu in("+sohieu+") ";
             }else{
                 whereSql += " and tbl_dontp.DTP_Sohieu  like '%"+sohieu+"%' ";
             }
             
         }
        String sql = "select tbl_dontp.DTP_Sohieu as sohieu,tbl_dontp.DTP_Total as tongtien, "
                + "DATE_FORMAT(tbl_dontp.DTP_Date,'%d-%m-%Y') as tbpdate,group_concat(tbl_dondatp.D_ID) as donids from tbl_dontp\n" +
                        "inner join tbl_dondatp on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID  \n" +
                        "where tbl_dontp.DTP_TDV = ? and  tbl_dontp.DTP_TDVDC = ? "+whereSql+" group by tbl_dontp.DTP_ID;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, diachi);
            rs = pstm.executeQuery();
            while(rs.next()){
                ThuPhiBean tbp = new ThuPhiBean();
                tbp.setSohieu(rs.getString("sohieu"));
                tbp.setTongtien(rs.getDouble("tongtien"));
                tbp.setDonids(rs.getString("donids"));
                tbp.setNgaytp(rs.getString("tbpdate"));
                tbpList.add(tbp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tbpList;
    }
    
    
    //lấy hóa đơn, ngày tháng, tiền và tổng số đơn id
    public ArrayList<ArrayList<String>> loadDTPInfor(String dtpids,Connection conn){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select distinct tbl_dontp.DTP_ID, DTP_Sohieu,DTP_Total,DATE_FORMAT(tbl_dontp.DTP_Date,'%d-%m-%Y'),\n" +
                    "group_concat(tbl_dondatp.D_ID)\n" +
                    "from tbl_dondatp \n" +
                    "inner join tbl_dontp on tbl_dontp.DTP_ID = tbl_dondatp.DTP_ID  \n" +
                    "where tbl_dontp.DTP_ID in ("+dtpids+") \n" +
                    "group by tbl_dontp.DTP_ID\n" +
                    "order by tbl_dontp.DTP_ID desc;";
        try {
            pstm = conn.prepareStatement(sql);
            rs= pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt =  new ArrayList<>();
                dt.add(rs.getString(1));dt.add(rs.getString(2));dt.add(rs.getString(3));dt.add(rs.getString(4));dt.add(rs.getString(5));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(null, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    
    // Lấy đơn ids đã thu phí search
    public String getDonidsDTP(String fromday,String today,String loaithanhtoan){
        String donids = "";
        String whereSql = "";
        if(!fromday.equals("")){
            whereSql += " and tbl_dontp.DTP_Date >= str_to_date('"+fromday+"','%d-%m-%Y') ";
        }
        if(!today.equals("")){
            whereSql += " and tbl_dontp.DTP_Date <= str_to_date('"+today+"','%d-%m-%Y') ";
        }
        if(!loaithanhtoan.equals("0")){
            whereSql += " and tbl_dontp.LTT_ID in  ("+ loaithanhtoan+") ";
        }
        String sql = "select tbl_dondatp.D_ID from tbl_dontp \n" +
                        "inner join tbl_dondatp on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID \n" +
                        "where 1=1 \n" +whereSql+
                        " group by tbl_dondatp.D_ID order by tbl_dondatp.D_ID desc;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i=0;
            while(rs.next()){
                if(i==0){
                    donids = rs.getString(1);
                }else{
                    donids += ","+rs.getString(1);
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return donids;
    }
    
    // Lấy dữ liệu đơn xuất excel file cho cục
    public ArrayList<ArrayList<String>> loadDuLieuchoCuc(int loaithinhnhan,String donids){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String whereSql = "";
        if(loaithinhnhan !=0){
            whereSql += " tbl_don.DK_ID = "+loaithinhnhan;
        }
        
        String sql = "select tbl_khachhang.KH_Name as khname, tbl_khachhang.KH_Account as account,tbl_don.D_MDO as maonline"
                + ", DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap,"
                + "(select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID) as giatien \n" +
                        " from tbl_don "+
                        "inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_don.KH_ID\n" +
                        "where tbl_don.D_ID in ("+donids+") \n" +whereSql+
                        " group by tbl_don.D_ID\n" +
                        " order by tbl_don.D_ID desc;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString("khname"));
                dt.add(rs.getString("account"));
                dt.add(rs.getString("ngaynhap"));
                dt.add(rs.getString("maonline"));
                dt.add(rs.getString("giatien"));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Xóa đơn đã thu phí
    public int DeleteDonTP(int donid){
        int result = 0;
        String sql = "delete from tbl_dondatp where D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            result = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    // cập nhật lại thông tin đơn thu phí
    public int updateThuPhi(String tendonvi,String diachi,String lydo,int loaithanhtoan,int tongtien,String sohieu,String ngaytp,int dtpid){
        int result = 0;
        String sql = "update tbl_dontp set DTP_TDV = ?,DTP_TDVDC = ?,DTP_Sohieu =?,DTP_Lydo = ?"
                + " ,DTP_Date = str_to_date(?,'%d-%m-%Y'), DTP_Total = ?,LTT_ID = ?"
                + " where DTP_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, tendonvi);pstm.setString(2, diachi);pstm.setString(3, sohieu);
            pstm.setString(4, lydo);pstm.setString(5, ngaytp);pstm.setInt(6, tongtien);
            pstm.setInt(7, loaithanhtoan); pstm.setInt(8, dtpid);
            result = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    // update thông tin báo có
    public int updateBaoCo(String BC_Date,String BC_Number,int BC_Total,int BC_Thua,String BC_ND,int BC_ID,String nguoipl,int isreturnBank,Connection conn){
        int result = 0;
        String sql = "update tbl_baoco set BC_Date = str_to_date(?,'%d-%m-%Y'),BC_Number = ?,BC_Total = ?,"
                + "BC_Thua = ?,BC_ND = ?,BC_Nguoiphatlenh = ?,BC_IsreturnBank = ? where BC_ID = ?;";
     //   connect = new DBConnect().dbConnect();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, BC_Date);pstm.setString(2, BC_Number);
            pstm.setInt(3, BC_Total);pstm.setInt(4, BC_Thua);pstm.setString(5, BC_ND);
            pstm.setString(6, nguoipl);pstm.setInt(7, isreturnBank);pstm.setInt(8, BC_ID);
            result= pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
   
    // update baoco khid
    public void updateBCKH(int bcid,int khid,Connection conn){
        String sql = "update tbl_baocokh set KH_ID = ? where BC_ID = ?";
       // connect = new DBConnect().dbConnect();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, khid);
            pstm.setInt(2, bcid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // xoas kh bao co
    public void deleteBCKH(int bcid,int khid,Connection conn){
        String sql = "DELETE FROM tbl_baocokh WHERE BC_ID = ? and KH_ID = ?;";
        //connect = new DBConnect().dbConnect();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, bcid);
            pstm.setInt(2, khid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closePSRS(pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // tìm kiêm báo có và khid
    public boolean selectBCKH(int bcid,int khid){
        boolean check  = false;
        String sql = "select * from tbl_baocokh WHERE BC_ID = ? and KH_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bcid);
            pstm.setInt(2, khid);
            rs = pstm.executeQuery();
            while(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    // 
    // search báo có còn tiền thừa
    public ArrayList<ArrayList<String>> searchBC(String ngaythang,String sobc,String khids,String nguoipl,int page){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String whereSql = "";
        if(!ngaythang.trim().equals("")){
            whereSql += " and  tbl_baoco.BC_Date >= str_to_date('"+ngaythang+"','%d-%m-%Y') ";
        }
        if(!sobc.trim().equals("")){
            if(sobc.split(",").length >1){
                whereSql += " and tbl_baoco.BC_Number  in ("+sobc+") ";
            }else{
                whereSql += " and tbl_baoco.BC_Number  like '%"+sobc+"%' ";
            }
            
        }
        if(!khids.trim().equals("")){
            whereSql += " and tbl_baocokh.KH_ID  in( "+khids+" )";
        }
        if(!nguoipl.trim().equals("")){
            whereSql += " and tbl_baoco.BC_Nguoiphatlenh  like '%"+nguoipl+"%' ";
        }
        String sql = "SELECT DISTINCT tbl_baoco.BC_ID as bcid,DATE_FORMAT(tbl_baoco.BC_Date,'%d-%m-%Y') as bcday,tbl_baoco.BC_Number as bcnumber,ifnull(tbl_baoco.BC_Nguoiphatlenh,'') as nguoipl,"
                + "tbl_baoco.BC_Thua as bcthua, ifnull((select tbl_khachhang.KH_Name FROM tbl_khachhang where KH_ID = tbl_baocokh.KH_ID),'') as khname ,"
                + " ifnull((select tbl_khachhang.KH_Account FROM tbl_khachhang where KH_ID = tbl_baocokh.KH_ID ),'') as account, tbl_baoco.BC_ND as ndbc"
                + " FROM tbl_baoco LEFT JOIN tbl_baocokh ON tbl_baocokh.BC_ID = tbl_baoco.BC_ID "
                + " where tbl_baoco.BC_Thua >0 "+whereSql+" order by tbl_baoco.BC_ID asc limit "+(page-1)*10+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString("bcid"));
                dt.add(rs.getString("bcday"));
                dt.add(rs.getString("bcnumber"));
                dt.add(rs.getString("nguoipl"));
                dt.add(rs.getString("bcthua"));
                dt.add(rs.getString("khname"));
                dt.add(rs.getString("account"));
                dt.add(rs.getString("ndbc"));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // get Donid frombc id
    public ArrayList<String> getKhID(int bcid){
        ArrayList<String> kh = new ArrayList<>();
        String sql = "SELECT tbl_khachhang.KH_ID as khid, ifnull(tbl_khachhang.KH_Name,'') as khname,ifnull(tbl_khachhang.KH_Account,'') account,ifnull(KH_Address,'') as khaddress FROM tbl_baocokh "
                + "inner join tbl_khachhang on tbl_khachhang.KH_ID = tbl_baocokh.KH_ID"
                + "  where tbl_baocokh.BC_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bcid);
            rs= pstm.executeQuery();
            while(rs.next()){
                kh.add(rs.getString(2));
                kh.add(rs.getString(3));
                kh.add(rs.getString(4));
                kh.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return kh;
    }
    
    // lấy view search báo cso
    public String viewSearchBC(String ngaythang,String sobc,String  khids,String nguoipl,int page){
        String tbody = "";
        ArrayList<ArrayList<String>> bcList = searchBC(ngaythang, sobc, khids, nguoipl,page);
            for(ArrayList<String> dt : bcList){
           //     int bcid = Integer.parseInt(dt.get(0));
          //      ArrayList<String> khInfor = getKhID(bcid);
//                String name = "";
//                String khaccount = "";
//                if(!khInfor.isEmpty()){
//                    name = khInfor.get(0);
//                    khaccount = khInfor.get(1);
//                }
                tbody += "<tr>";
                tbody += "<td>"+dt.get(1)+"</td>";
                tbody += "<td>"+dt.get(2)+"</td>";
                tbody += "<td>"+dt.get(3)+"</td>";
                tbody += "<td>"+dt.get(7)+"</td>";
                tbody += "<td>"+new ChangeNumberToText().priceWithDecimal(Double.valueOf( dt.get(4)))+"</td>";
                tbody += "<td>"+dt.get(5)+"</td>";
                tbody += "<td>"+dt.get(6)+"</td>";
                tbody += "<td><input type='checkbox' class='cb_searchbc' value='"+dt.get(0)+"'</td>";
                tbody += "</tr>";
            }
        return tbody;
    }
    
    // lấy số trang báo có còn dư
    public int getPageSearchBCLuu(String ngaythang,String sobc,String  khids,String nguoipl){
        int total =1;
        String whereSql = "";
        if(!ngaythang.trim().equals("")){
            whereSql += " and  tbl_baoco.BC_Date >= str_to_date('"+ngaythang+"','%d-%m-%Y') ";
        }
        if(!sobc.trim().equals("")){
            if(sobc.split(",").length >1){
                whereSql += " and tbl_baoco.BC_Number  in ("+sobc+") ";
            }else{
                whereSql += " and tbl_baoco.BC_Number  like '%"+sobc+"%' ";
            }
            
        }
        if(!khids.trim().equals("")){
            whereSql += " and tbl_baocokh.KH_ID  in( "+khids+" )";
        }
        if(!nguoipl.trim().equals("")){
            whereSql += " and tbl_baoco.BC_Nguoiphatlenh  like '%"+nguoipl+"%' ";
        }
        String sql = "SELECT count(*) "
                + " FROM tbl_baoco "
                + " where tbl_baoco.BC_Thua >0 "+whereSql+" order by tbl_baoco.BC_Date asc;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs= pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (total/10)+1;
    }
    // Lấy báo có thông tin theo bcid
    public ArrayList<ArrayList<String>> getBCInforByID(String bcids){
        ArrayList<ArrayList<String>> bcInforList = new ArrayList<>();
        String sql = "SELECT tbl_baoco.BC_ID as bcid,DATE_FORMAT(tbl_baoco.BC_Date,'%d-%m-%Y') as bcday,tbl_baoco.BC_Number as bcnumber,"
                + "tbl_baoco.BC_Nguoiphatlenh as nguoipl,tbl_baoco.BC_Thua as bcthua,tbl_baoco.BC_Total as total, tbl_baoco.BC_ND as ndbc "
                + " FROM tbl_baoco "
                + " where tbl_baoco.BC_ID in ("+bcids+")  order by tbl_baoco.BC_Date asc;";
     //   System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString("bcid"));
                dt.add(rs.getString("bcday"));
                dt.add(rs.getString("bcnumber"));
                dt.add(rs.getString("nguoipl"));
                dt.add(rs.getString("bcthua"));
                dt.add(rs.getString("total"));
                dt.add(rs.getString("ndbc"));
                bcInforList.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bcInforList;
    }
    // get khid báo có
    
    // lấy view cóa báo có
    public String viewXoaBC(String bcids){
        String tbody = "";
        ArrayList<ArrayList<String>> bcList = getBCInforByID(bcids);
        for(ArrayList<String> bc : bcList){
                tbody += "<tr>";
                tbody += "<td>"+bc.get(1)+"</td>";
                tbody += "<td>"+bc.get(2)+"</td>";
                tbody += "<td>"+bc.get(3)+"</td>";
                tbody += "<td>"+bc.get(4)+"</td>";
                ArrayList<String> kh = getKhID(Integer.parseInt(bc.get(0)));
                String name = "";
                String khaccount  = "";
                if(!kh.isEmpty()){
                    name = kh.get(0);
                    khaccount = kh.get(1);
                }
                tbody += "<td>"+name+"</td>";
                tbody += "<td>"+khaccount+"</td>";
                tbody += "<td><img src='./images/document_delete.png' title='Xóa báo có' class='xoa_bc_img' id='"+bc.get(0)+"'></td>";
                tbody += "</tr>";
            }
        return tbody;
    }
    
    // lưu báo có và biên lai
    public void insertBCnBL(int bcid,int blid,Connection conn){
        String sql = "insert into tbl_thanhtoanbienlai(BC_ID,DTP_ID) values(?,?);";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, bcid);
            pstm.setInt(2, blid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // lay so dau ki trich lap
    public void laySoDauKyTrichLap(){
        String sql = "";
        
    }
    // lay so trich lap
    public void trichLap(Connection conn){
        String sql = "";
        try {
            pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // lấy tổng hợp công nợ 2
    public ArrayList<ArrayList<String>> getListTHCN(String khids,String ngaybatdau,String ngayketthuc){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select tbl_don.KH_ID as khid,sum( tbl_lephi.LP_Price ) as tongtien,\n" +
                "group_concat(distinct tbl_don.D_ID separator ',') as ids,\"sodauky\" as title \n" +
                "from tbl_don \n" +
                " left join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                "where tbl_don.D_ID not in (select tbl_dondatp.D_ID from tbl_dondatp ) and tbl_don.D_isRemove = 0 \n" +
                " and tbl_don.D_Date < str_to_date('"+ngayketthuc+"','%d-%m-%Y') \n" +
                " and tbl_don.KH_ID in( "+khids+")\n" +
                " union\n" +
                "select tbl_don.KH_ID as khid,sum(tbl_lephi.LP_Price  ) as tongtien,\n" +
                "group_concat(tbl_don.D_ID separator ',') as ids ,\"sophaithu\"\n" +
                "from tbl_don \n" +
                " left join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                "where tbl_don.D_ID not in (select tbl_dondatp.D_ID from tbl_dondatp )\n" +
                "and tbl_don.D_Date between str_to_date('"+ngaybatdau+"','%d-%m-%Y') and str_to_date('"+ngayketthuc+"','%d-%m-%Y')\n" +
                " and tbl_don.KH_ID in(  "+khids+")\n" +
                "group by tbl_don.KH_ID\n" +
                "union\n" +
                "select tbl_don.KH_ID as khid,sum(tbl_baoco.BC_Thua) as tongtien,\n" +
                "group_concat( distinct tbl_baoco.BC_ID) as ids ,\"tongbaoco\"\n" +
                "from tbl_dondatp\n" +
                "left join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                "left join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID)\n" +
                "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID \n" +
                " where tbl_don.KH_ID in(  "+khids+")\n" +
                "   and tbl_don.D_Date <= str_to_date('"+ngayketthuc+"','%d-%m-%Y')  and tbl_don.D_isRemove = 0 \n" +
                "group by tbl_don.KH_ID having sum(tbl_baoco.BC_Thua) > 0\n" +
                "union\n" +
                "select tbl_don.KH_ID as khid,sum( tbl_lephi.LP_Price ) as tongtien,\n" +
                "group_concat(tbl_don.D_ID separator ',') as ids ,\"sodathu\"\n" +
                "from tbl_don \n" +
                " left join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                "where tbl_don.D_ID in (select tbl_dondatp.D_ID from tbl_dondatp )\n" +
                "and tbl_don.D_Date between str_to_date('"+ngaybatdau+"','%d-%m-%Y') and str_to_date('"+ngayketthuc+"','%d-%m-%Y')\n" +
                " and tbl_don.KH_ID in(  "+khids+")\n" +
                "group by tbl_don.KH_ID\n" +
                "union \n" +
                "select tbl_don.KH_ID as khid,sum(tbl_lephi.LP_Price  ) as tongtien,\n" +
                "group_concat(tbl_don.D_ID separator ',') as ids,\"socuoiky\" \n" +
                "from tbl_don \n" +
                " left join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
                "where tbl_don.D_ID not in (select tbl_dondatp.D_ID from tbl_dondatp ) and tbl_don.D_isRemove = 0 \n" +
                "and tbl_don.D_Date <= str_to_date('"+ngayketthuc+"','%d-%m-%Y')\n" +
                " and tbl_don.KH_ID in(  "+khids+")\n" +
                "group by tbl_don.KH_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                String khid = rs.getString("khid");
                String tongtien = rs.getString("tongtien");
                String ids = rs.getString("ids");
                String loai = rs.getString("title");
                ArrayList<String> dt = new ArrayList<>();
                dt.add(khid);dt.add(tongtien);dt.add(ids);dt.add(loai);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // return view tong hop cong no
    public String viewTHCN(String ngaybatdau,String ngayketthuc,int page){
        String table = "";
        ArrayList<ArrayList<String>> khList = getKHang(ngayketthuc,page);
        String khids = "";
        for(ArrayList<String> khang : khList){
            if(khids.equals("")){
                khids =  khang.get(0);
            }else{
                khids += ","+khang.get(0);
            }
        }
        ArrayList<ArrayList<String>> data = getListTHCN(khids, ngaybatdau, ngayketthuc);
        int stt = 1;
        for(ArrayList<String> khang : khList){
            table += "<tr>";
            table += "<td>"+stt+"</td>";
            table += "<td>"+khang.get(1)+"</td>";
            table += "<td>"+khang.get(2)+"</td>";
            String[] daukyArr = {"0","0"};
            String[] phaithuArr = {"0","0"};
            String[] dathuArr = {"0","0"};
            String[] cuoikyArr = {"0","0"};
            String[] baocoArr = {"0","0"};
            for(ArrayList<String> dt : data){
                if(dt.get(0).equals(khang.get(0))){
                    switch(dt.get(3)){
                        case "sodauky" : daukyArr[0] = dt.get(1);daukyArr[1] = dt.get(2);
                            break;
                        case "sophaithu" : phaithuArr[0] = dt.get(1);phaithuArr[1] = dt.get(2);
                            break;
                        case "tongbaoco" : baocoArr[0] = dt.get(1);baocoArr[1] = dt.get(2);
                            break;
                        case "sodathu" : dathuArr[0] = dt.get(1);dathuArr[1] = dt.get(2);
                            break;
                        case "socuoiky" : cuoikyArr[0] = dt.get(1);cuoikyArr[1] = dt.get(2);
                            break;
                    }
                }
            }
            table += "<td>"+daukyArr[0]+"</td>";
            table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+daukyArr[1]+")'></td>";
            table += "<td>"+phaithuArr[0]+"</td>";
            table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+phaithuArr[1]+")'></td>";
            table += "<td>"+dathuArr[0]+"</td>";
            table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+dathuArr[1]+")'></td>";
            table += "<td>"+cuoikyArr[0]+"</td>";
            table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+cuoikyArr[1]+")'></td>";
            table += "<td>"+baocoArr[0]+"</td>";
            table += "<td><img src='./images/document_view.png' onclick='viewChiTiet("+baocoArr[1]+")'></td>";
            table += "</tr>";
            stt++;
        }
        return table;
    }
    
    // lấy dữ liệu thông báo phí theo tbpid
    public ArrayList<ThongBaoPhiBean> getDataTbp(String tbpids){
        ArrayList<ThongBaoPhiBean> data = new ArrayList<>();
        String sql = "select concat(MONTH(tbl_thongbaophi.TBP_ngaygui),tbl_thongbaophi.TBP_Number) as sotbp,"
                + "DATE_FORMAT(tbl_thongbaophi.TBP_ngaygui,'%d-%m-%Y') as ngaygui,DATE_FORMAT(tbl_thongbaophi.TBP_Ngayhethan,'%d-%m-%Y') as ngayhh"
                + ",tbl_saveInforTBP.NBP_Trachnhiem as nguoictn, group_concat(tbl_guithuphi.D_ID) as donids, tbl_saveInforTBP.TK_Des as taikhoan"
                + " from tbl_thongbaophi\n" +
            "left join tbl_saveInforTBP on tbl_saveinfortbp.ITBP_ID = tbl_thongbaophi.ITBP_ID\n" +
                " inner join tbl_guithuphi on tbl_guithuphi.TBP_ID = tbl_thongbaophi.TBP_ID "+
            "where tbl_thongbaophi.TBP_ID in ("+tbpids+") group by tbl_thongbaophi.TBP_ID ;";
        //System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ThongBaoPhiBean tbpbean = new ThongBaoPhiBean();
                String sotbp = rs.getString("sotbp");
                String ngaygui = rs.getString("ngaygui");
                String ngayhh = rs.getString("ngayhh");
                String nguoictn = rs.getString("nguoictn");
                String donids = rs.getString("donids");
                String taikhoan = rs.getString("taikhoan");
                tbpbean.setSotbp(sotbp);tbpbean.setNgaybp(ngaygui);tbpbean.setNgayhh(ngayhh);
                tbpbean.setNguoictn(nguoictn);tbpbean.setDonids(donids);tbpbean.setTaikhoan(taikhoan);
                data.add(tbpbean);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // lấy số báo có theo biên lai
    public ArrayList<ArrayList<String>> getBaoCoByBLID(int bienlaiid){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select tbl_baoco.BC_ID as bcid,BC_Number as bcnumber from tbl_baoco\n" +
                    "inner join tbl_thanhtoanbienlai on tbl_thanhtoanbienlai.BC_ID = tbl_baoco.BC_ID\n" +
                    "where tbl_thanhtoanbienlai.DTP_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bienlaiid);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString("bcid"));
                dt.add(rs.getString("bcnumber"));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // taọ view sau khi lấy báo có by bienlaiid
    public String viewBaoCoByBLID(int bienlaiid){
        String option = "";
        ArrayList<ArrayList<String>> data = getBaoCoByBLID(bienlaiid);
        for(ArrayList<String> dt : data){
            option += "<option value='"+dt.get(0)+"' >"+dt.get(1)+"</option>";
        }
        return option;
    }
}
