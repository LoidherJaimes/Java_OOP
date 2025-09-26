package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.ManageUsersView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ManageUsersController {

    private ManageUsersView manageUsersView;
    private UserRepository userRepository;

    public ManageUsersController(ManageUsersView manageUsersView, UserRepository userRepository) {
        this.manageUsersView = manageUsersView;
        this.userRepository = userRepository;

        // Cargar usuarios en la tabla al iniciar
        loadUsersTable();

        // Botón cerrar
        this.manageUsersView.getBtnClose().addActionListener(e -> manageUsersView.setVisible(false));

        // Botón eliminar
        this.manageUsersView.getBtnClose().addActionListener(e -> deleteUser());

        // Botón modificar
        this.manageUsersView.getBtnEditUser().addActionListener(e -> editUser());
    }

    private void loadUsersTable() {
        manageUsersView.clearTable();
        List<User> allUsers = userRepository.getAllUsers();
        for (User u : allUsers) {
            String rolNombre = (u.getRol() != null) ? u.getRol().getName() : "Sin rol";
            manageUsersView.addUserToTable(u.getId(), u.getUsername(), u.getPassword(), rolNombre);
        }
    }

    private void deleteUser() {
        int selectedRow = manageUsersView.getUserTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageUsersView, "Seleccione un usuario para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(manageUsersView,
                "¿Está seguro que desea eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        int userId = (int) manageUsersView.getUserTable().getValueAt(selectedRow, 0);
        userRepository.deleteUserById(userId);
        ((DefaultTableModel) manageUsersView.getUserTable().getModel()).removeRow(selectedRow);
    }

    private void editUser() {
        int selectedRow = manageUsersView.getUserTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageUsersView, "Seleccione un usuario para modificar.");
            return;
        }

        int userId = (int) manageUsersView.getUserTable().getValueAt(selectedRow, 0);
        User user = userRepository.getUserById(userId);

        if (user == null) {
            JOptionPane.showMessageDialog(manageUsersView, "No se encontró el usuario.");
            return;
        }

        String nuevoUsuario = JOptionPane.showInputDialog(manageUsersView, "Nuevo nombre de usuario:", user.getUsername());
        String nuevoPassword = JOptionPane.showInputDialog(manageUsersView, "Nueva contraseña:", user.getPassword());

        if (nuevoUsuario != null && !nuevoUsuario.trim().isEmpty()) {
            user.setUsername(nuevoUsuario);
        }
        if (nuevoPassword != null && !nuevoPassword.trim().isEmpty()) {
            user.setPassword(nuevoPassword);
        }

        userRepository.updateUser(user);

        // Actualizar tabla
        manageUsersView.getUserTable().setValueAt(user.getUsername(), selectedRow, 1);
        manageUsersView.getUserTable().setValueAt(user.getPassword(), selectedRow, 2);
    }
}
