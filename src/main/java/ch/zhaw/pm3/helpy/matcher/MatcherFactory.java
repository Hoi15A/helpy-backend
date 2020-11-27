package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.matcher.strategy.*;
import lombok.experimental.UtilityClass;

import javax.transaction.NotSupportedException;

@UtilityClass
public class MatcherFactory {
    public MatcherStrategy getMatcher(StrategyType strategyType) throws NotSupportedException {
        MatcherStrategy strategy;
        switch (strategyType) {
            case TAG:
                strategy = new TagMatcher();
            break;
            case RATING:
                strategy = new RatingMatcher();
            break;
            case WEEKDAY:
                strategy = new WeekdayMatcher();
            break;
            case CATEGORY:
                strategy = new CategoryMatcher();
            break;
            case LOCATION:
                strategy = new LocationMatcher();
            break;
            default:
                throw new NotSupportedException("Default is not supported");
        }
        return strategy;
    }
}
