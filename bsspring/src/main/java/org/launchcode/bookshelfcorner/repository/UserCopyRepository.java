package org.launchcode.bookshelfcorner.repository;

import org.launchcode.bookshelfcorner.models.UserCopy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCopyRepository extends CrudRepository<UserCopy, Integer> {

}
