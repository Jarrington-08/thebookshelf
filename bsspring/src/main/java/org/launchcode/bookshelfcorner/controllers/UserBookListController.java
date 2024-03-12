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
    public String addBook(@PathVariable int userId, @RequestBody Book book) {
        //I want  to refactor all this logic. 1. If user not present return error message 2. If book already saved in repo, then add user
        // to user list and add book to user bookList (regardless of whether it exists in user's list i.e. multiple copies of same book are possible
        //In future, may add a confirmation box with buttons to confirm adding duplicate
        //If book not in repo then add user and save to repo and add book to repo
        //Need to test what happens when a second user uploads same book. It should just add them to List user in book object. Manually??
        Optional<Book> optNewBook = bookRepository.findByIsbn(book.getIsbn());
        if (optNewBook.isPresent()){
            Book existingBook = optNewBook.get();
            Optional<User> optUser = userRepository.findById(userId);
            if (optUser.isPresent()) {
                User user = optUser.get();
                if (user.getBookList().contains(existingBook)) {
                    //This is where I need ro return HTTPresponse with message "Book already added"
                } else {
                    user.addBook(existingBook);
                    existingBook.addUser(user);
                }
        }
        book.setYearPublished(Integer.valueOf(book.getYearPublished().toString().substring(0,4)));
//        Book newBook = new Book(book.getTitle(), book.getAuthors(), book.getIsbn(), book.YearPublished(), book.getCoverUrl());


        }

        bookRepository.save(book);
            //This is why I need to refactor all API methods to return HttpResponse - Can't return a String
            //return "User not found"
            //Seems like bookRequestDTO is unecessary because it won't reduce remote calls as far as I can see. DTO's seem to be intended for bundling data to reduce remote calls / method calls
            //Eventually when I add an Author object (i.e. to be able to sort lists by author I will probably use a DTO to bundle book and author separately)
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
