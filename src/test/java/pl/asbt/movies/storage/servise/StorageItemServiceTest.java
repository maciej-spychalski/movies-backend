package pl.asbt.movies.storage.servise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.repository.StorageItemRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageItemServiceTest {

    @Autowired
    StorageItemService storageItemService;

    @Autowired
    StorageItemRepository storageItemRepository;

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

    private Movie movie1;
    private Movie movie2;
    private Director director1;
    private Writer writer1;
    private Writer writer2;
    private Actor actor1;
    private Actor actor2;
    private Genre genre1;
    private Genre genre2;


    @Before
    public void createData() {
        director1 = new Director("DirectorName1", "DirectorSurname1");
        writer1 = new Writer("WriterName1", "WriterSurname1");
        writer2 = new Writer("WriterName2", "WriterSurname3");
        actor1 = new Actor("ActorName1", "ActorSurname1");
        actor2 = new Actor("ActorName2", "ActorSurname2");
        genre1 = new Genre("Comedy");
        genre2 = new Genre("Sci-fi");

        movie1 = new Movie.MovieBuilder()
                .title("Title1")
                .director(director1)
                .writer(writer1)
                .writer(writer2)
                .actor(actor1)
                .actor(actor2)
                .genre(genre1)
                .genre(genre2)
                .duration(90)
                .build();
//        /movieService.saveMovie(movie1);

        movie2 = new Movie.MovieBuilder()
                .title("Title2")
                .director(director1)
                .writer(writer1)
                .writer(writer2)
                .actor(actor1)
                .actor(actor2)
                .genre(genre1)
                .genre(genre2)
                .duration(110)
                .build();
//        movieService.saveMovie(movie2);
    }

    @After
    public void deleteData() {
        Long movie1Id = movie1.getId();
        movieService.deleteMovie(movie1Id);
        Long movie2Id = movie2.getId();
        movieService.deleteMovie(movie2Id);
        Long director1Id = director1.getId();
        directorService.deleteDirector(director1Id);
        Long writer1Id = writer1.getId();
        writerService.deleteWriter(writer1Id);
        Long writer2Id = writer2.getId();
        writerService.deleteWriter(writer2Id);
        Long actor1Id = actor1.getId();
        actorService.deleteActor(actor1Id);
        Long actor2Id = actor2.getId();
        actorService.deleteActor(actor2Id);
        Long genre1Id = genre1.getId();
        genreService.deleteGenre(genre1Id);
        Long genre2Id = genre2.getId();
        genreService.deleteGenre(genre2Id);
    }

    @Test
    public void createStorageItemTestSuite() throws SearchingException {
        // Given
        int storageItemQuantity = storageItemService.getAllStorageItems().size();

        // When
        StorageItem storageItem1 = new StorageItem(2);
        storageItem1.setMovie(movie1);
        storageItem1 = storageItemService.saveStorageItem(storageItem1);
        Long storageItem1ID = storageItem1.getId();

        // Then
        List<StorageItem> storageItems = storageItemService.getAllStorageItems();
        assertEquals(storageItemQuantity + 1, storageItems.size());

        //CleanUp
        storageItemService.deleteStorageItem(storageItem1ID);
    }

    @Test
    public void getStorageItemTestSuite() throws SearchingException {
        // Given
        StorageItem storageItem1 = new StorageItem(2);
        storageItem1.setMovie(movie1);
        storageItem1 = storageItemService.saveStorageItem(storageItem1);
        Long storageItem1ID = storageItem1.getId();

        // When
        StorageItem storageItemDB1 = storageItemService.getStorageItem(storageItem1ID).orElse(new StorageItem());

        // Then
        assertEquals(2, (int) storageItemDB1.getQuantity());
        assertEquals(movie1.getTitle(), storageItemDB1.getMovie().getTitle());

        //CleanUp
        storageItemService.deleteStorageItem(storageItem1ID);
    }

    @Test
    public void getStorageItemByMovieTitle() throws SearchingException {
        // Given
        StorageItem storageItem1 = new StorageItem(2);
        storageItem1.setMovie(movie1);
        storageItem1 = storageItemService.saveStorageItem(storageItem1);
        Long storageItem1ID = storageItem1.getId();

        StorageItem storageItem2 = new StorageItem(4);
        storageItem2.setMovie(movie2);
        // ToDo: Zgłasza wyjątek bez adnotacji @Transactional ???
        storageItem2 = storageItemService.saveStorageItem(storageItem2);
        Long storageItem2ID = storageItem2.getId();

        // When
        List<StorageItem> storageItems = storageItemService.getStorageItemsByMovieTitle(movie2.getTitle());

        // Then
        assertEquals(4, (int) storageItems.get(0).getQuantity());
        assertEquals(movie2.getTitle(), storageItems.get(0).getMovie().getTitle());

        //CleanUp
        storageItemService.deleteStorageItem(storageItem1ID);
        storageItemService.deleteStorageItem(storageItem2ID);
    }

    @Test
    public void getAllStorageItemsTestSuite() throws SearchingException {
        // Given
        StorageItem storageItem1 = new StorageItem(2);
        storageItem1.setMovie(movie1);
        storageItem1 = storageItemService.saveStorageItem(storageItem1);
        Long storageItem1ID = storageItem1.getId();

        StorageItem storageItem2 = new StorageItem(4);
        storageItem2.setMovie(movie2);
        storageItem2 = storageItemService.saveStorageItem(storageItem2);
        Long storageItem2ID = storageItem2.getId();

        // When
        List<StorageItem> storageItems = storageItemService.getAllStorageItems();

        // Then
        assertTrue(storageItems.size() > 1);

        //CleanUp
        storageItemService.deleteStorageItem(storageItem1ID);
        storageItemService.deleteStorageItem(storageItem2ID);
    }

    @Test
    public void deleteStorageItem() throws SearchingException {
        // Given
        StorageItem storageItem1 = new StorageItem(2);
        storageItem1.setMovie(movie1);
        storageItem1 = storageItemService.saveStorageItem(storageItem1);
        Long storageItem1ID = storageItem1.getId();

        StorageItem storageItem2 = new StorageItem(4);
        storageItem2.setMovie(movie2);
        storageItem2 = storageItemService.saveStorageItem(storageItem2);
        Long storageItem2ID = storageItem2.getId();

        int storageItemsQuantity = storageItemService.getAllStorageItems().size();

        // When
        storageItemService.deleteStorageItem(storageItem1ID);
        storageItemService.deleteStorageItem(storageItem2ID);

        // Then
        assertEquals(storageItemsQuantity - 2, storageItemService.getAllStorageItems().size());

        //CleanUp
        // todo: z jakiegoś powodu nie kasuje tego ???
        // todo: po przerzuceniu wyrzej kasuje ale nie kasuje tego co jest zapisane w @After
//        storageItemService.deleteStorageItem(storageItem2ID);
    }

    @Test
    public void deleteStorageItemsByMovieTitle() throws SearchingException {
        // Given
        StorageItem storageItem1 = new StorageItem(2);
        storageItem1.setMovie(movie1);
        storageItem1 = storageItemService.saveStorageItem(storageItem1);
        Long storageItem1ID = storageItem1.getId();

        StorageItem storageItem2 = new StorageItem(4);
        storageItem2.setMovie(movie2);
        storageItem2 = storageItemService.saveStorageItem(storageItem2);
        Long storageItem2ID = storageItem2.getId();

        int storageItemsQuantity = storageItemService.getAllStorageItems().size();

        // When
        storageItemService.deleteStorageItemByMovieTitle(movie1.getTitle());
        storageItemService.deleteStorageItem(storageItem2ID);

        // Then
        assertEquals(storageItemsQuantity - 2, storageItemService.getAllStorageItems().size());

        //CleanUp
        // todo: z jakiegoś powodu nie kasuje tego ???
        // todo: po przerzuceniu wyrzej kasuje ale nie kasuje tego co jest zapisane w @After
//        storageItemService.deleteStorageItem(storageItem2ID);
    }

    @Test
    public void updateStorageItem() throws SearchingException {
        // Given
        StorageItem storageItem1 = new StorageItem(2);
        storageItem1.setMovie(movie1);
        storageItem1 = storageItemService.saveStorageItem(storageItem1);
        Long storageItem1ID = storageItem1.getId();

        StorageItemDto storageItemDto2 = new StorageItemDto(1L, movie1.getTitle(), movie1.getId(),4);

        // When
        storageItemService.updateStorageItem(storageItemDto2);

        // Then
        StorageItem theStorageItem = storageItemService.getStorageItem(storageItem1ID).orElse(new StorageItem());
        assertEquals(storageItemDto2.getQuantity(), theStorageItem.getQuantity());

        //CleanUp
        storageItemService.deleteStorageItem(storageItem1ID);
    }
}
