package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolService; // Nueva importación
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.CreateUserView;
import clinica_vet.views.ManageUsersView;

import java.util.UUID;
import javax.swing.*;

public class ManageUsersController {

    private ManageUsersView manageUsersView;
    private UserRepository userRepository;
    private IRolService rolService; // Nueva dependencia

    // Constructor modificado para recibir IRolService
    public ManageUsersController(ManageUsersView manageUsersView, UserRepository userRepository, IRolService rolService) {
        this.manageUsersView = manageUsersView;
        this.userRepository = userRepository;
        this.rolService = rolService; // Asignación

        // Cargar usuarios en la tabla al iniciar
        loadUsersIntoTable(); // ¡Esto carga los usuarios harcodeados!

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
            JOptionPane.showMessageDialog(manageUsersView, "Funcionalidad de edición en desarrollo");
        });

        // Listener Botón Crear Usuario (listo para usar CreateUserController)
        this.manageUsersView.getBtnCreate().addActionListener(e -> {
            CreateUserView createUserView = new CreateUserView();
            // Aquí puedes instanciar el CreateUserController con las dependencias
            // new CreateUserController(createUserView, userRepository, rolService, this); 
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

            // Nota: Aquí se asume que el ID de la tabla es del tipo que usa tu repositorio (UUID o Integer)
            Object idObject = manageUsersView.getTable().getValueAt(selectedRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(manageUsersView,
                "¿Está seguro de eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Aquí debes asegurar que el tipo de 'idObject' coincida con lo que espera 'deleteUserById'
                // Ejemplo asumiendo Integer si usas los IDs 1 y 2: userRepository.deleteUserById((Integer) idObject); 
                
                // Si usas UUID:
                if (idObject instanceof UUID) {
                    userRepository.deleteUserById((UUID) idObject);
                } else {
                    // Manejar caso donde el ID es un tipo diferente (e.g., Integer)
                    // userRepository.deleteUserById((Integer) idObject);
                    // Por ahora, solo simulamos la eliminación
                    JOptionPane.showMessageDialog(manageUsersView, "Simulación: Usuario con ID " + idObject + " eliminado.");
                }
                
                loadUsersIntoTable();
            }
        });

        // ¡CLAVE! Mostrar la ventana de gestión de usuarios DESPUÉS de cargar datos
        this.manageUsersView.setVisible(true);
    }

    // Método para cargar usuarios en la tabla
    public void loadUsersIntoTable() {
        // Asegúrate de que manageUsersView.clearTable() y manageUsersView.addUserToTable existen
        // y que el tipo de ID (el primer parámetro) coincide con el tipo de dato que devuelve user.getId()
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