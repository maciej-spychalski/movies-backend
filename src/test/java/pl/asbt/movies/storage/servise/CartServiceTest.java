package pl.asbt.movies.storage.servise;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.dto.StorageItemDto;
import pl.asbt.movies.storage.exception.StorageException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {

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

    @Autowired
    CartService cartService;

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
    private Item item1;
    private Item item2;

    @Before
    public void createData() {
        director1 = new Director("DirectorName41", "DirectorSurname41");
        director2 = new Director("DirectorName42", "DirectorSurname42");
        writer1 = new Writer("WriterName41", "WriterSurname41");
        writer2 = new Writer("WriterName42", "WriterSurname42");
        actor1 = new Actor("ActorName41", "ActorSurname41");
        actor2 = new Actor("ActorName42", "ActorSurname42");
        genre1 = new Genre("Type41");
        genre2 = new Genre("Type42");

        movie1 = new Movie.MovieBuilder()
                .title("Title41")
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
        StorageItemDto storageItemDto1 = new StorageItemDto(0L, "Title41", 0L, 100);
        storageItem1 = storageItemService.saveStorageItem(storageItemDto1);
        ItemDto itemDto1 = new ItemDto(0L, movie1.getTitle(), movie1.getId(), 10, new BigDecimal(0));
        item1 = itemService.saveItem(itemDto1);

        movie2 = new Movie.MovieBuilder()
                .title("Title42")
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
        StorageItemDto storageItemDto2 = new StorageItemDto(0L, "Title42", 0L, 100);
        storageItem2 = storageItemService.saveStorageItem(storageItemDto2);

        ItemDto itemDto2 = new ItemDto(0L, movie2.getTitle(), movie2.getId(), 10, new BigDecimal(0));
        item2 = itemService.saveItem(itemDto2);
    }

    @After
    public void removeDate() {
        Long item1Id = item1.getId();
        itemService.deleteItem(item1Id);
        Long item2Id = item2.getId();
        itemService.deleteItem(item2Id);
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

    @Ignore
    @Test
    public void saveCartTestSuite() {
        // Given
        List<Item> items = new ArrayList<>();
        BigDecimal price = new BigDecimal(0);
        Cart cart1 = new Cart(0L, items, price);
        int cartsQuantity = cartService.getAllCarts().size();

        // When
        cart1 = cartService.saveCart(cart1);

        // Then
        List<Cart> carts = cartService.getAllCarts();
        assertTrue(carts.size() > cartsQuantity);

        // CleanUp
        Long cart1Id = cart1.getId();
        cartService.deleteCart(cart1Id);
    }

    @Ignore
    @Transactional
    @Test
    public void getCartTestSuite() {
        // Given
        List<Item> items = new ArrayList<>();
        BigDecimal price = new BigDecimal(10);
        Cart cart1 = new Cart(0L, items, price);
        cart1 = cartService.saveCart(cart1);
        Long cart1Id = cart1.getId();

        // When
        Cart theCart = cartService.getCart(cart1Id).orElse(new Cart());

        // Then
        assertEquals(0, theCart.getItems().size());
        assertEquals(new BigDecimal(10), theCart.getPrice());

        // CleanUp
        cartService.deleteCart(cart1Id);
    }

    @Ignore
    @Test
    public void getAllCartsTestSuite() {
        // Given
        List<Item> items = new ArrayList<>();
        BigDecimal price = new BigDecimal(10);
        Cart cart1 = new Cart(0L, items, price);
        cart1 = cartService.saveCart(cart1);
        Long cart1Id = cart1.getId();

        // When
        List<Cart> carts = cartService.getAllCarts();

        // Then
        assertTrue(carts.size() > 0);

        // CleanUp
        cartService.deleteCart(cart1Id);
    }

    @Ignore
    @Test
    public void deleteCartTestSuite() {
        // Given
        List<Item> items = new ArrayList<>();
        BigDecimal price = new BigDecimal(10);
        Cart cart1 = new Cart(0L, items, price);
        cart1 = cartService.saveCart(cart1);
        Long cart1Id = cart1.getId();
        Cart cart2 = new Cart(0L, items, price);
        cart2 = cartService.saveCart(cart2);
        Long cart2Id = cart2.getId();
        int cartsQuantity = cartService.getAllCarts().size();

        // When
        cartService.deleteCart(cart1Id);

        // Then
        List<Cart> carts = cartService.getAllCarts();
        assertTrue(carts.size() < cartsQuantity);

        // CleanUp
        cartService.deleteCart(cart2Id);
    }

    @Ignore
    @Transactional
    @Test
    public void addItemTestSuite() throws StorageException {
        // Given
        List<Item> items = new ArrayList<>();
        BigDecimal price = new BigDecimal(0);
        Cart cart1 = new Cart(0L, items, price);
        cart1 = cartService.saveCart(cart1);
        Long cart1Id = cart1.getId();

        // When
        cart1 = cartService.addItem(cart1Id, item1.getId());

        // Then
        assertEquals(1, cart1.getItems().size());
        assertEquals(item1.getPrice(), cart1.getPrice());

        // CleanUp
        cartService.deleteItem(cart1Id, item1.getId());
        cartService.deleteCart(cart1Id);
    }

    @Ignore
    @Transactional
    @Test
    public void deleteItemTestSuite() throws StorageException {
        // Given
        List<Item> items = new ArrayList<>();
        BigDecimal price = new BigDecimal(0);
        Cart cart1 = new Cart(0L, items, price);
        cart1 = cartService.saveCart(cart1);
        Long cart1Id = cart1.getId();
        cartService.addItem(cart1Id, item1.getId());
        cart1 = cartService.addItem(cart1Id, item2.getId());
        int itemsQuantity = cart1.getItems().size();

        // When
        cart1 = cartService.deleteItem(cart1Id, item1.getId());

        // Then
        assertEquals(itemsQuantity - 1, cart1.getItems().size());

        // CleanUp
        cartService.deleteItem(cart1Id, item2.getId());
        cartService.deleteCart(cart1Id);
    }

    @Ignore
    @Test
    public void createOrderTestSuite() {
    }

    @Ignore
    @Transactional
    @Test
    public void updateCartPriceTestSuite() throws StorageException {
        // Given
        List<Item> items = new ArrayList<>();
        BigDecimal price = new BigDecimal(0);
        Cart cart1 = new Cart(0L, items, price);
        cart1 = cartService.saveCart(cart1);
        Long cart1Id = cart1.getId();
        cartService.addItem(cart1Id, item1.getId());
        cartService.addItem(cart1Id, item2.getId());
        itemService.updateItem(new ItemDto(item1.getId(), movie1.getTitle(), movie1.getId(), 5, new BigDecimal(10)));

        // When
        cart1 = cartService.updateCartPrice(cart1Id);

        // Then
        assertEquals(new BigDecimal(250), cart1.getPrice());

        // CleanUp
        cartService.deleteItem(cart1Id, item1.getId());
        cartService.deleteItem(cart1Id, item2.getId());
        cartService.deleteCart(cart1Id);
    }
}
