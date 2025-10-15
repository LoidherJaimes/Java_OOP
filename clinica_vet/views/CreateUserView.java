package clinica_vet.views;

import javax.swing.*;

public class CreateUserView {
    private JFrame createUserF;
    private JTextField createUserTF;
    private JPasswordField createPasswordPF;
    private JPasswordField verificationPasswordPF;
    private JButton btnCreateUserL;

    public CreateUserView() {
        // Crear la ventana principal
        createUserF = new JFrame("Crear usuario");
        createUserF.setSize(400, 300);
        createUserF.setLayout(null);
        createUserF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Centrar la ventana
        createUserF.setLocationRelativeTo(null);

        // Label pedir usuario
        JLabel textUserL = new JLabel("Ingrese Usuario:");
        textUserL.setBounds(140, 10, 120, 25);
        createUserF.add(textUserL);

        // Text field usuario (ahora inicializa el atributo)
        createUserTF = new JTextField();
        createUserTF.setBounds(130, 30, 150, 25);
        createUserTF.setHorizontalAlignment(JTextField.CENTER);
        createUserF.add(createUserTF);

        // Label pedir password
        JLabel textPasswordL = new JLabel("Ingrese contraseña:");
        textPasswordL.setBounds(140, 60, 150, 25);
        createUserF.add(textPasswordL);

        // Password field (atributo, no local)
        createPasswordPF = new JPasswordField();
        createPasswordPF.setBounds(130, 80, 150, 25);
        createUserF.add(createPasswordPF);

        // Label pedir verificación password
        JLabel textPassword2L = new JLabel("Confirme la contraseña:");
        textPassword2L.setBounds(140, 110, 180, 25);
        createUserF.add(textPassword2L);

        // Password field verificación (atributo, no local)
        verificationPasswordPF = new JPasswordField();
        verificationPasswordPF.setBounds(130, 130, 150, 25);
        createUserF.add(verificationPasswordPF);

        // Botón crear usuario (atributo, no local)
        btnCreateUserL = new JButton("Crear");
        btnCreateUserL.setBounds(150, 170, 100, 25);
        createUserF.add(btnCreateUserL);
    }

    // Getters
    public JFrame getCreateUserF() { return createUserF; }
    public JPasswordField getCreatePasswordPF() { return createPasswordPF; }
    public JPasswordField getVerificationPasswordPF() { return verificationPasswordPF; }
    public JTextField getCreateUserTF() { return createUserTF; }
    public JButton getBtnCreateUserL() { return btnCreateUserL; }
    public JButton getBtnCreateUser() { return btnCreateUser; }

    public void setVisible(boolean visible) {
        createUserF.setVisible(visible);
    }
}
