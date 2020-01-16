package com.cu.project.ui.Profiile;

public class Achievements  {

    String MainTitle;
    String Auth_name;
    String date;

    public Achievements()
    {}

    public Achievements(String maintitle  , String Date)
    {
        MainTitle = maintitle;
        date = Date;
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
}
