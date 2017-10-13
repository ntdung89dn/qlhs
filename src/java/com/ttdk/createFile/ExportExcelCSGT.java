/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

import com.ttdk.bean.NhapDon;
import com.ttdk.bean.ThongKe;
import com.ttdk.bean.VanThuCSGT;
import com.ttdk.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.poi.ss.util.RegionUtil;

/**
 *
 * @author ntdung
 */
public class ExportExcelCSGT {
    private Connection connect;
    private PreparedStatement pstm;
    private ResultSet rs;
    
    public ExportExcelCSGT(){}
    
    void createCell(Row row,int cellposition,boolean merger,int frow,int srow,int fcol,int scol,HSSFSheet sheet,String cellValue,boolean setStyle, HSSFWorkbook workbook){
        Cell cell = row.createCell(cellposition);
        
        
        if(setStyle){
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
         
        
         

        if(merger){
//            sheet.addMergedRegion(new CellRangeAddress(
//                    frow, //first row (0-based)
//                   srow, //last row  (0-based)
//                    fcol, //first column (0-based)
//                    scol  //last column  (0-based)
//            ));
            CellRangeAddress range = new CellRangeAddress(frow, srow , fcol, scol );

            sheet.addMergedRegion(range);
            RegionUtil.setBorderBottom(cell.getCellStyle().getBorderBottom(), range, sheet, sheet.getWorkbook());
            RegionUtil.setBorderTop(cell.getCellStyle().getBorderTop(), range, sheet, sheet.getWorkbook());
            RegionUtil.setBorderLeft(cell.getCellStyle().getBorderLeft(), range, sheet, sheet.getWorkbook());
            RegionUtil.setBorderRight(cell.getCellStyle().getBorderRight(), range, sheet, sheet.getWorkbook());

        
        }
        cell.setCellValue(cellValue);
    }
    
    // Lấy tổng số trang văn thư csgt
    public int getTotalPageCSGT(String ngaybatdau,String ngayketthuc,String maonline,String manhan,
            String bnbd,String bbd,String mabuudiens,String noinhan,int cityid){
        int total = 0;
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and (select tbl_vtngaygoi.VT_ngaygoi from tbl_vtngaygoi\n" +
                    "where tbl_vtngaygoi.VTNG_ID = tbl_vtcsgt.VTNG_ID) >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and (select tbl_vtngaygoi.VT_ngaygoi from tbl_vtngaygoi\n" +
                    "where tbl_vtngaygoi.VTNG_ID = tbl_vtcsgt.VTNG_ID) <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        
        if(!bnbd.equals("")){
             whereSql += " and (select group_concat(`KHD_Name` separator ' & ') \n" +
                                    " as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_doncsgt.D_ID order by tbl_bnbd.KHD_ID) like '%"+bnbd.trim()+"%'";
        }
        
        if(!bbd.equals("")){
             whereSql += " and (select group_concat(`BDB_Name` separator ' & ') \n" +
                " as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_doncsgt.D_ID group by D_ID) like '%"+bbd.trim()+"%'";
        }
        if(cityid != 0){
            whereSql += " and tbl_csgtname.C_ID = "+cityid; 
        }
        if(!manhan.equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhan)+"%'";
            }
        }
        if(!mabuudiens.equals("")){
            String[] mabd = manhan.split(",");
            if(mabd.length >1){
                whereSql += " and tbl_don.D_manhan in("+mabuudiens+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(mabuudiens)+"%'";
            }
        }
        
        if(!maonline.equals("")){
            String[] mon = maonline.split(",");
            if(mon.length >1){
                whereSql += " and tbl_don.D_MDO in("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+Integer.parseInt(maonline)+"%'";
            }
        }
        
        String sql= "select count( distinct tbl_vtcsgt.VTGT_ID) "+
                            "from tbl_vtcsgt\n" +
                            "inner join (tbl_doncsgt inner join tbl_don on tbl_don.D_ID = tbl_doncsgt.D_ID)"
                        + "on tbl_doncsgt.VTGT_ID = tbl_vtcsgt.VTGT_ID\n" +
                            "inner join (tbl_csgtdiachi inner join tbl_csgtname on tbl_csgtname.CSN_ID = tbl_csgtdiachi.CSN_ID )\n" +
                            "on tbl_csgtdiachi.CSDC_ID = tbl_vtcsgt.CSDC_ID \n" +
                            " where tbl_don.DK_ID = 3 "+whereSql+
                            "group by tbl_vtcsgt.VTGT_MBD ;";
        
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VanThuCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (total/50000)+1;
    }
    
