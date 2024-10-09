package cleancode.studycafe.tobe.machine.finder.seat;

import cleancode.studycafe.tobe.machine.finder.StudyCafePassFinder;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassReader;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.StudyCafePasses;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeWeeklySeatPassFinder implements StudyCafePassFinder {

    private static final StudyCafePassType PASS_TYPE = StudyCafePassType.WEEKLY;

    private final StudyCafePassReader seatPassReader;

    private StudyCafeWeeklySeatPassFinder(StudyCafePassReader seatPassReader) {
        this.seatPassReader = seatPassReader;
    }

    public static StudyCafeWeeklySeatPassFinder from(StudyCafePassReader seatPassReader) {
        return new StudyCafeWeeklySeatPassFinder(seatPassReader);
    }

    @Override
    public boolean support(StudyCafePassType passType) {
        return PASS_TYPE == passType;
    }

    @Override
    public StudyCafePasses findAll() {
        List<StudyCafePass> seatPasses = getTotalStudyCafePasses();
        List<StudyCafePass> weeklySeatPasses = getWeeklyStudyCafeSeatPasses(seatPasses);
        return convertStudyCafePasses(weeklySeatPasses);
    }

    private List<StudyCafePass> getTotalStudyCafePasses() {
        return seatPassReader.readStudyCafePasses();
    }

    private List<StudyCafePass> getWeeklyStudyCafeSeatPasses(List<StudyCafePass> studyCafeSeatPasses) {
        return studyCafeSeatPasses.stream()
                                  .filter(studyCafePass -> studyCafePass.isSamePassType(PASS_TYPE))
                                  .toList();
    }

    private StudyCafePasses convertStudyCafePasses(List<StudyCafePass> target) {
        return StudyCafePasses.from(target);
    }
}
