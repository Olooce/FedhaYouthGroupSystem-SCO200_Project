package ac.ku.oloo.userInterface.panels;

import ac.ku.oloo.services.MemberService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import ac.ku.oloo.models.Member;

import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userInterface)
 * Created by: oloo
 * On: 26/09/2024. 13:18
 * Description:
 **/

public class MemberPanel {

    private MemberService memberService;

    public MemberPanel() {
        this.memberService = new MemberService();
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

    private VBox createAddMemberContent() {
        VBox vbox = new VBox();

        // UI components for adding a member
        TextField honorificField = new TextField();
        honorificField.setPromptText("Honorific");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Surname");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField otherNameField = new TextField();
        otherNameField.setPromptText("Other Name");

        DatePicker dateOfBirthPicker = new DatePicker();
        dateOfBirthPicker.setPromptText("Date of Birth");

        TextField idNumberField = new TextField();
        idNumberField.setPromptText("ID Number");

        TextField idTypeField = new TextField();
        idTypeField.setPromptText("ID Type");

        TextField taxIdField = new TextField();
        taxIdField.setPromptText("Tax ID");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number");

        TextArea addressField = new TextArea();
        addressField.setPromptText("Address");

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("ACTIVE", "INACTIVE");
        statusComboBox.setPromptText("Status");

        Button addButton = new Button("Add Member");
        addButton.setOnAction(e -> {
            try {
                Member member = new Member();
                member.setHonorific(honorificField.getText());
                member.setSurname(surnameField.getText());
                member.setFirstName(firstNameField.getText());
                member.setOtherName(otherNameField.getText());
                member.setDateOfBirth(java.sql.Date.valueOf(dateOfBirthPicker.getValue()));
                member.setIdNumber(idNumberField.getText());
                member.setIdType(idTypeField.getText());
                member.setTaxId(taxIdField.getText());
                member.setEmail(emailField.getText());
                member.setPhoneNumber(phoneNumberField.getText());
                member.setAddress(addressField.getText());
                member.setStatus(statusComboBox.getValue());

                memberService.createMember(member);
                // Clear fields or show success message
                clearAddMemberFields(honorificField, surnameField, firstNameField, otherNameField,
                        dateOfBirthPicker, idNumberField, idTypeField, taxIdField, emailField,
                        phoneNumberField, addressField, statusComboBox);

            } catch (SQLException ex) {
                ex.printStackTrace(); // TODO: Handle the exception (Maybe show alert)
            }
        });

        vbox.getChildren().addAll(honorificField, surnameField, firstNameField, otherNameField,
                dateOfBirthPicker, idNumberField, idTypeField, taxIdField, emailField,
                phoneNumberField, addressField, statusComboBox, addButton);

        return vbox;
    }

    private void clearAddMemberFields(TextField honorificField, TextField surnameField, TextField firstNameField,
                                      TextField otherNameField, DatePicker dateOfBirthPicker, TextField idNumberField,
                                      TextField idTypeField, TextField taxIdField, TextField emailField,
                                      TextField phoneNumberField, TextArea addressField, ComboBox<String> statusComboBox) {
        honorificField.clear();
        surnameField.clear();
        firstNameField.clear();
        otherNameField.clear();
        dateOfBirthPicker.setValue(null);
        idNumberField.clear();
        idTypeField.clear();
        taxIdField.clear();
        emailField.clear();
        phoneNumberField.clear();
        addressField.clear();
        statusComboBox.setValue(null);
    }

    private VBox createViewMembersContent() {
        VBox vbox = new VBox();

        // TableView for displaying existing members
        TableView<Member> memberTable = new TableView<>();

        TableColumn<Member, Integer> idColumn = new TableColumn<>("Member ID");
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMemberId()).asObject());

        TableColumn<Member, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFirstName() + " " + cellData.getValue().getSurname()));

        memberTable.getColumns().addAll(idColumn, nameColumn);

        // Fetch members from the database and add to the table
        try {
            List<Member> members = memberService.getMembers(1, 10);
            memberTable.getItems().addAll(members);
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: Handle the exception
        }

        vbox.getChildren().add(memberTable);
        return vbox;
    }
}
