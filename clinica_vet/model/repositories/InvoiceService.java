package clinica_vet.model.repositories;

import clinica_vet.model.entities.Invoice;
import clinica_vet.model.entities.InvoiceItem;
import clinica_vet.model.entities.Owner;
import java.time.LocalDate;
import java.util.List;

public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice createInvoice(Owner owner, List<InvoiceItem> items) {
        Invoice invoice = new Invoice(owner);
        
        for (InvoiceItem i : items) {
            invoice.addItem(i);
        }
        
        invoiceRepository.addInvoice(invoice);
        return invoice;
    }

    public void registerPayment(int invoiceId, String method) {
        List<Invoice> invoices = invoiceRepository.getAll();
        for (Invoice invoice : invoices) {
            if (invoice.getId() == invoiceId) {
                invoice.markAsPaid();
                break;
            }
        }
    }

    public List<Invoice> searchInvoices(LocalDate from, LocalDate to, String clientFilter, String statusFilter) {
        return invoiceRepository.search(from, to, clientFilter, statusFilter);
    }
    
    public List<Invoice> getByClient(String name) {
        return invoiceRepository.findByClient(name);
    }

    public List<Invoice> getByStatus(String status) {
        return invoiceRepository.findByStatus(status);
    }

    public List<Invoice> getByDateRange(LocalDate start, LocalDate end) {
        return invoiceRepository.findByDateRange(start, end);
    }

    public List<Invoice> getAll() {
        return invoiceRepository.getAll();
    }
    
    public Invoice getInvoiceById(int id) {
        List<Invoice> invoices = invoiceRepository.getAll();
        for (Invoice invoice : invoices) {
            if (invoice.getId() == id) {
                return invoice;
            }
        }
        return null;
    }
}