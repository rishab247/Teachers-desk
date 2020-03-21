package com.cu.project;

public class SubjectDataHod {

    String eid;
    String name;
    Boolean verify;

    public SubjectDataHod(String eid, String name, Boolean verify) {
        this.eid = eid;
        this.name = name;
        this.verify = verify;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }
}
