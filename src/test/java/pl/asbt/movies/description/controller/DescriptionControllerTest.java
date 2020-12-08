package pl.asbt.movies.description.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.asbt.movies.description.dto.DescriptionDto;
import pl.asbt.movies.description.service.DescriptionService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DescriptionController.class)
public class DescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DescriptionService descriptionService;

    @Test
    public void getDescription() throws Exception {
        // Given
        DescriptionDto descriptionDto = new DescriptionDto();

        when(descriptionService.getDescription(ArgumentMatchers.anyString())).thenReturn(descriptionDto);

        // When & Then
        mockMvc.perform(get("/v1/description/movieTitle")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieTitle", "movieTitle"))
                .andExpect(status().isOk());
    }
}
