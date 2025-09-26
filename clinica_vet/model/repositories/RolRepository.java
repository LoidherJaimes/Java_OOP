package clinica_vet.model.repositories;

import clinica_vet.model.entities.Rol;
import java.util.ArrayList;
import java.util.List;

public class RolRepository implements IRolRepository {

    List<Rol> rolCollection;

    public RolRepository(){
        rolCollection = new ArrayList<>();
    }

    @Override
    public int getId(){
        return rolCollection.size() + 1;
    }

    @Override
    public void addRol(Rol rol) {
        rolCollection.add(rol);
    }

    @Override
    public void editRol(Rol rol) {
        // implementar si se quiere editar roles
    }

    @Override
    public List<Rol> getAllRoles() {
        return rolCollection;
    }
}
