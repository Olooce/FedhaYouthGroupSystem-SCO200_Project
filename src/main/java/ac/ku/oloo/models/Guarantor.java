package ac.ku.oloo.models;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 22/10/2024. 08:22
 * Description:
 **/

public class Guarantor {
    private final String name;
    private final double guaranteedAmount;

    public Guarantor(String name, double guaranteedAmount) {
        this.name = name;
        this.guaranteedAmount = guaranteedAmount;
    }

    public String getName() {
        return name;
    }

    public double getGuaranteedAmount() {
        return guaranteedAmount;
    }
}
