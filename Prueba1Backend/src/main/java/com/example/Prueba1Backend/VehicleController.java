package com.example.Prueba1Backend;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class VehicleController {

    // get all vehicles
    @GetMapping("/vehicles")
    public ArrayList<Vehicle> getVehicles() throws IOException {
        JsonManager jsonManager =  new JsonManager(); // Create a JsonManager object
        ArrayList<Vehicle> vehicleList = jsonManager.getVehicles(); // Call the getVehicles method from the JsonManager object. Returns a list of all the Vehicle objects read from the json file
        return vehicleList;
    }

    // create new vehicles
    @PostMapping("/vehicles")
    public ArrayList<Vehicle> createVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        JsonManager jsonManager =  new JsonManager(); // Create a JsonManager object
        ArrayList<Vehicle> vehicleList = jsonManager.getVehicles(); // Call the getVehicles method from the JsonManager object. Returns a list of all the Vehicle objects read from the json file
        
        // Para probar sin frontend
        // newVehicle.setLicensePlate("222"); // Set a new license plate for the new vehicle
        // newVehicle.setMake("Toyota"); // Set a new make for the new vehicle
        // newVehicle.setModel("Yaris GR"); // Set a new model for the new vehicle
        // newVehicle.setYear(2024); // Set a new year for the new vehicle
        // newVehicle.setType("Sedan"); // Set a new vehicle type for the new vehicle
        // newVehicle.setAvailability(true); // Set the availability of the new vehicle
        // Para probar sin frontend
        
        vehicleList.add(newVehicle); // Add the new vehicle to the list
        jsonManager.saveVehicles(vehicleList); // Call the saveVehicles method from the JsonManager object to save the updated list of vehicles to the json file
        return vehicleList;
    }


    // delete vehicles
    @DeleteMapping("/vehicles/{licensePlate}")
    public boolean deleteVehicle(@PathVariable String licensePlate) throws IOException {
        JsonManager manager = new JsonManager(); // Create a JsonManager object
        ArrayList<Vehicle> vehicleList = manager.getVehicles(); // Call the getVehicles method from the JsonManager object. Returns a list of all the Vehicle objects read from the json file
        ArrayList<Vehicle> newVehicleList = new ArrayList<>(); // Create a new list to store the vehicles that are not going to be deleted
        ArrayList<Vehicle> errasedVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicleList){
            if(!vehicle.getLicensePlate().equals(licensePlate)){ // Check if the license plate of the vehicle is different from the one to be deleted
                newVehicleList.add(vehicle); // Add the vehicle to the new list
            } // By doing this we manage to exclude the vehicles that are not the one we want to delete and add them to a new list, that will be saved later as the updated list of vehicles
            else{
                errasedVehicles.add(vehicle);
            }
        }

        manager.saveVehicles(newVehicleList); // Call the saveVehicles method from the JsonManager object to save the updated list of vehicles to the json file

        if(errasedVehicles.size() == 0){
            return false;
        }else{
            return true; // Return a message indicating that the vehicle was deleted
        }
    }
}
