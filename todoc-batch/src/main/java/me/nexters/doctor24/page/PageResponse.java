package me.nexters.doctor24.page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author manki.kim
 */
@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponse<T> {
	private List<T> contents;
	private int requestCount;
	private int page;

	public static <T> PageResponse<T> of(List<T> contents, PageRequest pageRequest) {
		return new PageResponse<>(contents, pageRequest.getCount(), pageRequest.getPage());
	}

	public boolean hasNext() {
		return !CollectionUtils.isEmpty(contents) && contents.size() == requestCount;
	}

	public int getNextPage() {
		return page + 1;
	}
}
