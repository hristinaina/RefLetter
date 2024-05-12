package com.ftn.sbnz.model.models;

public class Car {
    private int carId;
    private double temperature;
    private double pritisak;
    private double oilLevel;

    public Car() {
    }


    public Car(int carId, double temperature, double pritisak, double oilLevel) {
        this.carId = carId;
        this.temperature = temperature;
        this.pritisak = pritisak;
        this.oilLevel = oilLevel;
    }


    public int getCarId() {
        return this.carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPritisak() {
        return this.pritisak;
    }

    public void setPritisak(double pritisak) {
        this.pritisak = pritisak;
    }

    public double getOilLevel() {
        return this.oilLevel;
    }

    public void setOilLevel(double oilLevel) {
        this.oilLevel = oilLevel;
    }

}
