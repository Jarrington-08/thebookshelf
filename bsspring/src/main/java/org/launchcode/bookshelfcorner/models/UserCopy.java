package org.launchcode.bookshelfcorner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class UserCopy extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    private boolean isAvailable;

    //What relationship is this? One to one isn't it?
    //If I save just the ID at the time of borrowing, then I should be able to create a Get Request that retrieves the user info
    private int borrowerId;

    private Date dateBorrowed;

    public UserCopy() {}

    public UserCopy(Book aBook, User aOwner) {
        super();
        this.book = aBook;
        this.owner = aOwner;
        this.isAvailable = true;
    }


}
