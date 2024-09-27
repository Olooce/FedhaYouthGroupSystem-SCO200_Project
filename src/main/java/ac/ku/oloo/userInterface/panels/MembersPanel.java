package ac.ku.oloo.userInterface.panels;

import ac.ku.oloo.services.MemberService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ac.ku.oloo.models.Member;

import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterface)
 * Created by: oloo
 * On: 26/09/2024. 13:18
 * Description: Panel for managing member information.
 **/

public class MembersPanel {

    private MemberService memberService;
    private TableView<Member> memberTable;
    private ObservableList<Member> observableMembers;
    private Pagination pagination;
    private ComboBox<Integer> entriesPerPageComboBox;
    private int currentPage = 0;
    private int entriesPerPage = 10;

    public MembersPanel() {
        this.memberService = new MemberService();
        this.observableMembers = FXCollections.observableArrayList(); // Initialize observable list
    }

    public TabPane createMemberPanel() {
        TabPane tabPane = new TabPane();

        // Tab for adding a member
        Tab addMemberTab = new Tab("Add Member");
        addMemberTab.setContent(createAddMemberContent());
        tabPane.getTabs().add(addMemberTab);

        // Tab for viewing existing members
        Tab viewMembersTab = new Tab("View Members");
        viewMembersTab.setContent(createViewMembersContent());
        tabPane.getTabs().add(viewMembersTab);

        return tabPane;
    }

    private ScrollPane createAddMemberContent() {
        VBox vbox = new VBox(15); // Increased spacing between elements
        vbox.setPadding(new Insets(20)); // Padding around the VBox

        // UI components for adding a member
        Label titleLabel = new Label("Add New Member");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Personal Information section
        TitledPane personalInfoPane = new TitledPane();
        personalInfoPane.setText("Personal Information");

        VBox personalInfoBox = new VBox(10);
        ComboBox<String> honorificComboBox = new ComboBox<>();
        honorificComboBox.getItems().addAll("Mr.", "Mrs.", "Ms.", "Dr.", "Prof.");
        honorificComboBox.setPromptText("Honorific");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Surname");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField otherNameField = new TextField();
        otherNameField.setPromptText("Other Name");

        DatePicker dateOfBirthPicker = new DatePicker();
        dateOfBirthPicker.setPromptText("Date of Birth");

        personalInfoBox.getChildren().addAll(honorificComboBox, surnameField, firstNameField, otherNameField, dateOfBirthPicker);
        personalInfoPane.setContent(personalInfoBox);

        // Contact Information section
        TitledPane contactInfoPane = new TitledPane();
        contactInfoPane.setText("Contact Information");

        VBox contactInfoBox = new VBox(10);
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");

        TextArea addressField = new TextArea();
        addressField.setPromptText("Address");
        addressField.setPrefRowCount(3); // Reduce the height of the text area

        contactInfoBox.getChildren().addAll(emailField, phoneNumberField, addressField);
        contactInfoPane.setContent(contactInfoBox);

        // Identification Information section
        TitledPane idInfoPane = new TitledPane();
        idInfoPane.setText("Identification");

        VBox idInfoBox = new VBox(10);
        ComboBox<String> idTypeComboBox = new ComboBox<>();
        idTypeComboBox.getItems().addAll("National ID", "Passport", "Driver's License", "Student ID");
        idTypeComboBox.setPromptText("ID Type");

        TextField idNumberField = new TextField();
        idNumberField.setPromptText("ID Number");

        TextField taxIdField = new TextField();
        taxIdField.setPromptText("Tax ID");

        idInfoBox.getChildren().addAll(idTypeComboBox, idNumberField, taxIdField);
        idInfoPane.setContent(idInfoBox);

        // Status
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("ACTIVE", "INACTIVE");
        statusComboBox.setPromptText("Status");

        // Add Member Button
        Button addButton = new Button("Add Member");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        addButton.setOnAction(e -> {
            try {
                Member member = new Member();
                member.setHonorific(honorificComboBox.getValue());
                member.setSurname(surnameField.getText());
                member.setFirstName(firstNameField.getText());
                member.setOtherName(otherNameField.getText());
                member.setDateOfBirth(java.sql.Date.valueOf(dateOfBirthPicker.getValue()));
                member.setIdNumber(idNumberField.getText());
                member.setIdType(idTypeComboBox.getValue());
                member.setTaxId(taxIdField.getText());
                member.setEmail(emailField.getText());
                member.setPhoneNumber(phoneNumberField.getText());
                member.setAddress(addressField.getText());
                member.setStatus(statusComboBox.getValue());

                memberService.createMember(member);

                // Show success alert
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Member added successfully!");
                successAlert.showAndWait();

                // Clear fields after adding
                clearAddMemberFields(honorificComboBox, surnameField, firstNameField, otherNameField,
                        dateOfBirthPicker, idNumberField, idTypeComboBox, taxIdField, emailField,
                        phoneNumberField, addressField, statusComboBox);

                // Refresh the view members tab
                refreshMemberTable();

            } catch (SQLException ex) {
                ex.printStackTrace();
                // Show error alert
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Database Error");
                errorAlert.setContentText("An error occurred while adding the member. Please try again.");
                errorAlert.showAndWait();
            }
        });

        // Add components to the main VBox
        vbox.getChildren().addAll(titleLabel, personalInfoPane, contactInfoPane, idInfoPane, statusComboBox, addButton);

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");

        return scrollPane;
    }


