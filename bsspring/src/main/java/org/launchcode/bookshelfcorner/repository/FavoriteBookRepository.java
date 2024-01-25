package org.launchcode.bookshelfcorner.repository;

import org.launchcode.bookshelfcorner.models.FavoriteBook;
import org.launchcode.bookshelfcorner.models.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoriteBookRepository extends CrudRepository<FavoriteBook, Integer> {
    List<FavoriteBook> findByUserId(int user_id);

}
