package com.errabi.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    protected UUID id;
    private  String password;
    private  String username;
    private  String firstName ;
    private  String lastName ;
    private  String email ;
    private  String phone ;
    private  String address ;
    private  Long version;
    private  Timestamp createdDate;
    private  Timestamp lastModifiedDate;
    private Set<RoleDto> roles;

}
