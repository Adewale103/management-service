package com.payu.management_service;

import com.payu.management_service.enums.BookType;
import com.payu.management_service.models.Book;
import com.payu.management_service.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class ManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(BookRepository bookRepository) {
		return args -> {
			bookRepository.save(new Book("Book One", 12345, LocalDate.now(), BigDecimal.valueOf(29.99), BookType.EBOOK));
			bookRepository.save(new Book("Book Two", 67890, LocalDate.now(), BigDecimal.valueOf(39.99), BookType.HARDCOVER));
		};
	}
}
