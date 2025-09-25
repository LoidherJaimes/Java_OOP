package clinica_vet.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import clinica_vet.model.entities.Rol;

import java.awt.*;

public class ManageUsersView {

    private JFrame manageUserF;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton btnClose;

    public ManageUsersView() {
        // Ventana principal
        manageUserF = new JFrame("Gestionar Usuarios");
        manageUserF.setSize(500, 400);
        manageUserF.setLayout(new BorderLayout());
        manageUserF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        manageUserF.setLocationRelativeTo(null);

        // Columnas de la tabla
        String[] columnNames = {"ID", "Usuario", "Contraseña", "Rol"};

        // Modelo de tabla vacío
        tableModel = new DefaultTableModel(columnNames, 0);

        // Tabla de usuarios
        userTable = new JTable(tableModel);
        userTable.setFillsViewportHeight(true);

        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(userTable);
        manageUserF.add(scrollPane, BorderLayout.CENTER);

        // Botón cerrar
        btnClose = new JButton("Cerrar");
        JPanel panelBottom = new JPanel();
        panelBottom.add(btnClose);

        manageUserF.add(panelBottom, BorderLayout.SOUTH);
    }

    // Método para añadir un usuario a la tabla
    public void addUserToTable(int id, String username, String password, Rol role) {
        tableModel.addRow(new Object[]{id, username, password, role});
    }

    public JButton getBtnClose() { return btnClose; }
    public JFrame getManageUserF() { return manageUserF; }
    public void setVisible(boolean visible) { manageUserF.setVisible(visible); }

    // Limpiar la tabla antes de volver a llenarla
    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addUserToTable(int id, String username, String password, Rol rol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUserToTable'");
    }
}
