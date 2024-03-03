package org.launchcode.bookshelfcorner.controllers;

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

    @PostMapping("/addBook/{userId}")
    public String addBook(@PathVariable int userId, @RequestBody String title, String author, Integer ISBN, Integer yearPublished, String coverUrl) {
//        String a = valueOf(yearPublished);
        System.out.println(title + author + ISBN + yearPublished + coverUrl);
        return "Book added";
    }
}
