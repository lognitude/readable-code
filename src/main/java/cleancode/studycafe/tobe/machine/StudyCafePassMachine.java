package cleancode.studycafe.tobe.machine;

import cleancode.studycafe.tobe.machine.dto.OrderSummaryDto;
import cleancode.studycafe.tobe.machine.exception.AppClientException;
import cleancode.studycafe.tobe.machine.exception.AppException;
import cleancode.studycafe.tobe.machine.exception.AppServerException;
import cleancode.studycafe.tobe.machine.finder.StudyCafeLockerPassFinders;
import cleancode.studycafe.tobe.machine.finder.StudyCafeSeatPassFinders;
import cleancode.studycafe.tobe.machine.io.view.vo.SelectedIndex;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.enums.LockerStatus;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

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
            List<StudyCafePass> seatPasses = findAllPassesBy(passType);
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

    private List<StudyCafePass> findAllPassesBy(StudyCafePassType passType) {
        return seatPassFinders.findAllBy(passType);
    }

    private StudyCafePass getPassSelectingUserAction(List<StudyCafePass> studyCafePasses) {
        outputHandler.showPassListForSelection(studyCafePasses);

        SelectedIndex selectedIndex = inputHandler.getSelectPass();
        validateSelectedIndex(studyCafePasses, selectedIndex);

        return studyCafePasses.get(selectedIndex.getValue());
    }

    private OrderSummaryDto processOrderSummary(StudyCafePass seatPass) {
        if (seatPass.cannotUseLocker()) {
            return OrderSummaryDto.from(seatPass);
        }

        LockerStatus lockerStatus = getLockerStatusSelectingUserAction(seatPass);
        if (lockerStatus.isNotUsed()) {
            return OrderSummaryDto.from(seatPass);
        }

        List<StudyCafePass> lockerPasses = lockerPassFinders.findAllBy(seatPass.getPassInfo().getPassType());
        StudyCafePass selectedLockerPass = getSelectedLockerPass(lockerPasses, seatPass);
        return OrderSummaryDto.of(seatPass, selectedLockerPass);
    }

    private void showPassOrderSummary(OrderSummaryDto orderSummaryDto) {
        outputHandler.showPassOrderSummary(orderSummaryDto);
    }

    private static StudyCafePass getSelectedLockerPass(
            List<StudyCafePass> lockerPasses,
            StudyCafePass selectedSeatPass
    ) {
        return lockerPasses.stream()
                           .filter(lockerPass -> lockerPass.isSamePassInfo(selectedSeatPass))
                           .findAny()
                           .orElseThrow(() -> new AppServerException("지정한 좌석에 해당하는 락커가 없습니다."));
    }

    private LockerStatus getLockerStatusSelectingUserAction(StudyCafePass selectedSeatPass) {
        outputHandler.askLockerPass(selectedSeatPass);
        return inputHandler.getLockerSelection();
    }

    private void validateSelectedIndex(List<StudyCafePass> studyCafePasses, SelectedIndex selectedIndex) {
        if (selectedIndex.isGreaterOrEqualTo(studyCafePasses.size())) {
            throw new AppClientException(studyCafePasses.size() + " 이하의 값을 입력해주세요.");
        }
    }
}
