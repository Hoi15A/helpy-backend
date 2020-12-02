package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.Weekdays;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class WeekdayStrategy extends MatcherStrategy {

    private static final int RANGE = 1;

    @Override
    public Collection<User> getPotentialHelpers(Job job) {
        LocalDate dueDate = job.getDueDate();
        Set<Weekdays> weekdaysInRange = getWeekdaysInRange(dueDate);
        System.out.println(weekdaysInRange);
        Set<User> potentialHelpers = new HashSet<>();
        for(Weekdays weekday : weekdaysInRange) {
            potentialHelpers.addAll(getUserRepository().findUsersByAvailability(weekday));
        }
        return potentialHelpers;
    }

    private Set<Weekdays> getWeekdaysInRange(LocalDate date) {
        HashSet<Weekdays> weekdaysInRange = new HashSet<>();
        for(int i = 0; i <= RANGE; i++) {
            int newDayPosValue = date.plusDays(i).getDayOfWeek().getValue();
            int newDayNegValue = date.minusDays(i).getDayOfWeek().getValue();
            Weekdays weekdayPos = Weekdays.values()[newDayPosValue-1];
            Weekdays weekdayNeg = Weekdays.values()[newDayNegValue-1];
            weekdaysInRange.add(weekdayPos);
            weekdaysInRange.add(weekdayNeg);
        }
        return weekdaysInRange;
    }
}
