package clinica_vet.model.entities;

import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String password;
    private Rol rol;

    // Constructor de CREACIÓN (Genera UUID automáticamente al crear)
    public User(String username, String password, Rol rol) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.rol = rol;
    }
    
    // Constructor para CARGA (Con ID existente)
    public User(UUID id, String username, String password, Rol rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Rol getRol() { return rol; }

    public void setId(UUID id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRol(Rol rol) { this.rol = rol; }
}