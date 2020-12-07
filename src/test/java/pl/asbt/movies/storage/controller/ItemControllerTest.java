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
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.ItemFacade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemFacade itemFacade;

    @Test
    public void createItem() throws Exception {
        // Given
        ItemDto itemDto = new ItemDto(1L, "title", 1L, 1, new BigDecimal(10));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(itemDto);

        when(itemFacade.createItem(ArgumentMatchers.any(ItemDto.class))).thenReturn(itemDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/items")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void getItem() throws Exception {
        // Given
        ItemDto itemDto = new ItemDto(1L, "title", 1L, 1, new BigDecimal(10));

        when(itemFacade.fetchItem(ArgumentMatchers.anyLong())).thenReturn(itemDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.movieTitle", is("title")))
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.quantity", is(1)))
                .andExpect(jsonPath("$.price", is(10)));
    }

    @Test
    public void addQuantity() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/items/add-quantity/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1")
                .param("quantity", "1"))
                .andExpect(status().isOk());
        Mockito.verify(itemFacade, Mockito.times(1))
                .addQuantity(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt());
    }

    @Test
    public void subQuantity() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/items/sub-quantity/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1")
                .param("quantity", "1"))
                .andExpect(status().isOk());
        Mockito.verify(itemFacade, Mockito.times(1))
                .subQuantity(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt());
    }

    @Test
    public void getItems() throws Exception {
        // Given
        ItemDto itemDto = new ItemDto(1L, "title", 1L, 1, new BigDecimal(10));
        List<ItemDto> itemsDto = new ArrayList<>();
        itemsDto.add(itemDto);

        when(itemFacade.fetchItems()).thenReturn(itemsDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/items")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].movieTitle", is("title")))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].quantity", is(1)))
                .andExpect(jsonPath("$[0].price", is(10)));

    }

    @Test
    public void deleteItem() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("itemId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(itemFacade, Mockito.times(1)).deleteItem(ArgumentMatchers.anyLong());
    }

    @Test
    public void updateItem() throws Exception {
        // Given
        ItemDto itemDto = new ItemDto(1L, "title", 1L, 1, new BigDecimal(10));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(itemDto);

        when(itemFacade.updateItem(ArgumentMatchers.any(ItemDto.class))).thenReturn(itemDto);

        // When & Then
        mockMvc.perform(put("/v1/storage/items")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }
}
