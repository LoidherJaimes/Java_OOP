package clinica_vet.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MedicalHistoryView extends JPanel {
    
    private JTextField txtSearchPet;
    private JButton btnSearch;
    
    private JLabel lblPetInfo;
    private JLabel lblOwnerInfo;
    private JLabel lblPetDetails;
    
    private JTable tableAttentions;
    private DefaultTableModel attentionsModel;
    
    private JTextArea txtAttentionDetail;
    
    private JButton btnViewDetail;
    private JButton btnPrint;
    private JButton btnExport;
    private JButton btnClose;
    
    public MedicalHistoryView() {
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(780, 550));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        initComponents();
    }
    
    private void initComponents() {
        JLabel titleLabel = new JLabel("Historia Clínica", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Buscar Mascota",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(70, 130, 180)
        ));
        
        JLabel lblSearch = new JLabel("Nombre:");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        txtSearchPet = new JTextField(20);
        txtSearchPet.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        btnSearch = new JButton("Buscar");
        styleButton(btnSearch, new Color(70, 130, 180));
        
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearchPet);
        searchPanel.add(btnSearch);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Panel de información de mascota
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBackground(new Color(240, 248, 255));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        lblPetInfo = new JLabel("Mascota: Seleccione una mascota para ver su historial");
        lblOwnerInfo = new JLabel("Propietario: ");
        lblPetDetails = new JLabel("Detalles: ");
        
        Font infoFont = new Font("Segoe UI", Font.BOLD, 12);
        lblPetInfo.setFont(infoFont);
        lblOwnerInfo.setFont(infoFont);
        lblPetDetails.setFont(infoFont);
        
        infoPanel.add(lblPetInfo);
        infoPanel.add(lblOwnerInfo);
        infoPanel.add(lblPetDetails);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 179, 113), 2),
            "Atenciones Médicas Registradas",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(34, 139, 34)
        ));
        
        String[] columns = {"ID", "Fecha", "Veterinario", "Diagnóstico", "Estado"};
        attentionsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableAttentions = new JTable(attentionsModel);
        tableAttentions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAttentions.setRowHeight(25);
        tableAttentions.getTableHeader().setReorderingAllowed(false);
        tableAttentions.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableAttentions.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        tableAttentions.getColumnModel().getColumn(0).setMinWidth(0);
        tableAttentions.getColumnModel().getColumn(0).setMaxWidth(0);
        tableAttentions.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tableAttentions);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        btnViewDetail = new JButton("Ver Detalle Completo");
        styleButton(btnViewDetail, new Color(138, 43, 226));
        buttonPanel.add(btnViewDetail);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(138, 43, 226), 2),
            "Detalle de Atención Seleccionada",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 13),
            new Color(138, 43, 226)
        ));
        
        txtAttentionDetail = new JTextArea(6, 20);
        txtAttentionDetail.setEditable(false);
        txtAttentionDetail.setLineWrap(true);
        txtAttentionDetail.setWrapStyleWord(true);
        txtAttentionDetail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtAttentionDetail.setBackground(new Color(250, 250, 250));
        txtAttentionDetail.setText("Seleccione una atención de la tabla para ver los detalles completos.");
        
        JScrollPane scrollPane = new JScrollPane(txtAttentionDetail);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(Color.WHITE);
        
        btnPrint = new JButton("Imprimir");
        btnExport = new JButton("Exportar PDF");
        btnClose = new JButton("Volver");
        
        styleButton(btnPrint, new Color(100, 149, 237));
        styleButton(btnExport, new Color(60, 179, 113));
        styleButton(btnClose, new Color(220, 20, 60));
        
        btnPrint.setPreferredSize(new Dimension(120, 35));
        btnExport.setPreferredSize(new Dimension(120, 35));
        btnClose.setPreferredSize(new Dimension(120, 35));
        
        panel.add(btnPrint);
        panel.add(btnExport);
        panel.add(btnClose);
        
        return panel;
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public void setPetInfo(String info) {
        lblPetInfo.setText("Mascota: " + info);
    }
    
    public void setOwnerInfo(String info) {
        lblOwnerInfo.setText("Propietario: " + info);
    }
    
    public void setPetDetails(String info) {
        lblPetDetails.setText("Detalles: " + info);
    }
    
    public void clearPetInfo() {
        lblPetInfo.setText("Mascota: Seleccione una mascota para ver su historial");
        lblOwnerInfo.setText("Propietario: ");
        lblPetDetails.setText("Detalles: ");
    }
    
    // Métodos para gestionar tabla de atenciones
    public void addAttentionToTable(String id, String date, String veterinarian, 
                                   String diagnosis, String status) {
        attentionsModel.addRow(new Object[]{id, date, veterinarian, diagnosis, status});
    }
    
    public void clearAttentionsTable() {
        attentionsModel.setRowCount(0);
    }
    
    public String getSelectedAttentionId() {
        int selectedRow = tableAttentions.getSelectedRow();
        if (selectedRow >= 0) {
            return (String) attentionsModel.getValueAt(selectedRow, 0);
        }
        return null;
    }
    
    public int getSelectedAttentionRow() {
        return tableAttentions.getSelectedRow();
    }
    
    public void setAttentionDetail(String detail) {
        txtAttentionDetail.setText(detail);
    }
    
    public void clearAttentionDetail() {
        txtAttentionDetail.setText("Seleccione una atención de la tabla para ver los detalles completos.");
    }
    
    public String getSearchText() {
        return txtSearchPet.getText().trim();
    }
    
    public void clearSearchText() {
        txtSearchPet.setText("");
    }
    
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnViewDetail() { return btnViewDetail; }
    public JButton getBtnPrint() { return btnPrint; }
    public JButton getBtnExport() { return btnExport; }
    public JButton getBtnClose() { return btnClose; }
    public JTable getTableAttentions() { return tableAttentions; }
    public JTextField getTxtSearchPet() { return txtSearchPet; }
}