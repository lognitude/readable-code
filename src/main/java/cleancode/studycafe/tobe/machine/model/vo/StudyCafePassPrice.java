package cleancode.studycafe.tobe.machine.model.vo;

import cleancode.studycafe.tobe.machine.exception.AppClientException;

public class StudyCafePassPrice {

    private final Money price;
    private final double discountRate;

    private StudyCafePassPrice(Money price, double discountRate) {
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafePassPrice from(int price) {
        return StudyCafePassPrice.of(price, 0.0d);
    }

    public static StudyCafePassPrice of(int price, double discountRate) {
        Money money = Money.from(price);

        validateDiscountRate(discountRate);

        return new StudyCafePassPrice(money, discountRate);
    }

    public Money calculateDiscountPrice() {
        return price.multiply(discountRate);
    }

    public Money calculateTotalPrice() {
        if (discountRate == 0.0d) {
            return price;
        }

        return price.minus(calculateDiscountPrice());
    }

    private static void validateDiscountRate(double discountRate) {
        if (discountRate < 0.0d) {
            throw new AppClientException("할인율은 0 미만일 수 없습니다.");
        }
    }

    public Money getPrice() {
        return price;
    }
}
