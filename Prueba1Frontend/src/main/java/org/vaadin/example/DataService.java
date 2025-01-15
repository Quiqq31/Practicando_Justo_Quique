package org.vaadin.example;

import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.io.IOException;


import java.util.ArrayList;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class DataService {

    private static final String BACKEND_URL = "http://localhost:8080/vehicles";

    public static ArrayList<Vehicle> getVehicles() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(BACKEND_URL))
            .GET()
            .build();
    
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        Gson gson = new Gson();
        Type vehicleListType = new TypeToken<ArrayList<Vehicle>>(){}.getType();

        ArrayList<Vehicle> vehicles = gson.fromJson(responseBody, vehicleListType);
        return vehicles;
    }

    public static Vehicle creatVehicle(Vehicle vehicle) throws IOException, InterruptedException{
        Gson gson = new Gson();
        String body = gson.toJson(vehicle);
    
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BACKEND_URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
    
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    
        return vehicle;
    }

    public static boolean deleteVehicle(String licensePlate) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BACKEND_URL + "/" + licensePlate))
            .DELETE()
            .build();
    
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        return Boolean.parseBoolean(response.body());
    }
}
