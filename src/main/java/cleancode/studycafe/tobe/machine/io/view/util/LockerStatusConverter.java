package cleancode.studycafe.tobe.machine.io.view.util;

import cleancode.studycafe.tobe.machine.exception.AppClientException;
import cleancode.studycafe.tobe.machine.model.enums.LockerStatus;
import java.util.Arrays;

public enum LockerStatusConverter {
    USED("1", LockerStatus.USED),
    UN_USED("2", LockerStatus.UN_USED);

    private final String userInput;
    private final LockerStatus status;

    LockerStatusConverter(String userInput, LockerStatus status) {
        this.userInput = userInput;
        this.status = status;
    }

    public static LockerStatus convertBy(String userInput) {
        LockerStatusConverter converter = findBy(userInput);

        return converter.convert();
    }

    private static LockerStatusConverter findBy(String userInput) {
        return Arrays.stream(LockerStatusConverter.values())
                     .filter(converter -> converter.isSameUserInput(userInput))
                     .findAny()
                     .orElseThrow(() -> new AppClientException("잘못된 입력입니다."));
    }

    private boolean isSameUserInput(String inputValue) {
        return this.userInput.equals(inputValue);
    }

    private LockerStatus convert() {
        return this.status;
    }
}
