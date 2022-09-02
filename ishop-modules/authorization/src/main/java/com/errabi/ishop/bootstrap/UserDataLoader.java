package com.errabi.ishop.bootstrap;

import com.errabi.ishop.entities.Authority;
import com.errabi.ishop.entities.Role;
import com.errabi.ishop.entities.User;
import com.errabi.ishop.repositories.AuthorityRepository;
import com.errabi.ishop.repositories.RoleRepository;
import com.errabi.ishop.repositories.UserRepository;
import com.errabi.ishop.services.TokenService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataLoader implements CommandLineRunner {
    private final UserRepository userRepository ;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository ;
    private final TokenService tokenService;
    @Override
    public void run(String... args) throws Exception {
      loadUser();
    }
    @Transactional
    public void loadUser() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, JOSEException, ParseException, UnrecoverableKeyException {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Authority createUser = authorityRepository.save(Authority.builder().permission("write_user").build());
        Authority deleteUser = authorityRepository.save(Authority.builder().permission("delete_user").build());
        Authority updateUser = authorityRepository.save(Authority.builder().permission("update_user").build());
        Authority readUser   = authorityRepository.save(Authority.builder().permission("read_user").build());

        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role userRole = roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        Role guestRole = roleRepository.save(Role.builder().name("ROLE_GUEST").build());

        adminRole.setAuthorities(Set.of(createUser,updateUser,deleteUser,readUser));
        userRole.setAuthorities(Set.of(updateUser,readUser));
        guestRole.setAuthorities(Set.of(readUser));

        roleRepository.saveAll(Arrays.asList(adminRole,userRole,guestRole));
        log.info("Creating users ....");
        var user1 = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .firstName("ayoub")
                .lastName("errabi")
                .email("errabi.ayoube@gmail.com")
                .phone("0607707989")
                .role(adminRole) // updated using the CascadType.Merge
                .build();

        userRepository.save(user1);

        var token =  tokenService.newToken(user1);
        log.info("token : {}",token);
        var claim = tokenService.verify(token);
        claim.forEach((k,v)->{
            log.info(" k : {}  v : {}",k,v);
        });
    }
}
