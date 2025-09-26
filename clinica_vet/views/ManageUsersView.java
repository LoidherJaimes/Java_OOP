package clinica_vet.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageUsersView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    // Botones
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClose;

    public ManageUsersView() {
        setTitle("Gestión de Usuarios");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // Modelo de tabla
        tableModel = new DefaultTableModel(new Object[]{"ID", "Usuario", "Password", "Rol"}, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        btnEdit = new JButton("Modificar");
        btnDelete = new JButton("Eliminar");
        btnClose = new JButton("Cerrar");

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
    public void addUserToTable(int id, String username, String password, Object rol) {
        tableModel.addRow(new Object[]{id, username, password, rol});
    }

    // Getters
    public JTable getTable() { return table; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClose() { return btnClose; }
}
