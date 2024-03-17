package org.launchcode.bookshelfcorner.repository;

import org.launchcode.bookshelfcorner.models.User;
import org.launchcode.bookshelfcorner.models.UserCopy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCopyRepository extends CrudRepository<UserCopy, Integer> {
    List<UserCopy> findByOwner(User owner);
}
