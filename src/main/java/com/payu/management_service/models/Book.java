package com.payu.management_service.models;

import com.payu.management_service.enums.BookType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Setter
@Getter
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends AppendableReference {
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, unique = true)
    @Min(value = 1, message = "Please enter an isbn")
    private int isbn;
    @Column(nullable = false)
    private LocalDate publishDate;
    @Column(nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private BookType bookType;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Book(String name, int isbn, LocalDate publishDate, BigDecimal price, BookType bookType) {
        this.name = name;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.price = price;
        this.bookType = bookType;
    }
}
