package clinica_vet.views;

import java.awt.*;
import javax.swing.*;

public class BillingAndPaymentsView extends JPanel {

    // Combo cliente
    public JComboBox<String> clientComboBox;

    // Panel Crear Factura
    public JTextField itemNameField;
    public JTextField quantityField;
    public JTextField priceField;
    public JButton addItemButton;
    public JTable itemsTable;
    public JLabel totalLabel;
    public JButton generateInvoiceButton;

    // Panel Registrar Pago
    public JComboBox<String> paymentMethodBox;
    public JButton registerPaymentButton;

    // Panel Consultar Facturas
    public JTextField dateFromField;
    public JTextField dateToField;
    public JTextField clientSearchField;
    public JComboBox<String> statusFilterBox;
    public JButton searchInvoicesButton;
    public JTable invoicesTable;

    public BillingAndPaymentsView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("💳 Módulo de Facturación y Pagos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Crear Factura", buildCreateInvoicePanel());
        tabs.addTab("Registrar Pago", buildRegisterPaymentPanel());
        tabs.addTab("Consultar Facturas", buildSearchInvoicesPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildCreateInvoicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Datos de Factura"));
        form.setBackground(Color.WHITE);

        // Selección cliente
        form.add(new JLabel("Cliente:"));
        clientComboBox = new JComboBox<>();
        form.add(clientComboBox);

        form.add(new JLabel("Nombre del ítem:"));
        itemNameField = new JTextField();
        form.add(itemNameField);

        form.add(new JLabel("Cantidad:"));
        quantityField = new JTextField();
        form.add(quantityField);

        form.add(new JLabel("Precio unitario:"));
        priceField = new JTextField();
        form.add(priceField);

        addItemButton = new JButton("Agregar Ítem");
        form.add(addItemButton);

        panel.add(form, BorderLayout.NORTH);

        itemsTable = new JTable(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"Ítem", "Cantidad", "Precio", "Subtotal"}
        ));
        panel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(new JLabel("Total: "));
        totalLabel = new JLabel("0.00");
        bottom.add(totalLabel);

        generateInvoiceButton = new JButton("Generar Factura");
        bottom.add(generateInvoiceButton);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildRegisterPaymentPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Pago"));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("Método de pago:"));
        paymentMethodBox = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia"});
        panel.add(paymentMethodBox);

        registerPaymentButton = new JButton("Registrar Pago");
        panel.add(registerPaymentButton);

        return panel;
    }

    private JPanel buildSearchInvoicesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JPanel filters = new JPanel(new GridLayout(4, 2, 10, 10));
        filters.setBorder(BorderFactory.createTitledBorder("Filtros de búsqueda"));
        filters.setBackground(Color.WHITE);

        filters.add(new JLabel("Fecha desde (YYYY-MM-DD):"));
        dateFromField = new JTextField();
        filters.add(dateFromField);

        filters.add(new JLabel("Fecha hasta (YYYY-MM-DD):"));
        dateToField = new JTextField();
        filters.add(dateToField);

        filters.add(new JLabel("Cliente:"));
        clientSearchField = new JTextField();
        filters.add(clientSearchField);

        filters.add(new JLabel("Estado:"));
        statusFilterBox = new JComboBox<>(new String[]{"Todos", "Pagada", "Pendiente"});
        filters.add(statusFilterBox);

        searchInvoicesButton = new JButton("Buscar");
        filters.add(searchInvoicesButton);

        panel.add(filters, BorderLayout.NORTH);

        invoicesTable = new JTable(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Cliente", "Fecha", "Total", "Estado"}
        ));
        panel.add(new JScrollPane(invoicesTable), BorderLayout.CENTER);

        return panel;
    }
}
