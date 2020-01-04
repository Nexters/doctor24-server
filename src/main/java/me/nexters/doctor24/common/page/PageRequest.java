package me.nexters.doctor24.common.page;

import lombok.Value;

/**
 * @author manki.kim
 */
@Value(staticConstructor = "of")
public class PageRequest {
	private int page;
	private int count;
}
