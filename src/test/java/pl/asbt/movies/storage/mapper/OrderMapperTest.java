package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.dto.OrderDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void mapToOrderTest() {
        // Given
        ItemDto itemDto =  new ItemDto(1L, "Title", 1L, 1, new BigDecimal(1));
        List<ItemDto> itemsDto = new ArrayList<>();
        itemsDto.add(itemDto);
        OrderDto orderDto = new OrderDto(1L, false, itemsDto, new BigDecimal(10));

        // When
        Order order = orderMapper.mapToOrder(orderDto);

        // Then
        assertEquals((new Order()).getClass(), order.getClass());
    }

    @Test
    public void mapToOrderDtoTest() {
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
        List<Order> orders = new ArrayList<>();
        User user = new User(1L, "firstname", "surname", "email", "password", false, false, cart, orders);
        order = new Order(1L, false, items, user, new BigDecimal(10));

        // When
        OrderDto orderDto = orderMapper.mapToOrderDto(order);

        // Then
        assertEquals(1L, (long) orderDto.getId());
        assertEquals(false, orderDto.getIsFinalized());
        assertEquals(1, orderDto.getItemsDto().size());
        assertEquals(new BigDecimal(10), orderDto.getPrice());
    }

    @Test
    public void mapToOrdersDtoTest() {
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
        List<Order> orders = new ArrayList<>();
        User user = new User(1L, "firstname", "surname", "email", "password", false, false, cart, orders);
        order = new Order(1L, false, items, user, new BigDecimal(10));
        orders.add(order);

        // When
        List<OrderDto> ordersDto = orderMapper.mapToOrdersDto(orders);

        // Then
        assertEquals(1L, (long) ordersDto.get(0).getId());
        assertEquals(false, ordersDto.get(0).getIsFinalized());
        assertEquals(1, ordersDto.get(0).getItemsDto().size());
        assertEquals(new BigDecimal(10), ordersDto.get(0).getPrice());
    }

}
