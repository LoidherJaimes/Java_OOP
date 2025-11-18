package clinica_vet.model.repositories;

import clinica_vet.model.entities.Appointment;
import clinica_vet.model.entities.AppointmentStatus;
import clinica_vet.model.entities.User;
import clinica_vet.model.entities.Pet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AppointmentService {
    
    private IAppointmentRepository appointmentRepository;

    public AppointmentService(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public boolean hasOverlap(User doctor, LocalDateTime start, LocalDateTime end, UUID excludeAppointmentId) {
        List<Appointment> doctorAppointments = appointmentRepository.getAppointmentsByDoctor(doctor);
        
        for (Appointment appointment : doctorAppointments) {
            // Ignore current appointment if we're editing
            if (excludeAppointmentId != null && appointment.getId().equals(excludeAppointmentId)) {
                continue;
            }
            
            // Ignore cancelled appointments
            if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
                continue;
            }
            
            LocalDateTime appointmentStart = appointment.getDateTime();
            LocalDateTime appointmentEnd = appointment.getEndDateTime();
            
            // Check for overlap
            if (start.isBefore(appointmentEnd) && end.isAfter(appointmentStart)) {
                return true; // There's an overlap
            }
        }
        
        return false; // No overlap
    }

    public boolean createAppointment(Appointment appointment) {
        if (appointment == null || appointment.getDoctor() == null || 
            appointment.getDateTime() == null) {
            return false;
        }

        LocalDateTime start = appointment.getDateTime();
        LocalDateTime end = appointment.getEndDateTime();
        
        if (hasOverlap(appointment.getDoctor(), start, end, null)) {
            return false; // Cannot create, there's an overlap
        }
        
        appointmentRepository.addAppointment(appointment);
        return true;
    }

    /**
     * Updates an existing appointment validating overlaps
     * @return true if updated successfully, false if there's an overlap
     */
    public boolean updateAppointment(Appointment appointment) {
        if (appointment == null || appointment.getId() == null || 
            appointment.getDoctor() == null || appointment.getDateTime() == null) {
            return false;
        }

        LocalDateTime start = appointment.getDateTime();
        LocalDateTime end = appointment.getEndDateTime();
        
        if (hasOverlap(appointment.getDoctor(), start, end, appointment.getId())) {
            return false; // Cannot update, there's an overlap
        }
        
        appointmentRepository.updateAppointment(appointment);
        return true;
    }

    public boolean cancelAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.getAppointmentById(appointmentId);
        if (appointment != null && appointment.isCancellable()) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            appointmentRepository.updateAppointment(appointment);
            return true;
        }
        return false;
    }

    public boolean confirmAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.getAppointmentById(appointmentId);
        if (appointment != null && appointment.isConfirmable()) {
            appointment.setStatus(AppointmentStatus.CONFIRMED);
            appointmentRepository.updateAppointment(appointment);
            return true;
        }
        return false;
    }

    public boolean completeAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.getAppointmentById(appointmentId);
        if (appointment != null && appointment.isCompletable()) {
            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepository.updateAppointment(appointment);
            return true;
        }
        return false;
    }

    public boolean deleteAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.getAppointmentById(appointmentId);
        if (appointment != null && 
            (appointment.getStatus() == AppointmentStatus.PENDING || 
             appointment.getStatus() == AppointmentStatus.CANCELLED)) {
            appointmentRepository.deleteAppointment(appointmentId);
            return true;
        }
        return false;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.getAllAppointments();
    }

    public Appointment getAppointmentById(UUID id) {
        return appointmentRepository.getAppointmentById(id);
    }

    public List<Appointment> getAppointmentsByDoctor(User doctor) {
        return appointmentRepository.getAppointmentsByDoctor(doctor);
    }

    public List<Appointment> getAppointmentsByPet(Pet pet) {
        return appointmentRepository.getAppointmentsByPet(pet);
    }

    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.getAppointmentsByDate(date);
    }

    public List<Appointment> getAppointmentsByDoctorAndDate(User doctor, LocalDate date) {
        return appointmentRepository.getAppointmentsByDoctorAndDate(doctor, date);
    }

    public List<Appointment> getAppointmentsByWeek(LocalDate startOfWeek) {
        return appointmentRepository.getAppointmentsByWeek(startOfWeek);
    }

    public List<Appointment> getActiveAppointments() {
        return appointmentRepository.getAllAppointments().stream()
                .filter(Appointment::isActive)
                .sorted((a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime()))
                .collect(Collectors.toList());
    }

    public List<Appointment> getUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        return appointmentRepository.getAllAppointments().stream()
                .filter(a -> a.isActive() && a.getDateTime().isAfter(now))
                .sorted((a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime()))
                .collect(Collectors.toList());
    }
}