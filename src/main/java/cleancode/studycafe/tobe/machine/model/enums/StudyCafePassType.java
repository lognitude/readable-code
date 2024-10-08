package cleancode.studycafe.tobe.machine.model.enums;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private final String description;

    StudyCafePassType(String description) {
        this.description = description;
    }

    public boolean cannotUseLocker() {
        return this != StudyCafePassType.FIXED;
    }
}
