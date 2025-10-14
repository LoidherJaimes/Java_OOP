package clinica_vet.controllers;


import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.ManageUsersView;
import clinica_vet.views.CreateUserView;


public class ManageUsersController {

    private ManageUsersView manageUsersView;
    private UserRepository userRepository;

    public ManageUsersController(ManageUsersView manageUsersView, UserRepository userRepository) {
        this.manageUsersView = manageUsersView;
        this.userRepository = userRepository;

        // Listener botón cerrar
        this.manageUsersView.getBtnClose().addActionListener(e -> {
            manageUsersView.setVisible(false);
        });

        // Listener Crear Usuario
        manageUsersView.getBtnCreateUser().addActionListener(ev -> {
            CreateUserView createUserView = new CreateUserView();
            new CreateUserController(createUserView, userRepository, vista);
            createUserView.setVisible(true);
            vista.setVisible(false);
        });

        
    }
}
