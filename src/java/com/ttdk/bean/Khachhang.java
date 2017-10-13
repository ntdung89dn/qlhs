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
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Thorfinn
 */
public class Khachhang {
    private ArrayList<String> list;
    private KhachhangBean khbean = new KhachhangBean();
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    private ArrayList<ArrayList<String>> data;
    
    public ArrayList<ArrayList<String>> getKhachHang(){
        String kh_sql = "select tbl_khachhang.KH_ID as id, tbl_khachhang.KH_Account as account,tbl_khachhang.KH_Name as name"
                + ",tbl_khachhang.KH_Address as address,tbl_khachhang.KH_Email as email from tbl_khachhang \n" +
                " where tbl_khachhang.KH_Status is null limit 100;";
        data = new ArrayList<>();
        DBConnect conn = new DBConnect();
        connect = conn.dbConnect();
        try {
            pstm = connect.prepareStatement(kh_sql);
            rs = pstm.executeQuery();
            int j=0;
            while(rs.next()){
                list = new ArrayList<>();
                String account =  rs.getString("account");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                //System.out.println("Name : "+name);
                //System.out.println("Address : "+address);
               list.add(name);list.add(address);
               list.add(email); list.add(account);
                data.add(j, list);
                j++;
            }
            for(int i =0;i<data.size();i++){
                ArrayList<String> list1 = data.get(i);
                String name = list1.get(0);
                String address = list1.get(1);
                System.out.println("Name : "+name);
                System.out.println("Address : "+address);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    public List<KhachhangBean> getKhachHangList(int page){
        String kh_sql = "select tbl_khachhang.KH_ID as id, tbl_khachhang.KH_Account as account,tbl_khachhang.KH_Name as name"
                + ",tbl_khachhang.KH_Address as address,tbl_khachhang.KH_Email as email from tbl_khachhang \n" +
                " where tbl_khachhang.KH_Status is null limit "+(page-1)*10+",10;";
        
        List<KhachhangBean> dataKH = new ArrayList<>();
         DBConnect conn = new DBConnect();
        connect = conn.dbConnect();
        try {
            pstm = connect.prepareStatement(kh_sql);
            rs = pstm.executeQuery();
            int j=0;
            while(rs.next()){
                KhachhangBean khb = new KhachhangBean();
                String account =  rs.getString("account");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                //System.out.println("Name : "+name);
                //System.out.println("Address : "+address);
               khb.setName(name);khb.setAddress(address);
               khb.setEmail(email);khb.setAccount(account);
                dataKH.add(khb);
                j++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dataKH;
    }
    
    // search khach hang theo ten, dia chi, email, acount
    public String searchKh(String name,String address,String email,String account,int roleEdit,int roleDel){
        String table = "";
        String whereSql = "";
        if(!name.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name LIKE '%"+name+"%' ";
        }
        if(!address.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Address LIKE '%"+address+"%'  ";
        }
        if(!email.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Email LIKE '%"+email+"%'  ";
        }
        if(!account.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Account LIKE '%"+account+"%' ";
        }
        String sql = "select tbl_khachhang.KH_ID as id, tbl_khachhang.KH_Account as account,tbl_khachhang.KH_Name as name"
                + ",tbl_khachhang.KH_Address as address,tbl_khachhang.KH_Email as email from tbl_khachhang \n" +
                " where tbl_khachhang.KH_Status is null "+whereSql+" limit 0,10;";
        
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i=1;
            while(rs.next()){
                 int khid = rs.getInt("id");
                table +="<tr>";
                table +="<td>"+i+"</td>";
                String namers = rs.getString("name");
                table +="<td>"+namers+"</td>";
                String addressrs = rs.getString("address");
                table +="<td>"+addressrs+"</td>";
                String emailrs = rs.getString("email");
                table +="<td>"+emailrs+"</td>";
                String accountrs = rs.getString("account");
                 table +="<td>"+accountrs+"</td>";
               if(roleEdit ==1){
                     table +="<td><img src='./images/document_edit.png' data-toggle='modal' onclick='editKH("+khid+")'></td>\n" ;
                 }
                if(roleDel ==1){
                    table +="<td ><img src='./images/document_delete.png'></td></tr>";
                }
                i++;    
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return table;
    }
    // get page search KH
    public int getPagesearchKh(String name,String address,String email,String account){
        int total = 0;
        String whereSql = "";
        if(!name.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name LIKE '%"+name+"%' ";
        }
        if(!address.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Address LIKE '%"+address+"%'  ";
        }
        if(!email.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Email LIKE '%"+email+"%'  ";
        }
        if(!account.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Account LIKE '%"+account+"%' ";
        }
        String sql = "select count(tbl_khachhang.KH_ID) "
                + "from tbl_khachhang \n" +
                    " where tbl_khachhang.KH_Status is null "+whereSql+" limit 0,10;";
        
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i=1;
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return (total/10)+1;
    }
    // search khach hang theo id
    public ArrayList searchKh(int id){
        ArrayList khdetail = new ArrayList();
        String sql = "select tbl_khachhang.KH_ID as id, tbl_khachhang.KH_Account as account,tbl_khachhang.KH_Name as name"
                + ",tbl_khachhang.KH_Address as address,tbl_khachhang.KH_Email as email from tbl_khachhang \n" +
                " where tbl_khachhang.KH_ID = ? ;";
        
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            pstm.setInt(1, id); 
            rs = pstm.executeQuery();
            while(rs.next()){
                 int khid = rs.getInt("id");
                String namers = rs.getString("name");
                String addressrs = rs.getString("address");
                String emailrs = rs.getString("email");
                String accountrs = rs.getString("account");
                
                khdetail.add(khid);
                khdetail.add(namers);
                khdetail.add(addressrs);
                khdetail.add(emailrs);
                khdetail.add(accountrs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return khdetail;
    }
    // get tai khoan chua co tai khoan
    public int checkTKCK(){
        String sql = "select A_Number from tbl_account where tbl_account.A_Number like '%CK%';";
        int numberAcc = 0;
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                String tk = rs.getString(1);
                tk = tk.substring(tk.indexOf("CK"));
                numberAcc = Integer.parseInt(tk);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numberAcc;
    }
    // thêm khách hàng
    public int addKhachHang(String name,String address,String email,String account){
        String khSQL = "insert into tbl_khachhang(KH_Name,KH_Address,KH_Account,KH_Email,KH_Lock) values(?,?,?,?,'n')";
        connect = new DBConnect().dbConnect();
        int khid = 0;
        try {
            //Lưu tài khoản
            pstm = connect.prepareStatement(khSQL,PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setString(3, account);
            pstm.setString(4, email);
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if(rs.next()){
                khid = rs.getInt(1);
            }
            pstm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return khid;
    }
    public void updateKHList(String name,String diachi,String account,String email,int khid){
        String sql = "update qlhsdb.tbl_khachhang SET KH_Name =?,KH_Address = ?,KH_Account = ?,KH_Email = ? where qlhsdb.tbl_khachhang.KH_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, diachi);
            pstm.setString(3, account);
            pstm.setString(4, email);
            pstm.setInt(5, khid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public int khCount(){
        int count = 0;
        String sql = "SELECT COUNT(*) AS COUNT FROM tbl_khachhang  where tbl_khachhang.KH_Status is null ;";
        try {
            connect = new DBConnect().dbConnect();
            pstm = connect.prepareStatement(sql);
             rs = pstm.executeQuery();
            while (rs.next())
            {
                count=rs.getInt("COUNT");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (count/10)+1;
    }
    
    // load khach hang
    public String loadKh(int page,int roleEdit,int roleDel){
        String table = "";
        String sql = "select tbl_khachhang.KH_ID as id, tbl_khachhang.KH_Account as account,tbl_khachhang.KH_Name as name"
                + ",tbl_khachhang.KH_Address as address,tbl_khachhang.KH_Email as email from tbl_khachhang \n" +
                " where tbl_khachhang.KH_Status is null "
                + "order by tbl_khachhang.KH_ID DESC limit "+((page-1)*10)+",10;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i=1;
            while(rs.next()){
                int khid = rs.getInt("id");
                table +="<tr>";
                table +="<td>"+i+"</td>";
                String namers = rs.getString("name");
                table +="<td>"+namers+"</td>";
                String addressrs = rs.getString("address");
                table +="<td>"+addressrs+"</td>";
                String emailrs = rs.getString("email");
                table +="<td>"+emailrs+"</td>";
                String accountrs = rs.getString("account");
               
                 table +="<td>"+accountrs+"</td>";
                 if(roleEdit ==1){
                     table +="<td><img src='./images/document_edit.png' data-toggle='modal' onclick='editKH("+khid+")'></td>\n" ;
                 }
                if(roleDel ==1){
                    table +="<td ><img src='./images/document_delete.png'></td></tr>";
                }
                
                i++;    
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return table;
    }
    
    // Xóa khách hàng
    public int deleteKH(int khid){
        String sql = "Update  tbl_khachhang SET KH_Status = 1 where KH_ID = ? ";
        connect = new DBConnect().dbConnect();
        int delCount = 0;
        try {
            pstm =  connect.prepareStatement(sql);
            pstm.setInt(1, khid);
           delCount = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return delCount;
    }
    
    // Khóa khách hàng
    public int lockKH(int khid, String isLock){
        int lockCount = 0;
        String sql = "Update  tbl_khachhang SET KH_Lock = ? where KH_ID = ? ";
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            pstm.setString(1, isLock);
            pstm.setInt(2, khid);
            lockCount = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lockCount;
    }
    
    // lấy khách hàng thu phí bằng account
    
    public ArrayList<ArrayList<String>> getKHangByAccount(String accountS){
         ArrayList<ArrayList<String>> data= new ArrayList<>();
        String sql = "select tbl_khachhang.KH_ID as id, tbl_khachhang.KH_Account as account,tbl_khachhang.KH_Name as name"
                + ",tbl_khachhang.KH_Address as address from tbl_khachhang \n" +
                " where tbl_khachhang.KH_Account like '%"+accountS+"%' ;";
       // System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
          //  pstm.setString(1, "'%"+accountS+"%"); 
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                 String khid = rs.getString("id");
                String namers = rs.getString("name");
                String addressrs = rs.getString("address");
                String accountrs = rs.getString("account");
                
                dt.add(khid);
                dt.add(namers);
                dt.add(addressrs);
                dt.add(accountrs);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // Trả về view cho khách hàng search
    public String getKHangByAccountView(String accountS,int stt){
        String table = "";
        ArrayList<ArrayList<String>> data = getKHangByAccount(accountS);
        for(int i=0;i < data.size();i++){
            table += "<p>";
            if(i==0){
                table += "<input type='radio' value='"+data.get(i).get(0)+"' name='rd"+stt+"_"+(data.get(i).get(0)+i)+"' checked>";
            }else{
                table += "<input type='radio' value='"+data.get(i).get(0)+"' name='rd"+stt+"_"+(data.get(i).get(0)+i)+"'>";
            }
            table += data.get(i).get(1)+" - "+data.get(i).get(2)+" - "+data.get(i).get(3);
            table +="</p>";
        }
        return table;
    }
    
    // Lấy email khách hàng by khid
    public String getEmailKH(int khid){
        String email = "";
        String sql = "select tbl_khachhang.KH_Email as email from tbl_khachhang \n" +
                " where tbl_khachhang.KH_ID = ? ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            pstm.setInt(1, khid);
            rs = pstm.executeQuery();
            while(rs.next()){
                email = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return email;
    }
    
    // Lấy khách hàng bằng tên và account
    public ArrayList<String> getKHID(String khname,String account){  
        ArrayList<String> data = new ArrayList<>();
        String whereSql = "";
        if(!khname.trim().equals("")){
            whereSql += " and  KH_Name like '%"+khname+"%' ";
        }
        if(!account.trim().equals("")){
            whereSql += " and KH_Account like '%"+account+"%' ";
        }
        String sql = "SELECT KH_ID FROM qlhsdb.tbl_khachhang where 1=1 "+whereSql+" ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm =  connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Khachhang.class.getName()).log(Level.SEVERE, null, ex);
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
