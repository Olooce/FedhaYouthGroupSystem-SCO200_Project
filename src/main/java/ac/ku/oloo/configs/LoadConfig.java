package ac.ku.oloo.configs;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.configs)
 * Created by: oloo
 * On: 19/09/2024. 12:07
 * Description:This class loads the configuration from the config.xml file and provides access to
 * the configurations via getter methods.
 **/

public class LoadConfig {

    private double businessLoanInterestRate;
    private double personalLoanInterestRate;
    private double emergencyLoanInterestRate;
    private double fixedDepositInterestRate;
    private double registrationFee;
    private double minimumMonthlyContribution;
    private int eligibleLoanAfterMonths;
    private int dividendsPercentage;
    private int officeExpensesPercentage;
    private int noticePeriodMonths;

    public LoadConfig() {
        loadConfigurations();
    }

    private void loadConfigurations() {
        try {
            File configFile = new File("config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(configFile);
            doc.getDocumentElement().normalize();

            // Load loan types interest rates
            NodeList loanTypes = doc.getElementsByTagName("loanType");
            for (int i = 0; i < loanTypes.getLength(); i++) {
                Element loanTypeElement = (Element) loanTypes.item(i);
                String loanName = loanTypeElement.getElementsByTagName("name").item(0).getTextContent();
                double interestRate = Double.parseDouble(loanTypeElement.getElementsByTagName("interestRate").item(0).getTextContent());

                switch (loanName) {
                    case "Business Loan":
                        this.businessLoanInterestRate = interestRate;
                        break;
                    case "Personal Loan":
                        this.personalLoanInterestRate = interestRate;
                        break;
                    case "Emergency Loan":
                        this.emergencyLoanInterestRate = interestRate;
                        break;
                }
            }

            // Load fixed deposit interest rate
            this.fixedDepositInterestRate = Double.parseDouble(doc.getElementsByTagName("interestRate").item(loanTypes.getLength()).getTextContent());

            // Load membership configurations
            Element membershipElement = (Element) doc.getElementsByTagName("membership").item(0);
            this.registrationFee = Double.parseDouble(membershipElement.getElementsByTagName("registrationFee").item(0).getTextContent());
            this.minimumMonthlyContribution = Double.parseDouble(membershipElement.getElementsByTagName("minimumMonthlyContribution").item(0).getTextContent());
            this.eligibleLoanAfterMonths = Integer.parseInt(membershipElement.getElementsByTagName("eligibleLoanAfterMonths").item(0).getTextContent());

            // Load financial configurations
            Element financialsElement = (Element) doc.getElementsByTagName("financials").item(0);
            this.dividendsPercentage = Integer.parseInt(financialsElement.getElementsByTagName("percentageOfRevenue").item(0).getTextContent());
            this.officeExpensesPercentage = Integer.parseInt(financialsElement.getElementsByTagName("percentageOfRevenue").item(1).getTextContent());

            // Load exit policy configuration
            Element exitPolicyElement = (Element) doc.getElementsByTagName("exitPolicy").item(0);
            this.noticePeriodMonths = Integer.parseInt(exitPolicyElement.getElementsByTagName("noticePeriodMonths").item(0).getTextContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getter methods to access the loaded configurations

    public double getBusinessLoanInterestRate() {
        return businessLoanInterestRate;
    }

    public double getPersonalLoanInterestRate() {
        return personalLoanInterestRate;
    }

    public double getEmergencyLoanInterestRate() {
        return emergencyLoanInterestRate;
    }

    public double getFixedDepositInterestRate() {
        return fixedDepositInterestRate;
    }

    public double getRegistrationFee() {
        return registrationFee;
    }

    public double getMinimumMonthlyContribution() {
        return minimumMonthlyContribution;
    }

    public int getEligibleLoanAfterMonths() {
        return eligibleLoanAfterMonths;
    }

    public int getDividendsPercentage() {
        return dividendsPercentage;
    }

    public int getOfficeExpensesPercentage() {
        return officeExpensesPercentage;
    }

    public int getNoticePeriodMonths() {
        return noticePeriodMonths;
    }
}
