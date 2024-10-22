package cleancode.studycafe.tobe.model.pass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StudyCafeSeatPassTest {

    @Test
    @DisplayName("of() 메서드는 유효한 파라미터를 전달하면 StudyCafeSeatPass를 초기화해 반환한다")
    void ofTest() {
        // when & then
        StudyCafeSeatPass actual = assertDoesNotThrow(
            () -> StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 1, 1, 1.0d)
        );

        assertThat(actual).isInstanceOf(StudyCafeSeatPass.class);
    }

    private static Stream<Arguments> cannotUseLockerTestArguments() {
        return Stream.of(
            Arguments.of(StudyCafePassType.FIXED, false),
            Arguments.of(StudyCafePassType.HOURLY, true),
            Arguments.of(StudyCafePassType.WEEKLY, true)
        );
    }

    @ParameterizedTest(name = "StudyCafeSeatPass의 StudyCafePassType이 {0} 일 때 {1}을 반환한다")
    @DisplayName("cannotUseLocker() 메서드는 StudyCafeSeatPass가 락커를 사용할 수 있는지 여부를 반환한다")
    @MethodSource("cannotUseLockerTestArguments")
    void cannotUseLockerTest(StudyCafePassType passType, boolean expected) {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, 1, 1, 1.0d);

        // when
        boolean actual = seatPass.cannotUseLocker();

        // then
        assertThat(actual).isSameAs(expected);
    }

    private static Stream<Arguments> isSameDurationTypeTestArguments() {
        return Stream.of(
            Arguments.of(StudyCafePassType.HOURLY, 1, StudyCafePassType.HOURLY, 1, true),
            Arguments.of(StudyCafePassType.HOURLY, 1, StudyCafePassType.HOURLY, 2, false),
            Arguments.of(StudyCafePassType.HOURLY, 1, StudyCafePassType.FIXED, 1, false)
        );
    }

    @ParameterizedTest(name = "StudyCafeSeatPass의 StudyCafePassType이 {0}, duration이 {1}이고 StudyCafeLockerPass의 StudyCafePassType이 {2}, duration이 {3}일 때 {4}를 반환한다")
    @DisplayName("isSameDurationType() 메서드는 전달한 StudyCafeLockerPass의 StudyCafePassType과 duration이 같은지 여부를 반환한다")
    @MethodSource("isSameDurationTypeTestArguments")
    void isSameDurationTypeTest(StudyCafePassType seatPassType, int seatPassDuration, StudyCafePassType lockerPassType, int lockerPassDuration, boolean expected) {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(seatPassType, seatPassDuration, 1, 1.0d);

        // when
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(lockerPassType, lockerPassDuration, 1);
        boolean actual = seatPass.isSameDurationType(lockerPass);

        // then
        assertThat(actual).isSameAs(expected);
    }

    private static Stream<Arguments> isSamePassTypeTestArguments() {
        return Stream.of(
            Arguments.of(StudyCafePassType.FIXED, false),
            Arguments.of(StudyCafePassType.HOURLY, true),
            Arguments.of(StudyCafePassType.WEEKLY, false)
        );
    }

    @ParameterizedTest(name = "StudyCafeSeatPass의 StudyCafePassType이 HOURLY일 때 passType이 {0}이라면 {1}을 반환한다")
    @DisplayName("isSamePassType() 메서드는 StudyCafeSeatPass의 StudyCafePassType과 전달한 StudyCafePassTyp이 일치하는지 여부를 반환한다")
    @MethodSource("isSamePassTypeTestArguments")
    void isSamePassTypeTest(StudyCafePassType passType, boolean expected) {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 1, 1, 1.0d);

        // when
        boolean actual = seatPass.isSamePassType(passType);

        // then
        assertThat(actual).isSameAs(expected);
    }

    @Test
    @DisplayName("getDiscountPrice() 메서드는 StudyCafeSeatPass의 할인 금액을 계산해 반환한다")
    void getDiscountPriceTest() {
        // given
        int price = 1;
        double discountRate = 1.0d;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 1, price, discountRate);

        // when
        int actual = seatPass.getDiscountPrice();

        // then
        int expected = (int) (price * discountRate);

        assertThat(actual).isSameAs(expected);
    }
}
