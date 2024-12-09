package com.payu.management_service.services;


import com.payu.management_service.dtos.BookDto;
import com.payu.management_service.dtos.BookRequest;
import com.payu.management_service.enums.BookType;
import com.payu.management_service.exception.GenericException;
import com.payu.management_service.models.Book;
import com.payu.management_service.repositories.BookRepository;
import com.payu.management_service.services.implementations.BookServiceImpl;
import com.payu.management_service.services.interfaces.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void createBook_success() throws IOException {
        BookRequest bookRequest = new BookRequest("Book One", "2023-12-01", 12345, BigDecimal.valueOf(29.99), BookType.HARDCOVER);
        when(bookRepository.findBookByIsbn(bookRequest.getIsbn())).thenReturn(Optional.empty());

        Book savedBook = new Book("Book One", 12345, LocalDate.parse("2023-12-01"), BigDecimal.valueOf(29.99), BookType.HARDCOVER, null, null);
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        BookDto createdBook = bookService.createBook(bookRequest);

        assertNotNull(createdBook);
        assertEquals("Book One", createdBook.getName());
        assertEquals(12345, createdBook.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_duplicateIsbn_throwsException() {
        BookRequest bookRequest = new BookRequest("Book One", "2023-12-01", 12345, BigDecimal.valueOf(29.99), BookType.HARDCOVER);
        Book existingBook = new Book("Book One", 12345, LocalDate.parse("2023-12-01"), BigDecimal.valueOf(29.99), BookType.HARDCOVER, null, null);

        when(bookRepository.findBookByIsbn(bookRequest.getIsbn())).thenReturn(Optional.of(existingBook));

        GenericException exception = assertThrows(GenericException.class, () -> bookService.createBook(bookRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void findById_success() {
        long bookId = 1L;
        Book book = new Book("Book One", 12345, LocalDate.parse("2023-12-01"), BigDecimal.valueOf(29.99), BookType.HARDCOVER, null, null);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.findById(bookId);

        assertNotNull(bookDto);
        assertEquals("Book One", bookDto.getName());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void findById_notFound_throwsException() {
        long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        GenericException exception = assertThrows(GenericException.class, () -> bookService.findById(bookId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(bookRepository, times(1)).findById(bookId);
    }
}
