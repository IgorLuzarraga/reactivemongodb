package com.example.springboot.vaadin.reactivemongodb.frontend;

import com.example.springboot.vaadin.reactivemongodb.Application;
import com.example.springboot.vaadin.reactivemongodb.domain.Book;
import com.example.springboot.vaadin.reactivemongodb.services.BookService;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BuildStartLayoutTests {

    BuildStarLayout buildStarLayout;
    BookEditor bookEditor;
    @Autowired
    BookService bookService;

    private final String GRID_COLUMN_TWO = "title";
    private final String GRID_COLUMN_THREE = "author";
    private final String TITLE = "Java 8 Lambdas";
    private final String AUTHOR = "Richard Warburton";

    @Before
    public void setup() {
        //Clean the DB to start fresh
        bookService.deleteAll();

        bookEditor = new BookEditor(bookService);

        buildStarLayout = new BuildStarLayout(
                bookEditor,
                bookService);

    }

    @Test
    public void givenTheBookEditor_WhenInitialize_ThenShouldBeInvisible(){
        assertEquals(false, bookEditor.isVisible());
    }

    @Test
    public void givenABookInTheGridWhenSelectTheBook_ThenTheBookEditorShoulBeVisible(){

        // given book saved
        fillBookData(TITLE, AUTHOR);
        bookEditor.save();

        // when book from the grid is selected
        buildStarLayout.getGrid()
                .select(getBooksInGrid().get(0));

        // then the book's editor is visible
        assertEquals(true, bookEditor.isVisible());
    }

    @Test
    public void givenTheGRid_ThenTheNumberOfColumnsShouldBeTwo() {
        assertEquals(2, buildStarLayout.getGrid().getColumns().size());
    }

    @Test
    public void givenRepository_ThenGridShouldHaveSameNumberOfBooks() {
        // number of books in the repository
        long bookCount =  bookService.count();

        // the repository and the grid should have the same
        // number of books.
        assertEquals((int)bookCount, getBooksInGrid().size());
    }

    @Test
    public void whenBookSaved_ThenBookShouldBeInGrid(){
        long initialBookCount =  bookService.count();

        // when
        fillBookData(TITLE, AUTHOR);
        bookEditor.save();

        // then
        int finalBookCount = (int)initialBookCount + 1;
        assertEquals(finalBookCount, getBooksInGrid().size());

        Book book = getBooksInGrid().get(getBooksInGrid().size() -1);
        assertEquals(TITLE, book.getTitle());
        assertEquals(AUTHOR, book.getAuthor());
    }

    @Test
    public void givenBookSaved_WhenFilterThatBook_ThenBookShouldBeInGrid(){

        // given book saved
        fillBookData(TITLE, AUTHOR);
        bookEditor.save();

        // when filter by Book´s author
        buildStarLayout.addBooksByAuthorToGrid(AUTHOR);

        // then book should be in the grid
        assertEquals(1, getBooksInGrid().size());
        Book book = getBooksInGrid().get(getBooksInGrid().size() -1);
        assertEquals(TITLE, book.getTitle());
        assertEquals(AUTHOR, book.getAuthor());
    }

    private List<Book> getBooksInGrid() {
        ListDataProvider<Book> ldp = (ListDataProvider) buildStarLayout.getGrid().getDataProvider();
        return new ArrayList<>(ldp.getItems());
    }

    private void fillBookData(String title,
                              String author) {
        //bookEditor.title.setValue(title);
        //bookEditor.author.setValue(author);
        bookEditor.editBook(Optional.ofNullable(new Book(title, author)));
    }
}
