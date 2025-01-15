package com.example.Prueba1Backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class JsonManager {

    private static final String FILE_PATH = "src/main/resources/vehicles.json"; // not sure if the path is correct, in case of error take it into account for later
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Vehicle> getVehicles() throws IOException {
        return objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Vehicle>>() {});
    }

    public void saveVehicles(List<Vehicle> vehicles) throws IOException {
        objectMapper.writeValue(new File(FILE_PATH), vehicles);
    }
}
