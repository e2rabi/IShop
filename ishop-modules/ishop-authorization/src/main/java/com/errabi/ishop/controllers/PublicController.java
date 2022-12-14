package com.errabi.ishop.controllers;

import com.errabi.common.model.AuthenticationRequestDto;
import com.errabi.common.model.AuthenticationResponseDto;
import com.errabi.common.model.User2fResponseDto;
import com.errabi.common.model.UserDto;
import com.errabi.ishop.services.UserAuthenticationService;
import com.errabi.ishop.services.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@Tag(name = "Public api controller")
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;
    private final UserAuthenticationService userAuthenticationService;

    @PostMapping(value = "/login",produces = {"application/json"})
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userAuthenticationService.login(requestDto,request));
    }

    @PostMapping(value = "/verifyOtp",produces = {"application/json"})
    public ResponseEntity<Boolean> verifyOtp(@RequestBody AuthenticationRequestDto requestDto, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(userAuthenticationService.verifyOtp(requestDto,request));
    }
    @PostMapping(value = "/",produces = {"application/json"})
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto dto){
        return ResponseEntity.ok(userService.saveUser(dto));
    }
}
