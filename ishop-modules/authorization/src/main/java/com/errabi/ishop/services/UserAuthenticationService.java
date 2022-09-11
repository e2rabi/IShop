package com.errabi.ishop.services;

import com.errabi.common.exception.IShopExceptionAuth;
import com.errabi.common.model.AuthenticationRequestDto;
import com.errabi.common.model.AuthenticationResponseDto;
import com.errabi.ishop.entities.User;
import com.errabi.ishop.repositories.LoginFailureRepository;
import com.errabi.ishop.repositories.UserRepository;
import com.errabi.ishop.security.task.LoginFailure;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.errabi.common.utils.IShopCodeError.USER_LOCKED_ERROR_CODE;
import static com.errabi.common.utils.IShopCodeError.USER_NOT_FOUND_ERROR_CODE;
import static com.errabi.common.utils.IShopMessageError.INVALID_USERNAME_OR_PASSWORD;
import static com.errabi.common.utils.IShopMessageError.USER_ACCOUNT_LOCKED;

/**
 * User security operations like Login,logout operations on {@link AuthenticationResponseDto}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

    private final TokenService tokens;
    private final UserService userService;
    private final LoginFailureRepository loginFailureRepository ;
    private final UserRepository userRepository;

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param requestDto
     * @return an {@link AuthenticationResponseDto} of a user when login succeeds
     */
    public AuthenticationResponseDto login(AuthenticationRequestDto requestDto) throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, JOSEException {
        log.info("Attempted username : {}",requestDto.getUserName());
        var user =   userService
                .findByUsername(requestDto.getUserName());

        // todo move to validate function
        if(user.isPresent() && user.get().isAccountNonLocked()==false){
            throw  new IShopExceptionAuth(USER_LOCKED_ERROR_CODE,USER_ACCOUNT_LOCKED);
        }
        if(user.isPresent() && !verifyUserPassword(requestDto.getPassword(),user.get().getPassword()) ){
            attemptToLockUserAccount(user.get());
            throw  new IShopExceptionAuth(USER_NOT_FOUND_ERROR_CODE,INVALID_USERNAME_OR_PASSWORD);
        }else if(!user.isPresent()){
            throw  new IShopExceptionAuth(USER_NOT_FOUND_ERROR_CODE,INVALID_USERNAME_OR_PASSWORD);
        }
        // Verify otp code

        // Return JWT token
        return  AuthenticationResponseDto.builder()
                .jwt(tokens.newToken(user.get()))
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

    private boolean verifyUserPassword(String requestPassword,String userPassword){
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(requestPassword, userPassword);
    }
    public void logout(final User user) {
        // Nothing to do
    }
    private void attemptToLockUserAccount(User user) {
        log.debug("Verify user account status ...");
        var  loginFailure = LoginFailure.builder()
                                                    .userName(user.getUsername())
                                                    .user(user)
                                                    .build();
        loginFailureRepository.save(loginFailure);

        List<LoginFailure> failures = loginFailureRepository.findAllByUserAndCreatedDateIsAfter(user,
                Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
        // The number of failed attempts need to be externalized in a config file
        if(failures.size()>3){
            log.info("Locking user account ...");
            user.setAccountNonLocked(false);
            userRepository.save(user);

        }
    }

}
