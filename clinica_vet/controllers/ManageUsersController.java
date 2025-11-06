package clinica_vet.controllers;

import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.CreateUserView;
import clinica_vet.views.ManageUsersView;



public class ManageUsersController {

    private ManageUsersView manageUsersView;
    private UserRepository userRepository;
    private CreateUserView createUserView;

    public ManageUsersController(ManageUsersView manageUsersView, UserRepository userRepository) {
        this.manageUsersView = manageUsersView;
        this.userRepository = userRepository;

        // Listener botón cerrar
        this.manageUsersView.getBtnClose().addActionListener(e -> {
            manageUsersView.setVisible(false);
        });

        // Listener Botón editar
        this.manageUsersView.getBtnEdit().addActionListener(e -> {
            manageUsersView.setVisible(false);
        });

        // Listener Botón Crear Usuario
        this.manageUsersView.getBtnCreate().addActionListener(e -> {
            createUserView.getCreateUserF();
        });

        // Mostrar la ventana de gestión de usuarios
        this.manageUsersView.setVisible(true);
    }
}
