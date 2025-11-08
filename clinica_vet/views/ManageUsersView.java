package clinica_vet.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.UUID;

// ⭐ CAMBIO CLAVE: Ahora extiende JPanel
public class ManageUsersView extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    // Botones
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClose; // Usado para volver al panel de bienvenida
    private JButton btnCreate;

    public ManageUsersView() {
        // Ya no necesitas setTitle, setSize, setLocationRelativeTo, etc.
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(780, 550)); 

        // Modelo de tabla
        tableModel = new DefaultTableModel(new Object[]{"ID", "Usuario", "Password", "Rol"}, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnEdit = new JButton("Modificar");
        btnDelete = new JButton("Eliminar");
        btnClose = new JButton("Volver al Menú Principal");
        btnCreate = new JButton("Crear");

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
    public JButton getBtnClose() { return btnClose; } // Ahora se usa para navegación interna
}