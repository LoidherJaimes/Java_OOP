package clinica_vet.model.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MedicalOrder {
    private UUID id;
    private UUID medicalAttentionId;
    private OrderType orderType;
    private String description;
    private LocalDateTime requestedDate;
    private boolean completed;
    private String notes;
    private Double price;

    public MedicalOrder() {
        this.id = UUID.randomUUID();
        this.requestedDate = LocalDateTime.now();
        this.completed = false;
        this.notes = "";
        this.price = 0.0;

    }

    public MedicalOrder(UUID medicalAttentionId, OrderType orderType, String description) {
        this.id = UUID.randomUUID();
        this.medicalAttentionId = medicalAttentionId;
        this.orderType = orderType;
        this.description = description;
        this.requestedDate = LocalDateTime.now();
        this.completed = false;
        this.notes = "";
    }

    public MedicalOrder(UUID medicalAttentionId, OrderType orderType, 
                       String description, String notes) {
        this.id = UUID.randomUUID();
        this.medicalAttentionId = medicalAttentionId;
        this.orderType = orderType;
        this.description = description;
        this.requestedDate = LocalDateTime.now();
        this.completed = false;
        this.notes = notes;
        this.price = 0.0;

    }

    public MedicalOrder(UUID id, UUID medicalAttentionId, OrderType orderType,
                       String description, LocalDateTime requestedDate,
                       boolean completed, String notes) {
        this.id = id;
        this.medicalAttentionId = medicalAttentionId;
        this.orderType = orderType;
        this.description = description;
        this.requestedDate = requestedDate;
        this.completed = completed;
        this.notes = notes;
        this.price = 0.0;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMedicalAttentionId() {
        return medicalAttentionId;
    }

    public void setMedicalAttentionId(UUID medicalAttentionId) {
        this.medicalAttentionId = medicalAttentionId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFormattedRequestedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return requestedDate.format(formatter);
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return requestedDate.format(formatter);
    }

    public boolean isValid() {
        return orderType != null &&
               description != null && !description.trim().isEmpty();
    }

    public String getStatusText() {
        return completed ? "Completada" : "Pendiente";
    }

    public String getSummary() {
        return String.format("%s - %s (%s)", 
            orderType.getDisplayName(), 
            description.length() > 50 ? description.substring(0, 47) + "..." : description,
            getStatusText());
    }

    public void markAsCompleted(String completionNotes) {
        this.completed = true;
        if (completionNotes != null && !completionNotes.trim().isEmpty()) {
            this.notes = this.notes.isEmpty() ? completionNotes : this.notes + "\n" + completionNotes;
        }
    }

    public boolean isUrgent() {
        return orderType.isUrgentByDefault();
    }

    public boolean requiresAppointment() {
        return orderType.requiresAppointment();
    }

    @Override
    public String toString() {
        return getSummary();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MedicalOrder order = (MedicalOrder) obj;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}