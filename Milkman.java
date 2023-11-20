import java.util.List;
import java.io.Serializable;
class Milkman<T extends Product>implements Serializable  {
    private String name;
    private List<House> houses;
    private T product;

    public Milkman(String name, List<House> houses, T product) {
        this.name = name;
        this.houses = houses;
        this.product = product;
    }

    // Function to process orders and calculate total revenue
    public double processOrders(List<Order> orders) {
        double totalRevenue = 0;

        for (Order order : orders) {
            System.out.println("Processing order for " + order.getQuantity() +
                    " units to " + order.getCustomerName());
            totalRevenue += order.getQuantity() * product.getPrice();
        }

        return totalRevenue;
    }

    @Override
    public String toString() {
        return "Milkman: " + name;
    }
}
