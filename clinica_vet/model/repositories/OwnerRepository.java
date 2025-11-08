package clinica_vet.model.repositories;

import clinica_vet.model.entities.Owner;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OwnerRepository {
    private List<Owner> owners;

    public OwnerRepository() {
        this.owners = new ArrayList<>();
        // ⭐ La lista comienza vacía. Los datos de prueba se inicializan en MainApp.
    }

    public List<Owner> getAllOwners() {
        return owners;
    }

    public void addOwner(Owner owner) {
        owners.add(owner);
    }
    
    public Owner getOwnerById(UUID id) {
        for (Owner o : owners) {
            if (o.getId().equals(id)) {
                return o;
            }
        }
        return null;
    }

    public void updateOwner(Owner updatedOwner) {
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(updatedOwner.getId())) {
                owners.set(i, updatedOwner);
                return;
            }
        }
    }

    public void deleteOwnerById(UUID ownerId) {
        owners.removeIf(o -> o.getId().equals(ownerId));
    }
}