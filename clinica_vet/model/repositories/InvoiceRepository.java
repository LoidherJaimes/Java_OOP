package clinica_vet.model.repositories;

import clinica_vet.model.entities.Invoice;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceRepository {

    private final List<Invoice> invoices = new ArrayList<>();

    public void save(Invoice invoice) {
        invoices.add(invoice);
    }

    public List<Invoice> getAll() {
        return invoices;
    }

    public List<Invoice> search(LocalDate from, LocalDate to, String client, String status) {

        return invoices.stream()
                .filter(i -> (from == null || !i.getDate().toLocalDate().isBefore(from)))
                .filter(i -> (to == null || !i.getDate().toLocalDate().isAfter(to)))
                .filter(i -> (client == null || client.isEmpty() || i.getClientName().toLowerCase().contains(client.toLowerCase())))
                .filter(i -> (status.equals("Todos") || i.getStatus().equals(status)))
                .collect(Collectors.toList());
    }
}
