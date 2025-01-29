package fr.benseddik.gestioncmd.fake;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void fake() throws Exception {
        String except = "fake";
        mockMvc.perform(get("/fake"))
                .andExpect(status().isOk())
                .andExpect(content().string(except));
    }

    @Test
    void fakePost() throws Exception {
        FakeObject fakeObject = new FakeObject("fake", 25);
        String except = "fake";
        mockMvc.perform(post("/fake")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fakeObject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(except))
                .andExpect(jsonPath("$.age").value(25));
    }
}