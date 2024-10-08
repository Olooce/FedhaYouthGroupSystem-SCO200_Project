package ac.ku.oloo.userinterfaceSwing;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing)
 * Created by: oloo
 * On: 08/10/2024. 12:20
 * Description:
 **/

import ac.ku.oloo.models.AuthResult;
import ac.ku.oloo.models.User;
import ac.ku.oloo.services.AuthenticationService;
import ac.ku.oloo.userinterfaceSwing.panels.MemberDashboard;
import ac.ku.oloo.userinterfaceSwing.panels.StaffDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class UserInterface extends JFrame {

    public UserInterface() {
        setTitle("Fedha Group");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Show the start screen
        showStartScreen();
    }

    private void showStartScreen() {
        JPanel startScreen = new JPanel();
        startScreen.setLayout(new BorderLayout());

        // Load the image
        ImageIcon logo = new ImageIcon(getClass().getResource("/logo.png"));
        JLabel imageLabel = new JLabel(logo);
        startScreen.add(imageLabel, BorderLayout.CENTER);

        // Loading animation
        JProgressBar loadingIndicator = new JProgressBar();
        loadingIndicator.setIndeterminate(true);
        loadingIndicator.setString("Loading...");
        loadingIndicator.setStringPainted(true);
        startScreen.add(loadingIndicator, BorderLayout.SOUTH);

        add(startScreen);
        setVisible(true);

        // Timer to switch screens after a delay
        new Timer(3000, e -> showLoginScreen()).start();
    }

    private void showLoginScreen() {
        removeAllComponents();

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(usernameLabel, gbc);
        JTextField usernameField = new JTextField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(passwordLabel, gbc);
        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passwordField, gbc);

        // Remember me checkbox
        JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(rememberMeCheckBox, gbc);

        // Forgotten password link
        JLabel forgottenPasswordLabel = new JLabel("<html><a href='#'>Forgotten password?</a></html>");
        forgottenPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        loginPanel.add(forgottenPasswordLabel, gbc);

        // Login button
        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener((ActionEvent e) -> {
            try {
                AuthResult authResult = AuthenticationService.authenticate(usernameField.getText(), new String(passwordField.getPassword()));

                if (authResult.isAuthenticated()) {
                    User user = authResult.getUser();
                    if ("STAFF".equals(user.getRole())) {
                        showStaffDashboard();
                    } else if ("MEMBER".equals(user.getRole())) {
                        showMemberDashboard(user);
                    }
                } else {
                    showAlert("Login Failed", "Invalid username or password.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error", "An error occurred during authentication.");
            }
        });
        gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Register button
        JButton registerButton = new JButton("REGISTER");
        gbc.gridy = 6;
        loginPanel.add(registerButton, gbc);

        add(loginPanel);
        revalidate();
        repaint();
    }

    private void showStaffDashboard() {
        removeAllComponents();
        StaffDashboard staffDashboard = new StaffDashboard();
        add(staffDashboard);
        revalidate();
        repaint();
    }

    private void showMemberDashboard(User user) {
        removeAllComponents();
        MemberDashboard memberDashboard = new MemberDashboard(user);
        add(memberDashboard);
        revalidate();
        repaint();
    }

    private void removeAllComponents() {
        getContentPane().removeAll();
    }

    private void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserInterface().setVisible(true));
    }
}
