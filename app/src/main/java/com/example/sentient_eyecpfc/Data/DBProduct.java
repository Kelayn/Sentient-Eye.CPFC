package com.example.sentient_eyecpfc.Data;

import java.util.Date;
//TODO change String mDate to Date mDate;
public class DBProduct extends Product {
    private Date mDate;
    private Double mDose;
    public DBProduct(Date date, Double dose, String code, String name, Double calories, Double proteins, Double carbohydrates, Double fats) {
        super(code,name,calories,proteins,carbohydrates,fats);
        this.mDate = date;
        this.mDose = dose;
    }

    public Date getmDate() { return mDate; }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public Double getmDose() {
        return mDose;
    }

    public void setmDose(Double mDose) {
        this.mDose = mDose;
    }
}
