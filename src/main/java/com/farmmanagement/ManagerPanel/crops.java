package com.farmmanagement.ManagerPanel;

public class crops {
    public String cropsid;
    public String cropsname;
    public String farmid;
    public String size;

    public String getCropsid() {
        return cropsid;
    }

    public void setCropsid(String cropsid) {
        this.cropsid = cropsid;
    }

    public String getCropsname() {
        return cropsname;
    }

    public void setCropsname(String cropsname) {
        this.cropsname = cropsname;
    }

    public String getFarmid() {
        return farmid;
    }

    public void setFarmid(String farmidid) {
        this.farmid = farmidid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public crops(String cropsid, String cropsname, String farmid, String size) {
        this.cropsid = cropsid;
        this.cropsname = cropsname;
        this.farmid = farmid;
        this.size = size;
    }
}
