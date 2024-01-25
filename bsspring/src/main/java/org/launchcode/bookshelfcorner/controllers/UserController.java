package org.launchcode.bookshelfcorner.controllers;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.launchcode.bookshelfcorner.models.FavoriteBook;
import org.launchcode.bookshelfcorner.models.Genre;
import org.launchcode.bookshelfcorner.models.User;
import org.launchcode.bookshelfcorner.repository.FavoriteBookRepository;
import org.launchcode.bookshelfcorner.repository.GenreRepository;
import org.launchcode.bookshelfcorner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FavoriteBookRepository favoriteBookRepository;

    //Users can add genres to their profiles
   @PostMapping("/addGenre/{userId}")
   public String saveGenre(@PathVariable int userId, @RequestBody String genre) {
       Optional optUser = userRepository.findById(userId);
       if (optUser.isPresent()) {
           User user = (User) optUser.get();
           Genre newGenre = new Genre(genre);
           newGenre.setUser(user);
           user.addGenre(newGenre);
           genreRepository.save(newGenre);
       } else {
           return "User not found";
       }
       return "Genre added";
   }

    //Users can see their personal favorite genres
    @GetMapping(value = "/getGenres/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Genre> getGenres(@PathVariable int userId) {

        return genreRepository.findByUserId(userId);
    }

    //Users can delete a particular genre from their profile
    @DeleteMapping("/deleteGenre/{genreId}")
    public String deleteGenre(@PathVariable int genreId) {
        Optional optGenre = genreRepository.findById(genreId);
        if (optGenre.isPresent()) {
            Genre genre = (Genre) optGenre.get();
            genreRepository.deleteById(genreId);
        }
        return "Genre deleted";
   }

   //Users can update a particular genre in their profile
   @PutMapping("/updateGenre/{genreId}")
   public String updateGenre(@PathVariable int genreId, @RequestBody String genreName) {
       Optional<Genre> optGenre = genreRepository.findById(genreId);
       if (optGenre.isPresent()) {
           Genre genre = (Genre) optGenre.get();
           genre.setGenreName(genreName);
           genreRepository.save(genre);
       } else {
           return "Genre not found";
       }
       return "Genre updated";
   }

    @PostMapping("/addFavoriteBook/{userId}")
    public String saveFavoriteBook(@PathVariable int userId, @RequestBody String book) {
        Optional optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            FavoriteBook newBook = new FavoriteBook(book);
            newBook.setUser(user);
            user.addFavoriteBook(newBook);
            favoriteBookRepository.save(newBook);
        } else {
            return "User not found";
        }
        return "Book added";
    }

    //Users can see their personal favorite genres
    @GetMapping(value = "/getFavoriteBooks/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<FavoriteBook> getBooks(@PathVariable int userId) {
        return favoriteBookRepository.findByUserId(userId);
    }

    //Users can delete a particular genre from their profile
    @DeleteMapping("/deleteFavoriteBook/{bookId}")
    public String deleteFavoriteBook(@PathVariable int bookId) {
        Optional optBook = favoriteBookRepository.findById(bookId);
        if (optBook.isPresent()) {
            FavoriteBook favoriteBook = (FavoriteBook) optBook.get();
            favoriteBookRepository.deleteById(bookId);
        }
        return "Genre deleted";
    }

    //Users can update a particular genre in their profile
    @PutMapping("/updateFavoriteBook/{bookId}")
    public String updateFavoriteBook(@PathVariable int bookId, @RequestBody String bookName) {
        Optional<FavoriteBook> optBook = favoriteBookRepository.findById(bookId);
        if (optBook.isPresent()) {
            FavoriteBook book = (FavoriteBook) optBook.get();
            book.setBookName(bookName);
            favoriteBookRepository.save(book);
        } else {
            return "Genre not found";
        }
        return "Genre updated";
    }

   //Users can see their username in their profile
    @ResponseBody
    @GetMapping("/getUsername/{userId}")
    public String getUsername(@PathVariable int userId) {
        Optional optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            return user.getUsername();
        } else {
            return "User not found";
        }
    }

    @PutMapping("/updateUsername/{userId}")
    public String updateUsername(@PathVariable int userId, @RequestBody String username) {
        Optional optUser = userRepository.findById(userId);
        Optional optNewUsername = userRepository.findByUsername(username);

        if (optNewUsername.isPresent()) {
            return "Username unavailable";
        } else {
            User user = (User) optUser.get();
            user.setUsername(username);
            userRepository.save(user);
            return "Username updated successfully";
        }
    }

    @PostMapping("/updateAboutMe/{userId}")
    public void updateAboutMe(@PathVariable int userId, @RequestBody String aboutMe) {
        Optional optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            user.setAboutMe(aboutMe);
            userRepository.save(user);
        }
    }

    @ResponseBody
    @GetMapping("/getAboutMe/{userId}")
    public String getAboutMe(@PathVariable int userId) {
        Optional optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            return user.getAboutMe();
        } else {
            return "User not found";
        }
    }

    @PostMapping("/updateLocation/{userId}")
    public void updateLocation(@PathVariable int userId, @RequestBody String location) {
        Optional optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            user.setLocation(location);
            userRepository.save(user);
        }
    }

    @ResponseBody
    @GetMapping("/getLocation/{userId}")
    public String getLocation(@PathVariable int userId) {
        Optional optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            return user.getLocation();
        } else {
            return "User not found";
        }
    }

    @PostMapping("/updateContactInfo/{userId}")
    public void updateContactInfo(@PathVariable int userId, @RequestBody String contactInfo) {
        Optional optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            user.setContactInfo(contactInfo);
            userRepository.save(user);
        }
    }

    @ResponseBody
    @GetMapping("/getContactInfo/{userId}")
    public String getContactInfo(@PathVariable int userId) {
        Optional optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            User user = (User) optUser.get();
            return user.getContactInfo();
        } else {
            return "User not found";
        }
    }



}

