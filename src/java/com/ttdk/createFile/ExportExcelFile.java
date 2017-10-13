/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

/**
 *
 * @author ntdung
 */
import com.ttdk.bean.Khachhang;
import com.ttdk.bean.KhachhangBean;
import com.ttdk.bean.NhapDon;
import com.ttdk.bean.ThongKe;
import com.ttdk.bean.Thuphi;
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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportExcelFile {
    private ArrayList<String> list;
    private KhachhangBean khbean = new KhachhangBean();
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    private ArrayList<ArrayList<String>> data;
    
    public ExportExcelFile(){}
    
    // Xuất excel cho thống kê biên lai
    // Lấy tổng số trang thống kê biên lai tìm kiếm
    private int getPageSearchTKBL(String ngaybatdau,String ngayketthuc,String sobienlai,int loaitt){
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
        String sql = "select count(distinct tbl_dondatp.D_ID)\n" +
                        "from tbl_dondatp\n" +
                        "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                        "inner join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                        "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                        " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                        "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID)\n" +
                        "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID where 1=1 "
                + whereSql+";";
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
                Logger.getLogger(ExportExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (currentpage/50000)+1;
    }
    
    // lấy mảng TKBL
    private Map<String, Object[]> getArrayTKBL(int page,String ngaybatdau,String ngayketthuc,String sobienlai,int loaitt){
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
        String sql = "select  DATE_FORMAT(tbl_dontp.DTP_Date,'%d-%m-%Y') as ngaynop,tbl_dontp.DTP_Sohieu as sohieu,\n" +
                        "DATE_FORMAT(tbl_baoco.BC_Date,'%d-%m-%Y') as ngaybaoco, tbl_baoco.BC_Number as sobaoco,\n" +
                        "(select tbl_khachhang.KH_Account from tbl_khachhang "
                + " where tbl_khachhang.KH_ID = tbl_don.KH_ID  ) as taikhoan,"+
                        "tbl_baoco.BC_Total as sotienbc,tbl_baoco.BC_ND as noidung, tbl_dontp.DTP_TDV as bennp,\n" +
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
                        "group_concat(DISTINCT tbl_dontp.DTP_Sohieu SEPARATOR  ',') as sohieu \n" +
                    //    " group_concat(DISTINCT tbl_don.D_ID SEPARATOR  ',') as donids\n" +
                        "from tbl_dondatp\n" +
                        "inner join tbl_don on tbl_don.D_ID = tbl_dondatp.D_ID\n" +
                        "inner join ( tbl_dontp left join (tbl_thanhtoanbienlai left join \n" +
                        "(tbl_baoco left join tbl_baocokh on tbl_baocokh.BC_ID = tbl_baoco.BC_ID)\n" +
                        " on tbl_baoco.BC_ID = tbl_thanhtoanbienlai.BC_ID )\n" +
                        "on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID)\n" +
                        "on tbl_dondatp.DTP_ID = tbl_dontp.DTP_ID  where 1=1 "
                + whereSql+" group by tbl_dondatp.DTP_ID order by tbl_dontp.DTP_ID desc limit "+(page-1)*50000+",50000;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();
        data.put("1", new Object[] {"STT", "Ngày nộp", "Số hiệu","Ngày BC","Số BC","ND BC",
            "Số Tài Khoản","Tên Khách Hàng","Diễn giải","Tổng tiền","GDBĐ","CC-TT","BẢN SAO"});
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
                    //int sotienbc = rs.getInt("sotienbc");
                    String noidung = rs.getString("noidung");
                    String taikhoan = rs.getString("taikhoan");
                    String tendv = rs.getString("bennp");
                    String lydo = rs.getString("lydo");
                    double tongtien = rs.getDouble("tongtien");
                    double phiBD = rs.getDouble("phiBD");
                    double phiBS = rs.getDouble("phiBS");
                    double phiTT = rs.getDouble("phiTT");
                    data.put(""+(i+1), new Object[] {""+i,ngaynop, sohieu,ngaybc,sobc,noidung,taikhoan,
                        tendv,lydo,tongtien,phiBD,phiTT,phiBS});
                    i++;
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ExportExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // xuất file excel cho thống kê biên lai
    public HSSFWorkbook exportExcelTKBL(String ngaybatdau,String ngayketthuc,String sobienlai,int loaitt){
            int sheetCheck = getPageSearchTKBL( ngaybatdau, ngayketthuc, sobienlai, loaitt);
            Double tongtien = new Thuphi().getTongTien(ngaybatdau, ngayketthuc, sobienlai, loaitt);
            Double tongBD = new Thuphi().getTongBD(ngaybatdau, ngayketthuc, sobienlai, loaitt);
            Double tongBS = new Thuphi().getTongBS(ngaybatdau, ngayketthuc, sobienlai, loaitt);
             Double tongTT = new Thuphi().getTongTT(ngaybatdau, ngayketthuc, sobienlai, loaitt);
            HSSFWorkbook workbook = new HSSFWorkbook();
                for(int i=1;i<= sheetCheck;i++){
                    HSSFSheet sheet = workbook.createSheet("Trang "+i+"-TKBL");
                    Map<String, Object[]> data = getArrayTKBL(i,ngaybatdau, ngayketthuc, sobienlai, loaitt);

                    Set<String> keyset = data.keySet();
                    Row rowHeader = sheet.createRow(0);
                    Cell cellHeader = rowHeader.createCell(0);
                    cellHeader.setCellValue("SỔ ĐĂNG KÝ GIAO DỊCH BẢO ĐẢM:");
                    sheet.addMergedRegion(new CellRangeAddress(
                            0, //first row (0-based)
                            0, //last row  (0-based)
                            0, //first column (0-based)
                            30  //last column  (0-based)
                    ));
                    int rownum = 1;
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
                    Row rowFooter = sheet.createRow(rownum+1);
                    Cell cellFooter = rowFooter.createCell(4);
                    cellFooter.setCellValue("Tổng tiền:");
                    sheet.addMergedRegion(new CellRangeAddress(
                            rownum+1, //first row (0-based)
                            rownum+1, //last row  (0-based)
                            4, //first column (0-based)
                            8  //last column  (0-based)
                    ));
                    Cell tongtienCell= rowFooter.createCell(9);
                    tongtienCell.setCellValue(tongtien);
                    Cell tongBDCell= rowFooter.createCell(10);
                    tongBDCell.setCellValue(tongBD);
                    Cell tongTTCell= rowFooter.createCell(11);
                    tongTTCell.setCellValue(tongTT);
                    Cell tongBSCell= rowFooter.createCell(12);
                    tongBSCell.setCellValue(tongBS);
                    
                }
        return workbook;
    }
    
    // Xuất Excel cho thống kê báo có
    // Lấy tổng số trang thống kê báo có tìm kiếm
    private int getPageSearchTKBC(String ngaybatdau,String ngayketthuc,String sobaoco,String sotaikhoan,String bnbd,int checktien){
        int currentpage = 0 ;
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
        String sql = "select count(*)\n" +
                        "from tbl_baoco \n" +
                        "LEFT JOIN tbl_baocokh ON tbl_baocokh.BC_ID = tbl_baoco.BC_ID \n" +
                        " where 1=1 "+whereSql+";";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                currentpage = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ExportExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (currentpage/50000)+1;
    }
    
    // lấy mảng TKBL
    private ArrayList<BaoCoExcelBean> getArrayTKBC(int page,String ngaybatdau,String ngayketthuc,String sobaoco,
            String khids,int checktien){
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
         if(!khids.trim().equals("")){
             whereSql += " and tbl_baocokh.KH_ID in ("+khids+")";
         }
         if(checktien !=0){
             if(checktien ==1){
                 whereSql += " and tbl_baoco.BC_Thua >=0";
             }else if(checktien ==2){
                 whereSql += " and tbl_baoco.BC_Thua < 0 ";
             }
         }
        String sql = "select tbl_baoco.BC_ID as bcid, DATE_FORMAT(tbl_baoco.BC_Date,'%d-%m-%Y') as ngaybc, tbl_baoco.BC_Number as sobc,\n" +
                            "ifnull((select tbl_khachhang.KH_Account from tbl_khachhang where  tbl_khachhang.KH_ID =  tbl_baocokh.KH_ID),'') as taikhoan,\n" +
                            "ifnull((select tbl_khachhang.KH_Name from tbl_khachhang where  tbl_khachhang.KH_ID =  tbl_baocokh.KH_ID),'') as bnbd,\n" +
                            "tbl_baoco.BC_ND as noidung,\n" +
                            "tbl_baoco.BC_Total as tongtienbc,tbl_baoco.BC_Thua as tienthua "+
                        "from tbl_baoco\n" +
                        "left join tbl_baocokh ON tbl_baocokh.BC_ID = tbl_baoco.BC_ID \n" 
                + "where 1=1 "
                +whereSql+" limit "+(page-1)*50000+",50000 ;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
//        Map<String, Object[]> data = new HashMap<String, Object[]>();
//        data.put("1", new Object[] {"STT", "Ngày Báo có", "Số báo có","Mã khách hàng","Tên khách hàng","Số hiệu","Nội dung báo có"
//           ,"Số tiền báo có","Tổng tiền thanh toán","Tiền thừa"});
        ArrayList<BaoCoExcelBean> data = new ArrayList<>();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
      //      int i=1;
            while(rs.next()){
                int bcid = rs.getInt("bcid");
                String ngaybc = rs.getString("ngaybc");
                String sobc = rs.getString("sobc");
                String taikhoan = rs.getString("taikhoan");
                String bnbdrs = rs.getString("bnbd");
               // String sohieu = rs.getString("sohieu");
                String noidungbc = rs.getString("noidung");
                String sotienbc = rs.getString("tongtienbc");
                String tienthua = rs.getString("tienthua");
               // String tongtientt = String.valueOf((Double.valueOf(sotienbc)-Double.valueOf(tienthua)));
//                    data.put(""+(i+1), new Object[] {""+i,ngaybc, sobc,taikhoan,bnbdrs,sohieu,noidungbc,sotienbc,tongtientt,
//                        tienthua});
                BaoCoExcelBean bcData = new BaoCoExcelBean();
                bcData.setBcid(bcid);bcData.setNgaybc(ngaybc);
                bcData.setSobc(sobc);bcData.setMakh(taikhoan);
                bcData.setTenkh(bnbdrs);bcData.setNdbc(noidungbc);
                bcData.setTongtienbc(sotienbc);bcData.setTienthua(tienthua);
                data.add(bcData);
               //     i++;
                }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ExportExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    // Lấy thông tin biên lai từ báo có
    public ArrayList<ArrayList<String>> getBLTuBaoCoId(int bcid){
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String sql = "SELECT DTP_Sohieu,DTP_Total FROM qlhsdb.tbl_dontp \n" +
                "inner join tbl_thanhtoanbienlai on tbl_thanhtoanbienlai.DTP_ID = tbl_dontp.DTP_ID\n" +
                "where tbl_thanhtoanbienlai.BC_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, bcid);
            rs = pstm.executeQuery();
            while(rs.next()){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(rs.getString(1));
                dt.add(rs.getString(2));
                data.add(dt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExportExcelFile.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ExportExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    public ArrayList<BaoCoExcelBean> dataTKBCExcel(int page,String ngaybatdau,String ngayketthuc,String sobaoco,
            String khids,int checktien){
        ArrayList<BaoCoExcelBean> bclist = getArrayTKBC(page, ngaybatdau, ngayketthuc, sobaoco, khids, checktien);
        bclist.forEach((bcInfor) -> {
            ArrayList<ArrayList<String>> bienlaiList = getBLTuBaoCoId(bcInfor.getBcid());
            bcInfor.setBienlailist(bienlaiList);
        });
        return bclist;
    }
    // Lấy tổng tiền các loại
    public ArrayList<Long> getTongTienBC(String ngaybatdau,String ngayketthuc,String sobaoco,String sotaikhoan,String bnbd,int checktien){
        ArrayList<Long> bcArr = new ArrayList<>();
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
        String sql = "select ifnull(SUM(tbl_baoco.BC_Total),0) as tongtienbc,ifnull(SUM(tbl_baoco.BC_Thua),0) as tienthua\n" +
                        "from tbl_baoco\n" +
                        " where 1=1 "+whereSql+";";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                long tongtienbc = rs.getLong("tongtienbc");
                long tongtienthua = rs.getLong("tienthua");
                long tongtientt = tongtienbc - tongtienthua;
                bcArr.add(tongtienbc);
                bcArr.add(tongtientt);
                bcArr.add(tongtienthua);
               // table += "<tr><td colspan='5' style='text-align:right;'>Tổng cộng</td><td>"+tongtienbc+"</td><td>"+tongtientt+"</td><td>"+tongtienthua+"</td><td></td></tr>";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ExportExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bcArr;
    }
    // xuất file excel cho thống kê biên lai
    public HSSFWorkbook exportExcelTKBC(String ngaybatdau,String ngayketthuc,String sobaoco,String sotaikhoan,String bnbd,int checktien){
            int sheetCheck = getPageSearchTKBC(ngaybatdau, ngayketthuc, sobaoco, sotaikhoan, bnbd, checktien);
            ArrayList<Long> tongtien = getTongTienBC(ngaybatdau, ngayketthuc, sobaoco, sotaikhoan, bnbd, checktien);
            HSSFWorkbook workbook = new HSSFWorkbook();
                for(int i=1;i<= sheetCheck;i++){
                    HSSFSheet sheet = workbook.createSheet("Trang "+i+"-TKBL");
                    String khids = "";
                    if(!bnbd.equals("") && !sotaikhoan.equals("")){
                        ArrayList<String> khList = new Khachhang().getKHID(bnbd, sotaikhoan);
                        khids = khList.toString().replaceAll("\\]", "").replaceAll("\\[", "");
                    }
                    
                     
                //    Map<String, Object[]> data1 = new HashMap<String, Object[]>();
        
                   // Map<String, Object[]> data = getArrayTKBC(i, ngaybatdau, ngayketthuc, sobaoco,khids, checktien);
                    ArrayList<BaoCoExcelBean> bcList = dataTKBCExcel(i, ngaybatdau, ngayketthuc, sobaoco, khids, checktien);
                 //   Set<String> keyset = data.keySet();
                    Row rowHeader = sheet.createRow(0);
                    Cell cellHeader = rowHeader.createCell(0);
                    cellHeader.setCellValue("Thống kê báo có:");
                    sheet.addMergedRegion(new CellRangeAddress(
                            0, //first row (0-based)
                            0, //last row  (0-based)
                            0, //first column (0-based)
                            30  //last column  (0-based)
                    ));
                    int rownum = 1;
                    Row rowTitle = sheet.createRow(rownum);
                    createCell(rowTitle, 0,false, 3, 4, 0, 0, sheet,"STT",true,workbook);
                    createCell(rowTitle, 1,false, 3, 4, 1, 1, sheet,"Ngày Báo có",true,workbook);
                    createCell(rowTitle, 2,false, 3, 3, 2, 3, sheet,"Số báo có",true,workbook);
                    createCell(rowTitle, 3,false, 3, 4, 4, 4, sheet,"Mã khách hàng",true,workbook);
                    createCell(rowTitle, 4,false, 3, 4, 5, 5, sheet,"Tên khách hàng",true,workbook);
                    createCell(rowTitle, 5,false, 3, 4, 6, 6, sheet,"Số hiệu",true,workbook);
                    createCell(rowTitle,6,false, 3, 4, 7, 7, sheet,"Nội dung báo có",true,workbook);
                    createCell(rowTitle,7,false, 3, 4, 8, 8, sheet,"Số tiền báo có",true,workbook);
                    createCell(rowTitle,8,false, 3, 4, 9, 9, sheet,"Tổng tiền thanh toán",true,workbook);
                    createCell(rowTitle,9,false, 3, 4, 10, 10, sheet,"Tiền thừa",true,workbook);
                     rownum ++;
                     int stt = 1;
                    for (BaoCoExcelBean bcBean : bcList) {
                            Row row = sheet.createRow(rownum);
//                            Object [] objArr = data.get(key);
                        ArrayList<ArrayList<String>> blInfor = bcBean.getBienlailist();
                        int groupLength = blInfor.size() + rownum-1;
                        if(blInfor.size() <=1){
                            createCell(row, 0,false, rownum, groupLength, 0, 0, sheet,""+stt,false,workbook);
                            createCell(row, 1,false, rownum, groupLength, 0, 0, sheet,bcBean.getNgaybc(),false,workbook);
                            createCell(row, 2,false, rownum, groupLength, 0, 0, sheet,bcBean.getSobc(),false,workbook);
                            createCell(row, 3,false, 3, 4, 0, 0, sheet,bcBean.getMakh(),false,workbook);
                            createCell(row, 4,false, rownum, groupLength, 0, 0, sheet,bcBean.getTenkh(),false,workbook);
                            createCell(row, 6,false, rownum, groupLength, 0, 0, sheet,bcBean.getNdbc(),false,workbook);
                            createCell(row, 7,false, rownum, groupLength, 0, 0, sheet,bcBean.getTongtienbc(),false,workbook);
                            createCell(row, 9,false, rownum, groupLength, 0, 0, sheet,bcBean.getTienthua(),false,workbook);
                            if(blInfor.isEmpty()){
                                createCell(row, 5,false, 3, 4, 0, 0, sheet,"",false,workbook);
                                createCell(row, 8,false, 3, 4, 0, 0, sheet,"0",false,workbook);
                            }else{
                                createCell(row, 5,false, 3, 4, 0, 0, sheet,blInfor.get(0).get(0),false,workbook);
                                createCell(row, 8,false, 3, 4, 0, 0, sheet,blInfor.get(0).get(1),false,workbook);
                            }
                            
                            rownum ++;
                        }else{
                            createCell(row, 0,true, rownum, groupLength, 0, 0, sheet,""+stt,true,workbook);
                            createCell(row, 1,true, rownum, groupLength, 0, 0, sheet,bcBean.getNgaybc(),true,workbook);
                            createCell(row, 2,true, rownum, groupLength, 0, 0, sheet,bcBean.getSobc(),true,workbook);
                            createCell(row, 3,false, 3, 4, 0, 0, sheet,bcBean.getMakh(),true,workbook);
                            createCell(row, 4,true, rownum, groupLength, 0, 0, sheet,bcBean.getTenkh(),true,workbook);
                            createCell(row, 6,true, rownum, groupLength, 0, 0, sheet,bcBean.getNdbc(),true,workbook);
                            createCell(row, 7,true, rownum, groupLength, 0, 0, sheet,bcBean.getTongtienbc(),true,workbook);
                            createCell(row, 9,true, rownum, groupLength, 0, 0, sheet,bcBean.getTienthua(),true,workbook);
                            for(int k = 0; k < blInfor.size();k++){
                                int subNum = rownum +k;
                                Row rowSub = sheet.createRow(subNum);
                                createCell(rowSub, 5,false, 3, 4, 0, 0, sheet,blInfor.get(k).get(0),true,workbook);
                                createCell(rowSub,8,false, 3, 4, 0, 0, sheet,blInfor.get(k).get(1),true,workbook);
                            }
                            rownum += blInfor.size();
                        }
                        stt++;
                    }
                    Row rowFooter = sheet.createRow(rownum+1);
                    Cell cellFooter = rowFooter.createCell(4);
                    cellFooter.setCellValue("Tổng tiền:");
                    sheet.addMergedRegion(new CellRangeAddress(
                            rownum+1, //first row (0-based)
                            rownum+1, //last row  (0-based)
                            2, //first column (0-based)
                            6  //last column  (0-based)
                    ));
                    Cell tongtienBCCell= rowFooter.createCell(7);
                    tongtienBCCell.setCellValue(tongtien.get(0));
                    Cell tongtienTTCell= rowFooter.createCell(8);
                    tongtienTTCell.setCellValue(tongtien.get(1));
                    Cell tongtienThuaCell= rowFooter.createCell(9);
                    tongtienThuaCell.setCellValue(tongtien.get(2));
//                    Cell tongBSCell= rowFooter.createCell(12);
//                    tongBSCell.setCellValue(tongBS);
                    
                }
        return workbook;
    }
    
    // Create excel file in Thống kê
    // Lấy tổng số trang thống kê nhập đơn tìm kiếm
    private int getRowTkNhapdon(String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String bnbdS,String btpS,String bbdS){
        int currentpage = 0 ;
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
            whereSql += " and  tbl_khachhang.KH_Name  like '%"+btpS+"%'";
        }
        String sql = "select count(tbl_don.D_ID)"+
                            "from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                            "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                            "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                            "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                            "inner join tbl_bnbd on tbl_don.D_ID =  tbl_bnbd.KHD_ID "+whereSql
                            + " group by tbl_don.D_ID order by tbl_don.D_ID DESC ;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                currentpage = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ExportExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (currentpage/50000)+1;
    }
    // get arraylist data for excel file
    public Map<String, Object[]> getTkNhapdon(int page,String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String bnbdS,String btpS,String bbdS){
        Map<String, Object[]> dataArr = new HashMap<String, Object[]>();
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
            whereSql += " and  tbl_khachhang.KH_Name  like '%"+btpS+"%'";
        }
        String sql = "select tbl_don.D_ID as id,concat(tbl_don.D_GioNhan,\":\",tbl_don.D_PhutNhan,\" \",DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y')) as thoigian,\n" +
                            "tbl_cctt.CCTT_Des as cctt,\n" +
                            "tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,tbl_loainhan.LN_Short as loainhan \n" +
                            ",tbl_loaidon.LD_Des as loaidon,\n" +
                            "ifnull(tbl_don.D_MDO ,'') as dononline,\n" +
                            "ifnull(tbl_don.P_Pin,'') as mapin, \n" +
                            "(select group_concat(`KHD_Name` separator ' & ') \n" +
                            "as Result from tbl_bnbd where tbl_bnbd.KHD_ID = tbl_don.D_ID order by KHD_ID) as bnbd,\n" +
                            "tbl_khachhang.KH_Name  as bndbtp,\n" +
                            "(select group_concat(`BDB_Name` separator ' & ')\n" +
                            "as Result from tbl_benbaodam where tbl_benbaodam.D_ID = tbl_don.D_ID group by D_ID) as benbaodam ,\n" +
                            "(select tbl_lephi.LP_Price from tbl_lephi where tbl_lephi.LP_ID = tbl_don.LP_ID) as lephi "+
                            "from tbl_don\n" +
                            "inner join tbl_khachhang on tbl_don.KH_ID = tbl_khachhang.KH_ID \n" +
                            "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                            "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                            "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                            "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                            "inner join tbl_bnbd on tbl_don.D_ID =  tbl_bnbd.KHD_ID "+whereSql
                            + " group by tbl_don.D_ID order by tbl_don.D_ID DESC limit "+(page-1)*50000+",50000;";
        System.out.println(sql);
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            dataArr.put("1", new Object[] {"STT", "Thời điểm nhập", "Số báo có","Mã khách hàng","Tên khách hàng","Số hiệu","Nội dung báo có"
           ,"Số tiền báo có","Tổng tiền thanh toán","Tiền thừa"});
            int i = 0;
            while(rs.next()){
                String ngaynhap = rs.getString("thoigian");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, ngaynhap);
                String loaidon = rs.getString("loaidon");
                String bnbd = rs.getString("bnbd");
                String bndbtp = rs.getString("bndbtp");
                String bbd = rs.getString("benbaodam");
                String maonline = rs.getString("dononline");
                String mapin = rs.getString("mapin");
                String cctt = rs.getString("cctt");
                Double lephi = rs.getDouble("lephi");
                
                 dataArr.put(""+(i+1), new Object[] {""+i,ngaynhap, maonline,mapin,maloainhan,loaidon,lephi,cctt,bnbd,
                        bndbtp,bbd});
                    i++;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ExportExcelFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dataArr;
    }
    // create excel in thống kê nhập đơn
    public HSSFWorkbook exportTkNhapdon(String ngaybatdau,String ngayketthuc,String maonlineS,int loaidonS,int loaihinhnhanS,
            String manhanS,int loaidkS,String bnbdS,String btpS,String bbdS){
        int sheetCheck = getRowTkNhapdon( ngaybatdau, ngayketthuc, maonlineS, loaidonS, loaihinhnhanS, manhanS, loaidkS, bnbdS, btpS, bbdS);
   //         ArrayList<Integer> tongtien = getTkNhapdon(loaidkS, ngaybatdau, ngayketthuc, maonlineS, loaidonS, loaihinhnhanS, manhanS, loaidkS, bnbdS, btpS, bbdS);
            HSSFWorkbook workbook = new HSSFWorkbook();
                for(int i=1;i<= sheetCheck;i++){
                    HSSFSheet sheet = workbook.createSheet("Trang "+i+"-TK nhập đơn");
                    Map<String, Object[]> data = getTkNhapdon(i, ngaybatdau, ngayketthuc, maonlineS, loaidonS, loaihinhnhanS, manhanS, loaidkS, bnbdS, btpS, bbdS);

                    

                    Set<String> keyset = data.keySet();
                    Row rowHeader = sheet.createRow(0);
                    createCell(rowHeader, 0,true, 0, 0, 0, 30, sheet,"TÌNH HÌNH GIẢI QUYẾT ĐƠN CỦA TRUNG TÂM",false,workbook);

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
                    createCell(rowTD, 0,true, 3, 4, 0, 0, sheet,"STT",true,workbook);
                    createCell(rowTD, 1,true, 3, 4, 1, 1, sheet,"Thời điểm nhập",true,workbook);
                    createCell(rowTD, 2,true, 3, 3, 2, 3, sheet,"Số Đơn Do Online Cấp",true,workbook);
                     createCell(rowTD, 4,true, 3, 4, 4, 4, sheet,"Loại hình nhận",true,workbook);
                     createCell(rowTD, 5,true, 3, 4, 5, 5, sheet,"Loại Đơn",true,workbook);
                     createCell(rowTD, 6,true, 3, 4, 6, 6, sheet,"Lệ Phi",true,workbook);
                     createCell(rowTD,7,true, 3, 4, 7, 7, sheet,"Cung cấp thông tin",true,workbook);
                     createCell(rowTD,8,true, 3, 4, 8, 8, sheet,"Bên Nhận Bảo Đảm",true,workbook);
                     createCell(rowTD,9,true, 3, 4, 9, 9, sheet,"Bên Nhận Bảo Đảm(TT phí)",true,workbook);
                     createCell(rowTD,10,true, 3, 4, 10, 10, sheet,"Bên Bảo Đảm",true,workbook);
                    Row rowO = sheet.createRow(4);
                    createCell(rowO, 2,false, 3, 3, 2, 3, sheet,"Số đơn Online",true,workbook);
                    createCell(rowO, 3,false, 3, 3, 2, 3, sheet,"Mã pin",true,workbook);
                    
  
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
    
    
    private void createCell(Row row,int cellposition,boolean merger,int frow,int srow,int fcol,int scol,HSSFSheet sheet,Object cellValue,boolean setStyle, HSSFWorkbook workbook){
        Cell cell = row.createCell(cellposition);
        if(cellValue instanceof Date) 
                cell.setCellValue((Date)cellValue);
        else if(cellValue instanceof Boolean)
                cell.setCellValue((Boolean)cellValue);
        else if(cellValue instanceof String)
                cell.setCellValue((String)cellValue);
        else if(cellValue instanceof Double)
                cell.setCellValue((Double)cellValue);
       // cell.setCellValue(cellValue);
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
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
    }
}
