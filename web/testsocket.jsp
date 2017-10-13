<%-- 
    Document   : testsocket
    Created on : Jul 20, 2017, 12:25:52 AM
    Author     : ntdung
--%>

<%@page import="com.ttdk.bean.LoadDonMongoDB"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.mongodb.DBObject"%>
<%@page import="com.mongodb.client.MongoCursor"%>
<%@page import="com.mongodb.client.MongoCollection"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoDatabase"%>
<%@page import="com.mongodb.MongoClient"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.Socket"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="js/jquery.min.js" ></script>
    </head>
    <body>
        <H1>Creating Client/Server Applications</H1>
        <form method="POST" action="sendSocket">
            <div class="col-sm-2"><input type="text" class="form-control" id="username" name="username"></div>
            <div class="col-sm-2"><input type="password" class="form-control" id="password" name="password"></div>
            <input type="submit" value="Lấy đơn" >
        </form>
        
         <% 
             String username = (String)request.getAttribute("username");
              String password = (String)request.getAttribute("passwd");
            //  out.println(username);
             String table = "";
             String resultSend = "0";
             if(username != null && password != null ){
                 try{
                        int character;
                        Socket socket = new Socket("127.0.0.1", 8765);

                        InputStream inSocket = socket.getInputStream();
                        OutputStream outSocket = socket.getOutputStream();

                        String str = "dungnt89&Dung89@Finance\n";
                        byte buffer[] = str.getBytes();
                        outSocket.write(buffer);
                        while ((character = inSocket.read()) != -1) {
                            resultSend = String.valueOf((char) character);
                            if(resultSend.equals("1")) break;
                            
                        //    out.print((char) character);
                        }

                        socket.close();
                        System.out.println("LAST RESULT = "+resultSend);
                        if(resultSend.equals("1")){
                                LoadDonMongoDB ldmg =  new LoadDonMongoDB();
                                table = ldmg.loadCollectionMG();
                              //  out.print(table);
                                request.removeAttribute("username");
                                request.removeAttribute("password");
                            }
                    }
                    catch(java.net.ConnectException e){
                    %>
                     Bạn Cần phải chạy file Server trước
                    <%
                    }
            }
                    %>
                    <table>
                        <thead>
                            <tr>
                                <th rowspan="2" align="center" >Thời điểm nhập</th>
                                <th colspan="2" align="center" >Số Đơn Do Online Cấp</th>
                                <th rowspan="2" align="center" >Loại Đơn</th>
                                <th rowspan="2" align="center" >Bên Nhận Bảo Đảm</th>
                                <th rowspan="2" align="center" >Bên Nhận Bảo Đảm(TT Phí)</th>
                                <th rowspan="2" align="center" >Bên Bảo Đảm</th>
                            </tr>
                            <tr>
                                <td class="column2" align="center">Số Đơn Online</td>
                                <td class="column2" align="center">Số Pin</td>
                            </tr>
                        </thead>
                        <tbody>
                            <%=table%>
                        </tbody>
                    </table>
    </body>
</html>
