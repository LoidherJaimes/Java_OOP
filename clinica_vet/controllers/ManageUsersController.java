package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolService;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.CreateUserView;
import clinica_vet.views.ManageUsersView;
import clinica_vet.views.EditUserView; 
import clinica_vet.views.MainWindowView; // Necesitas el owner para los JDialogs

import java.util.UUID;
import javax.swing.*;

public class ManageUsersController {

    private ManageUsersView manageUsersView;
    private UserRepository userRepository;
    private IRolService rolService;
    private MainWindowView mainWindowViewOwner; // Referencia al Main Window para JDialogs

    // Se cambió el constructor para recibir el ManageUsersView como JPanel, y el MainWindowView como owner
    public ManageUsersController(ManageUsersView manageUsersView, UserRepository userRepository, IRolService rolService, MainWindowView mainWindowViewOwner) {
        this.manageUsersView = manageUsersView;
        this.userRepository = userRepository;
        this.rolService = rolService; 
        this.mainWindowViewOwner = mainWindowViewOwner;

        loadUsersIntoTable(); 

        // Listener para volver al menú principal
        this.manageUsersView.getBtnClose().addActionListener(e -> {
            mainWindowViewOwner.setContent(mainWindowViewOwner.getWelcomeView());
        });

        // Listener Botón Crear Usuario
        this.manageUsersView.getBtnCreate().addActionListener(e -> {
            CreateUserView createUserView = new CreateUserView();
            new CreateUserController(createUserView, userRepository, this, rolService); 
            createUserView.setVisible(true);
        });

        // Listener Botón Modificar
        this.manageUsersView.getBtnEdit().addActionListener(e -> {
            int selectedRow = manageUsersView.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageUsersView, "Por favor, seleccione un usuario para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Object idObject = manageUsersView.getTable().getValueAt(selectedRow, 0);
            UUID userId;
            
            try {
                userId = (UUID) idObject;
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(manageUsersView, "Error: El ID del usuario no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User userToEdit = userRepository.getUserById(userId); 

            if (userToEdit != null) {
                // Usar el MainWindowView como 'owner' para el JDialog
                EditUserView editView = new EditUserView(mainWindowViewOwner, userToEdit); 
                new EditUserController(editView, userToEdit, userRepository, rolService, this); 
                editView.setVisible(true);
            } else {
                 JOptionPane.showMessageDialog(manageUsersView, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Listener Botón Eliminar
        this.manageUsersView.getBtnDelete().addActionListener(e -> {
            int selectedRow = manageUsersView.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageUsersView, "Por favor, seleccione un usuario para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Object idObject = manageUsersView.getTable().getValueAt(selectedRow, 0);
            UUID userId;
            
            try {
                userId = (UUID) idObject;
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(manageUsersView, "Error: El ID del usuario no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(manageUsersView,
                "¿Está seguro de eliminar a " + manageUsersView.getTable().getValueAt(selectedRow, 1) + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    userRepository.deleteUserById(userId);
                    loadUsersIntoTable();
                    JOptionPane.showMessageDialog(manageUsersView, "Usuario eliminado exitosamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(manageUsersView, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

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