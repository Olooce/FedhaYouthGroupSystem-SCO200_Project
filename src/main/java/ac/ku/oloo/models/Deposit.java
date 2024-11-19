package ac.ku.oloo.models;

import java.math.BigDecimal;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.models)
 * Created by: oloo
 * On: 21/09/2024. 20:18
 * Description:
 **/

import javafx.beans.property.*;

public class Deposit {

    private final LongProperty depositId;
    private final LongProperty memberId;
    private final DoubleProperty amount;
    private final StringProperty dateCreated;

    public Deposit(long depositId, long memberId, double amount, String dateCreated) {
        this.depositId = new SimpleLongProperty(depositId);
        this.memberId = new SimpleLongProperty(memberId);
        this.amount = new SimpleDoubleProperty(amount);
        this.dateCreated = new SimpleStringProperty(dateCreated);
    }

    public LongProperty depositIdProperty() {
        return depositId;
    }

    public LongProperty memberIdProperty() {
        return memberId;
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public StringProperty dateCreatedProperty() {
        return dateCreated;
    }
}


