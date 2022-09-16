package com.errabi.ishop.repositories;

import com.errabi.common.exception.IShopException;
import com.errabi.ishop.entities.User;
import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.errabi.common.utils.IShopCodeError.USER_NOT_FOUND_ERROR_CODE;
import static com.errabi.common.utils.IShopMessageError.USERNAME_USER_NOT_FOUND;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GoogleCredentialRepository  implements ICredentialRepository {

    private final UserRepository userRepository ;

    @Override
    public String getSecretKey(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(()->new IShopException(USER_NOT_FOUND_ERROR_CODE,USERNAME_USER_NOT_FOUND));
        return user.getGoogle2faSecret();

    }

    @Override
    public void saveUserCredentials(String username, String secretKey, int validationKey, List<Integer> scratchCode) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(()-> new IShopException(USER_NOT_FOUND_ERROR_CODE,USERNAME_USER_NOT_FOUND));
        user.setGoogle2faSecret(secretKey);
        user.setUseGoogle2Fa(true);
        user.setGoogle2faRequired(true);
        userRepository.save(user);
    }
}