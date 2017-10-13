/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thorfinn
 */
public class DBConnect {
    Connection connect = null;
    public Connection dbConnect(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost/qlhsdb?useUnicode=true&characterEncoding=UTF-8";
          //  String url = "jdbc:mysql://195.195.200.168/qlhsdb?useUnicode=true&characterEncoding=UTF-8";
            connect = DriverManager.getConnection(url, "root", "root");
            // = DriverManager.getConnection("jdbc:mysql://195.195.200.186/qlhs?user=ttdk3&password=trungtam3");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
    }
    
    public void closeAll(Connection connect,PreparedStatement pstm, ResultSet rs) throws SQLException{
        if(rs != null){
            rs.close();
        }
        if(pstm != null){
            pstm.close();
        }
        if(!connect.isClosed() && connect !=null){
            connect.close();
        }
    }
    
    public void closePSRS(PreparedStatement pstm, ResultSet rs) throws SQLException{
        if(rs != null){
            rs.close();
        }
        if(pstm != null){
            pstm.close();
        }
    }
    public void closeAll(Connection connect,PreparedStatement pstm) throws SQLException{
        if(pstm != null){
            pstm.close();
        }
        if(!connect.isClosed()){
            connect.close();
        }
    }
}
