package clinica_vet.views;

import clinica_vet.model.entities.Sex;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CreatePetView extends JDialog {

    private JTextField nameTF;
    private JTextField speciesTF;
    private JTextField raceTF;
    private JTextField ageTF;     // 🔹 Cambiado de JSpinner a JTextField
    private JComboBox<Sex> sexCB;
    private JTextField weightTF;  // 🔹 Cambiado de JSpinner a JTextField
    private JTextArea observationsTA;
    private JList<String> vaccinesList;
    private JList<String> allergiesList;
    private JButton btnSave;
    private JButton btnCancel;
    
    private final String[] dummyVaccines = {"Rabia", "Parvovirus", "Moquillo"};
    private final String[] dummyAllergies = {"Polen", "Grasas", "Penicilina"};

    public CreatePetView(JFrame owner) {
        super(owner, "Crear Nueva Mascota", true);
        setupDialog();
        createComponents();
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

        // Edad (ahora campo de texto)
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Edad (años):"), gbc);
        ageTF = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(ageTF, gbc);

        // Sexo (ComboBox)
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Sexo:"), gbc);
        sexCB = new JComboBox<>(Sex.values());
        gbc.gridx = 1; gbc.gridy = y++; formPanel.add(sexCB, gbc);

        // Peso (ahora campo de texto)
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
        btnSave = new JButton("Guardar Mascota");
        btnCancel = new JButton("Cancelar");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Métodos para obtener los datos seleccionados
    public List<String> getSelectedVaccines() {
        return vaccinesList.getSelectedValuesList();
    }
    
    public List<String> getSelectedAllergies() {
        return allergiesList.getSelectedValuesList();
    }

    // 🔹 Getters actualizados
    public JTextField getNameTF() { return nameTF; }
    public JTextField getSpeciesTF() { return speciesTF; }
    public JTextField getRaceTF() { return raceTF; }
    public JTextField getAgeTF() { return ageTF; }        // 🔹 nuevo getter
    public JComboBox<Sex> getSexCB() { return sexCB; }
    public JTextField getWeightTF() { return weightTF; }  // 🔹 nuevo getter
    public JTextArea getObservationsTA() { return observationsTA; }
    public JButton getBtnSave() { return btnSave; }
}
