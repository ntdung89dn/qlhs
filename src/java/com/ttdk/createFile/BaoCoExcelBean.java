/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.createFile;

import java.util.ArrayList;

/**
 *
 * @author ntdung
 */
public class BaoCoExcelBean {
    private int bcid;
    private String ngaybc;
    private String sobc;
    private String makh;
    private String tenkh;
    private String ndbc;
    private String tongtienbc;
    private String tienthua;
    private ArrayList<ArrayList<String>> bienlailist;

    public String getTienthua() {
        return tienthua;
    }

    public void setTienthua(String tienthua) {
        this.tienthua = tienthua;
    }

    public int getBcid() {
        return bcid;
    }

    public void setBcid(int bcid) {
        this.bcid = bcid;
    }
    
    public String getNgaybc() {
        return ngaybc;
    }

    public void setNgaybc(String ngaybc) {
        this.ngaybc = ngaybc;
    }

    public String getSobc() {
        return sobc;
    }

    public void setSobc(String sobc) {
        this.sobc = sobc;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getNdbc() {
        return ndbc;
    }

    public void setNdbc(String ndbc) {
        this.ndbc = ndbc;
    }

    public String getTongtienbc() {
        return tongtienbc;
    }

    public void setTongtienbc(String tongtienbc) {
        this.tongtienbc = tongtienbc;
    }

    public ArrayList<ArrayList<String>> getBienlailist() {
        return bienlailist;
    }

    public void setBienlailist(ArrayList<ArrayList<String>> bienlailist) {
        this.bienlailist = bienlailist;
    }
}
