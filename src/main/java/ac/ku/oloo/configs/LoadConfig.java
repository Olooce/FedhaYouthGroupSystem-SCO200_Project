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
 * On: 19/09/2024.
 * Description: Loads configurations and makes them available to the system via getter methods.
 **/

public class LoadConfig {

    private static double businessLoanInterestRate;
    private static double personalLoanInterestRate;
    private static double emergencyLoanInterestRate;
    private static double fixedDepositInterestRate;
    private static double registrationFee;
    private static double minimumMonthlyContribution;
    private static int eligibleLoanAfterMonths;
    private static int dividendsPercentage;
    private static int officeExpensesPercentage;
    private static int noticePeriodMonths;

    static {
        loadConfigurations();
    }

    private static void loadConfigurations() {
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
                        businessLoanInterestRate = interestRate;
                        break;
                    case "Personal Loan":
                        personalLoanInterestRate = interestRate;
                        break;
                    case "Emergency Loan":
                        emergencyLoanInterestRate = interestRate;
                        break;
                }
            }

            // Load fixed deposit interest rate
            fixedDepositInterestRate = Double.parseDouble(doc.getElementsByTagName("interestRate").item(loanTypes.getLength()).getTextContent());

            // Load membership configurations
            Element membershipElement = (Element) doc.getElementsByTagName("membership").item(0);
            registrationFee = Double.parseDouble(membershipElement.getElementsByTagName("registrationFee").item(0).getTextContent());
            minimumMonthlyContribution = Double.parseDouble(membershipElement.getElementsByTagName("minimumMonthlyContribution").item(0).getTextContent());
            eligibleLoanAfterMonths = Integer.parseInt(membershipElement.getElementsByTagName("eligibleLoanAfterMonths").item(0).getTextContent());

            // Load financial configurations
            Element financialsElement = (Element) doc.getElementsByTagName("financials").item(0);
            dividendsPercentage = Integer.parseInt(financialsElement.getElementsByTagName("percentageOfRevenue").item(0).getTextContent());
            officeExpensesPercentage = Integer.parseInt(financialsElement.getElementsByTagName("percentageOfRevenue").item(1).getTextContent());

            // Load exit policy configuration
            Element exitPolicyElement = (Element) doc.getElementsByTagName("exitPolicy").item(0);
            noticePeriodMonths = Integer.parseInt(exitPolicyElement.getElementsByTagName("noticePeriodMonths").item(0).getTextContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getter methods to access the loaded configurations
    public static double getBusinessLoanInterestRate() {
        return businessLoanInterestRate;
    }

    public static double getPersonalLoanInterestRate() {
        return personalLoanInterestRate;
    }

    public static double getEmergencyLoanInterestRate() {
        return emergencyLoanInterestRate;
    }

    public static double getFixedDepositInterestRate() {
        return fixedDepositInterestRate;
    }

    public static double getRegistrationFee() {
        return registrationFee;
    }

    public static double getMinimumMonthlyContribution() {
        return minimumMonthlyContribution;
    }

    public static int getEligibleLoanAfterMonths() {
        return eligibleLoanAfterMonths;
    }

    public static int getDividendsPercentage() {
        return dividendsPercentage;
    }

    public static int getOfficeExpensesPercentage() {
        return officeExpensesPercentage;
    }

    public static int getNoticePeriodMonths() {
        return noticePeriodMonths;
    }
}
