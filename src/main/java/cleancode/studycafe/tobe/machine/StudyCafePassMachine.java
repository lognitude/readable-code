package cleancode.studycafe.tobe.machine;

import cleancode.studycafe.tobe.machine.dto.OrderSummaryDto;
import cleancode.studycafe.tobe.machine.exception.AppException;
import cleancode.studycafe.tobe.machine.finder.StudyCafeLockerPassFinders;
import cleancode.studycafe.tobe.machine.finder.StudyCafeSeatPassFinders;
import cleancode.studycafe.tobe.machine.model.vo.SelectedIndex;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.StudyCafePasses;
import cleancode.studycafe.tobe.machine.model.enums.LockerStatus;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;

public class StudyCafePassMachine {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final StudyCafeSeatPassFinders seatPassFinders;
    private final StudyCafeLockerPassFinders lockerPassFinders;

    private StudyCafePassMachine(
            InputHandler inputHandler,
            OutputHandler outputHandler,
            StudyCafeSeatPassFinders seatPassFinders,
            StudyCafeLockerPassFinders lockerPassFinders
    ) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.seatPassFinders = seatPassFinders;
        this.lockerPassFinders = lockerPassFinders;
    }

    public static StudyCafePassMachine of(
            InputHandler inputHandler,
            OutputHandler outputHandler,
            StudyCafeSeatPassFinders studyCafeSeatPassFinders,
            StudyCafeLockerPassFinders lockerPassFinders
    ) {
        return new StudyCafePassMachine(inputHandler, outputHandler, studyCafeSeatPassFinders, lockerPassFinders);
    }

    public void run() {
        showStartMessage();

        try {
            StudyCafePassType passType = getPassTypeSelectingUserAction();
            StudyCafePasses seatPasses = findAllPassesBy(passType);
            StudyCafePass selectedSeatPass = getPassSelectingUserAction(seatPasses);
            OrderSummaryDto orderSummaryDto = processOrderSummary(selectedSeatPass);

            showPassOrderSummary(orderSummaryDto);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void showStartMessage() {
        outputHandler.showWelcomeMessage();
        outputHandler.showAnnouncement();
    }

    private StudyCafePassType getPassTypeSelectingUserAction() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    private StudyCafePasses findAllPassesBy(StudyCafePassType passType) {
        return seatPassFinders.findAllBy(passType);
    }

    private StudyCafePass getPassSelectingUserAction(StudyCafePasses studyCafePasses) {
        outputHandler.showPassListForSelection(studyCafePasses);

        SelectedIndex selectedIndex = inputHandler.getSelectPass();
        return studyCafePasses.getSelectedPass(selectedIndex);
    }

    private OrderSummaryDto processOrderSummary(StudyCafePass seatPass) {
        if (seatPass.cannotUseLocker()) {
            return OrderSummaryDto.from(seatPass);
        }

        LockerStatus lockerStatus = getLockerStatusSelectingUserAction(seatPass);
        if (lockerStatus.isNotUsed()) {
            return OrderSummaryDto.from(seatPass);
        }

        StudyCafePasses lockerPasses = getTotalLockerPasses(seatPass);
        StudyCafePass selectedLockerPass = getSelectedPass(lockerPasses, seatPass);
        return OrderSummaryDto.of(seatPass, selectedLockerPass);
    }

    private void showPassOrderSummary(OrderSummaryDto orderSummaryDto) {
        outputHandler.showPassOrderSummary(orderSummaryDto);
    }

    private StudyCafePasses getTotalLockerPasses(StudyCafePass seatPass) {
        return lockerPassFinders.findAllBy(seatPass.getPassInfo().getPassType());
    }

    private StudyCafePass getSelectedPass(StudyCafePasses passes, StudyCafePass selectedPass) {
        return passes.getSameStudyCafeInfoPassBy(selectedPass);
    }

    private LockerStatus getLockerStatusSelectingUserAction(StudyCafePass selectedSeatPass) {
        outputHandler.askLockerPass(selectedSeatPass);
        return inputHandler.getLockerSelection();
    }
}
