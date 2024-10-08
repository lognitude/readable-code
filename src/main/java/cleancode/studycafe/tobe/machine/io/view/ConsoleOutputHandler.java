package cleancode.studycafe.tobe.machine.io.view;

import cleancode.studycafe.tobe.machine.OutputHandler;
import cleancode.studycafe.tobe.machine.dto.OrderSummaryDto;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import cleancode.studycafe.tobe.machine.model.vo.Money;
import cleancode.studycafe.tobe.machine.model.vo.StudyCafePassInfo;
import java.util.List;

public class ConsoleOutputHandler implements OutputHandler {

    @Override
    public void showWelcomeMessage() {
        System.out.println("*** 프리미엄 스터디카페 ***");
    }

    @Override
    public void showAnnouncement() {
        System.out.println("* 사물함은 고정석 선택 시 이용 가능합니다. (추가 결제)");
        System.out.println("* !오픈 이벤트! 2주권 이상 결제 시 10% 할인, 12주권 결제 시 15% 할인! (결제 시 적용)");
        System.out.println();
    }

    @Override
    public void askPassTypeSelection() {
        System.out.println("사용하실 이용권을 선택해 주세요.");
        System.out.println("1. 시간 이용권(자유석) | 2. 주단위 이용권(자유석) | 3. 1인 고정석");
    }

    @Override
    public void showPassListForSelection(List<StudyCafePass> passes) {
        System.out.println();
        System.out.println("이용권 목록");
        for (int index = 0; index < passes.size(); index++) {
            StudyCafePass pass = passes.get(index);
            System.out.println(String.format("%s. ", index + 1) + displayPass(pass));
        }
    }

    @Override
    public void askLockerPass(StudyCafePass lockerPass) {
        System.out.println();
        String askMessage = String.format("사물함을 이용하시겠습니까? (%s)", displayPass(lockerPass));

        System.out.println(askMessage);
        System.out.println("1. 예 | 2. 아니오");
    }

    @Override
    public void showPassOrderSummary(OrderSummaryDto orderSummaryDto) {
        StudyCafePass seatPass = orderSummaryDto.seatPass();
        StudyCafePass lockerPass = orderSummaryDto.lockerPass();

        System.out.println();
        System.out.println("이용 내역");
        System.out.println("이용권: " + displayPass(seatPass));
        if (lockerPass != null) {
            System.out.println("사물함: " + displayPass(lockerPass));
        }

        Money discountPrice = seatPass.calculateDiscountPrice();

        if (discountPrice.isGreaterThan(0)) {
            System.out.println("이벤트 할인 금액: " + discountPrice.getValue() + "원");
        }

        Money totalPrice = seatPass.calculateTotalPrice();

        if (lockerPass != null) {
            totalPrice = totalPrice.plus(lockerPass.getOriginalPrice());
        }

        System.out.println("총 결제 금액: " + totalPrice.getValue() + "원");
        System.out.println();
    }

    @Override
    public void showSimpleMessage(String message) {
        System.out.println(message);
    }

    private String displayPass(StudyCafePass pass) {
        StudyCafePassInfo identifier = pass.getPassInfo();
        StudyCafePassType passType = identifier.getPassType();
        int duration = identifier.getDuration();
        int price = pass.getOriginalPrice()
                        .getValue();

        if (passType == StudyCafePassType.HOURLY) {
            return String.format("%s시간권 - %d원", duration, price);
        }
        if (passType == StudyCafePassType.WEEKLY) {
            return String.format("%s주권 - %d원", duration, price);
        }
        if (passType == StudyCafePassType.FIXED) {
            return String.format("%s주권 - %d원", duration, price);
        }
        return "";
    }
}
