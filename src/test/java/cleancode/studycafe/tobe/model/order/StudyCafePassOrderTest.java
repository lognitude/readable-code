package cleancode.studycafe.tobe.model.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyCafePassOrderTest {

    @Test
    @DisplayName("of() 메서드는 유효한 파라미터를 전달하면 StudyCafePassOrder를 초기화해 반환한다")
    void ofTest() {
        // given
        StudyCafePassType passType = StudyCafePassType.FIXED;
        int duration = 12;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, duration, 1, 1.0d);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(passType, duration, 1);

        // when & then
        StudyCafePassOrder actual = assertDoesNotThrow(() -> StudyCafePassOrder.of(seatPass, lockerPass));

        assertThat(actual).isInstanceOf(StudyCafePassOrder.class);
    }

    @Test
    @DisplayName("getDiscountPrice() 메서드는 StudyCafeSeatPass의 할인 금액을 반환한다")
    void getDiscountPriceTest() {
        // given
        StudyCafePassType passType = StudyCafePassType.FIXED;
        int duration = 12;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, duration, 1, 1.0d);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(passType, duration, 1);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        // when
        int actual = passOrder.getDiscountPrice();

        // then
        assertThat(actual).isSameAs(seatPass.getDiscountPrice());
    }

    @Test
    @DisplayName("getTotalPrice() 메서드는 StudyCafeSeatPass와 StudyCafeLockerPass가 모두 존재하면 그 둘의 가격을 합친 금액에서 StudyCafeSeatPass의 할인 금액을 뺀 금액을 반환한다")
    void totalPriceByNotExistsStudyCafeLockerPassTest() {
        // given
        StudyCafePassType passType = StudyCafePassType.FIXED;
        int duration = 12;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, duration, 1, 1.0d);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, null);

        // when
        int actual = passOrder.getTotalPrice();

        // then
        int expected = seatPass.getPrice() - seatPass.getDiscountPrice();

        assertThat(actual).isSameAs(expected);
    }

    @Test
    @DisplayName("getTotalPrice() 메서드는 StudyCafeSeatPass만 존재하면 StudyCafeSeatPass의 할인 금액을 반환한다")
    void totalPriceByExistsStudyCafeLockerPassTest() {
        // given
        StudyCafePassType passType = StudyCafePassType.FIXED;
        int duration = 12;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, duration, 1, 1.0d);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(passType, duration, 1);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        // when
        int actual = passOrder.getTotalPrice();

        // then
        int expected = (seatPass.getPrice() + lockerPass.getPrice()) - seatPass.getDiscountPrice();

        assertThat(actual).isSameAs(expected);
    }

    @Test
    @DisplayName("getLockerPass() 메서드는 LockerPass가 존재하면 LockerPass를 Optional로 감싸 반환한다")
    void getLockerPassByExistsLockerPassTest() {
        // given
        StudyCafePassType passType = StudyCafePassType.FIXED;
        int duration = 12;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, duration, 1, 1.0d);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(passType, duration, 1);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        // when
        Optional<StudyCafeLockerPass> actual = passOrder.getLockerPass();

        // then
        assertAll(
            () -> assertThat(actual).isPresent(),
            () -> assertThat(actual).contains(lockerPass)
        );
    }

    @Test
    @DisplayName("getLockerPass() 메서드는 LockerPass가 존재하지 않으면 빈 Optional을 반환한다")
    void getLockerPassByNotExistsLockerPassTest() {
        // given
        StudyCafePassType passType = StudyCafePassType.FIXED;
        int duration = 12;
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(passType, duration, 1, 1.0d);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, null);

        // when
        Optional<StudyCafeLockerPass> actual = passOrder.getLockerPass();

        // then
        assertThat(actual).isEmpty();
    }
}
