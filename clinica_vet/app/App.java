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
    public static void main(String[] args) {
        // Configuración de roles
        IRolRepository rolRepository = new RolRepository();
        IRolService rolService = new RolService(rolRepository);
        rolService.addRol("Administrador");
        rolService.addRol("Auxiliar");
        rolService.addRol("Medico");

        // Crear usuarios iniciales
        Rol rolAdmin = rolService.getRolByName("Administrador");
        Rol rolaux = rolService.getRolByName("Auxiliar");
        User admin = new User(1, "pedro", "1234", rolAdmin);
        User aux = new User(2, "ana", "1234", rolaux);
        UserRepository userRepository = new UserRepository();
        userRepository.addUser(admin);
        userRepository.addUser(aux);
        // Crear vista login
        LoginView login = new LoginView();
        new LoginController(login, userRepository);

        // Mostrar login
        login.setVisible(true);
    }
}
