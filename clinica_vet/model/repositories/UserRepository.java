package clinica_vet.model.repositories;

import java.util.ArrayList;
import java.util.List;

import clinica_vet.model.entities.User;

public class UserRepository implements IUsuarioRepository {

    private List<User> listadoUsuario;

    public UserRepository() {
        listadoUsuario = new ArrayList<>();
    }


    public void addUser(User user) {
        listadoUsuario.add(user);
    }

    // 🔹 Método para obtener toda la lista de usuarios
    public List<User> getAllUsers() {
        return listadoUsuario;
    }

    @Override
    public User getUser(String email) {
        // TODO: implementar búsqueda por email si quieres
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }
}
