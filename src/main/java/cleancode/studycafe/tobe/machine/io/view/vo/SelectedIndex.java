package cleancode.studycafe.tobe.machine.io.view.vo;

import cleancode.studycafe.asis.exception.AppException;
import java.util.Objects;

public class SelectedIndex {

    private final int value;

    private SelectedIndex(int value) {
        this.value = value;
    }

    public static SelectedIndex from(String userInput) {
        int value = convertIndexValue(userInput);

        validate(value);

        return new SelectedIndex(value);
    }

    public boolean isGreaterOrEqualTo(int target) {
        return this.value >= target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SelectedIndex targetSelectedIndex = (SelectedIndex) o;
        return value == targetSelectedIndex.value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    private static int convertIndexValue(String userInput) {
        try {
            return Integer.parseInt(userInput) - 1;
        } catch (NumberFormatException e) {
            throw new AppException("숫자를 입력해주세요.");
        }
    }

    private static void validate(int value) {
        if (value < 0) {
            throw new AppException("1 이상의 값을 입력해주세요.");
        }
    }
}
