package com.errabi.ishop.services;

import com.errabi.common.exception.IShopException;
import com.errabi.common.exception.IShopExceptionAuth;
import com.errabi.common.model.AuthenticationRequestDto;
import com.errabi.common.model.AuthenticationResponseDto;
import com.errabi.ishop.entities.LoginSuccess;
import com.errabi.ishop.entities.User;
import com.errabi.ishop.repositories.LoginFailureRepository;
import com.errabi.ishop.repositories.LoginSuccessRepository;
import com.errabi.ishop.repositories.UserRepository;
import com.errabi.ishop.entities.LoginFailure;
import com.nimbusds.jose.JOSEException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.errabi.common.utils.IShopCodeError.*;
import static com.errabi.common.utils.IShopMessageError.*;

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
    private final LoginSuccessRepository loginSuccessRepository;
    private final GoogleAuthenticator googleAuthenticator;

    @Value("${ishop.user.lock.attempts:3}")
    private int attemptsNbBeforeLockAccount ;

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param authRequest
     * @return an {@link AuthenticationResponseDto} of a user when login succeeds
     */
    public AuthenticationResponseDto login(AuthenticationRequestDto authRequest, HttpServletRequest request) throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, JOSEException {
        log.debug("Attempted username : {}",authRequest.getUserName());

        var validUser = validateUserCredentials(authRequest,request);
        traceLoginSuccess(validUser,request);
        // Verify otp code
        //validOtpCode(Integer.valueOf(authRequest.getOtpCode()),validUser);
        // Return JWT token
        return  AuthenticationResponseDto.builder()
                .jwt(tokens.newToken(validUser))
                .build();
    }
    private User validateUserCredentials(AuthenticationRequestDto authRequest,HttpServletRequest request){
        log.debug("Validate user credentials ...");

        var user =   userService
                .findByUsername(authRequest.getUserName());

        if(user.isPresent() && !user.get().isAccountNonLocked()){
            throw  new IShopExceptionAuth(USER_LOCKED_ERROR_CODE,USER_ACCOUNT_LOCKED);
        }
        if(user.isPresent() && !verifyUserPassword(authRequest.getPassword(),user.get().getPassword()) ){
            attemptToLockUserAccount(user.get(),request);
            throw  new IShopExceptionAuth(USER_NOT_FOUND_ERROR_CODE,INVALID_USERNAME_OR_PASSWORD);
        }else if(user.isEmpty()){
            throw  new IShopExceptionAuth(USER_NOT_FOUND_ERROR_CODE,INVALID_USERNAME_OR_PASSWORD);
        }

        return user.get();
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

    private void traceLoginSuccess(User user, HttpServletRequest request){
        log.debug("user login success {}",user);
        var loginSuccess = LoginSuccess.builder().userName(user.getUsername())
                                                                .user(user)
                                                                .sourceIp(request.getRemoteAddr())
                                                                .build();
        loginSuccessRepository.save(loginSuccess);
    }
    private void traceLoginFailed(User user, HttpServletRequest request){
        log.debug("user login success {}",user);
        var  loginFailure = LoginFailure.builder()
                                                    .userName(user.getUsername())
                                                    .user(user)
                                                    .sourceIp(request.getRemoteAddr())
                                                    .build();
        loginFailureRepository.save(loginFailure);
    }
    private void attemptToLockUserAccount(User user, HttpServletRequest request) {
        log.debug("Verify user account status ...");

        traceLoginFailed(user,request);
        List<LoginFailure> failures = loginFailureRepository.findAllByUserAndCreatedDateIsAfter(user,
                Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

        if(failures.size()>attemptsNbBeforeLockAccount){
            log.info("Locking user account ...");
            user.setAccountNonLocked(false);
            userRepository.save(user);

        }
    }
    private void validOtpCode(Integer otp,User user ) {
        log.info("Check opt code validity ...");
        if( user.getUseGoogle2Fa() && otp != null) {
            if (!googleAuthenticator.authorizeUser(user.getUsername(), otp)) {
                throw new IShopException(USER_INVALID_OTP_ERROR_CODE,USER_INVALID_OTP);
            }
        }else {
            throw new IShopException(USER_OTP_CODE_REQUIRED_ERROR_CODE,OTP_CODE_REQUIRED);

        }
    }
}
