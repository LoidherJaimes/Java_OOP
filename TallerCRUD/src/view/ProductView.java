package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductView extends JFrame {
    public JTextField txtName = new JTextField(15);
    public JTextField txtPrice = new JTextField(8);

    public JLabel lblPrice = new JLabel("Price:");
    
    public JButton btnAdd = new JButton("Add");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnDelete = new JButton("Delete");

    public JTable table;
    public DefaultTableModel tableModel;

    public ProductView() {
        setTitle("Gestión de Productos");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelForm.add(new JLabel("Name"));
        panelForm.add(txtName);
        panelForm.add(lblPrice);
        panelForm.add(txtPrice);

        panelForm.add(btnAdd);
        panelForm.add(btnUpdate);
        panelForm.add(btnDelete);
        
        tableModel = new DefaultTableModel(new Object[]{"Id", "Name", "Price"}), 0){
            @Override

            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionModel(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);

        add(panelForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}
