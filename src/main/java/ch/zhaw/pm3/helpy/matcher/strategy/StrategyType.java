package ch.zhaw.pm3.helpy.matcher.strategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StrategyType {
    CATEGORY(3, false),
    LOCATION(1, true),
    RATING(4, false),
    TAG(3, false),
    WEEKDAY(2, true);

    private final int priority;
    private final boolean mandatory;

}
