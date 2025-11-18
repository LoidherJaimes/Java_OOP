package clinica_vet.controllers;

import clinica_vet.model.entities.Appointment;
import clinica_vet.model.entities.AppointmentStatus;
import clinica_vet.model.entities.User;
import clinica_vet.model.repositories.AppointmentService;
import clinica_vet.model.repositories.PetRepository;
import clinica_vet.model.repositories.UserRepository;
import clinica_vet.views.AppointmentsView;
import clinica_vet.views.CreateAppointmentView;
import clinica_vet.views.EditAppointmentView;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AppointmentsController {

    private AppointmentsView view;
    private AppointmentService appointmentService;
    private PetRepository petRepository;
    private UserRepository userRepository;
    private JFrame parentFrame;

    public AppointmentsController(AppointmentsView view, 
                                 AppointmentService appointmentService,
                                 PetRepository petRepository,
                                 UserRepository userRepository, 
                                 JFrame parentFrame) {
        this.view = view;
        this.appointmentService = appointmentService;
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.parentFrame = parentFrame;

        initController();
        loadDoctorsFilter();
        loadAppointments();
    }

    private void initController() {
        // Create button
        view.getBtnCreate().addActionListener(e -> handleCreate());

        // Edit button
        view.getBtnEdit().addActionListener(e -> handleEdit());

        // Confirm button
        view.getBtnConfirm().addActionListener(e -> handleConfirm());

        // Cancel button
        view.getBtnCancel().addActionListener(e -> handleCancel());

        // Complete button
        view.getBtnComplete().addActionListener(e -> handleComplete());

        // Delete button
        view.getBtnDelete().addActionListener(e -> handleDelete());

        // Refresh button
        view.getBtnRefresh().addActionListener(e -> loadAppointments());

        // Apply filters button
        view.getBtnApplyFilters().addActionListener(e -> applyFilters());

        // Clear filters button
        view.getBtnClearFilters().addActionListener(e -> clearFilters());
    }

    private void handleCreate() {
        CreateAppointmentView createView = new CreateAppointmentView(parentFrame);
        new CreateAppointmentController(createView, appointmentService, petRepository, userRepository, this);
        createView.setVisible(true);
    }

    private void handleEdit() {
        String selectedId = view.getSelectedAppointmentId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view, 
                "Por favor, seleccione una cita para editar.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            UUID appointmentId = UUID.fromString(selectedId);
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
            
            if (appointment == null) {
                JOptionPane.showMessageDialog(view, 
                    "No se encontró la cita seleccionada.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            EditAppointmentView editView = new EditAppointmentView(parentFrame);
            new EditAppointmentController(editView, appointmentService, petRepository, userRepository, appointment, this);
            editView.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "Error al abrir el editor de cita: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleConfirm() {
        String selectedId = view.getSelectedAppointmentId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view, 
                "Por favor, seleccione una cita para confirmar.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, 
            "¿Está seguro de confirmar esta cita?", 
            "Confirmar Cita", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                UUID appointmentId = UUID.fromString(selectedId);
                boolean success = appointmentService.confirmAppointment(appointmentId);
                
                if (success) {
                    JOptionPane.showMessageDialog(view, 
                        "Cita confirmada exitosamente.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadAppointments();
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "No se puede confirmar esta cita. Verifique su estado actual.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, 
                    "Error al confirmar la cita: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleCancel() {
        String selectedId = view.getSelectedAppointmentId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view, 
                "Por favor, seleccione una cita para cancelar.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, 
            "¿Está seguro de cancelar esta cita?", 
            "Cancelar Cita", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                UUID appointmentId = UUID.fromString(selectedId);
                boolean success = appointmentService.cancelAppointment(appointmentId);
                
                if (success) {
                    JOptionPane.showMessageDialog(view, 
                        "Cita cancelada exitosamente.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadAppointments();
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "No se puede cancelar esta cita. Verifique su estado actual.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, 
                    "Error al cancelar la cita: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleComplete() {
        String selectedId = view.getSelectedAppointmentId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view, 
                "Por favor, seleccione una cita para completar.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, 
            "¿Está seguro de marcar esta cita como completada?", 
            "Completar Cita", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                UUID appointmentId = UUID.fromString(selectedId);
                boolean success = appointmentService.completeAppointment(appointmentId);
                
                if (success) {
                    JOptionPane.showMessageDialog(view, 
                        "Cita marcada como completada.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadAppointments();
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "No se puede completar esta cita. Debe estar confirmada primero.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, 
                    "Error al completar la cita: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleDelete() {
        String selectedId = view.getSelectedAppointmentId();
        if (selectedId == null) {
            JOptionPane.showMessageDialog(view, 
                "Por favor, seleccione una cita para eliminar.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, 
            "¿Está seguro de eliminar esta cita? Esta acción no se puede deshacer.", 
            "Eliminar Cita", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                UUID appointmentId = UUID.fromString(selectedId);
                boolean success = appointmentService.deleteAppointment(appointmentId);
                
                if (success) {
                    JOptionPane.showMessageDialog(view, 
                        "Cita eliminada exitosamente.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadAppointments();
                } else {
                    JOptionPane.showMessageDialog(view, 
                        "No se puede eliminar esta cita. Solo se pueden eliminar citas pendientes o canceladas.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, 
                    "Error al eliminar la cita: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadDoctorsFilter() {
        List<User> allUsers = userRepository.getAllUsers();
        List<String> doctorNames = allUsers.stream()
            .filter(u -> u.getRol() != null && u.getRol().getName().equalsIgnoreCase("Medico"))
            .map(User::getUsername)
            .collect(Collectors.toList());
        
        view.loadDoctors(doctorNames);
    }

    public void loadAppointments() {
        view.clearTable();
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        for (Appointment appointment : appointments) {
            String petName = appointment.getPet() != null ? appointment.getPet().getName() : "N/A";
            String ownerName = appointment.getPet() != null && appointment.getPet().getOwner() != null 
                ? appointment.getPet().getOwner().getName() 
                : "N/A";
            String doctorName = appointment.getDoctor() != null ? appointment.getDoctor().getUsername() : "N/A";
            String duration = appointment.getDurationMinutes() + " min";
            
            view.addAppointmentToTable(
                appointment.getId().toString(),
                appointment.getFormattedDate(),
                appointment.getFormattedTime(),
                petName,
                ownerName,
                doctorName,
                appointment.getReason(),
                duration,
                appointment.getStatus().getDisplayName()
            );
        }
    }

    private void applyFilters() {
        view.clearTable();
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        // Get filter values
        String selectedStatus = view.getSelectedStatus();
        String selectedDoctor = view.getSelectedDoctor();
        java.util.Date selectedDate = view.getSelectedDate();
        
        // Flags to determine which filters to apply
        boolean filterByStatus = !selectedStatus.equals("Todos");
        boolean filterByDoctor = !selectedDoctor.equals("Todos los Médicos");
        boolean filterByDate = true; // Date filter is always applied
        
        // Convert date to LocalDate for comparison
        LocalDate filterDate = selectedDate.toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();
        
        // Apply filters
        List<Appointment> filteredAppointments = appointments.stream()
            .filter(appointment -> {
                // Filter by status (optional)
                if (filterByStatus) {
                    String appointmentStatus = appointment.getStatus().getDisplayName();
                    if (!appointmentStatus.equals(selectedStatus)) {
                        return false;
                    }
                }
                
                // Filter by doctor (optional)
                if (filterByDoctor) {
                    if (appointment.getDoctor() == null || 
                        !appointment.getDoctor().getUsername().equals(selectedDoctor)) {
                        return false;
                    }
                }
                
                // Filter by date (always applied)
                if (filterByDate) {
                    LocalDate appointmentDate = appointment.getDateTime().toLocalDate();
                    if (!appointmentDate.equals(filterDate)) {
                        return false;
                    }
                }
                
                return true;
            })
            .sorted((a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime())) // Sort by time
            .collect(Collectors.toList());
        
        // Display filtered results
        if (filteredAppointments.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "No se encontraron citas con los filtros aplicados.\n" +
                "Filtros activos:\n" +
                "- Fecha: " + filterDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                (filterByStatus ? "\n- Estado: " + selectedStatus : "") +
                (filterByDoctor ? "\n- Médico: " + selectedDoctor : ""),
                "Sin Resultados", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Show count of filtered results
            String filterInfo = "Mostrando " + filteredAppointments.size() + " cita(s) para " + 
                               filterDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (filterByStatus) filterInfo += " - Estado: " + selectedStatus;
            if (filterByDoctor) filterInfo += " - Médico: " + selectedDoctor;
            
            view.setFilterInfo(filterInfo);
        }
        
        for (Appointment appointment : filteredAppointments) {
            String petName = appointment.getPet() != null ? appointment.getPet().getName() : "N/A";
            String ownerName = appointment.getPet() != null && appointment.getPet().getOwner() != null 
                ? appointment.getPet().getOwner().getName() 
                : "N/A";
            String doctorName = appointment.getDoctor() != null ? appointment.getDoctor().getUsername() : "N/A";
            String duration = appointment.getDurationMinutes() + " min";
            
            view.addAppointmentToTable(
                appointment.getId().toString(),
                appointment.getFormattedDate(),
                appointment.getFormattedTime(),
                petName,
                ownerName,
                doctorName,
                appointment.getReason(),
                duration,
                appointment.getStatus().getDisplayName()
            );
        }
    }

    private void clearFilters() {
        // Reset date to today
        view.getFilterDateSpinner().setValue(new java.util.Date());
        view.clearFilterInfo();
        loadAppointments();
    }
}