package cleancode.studycafe.tobe.machine;

import cleancode.studycafe.tobe.machine.dto.OrderSummaryDto;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import java.util.List;

public interface OutputHandler {

    void showWelcomeMessage();

    void showAnnouncement();

    void askPassTypeSelection();

    void showPassListForSelection(List<StudyCafePass> passes);

    void askLockerPass(StudyCafePass lockerPass);

    void showPassOrderSummary(OrderSummaryDto orderSummaryDto);

    void showSimpleMessage(String message);
}
