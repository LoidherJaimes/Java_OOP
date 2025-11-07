package clinica_vet.model.repositories;

import clinica_vet.model.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    private List<User> users;
    private int nextId = 1;

    public UserRepository() {
        users = new ArrayList<>();
       
    }

    public List<User> getAllUsers() {
        return users;
    }


    public User getUserById(UUID id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public void addUser(User user) {
        user.setId(UUID.randomUUID());
        users.add(user);
    }

    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                return;
            }
        }
    }

    public void deleteUserById(UUID userId) {
        users.removeIf(u -> u.getId() == userId);
    }
}
