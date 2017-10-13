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
 * @author NTD
 */
public class HieuSuat {
    private Connection connect;
    private PreparedStatement pstm;
    private ResultSet rs;
    public HieuSuat(){
        
    }
    
    // thong ke 2
    
    public ArrayList<String> soNhanVien(String fromday,String today){
        ArrayList<String> nvList = new ArrayList<>();
        String sql = " select distinct DM_NVnhan as nvien,tbl_username.U_Fullname as fullname from tbl_dinhmuc \n" +
                            " inner join tbl_username on tbl_username.U_ID = tbl_dinhmuc.DM_NVnhan\n" +
                            " where tbl_dinhmuc.DM_Time between str_to_date(?,'%d-%m-%Y')\n" +
                            " and str_to_date(?,'%d-%m-%Y') order by DM_NVnhan asc;";
//        System.out.println("-------------------------A");
//        System.out.println(sql);
//        System.out.println("-------------------------A");
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, fromday);
            pstm.setString(2, today);
            rs = pstm.executeQuery();
            while(rs.next()){
                nvList.add(rs.getString("nvien")+"-"+rs.getString("fullname"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nvList;
    }
    
    
    public ArrayList<HieuSuatBean> selectHSNV(String fromday,String nvid){
        ArrayList<HieuSuatBean> hsArr = new ArrayList<>();
        String sql = "select tbl_dinhmuc.DM_DM as thucte,\n" +
                            " (select sum(tbl_dinhmuc.DM_DM) from tbl_dinhmuc \n" +
                            " where tbl_dinhmuc.DM_Time = str_to_date(?,'%d-%m-%Y')) as tongts,\n" +
                            "  (select count(*) from tbl_dinhmuc \n" +
                            "  where tbl_dinhmuc.DM_Time = str_to_date(?,'%d-%m-%Y')) as tongnv\n" +
                            " from tbl_dinhmuc \n" +
                            " where tbl_dinhmuc.DM_NVnhan = ? \n" +
                            " and tbl_dinhmuc.DM_Time = str_to_date(?,'%d-%m-%Y');";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
             pstm.setString(1, fromday);
            pstm.setString(2, fromday);
            pstm.setString(3, nvid);
             pstm.setString(4, fromday);
            rs = pstm.executeQuery();
            while(rs.next()){
                HieuSuatBean hsb = new HieuSuatBean();
                int thucte = rs.getInt("thucte");
                int tongts = rs.getInt("tongts");
                int tongnv = rs.getInt("tongnv");
                hsb.setSonv(tongnv); 
                hsb.setThucte(thucte);
                hsb.setTongts(tongts); 
                int percent = 0;
                if(tongts !=0){
                    percent = (thucte/tongts)*100;
                }
                hsb.setPercent(percent);
                int tsdu = 0;
                
                
                hsb.setNgaythang(fromday);
                int dinhmuc = 0;
                if(tongnv !=0){
                    dinhmuc = tongts/tongnv;
                    tsdu = tongts%tongnv;
                }
                hsb.setTsdu(tsdu);
                hsb.setDinhmucTB(dinhmuc);
                hsArr.add(hsb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return hsArr;
    }
    
    // lấy nhân viên được nhiều tài sản hơn
    public ArrayList<String> nvHighest(String fromday,int limit){
        ArrayList<String> nvList = new ArrayList<>();
        String sql = "select DM_NVnhan as nvien\n" +
                        "from tbl_dinhmuc\n" +
                        "where tbl_dinhmuc.DM_Time = str_to_date(?,'%d-%m-%Y') \n" +
                        "order by tbl_dinhmuc.DM_DM desc limit ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, fromday);
            pstm.setInt(2, limit);
            rs = pstm.executeQuery();
            while(rs.next()){
                nvList.add(rs.getString("nvien"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nvList;
    }
    
    public ArrayList<String> listDay(String fromday, String today){
        ArrayList<String> dayArr = new ArrayList<>();
        String sql="select distinct DATE_FORMAT(tbl_dinhmuc.DM_Time,'%d-%m-%Y') as daytime from tbl_dinhmuc \n" +
                            " where tbl_dinhmuc.DM_Time between str_to_date(?,'%d-%m-%Y') and str_to_date(?,'%d-%m-%Y')\n" +
                            " order by tbl_dinhmuc.DM_Time asc;";
//        System.out.println("-------------");
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, fromday);
            pstm.setString(2, today);
            rs = pstm.executeQuery();
            while(rs.next()){
                dayArr.add(rs.getString("daytime"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dayArr;
    }
    public String tableHS(String fromday, String today){
        String table = "";
        String thead ="<thead><tr ><th rowspan=\"2\" style=\"min-width: 60px; \">STT</th>"
                + "<th rowspan=\"2\" style=\"min-width: 200px; \">Danh Sách Nhân Viên</th>";
        String tsub_head = "<tr>";
        String tbody = "<tbody>";
        ArrayList<String> nvList = soNhanVien(fromday, today);
        ArrayList<HieuSuatTable> body =new ArrayList<>();
        int no = 1;
        for(String username : nvList){
            HieuSuatTable hstable = new HieuSuatTable();
            String[] infor =  username.split("-");
            hstable.setUsername(infor[0]);
            String row = "<tr><td>"+no+"</td>"+"<td>"+infor[1]+"</td>";
            hstable.setRow(row);
            body.add(hstable);
            no++;
        }
        
        ArrayList<String> dayList = listDay(fromday, today);
        for(String daytime : dayList){
            for(String username : nvList){
                       String[] infor = username.split("-");
                        String user = infor[0];
                        ArrayList<HieuSuatBean> hsArr = selectHSNV(daytime, user);
                        if(hsArr.isEmpty()){
                            for(HieuSuatTable hst : body){
                                if(user.equals(hst.getUsername())){
                                    hst.setRow(hst.getRow()+"<td></td><td></td>");
                                }
                            }
                        }else{
                            for(HieuSuatBean hsb : hsArr){
                                for(HieuSuatTable hst : body){
                                    if(user.equals(hst.getUsername())){
                                        int thucte = hsb.getThucte();
                                        double percent  = hsb.getPercent();
                                        int tsdu = hsb.getTsdu();
                                        int tongts =  hsb.getTongts();
                                        int dmTB = hsb.getDinhmucTB();
                                        if(tsdu !=0){
                                             ArrayList<String> nvHighest = nvHighest(daytime, tsdu);
                                             for(String nvienH : nvHighest){
                                                 if(nvienH.equals(user)){
                                                     dmTB +=1;
                                                 }
                                             }
                                        }
                                        String dinhmucStr = new DecimalFormat("##.##").format(((double)dmTB/(double)tongts)*100);
                                        String td = "<td>"+dmTB+"("+dinhmucStr+"%)</td><td>"+thucte+"("+percent+"%)</td>";
                                        hst.setRow(hst.getRow()+td);
                                    }
                                }
                            }
                        }
                        
                  }
                 thead += "<th colspan=\"2\" style=\"min-width: 100px;\">"+daytime+" </th>";
                tsub_head += "<th style=\"min-width: 100px;\" >Định Mức</th><th style=\"min-width: 100px;\">Thực Tế</th>";
        }
       thead += "</tr>"+tsub_head+ "</tr>"+"</thead>";
       for(HieuSuatTable hst : body){
           tbody += hst.getRow()+"</tr>";
       }
       tbody += "</tbody>";
       table = thead + tbody;
        return table;
    }
    
    // 
    
    // Lấy ngày phân đơn
    public String getDayPD(String fromday,String endday){
        String listday = "";
        String sql = "select distinct DATE(tbl_phandon.PD_Time) as ngaygio from tbl_phandon\n" +
                            "where DATE_FORMAT(tbl_phandon.PD_Time,'%Y-%m-%d') between str_to_date(?,'%d-%m-%Y') and str_to_date(?,'%d-%m-%Y')\n" +
                            "group by tbl_phandon.PD_Time asc;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, fromday);
            pstm.setString(2, endday);
            rs = pstm.executeQuery();
            while(rs.next()){
                String ngaynhap = rs.getString("ngaygio");
                if(listday.equals("")){
                    listday = "'"+ngaynhap+"'";
                }else{
                    listday += ",'"+ngaynhap+"'";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listday;
    }
    
    // Load tổng tài sản theo ngày
    public ArrayList<ArrayList<String>> listTongTSByDay(String dayvalue){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select date_format(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap,sum(tbl_don.D_SLTS) as tongts\n" +
                            "from tbl_don\n" +
                            "where tbl_don.D_Date in( "+dayvalue+") and tbl_don.D_isRemove = 0\n" +
                            " group by tbl_don.D_Date;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String ngaynhap = rs.getString("ngaynhap");
                String tongts = rs.getString("tongts");
                dt.add(ngaynhap);dt.add(tongts);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // lấy số nhân viên làm theo ngày
    public int getSoNVByNgay(String fromday){
        int sonv = 0;
        String sql = " select count(*) from tbl_dinhmuc\n" +
                            " where tbl_dinhmuc.DM_Time = str_to_date(?,'%d-%m-%Y');";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, fromday);
            rs = pstm.executeQuery();
            while(rs.next()){
                sonv = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sonv;
    }
    // load số ts nhân viên làm theo ngày
        public ArrayList<ArrayList<String>> getDinhMucNVByDay(String fromday,String today,String userid){
            ArrayList<ArrayList<String>> data = new ArrayList<>();
            String sql = "select tbl_dinhmuc.DM_DM as thucte,DATE_FORMAT(tbl_dinhmuc.DM_Time,'%d-%m-%Y') as ngaynhap \n" +
                                " from tbl_dinhmuc \n" +
                                " inner join tbl_username on tbl_username.U_ID = tbl_dinhmuc.DM_NVnhan\n" +
                                " where tbl_dinhmuc.DM_NVnhan = ? \n" +
                                " and tbl_dinhmuc.DM_Time between str_to_date(?,'%d-%m-%Y') and str_to_date(?,'%d-%m-%Y')\n" +
                                " group by tbl_dinhmuc.DM_Time;";
         //   System.out.println(sql);
            connect = new DBConnect().dbConnect();
            try {
                pstm = connect.prepareStatement(sql);
                pstm.setString(1, userid);
                pstm.setString(2, fromday);
                pstm.setString(3, today);
                rs = pstm.executeQuery();
                while(rs.next()){
                    ArrayList<String> dt = new ArrayList<>();
                    String ngaynhap = rs.getString("ngaynhap");
                    String thucte = rs.getString("thucte");
                    dt.add(ngaynhap);dt.add(thucte);
                    data.add(dt);
                }
            } catch (SQLException ex) {
                Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return data;
        }
        // check nhan vien co lam theo ngay
        public boolean checkNVcoHS(String userid,String fromday){
            boolean check = false;
            String sql = " select 1 from tbl_dinhmuc\n" +
                            " where tbl_dinhmuc.DM_Time = str_to_date(?,'%d-%m-%Y') and tbl_dinhmuc.DM_NVnhan =? ;";
            connect = new DBConnect().dbConnect();
            try {
                pstm = connect.prepareStatement(sql);
                pstm.setString(1, fromday);
                pstm.setString(2, userid);
                rs = pstm.executeQuery();
                while(rs.next()){
                    check = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return check;
        }
        
        // load table hieu suat
        public String viewTableHieuSuat(String fromday,String today){
         //    table = "<table>";
            String thead ="<thead><tr ><th rowspan=\"2\" style=\"min-width: 60px; \">STT</th>"
                + "<th rowspan=\"2\" style=\"min-width: 200px; \">Danh Sách Nhân Viên</th>";
            String tsub_head = "<tr>";
            String tbody ="";
            ArrayList<String> listNgay = listDay(fromday, today);
            for(String day : listNgay){
                thead += "<th colspan=\"2\" style=\"min-width: 100px;\">"+day+" </th>";
                tsub_head += "<th style=\"min-width: 100px;\" >Định Mức</th><th style=\"min-width: 100px;\">Thực Tế</th>";
            }
            String listday = getDayPD(fromday, today);
            ArrayList<ArrayList<String>> tongsots = listTongTSByDay(listday);
            ArrayList<String> nviens = soNhanVien(fromday, today);
            int stt = 1;
            for(String nvien : nviens){
                tbody+= "<tr>";
                tbody+="<td>"+stt+"</td>";
                tbody+="<td>"+nvien.split("-")[1]+"</td>";
                ArrayList<ArrayList<String>> thuctenv = getDinhMucNVByDay(fromday, today, nvien.split("-")[0]);
               // System.out.println("SIZE = "+thuctenv.size());
                for(int i=0;i<tongsots.size();i++){
                    String thisday = tongsots.get(i).get(0);
                    boolean check = checkNVcoHS(nvien.split("-")[0], thisday);
                    System.out.println("NGAY = "+thisday+"  CHECK = "+check);
                    boolean isBreak = false;
                    for(int index =0; index < listNgay.size();index++){
                        if(thisday.equals(listNgay.get(index))){
                            isBreak = true;
                        }
                    }
                    if(!isBreak){
                        continue;
                    }
                    if(check){
                        int sonv = getSoNVByNgay(thisday);
                        double tongts = Double.parseDouble(tongsots.get(i).get(1));
                        System.out.println("SO NV ="+sonv);System.out.println("TONG ts = "+tongts);
                        int dinhmuc = (int)(tongts/sonv);
                        int tsdu =(int) tongts%sonv;
                        
                        if(tsdu >0){
                            ArrayList<String> nvH =  nvHighest(thisday, tsdu);
                            for(String user : nvH){
                                if(nvien.split("-")[0].equals(user)){
                                    dinhmuc = dinhmuc +1;
                                }
                            }
                        }
                        String dinhmucStr = new DecimalFormat("##.##").format(((double)dinhmuc/(double)tongts)*100); 
                        System.out.println("dinh muc ="+dinhmuc);System.out.println("thuc te = "+dinhmucStr);
                        tbody +="<td style='font-weight:bold;'>"+dinhmuc+"<span style='color:red;'> ("+dinhmucStr+"%)</span></td>";
                        String thucte = "";
                        for(ArrayList<String> dayofNV : thuctenv){
                        //    System.out.println(dayofNV.get(0));
                            if(dayofNV.get(0).equals(thisday)){
                                thucte = dayofNV.get(1);
                            }
                            
                        }
                        String thucteStr = new DecimalFormat("##.##").format((Double.parseDouble(thucte)/(double)tongts)*100); 
                        tbody +="<td style='font-weight:bold;'>"+thucte+"<span style='color:red;'> ("+thucteStr+"%)</span></td>";
                     //   System.out.println(tbody);
                    }else{
                        tbody +="<td></td>";
                        tbody +="<td></td>";
                    }
                    
                }
                tbody += "</tr>";
                stt++;
            }
              
              thead += "</tr>"+tsub_head+ "</tr>"+"</thead>";
              String table = "<table>" +thead +tbody+"</table>";
            return table;
        }
        
        //lấy tổng số tài sản trong tháng
        public int getTongsoTs(String ngaybatdau,String ngayketthuc){
            int tongts = 0;
            String whereSql = "";
            if(!ngaybatdau.equals("")){
                whereSql += " and tbl_don.D_Date >= str_to_date('"+ngaybatdau+"','%d-%m-%Y')";
            }
            if(!ngayketthuc.equals("")){
                whereSql += " and tbl_don.D_Date <= str_to_date('"+ngayketthuc+"','%d-%m-%Y')";
            }
            String sql = "select sum(tbl_don.D_SLTS) from tbl_don where 1=1 "+whereSql;
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                tongts  = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                try {
                    new DBConnect().closeAll(connect, pstm, rs);
                } catch (SQLException ex) {
                    Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return tongts;
        }
        
        public int getDinhmucNV(String ngaybatdau,String ngayketthuc,String nhanvien){
            int dinhmuc = 0;
            String whereSql = "";
            if(!ngaybatdau.equals("")){
                whereSql += " and tbl_dinhmuc.DM_Time >= str_to_date('"+ngaybatdau+"','%d-%m-%Y') ";
            }
            if(!ngayketthuc.equals("")){
                whereSql += " and tbl_dinhmuc.DM_Time <= str_to_date('"+ngayketthuc+"','%d-%m-%Y') ";
            }
            String sql = "select sum(DM_DM) from tbl_dinhmuc where DM_NVnhan = ? "+whereSql;
            System.out.println(nhanvien);
            System.out.println(sql);
            connect = new DBConnect().dbConnect();
            try {
                pstm = connect.prepareStatement(sql);
                pstm.setString(1, nhanvien.split("-")[0]);
                rs = pstm.executeQuery();
                while(rs.next()){
                    dinhmuc  = rs.getInt(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(HieuSuat.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                    try {
                        new DBConnect().closeAll(connect, pstm, rs);
                    } catch (SQLException ex) {
                        Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            System.out.println(dinhmuc);
            return dinhmuc;
        }
}
