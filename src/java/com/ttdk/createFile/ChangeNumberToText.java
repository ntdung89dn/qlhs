/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author ntdung
 */
public class ChangeNumberToText {
    private static final String[] tensNames = {
        "",
        " mười",
        " hai mươi",
        " ba mươi",
        " bốn mươi",
        " năm mươi",
        " sáu mươi",
        " bảy mươi",
        " tám mươi",
        " chín mươi"
      };

      private static final String[] numNames = {
        "",
        " một",
        " hai",
        " ba",
        " bốn",
        " năm",
        " sáu",
        " bảy",
        " tám",
        " chín",
        " mười",
        " mười một",
        " mười hai",
        " mười ba",
        " mười bốn",
        " mười lăm",
        " mười sáu",
        " mười bảy",
        " mười tám",
        " mười chín"
      };
      private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20){
          soFar = numNames[number % 100];
          number /= 100;
        }
        else {
          soFar = numNames[number % 10];
          number /= 10;

          soFar = tensNames[number % 10] + soFar;
          number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " trăm" + soFar;
      }
    public String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) { return "không"; }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0,3));
        // nnnXXXnnnnnn
        int millions  = Integer.parseInt(snumber.substring(3,6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6,9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9,12));

        String tradBillions;
        switch (billions) {
        case 0:
          tradBillions = "";
          break;
        case 1 :
          tradBillions = convertLessThanOneThousand(billions)
          + " tỉ ";
          break;
        default :
          tradBillions = convertLessThanOneThousand(billions)
          + " tỉ ";
        }
        String result =  tradBillions;

        String tradMillions;
        switch (millions) {
        case 0:
          tradMillions = "";
          break;
        case 1 :
          tradMillions = convertLessThanOneThousand(millions)
             + " triệu ";
          break;
        default :
          tradMillions = convertLessThanOneThousand(millions)
             + " triệu ";
        }
        result =  result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
        case 0:
          tradHundredThousands = "";
          break;
        case 1 :
          tradHundredThousands = "một nghìn ";
          break;
        default :
          tradHundredThousands = convertLessThanOneThousand(hundredThousands)
             + " nghìn ";
        }
        result =  result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result =  result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
      }

    
    // make short name
    public String makeShortName(String name){
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
        if(name.toLowerCase().contains("tmcp") ){
            name = name.substring(0,name.toLowerCase().indexOf("tmcp")) + "" + name.substring(name.toLowerCase().indexOf("tmcp")+"tmcp ".length());
        }
        if(name.toLowerCase().contains("một thành viên")){
            name = name.substring(0,name.toLowerCase().indexOf("một thành viên")) + "" + name.substring(name.toLowerCase().indexOf("một thành viên")+"một thành viên".length());
        }else if(name.toLowerCase().contains("mtv")){
           name = name.substring(0,name.toLowerCase().indexOf("mtv")) + "" + name.substring(name.toLowerCase().indexOf("mtv")+"mtv".length());
        }
        if(name.toLowerCase().contains("tnhh")){
            name = name.substring(0,name.toLowerCase().indexOf("tnhh")) + "" + name.substring(name.toLowerCase().indexOf("tnhh")+"tnhh".length());
        }
        if(name.toLowerCase().contains("ngân hàng")){
            name = name.substring(0,name.toLowerCase().indexOf("ngân hàng")) + "" + name.substring(name.toLowerCase().indexOf("ngân hàng")+"ngân hàng".length());
        }
        return name;
    }
    
    // Thay đổi loại đơn
    public String[] getLoaiDon(String loaidon){
        String[] ldon = {"","","",""};
        switch(loaidon){
            case "Đăng ký lần đầu": ldon[0] = "thế chấp";ldon[1] = "Đăng ký thế chấp";ldon[2] = "Đăng ký thế chấp";ldon[3] = "THẾ CHẤP";
                break;
            case "Đăng ký thay đổi": ldon[0] = " thế chấp";ldon[1] = "Đăng ký thế chấp";ldon[2] = "Đăng ký thay đổi";ldon[3] = "THAY ĐỔI THẾ CHẤP";
                break;
            case "Đăng ký thay đổi xóa":ldon[0] = "xóa thế chấp";ldon[1] = "Xóa thế chấp";ldon[2] = "Rút bớt tài sản";ldon[3] = "THAY ĐỔI THẾ CHẤP";
                break;
            case "Xóa đơn đăng ký":ldon[0] = "xóa thế chấp";ldon[1] = "Xóa thế chấp";ldon[2] = "Xóa đăng ký";ldon[3] = "XÓA THẾ CHẤP";
                break;
            default: ldon[0] = "Thế chấp";ldon[1] = "Thế chấp";ldon[2] = "Đăng ký thế chấp";ldon[3] = "THẾ CHẤP";
                break;
        }
        return ldon;
    }
    
    public String priceWithDecimal (Double price) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat df = (DecimalFormat)nf;
       // DecimalFormat formatter = new DecimalFormat("###.###.###");
        return df.format(price);
    }
}
