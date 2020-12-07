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
import pl.asbt.movies.storage.dto.ItemDto;
import pl.asbt.movies.storage.dto.OrderDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.OrderFacade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderFacade orderFacade;

    @Test
    public void createOrder() throws Exception {
        // Given
        ItemDto itemDto =  new ItemDto(1L, "Title", 1L, 1, new BigDecimal(1));
        List<ItemDto> itemsDto = new ArrayList<>();
        itemsDto.add(itemDto);
        OrderDto orderDto = new OrderDto(1L, false, itemsDto, new BigDecimal(10));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(orderDto);

        when(orderFacade.createOrder(ArgumentMatchers.any(OrderDto.class))).thenReturn(orderDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void getOrder() throws Exception {
        // Given
        ItemDto itemDto =  new ItemDto(1L, "Title", 1L, 1, new BigDecimal(1));
        List<ItemDto> itemsDto = new ArrayList<>();
        itemsDto.add(itemDto);
        OrderDto orderDto = new OrderDto(1L, false, itemsDto, new BigDecimal(10));

        when(orderFacade.fetchOrder(ArgumentMatchers.anyLong())).thenReturn(orderDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("orderId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.isFinalized", is(false)))
                .andExpect(jsonPath("$.price", is(10)));
    }

    @Test
    public void getOrders() throws Exception {
        // Given
        ItemDto itemDto =  new ItemDto(1L, "Title", 1L, 1, new BigDecimal(1));
        List<ItemDto> itemsDto = new ArrayList<>();
        itemsDto.add(itemDto);
        OrderDto orderDto = new OrderDto(1L, false, itemsDto, new BigDecimal(10));
        List<OrderDto> ordersDto = new ArrayList<>();
        ordersDto.add(orderDto);

        when(orderFacade.fetchOrders()).thenReturn(ordersDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].isFinalized", is(false)))
                .andExpect(jsonPath("$[0].price", is(10)));
    }

    @Test
    public void deleteOrder() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("orderId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(orderFacade, Mockito.times(1)).deleteOrder(ArgumentMatchers.anyLong());
    }

    @Test
    public void finalizeOrder() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/orders/finalize/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("orderId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(orderFacade, Mockito.times(1)).finalizeOrder(ArgumentMatchers.anyLong());
    }
}
