package ch.zhaw.pm3.helpy.matcher.strategy;

import ch.zhaw.pm3.helpy.model.job.Job;
import ch.zhaw.pm3.helpy.model.user.User;
import ch.zhaw.pm3.helpy.model.user.Weekdays;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WeekdayStrategy implements Strategy {

    private static final int RANGE = 1;
    private static final Weekdays[] weekdaysArray = Weekdays.values();

    @Override
    public Collection<User> filterPotentialHelpers(Job job, List<User> userList) {
        LocalDate dueDate = job.getDueDate();
        Set<Weekdays> weekdaysInRange = getWeekdaysInRange(dueDate);

        return userList.stream()
                .filter(User::isWantsToHelpActive)
                .filter(user -> !Collections.disjoint(user.getAvailableWeekDays(), weekdaysInRange))
                .collect(Collectors.toList());
    }

    private Set<Weekdays> getWeekdaysInRange(LocalDate date) {
        Set<Weekdays> weekdaysInRange = new HashSet<>();
        for(int i = 0; i <= RANGE; i++) {
            int newDayPosValue = date.plusDays(i).getDayOfWeek().getValue();
            int newDayNegValue = date.minusDays(i).getDayOfWeek().getValue();
            Weekdays weekdayPos = weekdaysArray[newDayPosValue-1];
            Weekdays weekdayNeg = weekdaysArray[newDayNegValue-1];
            weekdaysInRange.add(weekdayPos);
            weekdaysInRange.add(weekdayNeg);
        }
        return weekdaysInRange;
    }
}
