package ac.ku.oloo.userInterfaceFX.panels;

import ac.ku.oloo.models.Guarantor;
import ac.ku.oloo.models.Member;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
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
    private int currentPage = 0;
    private int entriesPerPage = 10;

    public ScrollPane createGuarantorPanel() {
        VBox vbox = new VBox();
        // ComboBox for selecting entries per page
        entriesPerPageComboBox = new ComboBox<>();
        entriesPerPageComboBox.getItems().addAll(5, 10, 20, 50);
        entriesPerPageComboBox.setValue(entriesPerPage);
        entriesPerPageComboBox.setOnAction(e -> {
            entriesPerPage = entriesPerPageComboBox.getValue();
            refreshGuarantorsTable(); // Refresh when changing entries per page
        });

        // Pagination control
        pagination = new Pagination();
        pagination.setPageFactory(this::createPage);

        // Layout for pagination and entries per page
        VBox controlBox = new VBox(10, entriesPerPageComboBox, pagination);
        vbox.getChildren().add(controlBox);

        refreshGuarantorsTable(); // Initial load of members

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
        int toIndex = Math.min(fromIndex + entriesPerPage, observablGuarantors.size());

        // Create a new TableView for the current page
        TableView<Guarantor> pageTable = new TableView<>();
        pageTable.setItems(FXCollections.observableArrayList(observableGurantors.subList(fromIndex, toIndex)));

        // Add all columns to the pageTable
        pageTable.getColumns().addAll(

        );

        return pageTable; // Return the TableView for this page
    }


    private void refreshGuarantorsTable() {
        observableGuarantors.clear(); // Clear existing items
        try {
            List<Guarantor> gurantors = LoanService.getGurantors(1, Integer.MAX_VALUE); // Fetch all members
            observableGuarantors.addAll(quarantors); // Add fetched members to the observable list
            pagination.setPageCount((int) Math.ceil((double) observableGuarantors.size() / entriesPerPage)); // Update pagination
        } catch (SQLException e) {
            e.printStackTrace(); //TODO:  Handle exception
        }
    }
}
