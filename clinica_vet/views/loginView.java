package clinica_vet.views;
import javax.swing.*;
public class loginView {
    private static JFrame login = new JFrame("Login");

    public loginView() {
        // creo la ventana de login
        login.setSize(400, 200);
        login.setLayout(null);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // centrar la ventana
        login.setLocationRelativeTo(null);

        // laber pedir usuario
        JLabel TextUser = new JLabel("Ingrese Usuario:");
        TextUser.setBounds(140,10,100,25);
        login.add(TextUser);
        JTextField UserTF = new JTextField();
        UserTF.setBounds(130,30,150,25);
        UserTF.setHorizontalAlignment(JTextField.CENTER);
        login.add(UserTF);

        // laber pedir password 
        JLabel TextPassword = new JLabel("Ingrese contraseña :");
        TextPassword.setBounds(140,50,150,25);
        login.add(TextPassword);
        JPasswordField Password = new JPasswordField();
        Password.setBounds(130,70,150,25);
        login.add(Password);
        // boton login
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(160,100,80,25);
        login.add(btnLogin);

    }

    public void setVisible(boolean b) {
        login.setVisible(b);
    }
}
