package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.exception.CreatingException;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.domain.StorageItem;
import pl.asbt.movies.storage.domain.StorageItemDto;
import pl.asbt.movies.storage.mapper.StorageItemMapper;
import pl.asbt.movies.storage.repository.MovieRepository;
import pl.asbt.movies.storage.repository.StorageItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StorageItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageItemService.class);
    private StorageItemRepository storageItemRepository;

    @Autowired
    public StorageItemService(StorageItemRepository storageItemRepository) {
        this.storageItemRepository = storageItemRepository;
    }

    public StorageItem saveStorageItem(final StorageItem storageItem) {
        return storageItemRepository.save(storageItem);
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

    public StorageItem updateStorageItem(final StorageItemDto storageItemDto) throws SearchingException {
        StorageItem result = new StorageItem();
        Long storageId = storageItemDto.getId();
        try {
            StorageItem storageItem = getStorageItem(storageId).orElseThrow(SearchingException::new);
            storageItem.setQuantity(storageItemDto.getQuantity());
            return saveStorageItem(storageItem);
        } catch (Exception e) {
            LOGGER.error(SearchingException.ERR_NO_STORAGE_ITEM);
        }
        return result;
    }

}
