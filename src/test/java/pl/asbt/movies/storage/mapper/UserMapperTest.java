package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.CartDto;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.dto.OrderDto;
import pl.asbt.movies.storage.dto.UserDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void mapToUserTest() {
        // Given
        ItemDto itemDto =  new ItemDto(1L, "Title", 1L, 1, new BigDecimal(1));
        List<ItemDto> itemsDto = new ArrayList<>();
        itemsDto.add(itemDto);
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(1));
        List<OrderDto> ordersDto = new ArrayList<>();
        UserDto userDto = new UserDto(1L, "firstname", "surname", "email",
                "password", false, false, cartDto, ordersDto);

        // When
        User user = userMapper.mapToUser(userDto);

        // Then
        assertEquals("firstname", user.getFirstname());
        assertEquals("surname", user.getSurname());
        assertEquals("email", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(false, user.getIsAdmin());
        assertEquals(false, user.getIsLogged());
    }

    @Test
    public void mapToUserDto() {
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
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, false, items, new User(), new BigDecimal(10)));

        User user = new User(1L, "firstname", "surname", "email", "password", false, false, cart, orders);

        // When
        UserDto userDto = userMapper.mapToUserDto(user);

        // Then
        assertEquals("firstname", userDto.getFirstname());
        assertEquals("surname", userDto.getSurname());
        assertEquals("email", userDto.getEmail());
        assertEquals("password", userDto.getPassword());
        assertEquals(false, userDto.getIsAdmin());
        assertEquals(false, userDto.getIsLogged());
    }

    @Test
    public void mapToUsersDto() {
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
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, false, items, new User(), new BigDecimal(10)));
        User user = new User(1L, "firstname", "surname", "email", "password", false, false, cart, orders);
        List<User> users = new ArrayList<>();
        users.add(user);

        // When
        List<UserDto> usersDto = userMapper.mapToUsersDto(users);

        // Then
        assertEquals("firstname", usersDto.get(0).getFirstname());
        assertEquals("surname", usersDto.get(0).getSurname());
        assertEquals("email", usersDto.get(0).getEmail());
        assertEquals("password", usersDto.get(0).getPassword());
        assertEquals(false, usersDto.get(0).getIsAdmin());
        assertEquals(false, usersDto.get(0).getIsLogged());
    }
}
