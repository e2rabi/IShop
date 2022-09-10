package com.errabi.ishop.stub;

import com.errabi.common.model.RoleDto;

import java.util.UUID;

public final class RoleStub {

    public static RoleDto getRole(){
       return RoleDto.builder()
                       .id(UUID.randomUUID())
                       .name("demo")
                       .build();
    }
}
