package clinica_vet.views;

import clinica_vet.model.entities.Owner;
import clinica_vet.model.entities.Pet;
import clinica_vet.model.entities.Sex;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class EditPetView extends JDialog {

    private final Pet petToEdit; 
    private JTextField nameTF;
    private JTextField speciesTF;
    private JTextField raceTF;
    private JTextField ageTF;
    private JComboBox<Sex> sexCB;
    private JTextField weightTF;
    private JTextArea observationsTA;
    private JList<String> vaccinesList;
    private JList<String> allergiesList;
    private JComboBox<Owner> ownerCB;  // ComboBox para dueño
    private JButton btnSave;
    private JButton btnCancel;
    
    private final String[] dummyVaccines = {"Rabia", "Parvovirus", "Moquillo"};
    private final String[] dummyAllergies = {"Polen", "Grasas", "Penicilina"};
    
    private List<Owner> availableOwners;

    public EditPetView(JFrame owner, Pet petToEdit, List<Owner> availableOwners) {
        super(owner, "Modificar Mascota: " + petToEdit.getName(), true);
        this.petToEdit = petToEdit;
        this.availableOwners = availableOwners;
        setupDialog();
        createComponents();
        loadPetData();
        btnCancel.addActionListener(e -> dispose());
    }

    private void setupDialog() {
        setSize(600, 750);
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

        // Dueño (ComboBox)
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Dueño:"), gbc);
        ownerCB = new JComboBox<>();
        ownerCB.addItem(null); // Opción "Sin dueño"
        for (Owner o : availableOwners) {
            ownerCB.addItem(o);
        }
        ownerCB.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("-- Sin dueño --");
                } else {
                    setText(((Owner) value).getName());
                }
                return this;
            }
        });
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(ownerCB, gbc);

        // Especie
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Especie:"), gbc);
        speciesTF = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(speciesTF, gbc);

        // Raza
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Raza:"), gbc);
        raceTF = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(raceTF, gbc);

        // Edad
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Edad (años):"), gbc);
        ageTF = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(ageTF, gbc);

        // Sexo
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Sexo:"), gbc);
        sexCB = new JComboBox<>(Sex.values());
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(sexCB, gbc);

        // Peso
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Peso (Kg):"), gbc);
        weightTF = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(weightTF, gbc);
        
        // Observaciones
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Observaciones:"), gbc);
        observationsTA = new JTextArea(4, 20);
        JScrollPane obsScrollPane = new JScrollPane(observationsTA);
        gbc.gridx = 1; gbc.gridy = y++; 
        gbc.fill = GridBagConstraints.BOTH; 
        formPanel.add(obsScrollPane, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        // Vacunas
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Vacunas:"), gbc);
        vaccinesList = new JList<>(dummyVaccines);
        vaccinesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(new JScrollPane(vaccinesList), gbc);

        // Alergias
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Alergias:"), gbc);
        allergiesList = new JList<>(dummyAllergies);
        allergiesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(new JScrollPane(allergiesList), gbc);

        // Botones
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
        
        double age = petToEdit.getAge();
        if (age == (int) age) {
            ageTF.setText(String.valueOf((int) age));
        } else {
            ageTF.setText(String.valueOf(age));
        }

        sexCB.setSelectedItem(petToEdit.getSex());

        double weight = petToEdit.getWeight();
        if(weight == (int) weight)
        {
            weightTF.setText(String.valueOf((int) weight));
        } else {
            weightTF.setText(String.valueOf(weight));
        }

        observationsTA.setText(petToEdit.getObservations());
        
        ownerCB.setSelectedItem(petToEdit.getOwner());
        
        setSelectedValues(vaccinesList, petToEdit.getVaccinnes());
        setSelectedValues(allergiesList, petToEdit.getAllergies());
    }
    
    private void setSelectedValues(JList<String> list, List<String> selectedValues) {
        if (selectedValues == null) return;
        ListModel<String> model = list.getModel();
        int[] indices = new int[selectedValues.size()];
        int count = 0;
        for (int i = 0; i < model.getSize(); i++) {
            if (selectedValues.contains(model.getElementAt(i))) {
                indices[count++] = i;
            }
        }
        list.setSelectedIndices(java.util.Arrays.copyOf(indices, count));
    }

    public List<String> getSelectedVaccines() { return vaccinesList.getSelectedValuesList(); }
    public List<String> getSelectedAllergies() { return allergiesList.getSelectedValuesList(); }
    
    // Getters
    public Pet getPetToEdit() { return petToEdit; }
    public JTextField getNameTF() { return nameTF; }
    public JTextField getSpeciesTF() { return speciesTF; }
    public JTextField getRaceTF() { return raceTF; }
    public JTextField getAgeTF() { return ageTF; }
    public JComboBox<Sex> getSexCB() { return sexCB; }
    public JTextField getWeightTF() { return weightTF; }
    public JTextArea getObservationsTA() { return observationsTA; }
    public JComboBox<Owner> getOwnerCB() { return ownerCB; }  // Getter del ComboBox
    public JButton getBtnSave() { return btnSave; }
}