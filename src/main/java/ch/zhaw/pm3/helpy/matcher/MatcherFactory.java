package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.matcher.strategy.*;
import lombok.experimental.UtilityClass;

import javax.transaction.NotSupportedException;

@UtilityClass
public class MatcherFactory {
    public Strategy getMatcher(StrategyType strategyType) throws NotSupportedException {
        Strategy strategy;
        switch (strategyType) {
            case TAG:
                strategy = new TagStrategy();
            break;
            case RATING:
                strategy = new RatingStrategy();
            break;
            case WEEKDAY:
                strategy = new Weekday();
            break;
            case CATEGORY:
                strategy = new CategoryStrategy();
            break;
            case LOCATION:
                strategy = new LocationStrategy();
            break;
            default:
                throw new NotSupportedException("Default is not supported");
        }
        return strategy;
    }
}
