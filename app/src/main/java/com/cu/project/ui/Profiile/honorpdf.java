package com.cu.project.ui.Profiile;

public class honorpdf {

    String Title;
    String issuer;
    String date;
    String des;

    public honorpdf(String title, String issuer, String date, String des) {
        Title = title;
        this.issuer = issuer;
        this.date = date;
        this.des = des;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
