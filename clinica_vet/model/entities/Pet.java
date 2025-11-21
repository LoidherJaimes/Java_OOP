package clinica_vet.model.entities;

import java.util.List;
import java.util.UUID;

public class Pet {
    private UUID id;
    private String name;
    private String species;
    private String race;
    private double age;
    private Sex sex;
    private double weight;
    private String observations;
    private List<String> vaccinnes;
    private List<String> allergies;
    private Owner owner;

    public Pet() {}

    public Pet(String name, String species, String race, double age, Sex sex, double weight, 
               String observations, List<String> vaccinnes, List<String> allergies) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.species = species;
        this.race = race;
        this.age = age; 
        this.sex = sex;
        this.weight = weight;
        this.observations = observations;
        this.vaccinnes = vaccinnes;
        this.allergies = allergies;
        this.owner = null;
    }
    
    // Constructor de CREACIÓN (con Owner)
    public Pet(String name, String species, String race, double age, Sex sex, double weight, 
               String observations, List<String> vaccinnes, List<String> allergies, Owner owner) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.species = species;
        this.race = race;
        this.age = age; 
        this.sex = sex;
        this.weight = weight;
        this.observations = observations;
        this.vaccinnes = vaccinnes;
        this.allergies = allergies;
        this.owner = owner;
    }

    // Constructor para CARGA (Con ID existente)
    public Pet(UUID id, String name, String species, String race, int age, Sex sex, int weight, 
               String observations, List<String> vaccinnes, List<String> allergies) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.race = race;
        this.age = age; 
        this.sex = sex;
        this.weight = weight;
        this.observations = observations;
        this.vaccinnes = vaccinnes;
        this.allergies = allergies;
        this.owner = null;
    }
    
    // Constructor para CARGA (Con ID existente y Owner)
    public Pet(UUID id, String name, String species, String race, int age, Sex sex, int weight, 
               String observations, List<String> vaccinnes, List<String> allergies, Owner owner) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.race = race;
        this.age = age; 
        this.sex = sex;
        this.weight = weight;
        this.observations = observations;
        this.vaccinnes = vaccinnes;
        this.allergies = allergies;
        this.owner = owner;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }

    public double getAge() { return age; } 
    public void setAge(double age) { this.age = age; }

    public Sex getSex() { return sex; }
    public void setSex(Sex sex) { this.sex = sex; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public List<String> getVaccinnes() { return vaccinnes; }
    public void setVaccinnes(List<String> vaccinnes) { this.vaccinnes = vaccinnes; }

    public List<String> getAllergies() { return allergies; }
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }
    
    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }
}