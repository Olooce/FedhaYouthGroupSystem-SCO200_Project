package ac.ku.oloo.userInterface.panels;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterface)
 * Created by: oloo
 * On: 26/09/2024. 13:18
 * Description:
 **/

public class MemberPanel{

    public VBox createMemberPanel() {
        VBox vbox = new VBox();
        Label label = new Label("Members");
        vbox.getChildren().add(label);
        // TODO: Add more member-specific UI components here
        return vbox;
    }
}
