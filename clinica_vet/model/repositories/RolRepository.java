package clinica_vet.model.repositories;

import java.util.ArrayList;
import java.util.List;

import clinica_vet.model.entities.Rol;

public class RolRepository implements IRolRepository {

    List<Rol> rolCollection;

    public int getId(){
        return rolCollection.size() + 1;
    }

    public RolRepository(){
        rolCollection = new ArrayList<>();
    }

    @Override
    public void addRol(Rol rol) {
        rolCollection.add(rol);
    }
        

    @Override
    public void editRol(Rol rol) {
        
    }

}