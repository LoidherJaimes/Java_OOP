package clinica_vet.model.repositories;
import java.util.ArrayList;
import java.util.List;

import model.entities.Usuario;

public class UsuarioRepository implements IUsuarioRepository {

    List<Usuario> listadoUsuario;

    public UsuarioRepository(){
        listadoUsuario = new ArrayList<>();
    }
    

    @Override
    public void addUser(Usuario user) {

        listadoUsuario.add(user);
    }

    @Override
    public Usuario getUser(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }

}