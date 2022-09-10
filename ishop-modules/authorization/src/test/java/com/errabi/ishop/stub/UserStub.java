package com.errabi.ishop.stub;

import com.errabi.ishop.entities.User;

import java.util.Date;
import java.sql.Timestamp;
import java.util.UUID;

public final class UserStub {

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
