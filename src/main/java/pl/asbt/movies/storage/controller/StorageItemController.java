package pl.asbt.movies.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.domain.StorageItem;
import pl.asbt.movies.storage.domain.StorageItemDto;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.mapper.StorageItemMapper;
import pl.asbt.movies.storage.servise.MovieService;
import pl.asbt.movies.storage.servise.StorageItemService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/storageItems")
public class StorageItemController {
    @Autowired
    StorageItemService storageItemService;

    @Autowired
    StorageItemMapper storageItemMapper;

    @Autowired
    MovieService movieService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createStorageItem(@RequestBody StorageItemDto storageItemDto) throws SearchingException {
        Movie movie = new Movie();
        movie = movieService.getMovie(storageItemDto.getMovieId()).orElse(
                movieService.getAllMoviesByTitle(storageItemDto.getMovieTitle()).get(0));
        StorageItem storageItem = storageItemService.saveStorageItem(storageItemMapper.mapToStorageItem(storageItemDto));
        storageItem.setMovie(movie);
        storageItemService.saveStorageItem(storageItem);
    }

    @GetMapping(value = "/{storageItemId}")
    public StorageItemDto getStorageItem(@PathVariable Long storageItemId) throws SearchingException {
        return storageItemMapper.mapToStorageItemDto(storageItemService.getStorageItem(storageItemId).
                orElseThrow(SearchingException::new));
    }

    @GetMapping(value = "/title/{title}")
    public List<StorageItemDto> getStorageItemByMovieTitle(@PathVariable String title) {
        return storageItemMapper.mapToStorageItemsDto(storageItemService.getStorageItemsByMovieTitle(title));
    }

    @GetMapping
    public List<StorageItemDto> getStorageItems() {
        return storageItemMapper.mapToStorageItemsDto(storageItemService.getAllStorageItems());
    }

    @DeleteMapping(value = "/{storageItemId}")
    public void deleteStorageItem(@PathVariable Long storageItemId) {
        storageItemService.deleteStorageItem(storageItemId);
    }

    @DeleteMapping(value = "/title/{title}")
    public void deleteStorageItemByMovieTitle(@PathVariable String title) {
        storageItemService.deleteStorageItemByMovieTitle(title);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public StorageItemDto updateStorageItem(@RequestBody StorageItemDto storageItemDto) throws SearchingException {
        return storageItemMapper.mapToStorageItemDto(storageItemService.updateStorageItem(storageItemDto));
    }
}
