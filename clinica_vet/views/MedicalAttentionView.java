package clinica_vet.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MedicalAttentionView extends JPanel {
    
    // Paneles de información
    private JLabel lblPetInfo;
    private JLabel lblOwnerInfo;
    private JLabel lblDateInfo;
    private JLabel lblVeterinarianInfo;
    
    // Campos de evolución médica
    private JTextArea txtSymptoms;
    private JTextArea txtDiagnosis;
    private JTextArea txtProcedures;
    
    // Tabla de tratamientos
    private JTable tableTreatments;
    private DefaultTableModel treatmentsModel;
    private JButton btnAddTreatment;
    private JButton btnEditTreatment;
    private JButton btnDeleteTreatment;
    
    // Tabla de órdenes médicas
    private JTable tableOrders;
    private DefaultTableModel ordersModel;
    private JButton btnAddOrder;
    private JButton btnEditOrder;
    private JButton btnDeleteOrder;
    
    // Botones principales
    private JButton btnSave;
    private JButton btnCloseAttention;
    private JButton btnCancel;
    
    public MedicalAttentionView() {
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(780, 550));
        setBackground(Color.WHITE);
        
        initComponents();
    }
    
    private void initComponents() {
        // Panel principal con scroll
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);
        
        // Título
        JLabel titleLabel = new JLabel("Atención Médica Veterinaria", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Panel de información superior
        JPanel infoPanel = createInfoPanel();
        
        // Panel central con scroll
        JPanel centerPanel = createCenterPanel();
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Panel combinado (info + center)
        JPanel combinedPanel = new JPanel(new BorderLayout(10, 10));
        combinedPanel.setBackground(Color.WHITE);
        combinedPanel.add(infoPanel, BorderLayout.NORTH);
        combinedPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(combinedPanel, BorderLayout.CENTER);
        
        // Panel de botones inferior
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        lblPetInfo = new JLabel("Mascota: ");
        lblOwnerInfo = new JLabel("Propietario: ");
        lblDateInfo = new JLabel("Fecha: ");
        lblVeterinarianInfo = new JLabel("Veterinario: ");
        
        Font infoFont = new Font("Segoe UI", Font.BOLD, 13);
        lblPetInfo.setFont(infoFont);
        lblOwnerInfo.setFont(infoFont);
        lblDateInfo.setFont(infoFont);
        lblVeterinarianInfo.setFont(infoFont);
        
        panel.add(lblPetInfo);
        panel.add(lblOwnerInfo);
        panel.add(lblDateInfo);
        panel.add(lblVeterinarianInfo);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        
        // Sección de evolución médica
        panel.add(createEvolutionSection());
        panel.add(Box.createVerticalStrut(15));
        
        // Sección de tratamientos
        panel.add(createTreatmentsSection());
        panel.add(Box.createVerticalStrut(15));
        
        // Sección de órdenes médicas
        panel.add(createOrdersSection());
        
        return panel;
    }
    
    private JPanel createEvolutionSection() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            "Evolución Médica",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(70, 130, 180)
        ));
        
        JPanel fieldsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Síntomas
        JPanel symptomsPanel = new JPanel(new BorderLayout(5, 5));
        symptomsPanel.setBackground(Color.WHITE);
        JLabel lblSymptoms = new JLabel("Síntomas: *");
        lblSymptoms.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSymptoms.setForeground(new Color(220, 20, 60));
        txtSymptoms = new JTextArea(3, 20);
        txtSymptoms.setLineWrap(true);
        txtSymptoms.setWrapStyleWord(true);
        txtSymptoms.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtSymptoms.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane symptomsScroll = new JScrollPane(txtSymptoms);
        symptomsPanel.add(lblSymptoms, BorderLayout.NORTH);
        symptomsPanel.add(symptomsScroll, BorderLayout.CENTER);
        
        // Diagnóstico
        JPanel diagnosisPanel = new JPanel(new BorderLayout(5, 5));
        diagnosisPanel.setBackground(Color.WHITE);
        JLabel lblDiagnosis = new JLabel("Diagnóstico: *");
        lblDiagnosis.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDiagnosis.setForeground(new Color(220, 20, 60));
        txtDiagnosis = new JTextArea(3, 20);
        txtDiagnosis.setLineWrap(true);
        txtDiagnosis.setWrapStyleWord(true);
        txtDiagnosis.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtDiagnosis.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane diagnosisScroll = new JScrollPane(txtDiagnosis);
        diagnosisPanel.add(lblDiagnosis, BorderLayout.NORTH);
        diagnosisPanel.add(diagnosisScroll, BorderLayout.CENTER);
        
        // Procedimientos
        JPanel proceduresPanel = new JPanel(new BorderLayout(5, 5));
        proceduresPanel.setBackground(Color.WHITE);
        JLabel lblProcedures = new JLabel("Procedimientos Realizados:");
        lblProcedures.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtProcedures = new JTextArea(3, 20);
        txtProcedures.setLineWrap(true);
        txtProcedures.setWrapStyleWord(true);
        txtProcedures.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtProcedures.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane proceduresScroll = new JScrollPane(txtProcedures);
        proceduresPanel.add(lblProcedures, BorderLayout.NORTH);
        proceduresPanel.add(proceduresScroll, BorderLayout.CENTER);
        
        fieldsPanel.add(symptomsPanel);
        fieldsPanel.add(diagnosisPanel);
        fieldsPanel.add(proceduresPanel);
        
        panel.add(fieldsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTreatmentsSection() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 179, 113), 2),
            "Tratamientos",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(34, 139, 34)
        ));
        
        // Tabla de tratamientos
        String[] columns = {"ID", "Medicamento", "Dosis", "Frecuencia", "Duración"};
        treatmentsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableTreatments = new JTable(treatmentsModel);
        tableTreatments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTreatments.setRowHeight(25);
        tableTreatments.getTableHeader().setReorderingAllowed(false);
        tableTreatments.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableTreatments.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Ocultar columna ID
        tableTreatments.getColumnModel().getColumn(0).setMinWidth(0);
        tableTreatments.getColumnModel().getColumn(0).setMaxWidth(0);
        tableTreatments.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tableTreatments);
        scrollPane.setPreferredSize(new Dimension(0, 120));
        
        // Botones
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonsPanel.setBackground(Color.WHITE);
        
        btnAddTreatment = new JButton("Agregar");
        btnEditTreatment = new JButton("Editar");
        btnDeleteTreatment = new JButton("Eliminar");
        
        styleButton(btnAddTreatment, new Color(60, 179, 113));
        styleButton(btnEditTreatment, new Color(70, 130, 180));
        styleButton(btnDeleteTreatment, new Color(220, 20, 60));
        
        buttonsPanel.add(btnAddTreatment);
        buttonsPanel.add(btnEditTreatment);
        buttonsPanel.add(btnDeleteTreatment);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createOrdersSection() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(138, 43, 226), 2),
            "Órdenes Médicas",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(138, 43, 226)
        ));
        
        // Tabla de órdenes
        String[] columns = {"ID", "Tipo", "Descripción", "Fecha"};
        ordersModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableOrders = new JTable(ordersModel);
        tableOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableOrders.setRowHeight(25);
        tableOrders.getTableHeader().setReorderingAllowed(false);
        tableOrders.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableOrders.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Ocultar columna ID
        tableOrders.getColumnModel().getColumn(0).setMinWidth(0);
        tableOrders.getColumnModel().getColumn(0).setMaxWidth(0);
        tableOrders.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tableOrders);
        scrollPane.setPreferredSize(new Dimension(0, 120));
        
        // Botones
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonsPanel.setBackground(Color.WHITE);
        
        btnAddOrder = new JButton("Agregar");
        btnEditOrder = new JButton("Editar");
        btnDeleteOrder = new JButton("Eliminar");
        
        styleButton(btnAddOrder, new Color(60, 179, 113));
        styleButton(btnEditOrder, new Color(70, 130, 180));
        styleButton(btnDeleteOrder, new Color(220, 20, 60));
        
        buttonsPanel.add(btnAddOrder);
        buttonsPanel.add(btnEditOrder);
        buttonsPanel.add(btnDeleteOrder);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(Color.WHITE);
        
        btnSave = new JButton("Guardar Evolución");
        btnCloseAttention = new JButton("Cerrar Cita");
        btnCancel = new JButton("Volver");
        
        styleButton(btnSave, new Color(60, 179, 113));
        styleButton(btnCloseAttention, new Color(138, 43, 226));
        styleButton(btnCancel, new Color(220, 20, 60));
        
        btnSave.setPreferredSize(new Dimension(150, 35));
        btnCloseAttention.setPreferredSize(new Dimension(150, 35));
        btnCancel.setPreferredSize(new Dimension(150, 35));
        
        panel.add(btnSave);
        panel.add(btnCloseAttention);
        panel.add(btnCancel);
        
        return panel;
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    // Métodos para establecer información
    public void setPetInfo(String info) {
        lblPetInfo.setText("Mascota: " + info);
    }
    
    public void setOwnerInfo(String info) {
        lblOwnerInfo.setText("Propietario: " + info);
    }
    
    public void setDateInfo(String info) {
        lblDateInfo.setText("Fecha: " + info);
    }
    
    public void setVeterinarianInfo(String info) {
        lblVeterinarianInfo.setText("Veterinario: " + info);
    }
    
    // Métodos para gestionar tratamientos
    public void addTreatmentToTable(String id, String medication, String dosage, String frequency, String duration) {
        treatmentsModel.addRow(new Object[]{id, medication, dosage, frequency, duration});
    }
    
    public void clearTreatmentsTable() {
        treatmentsModel.setRowCount(0);
    }
    
    public String getSelectedTreatmentId() {
        int selectedRow = tableTreatments.getSelectedRow();
        if (selectedRow >= 0) {
            return (String) treatmentsModel.getValueAt(selectedRow, 0);
        }
        return null;
    }
    
    public void removeTreatmentFromTable(int row) {
        if (row >= 0 && row < treatmentsModel.getRowCount()) {
            treatmentsModel.removeRow(row);
        }
    }
    
    public int getSelectedTreatmentRow() {
        return tableTreatments.getSelectedRow();
    }
    
    // Métodos para gestionar órdenes
    public void addOrderToTable(String id, String type, String description, String date) {
        ordersModel.addRow(new Object[]{id, type, description, date});
    }
    
    public void clearOrdersTable() {
        ordersModel.setRowCount(0);
    }
    
    public String getSelectedOrderId() {
        int selectedRow = tableOrders.getSelectedRow();
        if (selectedRow >= 0) {
            return (String) ordersModel.getValueAt(selectedRow, 0);
        }
        return null;
    }
    
    public void removeOrderFromTable(int row) {
        if (row >= 0 && row < ordersModel.getRowCount()) {
            ordersModel.removeRow(row);
        }
    }
    
    public int getSelectedOrderRow() {
        return tableOrders.getSelectedRow();
    }
    
    // Getters para campos de texto
    public String getSymptoms() {
        return txtSymptoms.getText();
    }
    
    public void setSymptoms(String text) {
        txtSymptoms.setText(text);
    }
    
    public String getDiagnosis() {
        return txtDiagnosis.getText();
    }
    
    public void setDiagnosis(String text) {
        txtDiagnosis.setText(text);
    }
    
    public String getProcedures() {
        return txtProcedures.getText();
    }
    
    public void setProcedures(String text) {
        txtProcedures.setText(text);
    }
    
    // Getters para botones
    public JButton getBtnAddTreatment() { return btnAddTreatment; }
    public JButton getBtnEditTreatment() { return btnEditTreatment; }
    public JButton getBtnDeleteTreatment() { return btnDeleteTreatment; }
    public JButton getBtnAddOrder() { return btnAddOrder; }
    public JButton getBtnEditOrder() { return btnEditOrder; }
    public JButton getBtnDeleteOrder() { return btnDeleteOrder; }
    public JButton getBtnSave() { return btnSave; }
    public JButton getBtnCloseAttention() { return btnCloseAttention; }
    public JButton getBtnCancel() { return btnCancel; }
    public JTable getTableTreatments() { return tableTreatments; }
    public JTable getTableOrders() { return tableOrders; }
}