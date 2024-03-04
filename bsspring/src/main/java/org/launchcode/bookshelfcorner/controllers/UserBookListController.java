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
    //This is treating the JSON object as the "title" parameter and treating the others as null. Why? Do I need to declare a different data type besides String? How do I package it in Java? Need to research POST JSON objects
    //Do I need to use a book object? Don't forget to account for missing data (again, do I send default values from front end if missing?)
    public String addBook(@PathVariable int userId, @RequestBody String title, String author, Integer ISBN, Integer yearPublished, String coverUrl) {
//        String a = valueOf(yearPublished);
        System.out.println(title);
        return "Book added";
    }
}
