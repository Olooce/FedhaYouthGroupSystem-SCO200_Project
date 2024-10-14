package ac.ku.oloo.userinterfaceSwing;

import ac.ku.oloo.models.AuthResult;
import ac.ku.oloo.models.User;
import ac.ku.oloo.services.AuthenticationService;
import ac.ku.oloo.userinterfaceSwing.panels.MemberDashboard;
import ac.ku.oloo.userinterfaceSwing.panels.StaffDashboard;

import javax.swing.*;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing)
 * Created by: oloo
 * On: 08/10/2024. 12:36
 * Description: Login screen for the application.
 **/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

public class FedhaLogin extends JFrame {

    public FedhaLogin() {
        setTitle("Fedha Youth Group System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontal space
        gbc.insets = new Insets(10, 10, 10, 10); // Set insets for spacing

        // Title label
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the title
        panel.add(titleLabel, gbc);

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridwidth = 1; // Reset grid width
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST; // Align label to the east
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1; // Move to the next column
        gbc.anchor = GridBagConstraints.WEST; // Align field to the west
        panel.add(usernameField, gbc);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1; // Move to the next column
        panel.add(passwordField, gbc);

        // Remember me checkbox
        JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
        gbc.gridwidth = 2; // Span across two columns
        gbc.gridx = 0; // Reset column
        gbc.gridy = 3; // Next row
        gbc.anchor = GridBagConstraints.WEST; // Align checkbox to the west
        panel.add(rememberMeCheckBox, gbc);

        // Forgotten password link
        JLabel forgottenPasswordLabel = new JLabel("<html><u>Forgotten password?</u></html>");
        forgottenPasswordLabel.setForeground(Color.LIGHT_GRAY);
        forgottenPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgottenPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Handle forgotten password action
                JOptionPane.showMessageDialog(panel, "Password recovery functionality is not implemented.");
            }
        });
        gbc.gridwidth = 1; // Reset grid width
        gbc.gridx = 1; // Move to the next column
        gbc.gridy = 4; // Next row
        gbc.anchor = GridBagConstraints.EAST; // Align to the east
        panel.add(forgottenPasswordLabel, gbc);

        // Login button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(new Color(255, 0, 128));
        loginButton.setForeground(Color.WHITE);
        gbc.gridwidth = 2; // Span across two columns
        gbc.gridx = 0; // Reset column
        gbc.gridy = 5; // Next row
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AuthResult authResult = AuthenticationService.authenticate(usernameField.getText(), new String(passwordField.getPassword()));

                    if (authResult.isAuthenticated()) {
                        User user = authResult.getUser();
                        if (Objects.equals(user.getRole(), "STAFF")) {
                            showMainApp();
                        } else if (Objects.equals(user.getRole(), "MEMBER")) {
                            showMemberApp(user);
                        }
                    } else {
                        showAlert("Login Failed", "Invalid username or password.");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Register button
        JButton registerButton = new JButton("REGISTER");
        registerButton.setBackground(new Color(68, 68, 68));
        registerButton.setForeground(Color.WHITE);
        gbc.gridy = 6; // Next row
        panel.add(registerButton, gbc);

        // Set the panel background color
        panel.setBackground(new Color(0, 0, 0, 0.7f)); // Semi-transparent background

        // Add the panel to the frame
        add(panel);

        // Set the frame visibility
        setVisible(true);
    }

    private void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }

    private void showMainApp() {
        new StaffDashboard();
    }

    private void showMemberApp(User user) {
        new MemberDashboard(user);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FedhaLogin::new);
    }
}
