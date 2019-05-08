package com.example.springboot.vaadin.reactivemongodb.repositories;

import com.example.springboot.vaadin.reactivemongodb.domain.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactiveTests {

    @Autowired
    private BookReactiveRepository reacRepository;

    @Before
    public void setUp(){
        reacRepository.deleteAll().block();
    }

    @Test
    public void givenAuthor_whenFindByAuthor_thenFindBook() {
        reacRepository.save(new Book("Title", "Author")).block();
        Flux<Book> bookFlux = reacRepository.findByAuthor("Author");

        StepVerifier
                .create(bookFlux)
                .assertNext(book -> {
                    assertEquals("Author", book.getAuthor());
                    assertEquals("Title" , book.getTitle());
                    assertNotNull(book.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenSomeAuthors_whenFindByAuthor_thenFindBooks() {
        reacRepository.save(new Book("Title", "Author")).block();
        reacRepository.save(new Book("Title2", "Author")).block();
        reacRepository.save(new Book("Title Froga", "Author")).block();
        Flux<Book> bookFlux = reacRepository.findByAuthor("Author");

        StepVerifier
                .create(bookFlux)
                .assertNext(book -> {
                    assertEquals("Author", book.getAuthor());
                    assertEquals("Title" , book.getTitle());
                    assertNotNull(book.getId());
                })
                .assertNext(book -> {
                    assertEquals("Author", book.getAuthor());
                    assertEquals("Title2" , book.getTitle());
                    assertNotNull(book.getId());
                })
                .assertNext(book -> {
                    assertEquals("Author", book.getAuthor());
                    assertEquals("Title Froga" , book.getTitle());
                    assertNotNull(book.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenSomeBooks_whenFindByAll_thenFindAllBooks() {
        reacRepository.save(new Book("Title1", "Author1")).block();
        reacRepository.save(new Book("Title2", "Author2")).block();
        reacRepository.save(new Book("Title3", "Author3")).block();
        Flux<Book> bookFluxAll = reacRepository.findAll();

        StepVerifier
                .create(bookFluxAll)
                .assertNext(book -> {
                    assertEquals("Author1", book.getAuthor());
                    assertEquals("Title1" , book.getTitle());
                    assertNotNull(book.getId());
                })
                .assertNext(book -> {
                    assertEquals("Author2", book.getAuthor());
                    assertEquals("Title2" , book.getTitle());
                    assertNotNull(book.getId());
                })
                .assertNext(book -> {
                    assertEquals("Author3", book.getAuthor());
                    assertEquals("Title3" , book.getTitle());
                    assertNotNull(book.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenTitle_whenFindByTitle_thenFindBook() {
        reacRepository.save(new Book("Title", "Author")).block();
        Flux<Book> bookFlux = reacRepository.findByTitle("Title");

        StepVerifier
                .create(bookFlux)
                .assertNext(book -> {
                    assertEquals("Author", book.getAuthor());
                    assertEquals("Title" , book.getTitle());
                    assertNotNull(book.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenAccount_whenSave_thenSaveAccount() {
        Mono<Book> bookMono = reacRepository.save(new Book("Title", "Author"));

        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();
    }
}
