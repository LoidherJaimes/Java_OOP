package clinica_vet.controllers;

import clinica_vet.views.CreateUserView;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.model.entities.User;
import clinica_vet.model.entities.Rol;

import javax.swing.*;

public class CreateUserController {

    private CreateUserView createUserView;
    private UserRepository userRepository;
    private ManageUsersController manageUsersController;

    public CreateUserController(CreateUserView createUserView, UserRepository userRepository, ManageUsersController manageUsersController) {
        this.createUserView = createUserView;
        this.userRepository = userRepository;
        this.manageUsersController = manageUsersController;

        this.createUserView.getBtnCreateUserL().addActionListener(e -> {
            String username = createUserView.getCreateUserTF().getText().trim();
            String password = new String(createUserView.getCreatePasswordPF().getPassword());
            String passwordVerify = new String(createUserView.getVerificationPasswordPF().getPassword());

            // Validación de campos vacíos
            if (username.isEmpty() || password.isEmpty() || passwordVerify.isEmpty()) {
                JOptionPane.showMessageDialog(createUserView.getCreateUserF(), 
                    "Por favor, complete todos los campos.",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validación de coincidencia de contraseñas
            if (!password.equals(passwordVerify)) {
                JOptionPane.showMessageDialog(createUserView.getCreateUserF(), 
                    "Las contraseñas no coinciden.",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validación de usuario duplicado
            for (User u : userRepository.getAllUsers()) {
                if (username.equalsIgnoreCase(u.getUsername())) {
                    JOptionPane.showMessageDialog(createUserView.getCreateUserF(), 
                        "El nombre de usuario ya existe.",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Crear nuevo usuario con rol por defecto
            Rol defaultRol = new Rol(0, "Auxiliar");
            User newUser = new User(0, username, password, defaultRol);
            userRepository.addUser(newUser);

            JOptionPane.showMessageDialog(createUserView.getCreateUserF(), 
                "Usuario creado exitosamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

            // Recargar tabla de usuarios
            if (manageUsersController != null) {
                manageUsersController.loadUsersIntoTable();
            }

            // Cerrar ventana
            createUserView.getCreateUserF().dispose();
        });
    }
}