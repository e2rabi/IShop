package com.errabi.ishop.controllers;

import com.errabi.common.model.RoleDto;
import com.errabi.common.model.UserDto;
import com.errabi.ishop.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@Tag(name = "User controller")
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

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId")UUID userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }
    @PutMapping(value = "/{userId}",produces = {"application/json"})
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto dto,@PathVariable("userId")UUID userId){
        return ResponseEntity.ok(userService.updateUser(dto,userId));
    }
    @PutMapping("/{id}/roles")
    public ResponseEntity addRoleToUser(@PathVariable("id") UUID id,@RequestBody List<RoleDto> roles){
        userService.addRolesToUser(id,roles);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
