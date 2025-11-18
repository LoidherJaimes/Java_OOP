package clinica_vet.model.repositories;

import clinica_vet.model.entities.Pet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PetRepository {
    private List<Pet> pets;

    public PetRepository() {
        this.pets = new ArrayList<>();
    }

    public List<Pet> getAllPets() {
        return pets;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }
    
    public Pet getPetById(UUID id) {
        for (Pet p : pets) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public void updatePet(Pet updatedPet) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId().equals(updatedPet.getId())) {
                pets.set(i, updatedPet);
                return;
            }
        }
    }

    public void deletePetById(UUID petId) {
        pets.removeIf(p -> p.getId().equals(petId));
    }
}