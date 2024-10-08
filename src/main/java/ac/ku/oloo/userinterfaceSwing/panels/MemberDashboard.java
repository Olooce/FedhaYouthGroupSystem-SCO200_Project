package ac.ku.oloo.userinterfaceSwing.panels;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing.panels)
 * Created by: oloo
 * On: 08/10/2024. 12:22
 * Description:
 **/

import ac.ku.oloo.models.User;

import javax.swing.*;
import java.awt.*;

public class MemberDashboard extends JPanel {

    public MemberDashboard(User user) {
        setLayout(new BorderLayout());

        // Create tabbed interface
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("User Info", new UserPanel(user).createUserPanel());
        tabbedPane.addTab("Account Statements", new AccountStatementsPanel().createAccountStatementsPanel());
        tabbedPane.addTab("Loans", new MemberLoansPanel().createLoanPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("© " + java.time.Year.now().getValue() + " Oloo Stephen");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(footerLabel, BorderLayout.SOUTH);
    }
}

