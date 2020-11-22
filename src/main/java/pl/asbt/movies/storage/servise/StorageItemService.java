package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.*;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.StorageItemMapper;
import pl.asbt.movies.storage.repository.StorageItemRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StorageItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageItemService.class);
    private final StorageItemRepository storageItemRepository;
    private final StorageItemMapper storageItemMapper;
    private final MovieService movieService;

    public StorageItem saveStorageItem(final StorageItem storageItem) {
        return storageItemRepository.save(storageItem);
    }

    public StorageItem saveStorageItem(final StorageItemDto storageItemDto) {
        Movie movie = movieService.getMovie(storageItemDto.getMovieId()).orElse(
                movieService.getAllMoviesByTitle(storageItemDto.getMovieTitle()).get(0));
        StorageItem storageItem = storageItemRepository.save(storageItemMapper.mapToStorageItem(storageItemDto));
        storageItem.setMovie(movie);
        return storageItemRepository.save(storageItem);
    }

    public void addQuantity(Long id, int quantity) {
        StorageItem storageItem = getStorageItem(id).orElse(new StorageItem());
        storageItem.setQuantity(storageItem.getQuantity() + quantity);
        storageItemRepository.save(storageItem);
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
            return saveStorageItem(storageItem);
        } catch (Exception e) {
            LOGGER.error("Storage item: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

}
