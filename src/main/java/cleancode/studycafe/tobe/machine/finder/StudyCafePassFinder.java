package cleancode.studycafe.tobe.machine.finder;

import cleancode.studycafe.tobe.machine.model.StudyCafePasses;
import cleancode.studycafe.tobe.machine.model.enums.StudyCafePassType;

public interface StudyCafePassFinder {

    boolean support(StudyCafePassType passType);

    StudyCafePasses findAll();
}
