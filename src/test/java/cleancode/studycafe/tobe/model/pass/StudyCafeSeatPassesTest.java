package cleancode.studycafe.tobe.model.pass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafeSeatPassesTest {

    @Test
    @DisplayName("of() 메서드는 List 형태의 StudyCafeSeatPass를 전달하면 StudyCafeSeatPasses를 초기화해 반환한다")
    void ofTest() {
        // given
        List<StudyCafeSeatPass> studyCafeSeatPasses = List.of(
            StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 1, 1, 1.0d)
        );

        // when & then
        StudyCafeSeatPasses actual = assertDoesNotThrow(() -> StudyCafeSeatPasses.of(studyCafeSeatPasses));

        assertThat(actual).isInstanceOf(StudyCafeSeatPasses.class);
    }

    @Test
    @DisplayName("findPassBy() 메서드는 전달한 StudyCafePassType과 일치하는 StudyCafeSeatPass가 있다면 StudyCafeSeatPass를 List 형태로 반환한다")
    void findPassByExistsStudyCafePassTypeTest() {
        // given
        StudyCafePassType passType = StudyCafePassType.HOURLY;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, 1, 1, 1.0d);
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(List.of(seatPass));

        // when
        List<StudyCafeSeatPass> actual = seatPasses.findPassBy(passType);

        // then
        assertAll(
            () -> assertThat(actual).hasSize(1),
            () -> assertThat(actual.get(0)).isEqualTo(seatPass)
        );
    }

    @Test
    @DisplayName("findPassBy() 메서드는 전달한 StudyCafePassType과 일치하는 StudyCafeSeatPass가 없다면 빈 List를 반환한다")
    void findPassByNotExistsStudyCafePassTypeTest() {
        // given
        StudyCafePassType passType = StudyCafePassType.HOURLY;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, 1, 1, 1.0d);
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(List.of(seatPass));

        // when
        List<StudyCafeSeatPass> actual = seatPasses.findPassBy(StudyCafePassType.FIXED);

        // then
        assertThat(actual).isEmpty();
    }
}
