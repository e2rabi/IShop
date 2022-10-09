package com.errabi.ishop.it;

import com.errabi.ishop.entities.User;
import com.errabi.ishop.services.TokenService;
import com.errabi.ishop.stub.UserStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


public abstract class BaseIT {
    @Autowired
    WebApplicationContext wac;
    @Autowired
    protected TokenService tokenService;

    protected static String token ;
    protected static String userId ;
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, JOSEException {
        if(token == null)
        token =  tokenService.newToken(UserStub.getUser());

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())// apply spring security on web context
                .build();
    }
    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}