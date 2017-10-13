/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Jsoup;
/**
 *
 * @author ntdung
 */
public class CreatePhuLuc05 {
    
        public CreatePhuLuc05(){}

        // replace text
        public void replaceText(String textReplace,String value,XWPFDocument doc){
            // XWPFDocument doc = new XWPFDocument(OPCPackage.open(filedoc));
            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains(textReplace)) {
                            text = text.replace(textReplace, value);
                            r.setText(text, 0);
                        }
                    }
                }
            }
            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                if (text.contains(textReplace)) {
                                    text = text.replace(textReplace, value);
                                    r.setText(text,0);
                                }
                            }
                        }
                    }
                }
            }
        }

        public void addImageToWord(XWPFDocument doc,String imageName,String fileName) throws FileNotFoundException, IOException, InvalidFormatException{
            XWPFParagraph p = doc.createParagraph();
            XWPFRun run = p.createRun();
             try (FileInputStream is = new FileInputStream(imageName)) {
                 run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imageName, Units.toEMU(20),Units.toEMU(80));
             }
            FileOutputStream fos = new FileOutputStream(fileName+".docx");
            doc.write(fos);
            fos.close(); 
        }
        public static BufferedImage rotate90ToLeft( BufferedImage inputImage ){
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();
            BufferedImage returnImage = new BufferedImage( height, width , inputImage.getType()  );
            for( int x = 0; x < width; x++ ) {
                                for( int y = 0; y < height; y++ ) {
                                        returnImage.setRGB(y, width - x - 1, inputImage.getRGB( x, y  )  );
                                }
                        }
            return returnImage;

        }
       
       public void createBarCode(String code) throws BarcodeException, OutputException, IOException{
           Barcode barcode = BarcodeFactory.createCode128C(code);
            barcode.setBarHeight(30);
            barcode.setBarWidth(2);
            File imgFile = new File(code+".jpg");
            //Write the bar code to PNG file
            BarcodeImageHandler.savePNG(barcode, imgFile);
           // savePhuluc5("testsize1.jpg");
            BufferedImage buffer = ImageIO.read(imgFile);

            BufferedImage newImg = rotate90ToLeft(buffer);
            File outputfile = new File(code+".jpg");
            ImageIO.write(newImg, "jpg", outputfile);
       }
       public static ArrayList<NganHangBean> shortName(){
        ArrayList<NganHangBean> nhArr = new ArrayList<>();
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build("data/banks.xml");
            Element classElement = document.getRootElement();
            List<Element> bankList = classElement.getChildren();
            for (int i = 0; i < bankList.size(); i++) {
                Element bankEle = bankList.get(i);
                NganHangBean nhb = new NganHangBean();
                nhb.setNHName(bankEle.getChildText("name"));
                nhb.setNHSName(bankEle.getChildText("sname"));
                nhArr.add(nhb);
            }
            
        } catch (JDOMException ex) {
            Logger.getLogger(CreatePhuLuc05.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreatePhuLuc05.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nhArr;
    }
    
    public static String tenLH(String name){
        String s_name = "";
        name = name.replaceAll("\n", "");
        name = name.replaceAll("-", "");
        
        if(name.toLowerCase().contains("chi nhánh")){
            name = name.substring(0,name.toLowerCase().indexOf("chi nhánh")) + "CN" + name.substring(name.toLowerCase().indexOf("chi nhánh")+"chi nhánh".length());
        }
        if(name.toLowerCase().contains("phòng giao dịch")){
            name = name.substring(0,name.toLowerCase().indexOf("phòng giao dịch")) + "PGD" + name.substring(name.toLowerCase().indexOf("phòng giao dịch")+"phòng giao dịch".length());
        }
        if(name.toLowerCase().contains("thương mại cổ phần") ){
            name = name.substring(0,name.toLowerCase().indexOf("thương mại cổ phần")) + "TMCP" + name.substring(name.toLowerCase().indexOf("thương mại cổ phần")+"thương mại cổ phần".length());
        }
        if(name.toLowerCase().contains("một thành viên")){
            name = name.substring(0,name.toLowerCase().indexOf("một thành viên")) + "" + name.substring(name.toLowerCase().indexOf("một thành viên")+"một thành viên".length());
        }else if(name.toLowerCase().contains("mtv")){
           name = name.substring(0,name.toLowerCase().indexOf("mtv")) + "" + name.substring(name.toLowerCase().indexOf("mtv")+"mtv".length());
        }
        if(name.contains("tnhh")){
            name = name.substring(0,name.toLowerCase().indexOf("tnhh")) + "" + name.substring(name.toLowerCase().indexOf("tnhh")+"tnhh".length());
        }
        ArrayList<NganHangBean> nhArr = shortName();
        String subname = "";
        for (NganHangBean nhArr1 : nhArr) {
            String NHname = nhArr1.getNHName();
            // System.out.println("NH NAME = "+NHname );
            String sNHname = nhArr1.getNHSName();
            String NH = name.replaceAll(",", "");
            if(NH.contains("CN")){
                NH = NH.substring( NH.indexOf("CN"));
                if(NH.toLowerCase().contains("ngân hàng")){
                    NH = NH.substring(NH.toLowerCase().indexOf("ngân hàng")+"ngân hàng ".length());
                }
                if(NH.toLowerCase().contains("tmcp")){
                    NH = NH.substring(NH.toLowerCase().indexOf("tmcp ")+"tmcp ".length());
                }
                if(name.toLowerCase().contains("pgd")){
                    NH = NH.substring(0,NH.toLowerCase().indexOf("pgd"));
                }
            }
            if(NH.toLowerCase().trim().equals(NHname.toLowerCase())){
              //  s_name = sNHname+""+subname; 
              s_name = name.substring(0,name.toLowerCase().indexOf(NH.toLowerCase().trim())) + sNHname + name.substring(name.toLowerCase().indexOf(NH.toLowerCase().trim())+NH.toLowerCase().trim().length());
              if(s_name.toLowerCase().contains("ngân hàng")){
                s_name  = s_name.substring(0,s_name.toLowerCase().indexOf("ngân hàng")) + "" + s_name.substring(name.toLowerCase().indexOf("ngân hàng")+"ngân hàng ".length());
              }
            }
            
        }
        return s_name;
    }
       public XWPFDocument createWord(int type,String sodontt,String kieuphuongtien,String bbd,int loaidon,
               String tenkhachhang,String thoidiemdk,String sodonlandau,String sodononline,String inforPT,int loaiphuongtien,int csgt,int loaicsgt) throws InvalidFormatException, IOException{
           String filedoc = "";
           switch(type){
               case 1: filedoc = "../phuluc/phuluc05.docx";
                   break;
               case 2: filedoc = "../phuluc/romoc.docx";
                   break;
               case 3: filedoc = "../phuluc/tauca.docx";
                   break;
               default:
                   break;
           }
           XWPFDocument doc = new XWPFDocument(OPCPackage.open(filedoc));
           Calendar cal = Calendar.getInstance();
           int day = cal.get(Calendar.DAY_OF_MONTH);
           replaceText("xx1", String.valueOf(day), doc);
           int month = cal.get(Calendar.MONTH)+1;
           replaceText("xx2", String.valueOf(month), doc);
           int year = cal.get(Calendar.YEAR);
           replaceText("2xxx", String.valueOf(year), doc);
           
           replaceText("sodontt", sodontt, doc);
           replaceText("TypeofCar", kieuphuongtien, doc);
           replaceText("benbd", bbd, doc);
           replaceText("tenkhachhang", tenkhachhang, doc);
           replaceText("thoigiandangky", thoidiemdk, doc);
           replaceText("sodondklandau", sodonlandau, doc);
           String shortname = tenLH(tenkhachhang);
           replaceText("tenkhachhang2 ", shortname, doc);
           replaceText("madononline ", sodontt, doc);
           // loaidon danh cho phu lucj : 1.đăng ký, 2. đk thay đổi , 3.thay đổi rứt bớt, 4.xóa đăng ký
           String[] loaidonValue = {"","",""};
           switch(loaidon){
               case 1 : loaidonValue[0] = "Thế chấp";loaidonValue[1] = "Thế chấp";loaidonValue[2] = "Đăng ký lần đầu";
                   break;
               case 2: loaidonValue[0] = "Thế chấp";loaidonValue[1] = "Thế chấp";loaidonValue[2] = "Đăng ký thay đổi";
                   break;
               case 3:  loaidonValue[0] = "Xóa thế chấp";loaidonValue[1] = "Xóa thế chấp";loaidonValue[2] = "Rút bớt tài sản";
                   break;
               case 4:  loaidonValue[0] = "Xóa thế chấp";loaidonValue[1] = "Xóa thế chấp";loaidonValue[2] = "Xóa đăng ký";
                   break;
               default:
                   break;
           }
           replaceText("loaidon1", loaidonValue[0], doc);
           replaceText("Loaidon2", loaidonValue[1], doc);
           replaceText("loaidon3 ", loaidonValue[2], doc);
           if(type ==1){
               String[] phuongtien = inforPT.split("_");
               replaceText("SoKhung", loaidonValue[0], doc);
               replaceText("Somay", loaidonValue[0], doc);
               replaceText("Bienso", loaidonValue[0], doc);
           }else if(type ==2){
               String[] romoc = inforPT.split("_");
               replaceText("SoKhung", loaidonValue[0], doc);
               replaceText("Bienso", loaidonValue[0], doc);
           }else if(type ==3){
               replaceText("infortauca", loaidonValue[2], doc);
           }
           
           return doc;
           
       }
       
       public void getThongTinXe() throws IOException{
           String url ="https://demodktructuyen.moj.gov.vn/dtn_str/search/public/";
           org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
           org.jsoup.nodes.Element content = doc.getElementById("registration_number");
           content.val("2013188077");
           
       }
}
