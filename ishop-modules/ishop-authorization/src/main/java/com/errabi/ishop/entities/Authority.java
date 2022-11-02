package com.errabi.ishop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority extends BaseEntity {
    @NotBlank
    private String permission ;

    @Singular
    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

    @JsonIgnore
    public Set<Role> getRoles() {
        return new HashSet<>(roles);
    }
}