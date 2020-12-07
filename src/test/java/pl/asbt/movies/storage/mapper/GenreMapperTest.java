package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.GenreDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreMapperTest {

    @Autowired
    private GenreMapper genreMapper;

    @Test
    public void mapToGenreTest() {
        // Given
        List<String> movieTitles = new ArrayList<>();
        GenreDto genreDto = new GenreDto(1L, "Type1", movieTitles);

        // When
        Genre genre = genreMapper.mapToGenre(genreDto);

        // Then
        assertEquals("Type1", genre.getType());
    }

    @Test
    public void mapToGenreDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie("title", director, writers, actors, genres, 90, new BigDecimal(10));
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        Genre genre = new Genre(1L, "Type1", movies);

        // When
        GenreDto genreDto = genreMapper.mapToGenreDto(genre);

        // Then
        assertEquals(1L, (long) genreDto.getId());
        assertEquals("Type1", genreDto.getType());
        assertEquals("title", genreDto.getMoviesTitle().get(0));
    }

    @Test
    public void mapToGenresDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie("title", director, writers, actors, genres, 90, new BigDecimal(10));
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        Genre genre = new Genre(1L, "Type1", movies);
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre);

        // When
        List<GenreDto> genreDtoList = genreMapper.mapToGenresDto(genreList);

        // Then
        assertEquals(1L, (long) genreDtoList.get(0).getId());
        assertEquals("Type1", genreDtoList.get(0).getType());
        assertEquals(1, genreDtoList.size());
    }
}
