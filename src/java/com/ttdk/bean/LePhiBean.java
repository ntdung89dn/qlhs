/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import java.sql.Date;

/**
 *
 * @author Thorfinn
 */
public class LePhiBean {
    private int lpid;
    private int ldID;
    private int lpPrice;
    private Date beginDate;
    private Date endDate;

    public int getLpid() {
        return lpid;
    }

    public void setLpid(int lpid) {
        this.lpid = lpid;
    }

    public int getLdID() {
        return ldID;
    }

    public void setLdID(int ldID) {
        this.ldID = ldID;
    }

    public int getLpPrice() {
        return lpPrice;
    }

    public void setLpPrice(int lpPrice) {
        this.lpPrice = lpPrice;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
    
}
