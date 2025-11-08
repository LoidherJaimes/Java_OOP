package clinica_vet.views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CreateUserView {
    private JFrame createUserF;
    private JTextField createUserTF;
    private JPasswordField createPasswordPF;
    private JPasswordField verificationPasswordPF;
    private JButton btnCreateUserL;
    private JComboBox<String> rolComboBox; // ¡NUEVO CAMPO!

    public CreateUserView() {
        // Crear la ventana principal
        createUserF = new JFrame("Crear usuario");
        createUserF.setSize(400, 350); // Aumentar altura para el ComboBox
        createUserF.setLayout(null);
        createUserF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Centrar la ventana
        createUserF.setLocationRelativeTo(null);

        // Label pedir usuario
        JLabel textUserL = new JLabel("Ingrese Usuario:");
        textUserL.setBounds(140, 10, 120, 25);
        createUserF.add(textUserL);

        // Text field usuario
        createUserTF = new JTextField();
        createUserTF.setBounds(130, 30, 150, 25);
        createUserTF.setHorizontalAlignment(JTextField.CENTER);
        createUserF.add(createUserTF);

        // Label pedir password
        JLabel textPasswordL = new JLabel("Ingrese contraseña:");
        textPasswordL.setBounds(140, 60, 150, 25);
        createUserF.add(textPasswordL);

        // Password field
        createPasswordPF = new JPasswordField();
        createPasswordPF.setBounds(130, 80, 150, 25);
        createUserF.add(createPasswordPF);

        // Label pedir verificación password
        JLabel textPassword2L = new JLabel("Confirme la contraseña:");
        textPassword2L.setBounds(140, 110, 180, 25);
        createUserF.add(textPassword2L);

        // Password field verificación
        verificationPasswordPF = new JPasswordField();
        verificationPasswordPF.setBounds(130, 130, 150, 25);
        createUserF.add(verificationPasswordPF);

        // NUEVOS ELEMENTOS PARA EL ROL
        JLabel textRolL = new JLabel("Seleccione Rol:");
        textRolL.setBounds(140, 160, 180, 25);
        createUserF.add(textRolL);
        
        // ComboBox de Roles
        rolComboBox = new JComboBox<>();
        rolComboBox.setBounds(130, 180, 150, 25);
        createUserF.add(rolComboBox);

        // Botón crear usuario
        btnCreateUserL = new JButton("Crear");
        btnCreateUserL.setBounds(150, 230, 100, 25);
        createUserF.add(btnCreateUserL);
    }

    // Getters
    public JFrame getCreateUserF() { return createUserF; }
    public JPasswordField getCreatePasswordPF() { return createPasswordPF; }
    public JPasswordField getVerificationPasswordPF() { return verificationPasswordPF; }
    public JTextField getCreateUserTF() { return createUserTF; }
    public JButton getBtnCreateUserL() { return btnCreateUserL; }
    public JComboBox<String> getRolComboBox() { return rolComboBox; } 

    public void setVisible(boolean visible) {
        createUserF.setVisible(visible);
    }
}