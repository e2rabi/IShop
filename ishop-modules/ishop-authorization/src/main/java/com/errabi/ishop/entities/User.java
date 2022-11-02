package com.errabi.ishop.entities;

import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@ToString
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails, CredentialsContainer {

    @ToString.Exclude
    private  String password;
    private  String username;
    private  String firstName ;
    private  String lastName ;
    private  String email ;
    private  String phone ;
    private  String address ;

    @Builder.Default
    private  boolean accountNonExpired = true;

    @Builder.Default
    private  boolean accountNonLocked = true;

    @Builder.Default
    private  boolean credentialsNonExpired = true;

    @Builder.Default
    private  boolean enabled = true ;

    @Builder.Default
    private Boolean useGoogle2Fa  = false ;

    private String google2faSecret ;

    @Transient
    private Boolean google2faRequired = false;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")},inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")
    })
    private Set<Role> roles = new HashSet<>();


    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(authority-> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());
    }

    public Set<Role> getRoles() {
        return new HashSet<Role>(roles);
    }
}