package pl.asbt.movies.storage.servise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.dto.StorageItemDto;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

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

    @Autowired
    StorageItemService storageItemService;

    @Autowired
    ItemService itemService;

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
    private StorageItem storageItem1;
    private StorageItem storageItem2;

    @Before
    public void createData() {
        director1 = new Director("DirectorName31", "DirectorSurname31");
        director2 = new Director("DirectorName32", "DirectorSurname32");
        writer1 = new Writer("WriterName31", "WriterSurname31");
        writer2 = new Writer("WriterName32", "WriterSurname32");
        actor1 = new Actor("ActorName31", "ActorSurname31");
        actor2 = new Actor("ActorName32", "ActorSurname32");
        genre1 = new Genre("Type31");
        genre2 = new Genre("Type32");

        movie1 = new Movie.MovieBuilder()
                .title("Title31")
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
        movie1 = movieService.saveMovie(movie1);
        StorageItemDto storageItemDto1 = new StorageItemDto(0L,"Title31", 0L, 100);
        storageItem1 = storageItemService.saveStorageItem(storageItemDto1);

        movie2 = new Movie.MovieBuilder()
                .title("Title32")
                .director(director2)
                .writer(writer2)
                .actor(actor2)
                .genre(genre2)
                .duration(110)
                .price(new BigDecimal(20.00))
                .build();

        director2.getMovies().add(movie2);
        writer2.getMovies().add(movie2);
        actor2.getMovies().add(movie2);
        genre2.getMovies().add(movie2);
        movie2 = movieService.saveMovie(movie2);
        StorageItemDto storageItemDto2 = new StorageItemDto(0L,"Title32", 0L, 100);
        storageItem2 = storageItemService.saveStorageItem(storageItemDto2);
    }

    @After
    public void removeDate() {
        Long storageItem1Id = storageItem1.getId();
        storageItemService.deleteStorageItem(storageItem1Id);
        Long storageItem2Id = storageItem2.getId();
        storageItemService.deleteStorageItem(storageItem2Id);
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
    public void saveItemTestSuite() {
        // Given
        ItemDto itemDto = new ItemDto(0L, movie1.getTitle(), movie1.getId(), 10, new BigDecimal(10));
        int itemsQuantity = itemService.getAllItems().size();

        // When
        Item item = itemService.saveItem(itemDto);

        // Then
        Long itemId = item.getId();
        List<Item> items = itemService.getAllItems();
        assertEquals(itemsQuantity + 1, items.size());

        // CleanUp
        itemService.deleteItem(itemId);
    }

    @Test
    public void addQuantityTestSuite() {
        // Given
        ItemDto itemDto = new ItemDto(0L, movie1.getTitle(), movie1.getId(), 10, new BigDecimal(10));
        Item item = itemService.saveItem(itemDto);
        Long itemId = item.getId();

        // When
        itemService.addQuantity(itemId, 10);

        // Then
        Item theItem = itemService.getItem(itemId).orElse(new Item());
        assertEquals(20, (int) theItem.getQuantity());

        // CleanUp
        itemService.deleteItem(itemId);
    }

    @Test
    public void subQuantityTestSuite() {
        // Given
        ItemDto itemDto = new ItemDto(0L, movie1.getTitle(), movie1.getId(), 20, new BigDecimal(10));
        Item item = itemService.saveItem(itemDto);
        Long itemId = item.getId();

        // When
        itemService.subQuantity(itemId, 10);

        // Then
        Item theItem = itemService.getItem(itemId).orElse(new Item());
        assertEquals(10, (int) theItem.getQuantity());

        // CleanUp
        itemService.deleteItem(itemId);
    }

    @Test
    public void getItemTestSuite() {
        // Given
        ItemDto itemDto = new ItemDto(0L, movie1.getTitle(), movie1.getId(), 10, new BigDecimal(10));
        Item item = itemService.saveItem(itemDto);
        Long itemId = item.getId();

        // When

        // Then
        Item theItem = itemService.getItem(itemId).orElse(new Item());
        assertEquals(10, (int) theItem.getQuantity());
        assertEquals(movie1.getTitle(), theItem.getMovie().getTitle());

        // CleanUp
        itemService.deleteItem(itemId);
    }

    @Test
    public void getAllItemTestSuite() {
        // Given
        ItemDto itemDto1 = new ItemDto(0L, movie1.getTitle(), movie1.getId(), 10, new BigDecimal(10));
        ItemDto itemDto2 = new ItemDto(0L, movie2.getTitle(), movie2.getId(), 20, new BigDecimal(20));
        Item item1 = itemService.saveItem(itemDto1);
        Long item1Id = item1.getId();
        Item item2 = itemService.saveItem(itemDto2);
        Long item2Id = item2.getId();

        // When
        List<Item> items = itemService.getAllItems();

        // Then
        assertTrue(items.size() > 1);

        // CleanUp
        itemService.deleteItem(item1Id);
        itemService.deleteItem(item2Id);
    }

    @Test
    public void deleteItemTestSuite() {
        // Given
        ItemDto itemDto1 = new ItemDto(0L, movie1.getTitle(), movie1.getId(), 10, new BigDecimal(10));
        ItemDto itemDto2 = new ItemDto(0L, movie2.getTitle(), movie2.getId(), 20, new BigDecimal(20));
        Item item1 = itemService.saveItem(itemDto1);
        Long item1Id = item1.getId();
        Item item2 = itemService.saveItem(itemDto2);
        Long item2Id = item2.getId();
        int itemsQuantity = itemService.getAllItems().size();

        // When
        itemService.deleteItem(item1Id);

        // Then
        List<Item> items = itemService.getAllItems();
        assertEquals(itemsQuantity -1, items.size());

        // CleanUp
        itemService.deleteItem(item2Id);
    }

    @Test
    public void updateItemTestSuite() {
        // Given
        ItemDto itemDto1 = new ItemDto(0L, movie1.getTitle(), movie1.getId(), 10, new BigDecimal(10));
        Item item = itemService.saveItem(itemDto1);
        Long item1Id = item.getId();
        ItemDto itemDto2 = new ItemDto(item1Id,movie1.getTitle(), movie1.getId(), 20, new BigDecimal(10));

        // When
        itemService.updateItem(itemDto2);

        // Then
        Item theItem = itemService.getItem(item1Id).orElse(new Item());
        assertEquals(20, (int) theItem.getQuantity());

        // CleanUp
        itemService.deleteItem(item1Id);
    }
}
