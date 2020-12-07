package pl.asbt.movies.storage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.StorageItemDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageItemMapperTest {

    @Autowired
    private StorageItemMapper storageItemMapper;

    @Test
    public void mapToStorageItemTest() {
        // Given
        StorageItemDto storageItemDto = new StorageItemDto(1L, "title", 1L, 10);

        // When
        StorageItem storageItem = storageItemMapper.mapToStorageItem(storageItemDto);

        // Then
        assertEquals(10, (int) storageItem.getQuantity());
    }

    @Test
    public void mapToStorageItemDtoTest() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie(1L,"title", director, writers, actors, genres, 90, new BigDecimal(10));
        StorageItem storageItem =  new StorageItem(1L, movie, 1);

        // When
        StorageItemDto storageItemDto = storageItemMapper.mapToStorageItemDto(storageItem);

        // Then
        assertEquals(1L, (long) storageItemDto.getId());
        assertEquals("title", storageItemDto.getMovieTitle());
        assertEquals(1L, (long) storageItemDto.getMovieId());
        assertEquals(1, (int) storageItemDto.getQuantity());
    }

    @Test
    public void mapToStorageItemsDto() {
        // Given
        Director director = new Director();
        List<Writer> writers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        Movie movie = new Movie(1L,"title", director, writers, actors, genres, 90, new BigDecimal(10));
        StorageItem storageItem =  new StorageItem(1L, movie, 1);
        List<StorageItem> storageItems = new ArrayList<>();
        storageItems.add(storageItem);

        // When
        List<StorageItemDto> storageItemsDto = storageItemMapper.mapToStorageItemsDto(storageItems);

        // Then
        assertEquals(1L, (long) storageItemsDto.get(0).getId());
        assertEquals("title", storageItemsDto.get(0).getMovieTitle());
        assertEquals(1L, (long) storageItemsDto.get(0).getMovieId());
        assertEquals(1, (int) storageItemsDto.get(0).getQuantity());
    }
}
