package clinica_vet.model.entities;

import java.util.UUID;

public class Treatment {
    private UUID id;
    private UUID medicalAttentionId;
    private String medication;
    private String dosage;
    private String frequency;
    private String duration;
    private String instructions; 

    public Treatment() {
        this.id = UUID.randomUUID();
        this.instructions = "";
    }

    public Treatment(UUID medicalAttentionId, String medication, String dosage, 
                    String frequency, String duration) {
        this.id = UUID.randomUUID();
        this.medicalAttentionId = medicalAttentionId;
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.instructions = "";
    }

    public Treatment(UUID medicalAttentionId, String medication, String dosage,
                    String frequency, String duration, String instructions) {
        this.id = UUID.randomUUID();
        this.medicalAttentionId = medicalAttentionId;
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.instructions = instructions;
    }

    public Treatment(UUID id, UUID medicalAttentionId, String medication,
                    String dosage, String frequency, String duration, String instructions) {
        this.id = id;
        this.medicalAttentionId = medicalAttentionId;
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.instructions = instructions;
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

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isValid() {
        return medication != null && !medication.trim().isEmpty() &&
               dosage != null && !dosage.trim().isEmpty() &&
               frequency != null && !frequency.trim().isEmpty() &&
               duration != null && !duration.trim().isEmpty();
    }

    public String getSummary() {
        return String.format("%s - %s - %s", medication, dosage, frequency);
    }

    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Medicamento: ").append(medication).append("\n");
        sb.append("Dosis: ").append(dosage).append("\n");
        sb.append("Frecuencia: ").append(frequency).append("\n");
        sb.append("Duración: ").append(duration);
        
        if (instructions != null && !instructions.trim().isEmpty()) {
            sb.append("\nInstrucciones: ").append(instructions);
        }
        
        return sb.toString();
    }

    public Treatment copyForAttention(UUID newAttentionId) {
        return new Treatment(newAttentionId, medication, dosage, 
                           frequency, duration, instructions);
    }

    @Override
    public String toString() {
        return getSummary();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Treatment treatment = (Treatment) obj;
        return id.equals(treatment.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}