package clinica_vet.app;

import clinica_vet.controllers.LoginController;
import clinica_vet.model.entities.Rol;
import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolRepository;
import clinica_vet.model.repositories.IRolService;
import clinica_vet.model.repositories.RolRepository;
import clinica_vet.model.repositories.RolService;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.LoginView;

public class App {
    public static void main(String[] args) throws Exception {
        // Configuración de roles
        IRolRepository rolRepository = new RolRepository();
        IRolService rolService = new RolService(rolRepository);
        rolService.addRol("Administrador");
        rolService.addRol("Auxiliar");
        rolService.addRol("Medico");

        // Crear usuarios
        Rol rolAdmin = rolService.getRolByName("Administrador");
        User admin = new User(1, "pedro", "1234", rolAdmin);

        UserRepository userRepository = new UserRepository();
        userRepository.addUser(admin);

        // Crear vista
        LoginView login = new LoginView();
        // Conectar vista con controlador 
        LoginController LoginController = new LoginController(login, userRepository);
        // Abrir vista del login
        login.setVisible(true);
    }
}
