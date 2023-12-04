package com.example.guiproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MilkDistributionSystem extends Application {

    private static final String USER_DATA_FILE = "userdata.txt";
    private static final String ORDERS_FILE = "orders.txt";

    private static final String ALLOWED_USERNAME_DEFAULT = "aroojkhan@gmail.com";
    private static final String ALLOWED_PASSWORD_DEFAULT = "1244";

    private String allowedUsername = ALLOWED_USERNAME_DEFAULT;
    private String allowedPassword = ALLOWED_PASSWORD_DEFAULT;

    private BorderPane root;
    private Stage primaryStage;
    private Milkman<Milk> milkman;
    private BilingSystem billingSystem;
    private TextArea outputTextArea;
    private Label errorLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Milk Distribution System");


        loadUserDataFromFile();

    
        loadOrdersFromFile();

    
        milkman = new Milkman<>(new Milk(2)); 

        
        createLoginPage();
    }

    private void loadOrdersFromFile() {
        try (Scanner scanner = new Scanner(new File(ORDERS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Order order = parseOrder(line);
                if (order != null) {
                    billingSystem.addOrder(order);
                } else {
                    System.err.println("Error parsing order from line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Orders file not found. Creating a new file.");
        }
    }

    private void createLoginPage() {
        VBox loginBox = new VBox(10);
        loginBox.setPadding(new Insets(10));

        Label loginLabel = new Label("Login");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        errorLabel = new Label(); 

        
        loginButton.setOnAction(e -> {
            if (checkLoginCredentials(usernameField.getText(), passwordField.getText())) {
                try {
                    showMainPage();
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                errorLabel.setText("Invalid username or password. Please try again.");
            }
        });

        
        loginBox.getChildren().addAll(loginLabel, new Label("Username:"), usernameField,
                new Label("Password:"), passwordField, loginButton, errorLabel);

        Scene loginScene = new Scene(loginBox, 400, 300);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showMainPage() throws MalformedURLException {
        root = new BorderPane();

    
        List<House> houses = new ArrayList<>();
        houses.add(new House("House1", "Ali Town"));
        houses.add(new House("House2", "Johar Town"));
        houses.add(new House("House3", "Iqbal Town"));


        Milk milk = new Milk(2); 

        
        List<Milkman<Milk>> milkmen = new ArrayList<>();
        milkmen.add(new Milkman<>("Ali", houses, milk));
        milkmen.add(new Milkman<>("Ahmad", houses, milk));
        milkmen.add(new Milkman<>("Ehtsham", houses, milk));
        milkmen.add(new Milkman<>("Abdullah", houses, milk));
        milkmen.add(new Milkman<>("Owais", houses, milk));

    
        billingSystem = new BilingSystem();

        VBox centerBox = createCenterBox();
        root.setCenter(centerBox);

        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setWrapText(true);
        root.setBottom(outputTextArea)
            
        File file = new File("C:\\Users\\ah\\Desktop\\Data\\GUIPROJECT\\pic2.jpg");
        String localUrl = file.toURI().toURL().toString();
        ImageView imageView = new ImageView(new Image(localUrl));
        imageView.setFitWidth(200); 
        imageView.setPreserveRatio(true);
        root.setRight(imageView); 

        Scene mainScene = new Scene(root, 800, 600);
        primaryStage.setScene(mainScene);
    }


    private VBox createCenterBox() {
        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(10));

        
        Button addOrderButton = new Button("Add Order");
        Button removeOrderButton = new Button("Remove Order");
        Button calculateRevenueButton = new Button("Calculate Revenue");
        Button addHouseButton = new Button("Add House");
        Button saveOrdersButton = new Button("Save Orders");
        Button exitButton = new Button("Exit");
        Button nextButton = new Button("Next");

        
        addOrderButton.setOnAction(e -> handleAddOrder());
        removeOrderButton.setOnAction(e -> handleRemoveOrder());
        calculateRevenueButton.setOnAction(e -> handleCalculateRevenue());
        addHouseButton.setOnAction(e -> handleAddHouse());
        saveOrdersButton.setOnAction(e -> saveOrdersToFile());
        exitButton.setOnAction(e -> primaryStage.close());


        
        centerBox.getChildren().addAll(addOrderButton, removeOrderButton, calculateRevenueButton, addHouseButton, saveOrdersButton, exitButton);

        return centerBox;
    }


    private void handleAddOrder() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Order");
        dialog.setHeaderText("Enter customer details");
        dialog.setContentText("Customer Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(customerName -> {
            int quantity = getQuantityFromUser();
            Milk milkObject = new Milk(2);
            Order newOrder = new Order(customerName, quantity, milkObject);
            billingSystem.addOrder(newOrder);
            showOutput("Order added: " + newOrder);
        });
    }

    private void handleRemoveOrder() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove Order");
        dialog.setHeaderText("Enter customer name to remove order");
        dialog.setContentText("Customer Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(customerName -> {
            Order orderToRemove = billingSystem.getOrderByName(customerName);
            if (orderToRemove != null) {
                billingSystem.removeOrder(orderToRemove);
                showOutput("Order removed: " + orderToRemove);
            } else {
                showOutput("Order not found for customer: " + customerName);
            }
        });
    }

    private void handleCalculateRevenue() {
        double totalRevenue = billingSystem.calculateRevenue();
        showOutput("Total Revenue: $" + totalRevenue);
    }


    private void handleAddHouse() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add House");
        dialog.setHeaderText("Enter house details");
        dialog.setContentText("Owner Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(ownerName -> {
            String address = getAddressFromUser();
            House newHouse = new House(ownerName, address);
            milkman.addHouse(newHouse);
            showOutput("House added: " + newHouse);
        });
    }

    private int getQuantityFromUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Quantity");
        dialog.setHeaderText("Enter quantity of milk:");

        Optional<String> result = dialog.showAndWait();
        return result.map(s -> {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                showOutput("Invalid quantity. Please enter a valid number.");
                return 0;
            }
        }).orElse(0);
    }

    private String getAddressFromUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Address");
        dialog.setHeaderText("Enter address of the house:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    private boolean checkLoginCredentials(String username, String password) {
        return username.equals(allowedUsername) && password.equals(allowedPassword);
    }

    private void showOutput(String message) {
        if (outputTextArea != null) {
            outputTextArea.appendText(message + "\n");
        } else {
            System.err.println("OutputTextArea is null. Cannot show message: " + message);
        }
    }

    private void loadUserDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            if ((line = reader.readLine()) != null) {
                allowedUsername = line.trim();
            }
            if ((line = reader.readLine()) != null) {
                allowedPassword = line.trim();
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        
        saveUserDataToFile();
        
        saveOrdersToFile();
    }

    private void saveUserDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            writer.write(allowedUsername);
            writer.newLine();
            writer.write(allowedPassword);
        } catch (IOException e) {
            System.err.println("Error saving user data to file: " + e.getMessage());
        }
    }

    private void saveOrdersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ORDERS_FILE))) {
            for (Order order : billingSystem.getOrders()) {
                writer.println(formatOrder(order));
            }
        } catch (IOException e) {
            System.err.println("Error saving orders to file: " + e.getMessage());
        }
    }

    private String formatOrder(Order order) {
        return order.getCustomerName() + "," + order.getQuantity();
    }

    private Order parseOrder(String line) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            String customerName = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            Milk milkObject = new Milk(2); 

        
            return new Order(customerName, quantity, milkObject);

        } else {
        
            return null;
        }
    }
}
