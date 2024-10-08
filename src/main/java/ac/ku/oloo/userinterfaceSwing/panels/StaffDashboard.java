package ac.ku.oloo.userinterfaceSwing.panels;

import ac.ku.oloo.userinterfaceSwing.FedhaLogin;

import javax.swing.*;
import java.awt.*;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing.panels)
 * Created by: oloo
 * On: 08/10/2024. 12:25
 * Description: Staff Dashboard for managing members, loans, and deposits.
 **/

public class StaffDashboard extends JPanel {

    public StaffDashboard() {
        setLayout(new BorderLayout());

        // Create the tabbed pane for different functionalities
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Manage Members", new MembersPanel());
        tabbedPane.addTab("View Loans", createLoanPanel());
        tabbedPane.addTab("Manage Shares", createSharesPanel());
        tabbedPane.addTab("Fixed Deposits", createFixedDepositsPanel());
        tabbedPane.addTab("Generate Reports", createReportsPanel());

        // Logout button functionality
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(StaffDashboard.this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                navigateToLogin(); // Navigate to the login screen
            }
        });

        // Main panel to hold the tabbed pane and logout button
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(logoutButton, BorderLayout.SOUTH);

        add(mainPanel);

        // Footer
        JLabel footerLabel = new JLabel("© " + java.time.Year.now().getValue() + " Oloo Stephen");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(footerLabel, BorderLayout.SOUTH);
    }

    private void navigateToLogin() {
        // Close the current dashboard
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.dispose(); // Close the dashboard window
        }
        new FedhaLogin(); // Open the login screen
    }

    private JPanel createLoanPanel() {
        JPanel loanPanel = new JPanel();
        loanPanel.add(new JLabel("Loan Management - Feature Coming Soon!"));
        return loanPanel;
    }

    private JPanel createSharesPanel() {
        JPanel sharesPanel = new JPanel();
        sharesPanel.add(new JLabel("Shares Management - Feature Coming Soon!"));
        return sharesPanel;
    }

    private JPanel createFixedDepositsPanel() {
        JPanel fixedDepositsPanel = new JPanel();
        fixedDepositsPanel.add(new JLabel("Fixed Deposits Management - Feature Coming Soon!"));
        return fixedDepositsPanel;
    }

    private JPanel createReportsPanel() {
        JPanel reportsPanel = new JPanel();
        reportsPanel.add(new JLabel("Reports Generation - Feature Coming Soon!"));
        return reportsPanel;
    }
}
