package clinica_vet.views;
import javax.swing.*;

public class loginView {
    private JFrame login;
    private JTextField userTF;
    private JPasswordField passwordPF;
    private JButton btnLogin;
    private JButton btnCreateUser;

    public loginView() {
        // creo la ventana de login
        login = new JFrame("Login");
        login.setSize(400, 250);
        login.setLayout(null);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // centrar la ventana
        login.setLocationRelativeTo(null);

        // label pedir usuario
        JLabel TextUser = new JLabel("Ingrese Usuario:");
        TextUser.setBounds(140,10,100,25);
        login.add(TextUser);

        userTF = new JTextField();
        userTF.setBounds(130,30,150,25);
        userTF.setHorizontalAlignment(JTextField.CENTER);
        login.add(userTF);

        // label pedir password 
        JLabel TextPassword = new JLabel("Ingrese contraseña :");
        TextPassword.setBounds(140,50,150,25);
        login.add(TextPassword);

        passwordPF = new JPasswordField();
        passwordPF.setBounds(130,70,150,25);
        login.add(passwordPF);

        // boton login
        btnLogin = new JButton("Login");
        btnLogin.setBounds(160,100,80,25);
        login.add(btnLogin);

        // crear usuario
        JLabel TextCreateUser = new JLabel("Crear Usuario");
        TextCreateUser.setBounds(160,130,100,25);
        login.add(TextCreateUser);

        btnCreateUser = new JButton("Crear");
        btnCreateUser.setBounds(150,150,100,25);
        login.add(btnCreateUser);
    }

    public JTextField getUserTF() { return userTF; }
    public JPasswordField getPasswordPF() { return passwordPF; }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnCreateUser() { return btnCreateUser; }

    public void setVisible(boolean b) {
        login.setVisible(b);
    }
}
