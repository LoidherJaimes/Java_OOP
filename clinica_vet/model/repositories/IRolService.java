package clinica_vet.model.repositories;

import clinica_vet.model.entities.Rol;
import java.util.List;

public interface IRolService {
    void addRol(String nombre);
    void editRol(int id, String nuevoNombre);
    Rol getRolByName(String nombre);
    List<Rol> getAllRoles(); 
}