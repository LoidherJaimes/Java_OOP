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
    
    // Hacemos las dependencias accesibles/injectables
    private static IRolService rolService; 
    private static UserRepository userRepository;

    public static void main(String[] args) {
        // Configuración de roles
        IRolRepository rolRepository = new RolRepository();
        rolService = new RolService(rolRepository); 
        rolService.addRol("Administrador");
        rolService.addRol("Auxiliar");
        rolService.addRol("Medico");

        // Crear usuarios iniciales
        Rol rolAdmin = rolService.getRolByName("Administrador");
        Rol rolaux = rolService.getRolByName("Auxiliar");
        
        // NOTA: Tu constructor de User genera un nuevo UUID internamente,
        // por lo que el 'int 1' y 'int 2' son ignorados, pero se dejan para inicialización.
        User admin = new User(1, "admin", "1234", rolAdmin);
        User aux = new User(2, "aux", "1234", rolaux);
        
        userRepository = new UserRepository();
        userRepository.addUser(admin);
        userRepository.addUser(aux);
        
        // Crear vista login
        LoginView login = new LoginView();
        
        // ¡CORRECCIÓN CLAVE! Pasar el RolService al LoginController
        new LoginController(login, userRepository, rolService); 

        // Mostrar login
        login.setVisible(true);
    }
    
    // Método auxiliar (aunque se recomienda inyección directa, se deja por si es útil)
    public static IRolService getRolService() {
        return rolService;
    }
}