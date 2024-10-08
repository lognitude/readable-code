package cleancode.studycafe.tobe.machine.model;

import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import cleancode.studycafe.tobe.machine.model.vo.Money;
import cleancode.studycafe.tobe.machine.model.vo.StudyCafePassInfo;
import cleancode.studycafe.tobe.machine.model.vo.StudyCafePassPrice;

public class StudyCafeLockerPass implements StudyCafePass {

    private final StudyCafePassInfo passInfo;
    private final StudyCafePassPrice passPrice;

    private StudyCafeLockerPass(StudyCafePassInfo passInfo, StudyCafePassPrice passPrice) {
        this.passInfo = passInfo;
        this.passPrice = passPrice;
    }

    public static StudyCafeLockerPass of(StudyCafePassType passType, int duration, int price) {
        StudyCafePassInfo seatInfo = StudyCafePassInfo.of(passType, duration);
        StudyCafePassPrice passPrice = StudyCafePassPrice.from(price);

        return new StudyCafeLockerPass(seatInfo, passPrice);
    }

    @Override
    public Money calculateDiscountPrice() {
        return passPrice.calculateDiscountPrice();
    }

    @Override
    public Money calculateTotalPrice() {
        return passPrice.calculateTotalPrice();
    }

    @Override
    public boolean cannotUseLocker() {
        return passInfo.cannotUseLocker();
    }

    @Override
    public boolean isSamePassType(StudyCafePassType passType) {
        return passInfo.isSamePassType(passType);
    }

    @Override
    public boolean isSamePassInfo(StudyCafePass other) {
        return this.passInfo.equals(other.getPassInfo());
    }

    @Override
    public Money getOriginalPrice() {
        return passPrice.getPrice();
    }

    @Override
    public StudyCafePassInfo getPassInfo() {
        return passInfo;
    }
}
