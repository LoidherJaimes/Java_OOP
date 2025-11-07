package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.CreateUserView;
import clinica_vet.views.ManageUsersView;

import javax.swing.*;

public class ManageUsersController {

    private ManageUsersView manageUsersView;
    private UserRepository userRepository;

    public ManageUsersController(ManageUsersView manageUsersView, UserRepository userRepository) {
        this.manageUsersView = manageUsersView;
        this.userRepository = userRepository;

        // Cargar usuarios en la tabla al iniciar
        loadUsersIntoTable();

        // Listener botón cerrar
        this.manageUsersView.getBtnClose().addActionListener(e -> {
            manageUsersView.dispose();
        });

        // Listener Botón editar
        this.manageUsersView.getBtnEdit().addActionListener(e -> {
            int selectedRow = manageUsersView.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageUsersView, 
                    "Por favor, seleccione un usuario para editar.", 
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Aquí implementarás la edición más adelante
            JOptionPane.showMessageDialog(manageUsersView, "Funcionalidad de edición en desarrollo");
        });

        // Listener Botón Crear Usuario
        this.manageUsersView.getBtnCreate().addActionListener(e -> {
            CreateUserView createUserView = new CreateUserView();
            new CreateUserController(createUserView, userRepository, this);
            createUserView.setVisible(true);
        });

        // Listener Botón Eliminar
        this.manageUsersView.getBtnDelete().addActionListener(e -> {
            int selectedRow = manageUsersView.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageUsersView, 
                    "Por favor, seleccione un usuario para eliminar.", 
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            int userId = (int) manageUsersView.getTable().getValueAt(selectedRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(manageUsersView,
                "¿Está seguro de eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                userRepository.deleteUserById(userId);
                loadUsersIntoTable();
                JOptionPane.showMessageDialog(manageUsersView, "Usuario eliminado exitosamente");
            }
        });

        // Mostrar la ventana de gestión de usuarios
        this.manageUsersView.setVisible(true);
    }

    // Método para cargar usuarios en la tabla
    public void loadUsersIntoTable() {
        manageUsersView.clearTable();
        for (User user : userRepository.getAllUsers()) {
            manageUsersView.addUserToTable(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRol() != null ? user.getRol().getName() : "Sin rol"
            );
        }
    }
}