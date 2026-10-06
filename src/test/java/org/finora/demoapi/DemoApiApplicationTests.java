package org.finora.demoapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DemoApiApplicationTests {

    @Autowired
    private BasicApiController basicApiController;

    @Test
    void contextLoads() {
    }

    @Test
    void helloEndpointReturnsServiceAndInstance() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(basicApiController).build();

        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("demo-api"))
                .andExpect(jsonPath("$.instanceId").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Hello from demo-api"));
    }

    @Test
    void rootEndpointReturnsInstanceInformation() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(basicApiController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("demo-api"))
                .andExpect(jsonPath("$.instanceId").isNotEmpty())
                .andExpect(jsonPath("$.hostName").isNotEmpty())
                .andExpect(jsonPath("$.ipAddresses").isArray())
                .andExpect(jsonPath("$.operatingSystem").isNotEmpty())
                .andExpect(jsonPath("$.javaVersion").isNotEmpty());
    }

    @Test
    void healthEndpointReturnsUpStatus() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(basicApiController).build();

        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("demo-api"));
    }
}
