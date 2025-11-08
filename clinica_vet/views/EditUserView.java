package clinica_vet.views;

import clinica_vet.model.entities.User;
import javax.swing.*;
import java.awt.*;

public class EditUserView extends JDialog {

    private JTextField usernameTF;
    private JPasswordField passwordPF;
    private JComboBox<String> rolComboBox;
    private JButton btnSave;
    private JButton btnCancel;

    public EditUserView(JFrame owner, User userToEdit) {
        super(owner, "Modificar Usuario: " + userToEdit.getUsername(), true);
        setSize(400, 350);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel title = new JLabel("Editar Usuario");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);
        gbc.gridwidth = 1;

        // Nombre de Usuario
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Usuario:"), gbc);
        usernameTF = new JTextField(userToEdit.getUsername());
        gbc.gridx = 1; gbc.gridy = 1; add(usernameTF, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Nueva Contraseña:"), gbc);
        passwordPF = new JPasswordField();
        passwordPF.setToolTipText("Dejar vacío para no cambiar la contraseña.");
        gbc.gridx = 1; gbc.gridy = 2; add(passwordPF, gbc);

        // Rol
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Rol:"), gbc);
        rolComboBox = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 3; add(rolComboBox, gbc);

        // Botones
        btnSave = new JButton("Guardar Cambios");
        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }
    
    // Getters para el controlador
    public JTextField getUsernameTF() { return usernameTF; }
    public JPasswordField getPasswordPF() { return passwordPF; }
    public JComboBox<String> getRolComboBox() { return rolComboBox; }
    public JButton getBtnSave() { return btnSave; }
}