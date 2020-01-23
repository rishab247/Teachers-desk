package com.cu.project;

public class setauthdetails {
    String name;
    String email;
    String pno;


    public setauthdetails(String name, String email , String pno)
    {
        this.name = name;
        this.email = email;
        this.pno = pno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }
}
