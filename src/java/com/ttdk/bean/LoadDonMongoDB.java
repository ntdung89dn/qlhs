/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.UpdateResult;
import com.ttdk.connect.DBConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONException;

/**
 *
 * @author ntdung
 */
public class LoadDonMongoDB {
    private String HOST = "localhost";
    private int PORT = 27017;
    private Connection connect;
    private ResultSet rs = null;
    private PreparedStatement pstm = null;
    private CallableStatement cstm = null;
    MongoClient client =  null;
    public LoadDonMongoDB(){}
    
    public MongoDatabase getConnectionMG(){
        client =  new MongoClient(HOST, PORT);
        MongoDatabase db = client.getDatabase("qlhsdb");
        return db;
    }
    
    public String loadCollectionMG() throws JSONException{
        String table = "";
        MongoCursor<Document> cursor = null;
        MongoDatabase db = getConnectionMG();
        if(db != null){
            MongoCollection<Document> collection = db.getCollection("qlhs_don");
            cursor = collection.find(eq("isInsert",0)).iterator();
            int stt = 1;
            try{
            while(cursor.hasNext()){
                table +="<tr>";
                Document object = cursor.next();
                Object id = object.get("_id");
              //  JSONObject idObj = (JSONObject)object.get("_id");
         //        String strID = (String) idObj.get("$oid");
//               String select =  "<select id='loaidon' class='form-control loaidon' name=\"loaidon\" style=\"width: 150px !important; height: 30px\">\n" +
//                                        "<option value=\"1\">LĐ</option>\n" +
//                                        "<option value=\"2\">TĐ</option>\n" +
//                                        "<option value=\"3\">Xóa</option>\n" +
//                                        "<option value=\"4\">VB-XL-TS</option>\n" +
//                                        "<option value=\"5\">CC-TT</option>\n" +
//                                        "<option value=\"6\">BẢN SAO</option>\n" +
//                                        "<option value=\"7\">TBKBTHA</option>\n" +
//                                        "<option value=\"8\">MIỄN PHÍ</option>\n" +
//                                        "</select>";
                table += "<td>"+stt+"</td>";
                String thoigiandk = object.getString("thoigiandky");
                table += "<td>"+thoigiandk+"</td>";
                String maonline = object.getString("maonline");
                table += "<td>"+maonline+"</td>";
                String mapin = object.getString("mapin");
                table += "<td>"+mapin+"</td>";
                String loaidon = object.getString("loaidon");
                if(loaidon.toLowerCase().contains("lần đầu")){
                    String select =  "<select id='loaidon' class='form-control loaidon' name=\"loaidon\" style=\"width: 150px !important; height: 30px\">\n" +
                                        "<option value=\"1\" selected=\"selected\">LĐ</option>\n" +
                                        "<option value=\"2\">TĐ</option>\n" +
                                        "<option value=\"3\">Xóa</option>\n" +
                                        "<option value=\"4\">VB-XL-TS</option>\n" +
                                        "<option value=\"5\">CC-TT</option>\n" +
                                        "<option value=\"6\">BẢN SAO</option>\n" +
                                        "<option value=\"7\">TBKBTHA</option>\n" +
                                        "<option value=\"8\">MIỄN PHÍ</option>\n" +
                                        "</select>";
                    table += "<td>"+select+"</td>";
                }else if(loaidon.toLowerCase().contains("thay đổi")){
                     String select =  "<select id='loaidon' class='form-control loaidon' name=\"loaidon\" style=\"width: 150px !important; height: 30px\">\n" +
                                        "<option value=\"1\" >LĐ</option>\n" +
                                        "<option value=\"2\" selected=\"selected\">TĐ</option>\n" +
                                        "<option value=\"3\">Xóa</option>\n" +
                                        "<option value=\"4\">VB-XL-TS</option>\n" +
                                        "<option value=\"5\">CC-TT</option>\n" +
                                        "<option value=\"6\">BẢN SAO</option>\n" +
                                        "<option value=\"7\">TBKBTHA</option>\n" +
                                        "<option value=\"8\">MIỄN PHÍ</option>\n" +
                                        "</select>";
                      table += "<td>"+select+"</td>";
                }else if(loaidon.toLowerCase().contains("xóa")){
                    String select =  "<select id='loaidon' class='form-control loaidon' name=\"loaidon\" style=\"width: 150px !important; height: 30px\">\n" +
                                        "<option value=\"1\" >LĐ</option>\n" +
                                        "<option value=\"2\" >TĐ</option>\n" +
                                        "<option value=\"3\" selected=\"selected\">Xóa</option>\n" +
                                        "<option value=\"4\">VB-XL-TS</option>\n" +
                                        "<option value=\"5\">CC-TT</option>\n" +
                                        "<option value=\"6\">BẢN SAO</option>\n" +
                                        "<option value=\"7\">TBKBTHA</option>\n" +
                                        "<option value=\"8\">MIỄN PHÍ</option>\n" +
                                        "</select>";
                     table += "<td>"+select+"</td>";
                }
                
               Document bnbdList = (Document) object.get("bnbd");
               String[] nameBnBd = bnbdList.getString("name").split("__");
                String[] addressBnBD = bnbdList.getString("address").split("__");;
                table += "<td>";
                for(int i=0;i < nameBnBd.length;i++){
                    table += "<p>"+nameBnBd[i]+"-"+addressBnBD[i]+"</p>";
                }
                table += "</td>";
                String makhachhang = object.getString("makhachhang");
                Khachhang kh = new Khachhang();
                System.out.println(makhachhang);
                System.out.println(kh.getKHangByAccountView(makhachhang,stt));
                table += "<td>"+kh.getKHangByAccountView(makhachhang,stt)+"</td>";
                
                Document bbdList = (Document) object.get("bbd");
                String[] nameBBd = bbdList.getString("name").split("__");
                String[] masoBBD = bbdList.getString("maso").split("__");
                table += "<td>";
                for(int i=0;i < nameBBd.length;i++){
                    table += "<p>"+nameBBd[i]+"-"+masoBBD[i]+"</p>";
                }
                stt++;
                table += "</td>";
//                table += "<td style='width:150px;'>Số lượng PL :<input type='text' value='0' class='input_slphuluc' style='width:50px;'><br>"
//                        + "<div class='div_sopl' hidden>Số CV đầu tiên :<input type='text' value='0' class='input_socvphuluc' style='width:50px;' ><br>"
//                        + " Mã Online: <input type='text' value='0' class='input_soonlinepl' style='width:150px;' ></div></td>";
                String barcode = object.getString("barcode");
                    table += "<td style='width:150px;'>Số lượng PL :<input type='text' value='0' class='input_slphuluc' style='width:50px;'><br>"
                        + "<div class='div_sopl' hidden>Số CV đầu tiên :<input type='text' value='0' class='input_socvphuluc' style='width:50px;' ><br>"
                        + " Mã Online: <input type='text' value='0' class='input_soonlinepl' style='width:150px;' >";
                            if(barcode != null || !barcode.equals("")){
                              //  table += "<a target='_blank' href='./print/phuluc05.jsp?id="+barcode+"&page=' >In Phụ Lục</a>";
                              table += "<input type='text' value='"+barcode+"' class='barcode' hidden><button type='button' class='btn btn-link link_phuluc' >Link</button>";
                            }
                            table+= "</div><br><select class='form-control' id='loaiphuongtien'>\n" +
                                                "    <option value='1'>Xe ô tô</option>\n" +
                                                "    <option value='2'>Sơmi rơmooc</option>\n" +
                                                "    <option value='3'>Tàu Thủy</option>\n" +
                                                "  </select></td>";
                if(barcode == null || barcode.equals("")){
                    table += "<td><button type='button' class='btn btn-default nhapdon'>Nhập đơn</button></td>";
                }else{
                    String link = "./print/print_mau2017.jsp?id="+barcode+"&type=on";
                    table += "<td><a target='_' href='"+link+"'>Link Print</a></td>";
                }
                table+="</tr>";
            }
         }catch(Exception ex){
             
         }finally {
                closeAll(client, cursor);
         }
        }else{
            table = "<tr><td colspan='6'>Không kết nối được tới dữ liệu</td></tr>";
        }
        
        return table;
    }
    
