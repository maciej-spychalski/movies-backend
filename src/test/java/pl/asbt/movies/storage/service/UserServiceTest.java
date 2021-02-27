package pl.asbt.movies.storage.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.User;
import pl.asbt.movies.storage.dto.CartDto;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.dto.OrderDto;
import pl.asbt.movies.storage.dto.UserDto;
import pl.asbt.movies.storage.mapper.CartMapper;
import pl.asbt.movies.storage.mapper.OrderMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void saveUserTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user1Password#$39", false, false);
        int usersQuantity = userService.getAllUsers().size();

        // When
        user1 = userService.saveUser(user1);

        // Then
        List<User> users = userService.getAllUsers();
        assertTrue(users.size() > usersQuantity);

        // CleanUp
        Long user1Id = user1.getId();
        userService.deleteUser(user1Id);
    }

    @Test
    public void getUserByIdTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user1Password#$39", false, false);
        user1 = userService.saveUser(user1);
        Long user1Id = user1.getId();

        // When
        User theUser = userService.getUser(user1Id).orElse(new User());

        // Then
        assertEquals("UserName1", theUser.getFirstname());
        assertEquals("UserSurname1", theUser.getSurname());
        assertEquals("user1@gmail.com", theUser.getEmail());
        assertEquals("user1Password#$39", theUser.getPassword());
        assertEquals(false, theUser.getIsAdmin());
        assertEquals(false, theUser.getIsLogged());

        // CleanUp
        userService.deleteUser(user1Id);
    }


    @Test
    public void getUserByEmailTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user1Password#$39", false, false);
        user1 = userService.saveUser(user1);
        Long user1Id = user1.getId();

        // When
        User theUser = userService.getUser("user1@gmail.com").orElse(new User());

        // Then
        assertEquals("UserName1", theUser.getFirstname());
        assertEquals("UserSurname1", theUser.getSurname());
        assertEquals("user1@gmail.com", theUser.getEmail());
        assertEquals("user1Password#$39", theUser.getPassword());
        assertEquals(false, theUser.getIsAdmin());
        assertEquals(false, theUser.getIsLogged());

        // CleanUp
        userService.deleteUser(user1Id);
    }

    // todo: chyba do usunięcia
    @Test
    public void getUserByNameAndSurnameTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user1Password#$39", false, false);
        user1 = userService.saveUser(user1);
        Long user1Id = user1.getId();

        // When
        User theUser = userService.getUserByNameAndSurname("UserName1", "UserSurname1").get(0);

        // Then
        assertEquals("UserName1", theUser.getFirstname());
        assertEquals("UserSurname1", theUser.getSurname());
        assertEquals("user1@gmail.com", theUser.getEmail());
        assertEquals("user1Password#$39", theUser.getPassword());
        assertEquals(false, theUser.getIsAdmin());
        assertEquals(false, theUser.getIsLogged());

        // CleanUp
        userService.deleteUser(user1Id);
    }

    @Test
    public void getAllUserTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user1Password#$39", false, false);
        user1 = userService.saveUser(user1);
        Long user1Id = user1.getId();
        User user2 = new User("UserName2", "UserSurname2", "user2@gmail.com",
                "user2Password#$39", false, false);
        user2 = userService.saveUser(user2);
        Long user2Id = user2.getId();

        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertTrue(users.size() > 1);

        // CleanUp
        userService.deleteUser(user1Id);
        userService.deleteUser(user2Id);
    }

    @Test
    public void deleteUserByIdTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user1Password#$39", false, false);
        user1 = userService.saveUser(user1);
        Long user1Id = user1.getId();
        User user2 = new User("UserName2", "UserSurname2", "user2@gmail.com",
                "user2Password#$39", false, false);
        user2 = userService.saveUser(user2);
        Long user2Id = user2.getId();
        int usersQuantity = userService.getAllUsers().size();

        // When
        userService.deleteUser(user1Id);
        // Then
        List<User> users = userService.getAllUsers();
        assertEquals(usersQuantity -1, users.size());

        // CleanUp
        userService.deleteUser(user2Id);
    }

    // todo: chyba do usunięcia
    @Test
    public void deleteUserByFirstnameAndSurnameTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user1Password#$39", false, false);
        user1 = userService.saveUser(user1);
        Long user1Id = user1.getId();
        User user2 = new User("UserName2", "UserSurname2", "user2@gmail.com",
                "user2Password#$39", false, false);
        user2 = userService.saveUser(user2);
        Long user2Id = user2.getId();
        int usersQuantity = userService.getAllUsers().size();

        // When
        userService.deleteUserByFirstnameAndSurname("UserName1", "UserSurname1");

        // Then
        List<User> users = userService.getAllUsers();
        assertEquals(usersQuantity -1, users.size());

        // CleanUp
        userService.deleteUser(user2Id);
    }

    @Test
    public void updateUserTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user1Password#$39", false, false);
        user1 = userService.saveUser(user1);
        Long user1Id = user1.getId();
        List<ItemDto> items = new ArrayList<>();
        BigDecimal price = new BigDecimal(10);
        CartDto cartDto = new CartDto(user1.getCart().getId(), items, price);
        List<OrderDto> ordersDto = new ArrayList<>();
        UserDto userDto = new UserDto(user1Id, "UserName2","UserSurname2", "user2@gmail.com",
                "user2Password#$39", true, true, cartDto, ordersDto);

        // When
        User theUser = userService.updateUser(userDto);

        // Then
        assertEquals("UserName2", theUser.getFirstname());
        assertEquals("UserSurname2", theUser.getSurname());
        assertEquals("user2@gmail.com", theUser.getEmail());
        assertEquals("user2Password#$39", theUser.getPassword());
        assertEquals(true, theUser.getIsAdmin());
        assertEquals(true, theUser.getIsLogged());

        // CleanUp
        userService.deleteUser(user1Id);
    }

    @Test
    public void loginUserTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user2Password#$39", false, false);
        user1 = userService.saveUser(user1);
        Long user1Id = user1.getId();

        // When
        User theUser = userService.loginUser("user1@gmail.com", "user2Password#$39");

        // Then
        assertEquals(true, theUser.getIsLogged());

        // CleanUp
        userService.deleteUser(user1Id);
    }

    @Test
    public void logoutUserTestSuite() {
        // Given
        User user1 = new User("UserName1", "UserSurname1", "user1@gmail.com",
                "user2Password#$39", false, true);
        user1 = userService.saveUser(user1);
        Long user1Id = user1.getId();

        // When
        User theUser = userService.logoutUser(user1Id);

        // Then
        assertEquals(false, theUser.getIsLogged());

        // CleanUp
        userService.deleteUser(user1Id);
    }

}
