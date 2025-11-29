package clinica_vet.model.repositories;

import clinica_vet.model.entities.MedicalOrder;
import java.util.List;
import java.util.UUID;

public interface IMedicalOrderRepository {
    
    void addOrder(MedicalOrder order);
    
    void updateOrder(MedicalOrder order);
    
    MedicalOrder getOrderById(UUID id);
    
    List<MedicalOrder> getOrdersByAttentionId(UUID medicalAttentionId);
    
    List<MedicalOrder> getPendingOrders();
    
    List<MedicalOrder> getCompletedOrders();
    
    List<MedicalOrder> getAllOrders();
    
    boolean deleteOrder(UUID id);
    
    int deleteOrdersByAttentionId(UUID medicalAttentionId);
    
    boolean markOrderAsCompleted(UUID id, String completionNotes);
}