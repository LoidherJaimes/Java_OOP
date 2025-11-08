package clinica_vet.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.UUID;

// Extiende JPanel para integrarse en MainWindowView
public class OwnerManagementView extends JPanel { 

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClose;
    private JButton btnCreate;

    public OwnerManagementView() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(780, 550)); 
        
        // Título
        JLabel title = new JLabel("Gestión de Dueños", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Modelo de tabla
        String[] columnNames = {"ID", "Nombre", "Teléfono", "Dirección"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnCreate = new JButton("Crear Dueño");
        btnEdit = new JButton("Modificar");
        btnDelete = new JButton("Eliminar");
        btnClose = new JButton("Volver al Menú Principal");

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Métodos para interactuar con la tabla
    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addOwnerToTable(UUID id, String name, String phone, String address) {
        tableModel.addRow(new Object[]{id, name, phone, address});
    }
    
    // Getters
    public JTable getTable() { return table; }
    public JButton getBtnCreate() { return btnCreate; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClose() { return btnClose; }
}