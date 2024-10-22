package cleancode.studycafe.tobe.model.pass.locker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafeLockerPassesTest {

    @Test
    @DisplayName("of() 메서드는 List 형태의 StudyCafeLockerPass를 전달하면 StudyCafeLockerPasses를 초기화해 반환한다")
    void ofTest() {
        // given
        List<StudyCafeLockerPass> studyCafeLockerPasses = List.of(
            StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 1, 1)
        );

        // when & then
        StudyCafeLockerPasses actual = assertDoesNotThrow(() -> StudyCafeLockerPasses.of(studyCafeLockerPasses));

        assertThat(actual).extracting("lockerPasses")
                          .isEqualTo(studyCafeLockerPasses);
    }

    @Test
    @DisplayName("findPassBy() 메서드는 전달한 StudyCafeSeatPass와 연관된 StudyCafeSeatPass가 있다면 StudyCafeLockerPass를 Optional로 감싸 반환한다")
    void findLockerPassByExistsStudyCafeSeatPassTest() {
        // given
        StudyCafePassType passType = StudyCafePassType.HOURLY;
        int duration = 1;
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(passType, duration, 1);
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass));

        // when
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, duration, 1, 1.0d);
        Optional<StudyCafeLockerPass> actual = lockerPasses.findLockerPassBy(seatPass);

        // then
        assertAll(
            () -> assertThat(actual).isPresent(),
            () -> assertThat(actual).contains(lockerPass)
        );
    }

    @Test
    @DisplayName("findPassBy() 메서드는 전달한 StudyCafeSeatPass와 연관된 StudyCafeSeatPass가 없다면 빈 Optional을 반환한다")
    void findLockerPassByNotExistsStudyCafeSeatPassTest() {
        // given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 1, 1);
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(lockerPass));

        // when
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 2, 1, 1.0d);
        Optional<StudyCafeLockerPass> actual = lockerPasses.findLockerPassBy(seatPass);

        // then
        assertThat(actual).isEmpty();
    }
}
