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

        // NO CREES nuevos repos, usa los que llegan por el constructor
        this.treatmentRepository = treatmentRepository;
        this.medicalOrderRepository = medicalOrderRepository;

        setupListeners();
        configureMenuByRole();
        mainWindowView.setContent(mainWindowView.getWelcomeView());
    }


    private void configureMenuByRole() {
        boolean isAdmin = currentUser.getRol() != null &&
                currentUser.getRol().getName().equalsIgnoreCase("Administrador");
        boolean isVeterinarian = currentUser.getRol() != null &&
                currentUser.getRol().getName().equalsIgnoreCase("Veterinario");
        boolean isAuxiliary = currentUser.getRol() != null &&
                currentUser.getRol().getName().equalsIgnoreCase("Auxiliar");

        mainWindowView.getBtnUsers().setVisible(isAdmin);

        if (mainWindowView.getBtnHistory() != null) {
            mainWindowView.getBtnHistory().setVisible(isVeterinarian || isAdmin);
        }

        if (mainWindowView.getBtnBillingAndPayments() != null) {
            mainWindowView.getBtnBillingAndPayments().setVisible(isAuxiliary || isAdmin);
        }

        // Reportes visible para todos, no hay filtros
    }


    private void setupListeners() {

        this.mainWindowView.getBtnLogout().addActionListener(e -> onLogoutAction.run());

        this.mainWindowView.getBtnUsers().addActionListener(e -> loadUserManagementView());
        this.mainWindowView.getBtnOwners().addActionListener(e -> loadOwnerManagementView());
        this.mainWindowView.getBtnPets().addActionListener(e -> loadPetManagementView());
        this.mainWindowView.getBtnAppointment().addActionListener(e -> loadAppointmentsView());

        if (this.mainWindowView.getBtnReports() != null) {
            this.mainWindowView.getBtnReports().addActionListener(e -> loadReportsView());
        }

        if (this.mainWindowView.getBtnBillingAndPayments() != null) {
            this.mainWindowView.getBtnBillingAndPayments().addActionListener(e -> loadBillingAndPaymentsView());
        }

        if (this.mainWindowView.getBtnHistory() != null) {
            this.mainWindowView.getBtnHistory().addActionListener(e -> loadMedicalHistoryView());
        }
    }


    private void loadUserManagementView() {
        ManageUsersView manageUsersView = new ManageUsersView();
        new ManageUsersController(manageUsersView, userRepository, rolService, mainWindowView);
        mainWindowView.setContent(manageUsersView);
    }

    private void loadOwnerManagementView() {
        OwnerManagementView view = new OwnerManagementView();
        new OwnerManagementController(view, ownerRepository, mainWindowView);
        mainWindowView.setContent(view);
    }

    private void loadPetManagementView() {
        PetManagementView view = new PetManagementView();
        new PetController(view, petRepository, ownerRepository, mainWindowView);
        mainWindowView.setContent(view);
    }

    private void loadAppointmentsView() {
        AppointmentsView view = new AppointmentsView();
        new AppointmentsController(
                view,
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
        mainWindowView.setContent(view);
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
        ReportsView view = new ReportsView();

        ReportsController controller = new ReportsController(
                view,
                mainWindowView,
                appointmentService,
                petRepository,
                medicalOrderRepository,
                medicalAttentionRepository
        );

        mainWindowView.setContent(view);
    }


    private void loadBillingAndPaymentsView() {
        BillingAndPaymentsView view = new BillingAndPaymentsView();
        new BillingAndPaymentsController(view, mainWindowView);
        mainWindowView.setContent(view);
    }
}
