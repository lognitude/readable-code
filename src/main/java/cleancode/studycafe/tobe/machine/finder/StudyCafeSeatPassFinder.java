package cleancode.studycafe.tobe.machine.finder;

import cleancode.studycafe.tobe.machine.model.StudyCafePass;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;
import java.util.List;

public interface StudyCafeSeatPassFinder {

    boolean support(StudyCafePassType passType);

    List<StudyCafePass> findAll();
}
