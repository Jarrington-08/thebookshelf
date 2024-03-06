package org.launchcode.bookshelfcorner.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

@Entity
public class Book extends AbstractEntity {

    @NotNull
    private String title;

    @NotNull
    private ArrayList<String> authors;

    //At some point need to make this Genre object and @ManyToMany annotation?
//    @NotNull
//    private String genre;
    @NotNull
    private long isbn;

    @NotNull
    private Integer yearPublished;

    @NotNull
    private String coverURL;

    private boolean isAvailable;

    public Book() {}

    public Book(String aTitle, ArrayList<String> aAuthors, long aIsbn, Integer aYearPublished, String aCoverURL) {
        super();
        this.title = aTitle;
        this.authors = aAuthors;
        this.isbn = aIsbn;
        this.yearPublished = aYearPublished;
        this.coverURL = aCoverURL;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    //    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }


    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public Integer getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Integer yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
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
