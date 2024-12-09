package com.payu.management_service.specifications;

import com.payu.management_service.models.Book;

public class BookSpecs extends QueryToCriteria<Book> {
    public BookSpecs(String query) {
        super(query);
    }
}
