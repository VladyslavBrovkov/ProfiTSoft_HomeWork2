package homework.second.Task2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class Violation {

    private final String type;

    private final BigDecimal fineAmount;


    public Violation(@JsonProperty(value = "type") String type,
                     @JsonProperty(value = "fine_amount") BigDecimal fineAmount) {
        this.type = type;
        this.fineAmount = fineAmount;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getFine_amount() {
        return fineAmount;
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
