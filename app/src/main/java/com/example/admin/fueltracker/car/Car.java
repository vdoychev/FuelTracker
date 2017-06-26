package com.example.admin.fueltracker.car;

public class Car {

    private int id;

    private String brand;

    private String mark;

    private String registrationNumber;

    private String fuelType;

    public Car() {
    }
    public Car(int id ,String brand, String mark, String registrationNumber, String fuelType) {
        this.id = id;
        this.brand = brand;
        this.mark = mark;
        this.registrationNumber = registrationNumber;
        this.fuelType = fuelType;
    }


    public Car(String brand, String mark, String registrationNumber, String fuelType) {
        this.brand = brand;
        this.mark = mark;
        this.registrationNumber = registrationNumber;
        this.fuelType = fuelType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return this.getId() + ", "
             + this.getBrand() + ", "
             + this.getMark() + ", "
             + this.getRegistrationNumber() + ", "
             + this.getFuelType() + "\n";
    }

}