    // Update isInsert = 1 đơn theo maonline 
    public DonInfor updateDonByMaOn(String maon,int maloaidon){
        MongoDatabase db = getConnectionMG();
        DonInfor di = new DonInfor();
        if(db != null){
            MongoCollection<Document> collection = db.getCollection("qlhs_don");
            Document object = collection.findOneAndUpdate(eq("maonline",maon),new Document("$set", new Document("isInsert", 1)));
            
            String thoigiandk = object.getString("thoigiandky");
            di.setThoigiandk(thoigiandk);
            String maonline = object.getString("maonline");
            di.setSodon(maonline);
            String mapin = object.getString("mapin");
            di.setMapin(mapin);
          //  String loaidon = object.getString("loaidon");
            di.setMaloaidon(maloaidon);
            Document bnbdList = (Document) object.get("bnbd");
            String[] nameBnBd = bnbdList.getString("name").split("__");
            String[] addressBnBD = bnbdList.getString("address").split("__");
            ArrayList<ArrayList<String>> bnbdReturnList = new ArrayList<>();
            for(int i=0;i< nameBnBd.length;i++){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(nameBnBd[i]);dt.add(addressBnBD[i]);
                bnbdReturnList.add(dt);
            }
            di.setBennhanbaodam(bnbdReturnList);;
            String makhachhang = object.getString("makhachhang");
            di.setMakhachhang(makhachhang);
            Document bbdList = (Document) object.get("bbd");
            String[] nameBBd = bbdList.getString("name").split("__");
            String[] masoBBD = bbdList.getString("maso").split("__");
            ArrayList<ArrayList<String>> bbdReturnList = new ArrayList<>();
            for(int i=0;i< nameBBd.length;i++){
                ArrayList<String> dt = new ArrayList<>();
                dt.add(nameBBd[i]);dt.add(masoBBD[i]);
                bbdReturnList.add(dt);
            }
            di.setBenbaodam(bbdReturnList); 
        }
        closeAll(client, null);
        return di;
    }
    // Update barcode đơn theo maonline 
    public DonInfor updateBarcodeByMaOn(String maon,String barcode){
        MongoDatabase db = getConnectionMG();
        DonInfor di = new DonInfor();
        if(db != null){
            MongoCollection<Document> collection = db.getCollection("qlhs_don");
            UpdateResult object = collection.updateOne(eq("maonline",maon), new Document("$set", new Document("barcode", barcode)));
        }
        closeAll(client, null);
        return di;
    }
    // search đơn by maonline
    public ArrayList<Document> searchDonByOn(String maon,int isInsert,int savePL){
        MongoDatabase db = getConnectionMG();
        MongoCursor<Document> cursor = null;
        String text = "";
        ArrayList<Document> docs = new ArrayList();
        if(db != null){
            MongoCollection<Document> collection = db.getCollection("qlhs_don");
            Document sv1 = new Document();
            if(!maon.equals("")){
                sv1.append("maonline", new Document("$regex", maon));
            }
        sv1.append("isInsert", isInsert).append("isSavePL", savePL);
            System.out.println(sv1.toJson());
            cursor = collection.find(Filters.and(sv1)).iterator();
            while(cursor.hasNext()){
                Document docObj = cursor.next();
                docs.add(docObj);
//                String maonline = docObj.getString("maonline");
//                String thoigiandk = docObj.getString("thoigiandky");
            }
        }
        closeAll(client, null);
        return docs;
    }
    
