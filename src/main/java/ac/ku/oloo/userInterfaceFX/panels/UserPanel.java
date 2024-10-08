package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.User;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:48
 * Description: Displaying authenticated user's information
 **/

public class UserPanel {

    private User user;

    public UserPanel(User user) {
        this.user = user;
    }

    public VBox createUserPanel() {
        VBox userInfoPanel = new VBox(10);
        userInfoPanel.setPadding(new Insets(20));

        // Display user information
        userInfoPanel.getChildren().addAll(
                new Label("User ID: " + user.getUserId()),
                new Label("Username: " + user.getUsername()),
                new Label("Role: " + user.getRole()),
                new Label("Member ID: " + user.getMemberId()),
                new Label("Date Created: " + user.getDateCreated()),
                new Label("Date Modified: " + user.getDateModified())
        );

        return userInfoPanel;
    }
}