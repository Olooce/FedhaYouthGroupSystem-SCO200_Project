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
    private static double shortLoanInterestRate;
    private static double normalLoanInterestRate;
    private static double developmentLoanInterestRate;
    private static double emergencyLoanMultiplier;
    private static double shortLoanMultiplier;
    private static double normalLoanMultiplier;
    private static double developmentLoanMultiplier;

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

            // Load loan types interest rates and multipliers
            NodeList loanTypes = doc.getElementsByTagName("LOAN_TYPE");
            for (int i = 0; i < loanTypes.getLength(); i++) {
                Element loanTypeElement = (Element) loanTypes.item(i);
                String loanName = loanTypeElement.getElementsByTagName("NAME").item(0).getTextContent();
                double interestRate = Double.parseDouble(loanTypeElement.getElementsByTagName("INTEREST_RATE").item(0).getTextContent());
                double multiplier = Double.parseDouble(loanTypeElement.getElementsByTagName("MULTIPLIER").item(0).getTextContent());

                switch (loanName) {
                    case "Business Loan":
                        businessLoanInterestRate = interestRate;
                        break;
                    case "Personal Loan":
                        personalLoanInterestRate = interestRate;
                        break;
                    case "Emergency Loan":
                        emergencyLoanInterestRate = interestRate;
                        emergencyLoanMultiplier = multiplier;
                        break;
                    case "Short Loan":
                        shortLoanInterestRate = interestRate;
                        shortLoanMultiplier = multiplier;
                        break;
                    case "Normal Loan":
                        normalLoanInterestRate = interestRate;
                        normalLoanMultiplier = multiplier;
                        break;
                    case "Development Loan":
                        developmentLoanInterestRate = interestRate;
                        developmentLoanMultiplier = multiplier;
                        break;
                }
            }

            // Load fixed deposit interest rate
            fixedDepositInterestRate = Double.parseDouble(doc.getElementsByTagName("INTEREST_RATE").item(loanTypes.getLength()).getTextContent());

            // Load membership configurations
            Element membershipElement = (Element) doc.getElementsByTagName("MEMBERSHIP").item(0);
            registrationFee = Double.parseDouble(membershipElement.getElementsByTagName("REGISTRATION_FEE").item(0).getTextContent());
            minimumMonthlyContribution = Double.parseDouble(membershipElement.getElementsByTagName("MINIMUM_MONTHLY_CONTRIBUTION").item(0).getTextContent());
            eligibleLoanAfterMonths = Integer.parseInt(membershipElement.getElementsByTagName("ELIGIBLE_LOAN_AFTER_MONTHS").item(0).getTextContent());

            // Load financial configurations
            Element financialsElement = (Element) doc.getElementsByTagName("FINANCIALS").item(0);
            dividendsPercentage = Integer.parseInt(financialsElement.getElementsByTagName("PERCENTAGE_OF_REVENUE").item(0).getTextContent());
            officeExpensesPercentage = Integer.parseInt(financialsElement.getElementsByTagName("PERCENTAGE_OF_REVENUE").item(1).getTextContent());

            // Load exit policy configuration
            Element exitPolicyElement = (Element) doc.getElementsByTagName("EXIT_POLICY").item(0);
            noticePeriodMonths = Integer.parseInt(exitPolicyElement.getElementsByTagName("NOTICE_PERIOD_MONTHS").item(0).getTextContent());

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

    public static double getShortLoanInterestRate() {
        return shortLoanInterestRate;
    }

    public static double getNormalLoanInterestRate() {
        return normalLoanInterestRate;
    }

    public static double getDevelopmentLoanInterestRate() {
        return developmentLoanInterestRate;
    }

    public static double getEmergencyLoanMultiplier() {
        return emergencyLoanMultiplier;
    }

    public static double getShortLoanMultiplier() {
        return shortLoanMultiplier;
    }

    public static double getNormalLoanMultiplier() {
        return normalLoanMultiplier;
    }

    public static double getDevelopmentLoanMultiplier() {
        return developmentLoanMultiplier;
    }
}
