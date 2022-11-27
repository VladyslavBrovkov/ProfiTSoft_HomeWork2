package homework.second.Task2.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Model class for data serialization from JSON to XML
 */
public class Violation {

    private String type;

    private BigDecimal fineAmount;

    public Violation() {
    }

    public Violation(String type, BigDecimal fineAmount) {
        this.type = type;
        this.fineAmount = fineAmount;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Violation violation = (Violation) o;
        return Objects.equals(type, violation.type) && Objects.equals(fineAmount, violation.fineAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fineAmount);
    }

    @Override
    public String toString() {
        return "Violation{" +
                "type='" + type + '\'' +
                ", fineAmount=" + fineAmount +
                '}';
    }
}
