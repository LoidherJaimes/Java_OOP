package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.*;
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
    
    private final MedicalAttentionRepository medicalAttentionRepository;
    private final TreatmentRepository treatmentRepository;
    private final MedicalOrderRepository medicalOrderRepository;

    public MainWindowController(MainWindowView mainWindowView, 
                               User currentUser, 
                               UserRepository userRepository, 
                               IRolService rolService, 
                               OwnerRepository ownerRepository, 
                               PetRepository petRepository,
                               AppointmentService appointmentService,
                               MedicalAttentionRepository medicalAttentionRepository, // ⭐ NUEVO
                               TreatmentRepository treatmentRepository,               // ⭐ NUEVO
                               MedicalOrderRepository medicalOrderRepository,         // ⭐ NUEVO
                               Runnable onLogoutAction) {
        this.mainWindowView = mainWindowView;
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.rolService = rolService;
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.appointmentService = appointmentService;
        this.medicalAttentionRepository = medicalAttentionRepository;
        this.treatmentRepository = treatmentRepository;
        this.medicalOrderRepository = medicalOrderRepository;
        this.onLogoutAction = onLogoutAction;
        
        setupListeners();
        configureMenuByRole(); // ⭐ NUEVO: Configurar menú según rol
        mainWindowView.setContent(mainWindowView.getWelcomeView());
    }
    
    private void configureMenuByRole() {
        boolean isAdmin = currentUser.getRol() != null && 
                         currentUser.getRol().getName().equalsIgnoreCase("Administrador");
        boolean isVeterinarian = currentUser.getRol() != null && 
                                currentUser.getRol().getName().equalsIgnoreCase("Veterinario");
        
        mainWindowView.getBtnUsers().setVisible(isAdmin);
        
        if (mainWindowView.getBtnHistory() != null) {
            mainWindowView.getBtnHistory().setVisible(isVeterinarian);
        }
    }

    private void setupListeners() {
        this.mainWindowView.getBtnLogout().addActionListener(e -> onLogoutAction.run());

        this.mainWindowView.getBtnUsers().addActionListener(e -> loadUserManagementView());

        this.mainWindowView.getBtnOwners().addActionListener(e -> loadOwnerManagementView());

        this.mainWindowView.getBtnPets().addActionListener(e -> loadPetManagementView());
        
        this.mainWindowView.getBtnAppointment().addActionListener(e -> loadAppointmentsView());
        
        if (this.mainWindowView.getBtnHistory() != null) {
            this.mainWindowView.getBtnHistory().addActionListener(e -> loadMedicalHistoryView());
        }
    }
    
    private void loadUserManagementView() {
        ManageUsersView manageUsersView = new ManageUsersView();
        ManageUsersController manageUsersController = new ManageUsersController(manageUsersView, userRepository, rolService, mainWindowView); 
        mainWindowView.setContent(manageUsersView);
    }
    
    private void loadOwnerManagementView() {
        OwnerManagementView ownerManagementView = new OwnerManagementView();
        OwnerManagementController ownerManagementController = new OwnerManagementController(ownerManagementView, ownerRepository, mainWindowView);
        mainWindowView.setContent(ownerManagementView);
    }
    
    private void loadPetManagementView() {
        PetManagementView petManagementView = new PetManagementView();
        PetController petController = new PetController(petManagementView, petRepository, ownerRepository, mainWindowView);
        mainWindowView.setContent(petManagementView);
    }
    
    private void loadAppointmentsView() {
        AppointmentsView appointmentsView = new AppointmentsView();
        AppointmentsController appointmentsController = new AppointmentsController(
            appointmentsView,
            appointmentService,
            petRepository,
            userRepository,
            (JFrame) SwingUtilities.getWindowAncestor(mainWindowView),
            medicalAttentionRepository,  // ⭐ NUEVO
            treatmentRepository,         // ⭐ NUEVO
            medicalOrderRepository,      // ⭐ NUEVO
            mainWindowView,              // ⭐ NUEVO
            currentUser                  // ⭐ NUEVO
        );
        mainWindowView.setContent(appointmentsView);
    }
    
    private void loadMedicalHistoryView() {
        // TODO: Implementar en FASE 6
        JOptionPane.showMessageDialog(mainWindowView,
            "Vista de Historia Clínica en desarrollo.\nSe implementará en la siguiente fase.",
            "Próximamente",
            JOptionPane.INFORMATION_MESSAGE);
    }
}