package ac.ku.oloo.userinterfaceSwing.panels;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing.panels)
 * Created by: oloo
 * On: 08/10/2024. 12:39
 * Description:
 **/
import ac.ku.oloo.models.Member;
import ac.ku.oloo.services.MemberService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.userinterfaceSwing.panels)
 * Created by: oloo
 * On: 26/09/2024. 13:18
 * Description: Panel for managing member information.
 **/

public class MembersPanel extends JPanel {

    private final MemberService memberService;
    private DefaultTableModel tableModel;
    private JComboBox<Integer> entriesPerPageComboBox;
    private int currentPage = 0;
    private int entriesPerPage = 10;
    private int totalMembers = 0;

    public MembersPanel() {
        this.memberService = new MemberService();
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Member", createAddMemberContent());
        tabbedPane.addTab("View Members", createViewMembersContent());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createAddMemberContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Add New Member");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Personal Information section
        JPanel personalInfoPanel = new JPanel();
        personalInfoPanel.setBorder(new TitledBorder("Personal Information"));
        personalInfoPanel.setLayout(new GridLayout(5, 2, 10, 10));

        String[] honorifics = {"Mr.", "Mrs.", "Ms.", "Dr.", "Prof."};
        JComboBox<String> honorificComboBox = new JComboBox<>(honorifics);
        personalInfoPanel.add(new JLabel("Honorific:"));
        personalInfoPanel.add(honorificComboBox);

        JTextField surnameField = new JTextField();
        personalInfoPanel.add(new JLabel("Surname:"));
        personalInfoPanel.add(surnameField);

        JTextField firstNameField = new JTextField();
        personalInfoPanel.add(new JLabel("First Name:"));
        personalInfoPanel.add(firstNameField);

        JTextField otherNameField = new JTextField();
        personalInfoPanel.add(new JLabel("Other Name:"));
        personalInfoPanel.add(otherNameField);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateOfBirthSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateOfBirthSpinner, "yyyy-MM-dd");
        dateOfBirthSpinner.setEditor(dateEditor);

        panel.add(personalInfoPanel, BorderLayout.CENTER);

        // Contact Information section
        JPanel contactInfoPanel = new JPanel();
        contactInfoPanel.setBorder(new TitledBorder("Contact Information"));
        contactInfoPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JTextField emailField = new JTextField();
        contactInfoPanel.add(new JLabel("Email:"));
        contactInfoPanel.add(emailField);

        JTextField phoneNumberField = new JTextField();
        contactInfoPanel.add(new JLabel("Phone Number:"));
        contactInfoPanel.add(phoneNumberField);

        JTextArea addressField = new JTextArea();
        addressField.setRows(3);
        contactInfoPanel.add(new JLabel("Address:"));
        contactInfoPanel.add(new JScrollPane(addressField));

        panel.add(contactInfoPanel, BorderLayout.SOUTH);

        // Status
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"ACTIVE", "INACTIVE"});
        panel.add(statusComboBox, BorderLayout.SOUTH);

        // Add Member Button
        JButton addButton = new JButton("Add Member");
        addButton.addActionListener(e -> {
            try {
                Member member = new Member();
                member.setHonorific((String) honorificComboBox.getSelectedItem());
                member.setSurname(surnameField.getText());
                member.setFirstName(firstNameField.getText());
                member.setOtherName(otherNameField.getText());

                // Convert spinner date to SQL date
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = (java.util.Date) dateOfBirthSpinner.getValue();
                member.setDateOfBirth(new Date(date.getTime()));

                member.setEmail(emailField.getText());
                member.setPhoneNumber(phoneNumberField.getText());
                member.setAddress(addressField.getText());
                member.setStatus((String) statusComboBox.getSelectedItem());

                memberService.createMember(member);

                JOptionPane.showMessageDialog(panel, "Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearAddMemberFields(honorificComboBox, surnameField, firstNameField, otherNameField,
                        dateOfBirthSpinner, emailField, phoneNumberField, addressField, statusComboBox);

                refreshMemberTable();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "An error occurred while adding the member.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }

    private void clearAddMemberFields(JComboBox<String> honorificComboBox, JTextField surnameField, JTextField firstNameField,
                                      JTextField otherNameField, JSpinner dateOfBirthSpinner, JTextField emailField,
                                      JTextField phoneNumberField, JTextArea addressField, JComboBox<String> statusComboBox) {
        honorificComboBox.setSelectedIndex(-1);
        surnameField.setText("");
        firstNameField.setText("");
        otherNameField.setText("");
        dateOfBirthSpinner.setValue(null);
        emailField.setText("");
        phoneNumberField.setText("");
        addressField.setText("");
        statusComboBox.setSelectedIndex(-1);
    }

    private JPanel createViewMembersContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Table for displaying existing members
        String[] columnNames = {"Member ID", "Name"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable memberTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(memberTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Control panel for pagination and entries per page
        JPanel controlPanel = new JPanel();
        entriesPerPageComboBox = new JComboBox<>(new Integer[]{5, 10, 20, 50});
        entriesPerPageComboBox.setSelectedItem(entriesPerPage);
        entriesPerPageComboBox.addActionListener(e -> {
            entriesPerPage = (Integer) entriesPerPageComboBox.getSelectedItem();
            refreshMemberTable();
        });
        controlPanel.add(new JLabel("Entries per page:"));
        controlPanel.add(entriesPerPageComboBox);

        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                refreshMemberTable();
            }
        });
        controlPanel.add(previousButton);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if (currentPage < (totalMembers / entriesPerPage)) {
                currentPage++;
                refreshMemberTable();
            }
        });
        controlPanel.add(nextButton);

        panel.add(controlPanel, BorderLayout.SOUTH);
        refreshMemberTable(); // Initial load of members
        return panel;
    }

    private void refreshMemberTable() {
        tableModel.setRowCount(0); // Clear existing rows
        try {
            List<Member> members = memberService.getMembers(currentPage + 1, entriesPerPage); // Fetch members for the current page
            totalMembers = memberService.getTotalMembers(); // Assume this method exists in MemberService
            for (Member member : members) {
                tableModel.addRow(new Object[]{member.getMemberId(), member.getFirstName() + " " + member.getSurname()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
