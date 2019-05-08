package com.example.springboot.vaadin.reactivemongodb.repositories;

import com.example.springboot.vaadin.reactivemongodb.domain.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookReactiveRepository
        extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByTitle(String title);
    Flux<Book> findByAuthor(String author);
    Flux<Book> findByTitleStartsWithIgnoreCase(String title);
    Flux<Book> findByAuthorStartsWithIgnoreCase(String author);
}