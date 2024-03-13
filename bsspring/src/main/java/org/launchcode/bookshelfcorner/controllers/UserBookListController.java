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
        //Need to test what happens when a second user uploads same book. It should just add them to List user in book object. Manually??
        //Latest error message when trying to add Sorcerer's Stone with full date saying "Cannot deserialize value of type `java.lang.Integer` from String "2015-12-08": not a valid `java.lang.Integer` value]"
        //Need to address this after solving the fact that the book isn't saving in the join table now (seperate issue)
        book.setYearPublished(Integer.valueOf(book.getYearPublished().toString().substring(0,4)));

        Optional<Book> optNewBook = bookRepository.findByIsbn(book.getIsbn());
        Optional<User> optUser = userRepository.findById(userId);

        if (optUser.isEmpty()) {
            return "User does not exist";
        }
        User user = optUser.get();
        if (optNewBook.isPresent()) {
            //!!!This is not currently working - need to fix - !!!
            //This logic allows for users to add multiple copies of the same volume if they possess them.
            //In the future, it would probably be good to use a pop to confirm adding an existing book
            user.addBook(book);
            //This logic allows for users who add an existing volume to their personal list to be added to list of users possessing that volume
            if (!book.getUsers().contains(user)) {
                book.addUser(user);
            }
        } else {
            user.addBook(book);
            book.addUser(user);
            bookRepository.save(book);

        }
            //This is why I need to refactor all API methods to return HttpResponse - Can't return a String
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
