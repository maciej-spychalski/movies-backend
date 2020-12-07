package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.ActorDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActorMapperTest {

    @Autowired
    private ActorMapper actorMapper;

    @Test
    public void mapToActorTest() {
        // Given
        List<String> movieTitles = new ArrayList<>();
        ActorDto actorDto = new ActorDto(1L, "Firstname", "Surname", movieTitles);

        // When
        Actor actor = actorMapper.mapToActor(actorDto);

        // Then
        assertEquals("Firstname", actor.getFirstname());
        assertEquals("Surname", actor.getSurname());
    }

    @Test
    public void mapToActorDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie("title", director, writers, actors, genres, 90, new BigDecimal(10));
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        Actor actor = new Actor(1L, "Firstname", "Surname", movies);

        // When
        ActorDto actorDto = actorMapper.mapToActorDto(actor);

        // Then
        assertEquals(1L, (long) actorDto.getId());
        assertEquals("Firstname", actorDto.getFirstname());
        assertEquals("Surname", actorDto.getSurname());
        assertEquals("title", actorDto.getMoviesTitle().get(0));
    }

    @Test
    public void mapToActorsDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie("title", director, writers, actors, genres, 90, new BigDecimal(10));
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        Actor actor = new Actor(1L, "Firstname", "Surname", movies);
        List<Actor> actorList = new ArrayList<>();
        actorList.add(actor);

        // When
        List<ActorDto> actorDtoList = actorMapper.mapToActorsDto(actorList);

        // Then
        assertEquals(1L, (long) actorDtoList.get(0).getId());
        assertEquals("Firstname", actorDtoList.get(0).getFirstname());
        assertEquals("Surname", actorDtoList.get(0).getSurname());
        assertEquals("title", actorDtoList.get(0).getMoviesTitle().get(0));
    }
}
