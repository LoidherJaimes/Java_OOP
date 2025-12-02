package clinica_vet.model.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private static int idCounter = 1;

    private int id;
    private Owner owner;
    private LocalDate date;
    private List<InvoiceItem> items;
    private double total;
    private String status; // "Pendiente" o "Pagada"

    public Invoice(Owner owner) {
        this.id = idCounter++;
        this.owner = owner;
        this.date = LocalDate.now();
        this.items = new ArrayList<>();
        this.status = "Pendiente";
    }

    public void addItem(InvoiceItem item) {
        items.add(item);
        calculateTotal();
    }

    private void calculateTotal() {
        total = items.stream().mapToDouble(InvoiceItem::getSubtotal).sum();
    }

    public int getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public void markAsPaid() {
        this.status = "Pagada";
    }
}
