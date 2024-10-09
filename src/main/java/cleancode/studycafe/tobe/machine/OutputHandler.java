package cleancode.studycafe.tobe.machine;

import cleancode.studycafe.tobe.machine.dto.OrderSummaryDto;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.StudyCafePasses;

public interface OutputHandler {

    void showWelcomeMessage();

    void showAnnouncement();

    void askPassTypeSelection();

    void showPassListForSelection(StudyCafePasses passes);

    void askLockerPass(StudyCafePass lockerPass);

    void showPassOrderSummary(OrderSummaryDto orderSummaryDto);

    void showSimpleMessage(String message);
}
