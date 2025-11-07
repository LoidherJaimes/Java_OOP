package clinica_vet.model.entities;

import java.util.List;
// Si la clase Pet está en otro paquete, necesitarías importarla, 
// pero asumo que está en el mismo paquete.

public class owner {
    private int document;
    private String name;
    private String phone;
    // 1. Atributo para la lista de mascotas
    private List<Pet> pets; 

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 2. Getter para la lista de mascotas
    public List<Pet> getPets() {
        return pets;
    }

    // 3. Setter para la lista de mascotas
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
    
   
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}