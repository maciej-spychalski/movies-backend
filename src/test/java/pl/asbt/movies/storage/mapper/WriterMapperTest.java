package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.WriterDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WriterMapperTest {

    @Autowired
    private WriterMapper writerMapper;

    @Test
    public void mapToWriterTest() {
        // Given
        List<String> movieTitles = new ArrayList<>();
        WriterDto writerDto = new WriterDto(1L, "Firstname", "Surname", movieTitles);

        // When
        Writer writer = writerMapper.mapToWriter(writerDto);

        // Then
        assertEquals("Firstname", writer.getFirstname());
        assertEquals("Surname", writer.getSurname());
    }

    @Test
    public void mapToAWriterDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie("title", director, writers, actors, genres, 90, new BigDecimal(10));
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        Writer writer = new Writer(1L, "Firstname", "Surname", movies);

        // When
        WriterDto writerDto = writerMapper.mapToWriterDto(writer);

        // Then
        assertEquals(1L, (long) writerDto.getId());
        assertEquals("Firstname", writerDto.getFirstname());
        assertEquals("Surname", writerDto.getSurname());
        assertEquals("title", writerDto.getMoviesTitle().get(0));
    }

    @Test
    public void mapToWritersDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie("title", director, writers, actors, genres, 90, new BigDecimal(10));
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        Writer writer = new Writer(1L, "Firstname", "Surname", movies);
        List<Writer> writerList = new ArrayList<>();
        writerList.add(writer);

        // When
        List<WriterDto> writerDtoList = writerMapper.mapToWritersDto(writerList);

        // Then
        assertEquals(1L, (long) writerDtoList.get(0).getId());
        assertEquals("Firstname", writerDtoList.get(0).getFirstname());
        assertEquals("Surname", writerDtoList.get(0).getSurname());
        assertEquals("title", writerDtoList.get(0).getMoviesTitle().get(0));
    }
}
