package ac.ku.oloo.userInterface.panels;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterface.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:58
 * Description:
 **/

public class AccountStatementsPanel {

    public VBox createAccountStatementsPanel() {
        VBox statementsPanel = new VBox(10);
        statementsPanel.setPadding(new Insets(20));

        statementsPanel.getChildren().addAll(
                new Label("Account Statement 1: ..."),
                new Label("Account Statement 2: ..."),
                new Label("Account Statement 3: ...")
        );

        return statementsPanel;
    }
}
