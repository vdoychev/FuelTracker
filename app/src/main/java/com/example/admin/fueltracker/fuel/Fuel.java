package com.example.admin.fueltracker.fuel;

public class Fuel {

    private int id;

    private int carId;

    private Double liters;

    private Double kilometers;

    private Double pricePerKilometer;

    private String gasStation;

    private String date;

    public Fuel() {
    }

    public Fuel(int carId, Double liters, Double kilometers, Double pricePerKilometer, String gasStation, String date) {
        this.carId = carId;
        this.liters = liters;
        this.kilometers = kilometers;
        this.pricePerKilometer = pricePerKilometer;
        this.gasStation = gasStation;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Double getLiters() {
        return liters;
    }

    public void setLiters(Double liters) {
        this.liters = liters;
    }

    public Double getKilometers() {
        return kilometers;
    }

    public void setKilometers(Double kilometers) {
        this.kilometers = kilometers;
    }

    public Double getPricePerKilometer() {
        return pricePerKilometer;
    }

    public void setPricePerKilometer(Double pricePerKilometer) {
        this.pricePerKilometer = pricePerKilometer;
    }

    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getCarId() + ", "
             + this.getLiters() + ", "
             + this.getKilometers() + ", "
             + this.getPricePerKilometer() + ", "
             + this.getGasStation() + ", "
             + this.getDate() + "\n";
    }

}
