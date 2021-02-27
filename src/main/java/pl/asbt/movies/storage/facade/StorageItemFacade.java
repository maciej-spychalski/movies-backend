package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.StorageItemDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.StorageItemMapper;
import pl.asbt.movies.storage.service.StorageItemService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class StorageItemFacade {
    private final StorageItemService storageItemService;
    private final StorageItemMapper storageItemMapper;

    public StorageItemDto createStorageItem(StorageItemDto storageItemDto) {
        return storageItemMapper.mapToStorageItemDto(storageItemService.saveStorageItem(storageItemDto));
    }

    public void addQuantity(Long id, int quantity) {
        storageItemService.addQuantity(id, quantity);
    }

    public Boolean subQuantity(Long id, int quantity) {
        return storageItemService.subQuantity(id, quantity);
    }

    public StorageItemDto fetchStorageItem(Long storageItemId) throws StorageException {
        return storageItemMapper.mapToStorageItemDto(storageItemService.getStorageItem(storageItemId)
                .orElseThrow(() ->
                        StorageException.builder()
                                .errorType(ErrorType.NOT_FOUND)
                                .message("There are no storage item with given id.")
                                .build()
                ));
    }

    public List<StorageItemDto> fetchStorageItemByMovieTitle(String title) {
        return storageItemMapper.mapToStorageItemsDto(storageItemService.getStorageItemsByMovieTitle(title));
    }

    public List<StorageItemDto> fetchStorageItems() {
        return storageItemMapper.mapToStorageItemsDto(storageItemService.getAllStorageItems());
    }

    public void deleteStorageItem(Long storageItemId) {
        storageItemService.deleteStorageItem(storageItemId);
    }

    public void deleteStorageItemByMovieTitle(String title) {
        storageItemService.deleteStorageItemByMovieTitle(title);
    }

    public StorageItemDto updateStorageItem(StorageItemDto storageItemDto) {
        return storageItemMapper.mapToStorageItemDto(storageItemService.updateStorageItem(storageItemDto));
    }
}
