package ru.zenegix.carrental.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class PageDto<T> {

    private final List<T> content;
    private final long page;
    private final long totalPages;

    public static <T> PageDto<T> fromPage(Page<T> page) {
        return PageDto.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .totalPages(page.getTotalPages())
                .build();
    }

}
