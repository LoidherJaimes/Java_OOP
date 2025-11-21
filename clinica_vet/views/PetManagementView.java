package clinica_vet.views;

import java.awt.*;
import java.util.UUID;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PetManagementView extends JPanel { 

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClose;
    private JButton btnCreate;

    public PetManagementView() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(780, 550)); 
        
        // Título
        JLabel title = new JLabel("Gestión de Mascotas", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Columnas: ID, Nombre, Dueño, Especie, Raza, Edad, Sexo, Peso
        String[] columnNames = {"ID", "Nombre", "Dueño", "Especie", "Raza", "Edad", "Sexo", "Peso (Kg)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnCreate = new JButton("Crear Mascota");
        btnEdit = new JButton("Modificar");
        btnDelete = new JButton("Eliminar");
        btnClose = new JButton("Volver al Menú Principal");

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    // Orden corregido: ID, Nombre, Dueño, Especie, Raza, Edad, Sexo, Peso
    public void addPetToTable(UUID id, String name, String ownerName, String species, 
                               String race, double age, String sex, double weight) {
        tableModel.addRow(new Object[]{id, name, ownerName, species, race, age, sex, weight});
    }
    
    // Getters
    public JTable getTable() { return table; }
    public JButton getBtnCreate() { return btnCreate; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClose() { return btnClose; }
}