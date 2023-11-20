class Order {
    private String customerName;
    private int quantity;

    public Order(String customerName, int quantity) {
        this.customerName = customerName;
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Order: Customer=" + customerName + ", Quantity=" + quantity;
    }
}