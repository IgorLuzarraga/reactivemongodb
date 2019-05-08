package com.example.springboot.vaadin.reactivemongodb.services;

import com.example.springboot.vaadin.reactivemongodb.domain.Book;

import java.util.List;

public interface BookService {
    public List<Book> findAll();
    public List<Book> findByTitle(String title);
    public List<Book> findByAuthor(String author);
    public Book save(Book book);
    public void delete(Book book);
    public void deleteAll();
    public Long count();
}
