/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qlhs.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfReader;
import com.ttdk.createFile.ThongBaoPhiPDF;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ntdung
 */
public class TestSearch {
    public static void main(String args[]){
//       String data= "NGUYỄN VĂN BẰNG | 245084155 |   |  || HUỲNH THỊ NGỌC HÂN | 245372562 |   |";
//       for(int i=0; i< data.split("\\|\\|").length;i++){
//           System.out.println(data.split("\\|\\|")[i]);
//       }
//        String data = "91661,91665,91666,91667,91668,91669,91670,91671,91672,91673,91674,91675,91676,91677,91678,91679,91680,91681,91782,98691,98692,98693,98694,98695,98696,98698,99706,99711,99713,99720,99773,99774,99776,100326,100414,100449,100453,100477,100478,100479,100482,100483,100485,100492,100494,100495,100498,100507,100511,100521,100524,100525,100528,100740,102359,102392,102393,102398,102399,102400,102403,102404,102405,102406,109696,109712,109737,109764,109766,109767,109768,109769,109772,109773,109774,109825,109826,109827,109828,109829,109830,109831,109832,109854,109861,110439,110440,110441,110442,110443,110444,110446,110447,110448,110449,110450,110451,111179,112461,119481,119482,119484,119500,119502,119503,119504,119505,119507,119512,119514,119515,119516,119517,119518,119519,119520,119602,119614,119618,119624,119814,120715,121579";
//        System.out.println(data.length());
             String DOC_ONE_PATH = "D:/Test.pdf";
            String DOC_TWO_PATH = "D:/Test1.pdf";
            String DOC_THREE_PATH = "D:/three.pdf";
            Document document = new Document();
            PdfCopy copy;
        try {
            copy = new PdfCopy(document, new FileOutputStream(DOC_THREE_PATH));
             document.open();
            PdfReader readerOne = new PdfReader(DOC_ONE_PATH);
            PdfReader readerTwo = new PdfReader(DOC_TWO_PATH);
            copy.addDocument(readerOne);
            copy.addDocument(readerTwo);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TestSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(TestSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
}
