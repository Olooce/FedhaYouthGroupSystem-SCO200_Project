package ac.ku.oloo.userinterfaceSwing.panels;

import javax.swing.*;
import java.awt.*;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing.panels)
 * Created by: oloo
 * On: 14/10/2024. 20:41
 * Description:
 **/

public class MemberLoansPanel extends JPanel {

    public MemberLoansPanel() {
        createLoanPanel();
    }

    public Component createLoanPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // Vertical stacking of components
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Padding similar to VBox Insets

        // Add loan details as JLabels
        add(createLabel("Loan 1: ..."));
        add(createLabel("Loan 2: ..."));
        add(createLabel("Loan 3: ..."));
        return null;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);  // Aligns the label to the left
        return label;
    }
}