    // load bnbd 
    public DonInfor  loadBNBDnBBD(String maonline){
        MongoDatabase db = getConnectionMG();
        DonInfor di = new DonInfor();
        MongoCursor<Document> cursor = null;
        if(db != null){
            MongoCollection<Document> collection = db.getCollection("qlhs_don");
            cursor = collection.find(eq("maonline", maonline)).iterator();
            while(cursor.hasNext()){
                Document object = cursor.next();
                Document bnbdList = (Document) object.get("bnbd");
                String[] nameBnBd = bnbdList.getString("name").split("__");
                String[] addressBnBD = bnbdList.getString("address").split("__");
                ArrayList<ArrayList<String>> bnbdReturnList = new ArrayList<>();
                for(int i=0;i< nameBnBd.length;i++){
                    ArrayList<String> dt = new ArrayList<>();
                    dt.add(nameBnBd[i]);dt.add(addressBnBD[i]);
                    bnbdReturnList.add(dt);
                    System.out.println(nameBnBd[i] +" || "+addressBnBD[i]);
                }
                di.setBennhanbaodam(bnbdReturnList);;
                Document bbdList = (Document) object.get("bbd");
                String[] nameBBd = bbdList.getString("name").split("__");
                String[] masoBBD = bbdList.getString("maso").split("__");
                ArrayList<ArrayList<String>> bbdReturnList = new ArrayList<>();
                for(int i=0;i< nameBBd.length;i++){
                    ArrayList<String> dt = new ArrayList<>();
                    dt.add(nameBBd[i]);dt.add(masoBBD[i]);
                    bbdReturnList.add(dt);
                    System.out.println(nameBBd[i] +" || "+masoBBD[i]);
                }
                di.setBenbaodam(bbdReturnList); 
            }
            closeAll(client, cursor);
        }
        return di;
    }
    
