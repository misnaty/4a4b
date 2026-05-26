package com.meows.meows.controller;

/* template CRUD */

import com.meows.meows.dto.UserDTORequest;
import com.meows.meows.dto.UserDTOResponse;
import com.meows.meows.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTOResponse>> listarUsers(){
        return ResponseEntity.ok(userService.listarUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTOResponse> listarUsersById(@PathVariable Long id){
        return ResponseEntity.ok(userService.listarUsersById(id));
    }

    @PostMapping()
    public ResponseEntity<UserDTOResponse> createUsers(@RequestBody UserDTORequest userRequest){
        return ResponseEntity.ok(userService.createUsers(userRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTOResponse> updateUsers(@PathVariable Long id, @RequestBody UserDTORequest userRequest){
        return ResponseEntity.ok(userService.updateUsers(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable Long id){
        userService.deleteUsers(id);
        return ResponseEntity.noContent().build();
    }
}
