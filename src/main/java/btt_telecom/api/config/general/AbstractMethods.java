package btt_telecom.api.config.general;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AbstractMethods {
	
	public <T> Page<T> convertListToPage(List<T> list, Pageable pageable){
		list.stream()
			.skip(pageable.getPageSize() * pageable.getPageNumber())
			.limit(pageable.getPageSize())
			.collect(Collectors.toList());
		return new PageImpl<>(list, pageable, list.size());
	}
}
