package clinica_vet.model.entities;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Rol rol;

    //constructor
    public User(int id, String username, String password, String rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }



    //getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Rol getRol() {
        return rol;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}

