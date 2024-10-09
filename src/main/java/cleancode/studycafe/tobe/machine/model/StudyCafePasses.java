package cleancode.studycafe.tobe.machine.model;

import cleancode.studycafe.tobe.machine.exception.AppClientException;
import cleancode.studycafe.tobe.machine.exception.AppServerException;
import cleancode.studycafe.tobe.machine.model.vo.SelectedIndex;
import java.util.List;

public class StudyCafePasses {

    private final List<StudyCafePass> passes;

    private StudyCafePasses(List<StudyCafePass> passes) {
        this.passes = passes;
    }

    public static StudyCafePasses from(List<StudyCafePass> passes) {
        return new StudyCafePasses(passes);
    }

    public StudyCafePass getSelectedPass(SelectedIndex selectedIndex) {
        validateSelectedIndex(passes, selectedIndex);

        return passes.get(selectedIndex.getValue());
    }

    public StudyCafePass getSameStudyCafeInfoPassBy(StudyCafePass target) {
        return passes.stream()
                     .filter(pass -> pass.isSamePassInfo(target))
                     .findAny()
                     .orElseThrow(() -> new AppServerException("지정한 이용권과 같은 정보를 가진 이용권이 없습니다."));
    }

    private void validateSelectedIndex(List<StudyCafePass> passes, SelectedIndex selectedIndex) {
        if (selectedIndex.isGreaterOrEqualTo(passes.size())) {
            throw new AppClientException(passes.size() + " 이하의 값을 입력해주세요.");
        }
    }

    public List<StudyCafePass> getPasses() {
        return List.copyOf(passes);
    }
}
