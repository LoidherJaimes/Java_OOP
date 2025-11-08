package clinica_vet.controllers;

import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.IRolService;
import clinica_vet.model.repositories.OwnerRepository; // Importar nuevo repositorio
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.ManageUsersView;
import clinica_vet.views.OwnerManagementView;
import clinica_vet.views.ProfileView;
import clinica_vet.views.LogoutView;



public class MainWindowController {
    
    private MainWindowView mainWindowView;
    private User currentUser;
    private UserRepository userRepository;
    private IRolService rolService;
    private OwnerRepository ownerRepository; // ⭐ NUEVO REPOSITORIO
    private final Runnable onLogoutAction; 

    // ⭐ CONSTRUCTOR COMPLETO: Ahora incluye OwnerRepository
    public MainWindowController(MainWindowView mainWindowView, User currentUser, UserRepository userRepository, 
                                IRolService rolService, OwnerRepository ownerRepository, Runnable onLogoutAction) { 
        this.mainWindowView = mainWindowView;
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.rolService = rolService;
        this.ownerRepository = ownerRepository; // Asignar
        this.onLogoutAction = onLogoutAction;
        
        setupListeners();
        mainWindowView.setContent(mainWindowView.getWelcomeView());
    }

    private void setupListeners() {
        // Listener para Perfil (Carga la vista en el panel central)
        mainWindowView.getBtnProfile().addActionListener(e -> {
            loadProfileView();
        });

        // Listener para Cerrar Sesión (Carga la vista de confirmación)
        mainWindowView.getBtnLogout().addActionListener(e -> {
            loadLogoutView();
        });

        // Listener para Gestión de Usuarios
        mainWindowView.getBtnUsers().addActionListener(e -> {
            loadManageUsersView();
        });
        
        // ⭐ Listener para Gestión de Dueños
        mainWindowView.getBtnOwners().addActionListener(e -> {
            loadOwnerManagementView();
        });
        
        // TODO: Agregar listeners para los demás botones (Pets, Agenda, etc.)
    }
    
    // --------------------------------------------------------
    // MÉTODOS DE CARGA DE VISTAS EN EL PANEL CENTRAL
    // --------------------------------------------------------

    private void loadProfileView() {
        ProfileView profileView = new ProfileView(currentUser);
        mainWindowView.setContent(profileView);
    }
    
    private void loadLogoutView() {
        LogoutView logoutView = new LogoutView();
        mainWindowView.setContent(logoutView);
        
        logoutView.getBtnYes().addActionListener(e -> {
            // 1. Destruir la ventana principal
            mainWindowView.dispose(); 
            // 2. Ejecutar la acción inyectada que abre el Login
            onLogoutAction.run(); 
        });
        
        logoutView.getBtnNo().addActionListener(e -> {
            // Vuelve a la vista de bienvenida si cancela el cierre
            mainWindowView.setContent(mainWindowView.getWelcomeView());
        });
    }

    private void loadManageUsersView() {
        ManageUsersView manageUsersView = new ManageUsersView();
        new ManageUsersController(manageUsersView, userRepository, rolService, mainWindowView);
        mainWindowView.setContent(manageUsersView);
        
        manageUsersView.getBtnClose().addActionListener(e -> {
            mainWindowView.setContent(mainWindowView.getWelcomeView());
        });
    }
    
    // ⭐ NUEVO MÉTODO DE CARGA: Gestión de Dueños
    private void loadOwnerManagementView() {
        OwnerManagementView ownerManagementView = new OwnerManagementView();
        
        // Instanciar el controlador, pasando el OwnerRepository y la ventana principal
        new OwnerManagementController(ownerManagementView, ownerRepository, mainWindowView);
        
        // Establecer el JPanel de la vista en el área central
        mainWindowView.setContent(ownerManagementView);
    }
}