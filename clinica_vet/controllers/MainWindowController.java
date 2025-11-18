package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.AppointmentService;
import clinica_vet.model.repositories.IRolService;
import clinica_vet.model.repositories.OwnerRepository;
import clinica_vet.model.repositories.PetRepository;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.AppointmentsView;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.ManageUsersView;
import clinica_vet.views.OwnerManagementView;
import clinica_vet.views.PetManagementView;

import javax.swing.*;

public class MainWindowController {
    
    private final MainWindowView mainWindowView;
    private final User currentUser;
    private final UserRepository userRepository;
    private final IRolService rolService;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final AppointmentService appointmentService;
    private final Runnable onLogoutAction;

    public MainWindowController(MainWindowView mainWindowView, 
                               User currentUser, 
                               UserRepository userRepository, 
                               IRolService rolService, 
                               OwnerRepository ownerRepository, 
                               PetRepository petRepository,
                               AppointmentService appointmentService,
                               Runnable onLogoutAction) {
        this.mainWindowView = mainWindowView;
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.rolService = rolService;
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.appointmentService = appointmentService;
        this.onLogoutAction = onLogoutAction;
        
        setupListeners();
        mainWindowView.setContent(mainWindowView.getWelcomeView());
    }

    private void setupListeners() {
        // Botón de logout
        this.mainWindowView.getBtnLogout().addActionListener(e -> onLogoutAction.run());

        // Botón Usuarios
        this.mainWindowView.getBtnUsers().addActionListener(e -> loadUserManagementView());

        // Botón Dueños
        this.mainWindowView.getBtnOwners().addActionListener(e -> loadOwnerManagementView());

        // Botón Mascotas
        this.mainWindowView.getBtnPets().addActionListener(e -> loadPetManagementView());
        
        // Botón Citas (NUEVO)
        this.mainWindowView.getBtnAppointment().addActionListener(e -> loadAppointmentsView());
    }
    
    // --- Métodos para cargar vistas ---
    
    private void loadUserManagementView() {
        ManageUsersView manageUsersView = new ManageUsersView();
        new ManageUsersController(manageUsersView, userRepository, rolService, mainWindowView); 
        mainWindowView.setContent(manageUsersView);
    }
    
    private void loadOwnerManagementView() {
        OwnerManagementView ownerManagementView = new OwnerManagementView();
        new OwnerManagementController(ownerManagementView, ownerRepository, mainWindowView);
        mainWindowView.setContent(ownerManagementView);
    }
    
    private void loadPetManagementView() {
        PetManagementView petManagementView = new PetManagementView();
        new PetController(petManagementView, petRepository, ownerRepository, mainWindowView);
        mainWindowView.setContent(petManagementView);
    }
    
    private void loadAppointmentsView() {
        AppointmentsView appointmentsView = new AppointmentsView();
        new AppointmentsController(
            appointmentsView,
            appointmentService,
            petRepository,
            userRepository,
            (JFrame) SwingUtilities.getWindowAncestor(mainWindowView)
        );
        mainWindowView.setContent(appointmentsView);
    }
}