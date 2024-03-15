package org.launchcode.bookshelfcorner.repository;

import org.launchcode.bookshelfcorner.models.Genre;
import org.launchcode.bookshelfcorner.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
    //Not sure this method does what I meant it to. This shouldn't be necessary if User objects have a getFavoriteBookList method
    //This whole thing needs to be refactored. The favorite book feature maybe should utilize Google Books api
    //The favorite genre thing maybe needs to be a fixed set of pre-defined genres? I was missing the @Repository annotation so not sure it was actually doing any relational database stuff anyway
    List<Genre> findByUserId(int user_id);

}
