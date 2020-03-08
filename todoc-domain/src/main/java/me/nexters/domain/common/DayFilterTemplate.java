package me.nexters.domain.common;

import java.util.List;
import java.util.Optional;

/**
 * @author manki.kim
 */
public interface DayFilterTemplate {

	List<Day> getDays();

	default boolean isOpen(Day requestDay) {
		Optional<Day> matchDayOpt = getDays().stream()
			.filter(day -> day.getDayType() == requestDay.getDayType())
			.findAny();
		if (matchDayOpt.isEmpty()) {
			return false;
		}

		Day matchDay = matchDayOpt.get();
		return matchDay.isInRange(requestDay.getStartTime(), requestDay.getEndTime());
	}

	default boolean isOpenWithoutTime(Day requestDay) {
		return getDays().stream()
			.anyMatch(day -> day.getDayType() == requestDay.getDayType());
	}
}
