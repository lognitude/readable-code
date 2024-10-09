package cleancode.studycafe.tobe.machine.finder.seat;

import cleancode.studycafe.tobe.machine.finder.StudyCafePassFinder;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassReader;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.StudyCafePasses;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeFixedSeatPassFinder implements StudyCafePassFinder {

    private static final StudyCafePassType PASS_TYPE = StudyCafePassType.FIXED;

    private final StudyCafePassReader seatPassReader;

    private StudyCafeFixedSeatPassFinder(StudyCafePassReader seatPassReader) {
        this.seatPassReader = seatPassReader;
    }

    public static StudyCafeFixedSeatPassFinder from(StudyCafePassReader seatPassReader) {
        return new StudyCafeFixedSeatPassFinder(seatPassReader);
    }

    @Override
    public boolean support(StudyCafePassType passType) {
        return PASS_TYPE == passType;
    }

    @Override
    public StudyCafePasses findAll() {
        List<StudyCafePass> seatPasses = getTotalStudyCafePasses();
        List<StudyCafePass> fixedSeatPasses = getFixedStudyCafeSeatPasses(seatPasses);
        return convertStudyCafePasses(fixedSeatPasses);
    }

    private List<StudyCafePass> getTotalStudyCafePasses() {
        return seatPassReader.readStudyCafePasses();
    }

    private List<StudyCafePass> getFixedStudyCafeSeatPasses(List<StudyCafePass> studyCafeSeatPasses) {
        return studyCafeSeatPasses.stream()
                                  .filter(studyCafePass -> studyCafePass.isSamePassType(PASS_TYPE))
                                  .toList();
    }

    private StudyCafePasses convertStudyCafePasses(List<StudyCafePass> target) {
        return StudyCafePasses.from(target);
    }
}
