package clinica_vet.views;

import javax.swing.*;
import java.awt.*;

public class CreateOwnerView extends JDialog {

    private JTextField nameTF;
    private JTextField phoneTF;
    private JTextField addressTF;
    private JButton btnSave;
    private JButton btnCancel;

    public CreateOwnerView(JFrame owner) {
        super(owner, "Crear Nuevo Dueño", true); // Modal
        setupDialog();
        createComponents();
        // Listener para Cancelar
        btnCancel.addActionListener(e -> dispose());
    }

    private void setupDialog() {
        setSize(450, 300);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void createComponents() {
        // Panel de Formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Nombre:"), gbc);
        nameTF = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(nameTF, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Teléfono:"), gbc);
        phoneTF = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(phoneTF, gbc);

        // Dirección
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Dirección:"), gbc);
        addressTF = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(addressTF, gbc);

        // Panel de Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnSave = new JButton("Guardar Dueño");
        btnCancel = new JButton("Cancelar");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters
    public JTextField getNameTF() { return nameTF; }
    public JTextField getPhoneTF() { return phoneTF; }
    public JTextField getAddressTF() { return addressTF; }
    public JButton getBtnSave() { return btnSave; }
}