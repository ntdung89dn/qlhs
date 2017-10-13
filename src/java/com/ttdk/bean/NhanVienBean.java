/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

/**
 *
 * @author Thorfinn
 */
public class NhanVienBean {
    private String fullname;
    private String username;
    private int dinhmuc;
    private int dinhmucreal;
    private boolean isNhapLieu;

    public boolean isNhapLieu() {
        return isNhapLieu;
    }

    public void setIsNhapLieu(boolean isNhapLieu) {
        this.isNhapLieu = isNhapLieu;
    }
    
    public int getDinhmucreal() {
        return dinhmucreal;
    }

    public void setDinhmucreal(int dinhmucreal) {
        this.dinhmucreal = dinhmucreal;
    }
    
    public int getDinhmuc() {
        return dinhmuc;
    }

    public void setDinhmuc(int dinhmuc) {
        this.dinhmuc = dinhmuc;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
