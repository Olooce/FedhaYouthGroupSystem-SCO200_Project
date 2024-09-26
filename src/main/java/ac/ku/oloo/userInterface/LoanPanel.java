package ac.ku.oloo.userInterface;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterface)
 * Created by: oloo
 * On: 26/09/2024. 13:19
 * Description:
 **/


public class LoanPanel {

    public VBox createLoanPanel() {
        VBox vbox = new VBox();
        Label label = new Label("Loan Information");
        vbox.getChildren().add(label);
        // TODO: Add more loan-specific UI components here
        return vbox;
    }
}
