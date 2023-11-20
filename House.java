import java.io.Serializable;
class House implements Serializable  {
    private String ownerName;

    public House(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    // Function to place an order for a product
    public Order placeOrder(int quantity) {
        return new Order(ownerName, quantity);
    }

    @Override
    public String toString() {
        return "House: Owner=" + ownerName;
    }
}
