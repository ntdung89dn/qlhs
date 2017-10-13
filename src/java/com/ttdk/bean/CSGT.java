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
 * @author ntdung
 */
public class CSGT {
    
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    public CSGT(){}
    
    // Lấy tổng số trang csgt
    public int getPageCSGT(){
        int total = 0;
        String sql = "select count(tbl_csgtname.CSN_ID) from tbl_csgtname;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (total/10)+1;
    }
    
    // Lưu CSGT
    public void addCSGT(int cityid,String name,String[] diachi,String bienso){
        int keyname = addCSGTName(name,cityid);
        for(String dc : diachi){
            addCSGTDiachi(keyname, dc);
        }
        String[] biensoList = bienso.split(",");
        for(String bs : biensoList){
            int bsNo = Integer.parseInt(bs);
            addBienSo(bsNo, cityid);
        }
    }
    
    // Lưu CSGT tên
    private int addCSGTName(String name,int cityid){
        int nameid = 0;
        String sql = "insert into tbl_csgtname(CSN_Name,C_ID) values(?,?);";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, name);
            pstm.setInt(2, cityid);
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                nameid = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nameid;
    }
    
    // Lưu CSGT địa chỉ
    private void addCSGTDiachi(int nameid, String diachi){
        String sql = "insert into tbl_csgtdiachi(CSDC_Diachi,CSN_ID) values(?,?);";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, diachi);
            pstm.setInt(2, nameid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Lấy dữ liệu tên thành phố
    public String getCityData(){
        //ArrayList<CityBean> cityList = new ArrayList<>();
        String data = "";
        String sql = "select C_ID as cityid, C_Name as cityname from tbl_city;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                CityBean cb = new CityBean();
                cb.setName(rs.getString("cityname"));
                cb.setCityid(rs.getInt("cityid"));
                data += "<option value='"+rs.getInt("cityid")+"'>"+rs.getString("cityname")+"</option>";
               // cityList.add(cb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Lấy dữ liệu biển số    
    private ArrayList<Integer> getBienso(int city){
         ArrayList<Integer> bsList = new ArrayList<>();
        String sql = "select BS_Number as bienso from tbl_bienso where C_ID = ? ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, city);
            rs = pstm.executeQuery();
            while(rs.next()){
                bsList.add(rs.getInt("bienso"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bsList;
    }
    
    // Nhập dữ liệu biển số
    private int insertBienso(int bienso,int cityid){
        String sql = "insert into tbl_bienso(BS_Number,C_ID) values(?,?);";
        connect = new DBConnect().dbConnect();
        int check = 0;
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bienso);
            pstm.setInt(2, cityid);
            check = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    // Kiểm tra và lưu biển số
    public void addBienSo(int bienso,int cityid){
        ArrayList<Integer> bsList = getBienso(cityid);
       // System.out.println(bsList.size());
        int bsCheck = -1;
        if(bsList.isEmpty()){
            insertBienso(bienso, cityid);
        }else{
            for(int bsoCheck : bsList){
             //   System.out.println("_____");
                if(bsoCheck != bienso ){
                    bsCheck = bienso;
              //      System.out.println("A");
                }else{
                    bsCheck = -1;
             //       System.out.println("B");
                    break;
                }
            }
        //    System.out.println("C");
            if(bsCheck != -1){
                int t = insertBienso(bienso, cityid);
              //  System.out.println(t);
            }
        }
        
    }
    
    // Lấy danh sách Tên gửi 
    private ArrayList<ArrayList> getCSGTName(int cityid){
        ArrayList<ArrayList> nameList = new ArrayList<>();
        String sql = "select distinct CSN_ID as csnid, tbl_csgtname.CSN_Name as csgtname from tbl_csgtname\n" +
                            "inner join tbl_city on tbl_city.C_ID = tbl_csgtname.C_ID\n" +
                            "where tbl_city.C_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, cityid); 
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList list = new ArrayList();
                list.add(rs.getInt("csnid"));
                list.add(rs.getString("csgtname"));
                nameList.add(list);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nameList;
    }
    // Lấy địa chỉ danh sách theo csgt name
    private ArrayList<ArrayList> getCSGTDC(int csnid){
        ArrayList<ArrayList> dcList = new ArrayList<>();
        String sql = "select tbl_csgtdiachi.CSDC_ID as dcid,tbl_csgtdiachi.CSDC_Diachi as csgtdc from tbl_csgtdiachi \n" +
                        "inner join tbl_csgtname on tbl_csgtname.CSN_ID = tbl_csgtdiachi.CSN_ID\n" +
                        "where tbl_csgtname.CSN_ID =?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, csnid);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList dc = new ArrayList();
                dc.add(rs.getString("dcid"));
                dc.add(rs.getString("csgtdc"));
                
                dcList.add(dc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dcList;
    } 
    // Lấy ID của city
    public ArrayList<CityBean> getCity(){
        ArrayList<CityBean> cityList = new ArrayList<>();
         connect = new DBConnect().dbConnect();
         String sql = "select C_ID as cityid, C_Name as cityname from tbl_city;";
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                CityBean cb = new CityBean();
                cb.setName(rs.getString("cityname"));
                cb.setCityid(rs.getInt("cityid"));
                cityList.add(cb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cityList;
    }
    // Lấy thành phố có csgt
    private ArrayList<CityBean> getTpcsgt(){
        ArrayList<CityBean> cityList = new ArrayList<>();
        String sql = "select tbl_city.C_ID as cityid,tbl_city.C_Name as cityname \n" +
                        "from tbl_csgtname\n" +
                        "inner join ( tbl_city inner join tbl_bienso on tbl_city.C_ID = tbl_bienso.C_ID)on tbl_city.C_ID = tbl_csgtname.C_ID\n" +
                        "inner join tbl_csgtdiachi on tbl_csgtdiachi.CSN_ID = tbl_csgtdiachi.CSN_ID\n" +
                        "group by tbl_city.C_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                CityBean cb = new CityBean();
                cb.setName(rs.getString("cityname"));
                cb.setCityid(rs.getInt("cityid"));
                cityList.add(cb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cityList;
    }
    // Lấy danh sách gửi csgt
    public String getCsgtInfor(){
        String table = "";
        ArrayList<CityBean> cityList = getTpcsgt();
        for(CityBean cb : cityList){
            String cityname = cb.getName();
             ArrayList<ArrayList> nameList = getCSGTName(cb.getCityid());
             int rowspan = nameList.size();
            table += "<tr>";
            table += "<th rowspan='"+nameList.size()+"' >"+cityname+"</th>";
            
           int i=0;
            for(ArrayList namedt : nameList){
                if(i==0){
                    table += "<td>"+namedt.get(1)+"</td>";
                    ArrayList<ArrayList> diachi = getCSGTDC((int)namedt.get(0));
                      table += "<td><ul>";
                      String dcid = "";
                      int countDC = 0;
                   for(ArrayList dc : diachi){
                       table += "<li>"+dc.get(1)+"</li>";
                       if(countDC ==0){
                           dcid += diachi.get(0);
                       }else{
                           dcid += ","+diachi.get(0);
                       }
                       countDC++;
                   }
                    ArrayList<Integer> bsoList = getBienso(cb.getCityid());
                    String bienso = "";
                    int bsStt = 0;
                    for(int bs : bsoList){
                        if(bsStt ==0){
                            bienso += bs;
                        }else{
                            bienso += ","+bs;
                        }
                        bsStt++;
                    }
                    table += "</ul><input type='text' value='"+dcid+"' hidden></td>";
                    table += "<td rowspan='"+rowspan+"'>"+bienso+"</td>";
                    i++;
                    
                    table +="<td><img src='./images/document_edit.png' data-toggle='modal' onclick='editCSGT("+namedt.get(0)+")'></td>\n" +
                        "<td ><img src='./images/document_delete.png' onclick='delCSGT("+namedt.get(0)+")'></td></tr>";
                }else{
                    table += "<tr><td>"+namedt.get(1)+"</td>";
                    ArrayList<ArrayList> diachi = getCSGTDC((int)namedt.get(0));
                      table += "<td><ul>";
                      String dcid = "";
                      int countDC = 0;
                   for(ArrayList dc : diachi){
                       table += "<li>"+dc.get(1)+"</li>";
                       if(countDC ==0){
                           dcid += diachi.get(0);
                       }else{
                           dcid += ","+diachi.get(0);
                       }
                       countDC++;
                   }
               
                    table += "</ul><input type='text' value='"+dcid+"' hidden></td>";
                    table +="<td><img src='./images/document_edit.png' data-toggle='modal' onclick='editCSGT("+namedt.get(0)+")'></td>\n" +
                        "<td ><img src='./images/document_delete.png' onclick='delCSGT("+namedt.get(0)+")'></td></tr>";
                }
                
            }
             
        }
       //  System.out.println(table);
        return table;
    }
    
    // Load thông tin của cSGT
    private CSGTBean loadCsgtById(int nameid){
        CSGTBean csgt = new CSGTBean();
        String sql = "select tbl_csgtname.CSN_ID as nameid,tbl_csgtname.CSN_Name as csgtname,tbl_city.C_ID as cityid, "
                + "group_concat(distinct tbl_bienso.BS_Number separator ',') as bienso,"
                + "group_concat(distinct tbl_bienso.BS_ID separator ',') as bsid "
                + "from tbl_csgtname\n" +
                            "inner join tbl_csgtdiachi on tbl_csgtdiachi.CSN_ID = tbl_csgtname.CSN_ID\n" +
                            "inner join ( tbl_city left join tbl_bienso on tbl_city.C_ID = tbl_bienso.C_ID) on tbl_city.C_ID = tbl_csgtname.C_ID\n" +
                            "where tbl_csgtname.CSN_ID = ? \n" +
                            "group by tbl_csgtname.CSN_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, nameid);
            rs = pstm.executeQuery();
            while(rs.next()){
                int nameidrs = rs.getInt("nameid");
                String name = rs.getString("csgtname");
                int id = rs.getInt("cityid");
                String bienso = rs.getString("bienso");
                String bsid = rs.getString("bsid");
                csgt.setName(name);
                csgt.setCityid(id);
                csgt.setBienso(bienso); 
                csgt.setNameid(nameidrs);
                csgt.setBsid(bsid);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return csgt;
    }
    
    // Load csgt edit
    public CSGTBean loadEditCSGT(int nameid){
      //  ArrayList<CSGTBean> csgtList = new ArrayList<>();
        CSGTBean csgt = loadCsgtById(nameid);
        ArrayList<ArrayList> dcList = getCSGTDC(nameid);
        csgt.setCsgtdc(dcList);
        return csgt;
    }
    // Lấy tổng số địa chỉ
    public int getCountDC(int nameid){
        int total = 0;
        String sql  ="select count(distinct tbl_csgtdiachi.CSDC_ID)\n" +
                        " from tbl_csgtname\n" +
                        "inner join tbl_csgtdiachi on tbl_csgtdiachi.CSN_ID = tbl_csgtname.CSN_ID\n" +
                        "inner join ( tbl_city left join tbl_bienso on tbl_city.C_ID = tbl_bienso.C_ID) on tbl_city.C_ID = tbl_csgtname.C_ID\n" +
                        "where tbl_csgtname.CSN_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, nameid);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }
    
    // xóa csgt dia chi
    private void delCSGTDc(int dcid){
        String sql = "DELETE FROM tbl_csgtdiachi where CSDC_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, dcid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // update csgt name
    public void updateCsgtName(int nameid,String name){
        String sql = "UPDATE  tbl_csgtname set CSN_Name = ? where CSN_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setInt(2, nameid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // update csgt dia chi
    public void updateCsgtDc(int dcid,String diachi){
        String sql = "UPDATE  tbl_csgtdiachi set CSDC_Diachi = ? where CSDC_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, diachi);
            pstm.setInt(2, dcid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // Xóa biển số
    public void delBienSo(int bienso){
        String sql = "DELETE FROM tbl_bienso where BS_Number = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bienso);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // update csgt bien so
    public void updateBienSo(int bienso,int bsid){
        String sql = "UPDATE  tbl_bienso set BS_Number = ? where BS_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bienso);
            pstm.setInt(2, bsid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // lấy tổng biển số
    private int getTotalBs(int cityid){
        int total = 0;
        String sql  ="select count(distinct tbl_bienso.BS_ID) from tbl_bienso\n" +
                            " left join tbl_city on tbl_city.C_ID = tbl_bienso.C_ID"+
                            " where tbl_bienso.C_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, cityid);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return total;
    }
    // update csgt 
    public void updateCSGT(int cityid,String name,String[] diachi,int[] bienso,int nameid, int[] csgtdcid,int[] bsid){
        addCSGTName(name,nameid);
        int totalDC = getCountDC(nameid);
        if(totalDC > diachi.length){
            for(int i=0; i< totalDC; i++){
                if(i < diachi.length){
                    updateCsgtDc(csgtdcid[i],diachi[i] );
                }else{
                    delCSGTDc(csgtdcid[i]);
                }
            }
        }else if(totalDC == diachi.length){
            for(int i=0; i< csgtdcid.length;i++){
                System.out.println(diachi[i]);
                 updateCsgtDc(csgtdcid[i],diachi[i] );
            }
        }else if(totalDC < diachi.length){
            for(int i=0; i < totalDC;i++){
                if(i < totalDC){
                    updateCsgtDc(csgtdcid[i],diachi[i] );
                }else{
                    addCSGTDiachi(nameid, diachi[i]);
                }
            }
        }
        
        int totalBS = getTotalBs(cityid);
        if(totalBS > bienso.length){
            for(int i=0; i< totalBS; i++){
                if(i < bienso.length){
                    updateBienSo(bienso[i], bsid[i]);
                }else{
                    delBienSo(bienso[i]);
                }
            }
        }else if(totalBS == bienso.length){
            for(int i=0; i< bienso.length;i++){
                 updateBienSo(bienso[i], bsid[i]);
            }
        }else if(totalBS < bienso.length){
            for(int i=0; i < totalBS;i++){
                if(i < totalBS){
                    updateBienSo(bienso[i], bsid[i]);
                }else{
                    addBienSo(bienso[i], cityid);
                }
            }
        }
    }
    
    
}
