package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.combobox.ComboBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed bean.
     * @throws InterruptedException 
     * @throws IOException 
     * @throws URISyntaxException 
     */
    public MainView(@Autowired GreetService service) throws URISyntaxException, IOException, InterruptedException {
        
        Grid<Vehicle> grid = new Grid<>(Vehicle.class, false);
        grid.addColumn(Vehicle::getLicensePlate).setHeader("License Plate");
        grid.addColumn(Vehicle::getMake).setHeader("Make");
        grid.addColumn(Vehicle::getModel).setHeader("Model");
        grid.addColumn(Vehicle::getYear).setHeader("Year");
        grid.addColumn(Vehicle::getType).setHeader("Type");
        grid.addColumn(Vehicle::getAvailability).setHeader("Availability");


        ArrayList<Vehicle> vehicle = DataService.getVehicles();
        grid.setItems(vehicle);

        // Add a filter for availability, Only see the cars available or unavailable

        ComboBox<String> availabilityFilter = new ComboBox<>("Availability");
        availabilityFilter.setItems("Available", "Unavailable", "-");
        availabilityFilter.addValueChangeListener(event -> {
            String selected = event.getValue();
            if ((selected != null) && (selected != "-")) {
                grid.setItems(vehicle.stream()
                .filter(v -> (selected.equals("Available") && v.getAvailability()) || (selected.equals("Unavailable") && !v.getAvailability()))
                    .collect(Collectors.toList()));
            } else {
                grid.setItems(vehicle);
            }
        });

        // Form to add a new vehicle, conected to the endpoint of createVehicle from the backend

        TextField licensePlateField = new TextField("License Plate");
        TextField makeField = new TextField("Make");
        TextField modelField = new TextField("Model");
        TextField yearField = new TextField("Year");
        ComboBox<String> typeField = new ComboBox<>("Type");
        typeField.setItems("SUV", "Truck", "Sedan");
        ComboBox<Boolean> availabilityField = new ComboBox<>("Availability");
        availabilityField.setItems(true, false);

        Button addButton = new Button("Add Vehicle"); // Button, when clicked it adds a new vehicle
        addButton.addClickListener(e -> {
            String licensePlate = licensePlateField.getValue(); // YYY-XXX
            String make = makeField.getValue(); // Make
            String model = modelField.getValue(); // Model
            int year = Integer.parseInt(yearField.getValue()); // XXXX
            String type = typeField.getValue(); // SUV, Truck, Sedan
            boolean availability = availabilityField.getValue(); // true or false

            Vehicle newVehicle = new Vehicle(make, model, year, type, licensePlate, availability); // Create a new vehicle object
            
            // Check if all fields are filled out, if not show an error message
            if (licensePlate.isEmpty() || make.isEmpty() || model.isEmpty() || yearField.isEmpty() || type == null || availabilityField.isEmpty()) {
                // Show an error message or notification
                Notification.show("All fields must be filled out.", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                DataService.creatVehicle(newVehicle);
                vehicle.add(newVehicle);
                grid.setItems(vehicle);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add a process to delete vehicles from the grid

        TextField deleteLicensePlateField = new TextField("License Plate to Delete");
        Button deleteButton = new Button("Delete Vehicle");
        deleteButton.addClickListener(e -> {
            String licensePlateToDelete = deleteLicensePlateField.getValue();
            if (licensePlateToDelete.isEmpty()) {
                Notification.show("Please enter a license plate.", 3000, Notification.Position.MIDDLE);
                return;
            }


            try {
                boolean vehicleDeleted = DataService.deleteVehicle(licensePlateToDelete);
                if (vehicleDeleted) {
                    vehicle.removeIf(v -> v.getLicensePlate().equals(licensePlateToDelete));
                    grid.setItems(vehicle);
                    Notification.show("Vehicle deleted successfully.", 3000, Notification.Position.MIDDLE);
                } else {
                    Notification.show("Vehicle not found.", 3000, Notification.Position.MIDDLE);
                }
            } catch (IOException | InterruptedException ex) {
                Notification.show("An error occurred while deleting the vehicle: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
                ex.printStackTrace();
            }
        });

        
        // Add the ELEMENTS to the view
        
        HorizontalLayout formLayout = new HorizontalLayout(licensePlateField, makeField, modelField, yearField, typeField, availabilityField, addButton);
        add(formLayout);
        
        add(availabilityFilter, grid);

        HorizontalLayout deleteLayout = new HorizontalLayout(deleteLicensePlateField, deleteButton);
        add(deleteLayout);
    }



}
