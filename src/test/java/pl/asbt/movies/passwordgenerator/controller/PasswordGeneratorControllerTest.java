package pl.asbt.movies.passwordgenerator.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.asbt.movies.passwordgenerator.dto.GeneratedPasswordDto;
import pl.asbt.movies.passwordgenerator.service.PasswordGeneratorService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PasswordGeneratorController.class)
public class PasswordGeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordGeneratorService passwordGeneratorService;

    @Test
    public void getPasswords() throws Exception {
        // Given
        GeneratedPasswordDto generatedPasswordDto = new GeneratedPasswordDto();

        when(passwordGeneratorService.getPasswords()).thenReturn(generatedPasswordDto);

        // When & Then
        mockMvc.perform(get("/v1/passwordGenerator")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }
}
