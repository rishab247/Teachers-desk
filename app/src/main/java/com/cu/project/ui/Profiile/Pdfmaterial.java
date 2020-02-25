package com.cu.project.ui.Profiile;

public class Pdfmaterial {

    String title;
    String publisher;
    String date;
    String Des;
    String URL;

    public Pdfmaterial(String Title , String publisher , String date , String Des , String url) {
        this.title = Title;
        this.publisher = publisher;
        this.date = date;
        this.Des = Des;
        this.URL = url;

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
