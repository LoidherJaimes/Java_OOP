package clinica_vet.model.repositories;

import clinica_vet.model.entities.MedicalAttention;
import java.util.List;
import java.util.UUID;

public interface IMedicalAttentionRepository {
    
    void addAttention(MedicalAttention attention);
    
    void updateAttention(MedicalAttention attention);
    
    MedicalAttention getAttentionById(UUID id);
    
    MedicalAttention getAttentionByAppointmentId(UUID appointmentId);
    
    List<MedicalAttention> getAttentionsByPetId(UUID petId);
    
    List<MedicalAttention> getAttentionsByVeterinarianId(int veterinarianId);
    
    List<MedicalAttention> getAllAttentions();
    
    boolean deleteAttention(UUID id);
    
    boolean existsForAppointment(UUID appointmentId);
}