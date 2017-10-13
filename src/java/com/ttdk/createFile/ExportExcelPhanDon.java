/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

import com.ttdk.bean.HieuSuat;
import com.ttdk.bean.KhachhangBean;
import com.ttdk.bean.PhanDonBean;
import com.ttdk.bean.ThongKe;
import com.ttdk.bean.VanThuBean;
import com.ttdk.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author ntdung
 */
public class ExportExcelPhanDon {
    private ArrayList<String> list;
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    
    public ExportExcelPhanDon(){}
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
                        "where 1=1 "+whereSql+
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
    public Map<String, Object[]> getTkVanThu(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String bnbdS,String btpS,String bbdS,String mabuudien){
        Map<String, Object[]> dataArr = new HashMap<String, Object[]>();
        ArrayList<PhanDonBean> pdbList = getSLTSDays(ngaybatdau, ngayketthuc);
        ArrayList<String> nvList = getNVienDuocPhan(ngaybatdau, ngayketthuc);
        
        String table = "<table>";
        String th = "<thead><tr><th>Nhân viên</th>";
        String tbody = "<tbody><tr>";
         int i=0;
        for(String nv : nvList){
            int t = 0;
            tbody += "<tr>";
            tbody += "<td>"+nv+"</td>";
            Object[] dataobject = new Object[pdbList.size()*2 +3];
            dataobject[dataobject.length] = nv;
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
                DecimalFormat df2 = new DecimalFormat(".##");
                double sotsnv = getSLTSNhanvien(nv, pdb.getNgayphan());
                dataobject[dataobject.length] = nv;
                tbody += "<td><span style='font-weight:bold'>"+(int)dinhmuc+"</span>"
                        + "<span style='color:red;font-weight:bold'>(100%)</span></td>";
                tbody += "<td><span style='font-weight:bold'>"+getSLTSNhanvien(nv, pdb.getNgayphan())+"</span>"
                        + "<span style='color:red;font-weight:bold'>("+df2.format((sotsnv/dinhmuc)*100)+"%)</span></td>";
                tongdinhmuc += dinhmuc;
                tongthucte += getSLTSNhanvien(nv, pdb.getNgayphan());
                tongts += pdb.getSlts();
            }
            
            tbody += "</tr>";
        }
        for(PhanDonBean pdb : pdbList){
                 th += "<th colspan='2'>"+pdb.getNgayphan()+"</th>";
         }
        return dataArr;
    }
    
    public HSSFWorkbook exportExcelPD(String ngaybatdau,String ngayketthuc){
            HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet(" Phân đơn");

                  //  Set<String> keyset = data.keySet();
                    Row rowHeader = sheet.createRow(0);
                    createCell(rowHeader, 0,true, 0, 0, 0, 30, sheet,"CỤC QUỐC GIA GIAO DỊCH BẢO ĐẢM TT ĐĂNG KÝ GIAO DỊCH, TÀI SẢN ĐÀ NẴNG",false,workbook,false);
                    
                     Date date = new Date(); // your date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    Row rowNgay= sheet.createRow(1);
                    createCell(rowNgay, 0, true,1, 1, 0, 30, sheet,"NGÀY TẠO FILE "+day+"-"+month+"-"+year,false,workbook,false);

                    Row rowSodk = sheet.createRow(2);
                    createCell(rowSodk, 0,true, 2, 2, 0, 30, sheet,"BẢNG TỔNG HỢP KHỐI LƯỢNG CÔNG VIỆC TỪ "
                            + "NGÀY "+ngaybatdau+" ĐẾN NGÀY "+ngayketthuc,false,workbook,false);
                    
                    Row rowTD = sheet.createRow(3);
                     Row rowsubTD = sheet.createRow(4);
                    int colsTitle = 0;
                    createCell(rowTD, colsTitle,true, 3, 4, 0, 0, sheet,"STT",true,workbook,false);
                    colsTitle += 1;
                    createCell(rowTD, colsTitle,true, 3, 4, 1, 1, sheet,"Nhân Viên",true,workbook,false);
                    colsTitle += 1;
                    HieuSuat hs = new  HieuSuat();
                    ArrayList<String> listNgay = hs.listDay(ngaybatdau, ngayketthuc);
                    for(String ngayphan : listNgay){
                            createCell(rowTD, colsTitle,true, 3, 3, colsTitle, colsTitle+1, sheet,"Ngày "+ngayphan,true,workbook,false);
                           
                            createCell(rowsubTD, colsTitle,false, 4, 4, 0, 0,sheet, "Định Mức",true,workbook,false);
                            createCell(rowsubTD, colsTitle+1,false, 4, 4, 0, 0,sheet, "Thực tế",true,workbook,false);
                            colsTitle += 2;
                     }
                     createCell(rowTD, colsTitle,true, 3, 4, colsTitle, colsTitle, sheet,"Tổng định mức",true,workbook,false);
                    colsTitle += 1;
                     createCell(rowTD, colsTitle,true, 3, 4, colsTitle, colsTitle, sheet,"Tổng Thực tế",true,workbook,false);
                    colsTitle += 1;
                    String listday = hs.getDayPD(ngaybatdau, ngayketthuc);
                    ArrayList<ArrayList<String>> tongsots = hs.listTongTSByDay(listday);
                    ArrayList<String> nvList = hs.soNhanVien(ngaybatdau, ngayketthuc);
                    int rowCount = 5;
                     int stt = 1;
                    for(String nv : nvList){
                        ArrayList<ArrayList<String>> thuctenv = hs.getDinhMucNVByDay(ngaybatdau, ngayketthuc, nv .split("-")[0]);
                        int colsCount = 0;
                        Row rowNV = sheet.createRow(rowCount);
                        createCell(rowNV, colsCount,false, 5, 5, 0, 0, sheet,""+stt,false,workbook,false);
                        colsCount += 1;
                        createCell(rowNV, colsCount,false, 3, 4, 1, 1, sheet,nv.split("-")[1],false,workbook,false);
                        colsCount += 1;
                        int totalTs = 0;
                        int tongthucte = 0;
                        double tongts = 0;
                        for(int i=0;i<tongsots.size();i++){
                            String thisday = tongsots.get(i).get(0);
                            boolean check = hs.checkNVcoHS(nv.split("-")[0], thisday);
                            if(check){
                                int sonv = hs.getSoNVByNgay(thisday);
                                tongts = Double.parseDouble(tongsots.get(i).get(1));
                                int dinhmuc = (int)(tongts/sonv);
                                int tsdu =(int) tongts%sonv;

                                if(tsdu >0){
                                    ArrayList<String> nvH =  hs.nvHighest(thisday, tsdu);
                                    for(String user : nvH){
                                        if(nv.split("-")[0].equals(user)){
                                            dinhmuc = dinhmuc +1;
                                        }
                                    }
                                }
                                totalTs += dinhmuc;
                                String dinhmucStr = new DecimalFormat("##.##").format(((double)dinhmuc/(double)tongts)*100); 
                            //    tbody +="<td style='font-weight:bold;'>"+dinhmuc+"<span style='color:red;'> ("+dinhmucStr+"%)</span></td>";
                            String dinhmucValue =  dinhmuc +"("+dinhmucStr+"%)";
                            createCell(rowNV, colsCount,false, 3, 4, 1, 1, sheet,dinhmucValue,false,workbook,false);
                            colsCount += 1;
                                String thucte = "";
                                for(ArrayList<String> dayofNV : thuctenv){
                                //    System.out.println(dayofNV.get(0));
                                    if(dayofNV.get(0).equals(thisday)){
                                        thucte = dayofNV.get(1);
                                    }

                                }
                                tongthucte += Integer.parseInt(thucte);
                                String thucteStr = new DecimalFormat("##.##").format((Double.parseDouble(thucte)/(double)tongts)*100); 
                                //      tbody +="<td style='font-weight:bold;'>"+thucte+"<span style='color:red;'> ("+thucteStr+"%)</span></td>";
                                String thucteValue = thucte  +"("+thucteStr+"%)";
                                createCell(rowNV, colsCount,false, 3, 4, 1, 1, sheet,thucteValue,false,workbook,false);
                                colsCount += 1;
                            }else{
                                createCell(rowNV, colsCount,false, 3, 4, 1, 1, sheet,"",false,workbook,false);
                                colsCount += 1;
                                createCell(rowNV, colsCount,false, 3, 4, 1, 1, sheet,"",false,workbook,false);
                                colsCount += 1;
                            }
                        }
                        int totalTsEnd = hs.getTongsoTs(ngaybatdau, ngayketthuc);
                        String percentDM = String.valueOf((totalTs*10000)/totalTsEnd);
                        System.out.println("TONG TS = "+totalTsEnd);
                        Double percentValueDM = Double.valueOf(percentDM)/100;
                        createCell(rowNV, colsCount,false, 3, 4, 1, 1, sheet,""+totalTs+"("+percentValueDM+"%)",false,workbook,false);
                        colsCount += 1;
                        //int tongdm = hs.getDinhmucNV(ngaybatdau, ngayketthuc, nv);
                        String percent = String.valueOf((tongthucte*100)/(totalTs));
                        Double percentValue = Double.valueOf(percent)/100;
                        createCell(rowNV, colsCount,false, 3, 4, 1, 1, sheet,""+tongthucte+"("+percentValue+"%)",false,workbook,false);
                        rowCount ++;
                        stt++;
                    }
        return workbook;
    }
    
    void createCell(Row row,int cellposition,boolean merger,int frow,int srow,int fcol,int scol,HSSFSheet sheet,
            String cellValue,boolean setStyle, HSSFWorkbook workbook,boolean textStyle){
        Cell cell = row.createCell(cellposition);
        RichTextString richString = new HSSFRichTextString(cellValue);
        if(textStyle){
            int beginIndex = cellValue.indexOf("(");
            System.out.println("BEGIN = "+beginIndex);
            int endIndex = cellValue.length();
            Font Font1 = workbook.createFont();
            Font1.setColor(HSSFColor.RED.index);
            CellStyle style = workbook.createCellStyle();
            style.setFont(Font1);
            cell.setCellStyle(style);
            richString.applyFont(beginIndex+1, endIndex, Font1);
        }
        cell.setCellValue(richString);
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
    
    public static void differentColorInSingleCell(HSSFWorkbook workbook,Cell cell,String text){
//        Workbook wb = new HSSFWorkbook();
//        Sheet sheet = wb.createSheet("Sheet1");
//        Cell cell = sheet.createRow(0).createCell(0);
        Font Font1 = workbook.createFont();
        Font1.setColor(HSSFColor.RED.index);
        CellStyle style = workbook.createCellStyle();
        style.setFont(Font1);
        cell.setCellStyle(style);
        RichTextString richString = new HSSFRichTextString("RED, BLUE");
        richString.applyFont(4, 9, Font1);
        cell.setCellValue(richString);
        //Write the file Now
    }
    
}
