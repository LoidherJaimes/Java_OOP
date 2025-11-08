package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolService; // Nueva Importación
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.LoginView;
import clinica_vet.views.MainWindowView;

import java.util.List;
import javax.swing.JOptionPane;

public class LoginController {
    
    // Constructor modificado
    public LoginController(LoginView vista, UserRepository userRepository, IRolService rolService) {
        
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

                MainWindowView mainView = new MainWindowView();
                // ¡CORRECCIÓN CLAVE! Pasar el rolService a MainWindowController
                new MainWindowController(mainView, loginUser, userRepository, rolService); 
                mainView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Login fallido");
            }
        });
    }
}