package clinica_vet.model.entities;

public class InvoiceItem {

    private String name;
    private int quantity;
    private double price;
    private double subtotal;

    public InvoiceItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = quantity * price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getSubtotal() {
        return subtotal;
    }
    
    @Override
    public String toString() {
        return String.format("%s x%d @ $%.2f = $%.2f", 
            name, quantity, price, subtotal);
    }
}