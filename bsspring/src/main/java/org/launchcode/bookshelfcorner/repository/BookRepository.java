package org.launchcode.bookshelfcorner.repository;

import org.launchcode.bookshelfcorner.models.Book;
import org.launchcode.bookshelfcorner.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//What would a join table look like for many to many users to books?
//Do we need just one book repo with ALL books?
//Should users have their own book repo? NO Because the api call can request only books associated with userID
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    Optional<Book> findByIsbn(long isbn);
}
