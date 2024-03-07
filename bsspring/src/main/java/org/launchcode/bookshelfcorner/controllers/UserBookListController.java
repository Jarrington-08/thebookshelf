package org.launchcode.bookshelfcorner.controllers;

import org.launchcode.bookshelfcorner.models.Book;
import org.launchcode.bookshelfcorner.models.Genre;
import org.launchcode.bookshelfcorner.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.valueOf;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserBookListController {

    @Autowired
    BookRepository bookRepository;

    @PostMapping(value = "/addBook/{userId}", consumes = "application/json;charset=UTF-8")
    //Do I need to use a book object? Don't forget to account for missing data (again, do I send default values from front end if missing?)
    //Do I just default to null? And then handle it again in the front end when nothing renders for that data?
    //Needs to return EntityResponse ultimately
    //Not sure if declaring a newBook variable is necessary. Can we just save the value passed to the function? Need to test this
    public String addBook(@PathVariable int userId, @RequestBody Book book) {
        Book newBook = new Book(book.getTitle(),
                book.getAuthors(),
                book.getIsbn(),
                book.getYearPublished(),
                book.getCoverURL());
        bookRepository.save(newBook);
        //Let's get this working then add in a bookRequestDTO
        //Seems like bookRequestDTO is unecessary because it won't reduce remote calls as far as I can see. DTO's seem to be intended for bundling data to reduce remote calls / method calls
        return "Book added";
    }
}
