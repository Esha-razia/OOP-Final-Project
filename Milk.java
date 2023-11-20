import java.io.Serializable;
class Milk extends Product implements Sellable, Serializable{
    private double pricePerLiter;

    public Milk(double pricePerLiter) {
        super("Milk");
        this.pricePerLiter = pricePerLiter;
    }

    @Override
    public double getPrice() {
        return pricePerLiter;
    }

    @Override
    public String toString() {
        return "Milk - Price per Liter: $" + pricePerLiter;
    }
}
