package clinica_vet.app;

import clinica_vet.views.loginView;
import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolRepository;
import clinica_vet.model.repositories.IRolService;
import clinica_vet.model.repositories.RolRepository;
import clinica_vet.model.repositories.RolService;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.controllers.LoginController;

public class App {
    public static void main(String[] args) throws Exception {
        // Configuración de roles
        IRolRepository rolRepository = new RolRepository();
        IRolService rolService = new RolService(rolRepository);
        rolService.addRol("Administrador");
        rolService.addRol("Auxiliar");
        rolService.addRol("Medico");

        // Crear usuarios
        User pedro = new User(1, "pedro", "1234");
        UserRepository userRepository = new UserRepository();
        userRepository.addUser(pedro);

        // Crear vista
        loginView login = new loginView();

        // Conectar vista con controlador ✅
        LoginController loginController = new LoginController(login, userRepository);

        // Mostrar ventana
        login.setVisible(true);
    }
}
