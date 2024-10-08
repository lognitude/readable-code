package cleancode.studycafe.tobe.machine.model;

import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import cleancode.studycafe.tobe.machine.model.vo.Money;
import cleancode.studycafe.tobe.machine.model.vo.StudyCafePassInfo;

public interface StudyCafePass {

    Money calculateDiscountPrice();

    Money calculateTotalPrice();

    boolean cannotUseLocker();

    boolean isSamePassType(StudyCafePassType passType);

    boolean isSamePassInfo(StudyCafePass other);

    Money getOriginalPrice();

    StudyCafePassInfo getPassInfo();
}
