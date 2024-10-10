package com.mtvs.devlinkbackend.config;

import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.HttpHeaders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CorsRequestTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCorsConfig() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.options("/api/some-endpoint")
                        .header(ORIGIN, "http://localhost:5173")
                        .header(ACCESS_CONTROL_REQUEST_METHOD, "GET"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:5173"))
                .andExpect(MockMvcResultMatchers.header().string(ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE"));
    }

    @Test
    public void testCorsConfig_withDisallowedOrigin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.options("/api/some-endpoint")
                        .header(ORIGIN, "http://disallowed-origin.com")
                        .header(ACCESS_CONTROL_REQUEST_METHOD, "GET"))
                .andExpect(MockMvcResultMatchers.status().isForbidden()); // 허용되지 않은 Origin인 경우
    }
}
