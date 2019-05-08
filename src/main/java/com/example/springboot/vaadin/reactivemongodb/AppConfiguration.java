package com.example.springboot.vaadin.reactivemongodb;

import com.example.springboot.vaadin.reactivemongodb.domain.Book;
import com.example.springboot.vaadin.reactivemongodb.repositories.BookReactiveRepository;
import com.example.springboot.vaadin.reactivemongodb.services.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class AppConfiguration {
    @Bean
    public CommandLineRunner loadData(BookService bookService) {
        return (args) -> {
            bookService.deleteAll();

            // save some books
            bookService.save(new Book("BDD IN ACTION", "John Ferguson Smart"));
            bookService.save(new Book("Clean Code", "Robert C. Martin"));
            bookService.save(new Book("Clean Architecture", "Robert C. Martin"));
            bookService.save(new Book("Redis IN ACTION", "Josiah L. Carlson"));
            bookService.save(new Book("Spring Boot IN ACTION", "Craig Walls"));
            bookService.save(new Book("Functional Programming in Java", "Venkat Subramaniam"));
        };
    }

    @Bean
    public Mono<Book> getMonoBook(){
        Book book = new Book("TITLE", "AUTHOR");
        return Mono.just(book);
    }

    @Bean
    public Mono<Void> getMonoVoid(){
        return Mono.empty();
    }

}
