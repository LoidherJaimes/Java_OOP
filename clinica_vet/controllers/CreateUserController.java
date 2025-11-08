package clinica_vet.controllers;

import clinica_vet.views.CreateUserView;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.model.repositories.IRolService;
import clinica_vet.model.entities.User;
import clinica_vet.model.entities.Rol;

import javax.swing.*;
import java.util.List;

public class CreateUserController {

    private CreateUserView createUserView;
    private UserRepository userRepository;
    private ManageUsersController manageUsersController;
    private IRolService rolService;

    public CreateUserController(CreateUserView createUserView, UserRepository userRepository, 
                                ManageUsersController manageUsersController, IRolService rolService) {
        this.createUserView = createUserView;
        this.userRepository = userRepository;
        this.manageUsersController = manageUsersController;
        this.rolService = rolService; 
        
        // 1. Cargar roles en el ComboBox al iniciar el controlador
        loadRolesIntoComboBox();

        this.createUserView.getBtnCreateUserL().addActionListener(e -> {
            String username = createUserView.getCreateUserTF().getText().trim();
            String password = new String(createUserView.getCreatePasswordPF().getPassword());
            String passwordVerify = new String(createUserView.getVerificationPasswordPF().getPassword());
            // Obtener el nombre del Rol seleccionado (o el objeto Rol si el ComboBox lo almacena)
            String selectedRolName = (String) createUserView.getRolComboBox().getSelectedItem();


            // --- Validaciones (iguales a la versión anterior) ---
            if (username.isEmpty() || password.isEmpty() || passwordVerify.isEmpty() || selectedRolName == null) {
                JOptionPane.showMessageDialog(createUserView.getCreateUserF(), 
                    "Por favor, complete todos los campos y seleccione un rol.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.equals(passwordVerify)) {
                JOptionPane.showMessageDialog(createUserView.getCreateUserF(), 
                    "Las contraseñas no coinciden.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (User u : userRepository.getAllUsers()) {
                if (username.equalsIgnoreCase(u.getUsername())) {
                    JOptionPane.showMessageDialog(createUserView.getCreateUserF(), 
                        "El nombre de usuario ya existe.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // 2. Obtener el objeto Rol desde el servicio basado en el nombre seleccionado
            Rol selectedRol = rolService.getRolByName(selectedRolName);
            if (selectedRol == null) {
                JOptionPane.showMessageDialog(createUserView.getCreateUserF(), 
                    "Error: El rol seleccionado no es válido.", "Error de Rol", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear nuevo usuario
            User newUser = new User(0, username, password, selectedRol); 
            userRepository.addUser(newUser);

            JOptionPane.showMessageDialog(createUserView.getCreateUserF(), 
                "Usuario creado exitosamente con el rol '" + selectedRol.getName() + "'.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

            if (manageUsersController != null) {
                manageUsersController.loadUsersIntoTable();
            }

            createUserView.getCreateUserF().dispose();
        });
    }
    
    // Método para cargar los roles en el ComboBox
    private void loadRolesIntoComboBox() {
        List<Rol> roles = rolService.getAllRoles(); // Asumiendo que has añadido este método a IRolService
        for (Rol rol : roles) {
            createUserView.getRolComboBox().addItem(rol.getName());
        }
    }
}