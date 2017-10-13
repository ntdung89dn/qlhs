/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.gsonObject;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author ntdung
 */
public class DonWrapper {
    @SerializedName("_id")
    public String _id;
    @SerializedName("thoigiandk")
    public String thoigiandk;
    @SerializedName("maonline")
    public String maonline;
    @SerializedName("mapin")
    public String mapin;
    @SerializedName("loaidon")
    public String loaidon;
    @SerializedName("bnbd")
    public List<BenNhanBaoDam> bnbd;
    @SerializedName("bbd")
    public List<BenBaoDam> bbd;
    @SerializedName("makhachhang")
    public String makhachhang;
   // public List<PhuongTien> phuongtien;
}
class BenNhanBaoDam{
    @SerializedName("bnbd.name")
    public String name;
    @SerializedName("bnbd.address")
    public String address;
}

class BenBaoDam{
    @SerializedName("bbd.name")
    public String name;
    @SerializedName("bbd.maso")
    public String maso;
}