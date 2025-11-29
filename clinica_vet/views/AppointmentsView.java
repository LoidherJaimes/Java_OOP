package clinica_vet.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppointmentsView extends JPanel {

    private JTable appointmentsTable;
    private DefaultTableModel tableModel;

    // Filters
    private JComboBox<String> filterStatusCombo;
    private JComboBox<String> filterDoctorCombo;
    private JSpinner filterDateSpinner;
    private JButton btnApplyFilters;
    private JButton btnClearFilters;

    // Action buttons
    private JButton btnCreate;
    private JButton btnEdit;
    private JButton btnCancel;
    private JButton btnConfirm;
    private JButton btnComplete;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnStartAttention;
    private JComboBox<String> viewModeCombo;
    
    private JLabel filterInfoLabel;

    public AppointmentsView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel filterPanel = createFilterPanel();
        add(filterPanel, BorderLayout.WEST);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Gestión de Citas", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(70, 130, 180));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel viewPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        viewPanel.setBackground(Color.WHITE);
        JLabel viewLabel = new JLabel("Vista:");
        viewModeCombo = new JComboBox<>(new String[]{"Vista Tabla", "Vista Día", "Vista Semana"});
        viewPanel.add(viewLabel);
        viewPanel.add(viewModeCombo);

        panel.add(title, BorderLayout.CENTER);
        panel.add(viewPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createTitledBorder("Filtros"));
        panel.setPreferredSize(new Dimension(200, 0));

        JLabel statusLabel = new JLabel("Estado:");
        filterStatusCombo = new JComboBox<>(new String[]{
            "Todos", "Pendiente", "Confirmada", "Cancelada", "Completada"
        });

        JLabel doctorLabel = new JLabel("Médico:");
        filterDoctorCombo = new JComboBox<>();
        filterDoctorCombo.addItem("Todos los Médicos");

        JLabel dateLabel = new JLabel("Fecha:");
        SpinnerDateModel dateModel = new SpinnerDateModel();
        filterDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(filterDateSpinner, "dd/MM/yyyy");
        filterDateSpinner.setEditor(dateEditor);

        btnApplyFilters = new JButton("Aplicar Filtros");
        btnClearFilters = new JButton("Limpiar Filtros");
        styleFilterButton(btnApplyFilters, new Color(60, 179, 113));
        styleFilterButton(btnClearFilters, new Color(220, 20, 60));

        panel.add(Box.createVerticalStrut(10));
        panel.add(statusLabel);
        panel.add(filterStatusCombo);
        panel.add(Box.createVerticalStrut(15));
        panel.add(doctorLabel);
        panel.add(filterDoctorCombo);
        panel.add(Box.createVerticalStrut(15));
        panel.add(dateLabel);
        panel.add(filterDateSpinner);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnApplyFilters);
        panel.add(Box.createVerticalStrut(5));
        panel.add(btnClearFilters);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        String[] columnNames = {"ID", "Fecha", "Hora", "Mascota", "Dueño", "Médico", "Motivo", "Duración", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        appointmentsTable = new JTable(tableModel);
        appointmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentsTable.setRowHeight(25);
        appointmentsTable.getTableHeader().setReorderingAllowed(false);

        appointmentsTable.getColumnModel().getColumn(0).setMinWidth(0);
        appointmentsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        appointmentsTable.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(Color.WHITE);

        btnCreate = new JButton("Crear");
        btnEdit = new JButton("Editar");
        btnCancel = new JButton("Cancelar");
        btnConfirm = new JButton("Confirmar");
        btnComplete = new JButton("Completar");
        btnDelete = new JButton("Eliminar");
        btnRefresh = new JButton("Actualizar");
        btnStartAttention = new JButton("Iniciar Atencion"); // ⭐ NUEVO

        styleActionButton(btnCreate, new Color(60, 179, 113));
        styleActionButton(btnEdit, new Color(70, 130, 180));
        styleActionButton(btnCancel, new Color(220, 20, 60));
        styleActionButton(btnConfirm, new Color(34, 139, 34));
        styleActionButton(btnComplete, new Color(138, 43, 226));
        styleActionButton(btnDelete, new Color(178, 34, 34));
        styleActionButton(btnRefresh, new Color(100, 149, 237));
        styleActionButton(btnStartAttention, new Color(255, 140, 0)); // ⭐ NUEVO: Color naranja

        panel.add(btnCreate);
        panel.add(btnEdit);
        panel.add(btnConfirm);
        panel.add(btnStartAttention); // ⭐ NUEVO: Agregado después de Confirmar
        panel.add(btnCancel);
        panel.add(btnComplete);
        panel.add(btnDelete);
        panel.add(btnRefresh);

        return panel;
    }

    private void styleActionButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleFilterButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addAppointmentToTable(String id, String date, String time, String pet, 
                                     String owner, String doctor, String reason, 
                                     String duration, String status) {
        tableModel.addRow(new Object[]{id, date, time, pet, owner, doctor, reason, duration, status});
    }

    public String getSelectedAppointmentId() {
        int selectedRow = appointmentsTable.getSelectedRow();
        if (selectedRow >= 0) {
            return (String) tableModel.getValueAt(selectedRow, 0);
        }
        return null;
    }

    public int getSelectedRow() {
        return appointmentsTable.getSelectedRow();
    }

    public String getSelectedStatus() {
        return (String) filterStatusCombo.getSelectedItem();
    }

    public String getSelectedDoctor() {
        return (String) filterDoctorCombo.getSelectedItem();
    }

    public java.util.Date getSelectedDate() {
        return (java.util.Date) filterDateSpinner.getValue();
    }

    public String getSelectedViewMode() {
        return (String) viewModeCombo.getSelectedItem();
    }

    public void loadDoctors(java.util.List<String> doctors) {
        filterDoctorCombo.removeAllItems();
        filterDoctorCombo.addItem("Todos los Médicos");
        for (String doctor : doctors) {
            filterDoctorCombo.addItem(doctor);
        }
    }
    
    public void setFilterInfo(String info) {
        if (filterInfoLabel != null) {
            filterInfoLabel.setText(info);
        }
    }
    
    public void clearFilterInfo() {
        if (filterInfoLabel != null) {
            filterInfoLabel.setText("");
        }
    }

    public JButton getBtnCreate() { return btnCreate; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnCancel() { return btnCancel; }
    public JButton getBtnConfirm() { return btnConfirm; }
    public JButton getBtnComplete() { return btnComplete; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnApplyFilters() { return btnApplyFilters; }
    public JButton getBtnClearFilters() { return btnClearFilters; }
    public JButton getBtnStartAttention() { return btnStartAttention; }
    public JComboBox<String> getViewModeCombo() { return viewModeCombo; }
    public JTable getAppointmentsTable() { return appointmentsTable; }
    public JSpinner getFilterDateSpinner() { return filterDateSpinner; }
}