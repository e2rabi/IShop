package com.errabi.ishop.it;

import com.errabi.ishop.stub.UserStub;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PublicControllerTest extends BaseIT{

    @Test
    @Order(1)
    void loginTest() throws Exception {
        mockMvc.perform(post("/api/v1/public/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserStub.getLoginCredential())))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn();
    }

    @Test
    @Order(2)
    void saveUserTest() throws Exception {
        mockMvc.perform(post("/api/v1/public/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserStub.getNewUser())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    @Order(3)
    @Disabled
    void saveDuplicateUserTest() throws Exception {
        mockMvc.perform(post("/api/v1/public/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserStub.getNewUser())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
