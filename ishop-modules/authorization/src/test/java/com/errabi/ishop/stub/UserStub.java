package com.errabi.ishop.stub;

import com.errabi.common.model.AuthenticationRequestDto;
import com.errabi.ishop.entities.User;
import java.util.UUID;

public final class UserStub {

    public static AuthenticationRequestDto getLoginCredential(){
        return AuthenticationRequestDto.builder()
                .userName("admin")
                .password("admin")
                .build();
    }
    public static User getNewUser(){
        var user = getUser();
        user.setUsername("test");
        user.setId(UUID.randomUUID());
        return user;
    }
    public static User getUser(){
        var user =  User.builder()
                .password("admin")
                .username("admin")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .address("casablanca")
                .email("errabi.ayoube@gmail.com")
                .phone("0607806269")
                .firstName("errabi")
                .lastName("ayoub")
                .enabled(true)
                .build();

        user.setId(UUID.fromString("6e57e08a-7fd9-4886-ac79-1934ab06d015"));
        user.setVersion(0l);

        return user;
    }

}
