package com.cu.project.ui.Profiile;

public class SubjectData {

    private String title;
    private String date;
    private int id;

    public SubjectData(String title, String date, int id) {
        this.title = title;
        this.date = date;
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }
}
