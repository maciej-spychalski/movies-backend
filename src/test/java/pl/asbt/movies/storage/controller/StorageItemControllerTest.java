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
import pl.asbt.movies.storage.dto.StorageItemDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.StorageItemFacade;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StorageItemController.class)
public class StorageItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageItemFacade storageItemFacade;

    @Test
    public void createStorageItem() throws Exception {
        // Given
        StorageItemDto storageItemDto = new StorageItemDto(1L, "title", 1L, 1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(storageItemDto);

        when(storageItemFacade.createStorageItem(ArgumentMatchers.any(StorageItemDto.class))).thenReturn(storageItemDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/storageItems")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void addQuantity() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/storageItems/add-quantity/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1")
                .param("quantity", "1"))
                .andExpect(status().isOk());
        Mockito.verify(storageItemFacade, Mockito.times(1))
                .addQuantity(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt());
    }

    @Test
    public void subQuantity() throws Exception {
        // When & Then
        mockMvc.perform(post("/v1/storage/storageItems/sub-quantity/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id", "1")
                .param("quantity", "1"))
                .andExpect(status().isOk());
        Mockito.verify(storageItemFacade, Mockito.times(1))
                .subQuantity(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt());
    }

    @Test
    public void getStorageItem() throws Exception {
        // Given
        StorageItemDto storageItemDto = new StorageItemDto(1L, "title", 1L, 1);

        when(storageItemFacade.fetchStorageItem(ArgumentMatchers.anyLong())).thenReturn(storageItemDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/storageItems/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("storageItemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.movieTitle", is("title")))
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.quantity", is(1)));
    }

    @Test
    public void getStorageItemByMovieTitle() throws Exception {
        // Given
        StorageItemDto storageItemDto = new StorageItemDto(1L, "title", 1L, 1);
        List<StorageItemDto> storageItemsDto = new ArrayList<>();
        storageItemsDto.add(storageItemDto);

        when(storageItemFacade.fetchStorageItemByMovieTitle(ArgumentMatchers.anyString())).thenReturn(storageItemsDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/storageItems/title/title")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("title", "title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].movieTitle", is("title")))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].quantity", is(1)));
    }

    @Test
    public void getStorageItems() throws Exception {
        // Given
        StorageItemDto storageItemDto = new StorageItemDto(1L, "title", 1L, 1);
        List<StorageItemDto> storageItemsDto = new ArrayList<>();
        storageItemsDto.add(storageItemDto);

        when(storageItemFacade.fetchStorageItems()).thenReturn(storageItemsDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/storageItems")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].movieTitle", is("title")))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].quantity", is(1)));
    }

    @Test
    public void deleteStorageItem() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/storageItems/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("storageItemId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(storageItemFacade, Mockito.times(1)).deleteStorageItem(ArgumentMatchers.anyLong());
    }

    @Test
    public void deleteStorageItemByMovieTitle() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/storageItems/title/title")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("title", "title"))
                .andExpect(status().isOk());
        Mockito.verify(storageItemFacade, Mockito.times(1))
                .deleteStorageItemByMovieTitle(ArgumentMatchers.anyString());
    }

    @Test
    public void updateStorageItem() throws Exception {
        // Given
        StorageItemDto storageItemDto = new StorageItemDto(1L, "title", 1L, 1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(storageItemDto);

        when(storageItemFacade.updateStorageItem(ArgumentMatchers.any(StorageItemDto.class))).thenReturn(storageItemDto);

        // When & Then
        mockMvc.perform(put("/v1/storage/storageItems")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }
}
