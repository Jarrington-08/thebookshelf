package org.launchcode.bookshelfcorner.controllers;

import org.launchcode.bookshelfcorner.models.Book;
import org.launchcode.bookshelfcorner.models.Genre;
import org.launchcode.bookshelfcorner.models.User;
import org.launchcode.bookshelfcorner.models.UserCopy;
import org.launchcode.bookshelfcorner.repository.BookRepository;
import org.launchcode.bookshelfcorner.repository.UserCopyRepository;
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

    @Autowired
    UserCopyRepository userCopyRepository;

    @PostMapping(value = "/addBook/{userId}", consumes = "application/json;charset=UTF-8")
    //Do I need to use a book object? Don't forget to account for missing data (again, do I send default values from front end if missing?)
    //Do I just default to null? And then handle it again in the front end when nothing renders for that data?
    //Needs to return EntityResponse ultimately
    public String addBook(@PathVariable int userId, @RequestBody Book book) {
        //Latest error message when trying to add Sorcerer's Stone with full date saying "Cannot deserialize value of type `java.lang.Integer` from String "2015-12-08": not a valid `java.lang.Integer` value]"

        Optional<Book> optNewBook = bookRepository.findByIsbn(book.getIsbn());
        Optional<User> optUser = userRepository.findById(userId);

        if (optUser.isEmpty()) {
            return "User does not exist";
        }
        User user = optUser.get();
        if (optNewBook.isPresent()) {
            Book existingBook = optNewBook.get();
            //!!!This is not currently working - need to fix - !!!
            //This logic allows for users to add multiple copies of the same volume if they possess them.
            //In the future, it would probably be good to use a pop to confirm adding an existing book
            UserCopy userCopy = new UserCopy(book, user);
            userCopyRepository.save(userCopy);
            //These lines don't seem necessary. It doesn't seem like they are doing anything. Rewatch the LC java videos to clarify
//            user.addUserCopy(userCopy);
//            existingBook.addUserCopy(userCopy);
        } else {
            //Will this work for book.adduserCopy? I feel like I might need to create a Book object first (but the method @requestbody requires a book object
            bookRepository.save(book);
            UserCopy userCopy = new UserCopy(book, user);
            userCopyRepository.save(userCopy);
//            user.addUserCopy(userCopy);
//            book.addUserCopy(userCopy);
        }
            //This is why I need to refactor all API methods to return HttpResponse - Can't return a String
            //Seems like bookRequestDTO is unecessary because it won't reduce remote calls as far as I can see. DTO's seem to be intended for bundling data to reduce remote calls / method calls
            //Eventually when I add an Author object (i.e. to be able to sort lists by author I will probably use a DTO to bundle book and author separately)
        return "Book added";
    }

    @GetMapping("/getUserBookList/{userId}")
    public Iterable <UserCopy> getUserBookList(@PathVariable int userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = optUser.get();
            return userCopyRepository.findByOwner(user);
        } else {
            //This is why I need to refactor all API methods to return HttpResponse - Can't return a String
            //return "User not found";
        }
        return null;
    }
}
