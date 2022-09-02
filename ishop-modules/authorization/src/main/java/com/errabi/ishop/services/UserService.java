package com.errabi.ishop.services;

import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.common.model.UserDto;
import com.errabi.ishop.entities.User;
import com.errabi.ishop.repositories.UserRepository;
import com.errabi.ishop.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.errabi.common.utils.IShopErrors.USER_NOT_FOUND_ERROR_CODE;

/**
 * User security operations like login and logout, and CRUD operations on {@link User}.
 *
 * @author jerome
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDto saveUser(UserDto dto){
        log.debug("save user {}",dto);
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(mapper.toEntity(dto));
        return dto;
    }


    public void deleteUserById(UUID id){
        log.debug("delete user by id {}",id);
        userRepository.deleteById(id);
    }
    public Optional<UserDto> getUserById(UUID id){
        log.debug("get user by id {}",id);
        var user = userRepository.findById(id)
                                       .orElseThrow(()-> new IShopNotFoundException(USER_NOT_FOUND_ERROR_CODE));
        return Optional.of(mapper.toModel(user));
    }

    public List<UserDto> getAllUsers(){
        log.debug("Get all users ...");
        return userRepository.findAll().stream()
                                        .map(mapper::toModel)
                                        .collect(Collectors.toList());
    }

    public UserDto updateUser(UserDto dto,UUID id){
        log.debug("update user by id {}",id);
        var user = getUserById(id).get();
        var newUser = new User();

        BeanUtils.copyProperties(dto,newUser);
        userRepository.save(newUser);

        return mapper.toModel(newUser);
    }
    public Optional<User> findByUsername(Object username) {
        log.debug("find user by username {}",username);
        return userRepository.findByUsername((String) username);
    }
}
