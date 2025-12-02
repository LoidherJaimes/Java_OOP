package clinica_vet.controllers;

import clinica_vet.model.entities.Invoice;
import clinica_vet.model.entities.InvoiceItem;
import clinica_vet.model.entities.Owner;
import clinica_vet.model.repositories.InvoiceService;
import clinica_vet.model.repositories.OwnerRepository;
import clinica_vet.views.BillingAndPaymentsView;
import clinica_vet.views.MainWindowView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class BillingAndPaymentsController {
    
    private final BillingAndPaymentsView view;
    private final MainWindowView mainWindowView;
    private final InvoiceService invoiceService;
    private final OwnerRepository ownerRepository;
    
    private List<InvoiceItem> currentItems = new ArrayList<>();
    private DefaultTableModel itemsTableModel;
    private DefaultTableModel invoicesTableModel;
    
    public BillingAndPaymentsController(BillingAndPaymentsView view, 
                                        MainWindowView mainWindowView,
                                        InvoiceService invoiceService,
                                        OwnerRepository ownerRepository) {
        this.view = view;
        this.mainWindowView = mainWindowView;
        this.invoiceService = invoiceService;
        this.ownerRepository = ownerRepository;
        
        setupInitialData();
        setupListeners();
    }
    
    private void setupInitialData() {
        // Configurar tablas
        itemsTableModel = (DefaultTableModel) view.itemsTable.getModel();
        invoicesTableModel = (DefaultTableModel) view.invoicesTable.getModel();
        
        // Cargar clientes en el combo
        loadClients();
        
        // Cargar facturas existentes
        loadAllInvoices();
        
        // Actualizar total inicial
        updateTotal();
        
        // Configurar selección de tabla para pagos
        setupTableSelection();
    }
    
    private void loadClients() {
        view.clientComboBox.removeAllItems();
        ownerRepository.getAllOwners().forEach(owner -> {
            view.clientComboBox.addItem(owner.getName());
        });
    }
    
    private void loadAllInvoices() {
        invoicesTableModel.setRowCount(0);
        List<Invoice> invoices = invoiceService.getAll();
        for (Invoice inv : invoices) {
            invoicesTableModel.addRow(new Object[]{
                inv.getId(),
                inv.getOwner() != null ? inv.getOwner().getName() : "N/A",
                inv.getDate(),
                String.format("%.2f", inv.getTotal()),
                inv.getStatus()
            });
        }
    }
    
    private void setupListeners() {
        // Botón para agregar ítem
        view.addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        
        // Botón para generar factura
        view.generateInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });
        
        // Botón para registrar pago
        view.registerPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerPayment();
            }
        });
        
        // Botón para buscar facturas
        view.searchInvoicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchInvoices();
            }
        });
        
        // Enter en los campos de ítem
        view.priceField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
    }
    
    private void setupTableSelection() {
        // Permitir selección simple
        view.invoicesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void addItem() {
        try {
            String name = view.itemNameField.getText().trim();
            String quantityText = view.quantityField.getText().trim();
            String priceText = view.priceField.getText().trim();
            
            if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(view, 
                    "Por favor complete todos los campos", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);
            
            if (name.isEmpty() || quantity <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(view, 
                    "Por favor ingrese valores válidos", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            InvoiceItem item = new InvoiceItem(name, quantity, price);
            currentItems.add(item);
            
            // Agregar a la tabla
            itemsTableModel.addRow(new Object[]{
                name, quantity, price, item.getSubtotal()
            });
            
            // Limpiar campos
            view.itemNameField.setText("");
            view.quantityField.setText("");
            view.priceField.setText("");
            
            // Enfocar primer campo
            view.itemNameField.requestFocus();
            
            // Actualizar total
            updateTotal();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, 
                "Cantidad y Precio deben ser números válidos", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTotal() {
        double total = currentItems.stream()
            .mapToDouble(InvoiceItem::getSubtotal)
            .sum();
        view.totalLabel.setText(String.format("$%.2f", total));
    }
    
    private void generateInvoice() {
        if (view.clientComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(view, 
                "Seleccione un cliente", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (currentItems.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Agregue al menos un ítem", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String clientName = (String) view.clientComboBox.getSelectedItem();
            Owner owner = ownerRepository.getAllOwners().stream()
                .filter(o -> o.getName().equals(clientName))
                .findFirst()
                .orElse(null);
            
            if (owner == null) {
                JOptionPane.showMessageDialog(view, 
                    "Cliente no encontrado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear una copia de los items
            List<InvoiceItem> itemsToInvoice = new ArrayList<>(currentItems);
            Invoice invoice = invoiceService.createInvoice(owner, itemsToInvoice);
            
            JOptionPane.showMessageDialog(view, 
                "Factura #" + invoice.getId() + " creada exitosamente\n" +
                "Fecha: " + invoice.getDate() + "\n" +
                "Cliente: " + invoice.getOwner().getName() + "\n" +
                "Total: $" + String.format("%.2f", invoice.getTotal()) + "\n" +
                "Estado: " + invoice.getStatus(),
                "Factura Generada",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar para nueva factura
            currentItems.clear();
            itemsTableModel.setRowCount(0);
            updateTotal();
            
            // Recargar lista de facturas
            loadAllInvoices();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "❌ Error al crear factura: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void registerPayment() {
        int selectedRow = view.invoicesTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, 
                "Seleccione una factura de la tabla para registrar el pago",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int invoiceId = (int) view.invoicesTable.getValueAt(selectedRow, 0);
            String status = (String) view.invoicesTable.getValueAt(selectedRow, 4);
            
            if ("Pagada".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(view, 
                    "Esta factura ya está pagada",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Confirmar pago
            int confirm = JOptionPane.showConfirmDialog(view,
                "¿Registrar pago para la factura #" + invoiceId + "?\n" +
                "Método: " + view.paymentMethodBox.getSelectedItem(),
                "Confirmar Pago",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                String method = (String) view.paymentMethodBox.getSelectedItem();
                invoiceService.registerPayment(invoiceId, method);
                
                // Actualizar tabla
                view.invoicesTable.setValueAt("Pagada", selectedRow, 4);
                
                JOptionPane.showMessageDialog(view,
                    "✅ Pago registrado exitosamente\n" +
                    "Factura #" + invoiceId + " marcada como Pagada\n" +
                    "Método: " + method,
                    "Pago Registrado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "Error al registrar pago: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void searchInvoices() {
        try {
            // Obtener filtros
            LocalDate from = null;
            LocalDate to = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            String fromText = view.dateFromField.getText().trim();
            String toText = view.dateToField.getText().trim();
            
            if (!fromText.isEmpty()) {
                from = LocalDate.parse(fromText, formatter);
            }
            
            if (!toText.isEmpty()) {
                to = LocalDate.parse(toText, formatter);
            }
            
            String clientFilter = view.clientSearchField.getText().trim();
            String statusFilter = (String) view.statusFilterBox.getSelectedItem();
            
            // Buscar facturas
            List<Invoice> invoices = invoiceService.searchInvoices(from, to, clientFilter, statusFilter);
            
            // Actualizar tabla
            invoicesTableModel.setRowCount(0);
            for (Invoice inv : invoices) {
                invoicesTableModel.addRow(new Object[]{
                    inv.getId(),
                    inv.getOwner() != null ? inv.getOwner().getName() : "N/A",
                    inv.getDate(),
                    String.format("%.2f", inv.getTotal()),
                    inv.getStatus()
                });
            }
            
            // Mostrar resultados
            if (invoices.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                    "No se encontraron facturas con los filtros especificados",
                    "Resultados",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view, 
                "Formato de fecha incorrecto. Use YYYY-MM-DD (ej: 2024-01-15)",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "Error en la búsqueda: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}