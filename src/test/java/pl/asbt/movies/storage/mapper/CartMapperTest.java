package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.CartDto;
import pl.asbt.movies.storage.dto.ItemDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void mapToCart() {
        // Given
        ItemDto itemDto =  new ItemDto(1L, "Title", 1L, 1, new BigDecimal(1));
        List<ItemDto> itemsDto = new ArrayList<>();
        itemsDto.add(itemDto);
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(1));

        // When
        Cart cart = cartMapper.matToCart(cartDto);

        // Then
        assertEquals((new Cart()).getClass(), cart.getClass());
    }

    @Test
    public void mapToCartDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Cart cart = new Cart();
        Order order = new Order();
        Movie movie = new Movie(1L,"title", director, writers, actors, genres, 90, new BigDecimal(10));
        Item item =  new Item(1L, movie, 1, cart, order, new BigDecimal(10));
        List<Item> items = new ArrayList<>();
        items.add(item);
        cart = new Cart(1L, items, new BigDecimal(10));

        // When
        CartDto cartDto = cartMapper.mapToCartDto(cart);

        // Then
        assertEquals(1L, (long) cartDto.getId());
        assertEquals("title", cartDto.getItemsDto().get(0).getMovieTitle());
        assertEquals(new BigDecimal(10), cartDto.getPrice());
    }

    @Test
    public void mapToCartsDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Cart cart = new Cart();
        Order order = new Order();
        Movie movie = new Movie(1L,"title", director, writers, actors, genres, 90, new BigDecimal(10));
        Item item =  new Item(1L, movie, 1, cart, order, new BigDecimal(10));
        List<Item> items = new ArrayList<>();
        items.add(item);
        cart = new Cart(1L, items, new BigDecimal(10));
        List<Cart> carts = new ArrayList<>();
        carts.add(cart);

        // When
        List<CartDto> cartsDto = cartMapper.mapToCartsDto(carts);

        // Then
        assertEquals(1, cartsDto.size());
        assertEquals(1L, (long) cartsDto.get(0).getId());
        assertEquals("title", cartsDto.get(0).getItemsDto().get(0).getMovieTitle());
        assertEquals(new BigDecimal(10), cartsDto.get(0).getPrice());
    }
}
