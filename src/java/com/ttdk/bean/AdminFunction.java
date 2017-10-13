/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import com.ttdk.connect.DBConnect;
import java.sql.Array;
import java.sql.CallableStatement;
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
public class AdminFunction {
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    private CallableStatement stmt = null;
    // Login admin
    public boolean loginAdmin(String username,String password){
        boolean check = false;
        String sql = "select 1 from tbl_username where U_ID ='"+ username + "' and U_Pass='" + password + "'";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            if(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    
    // get all data nhan vien    
    private ArrayList<ArrayList<String>> getAllNhanVien(int page){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select tbl_username.U_ID as username,\n" +
                "ifnull(U_FullName,'') as fullname,ifnull(U_Des,'') as infor\n" +
                "from tbl_username order by tbl_username.U_ID asc limit "+(page-1)*10+",10;";
        //System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList dt = new ArrayList();
                dt.add(rs.getString("username"));
                dt.add(rs.getString("fullname"));
                dt.add(rs.getString("infor"));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // get page for nhan vien
    public int getPageNhanVien(){
        int totalpage = 0;
        String sql = "select count(*) "+
                "from tbl_username;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
               totalpage = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if((totalpage%10) == 0)totalpage = totalpage/10;
        else totalpage = totalpage/10 + 1;
        return totalpage;
    }
    // load table html for load nhan vien
    public String loadTableAllNhanvien(int page){
        String tbody = "";
        ArrayList<ArrayList<String>> listNv = getAllNhanVien(page);
        for(int i=0;i< listNv.size();i++){
            tbody += "<tr>";
            tbody += "<td>"+(i+1)+"</td>";
            tbody += "<td>"+listNv.get(i).get(1)+"</td>";
            tbody += "<td>"+listNv.get(i).get(0)+"</td>";
            tbody += "<td>"+listNv.get(i).get(2)+"</td>";
            tbody += "<td><img src=\"./images/document_edit.png\" data-toggle=\"modal\" onclick=\"editDon('"+listNv.get(i).get(0)+"')\"></td>";
            tbody += "<td><img src='./images/document_delete.png' onclick=\"deleteDon('"+listNv.get(i).get(0)+"')\"></td>";
            
            tbody += "</tr>";
        }
        return tbody;
    }
    
    // Load nhan vien edit
    public ArrayList<String> loadNVEdit(String username){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select tbl_username.U_ID as username,\n" +
                "ifnull(U_FullName,'') as fullname,ifnull(U_Des,'') as infor,U_Pass as passwd \n" +
                "from tbl_username  where tbl_username.U_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, username);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString("username"));
                data.add(rs.getString("fullname"));
                data.add(rs.getString("infor"));
                data.add(rs.getString("passwd"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return data;
    }
    
    // Save nhan vien edit
    public void saveNVEdit(String password,String fullname,String des,String username){
        saveEditUserName(password, username,fullname,des);
        //saveEditInfor(fullname, infor, username);
        
    }
    // save username
    private void saveEditUserName(String password,String username,String fullname,String des){
        String sql = "update tbl_username set U_Pass = ?,U_FullName = ?,U_Des = ? where U_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, password); 
            pstm.setString(2,fullname ); 
            pstm.setString(3,des ); 
            pstm.setString(4,username ); 
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // luu username and passwd
    public void saveUser(String username,String password,String fullname,String des){
        String sql = "insert into tbl_username(U_ID,U_Pass,U_FullName,U_Des) values(?,?,?,?);";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, username.toLowerCase());
            pstm.setString(2, password);
            pstm.setString(3, fullname);
            pstm.setString(4, des);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    // Lưu nhân viên
    public void saveNewNhanVien(String username,String password,String fullname,String des){
            saveUser(username, password,fullname,des);
    }
    // Lấy tên nhóm
    private ArrayList<ArrayList> getListNhom(){
        ArrayList<ArrayList> data = new ArrayList<>();
        String sql = "select R_ID as id,R_Des  as des from tbl_Role order by R_ID asc;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList dt = new ArrayList();
                String id = rs.getString("id");
                String des = rs.getString("des");
                dt.add(id);dt.add(des);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // tra ve option cho list nhom
    public String optionNhom(){
        String option = "";
        ArrayList<ArrayList> listnhom = getListNhom();
        for(ArrayList list : listnhom){
            option += "<option value='"+list.get(0)+"'>"+list.get(1)+"</option>"; 
        }
        return option;
    }
    //lấy nhân viên theo role
    public ArrayList<String> getNVNhom(int roleid ){
        ArrayList<String> data = new ArrayList();
        String sql = "select ifnull(group_concat(U_FullName separator ','),'') as nhanvien,"
                        + "ifnull(group_concat(tbl_username.U_ID separator ','),'') as nvids\n" +
                            "from tbl_username\n" +
                            "inner join tbl_phannhom on tbl_phannhom.U_ID = tbl_username.U_ID\n" +
                            "where tbl_phannhom.R_ID = ?;";
        System.out.println("NV NHOM : \n"+sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, roleid);
            rs = pstm.executeQuery();
            while(rs.next()){
                System.out.println("NV = "+ rs.getString("nhanvien"));
               data.add(rs.getString("nhanvien"));
               data.add(rs.getString("nvids"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // load table nhom
    public String loadNhom(){
        String table = "";
        ArrayList<ArrayList> listNhom = getListNhom();
      //  ArrayList<String> listNV = getNVNhom();
        for(int i=0;i < listNhom.size();i++){
            table += "<tr>";
            table += "<td>"+(i+1)+"</td>";
            table += "<td>"+listNhom.get(i).get(1)+"</td>";
            
            ArrayList<String> listNV = getNVNhom(Integer.parseInt(listNhom.get(i).get(0).toString()));
            table += "<td>"+listNV.get(0)+"</td>";
            table += "<td><img src=\"./images/document_edit.png\" data-toggle=\"modal\" onclick=\"loadEditNhom('"+listNhom.get(i).get(0)+"')\"></td>";
            table += "<td><img src='./images/document_delete.png' onclick=\"deleteNhom('"+listNhom.get(i).get(0)+"')\"></td>";
            table += "</tr>";
        }
        return table;
    }
    // load nhân viên trang nhóm
    private ArrayList<ArrayList> getNVChonNhom(){
        ArrayList<ArrayList> data = new ArrayList<>();
        String sql = "select tbl_username.U_ID as username,\n" +
                "ifnull(U_FullName,'') as fullname \n" +
                "from tbl_username order by tbl_username.U_ID asc ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList dt = new ArrayList();
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");
                dt.add(username);dt.add(fullname);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // load nhan vien theo nhom
    public String loadNhanVienNhom(){
        String table = "";
        ArrayList<ArrayList> nvNhom = getNVChonNhom();
        for(int i=0; i < nvNhom.size();i = i+3){
            table += "<tr>";
            table += "<td style='width: 200px; '><label class='checkbox-inline'><input type='checkbox' id='"+nvNhom.get(i).get(0)+"' value='"+nvNhom.get(i).get(0)+"'>"+nvNhom.get(i).get(1)+"</label></td>";
            if((i+1)< nvNhom.size()){
                table += "<td style='width: 200px; '><label class='checkbox-inline'><input type='checkbox' id='"+nvNhom.get(i+1).get(0)+"' value='"+nvNhom.get(i+1).get(0)+"'>"+nvNhom.get(i+1).get(1)+"</label></td>";
            }
            if((i+2)< nvNhom.size()){
                table += "<td style='width: 200px; '><label class='checkbox-inline'><input type='checkbox' id='"+nvNhom.get(i+2).get(0)+"' value='"+nvNhom.get(i+2).get(0)+"'>"+nvNhom.get(i+2).get(1)+"</label></td>";
            }
            table += "</tr>";
        }
        return table;
    }
    
    // save nhom
    public int saveNhom(String name){
        int keyRole = 0;
        String sql = "insert into tbl_role(R_Des) values(?);";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, name);
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if(rs.next()){
                keyRole = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return keyRole;
    }
    
    // save nhan vien cho nhom
    public void updateRoleNV(String[] username,int role){
        String addSql = "";
        for(int i=0; i < username.length;i++){
            if(i ==0){
                addSql += "(?,?)";
            }else{
                addSql += ",(?,?)";
            }
            
        }
        String sql = "insert into tbl_phannhom(R_ID,U_ID) values"+addSql+";";
        connect = new DBConnect().dbConnect();
        try {
            stmt = connect.prepareCall(sql);
            int index = 1;
            for(String user : username){
                stmt.setInt(index, role);
                stmt.setString(index+1, user);
                index = index +2;
            }        
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // delete all nhan vien cua nhom
    public void deleteNVNhom(int nhomid){
        String sql ="delete from tbl_phannhom where tbl_phannhom.R_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, nhomid);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // edit ten nhom
    public void updateTenNhom(String name,int role){
        String sql = "update tbl_Role set R_Des = ? where R_ID = ?; ";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setInt(2, role);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // get name of nhom
    public ArrayList<String> getNhomName(int roleid){
        ArrayList<String> data = new ArrayList<>();
        String sql =" select tbl_role.R_ID as role,tbl_role.R_Des as tennhom \n" +
                    " from tbl_role\n" +
                    " where tbl_role.R_ID = ? group by tbl_role.R_ID;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, roleid);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString("role"));
                data.add(rs.getString("tennhom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    // load thong tin nhan vien nhom edit
    public String loadEditNV(int roleid){
        String nhanviens = "";
        String sql = "select group_concat(U_FullName separator ',') as nhanvien,\n" +
                            "group_concat(tbl_username.U_ID separator ',') as nvid \n" +
                            "from tbl_username"+
                            " where tbl_username.U_Role = ? group by tbl_username.U_Role;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, roleid);
            rs = pstm.executeQuery();
            while(rs.next()){
                nhanviens = rs.getString("nvid");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nhanviens;
    }
    
    // get all thong tin nhom
    public ArrayList<String> loadEditNhom(int nhomid){
        ArrayList<String> ttNhom = getNhomName(nhomid);
        ArrayList<String> nhanviens = null;
        if(ttNhom.get(0) != null && !ttNhom.get(0).equals("")){
            nhanviens = getNVNhom(Integer.parseInt(ttNhom.get(0))); 
        }
        ttNhom.add(nhanviens.get(1));
        return ttNhom;
    }
    // get quyen 
    private ArrayList<ArrayList<String>> loadQuyen(){
        ArrayList<ArrayList<String>> data =new ArrayList<>();
        String sql = "select Q_ID as id,Q_Name as name,Q_Des as des from tbl_quyen order by Q_ID asc;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString("id"));dt.add(rs.getString("name"));dt.add(rs.getString("des"));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // load quyen table
    public String loadTableQuyen(){
        String table = "";
        ArrayList<ArrayList<String>> quyenList  = loadQuyen();
        int i =1;
        for(ArrayList<String> list : quyenList){
            table += "<tr>";
            table += "<td>"+i+"</td>";
            table += "<td>"+list.get(1)+"</td>";
            table += "<td>"+list.get(2)+"</td>";
            table += "<td><input type='checkbox' value='"+list.get(0)+"'></td>";
            table += "</tr>";
            i++;
        }
        return table;
    }
    
    // Alias Lưu phân quyền
    public void savePhanQuyenList(int nhomid,String[] quyenids,int status){
        for(String quyenid : quyenids){
            int qid = Integer.parseInt(quyenid);
            savePhanQuyen(nhomid, qid,status);
        }
    }
    //update phân quyền
    public void savePhanQuyen(int nhomid,int qid,int status){
      //  String sql = "update tbl_phanquyen set PQ_Status = ? where R_ID = ? and PQ_QID = ?;";
        String sql = "call updatePhanQuyen(?,?,?);";
        connect = new DBConnect().dbConnect();
        try {
            stmt = connect.prepareCall(sql);
            stmt.setInt(1, nhomid);
            stmt.setInt(2, qid);
            stmt.setInt(3, status);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                stmt.close();
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //load phân quyền
    private ArrayList<ArrayList<String>> loadLoaidon(){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = " select tbl_loaidon.LD_ID as ldid,tbl_loaidon.LD_Des as loaidon from tbl_loaidon;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String ldid = rs.getString("ldid");
                String loaidon = rs.getString("loaidon");
                dt.add(ldid);dt.add(loaidon);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // get data hear
    public ArrayList<String> getHeadLoaiDon(){
        ArrayList<String> data = new ArrayList<>();
        String sql = " select ifnull(tbl_lephi.LP_End,'') as dayend from tbl_lephi group by tbl_lephi.LP_End; ";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                    data.add(rs.getString("dayend"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // get giá của loại đơn
    public ArrayList<String> getPriceLD(int ldid){
        ArrayList<String> data = new ArrayList<>();
        String sql =" select tbl_lephi.LP_Price as price from tbl_lephi \n" +
                        " where tbl_lephi.LD_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, ldid);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString("price"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // get table loaị đơn
    public String getTableLoaiDon(){
        String table = "";
        ArrayList<String> dataHeader = getHeadLoaiDon();
        table += "<thead>";
        table += "<th>STT</th>";
        table += "<th>Loại đơn</th>";
       for(String head : dataHeader){
           if(!head.equals("")){
               table += "<th>Giá Tiền Trước "+head+"</th>";
           }else{
               table += "<th>Giá Tiền Sau "+head+"</th>";
           }
           
       }
       table += "<th>Sửa</th>";
       table += "<th>Xóa</th>";
       table += "</thead>";
        ArrayList<ArrayList<String>> data = loadLoaidon();
        int i=1;
        table += "<tbody>";
        for(ArrayList<String> loaidon : data){
            table += "<tr>";
            table += "<td>"+i+"</td>";
            table += "<td>"+loaidon.get(1)+"</td>";
            ArrayList<String> priceData = getPriceLD(Integer.parseInt(loaidon.get(0)));
            for(String price : priceData){
                table += "<td>"+price+"</td>";
            }
             table += "<td><img src='./images/document_edit.png' data-toggle='modal' ></td>";
            table += "<td><img src='./images/document_delete.png' ></td>";
            table += "</tr>";
            i++;
        }
        table += "</tbody>";
        System.out.println(table);
        return table;
    }
    
    // get role id cho nhom
    public String getRoleidNhom(int nhomid){
        String roleid = "";
        String sql = "select group_concat(tbl_phanquyen.Q_ID separator ',') as phanquyen "
                + "from tbl_phanquyen where R_ID = ? and tbl_phanquyen.PQ_Status =1 ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, nhomid);
            rs = pstm.executeQuery();
            while(rs.next()){
                roleid = rs.getString("phanquyen");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roleid;
    }
}
