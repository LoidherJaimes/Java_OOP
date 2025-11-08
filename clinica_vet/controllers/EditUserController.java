package clinica_vet.controllers;

import clinica_vet.model.entities.Rol;
import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolService;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.EditUserView;

import javax.swing.*;
import java.util.List;

public class EditUserController {

    private EditUserView editView;
    private User userToEdit;
    private UserRepository userRepository;
    private IRolService rolService;
    private ManageUsersController manageUsersController; // Para recargar la tabla

    public EditUserController(EditUserView editView, User userToEdit, UserRepository userRepository, 
                              IRolService rolService, ManageUsersController manageUsersController) {
        this.editView = editView;
        this.userToEdit = userToEdit;
        this.userRepository = userRepository;
        this.rolService = rolService;
        this.manageUsersController = manageUsersController;
        
        // 1. Configurar la vista al iniciar
        loadRolesIntoComboBox();

        // 2. Listener para guardar cambios
        this.editView.getBtnSave().addActionListener(e -> saveChanges());
    }
    
    // Método para cargar los roles en el ComboBox
    private void loadRolesIntoComboBox() {
        List<Rol> roles = rolService.getAllRoles();
        for (Rol rol : roles) {
            this.editView.getRolComboBox().addItem(rol.getName());
        }
        
        // Seleccionar el rol actual del usuario
        if (userToEdit.getRol() != null) {
            this.editView.getRolComboBox().setSelectedItem(userToEdit.getRol().getName());
        }
    }
    
    // Método para manejar el guardado de cambios
    private void saveChanges() {
        // Obtener nuevos valores
        String newUsername = editView.getUsernameTF().getText().trim();
        String newPassword = new String(editView.getPasswordPF().getPassword());
        String newRolName = (String) editView.getRolComboBox().getSelectedItem();
        
        // --- Validaciones ---
        if (newUsername.isEmpty()) {
            JOptionPane.showMessageDialog(editView, "El nombre de usuario no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Opcional: Validación para evitar duplicidad de username con otros usuarios
        for (User u : userRepository.getAllUsers()) {
            // Comprobar si es un usuario diferente y si el nombre ya existe
            if (!u.getId().equals(userToEdit.getId()) && newUsername.equalsIgnoreCase(u.getUsername())) {
                JOptionPane.showMessageDialog(editView, "El nombre de usuario ya existe en otro registro.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // --- Aplicar cambios usando Setters ---
        userToEdit.setUsername(newUsername);
        
        // Actualizar password solo si se introdujo uno nuevo
        if (!newPassword.isEmpty()) {
            userToEdit.setPassword(newPassword); 
        }
        
        // Actualizar Rol
        if (newRolName != null) {
            userToEdit.setRol(rolService.getRolByName(newRolName));
        }

        // Guardar cambios en el repositorio
        userRepository.updateUser(userToEdit); 
        
        JOptionPane.showMessageDialog(editView, "Usuario modificado exitosamente.");
        
        // Recargar tabla y cerrar ventana de edición
        manageUsersController.loadUsersIntoTable();
        editView.dispose();
    }
}