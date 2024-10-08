package cleancode.studycafe.tobe.machine.model.vo;

import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.Objects;

public class StudyCafePassInfo {

    private final StudyCafePassType passType;
    private final Duration duration;

    private StudyCafePassInfo(StudyCafePassType passType, Duration duration) {
        this.passType = passType;
        this.duration = duration;
    }

    public static StudyCafePassInfo of(StudyCafePassType passType, int duration) {
        return new StudyCafePassInfo(passType, Duration.from(duration));
    }

    public boolean cannotUseLocker() {
        return passType.cannotUseLocker();
    }

    public boolean isSamePassType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public StudyCafePassType getPassType() {
        return passType;
    }

    public int getDuration() {
        return duration.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudyCafePassInfo that = (StudyCafePassInfo) o;
        return passType == that.passType && Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passType, duration);
    }
}
