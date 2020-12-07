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
import pl.asbt.movies.storage.dto.CartDto;
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.facade.CartFacade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartFacade cartFacade;

    @Test
    public void createCart() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(cartDto);

        when(cartFacade.createCart(ArgumentMatchers.any(CartDto.class))).thenReturn(cartDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void getCart() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));

        when(cartFacade.fetchCard(ArgumentMatchers.anyLong())).thenReturn(cartDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/carts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("cartId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getCarts() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));
        List<CartDto> cartsDto = new ArrayList<>();
        cartsDto.add(cartDto);

        when(cartFacade.fetchCarts()).thenReturn(cartsDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));

    }

    @Test
    public void deleteCart() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/carts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("cartId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(cartFacade, Mockito.times(1)).deleteCart(ArgumentMatchers.anyLong());
    }

    @Test
    public void createOrder() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/carts/create-order/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("cartId", "1")
                .param("userId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(cartFacade, Mockito.times(1))
                .createOrder(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void addItem() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/carts/add-item/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("cartId", "1")
                .param("itemId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(cartFacade, Mockito.times(1))
                .addItem(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void deleteItem() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/carts/delete-item/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("cartId", "1")
                .param("itemId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(cartFacade, Mockito.times(1))
                .deleteItem(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void updateCartPrice() throws Exception {
        // Given
        List<ItemDto> itemsDto = new ArrayList<>();
        CartDto cartDto = new CartDto(1L, itemsDto, new BigDecimal(0));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(cartDto);

        when(cartFacade.createCart(ArgumentMatchers.any(CartDto.class))).thenReturn(cartDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/carts/update-price/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("cartId", "1")
                .content(jsonContent));
    }
}
