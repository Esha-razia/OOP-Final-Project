abstract class Product implements Sellable {
    private String name;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public abstract double getPrice();

    @Override
    public String toString() {
        return name + " - Price: $" + getPrice();
    }
}

