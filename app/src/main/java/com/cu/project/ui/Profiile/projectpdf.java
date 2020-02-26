package com.cu.project.ui.Profiile;

import java.util.ArrayList;

public class projectpdf {

    String Title;
    String date;
    String url;
    String des;
    ArrayList arrayListl;

    public projectpdf(String title, String date, String url, String des, ArrayList arrayListl) {
        Title = title;
        this.date = date;
        this.url = url;
        this.des = des;
        this.arrayListl = arrayListl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public ArrayList getArrayListl() {
        return arrayListl;
    }

    public void setArrayListl(ArrayList arrayListl) {
        this.arrayListl = arrayListl;
    }
}