    // Lấy thống kê csgt
    public ArrayList<ArrayList> tkVtCSGT(int page,String ngaybatdau,String ngayketthuc,String maonline,String manhan,
            String bnbd,String bbd,String mabuudiens,String noinhan,int cityid){
        ArrayList<ArrayList> data = new ArrayList();
        String whereSql = "";
        if(!ngaybatdau.trim().equals("")){
            whereSql += " and (select tbl_vtngaygoi.VT_ngaygoi from tbl_vtngaygoi\n" +
                    "where tbl_vtngaygoi.VTNG_ID = tbl_vtcsgt.VTNG_ID) >= STR_TO_DATE('"+ngaybatdau+"','%d-%m-%Y') ";
        }
        
        if(!ngayketthuc.trim().equals("") ){
            whereSql += " and (select tbl_vtngaygoi.VT_ngaygoi from tbl_vtngaygoi\n" +
                    "where tbl_vtngaygoi.VTNG_ID = tbl_vtcsgt.VTNG_ID) <= STR_TO_DATE('"+ngayketthuc+"','%d-%m-%Y') ";
        }
        
        if(!bnbd.equals("")){
             whereSql += " and (select group_concat(`KHD_Name` separator ' & ') \n" +
                                    " as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_doncsgt.D_ID order by tbl_bnbd.KHD_ID) like '%"+bnbd.trim()+"%'";
        }
        
        if(!bbd.equals("")){
             whereSql += " and (select group_concat(`BDB_Name` separator ' & ') \n" +
                " as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_doncsgt.D_ID group by D_ID) like '%"+bbd.trim()+"%'";
        }
        if(cityid != 0){
            whereSql += " and tbl_csgtname.C_ID = "+cityid; 
        }
        if(!manhan.equals("")){
            String[] manhans = manhan.split(",");
            if(manhans.length >1){
                whereSql += " and tbl_don.D_manhan in("+manhan+")";
            }else{
                whereSql += " and tbl_don.D_manhan like '%"+Integer.parseInt(manhan)+"%'";
            }
        }
        if(!mabuudiens.equals("")){
            String[] mabd = manhan.split(",");
            if(mabd.length >1){
                whereSql += " and tbl_vtcsgt.VTGT_MBD in("+mabuudiens+")";
            }else{
                whereSql += " and tbl_vtcsgt.VTGT_MBD like '%"+Integer.parseInt(mabuudiens)+"%'";
            }
        }
        
        if(!maonline.equals("")){
            String[] mon = maonline.split(",");
            if(mon.length >1){
                whereSql += " and tbl_don.D_MDO in("+maonline+")";
            }else{
                whereSql += " and tbl_don.D_MDO like '%"+Integer.parseInt(maonline)+"%'";
            }
        }
        String sql ="select distinct tbl_vtcsgt.VTGT_ID as vtgtid, tbl_vtcsgt.VTGT_MBD as mabuudien,\n" +
                            "DATE_FORMAT(tbl_vtcsgt.VT_ngaygoi,'%d-%m-%Y' ) as ngaygoi,\n" +
                            "tbl_vtcsgt.VTGT_GhiChu as ghichu,tbl_csgtname.CSN_Name as csgtname,tbl_csgtdiachi.CSDC_Diachi as csgtdc,\n" +
                            "group_concat(tbl_doncsgt.D_ID separator ',') as donids,VTGT_NguoiNhan as nguoinhan \n" +
                            "from tbl_vtcsgt\n" +
                            "inner join (tbl_doncsgt inner join tbl_don on tbl_don.D_ID = tbl_doncsgt.D_ID)"
                        + "on tbl_doncsgt.VTGT_ID = tbl_vtcsgt.VTGT_ID\n" +
                            "inner join (tbl_csgtdiachi inner join tbl_csgtname on tbl_csgtname.CSN_ID = tbl_csgtdiachi.CSN_ID )\n" +
                            "on tbl_csgtdiachi.CSDC_ID = tbl_vtcsgt.CSDC_ID \n" +
                            " where 1=1 "+whereSql+
                            "group by tbl_vtcsgt.VTGT_MBD limit "+((page-1)*50000)+",10;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs =pstm.executeQuery();
            while(rs.next()){
                 ArrayList dt = new ArrayList<>();
                String donids = rs.getString("donids");
                String mabuudien = rs.getString("mabuudien");
                String ngaygoi = rs.getString("ngaygoi");
                String ghichu = rs.getString("ghichu");
                String csgtname = rs.getString("csgtname");
                String csgtdc = rs.getString("csgtdc");
                String nguoinhan = rs.getString("nguoinhan");
                dt.add(donids); dt.add(mabuudien);dt.add(ngaygoi);
                dt.add(ghichu); dt.add(csgtname);dt.add(csgtdc);dt.add(nguoinhan);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExportExcelCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
  
    
    // Lấy ngày nhập theo bnbd và đơn ids
    public ArrayList<String> getNgaynhapCSGT(String donids, String bnbd){
        ArrayList<String> data = new ArrayList();
        String sql = "select distinct DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y' ) as ngaynhap \n" +
                        "from tbl_don\n" +
                        "where tbl_don.D_ID in ("+donids+") and (select group_concat(KHD_Name separator ' & ') \n" +
                        "as Result from tbl_bnbd \n" +
                        "where tbl_bnbd.KHD_ID = tbl_don.D_ID order by KHD_ID) = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, bnbd);
            rs = pstm.executeQuery();
            while(rs.next()){
                String benbaodam = rs.getString("ngaynhap");
                data.add(benbaodam);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExportExcelCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    

    
    // Lấy Số hiệu văn bản và ngày nhập theo bnbd va bbd. so donid
    public ArrayList<ArrayList<String>> getInorDon(String donids){
        ArrayList<ArrayList<String>> data = new ArrayList();
        String sql = "select DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y' ) as ngaynhap,"
                + "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan,"
                + "group_concat(tbl_bnbd.KHD_Name separator '&') as bnbd,group_concat(tbl_benbaodam.BDB_Name separator '&') as benbaodam \n" +
                        "from tbl_don\n" +
                        "left join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID\n" +
                        "left join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID "+
                        " left join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID "+
                        " left join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID  "+
                        "where tbl_don.D_ID in ("+donids+") group by tbl_don.D_ID order by tbl_don.D_Date desc;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                String thoigian = rs.getString("ngaynhap");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = "";
                if(!manhan.equals("0")){
                    maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, thoigian);
                }
                String benbaodam = rs.getString("benbaodam");
                String bnbd = rs.getString("bnbd");
                 dt.add(thoigian);dt.add(maloainhan);dt.add(benbaodam);dt.add(bnbd);
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExportExcelCSGT.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // Tạo excel văn thư csgt
    public HSSFWorkbook exportVanThuExcel(String ngaybatdau,String ngayketthuc,String maonline,String manhan,
            String bnbd,String bbd,String mabuudiens,String noinhan,int cityid){
        int sheetCheck = getTotalPageCSGT(ngaybatdau, ngayketthuc, maonline, manhan, bnbd, bbd, mabuudiens, noinhan, cityid);
        //System.out.println("sheetCheck= "+sheetCheck);
            HSSFWorkbook workbook = new HSSFWorkbook();
                for(int i=1;i<= sheetCheck;i++){
                    HSSFSheet sheet = workbook.createSheet("Trang "+i+"-TK CSGT");
                    Row rowHeader = sheet.createRow(0);
                    createCell(rowHeader, 0,true, 0, 0, 0, 10, sheet,"SỔ CÔNG VĂN THEO DÕI BẢN SAO GỬI CẢNH SÁT GIAO THÔNG CÁC TỈNH",false,workbook);
                    
                     Date date = new Date(); // your date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    Row rowNgay= sheet.createRow(1);
                    createCell(rowNgay, 0, true,1, 1, 0, 10, sheet,"Ngày "+day+"/"+month+"/"+year,false,workbook);


                    Row rowTD = sheet.createRow(3);
                    createCell(rowTD, 0,true, 3, 4, 0, 0, sheet,"STT",true,workbook);
                    createCell(rowTD, 1,true, 3, 4, 1, 1, sheet,"MÃ BƯU ĐIỆN",true,workbook);
                    createCell(rowTD, 2,true, 3, 4, 2, 2, sheet,"NGÀY THÁNG",true,workbook);
                     createCell(rowTD, 3,true, 3, 4, 3, 3, sheet,"BÊN NHẬN",true,workbook);
                    createCell(rowTD, 4,true, 3, 3, 4, 5, sheet,"CSGT/SỞ/CHI CỤC",true,workbook);
                    createCell(rowTD, 6,true, 3, 4, 7, 7, sheet,"NGÀY NHẬP",true,workbook);
                    createCell(rowTD, 7,true, 3, 4, 6, 6, sheet,"BÊN NHẬN BẢO ĐẢM",true,workbook);
                    createCell(rowTD, 8,true, 3, 4, 8, 8, sheet,"BÊN BẢO ĐẢM",true,workbook);
                    createCell(rowTD, 9,true, 3, 4, 9,9, sheet,"SỐ HIỆU VĂN BẢN",true,workbook);
                    createCell(rowTD, 10,true, 3, 4, 10,10, sheet,"GHI CHÚ",true,workbook);
                    Row rowO = sheet.createRow(4);
                    createCell(rowO, 4,false, 4, 4, 4, 4, sheet,"TÊN",true,workbook);
                    createCell(rowO, 5,false, 5, 5,5, 5, sheet,"ĐỊA CHỈ",true,workbook);
                    
//                    dt.add(donids); dt.add(mabuudien);dt.add(ngaygoi);
//                dt.add(ghichu); dt.add(csgtname);
                    
                    int rowCount = 5;
                    int stt = 1;
                    ArrayList<ArrayList> vtcsgt = tkVtCSGT(i, ngaybatdau, ngayketthuc, maonline, manhan, bnbd, bbd, mabuudiens, noinhan, cityid);
                    for(ArrayList csgtList : vtcsgt){
                        Row rowCreate = sheet.createRow(rowCount);
                        String donids = csgtList.get(0).toString();
                        String mabuudien = csgtList.get(1).toString();
                        String ngaygoi = csgtList.get(2).toString();
                        String ghichu = csgtList.get(3).toString();
                        String csgtname = csgtList.get(4).toString();
                        String csgtdc = csgtList.get(5).toString();
                        //String nguoinhan = csgtList.get(6).toString();
                        boolean mergeCheck = true;
                        if((donids.split(",").length-1) ==0){
                            mergeCheck = false;
                        }
                        int lastRow = (donids.split(",").length-1) + rowCount;
                        
                        Row[] nextRowList = new Row[donids.split(",").length];
                        for(int nextRow =0; nextRow < nextRowList.length;nextRow++){
                            
                            nextRowList[nextRow] =  sheet.createRow(rowCount+nextRow);
                            System.out.println(rowCount+nextRow);
                        }
                        createCell(rowCreate, 0, mergeCheck, rowCount, lastRow, 0, 0, sheet, ""+stt, false, workbook);
                        createCell(rowCreate, 1, mergeCheck, rowCount, lastRow, 1,1, sheet, mabuudien, false, workbook);
                        createCell(rowCreate, 2, mergeCheck, rowCount, lastRow, 2, 2, sheet, ngaygoi, false, workbook);
                        createCell(rowCreate, 3, mergeCheck, rowCount, lastRow, 3, 3, sheet, "", false, workbook);
                        createCell(rowCreate, 4, mergeCheck, rowCount, lastRow, 4, 4, sheet, csgtname, false, workbook);
                        createCell(rowCreate, 5, mergeCheck, rowCount, lastRow, 5, 5, sheet, csgtdc, false, workbook);
                        createCell(rowCreate, 10, mergeCheck, rowCount, lastRow, 10, 10, sheet, ghichu, false, workbook);
                        ArrayList<ArrayList<String>> doninfor = getInorDon(donids);
                        //int bnbdCount = bnbdList.size();
                     //   int noNexCount = 0;
                        int rowSHVB = 0;
                     //   boolean mergerDT2 = true;
                        for(ArrayList<String> don : doninfor){
                        //    dt.add(thoigian);dt.add(maloainhan);dt.add(benbaodam);dt.add(bnbd);
                            createCell(nextRowList[rowSHVB], 6, false, rowCount, lastRow, 9, 9, sheet, don.get(0), false, workbook);
                            createCell(nextRowList[rowSHVB], 7, false, rowCount, lastRow, 8, 8, sheet, don.get(3), false, workbook);
                            createCell(nextRowList[rowSHVB], 8, false, rowCount, lastRow, 7, 7, sheet, don.get(2), false, workbook);
                            createCell(nextRowList[rowSHVB], 9, false, rowCount, lastRow, 6, 6, sheet, don.get(1), false, workbook);
                            rowSHVB++;
                        }
                        stt++;
                        rowCount = lastRow + 1;
                    }
                }
        
        return workbook;
    }
}
