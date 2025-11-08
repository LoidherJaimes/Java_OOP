package clinica_vet.model.repositories;

import clinica_vet.model.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {
    
    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public List<User> getAllUsers() { return users; }
    public void addUser(User user) { users.add(user); }
    
    public User getUserById(UUID id) {
        for (User u : users) {
            if (u.getId().equals(id)) { return u; }
        }
        return null;
    }
    
    public void deleteUserById(UUID userId) {
        users.removeIf(u -> u.getId().equals(userId)); 
    }
    
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                return;
            }
        }
    }
    
    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}