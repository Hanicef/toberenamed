package com.communityratesgames.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> RegisterNewUser(@RequestBody UserEntity userModel) {
        return userService.validateUserConstraints(userModel);
    }

    @GetMapping("/user")
    public ResponseEntity<String> getAllUsers() {
        List<UserEntity> resultList = userService.findAllUsers();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JsonFactory factory = new JsonFactory();
        try {
            JsonGenerator generator = factory.createGenerator(out);
            generator.writeStartArray();
            for (UserEntity entity : resultList) {
                generator.writeStartObject();
                generator.writeObjectField("registered", entity.getUserCreated() == null ? "¯\\_(ツ)_/¯" : entity.getUserCreated().toString());
                generator.writeObjectField("userName", entity.getUserName());
                generator.writeObjectField("firstName", entity.getFirstName());
                generator.writeObjectField("lastName", entity.getLastName());
                generator.writeEndObject();
            }
            generator.writeEndArray();
            generator.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("500  ᕕ( ⌓̈ )ᕗ ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(out.toString(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserEntity> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity(userService.findUserByUserName(username), HttpStatus.OK);
    }
}
