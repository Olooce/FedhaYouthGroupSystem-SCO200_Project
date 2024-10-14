package ac.ku.oloo.userinterfaceSwing.panels;

import javax.swing.*;
import java.awt.*;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing.panels)
 * Created by: oloo
 * On: 14/10/2024. 20:39
 * Description:
 **/
public class AccountStatementsPanel extends JPanel {

    public AccountStatementsPanel() {
        createAccountStatementsPanel();
    }

    public Component createAccountStatementsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // Vertically stack components like VBox
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Padding similar to VBox Insets

        // Add account statements using JLabels
        add(createLabel("Account Statement 1: ..."));
        add(createLabel("Account Statement 2: ..."));
        add(createLabel("Account Statement 3: ..."));
        return null;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // Aligns the label to the left
        return label;
    }
}