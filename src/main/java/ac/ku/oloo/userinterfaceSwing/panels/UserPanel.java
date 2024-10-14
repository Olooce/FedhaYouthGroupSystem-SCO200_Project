package ac.ku.oloo.userinterfaceSwing.panels;

import ac.ku.oloo.models.User;

import javax.swing.*;
import java.awt.*;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing.panels)
 * Created by: oloo
 * On: 14/10/2024. 20:37
 * Description:
 **/

public class UserPanel extends JPanel {

    private User user;

    public UserPanel(User user) {
        this.user = user;
        createUserPanel();
    }

    public Component createUserPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Insets with padding

        // Display user information using JLabels
        add(createLabel("User ID: " + user.getUserId()));
        add(createLabel("Username: " + user.getUsername()));
        add(createLabel("Role: " + user.getRole()));
        add(createLabel("Member ID: " + user.getMemberId()));
        add(createLabel("Date Created: " + user.getDateCreated()));
        add(createLabel("Date Modified: " + user.getDateModified()));
        return null;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // Aligns the labels to the left
        return label;
    }
}