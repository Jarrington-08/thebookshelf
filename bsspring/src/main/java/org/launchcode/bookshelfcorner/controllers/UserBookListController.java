package org.launchcode.bookshelfcorner.controllers;

import org.launchcode.bookshelfcorner.models.Genre;
import org.launchcode.bookshelfcorner.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserBookListController {

    @Autowired
    BookRepository bookRepository;

    @PostMapping("/addBook/{userId}")
    public String addBook(@PathVariable int userId, @RequestBody String title, String author, Genre genre, String ISBN) {
        return "Book added";
    }
}
