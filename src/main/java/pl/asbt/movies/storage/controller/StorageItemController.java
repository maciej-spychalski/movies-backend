package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.StorageItemDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.StorageItemMapper;
import pl.asbt.movies.storage.servise.StorageItemService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/storageItems")
public class StorageItemController {

    private final StorageItemService storageItemService;
    private final StorageItemMapper storageItemMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createStorageItem(@Validated @RequestBody StorageItemDto storageItemDto) {
        storageItemService.saveStorageItem(storageItemDto);
    }

    @PatchMapping(value = "/add-Quantity/{id}/{quantity}")
    public void addQuantity(@Validated @PathVariable Long id,
                            @Validated @PathVariable int quantity) {
        storageItemService.addQuantity(id, quantity);
    }

    @PatchMapping(value = "/remove-Quantity/{id}/{quantity}")
    public Boolean subQuantity(@Validated @PathVariable Long id,
                               @Validated @PathVariable int quantity) {
        return storageItemService.subQuantity(id, quantity);
    }

    @GetMapping(value = "/{storageItemId}")
    public StorageItemDto getStorageItem(@Validated @PathVariable Long storageItemId) throws StorageException {
        return storageItemMapper.mapToStorageItemDto(storageItemService.getStorageItem(storageItemId)
                .orElseThrow(() ->
                        StorageException.builder()
                                .errorType(ErrorType.NOT_FOUND)
                                .message("There are no storage item with given id.")
                                .build()
                ));
    }

    @GetMapping(value = "/title/{title}")
    public List<StorageItemDto> getStorageItemByMovieTitle(@Validated @PathVariable String title) {
        return storageItemMapper.mapToStorageItemsDto(storageItemService.getStorageItemsByMovieTitle(title));
    }

    @GetMapping
    public List<StorageItemDto> getStorageItems() {
        return storageItemMapper.mapToStorageItemsDto(storageItemService.getAllStorageItems());
    }

    @DeleteMapping(value = "/{storageItemId}")
    public void deleteStorageItem(@Validated @PathVariable Long storageItemId) {
        storageItemService.deleteStorageItem(storageItemId);
    }

    @DeleteMapping(value = "/title/{title}")
    public void deleteStorageItemByMovieTitle(@Validated @PathVariable String title) {
        storageItemService.deleteStorageItemByMovieTitle(title);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public StorageItemDto updateStorageItem(@Validated @RequestBody StorageItemDto storageItemDto) {
        return storageItemMapper.mapToStorageItemDto(storageItemService.updateStorageItem(storageItemDto));
    }
}
