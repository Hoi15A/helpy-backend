package ch.zhaw.pm3.helpy.matcher;

import ch.zhaw.pm3.helpy.matcher.filter.CategoryFilter;
import ch.zhaw.pm3.helpy.matcher.filter.LocationFilter;
import ch.zhaw.pm3.helpy.matcher.filter.RatingFilter;
import ch.zhaw.pm3.helpy.matcher.filter.Filter;
import ch.zhaw.pm3.helpy.matcher.filter.FilterType;
import ch.zhaw.pm3.helpy.matcher.filter.TagFilter;
import ch.zhaw.pm3.helpy.matcher.filter.WeekdayFilter;
import lombok.experimental.UtilityClass;

import javax.transaction.NotSupportedException;

/**
 * Utility class to create a filter.
 */
@UtilityClass
public class MatcherFactory {

    /**
     * Create and get the filter from the given type
     * @param filterType as {@link FilterType}
     * @return the created filter
     * @throws NotSupportedException if the given type does not exist
     */
    public Filter getMatcher(FilterType filterType) throws NotSupportedException {
        Filter filter;
        switch (filterType) {
            case TAG:
                filter = new TagFilter();
            break;
            case RATING:
                filter = new RatingFilter();
            break;
            case WEEKDAY:
                filter = new WeekdayFilter();
            break;
            case CATEGORY:
                filter = new CategoryFilter();
            break;
            case LOCATION:
                filter = new LocationFilter();
            break;
            default:
                throw new NotSupportedException("Default is not supported");
        }
        return filter;
    }
}
