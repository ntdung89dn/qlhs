/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttdk.bean;

import javax.annotation.ManagedBean;

/**
 *
 * @author Thorfinn
 */
public class ErrorBean implements java.io.Serializable{
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
}
