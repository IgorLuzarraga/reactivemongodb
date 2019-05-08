package com.example.springboot.vaadin.reactivemongodb.services;

import com.example.springboot.vaadin.reactivemongodb.domain.Book;
import com.example.springboot.vaadin.reactivemongodb.repositories.BookReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    BookReactiveRepository bookReactiveRepository;
    private Mono<Book> bookMono;
    private Mono<Void> voidMono;

    @Autowired
    public BookServiceImpl(BookReactiveRepository bookReactiveRepository,
                      Mono<Book> bookMono,
                      Mono<Void> voidMono) {

        this.bookReactiveRepository = bookReactiveRepository;
        this.bookMono = bookMono;
        this.voidMono = voidMono;
    }

    @Override
    public List<Book> findAll(){
        List<Book> bookList = new ArrayList<>();

        bookReactiveRepository
                .findAll()
                .subscribe(bookList::add);

        goToSleep(500);

        return bookList;
    }

    @Override
    public List<Book> findByTitle(String title){
        List<Book> bookList = new ArrayList<>();

        bookReactiveRepository
                .findByTitleStartsWithIgnoreCase(title)
                .subscribe(book -> {
                    bookList.add(book);
                });

        goToSleep(500);

        return bookList;
    }

    @Override
    public List<Book>  findByAuthor(String author){
        List<Book> bookList = new ArrayList<>();

        bookReactiveRepository
                .findByAuthorStartsWithIgnoreCase(author)
                .subscribe(book -> {
                    bookList.add(book);
                });

        goToSleep(500);

        return bookList;
    }

    @Override
    public Book save(Book book){
        bookMono = bookReactiveRepository.save(book);
        return bookMono.block();
    }

    @Override
    public void delete(Book book){
        voidMono = bookReactiveRepository.delete(book);
        voidMono.block();
    }

    @Override
    public void deleteAll(){
        bookReactiveRepository.deleteAll().block();
    }

    @Override
    public Long count(){
        return bookReactiveRepository.count().block();
    }

    private void goToSleep(long millis){
        try
        {
            Thread.sleep(millis);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}
