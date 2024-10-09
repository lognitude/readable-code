package cleancode.studycafe.tobe.machine.finder.locker;

import cleancode.studycafe.tobe.machine.finder.StudyCafePassFinder;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassReader;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.StudyCafePasses;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeFixedPassFinder implements StudyCafePassFinder {

    private static final StudyCafePassType PASS_TYPE = StudyCafePassType.FIXED;

    private final StudyCafePassReader lockerPassReader;

    private StudyCafeFixedPassFinder(StudyCafePassReader lockerPassReader) {
        this.lockerPassReader = lockerPassReader;
    }

    public static StudyCafeFixedPassFinder from(StudyCafePassReader lockerPassReader) {
        return new StudyCafeFixedPassFinder(lockerPassReader);
    }

    @Override
    public boolean support(StudyCafePassType passType) {
        return PASS_TYPE == passType;
    }

    @Override
    public StudyCafePasses findAll() {
        List<StudyCafePass> passes = lockerPassReader.readStudyCafePasses();
        return StudyCafePasses.from(passes);
    }
}
