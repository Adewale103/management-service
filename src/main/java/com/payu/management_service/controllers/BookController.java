package com.payu.management_service.controllers;

import com.payu.management_service.dtos.BookDto;
import com.payu.management_service.dtos.BookRequest;
import com.payu.management_service.dtos.PageDto;
import com.payu.management_service.dtos.PagedResponse;
import com.payu.management_service.services.interfaces.BookService;
import com.payu.management_service.utils.AppendableReferenceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
@Tag(name = "Book Management", description = "Endpoints for managing books")
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "", produces = "application/json")
    @Operation(summary = "Create a new book", description = "Adds a new book to the database.")
    public ResponseEntity<BookDto> createBook(
            @RequestBody BookRequest bookRequest) throws IOException {
        BookDto book = bookService.createBook(bookRequest);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping(value = "/search-book", produces = "application/json")
    @Operation(summary = "Search books", description = "Search books by criteria and pagination.")
    public ResponseEntity<PageDto> searchCriteriaForBook(
            @Parameter(description = "Search query string") @RequestParam(value = "q") String query,
            @Parameter(description = "Page number") @RequestParam int page,
            @Parameter(description = "Page size") @RequestParam int size) {
        PageDto pageDto = bookService.searchCriteria(query, page, size);
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "Get a book by ID", description = "Fetches a single book by its unique identifier.")
    public ResponseEntity<BookDto> getBook(
            @Parameter(description = "The book ID") @RequestParam String bookId) {
        long id = AppendableReferenceUtils.getIdFrom(bookId);
        BookDto book = bookService.findById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping(value = "", produces = "application/json")
    @Operation(summary = "Update a book", description = "Updates details of an existing book.")
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(
            @Parameter(description = "The book ID") @RequestParam String bookId,
            @RequestBody BookRequest bookRequest) throws IOException {
        long id = AppendableReferenceUtils.getIdFrom(bookId);
        bookService.updateBook(id, bookRequest);
    }

    @GetMapping(value = "/all", produces = "application/json")
    @Operation(summary = "Get all books", description = "Retrieve a paginated list of all books.")
    public ResponseEntity<PagedResponse> getAllBooks(
            @Parameter(description = "Page number (default: 0)") @RequestParam(value = "pageNo", required = false, defaultValue = "0") String pageNo,
            @Parameter(description = "Number of items per page (default: 1)") @RequestParam(value = "noOfItems", required = false, defaultValue = "1") String numberOfItems) {
        PagedResponse pagedResponse = bookService.findAll(Integer.parseInt(pageNo), Integer.parseInt(numberOfItems));
        return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "", produces = "application/json")
    @Operation(summary = "Delete a book", description = "Removes a book from the database by its ID.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBook(
            @Parameter(description = "The book ID") @RequestParam String bookId) {
        long id = AppendableReferenceUtils.getIdFrom(bookId);
        bookService.deleteBook(id);
    }
}
