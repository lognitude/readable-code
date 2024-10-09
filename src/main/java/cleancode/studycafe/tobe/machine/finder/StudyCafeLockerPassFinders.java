package cleancode.studycafe.tobe.machine.finder;

import cleancode.studycafe.tobe.machine.exception.AppServerException;
import cleancode.studycafe.tobe.machine.model.StudyCafePasses;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeLockerPassFinders {

    private final List<StudyCafePassFinder> finders;

    private StudyCafeLockerPassFinders(List<StudyCafePassFinder> finders) {
        this.finders = finders;
    }

    public static StudyCafeLockerPassFinders from(List<StudyCafePassFinder> finders) {
        return new StudyCafeLockerPassFinders(finders);
    }

    public StudyCafePasses findAllBy(StudyCafePassType passType) {
        for (StudyCafePassFinder finder : finders) {
            if (finder.support(passType)) {
                return finder.findAll();
            }
        }

        throw new AppServerException("지정한 방식에 해당하는 락커 이용권이 없습니다.");
    }
}
