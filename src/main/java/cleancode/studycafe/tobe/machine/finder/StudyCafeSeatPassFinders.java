package cleancode.studycafe.tobe.machine.finder;

import cleancode.studycafe.tobe.machine.exception.AppServerException;
import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public class StudyCafeSeatPassFinders {

    private final List<StudyCafeSeatPassFinder> finders;

    private StudyCafeSeatPassFinders(List<StudyCafeSeatPassFinder> finders) {
        this.finders = finders;
    }

    public static StudyCafeSeatPassFinders from(List<StudyCafeSeatPassFinder> finders) {
        return new StudyCafeSeatPassFinders(finders);
    }

    public List<StudyCafePass> findAllBy(StudyCafePassType passType) {
        for (StudyCafeSeatPassFinder finder : finders) {
            if (finder.support(passType)) {
                return finder.findAll();
            }
        }

        throw new AppServerException("지정한 방식에 해당하는 좌석 이용권이 없습니다.");
    }
}
