/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.ttdk.bean.NhapDon;
import com.ttdk.bean.ThongBaoPhiBean;
import com.ttdk.bean.ThongKe;
import com.ttdk.bean.ThuPhiBean;
import com.ttdk.bean.Thuphi;
import com.ttdk.connect.DBConnect;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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
public class ThongBaoPhiPDF {
    
    public static final String FONT = "C:\\Windows\\Fonts/timesbd.ttf";
    public static final String FONT2 = "C:\\Windows\\Fonts/times.ttf";
    public static Table table;
     private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    
    public void createParagraph(Document document,PdfFont font,String text,String title){
        Text kgText = new Text(title).setFont(font);
        Paragraph paNNH = new Paragraph().add(kgText).add("\u00a0"+text);
        paNNH.setFont(font);
        document.add(paNNH);
    }
    
    public Cell getCell(String text, TextAlignment alignment,PdfFont font) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        cell.setFont(font);
        return cell;
    }
    
    // Tạo file pdf
    public void createPDF(OutputStream outputStream,int khid,String donids,String ngaygui,String ngayhethan){
         PdfWriter writer;
        try {
            writer = new PdfWriter(outputStream);
            PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
            PdfFont font2 = PdfFontFactory.createFont(FONT2, PdfEncodings.IDENTITY_H);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
             Table table = new Table(2);
            table.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0 CỤC ĐĂNG KÝ QUỐC GIA", TextAlignment.LEFT,font));
            table.addCell(getCell("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", TextAlignment.CENTER,font));
            table.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0 \u00a0\u00a0 GIAO DỊCH BẢO ĐẢM", TextAlignment.LEFT,font));
            table.addCell(getCell("Độc lập - Tự do - Hạnh phúc                   ", TextAlignment.CENTER,font));
            table.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0 \u00a0\u00a0TRUNG TÂM ĐĂNG KÝ", TextAlignment.LEFT,font));
            table.addCell(getCell("Đà Nẵng, Ngày "+ngaygui.split("-")[0]+" tháng "+ngaygui.split("-")[1]+" năm "+ngaygui.split("-")[2]+" ", TextAlignment.CENTER,font2));
            table.addCell(getCell("GIAO DỊCH TÀI SẢN TẠI ĐÀ NẴNG", TextAlignment.LEFT,font));
            table.addCell(getCell(" ", TextAlignment.CENTER,font2));
            int cvNumber = new Thuphi().getSoCV(khid);
            table.addCell(getCell("\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0Số:"+cvNumber+"/TT3", TextAlignment.LEFT,font));
            table.addCell(getCell(" ", TextAlignment.CENTER,font2));
            document.add(table);
            Paragraph paragraphTB = new Paragraph("\u00a0\u00a0\u00a0\u00a0THÔNG BÁO THANH TOÁN LỆ PHÍ ĐĂNG KÝ,PHÍ CCTT VỀ GIAO DỊCH BẢO ĐẢM");
            paragraphTB.setFont(font);
            document.add(paragraphTB);
            ArrayList bbd = loadBBD(donids);
            ArrayList<String> khachhang = loadBNBD(khid);
            createParagraph(document, font, khachhang.get(0),"Kính gửi: ");
            createParagraph(document, font, khachhang.get(1),"Địa chỉ :  ");
            createParagraph(document, font, khachhang.get(2),"Mã khách hàng : ");
            createParagraph(document, font, "Diễn giải chi tiết đơn ngân hàng chưa thanh toán đến thời điểm cuối ngày "+ngaygui,"");
            table = new Table(5);
            table.addHeaderCell("STT").setFont(font);
            table.addHeaderCell("Ngày").setFont(font);
            table.addHeaderCell("Số đơn").setFont(font);
            table.addHeaderCell("Bên bảo đảm").setFont(font);
            table.addHeaderCell("Số tiền").setFont(font);
            int j =4;
            Long total = 0l;
            for (int i = 0; i < bbd.size(); i++) {
          //      bbd.get(i).toString()
                table.addCell(bbd.get(i).toString()).setFont(font2);
                if( i==j){
                    total += Long.parseLong(bbd.get(i).toString());
                    j= j+5;
                }
            }
            Cell cellTT = new Cell(1,4).add("Tổng tiền thanh toán: ");
            Cell cellTTValue = new Cell().add(String.valueOf(total));
            table.addCell(cellTT).setFont(font);
            table.addCell(cellTTValue).setFont(font);
            String totalWord = new ChangeNumberToText().convert(total);
            Cell cellSTBC = new Cell(1,5).add("Số tiền bằng chữ: ("+totalWord+" )");
            table.addCell(cellSTBC);
            document.add(table);
           // Phrase phrase = new Phrase();
           Paragraph pLast = new Paragraph();
       //     String inforText = "*Đề nghị Quý ngân hàng thanh toán trước ngày :";
              Text t1 = new Text("*Đề nghị Quý ngân hàng thanh toán trước ngày : \n").setFont(font);
              Text t2 = new Text("- Quá thời hạn trên, Trung Tâm sẽ từ chối đơn yêu cầu đăng ký GDBD của Quý ngân hàng theo quy định.\n").setFont(font2);
              Text t3 = new Text("Thanh toán bằng chuyển khoản qua kho bạc: \n").setFont(font2);
              Text t4 = new Text("- Tên đơn vị nhận tiền; Trung Tâm Đăng Ký Giao Dịch Tài Sản tại Đà Nẵng.\n").setFont(font2);
              Text t5 = new Text("- Tài khoản: 3511.0.1043491.00000 tại kho bạc nhà nước Đà Nẵng \n").setFont(font2);
              Text t6 = new Text("hoặc nộp tiền mặt tại trung tâm: 109 Hoàng Sỹ Khải, phường An Hải Bắc, Quận Sơn Trà - TP Đà Nẵng.\n").setFont(font2);
              Text t7 = new Text("- Sau khi nhận được tiền phí, lệ phí, Trung tâm sẽ gửi Biên lai thu phí, lệ phí tới Quý ngân hàng.\n").setFont(font2);
              Text t8 = new Text("- Khi chuyển khoản, đề nghị Quý Ngân Hàng  " ).setFont(font);
              Text t9 = new Text("THANH TOÁN 01 LẦN TOÀN BỘ SỐ TIỀN PHÍ THEO THÔNG BÁO \n").setFont(font2);
              Text t10 = new Text("Và ghi tại phần Nội dung: Thanh toán theo THÔNG BÁO SỐ.....").setFont(font);
              Text t11 = new Text("(nằm ở góc trái )").setFont(font2);
              Text t12 = new Text("HOẶC SỐ ĐƠN").setFont(font);
              Text t13 = new Text("  phải nộp phí \n").setFont(font2);
              Text t14 = new Text("Và ở dòng ").setFont(font2);
              Text t15 = new Text("NGƯỜI TRẢ TIỀN:").setFont(font);
              Text t16 = new Text("Ghi rõ tên Ngân hàng xác định đúng của Ngân hàng hay phòng giao dịch nào.\n").setFont(font2);
              Text t17 = new Text("*Nếu có vướng mắc, xin liên hệ - ĐT:(0236)3933111 - bấm 104 hoặc 0236.3503880\n").setFont(font2);
              pLast.add(t1);pLast.add(t2);pLast.add(t3);pLast.add(t4);
              pLast.add(t5);pLast.add(t6);pLast.add(t7);pLast.add(t8);
              pLast.add(t9);pLast.add(t10);pLast.add(t11);pLast.add(t12);
              pLast.add(t13);pLast.add(t14);pLast.add(t15);pLast.add(t16);
              pLast.add(t17);
              
            document.add(pLast);
            Paragraph pImg = new Paragraph();
            Image img = new Image(ImageDataFactory.create(getClass().getResource("condau_pgd.png")));
            img.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            document.add(img);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThongBaoPhiPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThongBaoPhiPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Load BBD
    public ArrayList loadBBD(String donids){
        ArrayList data = new ArrayList<>();
        String sql = "select tbl_don.D_ID as donid,DATE_FORMAT(tbl_don.D_Date,'%d-%m-%Y') as ngaynhap, "
                + "tbl_don.DK_ID as dkid,tbl_loaidk.DK_Short as loaidk,tbl_don.D_manhan as manhan ,\n" +
                            "tbl_loainhan.LN_Short as loainhan,"
                + "tbl_don.D_MDO as dononline,"
                + "group_concat(tbl_benbaodam.BDB_Name  SEPARATOR ',') as benbaodam,\n" +
                    "tbl_lephi.LP_Price  as tongtien\n" +
                    "from tbl_don \n"
                    + "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID \n" +
                    "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID\n" +
                    "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID\n" +
                    "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID "
                + "inner join tbl_lephi on tbl_lephi.LP_ID = tbl_don.LP_ID \n" +
            "where EXISTS(SELECT 1 FROM tbl_dondatp WHERE tbl_dondatp.D_ID = tbl_don.D_ID LIMIT 1) = 0 \n" +
            " and tbl_don.D_ID in( "+donids+") group by tbl_don.D_ID ;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            int i =1;
            while(rs.next()){
                String ngaynhap = rs.getString("ngaynhap");
                String loaidk = rs.getString("loaidk");
                String manhan = rs.getString("manhan");
                String loainhan = rs.getString("loainhan");
                String maloainhan = new NhapDon().returnManhan(loaidk, manhan, loainhan, ngaynhap);
                if(manhan == null || manhan.equals("")){
                    maloainhan = rs.getString("dononline");
                }
                String bbd = rs.getString("benbaodam");
                String tongtien = rs.getString("tongtien");
                data.add(i); data.add(ngaynhap); data.add(maloainhan);
                data.add(bbd);data.add(tongtien);
               i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Thuphi.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    // Load BNBD
    public ArrayList<String> loadBNBD(int khid){
        ArrayList<String> data = new ArrayList<>();
        String sql = "select tbl_khachhang.KH_ID as id, tbl_khachhang.KH_Account as account,tbl_khachhang.KH_Name as name"
                + ",tbl_khachhang.KH_Address as address from tbl_khachhang" +
                        "where tbl_khachhang.KH_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, khid);
            rs = pstm.executeQuery();
            while(rs.next()){
                data.add(rs.getString("name"));
                data.add(rs.getString("address"));
                data.add(rs.getString("account"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongBaoPhiPDF.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
    // get Email
    public String loadEmail(int khid){
        String email = "";
        String sql = "select KH_Email as email from tbl_khachhang "+
                        "where tbl_khachhang.KH_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, khid);
            rs = pstm.executeQuery();
            while(rs.next()){
               email = rs.getString("email");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongBaoPhiPDF.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return email;
    }
    

    // Taoj file PDF gửi thông báo phí
    public void createPDF(OutputStream outputStream,int khid,ArrayList<String> tbpInfor,String donids){
         PdfWriter writer;
        try {
            writer = new PdfWriter(outputStream);
            PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
            PdfFont font2 = PdfFontFactory.createFont(FONT2, PdfEncodings.IDENTITY_H);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
             Table table = new Table(2);
             //ArrayList<String> tbpInfor = new Thuphi().geTBP(tbpid);
             //ArrayList<Integer> donsTbp = new Thuphi().getDonTBPByID(tbpid);
           //  String donids = donsTbp.toString().replaceAll("\\[", "").replaceAll("\\]", "");
             String cvNumber =  tbpInfor.get(0);
             String ngaygui = tbpInfor.get(1);
             String ngayhh = tbpInfor.get(2);
            table.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0 CỤC ĐĂNG KÝ QUỐC GIA", TextAlignment.LEFT,font));
            table.addCell(getCell("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", TextAlignment.CENTER,font));
            table.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0 \u00a0\u00a0 GIAO DỊCH BẢO ĐẢM", TextAlignment.LEFT,font));
            table.addCell(getCell("Độc lập - Tự do - Hạnh phúc                   ", TextAlignment.CENTER,font));
            table.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0 \u00a0\u00a0TRUNG TÂM ĐĂNG KÝ", TextAlignment.LEFT,font));
            table.addCell(getCell("Đà Nẵng, Ngày "+ngaygui.split("-")[0]+" tháng "+ngaygui.split("-")[1]+" năm "+ngaygui.split("-")[2]+" ", TextAlignment.CENTER,font2));
            table.addCell(getCell("GIAO DỊCH TÀI SẢN TẠI ĐÀ NẴNG", TextAlignment.LEFT,font));
            table.addCell(getCell(" ", TextAlignment.CENTER,font2));
            //String cvNumber = new Thuphi().getSoTBP(tbpid);
            table.addCell(getCell("\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0Số:"+cvNumber+"/TT3", TextAlignment.LEFT,font));
            table.addCell(getCell(" ", TextAlignment.CENTER,font2));
            document.add(table);
            Paragraph paragraphTB = new Paragraph("\u00a0\u00a0\u00a0\u00a0THÔNG BÁO THANH TOÁN LỆ PHÍ ĐĂNG KÝ,PHÍ CCTT VỀ GIAO DỊCH BẢO ĐẢM");
            paragraphTB.setFont(font);
            document.add(paragraphTB);
            ArrayList bbd = loadBBD(donids);
            ArrayList<String> khachhang = loadBNBD(khid);
            createParagraph(document, font, khachhang.get(0),"Kính gửi: ");
            createParagraph(document, font, khachhang.get(1),"Địa chỉ :  ");
            createParagraph(document, font, khachhang.get(2),"Mã khách hàng : ");
            createParagraph(document, font, "Diễn giải chi tiết đơn ngân hàng chưa thanh toán đến thời điểm cuối ngày "+ngaygui,"");
            table = new Table(5);
            table.addHeaderCell("STT").setFont(font);
            table.addHeaderCell("Ngày").setFont(font);
            table.addHeaderCell("Số đơn").setFont(font);
            table.addHeaderCell("Bên bảo đảm").setFont(font);
            table.addHeaderCell("Số tiền").setFont(font);
            int j =4;
            Long total = 0l;
            for (int i = 0; i < bbd.size(); i++) {
          //      bbd.get(i).toString()
                table.addCell(bbd.get(i).toString()).setFont(font2);
                if( i==j){
                    total += Long.parseLong(bbd.get(i).toString());
                    j= j+5;
                }
            }
            Cell cellTT = new Cell(1,4).add("Tổng tiền thanh toán: ");
            Cell cellTTValue = new Cell().add(String.valueOf(total));
            table.addCell(cellTT).setFont(font);
            table.addCell(cellTTValue).setFont(font);
            String totalWord = new ChangeNumberToText().convert(total);
            Cell cellSTBC = new Cell(1,5).add("Số tiền bằng chữ: ("+totalWord+" )");
            table.addCell(cellSTBC);
            document.add(table);
           // Phrase phrase = new Phrase();
           Paragraph pLast = new Paragraph();
       //     String inforText = "*Đề nghị Quý ngân hàng thanh toán trước ngày :";
              Text t1 = new Text("*Đề nghị Quý ngân hàng thanh toán trước ngày : "+ngayhh+" \n").setFont(font);
              Text t2 = new Text("- Quá thời hạn trên, Trung Tâm sẽ từ chối đơn yêu cầu đăng ký GDBD của Quý ngân hàng theo quy định.\n").setFont(font2);
              Text t3 = new Text("Thanh toán bằng chuyển khoản qua kho bạc: \n").setFont(font2);
              Text t4 = new Text("- Tên đơn vị nhận tiền; Trung Tâm Đăng Ký Giao Dịch Tài Sản tại Đà Nẵng.\n").setFont(font2);
              Text t5 = new Text("- Tài khoản: 3511.0.1043491.00000 tại kho bạc nhà nước Đà Nẵng \n").setFont(font2);
              Text t6 = new Text("hoặc nộp tiền mặt tại trung tâm: 109 Hoàng Sỹ Khải, phường An Hải Bắc, Quận Sơn Trà - TP Đà Nẵng.\n").setFont(font2);
              Text t7 = new Text("- Sau khi nhận được tiền phí, lệ phí, Trung tâm sẽ gửi Biên lai thu phí, lệ phí tới Quý ngân hàng.\n").setFont(font2);
              Text t8 = new Text("- Khi chuyển khoản, đề nghị Quý Ngân Hàng  " ).setFont(font);
              Text t9 = new Text("THANH TOÁN 01 LẦN TOÀN BỘ SỐ TIỀN PHÍ THEO THÔNG BÁO \n").setFont(font2);
              Text t10 = new Text("Và ghi tại phần Nội dung: Thanh toán theo THÔNG BÁO SỐ.....").setFont(font);
              Text t11 = new Text("(nằm ở góc trái )").setFont(font2);
              Text t12 = new Text("HOẶC SỐ ĐƠN").setFont(font);
              Text t13 = new Text("  phải nộp phí \n").setFont(font2);
              Text t14 = new Text("Và ở dòng ").setFont(font2);
              Text t15 = new Text("NGƯỜI TRẢ TIỀN:").setFont(font);
              Text t16 = new Text("Ghi rõ tên Ngân hàng xác định đúng của Ngân hàng hay phòng giao dịch nào.\n").setFont(font2);
              Text t17 = new Text("*Nếu có vướng mắc, xin liên hệ - ĐT:(0236)3933111 - bấm 104 hoặc 0236.3503880\n").setFont(font2);
              pLast.add(t1);pLast.add(t2);pLast.add(t3);pLast.add(t4);
              pLast.add(t5);pLast.add(t6);pLast.add(t7);pLast.add(t8);
              pLast.add(t9);pLast.add(t10);pLast.add(t11);pLast.add(t12);
              pLast.add(t13);pLast.add(t14);pLast.add(t15);pLast.add(t16);
              pLast.add(t17);
              
            document.add(pLast);
            Paragraph pImg = new Paragraph();
            Image img = new Image(ImageDataFactory.create(getClass().getResource("condau_pgd.png")));
            img.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            document.add(img);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThongBaoPhiPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThongBaoPhiPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // tạo file thông báo phí pdf
    public void createPDFTBP(OutputStream outputStream,ArrayList<ThongBaoPhiBean> tbpids){
         PdfWriter writer;
        try {
            writer = new PdfWriter(outputStream);
            PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
            PdfFont font2 = PdfFontFactory.createFont(FONT2, PdfEncodings.IDENTITY_H);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            int stt=1;
            for(ThongBaoPhiBean tbpid : tbpids){
                String ngaygui = tbpid.getNgaybp();
                Table table = new Table(2);
                table.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0 CỤC ĐĂNG KÝ QUỐC GIA", TextAlignment.LEFT,font));
                table.addCell(getCell("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", TextAlignment.CENTER,font));
                table.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0 \u00a0\u00a0 GIAO DỊCH BẢO ĐẢM", TextAlignment.LEFT,font));
                table.addCell(getCell("Độc lập - Tự do - Hạnh phúc                   ", TextAlignment.CENTER,font));
                table.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0 \u00a0\u00a0TRUNG TÂM ĐĂNG KÝ", TextAlignment.LEFT,font));
                table.addCell(getCell("Đà Nẵng, Ngày "+ngaygui.split("-")[0]+" tháng "+ngaygui.split("-")[1]+" năm "+ngaygui.split("-")[2]+" ", TextAlignment.CENTER,font2));
                table.addCell(getCell("GIAO DỊCH TÀI SẢN TẠI ĐÀ NẴNG", TextAlignment.LEFT,font));
                table.addCell(getCell(" ", TextAlignment.CENTER,font2));
              //  int cvNumber = new Thuphi().getSoCV(khid);
                table.addCell(getCell("\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0Số:"+tbpid.getSotbp()+"/TT3", TextAlignment.LEFT,font));
                table.addCell(getCell(" ", TextAlignment.CENTER,font2));
                document.add(table);
                Paragraph paragraphTB = new Paragraph("\u00a0\u00a0\u00a0\u00a0THÔNG BÁO THANH TOÁN LỆ PHÍ ĐĂNG KÝ,PHÍ CCTT VỀ GIAO DỊCH BẢO ĐẢM");
                paragraphTB.setFont(font);
                document.add(paragraphTB);
                ArrayList bbd = loadBBD(tbpid.getDonids());
                ArrayList<String> khachhang = new Thuphi().printGetBNBD(tbpid.getDonids());
//                createParagraph(document, font, khachhang.get(0),"Kính gửi: ");
//                createParagraph(document, font, khachhang.get(1),"Địa chỉ :  ");
//                createParagraph(document, font, khachhang.get(2),"Mã khách hàng : ");
                 createParagraph(document, font, khachhang.get(0),"Kính gửi: ");
                createParagraph(document, font, khachhang.get(1),"Địa chỉ :  ");
                createParagraph(document, font, khachhang.get(2),"Mã khách hàng : ");
                createParagraph(document, font, "Diễn giải chi tiết đơn ngân hàng chưa thanh toán đến thời điểm cuối ngày "+ngaygui,"");
                table = new Table(5);
                table.addHeaderCell("STT").setFont(font);
                table.addHeaderCell("Ngày").setFont(font);
                table.addHeaderCell("Số đơn").setFont(font);
                table.addHeaderCell("Bên bảo đảm").setFont(font);
                table.addHeaderCell("Số tiền").setFont(font);
                int j =4;
                Long total = 0l;
                for (int i = 0; i < bbd.size(); i++) {
              //      bbd.get(i).toString()
                    table.addCell(bbd.get(i).toString()).setFont(font2);
                    if( i==j){
                        total += Long.parseLong(bbd.get(i).toString());
                        j= j+5;
                    }
                }
                Cell cellTT = new Cell(1,4).add("Tổng tiền thanh toán: ");
                Cell cellTTValue = new Cell().add(String.valueOf(total));
                table.addCell(cellTT).setFont(font);
                table.addCell(cellTTValue).setFont(font);
                String totalWord = new ChangeNumberToText().convert(total);
                Cell cellSTBC = new Cell(1,5).add("Số tiền bằng chữ: ("+totalWord+" )");
                table.addCell(cellSTBC);
                document.add(table);
               // Phrase phrase = new Phrase();
               Paragraph pLast = new Paragraph();
        //     String inforText = "*Đề nghị Quý ngân hàng thanh toán trước ngày :";
               Text t1 = new Text("*Đề nghị Quý ngân hàng thanh toán trước ngày : "+tbpid.getNgayhh()+"\n").setFont(font);
               Text t2 = new Text("- Quá thời hạn trên, Trung Tâm sẽ từ chối đơn yêu cầu đăng ký GDBD của Quý ngân hàng theo quy định.\n").setFont(font2);
               Text t3 = new Text("Thanh toán bằng chuyển khoản qua kho bạc: \n").setFont(font2);
               Text t4 = new Text("- Tên đơn vị nhận tiền; Trung Tâm Đăng Ký Giao Dịch Tài Sản tại Đà Nẵng.\n").setFont(font2);
               Text t5 = new Text("- Tài khoản: "+tbpid.getTaikhoan()+"  \n").setFont(font2);
               Text t6 = new Text("hoặc nộp tiền mặt tại trung tâm: 109 Hoàng Sỹ Khải, phường An Hải Bắc, Quận Sơn Trà - TP Đà Nẵng.\n").setFont(font2);
               Text t7 = new Text("- Sau khi nhận được tiền phí, lệ phí, Trung tâm sẽ gửi Biên lai thu phí, lệ phí tới Quý ngân hàng.\n").setFont(font2);
               Text t8 = new Text("- Khi chuyển khoản, đề nghị Quý Ngân Hàng  " ).setFont(font);
               Text t9 = new Text("THANH TOÁN 01 LẦN TOÀN BỘ SỐ TIỀN PHÍ THEO THÔNG BÁO \n").setFont(font2);
               Text t10 = new Text("Và ghi tại phần Nội dung: Thanh toán theo THÔNG BÁO SỐ.....").setFont(font);
               Text t11 = new Text("(nằm ở góc trái )").setFont(font2);
               Text t12 = new Text("HOẶC SỐ ĐƠN").setFont(font);
               Text t13 = new Text("  phải nộp phí \n").setFont(font2);
               Text t14 = new Text("Và ở dòng ").setFont(font2);
               Text t15 = new Text("NGƯỜI TRẢ TIỀN:").setFont(font);
               Text t16 = new Text("Ghi rõ tên Ngân hàng xác định đúng của Ngân hàng hay phòng giao dịch nào.\n").setFont(font2);
               Text t17 = new Text("*Nếu có vướng mắc, xin liên hệ - ĐT:(0236)3933111 - bấm 104 hoặc 0236.3503880\n").setFont(font2);
               pLast.add(t1);pLast.add(t2);pLast.add(t3);pLast.add(t4);
               pLast.add(t5);pLast.add(t6);pLast.add(t7);pLast.add(t8);
               pLast.add(t9);pLast.add(t10);pLast.add(t11);pLast.add(t12);
               pLast.add(t13);pLast.add(t14);pLast.add(t15);pLast.add(t16);
               pLast.add(t17);
               document.add(pLast);
              Table tableSign = new Table(3);
                tableSign.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0", TextAlignment.LEFT,font));
                tableSign.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0\u00a0", TextAlignment.CENTER,font));
                tableSign.addCell(getCell("\u00a0 \u00a0\u00a0 \u00a0 \u00a0\u00a0KT GIÁM ĐỐC \n \u00a0\u00a0 \u00a0 \u00a0\u00a0 PHÓ GIÁM ĐỐC"
                        + " \n\n\n\n LƯƠNG HOÀNG PHONG", TextAlignment.LEFT,font));
                document.add(tableSign);
                if(stt < tbpids.size()){
                    document.add(new AreaBreak());
                }
                
            }
            
            
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThongBaoPhiPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThongBaoPhiPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
