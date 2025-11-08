package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolService;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.CreateUserView;
import clinica_vet.views.ManageUsersView;
import clinica_vet.views.EditUserView; 

import java.util.UUID;
import javax.swing.*;

public class ManageUsersController {

    private ManageUsersView manageUsersView;
    private UserRepository userRepository;
    private IRolService rolService;

    public ManageUsersController(ManageUsersView manageUsersView, UserRepository userRepository, IRolService rolService) {
        this.manageUsersView = manageUsersView;
        this.userRepository = userRepository;
        this.rolService = rolService; 

        // Cargar usuarios al iniciar
        loadUsersIntoTable(); 

        // Listener botón cerrar
        this.manageUsersView.getBtnClose().addActionListener(e -> {
            manageUsersView.dispose();
        });

        // --- Listener Botón Crear Usuario ---
        this.manageUsersView.getBtnCreate().addActionListener(e -> {
            CreateUserView createUserView = new CreateUserView();
            // Pasa 'this' para que se pueda recargar la tabla al crear
            new CreateUserController(createUserView, userRepository, this, rolService); 
            createUserView.setVisible(true);
        });

        // --- Listener Botón Modificar (Usando EditUserController) ---
        this.manageUsersView.getBtnEdit().addActionListener(e -> {
            int selectedRow = manageUsersView.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageUsersView, 
                    "Por favor, seleccione un usuario para editar.", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 1. Obtener el ID del usuario de la tabla
            Object idObject = manageUsersView.getTable().getValueAt(selectedRow, 0);
            UUID userId;
            
            try {
                // Asegurar que el ID sea tratado como UUID
                userId = (UUID) idObject;
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(manageUsersView, "Error: El ID del usuario no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Buscar el objeto User en el repositorio
            User userToEdit = userRepository.getUserById(userId); 

            if (userToEdit != null) {
                // 3. Crear la vista de edición
                EditUserView editView = new EditUserView(manageUsersView, userToEdit);
                
                // 4. Instanciar el EditUserController y pasar todas las dependencias
                new EditUserController(editView, userToEdit, userRepository, rolService, this); 
                
                // 5. Mostrar la ventana de edición
                editView.setVisible(true);
            } else {
                 JOptionPane.showMessageDialog(manageUsersView, "Usuario no encontrado en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Listener Botón Eliminar (Corregido para UUID.equals()) ---
        this.manageUsersView.getBtnDelete().addActionListener(e -> {
            int selectedRow = manageUsersView.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageUsersView, 
                    "Por favor, seleccione un usuario para eliminar.", 
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 1. Obtener el ID del usuario
            Object idObject = manageUsersView.getTable().getValueAt(selectedRow, 0);
            UUID userId;
            
            try {
                userId = (UUID) idObject;
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(manageUsersView, "Error: El ID del usuario no es un formato válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(manageUsersView,
                "¿Está seguro de eliminar a " + manageUsersView.getTable().getValueAt(selectedRow, 1) + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // La corrección clave está en el UserRepository, aquí solo se llama
                    userRepository.deleteUserById(userId);
                    
                    loadUsersIntoTable();
                    JOptionPane.showMessageDialog(manageUsersView, "Usuario eliminado exitosamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(manageUsersView, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.manageUsersView.setVisible(true);
    }

    // Método para cargar usuarios en la tabla (usado por Create/Edit Controller para refrescar)
    public void loadUsersIntoTable() {
        manageUsersView.clearTable();
        for (User user : userRepository.getAllUsers()) {
            manageUsersView.addUserToTable(
                user.getId(),
                user.getUsername(),
                user.getPassword(), // Se asume que por ahora muestras el password
                user.getRol() != null ? user.getRol().getName() : "Sin rol"
            );
        }
    }
}