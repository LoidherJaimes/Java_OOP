package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.ManageUsersView;

import javax.swing.*;

public class ManageUsersController {

    private ManageUsersView manageUsersView;
    private UserRepository userRepository;

    public ManageUsersController(ManageUsersView manageUsersView, UserRepository userRepository) {
        this.manageUsersView = manageUsersView;
        this.userRepository = userRepository;

        // Listener botón cerrar
        this.manageUsersView.getBtnClose().addActionListener(e -> {
            manageUsersView.dispose();
        });

        // Listener eliminar usuario
        this.manageUsersView.getBtnDelete().addActionListener(e -> deleteUser());

        // Listener modificar usuario
        this.manageUsersView.getBtnEdit().addActionListener(e -> editUser());
    }

    private void deleteUser() {
        int selectedRow = manageUsersView.getUsersTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageUsersView, "Seleccione un usuario para eliminar.");
            return;
        }

        int userId = (int) manageUsersView.getUsersTable().getValueAt(selectedRow, 0);
        userRepository.deleteUserById(userId);
        ((javax.swing.table.DefaultTableModel) manageUsersView.getUsersTable().getModel()).removeRow(selectedRow);
    }

    private void editUser() {
        int selectedRow = manageUsersView.getUsersTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageUsersView, "Seleccione un usuario para modificar.");
            return;
        }

        int userId = (int) manageUsersView.getUsersTable().getValueAt(selectedRow, 0);
        User user = userRepository.getUserById(userId);

        if (user == null) {
            JOptionPane.showMessageDialog(manageUsersView, "No se encontró el usuario.");
            return;
        }

        String nuevoUsuario = JOptionPane.showInputDialog("Nuevo nombre de usuario:", user.getUsername());
        String nuevoPassword = JOptionPane.showInputDialog("Nuevo password:", user.getPassword());

        if (nuevoUsuario != null && !nuevoUsuario.trim().isEmpty()) {
            user.setUsername(nuevoUsuario);
        }
        if (nuevoPassword != null && !nuevoPassword.trim().isEmpty()) {
            user.setPassword(nuevoPassword);
        }

        userRepository.updateUser(user);

        // Refrescar tabla
        manageUsersView.getUsersTable().setValueAt(user.getUsername(), selectedRow, 1);
        manageUsersView.getUsersTable().setValueAt(user.getPassword(), selectedRow, 2);
    }
}
