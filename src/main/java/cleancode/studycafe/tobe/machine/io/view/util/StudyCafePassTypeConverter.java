package cleancode.studycafe.tobe.machine.io.view.util;

import cleancode.studycafe.tobe.machine.exception.AppClientException;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.Arrays;

public enum StudyCafePassTypeConverter {

    HOURLY("1", StudyCafePassType.HOURLY),
    WEEKLY("2", StudyCafePassType.WEEKLY),
    FIXED("3", StudyCafePassType.FIXED);

    private final String userInput;
    private final StudyCafePassType type;

    StudyCafePassTypeConverter(String userInput, StudyCafePassType type) {
        this.userInput = userInput;
        this.type = type;
    }

    public static StudyCafePassType convertBy(String userInput) {
        StudyCafePassTypeConverter converter = findBy(userInput);

        return converter.convert();
    }

    private static StudyCafePassTypeConverter findBy(String userInput) {
        return Arrays.stream(StudyCafePassTypeConverter.values())
                     .filter(converter -> converter.isSameUserInput(userInput))
                     .findAny()
                     .orElseThrow(() -> new AppClientException("잘못된 입력입니다."));
    }

    private boolean isSameUserInput(String inputValue) {
        return this.userInput.equals(inputValue);
    }

    private StudyCafePassType convert() {
        return this.type;
    }
}
