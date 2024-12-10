package com.payu.management_service.dtos;

import com.payu.management_service.enums.BookType;
import com.payu.management_service.models.Book;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
public class BookDto {
    private String id;
    private String name;
    private LocalDate publishDate;
    private int isbn;
    private BigDecimal price;
    private BookType bookType;

    public static BookDto fromModel(Book book){
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        bookDto.setId(book.getReference());
        return bookDto;
    }

    public static BookDto toDto(Object o){
        return fromModel((Book) o);
    }

}
