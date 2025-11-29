package clinica_vet.model.repositories;

import clinica_vet.model.entities.MedicalOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MedicalOrderRepository implements IMedicalOrderRepository {
    
    private List<MedicalOrder> orders;
    
    public MedicalOrderRepository() {
        this.orders = new ArrayList<>();
    }
    
    @Override
    public void addOrder(MedicalOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Medical order cannot be null");
        }
        orders.add(order);
    }
    
    @Override
    public void updateOrder(MedicalOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Medical order cannot be null");
        }
        
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(order.getId())) {
                orders.set(i, order);
                return;
            }
        }
    }
    
    @Override
    public MedicalOrder getOrderById(UUID id) {
        if (id == null) {
            return null;
        }
        
        for (MedicalOrder order : orders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
    
    @Override
    public List<MedicalOrder> getOrdersByAttentionId(UUID medicalAttentionId) {
        if (medicalAttentionId == null) {
            return new ArrayList<>();
        }
        
        return orders.stream()
            .filter(order -> order.getMedicalAttentionId() != null && 
                           order.getMedicalAttentionId().equals(medicalAttentionId))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<MedicalOrder> getPendingOrders() {
        return orders.stream()
            .filter(order -> !order.isCompleted())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<MedicalOrder> getCompletedOrders() {
        return orders.stream()
            .filter(MedicalOrder::isCompleted)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<MedicalOrder> getAllOrders() {
        return new ArrayList<>(orders);
    }
    
    @Override
    public boolean deleteOrder(UUID id) {
        if (id == null) {
            return false;
        }
        
        return orders.removeIf(order -> order.getId().equals(id));
    }
    
    @Override
    public int deleteOrdersByAttentionId(UUID medicalAttentionId) {
        if (medicalAttentionId == null) {
            return 0;
        }
        
        List<MedicalOrder> toRemove = orders.stream()
            .filter(order -> order.getMedicalAttentionId() != null && 
                           order.getMedicalAttentionId().equals(medicalAttentionId))
            .collect(Collectors.toList());
        
        int removedCount = toRemove.size();
        orders.removeAll(toRemove);
        
        return removedCount;
    }
    
    @Override
    public boolean markOrderAsCompleted(UUID id, String completionNotes) {
        if (id == null) {
            return false;
        }
        
        MedicalOrder order = getOrderById(id);
        if (order != null) {
            order.markAsCompleted(completionNotes);
            return true;
        }
        
        return false;
    }
}