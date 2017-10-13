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
public class CSGTBean {
    private String name;
    private ArrayList<ArrayList> csgtdc;
    private int cityid;
    private String bienso;
    private int nameid;
    private String bsid;

    public String getBsid() {
        return bsid;
    }

    public void setBsid(String bsid) {
        this.bsid = bsid;
    }

    public int getNameid() {
        return nameid;
    }

    public void setNameid(int nameid) {
        this.nameid = nameid;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ArrayList> getCsgtdc() {
        return csgtdc;
    }

    public void setCsgtdc(ArrayList<ArrayList> csgtdc) {
        this.csgtdc = csgtdc;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getBienso() {
        return bienso;
    }

    public void setBienso(String bienso) {
        this.bienso = bienso;
    }
    
}
