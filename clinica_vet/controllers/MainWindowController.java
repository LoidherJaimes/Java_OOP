package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.*;
import clinica_vet.views.*;

import javax.swing.*;

public class MainWindowController {

    private final MainWindowView mainWindowView;
    private final User currentUser;

    private final UserRepository userRepository;
    private final IRolService rolService;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final AppointmentService appointmentService;

    private final MedicalAttentionRepository medicalAttentionRepository;
    private final TreatmentRepository treatmentRepository;
    private final MedicalOrderRepository medicalOrderRepository;

    private final InvoiceService invoiceService; 
    // Eliminar esta línea duplicada: private final OwnerRepository ownerRepositoryForBilling;

    private final Runnable onLogoutAction;

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
                                 InvoiceService invoiceService,
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

        this.invoiceService = invoiceService; 
        this.onLogoutAction = onLogoutAction;

        setupListeners();
        configureMenuByRole();
        mainWindowView.setContent(mainWindowView.getWelcomeView());
    }

    private void configureMenuByRole() {
        String rolName = currentUser.getRol().getName();
        boolean isAdmin = rolName.equalsIgnoreCase("Administrador");
        boolean isAux = rolName.equalsIgnoreCase("Auxiliar");
        boolean isMedico = rolName.equalsIgnoreCase("Medico");
    
        if (mainWindowView.getBtnUsers() != null) {
            mainWindowView.getBtnUsers().setVisible(isAdmin);
        }
    
        if (mainWindowView.getBtnHistory() != null) {
            mainWindowView.getBtnHistory().setVisible(isMedico);
        }
    
        if (mainWindowView.getBtnBillingAndPayments() != null) {
            mainWindowView.getBtnBillingAndPayments().setVisible(isAdmin || isAux);
        }
    }

    private void setupListeners() {
        this.mainWindowView.getBtnLogout().addActionListener(e -> onLogoutAction.run());

        this.mainWindowView.getBtnUsers().addActionListener(e -> loadUserManagementView());
        this.mainWindowView.getBtnOwners().addActionListener(e -> loadOwnerManagementView());
        this.mainWindowView.getBtnPets().addActionListener(e -> loadPetManagementView());
        this.mainWindowView.getBtnAppointment().addActionListener(e -> loadAppointmentsView());

        if (mainWindowView.getBtnReports() != null)
            mainWindowView.getBtnReports().addActionListener(e -> loadReportsView());

        if (mainWindowView.getBtnHistory() != null)
            mainWindowView.getBtnHistory().addActionListener(e -> loadMedicalHistoryView());
            
        if (mainWindowView.getBtnBillingAndPayments() != null) {
            mainWindowView.getBtnBillingAndPayments().addActionListener(e -> loadBillingAndPaymentsView());
        }
    }

    private void loadUserManagementView() {
        ManageUsersView v = new ManageUsersView();
        new ManageUsersController(v, userRepository, rolService, mainWindowView);
        mainWindowView.setContent(v);
    }

    private void loadOwnerManagementView() {
        OwnerManagementView v = new OwnerManagementView();
        new OwnerManagementController(v, ownerRepository, mainWindowView);
        mainWindowView.setContent(v);
    }

    private void loadPetManagementView() {
        PetManagementView v = new PetManagementView();
        new PetController(v, petRepository, ownerRepository, mainWindowView);
        mainWindowView.setContent(v);
    }

    private void loadAppointmentsView() {
        AppointmentsView v = new AppointmentsView();
        new AppointmentsController(
                v,
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
        mainWindowView.setContent(v);
    }

    private void loadMedicalHistoryView() {
        MedicalHistoryView v = new MedicalHistoryView();
        new MedicalHistoryController(
                v,
                mainWindowView,
                medicalAttentionRepository,
                treatmentRepository,
                medicalOrderRepository,
                petRepository,
                userRepository
        );
        mainWindowView.setContent(v);
    }

    private void loadReportsView() {
        ReportsView v = new ReportsView();
        new ReportsController(v, mainWindowView,
                appointmentService, petRepository,
                medicalOrderRepository, medicalAttentionRepository);
        mainWindowView.setContent(v);
    }
    
    private void loadBillingAndPaymentsView() {
        BillingAndPaymentsView v = new BillingAndPaymentsView();
        new BillingAndPaymentsController(
            v, 
            mainWindowView,
            invoiceService,
            ownerRepository  // Usar el mismo ownerRepository
        );
        mainWindowView.setContent(v);
    }
}
