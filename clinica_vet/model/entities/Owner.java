package clinica_vet.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Owner {
    private UUID id;
    private String name;
    private String phone;
    private String address;
    private List<Pet> pets = new ArrayList<>();


    public Owner(String name, String phone, String address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
    
    public Owner(UUID id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public UUID getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Pet> getPets() { return pets; }

    public void addPet(Pet pet) {
        if (!pets.contains(pet)) {
            pets.add(pet);
        }
    }

    public void removePet(Pet pet) { pets.remove(pet); }
}