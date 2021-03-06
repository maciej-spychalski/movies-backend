package pl.asbt.movies.storage.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.*;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.StorageItemMapper;
import pl.asbt.movies.storage.repository.StorageItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EnableScheduling
@RequiredArgsConstructor
@Service
public class StorageItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageItemService.class);
    private static final int MINIMUM_STORAGE_ITEM_QUANTITY = 200;
    private final StorageItemRepository storageItemRepository;
    private final StorageItemMapper storageItemMapper;
    private final MovieService movieService;

    public StorageItem saveStorageItem(final StorageItemDto storageItemDto) {
        Movie movie = movieService.getMovie(storageItemDto.getMovieId()).orElse(
                movieService.getAllMoviesByTitle(storageItemDto.getMovieTitle()).get(0));

        int quantity = 0;
        Long id = 0L;
        List<StorageItem> storagesItem = getAllStorageItems();
        for (StorageItem theStorageItem : storagesItem) {
            if (theStorageItem.getMovie().getTitle().equals(storageItemDto.getMovieTitle())) {
                quantity = storageItemDto.getQuantity();
                id = theStorageItem.getId();
                return addQuantity(id, quantity);
            }
        }

        StorageItem storageItem = storageItemRepository.save(storageItemMapper.mapToStorageItem(storageItemDto));
        storageItem.setMovie(movie);
        return storageItemRepository.save(storageItem);
    }

    public StorageItem addQuantity(Long id, int quantity) {
        StorageItem storageItem = getStorageItem(id).orElse(new StorageItem());
        storageItem.setQuantity(storageItem.getQuantity() + quantity);
        return storageItemRepository.save(storageItem);
    }

    public Boolean subQuantity(Long id, int quantity) {
        Boolean succeed = false;
        StorageItem storageItem = getStorageItem(id).orElse(new StorageItem());
        int currentQuantity = storageItem.getQuantity();
        if (currentQuantity - quantity > 0) {
            storageItem.setQuantity(currentQuantity - quantity);
            succeed = true;
            storageItemRepository.save(storageItem);
        }
        return succeed;
    }

    public Optional<StorageItem> getStorageItem(final Long id) {
        return storageItemRepository.findById(id);
    }

    public List<StorageItem> getStorageItemsByMovieTitle(final String title) {
        return storageItemRepository.findByMovie_Title(title);
    }

    public List<StorageItem> getAllStorageItems() {
        return storageItemRepository.findAll();
    }

    public void deleteStorageItem(final Long id) {
        storageItemRepository.deleteById(id);
    }

    public void deleteStorageItemByMovieTitle(final String title) {
        storageItemRepository.deleteByMovie_Title(title);
    }

    public StorageItem updateStorageItem(final StorageItemDto storageItemDto) {
        StorageItem result = new StorageItem();
        Long storageId = storageItemDto.getId();
        try {
            StorageItem storageItem = getStorageItem(storageId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no storage item with given id.")
                            .build()
            );
            storageItem.setQuantity(storageItemDto.getQuantity());
            return storageItemRepository.save(storageItem);
        } catch (Exception e) {
            LOGGER.error("Storage item: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

    @Scheduled(cron = "0 0 10 * * *") // once a day
    public void stockUpStorageItem() {
        List<StorageItem> storageItems = new ArrayList<>();
        storageItems = getAllStorageItems();
        for (StorageItem theStorageItem : storageItems) {
            if (theStorageItem.getQuantity() < MINIMUM_STORAGE_ITEM_QUANTITY) {
                theStorageItem.setQuantity(MINIMUM_STORAGE_ITEM_QUANTITY);
                storageItemRepository.save(theStorageItem);
            }
        }
    }

}
