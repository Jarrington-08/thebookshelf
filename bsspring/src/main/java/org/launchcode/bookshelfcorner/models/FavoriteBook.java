package org.launchcode.bookshelfcorner.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
public class FavoriteBook extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @NotNull
    private String bookName;

    public FavoriteBook() {

    }

    public FavoriteBook(String aBookName) {
        super();
        this.bookName = aBookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return bookName;
    }
}
