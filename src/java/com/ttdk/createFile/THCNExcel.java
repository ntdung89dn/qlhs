/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

import com.ttdk.bean.ThongKe;
import com.ttdk.bean.Thuphi;
import com.ttdk.bean.VanThuBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
public class THCNExcel {
    Thuphi tp = new Thuphi();
    
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
    
    // get arraylist data for excel file
    public Map<String, Object[]> getTHCN(String ngaybatdau,String ngayketthuc){
        Map<String, Object[]> dataArr = new HashMap<>();
        ArrayList<ArrayList<String>> khList = tp.getKHang(ngayketthuc,-1);
        for(int i=0;i< khList.size();i++){
            int khid = Integer.parseInt(khList.get(i).get(0)) ;
            ArrayList<String> sdkList = tp.getSodauKy(khid,ngaybatdau);
            ArrayList<String> sptList = tp.getSoPhaiThu(khid, ngaybatdau, ngayketthuc);
            ArrayList<String> dtList = tp.getDaThu(khid, ngaybatdau, ngayketthuc);
            ArrayList<String> ckList = tp.getCuoiKy(khid, ngayketthuc);
            ArrayList<String> bcList = tp.getSotienBaoCo(khid, ngaybatdau, ngayketthuc);
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
            Double totalSDK = 0d;
            if(!sdkList.isEmpty()){
                totalSDK = Double.parseDouble(sdkList.get(0));
            }
            Double totalSP = 0d;
            if(!sptList.isEmpty()){
                totalSP = Double.parseDouble(sptList.get(0));
            }
            Double totalDT = 0d;
            if(!dtList.isEmpty()){
                totalDT = Double.parseDouble(dtList.get(0));
            }
            Double totalCK = 0d;
            if(ckList.isEmpty()){
                totalCK = totalSDK + totalSP - totalDT;
                System.out.println("totalCK SUB = "+totalCK);
            }else{
                totalCK = Double.parseDouble(ckList.get(0));
                System.out.println("totalCK = "+totalCK);
            }
            
            Double totalBC = 0d;
            if(!bcList.isEmpty()){
                totalBC = Double.parseDouble(bcList.get(0));
            }
            dataArr.put(""+(i+1), new Object[] {khList.get(i).get(1), khList.get(i).get(2),totalSDK,totalSP,totalDT,totalCK,totalBC});
        }
        return dataArr;
    }
    
    public HSSFWorkbook exportVanThuExcel(String ngaybatdau,String ngayketthuc){
            HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet("Tổng hợp công nợ");
                    Map<String, Object[]> data = getTHCN( ngaybatdau, ngayketthuc);

                    Set<String> keyset = data.keySet();
                    Row rowHeader = sheet.createRow(0);
                    createCell(rowHeader, 0,true, 0, 0, 0, 10, sheet,"TỔNG HỢP CÔNG NỢ",false,workbook);
                    
                     Date date = new Date(); // your date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    Row rowNgay= sheet.createRow(1);
                    createCell(rowNgay, 0, true,1, 1, 0, 10, sheet,"Ngày xuất : "+day+"/"+month+"/"+year,false,workbook);;

                    Row rowTD = sheet.createRow(3);
                    createCell(rowTD, 0,false, 3, 4, 0, 0, sheet,"STT",true,workbook);
                    createCell(rowTD, 1,false, 3, 4, 1, 1, sheet,"TÊN KHÁCH HÀNG",true,workbook);
                    createCell(rowTD, 2,false, 3, 4, 2, 2, sheet,"ĐẦU KỲ",true,workbook);
                    createCell(rowTD, 3,false, 3, 4, 3, 3, sheet,"PHẢI THU",true,workbook);
                    createCell(rowTD, 4,false, 3, 4, 4, 10, sheet,"ĐÃ THU",true,workbook);
                    createCell(rowTD, 5,false, 3, 3, 4, 10, sheet,"CUỐI KỲ",true,workbook);
                    createCell(rowTD, 6,false, 3, 3, 4, 10, sheet,"BÁO CÓ",true,workbook);
                    int rownum = 4;
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
        
        return workbook;
    }
}
