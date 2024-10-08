package cleancode.studycafe.tobe.machine.finder.seat;

import cleancode.studycafe.tobe.machine.finder.StudyCafeSeatPassFinder;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassReader;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeWeeklySeatPassFinder implements StudyCafeSeatPassFinder {

    private static final StudyCafePassType PASS_TYPE = StudyCafePassType.WEEKLY;

    private final StudyCafePassReader passReader;

    private StudyCafeWeeklySeatPassFinder(StudyCafePassReader passReader) {
        this.passReader = passReader;
    }

    public static StudyCafeWeeklySeatPassFinder from(StudyCafePassReader passReader) {
        return new StudyCafeWeeklySeatPassFinder(passReader);
    }

    @Override
    public boolean support(StudyCafePassType passType) {
        return PASS_TYPE == passType;
    }

    @Override
    public List<StudyCafePass> findAll() {
        List<StudyCafePass> totalStudyCafeSeatPasses = getTotalStudyCafePasses();
        return getWeeklyStudyCafePasses(totalStudyCafeSeatPasses);
    }

    private List<StudyCafePass> getTotalStudyCafePasses() {
        return passReader.readStudyCafePasses();
    }

    private List<StudyCafePass> getWeeklyStudyCafePasses(List<StudyCafePass> studyCafeSeatPasses) {
        return studyCafeSeatPasses.stream()
                                  .filter(studyCafePass -> studyCafePass.isSamePassType(PASS_TYPE))
                                  .toList();
    }
}
