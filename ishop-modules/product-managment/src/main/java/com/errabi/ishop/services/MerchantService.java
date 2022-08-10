package com.errabi.ishop.services;

import com.errabi.ishop.repositories.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository ;

    public Boolean checkMerchantExist(UUID id){
        log.info("Check merchant with id {} exist",id);
        return merchantRepository.findById(id)
                .isPresent();
    }
}
