package com.example.Prueba1Backend;

public class Vehicle {
    
    private String make;
    private String model;
    private int yearOfManufacture;
    private String vehicleType;
    private String licensePlateNumber;

    public Vehicle(String make, String model, int yearOfManufacture, String vehicleType, String licensePlateNumber) {
        this.make = make;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
        this.vehicleType = vehicleType;
        this.licensePlateNumber = licensePlateNumber;
    }

    // Getters and Setters
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }
}
