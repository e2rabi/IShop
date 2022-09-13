package com.errabi.ishop.security.task;

import com.errabi.ishop.entities.User;
import com.errabi.ishop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountUnlockService {

    private final UserRepository userRepository ;

    @Scheduled(fixedRate = 15000)
    public void unlockAccounts(){
        log.info("Running unlock accounts ...");
        List<User> lockedUsers = userRepository
                .findAllByAccountNonLockedAndLastModifiedDateIsBefore(false, Timestamp.valueOf(LocalDateTime.now().minusSeconds(38)));

        if(lockedUsers.size()>0){
            log.info("Locked accounts found , unlocking ...");
            lockedUsers.forEach(user -> user.setAccountNonLocked(true));

            userRepository.saveAll(lockedUsers);
        }
    }

}
