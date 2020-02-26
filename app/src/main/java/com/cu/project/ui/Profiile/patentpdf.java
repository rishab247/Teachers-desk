package com.cu.project.ui.Profiile;

import java.util.ArrayList;

public class patentpdf {

    String title;
    String office;
    String appno;
    String date;
    String des;
    String url;
    ArrayList arrayList;

    public patentpdf(String Title , String office , String appno , String date , String des , String url , ArrayList arrayList) {

        this.title = Title;
        this.office = office;
        this.appno = appno;
        this.date = date;
        this.des = des;
        this.url = url;
        this.arrayList = arrayList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }
}
