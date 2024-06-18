package com.security.spring_security.controller;

import com.security.spring_security.dto.UserDto;
import com.security.spring_security.entity.Product;
import com.security.spring_security.entity.User;
import com.security.spring_security.repository.UserRepository;
import jakarta.validation.Valid;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @PreAuthorize("hasAuthority('READ_ALL_USER')")
    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> users = userRepository.findAll();

        if (users != null && !users.isEmpty()) {
               return ResponseEntity.ok(
                        UserDto.fromEntity(users)
                );
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('SAVE_ONE_USER')")
    @PostMapping
    public ResponseEntity<User> createOne(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userRepository.save(user)
        );
    }

    @PreAuthorize("hasAuthority('EDIT_ONE_USER')")
    @PutMapping
    public ResponseEntity<User> editOne(@RequestBody @Valid User user) {
        User user1 = userRepository.findById(user.getId()).orElse(null);

        if (user1 != null) {
            return ResponseEntity.ok(
                    userRepository.save(user)
            );
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('DELETE_ONE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            userRepository.delete(user);
            return ResponseEntity.ok("User deleted");
        }

        return ResponseEntity.notFound().build();
    }

}
