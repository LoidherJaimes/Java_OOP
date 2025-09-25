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
        // TODO Auto-generated method stub
       
    }
    @Override
    public Rol getRolByName(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }


}