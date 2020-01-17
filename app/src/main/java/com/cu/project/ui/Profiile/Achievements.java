package com.cu.project.ui.Profiile;

public class Achievements  {

    String MainTitle;
    String type;
    String date;

    public Achievements()
    {}

    public Achievements(String maintitle  , String Date , String type)
    {
        MainTitle = maintitle;
        date = Date;
        this.type = type;
    }

    public String getMainTitle() {
        return MainTitle;
    }

    public void setMainTitle(String mainTitle) {
        MainTitle = mainTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
