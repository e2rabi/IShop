package com.errabi.ishop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users ;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority",joinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")},inverseJoinColumns = {
            @JoinColumn(name = "AUTHORITY_ID",referencedColumnName = "ID")
    })
    private Set<Authority> authorities = new HashSet<>();

    @JsonIgnore
    public Set<User> getUsers() {
        return new HashSet<User>(users);
    }

    public Set<Authority> getAuthorities() {
        return new HashSet<Authority>(authorities);
    }
}