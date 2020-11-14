package pl.asbt.movies.storage.servise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.exception.SearchingException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageItemTest {

//    @InjectMocks
//    private StorageItemService storageItemService;
//
//    @Mock
//    private MovieService movieService;

    @Autowired
    StorageItemService storageItemService;

    @Autowired
    DirectorService directorService;

    @Autowired
    WriterService writerService;

    @Autowired
    ActorService actorService;

    @Autowired
    GenreService genreService;

    @Autowired
    MovieService movieService;
/*
List<String> movies = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto(1L, "Name1", "Surname1", movies);
        int directorQuantity = directorService.getAllDirectors().size();

        // When
        Director director1 = directorService.createDirector(directorDto);
*/
    @Test
    public void createStorageItemTestSuite() throws SearchingException {
        // Given
        List<String> movies = new ArrayList<>();

        DirectorDto directorDto1 = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();

        WriterDto writerDto1 = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1Id = writer1.getId();
        writerDto1 = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto1);

        ActorDto actorDto1 = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        actorDto1 = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
        List<ActorDto> actorsDto =  new ArrayList<>();
        actorsDto.add(actorDto1);

        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1ID = genre1.getId();
        genreDto1 = new GenreDto(genre1ID, "Comedy", movies);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto1);

        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto1, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        movieDto1 = new MovieDto(movie1ID, "Title1", directorDto1, writersDto, actorsDto,genresDto, 90);

        int storageItemQuantity = storageItemService.getAllStorageItems().size();

        // When
        StorageItemDto storageItemDto1 = new StorageItemDto(1L, movieDto1.getTitle(), movie1ID, 2);
        StorageItem storageItem1 = storageItemService.createStorageItem(storageItemDto1);


        // Then
        List<StorageItem> storageItems = storageItemService.getAllStorageItems();
        Long storageItem1ID = storageItem1.getId();
        assertEquals(storageItemQuantity + 1, storageItems.size());

        //CleanUp
        directorService.deleteDirector(director1ID);
        writerService.deleteWriter(writer1Id);
        actorService.deleteActor(actor1ID);
        genreService.deleteGenre(genre1ID);
        movieService.deleteMovie(movie1ID);

    }

}

/*
@Test
    public void createActorTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        ActorDto actorDto = new ActorDto(1L, "Name1", "Surname1", movies);
        int actorsQuantity = actorService.getAllActors().size();

        // When
        Actor actor1 = actorService.createActor(actorDto);

        // Then
        List<Actor> actors = actorService.getAllActors();
        long actor1Id = actor1.getId();
        assertEquals(actorsQuantity + 1, actors.size());

        // CleanUp
        actorService.deleteActor(actor1Id);
    }

    @Test
    public void getActorTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        ActorDto actorDto1 = new ActorDto(1L, "Name1", "Surname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();

        // When
        Actor actorDB1 = actorService.getActor(actor1ID).orElse(new Actor());

        // Then
        assertEquals("Name1", actorDB1.getFirstname());
        assertEquals("Surname1", actorDB1.getSurname());

        // CleanUp
        actorService.deleteActor(actor1ID);
    }

    @Test
    public void getActorsByNameAndSurnameTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        ActorDto actorDto1 = new ActorDto(1L, "Name1", "Surname1", movies);
        ActorDto actorDto2 = new ActorDto(1L, "Name2", "Surname2", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        Actor actor2 = actorService.createActor(actorDto2);
        Long actor2ID = actor2.getId();

        // When
        List<Actor> actors;
        actors = actorService.getActorsByNameAndSurname(actor2.getFirstname(), actor2.getSurname());

        // Then
        assertEquals("Name2", actors.get(0).getFirstname());
        assertEquals("Surname2", actors.get(0).getSurname());
        assertTrue(actors.size() > 0);

        // CleanUp
        actorService.deleteActor(actor1ID);
        actorService.deleteActor(actor2ID);
    }

    @Test
    public void getAllActorsTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        ActorDto actorDto1 = new ActorDto(1L, "Name1", "Surname1", movies);
        ActorDto actorDto2 = new ActorDto(1L, "Name2", "Surname2", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        Actor actor2 = actorService.createActor(actorDto2);
        Long actor2ID = actor2.getId();

        // When
        List<Actor> actors;
        actors = actorService.getAllActors();

        // Then
        assertTrue(actors.size() > 1);

        // CleanUp
        actorService.deleteActor(actor1ID);
        actorService.deleteActor(actor2ID);
    }

    @Test
    public void deleteActorTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        ActorDto actorDto1 = new ActorDto(1L, "Name1", "Surname1", movies);
        ActorDto actorDto2 = new ActorDto(1L, "Name2", "Surname2", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        Actor actor2 = actorService.createActor(actorDto2);
        Long actor2ID = actor2.getId();
        int actorsNumber = actorService.getAllActors().size();

        // When
        actorService.deleteActor(actor1ID);

        // Then
        assertEquals(actorsNumber -1 , actorService.getAllActors().size());

        // CleanUp
        actorService.deleteActor(actor2ID);
    }

    @Test
    public void deleteActorsByNameAndSurnameTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        ActorDto actorDto1 = new ActorDto(1L, "Name1", "Surname1", movies);
        ActorDto actorDto2 = new ActorDto(1L, "Name2", "Surname2", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        Actor actor2 = actorService.createActor(actorDto2);
        //Long actor2ID = actor2.getId();
        int actorsNumber = actorService.getAllActors().size();

        // When
        actorService.deleteActorsByNameAndSurname(actor2.getFirstname(), actor2.getSurname());

        // Then
        assertEquals(actorsNumber -1 , actorService.getAllActors().size());

        // CleanUp
        actorService.deleteActor(actor1ID);
    }

    @Test
    public void updateActorTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        ActorDto actorDto1 = new ActorDto(1L, "Name1", "Surname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        ActorDto actorDto2 = new ActorDto(actor1ID, "Name2", "Surname2", movies);

        // When
        actorService.updateActor(actorDto2);

        // Then
        Actor actor2 = actorService.getActor(actor1ID).orElse(new Actor());
        assertEquals("Name2", actor2.getFirstname());
        assertEquals("Surname2", actor2.getSurname());

        // CleanUp
        actorService.deleteActor(actor1ID);
    }
 */