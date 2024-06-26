package org.launchcode.bookshelfcorner.controllers;

import jakarta.validation.Valid;

import org.apache.coyote.Response;
import org.launchcode.bookshelfcorner.models.ConfirmationToken;
import org.launchcode.bookshelfcorner.repository.ConfirmationTokenRepository;
import org.launchcode.bookshelfcorner.models.User;
import org.launchcode.bookshelfcorner.models.dto.ChangePasswordRequestDTO;
import org.launchcode.bookshelfcorner.repository.UserRepository;
import org.launchcode.bookshelfcorner.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.web.bind.annotation.*;

import org.launchcode.bookshelfcorner.models.dto.LoginRequestDTO;
import org.launchcode.bookshelfcorner.models.dto.LoginResponseDTO;
import org.launchcode.bookshelfcorner.models.dto.RegisterRequestDTO;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> performRegister(@Valid @RequestBody RegisterRequestDTO registerRequestDTO){

        Optional<User> optUserUsername = userRepository.findByUsername(registerRequestDTO.getUsername().trim());
        Optional<User> optUserEmail = userRepository.findByEmail(registerRequestDTO.getEmail().trim());

        if (optUserUsername.isPresent()) {
           return ResponseEntity.badRequest().body(new LoginResponseDTO(null,"Username unavailable"));
        } else if (optUserEmail.isPresent()) {
            return ResponseEntity.badRequest().body(new LoginResponseDTO(null,"Email already registered"));
        } else {
            User newUser = new User(registerRequestDTO.getUsername().trim(), registerRequestDTO.getEmail().trim(), registerRequestDTO.getPassword());
            userRepository.save(newUser);

            ConfirmationToken confirmationToken = new ConfirmationToken(newUser);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(newUser.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
            emailService.sendEmail(mailMessage);
            return ResponseEntity.ok(new LoginResponseDTO(newUser.getId(), "Success"));
        }
    }

    //Need to test this. Email is not sending for some reason
    @PostMapping("/resendConfirmationEmail/{userId}")
    public ResponseEntity<?> resendConfirmationEmail(@PathVariable int userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User newUser = optionalUser.get();
            ConfirmationToken confirmationToken = confirmationTokenRepository.findByUserId(userId);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(newUser.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
//                    +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
            emailService.sendEmail(mailMessage);
            return ResponseEntity.ok( "Success");

        }


        return ResponseEntity.badRequest().body("User not found");

    }

    @GetMapping("/verifyUser/{userId}")
    public Boolean verifyUser(@PathVariable int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User newUser = optionalUser.get();
            return newUser.isEnabled();
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> performLogin(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        Optional<User> optUser = userRepository.findByEmail(loginRequestDTO.getEmail().trim());
        if (optUser.isPresent()) {
            User user = optUser.get();
            if (user.isEnabled()) {
                if (user.isMatchingPassword(loginRequestDTO.getPassword())) {
                    return ResponseEntity.ok(new LoginResponseDTO(user.getId(), "Success !"));
                }
            } else {
                return ResponseEntity.badRequest().body((new LoginResponseDTO(null,"Please click the verification link in your email.")));
            }

        }
        return ResponseEntity.badRequest().body(new LoginResponseDTO(null,"Invalid email or password"));
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            Optional<User> optUser = userRepository.findByEmail(token.getUserEntity().getEmail());
            if (optUser.isPresent()) {
                User user = optUser.get();
                user.setEnabled(true);
                userRepository.save(user);
                return ResponseEntity.ok("Email verified successfully!");
            }
            return ResponseEntity.badRequest().body("Error: Couldn't verify email");
        }
        return ResponseEntity.badRequest().body("Invalid registration token. Please try again");
    }

    @PutMapping("/changePassword")
    public ResponseEntity<LoginResponseDTO> changePassword(@Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        Optional<User> optUser = userRepository.findById(changePasswordRequestDTO.getUserId());
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setPwHash(changePasswordRequestDTO.getPassword());
            userRepository.save(user);
            return ResponseEntity.ok(new LoginResponseDTO(user.getId(), "Success !"));
        }
        return ResponseEntity.ok(new LoginResponseDTO(null,"Password changed successfully"));
    }
}
