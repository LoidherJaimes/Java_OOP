package clinica_vet.views;

import javax.swing.*;
import java.awt.*;

public class AddTreatmentView extends JDialog {
    
    private JTextField txtMedication;
    private JTextField txtDosage;
    private JTextField txtFrequency;
    private JTextField txtDuration;
    private JTextArea txtInstructions;
    
    private JButton btnSave;
    private JButton btnCancel;
    
    private boolean confirmed = false;
    
    public AddTreatmentView(JDialog parent) {
        super(parent, "Agregar Tratamiento", true);
        setSize(500, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        initComponents();
    }
    
    public AddTreatmentView(JDialog parent, String title) {
        super(parent, title, true);
        setSize(500, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Título
        JLabel titleLabel = new JLabel("Información del Tratamiento");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel);
        
        // Panel de campos
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Medicamento
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblMedication = new JLabel("Medicamento: *");
        lblMedication.setFont(new Font("Arial", Font.BOLD, 12));
        fieldsPanel.add(lblMedication, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtMedication = new JTextField(20);
        txtMedication.setFont(new Font("Arial", Font.PLAIN, 12));
        fieldsPanel.add(txtMedication, gbc);
        
        // Dosis
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblDosage = new JLabel("Dosis: *");
        lblDosage.setFont(new Font("Arial", Font.BOLD, 12));
        fieldsPanel.add(lblDosage, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtDosage = new JTextField(20);
        txtDosage.setFont(new Font("Arial", Font.PLAIN, 12));
        fieldsPanel.add(txtDosage, gbc);
        
        // Frecuencia
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel lblFrequency = new JLabel("Frecuencia: *");
        lblFrequency.setFont(new Font("Arial", Font.BOLD, 12));
        fieldsPanel.add(lblFrequency, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtFrequency = new JTextField(20);
        txtFrequency.setFont(new Font("Arial", Font.PLAIN, 12));
        fieldsPanel.add(txtFrequency, gbc);
        
        // Duración
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        JLabel lblDuration = new JLabel("Duración: *");
        lblDuration.setFont(new Font("Arial", Font.BOLD, 12));
        fieldsPanel.add(lblDuration, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtDuration = new JTextField(20);
        txtDuration.setFont(new Font("Arial", Font.PLAIN, 12));
        fieldsPanel.add(txtDuration, gbc);
        
        // Instrucciones
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblInstructions = new JLabel("Instrucciones:");
        lblInstructions.setFont(new Font("Arial", Font.BOLD, 12));
        fieldsPanel.add(lblInstructions, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        txtInstructions = new JTextArea(4, 20);
        txtInstructions.setLineWrap(true);
        txtInstructions.setWrapStyleWord(true);
        txtInstructions.setFont(new Font("Arial", Font.PLAIN, 12));
        txtInstructions.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane instructionsScroll = new JScrollPane(txtInstructions);
        fieldsPanel.add(instructionsScroll, gbc);
        
        mainPanel.add(fieldsPanel);
        
        // Nota de campos obligatorios
        JLabel noteLabel = new JLabel("* Campos obligatorios");
        noteLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        noteLabel.setForeground(new Color(220, 20, 60));
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        noteLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(noteLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(Color.WHITE);
        
        btnSave = new JButton("Guardar");
        btnCancel = new JButton("Cancelar");
        
        styleButton(btnSave, new Color(60, 179, 113));
        styleButton(btnCancel, new Color(220, 20, 60));
        
        btnSave.setPreferredSize(new Dimension(120, 35));
        btnCancel.setPreferredSize(new Dimension(120, 35));
        
        // Acción por defecto para cancelar
        btnCancel.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        
        panel.add(btnSave);
        panel.add(btnCancel);
        
        return panel;
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    // Métodos de validación
    public boolean validateFields() {
        if (txtMedication.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El medicamento es obligatorio.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            txtMedication.requestFocus();
            return false;
        }
        
        if (txtDosage.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "La dosis es obligatoria.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            txtDosage.requestFocus();
            return false;
        }
        
        if (txtFrequency.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "La frecuencia es obligatoria.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            txtFrequency.requestFocus();
            return false;
        }
        
        if (txtDuration.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "La duración es obligatoria.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            txtDuration.requestFocus();
            return false;
        }
        
        return true;
    }
    
    // Getters
    public String getMedication() {
        return txtMedication.getText().trim();
    }
    
    public String getDosage() {
        return txtDosage.getText().trim();
    }
    
    public String getFrequency() {
        return txtFrequency.getText().trim();
    }
    
    public String getDuration() {
        return txtDuration.getText().trim();
    }
    
    public String getInstructions() {
        return txtInstructions.getText().trim();
    }
    
    // Setters (para edición)
    public void setMedication(String medication) {
        txtMedication.setText(medication);
    }
    
    public void setDosage(String dosage) {
        txtDosage.setText(dosage);
    }
    
    public void setFrequency(String frequency) {
        txtFrequency.setText(frequency);
    }
    
    public void setDuration(String duration) {
        txtDuration.setText(duration);
    }
    
    public void setInstructions(String instructions) {
        txtInstructions.setText(instructions);
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    
    public JButton getBtnSave() {
        return btnSave;
    }
    
    public JButton getBtnCancel() {
        return btnCancel;
    }
}