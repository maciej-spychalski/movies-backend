package pl.asbt.movies.storage.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.asbt.movies.storage.dto.DirectorDto;
import pl.asbt.movies.storage.facade.DirectorFacade;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DirectorController.class)
public class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorFacade directorFacade;

    @Test
    public void createDirector() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto(1L, "DirectorFirstname1",
                "DirectorSurname1", movieTitles);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(directorDto);

        when(directorFacade.createDirector(ArgumentMatchers.any(DirectorDto.class))).thenReturn(directorDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/directors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void getDirector() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto(1L, "DirectorFirstname1",
                "DirectorSurname1", movieTitles);

        when(directorFacade.fetchDirector(ArgumentMatchers.anyLong())).thenReturn(directorDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/directors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("directorId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("DirectorFirstname1")))
                .andExpect(jsonPath("$.surname", is("DirectorSurname1")));
    }

    @Test
    public void getDirectorByNameAndSurname() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto(1L, "DirectorFirstname1",
                "DirectorSurname1", movieTitles);
        List<DirectorDto> directorsDto = new ArrayList<>();
        directorsDto.add(directorDto);

        when(directorFacade.fetchDirectorByNameAndSurname(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(directorsDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/directors/DirectorFirstname1/DirectorSurname1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("name", "DirectorFirstname1")
                .param("surname", "DirectorSurname1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstname", is("DirectorFirstname1")))
                .andExpect(jsonPath("$[0].surname", is("DirectorSurname1")));
    }

    @Test
    public void getDirectors() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto(1L, "DirectorFirstname1",
                "DirectorSurname1", movieTitles);
        List<DirectorDto> directorsDto = new ArrayList<>();
        directorsDto.add(directorDto);

        when(directorFacade.fetchDirectors()).thenReturn(directorsDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/directors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstname", is("DirectorFirstname1")))
                .andExpect(jsonPath("$[0].surname", is("DirectorSurname1")));
    }

    @Test
    public void deleteDirector() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/directors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("directorId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(directorFacade, Mockito.times(1)).deleteDirector(ArgumentMatchers.anyLong());
    }

    @Test
    public void deleteDirectorByNameAndSurname() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/directors/DirectorFirstname1/DirectorSurname1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("name", "ActorFirstname1")
                .param("surname", "ActorSurname1"))
                .andExpect(status().isOk());
        Mockito.verify(directorFacade, Mockito.times(1))
                .deleteDirectorByNameAndSurname(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    public void updateDirector() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto(1L, "DirectorFirstname1",
                "DirectorSurname1", movieTitles);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(directorDto);

        when(directorFacade.updateDirector(ArgumentMatchers.any(DirectorDto.class))).thenReturn(directorDto);

        // When & Then
        mockMvc.perform(put("/v1/storage/directors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }
}
