package com.errabi.ishop.controllers;

import com.errabi.common.model.UserDto;
import com.errabi.ishop.entities.User;
import com.errabi.ishop.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/{userId}",produces = {"application/json"})
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") UUID userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
