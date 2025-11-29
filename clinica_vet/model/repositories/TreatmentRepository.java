package clinica_vet.model.repositories;

import clinica_vet.model.entities.Treatment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TreatmentRepository implements ITreatmentRepository {
    
    private List<Treatment> treatments;
    
    public TreatmentRepository() {
        this.treatments = new ArrayList<>();
    }
    
    @Override
    public void addTreatment(Treatment treatment) {
        if (treatment == null) {
            throw new IllegalArgumentException("Treatment cannot be null");
        }
        treatments.add(treatment);
    }
    
    @Override
    public void updateTreatment(Treatment treatment) {
        if (treatment == null) {
            throw new IllegalArgumentException("Treatment cannot be null");
        }
        
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getId().equals(treatment.getId())) {
                treatments.set(i, treatment);
                return;
            }
        }
    }
    
    @Override
    public Treatment getTreatmentById(UUID id) {
        if (id == null) {
            return null;
        }
        
        for (Treatment treatment : treatments) {
            if (treatment.getId().equals(id)) {
                return treatment;
            }
        }
        return null;
    }
    
    @Override
    public List<Treatment> getTreatmentsByAttentionId(UUID medicalAttentionId) {
        if (medicalAttentionId == null) {
            return new ArrayList<>();
        }
        
        return treatments.stream()
            .filter(treatment -> treatment.getMedicalAttentionId() != null && 
                               treatment.getMedicalAttentionId().equals(medicalAttentionId))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Treatment> getAllTreatments() {
        return new ArrayList<>(treatments);
    }
    
    @Override
    public boolean deleteTreatment(UUID id) {
        if (id == null) {
            return false;
        }
        
        return treatments.removeIf(treatment -> treatment.getId().equals(id));
    }
    
    @Override
    public int deleteTreatmentsByAttentionId(UUID medicalAttentionId) {
        if (medicalAttentionId == null) {
            return 0;
        }
        
        List<Treatment> toRemove = treatments.stream()
            .filter(treatment -> treatment.getMedicalAttentionId() != null && 
                               treatment.getMedicalAttentionId().equals(medicalAttentionId))
            .collect(Collectors.toList());
        
        int removedCount = toRemove.size();
        treatments.removeAll(toRemove);
        
        return removedCount;
    }
}