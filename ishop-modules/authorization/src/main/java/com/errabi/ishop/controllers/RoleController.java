package com.errabi.ishop.controllers;

import com.errabi.common.model.AuthorityDto;
import com.errabi.common.model.RoleDto;
import com.errabi.common.model.UserDto;
import com.errabi.ishop.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    RoleService roleService ;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable("id") UUID id){
        return new ResponseEntity<>(roleService.getRoleById(id),HttpStatus.OK);
    }

    @DeleteMapping(("/{id}"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable("id") UUID id){
        roleService.deleteRoleById(id);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity updateRole(@RequestBody @Valid RoleDto roleDto,@PathVariable("roleId")  UUID roleId){
        roleService.updateRole(roleDto,roleId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{roleId}/authorities")
    public ResponseEntity addAuthoritiesToRole(@PathVariable("roleId")  UUID roleId,@RequestBody List<AuthorityDto> authorities){
        roleService.addAuthorityToRole(roleId,authorities);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<UserDto> saveRole(@RequestBody @Valid  RoleDto roleDto){
        var dto = roleService.saveRole(roleDto);
        var headers = new HttpHeaders();
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        headers.setLocation(location);
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }
}
