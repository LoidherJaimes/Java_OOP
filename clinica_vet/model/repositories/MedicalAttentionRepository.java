package clinica_vet.model.repositories;

import clinica_vet.model.entities.MedicalAttention;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MedicalAttentionRepository implements IMedicalAttentionRepository {
    
    private List<MedicalAttention> attentions;
    
    public MedicalAttentionRepository() {
        this.attentions = new ArrayList<>();
    }
    
    @Override
    public void addAttention(MedicalAttention attention) {
        if (attention == null) {
            throw new IllegalArgumentException("Medical attention cannot be null");
        }
        attentions.add(attention);
    }
    
    @Override
    public void updateAttention(MedicalAttention attention) {
        if (attention == null) {
            throw new IllegalArgumentException("Medical attention cannot be null");
        }
        
        for (int i = 0; i < attentions.size(); i++) {
            if (attentions.get(i).getId().equals(attention.getId())) {
                attentions.set(i, attention);
                return;
            }
        }
    }
    
    @Override
    public MedicalAttention getAttentionById(UUID id) {
        if (id == null) {
            return null;
        }
        
        for (MedicalAttention attention : attentions) {
            if (attention.getId().equals(id)) {
                return attention;
            }
        }
        return null;
    }
    
    @Override
    public MedicalAttention getAttentionByAppointmentId(UUID appointmentId) {
        if (appointmentId == null) {
            return null;
        }
        
        for (MedicalAttention attention : attentions) {
            if (attention.getAppointmentId() != null && 
                attention.getAppointmentId().equals(appointmentId)) {
                return attention;
            }
        }
        return null;
    }
    
    @Override
    public List<MedicalAttention> getAttentionsByPetId(UUID petId) {
        if (petId == null) {
            return new ArrayList<>();
        }
        
        return attentions.stream()
            .filter(attention -> attention.getPetId() != null && 
                               attention.getPetId().equals(petId))
            .sorted(Comparator.comparing(MedicalAttention::getDateTime).reversed())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<MedicalAttention> getAttentionsByVeterinarianId(UUID veterinarianId) {
        return attentions.stream()
            .filter(attention -> attention.getVeterinarianId() == veterinarianId)
            .sorted(Comparator.comparing(MedicalAttention::getDateTime).reversed())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<MedicalAttention> getAllAttentions() {
        return new ArrayList<>(attentions);
    }
    
    @Override
    public boolean deleteAttention(UUID id) {
        if (id == null) {
            return false;
        }
        
        return attentions.removeIf(attention -> attention.getId().equals(id));
    }
    
    @Override
    public boolean existsForAppointment(UUID appointmentId) {
        if (appointmentId == null) {
            return false;
        }
        
        return attentions.stream()
            .anyMatch(attention -> attention.getAppointmentId() != null && 
                                 attention.getAppointmentId().equals(appointmentId));
    }
}