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
                        .header("Authorization","Bearer eyJraWQiOiJpc2hvcCIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJFUlJBQkkiLCJzY29wZSI6W3sicm9sZSI6InVwZGF0ZV91c2VyIn0seyJyb2xlIjoicmVhZF91c2VyIn0seyJyb2xlIjoiZGVsZXRlX3VzZXIifSx7InJvbGUiOiJ3cml0ZV91c2VyIn1dLCJpc3MiOiJFUlJBQkkiLCJSb2xlcyI6W3sibmFtZSI6IkFETUlOIiwidXNlcnMiOm51bGwsImF1dGhvcml0aWVzIjpbeyJwZXJtaXNzaW9uIjoicmVhZF91c2VyIiwicm9sZXMiOltdLCJpZCI6ImFmYTE5ZDAxLTYwNTYtNGYzOC1iOTBlLTcyNjg2YzJjZmVhZSIsInZlcnNpb24iOjAsImNyZWF0ZWREYXRlIjoiQXVnIDI5LCAyMDIyLCAzOjIxOjUyIFBNIiwibGFzdE1vZGlmaWVkRGF0ZSI6IkF1ZyAyOSwgMjAyMiwgMzoyMTo1MiBQTSJ9LHsicGVybWlzc2lvbiI6ImRlbGV0ZV91c2VyIiwicm9sZXMiOltdLCJpZCI6ImM1N2MwNzYzLWI4MGItNDJmYS05NDM4LTdlYmZlNjk4ZjI2YSIsInZlcnNpb24iOjAsImNyZWF0ZWREYXRlIjoiQXVnIDI5LCAyMDIyLCAzOjIxOjUyIFBNIiwibGFzdE1vZGlmaWVkRGF0ZSI6IkF1ZyAyOSwgMjAyMiwgMzoyMTo1MiBQTSJ9LHsicGVybWlzc2lvbiI6IndyaXRlX3VzZXIiLCJyb2xlcyI6W10sImlkIjoiYjQwMWM3M2UtMzIyMy00YmYyLThjZmItOGQ5ZDI2YTE1Mjc5IiwidmVyc2lvbiI6MCwiY3JlYXRlZERhdGUiOiJBdWcgMjksIDIwMjIsIDM6MjE6NTIgUE0iLCJsYXN0TW9kaWZpZWREYXRlIjoiQXVnIDI5LCAyMDIyLCAzOjIxOjUyIFBNIn0seyJwZXJtaXNzaW9uIjoidXBkYXRlX3VzZXIiLCJyb2xlcyI6W10sImlkIjoiYmMxOWUzODctNjUwOC00MGUyLTk3MGQtMzYwODdjZDM5NmRkIiwidmVyc2lvbiI6MCwiY3JlYXRlZERhdGUiOiJBdWcgMjksIDIwMjIsIDM6MjE6NTIgUE0iLCJsYXN0TW9kaWZpZWREYXRlIjoiQXVnIDI5LCAyMDIyLCAzOjIxOjUyIFBNIn1dLCJpZCI6ImFlMWYyYTBjLWQ1MDktNGQ0ZC1iY2ZhLTc1NDk3ODc2Y2RiNCIsInZlcnNpb24iOjAsImNyZWF0ZWREYXRlIjoiQXVnIDI5LCAyMDIyLCAzOjIxOjUyIFBNIiwibGFzdE1vZGlmaWVkRGF0ZSI6IkF1ZyAyOSwgMjAyMiwgMzoyMTo1MiBQTSJ9XSwidXNlck5hbWUiOiJhZG1pbiIsImV4cCI6MTY2MTc4NDcxM30.eEaEyT-QwLI5ztlc6Beg2yzNyHt3ZmY7b81pZuHzGcFr_UFZcd1PDRLMtdpJjXm8EHJcxjDbYiXzLeQASis9vKcBqI2CcC3C3KsS65jZc09Ppf1kf86hmwASgjCWv8ESbY-o4AFUtAEYnDjkWffZT5-w8-K7eBLXCapsScZ1WxKrHwIQfchs2x1mg-QY88AwYOiXYkHTVk9kPJpVlh9GAJotBKZvymXbVyoM3uPEAeDXm93QlU_Y6guUDz3Gboe-bFn8Ux7SBxkF8NtTlNC6vTSZWKZ3Vag4w8GQYBYydwQd6cNsPEOtDmH6RDaFiYU_2v7pKB_REXAlx_XMNyTmQg"))
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
