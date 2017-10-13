/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

import com.ttdk.bean.ThongKe;
import com.ttdk.bean.Thuphi;
import com.ttdk.connect.DBConnect;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author ntdung
 */
public class SendEmail {
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    
    
    public ArrayList<String> getEmail(String email){
        ArrayList<String> data =  new ArrayList<>();
        String sql = "select TTE_ID as email ,TTE_Passwd as passwd from tbl_ttemail where TTE_ID = ?";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, email);
            rs = pstm.executeQuery();
            while(rs.next()){
                String emailrs = rs.getString("email");
                String pawwd = rs.getString("passwd");
                data.add(emailrs);data.add(pawwd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return data;
    }
    public boolean email(int khid,String donids,String ngaygui,String ngayhethan,String email,String sender) {
        boolean checkSend = false;
      //  final String username = "ngdung89dn@gmail.com";
    //    final String password = "Sun Java";
        ArrayList<String> emailList = getEmail(email);
        String content = "THÔNG BÁO THANH TOÁN LỆ PHÍ ĐẾN NGÀY "+ngaygui; //this will be the text of the email
        String subject = "THÔNG BÁO THANH TOÁN LỆ PHÍ ĐẾN NGÀY "+ngaygui; //this will be the subject of the email
      //  String sender = "ngdung89dn@gmail.com"; //replace this with a valid sender email address
        String recipient = new ThongBaoPhiPDF().loadEmail(khid);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailList.get(0), emailList.get(1));
                }
          });
         
        ByteArrayOutputStream outputStream = null;
         
        try {           
            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setHeader("Content-Type","text/plain; charset=\"utf-8\""); 
            textBodyPart.setText(content, "UTF-8");
             
            //now write the PDF content to the output stream
            outputStream = new ByteArrayOutputStream();
            new ThongBaoPhiPDF().createPDF(outputStream, khid, donids, ngaygui,ngayhethan);
            byte[] bytes = outputStream.toByteArray();
             
            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("Thong bao no den ngay "+ngaygui+".pdf");
                         
            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);
             
            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);
             
            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject, "UTF-8");
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);
             
            //send off the email
            Transport.send(mimeMessage);
             
            System.out.println("sent from " + sender + 
                    ", to " + recipient);         
            checkSend =  true;
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            //clean off
            if(null != outputStream) {
                try { outputStream.close(); outputStream = null; }
                catch(Exception ex) { }
            }
        }
        return checkSend;
    }
    
    // gửi email thông báo phí
    public boolean emailTBP(int khid,int tbpid,String email,String sender) {
        boolean checkSend = false;
      //  final String username = "ngdung89dn@gmail.com";
    //    final String password = "Sun Java";
        ArrayList<String> emailList = getEmail(email);
        ArrayList<String> tbpInfor = new Thuphi().geTBP(tbpid);
        ArrayList<Integer> donsTbp = new Thuphi().getDonTBPByID(tbpid);
        String donids = donsTbp.toString().replaceAll("\\[", "").replaceAll("\\]", "");
        String content = "THÔNG BÁO THANH TOÁN LỆ PHÍ ĐẾN NGÀY "+tbpInfor.get(1); //this will be the text of the email
        String subject = "THÔNG BÁO THANH TOÁN LỆ PHÍ ĐẾN NGÀY "+tbpInfor.get(1); //this will be the subject of the email
      //  String sender = "ngdung89dn@gmail.com"; //replace this with a valid sender email address
        String recipient = new ThongBaoPhiPDF().loadEmail(khid);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailList.get(0), emailList.get(1));
                }
          });
         
        ByteArrayOutputStream outputStream = null;
         
        try {           
            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setHeader("Content-Type","text/plain; charset=\"utf-8\""); 
            textBodyPart.setText(content, "UTF-8");
             
            //now write the PDF content to the output stream
            outputStream = new ByteArrayOutputStream();
            new ThongBaoPhiPDF().createPDF(outputStream, khid, tbpInfor, donids);
            byte[] bytes = outputStream.toByteArray();
             
            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("Thong bao no den ngay "+tbpInfor.get(1)+".pdf");
                         
            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);
             
            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);
             
            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject, "UTF-8");
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);
             
            //send off the email
            Transport.send(mimeMessage);
             
            System.out.println("sent from " + sender + 
                    ", to " + recipient);         
            checkSend =  true;
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            //clean off
            if(null != outputStream) {
                try { outputStream.close(); outputStream = null; }
                catch(Exception ex) { }
            }
        }
        return checkSend;
    }
}
