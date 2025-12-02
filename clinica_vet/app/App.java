package clinica_vet.app;

import clinica_vet.controllers.LoginController;
import clinica_vet.controllers.MainWindowController;
import clinica_vet.model.entities.Appointment;
import clinica_vet.model.entities.AppointmentStatus;
import clinica_vet.model.entities.Owner;
import clinica_vet.model.entities.Pet;
import clinica_vet.model.entities.Rol;
import clinica_vet.model.entities.Sex;
import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.*;
import clinica_vet.views.LoginView;
import clinica_vet.views.MainWindowView;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.SwingUtilities;

public class App {
    
    private IRolService rolService; 
    private UserRepository userRepository;
    private OwnerRepository ownerRepository;
    private PetRepository petRepository;
    private IAppointmentRepository appointmentRepository;
    private AppointmentService appointmentService;

    private MedicalAttentionRepository medicalAttentionRepository;
    private TreatmentRepository treatmentRepository;
    private MedicalOrderRepository medicalOrderRepository;
    
    private InvoiceRepository invoiceRepository;
    private InvoiceService invoiceService; // <<< NUEVO: Declaración del Service
    
    private LoginView loginView;

    public App() {
        IRolRepository rolRepository = new RolRepository();
        this.rolService = new RolService(rolRepository); 
        this.userRepository = new UserRepository();
        this.ownerRepository = new OwnerRepository();
        this.petRepository = new PetRepository();
        this.appointmentRepository = new AppointmentRepository();
        this.appointmentService = new AppointmentService(appointmentRepository);
        
        this.invoiceRepository = new InvoiceRepository();
        this.invoiceService = new InvoiceService(invoiceRepository); // <<< CORRECCIÓN: Inicializar Service

        this.medicalAttentionRepository = new MedicalAttentionRepository();
        this.treatmentRepository = new TreatmentRepository();
        this.medicalOrderRepository = new MedicalOrderRepository();
        
        initializeData();
    }
    
    private void initializeData() {
        if (rolService.getAllRoles().isEmpty()) {
            rolService.addRol("Administrador");
            rolService.addRol("Auxiliar");
            rolService.addRol("Veterinario"); 
        }

        if (userRepository.getAllUsers().isEmpty()) {
            Rol rolAdmin = rolService.getRolByName("Administrador");
            Rol rolAux = rolService.getRolByName("Auxiliar");
            Rol rolVet = rolService.getRolByName("Veterinario");
            
            User admin = new User("admin", "1", rolAdmin);
            User aux = new User("aux", "1", rolAux);
            User vet1 = new User("Dr. Garcia", "1", rolVet);
            User vet2 = new User("Dra. Martinez", "1", rolVet);
            
            userRepository.addUser(admin);
            userRepository.addUser(aux);
            userRepository.addUser(vet1);
            userRepository.addUser(vet2);
        }

        if (petRepository.getAllPets().isEmpty()) {
    Pet pet1 = new Pet(
        "Firulais",
        "Perro",
        "Labrador",
        3,
        Sex.MALE,
        25,
        "Muy juguetón",
        Arrays.asList("Rabia", "Parvovirus"),
        Collections.singletonList("Ninguna")
    );

    Pet pet2 = new Pet(
        "Misu",
        "Gato",
        "Siames",
        2,
        Sex.FEMALE,
        5,
        "Le gusta dormir mucho",
        Arrays.asList("Triple felina"),
        Arrays.asList("Polvo")
    );

    if (ownerRepository.getAllOwners().isEmpty()) {
        Owner owner1 = new Owner("Juan Pérez", "555-1234", "Calle 10 #5-20");
        Owner owner2 = new Owner("Ana Gómez", "555-5678", "Av. Principal 45");
        ownerRepository.addOwner(owner1);
        ownerRepository.addOwner(owner2);

        owner1.addPet(pet1);
        owner2.addPet(pet2);

        pet1.setOwner(owner1);
        pet2.setOwner(owner2);
    }

    petRepository.addPet(pet1);
    petRepository.addPet(pet2);

    User vet1 = userRepository.getAllUsers().stream()
        .filter(u -> u.getUsername().equals("Dr. Garcia"))
        .findFirst()
        .orElse(null);

    User vet2 = userRepository.getAllUsers().stream()
        .filter(u -> u.getUsername().equals("Dra. Martinez"))
        .findFirst()
        .orElse(null);

    if (vet1 != null && vet2 != null) {

        Appointment appointment1 = new Appointment();
        appointment1.setDateTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));
        appointment1.setPet(pet1);
        appointment1.setDoctor(vet1);
        appointment1.setReason("Vacunación anual y chequeo general");
        appointment1.setStatus(AppointmentStatus.CONFIRMED);
        appointment1.setDurationMinutes(30);
        appointmentService.createAppointment(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setDateTime(LocalDateTime.now().plusDays(1).withHour(11).withMinute(0));
        appointment2.setPet(pet2);
        appointment2.setDoctor(vet2);
        appointment2.setReason("Control de peso y revisión de alergias");
        appointment2.setStatus(AppointmentStatus.PENDING);
        appointment2.setDurationMinutes(45);
        appointmentService.createAppointment(appointment2);

        Appointment appointment3 = new Appointment();
        appointment3.setDateTime(LocalDateTime.now().plusDays(3).withHour(15).withMinute(0));
        appointment3.setPet(pet1);
        appointment3.setDoctor(vet1);
        appointment3.setReason("Seguimiento post-vacunación");
        appointment3.setStatus(AppointmentStatus.PENDING);
        appointment3.setDurationMinutes(30);
        appointmentService.createAppointment(appointment3);
    }}
}
    
    public void startApplication() {
        if (loginView != null) {
            loginView.dispose();
        }
        loginView = new LoginView();
        
        LoginController loginController = new LoginController(loginView, userRepository, this::onLoginSuccess); 
        loginView.setVisible(true);
    }
    
    private void onLoginSuccess(User user) {
        loginView.dispose(); 
        MainWindowView mainWindowView = new MainWindowView();
        
        Runnable onLogoutAction = this::startApplication;
        
        MainWindowController mainWindowController = new MainWindowController(
            mainWindowView, 
            user, 
            userRepository, 
            rolService, 
            ownerRepository, 
            petRepository,
            appointmentService,
            medicalAttentionRepository,  
            treatmentRepository,         
            medicalOrderRepository,
            invoiceService, // <<< CORRECCIÓN: Pasar el InvoiceService
            onLogoutAction
        ); 
        
        mainWindowView.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.startApplication();
        });
    }
}
