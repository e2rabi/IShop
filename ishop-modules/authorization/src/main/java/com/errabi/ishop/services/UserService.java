package com.errabi.ishop.services;

import com.errabi.common.exception.IShopException;
import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.common.model.RoleDto;
import com.errabi.common.model.UserDto;
import com.errabi.common.utils.IShopMessageError;
import com.errabi.ishop.entities.Role;
import com.errabi.ishop.entities.User;
import com.errabi.ishop.repositories.RoleRepository;
import com.errabi.ishop.repositories.UserRepository;
import com.errabi.ishop.services.mappers.RoleMapper;
import com.errabi.ishop.services.mappers.UserMapper;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.errabi.common.utils.IShopCodeError.USERNAME_ALREADY_EXIST_ERROR_CODE;
import static com.errabi.common.utils.IShopCodeError.USER_NOT_FOUND_ERROR_CODE;

/**
 * User security operations like CRUD operations on {@link User}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final RoleMapper roleMapper;
    private final GoogleAuthenticator googleAuthenticator;

    @Transactional
    public UserDto saveUser(UserDto dto){
        log.debug("save user {}",dto);

        validateUser(dto);
        dto.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));

        var qrUrl = generateGoogleAuthSecretKey(dto);
        dto.setQrUrl(qrUrl);

        userRepository.save(mapper.toEntity(dto));
        return dto;
    }

    private String generateGoogleAuthSecretKey(UserDto userDto){
        log.info("Generate user google secret key ...");
        googleAuthenticator.createCredentials(userDto.getUsername());
        var qrUrl = GoogleAuthenticatorQRGenerator
                .getOtpAuthURL("errabi",userDto.getUsername(),googleAuthenticator.createCredentials(userDto.getUsername()));
        log.info("Google QR url : "+qrUrl);
        return qrUrl;
    }


    @Transactional
    public void deleteUserById(UUID id){
        log.debug("delete user by id {}",id);
        userRepository.deleteById(id);
    }
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

    @Transactional
    public UserDto updateUser(UserDto dto,UUID id){
        log.debug("update user by id {}",id);
        var user = getUserById(id);

        BeanUtils.copyProperties(dto,user);
        userRepository.save(mapper.toEntity(user));

        return user;
    }

    public Optional<User> findByUsername(Object username) {
        log.debug("find user by username {}",username);
        return userRepository.findByUsername((String) username);
    }

    @Transactional
    public void addRolesToUser(UUID uuid, List<RoleDto> rolesDto) {
        log.debug("add roles {} to user by id {}",rolesDto,uuid);
        if(!CollectionUtils.isEmpty(rolesDto))  {
            var  roles =   rolesDto.stream()
                                                .map(roleMapper::toEntity)
                                                .collect(Collectors.toList());
            var user = mapper.toEntity(getUserById(uuid));
            var roleNames = user.getRoles().stream()
                                                        .map(Role::getName)
                                                        .collect(Collectors.toSet());
            var rolesToAdd = roles.stream()
                                                .filter(e->!roleNames.contains(e.getName()))
                                                .collect(Collectors.toSet());
            rolesToAdd.forEach(e->{
                e.setUsers(Set.of(user));
            });

            roleRepository.saveAll(rolesToAdd);

        }
    }
    private void validateUser(UserDto dto){
       var user = userRepository.findByUsername(dto.getUsername());
       if(user.isPresent()){
           throw new IShopException(USERNAME_ALREADY_EXIST_ERROR_CODE, IShopMessageError.USERNAME_ALREADY_EXIST);
       }
    }
}
