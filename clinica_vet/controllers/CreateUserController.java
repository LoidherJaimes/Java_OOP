package clinica_vet.controllers;

import clinica_vet.views.CreateUserView;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.model.entities.User;
import clinica_vet.views.LoginView;

import javax.swing.*;
import java.util.List;

public class CreateUserController {

    private CreateUserView createUserView;
    private UserRepository UserRepository;
    private LoginView loginView;

    public CreateUserController(CreateUserView createUserView, UserRepository userRepository, LoginView loginView) {
        this.createUserView = createUserView;
        this.UserRepository = userRepository;
        this.loginView = loginView;

        // Listener del botón Crear Usuario
        this.createUserView.getBtnCreateUserL().addActionListener(e -> {
            String username = createUserView.getCreateUserTF().getText();
            String password = new String(createUserView.getCreatePasswordPF().getPassword());
            String passwordVerify = new String(createUserView.getVerificationPasswordPF().getPassword());

            // Validar campos
            if (username.isEmpty() || password.isEmpty() || passwordVerify.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                return;
            }

            // Validar que las contraseñas coincidan
            if (!password.equals(passwordVerify)) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                return;
            }

            // Verificar si el usuario ya existe
            List<User> listadoUsuario = userRepository.getAllUsers();
            for (User u : listadoUsuario) {
                if (username.equals(u.getUsername())) {
                    JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe.");
                    return;
                }
            }

            // Crear y agregar nuevo usuario
            int newId = listadoUsuario.size() + 1; // Generación simple de ID
            User newUser = new User(newId, username, password, "USER"); // por defecto rol USER
            userRepository.addUser(newUser);

            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente.");

            // Cerrar vista de creación y volver al login
            createUserView.setVisible(false);
            loginView.setVisible(true);
        });
    }
}
