package com.payu.management_service.controllers;


import com.payu.management_service.dtos.BookDto;
import com.payu.management_service.dtos.BookRequest;
import com.payu.management_service.dtos.PagedResponse;
import com.payu.management_service.enums.BookType;
import com.payu.management_service.services.interfaces.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private BookDto book;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        book = new BookDto();
        book.setName("Book One");
        book.setPublishDate(LocalDate.parse("2023-12-01"));
        book.setIsbn(12345);
        book.setPrice(BigDecimal.valueOf(29.99));
        book.setBookType(BookType.HARDCOVER);
    }

    @Test
    public void createBookTest() throws Exception {
        when(bookService.createBook(any(BookRequest.class))).thenReturn(book);

        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Book One\",\"publishedDate\":\"2023-12-01\",\"isbn\":12345,\"price\":29.99,\"bookType\":\"HARDCOVER\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Book One"))
                .andExpect(jsonPath("$.isbn").value(12345));
    }

    @Test
    public void getBookTest() throws Exception {
        when(bookService.findById(any(Long.class))).thenReturn(book);

        mockMvc.perform(get("/api/v1/book")
                        .param("bookId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Book One"))
                .andExpect(jsonPath("$.isbn").value(12345));
    }

    @Test
    public void getAllBooksTest() throws Exception {
        PagedResponse pagedResponse = new PagedResponse();
        pagedResponse.setPageNumber(0);
        pagedResponse.setTotalPages(1);
        pagedResponse.setTotalElements(1);
        pagedResponse.setSize(10);

        when(bookService.findAll(anyInt(), anyInt())).thenReturn(pagedResponse);

        mockMvc.perform(get("/api/v1/book/all")
                        .param("pageNo", "0")
                        .param("noOfItems", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));
    }
}

