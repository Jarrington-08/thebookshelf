package org.launchcode.bookshelfcorner.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//    @ManyToMany(mappedBy = "eventParticipants")
//    private List<Event> events= new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Genre> genreList;

//    @OneToMany(mappedBy = "createdBy")
//    private List<Review> reviews= new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<FavoriteBook> favoriteBookList;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String pwHash;

    private boolean isEnabled;

    private String aboutMe;

    private String location;

    private String contactInfo;

//    @ManyToMany
//    private List<Book> booksToShare = new ArrayList<>();

    public User() {}

    public User(String aUsername, String anEmail, String password) {
        super();
        this.username = aUsername;
        this.pwHash = encoder.encode(password);
        this.email = anEmail;
        this.isEnabled = false;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() { return email; }

    public void setUsername(String username) { this.username = username; }

    public void setEmail(String email) { this.email = email; }

    public void setPwHash (String password) { this.pwHash = encoder.encode(password); }

//    public List<Book> getBooksToShare() { return booksToShare; }
//
//
//    public void setBooksToShare(List<Book> booksToShare) { this.booksToShare = booksToShare; }

    //Do we need another method to update booksToShare list?
    //Should this be another class by itself?


    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public void addGenre(Genre genre) {
        this.genreList.add(genre);
    }

    public void removeGenre(Genre genre) {
        this.genreList.remove(genre);
    }

    public void addFavoriteBook(FavoriteBook favoriteBook) {
        this.favoriteBookList.add(favoriteBook);
    }

    public void removeFavoriteBook(FavoriteBook favoriteBook) {
        this.favoriteBookList.remove(favoriteBook);
    }

    public List<FavoriteBook> getFavoriteBookList() {
        return favoriteBookList;
    }

    public void setFavoriteBookList(List<FavoriteBook> favoriteBookList) {
        this.favoriteBookList = favoriteBookList;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return username;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }
}

