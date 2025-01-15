package com.example.Prueba1Backend;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonManager {

    public ArrayList<Vehicle> getVehicles() throws IOException {
        String root = System.getProperty("user.dir"); // Get the root directory
        String path = root + "/src/main/resources/vehicles.json"; // Path to the JSON file
        String jsonContent = new String(Files.readAllBytes(Paths.get(path))); // Read the JSON file and converts all the bytes into a string

        Gson gson = new Gson(); // Create a Gson object
        Type vehicleType = new TypeToken<ArrayList<Vehicle>>(){}.getType(); // Create a Type object that represents a list of Vehicle objects
        ArrayList<Vehicle> vehicles = gson.fromJson(jsonContent, vehicleType); // Convert the JSON content into a list of Vehicle objects
        return vehicles;
    }
    
}
