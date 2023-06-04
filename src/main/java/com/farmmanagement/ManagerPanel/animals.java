package com.farmmanagement.ManagerPanel;

public class animals {
    public  String animalid;
    public  String name;
    public String quantity;
    public String farmid;

    public animals(String animalid, String name, String quantity, String farmid) {
        this.animalid = animalid;
        this.name = name;
        this.quantity = quantity;
        this.farmid = farmid;
    }

    public String getAnimalid() {
        return animalid;
    }

    public void setAnimalid(String animalid) {
        this.animalid = animalid;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFarmid() {
        return farmid;
    }

    public void setFarmid(String farmid) {
        this.farmid = farmid;
    }
}
