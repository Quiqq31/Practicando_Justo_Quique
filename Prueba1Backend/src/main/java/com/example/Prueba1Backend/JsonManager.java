package com.example.Prueba1Backend;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonManager {

    public ArrayList<Vehicle> getVehicles() throws IOException {
        // String root = System.getProperty("user.dir"); // Get the root directory of the project
        String path = "src/main/resources/vehicles.json"; // Updated path to the JSON file
        String jsonContent = new String(Files.readAllBytes(Paths.get(path))); // Read the JSON file and converts all the bytes into a string

        Gson gson = new Gson(); // Create a Gson object
        Type vehicleType = new TypeToken<ArrayList<Vehicle>>(){}.getType(); // Create a Type object that represents a list of Vehicle objects
        ArrayList<Vehicle> vehicles = gson.fromJson(jsonContent, vehicleType); // Convert the JSON content into a list of Vehicle objects
        return vehicles;
    }

    public void saveVehicles(ArrayList<Vehicle> currentVehicles) throws IOException {
        String path = "src/main/resources/vehicles.json"; // Updated path to the JSON file

        Gson gson = new Gson(); // Create a Gson object
        String jsonContent = gson.toJson(currentVehicles); // Convert the list of Vehicle objects into a JSON string
        Writer writer = Files.newBufferedWriter(Paths.get(path)); // Create a writer object and write the JSON content into the file
        writer.write(jsonContent); // Write the JSON content into the file
        writer.close(); // Close the writer
    }

    // Para ver la ruta agarrar el archivo json con el raton y arrastrar al codigo y soltar pulsando shift.
    // src/main/resources/vehicles.json
}
