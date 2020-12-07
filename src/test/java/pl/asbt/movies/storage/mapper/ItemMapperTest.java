package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.ItemDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemMapperTest {

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void mapToItemTest() {
        // Given
        ItemDto itemDto = new ItemDto(1L, "title", 1L, 10, new BigDecimal(10));

        // When
        Item item = itemMapper.mapToItem(itemDto);

        // Then
        assertEquals(10, (int) item.getQuantity());
    }

    @Test
    public void mapToItemDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Cart cart = new Cart();
        Order order = new Order();
        Movie movie = new Movie(1L,"title", director, writers, actors, genres, 90, new BigDecimal(10));
        Item item =  new Item(1L, movie, 1, cart, order, new BigDecimal(10));

        // When
        ItemDto itemDto = itemMapper.mapToItemDto(item);

        // Then
        assertEquals(1L, (long) itemDto.getId());
        assertEquals("title", itemDto.getMovieTitle());
        assertEquals(1L, (long) itemDto.getMovieId());
        assertEquals(1, (int) itemDto.getQuantity());
        assertEquals(new BigDecimal(10), itemDto.getPrice());
    }

    @Test
    public void mapToItemsDtoTest() {
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

        // When
        List<ItemDto> itemsDto = itemMapper.mapToItemsDto(items);

        // Then
        assertEquals(1L, (long) itemsDto.get(0).getId());
        assertEquals("title", itemsDto.get(0).getMovieTitle());
        assertEquals(1L, (long) itemsDto.get(0).getMovieId());
        assertEquals(1, (int) itemsDto.get(0).getQuantity());
        assertEquals(new BigDecimal(10), itemsDto.get(0).getPrice());
    }
}
