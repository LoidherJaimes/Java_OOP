package clinica_vet.model.repositories;

import clinica_vet.model.entities.Invoice;
import clinica_vet.model.entities.InvoiceItem;

import java.time.LocalDate;
import java.util.List;

public class InvoiceService {

    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    public Invoice createInvoice(String clientName) {
        Invoice invoice = new Invoice(clientName);
        repository.save(invoice);
        return invoice;
    }

    public void addItem(Invoice invoice, String name, int quantity, double price) {
        InvoiceItem item = new InvoiceItem(name, quantity, price);
        invoice.addItem(item);
    }

    public void registerPayment(Invoice invoice, String method) {
        invoice.setStatus("Pagada");
        // Simulación: solo marcamos como pagada
    }

    public List<Invoice> search(LocalDate from, LocalDate to, String client, String status) {
        return repository.search(from, to, client, status);
    }

    public List<Invoice> getAll() {
        return repository.getAll();
    }
}
