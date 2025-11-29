package clinica_vet.model.repositories;

import clinica_vet.model.entities.Treatment;
import java.util.List;
import java.util.UUID;

public interface ITreatmentRepository {
    
    void addTreatment(Treatment treatment);
    
    void updateTreatment(Treatment treatment);
    
    Treatment getTreatmentById(UUID id);
    
    List<Treatment> getTreatmentsByAttentionId(UUID medicalAttentionId);
    
    List<Treatment> getAllTreatments();
    
    boolean deleteTreatment(UUID id);
    
    int deleteTreatmentsByAttentionId(UUID medicalAttentionId);
}