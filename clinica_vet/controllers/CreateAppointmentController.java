package clinica_vet.controllers;

import clinica_vet.model.entities.Appointment;
import clinica_vet.model.entities.AppointmentStatus;
import clinica_vet.model.entities.Pet;
import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.AppointmentService;
import clinica_vet.model.repositories.PetRepository;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.CreateAppointmentView;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CreateAppointmentController {

    private CreateAppointmentView view;
    private AppointmentService appointmentService;
    private PetRepository petRepository;
    private UserRepository userRepository;
    private AppointmentsController parentController;
    
    private List<Pet> availablePets;
    private List<User> availableDoctors;

    public CreateAppointmentController(CreateAppointmentView view, 
                                      AppointmentService appointmentService,
                                      PetRepository petRepository,
                                      UserRepository userRepository,
                                      AppointmentsController parentController) {
        this.view = view;
        this.appointmentService = appointmentService;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.parentController = parentController;

        initController();
        loadData();
    }

    private void initController() {
        // Create button
        view.getBtnCreate().addActionListener(e -> handleCreate());

        // Cancel button
        view.getBtnCancel().addActionListener(e -> view.dispose());
    }

    private void loadData() {
        // Load pets from repository
        availablePets = petRepository.getAllPets();
        
        if (availablePets.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "No hay mascotas registradas. Por favor, registre una mascota primero.", 
                "Sin Mascotas", 
                JOptionPane.WARNING_MESSAGE);
            view.dispose();
            return;
        }
        
        List<String> petDisplayNames = availablePets.stream()
            .map(pet -> pet.getName() + " - " + pet.getSpecies() + " (" + pet.getRace() + ")")
            .collect(Collectors.toList());
        view.loadPets(petDisplayNames);
        
        // Load doctors from repository
        availableDoctors = userRepository.getAllUsers().stream()
            .filter(u -> u.getRol() != null && u.getRol().getName().equalsIgnoreCase("Veterinario"))
            .collect(Collectors.toList());
            
        if (availableDoctors.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "No hay médicos registrados. Por favor, registre un médico primero.", 
                "Sin Médicos", 
                JOptionPane.WARNING_MESSAGE);
            view.dispose();
            return;
        }
        
        List<String> doctorNames = availableDoctors.stream()
            .map(User::getUsername)
            .collect(Collectors.toList());
        view.loadDoctors(doctorNames);
    }

    private void handleCreate() {
        // Validate fields
        if (!validateFields()) {
            return;
        }

        try {
            // Get data from view
            String selectedPetDisplay = view.getSelectedPet();
            String selectedDoctorName = view.getSelectedDoctor();
            Date date = view.getSelectedDate();
            Date time = view.getSelectedTime();
            int duration = view.getDuration();
            String reason = view.getReason();

            // Convert Date to LocalDateTime
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime localTime = time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);

            // Find actual Pet and User objects
            Pet pet = findPetByDisplayName(selectedPetDisplay);
            User doctor = findDoctorByUsername(selectedDoctorName);
            
            if (pet == null) {
                JOptionPane.showMessageDialog(view, 
                    "No se pudo encontrar la mascota seleccionada.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (doctor == null) {
                JOptionPane.showMessageDialog(view, 
                    "No se pudo encontrar el médico seleccionado.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create appointment object
            Appointment appointment = new Appointment();
            appointment.setDateTime(dateTime);
            appointment.setPet(pet);
            appointment.setDoctor(doctor);
            appointment.setReason(reason);
            appointment.setStatus(AppointmentStatus.PENDING);
            appointment.setDurationMinutes(duration);

            // Attempt to create appointment
            boolean success = appointmentService.createAppointment(appointment);

            if (success) {
                JOptionPane.showMessageDialog(view, 
                    "Cita creada exitosamente para " + pet.getName() + ".", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh parent view
                parentController.loadAppointments();
                
                // Close dialog
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "No se puede crear la cita. Hay un conflicto de horarios con otra cita del médico " + doctor.getUsername() + ".", 
                    "Error de Horario", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "Error al crear la cita: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private boolean validateFields() {
        // Validate pet selection
        if (view.getSelectedPet() == null || view.getSelectedPet().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Por favor, seleccione una mascota.", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validate doctor selection
        if (view.getSelectedDoctor() == null || view.getSelectedDoctor().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Por favor, seleccione un médico.", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validate reason
        if (view.getReason() == null || view.getReason().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Por favor, ingrese el motivo de la consulta.", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validate date is not in the past
        Date selectedDate = view.getSelectedDate();
        Date selectedTime = view.getSelectedTime();
        LocalDate date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime time = selectedTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);
        
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            JOptionPane.showMessageDialog(view, 
                "No se puede crear una cita en el pasado.", 
                "Fecha Inválida", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private Pet findPetByDisplayName(String displayName) {
        // Display format: "Name - Species (Race)"
        String petName = displayName.split(" - ")[0].trim();
        return availablePets.stream()
            .filter(p -> p.getName().equals(petName))
            .findFirst()
            .orElse(null);
    }

    private User findDoctorByUsername(String username) {
        return availableDoctors.stream()
            .filter(d -> d.getUsername().equals(username))
            .findFirst()
            .orElse(null);
    }
}