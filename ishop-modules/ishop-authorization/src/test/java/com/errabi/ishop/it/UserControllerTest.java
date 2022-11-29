package com.errabi.ishop.it;

import com.errabi.ishop.stub.RoleStub;
import com.errabi.ishop.stub.UserStub;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;


import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest extends BaseIT{

    @Test
    @Disabled
    @Order(1)
        // @WithMockUser("ayoub")
    void getAllUsers() throws Exception {
              mockMvc.perform(get("/api/v1/users/")
                        .header("Authorization","Bearer " +token))
               //  .with(httpBasic("ayoub","admin")))
                 .andDo(print())
                 .andExpect(status().isOk())
                 .andReturn();
    }
    @Test
    @Disabled
    @Order(2)
    void getUserById() throws Exception {
        mockMvc.perform(get("/api/v1/users/6e57e08a-7fd9-4886-ac79-1934ab06d015")
                        .header("Authorization","Bearer " +token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Disabled
    @Order(5)
    void updateUserById() throws Exception {
        mockMvc.perform(put("/api/v1/users/6e57e08a-7fd9-4886-ac79-1934ab06d015")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(UserStub.getUser()))
                        .header("Authorization","Bearer " +token))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Disabled
    @Order(6) // todo to fix this test
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/api/v1/users/6e57e08a-7fd9-4886-ac79-1934ab06d015")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer " +token))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
    @Test
    @Disabled
    @Order(3)
    void addRoleToUserById() throws Exception {
        mockMvc.perform(put("/api/v1/users/{id}/roles","6e57e08a-7fd9-4886-ac79-1934ab06d015")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(List.of(RoleStub.getRole())))
                        .header("Authorization","Bearer " +token))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Disabled
    @Order(4)
    void register2f() throws Exception {
        mockMvc.perform(put("/api/v1/users/6e57e08a-7fd9-4886-ac79-1934ab06d015/register2f")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer " +token))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
