package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.ActorDto;
import pl.asbt.movies.storage.dto.DirectorDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectorMapperTest {

    @Autowired
    private DirectorMapper directorMapper;

    @Test
    public void mapToDirectorTest() {
        // Given
        List<String> movieTitles = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto(1L, "Firstname", "Surname", movieTitles);

        // When
        Director director = directorMapper.mapToDirector(directorDto);

        // Then
        assertEquals("Firstname", director.getFirstname());
        assertEquals("Surname", director.getSurname());
    }

    @Test
    public void mapToDirectorDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie("title", director, writers, actors, genres, 90, new BigDecimal(10));
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        director = new Director(1L, "Firstname", "Surname", movies);

        // When
        DirectorDto directorDto = directorMapper.mapToDirectorDto(director);

        // Then
        assertEquals(1L, (long) directorDto.getId());
        assertEquals("Firstname", directorDto.getFirstname());
        assertEquals("Surname", directorDto.getSurname());
        assertEquals("title", directorDto.getMoviesTitle().get(0));
    }

    @Test
    public void mapToDirectorsDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie("title", director, writers, actors, genres, 90, new BigDecimal(10));
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        director = new Director(1L, "Firstname", "Surname", movies);
        List<Director> directorList = new ArrayList<>();
        directorList.add(director);

        // When
        List<DirectorDto> directorDtoList = directorMapper.mapToDirectorsDto(directorList);

        // Then
        assertEquals(1L, (long) directorDtoList.get(0).getId());
        assertEquals("Firstname", directorDtoList.get(0).getFirstname());
        assertEquals("Surname", directorDtoList.get(0).getSurname());
        assertEquals("title", directorDtoList.get(0).getMoviesTitle().get(0));
    }
}
