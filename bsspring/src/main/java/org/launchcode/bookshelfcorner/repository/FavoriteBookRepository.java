package org.launchcode.bookshelfcorner.repository;

import org.launchcode.bookshelfcorner.models.FavoriteBook;
import org.launchcode.bookshelfcorner.models.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteBookRepository extends CrudRepository<FavoriteBook, Integer> {
    //Not sure this method does what I meant it to. This shouldn't be necessary if User objects have a getFavoriteBookList method
    List<FavoriteBook> findByUserId(int user_id);

}
