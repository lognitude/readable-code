package cleancode.studycafe.tobe.machine.model.vo;

import cleancode.studycafe.tobe.machine.exception.AppClientException;
import java.math.BigDecimal;
import java.util.Objects;

public class Money {

    private final BigDecimal value;

    private Money(BigDecimal value) {
        this.value = value;
    }

    public static Money from(int value) {
        BigDecimal targetValue = new BigDecimal(value);

        return Money.from(targetValue);
    }

    public static Money from(BigDecimal value) {
        validate(value);

        return new Money(value);
    }

    private static void validate(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppClientException("금액은 0원 이하일 수 없습니다.");
        }
    }

    public Money plus(Money target) {
        return new Money(this.value.add(target.value));
    }

    public Money minus(Money target) {
        return new Money(this.value.subtract(target.value));
    }

    public Money multiply(double target) {
        return new Money(value.multiply(BigDecimal.valueOf(target)));
    }

    public boolean isGreaterThan(int target) {
        return this.value.compareTo(new BigDecimal(target)) > 0;
    }

    public int getValue() {
        return value.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return Objects.equals(value, money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
