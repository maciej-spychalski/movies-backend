package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.StorageItemDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.StorageItemFacade;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/storageItems")
public class StorageItemController {
    private final StorageItemFacade saveStorageItem;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createStorageItem(@Validated @RequestBody StorageItemDto storageItemDto) {
        saveStorageItem.createStorageItem(storageItemDto);
    }

//    @PatchMapping(value = "/add-quantity/{id}/{quantity}")
//    @PutMapping(value = "/add-quantity/{id}/{quantity}")
    @PostMapping(value = "/add-quantity/{id}/{quantity}")
    public void addQuantity(@Validated @PathVariable Long id,
                            @Validated @PathVariable int quantity) {
        saveStorageItem.addQuantity(id, quantity);
    }

//    @PatchMapping(value = "/sub-quantity/{id}/{quantity}")
//    @PutMapping(value = "/sub-quantity/{id}/{quantity}")
    @PostMapping(value = "/sub-quantity/{id}/{quantity}")
    public Boolean subQuantity(@Validated @PathVariable Long id,
                               @Validated @PathVariable int quantity) {
        return saveStorageItem.subQuantity(id, quantity);
    }

    @GetMapping(value = "/{storageItemId}")
    public StorageItemDto getStorageItem(@Validated @PathVariable Long storageItemId) throws StorageException {
        return saveStorageItem.fetchStorageItem(storageItemId);
    }

    @GetMapping(value = "/title/{title}")
    public List<StorageItemDto> getStorageItemByMovieTitle(@Validated @PathVariable String title) {
        return saveStorageItem.fetchStorageItemByMovieTitle(title);
    }

    @GetMapping
    public List<StorageItemDto> getStorageItems() {
        return saveStorageItem.fetchStorageItems();
    }

    @DeleteMapping(value = "/{storageItemId}")
    public void deleteStorageItem(@Validated @PathVariable Long storageItemId) {
        saveStorageItem.deleteStorageItem(storageItemId);
    }

    @DeleteMapping(value = "/title/{title}")
    public void deleteStorageItemByMovieTitle(@Validated @PathVariable String title) {
        saveStorageItem.deleteStorageItemByMovieTitle(title);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public StorageItemDto updateStorageItem(@Validated @RequestBody StorageItemDto storageItemDto) {
        return saveStorageItem.updateStorageItem(storageItemDto);
    }
}
