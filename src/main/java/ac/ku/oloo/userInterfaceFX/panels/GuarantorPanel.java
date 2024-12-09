package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Guarantor;
import ac.ku.oloo.services.LoanService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterfaceFX.panels)
 * Created by: oloo
 * On: 09/12/2024. 21:57
 * Description:
 **/
public class GuarantorPanel {
    private Pagination pagination;
    private ComboBox<Integer> entriesPerPageComboBox;
    private int entriesPerPage = 10;
    private final ObservableList<Guarantor> observableGuarantors = FXCollections.observableArrayList();

    public ScrollPane createGuarantorPanel() {
        VBox vbox = new VBox(10); // Add spacing between nodes
        // ComboBox for selecting entries per page
        entriesPerPageComboBox = new ComboBox<>();
        entriesPerPageComboBox.getItems().addAll(5, 10, 20, 50);
        entriesPerPageComboBox.setValue(entriesPerPage); // Default selection
        entriesPerPageComboBox.setOnAction(e -> {
            entriesPerPage = entriesPerPageComboBox.getValue();
            refreshGuarantorsTable(); // Refresh the table when changing entries per page
        });

        // Pagination control
        pagination = new Pagination();
        pagination.setPageFactory(this::createPage);

        // Layout for pagination and entries per page selector
        VBox controlBox = new VBox(10, new Label("Entries Per Page:"), entriesPerPageComboBox, pagination);
        vbox.getChildren().add(controlBox);

        refreshGuarantorsTable(); // Initial load of guarantors

        // Wrap the VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");

        return scrollPane; // Return the ScrollPane
    }

    private Node createPage(int pageIndex) {
        // Calculate the data for this page
        int fromIndex = pageIndex * entriesPerPage;
        int toIndex = Math.min(fromIndex + entriesPerPage, observableGuarantors.size());

        // Create a new TableView for the current page
        TableView<Guarantor> pageTable = new TableView<>();
        pageTable.setItems(FXCollections.observableArrayList(observableGuarantors.subList(fromIndex, toIndex)));

        // Define columns
        TableColumn<Guarantor, String> idColumn = new TableColumn<>("Guarantor ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("guarantorId"));

        TableColumn<Guarantor, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Guarantor, String> loanIdColumn = new TableColumn<>("Loan ID");
        loanIdColumn.setCellValueFactory(new PropertyValueFactory<>("loanId"));

        TableColumn<Guarantor, Double> guaranteeAmountColumn = new TableColumn<>("Guarantee Amount");
        guaranteeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("guaranteeAmount"));

        // Add columns to the table
        pageTable.getColumns().addAll(idColumn, nameColumn, loanIdColumn, guaranteeAmountColumn);

        return pageTable; // Return the TableView for this page
    }

    private void refreshGuarantorsTable() {
        observableGuarantors.clear(); // Clear existing items
        List<Guarantor> guarantors = LoanService.getGuarantors(1, Integer.MAX_VALUE);
        observableGuarantors.addAll(guarantors); // Add fetched guarantors to the observable list
        pagination.setPageCount((int) Math.ceil((double) observableGuarantors.size() / entriesPerPage)); // Update pagination
    }
}


