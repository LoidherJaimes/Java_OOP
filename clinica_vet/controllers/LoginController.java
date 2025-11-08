package clinica_vet.controllers;

import clinica_vet.model.entities.User;

import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.LoginView;

import java.util.List;
import java.util.function.Consumer; // ⭐ Nueva Importación
import javax.swing.JOptionPane;

public class LoginController {
    
    private Consumer<User> onLoginSuccessCallback;

    // ⭐ CONSTRUCTOR CORREGIDO: Recibe el callback de éxito de login
    public LoginController(LoginView vista, UserRepository userRepository, Consumer<User> onLoginSuccessCallback) {
        
        this.onLoginSuccessCallback = onLoginSuccessCallback;
        
        // Listener Login
        vista.getBtnLogin().addActionListener(e -> {
            String username = vista.getUserTF().getText();
            String password = new String(vista.getPasswordPF().getPassword());
            List<User> listadoUsuario = userRepository.getAllUsers();
            User loginUser = null;

            for (User u : listadoUsuario) {
                if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                    loginUser = u;
                    break;
                }
            }

            if (loginUser != null) {
                vista.dispose();
                // ⭐ LLAMA AL CALLBACK: Notifica a App que el login fue exitoso
                onLoginSuccessCallback.accept(loginUser); 
                
            } else {
                JOptionPane.showMessageDialog(null, "Login fallido");
            }
        });
        
        // El rolService ya no se necesita en el constructor de LoginController
        // si la verificación de rol se hace en el App o MainWindowController.
        // Lo eliminamos para simplificar la firma del constructor aquí.
    }
}