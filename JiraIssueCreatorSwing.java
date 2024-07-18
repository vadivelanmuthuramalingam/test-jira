package com.solace.junit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JiraIssueCreatorSwing extends JFrame implements ActionListener, ItemListener {
    // Swing components
    private JTextField tfSummary, tfProjectKey, tfIssueType;
    private JTextField tfEnvironment, tfClientName, tfApimClientId, tfProgramId, tfProgramName, tfRegion, tfCountryCode;
    private JTextField tfNotificationUrl, tfProcessorId, tfBankId;
    private JComboBox<String> cbClientType, cbNetworkType;
    private JCheckBox cbIdempotency, cbFailsafe, cbIdProvisioning;
    private JButton btnSubmit;

    public JiraIssueCreatorSwing() {
        // Frame setup
        setTitle("JIRA Issue Creator");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout for 2 columns

        // Initialize components
        tfSummary = createTextField(20);
        tfProjectKey = createTextField(20);
        tfIssueType = createTextField(20);

        tfEnvironment = createTextField(20);
        tfClientName = createTextField(20);
        tfApimClientId = createTextField(20);
        tfProgramId = createTextField(20);
        tfProgramName = createTextField(20);
        tfRegion = createTextField(20);
        tfCountryCode = createTextField(20);

        cbClientType = new JComboBox<>(new String[]{"Normal", "PI"});
        cbClientType.setPreferredSize(new Dimension(150, cbClientType.getPreferredSize().height));
        cbClientType.addItemListener(this);
        tfNotificationUrl = createTextField(20);
        tfNotificationUrl.setVisible(false);

        cbNetworkType = new JComboBox<>(new String[]{"Normal", "VISA"});
        cbNetworkType.setPreferredSize(new Dimension(150, cbNetworkType.getPreferredSize().height));
        cbNetworkType.addItemListener(this);
        tfProcessorId = createTextField(20);
        tfProcessorId.setVisible(false);
        tfBankId = createTextField(20);
        tfBankId.setVisible(false);

        cbIdempotency = new JCheckBox();
        cbFailsafe = new JCheckBox();
        cbIdProvisioning = new JCheckBox();

        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(this);

        // Add components to the frame
        add(createLabelAndFieldPanel("Summary:", tfSummary));
        add(createLabelAndFieldPanel("Project Key:", tfProjectKey));
        add(createLabelAndFieldPanel("Issue Type:", tfIssueType));
        add(createLabelAndFieldPanel("Environment:", tfEnvironment));
        add(createLabelAndFieldPanel("Client Name:", tfClientName));
        add(createLabelAndFieldPanel("APIM Client ID:", tfApimClientId));
        add(createLabelAndFieldPanel("Program ID:", tfProgramId));
        add(createLabelAndFieldPanel("Program Name:", tfProgramName));
        add(createLabelAndFieldPanel("Region:", tfRegion));
        add(createLabelAndFieldPanel("Country Code:", tfCountryCode));

        add(createLabelAndFieldPanel("Client Type:", cbClientType));
        add(createLabelAndFieldPanel("Notification URL:", tfNotificationUrl));

        add(createLabelAndFieldPanel("Network Type:", cbNetworkType));
        add(createLabelAndFieldPanel("Processor ID:", tfProcessorId));
        add(createLabelAndFieldPanel("Bank ID:", tfBankId));

        add(createLabelAndFieldPanel("Idempotency Feature (Y/N):", cbIdempotency));
        add(createLabelAndFieldPanel("Failsafe Feature (Y/N):", cbFailsafe));
        add(createLabelAndFieldPanel("ID Provisioning Automation (Y/N):", cbIdProvisioning));
        add(createLabelAndFieldPanel("", btnSubmit));
        
        setVisible(true);
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setVisible(true); // Initially hide all text fields
        return textField;
    }

    private JPanel createLabelAndFieldPanel(String labelText, Component fieldComponent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(fieldComponent);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            String summary = tfSummary.getText();
            String projectKey = tfProjectKey.getText();
            String issueType = tfIssueType.getText();
            String environment = tfEnvironment.getText();
            String clientName = tfClientName.getText();
            String apimClientId = tfApimClientId.getText();
            String programId = tfProgramId.getText();
            String programName = tfProgramName.getText();
            String region = tfRegion.getText();
            String countryCode = tfCountryCode.getText();
            String clientType = (String) cbClientType.getSelectedItem();
            String notificationUrl = tfNotificationUrl.getText();
            String networkType = (String) cbNetworkType.getSelectedItem();
            String processorId = tfProcessorId.getText();
            String bankId = tfBankId.getText();
            String idempotency = cbIdempotency.isSelected() ? "Y" : "N";
            String failsafe = cbFailsafe.isSelected() ? "Y" : "N";
            String idProvisioning = cbIdProvisioning.isSelected() ? "Y" : "N";

            // Construct description with GPT-3.5 generated content
            String description = generateDescription(environment, clientName, apimClientId, programId,
                    programName, region, countryCode, clientType, notificationUrl,
                    networkType, processorId, bankId, idempotency, failsafe, idProvisioning);

            // Submit JIRA issue (actual implementation based on your JIRA instance)
            try {
                createJiraIssue(summary, description, projectKey, issueType);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to create JIRA issue: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cbClientType) {
            // Show/hide Notification URL based on Client Type selection
            String selectedClientType = (String) cbClientType.getSelectedItem();
            tfNotificationUrl.setVisible(selectedClientType.equals("PI"));
        } else if (e.getSource() == cbNetworkType) {
            // Show/hide Processor ID and Bank ID based on Network Type selection
            String selectedNetworkType = (String) cbNetworkType.getSelectedItem();
            tfProcessorId.setVisible(selectedNetworkType.equals("VISA"));
            tfBankId.setVisible(selectedNetworkType.equals("VISA"));
        }
        // Repaint the frame to reflect changes
        revalidate();
        repaint();
    }

    private String generateDescription(String environment, String clientName, String apimClientId,
                                       String programId, String programName, String region, String countryCode,
                                       String clientType, String notificationUrl, String networkType,
                                       String processorId, String bankId, String idempotency,
                                       String failsafe, String idProvisioning) {
        // Use GPT-3.5 or other AI capabilities here to generate detailed description
        // For simplicity, a basic HTML table is constructed
        return "<html><body><table border='1'>"
                + "<tr><td>Environment</td><td>" + environment + "</td></tr>"
                + "<tr><td>Client Name</td><td>" + clientName + "</td></tr>"
                + "<tr><td>APIM Client ID</td><td>" + apimClientId + "</td></tr>"
                + "<tr><td>Program ID</td><td>" + programId + "</td></tr>"
                + "<tr><td>Program Name</td><td>" + programName + "</td></tr>"
                + "<tr><td>Region</td><td>" + region + "</td></tr>"
                + "<tr><td>Country Code</td><td>" + countryCode + "</td></tr>"
                + "<tr><td>Client Type</td><td>" + clientType + "</td></tr>"
                + "<tr><td>Notification URL</td><td>" + notificationUrl + "</td></tr>"
                + "<tr><td>Network Type</td><td>" + networkType + "</td></tr>"
                + "<tr><td>Processor ID</td><td>" + processorId + "</td></tr>"
                + "<tr><td>Bank ID</td><td>" + bankId + "</td></tr>"
                + "<tr><td>Idempotency Feature</td><td>" + idempotency + "</td></tr>"
                + "<tr><td>Failsafe Feature</td><td>" + failsafe + "</td></tr>"
                + "<tr><td>ID Provisioning Automation</td><td>" + idProvisioning + "</td></tr>"
                + "</table></body></html>";
    }

    private void createJiraIssue(String summary, String description, String projectKey, String issueType) throws IOException {
        // Replace with actual code to create JIRA issue via REST API
        // Example code to demonstrate JIRA API integration (replace with your own implementation)
        String jiraUrl = "https://your-jira-instance.atlassian.net/rest/api/2/issue";
        String username = "your-email@example.com";
        String apiToken = "your-api-token";

        String payload = "{"
                + "\"fields\": {"
                + "\"project\": {"
                + "\"key\": \"" + projectKey + "\""
                + "},"
                + "\"summary\": \"" + summary + "\","
                + "\"description\": \"" + description + "\","
                + "\"issuetype\": {"
                + "\"name\": \"" + issueType + "\""
                + "}"
                + "}"
                + "}";

        URL url = new URL(jiraUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        String auth = username + ":" + apiToken;
        String encodedAuth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());
        conn.setRequestProperty("Authorization", "Basic " + encodedAuth);

        // Send request
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            JOptionPane.showMessageDialog(this, "JIRA issue created successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create JIRA issue. HTTP error code: " + responseCode,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        conn.disconnect();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JiraIssueCreatorSwing::new);
    }
}
