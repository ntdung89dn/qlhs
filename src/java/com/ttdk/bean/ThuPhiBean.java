/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

/**
 *
 * @author ntdung
 */
public class ThuPhiBean {
    private String donids;
    private String bnbd;
    private double tongtien;
    private String account;
    private String benbaodam;
    private int khID;
    private String address;
    private String manhan;
    private String sohieu;
    private String ngaytp;
    private String lydo;
    private int loaitt;
    private String tendonvi;
    private String tdvdiachi;
    private String ngaynhap;
    private boolean isPhuluc;

    public boolean isIsPhuluc() {
        return isPhuluc;
    }

    public void setIsPhuluc(boolean isPhuluc) {
        this.isPhuluc = isPhuluc;
    }
    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }
    
    public String getTendonvi() {
        return tendonvi;
    }

    public void setTendonvi(String tendonvi) {
        this.tendonvi = tendonvi;
    }

    public String getTdvdiachi() {
        return tdvdiachi;
    }

    public void setTdvdiachi(String tdvdiachi) {
        this.tdvdiachi = tdvdiachi;
    }
    public String getSohieu() {
        return sohieu;
    }

    public void setSohieu(String sohieu) {
        this.sohieu = sohieu;
    }

    public String getNgaytp() {
        return ngaytp;
    }

    public void setNgaytp(String ngaytp) {
        this.ngaytp = ngaytp;
    }

    public String getLydo() {
        return lydo;
    }

    public void setLydo(String lydo) {
        this.lydo = lydo;
    }

    public int getLoaitt() {
        return loaitt;
    }

    public void setLoaitt(int loaitt) {
        this.loaitt = loaitt;
    }
    
    public String getManhan() {
        return manhan;
    }

    public void setManhan(String manhan) {
        this.manhan = manhan;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getKhID() {
        return khID;
    }

    public void setKhID(int khID) {
        this.khID = khID;
    }
    public String getBenbaodam() {
        return benbaodam;
    }

    public void setBenbaodam(String benbaodam) {
        this.benbaodam = benbaodam;
    }
    public String getDonids() {
        return donids;
    }

    public void setDonids(String donids) {
        this.donids = donids;
    }

    public String getBnbd() {
        return bnbd;
    }

    public void setBnbd(String bnbd) {
        this.bnbd = bnbd;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String maNhan(int dkid,String manhan, int lnid){
        String returnStr = "";
        switch(dkid){
            case 1:
                break;
            case 2:
                break;
            case 3 :
                break;
            default:
                break;
        }
        return returnStr;
    }
}
