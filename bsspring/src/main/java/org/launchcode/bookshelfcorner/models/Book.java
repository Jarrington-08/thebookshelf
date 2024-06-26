package org.launchcode.bookshelfcorner.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book extends AbstractEntity {

    //This fixed Http 415 Unsupported Media type error with JSON char set UTF 8
    //Commenting out the ManyToMany relevant code as I refactor this into one to many
//    @JsonIgnore
//    @ManyToMany(mappedBy = "bookList")
//    private List<User> users = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    @JsonIgnore
//    private User user;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<UserCopy> userCopyList;


    @NotNull
    private String title;

    @NotNull
    private List<String> authors = new ArrayList<>();

    //At some point need to make this Genre object and @ManyToMany annotation?
//    @NotNull
//    private String genre;
    @NotNull
    private long isbn;

    @NotNull
    private Integer yearPublished;

    @NotNull
//    @Column (name = "cover_url")
    private String coverUrl;

//    private boolean isAvailable;

    public Book() {}

    public Book(String aTitle, List<String> aAuthors, long aIsbn, Integer aYearPublished, String aCoverUrl) {
        super();
        this.title = aTitle;
        this.authors = aAuthors;
        this.isbn = aIsbn;
        this.yearPublished = aYearPublished;
        this.coverUrl = aCoverUrl;
//        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }


//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    public void setAvailable(boolean available) {
//        isAvailable = available;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    //    public void setUsers() { this.users = users; }
//
//    public List<User> getUsers() {
//        return users;
//    }
//
//   public void addUser(User user) {
//        this.users.add(user);
//   }

   //delete user method needed

    //Will this even be needed? Since I will be retrieving data from MySQL?


    public List<UserCopy> getUserCopyList() {
        return userCopyList;
    }

    public void setUserCopyList(List<UserCopy> userCopyList) {
        this.userCopyList = userCopyList;
    }

    public void addUserCopy(UserCopy userCopy) {
        this.userCopyList.add(userCopy);
    }

    @Override
    public String toString() {
        return title;
    }
}
