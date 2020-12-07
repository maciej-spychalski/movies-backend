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
import pl.asbt.movies.storage.dto.*;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.MovieFacade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieFacade movieFacade;

    @Test
    public void addDirector() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/movies/1/add-director/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieId", "1")
                .param("directorId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(movieFacade, Mockito.times(1))
                .addDirector(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void addWriter() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/movies/1/add-writer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieId", "1")
                .param("writerId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(movieFacade, Mockito.times(1))
                .addWriter(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
   }

    @Test
    public void removeWriter() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/movies/1/remove-writer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieId", "1")
                .param("writerId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(movieFacade, Mockito.times(1))
                .removeWriter(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void addActor() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/movies/1/add-actor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieId", "1")
                .param("actorId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(movieFacade, Mockito.times(1))
                .addActor(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void removeActor() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/movies/1/remove-actor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieId", "1")
                .param("actorId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(movieFacade, Mockito.times(1))
                .removeActor(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void addGenre() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/movies/1/add-genre/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieId", "1")
                .param("genreId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(movieFacade, Mockito.times(1))
                .addGenre(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void removeGenre() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/movies/1/remove-genre/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieId", "1")
                .param("genreId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(movieFacade, Mockito.times(1))
                .removeGenre(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void createMovie() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        movieTitles.add("title");
        DirectorDto directorDto = new DirectorDto(1L, "firstname", "surname", movieTitles);
        List<WriterDto> writersDto = new ArrayList<>();
        List<ActorDto> actorsDto = new ArrayList<>();
        List<GenreDto> genresDto = new ArrayList<>();
        List<ItemDto> itemsDto = new ArrayList<>();
        MovieDto movieDto = new MovieDto(1L, "title", directorDto, writersDto, actorsDto,
                genresDto, 90, new BigDecimal(10));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(movieDto);

        when(movieFacade.createMovie(ArgumentMatchers.any(MovieDto.class))).thenReturn(movieDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void getMovie() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        movieTitles.add("title");
        DirectorDto directorDto = new DirectorDto(1L, "firstname", "surname", movieTitles);
        List<WriterDto> writersDto = new ArrayList<>();
        List<ActorDto> actorsDto = new ArrayList<>();
        List<GenreDto> genresDto = new ArrayList<>();
        List<ItemDto> itemsDto = new ArrayList<>();
        MovieDto movieDto = new MovieDto(1L, "title", directorDto, writersDto, actorsDto,
                genresDto, 90, new BigDecimal(10));

        when(movieFacade.fetchMovie(ArgumentMatchers.anyLong())).thenReturn(movieDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.duration", is(90)))
                .andExpect(jsonPath("$.price", is(10)));
    }

    @Test
    public void getMovieByTitle() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        movieTitles.add("title");
        DirectorDto directorDto = new DirectorDto(1L, "firstname", "surname", movieTitles);
        List<WriterDto> writersDto = new ArrayList<>();
        List<ActorDto> actorsDto = new ArrayList<>();
        List<GenreDto> genresDto = new ArrayList<>();
        List<ItemDto> itemsDto = new ArrayList<>();
        MovieDto movieDto = new MovieDto(1L, "title", directorDto, writersDto, actorsDto,
                genresDto, 90, new BigDecimal(10));
        List<MovieDto> moviesDto = new ArrayList<>();
        moviesDto.add(movieDto);

        when(movieFacade.fetchMovieByTitle(ArgumentMatchers.anyString())).thenReturn(moviesDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/movies/title/title")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("title", "title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title")))
                .andExpect(jsonPath("$[0].duration", is(90)))
                .andExpect(jsonPath("$[0].price", is(10)));
    }

    @Test
    public void getMovies() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        movieTitles.add("title");
        DirectorDto directorDto = new DirectorDto(1L, "firstname", "surname", movieTitles);
        List<WriterDto> writersDto = new ArrayList<>();
        List<ActorDto> actorsDto = new ArrayList<>();
        List<GenreDto> genresDto = new ArrayList<>();
        List<ItemDto> itemsDto = new ArrayList<>();
        MovieDto movieDto = new MovieDto(1L, "title", directorDto, writersDto, actorsDto,
                genresDto, 90, new BigDecimal(10));
        List<MovieDto> moviesDto = new ArrayList<>();
        moviesDto.add(movieDto);

        when(movieFacade.fetchMovies()).thenReturn(moviesDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("title")))
                .andExpect(jsonPath("$[0].duration", is(90)))
                .andExpect(jsonPath("$[0].price", is(10)));
    }

    @Test
    public void deleteMovie() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("movieId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(movieFacade, Mockito.times(1)).deleteMovie(ArgumentMatchers.anyLong());
    }

    @Test
    public void deleteMovieByTitle() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/movies/title/title")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("title", "title"))
                .andExpect(status().isOk());
        Mockito.verify(movieFacade, Mockito.times(1)).deleteMovieByTitle(ArgumentMatchers.anyString());
    }

    @Test
    public void updateMovie() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        movieTitles.add("title");
        DirectorDto directorDto = new DirectorDto(1L, "firstname", "surname", movieTitles);
        List<WriterDto> writersDto = new ArrayList<>();
        List<ActorDto> actorsDto = new ArrayList<>();
        List<GenreDto> genresDto = new ArrayList<>();
        List<ItemDto> itemsDto = new ArrayList<>();
        MovieDto movieDto = new MovieDto(1L, "title", directorDto, writersDto, actorsDto,
                genresDto, 90, new BigDecimal(10));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(movieDto);

        when(movieFacade.updateMovie(ArgumentMatchers.any(MovieDto.class))).thenReturn(movieDto);

        // When & Then
        mockMvc.perform(put("/v1/storage/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }
}
