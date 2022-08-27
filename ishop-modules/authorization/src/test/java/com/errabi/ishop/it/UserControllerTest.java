package com.errabi.ishop.it;

import com.errabi.common.exception.IShopNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import spock.lang.Ignore;

import static com.errabi.common.utils.IShopErrors.USER_NOT_FOUND_ERROR_CODE;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest extends BaseIT{


    @Test
   // @WithMockUser("ay")
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users/")
                 .with(httpBasic("ayoub","admin")))
                 .andDo(print())
                 .andExpect(status().isOk());
    }

    @Test
    @Ignore
    //@WithMockUser("admin")
    void getUserById() throws Exception {
        mockMvc.perform(get("/api/v1/users/4c522682-246b-4f58-909e-01deb686e2e1"))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
