package cleancode.studycafe.tobe.machine.dto;

import cleancode.studycafe.tobe.machine.model.StudyCafePass;

public record OrderSummaryDto(StudyCafePass seatPass, StudyCafePass lockerPass) {

    public static OrderSummaryDto of(StudyCafePass seatPass, StudyCafePass lockerPass) {
        return new OrderSummaryDto(seatPass, lockerPass);
    }

    public static OrderSummaryDto from(StudyCafePass seatPass) {
        return OrderSummaryDto.of(seatPass, null);
    }
}
