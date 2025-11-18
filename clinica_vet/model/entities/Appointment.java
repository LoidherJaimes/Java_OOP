package clinica_vet.model.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Appointment {
    private UUID id;
    private LocalDateTime dateTime;
    private Pet pet;
    private User doctor; // User with "Medico" role
    private String reason;
    private AppointmentStatus status;
    private int durationMinutes; // Estimated appointment duration

    // Empty constructor
    public Appointment() {
        this.id = UUID.randomUUID();
        this.status = AppointmentStatus.PENDING;
        this.durationMinutes = 30; // Default duration: 30 minutes
    }

    // Full constructor
    public Appointment(UUID id, LocalDateTime dateTime, Pet pet, User doctor, 
                      String reason, AppointmentStatus status, int durationMinutes) {
        this.id = id;
        this.dateTime = dateTime;
        this.pet = pet;
        this.doctor = doctor;
        this.reason = reason;
        this.status = status;
        this.durationMinutes = durationMinutes;
    }

    // Constructor without ID (will be generated)
    public Appointment(LocalDateTime dateTime, Pet pet, User doctor, 
                      String reason, AppointmentStatus status, int durationMinutes) {
        this.id = UUID.randomUUID();
        this.dateTime = dateTime;
        this.pet = pet;
        this.doctor = doctor;
        this.reason = reason;
        this.status = status;
        this.durationMinutes = durationMinutes;
    }

    // Getters and Setters
    public UUID getId() { 
        return id; 
    }
    
    public void setId(UUID id) { 
        this.id = id; 
    }

    public LocalDateTime getDateTime() { 
        return dateTime; 
    }
    
    public void setDateTime(LocalDateTime dateTime) { 
        this.dateTime = dateTime; 
    }

    public Pet getPet() { 
        return pet; 
    }
    
    public void setPet(Pet pet) { 
        this.pet = pet; 
    }

    public User getDoctor() { 
        return doctor; 
    }
    
    public void setDoctor(User doctor) { 
        this.doctor = doctor; 
    }

    public String getReason() { 
        return reason; 
    }
    
    public void setReason(String reason) { 
        this.reason = reason; 
    }

    public AppointmentStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(AppointmentStatus status) { 
        this.status = status; 
    }

    public int getDurationMinutes() { 
        return durationMinutes; 
    }
    
    public void setDurationMinutes(int durationMinutes) { 
        this.durationMinutes = durationMinutes; 
    }

    // Utility methods
    public LocalDateTime getEndDateTime() {
        return dateTime.plusMinutes(durationMinutes);
    }

    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(formatter);
    }

    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }

    public boolean isActive() {
        return status == AppointmentStatus.PENDING || status == AppointmentStatus.CONFIRMED;
    }

    public boolean isCancellable() {
        return status == AppointmentStatus.PENDING || status == AppointmentStatus.CONFIRMED;
    }

    public boolean isConfirmable() {
        return status == AppointmentStatus.PENDING;
    }

    public boolean isCompletable() {
        return status == AppointmentStatus.CONFIRMED;
    }

    @Override
    public String toString() {
        return "Appointment #" + id.toString().substring(0, 8) + " - " + getFormattedDateTime() + 
               " - " + (pet != null ? pet.getName() : "No pet") + 
               " - " + status.getDisplayName();
    }
}