    // Lấy mã nhận cho phụ lục đã lưu
    public int maphuluc(int donid){
        int number = 0;
        String sql = "select D_manhan as maphuluc"
                + " from tbl_don where tbl_don.D_ID = ?;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setInt(1, donid);
            rs = pstm.executeQuery();
            while(rs.next()){
                number = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoadDonMongoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return number;
    }
    // view search đơn 
    public String viewSearchDonByOn(String maon,int isInsert,int savePL){

        String table = "";
        ArrayList<Document> docs =  searchDonByOn(maon, isInsert, savePL);
        int stt = 1;
        for(Document object : docs){
             table +="<tr>";
                Object id = object.get("_id");
              //  JSONObject idObj = (JSONObject)object.get("_id");
         //        String strID = (String) idObj.get("$oid");
//               String select =  "<select id='loaidon' class='form-control loaidon' name=\"loaidon\" style=\"width: 150px !important; height: 30px\">\n" +
//                                        "<option value=\"1\">LĐ</option>\n" +
//                                        "<option value=\"2\">TĐ</option>\n" +
//                                        "<option value=\"3\">Xóa</option>\n" +
//                                        "<option value=\"4\">VB-XL-TS</option>\n" +
//                                        "<option value=\"5\">CC-TT</option>\n" +
//                                        "<option value=\"6\">BẢN SAO</option>\n" +
//                                        "<option value=\"7\">TBKBTHA</option>\n" +
//                                        "<option value=\"8\">MIỄN PHÍ</option>\n" +
//                                        "</select>";
                table += "<td>"+stt+"</td>";
                String thoigiandk = object.getString("thoigiandky");
                table += "<td>"+thoigiandk+"</td>";
                String maonline = object.getString("maonline");
                table += "<td>"+maonline+"</td>";
                String mapin = object.getString("mapin");
                table += "<td>"+mapin+"</td>";
                String loaidon = object.getString("loaidon");
                if(loaidon.toLowerCase().contains("lần đầu")){
                    String select =  "<select id='loaidon' class='form-control loaidon' name=\"loaidon\" style=\"width: 150px !important; height: 30px\">\n" +
                                        "<option value=\"1\" selected=\"selected\">LĐ</option>\n" +
                                        "<option value=\"2\">TĐ</option>\n" +
                                        "<option value=\"3\">Xóa</option>\n" +
                                        "<option value=\"4\">VB-XL-TS</option>\n" +
                                        "<option value=\"5\">CC-TT</option>\n" +
                                        "<option value=\"6\">BẢN SAO</option>\n" +
                                        "<option value=\"7\">TBKBTHA</option>\n" +
                                        "<option value=\"8\">MIỄN PHÍ</option>\n" +
                                        "</select>";
                    table += "<td>"+select+"</td>";
                }else if(loaidon.toLowerCase().contains("thay đổi")){
                     String select =  "<select id='loaidon' class='form-control loaidon' name=\"loaidon\" style=\"width: 150px !important; height: 30px\">\n" +
                                        "<option value=\"1\" >LĐ</option>\n" +
                                        "<option value=\"2\" selected=\"selected\">TĐ</option>\n" +
                                        "<option value=\"3\">Xóa</option>\n" +
                                        "<option value=\"4\">VB-XL-TS</option>\n" +
                                        "<option value=\"5\">CC-TT</option>\n" +
                                        "<option value=\"6\">BẢN SAO</option>\n" +
                                        "<option value=\"7\">TBKBTHA</option>\n" +
                                        "<option value=\"8\">MIỄN PHÍ</option>\n" +
                                        "</select>";
                      table += "<td>"+select+"</td>";
                }else if(loaidon.toLowerCase().contains("xóa")){
                    String select =  "<select id='loaidon' class='form-control loaidon' name=\"loaidon\" style=\"width: 150px !important; height: 30px\">\n" +
                                        "<option value=\"1\" >LĐ</option>\n" +
                                        "<option value=\"2\" >TĐ</option>\n" +
                                        "<option value=\"3\" selected=\"selected\">Xóa</option>\n" +
                                        "<option value=\"4\">VB-XL-TS</option>\n" +
                                        "<option value=\"5\">CC-TT</option>\n" +
                                        "<option value=\"6\">BẢN SAO</option>\n" +
                                        "<option value=\"7\">TBKBTHA</option>\n" +
                                        "<option value=\"8\">MIỄN PHÍ</option>\n" +
                                        "</select>";
                     table += "<td>"+select+"</td>";
                }
                
               Document bnbdList = (Document) object.get("bnbd");
               String[] nameBnBd = bnbdList.getString("name").split("__");
                String[] addressBnBD = bnbdList.getString("address").split("__");;
                table += "<td>";
                for(int i=0;i < nameBnBd.length;i++){
                    table += "<p>"+nameBnBd[i]+"-"+addressBnBD[i]+"</p>";
                }
                table += "</td>";
                String makhachhang = object.getString("makhachhang");
                Khachhang kh = new Khachhang();
//                System.out.println(makhachhang);
//                System.out.println(kh.getKHangByAccountView(makhachhang,stt));
                table += "<td>"+kh.getKHangByAccountView(makhachhang,stt)+"</td>";
                
                Document bbdList = (Document) object.get("bbd");
                String[] nameBBd = bbdList.getString("name").split("__");
                String[] masoBBD = bbdList.getString("maso").split("__");
                table += "<td>";
                for(int i=0;i < nameBBd.length;i++){
                    table += "<p>"+nameBBd[i]+"-"+masoBBD[i]+"</p>";
                }
                stt++;
                table += "</td>";
                String barcode = object.getString("barcode");
                
                if(savePL ==1){
                    String barcodepl  = object.getString("barcodepl");
                    String maonlinepl = object.getString("maonlinePL");
                    int numPL = barcodepl.split("-").length;
                    int manhanPL = maphuluc(Integer.parseInt(barcodepl.split("-")[0]));
                    table += "<td style='width:150px;'>Số lượng PL :<input type='text' value='"+numPL+"' class='input_slphuluc' style='width:50px;'><br>"
                        + "<div class='div_plluu' >Số CV đầu tiên :<input type='text' value='"+manhanPL+"' class='input_socvphuluc' style='width:50px;' ><br>"
                        + " Mã Online: <input type='text' value='"+maonlinepl+"' class='input_soonlinepl' style='width:150px;' >";
                }else{
                    table += "<td style='width:150px;'>Số lượng PL :<input type='text' value='0' class='input_slphuluc' style='width:50px;'><br>"
                        + "<div class='div_sopl' hidden>Số CV đầu tiên :<input type='text' value='0' class='input_socvphuluc' style='width:50px;' ><br>"
                        + " Mã Online: <input type='text' value='0' class='input_soonlinepl' style='width:150px;' >";
                }
                    
                         if(barcode != null || !barcode.equals("")){
                             //   table += "<a target='_blank' href='./print/phuluc05.jsp?id="+barcode+"&page=' >In Phụ Lục</a>";
                             table += "<input type='text' value='"+barcode+"' class='barcode' hidden><button type='button' class='btn btn-link link_phuluc' onlick='openPhuluc()'>Link</button>";
                         }
                        table += "</div><br><select class='form-control' id='loaiphuongtien'>\n" +
                                                "    <option value='1'>Xe ô tô</option>\n" +
                                                "    <option value='2'>Sơmi rơmooc</option>\n" +
                                                "    <option value='3'>Tàu Thủy</option>\n" +
                                                "  </select></td>";
                
                
                if(barcode == null || barcode.equals("")){
                    table += "<td><button type='button' class='btn btn-default nhapdon'>Nhập đơn</button></td>";
                }else{
                    String link = "./print/print_mau2017.jsp?id="+barcode+"&type=on";
                    table += "<td><a target='_' href='"+link+"'>Link Print</a></td>";
                }
                
                table+="</tr>";
        }
        return table;
    }
    // close all connetion in Mongodb
    public void closeAll(MongoClient client,MongoCursor<Document> cursor){
        if(cursor != null){
            cursor.close();
        }
        if(client != null){
            client.close();
        }
    }
    
    // Kiểm tra đơn online đã nhập
    public boolean  checkOnlineNotExist(String day, String maonline){
        boolean check = false;
        String sql = "select 1 from tbl_don "+
                                "inner join tbl_cctt on tbl_don.CCTT_ID =  tbl_cctt.CCTT_ID \n" +
                                "inner join tbl_loaidon on tbl_don.LD_ID =  tbl_loaidon.LD_ID \n" +
                                "inner join tbl_loaidk on tbl_loaidk.DK_ID =  tbl_don.DK_ID \n" +
                                "inner join tbl_loainhan on tbl_loainhan.LN_ID =  tbl_don.LN_ID \n" +
                                "inner join tbl_bnbd on tbl_bnbd.KHD_ID = tbl_don.D_ID \n" +
                                "inner join tbl_benbaodam on tbl_benbaodam.D_ID = tbl_don.D_ID\n" +
                            "where tbl_don.D_MDO = ? and tbl_don.D_Date = STR_TO_DATE(?,'%d-%m-%Y') and tbl_don.D_GioNhan = ? and tbl_don.D_PhutNhan = ?;" ;
        int gio = Integer.parseInt(day.split(" ")[1].replaceAll(":", "").substring(0, 2));
        int phut = Integer.parseInt(day.split(" ")[1].replaceAll(":", "").substring(2, 4));
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            pstm.setString(1, maonline);
            pstm.setString(2, day.split(" ")[0]);
            pstm.setInt(3,gio);
            pstm.setInt(4, phut);
            rs = pstm.executeQuery();
            while(rs.next()){
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoadDonMongoDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                new DBConnect().closeAll(connect, pstm, rs);
            } catch (SQLException ex) {
                Logger.getLogger(ThongKe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
    
    
    // Lấy số phụ lục online 
    public int getNumberPLO(){
        int number = 0;
        String sql = "select coalesce(MAX(D_manhan)+1, 1) as maphuluc"
                + " from tbl_don where tbl_don.DK_ID = 3 and tbl_don.LN_ID = 2;";
        connect = new DBConnect().dbConnect();
        try {
            pstm = connect.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                number = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoadDonMongoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return number;
    }
        // Lấy đơn online cần check
    public void getListOnlineCheck(ArrayList<ArrayList<String>> listOnline){
        ArrayList<String> onlineArray = new ArrayList<>();
        for(ArrayList<String> maon : listOnline){
            boolean check = checkOnlineNotExist(maon.get(0), maon.get(1));
            if(check){
                onlineArray.add(maon.get(1));
            }
        }
    }
    
    // update csgt to don
    public void updateCSGT(String csgtname,String csgtdiachi,String barcode){
        MongoDatabase db = getConnectionMG();
        if(db != null){
            MongoCollection<Document> collection = db.getCollection("qlhs_don");
            collection.updateOne(eq("barcode", barcode), new Document("$set", new Document().append("csgtdiachi", csgtdiachi).append("csgtname", csgtname)));
            closeAll(client,null);
        }
    }
    
    // update luu phuluc
    public void updateSavePL(String barcode,String barcodepl,String onlinepl){
        MongoDatabase db = getConnectionMG();
        if(db != null){
            MongoCollection<Document> collection = db.getCollection("qlhs_don");
            collection.updateOne(eq("barcode", barcode), new Document("$set", new Document().append("isSavePL", 1)
                    .append("barcodepl", barcodepl).append("maonlinePL", onlinepl)));
            closeAll(client,null);
        }
    }
    // lấy chi tiết đơn
    public ChiTietDon getChiTietDon(String barcode){
        MongoDatabase db = getConnectionMG();
        MongoCursor<Document> cursor = null;
        ChiTietDon data = new ChiTietDon();
        if(db != null){
            MongoCollection<Document> collection = db.getCollection("qlhs_don");
            cursor = collection.find(eq("barcode", barcode)).iterator();
            while(cursor.hasNext()){
                Document object = cursor.next();
                String madonlandau = object.getString("madonlandau");
                String thoidiemdk = object.getString("thoidiemld");
                String loaihinhgiaodich = object.getString("loaihinhgiaodich");
                String loaidon = object.getString("loaidon");
                String sohopdong = object.getString("sohopdong");
                String ngayhopdong = object.getString("ngaykyhopdong");
                String motats = object.getString("motats");
                String thoigiandk = object.getString("thoigiandky");
              //  String startDateString = "06/27/2007";
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm"); 
                Date startDate;
                try {
                    startDate = df.parse(thoigiandk);
                    thoigiandk = df.format(startDate);
                    System.out.println(thoigiandk);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Document bnbdList = (Document) object.get("bnbd");
                String[] nameBNBD = bnbdList.getString("name").split("__");
                String[] addressBnbd = bnbdList.getString("address").split("__");
                ArrayList<ArrayList<String>> bnbd = new ArrayList<>();
                for(int i=0;i < nameBNBD.length;i++){
                    ArrayList<String> dt = new ArrayList<>();
                    dt.add(nameBNBD[i]);dt.add(addressBnbd[i]);
                    bnbd.add(dt);
                }
                Document bbdList = (Document) object.get("bbd");
                String[] nameBBd = bbdList.getString("name").split("__");
                String[] masoBBD = bbdList.getString("maso").split("__");
                String[] loaichuthe = bbdList.getString("loaichuthe").split("__");
                String[] diachibbd = bbdList.getString("diachibbd").split("__");
                ArrayList<ArrayList<String>> BenBaoDam = new ArrayList<>();
                for(int i=0;i < nameBBd.length;i++){
                    ArrayList<String> dt = new ArrayList<>();
                    dt.add(loaichuthe[i]);dt.add(masoBBD[i]);dt.add(nameBBd[i]);dt.add(diachibbd[i]);
                    BenBaoDam.add(dt);
                }
                ArrayList<ArrayList<String>> phuongtienList = new ArrayList<>();
                if(object.get("thongtinpt") != null){
                    Document thongtinpt = (Document) object.get("thongtinpt");
                    for(int i=0;i<thongtinpt.size();i++){
                        ArrayList<String> pt = new ArrayList<>();
                        String inforPT = thongtinpt.getString("phuongtien"+(i+1));
                        String sokhung = inforPT.split("__")[0];
                        String somay = inforPT.split("__")[1];
                        String bienso = inforPT.split("__")[2];
                        pt.add(sokhung);pt.add(somay);pt.add(bienso);
                        phuongtienList.add(pt);
                    }
                }
                String csgtname  = "";
                if(object.get("csgtname") != null){
                    csgtname = object.getString("csgtname");
                }
                String csgtdiachi  = "";
                if(object.get("csgtdiachi") != null){
                    csgtdiachi = object.getString("csgtdiachi");
                }
                int isSavePL = Integer.parseInt(object.get("isSavePL").toString());
                if(isSavePL ==0){
                    data.setSavePL(false);
                }else{
                    data.setSavePL(true);
                }
                data.setMalandau(madonlandau);data.setThoigianlandau(thoidiemdk);
                data.setLoaigiaodich(loaihinhgiaodich);data.setLoaidon(loaidon);
                data.setSohopdong(sohopdong);data.setNgayhopdong(ngayhopdong);
                data.setMotats(motats);data.setThoigiandk(thoigiandk); 
                data.setBenbaodam(BenBaoDam);data.setSokhungList(phuongtienList);
                data.setBnbd(bnbd);data.setCsgtname(csgtname);data.setCsgtdiachi(csgtdiachi);
                
//                data.add(madonlandau);data.add(thoidiemdk);
//                data.add(loaihinhgiaodich);data.add(loaidon);
//                data.add(sohopdong);data.add(ngayhopdong);
//                data.add(motats);
            }
            closeAll(client, cursor);
        }
        return data;
    }
    
    // load barcode cho phulic
    public ArrayList<String> loadBarcodes(String barcode){
        //String barcodes = "";
        ArrayList<String> data = new ArrayList<>();
        MongoDatabase db = getConnectionMG();
        MongoCursor<Document> cursor ;
        if(db != null){
            MongoCollection<Document> collection = db.getCollection("qlhs_don");
            cursor = collection.find(eq("barcode",barcode)).iterator();
            while(cursor.hasNext()){
                Document doc = cursor.next();
                data.add(doc.getString("barcodepl"));
                data.add(doc.getString("maonlinePL"));
            }
            closeAll(client, cursor);
        }
        return data;
    }
    
}
