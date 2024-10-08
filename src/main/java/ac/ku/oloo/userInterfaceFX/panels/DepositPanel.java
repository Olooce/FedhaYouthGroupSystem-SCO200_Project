package ac.ku.oloo.userInterfaceFX.panels;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX)
 * Created by: oloo
 * On: 26/09/2024. 13:20
 * Description:
 **/

public class DepositPanel {

    public VBox createDepositPanel() {
        VBox vbox = new VBox();
        Label label = new Label("Deposits");
        vbox.getChildren().add(label);
        // TODO: Add more deposit-specific UI components here
        return vbox;
    }
}

