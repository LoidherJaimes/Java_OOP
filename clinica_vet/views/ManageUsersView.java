package clinica_vet.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.UUID;

public class ManageUsersView extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    // Botones
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClose; 
    private JButton btnCreate;

    public ManageUsersView() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(780, 550)); 

        // ⭐ PANEL SUPERIOR CON TÍTULO
        JLabel titleLabel = new JLabel("Manejo de Usuarios", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Modelo de tabla
        tableModel = new DefaultTableModel(new Object[]{"ID", "Usuario", "Password", "Rol"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnCreate = new JButton("Crear");
        btnEdit = new JButton("Modificar");
        btnDelete = new JButton("Eliminar");
        btnClose = new JButton("Volver al Menú Principal");

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Limpiar tabla
    public void clearTable() {
        tableModel.setRowCount(0);
    }

    // Agregar fila a tabla
    public void addUserToTable(UUID id, String username, String password, Object rol) {
        tableModel.addRow(new Object[]{id, username, password, rol});
    }

    // Getters
    public JTable getTable() { return table; }
    public JButton getBtnCreate() { return btnCreate; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClose() { return btnClose; }
}
