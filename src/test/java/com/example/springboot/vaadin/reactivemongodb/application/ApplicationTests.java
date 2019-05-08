package com.example.springboot.vaadin.reactivemongodb.application;

import com.example.springboot.vaadin.reactivemongodb.Application;
import com.example.springboot.vaadin.reactivemongodb.domain.Book;
import com.example.springboot.vaadin.reactivemongodb.repositories.BookReactiveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ApplicationTests {
    private static final String AUTHOR = "Robert C. Martin";

    @Autowired
    private BookReactiveRepository bookReactiveRepository;

    @Test
    public void whenTheApplicationIsStarted_ThenFillOutComponentsWithData() {
        long bookCount =  bookReactiveRepository.count().block();
        long expectedbookCount = 6;

        assertEquals(expectedbookCount, bookCount);
    }

    @Test
    public void whenTheApplicationIsStarted_ThenFindTwoUncleBobBooks() {
        List<Book> bookList = new ArrayList<>();

        bookReactiveRepository.findByAuthorStartsWithIgnoreCase(AUTHOR)
        .subscribe(bookList::add);

        goToSleep(3000);

        assertEquals(2, bookList.size());
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

