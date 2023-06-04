package com.farmmanagement.ManagerPanel;

public class workers {
   public String workerid;
    public String name;
    public String dob;
    public int salary;
    public String gender;
    public String farmid;

    public workers(String workerid, String name, String dob, int salary, String gender, String farmid) {
        this.workerid = workerid;
        this.name = name;
        this.dob = dob;
        this.salary = salary;
        this.gender = gender;
        this.farmid = farmid;
    }

    public String getWorkerid() {
        return workerid;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public int getSalary() {
        return salary;
    }

    public String getGender() {
        return gender;
    }

    public String getFarmid() {
        return farmid;
    }

    public void setWorkerid(String workerid) {
        this.workerid = workerid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFarmid(String farmid) {
        this.farmid = farmid;
    }
}
