package cleancode.studycafe.tobe.machine.model.enums;

public enum LockerStatus {

    USED, UN_USED;

    public boolean isNotUsed() {
        return LockerStatus.USED != this;
    }
}
