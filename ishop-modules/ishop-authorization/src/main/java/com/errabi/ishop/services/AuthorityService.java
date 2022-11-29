package com.errabi.ishop.services;

import com.errabi.common.exception.IShopException;
import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.common.model.AuthorityDto;
import com.errabi.ishop.repositories.AuthorityRepository;
import com.errabi.ishop.services.mappers.AuthorityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.errabi.common.utils.IShopCodeError.AUTHORITY_ALREADY_EXIST;
import static com.errabi.common.utils.IShopCodeError.AUTHORITY_NOT_FOUND_ERROR_CODE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository ;
    private final AuthorityMapper authorityMapper ;


    public Page<AuthorityDto> getAllAuthorities(Pageable pageable) {
        log.debug("Get all authorities by page...");
        var pageOfAuthorities = authorityRepository.findAll(pageable);
        var authorities      =   pageOfAuthorities.stream()
                .map(authorityMapper::toModel)
                .collect(Collectors.toList());
        return  new PageImpl<>(authorities, pageOfAuthorities.getPageable(), pageOfAuthorities.getTotalElements());
    }
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
        var authority = authorityRepository.findByPermission(authorityDto.getPermission());
        if(authority.isPresent()){
            throw new IShopException(AUTHORITY_ALREADY_EXIST);
        }
        return authorityMapper.toModel(authorityRepository.save(authorityMapper.toEntity(authorityDto)));
    }
}
