package me.nexters.doctor24.page;

import lombok.Value;

/**
 * @author manki.kim
 */
@Value(staticConstructor = "of")
public class PageRequest {
	private int page;
	private int count;

	public int getPageSafety() {
		return page == 0 ? 1 : page;
	}
}
