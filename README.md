# 질문

1. 도메인에 VO를 둔 상태에서 VO에 저장되려는 값을 단순히 출력하기 위한 메서드는 VO를 직접 반환하는게 선호되는 편(1번)인가요, 아니면 VO로 감싼 값을 꺼내서 바로 반환하는 것이 선호되는 편(2번)인가요?
   리팩터링에서는 1번으로 진행한 상황입니다.

```java
public class Money {
    
    private final BigDecimal value;
    
    public BigDecimal getValue() {
        return value;
    }
}

// 1번
public class StudyCafeLockerPass {
    
    private Money price;
    
    public Money getPrice() {
        return price;
    }
}

// 2번
public class StudyCafeLockerPass {
    
    private Money price;
    
    public BigDecimal getPrice() {
        return price.getValue();
    }
}
```

2. 최대한 SRP를 신경쓰다보니 다음과 같이 단순 호출이 되는 메서드가 많아졌습니다.
   이러한 상황이 자연스러운 현상인지 너무 과도하게 객체를 분리한 것이라는게 드러난 것인지 궁금합니다.

```java
public class StudyCafeLockerPass {
    
    private final StudyCafePassInfo passInfo;
    
    public boolean cannotUseLocker() {
        return passInfo.cannotUseLocker();
    }	
}

public class StudyCafePassInfo {
    
    private final StudyCafePassType passType;
    
    public boolean cannotUseLocker() {
        return passType.cannotUseLocker();
    }
}

public enum StudyCafePassType {
    
    HOURLY("시간 단위 이용권"), 
    WEEKLY("주 단위 이용권"), 
    FIXED("1인 고정석");
    
    public boolean cannotUseLocker() {
        return this != StudyCafePassType.FIXED;
    }
}
```

3. StudyCafePass / StudyCafeLockerPass 모두 이용권이라는 개념을 표현했다고 생각했습니다.

   그래서 StudyCafeSeatPass / StudyCafeLockerPass로 네이밍을 변경한 뒤 StudyCafePass라는 인터페이스로 묶었습니다.

   인터페이스로 묶다 보니 현 시점에서는 StudyCafeLockerPass만이 사용하는 메서드가 StudyCafePass 인터페이스에 명시된 상황이 되었습니다.

   또한 cannotUseLocker()라는 메서드는 StudyCafeLockerPass에서는 무조건 false를 반환해야 하기 때문에 의미가 좀 퇴색된다는 생각이 들었습니다.

   StudyCafePass 인터페이스 입장에서는 어색하다는 느낌은 안들었지만, 구현체 입장에서 본다면 어색한 메서드가 들어간 것 같아 너무 성급하게 추상화를 한 게 아닐까? 하는 생각이 들었습니다.

   이에 대해 조언을 부탁드리고 싶습니다.

```java
public interface StudyCafePass {
    
    Money calculateDiscountPrice();
    
    Money calculateTotalPrice();
    
    // StudyCafeLockerPass에서는 무조건 false를 반환해야 함
    boolean cannotUseLocker();
    
    boolean isSamePassType(StudyCafePassType passType);
    
    // 현 시점 StudyCafeSeatPass에서는 사용되지 않음
    boolean isSamePassInfo(StudyCafePass other);
    
    Money getOriginalPrice();
    
    StudyCafePassInfo getPassInfo();
}
```
