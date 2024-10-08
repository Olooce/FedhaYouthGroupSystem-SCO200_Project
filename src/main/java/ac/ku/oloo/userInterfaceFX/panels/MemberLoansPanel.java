package ac.ku.oloo.userInterfaceFX.panels;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 27/09/2024. 09:59
 * Description:
 **/

public class MemberLoansPanel {

    public VBox createLoanPanel() {
        VBox loanPanel = new VBox(10);
        loanPanel.setPadding(new Insets(20));

        loanPanel.getChildren().addAll(
                new Label("Loan 1: ..."),
                new Label("Loan 2: ..."),
                new Label("Loan 3: ...")
        );

        return loanPanel;
    }
}

