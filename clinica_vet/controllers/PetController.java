package clinica_vet.controllers;

import clinica_vet.model.entities.Pet;
import clinica_vet.model.entities.Sex;
import clinica_vet.model.repositories.OwnerRepository;
import clinica_vet.model.repositories.PetRepository;
import clinica_vet.views.CreatePetView;
import clinica_vet.views.EditPetView;
import clinica_vet.views.MainWindowView;
import clinica_vet.views.PetManagementView;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

public class PetController {

    private final PetManagementView petManagementView;
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final MainWindowView mainView;

    public PetController(PetManagementView petManagementView,
                         PetRepository petRepository,
                         OwnerRepository ownerRepository,
                         MainWindowView mainView) {
        this.petManagementView = petManagementView;
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.mainView = mainView;

        // Configurar los listeners
        petManagementView.getBtnClose().addActionListener(e -> mainView.setContent(mainView.getWelcomeView()));
        petManagementView.getBtnCreate().addActionListener(e -> showCreatePetDialog());
        petManagementView.getBtnDelete().addActionListener(e -> deleteSelectedPet());
        petManagementView.getBtnEdit().addActionListener(e -> showEditPetDialog());

        // Cargar datos iniciales
        loadPetTable();
    }

    private void loadPetTable() {
        petManagementView.clearTable();
        for (Pet pet : petRepository.getAllPets()) {
            petManagementView.addPetToTable(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getRace(),
                pet.getAge(),
                pet.getSex().toString(),
                pet.getWeight()
            );
        }
    }

    private void showCreatePetDialog() {
        CreatePetView createView = new CreatePetView(mainView);
        
        createView.getBtnSave().addActionListener(e -> {
            try {
                String name = createView.getNameTF().getText().trim();
                String species = createView.getSpeciesTF().getText().trim();
                String race = createView.getRaceTF().getText().trim();
                String ageText = createView.getAgeTF().getText().trim();
                String weightText = createView.getWeightTF().getText().trim();
                Sex sex = (Sex) createView.getSexCB().getSelectedItem();
                String observations = createView.getObservationsTA().getText().trim();
                List<String> vaccines = createView.getSelectedVaccines();
                List<String> allergies = createView.getSelectedAllergies();

                // Validar campos obligatorios
                if (name.isEmpty() || species.isEmpty() || race.isEmpty() || ageText.isEmpty() || weightText.isEmpty()) {
                    JOptionPane.showMessageDialog(createView,
                            "Todos los campos obligatorios deben llenarse.",
                            "Error de Validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int age;
                double weight;

                try {
                    age = Integer.parseInt(ageText);
                    weight = Double.parseDouble(weightText);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(createView,
                            "Edad y peso deben ser valores numéricos válidos.",
                            "Error de Validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Pet newPet = new Pet(name, species, race, age, sex, weight, observations, vaccines, allergies);
                petRepository.addPet(newPet);

                JOptionPane.showMessageDialog(createView,
                        "Mascota " + name + " creada con éxito.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                createView.dispose();
                loadPetTable();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(createView,
                        "Error al guardar la mascota: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        createView.setVisible(true);
    }

    private void showEditPetDialog() {
        int selectedRow = petManagementView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainView,
                    "Seleccione una mascota para modificar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        UUID petId = (UUID) petManagementView.getTable().getModel().getValueAt(selectedRow, 0);
        Pet petToEdit = petRepository.getPetById(petId);

        if (petToEdit == null) {
            JOptionPane.showMessageDialog(mainView,
                    "Mascota no encontrada.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        EditPetView editView = new EditPetView(mainView, petToEdit);

        editView.getBtnSave().addActionListener(e -> {
            try {
                String name = editView.getNameTF().getText().trim();
                String species = editView.getSpeciesTF().getText().trim();
                String race = editView.getRaceTF().getText().trim();
                String ageText = editView.getAgeTF().getText().trim();
                String weightText = editView.getWeightTF().getText().trim();
                Sex sex = (Sex) editView.getSexCB().getSelectedItem();
                String observations = editView.getObservationsTA().getText().trim();
                List<String> vaccines = editView.getSelectedVaccines();
                List<String> allergies = editView.getSelectedAllergies();

                if (name.isEmpty() || species.isEmpty() || race.isEmpty() || ageText.isEmpty() || weightText.isEmpty()) {
                    JOptionPane.showMessageDialog(editView,
                            "Todos los campos obligatorios deben llenarse.",
                            "Error de Validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int age;
                double weight;

                try {
                    age = Integer.parseInt(ageText);
                    weight = Double.parseDouble(weightText);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(editView,
                            "Edad y peso deben ser valores numéricos válidos.",
                            "Error de Validación",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                petToEdit.setName(name);
                petToEdit.setSpecies(species);
                petToEdit.setRace(race);
                petToEdit.setAge(age);
                petToEdit.setSex(sex);
                petToEdit.setWeight(weight);
                petToEdit.setObservations(observations);
                petToEdit.setVaccinnes(vaccines);
                petToEdit.setAllergies(allergies);

                petRepository.updatePet(petToEdit);

                JOptionPane.showMessageDialog(editView,
                        "Mascota modificada con éxito.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                editView.dispose();
                loadPetTable();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editView,
                        "Error al modificar la mascota: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        editView.setVisible(true);
    }

    private void deleteSelectedPet() {
        int selectedRow = petManagementView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainView,
                    "Seleccione una mascota para eliminar.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        UUID petId = (UUID) petManagementView.getTable().getModel().getValueAt(selectedRow, 0);
        String petName = (String) petManagementView.getTable().getModel().getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(mainView,
                "¿Está seguro de eliminar la mascota " + petName + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            petRepository.deletePetById(petId);
            JOptionPane.showMessageDialog(mainView,
                    "Mascota eliminada.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            loadPetTable();
        }
    }
}
