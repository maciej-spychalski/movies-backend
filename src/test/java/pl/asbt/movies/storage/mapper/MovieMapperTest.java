package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieMapperTest {

    @Autowired
    private MovieMapper movieMapper;

    @Test
    public void mapToMovieTest() {
        // Given
        List<String> movieTitles = new ArrayList<>();
        movieTitles.add("title");
        DirectorDto directorDto = new DirectorDto(1L, "firstname", "surname", movieTitles);
        List<WriterDto> writersDto = new ArrayList<>();
        List<ActorDto> actorsDto = new ArrayList<>();
        List<GenreDto> genresDto = new ArrayList<>();
        List<ItemDto> itemsDto = new ArrayList<>();
        MovieDto movieDto = new MovieDto(1L, "title", directorDto, writersDto, actorsDto, genresDto, 90, new BigDecimal(10));

        // When
        Movie movie = movieMapper.mapToMovie(movieDto);

        // Then
        assertEquals("title", movie.getTitle());
        assertEquals(90, (int) movie.getDuration());
        assertEquals( new BigDecimal(10), movie.getPrice());
    }

    @Test
    public void mapToMovieDto() {
        // Given
        List<Movie> movies = new ArrayList<>();
        Director director = new Director(1L, "firstname", "surname", movies);
        List<Writer> writers = new ArrayList<>();
        writers.add(new Writer(1L, "firstname", "surname", movies));
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1L, "firstname", "surname", movies));
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1L, "type", movies));
        Movie movie = new Movie(1L,"title", director, writers, actors, genres, 90, new BigDecimal(10));

        // When
        MovieDto movieDto = movieMapper.mapToMovieDto(movie);

        // Then
        assertEquals("title", movie.getTitle());
        assertEquals(90, (int) movieDto.getDuration());
        assertEquals( new BigDecimal(10), movieDto.getPrice());
    }

    @Test
    public void mapToMoviesDto() {
        // Given
        List<Movie> movies = new ArrayList<>();
        Director director = new Director(1L, "firstname", "surname", movies);
        List<Writer> writers = new ArrayList<>();
        writers.add(new Writer(1L, "firstname", "surname", movies));
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1L, "firstname", "surname", movies));
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1L, "type", movies));
        Movie movie = new Movie(1L,"title", director, writers, actors, genres, 90, new BigDecimal(10));
        movies.add(movie);

        // When
        List<MovieDto> moviesDto = movieMapper.mapToMoviesDto(movies);

        // Then
        assertEquals("title", moviesDto.get(0).getTitle());
        assertEquals(90, (int) moviesDto.get(0).getDuration());
        assertEquals( new BigDecimal(10), moviesDto.get(0).getPrice());
    }

}
