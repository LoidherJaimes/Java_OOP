package clinica_vet.views;

import javax.swing.*;
import java.awt.*;

public class CreateAppointmentView extends JDialog {

    private JComboBox<String> petCombo;
    private JComboBox<String> doctorCombo;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    private JSpinner durationSpinner;
    private JTextArea reasonTextArea;
    
    private JButton btnCreate;
    private JButton btnCancel;

    public CreateAppointmentView(JFrame parent) {
        super(parent, "Crear Nueva Cita", true);
        
        setSize(500, 550);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        // Main panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Crear Nueva Cita");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(70, 130, 180));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        // Pet selection
        petCombo = new JComboBox<>();
        panel.add(createFieldPanel("Mascota:", petCombo));
        panel.add(Box.createVerticalStrut(15));

        // Doctor selection
        doctorCombo = new JComboBox<>();
        panel.add(createFieldPanel("Médico:", doctorCombo));
        panel.add(Box.createVerticalStrut(15));

        // Date selection
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        panel.add(createFieldPanel("Fecha:", dateSpinner));
        panel.add(Box.createVerticalStrut(15));

        // Time selection
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        panel.add(createFieldPanel("Hora:", timeSpinner));
        panel.add(Box.createVerticalStrut(15));

        // Duration selection
        SpinnerNumberModel durationModel = new SpinnerNumberModel(30, 15, 240, 15);
        durationSpinner = new JSpinner(durationModel);
        panel.add(createFieldPanel("Duración (minutos):", durationSpinner));
        panel.add(Box.createVerticalStrut(15));

        // Reason text area
        JLabel reasonLabel = new JLabel("Motivo de la consulta:");
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 14));
        reasonLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(reasonLabel);
        panel.add(Box.createVerticalStrut(5));

        reasonTextArea = new JTextArea(4, 30);
        reasonTextArea.setLineWrap(true);
        reasonTextArea.setWrapStyleWord(true);
        reasonTextArea.setFont(new Font("Arial", Font.PLAIN, 13));
        reasonTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane reasonScroll = new JScrollPane(reasonTextArea);
        reasonScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(reasonScroll);

        return panel;
    }

    private JPanel createFieldPanel(String labelText, JComponent component) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(component);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(Color.WHITE);

        btnCreate = new JButton("Crear Cita");
        btnCancel = new JButton("Cancelar");

        styleButton(btnCreate, new Color(60, 179, 113));
        styleButton(btnCancel, new Color(220, 20, 60));

        panel.add(btnCreate);
        panel.add(btnCancel);

        return panel;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(130, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Methods to populate combos
    public void loadPets(java.util.List<String> pets) {
        petCombo.removeAllItems();
        for (String pet : pets) {
            petCombo.addItem(pet);
        }
    }

    public void loadDoctors(java.util.List<String> doctors) {
        doctorCombo.removeAllItems();
        for (String doctor : doctors) {
            doctorCombo.addItem(doctor);
        }
    }

    // Getters
    public String getSelectedPet() {
        return (String) petCombo.getSelectedItem();
    }

    public String getSelectedDoctor() {
        return (String) doctorCombo.getSelectedItem();
    }

    public java.util.Date getSelectedDate() {
        return (java.util.Date) dateSpinner.getValue();
    }

    public java.util.Date getSelectedTime() {
        return (java.util.Date) timeSpinner.getValue();
    }

    public int getDuration() {
        return (Integer) durationSpinner.getValue();
    }

    public String getReason() {
        return reasonTextArea.getText().trim();
    }

    public JButton getBtnCreate() {
        return btnCreate;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    // Method to clear form
    public void clearForm() {
        if (petCombo.getItemCount() > 0) {
            petCombo.setSelectedIndex(0);
        }
        if (doctorCombo.getItemCount() > 0) {
            doctorCombo.setSelectedIndex(0);
        }
        dateSpinner.setValue(new java.util.Date());
        timeSpinner.setValue(new java.util.Date());
        durationSpinner.setValue(30);
        reasonTextArea.setText("");
    }
}