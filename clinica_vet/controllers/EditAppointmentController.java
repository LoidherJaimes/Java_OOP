package clinica_vet.controllers;

import clinica_vet.model.entities.Appointment;
import clinica_vet.model.entities.AppointmentStatus;
import clinica_vet.model.entities.Pet;
import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.AppointmentService;
import clinica_vet.model.repositories.PetRepository;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.EditAppointmentView;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EditAppointmentController {

    private EditAppointmentView view;
    private AppointmentService appointmentService;
    private PetRepository petRepository;
    private UserRepository userRepository;
    private Appointment appointment;
    private AppointmentsController parentController;
    
    private List<Pet> availablePets;
    private List<User> availableDoctors;

    public EditAppointmentController(EditAppointmentView view, 
                                    AppointmentService appointmentService,
                                    PetRepository petRepository,
                                    UserRepository userRepository,
                                    Appointment appointment,
                                    AppointmentsController parentController) {
        this.view = view;
        this.appointmentService = appointmentService;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.appointment = appointment;
        this.parentController = parentController;

        initController();
        loadData();
        loadAppointmentData();
    }

    private void initController() {
        // Update button
        view.getBtnUpdate().addActionListener(e -> handleUpdate());

        // Cancel button
        view.getBtnCancel().addActionListener(e -> view.dispose());
    }

    private void loadData() {
        // Load pets from repository
        availablePets = petRepository.getAllPets();
        List<String> petDisplayNames = availablePets.stream()
            .map(pet -> pet.getName() + " - " + pet.getSpecies() + " (" + pet.getRace() + ")")
            .collect(Collectors.toList());
        view.loadPets(petDisplayNames);
        
        // Load doctors from repository
        availableDoctors = userRepository.getAllUsers().stream()
            .filter(u -> u.getRol() != null && u.getRol().getName().equalsIgnoreCase("Medico"))
            .collect(Collectors.toList());
        List<String> doctorNames = availableDoctors.stream()
            .map(User::getUsername)
            .collect(Collectors.toList());
        view.loadDoctors(doctorNames);
    }

    private void loadAppointmentData() {
        // Store appointment ID
        view.setAppointmentId(appointment.getId().toString());

        // Load pet
        if (appointment.getPet() != null) {
            String petDisplay = appointment.getPet().getName() + " - " + 
                               appointment.getPet().getSpecies() + " (" + 
                               appointment.getPet().getRace() + ")";
            view.setSelectedPet(petDisplay);
        }

        // Load doctor
        if (appointment.getDoctor() != null) {
            view.setSelectedDoctor(appointment.getDoctor().getUsername());
        }

        // Load date and time
        LocalDateTime dateTime = appointment.getDateTime();
        Date date = Date.from(dateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date time = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        
        view.setDate(date);
        view.setTime(time);

        // Load duration
        view.setDuration(appointment.getDurationMinutes());

        // Load reason
        view.setReason(appointment.getReason());

        // Load status
        view.setStatus(appointment.getStatus().getDisplayName());
    }

    private void handleUpdate() {
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
            String statusText = view.getSelectedStatus();

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

            // Convert status text to enum
            AppointmentStatus status = convertStatusTextToEnum(statusText);

            // Update appointment object
            appointment.setDateTime(dateTime);
            appointment.setPet(pet);
            appointment.setDoctor(doctor);
            appointment.setReason(reason);
            appointment.setStatus(status);
            appointment.setDurationMinutes(duration);

            // Attempt to update appointment
            boolean success = appointmentService.updateAppointment(appointment);

            if (success) {
                JOptionPane.showMessageDialog(view, 
                    "Cita actualizada exitosamente.", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh parent view
                parentController.loadAppointments();
                
                // Close dialog
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "No se puede actualizar la cita. Hay un conflicto de horarios con otra cita del médico " + doctor.getUsername() + ".", 
                    "Error de Horario", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "Error al actualizar la cita: " + ex.getMessage(), 
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

        // Validate status
        if (view.getSelectedStatus() == null || view.getSelectedStatus().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "Por favor, seleccione un estado.", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private AppointmentStatus convertStatusTextToEnum(String statusText) {
        switch (statusText) {
            case "Pendiente":
                return AppointmentStatus.PENDING;
            case "Confirmada":
                return AppointmentStatus.CONFIRMED;
            case "Cancelada":
                return AppointmentStatus.CANCELLED;
            case "Completada":
                return AppointmentStatus.COMPLETED;
            default:
                return AppointmentStatus.PENDING;
        }
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