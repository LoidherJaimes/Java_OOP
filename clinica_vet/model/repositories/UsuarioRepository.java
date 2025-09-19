package clinica_vet.model.repositories;
import java.util.ArrayList;
import java.util.List;

import clinica_vet.model.entities.User;

public class UsuarioRepository implements IUsuarioRepository {

    List<User> listadoUsuario;

    public UsuarioRepository(){
        listadoUsuario = new ArrayList<>();
    }
    

    @Override
    public void addUser(User user) {

        listadoUsuario.add(user);
    }

    @Override
    public User getUser(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }

}