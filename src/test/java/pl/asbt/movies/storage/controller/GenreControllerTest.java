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
import pl.asbt.movies.storage.dto.GenreDto;
import pl.asbt.movies.storage.facade.GenreFacade;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreFacade genreFacade;

    @Test
    public void createGenre() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        GenreDto genreDto = new GenreDto(1L, "GenreTyp1", movieTitles);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(genreDto);

        when(genreFacade.createGenre(ArgumentMatchers.any(GenreDto.class))).thenReturn(genreDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void getGenre() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        GenreDto genreDto = new GenreDto(1L, "GenreTyp1", movieTitles);

        when(genreFacade.fetchGenre(ArgumentMatchers.anyLong())).thenReturn(genreDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/genres/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("genreId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is("GenreTyp1")));
    }

    @Test
    public void getGenreByType() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        GenreDto genreDto = new GenreDto(1L, "GenreTyp1", movieTitles);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto);

        when(genreFacade.fetchGenreByType(ArgumentMatchers.anyString())).thenReturn(genresDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/genres/type/GenreTyp1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("type", "GenreTyp1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].type", is("GenreTyp1")));
    }

    @Test
    public void getGenres() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        GenreDto genreDto = new GenreDto(1L, "GenreTyp1", movieTitles);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto);

        when(genreFacade.fetchGenres()).thenReturn(genresDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].type", is("GenreTyp1")));
    }

    @Test
    public void deleteGenre() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/genres/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("genreId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(genreFacade, Mockito.times(1)).deleteGenre(ArgumentMatchers.anyLong());

    }

    @Test
    public void deleteGenreByType() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/genres/type/GenreTyp1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("type", "GenreTyp1"))
                .andExpect(status().isOk());
        Mockito.verify(genreFacade, Mockito.times(1)).deleteGenreByType(ArgumentMatchers.anyString());
    }

    @Test
    public void updateGenre() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        GenreDto genreDto = new GenreDto(1L, "GenreTyp1", movieTitles);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(genreDto);

        when(genreFacade.updateGenre(ArgumentMatchers.any(GenreDto.class))).thenReturn(genreDto);

        // When & Then
        mockMvc.perform(put("/v1/storage/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }
}
