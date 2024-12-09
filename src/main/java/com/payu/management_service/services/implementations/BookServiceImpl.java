package com.payu.management_service.services.implementations;

import com.payu.management_service.dtos.*;
import com.payu.management_service.exception.GenericException;
import com.payu.management_service.models.Book;
import com.payu.management_service.repositories.BookRepository;
import com.payu.management_service.services.interfaces.BookService;
import com.payu.management_service.specifications.BookSpecs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static com.payu.management_service.utils.Constants.ALREADY_EXIST;
import static com.payu.management_service.utils.Constants.NOT_FOUND;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createBook(BookRequest bookRequest) {
        log.info("Creating new book {}", bookRequest);
        Optional<Book> foundBook = bookRepository.findBookByIsbn(bookRequest.getIsbn());
        if(foundBook.isPresent()){
            throw new GenericException(ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookRequest, book);
        book.setPublishDate(LocalDate.parse(bookRequest.getPublishedDate()));
        bookRepository.save(book);
        return BookDto.toDto(book);
    }


    @Override
    public BookDto findById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
        return BookDto.toDto(book);
    }

    @Override
    public void updateBook(long id, BookRequest bookRequest) {
        Book book = this.findBook(id);
        BeanUtils.copyProperties(bookRequest, book);
        book.setPublishDate(LocalDate.parse(bookRequest.getPublishedDate()));
        bookRepository.save(book);
    }

    @Override
    public PagedResponse findAll(int pageNumber, int noOfItems) {
        Pageable pageable = PageRequest.of(pageNumber, noOfItems, Sort.by("name"));
        Page<Book> page = bookRepository.findAll(pageable);

        PagedResponse pagedResponse = new PagedResponse();
        pagedResponse.setBooks(mapBookToBookDto(page.getContent()));
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setTotalPages(page.getTotalPages());
        pagedResponse.setTotalElements(page.getTotalElements());
        pagedResponse.setSize(page.getSize());
        pagedResponse.setNumberOfElementsInPage(page.getNumberOfElements());
        return pagedResponse;
    }
    private static List<BookDto> mapBookToBookDto(List<Book> books) {
        return books.stream().map(BookDto::fromModel).toList();
    }


    @Override
    public PageDto searchCriteria(String query, int page, int size) {
        BookSpecs bookSpecs = new BookSpecs(query);
        bookSpecs.setPage(page);
        bookSpecs.setSize(size);
        Page<Book> bookPage = bookRepository.findAll(bookSpecs, bookSpecs.getPageable());
        return PageDto.build(bookPage, BookDto::toDto);
    }

    /* This can also be implemented as soft delete where the record is not deleted but made invisible by adding a boolean field to the Book column i.e deleted - true or false */
    @Override
    public void deleteBook(long id) {
        Book book = this.findBook(id);
        bookRepository.delete(book);
    }

    private Book findBook(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
    }

}
