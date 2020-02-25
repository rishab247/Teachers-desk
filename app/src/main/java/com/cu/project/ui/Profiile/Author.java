package com.cu.project.ui.Profiile;

public class Author {


    String name;
    String email;
    String pno;
    public Author(String name , String mail , String pno) {
        this.name = name;
        this.email = mail;
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
