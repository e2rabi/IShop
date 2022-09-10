package com.errabi.ishop.services;

import com.errabi.common.exception.IShopNotFoundException;
import com.errabi.common.model.AuthorityDto;
import com.errabi.common.model.RoleDto;
import com.errabi.ishop.entities.Authority;
import com.errabi.ishop.entities.Role;
import com.errabi.ishop.repositories.RoleRepository;
import com.errabi.ishop.services.mappers.AuthorityMapper;
import com.errabi.ishop.services.mappers.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.errabi.common.utils.IShopErrors.ROLE_NOT_FOUND_ERROR_CODE;

/**
 * User roles security operations like CRUD operations on {@link Role}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper ;
    private final AuthorityMapper authorityMapper ;

    public List<RoleDto> getAllRoles() {
        log.debug("Get All roles ...");
        return roleRepository.findAll().stream().map(roleMapper::toModel).collect(Collectors.toList());
    }

    public RoleDto getRoleById(UUID id) {
        log.debug("get role by id {}",id);
        return roleMapper.toModel(roleRepository.findById(id)
                .orElseThrow(()-> new IShopNotFoundException(ROLE_NOT_FOUND_ERROR_CODE)));
    }

    public void deleteRoleById(UUID uuid) {
        log.debug("delete role by id {}",uuid);
        roleRepository.deleteById(uuid);
    }

    public void updateRole(RoleDto roleDto,UUID id) {
        log.debug("update role by id {}",id);
        var role = getRoleById(id);

        BeanUtils.copyProperties(roleDto,role);
        roleRepository.save(roleMapper.toEntity(role));
    }

    public RoleDto saveRole(RoleDto roleDto) {
        log.debug("save new role {}",roleDto);
        return roleMapper.toModel(roleRepository.save(roleMapper.toEntity(roleDto)));
    }


    public void addAuthorityToRole(UUID uuid, List<AuthorityDto> newAuthorities) {
        log.debug("add new authorities to role with id {}",uuid);
        if(CollectionUtils.isEmpty(newAuthorities))  {

            var authorities =   authorityMapper.toEntity(newAuthorities);
            var role = roleMapper.toEntity(getRoleById(uuid));

            var authoritiesNames = role.getAuthorities().stream()
                                                                .map(Authority::getPermission)
                                                                .collect(Collectors.toSet());

            var authoritiesToAdd = authorities.stream()
                                                            .filter(e->!authoritiesNames.contains(e.getPermission()))
                                                            .collect(Collectors.toList());

            role.getAuthorities().addAll(authoritiesToAdd);

            roleRepository.save(role);
        }
    }
}
