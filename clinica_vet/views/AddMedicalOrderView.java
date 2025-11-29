package clinica_vet.views;

import clinica_vet.model.entities.OrderType;
import javax.swing.*;
import java.awt.*;

/**
 * Vista modal para agregar o editar una orden médica.
 * Permite seleccionar el tipo de orden e ingresar descripción y notas.
 */
public class AddMedicalOrderView extends JDialog {
    
    private JComboBox<OrderType> cmbOrderType;
    private JTextArea txtDescription;
    private JTextArea txtNotes;
    
    private JButton btnSave;
    private JButton btnCancel;
    
    private boolean confirmed = false;
    
    public AddMedicalOrderView(JDialog parent) {
        super(parent, "Agregar Orden Médica", true);
        setSize(500, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        initComponents();
    }
    
    public AddMedicalOrderView(JDialog parent, String title) {
        super(parent, title, true);
        setSize(500, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        initComponents();
    }
    
    // ⭐ NUEVOS: Constructores que aceptan JFrame
    public AddMedicalOrderView(JFrame parent) {
        super(parent, "Agregar Orden Médica", true);
        setSize(500, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        initComponents();
    }
    
    public AddMedicalOrderView(JFrame parent, String title) {
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
        JLabel titleLabel = new JLabel("Información de la Orden Médica");
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
        
        // Tipo de orden
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblOrderType = new JLabel("Tipo de Orden: *");
        lblOrderType.setFont(new Font("Arial", Font.BOLD, 12));
        fieldsPanel.add(lblOrderType, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cmbOrderType = new JComboBox<>(OrderType.values());
        cmbOrderType.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbOrderType.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                         int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof OrderType) {
                    setText(((OrderType) value).getDisplayName());
                }
                return this;
            }
        });
        fieldsPanel.add(cmbOrderType, gbc);
        
        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblDescription = new JLabel("Descripción: *");
        lblDescription.setFont(new Font("Arial", Font.BOLD, 12));
        fieldsPanel.add(lblDescription, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.5;
        txtDescription = new JTextArea(5, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setFont(new Font("Arial", Font.PLAIN, 12));
        txtDescription.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane descriptionScroll = new JScrollPane(txtDescription);
        fieldsPanel.add(descriptionScroll, gbc);
        
        // Notas
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblNotes = new JLabel("Notas:");
        lblNotes.setFont(new Font("Arial", Font.BOLD, 12));
        fieldsPanel.add(lblNotes, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        txtNotes = new JTextArea(5, 20);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        txtNotes.setFont(new Font("Arial", Font.PLAIN, 12));
        txtNotes.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane notesScroll = new JScrollPane(txtNotes);
        fieldsPanel.add(notesScroll, gbc);
        
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
        if (cmbOrderType.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                "Debe seleccionar un tipo de orden.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            cmbOrderType.requestFocus();
            return false;
        }
        
        if (txtDescription.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "La descripción es obligatoria.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            txtDescription.requestFocus();
            return false;
        }
        
        return true;
    }
    
    // Getters
    public OrderType getSelectedOrderType() {
        return (OrderType) cmbOrderType.getSelectedItem();
    }
    
    public String getDescription() {
        return txtDescription.getText().trim();
    }
    
    public String getNotes() {
        return txtNotes.getText().trim();
    }
    
    // Setters (para edición)
    public void setSelectedOrderType(OrderType orderType) {
        cmbOrderType.setSelectedItem(orderType);
    }
    
    public void setDescription(String description) {
        txtDescription.setText(description);
    }
    
    public void setNotes(String notes) {
        txtNotes.setText(notes);
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