package cleancode.studycafe.tobe.machine.finder;

import cleancode.studycafe.tobe.machine.exception.AppServerException;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeLockerPassFinders {

    private final List<StudyCafeLockerPassFinder> finders;

    private StudyCafeLockerPassFinders(List<StudyCafeLockerPassFinder> finders) {
        this.finders = finders;
    }

    public static StudyCafeLockerPassFinders from(List<StudyCafeLockerPassFinder> finders) {
        return new StudyCafeLockerPassFinders(finders);
    }

    public List<StudyCafePass> findAllBy(StudyCafePassType passType) {
        for (StudyCafeLockerPassFinder finder : finders) {
            if (finder.support(passType)) {
                return finder.findAll();
            }
        }

        throw new AppServerException("지정한 방식에 해당하는 락커 이용권이 없습니다.");
    }
}
