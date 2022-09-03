package com.errabi.ishop.services;

import com.errabi.common.exception.IShopException;
import com.errabi.common.exception.IShopExceptionAuth;
import com.errabi.common.model.AuthenticationRequestDto;
import com.errabi.common.model.AuthenticationResponseDto;
import com.errabi.ishop.entities.User;
import com.errabi.ishop.services.mappers.UserMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import static com.errabi.common.utils.IShopErrors.USER_NOT_FOUND_ERROR_CODE;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {
    @NonNull
    private final TokenService tokens;
    @NonNull
    private final UserService userService;

    private final UserMapper mapper ;
    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param requestDto
     * @return an {@link AuthenticationResponseDto} of a user when login succeeds
     */
    public AuthenticationResponseDto login(AuthenticationRequestDto requestDto) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user =   userService
                .findByUsername(requestDto.getUserName())
                .filter(u -> passwordEncoder.matches(requestDto.getPassword(), u.getPassword()))
                .orElseThrow(() -> new IShopExceptionAuth(USER_NOT_FOUND_ERROR_CODE,"invalid login and/or password"));
        // Verify otp code

        // Return JWT token
        return  AuthenticationResponseDto.builder()
                .jwt(tokens.newToken(user))
                .build();
    }
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
