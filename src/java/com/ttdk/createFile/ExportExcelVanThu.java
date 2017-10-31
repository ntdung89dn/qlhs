/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

import com.ttdk.bean.KhachhangBean;
import com.ttdk.bean.NhapDon;
import com.ttdk.bean.ThongKe;
import com.ttdk.bean.VanThu;
import com.ttdk.bean.VanThuBean;
import com.ttdk.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author ntdung
 */
public class ExportExcelVanThu {
    private ArrayList<String> list;
    private KhachhangBean khbean = new KhachhangBean();
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    public ExportExcelVanThu(){}
    
    // view thống kê văn thư
    public ArrayList<ArrayList> tkVanthu(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String bnbdS,String btpS,String bbdS,String mabuudien){
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
                whereSql += " and tbl_don.D_MDO in("+maonlineS+")";
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
        if(!bbdS.trim().equals("")){
            whereSql += " and (select group_concat(`BDB_Name` separator ' \\n ')\n" +
                            "as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) like '%"+bbdS+"%'";
        }
        if(!bnbdS.trim().equals("")){
            whereSql += " and (select group_concat(`KHD_Name` separator ' \\n ') \n" +
                            "as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_don.D_ID order by KHD_ID) like '%"+bnbdS+"%'";
        }
        if(!btpS.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name  like '%"+btpS+"%'";
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
                                "where 1=1 "+whereSql
                                + " group by tbl_vanthu.VT_ID order by tbl_vanthu.VT_ngaygoi asc limit "+(page-1)*50000+",50000;";
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
    
     // lấy tổng số trang thống kê văn thư
    public int getTotalPagetkvt(String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String bnbdS,String btpS,String bbdS,String mabuudien){
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
                whereSql += " and tbl_don.D_MDO in("+maonlineS+")";
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
        if(!bbdS.trim().equals("")){
            whereSql += " and (select group_concat(`BDB_Name` separator ' \\n ')\n" +
                            "as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) like '%"+bbdS+"%'";
        }
        if(!bnbdS.trim().equals("")){
            whereSql += " and (select group_concat(`KHD_Name` separator ' \\n ') \n" +
                            "as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_don.D_ID order by KHD_ID) like '%"+bnbdS+"%'";
        }
        if(!btpS.trim().equals("")){
            whereSql += " and tbl_khachhang.KH_Name like '%"+btpS+"%'";
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
                                "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID  \n" +
                                "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID  \n" +
                                "inner join tbl_bnbd on tbl_don.D_ID =  tbl_bnbd.KHD_ID \n" +
                                "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                                "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                                "inner join (tbl_vtdon inner join tbl_vanthu on tbl_vanthu.VT_ID = tbl_vtdon.VT_ID)\n" +
                                "on tbl_don.D_ID = tbl_vtdon.D_ID "+whereSql
                                + " group by tbl_vanthu.VT_ID ;";
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
        return (total/50000)+1;
    }
    // get arraylist data for excel file
    public Map<String, Object[]> getTkVanThu(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String bnbdS,String btpS,String bbdS,String mabuudien){
        Map<String, Object[]> dataArr = new LinkedHashMap<>();
        ArrayList<ArrayList> vtList = tkVanthu(page, ngaybatdau, ngayketthuc, maonlineS, 
                loaidonS, loaihinhnhanS, manhanS, loaidkS, bnbdS, btpS, bbdS, mabuudien);
        System.out.println("VT LIST = "+vtList.size());
         int i=1;
        for(ArrayList data : vtList){
            String donid = data.get(5).toString();
            ArrayList<VanThuBean> vtb = new ThongKe().getDonVT(donid);
           
            for(VanThuBean vt : vtb){
                     dataArr.put(""+(i+1), new Object[] {data.get(1), data.get(2),data.get(3),data.get(4),vt.getDaytime(),vt.getDononline(),
                         vt.getMapin(),vt.getManhan(), vt.getBnbd(),vt.getBtp(),vt.getBbd()});
                i++;
            }
        }
        return dataArr;
    }
    
    public HSSFWorkbook exportVanThuExcel(String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String bnbdS,String btpS,String bbdS,String mabuudien){
        int sheetCheck = getTotalPagetkvt(ngaybatdau, ngayketthuc, maonlineS, loaidonS,
                loaihinhnhanS, manhanS, loaidkS, bnbdS, btpS, bbdS, mabuudien);
        System.out.println("sheetCheck= "+sheetCheck);
            HSSFWorkbook workbook = new HSSFWorkbook();
                for(int i=1;i<= sheetCheck;i++){
                    HSSFSheet sheet = workbook.createSheet("Trang "+i+"-TK văn thư");
                    Map<String, Object[]> data = getTkVanThu(i, ngaybatdau, ngayketthuc, maonlineS, loaidonS,
                            loaihinhnhanS, manhanS, loaidkS, bnbdS, btpS, bbdS, mabuudien);
                    System.out.println("DATA SIZE = "+data.size());
                    Set<String> keyset = data.keySet();
                    Row rowHeader = sheet.createRow(0);
                    createCell(rowHeader, 0,true, 0, 0, 0, 30, sheet,"Thống kê văn thư",false,workbook);
                    
                     Date date = new Date(); // your date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    Row rowNgay= sheet.createRow(1);
                    createCell(rowNgay, 0, true,1, 1, 0, 30, sheet,"Ngày "+day+"/"+month+"/"+year,false,workbook);

                    Row rowSodk = sheet.createRow(2);
                    createCell(rowSodk, 0,true, 2, 2, 0, 30, sheet,"Sổ đăng ký giao dịch bảo đảm",false,workbook);

                    Row rowTD = sheet.createRow(3);
                    createCell(rowTD, 0,true, 3, 4, 0, 0, sheet,"Ngày gởi",true,workbook);
                    createCell(rowTD, 1,true, 3, 4, 1, 1, sheet,"Mã số bưu điện",true,workbook);
                    createCell(rowTD, 2,true, 3, 4, 2, 2, sheet,"Nơi nhận",true,workbook);
                    createCell(rowTD, 3,true, 3, 4, 3, 3, sheet,"Số biên lai",true,workbook);
                    createCell(rowTD, 4,true, 3, 3, 4, 10, sheet,"Thông tin hồ sơ",true,workbook);
                    Row rowO = sheet.createRow(4);
                    createCell(rowO, 4,false, 4, 4, 4, 4, sheet,"Ngày Nhận",true,workbook);
                    createCell(rowO, 5,false, 4, 4,4, 4, sheet,"Số đơn Online",true,workbook);
                    createCell(rowO, 6,false, 4, 4,4, 4, sheet,"Mã Pin",true,workbook);
                    createCell(rowO, 7,false, 4, 4,4, 4, sheet,"Loại hình nhận",true,workbook);
                    createCell(rowO, 8,false, 4, 4,4, 4, sheet,"Bên Nhận Bảo Đảm",true,workbook);
                    createCell(rowO, 9,false, 4, 4,4, 4, sheet,"Bên Nhận Bảo Đảm(TT phí)",true,workbook);
                    createCell(rowO, 10,false, 4, 4,4, 4, sheet,"Bên Bảo Đảm",true,workbook);
                    
  
                    int rownum = 5;
                    for (String key : keyset) {
                            Row row = sheet.createRow(rownum++);
                            Object [] objArr = data.get(key);
                            int cellnum = 0;
                            for (Object obj : objArr) {
                                    Cell cell = row.createCell(cellnum++);
                                    if(obj instanceof Date) 
                                            cell.setCellValue((Date)obj);
                                    else if(obj instanceof Boolean)
                                            cell.setCellValue((Boolean)obj);
                                    else if(obj instanceof String)
                                            cell.setCellValue((String)obj);
                                    else if(obj instanceof Double)
                                            cell.setCellValue((Double)obj);
                            }
                    }
//                    Cell tongBSCell= rowFooter.createCell(12);
//                    tongBSCell.setCellValue(tongBS);
                    
                }
        
        return workbook;
    }
    
    // lấy dữ liệu đơn
    public ArrayList<ArrayList<String>> getDataDonByVTid(int vtid){
         ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "select tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                        "(select tbl_loaidk.DK_Short from tbl_loaidk where tbl_loaidk.DK_ID = tbl_don.DK_ID) as loaidk,\n" +
                        "tbl_don.D_manhan as manhan,\n" +
                        "(select tbl_loainhan.LN_Short from tbl_loainhan where tbl_loainhan.LN_ID = tbl_don.LN_ID) as loainhan,\n" +
                        "(select tbl_loaidon.LD_Des from tbl_loaidon where tbl_loaidon.LD_ID = tbl_don.LD_ID) as loaidon,"
                + " tbl_don.D_MDO as maonline \n" +
                        "from tbl_don\n" +
                        "left join tbl_vtdon on tbl_vtdon.D_ID = tbl_don.D_ID\n" +
                        "where tbl_vtdon.VT_ID = ?;";
        System.out.println(sql);
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
                String maonline = rs.getString("maonline");
                if(maloainhan.equals("CE1700000BD")){
                    maloainhan = "";
                }
                dt.add(maonline);dt.add(maloainhan);dt.add(donid);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    // create excel after save van thu
    public HSSFWorkbook createExcelVTSave(int vtid,String noinhan){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Danh sách đơn");
        Row rowHeader = sheet.createRow(0);
        createCell(rowHeader, 0,true, 0, 0, 0, 30, sheet,"Danh sách đơn",false,workbook);

         Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Row rowNgay= sheet.createRow(1);
        createCell(rowNgay, 0, true,1, 1, 0, 30, sheet,"Ngày "+day+"/"+month+"/"+year,false,workbook);

        Row rowTD = sheet.createRow(3);
        createCell(rowTD, 0,false, 3, 4, 1, 1, sheet,"Mã số bưu điện",true,workbook);
        createCell(rowTD, 1,false, 3, 4, 1, 1, sheet,"Nơi nhận",true,workbook);
        createCell(rowTD, 2,false, 4, 4,4, 4, sheet,"Số đơn Online",true,workbook);
        createCell(rowTD, 3,false, 4, 4,4, 4, sheet,"Số CV",true,workbook);
        createCell(rowTD, 4,false, 4, 4,4, 4, sheet,"Tổng số hồ sơ",true,workbook);
        int rownum = 4;
        ArrayList<String> vtinfor =  new VanThu().getInforVanThuById(vtid);
        ArrayList<ArrayList<String>> dt = getDataDonByVTid(vtid);
        String mabuudien = vtinfor.get(2);
        int totalRow = dt.size();
        Row rowContent = sheet.createRow(rownum);
        
        for(int i =0; i < totalRow;i++){
            int countRow = rownum+i;
            Row rowSub = sheet.createRow(countRow);
            createCell(rowSub, 2,false, rownum, countRow, 1, 1, sheet,dt.get(i).get(0),false,workbook);
            createCell(rowSub, 3,false, rownum, countRow, 1, 1, sheet,dt.get(i).get(1),false,workbook);
        }
        createCell(rowContent, 0,true, rownum, (rownum+totalRow-1), 0, 0, sheet,mabuudien,false,workbook);
        createCell(rowContent, 1,true, rownum, (rownum+totalRow-1), 1, 1, sheet,noinhan,false,workbook);
        createCell(rowContent, 4,true, rownum, (rownum+totalRow-1), 4, 4, sheet,""+totalRow,false,workbook);
        return workbook;
    }
    
    // create cell in excel
    void createCell(Row row,int cellposition,boolean merger,int frow,int srow,int fcol,int scol,HSSFSheet sheet,String cellValue,boolean setStyle, HSSFWorkbook workbook){
        Cell cell = row.createCell(cellposition);
        cell.setCellValue(cellValue);
        if(merger){
            sheet.addMergedRegion(new CellRangeAddress(
                    frow, //first row (0-based)
                   srow, //last row  (0-based)
                    fcol, //first column (0-based)
                    scol  //last column  (0-based)
            ));
        }
        
        if(setStyle){
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
    }
}
