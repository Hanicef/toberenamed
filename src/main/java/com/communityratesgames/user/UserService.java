package com.communityratesgames.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements UserServiceInterface/*, UserDetailsService*/ {

    @Autowired
    private final UserRepository userRepository;

    private static Pattern validationPattern;

    static {
        validationPattern = Pattern.compile("^[a-zA-Z0-9!#$*+-<>^~_]+$");
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createNewUser(UserEntity user) {
        user.setUserCreated(Timestamp.from(Instant.now()));
        user.setRole("user");
        return userRepository.save(user);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity findUserById(Long id) {
        return userRepository.getOne(id);
    }

    public UserEntity findUserByUserName(String username) {
        return userRepository.findUserByUserName(username);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

	public ResponseEntity<String> validateUserConstraints(UserEntity user){
        Matcher matches = validationPattern.matcher("");
        String username = user.getUserName();
        String password = user.getPassword();
        if (!matches.reset(username).matches()){
            return new ResponseEntity<String>("Username not valid" +
                    "Username may only be alpha numeric or contain !#$*+-<>^~_", HttpStatus.NOT_ACCEPTABLE);
        }
        if (username.length() < 3 || username.length() > 30){
            return new ResponseEntity<String>("Username not valid" +
                    "Username must be between 3 to 30 characters", HttpStatus.NOT_ACCEPTABLE);
        }
        if (!matches.reset(password).matches()){
            return new ResponseEntity<String>("Password not valid" +
                    "Password may only be alpha numeric or contain !#$*+-<>^~_", HttpStatus.NOT_ACCEPTABLE);
        }
        if (username.length() < 8 || username.length() > 30){
            return new ResponseEntity<String>("Password not valid" +
                    "Password must be between 8 to 30 characters", HttpStatus.NOT_ACCEPTABLE);
        }
        if (username.equalsIgnoreCase(findUserByUserName(username).getUserName())){
            return new ResponseEntity<String>("Username already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getEmail().equals(findUserByUserName(username).getUserName())){
            return new ResponseEntity<String>("Username already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        createNewUser(user);
        return new ResponseEntity<String>("New user created", HttpStatus.OK);
    }
}
