package clinica_vet.views;

import javax.swing.*;

public class LoginView extends JFrame {
    private JTextField userTF;
    private JPasswordField passwordPF;
    private JButton btnLogin;
    private JButton btnCreateUser;

    public LoginView() {
        setTitle("Login");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel TextUser = new JLabel("Ingrese Usuario:");
        TextUser.setBounds(140, 10, 120, 25);
        add(TextUser);

        userTF = new JTextField();
        userTF.setBounds(130, 30, 150, 25);
        userTF.setHorizontalAlignment(JTextField.CENTER);
        add(userTF);

        JLabel TextPassword = new JLabel("Ingrese contraseña:");
        TextPassword.setBounds(140, 50, 150, 25);
        add(TextPassword);

        passwordPF = new JPasswordField();
        passwordPF.setBounds(130, 70, 150, 25);
        add(passwordPF);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(160, 100, 80, 25);
        add(btnLogin);

        JLabel TextCreateUser = new JLabel("Crear Usuario");
        TextCreateUser.setBounds(160, 130, 100, 25);
        add(TextCreateUser);

        btnCreateUser = new JButton("Crear");
        btnCreateUser.setBounds(150, 150, 100, 25);
        add(btnCreateUser);
    }

    public JTextField getUserTF() { return userTF; }
    public JPasswordField getPasswordPF() { return passwordPF; }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnCreateUser() { return btnCreateUser; }
}
