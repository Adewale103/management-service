package com.payu.management_service.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PagedResponse {
    private List<BookDto> books;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
    private int size;
    private int numberOfElementsInPage;
}
