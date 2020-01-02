package com.cu.project.ui.Profiile;

public class Achievements  {

    String MainTitle;
    String Auth_name;
    String date;

    public Achievements()
    {}

    public Achievements(String maintitle , String auth_name , String Date)
    {
        MainTitle = maintitle;
        Auth_name = auth_name;
        date = Date;
    }

    public String getMainTitle() {
        return MainTitle;
    }

    public void setMainTitle(String mainTitle) {
        MainTitle = mainTitle;
    }

    public String getAuth_name() {
        return Auth_name;
    }

    public void setAuth_name(String auth_name) {
        Auth_name = auth_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
