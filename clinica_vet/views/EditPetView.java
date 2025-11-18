package clinica_vet.views;

import clinica_vet.model.entities.Pet;
import clinica_vet.model.entities.Sex;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditPetView extends JDialog {

    private final Pet petToEdit; 
    private JTextField nameTF;
    private JTextField speciesTF;
    private JTextField raceTF;
    private JTextField ageTF; // 🔄 reemplaza spinner
    private JComboBox<Sex> sexCB;
    private JTextField weightTF; // 🔄 reemplaza spinner
    private JTextArea observationsTA;
    private JList<String> vaccinesList;
    private JList<String> allergiesList;
    private JButton btnSave;
    private JButton btnCancel;
    
    private final String[] dummyVaccines = {"Rabia", "Parvovirus", "Moquillo"};
    private final String[] dummyAllergies = {"Polen", "Grasas", "Penicilina"};

    public EditPetView(JFrame owner, Pet petToEdit) {
        super(owner, "Modificar Mascota: " + petToEdit.getName(), true);
        this.petToEdit = petToEdit;
        setupDialog();
        createComponents();
        loadPetData(); // Cargar datos existentes
        btnCancel.addActionListener(e -> dispose());
    }

    private void setupDialog() {
        setSize(600, 700);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void createComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JScrollPane scrollPane = new JScrollPane(formPanel); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        int y = 0;

        // Nombre
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Nombre:"), gbc);
        nameTF = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(nameTF, gbc);

        // Especie
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Especie:"), gbc);
        speciesTF = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(speciesTF, gbc);

        // Raza
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Raza:"), gbc);
        raceTF = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(raceTF, gbc);

        // Edad (TextField)
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Edad (años):"), gbc);
        ageTF = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(ageTF, gbc);

        // Sexo (ComboBox)
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Sexo:"), gbc);
        sexCB = new JComboBox<>(Sex.values());
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(sexCB, gbc);

        // Peso (TextField)
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Peso (Kg):"), gbc);
        weightTF = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(weightTF, gbc);
        
        // Observaciones (TextArea)
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Observaciones:"), gbc);
        observationsTA = new JTextArea(4, 20);
        JScrollPane obsScrollPane = new JScrollPane(observationsTA);
        gbc.gridx = 1; gbc.gridy = y++; 
        gbc.fill = GridBagConstraints.BOTH; 
        formPanel.add(obsScrollPane, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        // Vacunas (JList)
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Vacunas:"), gbc);
        vaccinesList = new JList<>(dummyVaccines);
        vaccinesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(new JScrollPane(vaccinesList), gbc);

        // Alergias (JList)
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Alergias:"), gbc);
        allergiesList = new JList<>(dummyAllergies);
        allergiesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(new JScrollPane(allergiesList), gbc);

        // Panel de Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnSave = new JButton("Guardar Cambios");
        btnCancel = new JButton("Cancelar");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadPetData() {
        nameTF.setText(petToEdit.getName());
        speciesTF.setText(petToEdit.getSpecies());
        raceTF.setText(petToEdit.getRace());
        ageTF.setText(String.valueOf(petToEdit.getAge())); // 🔄 conversión a texto
        sexCB.setSelectedItem(petToEdit.getSex());
        weightTF.setText(String.valueOf(petToEdit.getWeight())); // 🔄 conversión a texto
        observationsTA.setText(petToEdit.getObservations());
        
        // Cargar selecciones de listas
        setSelectedValues(vaccinesList, petToEdit.getVaccinnes());
        setSelectedValues(allergiesList, petToEdit.getAllergies());
    }
    
    private void setSelectedValues(JList<String> list, List<String> selectedValues) {
        ListModel<String> model = list.getModel();
        int[] indices = new int[selectedValues.size()];
        int count = 0;
        for (int i = 0; i < model.getSize(); i++) {
            if (selectedValues.contains(model.getElementAt(i))) {
                indices[count++] = i;
            }
        }
        list.setSelectedIndices(indices);
    }

    // Métodos para obtener los datos seleccionados
    public List<String> getSelectedVaccines() {
        return vaccinesList.getSelectedValuesList();
    }
    
    public List<String> getSelectedAllergies() {
        return allergiesList.getSelectedValuesList();
    }
    
    // Getters
    public Pet getPetToEdit() { return petToEdit; }
    public JTextField getNameTF() { return nameTF; }
    public JTextField getSpeciesTF() { return speciesTF; }
    public JTextField getRaceTF() { return raceTF; }
    public JTextField getAgeTF() { return ageTF; } // 🔄 actualizado
    public JComboBox<Sex> getSexCB() { return sexCB; }
    public JTextField getWeightTF() { return weightTF; } // 🔄 actualizado
    public JTextArea getObservationsTA() { return observationsTA; }
    public JButton getBtnSave() { return btnSave; }
}
