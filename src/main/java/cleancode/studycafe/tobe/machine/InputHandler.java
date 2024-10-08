package cleancode.studycafe.tobe.machine;

import cleancode.studycafe.tobe.machine.io.view.vo.SelectedIndex;
import cleancode.studycafe.tobe.machine.model.enums.LockerStatus;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;

public interface InputHandler {

    StudyCafePassType getPassTypeSelectingUserAction();

    SelectedIndex getSelectPass();

    LockerStatus getLockerSelection();
}
