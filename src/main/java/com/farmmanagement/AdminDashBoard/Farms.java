package com.farmmanagement.AdminDashBoard;

public class Farms {

    String farmid;
    int mgrid;
    String landsize;
    String location;

    public Farms(String farmid, int mgrid, String landsize, String location) {
        this.farmid = farmid;
        this.mgrid = mgrid;
        this.landsize = landsize;
        this.location = location;
    }

    public String getFarmid() {
        return farmid;
    }

    public void setFarmid(String farmid) {
        this.farmid = farmid;
    }

    public int getMgrid() {
        return mgrid;
    }

    public void setMgrid(int mgrid) {
        this.mgrid = mgrid;
    }

    public String getLandsize() {
        return landsize;
    }

    public void setLandsize(String landsize) {
        this.landsize = landsize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
