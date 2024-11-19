package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Deposit;
import ac.ku.oloo.services.DepositService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX)
 * Created by: oloo
 * On: 26/09/2024. 13:20
 * Description:
 **/

public class DepositPanel {

    private final DepositService depositService;

    public DepositPanel() throws SQLException {
        this.depositService = new DepositService(); // Initialize service
    }

    public VBox createDepositPanel() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        Label label = new Label("Deposits");
        TableView<Deposit> table = new TableView<>();
        Pagination pagination = new Pagination();

        // Set up columns
        TableColumn<Deposit, Long> idColumn = new TableColumn<>("Deposit ID");
        idColumn.setCellValueFactory(data -> data.getValue().depositIdProperty().asObject());

        TableColumn<Deposit, Long> memberIdColumn = new TableColumn<>("Member ID");
        memberIdColumn.setCellValueFactory(data -> data.getValue().memberIdProperty().asObject());

        TableColumn<Deposit, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty().asObject());

        TableColumn<Deposit, String> dateColumn = new TableColumn<>("Date Created");
        dateColumn.setCellValueFactory(data -> data.getValue().dateCreatedProperty());

        table.getColumns().addAll(idColumn, memberIdColumn, amountColumn, dateColumn);

        // Configure table and pagination
        ObservableList<Deposit> allDeposits = getDepositsFromService();
        int rowsPerPage = 10;

        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * rowsPerPage;
            int toIndex = Math.min(fromIndex + rowsPerPage, allDeposits.size());
            table.setItems(FXCollections.observableArrayList(allDeposits.subList(fromIndex, toIndex)));
            return new VBox(table);
        });

        vbox.getChildren().addAll(label, table, pagination);
        return vbox;
    }

    private ObservableList<Deposit> getDepositsFromService() {
        ObservableList<Deposit> deposits = FXCollections.observableArrayList();
        try {
            deposits.addAll(depositService.getAllDeposits());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deposits;
    }
}
