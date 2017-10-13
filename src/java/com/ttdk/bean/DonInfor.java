/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import java.util.ArrayList;

/**
 *
 * @author ntdung
 */
public class DonInfor {
    private String sodon;
    private String thoigiandk;
    private ArrayList<ArrayList<String>> benbaodam;
    private ArrayList<ArrayList<String>> bennhanbaodam;
    private String loaidon;
    private String mapin;
    private String nguoithuchien;
    private String loaigiaodich;
    private String makhachhang;
    private String trungtam;
    private String link;
    private String linkprint;
    private int maloaidon;
    private int donid;

    public int getDonid() {
        return donid;
    }

    public void setDonid(int donid) {
        this.donid = donid;
    }
    public int getMaloaidon() {
        return maloaidon;
    }

    public void setMaloaidon(int maloaidon) {
        this.maloaidon = maloaidon;
    }
    
    public String getLinkprint() {
        return linkprint;
    }

    public void setLinkprint(String linkprint) {
        this.linkprint = linkprint;
    }
    
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSodon() {
        return sodon;
    }

    public void setSodon(String sodon) {
        this.sodon = sodon;
    }

    public String getThoigiandk() {
        return thoigiandk;
    }

    public void setThoigiandk(String thoigiandk) {
        this.thoigiandk = thoigiandk;
    }


    public String getLoaidon() {
        return loaidon;
    }

    public void setLoaidon(String loaidon) {
        this.loaidon = loaidon;
    }

    public String getMapin() {
        return mapin;
    }

    public void setMapin(String mapin) {
        this.mapin = mapin;
    }

    public String getNguoithuchien() {
        return nguoithuchien;
    }

    public void setNguoithuchien(String nguoithuchien) {
        this.nguoithuchien = nguoithuchien;
    }

    public String getLoaigiaodich() {
        return loaigiaodich;
    }

    public void setLoaigiaodich(String loaigiaodich) {
        this.loaigiaodich = loaigiaodich;
    }

    public String getMakhachhang() {
        return makhachhang;
    }

    public void setMakhachhang(String makhachhang) {
        this.makhachhang = makhachhang;
    }

    public String getTrungtam() {
        return trungtam;
    }

    public void setTrungtam(String trungtam) {
        this.trungtam = trungtam;
    }
    
    public ArrayList<ArrayList<String>> getBenbaodam() {
        return benbaodam;
    }

    public void setBenbaodam(ArrayList<ArrayList<String>> benbaodam) {
        this.benbaodam = benbaodam;
    }

    public ArrayList<ArrayList<String>> getBennhanbaodam() {
        return bennhanbaodam;
    }

    public void setBennhanbaodam(ArrayList<ArrayList<String>> bennhanbaodam) {
        this.bennhanbaodam = bennhanbaodam;
    }
}
