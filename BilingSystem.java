import java.util.ArrayList;
import java.util.List;
class BillingSystem {
    private List<Order> orders;

    public BillingSystem() {
        this.orders = new ArrayList<>();
    }

    // Function to add an order to the billing system
    public void addOrder(Order order) {
        orders.add(order);
        System.out.println("Order added to the billing system: " + order);
    }

    // Function to remove an order from the billing system
    public void removeOrder(Order order) {
        if (orders.remove(order)) {
            System.out.println("Order removed from the billing system: " + order);
        } else {
            System.out.println("Order not found in the billing system: " + order);
        }
    }

    // Function to update the bill
    public double updateBill(Milkman<?> milkman) {
        System.out.println("Updating the bill for " + milkman);
        double totalRevenue = milkman.processOrders(orders);
        System.out.println("Total revenue for the day: $" + totalRevenue);
        return totalRevenue;
    }
}
