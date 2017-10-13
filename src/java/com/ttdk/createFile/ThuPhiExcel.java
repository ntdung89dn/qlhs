/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

import com.ttdk.bean.NhapDon;
import com.ttdk.bean.ThuPhiBean;
import com.ttdk.bean.Thuphi;
import com.ttdk.connect.DBConnect;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ThuPhiExcel {
    
    public ThuPhiExcel(){
        
    }
    
    // tạo cell Excel
    void createCell(Row row,int cellposition,boolean merger,int frow,int srow,int fcol,int scol,HSSFSheet sheet,Object cellValue,boolean setStyle, HSSFWorkbook workbook){
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
         if(cellValue instanceof Date) 
                cell.setCellValue((Date)cellValue);
        else if(cellValue instanceof Boolean)
                cell.setCellValue((Boolean)cellValue);
        else if(cellValue instanceof String)
                cell.setCellValue((String)cellValue);
        else if(cellValue instanceof Double)
                cell.setCellValue((Double)cellValue);
    //    cell.setCellValue(cellValue);
    }
    
    // tạo excel chua thu phi BNBD
    public HSSFWorkbook exportChuaThuPhiExcelByBnbd(ArrayList<ThuPhiBean>  data,String title){
            HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet(title);
                  //  Map<String, Object[]> data = getTHCN( ngaybatdau, ngayketthuc);

                    Row rowHeader = sheet.createRow(0);
                    createCell(rowHeader, 0,false, 0, 0, 0, 10, sheet,title,false,workbook);
                    
                     Date date = new Date(); // your date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    Row rowNgay= sheet.createRow(1);
                    createCell(rowNgay, 0, false,1, 1, 0, 10, sheet,"Ngày xuất : "+day+"/"+month+"/"+year,false,workbook);;

                    Row rowTD = sheet.createRow(3);
                    createCell(rowTD, 0,false, 3, 4, 0, 0, sheet,"STT",true,workbook);
                    createCell(rowTD, 1,false, 3, 4, 1, 1, sheet,"TÊN KHÁCH HÀNG",true,workbook);
                    createCell(rowTD, 2,false, 3, 4, 2, 2, sheet,"Tổng tiền",true,workbook);
                    int rownum = 4;
                    int stt = 1;
                    for(ThuPhiBean tbp : data){
                        Row rowNV = sheet.createRow(rownum);
                        String bnbd = tbp.getBnbd() +" ("+tbp.getAccount()+")";
                    //    String tongtien = String.valueOf(tbp.getTongtien());
                        String tongtien =String.valueOf(Double.valueOf(tbp.getTongtien()).intValue());

                        System.out.println(tongtien);
                        createCell(rowNV, 0,false, 3, 4, 0, 0, sheet,""+stt,false,workbook);
                        createCell(rowNV, 1,false, 3, 4, 1, 1, sheet,bnbd,false,workbook);
                        createCell(rowNV, 2,false, 3, 4, 2, 2, sheet,tongtien,false,workbook);
                        rownum++;
                        stt++;
                    }
        
        return workbook;
    }
    
    // tạo excel chua thu phi BBD
    public HSSFWorkbook exportChuaThuPhiExcelByBbd(ArrayList<ThuPhiBean>  data,String khachhang,String title){
            HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet(title);
                    Row rowHeader = sheet.createRow(0);
                    createCell(rowHeader, 0,false, 0, 0, 0, 10, sheet,title+" "+khachhang,false,workbook);
                    
                     Date date = new Date(); // your date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    Row rowNgay= sheet.createRow(1);
                    createCell(rowNgay, 0, false,1, 1, 0, 10, sheet,"Ngày xuất : "+day+"/"+month+"/"+year,false,workbook);
                    
                    Row rowNH= sheet.createRow(2);
                    createCell(rowNH, 0, false,1, 1, 0, 10, sheet,khachhang,false,workbook);
                    
                    Row rowTD = sheet.createRow(4);
                    createCell(rowTD, 0,false, 3, 4, 0, 0, sheet,"STT",true,workbook);
                    createCell(rowTD, 1,false, 3, 4, 1, 1, sheet,"SỐ ĐƠN",true,workbook);
                    createCell(rowTD, 2,false, 3, 4, 2, 2, sheet,"TÊN KHÁCH HÀNG",true,workbook);
                    createCell(rowTD, 3,false, 3, 4, 3, 3, sheet,"Tổng tiền",true,workbook);
                    int rownum = 5;
                    int stt = 1;
                    for(ThuPhiBean tbp : data){
                        Row rowNV = sheet.createRow(rownum);
                        String PL = "";
                        if(tbp.isIsPhuluc()){
                            PL = " (PL 04) ";
                        }
                        String sodon = tbp.getManhan() +PL;
                        String benbaodam = tbp.getBenbaodam();
                    //    String tongtien = String.valueOf(tbp.getTongtien());
                        String tongtien =String.valueOf(Double.valueOf(tbp.getTongtien()).intValue());

                        System.out.println(tongtien);
                        createCell(rowNV, 0,false, 3, 4, 0, 0, sheet,""+stt,false,workbook);
                        createCell(rowNV, 1,false, 3, 4, 1, 1, sheet,sodon,false,workbook);
                        createCell(rowNV, 2,false, 3, 4, 2, 2, sheet,benbaodam,false,workbook);
                        createCell(rowNV, 3,false, 3, 4, 2, 2, sheet,tongtien,false,workbook);
                        rownum++;
                        stt++;
                    }
        
        return workbook;
    }
    
    // tạo excel chua thu phi All
    public HSSFWorkbook exportChuaThuPhiExcelAll(ArrayList<ThuPhiBean>  data,ArrayList<String> searchdata,String title){
            HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet(title);
                    Row rowHeader = sheet.createRow(0);
                    createCell(rowHeader, 0,false, 0, 0, 0, 10, sheet,title,false,workbook);
                    
                     Date date = new Date(); // your date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    Row rowNgay= sheet.createRow(1);
                    createCell(rowNgay, 0, false,1, 1, 0, 10, sheet,"Ngày xuất : "+day+"/"+month+"/"+year,false,workbook);;

                    Row rowTD = sheet.createRow(3);
                    createCell(rowTD, 0,false, 3, 4, 0, 0, sheet,"STT",true,workbook);
                    createCell(rowTD, 1,false, 3, 4, 1, 2, sheet,"BÊN THU PHÍ",true,workbook);
                    createCell(rowTD, 2,false, 3, 4, 2, 2, sheet,"MÃ KHÁCH HÀNG",true,workbook);
                    createCell(rowTD, 3,false, 3, 4, 1, 1, sheet,"SỐ ĐƠN",true,workbook);
                    createCell(rowTD, 4,false, 3, 4, 3, 3, sheet,"BÊN BẢO ĐẢM",true,workbook);
                    createCell(rowTD, 5,false, 3, 4, 3, 3, sheet,"SỐ TIỀN",true,workbook);
                    createCell(rowTD, 6,false, 3, 4, 4, 4, sheet,"TỔNG TIỀN",true,workbook);
                    int rownum =4;
                    int stt= 1;
                    Thuphi tp = new Thuphi();
                    for(ThuPhiBean tbp : data){
                        Row rowNV = sheet.createRow(rownum);
                        String PL = "";
                        if(tbp.isIsPhuluc()){
                            PL = " (PL 04) ";
                        }
                      //  String sodon = tbp.getManhan() +PL;
                       // String benbaodam = tbp.getBenbaodam();
                        String btp = tbp.getBnbd();
                        String makh = tbp.getAccount();
                        String tongtien =String.valueOf(Double.valueOf(tbp.getTongtien()).intValue());
                        String donids = tbp.getDonids();
                        String[] donList = null ;
                        if(donids.length() <1024){
                            donList = donids.split(",");
                        }else{
                            donids = tp.loadDonidsCTP(searchdata.get(0), searchdata.get(1), searchdata.get(2), searchdata.get(3), Integer.parseInt(searchdata.get(4)),tbp.getKhID());
                            donList = donids.split(",");
                        }
                         
                        Connection connect = new DBConnect().dbConnect();
                        if(donList.length>1){
                            int donNum = donList.length+rownum-1;
                            System.out.println("ROW = "+rownum);
                            createCell(rowNV, 0,true, rownum, donNum, 0, 0, sheet,""+stt,false,workbook);
                            createCell(rowNV, 1,true, rownum, donNum, 1, 1, sheet,btp,false,workbook);
                            createCell(rowNV, 2,true, rownum, donNum, 2, 2, sheet,makh,false,workbook);
                            for(int i=0;i< donList.length;i++){
                                Row other  = null;
                                if( i!=0){
                                    other = sheet.createRow((rownum+i));
                                }else{
                                    other = rowNV;
                                }
                                ArrayList<String> donInfor = tp.loadDonByID(Integer.parseInt(donList[i]),connect);
                                String benbaodam = tp.loadBBDDon(Integer.parseInt(donList[i]),connect) + PL;
                                createCell(other, 3,false, rownum, 4, 4, 4, sheet,donInfor.get(0),false,workbook);
                                createCell(other, 4,false, rownum, 4, 4, 4, sheet,benbaodam,false,workbook);
                                createCell(other, 5,false, rownum, 4, 4, 4, sheet,donInfor.get(1),false,workbook);
                            }
                            createCell(rowNV, 6,true, rownum, donNum, 6, 6, sheet,tongtien,false,workbook);
                        }else if(donList.length ==1){
                            
                            createCell(rowNV, 0,false, 3, 4, 0, 0, sheet,""+stt,false,workbook);
                            createCell(rowNV, 1,false, 3, 4, 1, 1, sheet,btp,false,workbook);
                            createCell(rowNV, 2,false, 3, 4, 2, 2, sheet,makh,false,workbook);
                            createCell(rowNV, 3,false, 3, 4, 2, 2, sheet,tbp.getManhan() +PL,false,workbook);
                            createCell(rowNV, 4,false, 3, 4, 2, 2, sheet,tbp.getBenbaodam(),false,workbook);
                            createCell(rowNV, 5,false, 3, 4, 2, 2, sheet,tongtien,false,workbook);
                            createCell(rowNV, 6,false, 3, 4, 2, 2, sheet,tongtien,false,workbook);
                        }else{
                            createCell(rowNV, 0,false, 3, 4, 0, 0, sheet,""+stt,false,workbook);
                            createCell(rowNV, 1,false, 3, 4, 1, 1, sheet,btp,false,workbook);
                            createCell(rowNV, 2,false, 3, 4, 2, 2, sheet,makh,false,workbook);
                            createCell(rowNV, 3,false, 3, 4, 2, 2, sheet,"",false,workbook);
                            createCell(rowNV, 4,false, 3, 4, 2, 2, sheet,"",false,workbook);
                            createCell(rowNV, 5,false, 3, 4, 2, 2, sheet,"",false,workbook);
                            createCell(rowNV, 6,false, 3, 4, 2, 2, sheet,"",false,workbook);
                        }
                try {
                    new DBConnect().closeAll(connect, null, null);
                } catch (SQLException ex) {
                    Logger.getLogger(ThuPhiExcel.class.getName()).log(Level.SEVERE, null, ex);
                }
                        rownum = rownum + donList.length;
                        stt++;
                    }
        return workbook;
    }
    
    // tạo excel chua thu phi BNBD
    public HSSFWorkbook exportThuPhiExcelByBnbd(ArrayList<ArrayList<String>>  data,String title,String fromday,String today,String sohieus){
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title);
          //  Map<String, Object[]> data = getTHCN( ngaybatdau, ngayketthuc);

            Row rowHeader = sheet.createRow(0);
            createCell(rowHeader, 0,false, 0, 0, 0, 10, sheet,title,false,workbook);

             Date date = new Date(); // your date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            Row rowNgay= sheet.createRow(1);
            createCell(rowNgay, 0, false,1, 1, 0, 10, sheet,"Ngày xuất : "+day+"/"+month+"/"+year,false,workbook);;
            Thuphi tp = new Thuphi();
            Row rowTD = sheet.createRow(3);
            createCell(rowTD, 0,false, 3, 4, 0, 0, sheet,"STT",true,workbook);
            createCell(rowTD, 1,false, 3, 4, 1, 1, sheet,"TÊN KHÁCH HÀNG",true,workbook);
            createCell(rowTD, 2,false, 3, 4, 1, 1, sheet,"ĐỊA CHỈ",true,workbook);
            createCell(rowTD, 3,false, 3, 4, 2, 2, sheet,"SỐ HIỆU",true,workbook);
            createCell(rowTD, 4,false, 3, 4, 2, 2, sheet,"NGÀY THU",true,workbook);
            createCell(rowTD, 5,false, 3, 4, 2, 2, sheet,"TỔNG TIỀN",true,workbook);
            int rownum = 4;
            int stt = 1;
            Connection connect = new DBConnect().dbConnect();
            for(ArrayList<String> tbp : data){
                Row rowNV = sheet.createRow(rownum);
                String name = tbp.get(0);
                String diachi = tbp.get(1);
                String dtpids = tbp.get(2);
                if(dtpids.length() >1023){
                    dtpids = tp.loadDTPIDS(fromday, today, name, diachi,sohieus);
                }
            //    String tongtien = String.valueOf(tbp.getTongtien());
           //     String tongtien =String.valueOf(Double.valueOf(tbp.getTongtien()).intValue());
                ArrayList<ArrayList<String>> sohieuList = tp.getSoHieuExcel(dtpids, connect);
           //     System.out.println(tongtien);
                if(sohieuList.size() >1){
                    int numMerger = rownum+ sohieuList.size() -1;
                    createCell(rowNV, 0,true, rownum, numMerger, 0, 0, sheet,""+stt,false,workbook);
                    createCell(rowNV, 1,true, rownum, numMerger, 1, 1, sheet,name,false,workbook);
                    createCell(rowNV, 2,true, rownum, numMerger, 2, 2, sheet,diachi,false,workbook);

                    for(int i=0; i< sohieuList.size();i++){
                        Row rowSH = null;
                        if(i==0){
                            rowSH = rowNV;
                        }else{
                            rowSH = sheet.createRow((rownum+i));
                        }
                        String sohieu = "",tongtien = "",ngaytp = "";
                        if(!sohieuList.isEmpty()){
                            sohieu = sohieuList.get(i).get(1);
                            tongtien = sohieuList.get(i).get(2);
                            ngaytp =  sohieuList.get(i).get(3);
                        }
                        createCell(rowSH, 3,false, 3, 4, 0, 0, sheet,sohieu,false,workbook);
                        createCell(rowSH, 4,false, 3, 4, 0, 0, sheet,ngaytp,false,workbook);
                        createCell(rowSH, 5,false, 3, 4, 1, 1, sheet,tongtien,false,workbook);
                    }
                }else{
                    createCell(rowNV, 0,false, 3, 4, 0, 0, sheet,""+stt,false,workbook);
                    createCell(rowNV, 1,false, 3, 4, 1, 1, sheet,name,false,workbook);
                    createCell(rowNV, 2,false, 3, 4, 1, 1, sheet,diachi,false,workbook);
                    String sohieu = "",tongtien = "",ngaytp = "";
                    if(!sohieuList.isEmpty()){
                        sohieu = sohieuList.get(0).get(1);
                        tongtien = sohieuList.get(0).get(2);
                        ngaytp = sohieuList.get(0).get(3);
                    }
                    createCell(rowNV, 3,false, 3, 4, 2, 2, sheet,sohieu,false,workbook); 
                //    String tongtien = tp.getTongtienSoHieu(Integer.parseInt(sohieuList.get(0).get(0)), connect);
                    createCell(rowNV, 4,false, 3, 4, 2, 2, sheet,ngaytp,false,workbook);
                    createCell(rowNV, 5,false, 3, 4, 2, 2, sheet,tongtien,false,workbook);
                }

                rownum = rownum + sohieuList.size();
                stt++;
            }
             try {
                    new DBConnect().closeAll(connect, null, null);
                } catch (SQLException ex) {
                    Logger.getLogger(ThuPhiExcel.class.getName()).log(Level.SEVERE, null, ex);
                }
        return workbook;
    }
    
    // tạo excel chua thu phi BBD
    public HSSFWorkbook exportThuPhiExcelByBbd(ArrayList<ThuPhiBean>  data,String khachhang,String title,String fromday,String today){
            HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet(title);
                    Row rowHeader = sheet.createRow(0);
                    createCell(rowHeader, 0,false, 0, 0, 0, 10, sheet,title+" "+khachhang.replaceAll("_", " - "),false,workbook);
                    
                     Date date = new Date(); // your date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    Row rowNgay= sheet.createRow(1);
                    createCell(rowNgay, 0, false,1, 1, 0, 10, sheet,"Ngày xuất : "+day+"/"+month+"/"+year,false,workbook);
                    
                    Row rowNH= sheet.createRow(2);
                    createCell(rowNH, 0, false,1, 1, 0, 10, sheet,khachhang.replaceAll("_", " - "),false,workbook);
                    
                    Row rowTD = sheet.createRow(4);
                    createCell(rowTD, 0,false, 3, 4, 0, 0, sheet,"STT",true,workbook);
                    createCell(rowTD, 1,false, 3, 4, 1, 1, sheet,"SỐ HIỆU",true,workbook);
                    createCell(rowTD, 2,false, 3, 4, 2, 2, sheet,"NGÀY TẠO",true,workbook);                   
                    createCell(rowTD, 3,false, 3, 4, 3, 3, sheet,"NGÀY NHẬP ĐƠN",true,workbook);
                    createCell(rowTD, 4,false, 3, 4, 3, 3, sheet,"SỐ ĐƠN",true,workbook);
                    createCell(rowTD, 5,false, 3, 4, 3, 3, sheet,"BÊN BẢO ĐẢM",true,workbook);
                    createCell(rowTD, 6,false, 3, 4, 3, 3, sheet,"GIÁ TIỀN",true,workbook);
                    createCell(rowTD, 7,false, 3, 4, 2, 2, sheet,"TỔNG TIỀN",true,workbook);
                    int rownum = 5;
                    int stt = 1;
                    Thuphi tp = new Thuphi();
                    Connection conn = new DBConnect().dbConnect();
                    for(ThuPhiBean tbp : data){
                        Row rowNV = sheet.createRow(rownum);
                        String sohieu = tbp.getSohieu();
                        String ngaytao = tbp.getNgaytp();
                        String tongtien =String.valueOf(Double.valueOf(tbp.getTongtien()).intValue());
                        String donids = tbp.getDonids();
                        if(tbp.getDonids() !=null){
                             if(donids.length() >1023){
                                donids = tp.loadDTPIDS(fromday, today, khachhang.split(" _ ")[0], khachhang.split(" _ ")[1], sohieu);
                            }
                            if(donids.split(",").length >1){
                                int lastRow = rownum + donids.split(",").length -1;
                                createCell(rowNV, 0,true, rownum, lastRow, 0, 0, sheet,""+stt,false,workbook);
                                createCell(rowNV, 1,true, rownum, lastRow, 1, 1, sheet,sohieu,false,workbook);
                                createCell(rowNV, 2,true, rownum, lastRow, 2, 2, sheet,ngaytao,false,workbook);
                                for(int i=0; i< donids.split(",").length;i++){
                                    Row rowDon = null;
                                    if(i==0){
                                        rowDon = rowNV;
                                    }else{
                                        rowDon =sheet.createRow((rownum+i));
                                    }
                                    int donid = Integer.parseInt(donids.split(",")[i]);
                                    ArrayList<String> donInfor = tp.loadDonByID(donid, conn);
                                    String mdo = donInfor.get(0);
                                    String ngaynhap = donInfor.get(3);
                                    String giatien = donInfor.get(1);
                                    String PL = donInfor.get(2);
                                    String benbaodam  = tp.loadBBDDon(donid,conn) + PL;
                                    createCell(rowDon, 3,false, 3, 4, 2, 2, sheet,ngaynhap,false,workbook);
                                    createCell(rowDon, 4,false, 3, 4, 2, 2, sheet,mdo,false,workbook);
                                    createCell(rowDon, 5,false, 3, 4, 2, 2, sheet,benbaodam,false,workbook);
                                    createCell(rowDon, 6,false, 3, 4, 2, 2, sheet,giatien,false,workbook);
                                }
                                createCell(rowNV, 7,true, rownum, lastRow, 7, 7, sheet,tongtien,false,workbook);
                            }else{
                                createCell(rowNV, 0,false, 3, 4, 0, 0, sheet,""+stt,false,workbook);
                                createCell(rowNV, 1,false, 3, 4, 1, 1, sheet,sohieu,false,workbook);
                                createCell(rowNV, 2,false, 3, 4, 2, 2, sheet,ngaytao,false,workbook);
                                ArrayList<String> donInfor = tp.loadDonByID(Integer.parseInt(donids), conn);
                                String mdo = donInfor.get(0);
                                String ngaynhap = donInfor.get(3);
                                String giatien = donInfor.get(1);
                                String PL = donInfor.get(2);
                                String benbaodam  = tp.loadBBDDon(Integer.parseInt(donids),conn) + PL;
                                createCell(rowNV, 3,false, 3, 4, 2, 2, sheet,ngaynhap,false,workbook);
                                createCell(rowNV, 4,false, 3, 4, 2, 2, sheet,mdo,false,workbook);
                                createCell(rowNV, 5,false, 3, 4, 2, 2, sheet,benbaodam,false,workbook);
                                createCell(rowNV, 6,false, 3, 4, 2, 2, sheet,giatien,false,workbook);
                                createCell(rowNV, 7,false, 3, 4, 2, 2, sheet,tongtien,false,workbook);
                            }
                            rownum = rownum + donids.split(",").length;
                        }else{
                            createCell(rowNV, 0,false, 3, 4, 0, 0, sheet,""+stt,false,workbook);
                                createCell(rowNV, 1,false, 3, 4, 1, 1, sheet,sohieu,false,workbook);
                                createCell(rowNV, 2,false, 3, 4, 2, 2, sheet,ngaytao,false,workbook);
                                createCell(rowNV, 3,false, 3, 4, 2, 2, sheet,"",false,workbook);
                                createCell(rowNV, 4,false, 3, 4, 2, 2, sheet,"",false,workbook);
                                createCell(rowNV, 5,false, 3, 4, 2, 2, sheet,"",false,workbook);
                                createCell(rowNV, 6,false, 3, 4, 2, 2, sheet,"",false,workbook);
                                createCell(rowNV, 7,false, 3, 4, 2, 2, sheet,"",false,workbook);
                                rownum = rownum + 1;
                        }
                        stt++;
                    }
                    try {
                            new DBConnect().closeAll(conn, null, null);
                        } catch (SQLException ex) {
                            Logger.getLogger(ThuPhiExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }
        return workbook;
    }
    
     // tạo excel thu phi đầy đủ
    public HSSFWorkbook exportThuPhiExcelAll(String donids,String title,String fromday,String today,String sohieus,String bnp){
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(title);
          //  Map<String, Object[]> data = getTHCN( ngaybatdau, ngayketthuc);

            Row rowHeader = sheet.createRow(0);
            createCell(rowHeader, 0,false, 0, 0, 0, 10, sheet,title,false,workbook);

            Date date = new Date(); // your date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            Row rowNgay= sheet.createRow(1);
            createCell(rowNgay, 0, false,1, 1, 0, 10, sheet,"Ngày xuất : "+day+"/"+month+"/"+year,false,workbook);;
            Thuphi tp = new Thuphi();
            Row rowTD = sheet.createRow(3);
            createCell(rowTD, 0,false, 3, 4, 0, 0, sheet,"STT",true,workbook);
            createCell(rowTD, 1,false, 3, 4, 1, 1, sheet,"TÊN KHÁCH HÀNG",true,workbook);
            createCell(rowTD, 2,false, 3, 4, 1, 1, sheet,"ĐỊA CHỈ",true,workbook);
            createCell(rowTD, 3,false, 3, 4, 2, 2, sheet,"SỐ HIỆU",true,workbook);
            createCell(rowTD, 4,false, 3, 4, 2, 2, sheet,"NGÀY THU",true,workbook);
            createCell(rowTD, 5,false, 3, 4, 3, 3, sheet,"NGÀY NHẬP ĐƠN",true,workbook);
            createCell(rowTD, 6,false, 3, 4, 3, 3, sheet,"SỐ ĐƠN",true,workbook);
            createCell(rowTD, 7,false, 3, 4, 3, 3, sheet,"BÊN BẢO ĐẢM",true,workbook);
            createCell(rowTD, 8,false, 3, 4, 3, 3, sheet,"GIÁ TIỀN",true,workbook);
            createCell(rowTD, 9,false, 3, 4, 2, 2, sheet,"TỔNG TIỀN",true,workbook);
            int rownum = 4;
            int stt = 1;
            ArrayList<ArrayList<String>> data = tp.getInforDTPforExcel(fromday, today, sohieus, bnp, donids);
            for(ArrayList<String> tbp : data){
                Row rowNV = sheet.createRow(rownum);
//                dt.add(tendonvi);dt.add(diachi);dt.add(total);dt.add(sohieurs);
//                dt.add(tbpday);
                String name = tbp.get(0);
                String diachi = tbp.get(1);
                String total = tbp.get(2);
                String sohieu = tbp.get(3);
                String tbpday = tbp.get(4);
                int tbpid = Integer.parseInt(tbp.get(5));
                ArrayList<ArrayList<String>> donList = tp.getInforDonByTBPID(tbpid);
                int lastRow = 0;
                boolean mergeCell = false;
                if(donList.size() >1){
                    lastRow = rownum + donList.size()-1;
                    mergeCell = true;
                }
                createCell(rowNV, 0,mergeCell, rownum,lastRow, 0, 0, sheet,stt,false,workbook);
                createCell(rowNV, 1,mergeCell, rownum,  lastRow, 1, 1, sheet,name,false,workbook);
                createCell(rowNV, 2,mergeCell, rownum,lastRow, 2, 2, sheet,diachi,false,workbook);
                createCell(rowNV, 3,mergeCell, rownum,lastRow, 3, 3, sheet,sohieu,false,workbook);
                createCell(rowNV, 4,mergeCell, rownum, lastRow, 4, 4, sheet,tbpday,false,workbook);
                createCell(rowNV, 9,mergeCell, rownum,lastRow, 9, 9, sheet,total,false,workbook);
                for(int i = 0; i < donList.size();i++){
                    Row rowSub ;
                    if(i==0){
                        rowSub = rowNV;
                    }else{
                        rowSub = sheet.createRow(rownum);
                    }
                    createCell(rowSub, 5,false, 3, 4, 1, 1, sheet,donList.get(i).get(0),false,workbook);
                    createCell(rowSub, 6,false, 3, 4, 1, 1, sheet,donList.get(i).get(1),false,workbook);
                    createCell(rowSub, 7,false, 3, 4, 3, 3, sheet,donList.get(i).get(2),false,workbook);
                    createCell(rowSub, 8,false, 3, 4, 3, 3, sheet,donList.get(i).get(3),false,workbook);
                    rownum++;
                }
                stt++;
            }
             
        return workbook;
    }
    
    
    // Xuất file Excel để upload đơn thanh toán cho cục
    public HSSFWorkbook exportDTPCuc(ArrayList<ArrayList<String>> donList){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Đơn đã thu phí");
        Row rowHeader = sheet.createRow(0);
        createCell(rowHeader, 0,false, 0, 0, 0, 10, sheet,"Đơn đã thu phí",false,workbook);

        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Row rowNgay= sheet.createRow(1);
        createCell(rowNgay, 0, false,1, 1, 0, 10, sheet,"Ngày xuất : "+day+"/"+month+"/"+year,false,workbook);;
        Thuphi tp = new Thuphi();
        Row rowTD = sheet.createRow(3);
        createCell(rowTD, 0,false, 3, 4, 0, 0, sheet,"STT",true,workbook);
        createCell(rowTD, 1,false, 3, 4, 1, 1, sheet,"TÊN KHÁCH HÀNG",true,workbook);
        createCell(rowTD, 2,false, 3, 4, 1, 1, sheet,"MÃ KHÁCH HÀNG",true,workbook);
        createCell(rowTD, 3,false, 3, 4, 3, 3, sheet,"NGÀY NHẬP ĐƠN",true,workbook);
        createCell(rowTD, 4,false, 3, 4, 3, 3, sheet,"SỐ ĐƠN",true,workbook);
        createCell(rowTD, 5,false, 3, 4, 3, 3, sheet,"GIÁ TIỀN",true,workbook);
        int rownum = 4;
        int stt = 1;
        for(ArrayList<String> data : donList){
            Row rowKH = sheet.createRow(rownum);
            createCell(rowKH, 0,false, 3, 4, 0, 0, sheet,""+stt,false,workbook);
            createCell(rowKH, 1,false, 3, 4, 1, 1, sheet,data.get(0),false,workbook);
            createCell(rowKH, 2,false, 3, 4, 1, 1, sheet,data.get(1),false,workbook);
            createCell(rowKH, 3,false, 3, 4, 3, 3, sheet,data.get(2),false,workbook);
            createCell(rowKH, 4,false, 3, 4, 3, 3, sheet,data.get(3),false,workbook);
            createCell(rowKH, 5,false, 3, 4, 3, 3, sheet,data.get(4),false,workbook);
            rownum++;
            stt++;
        }
         return workbook;
    }
}
