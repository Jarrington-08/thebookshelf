package org.launchcode.bookshelfcorner.controllers;

import org.launchcode.bookshelfcorner.models.Book;
import org.launchcode.bookshelfcorner.models.Genre;
import org.launchcode.bookshelfcorner.models.User;
import org.launchcode.bookshelfcorner.repository.BookRepository;
import org.launchcode.bookshelfcorner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.valueOf;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserBookListController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/addBook/{userId}", consumes = "application/json;charset=UTF-8")
    //Do I need to use a book object? Don't forget to account for missing data (again, do I send default values from front end if missing?)
    //Do I just default to null? And then handle it again in the front end when nothing renders for that data?
    //Needs to return EntityResponse ultimately
    //Not sure if declaring a newBook variable is necessary. Can we just save the value passed to the function? Need to test this
    public String addBook(@PathVariable int userId, @RequestBody Book book) {
        Book newBook = new Book(book.getTitle(), book.getAuthors(), book.getIsbn(), book.getYearPublished(), book.getCoverUrl());
//        System.out.println(newBook.getAuthors());
//        System.out.println(newBook.getIsbn());
//        System.out.println(newBook.getYearPublished());
//        System.out.println(newBook.getCoverUrl());

        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.addBook(newBook);
        } else { }
        bookRepository.save(newBook);
            //This is why I need to refactor all API methods to return HttpResponse - Can't return a String
            //return "User not found";
        //Need to figure out why ArrayList is saved as "BLOB" in MySQL and how to work with it / fix it
        //Do I need to add book to user booklist manually? Does hibernate do that for me?
        //Do I need to add user to Book users? Does hibernate do that for me?
        //Seems like bookRequestDTO is unecessary because it won't reduce remote calls as far as I can see. DTO's seem to be intended for bundling data to reduce remote calls / method calls
        return "Book added";
    }

    @GetMapping("/getUserBookList/{userId}")
    public List<Book> getUserBookList(@PathVariable int userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = optUser.get();
            return user.getBookList();
        } else {
            //This is why I need to refactor all API methods to return HttpResponse - Can't return a String
            //return "User not found";
        }
        return null;
    }
}
