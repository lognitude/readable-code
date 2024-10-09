package cleancode.studycafe.tobe.machine.finder.seat;

import cleancode.studycafe.tobe.machine.finder.StudyCafePassFinder;
import cleancode.studycafe.tobe.machine.io.reader.StudyCafePassReader;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.StudyCafePasses;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeHourlySeatPassFinder implements StudyCafePassFinder {

    private static final StudyCafePassType PASS_TYPE = StudyCafePassType.HOURLY;

    private final StudyCafePassReader seatPassReader;

    private StudyCafeHourlySeatPassFinder(StudyCafePassReader seatPassReader) {
        this.seatPassReader = seatPassReader;
    }

    public static StudyCafeHourlySeatPassFinder from(StudyCafePassReader seatPassReader) {
        return new StudyCafeHourlySeatPassFinder(seatPassReader);
    }

    @Override
    public boolean support(StudyCafePassType passType) {
        return PASS_TYPE == passType;
    }

    @Override
    public StudyCafePasses findAll() {
        List<StudyCafePass> seatPasses = getTotalStudyCafePasses();
        List<StudyCafePass> hourlySeatPasses = getHourlyStudyCafeSeatPasses(seatPasses);
        return convertStudyCafePasses(hourlySeatPasses);
    }

    private List<StudyCafePass> getTotalStudyCafePasses() {
        return seatPassReader.readStudyCafePasses();
    }

    private List<StudyCafePass> getHourlyStudyCafeSeatPasses(List<StudyCafePass> studyCafeSeatPasses) {
        return studyCafeSeatPasses.stream()
                                  .filter(studyCafePass -> studyCafePass.isSamePassType(PASS_TYPE))
                                  .toList();
    }

    private StudyCafePasses convertStudyCafePasses(List<StudyCafePass> target) {
        return StudyCafePasses.from(target);
    }
}
