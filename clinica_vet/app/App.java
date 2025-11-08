package clinica_vet.app;

import clinica_vet.controllers.LoginController;
import clinica_vet.controllers.MainWindowController;
import clinica_vet.model.entities.Owner;
import clinica_vet.model.entities.Rol;
import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.*;
import clinica_vet.views.LoginView;
import clinica_vet.views.MainWindowView;

import javax.swing.SwingUtilities;


public class App {
    
    private IRolService rolService; 
    private UserRepository userRepository;
    private OwnerRepository ownerRepository;
    private LoginView loginView;

    public App() {
        IRolRepository rolRepository = new RolRepository();
        this.rolService = new RolService(rolRepository); 
        this.userRepository = new UserRepository();
        this.ownerRepository = new OwnerRepository();
        
        initializeData();
    }
    
    private void initializeData() {
        if (rolService.getAllRoles().isEmpty()) {
            rolService.addRol("Administrador");
            rolService.addRol("Auxiliar");
            rolService.addRol("Veterinario");
        }

        if (userRepository.getAllUsers().isEmpty()) {
            Rol rolAdmin = rolService.getRolByName("Administrador");
            Rol rolAux = rolService.getRolByName("Auxiliar");
            
            User admin = new User(1, "admin", "1234", rolAdmin);
            User aux = new User(2, "aux", "1234", rolAux);
            
            userRepository.addUser(admin);
            userRepository.addUser(aux);
        }
        
        if (ownerRepository.getAllOwners().isEmpty()) {
            ownerRepository.addOwner(new Owner("Juan Pérez", "555-1234", "Calle 10 #5-20"));
            ownerRepository.addOwner(new Owner("Ana Gómez", "555-5678", "Av. Principal 45"));
        }
    }

    public void startApplication() {
        if (loginView != null) {
            loginView.dispose();
        }
        loginView = new LoginView();
        
        // ⭐ CORRECCIÓN DE LA LLAMADA AL CONSTRUCTOR:
        // Aseguramos que el LoginController recibe 3 argumentos: Vista, Repo, Callback (this::onLoginSuccess)
        // LoginController ahora usa la sobrecarga con 3 argumentos.
        new LoginController(loginView, userRepository, this::onLoginSuccess); 
        loginView.setVisible(true);
    }
    
    private void onLoginSuccess(User user) {
        loginView.dispose(); 
        MainWindowView mainWindowView = new MainWindowView();
        
        Runnable onLogoutAction = this::startApplication;
        
        // ⭐ CONSTRUCTOR COMPLETO DE MAINWINDOWCONTROLLER: 6 argumentos
        new MainWindowController(mainWindowView, user, userRepository, rolService, ownerRepository, onLogoutAction); 
        
        mainWindowView.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.startApplication();
        });
    }
}