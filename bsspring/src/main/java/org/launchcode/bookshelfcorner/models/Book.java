package org.launchcode.bookshelfcorner.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book extends AbstractEntity {

    @NotNull
    private String title;

    @NotNull
    private String author;

    //At some point need to make this Genre object and @ManyToMany annotation?
    @NotNull
    private String genre;
    @NotNull
    private String ISBN;

    private boolean isAvailable;

    public Book() {}

    public Book(String aTitle, String anAuthor, String aGenre, String anISBN) {
        super();
        this.title = aTitle;
        this.author = anAuthor;
        this.genre = aGenre;
        this.ISBN = anISBN;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    //Will this even be needed? Since I will be retrieving data from MySQL?
    @Override
    public String toString() {
        return title;
    }
}
