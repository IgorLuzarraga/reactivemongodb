package com.example.springboot.vaadin.reactivemongodb.frontend;

import com.example.springboot.vaadin.reactivemongodb.domain.Book;
import com.example.springboot.vaadin.reactivemongodb.services.BookService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpringComponent
@UIScope
public class BookEditor extends VerticalLayout implements KeyNotifier {

    private final BookService bookService;

    // The edited book
    private Optional<Book> editedBook = Optional.ofNullable(null);

    private final String TEXT_FIELD_TITLE = "Title";
    private final String TEXT_FIELD_AUTHOR = "Author";
    private final String TEXT_BUTTON_SAVE = "Save";
    private final String TEXT_BUTTON_CANCEL = "Cancel";
    private final String TEXT_BUTTON_DELETE = "Delete";

    // Fields to edit properties in Book entity
    TextField title = new TextField(TEXT_FIELD_TITLE);
    TextField author = new TextField(TEXT_FIELD_AUTHOR);

    Button save = new Button(TEXT_BUTTON_SAVE, VaadinIcon.CHECK.create());
    Button cancel = new Button(TEXT_BUTTON_CANCEL, VaadinIcon.CLOSE_CIRCLE_O.create());
    Button delete = new Button(TEXT_BUTTON_DELETE, VaadinIcon.TRASH.create());

    Binder<Book> binder = new Binder<>(Book.class);
    private IAddBooksToGrid addBooksToGrid;

    @Autowired
    public BookEditor(BookService bookService) {
        this.bookService = bookService;

        buildLayaout();
        addListeners();
    }

    public void editBook(Optional<Book> book) {
        book.ifPresent(book1 -> {
            editedBook = book;

            // Bind book fields to similarly named fields in BookEditor
            binder.setBean(book1);

            setVisible(true);
            title.focus();
        });
    }

    public void setAddBooksToGrid(IAddBooksToGrid e) {
        // When save, delete or cancel is clicked then implement the behaviour
        // injected by builStartLayaout.
        addBooksToGrid = e;
    }

    private void buildLayaout(){
        HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
        add(title, author, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        setVisible(false);
    }

    private void addListeners(){
        addKeyPressListener(Key.ENTER, e -> save());

        // bind action buttons to save, delete and cancel
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
    }

    public void save(){
        editedBook.ifPresent(book1 -> {
            bookService.save(book1);
            addBooksToGrid.onEdition();
        });
    }

    public void delete(){
        editedBook.ifPresent(book1 -> {
            bookService.delete(book1);
            addBooksToGrid.onEdition();
        });
    }

    public void cancel(){
        editedBook.ifPresent(book1 -> {
            addBooksToGrid.onEdition();
        });
    }
}
