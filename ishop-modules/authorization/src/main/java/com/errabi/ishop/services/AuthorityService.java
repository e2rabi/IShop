package com.errabi.ishop.services;

import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.common.model.AuthorityDto;
import com.errabi.ishop.repositories.AuthorityRepository;
import com.errabi.ishop.services.mappers.AuthorityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.errabi.common.utils.IShopErrors.AUTHORITY_NOT_FOUND_ERROR_CODE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository ;
    private final AuthorityMapper authorityMapper ;


    public List<AuthorityDto> getAllAuthorities() {
        log.debug("Get all authorities...");
        return authorityRepository.findAll().stream()
                .map(authorityMapper::toModel)
                .collect(Collectors.toList());
    }


    public AuthorityDto getAuthorityById(UUID uuid) {
        log.debug("Get authority by id {}",uuid);
        return authorityMapper.toModel(authorityRepository.findById(uuid)
                .orElseThrow(()-> new IShopNotFoundException(AUTHORITY_NOT_FOUND_ERROR_CODE)));
    }

    public void deleteAuthorityById(UUID uuid) {
        log.debug("Delete authority by id {}",uuid);
        authorityRepository.deleteById(uuid);
    }

    public void updateAuthority(AuthorityDto authorityDto,UUID id) {
        log.debug("Update authority {}",authorityDto);
        var authority =  getAuthorityById(id);

        BeanUtils.copyProperties(authorityDto,authority);
        authorityRepository.save(authorityMapper.toEntity(authority));
    }

    public AuthorityDto saveAuthority(AuthorityDto authorityDto) {
        return authorityMapper.toModel(authorityRepository.save(authorityMapper.toEntity(authorityDto)));
    }
}
