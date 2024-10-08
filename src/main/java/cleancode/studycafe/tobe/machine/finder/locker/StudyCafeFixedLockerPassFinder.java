package cleancode.studycafe.tobe.machine.finder.locker;

import cleancode.studycafe.tobe.machine.finder.StudyCafeLockerPassFinder;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassReader;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeFixedLockerPassFinder implements StudyCafeLockerPassFinder {

    private static final StudyCafePassType PASS_TYPE = StudyCafePassType.FIXED;

    private final StudyCafePassReader studyCafePassReader;

    private StudyCafeFixedLockerPassFinder(StudyCafePassReader passReader) {
        this.studyCafePassReader = passReader;
    }

    public static StudyCafeFixedLockerPassFinder from(StudyCafePassReader passReader) {
        return new StudyCafeFixedLockerPassFinder(passReader);
    }

    @Override
    public boolean support(StudyCafePassType passType) {
        return PASS_TYPE == passType;
    }

    @Override
    public List<StudyCafePass> findAll() {
        return studyCafePassReader.readStudyCafePasses();
    }
}
