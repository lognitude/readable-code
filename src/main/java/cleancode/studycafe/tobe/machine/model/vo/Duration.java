package cleancode.studycafe.tobe.machine.model.vo;

import cleancode.studycafe.tobe.machine.exception.AppClientException;
import java.util.Objects;

public class Duration {

    private static final int MIN_VALUE = 1;

    private final int value;

    private Duration(int value) {
        this.value = value;
    }

    public static Duration from(int value) {
        validate(value);

        return new Duration(value);
    }

    public int getValue() {
        return value;
    }

    private static void validate(int value) {
        if (value < MIN_VALUE) {
            throw new AppClientException("유효하지 않은 기간입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Duration duration = (Duration) o;
        return value == duration.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
