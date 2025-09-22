package clinica_vet.views;
import javax.swing.*;

public class CreateUserView {
    private JFrame CreateuserF;
    private JTextField createuserTF;
    private JPasswordField createpasswordPF;
    private JPasswordField verificationpasswordPF;
    private JButton btnCreateUserL;

    public CreateUserView() {
        //crear la ventana principal
        CreateuserF = new JFrame("crear usuario");
        CreateuserF.setSize(400, 300);
        CreateuserF.setLayout(null);
        CreateuserF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //centrar la ventana
        CreateuserF.setLocationRelativeTo(null);
        //label pedir usuario
        JLabel TextUserL = new JLabel("Ingrese Usuario:");
        TextUserL.setBounds(140,10,100,25);
        CreateuserF.add(TextUserL);
        // text field usuario
        JTextField createuserTF = new JTextField();
        createuserTF.setBounds(130,30,150,25);
        createuserTF.setHorizontalAlignment(JTextField.CENTER);
        CreateuserF.add(createuserTF);
        //label pedir password
        // label pedir password
        JLabel TextPasswordL = new JLabel("Ingrese contraseña :");
        TextPasswordL.setBounds(140,50,150,25);
        CreateuserF.add(TextPasswordL);
        // text field password
        JPasswordField createpasswordPF = new JPasswordField();
        createpasswordPF.setBounds(130,70,150,25);
        CreateuserF.add(createpasswordPF);
        // label pedir verificacion password
        JLabel TextPasswor2dL = new JLabel("confirme la contraseña :");
        TextPasswor2dL.setBounds(140,90,150,25);
        CreateuserF.add(TextPasswor2dL);
        // text field verificacion password
        JPasswordField verificationpasswordPF = new JPasswordField();
        verificationpasswordPF.setBounds(130,120,150,25);
        CreateuserF.add(verificationpasswordPF);
        //boton crear usuario
        JButton btnCreateUserL = new JButton("Crear");
        btnCreateUserL.setBounds(150,160,100,25);
        CreateuserF.add(btnCreateUserL);

    }
    public JFrame getCreateuserF() { return CreateuserF; }
    public JPasswordField getCreatepasswordPF() { return createpasswordPF; }
    public JPasswordField getVerificationpasswordPF() { return verificationpasswordPF; }
    public JTextField getCreateuserTF() { return createuserTF; }
    public JButton getBtnCreateUserL() { return btnCreateUserL; }

    public void setVisible(boolean visible) {
        CreateuserF.setVisible(visible);
    }
}
