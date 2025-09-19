package clinica_vet.controllers;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class LoginController {
    private JTextField UserTF = new JTextField();
    private JPasswordField Password = new JPasswordField();
    private JButton btnLogin = new JButton("Login");

    public LoginController() {
        btnLogin.addActionListener(e -> {
            String username = UserTF.getText();
            String password = new String(Password.getPassword());
            
        });
    }
}
