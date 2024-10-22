package cleancode.studycafe.tobe.model.pass.locker;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StudyCafeLockerPassTest {

    @Test
    @DisplayName("of() 메서드는 유효한 파라미터를 전달하면 StudyCafeSeatPass를 초기화해 반환한다")
    void ofTest() {
        // when & then
        StudyCafeLockerPass actual = assertDoesNotThrow(
            () -> StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 1, 1)
        );

        assertThat(actual).isInstanceOf(StudyCafeLockerPass.class);
    }

    private static Stream<Arguments> isSamePassTypeTestArguments() {
        return Stream.of(
            Arguments.of(StudyCafePassType.FIXED, false),
            Arguments.of(StudyCafePassType.HOURLY, true),
            Arguments.of(StudyCafePassType.WEEKLY, false)
        );
    }

    @ParameterizedTest(name = "StudyCafeLockerPass의 StudyCafePassType이 HOURLY일 때 passType이 {0}이라면 {1}을 반환한다")
    @DisplayName("isSamePassType() 메서드는 StudyCafeLockerPass의 StudyCafePassType과 전달한 StudyCafePassTyp이 일치하는지 여부를 반환한다")
    @MethodSource("isSamePassTypeTestArguments")
    void isSamePassTypeTest(StudyCafePassType passType, boolean expected) {
        // given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 1, 1);

        // when
        boolean actual = lockerPass.isSamePassType(passType);

        // then
        Assertions.assertThat(actual).isSameAs(expected);
    }

    private static Stream<Arguments> isSameDurationTestArguments() {
        return Stream.of(
            Arguments.of(1, true),
            Arguments.of(2, false)
        );
    }

    @ParameterizedTest(name = "StudyCafeLockerPass의 duration이 1일 때 전달된 duration이 {0}이라면 {1}을 반환한다")
    @DisplayName("isSmaeDuration() 메서드는 StudyCafeLockerPass의 duration과 전달한 duration이 일치하는지 여부를 반환한다")
    @MethodSource("isSameDurationTestArguments")
    void isSameDurationTest(int duration, boolean expected) {
        // given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.HOURLY, 1, 1);

        // when
        boolean actual = lockerPass.isSameDuration(duration);

        // then
        assertThat(actual).isSameAs(expected);
    }
}
