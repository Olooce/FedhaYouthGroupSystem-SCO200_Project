package ac.ku.oloo.userinterfaceSwing;

import javax.swing.*;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing)
 * Created by: oloo
 * On: 08/10/2024. 12:36
 * Description: Login screen for the application.
 **/

public class FedhaLogin extends JFrame {

    public FedhaLogin() {
        setTitle("Fedha Youth Group System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up login components here
        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(new JTextField(15));
        panel.add(new JLabel("Password:"));
        panel.add(new JPasswordField(15));

        JButton loginButton = new JButton("Login");
        // Add login button functionality here

        panel.add(loginButton);
        add(panel);

        setVisible(true); // Display the login screen
    }

    public static void main(String[] args) {
        new FedhaLogin(); // Launch the login screen directly for testing
    }
}
