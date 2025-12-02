package clinica_vet.model.repositories;

import clinica_vet.model.entities.Invoice;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceRepository {

    private final List<Invoice> invoices = new ArrayList<>();

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    public List<Invoice> getAll() {
        return new ArrayList<>(invoices);
    }

    public List<Invoice> findByClient(String name) {
        return invoices.stream()
                .filter(i -> i.getOwner() != null && 
                           i.getOwner().getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Invoice> findByStatus(String status) {
        return invoices.stream()
                .filter(i -> i.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public List<Invoice> findByDateRange(LocalDate start, LocalDate end) {
        return invoices.stream()
                .filter(i -> {
                    LocalDate invoiceDate = i.getDate(); 
                    return invoiceDate != null && 
                           !invoiceDate.isBefore(start) && 
                           !invoiceDate.isAfter(end);
                })
                .collect(Collectors.toList());
    }
    
    public List<Invoice> search(LocalDate from, LocalDate to, String clientFilter, String statusFilter) {
        return invoices.stream()
            .filter(i -> {
                // Filtro por cliente
                boolean clientMatch = clientFilter == null || clientFilter.isEmpty() || 
                                      (i.getOwner() != null && 
                                       i.getOwner().getName().toLowerCase()
                                        .contains(clientFilter.toLowerCase()));
                
                // Filtro por estado
                boolean statusMatch = statusFilter == null || 
                                     statusFilter.equalsIgnoreCase("Todos") || 
                                     i.getStatus().equalsIgnoreCase(statusFilter);

                // Filtro por fecha
                LocalDate invoiceDate = i.getDate();
                boolean dateMatch = true;
                
                if (invoiceDate == null) return false;
                
                if (from != null) {
                    dateMatch = !invoiceDate.isBefore(from);
                }
                if (to != null) {
                    dateMatch = dateMatch && !invoiceDate.isAfter(to);
                }
                
                return clientMatch && statusMatch && dateMatch;
            })
            .collect(Collectors.toList());
    }
}