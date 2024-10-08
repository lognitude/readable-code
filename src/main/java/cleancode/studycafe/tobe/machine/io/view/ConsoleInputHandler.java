package cleancode.studycafe.tobe.machine.io.view;

import cleancode.studycafe.tobe.machine.InputHandler;
import cleancode.studycafe.tobe.machine.io.view.util.LockerStatusConverter;
import cleancode.studycafe.tobe.machine.io.view.util.StudyCafePassTypeConverter;
import cleancode.studycafe.tobe.machine.io.view.vo.SelectedIndex;
import cleancode.studycafe.tobe.machine.model.enums.LockerStatus;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public StudyCafePassType getPassTypeSelectingUserAction() {
        String userInput = SCANNER.nextLine();

        return StudyCafePassTypeConverter.convertBy(userInput);
    }

    @Override
    public SelectedIndex getSelectPass() {
        String userInput = SCANNER.nextLine();

        return SelectedIndex.from(userInput);
    }

    @Override
    public LockerStatus getLockerSelection() {
        String userInput = SCANNER.nextLine();

        return LockerStatusConverter.convertBy(userInput);
    }
}
