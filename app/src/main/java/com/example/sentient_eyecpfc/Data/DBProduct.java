package com.example.sentient_eyecpfc.Data;

import java.util.Date;
//TODO change String mDate to Date mDate;
public class DBProduct extends Product {
    private String mDate;
    private Double mDose;
    public DBProduct(String date, Double dose, String code, String name, Double calories, Double proteins, Double carbohydrates, Double fats) {
        super(code,name,calories,proteins,carbohydrates,fats);
        this.mDate = date;
        this.mDose = dose;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public Double getmDose() {
        return mDose;
    }

    public void setmDose(Double mDose) {
        this.mDose = mDose;
    }
}
