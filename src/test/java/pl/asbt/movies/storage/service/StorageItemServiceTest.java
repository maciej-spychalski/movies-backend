package pl.asbt.movies.storage.service;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.StorageItemDto;
import pl.asbt.movies.storage.repository.StorageItemRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    private Director director2;
    private Writer writer1;
    private Writer writer2;
    private Actor actor1;
    private Actor actor2;
    private Genre genre1;
    private Genre genre2;

    @Before
    public void createData() {
        director1 = new Director("DirectorName21", "DirectorSurname21");
        director2 = new Director("DirectorName22", "DirectorSurname22");
        writer1 = new Writer("WriterName21", "WriterSurname21");
        writer2 = new Writer("WriterName22", "WriterSurname23");
        actor1 = new Actor("ActorName21", "ActorSurname21");
        actor2 = new Actor("ActorName22", "ActorSurname22");
        genre1 = new Genre("Type21");
        genre2 = new Genre("Type22");

        movie1 = new Movie.MovieBuilder()
                .title("Title21")
                .director(director1)
                .writer(writer1)
                .actor(actor1)
                .genre(genre1)
                .duration(90)
                .price(new BigDecimal(10.00))
                .build();

        director1.getMovies().add(movie1);
        writer1.getMovies().add(movie1);
        actor1.getMovies().add(movie1);
        genre1.getMovies().add(movie1);

        movieService.saveMovie(movie1);

        movie2 = new Movie.MovieBuilder()
                .title("Title22")
                .director(director2)
                .writer(writer2)
                .actor(actor2)
                .genre(genre2)
                .duration(110)
                .price(new BigDecimal(10.00))
                .build();

        director2.getMovies().add(movie2);
        writer2.getMovies().add(movie2);
        actor2.getMovies().add(movie2);
        genre2.getMovies().add(movie2);

        movieService.saveMovie(movie2);
    }

    @After
    public void deleteData() {
        Long movie1Id = movie1.getId();
        movieService.deleteMovie(movie1Id);
        Long movie2Id = movie2.getId();
        movieService.deleteMovie(movie2Id);
        Long director1Id = director1.getId();
        directorService.deleteDirector(director1Id);
        Long director2Id = director2.getId();
        directorService.deleteDirector(director2Id);
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
    public void createStorageItemTestSuite() {
        // Given
        int storageItemQuantity = storageItemService. getAllStorageItems().size();

        // When
        StorageItemDto storageItemDto1 = new StorageItemDto(1L,"Title21", 1L, 2);

        StorageItem storageItem1 = storageItemService.saveStorageItem(storageItemDto1);
        Long storageItem1Id = storageItem1.getId();

        // Then
        List<StorageItem> storageItems = storageItemService.getAllStorageItems();
        assertEquals(storageItemQuantity + 1, storageItems.size());

        // CleanUp
        storageItemService.deleteStorageItem(storageItem1Id);
    }

    @Test
    public void getStorageItemTestSuite() {
        // Given
        StorageItemDto storageItemDto1 = new StorageItemDto(0L,"Title21", 0L, 2);
        StorageItem storageItem1 = storageItemService.saveStorageItem(storageItemDto1);
        Long storageItem1Id = storageItem1.getId();

        // When
        StorageItem storageItemDB1 = storageItemService.getStorageItem(storageItem1Id).orElse(new StorageItem());

        // Then
        assertEquals(2, (int) storageItemDB1.getQuantity());
        assertEquals(movie1.getTitle(), storageItemDB1.getMovie().getTitle());

        // CleanUp
        storageItemService.deleteStorageItem(storageItem1Id);
    }

    @Test
    public void getStorageItemByMovieTitle() {
        // Given
        StorageItemDto storageItemDto1 = new StorageItemDto(0L,"Title21", 0L, 2);
        StorageItem storageItem1 = storageItemService.saveStorageItem(storageItemDto1);
        Long storageItem1Id = storageItem1.getId();

        StorageItemDto storageItemDto2 = new StorageItemDto(0L,"Title22", 0L, 4);
        StorageItem storageItem2 = storageItemService.saveStorageItem(storageItemDto2);;
        Long storageItem2Id = storageItem2.getId();

        // When
        List<StorageItem> storageItems = storageItemService.getStorageItemsByMovieTitle("Title22");

        // Then
        assertEquals(4, (int) storageItems.get(0).getQuantity());
        assertEquals(movie2.getTitle(), storageItems.get(0).getMovie().getTitle());

        // CleanUp
        storageItemService.deleteStorageItem(storageItem1Id);
        storageItemService.deleteStorageItem(storageItem2Id);
    }

    @Test
    public void getAllStorageItemsTestSuite() {
        // Given
        StorageItemDto storageItemDto1 = new StorageItemDto(0L,"Title21", 0L, 2);
        StorageItem storageItem1 = storageItemService.saveStorageItem(storageItemDto1);
        Long storageItem1Id = storageItem1.getId();

        StorageItemDto storageItemDto2 = new StorageItemDto(0L,"Title22", 0L, 4);
        StorageItem storageItem2 = storageItemService.saveStorageItem(storageItemDto2);;
        Long storageItem2Id = storageItem2.getId();

        // When
        List<StorageItem> storageItems = storageItemService.getAllStorageItems();

        // Then
        assertTrue(storageItems.size() > 1);

        // CleanUp
        storageItemService.deleteStorageItem(storageItem1Id);
        storageItemService.deleteStorageItem(storageItem2Id);
    }

    @Test
    public void deleteStorageItem() {
        // Given
        StorageItemDto storageItemDto1 = new StorageItemDto(0L,"Title21", 0L, 2);
        StorageItem storageItem1 = storageItemService.saveStorageItem(storageItemDto1);
        Long storageItem1Id = storageItem1.getId();

        StorageItemDto storageItemDto2 = new StorageItemDto(0L,"Title22", 0L, 4);
        StorageItem storageItem2 = storageItemService.saveStorageItem(storageItemDto2);;
        Long storageItem2Id = storageItem2.getId();;

        int storageItemsQuantity = storageItemService.getAllStorageItems().size();

        // When
        storageItemService.deleteStorageItem(storageItem1Id);

        // Then
        assertEquals(storageItemsQuantity - 1, storageItemService.getAllStorageItems().size());

        // CleanUp
        storageItemService.deleteStorageItem(storageItem2Id);
    }

    @Test
    public void deleteStorageItemsByMovieTitle() {
        // Given
        StorageItemDto storageItemDto1 = new StorageItemDto(0L,"Title21", 0L, 2);
        StorageItem storageItem1 = storageItemService.saveStorageItem(storageItemDto1);
        Long storageItem1Id = storageItem1.getId();

        StorageItemDto storageItemDto2 = new StorageItemDto(0L,"Title22", 0L, 4);
        StorageItem storageItem2 = storageItemService.saveStorageItem(storageItemDto2);;
        Long storageItem2Id = storageItem2.getId();

        int storageItemsQuantity = storageItemService.getAllStorageItems().size();

        // When
        storageItemService.deleteStorageItemByMovieTitle(movie1.getTitle());

        // Then
        assertEquals(storageItemsQuantity - 1, storageItemService.getAllStorageItems().size());

        // CleanUp
        storageItemService.deleteStorageItem(storageItem2Id);
    }

    @Test
    public void updateStorageItem() {
        // Given
        StorageItemDto storageItemDto1 = new StorageItemDto(0L,"Title21", 0L, 2);
        StorageItem storageItem1 = storageItemService.saveStorageItem(storageItemDto1);
        Long storageItem1Id = storageItem1.getId();
        Long movieId = storageItem1.getMovie().getId();

        StorageItemDto storageItemDto2 = new StorageItemDto(storageItem1Id, "Title22", movieId,4);

        // When
        storageItemService.updateStorageItem(storageItemDto2);

        // Then
        StorageItem theStorageItem = storageItemService.getStorageItem(storageItem1Id).orElse(new StorageItem());
        assertEquals(storageItemDto2.getQuantity(), theStorageItem.getQuantity());

        // CleanUp
        storageItemService.deleteStorageItem(storageItem1Id);
    }

}
