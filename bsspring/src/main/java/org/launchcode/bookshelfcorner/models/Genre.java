package org.launchcode.bookshelfcorner.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
public class Genre extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @NotNull
    private String genreName;

    public Genre() {

    }

    public Genre(String aGenreName) {
        super();
        this.genreName = aGenreName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return genreName;
    }
}
