package com.errabi.ishop.services;

import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.common.model.UserDto;
import com.errabi.ishop.repositories.UserRepository;
import com.errabi.ishop.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.errabi.common.utils.IShopErrors.USER_NOT_FOUND_ERROR_CODE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;


    public UserDto getUserById(UUID id){
        log.debug("get user by id {}",id);
        var user = userRepository.findById(id)
                                       .orElseThrow(()-> new IShopNotFoundException(USER_NOT_FOUND_ERROR_CODE));
        return mapper.toModel(user);
    }

    public List<UserDto> getAllUsers(){
        log.debug("Get all users ...");
        return userRepository.findAll().stream()
                                        .map(mapper::toModel)
                                        .collect(Collectors.toList());
    }
}
