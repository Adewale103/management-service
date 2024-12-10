package com.payu.management_service.dtos;



import com.payu.management_service.enums.BookType;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String publishedDate;
    @Positive
    private int isbn;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "1.00", inclusive = true, message = "Price cannot be less than 1")
    private BigDecimal price;
    @NotNull(message = "Book type must not be null")
    private BookType bookType;

}
