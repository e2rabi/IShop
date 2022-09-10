package com.errabi.ishop.services;

import com.errabi.common.exception.IShopExceptionAuth;
import com.errabi.common.model.AuthenticationRequestDto;
import com.errabi.common.model.AuthenticationResponseDto;
import com.errabi.ishop.entities.User;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Optional;

import static com.errabi.common.utils.IShopErrors.USER_NOT_FOUND_ERROR_CODE;

/**
 * User security operations like Login,logout operations on {@link AuthenticationResponseDto}.
 */

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

    private final TokenService tokens;
    private final UserService userService;

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param requestDto
     * @return an {@link AuthenticationResponseDto} of a user when login succeeds
     */
    public AuthenticationResponseDto login(AuthenticationRequestDto requestDto) throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, JOSEException {
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
        // Nothing to do
    }


}
