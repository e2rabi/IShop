package com.errabi.ishop.controllers;

import com.errabi.common.model.AuthorityDto;
import com.errabi.common.model.UserDto;
import com.errabi.ishop.services.AuthorityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Authority controller")
@RequestMapping("/api/v1/authorities")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService ;

    @GetMapping
    public ResponseEntity<Page<AuthorityDto>> getAllAuthoritiesWithPage(Pageable pageRequest){
        return new ResponseEntity<>(authorityService.getAllAuthorities(pageRequest), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AuthorityDto> getAuthorityById(@PathVariable("id") UUID id){
        return new ResponseEntity<>(authorityService.getAuthorityById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthority(@PathVariable("id") UUID id){
        authorityService.deleteAuthorityById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthority(@RequestBody @Valid AuthorityDto authorityDto,@PathVariable("id") UUID id){
        authorityService.updateAuthority(authorityDto,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveAuthority(@RequestBody @Valid AuthorityDto authorityDto){
        var dto = authorityService.saveAuthority(authorityDto);
        var headers = new HttpHeaders();
        var location = ServletUriComponentsBuilder
                                                .fromCurrentRequest()
                                                .path("/{id}")
                                                .buildAndExpand(dto.getId())
                                                .toUri();
        headers.setLocation(location);
        return new ResponseEntity(dto,headers,HttpStatus.CREATED);
    }
}
