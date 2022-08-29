package com.errabi.ishop.services;

import com.errabi.ishop.entities.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {
    @NonNull
    private final TokenService tokens;
    @NonNull
    private final UserService userService;


    public Optional<User> findByToken(final String token) {
        try {
            return Optional
                    .of(tokens.verify(token))
                    .map(map -> map.get("userName"))
                    .flatMap(userService::findByUsername);
        }catch (Exception ex){
            return Optional.empty();
        }
    }

    public void logout(final User user) {
        // Nothing to doy
    }


}
