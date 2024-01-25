package org.launchcode.bookshelfcorner.service;

import org.launchcode.bookshelfcorner.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> saveUser(User user);

    ResponseEntity<?> confirmEmail(String confirmationToken);
}