package clinica_vet.model.repositories;

import clinica_vet.model.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {
    
    private List<User> users;

    // Ya no se requiere IRolService en el constructor si no se inicializan usuarios aquí.
    public UserRepository() {
        this.users = new ArrayList<>();
        // ¡La lista de 'users' comienza vacía! 
        // La clase MainApp o un DataInitializer se encargará de agregar los usuarios iniciales.
    }

    /**
     * Obtiene todos los usuarios del repositorio.
     * @return Una lista de todos los objetos User.
     */
    public List<User> getAllUsers() {
        return users;
    }

    /**
     * Agrega un nuevo usuario a la lista.
     * @param user El objeto User a agregar.
     */
    public void addUser(User user) {
        users.add(user);
    }
    
    /**
     * Busca un usuario por su ID (UUID).
     * @param id El UUID del usuario a buscar.
     * @return El objeto User si es encontrado, o null.
     */
    public User getUserById(UUID id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }
    
    /**
     * Elimina un usuario por su ID (UUID).
     * @param userId El UUID del usuario a eliminar.
     */
    public void deleteUserById(UUID userId) {
        // Uso de .equals() para correcta comparación de UUIDs
        users.removeIf(u -> u.getId().equals(userId)); 
    }
    
    /**
     * Actualiza la información de un usuario existente.
     * @param user El objeto User con los datos actualizados.
     */
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                return;
            }
        }
    }
    
    /**
     * Verifica las credenciales de un usuario.
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return El objeto User si las credenciales son correctas, o null.
     */
    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}