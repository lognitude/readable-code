package cleancode.studycafe.tobe.machine.finder.seat;

import cleancode.studycafe.tobe.machine.finder.StudyCafeSeatPassFinder;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassReader;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeFixedSeatPassFinder implements StudyCafeSeatPassFinder {

    private static final StudyCafePassType PASS_TYPE = StudyCafePassType.FIXED;

    private final StudyCafePassReader passReader;

    private StudyCafeFixedSeatPassFinder(StudyCafePassReader passReader) {
        this.passReader = passReader;
    }

    public static StudyCafeFixedSeatPassFinder from(StudyCafePassReader passReader) {
        return new StudyCafeFixedSeatPassFinder(passReader);
    }

    @Override
    public boolean support(StudyCafePassType passType) {
        return PASS_TYPE == passType;
    }

    @Override
    public List<StudyCafePass> findAll() {
        List<StudyCafePass> studyCafeSeatPasses = getTotalStudyCafePasses();
        return getFixedStudyCafePasses(studyCafeSeatPasses);
    }

    private List<StudyCafePass> getTotalStudyCafePasses() {
        return passReader.readStudyCafePasses();
    }

    private List<StudyCafePass> getFixedStudyCafePasses(List<StudyCafePass> studyCafeSeatPasses) {
        return studyCafeSeatPasses.stream()
                                  .filter(studyCafePass -> studyCafePass.isSamePassType(PASS_TYPE))
                                  .toList();
    }
}
