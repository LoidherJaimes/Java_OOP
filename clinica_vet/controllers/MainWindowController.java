package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.*;

import clinica_vet.views.AppointmentsView;
import clinica_vet.views.BillingAndPaymentsView;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.ManageUsersView;
import clinica_vet.views.MedicalHistoryView;
import clinica_vet.views.OwnerManagementView;
import clinica_vet.views.PetManagementView;
import clinica_vet.views.ReportsView;

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
                               MedicalAttentionRepository medicalAttentionRepository, 
                               TreatmentRepository treatmentRepository,               
                               MedicalOrderRepository medicalOrderRepository,         
                               Runnable onLogoutAction) {
        this.mainWindowView = mainWindowView;
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.rolService = rolService;
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.appointmentService = appointmentService;
        this.medicalAttentionRepository = medicalAttentionRepository;
        this.onLogoutAction = onLogoutAction;
        this.treatmentRepository = new TreatmentRepository();
        this.medicalOrderRepository = new MedicalOrderRepository();
        
        setupListeners();
        configureMenuByRole();
        mainWindowView.setContent(mainWindowView.getWelcomeView());
    }
    
// Dentro de MainWindowController.java

// En MainWindowController.java

private void configureMenuByRole() {
    boolean isAdmin = currentUser.getRol() != null && 
                      currentUser.getRol().getName().equalsIgnoreCase("Administrador");
    boolean isVeterinarian = currentUser.getRol() != null && 
                             currentUser.getRol().getName().equalsIgnoreCase("Veterinario");
    boolean isAuxiliary = currentUser.getRol() != null && 
                          currentUser.getRol().getName().equalsIgnoreCase("Auxiliar");
    
    // Visibilidad por Rol
    mainWindowView.getBtnUsers().setVisible(isAdmin);
    
    // Botón de Historia Clínica (Visible para Veterinario O Administrador)
    if (mainWindowView.getBtnHistory() != null) {
        mainWindowView.getBtnHistory().setVisible(isVeterinarian || isAdmin); 
    }
    
    // Botón de Facturación y Pagos (Visible para Auxiliar O Administrador)
    if (mainWindowView.getBtnBillingAndPayments() != null) {
        // CORRECCIÓN CLAVE: Agregamos 'isAdmin' con el OR lógico (||)
        mainWindowView.getBtnBillingAndPayments().setVisible(isAuxiliary || isAdmin); 
    }
    
    // Botón de Reportes (Visible para todos)
}

    private void setupListeners() {
    this.mainWindowView.getBtnLogout().addActionListener(e -> onLogoutAction.run());

    this.mainWindowView.getBtnUsers().addActionListener(e -> loadUserManagementView());

    this.mainWindowView.getBtnOwners().addActionListener(e -> loadOwnerManagementView());

    this.mainWindowView.getBtnPets().addActionListener(e -> loadPetManagementView());
    
    this.mainWindowView.getBtnAppointment().addActionListener(e -> loadAppointmentsView());
    
    // Nuevo Listener para Reportes (Visible para todos)
    if (this.mainWindowView.getBtnReports() != null) {
        this.mainWindowView.getBtnReports().addActionListener(e -> loadReportsView()); 
    }
    
    // Nuevo Listener para Facturación y Pagos (Solo Auxiliar)
    if (this.mainWindowView.getBtnBillingAndPayments() != null) {
        this.mainWindowView.getBtnBillingAndPayments().addActionListener(e -> loadBillingAndPaymentsView()); 
    }
    
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
            medicalAttentionRepository,  
            treatmentRepository,         
            medicalOrderRepository,      
            mainWindowView,              
            currentUser                  
        );
        mainWindowView.setContent(appointmentsView);
    }
    
    private void loadMedicalHistoryView() {
        MedicalHistoryView historyView = new MedicalHistoryView();
        MedicalHistoryController historyController = new MedicalHistoryController(
            historyView,
            mainWindowView,
            medicalAttentionRepository,
            treatmentRepository,
            medicalOrderRepository,
            petRepository,
            userRepository
        );
        mainWindowView.setContent(historyView);
    }
    // AGREGAR ESTOS MÉTODOS A MainWindowController.java

private void loadReportsView() {
    ReportsView reportsView = new ReportsView();
    // Pasa los repositorios que el ReportsController necesite.
    // Aquí solo se pasa la vista principal por ahora.
    ReportsController reportsController = new ReportsController(reportsView, mainWindowView); 
    mainWindowView.setContent(reportsView);
}

private void loadBillingAndPaymentsView() {
    BillingAndPaymentsView billingAndPaymentsView = new BillingAndPaymentsView();
    // Pasa los repositorios que el BillingAndPaymentsController necesite.
    BillingAndPaymentsController billingAndPaymentsController = new BillingAndPaymentsController(billingAndPaymentsView, mainWindowView);
    mainWindowView.setContent(billingAndPaymentsView);
}
}