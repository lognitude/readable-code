package cleancode.studycafe.tobe.model.pass;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StudyCafePassTypeTest {

    @ParameterizedTest(name = "StudyCafePass가 {0}일 때 {1}을 반환한다")
    @DisplayName("isLockerType() 메서드는 해당 StudyCafePassType이 LockerType인지 여부를 반환한다")
    @MethodSource("isLockerTypeTestArguments")
    void isLockerTypeTest(StudyCafePassType passType, boolean expected) {
        // when
        boolean actual = passType.isLockerType();

        // then
        assertThat(actual).isSameAs(expected);
    }

    @ParameterizedTest(name = "StudyCafePass가 {0}일 때 {1}을 반환한다")
    @DisplayName("isLockerType() 메서드는 해당 StudyCafePassType이 LockerType가 아닌지 여부를 반환한다")
    @MethodSource("isNotLockerTypeTestArguments")
    void isNotLockerTypeTest(StudyCafePassType passType, boolean expected) {
        // when
        boolean actual = passType.isNotLockerType();

        // then
        assertThat(actual).isSameAs(expected);
    }

    private static Stream<Arguments> isLockerTypeTestArguments() {
        return Stream.of(
            Arguments.of(StudyCafePassType.FIXED, true),
            Arguments.of(StudyCafePassType.HOURLY, false),
            Arguments.of(StudyCafePassType.WEEKLY, false)
        );
    }

    private static Stream<Arguments> isNotLockerTypeTestArguments() {
        return Stream.of(
            Arguments.of(StudyCafePassType.FIXED, false),
            Arguments.of(StudyCafePassType.HOURLY, true),
            Arguments.of(StudyCafePassType.WEEKLY, true)
        );
    }
}
