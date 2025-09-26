package clinica_vet.model.repositories;

import clinica_vet.model.entities.Rol;

public class RolService implements IRolService {

    IRolRepository rolRepository;

    public RolService(IRolRepository rolRepository){
        this.rolRepository = rolRepository;
    }

    public void addRol(String nombre) {
        Rol rol = new Rol();
        rol.setId(rolRepository.getId());
        rol.setName(nombre);
        rolRepository.addRol(rol);
    }

    @Override
    public void editRol(int id, String nuevoNombre) {
        for (Rol r : rolRepository.getAllRoles()) {
            if (r.getId() == id) {
                r.setName(nuevoNombre);
            }
        }
    }

    @Override
    public Rol getRolByName(String nombre) {
        for (Rol rol : rolRepository.getAllRoles()) {
            if (rol.getName().equalsIgnoreCase(nombre)) {
                return rol;
            }
        }
        return null;
    }
}
