package com.cet325.bg69mm.model;

import java.util.Date;

/**
 * Created by jamessunley on 04/01/2018.
 */

//converter class
public class Converter {

    //create variables
    public Rates rates = new Rates();
    private String base;
    private Date date;

    //getters and setters for the conversion rates to be set when being read from fixer.io
    public String getBase(){
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }

    public Date getDate(){
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