    private void clearAddMemberFields(ComboBox<String> honorificCombobox, TextField surnameField, TextField firstNameField,
                                      TextField otherNameField, DatePicker dateOfBirthPicker, TextField idNumberField,
                                      ComboBox<String> idTypeCombobox, TextField taxIdField, TextField emailField,
                                      TextField phoneNumberField, TextArea addressField, ComboBox<String> statusComboBox) {
        honorificCombobox.setValue(null);
        surnameField.clear();
        firstNameField.clear();
        otherNameField.clear();
        dateOfBirthPicker.setValue(null);
        idNumberField.clear();
        idTypeCombobox.setValue(null);
        taxIdField.clear();
        emailField.clear();
        phoneNumberField.clear();
        addressField.clear();
        statusComboBox.setValue(null);
    }

    private ScrollPane createViewMembersContent() {
        VBox vbox = new VBox();

        // TableView for displaying existing members
        memberTable = new TableView<>();
        memberTable.setItems(observableMembers); // Bind observable list to the table

        TableColumn<Member, Integer> idColumn = new TableColumn<>("Member ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMemberId()).asObject());

        TableColumn<Member, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFirstName() + " " + cellData.getValue().getSurname()));

        memberTable.getColumns().addAll(idColumn, nameColumn);

        // Pagination for viewing members
        pagination = new Pagination();
        pagination.setPageFactory(this::createPage);

        // ComboBox for selecting entries per page
        entriesPerPageComboBox = new ComboBox<>();
        entriesPerPageComboBox.getItems().addAll(5, 10, 20, 50);
        entriesPerPageComboBox.setValue(entriesPerPage);
        entriesPerPageComboBox.setOnAction(e -> {
            entriesPerPage = entriesPerPageComboBox.getValue();
            refreshMemberTable(); // Refresh when changing entries per page
        });

        // Layout for pagination and entries per page
        VBox controlBox = new VBox(10, entriesPerPageComboBox, pagination);
        vbox.getChildren().addAll(controlBox, memberTable);

        refreshMemberTable(); // Initial load of members

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
        int toIndex = Math.min(fromIndex + entriesPerPage, observableMembers.size());

        // Create a new TableView for the current page
        TableView<Member> pageTable = new TableView<>();
        pageTable.setItems(FXCollections.observableArrayList(observableMembers.subList(fromIndex, toIndex)));

        TableColumn<Member, Integer> idColumn = new TableColumn<>("Member ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMemberId()).asObject());

        TableColumn<Member, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFirstName() + " " + cellData.getValue().getSurname()));

        pageTable.getColumns().addAll(idColumn, nameColumn);
        return pageTable;
    }

    private void refreshMemberTable() {
        observableMembers.clear(); // Clear existing items
        try {
            List<Member> members = memberService.getMembers(1, Integer.MAX_VALUE); // Fetch all members
            observableMembers.addAll(members); // Add fetched members to the observable list
            pagination.setPageCount((int) Math.ceil((double) observableMembers.size() / entriesPerPage)); // Update pagination
        } catch (SQLException e) {
            e.printStackTrace(); //TODO:  Handle exception
        }
    }
}
