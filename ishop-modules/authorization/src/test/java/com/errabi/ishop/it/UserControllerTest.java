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
                        .header("Authorization","Bearer eyJraWQiOiJpc2hvcCIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJFUlJBQkkiLCJzY29wZSI6W3sicm9sZSI6InVwZGF0ZV91c2VyIn0seyJyb2xlIjoicmVhZF91c2VyIn0seyJyb2xlIjoid3JpdGVfdXNlciJ9LHsicm9sZSI6ImRlbGV0ZV91c2VyIn1dLCJpc3MiOiJFUlJBQkkiLCJSb2xlcyI6W3sibmFtZSI6IkFETUlOIiwidXNlcnMiOm51bGwsImF1dGhvcml0aWVzIjpbeyJwZXJtaXNzaW9uIjoid3JpdGVfdXNlciIsInJvbGVzIjpbXSwiaWQiOiJiMzEwZDRjMS0yOTg0LTRiMjUtYjlhMC02NzNhN2MwNTlkODIiLCJ2ZXJzaW9uIjowLCJjcmVhdGVkRGF0ZSI6IlNlcCAxLCAyMDIyLCAxMjoxOTo0MiBBTSIsImxhc3RNb2RpZmllZERhdGUiOiJTZXAgMSwgMjAyMiwgMTI6MTk6NDIgQU0ifSx7InBlcm1pc3Npb24iOiJkZWxldGVfdXNlciIsInJvbGVzIjpbXSwiaWQiOiIzYjAyMGFjNi05NzllLTQ3MDMtOGEyOS04NDI2YzA5M2VkMjciLCJ2ZXJzaW9uIjowLCJjcmVhdGVkRGF0ZSI6IlNlcCAxLCAyMDIyLCAxMjoxOTo0MiBBTSIsImxhc3RNb2RpZmllZERhdGUiOiJTZXAgMSwgMjAyMiwgMTI6MTk6NDIgQU0ifSx7InBlcm1pc3Npb24iOiJyZWFkX3VzZXIiLCJyb2xlcyI6W10sImlkIjoiYjM3MGFhNjktY2YyMy00ODE1LWI4ZTEtMTI5ZjFiZTFjZDhhIiwidmVyc2lvbiI6MCwiY3JlYXRlZERhdGUiOiJTZXAgMSwgMjAyMiwgMTI6MTk6NDIgQU0iLCJsYXN0TW9kaWZpZWREYXRlIjoiU2VwIDEsIDIwMjIsIDEyOjE5OjQyIEFNIn0seyJwZXJtaXNzaW9uIjoidXBkYXRlX3VzZXIiLCJyb2xlcyI6W10sImlkIjoiYmQyMDY3NzMtMGM4ZC00NGVjLTkyMDgtMzM4OTBkN2QxMjdiIiwidmVyc2lvbiI6MCwiY3JlYXRlZERhdGUiOiJTZXAgMSwgMjAyMiwgMTI6MTk6NDIgQU0iLCJsYXN0TW9kaWZpZWREYXRlIjoiU2VwIDEsIDIwMjIsIDEyOjE5OjQyIEFNIn1dLCJpZCI6Ijg2MGY4Yzk1LTQwMTQtNDhkZi05ZmE1LTAyOWM5NzZhZGFhYiIsInZlcnNpb24iOjAsImNyZWF0ZWREYXRlIjoiU2VwIDEsIDIwMjIsIDEyOjE5OjQyIEFNIiwibGFzdE1vZGlmaWVkRGF0ZSI6IlNlcCAxLCAyMDIyLCAxMjoxOTo0MiBBTSJ9XSwidXNlck5hbWUiOiJhZG1pbiIsImV4cCI6MTY2MTk4OTc4M30.M6uJVgXYZySPro16PxR3euPzALwFFqMveSJStrg1V9d6kYkxo6mRUamLcFlGg2nr7tGRememDS0sVdtsHOmjys7ld1G1HU8cGPhbWorOOOPYPyKLkvzMzbgTHC6E7oUYtVnoBehJinyI0zoW3o6gldCvxQzT5OEPMtFA2Yid1J3vqseS1VXyH_lH83vnWE5bZk_swSkP2LEKPH4pXYPMziliVeWtWX6JmYI70mDLd3pKNf1rtGY1sa_jc66yJ7aRiPAkKP3_6t5LVUxPSdWSDLYq3lbLFpOcES9cnn74wjytmcQi8C76A2ef47mwTDybQ3mfFSfT7wFDqxYynLLM1A" ))
               //  .with(httpBasic("ayoub","admin")))
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
