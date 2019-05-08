package com.example.springboot.vaadin.reactivemongodb.frontend;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@StyleSheet("frontend://styles/styles.css")
@Route
public class MainView extends VerticalLayout {

    private final static String HEADER_TEXT = "BOOK'S REPOSITORY WITH REACTIVE MONGODB";
    private final static String HEADER_THEME = "dark";
    private final static String FOOTER_THEME = "dark";
    private final static String CLASS_NAME = "main-view";
    private final BuildStarLayout buildStarLayout;
    private final BookEditor bookEditor;

    public MainView(BuildStarLayout buildStarLayout,
                    BookEditor bookEditor){
        this.buildStarLayout = buildStarLayout;
        this.bookEditor = bookEditor;

        setUp();
        buildHeader();
        buildLayout();
        buildFooter();
    }

    private void setUp(){
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName(CLASS_NAME);
    }

    private void buildHeader(){
        H1 header = new H1(HEADER_TEXT);
        header.getElement().getThemeList().add(HEADER_THEME);
        add(header);
    }

    private void buildFooter(){
        Footer footer = new Footer();
        footer.getElement().getThemeList().add(FOOTER_THEME);
        add(footer);
    }

    private void buildLayout(){
        add(buildStarLayout, bookEditor);
    }
}
