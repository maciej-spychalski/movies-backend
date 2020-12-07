package pl.asbt.movies.storage.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.asbt.movies.storage.dto.*;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.UserFacade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @Test
    public void createUser() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));
        List<OrderDto> ordersDto = new ArrayList<>();
        UserDto userDto = new UserDto(1L, "UserFirstname", "UserSurname", "user@gmail.com",
                "userPassword#$*38", false, false, cartDto, ordersDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        when(userFacade.createUser(ArgumentMatchers.any(UserDto.class))).thenReturn(userDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void getUser() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));
        List<OrderDto> ordersDto = new ArrayList<>();
        UserDto userDto = new UserDto(1L, "UserFirstname", "UserSurname", "user@gmail.com",
                "userPassword#$*38", false, false, cartDto, ordersDto);

        when(userFacade.fetchUser(ArgumentMatchers.anyLong())).thenReturn(userDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("UserFirstname")))
                .andExpect(jsonPath("$.surname", is("UserSurname")))
                .andExpect(jsonPath("$.email", is("user@gmail.com")))
                .andExpect(jsonPath("$.password", is("userPassword#$*38")))
                .andExpect(jsonPath("$.isAdmin", is(false)))
                .andExpect(jsonPath("$.isLogged", is(false)));
    }

    @Test
    public void getUsers() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));
        List<OrderDto> ordersDto = new ArrayList<>();
        UserDto userDto = new UserDto(1L, "UserFirstname", "UserSurname", "user@gmail.com",
                "userPassword#$*38", false, false, cartDto, ordersDto);
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(userDto);

        when(userFacade.fetchUsers()).thenReturn(usersDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstname", is("UserFirstname")))
                .andExpect(jsonPath("$[0].surname", is("UserSurname")))
                .andExpect(jsonPath("$[0].email", is("user@gmail.com")))
                .andExpect(jsonPath("$[0].password", is("userPassword#$*38")))
                .andExpect(jsonPath("$[0].isAdmin", is(false)))
                .andExpect(jsonPath("$[0].isLogged", is(false)));
    }

    @Test
    public void deleteUser() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("userId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(userFacade, Mockito.times(1)).deleteUser(ArgumentMatchers.anyLong());
    }

    @Test
    public void updateUser() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));
        List<OrderDto> ordersDto = new ArrayList<>();
        UserDto userDto = new UserDto(1L, "UserFirstname", "UserSurname", "user@gmail.com",
                "userPassword#$*38", false, false, cartDto, ordersDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        when(userFacade.updateUser(ArgumentMatchers.any(UserDto.class))).thenReturn(userDto);

        // When & Then
        mockMvc.perform(put("/v1/storage/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void loginUser() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));
        List<OrderDto> ordersDto = new ArrayList<>();
        UserDto userDto = new UserDto(1L, "UserFirstname", "UserSurname", "user@gmail.com",
                "userPassword#$*38", false, false, cartDto, ordersDto);

        when(userFacade.loginUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(userDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/users/login/user@gmail.com/userPassword#$*38")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("email", "user@gmail.com")
                .param("password", "userPassword#$*38"))
                .andExpect(status().isOk());
        Mockito.verify(userFacade, Mockito.times(1))
                .loginUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    public void logoutUser() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));
        List<OrderDto> ordersDto = new ArrayList<>();
        UserDto userDto = new UserDto(1L, "UserFirstname", "UserSurname", "user@gmail.com",
                "userPassword#$*38", false, false, cartDto, ordersDto);

        when(userFacade.loginUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(userDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/users/logout/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("userId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(userFacade, Mockito.times(1))
                .logoutUser(ArgumentMatchers.anyLong());
    }
}
