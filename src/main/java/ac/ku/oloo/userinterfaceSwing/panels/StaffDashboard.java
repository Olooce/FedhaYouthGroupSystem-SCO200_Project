package ac.ku.oloo.userinterfaceSwing.panels;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing.panels)
 * Created by: oloo
 * On: 08/10/2024. 12:25
 * Description:
 **/

import javax.swing.*;
import java.awt.*;

public class StaffDashboard extends JPanel {

    public StaffDashboard() {
        setLayout(new BorderLayout());

        // Create menu bar
        JMenuBar menuBar = createMenuBar();
        add(menuBar, BorderLayout.NORTH);

        // Add content panel
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Members", new MembersPanel());
        tabbedPane.addTab("Loans", new LoansPanel());
        tabbedPane.addTab("Deposits", new DepositPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("© " + java.time.Year.now().getValue() + " Oloo Stephen");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(footerLabel, BorderLayout.SOUTH);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        return menuBar;
    }
}
