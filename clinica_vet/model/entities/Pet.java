package clinica_vet.model.entities;
import java.util.List;
public class Pet {
    private String especies;
    private String race;
    private int age;
    private Sex sex;
    private int weight;
    private String observations;
    private List<String> vaccinnes;
    private List<String> allergies;

    public Pet() {}
    public Pet(String especies, String race, int age, Sex sex, int weight, String observations, List<String> vaccinnes, List<String> allergies) {
        this.especies = especies;
        this.race = race;
        this.age = age; 
        this.sex = sex;
        this.weight = weight;
        this.observations = observations;
        this.vaccinnes = vaccinnes;
        this.allergies = allergies;
    }
    public String getEspecies() { return especies; }
    public void setEspecies(String especies) { this.especies = especies; }
    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }
    public int getAge() { return age; } 
    public void setAge(int age) { this.age = age; }
    public Sex getSex() { return sex; }
    public void setSex(Sex sex) { this.sex = sex; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public List<String> getVaccinnes() { return vaccinnes; }
    public void setVaccinnes(List<String> vaccinnes) { this.vaccinnes = vaccinnes; }
    public List<String> getAllergies() { return allergies; }
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }
    

    

}
