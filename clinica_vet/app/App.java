import clinica_vet.model.repositories.IRolRepository;

public class App {
    public static void main(String[] args) throws Exception {
        IRolRepository rolRepository = new RolRepository();
        IRolService rolService = new RolService(rolRepository);
        rolService.addRol("Administrador");
        rolService.addRol("Auxiliar");
        rolService.addRol("Medico");

    }
}