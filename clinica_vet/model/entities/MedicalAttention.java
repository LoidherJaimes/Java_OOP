package clinica_vet.model.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MedicalAttention {
    private UUID id;
    private UUID appointmentId;
    private UUID petId;
    private UUID veterinarianId;
    private LocalDateTime dateTime;
    private String symptoms;
    private String diagnosis;
    private String procedures;
    private AttentionStatus status;
    private LocalDateTime closureDate;
    private String closureNotes;

    public MedicalAttention() {
        this.id = UUID.randomUUID();
        this.status = AttentionStatus.IN_PROGRESS;
        this.dateTime = LocalDateTime.now();
    }

    public MedicalAttention(UUID appointmentId, UUID petId, UUID veterinarianId) {
        this.id = UUID.randomUUID();
        this.appointmentId = appointmentId;
        this.petId = petId;
        this.veterinarianId = veterinarianId;
        this.dateTime = LocalDateTime.now();
        this.status = AttentionStatus.IN_PROGRESS;
        this.symptoms = "";
        this.diagnosis = "";
        this.procedures = "";
        this.closureNotes = "";
    }

    public MedicalAttention(UUID id, UUID appointmentId, UUID petId, UUID veterinarianId,
                           LocalDateTime dateTime, String symptoms, String diagnosis,
                           String procedures, AttentionStatus status,
                           LocalDateTime closureDate, String closureNotes) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.petId = petId;
        this.veterinarianId = veterinarianId;
        this.dateTime = dateTime;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.procedures = procedures;
        this.status = status;
        this.closureDate = closureDate;
        this.closureNotes = closureNotes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }

    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public UUID getVeterinarianId() {
        return veterinarianId;
    }

    public void setVeterinarianId(UUID veterinarianId) {
        this.veterinarianId = veterinarianId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getProcedures() {
        return procedures;
    }

    public void setProcedures(String procedures) {
        this.procedures = procedures;
    }

    public AttentionStatus getStatus() {
        return status;
    }

    public void setStatus(AttentionStatus status) {
        this.status = status;
    }

    public LocalDateTime getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(LocalDateTime closureDate) {
        this.closureDate = closureDate;
    }

    public String getClosureNotes() {
        return closureNotes;
    }

    public void setClosureNotes(String closureNotes) {
        this.closureNotes = closureNotes;
    }

    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(formatter);
    }

    public String getFormattedClosureDate() {
        if (closureDate == null) {
            return "No cerrada";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return closureDate.format(formatter);
    }

    public boolean isComplete() {
        return symptoms != null && !symptoms.trim().isEmpty() &&
               diagnosis != null && !diagnosis.trim().isEmpty();
    }

    public boolean canBeClosed() {
        return status == AttentionStatus.IN_PROGRESS && isComplete();
    }

    public boolean isClosed() {
        return closureDate != null;
    }

    public void close(AttentionStatus finalStatus, String notes) {
        if (finalStatus == AttentionStatus.IN_PROGRESS) {
            throw new IllegalArgumentException("Cannot close attention with IN_PROGRESS status");
        }
        this.status = finalStatus;
        this.closureDate = LocalDateTime.now();
        this.closureNotes = notes != null ? notes : "";
    }

    @Override
    public String toString() {
        return "Atención #" + id.toString().substring(0, 8) + 
               " - " + getFormattedDateTime() + 
               " - " + status.getDisplayName();
    }
}