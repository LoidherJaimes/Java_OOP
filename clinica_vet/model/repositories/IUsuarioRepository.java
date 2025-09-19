package clinica_vet.model.repositories;

import clinica_vet.model.entities.User;

public interface IUsuarioRepository {
    void addUser(User user);
    User getUser(String email);
}