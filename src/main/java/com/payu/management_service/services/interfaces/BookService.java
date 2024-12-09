package com.payu.management_service.services.interfaces;

import com.payu.management_service.dtos.BookDto;
import com.payu.management_service.dtos.BookRequest;
import com.payu.management_service.dtos.PageDto;
import com.payu.management_service.dtos.PagedResponse;
import com.payu.management_service.exception.GenericException;

import java.io.IOException;

public interface BookService {
    BookDto createBook(BookRequest bookRequest) throws IOException;
    BookDto findById(long id) throws GenericException;
    void updateBook(long id, BookRequest bookRequest) throws IOException;
    PagedResponse findAll(int pageNumber, int noOfItems);
    PageDto searchCriteria(String query, int page, int size);
    void deleteBook(long id);
}
