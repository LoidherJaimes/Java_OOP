package clinica_vet.model.repositories;

import java.util.List;
import clinica_vet.model.entities.Rol;

public interface IRolRepository {
    void addRol(Rol rol);
    void editRol(Rol rol);
    int getId();
    List<Rol> getAllRoles();
}
