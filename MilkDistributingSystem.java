import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class MilkDistributingSystem {
    public static void main(String[] args) {
        // Initialize houses
        List<House> houses = new ArrayList<>();
        houses.add(new House("House1"));
        houses.add(new House("House2"));
        houses.add(new House("House3"));

        // Initialize milk product
        Milk milk = new Milk(2.5); // Set price per liter

        // Initialize Milkman
        Milkman<Milk> milkman = new Milkman<>("MilkMan1", houses, milk);

        // Create orders
        List<Order> orders = new ArrayList<>();
        orders.add(houses.get(0).placeOrder(2));
        orders.add(houses.get(1).placeOrder(3));
        orders.add(houses.get(2).placeOrder(1));

        // Create Billing System
        BillingSystem billingSystem = new BillingSystem();

        // Add orders to the billing system
        for (Order order : orders) {
            billingSystem.addOrder(order);
        }

        // Update the bill
        double totalRevenue = billingSystem.updateBill(milkman);

        // Save orders to a file
        saveOrdersToFile(orders, "orders.txt");

        // Save milkman and product information to a file
        saveToFile(milkman, "milkman.txt");
        saveToFile(milk, "milk.txt");

        // Print the milkman and product information
        System.out.println("\nMilkman Information:\n" + readFromFile("milkman.txt"));
        System.out.println("\nMilk Product Information:\n" + readFromFile("milk.txt"));
    }

    // Function to save orders to a file
    private static void saveOrdersToFile(List<Order> orders, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Order order : orders) {
                writer.write(order.getCustomerName() + "," + order.getQuantity());
                writer.newLine();
            }
            System.out.println("Orders saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to save an object to a file
    private static <T extends Serializable> void saveToFile(T object, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(object);
            System.out.println(object.getClass().getSimpleName() + " saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to read an object from a file
    private static String readFromFile(String filename) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
